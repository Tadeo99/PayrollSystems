package pe.buildsoft.erp.core.domain.servicios.cola;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.Queue;
import jakarta.jms.Session;
import pe.buildsoft.erp.core.domain.cache.ColaInstanceCacheUtil;
import pe.buildsoft.erp.core.domain.entidades.cola.SolicitudReporte;
import pe.buildsoft.erp.core.domain.interfaces.servicios.cola.ConfiguracionColaServiceLocal;
import pe.buildsoft.erp.core.domain.interfaces.servicios.cola.IJMSSender;
import pe.buildsoft.erp.core.domain.interfaces.servicios.cola.ProcesarColaServiceLocal;
import pe.buildsoft.erp.core.domain.interfaces.servicios.cola.ReporteServiceLocal;
import pe.buildsoft.erp.core.domain.util.ConfiguracionJMSUtil;
import pe.buildsoft.erp.core.domain.util.ParametroUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.MensajeDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.SolicitudReporteDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.state.EstadoSolicitudEjecucionEstate;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.BaseTransfer;
import pe.buildsoft.erp.core.infra.transversal.utilitario.FechaUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.SerializationUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class JMSSender.
 * <ul>
 * <li>Copyright 2014 MAPFRE- mapfre. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, 28/07/2021
 * @since Rep v1..0
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class JMSSender extends BaseTransfer implements IJMSSender {

	private static final String QUEUE_NAME = " , QUEUE_NAME : ";

	private static final String ERROR_EN_JMS_SENDER_SEND_MESSAGE_LISTA_OBJECT = "Error en JMSSender.sendMessage(listaObject) ";

	/** La log. */
	private Logger log = LoggerFactory.getLogger(JMSSender.class);

	@Inject
	private ReporteServiceLocal reporteServiceLocal;

	@Inject
	private ProcesarColaServiceLocal procesarColaServiceLocal;

	@Inject
	private ConfiguracionColaServiceLocal configuracionColaServiceLocal;

	@Override
	public void sendMessage(Object obj) {
		try {
			List<Object> listaObject = new ArrayList<>();
			listaObject.add(obj);
			sendMessageList(listaObject);
		} catch (Exception e) {
			log.error(ERROR_EN_JMS_SENDER_SEND_MESSAGE_LISTA_OBJECT + e.getMessage());
		}
	}

	public void sendMessageTrama(Object obj) {
		try {
			List<Object> listaObject = new ArrayList<>();
			listaObject.add(obj);
			sendMessageTramaList(listaObject);
		} catch (Exception e) {
			log.error("Error en JMSSender.sendMessageTrama(listaObject) " + e.getMessage());
		}
	}

	public void sendMessageTramaFTP(Object obj) {
		try {
			List<Object> listaObject = new ArrayList<>();
			listaObject.add(obj);
			sendMessageTramaFTPList(listaObject);
		} catch (Exception e) {
			log.error("Error en JMSSender.sendMessageTramaFTP(listaObject) " + e.getMessage());
		}
	}

	public void sendMessageCorreo(Object obj) {
		try {
			List<Object> listaObject = new ArrayList<>();
			listaObject.add(obj);
			sendRestMessageCorreoList(listaObject);
		} catch (Exception e) {
			log.error("Error en JMSSender.sendMessageCorreo(listaObject) " + e.getMessage());
		}
	}

	@Override
	public void sendMessagePreferencial(Object obj) {
		try {
			List<Object> listaObject = new ArrayList<>();
			listaObject.add(obj);
			sendMessagePreferencialList(listaObject);
		} catch (Exception e) {
			log.error("Error en JMSSender.sendMessagePreferencial(listaObject) " + e.getMessage());
		}
	}

	public void sendMessagePreferencial(Object obj, String url) {
		try {
			List<Object> listaObject = new ArrayList<>();
			listaObject.add(obj);
			sendMessagePreferencialList(listaObject, url);
		} catch (Exception e) {
			log.error("Error en JMSSender.sendMessagePreferencial(listaObject) " + e.getMessage());
		}
	}

	@Override
	public void sendMessageRegular(Object obj) {
		try {
			List<Object> listaObject = new ArrayList<>();
			listaObject.add(obj);
			sendMessageRegularList(listaObject);
		} catch (Exception e) {
			log.error("Error en JMSSender.sendMessageRegular(listaObject) " + e.getMessage());
		}
	}

	@Override
	public void sendMessagePesado(Object obj) {
		try {
			List<Object> listaObject = new ArrayList<>();
			listaObject.add(obj);
			sendMessagePesadoList(listaObject);
		} catch (Exception e) {
			log.error("Error en JMSSender.sendMessagePesado(listaObject) " + e.getMessage());
		}
	}

	public void sendMessageHeavy(Object obj) {
		try {
			List<Object> listaObject = new ArrayList<>();
			listaObject.add(obj);
			sendMessageHeavyList(listaObject);
		} catch (Exception e) {
			log.error("Error en JMSSender.sendMessageHeavy(listaObject) " + e.getMessage());
		}
	}

	public void sendMessage(Object obj, String qcfName, String queueName, String url, String operacion) {
		try {
			List<Object> listaObject = new ArrayList<>();
			listaObject.add(obj);
			sendRestMessageList(listaObject, qcfName, queueName, url, operacion);
		} catch (Exception e) {
			log.error(ERROR_EN_JMS_SENDER_SEND_MESSAGE_LISTA_OBJECT + e.getMessage());
		}
	}

	public void sendMessage(Object obj, String qcfName, String queueName) {
		try {
			List<Object> listaObject = new ArrayList<>();
			listaObject.add(obj);
			sendRestMessageList(listaObject, qcfName, queueName, null, null);
		} catch (Exception e) {
			log.error(ERROR_EN_JMS_SENDER_SEND_MESSAGE_LISTA_OBJECT + e.getMessage());
		}
	}

	@Override
	public String sendMessageNocturno(Object obj) {
		String resultado = "";
		try {
			List<Object> listaObject = new ArrayList<>();
			listaObject.add(obj);
			resultado = sendMessageNocturnoList(listaObject);
		} catch (Exception e) {
			resultado = e.getMessage();
			log.error("Error en JMSSender.sendMessageNocturno(listaObject) " + e.getMessage());
		}
		return resultado;
	}

	public String sendRestMessageList(List<Object> listaObject, String qcfName, String queueName) {
		return sendRestMessageList(listaObject, qcfName, queueName, null, null);
	}

	public String sendMessageList(List<Object> listaObject, String qcfName, String queueName) {
		String resultado = "";
		SolicitudReporteDTO solicitudReporteDTO = null;
		try {
			var ctx = new InitialContext();
			var connectionFactory = (ConnectionFactory) ctx.lookup(qcfName);
			var connection = connectionFactory.createConnection();
			var queue = (Queue) ctx.lookup(queueName);
			var session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			var messageProducer = session.createProducer(queue);
			for (var obj : listaObject) {
				var message = session.createObjectMessage();
				if (obj instanceof SolicitudReporteDTO objProcesar) {
					solicitudReporteDTO = objProcesar;
					solicitudReporteDTO = actualizarSolicitud(solicitudReporteDTO,
							EstadoSolicitudEjecucionEstate.EN_COLA, "");// para saber si esta encolado
					message.setObject(solicitudReporteDTO);
				} else {
					message.setObject((Serializable) obj);
				}

				messageProducer.send(message);
			}
			messageProducer.close();
			session.close();
			connection.close();
		} catch (Exception e) {
			resultado = e.getMessage();
			log.error("Error en JMSSender.sendMessage(listaObject,QCF_NAME : " + qcfName + QUEUE_NAME + queueName + ") "
					+ e.getMessage());
			if (solicitudReporteDTO != null) {
				actualizarSolicitud(solicitudReporteDTO, EstadoSolicitudEjecucionEstate.FALLO_EJECUCION,
						e.getMessage());// para saber si esta encolado
			}
		}
		return resultado;
	}

	public String sendRestMessageList(List<Object> listaObject, String qcfName, String queueName, String urlQueueName,
			String operacion) {
		String resultado = "";
		String uuid = UUIDUtil.generarElementUUID();
		SolicitudReporteDTO solicitudReporteDTO = null;
		if (StringUtil.isNullOrEmpty(operacion)) {
			operacion = "";
		}
		try {
			for (var obj : listaObject) {
				MensajeDTO mensaje = null;
				if (obj instanceof SolicitudReporteDTO) {
					solicitudReporteDTO = (SolicitudReporteDTO) obj;
					solicitudReporteDTO = actualizarSolicitud(solicitudReporteDTO,
							EstadoSolicitudEjecucionEstate.EN_COLA, "");// para saber si esta encolado
				}
				mensaje = generarMensaje(obj, urlQueueName, operacion, qcfName, queueName, uuid);
				/*RespuestaWSVO<String> res = null;// ColaProductorRestClienteUtil.jmsSender(mensaje);
				if (res.isError()) {
					log.error("Error en JMSSender.sendRestMessageList." + uuid + ".(QCF_NAME : " + qcfName + QUEUE_NAME
							+ queueName + ") " + res.getMensajeError());
					if (solicitudReporteDTO != null) {
						actualizarSolicitud(solicitudReporteDTO, EstadoSolicitudEjecucionEstate.FALLO_EJECUCION,
								res.getMensajeError());// para saber si esta encolado
					}
				}*/
			}
		} catch (Exception e) {
			resultado = e.getMessage();
			log.error("Error en JMSSender.sendRestMessageList." + uuid + ".(QCF_NAME : " + qcfName + QUEUE_NAME
					+ queueName + ") " + e.getMessage());
			if (solicitudReporteDTO != null) {
				actualizarSolicitud(solicitudReporteDTO, EstadoSolicitudEjecucionEstate.FALLO_EJECUCION,
						e.getMessage());// para saber si esta encolado
			}
		}
		return resultado;
	}

	private MensajeDTO generarMensaje(Object obj, String url, String operacion, String qcfName, String queueName,
			String uuid) {
		MensajeDTO mensaje = new MensajeDTO();
		mensaje.setQcfName(qcfName);
		mensaje.setQueueName(queueName);
		mensaje.setUrlRestEjecutor(url);
		mensaje.setData(SerializationUtil.toString(obj));
		mensaje.setOperacion(operacion);
		mensaje.setMetodo("post");
		mensaje.setUsuario("");
		mensaje.setClave("");
		mensaje.setUuid(uuid);
		return mensaje;
	}

	@Override
	public SolicitudReporteDTO actualizarSolicitud(SolicitudReporteDTO obj,
			EstadoSolicitudEjecucionEstate estadoSolicitudEjecucionEstate, String error) {
		try {

			obj.setEstado(estadoSolicitudEjecucionEstate.getKey());

			SolicitudReporte solicitudReporte = to(obj, SolicitudReporte.class);
			if (solicitudReporte.getIdSolicitudReporte() == null) {
				reporteServiceLocal.controladorAccionSolicitudReporte(solicitudReporte, AccionType.CREAR);
				obj.setIdSolicitudReporte(solicitudReporte.getIdSolicitudReporte());
			} else {
				reporteServiceLocal.controladorAccionSolicitudReporte(solicitudReporte, AccionType.MODIFICAR);
			}
			if (EstadoSolicitudEjecucionEstate.FALLO_EJECUCION.getKey()
					.equals(estadoSolicitudEjecucionEstate.getKey())) {
				obj.setFechaTermino(FechaUtil.obtenerFechaActual());
				procesarColaServiceLocal.guardarIntentosGenerarReporteFallidos(obj, error, false);
			}
			boolean isTieneColaNocturna = obj.getPametrosMap().containsKey(ParametroUtil.ES_COLA_NOCTURNA);
			if (isTieneColaNocturna) {
				String idColaNocturna = obj.getPametrosMap().get(ParametroUtil.COLA_NOCTURNA_ID) + "";
				actualizarColaNocturna(idColaNocturna, estadoSolicitudEjecucionEstate);
			}
		} catch (Exception e) {
			log.error("actualizarSolicitud", e);
		}
		return obj;
	}

	@Override
	public void actualizarColaNocturna(String idColaNocturna,
			EstadoSolicitudEjecucionEstate estadoSolicitudEjecucionEstate) {
		if (!StringUtil.isNullOrEmpty(idColaNocturna)) {
			configuracionColaServiceLocal.actualizarEstadoColaNoctura(idColaNocturna, estadoSolicitudEjecucionEstate);
		}
	}

	@Override
	public Integer cantidadMessage(String qcfName, String queueName) {
		return ColaInstanceCacheUtil.getInstance().getContarColaInstance(queueName);
	}

	@Override
	public void sendMessageList(List<Object> listaObject) {
		sendMessageList(listaObject, ConfiguracionJMSUtil.QCF_NAME, ConfiguracionJMSUtil.QUEUE_NAME);
	}

	public void sendMessagePreferencialList(List<Object> listaObject) {
		sendMessageList(listaObject, ConfiguracionJMSUtil.QCF_REPORTE_PREFERENCIAL_NAME,
				ConfiguracionJMSUtil.QUEUE_REPORTE_PREFERENCIAL_NAME);
	}

	public void sendMessagePreferencialList(List<Object> listaObject, String url) {
		sendMessageList(listaObject, ConfiguracionJMSUtil.QCF_REPORTE_PREFERENCIAL_NAME,
				ConfiguracionJMSUtil.QUEUE_REPORTE_PREFERENCIAL_NAME);
	}

	public void sendMessageRegularList(List<Object> listaObject) {
		sendMessageList(listaObject, ConfiguracionJMSUtil.QCF_REPORTE_REGULAR_NAME,
				ConfiguracionJMSUtil.QUEUE_REPORTE_REGULAR_NAME);
	}

	public void sendMessageRegularList(List<Object> listaObject, String url) {
		sendMessageList(listaObject, ConfiguracionJMSUtil.QCF_REPORTE_REGULAR_NAME,
				ConfiguracionJMSUtil.QUEUE_REPORTE_REGULAR_NAME);
	}

	public void sendMessagePesadoList(List<Object> listaObject) {
		sendMessageList(listaObject, ConfiguracionJMSUtil.QCF_REPORTE_PESADO_NAME,
				ConfiguracionJMSUtil.QUEUE_REPORTE_PESADO_NAME);
	}

	public void sendMessagePesadoList(List<Object> listaObject, String url) {
		sendMessageList(listaObject, ConfiguracionJMSUtil.QCF_REPORTE_PESADO_NAME,
				ConfiguracionJMSUtil.QUEUE_REPORTE_PESADO_NAME);
	}

	public void sendMessageHeavyList(List<Object> listaObject) {
		sendRestMessageList(listaObject, ConfiguracionJMSUtil.QCF_REPORTE_HEAVY_NAME,
				ConfiguracionJMSUtil.QUEUE_REPORTE_HEAVY_NAME);
	}

	public String sendMessageNocturnoList(List<Object> listaObject) {
		return sendRestMessageList(listaObject, ConfiguracionJMSUtil.QCF_REPORTE_NOCTURNO_NAME,
				ConfiguracionJMSUtil.QUEUE_REPORTE_NOCTURNO_NAME);
	}

	public void sendMessageTramaList(List<Object> listaObject) {
		sendRestMessageList(listaObject, ConfiguracionJMSUtil.QCF_TRAMA_CONTROL_NAME,
				ConfiguracionJMSUtil.QUEUE_TRAMA_CONTROL_NAME);
	}

	public void sendMessageTramaFTPList(List<Object> listaObject) {
		sendRestMessageList(listaObject, ConfiguracionJMSUtil.QCF_TRAMA_FTP_CONTROL_NAME,
				ConfiguracionJMSUtil.QUEUE_TRAMA_FTP_CONTROL_NAME);
	}

	@Override
	public void sendMessageCorreoList(List<Object> listaObject) {
		sendMessageList(listaObject, ConfiguracionJMSUtil.QCF_CORREO_NAME, ConfiguracionJMSUtil.QUEUE_CORREO_NAME);
	}

	public void sendRestMessageCorreoList(List<Object> listaObject) {
		sendRestMessageList(listaObject, ConfiguracionJMSUtil.QCF_CORREO_NAME, ConfiguracionJMSUtil.QUEUE_CORREO_NAME);
	}

}