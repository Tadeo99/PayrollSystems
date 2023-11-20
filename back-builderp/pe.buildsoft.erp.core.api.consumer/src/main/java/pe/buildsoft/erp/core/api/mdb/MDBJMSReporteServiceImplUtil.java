package pe.buildsoft.erp.core.api.mdb;

import java.io.Serializable;

import javax.naming.NamingException;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.ObjectMessage;
import pe.buildsoft.erp.core.application.interfaces.IServiceApp;
import pe.buildsoft.erp.core.application.interfaces.MensajeriaAppServiceLocal;
import pe.buildsoft.erp.core.application.interfaces.cola.ConfiguracionColaAppServiceLocal;
import pe.buildsoft.erp.core.application.interfaces.cola.ProcesarColaAppServiceLocal;
import pe.buildsoft.erp.core.application.interfaces.cola.ReporteAppServiceLocal;
import pe.buildsoft.erp.core.domain.cache.ColaInstanceCacheUtil;
import pe.buildsoft.erp.core.domain.util.ParametroUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.SolicitudReporteDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.CorreoVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.FileVO;
import pe.buildsoft.erp.core.infra.transversal.remote.Referencia;
import pe.buildsoft.erp.core.infra.transversal.state.EstadoSolicitudEjecucionEstate;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.type.AsuntoDetalleCorreoType;
import pe.buildsoft.erp.core.infra.transversal.type.TipoOpcionType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ConfiguracionUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.FechaUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ThreadColaUtil;

/**
 * La Class MDBJMSReporteServiceImplUtil.
 * <ul>
 * <li>Copyright 2014 MAPFRE - mapfre. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, 06/01/2017
 * @since PWR v1.0
 */
public class MDBJMSReporteServiceImplUtil {

	public static final String CHANNEL = "/notifySolicitudReporte";

	/** La log. */
	private static Logger log = LoggerFactory.getLogger(MDBJMSReporteServiceImplUtil.class);

	@Inject
	protected MensajeriaAppServiceLocal mensajeriaApp;

	@Inject
	protected ReporteAppServiceLocal reporteApp;

	@Inject
	protected ProcesarColaAppServiceLocal procesarColaApp;

	@Inject
	protected ConfiguracionColaAppServiceLocal confColaApp;

	// @Inject
	protected WebsocketClientEndpoint webSocketEndpoint;

	private String nombreModulo;

	public void onMessage(Message aMessage, String queueName) {
		String idMensaje = obtenerIdMensaje(aMessage);
		incremento(queueName, idMensaje);
		log.info("Inicio.MDBJMSReporteServiceImpl.onMessage--> " + FechaUtil.obtenerFechaActual());
		try {

			if (aMessage instanceof ObjectMessage objMessage) {
				Serializable serialObj = objMessage.getObject();
				// objetos de la cola
				if (serialObj instanceof CorreoVO correoVO) {
					ThreadColaUtil.getInstance().put("BuildErp-correo", correoVO.getUUID(), Thread.currentThread());
					mensajeriaApp.enviarCorreoElectronico(correoVO);
					ThreadColaUtil.getInstance().remove("BuildErp-correo" + correoVO.getUUID());
				}
			}
			if (aMessage.getJMSRedelivered()) {
				log.info("mensaje enviado otra vez");
				if (aMessage instanceof ObjectMessage objMessage) {
					Serializable serialObj = objMessage.getObject();
					actualizarSolicitud(serialObj);
				}
				disminuir(queueName, idMensaje);
				return;
			}

			if (aMessage instanceof ObjectMessage objMessage) {
				Serializable serialObj = objMessage.getObject();
				if (serialObj instanceof SolicitudReporteDTO obj) {
					String data = "ID=>" + obj.getIdSolicitudReporte() + " USER=> " + obj.getUserName() + " SOLI=> "
							+ obj.getCodigoSolicitud();
					updateKeyMensaje(queueName, idMensaje, data);
				}
				String data = ejecutarProceso(serialObj);
				updateKeyMensaje(queueName, idMensaje, data);
			}

		} catch (JMSException e) {
			log.error("onMessage", e);
			log.error(
					"Error MDBJMSReporteServiceImplUtil.onMessage error al consumir el mensaje a cola JMSException e : "
							+ e.getMessage());
		} catch (Exception e) {
			log.error("onMessage", e);
			log.error("Error MDBJMSReporteServiceImplUtil.onMessage error al consumir el mensaje a cola Exception e : "
					+ e.getMessage());
		}
		log.info("Fin.MDBJMSReporteServiceImplUtil.onMessage--> " + FechaUtil.obtenerFechaActual());
		disminuir(queueName, idMensaje);
	}

	protected IServiceApp obtenerRemoto(String jndi) throws NamingException {
		return (IServiceApp) Referencia.getReference(jndi);
	}

