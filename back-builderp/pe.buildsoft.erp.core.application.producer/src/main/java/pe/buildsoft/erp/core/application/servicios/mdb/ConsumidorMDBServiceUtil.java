package pe.buildsoft.erp.core.application.servicios.mdb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import pe.buildsoft.erp.core.application.interfaces.cola.ConfiguracionColaAppServiceLocal;
import pe.buildsoft.erp.core.application.interfaces.cola.ProcesarColaAppServiceLocal;
import pe.buildsoft.erp.core.application.interfaces.cola.ReporteAppServiceLocal;
import pe.buildsoft.erp.core.domain.cache.ColaInstanceCacheUtil;
import pe.buildsoft.erp.core.domain.util.ParametroUtil;
import pe.buildsoft.erp.core.infra.transversal.cache.ICache;
import pe.buildsoft.erp.core.infra.transversal.entidades.SolicitudReporteDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.CabeceraReporteVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RegistroMensajeriaOutputVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.state.EstadoSolicitudEjecucionEstate;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;
import pe.buildsoft.erp.core.infra.transversal.utilitario.AppHttpHeaderNames;
import pe.buildsoft.erp.core.infra.transversal.utilitario.FechaUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

public class ConsumidorMDBServiceUtil extends GenericServiceRestImpl {

	protected static final String ERROR = "${ERROR}";
	protected static final String TODOS = "Todos";
	protected static final String ERROR_DESCARGAR = "${DESCARGAR}";

	private Logger log = LoggerFactory.getLogger(ConsumidorMDBServiceUtil.class);

	@Inject
	protected ReporteAppServiceLocal reporteApp;

	@Inject
	protected ProcesarColaAppServiceLocal procesarColaApp;

	@Inject
	protected ConfiguracionColaAppServiceLocal configuracionColaApp;
	
	@Inject
	private ICache cache;


	protected Response ejecutar(@Context HttpHeaders headers,CabeceraReporteVO objFiltro, String formato) {
		objFiltro.setAuthToken(headers.getRequestHeaders().getFirst(AppHttpHeaderNames.AUTH_TOKEN));
		objFiltro.setKeyWebSockect(headers.getRequestHeaders().getFirst(AppHttpHeaderNames.KEY_WEBSOCKECT));
		String userName = cache.getUserName(objFiltro.getAuthToken());
		objFiltro.setUsuario(userName);
		objFiltro.setArchivoName(UUIDUtil.generarElementUUID());
		return generarReporteCola(objFiltro, formato);
	}


	@SuppressWarnings("static-access")
	protected void updateKeyMensaje(String key, String idMensaje, String data) {
		ColaInstanceCacheUtil.getInstance().updateKeyMensaje(key, idMensaje, data);
	}

	@SuppressWarnings("static-access")
	protected void disminuir(String key, String idMensaje) {
		ColaInstanceCacheUtil.getInstance().disminuir(key, idMensaje);
	}

	protected SolicitudReporteDTO procesarSolicitud(SolicitudReporteDTO objSolicitud, boolean isFallo) {
		if (isFallo) {
			objSolicitud.setEstado(EstadoSolicitudEjecucionEstate.FALLO_EJECUCION.getKey());
		} else {
			objSolicitud.setEstado(EstadoSolicitudEjecucionEstate.TERMINADO.getKey());
			objSolicitud.setFechaTermino(FechaUtil.obtenerFechaActual());
		}
		return objSolicitud;
	}

	protected Response generarReporteCola(CabeceraReporteVO objData, String formato) {
		RespuestaWSVO<String> resultado = inicializar(new RespuestaWSVO<String>());
		try {
			resultado.setObjetoResultado(generarReporteColaPer(objData, formato));
		} catch (Exception e) {
			log.error("generarReporteCola",e);
			parsearResultadoError(e, resultado);
		}
		return respuestaCola(resultado);
	}

	protected String obtenerIdMensaje(HttpHeaders headers) {
		String idMensaje = "";
		try {
			idMensaje = headers.getRequestHeaders().getFirst("JMSMessageID");
		} catch (Exception e) {
			log.error("obtenerIdMensaje",e);
		}
		return idMensaje;
	}

	protected boolean getJMSRedelivered(HttpHeaders headers) {
		return "true".equalsIgnoreCase(headers.getRequestHeaders().getFirst("JMSRedelivered"));
	}

	protected String getQueName(HttpHeaders headers) {
		return headers.getRequestHeaders().getFirst("QUEUE_NAME");
	}

	protected String getArchivoName(String resultado) {
		if (StringUtil.isNullOrEmpty(resultado)) {
			resultado = UUIDUtil.generarElementUUID();
		}
		return resultado;
	}

	protected void actualizarSolicitud(Object serialObj) {
		try {
			if (serialObj instanceof SolicitudReporteDTO) {
				SolicitudReporteDTO solicitudReporteDTO = (SolicitudReporteDTO) serialObj;
				solicitudReporteDTO.setEstado(EstadoSolicitudEjecucionEstate.FALLO_EJECUCION.getKey());
				solicitudReporteDTO.setFechaTermino(FechaUtil.obtenerFechaActual());
				if (solicitudReporteDTO.getIdSolicitudReporte() == null) {
					reporteApp.controladorAccionSolicitudReporte(solicitudReporteDTO, AccionType.CREAR);
				} else {
					reporteApp.controladorAccionSolicitudReporte(solicitudReporteDTO, AccionType.MODIFICAR);
				}
				procesarColaApp.guardarIntentosGenerarReporteFallidos(solicitudReporteDTO, "ARJUNA", true);
				actualizarEstadoColaNocturna(solicitudReporteDTO);
			}
		} catch (Exception e) {
			log.error("actualizarSolicitud",e);
		}

	}

