package pe.buildsoft.erp.core.domain.servicios.cola;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import pe.buildsoft.erp.core.domain.cache.ConfiguracionColaCacheSingletonLocal;
import pe.buildsoft.erp.core.domain.entidades.cola.Cola;
import pe.buildsoft.erp.core.domain.entidades.cola.ColaNoctura;
import pe.buildsoft.erp.core.domain.entidades.cola.ConfiguracionFiltroCola;
import pe.buildsoft.erp.core.domain.entidades.cola.ConfiguracionFiltroReporte;
import pe.buildsoft.erp.core.domain.entidades.cola.IntentoGenerarReporte;
import pe.buildsoft.erp.core.domain.entidades.cola.SolicitudReporte;
import pe.buildsoft.erp.core.domain.entidades.cola.ValorConfiguracionFiltroReporte;
import pe.buildsoft.erp.core.domain.interfaces.repositories.cola.ConfiguracionFiltroReporteDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.cola.IntentoGenerarReporteDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.servicios.cola.ConfiguracionColaServiceLocal;
import pe.buildsoft.erp.core.domain.interfaces.servicios.cola.IJMSSender;
import pe.buildsoft.erp.core.domain.interfaces.servicios.cola.ProcesarColaServiceLocal;
import pe.buildsoft.erp.core.domain.interfaces.servicios.cola.ReporteServiceLocal;
import pe.buildsoft.erp.core.domain.interfaces.servicios.cola.ValorConfiguracionFiltroReporteDaoLocal;
import pe.buildsoft.erp.core.domain.util.ConfiguracionJMSUtil;
import pe.buildsoft.erp.core.domain.util.ParametroUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.SolicitudReporteDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RegistroMensajeriaOutputVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaAsignacionColaVO;
import pe.buildsoft.erp.core.infra.transversal.state.EstadoGeneralState;
import pe.buildsoft.erp.core.infra.transversal.state.EstadoSolicitudEjecucionEstate;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.type.ColaDisponibleType;
import pe.buildsoft.erp.core.infra.transversal.type.MarcaRangoType;
import pe.buildsoft.erp.core.infra.transversal.type.TipoDatoFiltroType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.BaseTransfer;
import pe.buildsoft.erp.core.infra.transversal.utilitario.CollectionUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ConfiguracionUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.FechaUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.NumerosUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ObjectUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.SerializationUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class ProcesarColaServiceLocal.
 * <ul>
 * <li>Copyright 2014 MAPFRE- OSCE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Tue Apr 29 19:20:13 COT 2014
 * @since Rep v1..0
 */