	public String obtenerIdMensaje(Message aMessage) {
		String idMensaje = "";
		try {
			idMensaje = aMessage.getJMSMessageID();
		} catch (JMSException e) {
			log.error("obtenerIdMensaje", e);
		}
		return idMensaje;
	}

	public String ejecutarProceso(Serializable serialObj) throws Exception {
		try {
			if (serialObj instanceof SolicitudReporteDTO objSoli) {
				webSocketEndpoint = new WebsocketClientEndpoint(objSoli.getKeyWebSockect());
			}
		} catch (Exception e) {
			log.error("webSocketEndpoint.client.error", e);
		}
		String resultado = "";
		if (serialObj instanceof SolicitudReporteDTO objSoli) {
			if (nombreModulo == null) {
				nombreModulo = this.getClass().getModule().getClassLoader().getName().replace("deployment.", "")
						.replace(".war", "");
			}
			IServiceApp servicioApp = obtenerRemoto(nombreModulo + "/" + objSoli.getClassDinamic());
			ThreadColaUtil.getInstance().put(objSoli.getUserName(), objSoli.getCodigoSolicitud(),
					Thread.currentThread());
			if (objSoli.getIdOpcion() == null) {
				log.error("La solicitud " + objSoli.getCodigoSolicitud() + " no tiene asociado una opción");
				// return solicitudReporteDTO.getIdSolicitudReporte();
			}
			if (objSoli.getIdUsuario() == null) {
				log.error("La solicitud " + objSoli.getCodigoSolicitud() + " no tiene asociado un usuario");
				// return solicitudReporteDTO.getIdSolicitudReporte();
			}
			if (EstadoSolicitudEjecucionEstate.PENDIENTE.getKey().equals(objSoli.getEstado())
					|| EstadoSolicitudEjecucionEstate.EN_COLA.getKey().equals(objSoli.getEstado())) {
				objSoli.setEstado(EstadoSolicitudEjecucionEstate.EN_PROCESO.getKey());
				if (objSoli.getFechaGeneracion() == null) {
					objSoli.setFechaGeneracion(FechaUtil.obtenerFechaActual());
				}
				if (objSoli.getIdSolicitudReporte() == null) {
					reporteApp.controladorAccionSolicitudReporte(objSoli, AccionType.CREAR);
				} else {
					reporteApp.controladorAccionSolicitudReporte(objSoli, AccionType.MODIFICAR);
				}
				actualizarEstadoColaNocturna(objSoli);
			}
			resultado = objSoli.getIdSolicitudReporte();
			if (objSoli.getCriterioFiltro() != null) {
				resultado = objSoli.getIdSolicitudReporte();
				if (objSoli.getCriterioFiltro() != null) {
					String descripcionError = "";
					FileVO fileVO = new FileVO();
					try {
						String resul = servicioApp.procesar(objSoli.getCriterioFiltro());
						if (resul != null && resul.contains("${ERROR}")) {
							descripcionError = resul.replace("${ERROR}", "");
							objSoli.setEstado(EstadoSolicitudEjecucionEstate.FALLO_EJECUCION.getKey());
						} else {
							objSoli.setEstado(EstadoSolicitudEjecucionEstate.TERMINADO.getKey());
							objSoli.setFechaTermino(FechaUtil.obtenerFechaActual());
							resultado = objSoli.getIdSolicitudReporte();
						}

					} catch (Exception e) {
						log.error("ejecutarProceso", e);
						// solicitudReporteDTO.setFechaTermino(null);
						descripcionError = e.getMessage();
						objSoli.setEstado(EstadoSolicitudEjecucionEstate.FALLO_EJECUCION.getKey());
					}
					resultado = objSoli.getIdSolicitudReporte();
					enviarCorreo(objSoli, descripcionError, fileVO, true);
					resultado = objSoli.getIdSolicitudReporte();
				}
			}
		}
		return resultado;
	}