	protected void actualizarEstadoColaNocturna(SolicitudReporteDTO solicitudReporteDTO) {
		try {
			boolean isTieneColaNocturna = solicitudReporteDTO.getPametrosMap()
					.containsKey(ParametroUtil.ES_COLA_NOCTURNA);
			if (isTieneColaNocturna) {
				String idColaNocturna = solicitudReporteDTO.getPametrosMap().get(ParametroUtil.COLA_NOCTURNA_ID) + "";
				if (!StringUtil.isNullOrEmpty(idColaNocturna)) {
					EstadoSolicitudEjecucionEstate estadoSolicitudEjecucionEstate = EstadoSolicitudEjecucionEstate
							.get(solicitudReporteDTO.getEstado());
					configuracionColaApp.actualizarEstadoColaNoctura(idColaNocturna, estadoSolicitudEjecucionEstate);
				}
			}

		} catch (Exception e) {
			log.error("actualizarEstadoColaNocturna",e);
		}

	}

	protected boolean isError(String resultadoTemp) {
		return resultadoTemp != null && (resultadoTemp.contains(TODOS) || resultadoTemp.contains(ERROR));
	}

	protected boolean isSolicituRestValida(RespuestaWSVO<SolicitudReporteDTO> dataRes) {
		return !dataRes.isError() && !"-1".equalsIgnoreCase(dataRes.getCodigoError())
				&& isSolicitudValida(dataRes.getObjetoResultado());
	}

	private boolean isSolicitudValida(SolicitudReporteDTO objSolicitud) {
		return objSolicitud != null && objSolicitud.getCriterioFiltro() != null;
	}

	protected String isErrorFormat(String resultadoTemp) {
		if (resultadoTemp != null) {
			return resultadoTemp.replace(ERROR, "").replace(ERROR_DESCARGAR, "");
		}
		return resultadoTemp;
	}

	/**
	 * Generar reporte cola.
	 *
	 * @param objData el parametro reporte vo
	 */
	public String generarReporteColaPer(CabeceraReporteVO objData, String formato) {
		SolicitudReporteDTO solicitudReporteDTO = new SolicitudReporteDTO();
		solicitudReporteDTO.setCodigoSolicitud(objData.getArchivoName());
		solicitudReporteDTO.setUserName(objData.getUsuario());
		solicitudReporteDTO.setKeyWebSockect(objData.getKeyWebSockect());
		solicitudReporteDTO.setTipoOpcion(objData.getTipoOpcion());
		if (!StringUtil.isNotNullOrBlank(objData.getArchivoName())) {
			solicitudReporteDTO.setCodigoSolicitud(reporteApp.obtenerUltimoCodigoSolicitudByUsuario(0L));
		}
		
		if (formato != null) {
			solicitudReporteDTO.setFormatoReporte(formato);
		}
		objData.setArchivoName(solicitudReporteDTO.getCodigoSolicitud());
		objData.setFechaGeneracion(FechaUtil.obtenerFechaActual());
		solicitudReporteDTO.setCriterioFiltro(objData);
		solicitudReporteDTO.setClassDinamic(objData.getClassDinamic());
		/*
		 * UsuarioDTO usuario = new UsuarioDTO();
		 * usuario.setIdUsuario(parametroReporteVO.getIdUsuario());
		 * usuario.setCorreoCorporativo(parametroReporteVO.getCorreoCorporativo());
		 * usuario.setNombreCompleto(parametroReporteVO.getNombreCompleto());
		 * usuario.setUserName(parametroReporteVO.getUserName());
		 */
		solicitudReporteDTO.setIdUsuario(objData.getIdUsuario());
		/*
		 * OpcionDTO menu = new OpcionDTO();
		 * menu.setIdOpcion(parametroReporteVO.getMenu());
		 * menu.setCodigoCola(parametroReporteVO.getCodigoCola());
		 */
		solicitudReporteDTO.setIdOpcion(objData.getMenu());
		solicitudReporteDTO.setCodigoCola(objData.getCodigoCola());
		// getCriterioEnvioCorreoVO().setCorreoCorporativo(objData.getCorreoCorporativo());
		// getCriterioEnvioCorreoVO().setNombreCompleto(objData.getNombreCompleto());
		// excluirComponentesNoVisibles(parametroReporteVO);
		RegistroMensajeriaOutputVO resul = procesarColaApp.enviarColaGenerarSolicitudReporte(solicitudReporteDTO);
		if (resul != null && (resul.getError() == null || resul.getError().equals(""))) {
			return solicitudReporteDTO.getCodigoSolicitud();
		} else {
			if (resul != null && (resul.getError() != null && !resul.getError().equals(""))) {
				return ERROR + resul.getError();
			}
		}
		return null;
	}

}