@Stateless // Servicios remotos
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ProcesarColaServiceImpl extends BaseTransfer implements ProcesarColaServiceLocal {

	/** El log. */
	private static Logger log = LoggerFactory.getLogger(ProcesarColaServiceImpl.class);

	@Inject
	private IntentoGenerarReporteDaoLocal intentoGenerarReporteDaoImpl;

	@Inject
	private ValorConfiguracionFiltroReporteDaoLocal valorConfiguracionFiltroReporteDaoImpl;

	@Inject
	private ConfiguracionFiltroReporteDaoLocal configuracionFiltroReporteDaoImpl;

	@Inject
	private ReporteServiceLocal reporteServiceImpl;

	@Inject
	private ConfiguracionColaServiceLocal configuracionColaServiceImpl;

	@Inject
	private ConfiguracionColaCacheSingletonLocal confCache;

	@Inject
	private IJMSSender jMSSender;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.gob.osce.bsp.nms.ejb.service.local.MensajeriaServiceLocal#enviarCorreo(
	 * java.util.List)
	 */
	@Override
	public RegistroMensajeriaOutputVO enviarCola(List<Object> listaObjectos) {
		RegistroMensajeriaOutputVO resultado = new RegistroMensajeriaOutputVO();
		try {
			jMSSender.sendMessageList(listaObjectos);
		} catch (Exception e) {
			log.error(
					"Error ProcesarColaServiceImpl.enviarReporte(final List<SolicitudReporteDTO> solicitudReporteDTOs) al enviar cola de reporte "
							+ e.getMessage());
			resultado.setError(e.toString());
		}
		return resultado;
	}

	@Override
	public RegistroMensajeriaOutputVO enviarColaGenerarSolicitudReporte(SolicitudReporteDTO obj) {
		RegistroMensajeriaOutputVO resultado = new RegistroMensajeriaOutputVO();
		try {
			SolicitudReporte solicitudReporte = to(obj, SolicitudReporte.class);

			if (obj.getIdSolicitudReporte() == null) {
				obj.setIntentos(0L);
				solicitudReporte = to(obj, SolicitudReporte.class);
				solicitudReporte = reporteServiceImpl.controladorAccionSolicitudReporte(solicitudReporte,
						AccionType.CREAR);
				obj.setIdSolicitudReporte(solicitudReporte.getIdSolicitudReporte());
			}

			/* Inicio Gestor de Colas */
			if (obj.getIdOpcion() == null || StringUtil.isNullOrEmptyNumeric(obj.getCodigoCola())) {
				jMSSender.sendMessage(obj);
			} else if (obj.getCriterioFiltro() != null) {
				jMSSender.sendMessageRegular(obj);
			} else {
				boolean isTieneColaNocturna = obj.getPametrosMap().containsKey(ParametroUtil.ES_COLA_NOCTURNA);
				if (!isTieneColaNocturna) {
					RespuestaAsignacionColaVO respuestaAsignacionCola = registrarFiltroAndAsignarCola(obj);
					obj.setIdCola(respuestaAsignacionCola.getColaDisponibleType().getKey());
					obj.setCodigoJuego(respuestaAsignacionCola.getCodigoJuego());
					ColaDisponibleType colaDisponibleType = respuestaAsignacionCola.getColaDisponibleType();
					// Actualizar Datos de la Evaluacion
					solicitudReporte = to(obj, SolicitudReporte.class);
					solicitudReporte = reporteServiceImpl.controladorAccionSolicitudReporte(solicitudReporte,
							AccionType.MODIFICAR);
					switch (colaDisponibleType) {
						case PREFERENCIAL:
							jMSSender.sendMessagePreferencial(obj);
							break;
						case REGULAR:
							jMSSender.sendMessageRegular(obj);
							break;
						case PESADO:
							jMSSender.sendMessagePesado(obj);
							break;
						case NOCTURNO:
							registrarColaNocturna(obj);
							break;
						default:
							jMSSender.sendMessage(obj);
							break;
					}
				} else {
					jMSSender.sendMessageNocturno(obj);
				}
			}
			/* Fin Gestor de Colas */
		} catch (Exception e) {
			if (obj.getIntentos().intValue() == 0) {
				obj.setIdSolicitudReporte(null);
			}
			guardarIntentosGenerarReporteFallidos(obj, e.getMessage(), true);
			log.error(
					"Error ProcesarColaServiceImpl.enviarColaGenerarSolicitudReporte(SolicitudReporte solicitudReporte) al enviar cola de reporte "
							+ e.getMessage());
			resultado.setError(e.toString());
			log.error("enviarColaGenerarSolicitudReporte", e);
		}

		return resultado;
	}

	private void registrarColaNocturna(SolicitudReporteDTO solicitudReporte) {
		ColaNoctura colaNoctura = new ColaNoctura();
		colaNoctura.setObjectoJson(SerializationUtil.toString(solicitudReporte));
		colaNoctura.setFechaActualizacion(FechaUtil.obtenerFechaActual());
		colaNoctura.setCodigoUsuario(solicitudReporte.getUserName());
		colaNoctura.setEstadoProceso(EstadoSolicitudEjecucionEstate.PENDIENTE.getKey());
		colaNoctura.setEstado(EstadoGeneralState.ACTIVO.getKey());
		colaNoctura.setIdSolicitudReporte(solicitudReporte.getIdSolicitudReporte());
		configuracionColaServiceImpl.controladorAccionColaNoctura(colaNoctura, AccionType.CREAR);
	}

	private RespuestaAsignacionColaVO registrarFiltroAndAsignarCola(SolicitudReporteDTO solicitudReporte) {
		log.info("1.Traer configuracion de filtro para saber que campos leer del objeto");
		var filtro = new BaseSearch();
		filtro.setIdOpcionMenu(solicitudReporte.getIdOpcion());
		List<ConfiguracionFiltroReporte> configuracionFiltroReporteList = configuracionColaServiceImpl
				.listarConfiguracionFiltroReporte(filtro);
		log.info("1.1.-Grabar los criterios usados");
		List<ValorConfiguracionFiltroReporte> listaValorFiltro = this.registrarCriterio(solicitudReporte,
				configuracionFiltroReporteList);
		log.info("2.-Obtener evaluacion de asignar cola");
		return this.evaluarAsignacionCola(solicitudReporte, listaValorFiltro);
	}

	private RespuestaAsignacionColaVO evaluarAsignacionCola(SolicitudReporteDTO solicitudReporte,
			List<ValorConfiguracionFiltroReporte> listaValorFiltro) {
		RespuestaAsignacionColaVO resultado = new RespuestaAsignacionColaVO();
		resultado.setColaDisponibleType(ColaDisponibleType.get(solicitudReporte.getCodigoCola()));
		var filtro = new BaseSearch();
		Map<String, String> valorFiltroMap = new HashMap<>();
		Map<String, String> valorFiltroReporteMap = new HashMap<>();
		for (var objValorFiltroReporte : listaValorFiltro) {
			String key = objValorFiltroReporte.getConfiguracionFiltroReporte().getIdCodigoFiltro() + ""
					+ objValorFiltroReporte.getValorFiltro();
			valorFiltroMap.put(key.trim(), objValorFiltroReporte.getValorFiltro());
			valorFiltroReporteMap.put(objValorFiltroReporte.getConfiguracionFiltroReporte().getIdCodigoFiltro() + "",
					objValorFiltroReporte.getValorFiltro());
		}
		filtro.setIdOpcionMenu(solicitudReporte.getIdOpcion());
		filtro.setEstado(EstadoGeneralState.ACTIVO.getKey());
		Long nivelActual = confCache.getColaMap().get(solicitudReporte.getCodigoCola() + "").getNivelCola();
		TreeMap<Long, List<ConfiguracionFiltroCola>> listaConfiguracionFiltroCola = configuracionColaServiceImpl
				.listarConfiguracionFiltroColaMap(filtro);
		if (!CollectionUtil.isEmpty(listaConfiguracionFiltroCola)) {
			// evaluando la cola a asignar
			boolean encontroConfiguracionJuego = false;
			for (var dataColaConfiguracion : listaConfiguracionFiltroCola.entrySet()) {
				// Inicio lista Grupo o Juego
				int cantidadFiltro = dataColaConfiguracion.getValue().size();
				int cantidadFiltroEncontro = 0;
				for (var objFiltroCola : dataColaConfiguracion.getValue()) {
					// verificando que solo tenga datos
					String valorComodin = objFiltroCola.getValorJuegoFiltro();
					String valorReporteFiltro = valorFiltroReporteMap
							.get(objFiltroCola.getConfiguracionFiltroReporte().getIdCodigoFiltro() + "");
					boolean isComodin = (valorComodin != null && valorComodin.contains("*"))
							|| (valorComodin != null && (valorReporteFiltro != null && valorReporteFiltro.equals(""))
									&& valorComodin.contains("-"));
					if ((objFiltroCola.getConfiguracionFiltroReporte() != null
							&& objFiltroCola.getConfiguracionFiltroReporte().getIdCodigoFiltro() > 0)
							&& (objFiltroCola.getConfiguracionFiltroReporteRango() == null || objFiltroCola
									.getConfiguracionFiltroReporteRango().getIdCodigoFiltro() == null)) {
						if (MarcaRangoType.NO.getKey().equalsIgnoreCase(objFiltroCola.getMarcaRango())) {
							String key = objFiltroCola.getConfiguracionFiltroReporte().getIdCodigoFiltro() + ""
									+ objFiltroCola.getValorJuegoFiltro();
							if (valorFiltroMap.containsKey(key.trim()) || isComodin) {
								cantidadFiltroEncontro++;
							}
						} else {
							TipoDatoFiltroType tipoDatoFiltroType = TipoDatoFiltroType
									.get(objFiltroCola.getConfiguracionFiltroReporte().getTipoDatoFiltro());
							String key = objFiltroCola.getConfiguracionFiltroReporte().getIdCodigoFiltro() + "";
							switch (tipoDatoFiltroType) {
								case NUMERICO:
									if (valorFiltroReporteMap.containsKey(key) && !isComodin) {
										BigDecimal valorFiltro = ObjectUtil
												.objectToBigDecimal(valorFiltroReporteMap.get(key));
										BigDecimal rangoDesde = ObjectUtil
												.objectToBigDecimal(objFiltroCola.getValorRangoDesde());
										BigDecimal rangoHasta = ObjectUtil
												.objectToBigDecimal(objFiltroCola.getValorRangoHasta());
										if (NumerosUtil.estaDentroRango(valorFiltro, rangoDesde, rangoHasta)) {
											cantidadFiltroEncontro++;
										}
									} else {
										if (isComodin) {
											cantidadFiltroEncontro++;
										}
									}
									break;
								case FECHA:
									if (valorFiltroReporteMap.containsKey(key) && !isComodin) {
										String formato = objFiltroCola.getConfiguracionFiltroReporte()
												.getFormatoTipoDato();
										OffsetDateTime valorFiltro = FechaUtil.obtenerFechaFormatoPersonalizado(
												valorFiltroReporteMap.get(key), formato);
										OffsetDateTime rangoDesde = FechaUtil.obtenerFechaFormatoPersonalizado(
												objFiltroCola.getValorRangoDesde(), formato);
										OffsetDateTime rangoHasta = FechaUtil.obtenerFechaFormatoPersonalizado(
												objFiltroCola.getValorRangoHasta(), formato);
										if (FechaUtil.estaDentroRango(valorFiltro, rangoDesde, rangoHasta)) {
											cantidadFiltroEncontro++;
										}
									} else {
										if (isComodin) {
											cantidadFiltroEncontro++;
										}
									}
									break;
								case HORA:
									if (valorFiltroReporteMap.containsKey(key) && !isComodin) {
										String formato = objFiltroCola.getConfiguracionFiltroReporte()
												.getFormatoTipoDato();
										OffsetDateTime valorFiltro = FechaUtil.obtenerFechaFormatoPersonalizado(
												valorFiltroReporteMap.get(key), formato);
										OffsetDateTime rangoDesde = FechaUtil.obtenerFechaFormatoPersonalizado(
												objFiltroCola.getValorRangoDesde(), formato);
										OffsetDateTime rangoHasta = FechaUtil.obtenerFechaFormatoPersonalizado(
												objFiltroCola.getValorRangoHasta(), formato);
										if (FechaUtil.estaDentroRango(valorFiltro, rangoDesde, rangoHasta)) {
											cantidadFiltroEncontro++;
										}
									} else {
										if (isComodin) {
											cantidadFiltroEncontro++;
										}
									}
									break;
								default:
									break;
							}

						}
					} else if (objFiltroCola.getConfiguracionFiltroReporte().getIdCodigoFiltro() > 0
							&& objFiltroCola.getConfiguracionFiltroReporteRango().getIdCodigoFiltro() > 0) {
						TipoDatoFiltroType tipoDatoFiltroType = TipoDatoFiltroType
								.get(objFiltroCola.getConfiguracionFiltroReporte().getTipoDatoFiltro());
						String key = objFiltroCola.getConfiguracionFiltroReporte().getIdCodigoFiltro() + "";
						String keyDepen = objFiltroCola.getConfiguracionFiltroReporteRango().getIdCodigoFiltro() + "";
						switch (tipoDatoFiltroType) {
							case NUMERICO:
								if (valorFiltroReporteMap.containsKey(key) && !isComodin) {
									BigDecimal valorFiltro = ObjectUtil
											.objectToBigDecimal(valorFiltroReporteMap.get(key));
									BigDecimal valorFiltroDepen = ObjectUtil
											.objectToBigDecimal(valorFiltroReporteMap.get(keyDepen));
									BigDecimal valorFiltroDiferencia = valorFiltro.subtract(valorFiltroDepen);
									BigDecimal rangoDesde = ObjectUtil
											.objectToBigDecimal(objFiltroCola.getValorRangoDesde());
									BigDecimal rangoHasta = ObjectUtil
											.objectToBigDecimal(objFiltroCola.getValorRangoHasta());
									if (NumerosUtil.estaDentroRango(valorFiltroDiferencia, rangoDesde, rangoHasta)) {
										cantidadFiltroEncontro++;
									}
								} else {
									if (isComodin) {
										cantidadFiltroEncontro++;
									}
								}
								break;
							case FECHA:
								if (valorFiltroReporteMap.containsKey(key) && !isComodin) {
									String formato = objFiltroCola.getConfiguracionFiltroReporte().getFormatoTipoDato();
									String formatoDepen = objFiltroCola.getConfiguracionFiltroReporteRango()
											.getFormatoTipoDato();
									OffsetDateTime valorFiltro = FechaUtil
											.obtenerFechaFormatoPersonalizado(valorFiltroReporteMap.get(key), formato);
									OffsetDateTime valorFiltroDepen = FechaUtil.obtenerFechaFormatoPersonalizado(
											valorFiltroReporteMap.get(keyDepen), formatoDepen);
									long valorFiltroDiferencia = FechaUtil.restarFechas(valorFiltroDepen, valorFiltro);
									BigDecimal rangoDesde = ObjectUtil
											.objectToBigDecimal(objFiltroCola.getValorRangoDesde());
									BigDecimal rangoHasta = ObjectUtil
											.objectToBigDecimal(objFiltroCola.getValorRangoHasta());
									if (NumerosUtil.estaDentroRango(valorFiltroDiferencia, rangoDesde.intValue(),
											rangoHasta.intValue())) {
										cantidadFiltroEncontro++;
									}
								} else {
									if (isComodin) {
										cantidadFiltroEncontro++;
									}
								}

								break;
							case HORA:
								if (valorFiltroReporteMap.containsKey(key) && !isComodin) {
									String formato = objFiltroCola.getConfiguracionFiltroReporte().getFormatoTipoDato();
									String formatoDepen = objFiltroCola.getConfiguracionFiltroReporteRango()
											.getFormatoTipoDato();
									OffsetDateTime valorFiltro = FechaUtil
											.obtenerFechaFormatoPersonalizado(valorFiltroReporteMap.get(key), formato);
									OffsetDateTime valorFiltroDepen = FechaUtil.obtenerFechaFormatoPersonalizado(
											valorFiltroReporteMap.get(keyDepen), formatoDepen);
									long valorFiltroDiferencia = FechaUtil.restarFechasHora(valorFiltroDepen,
											valorFiltro);
									BigDecimal rangoDesde = ObjectUtil
											.objectToBigDecimal(objFiltroCola.getValorRangoDesde());
									BigDecimal rangoHasta = ObjectUtil
											.objectToBigDecimal(objFiltroCola.getValorRangoHasta());
									if (NumerosUtil.estaDentroRango(valorFiltroDiferencia, rangoDesde.intValue(),
											rangoHasta.intValue())) {
										cantidadFiltroEncontro++;
									}
								} else {
									if (isComodin) {
										cantidadFiltroEncontro++;
									}
								}
								break;

							default:
								break;
						}

					}
				}
				// Fin lista Grupo o Juego
				encontroConfiguracionJuego = cantidadFiltroEncontro == cantidadFiltro;
				if (encontroConfiguracionJuego && cantidadFiltro > 0) {
					Cola cola = dataColaConfiguracion.getValue().get(0).getCola();
					nivelActual = cola.getNivelCola();
					resultado.setColaDisponibleType(ColaDisponibleType.get(cola.getIdCola()));
					resultado.setCodigoJuego(dataColaConfiguracion.getKey());
					break;
				}
			}

		}
		if (isOcupadoCola(resultado.getColaDisponibleType())) {
			// buscaamos el proximo mayor
			String vNivelMa = confCache.getNivelMaximo() + "";
			Long nivelMaixmo = ObjectUtil.objectToLong(vNivelMa);
			boolean isOcupado = true;
			while (nivelMaixmo > nivelActual && nivelMaixmo > 0 && isOcupado) {
				Cola colaNivel = confCache.getColaNivelMap().get(nivelMaixmo + "");
				nivelMaixmo--;
				ColaDisponibleType resultadoTemp = ColaDisponibleType.get(colaNivel.getIdCola());
				isOcupado = isOcupadoCola(resultadoTemp);
				if (!isOcupado) {
					resultado.setColaDisponibleType(resultadoTemp);
					break;
				}
			}
		}
		return resultado;
	}

	private boolean isOcupadoCola(ColaDisponibleType colaDisponible) {
		boolean resultado = false;
		Integer maximaInstanciaCola = Integer.parseInt(ConfiguracionJMSUtil.MAXIMA_INSTANCIA_COLA);
		switch (colaDisponible) {
			case PREFERENCIAL:
				resultado = jMSSender.cantidadMessage(ConfiguracionJMSUtil.QCF_REPORTE_PREFERENCIAL_NAME,
						ConfiguracionJMSUtil.QUEUE_REPORTE_PREFERENCIAL_NAME) >= maximaInstanciaCola;
				break;
			case REGULAR:
				resultado = jMSSender.cantidadMessage(ConfiguracionJMSUtil.QCF_REPORTE_REGULAR_NAME,
						ConfiguracionJMSUtil.QUEUE_REPORTE_REGULAR_NAME) >= maximaInstanciaCola;
				break;
			case PESADO:
				resultado = jMSSender.cantidadMessage(ConfiguracionJMSUtil.QCF_REPORTE_PESADO_NAME,
						ConfiguracionJMSUtil.QUEUE_REPORTE_PESADO_NAME) >= maximaInstanciaCola;
				break;
			case NOCTURNO:
				resultado = jMSSender.cantidadMessage(ConfiguracionJMSUtil.QCF_REPORTE_NOCTURNO_NAME,
						ConfiguracionJMSUtil.QUEUE_REPORTE_NOCTURNO_NAME) >= maximaInstanciaCola;
				break;
			default:
				resultado = jMSSender.cantidadMessage(ConfiguracionJMSUtil.QCF_NAME,
						ConfiguracionJMSUtil.QUEUE_NAME) >= maximaInstanciaCola;
				break;
		}
		return resultado;
	}

	private List<ValorConfiguracionFiltroReporte> registrarCriterio(SolicitudReporteDTO objSolicitud,
			List<ConfiguracionFiltroReporte> configuracionFiltroReporteList) {
		log.info(objSolicitud.getCodigoSolicitud() + "." + "registrarCriterio");
		// eliminar filtros de busqueda si es reintento INCIDENCIA 07/06/2018
		eliminarByIdSolicitudReporte(objSolicitud.getIdSolicitudReporte());
		List<ValorConfiguracionFiltroReporte> resultado = new ArrayList<>();
		Map<String, ConfiguracionFiltroReporte> configuracionFiltroReporteMap = new HashMap<>();
		Map<String, String> fechaFormatopMap = new HashMap<>();
		Map<String, String> excluirNoVisiblesMap = objSolicitud.getIdComponentMap();
		if (!CollectionUtil.isEmpty(configuracionFiltroReporteList)) {
			Map<String, List<String>> listaObjetoAtributo = new HashMap<>();
			log.info(objSolicitud.getCodigoSolicitud() + "."
					+ "obtiene las clases internas y sus atributos + los atributos de descripcion");
			for (var configuracionFiltroReporte : configuracionFiltroReporteList) {
				String key = configuracionFiltroReporte.getObjectoAtributo();
				configuracionFiltroReporteMap.put(key, configuracionFiltroReporte);
				if (!StringUtil.isNullOrEmpty(configuracionFiltroReporte.getFormatoTipoDato())) {
					fechaFormatopMap.put(key, configuracionFiltroReporte.getFormatoTipoDato());
				}
				if (key.contains(".")) {
					boolean agregarCampoDescripcion = false;
					String[] arrayTemp = key.split("\\.");
					String campoDescripcion = "";
					if (!StringUtil.isNullOrEmpty(configuracionFiltroReporte.getObjectoAtributoDescripcion())) {
						agregarCampoDescripcion = true;
						if (configuracionFiltroReporte.getObjectoAtributoDescripcion().contains(".")) {
							campoDescripcion = configuracionFiltroReporte.getObjectoAtributoDescripcion()
									.split("\\.")[1];
						} else {
							campoDescripcion = configuracionFiltroReporte.getObjectoAtributoDescripcion();
						}
					}
					if (!listaObjetoAtributo.containsKey(arrayTemp[0])) {
						List<String> listaAtributoTemp = new ArrayList<>();
						listaAtributoTemp.add(arrayTemp[1]);
						if (agregarCampoDescripcion) {
							listaAtributoTemp.add(campoDescripcion);
						}
						listaObjetoAtributo.put(arrayTemp[0], listaAtributoTemp);
					} else {
						List<String> listaAtributoTemp = listaObjetoAtributo.get(arrayTemp[0]);
						if (agregarCampoDescripcion) {
							listaAtributoTemp.add(campoDescripcion);
						}
						listaAtributoTemp.add(arrayTemp[1]);
					}
				}
			}
			log.info(objSolicitud.getCodigoSolicitud() + "."
					+ "obtiene una estructura map con los atributos obtenidos del objecto filtro");
			Map<String, Map<String, Object>> listaCriterioFiltroDetalleMap = toFiltroMap(
					objSolicitud.getCriterioFiltro(), listaObjetoAtributo, null, true, null, fechaFormatopMap);
			log.info(objSolicitud.getCodigoSolicitud() + "." + "obtiene los valores para el grabado del filtro");
			for (var criterioFiltroDetalleMap : listaCriterioFiltroDetalleMap.entrySet()) {
				for (var criterioFiltroDetalleValueMap : criterioFiltroDetalleMap.getValue().entrySet()) {
					if (configuracionFiltroReporteMap.containsKey(criterioFiltroDetalleValueMap.getKey())
							&& !excluirNoVisiblesMap.containsKey(criterioFiltroDetalleValueMap.getKey())) {
						ValorConfiguracionFiltroReporte objValorFiltro = new ValorConfiguracionFiltroReporte();
						ConfiguracionFiltroReporte configuracionFiltro = configuracionFiltroReporteMap
								.get(criterioFiltroDetalleValueMap.getKey());
						objValorFiltro.setIdValorFiltro(UUIDUtil.generarElementUUID());
						objValorFiltro.setConfiguracionFiltroReporte(configuracionFiltro); // id del reporte filtro
						objValorFiltro.setIdSolicitudReporte(objSolicitud.getIdSolicitudReporte());
						if (!StringUtil.isNullOrEmpty(criterioFiltroDetalleValueMap.getValue())) {
							objValorFiltro.setValorFiltro(criterioFiltroDetalleValueMap.getValue() + "");
						} else {
							objValorFiltro.setValorFiltro(null);
						}
						if (!StringUtil.isNullOrEmpty(configuracionFiltro.getObjectoAtributoDescripcion())) {
							if (criterioFiltroDetalleMap.getValue()
									.containsKey(configuracionFiltro.getObjectoAtributoDescripcion())) {
								Object object = criterioFiltroDetalleMap.getValue()
										.get(configuracionFiltro.getObjectoAtributoDescripcion());
								if (!StringUtil.isNullOrEmpty(object)) {
									objValorFiltro.setValorFiltroText(object + "");
								} else {
									objValorFiltro.setValorFiltroText(null);
								}
							} else {
								objValorFiltro.setValorFiltroText(null);
							}
						} else {
							if (!StringUtil.isNullOrEmpty(criterioFiltroDetalleValueMap.getValue())) {
								objValorFiltro.setValorFiltroText(criterioFiltroDetalleValueMap.getValue() + "");
							} else {
								objValorFiltro.setValorFiltroText(null);
							}
						}
						objValorFiltro.setEstado(EstadoGeneralState.ACTIVO.getKey());
						resultado.add(objValorFiltro);
					}
				}
			}
		}
		// graba los valores en la tabla GCM_VALOR_FILTRO
		List<ValorConfiguracionFiltroReporte> listaValorConfiguracionFiltroReporte = new ArrayList<>();
		for (var valorConfiguracionFiltroReporte : resultado) {
			ValorConfiguracionFiltroReporte valorConfiguracionFiltroReporteEntity = to(valorConfiguracionFiltroReporte,
					ValorConfiguracionFiltroReporte.class);
			ConfiguracionFiltroReporte configuracionFiltroReporteEntity = to(
					valorConfiguracionFiltroReporte.getConfiguracionFiltroReporte(), ConfiguracionFiltroReporte.class);
			valorConfiguracionFiltroReporteEntity.setConfiguracionFiltroReporte(configuracionFiltroReporteEntity);
			listaValorConfiguracionFiltroReporte.add(valorConfiguracionFiltroReporteEntity);
		}
		registrarMasivo(listaValorConfiguracionFiltroReporte);
		return resultado;
	}

	@Override
	public void guardarIntentosGenerarReporteFallidos(SolicitudReporteDTO obj, String descripcionError,
			boolean isReintentar) {
		if (obj.getIdSolicitudReporte() != null) {
			IntentoGenerarReporte intentoGenerarReporte = new IntentoGenerarReporte();
			intentoGenerarReporte.setDescripcion(descripcionError);
			intentoGenerarReporte.setEstado(EstadoGeneralState.ACTIVO.getKey());
			intentoGenerarReporte.setFechaEnvio(FechaUtil.obtenerFechaActual());
			intentoGenerarReporte.setSolicitudReporte(new SolicitudReporte());
			intentoGenerarReporte.getSolicitudReporte().setIdSolicitudReporte(obj.getIdSolicitudReporte());
			try {
				obj.setIntentos(obj.getIntentos() + 1);
				intentoGenerarReporte.setNumeroIntento(obj.getIntentos());
				intentoGenerarReporte
						.setIdIntento(generarIdIntento(obj.getCodigoSolicitud(), obj.getIntentos().intValue()));
				registrarIntento(intentoGenerarReporte);
				obj.setEstado(EstadoSolicitudEjecucionEstate.FALLO_EJECUCION.getKey());
				SolicitudReporte solicitud = to(obj, SolicitudReporte.class);
				solicitud = reporteServiceImpl.controladorAccionSolicitudReporte(solicitud, AccionType.MODIFICAR);
				if (!"ARJUNA".equalsIgnoreCase(descripcionError) && isReintentar && obj.getIntentos()
						.intValue() < ConfiguracionUtil.getPWRConfUtilInt(ConfiguracionUtil.CANTIDAD_INTENTOS_COLA)) {
					enviarColaGenerarSolicitudReporte(obj);
				}
			} catch (Exception e1) {
				log.error("guardarIntentosGenerarReporteFallidos", e1);
			}
		}
	}

	@Override
	public boolean registrarMasivo(List<ValorConfiguracionFiltroReporte> listaValorConfiguracionFiltroReporte) {
		return valorConfiguracionFiltroReporteDaoImpl.registrarMasivo(listaValorConfiguracionFiltroReporte);
	}

	@Override
	public boolean eliminarByIdSolicitudReporte(String idSolicitudReporte) {
		return valorConfiguracionFiltroReporteDaoImpl.eliminarByIdSolicitudReporte(idSolicitudReporte);
	}

	@Override
	public List<ValorConfiguracionFiltroReporte> listarDatosFiltro(List<Long> keyList, List<String> listaLugarAparece) {
		return valorConfiguracionFiltroReporteDaoImpl.listarDatosFiltro(keyList, listaLugarAparece);
	}

	@Override
	public String generarIdIntento(String codigoUUID, int incremento) {
		return intentoGenerarReporteDaoImpl.generarId(codigoUUID, incremento);
	}

	@Override
	public IntentoGenerarReporte registrarIntento(IntentoGenerarReporte intentoGenerarReporte) {
		return intentoGenerarReporteDaoImpl.save(intentoGenerarReporte);
	}

	@Override
	public Map<String, List<String>> listarIdComponentes(Long idOpcion) {
		return configuracionFiltroReporteDaoImpl.listarIdComponentes(idOpcion);
	}
}