	public void actualizarSolicitud(Serializable serialObj) {
		try {
			if (serialObj instanceof SolicitudReporteDTO solicitudReporteDTO) {
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
			log.error("actualizarSolicitud", e);
		}
	}

	private void actualizarEstadoColaNocturna(SolicitudReporteDTO solicitudReporteDTO) {
		try {
			boolean isTieneColaNocturna = solicitudReporteDTO.getPametrosMap()
					.containsKey(ParametroUtil.ES_COLA_NOCTURNA);
			if (isTieneColaNocturna) {
				String idColaNocturna = solicitudReporteDTO.getPametrosMap().get(ParametroUtil.COLA_NOCTURNA_ID) + "";
				if (!StringUtil.isNullOrEmpty(idColaNocturna)) {
					EstadoSolicitudEjecucionEstate EstadoGeneralState = EstadoSolicitudEjecucionEstate
							.get(solicitudReporteDTO.getEstado());
					confColaApp.actualizarEstadoColaNoctura(idColaNocturna, EstadoGeneralState);
				}
			}

		} catch (Exception e) {
			log.error("actualizarEstadoColaNocturna", e);
		}

	}

	private void enviarCorreo(SolicitudReporteDTO obj, String descripcionError, FileVO fileVO, boolean isReintentar) {
		try {
			int intentos = obj.getIntentos().intValue();
			boolean actualizoSolicitud = false;
			if (EstadoSolicitudEjecucionEstate.FALLO_EJECUCION.getKey().equals(obj.getEstado())) {
				actualizoSolicitud = true;
				procesarColaApp.guardarIntentosGenerarReporteFallidos(obj, descripcionError, isReintentar);
			}
			if (EstadoSolicitudEjecucionEstate.TERMINADO.getKey().equals(obj.getEstado())
					|| (intentos + 1) == ConfiguracionUtil
							.getPWRConfUtilInt(ConfiguracionUtil.CANTIDAD_INTENTOS_COLA)) {
				if (EstadoSolicitudEjecucionEstate.TERMINADO.getKey().equals(obj.getEstado())) {
					if (!actualizoSolicitud) {
						reporteApp.controladorAccionSolicitudReporte(obj, AccionType.MODIFICAR);
						actualizarEstadoColaNocturna(obj);
					}
					if (TipoOpcionType.REPORTE.getKey().equals(obj.getTipoOpcion())) {
						mensajeriaApp.envioCorreoByTipo(obj.getEmail(), obj.getCodigoSolicitud(),
								AsuntoDetalleCorreoType.ASUNTO_REPORTE, null);
					} else if (TipoOpcionType.PROCESO.getKey().equals(obj.getTipoOpcion())) {
						mensajeriaApp.envioCorreoByTipo(obj.getEmail(), obj.getCodigoSolicitud(),
								AsuntoDetalleCorreoType.ASUNTO_PROCESO, null);
					}

				}
				if (EstadoSolicitudEjecucionEstate.TERMINADO.getKey().equals(obj.getEstado())
						|| (intentos + 1) == ConfiguracionUtil
								.getPWRConfUtilInt(ConfiguracionUtil.CANTIDAD_INTENTOS_COLA)) {
					send(obj.getTipoOpcion(), obj.getCodigoSolicitud(), obj.getUserName(), descripcionError,
							obj.getKeyWebSockect());
					ThreadColaUtil.getInstance().remove(obj.getUserName() + obj.getCodigoSolicitud());
				}
			}
		} catch (Exception e) {
			log.error(
					"Error MDBJMSMensajeriaServiceImpl.onMessage(Message aMessage).enviarCorreo error al consumir el mensaje a cola Exception e : "
							+ e.getMessage());
			if ((obj.getIntentos().intValue()) == ConfiguracionUtil
					.getPWRConfUtilInt(ConfiguracionUtil.CANTIDAD_INTENTOS_COLA)) {
				send(obj.getTipoOpcion(), obj.getCodigoSolicitud(), obj.getUserName(), descripcionError,
						obj.getKeyWebSockect());
			}
		}

	}

	public synchronized void incremento(String key, String idMensaje) {
		ColaInstanceCacheUtil.getInstance().incremento(key, idMensaje);
	}

	public static synchronized void disminuir(String key, String idMensaje) {
		ColaInstanceCacheUtil.getInstance().disminuir(key, idMensaje);
	}

	public static synchronized void updateKeyMensaje(String key, String idMensaje, String data) {
		ColaInstanceCacheUtil.getInstance().updateKeyMensaje(key, idMensaje, data);
	}

	/**
	 * Send.
	 *
	 * @param codigoSolicitud el codigo solicitud
	 * @param userName        el user name
	 */
	public void send(String tipo, String codigoSolicitud, String userName, String descripcionError,
			String keyWebSockect) {
		try {
			// add listener
			webSocketEndpoint.addMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
				public void handleMessage(String message) {
					System.out.println(message);
				}
			});

			webSocketEndpoint.sendMessage(getMensaje(tipo, codigoSolicitud, userName, descripcionError, keyWebSockect));

			/*
			 * EventBus eventBus = EventBusFactory.getDefault().eventBus();
			 * eventBus.publish(CHANNEL, new
			 * FacesMessage(StringEscapeUtils.escapeHtml(userName), StringEscapeUtils
			 * .escapeHtml("Se genere correctamente el reporte con codigo de solicitud " +
			 * codigoSolicitud)));
			 */} catch (Exception e) {
			log.error("send" + e.getMessage());
		}

	}

	private String getMensaje(String tipo, String codigoSolicitud, String userName, String descripcionError,
			String keyWebSockect) {
		if (descripcionError != null) {
			descripcionError = descripcionError.replace("\"", "'");
		}
		return "{\"tipo\":\"" + tipo + "\",\"codigoSolicitud\":\"" + codigoSolicitud
				+ "\",\"source\":\"servidor\",\"content\":\"" + StringEscapeUtils.escapeJson(descripcionError) + "\"}";
	}

}