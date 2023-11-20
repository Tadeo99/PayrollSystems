package pe.buildsoft.erp.core.domain.servicios;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.activation.CommandMap;
import jakarta.activation.MailcapCommandMap;
import jakarta.inject.Inject;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import pe.buildsoft.erp.core.domain.cache.ConfiguracionColaCacheSingletonLocal;
import pe.buildsoft.erp.core.domain.interfaces.servicios.MensajeriaServiceLocal;
import pe.buildsoft.erp.core.domain.interfaces.servicios.cola.IJMSSender;
import pe.buildsoft.erp.core.infra.transversal.cache.ConfiguracionCacheUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.CorreoVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.DetalleCorreoVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RegistroMensajeriaOutputVO;
import pe.buildsoft.erp.core.infra.transversal.type.AsuntoDetalleCorreoType;
import pe.buildsoft.erp.core.infra.transversal.type.RespuestaNaturalType;
import pe.buildsoft.erp.core.infra.transversal.type.TipoDetalleCorreoType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ConfiguracionActiveDirectoryWSUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ConstanteConfigUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ConstanteTypeUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.FechaUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.MailMessages;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ProcesarPlantillaUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ResourceUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.SMTPAuthenticator;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class MensajeriaServiceImpl.
 * <ul>
 * <li>Copyright 2014 MAPFRE- OSCE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Tue Apr 29 19:20:13 COT 2014
 * @since Rep v1..0
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@TransactionManagement(TransactionManagementType.BEAN)
public class MensajeriaServiceImpl implements MensajeriaServiceLocal {

	private static final String ENVIAR_CORREO_ELECTRONICO = "enviarCorreoElectronico.";
	private static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
	/** El log. */
	private static Logger log = LoggerFactory.getLogger(MensajeriaServiceImpl.class);
	private boolean isCargoComando = false;

	@Inject
	private ConfiguracionColaCacheSingletonLocal integracionTron2000Cache;

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
	public RegistroMensajeriaOutputVO enviarCorreo(final List<CorreoVO> correos) {
		final RegistroMensajeriaOutputVO resultado = new RegistroMensajeriaOutputVO();
		try {
			for (var correoVO : correos) {
				correoVO.modificarUUID(UUIDUtil.generarUUID());
			}
			jMSSender.sendMessageCorreoList(new ArrayList<>(correos));
		} catch (Exception e) {
			log.error("Error MensajeriaServiceImpl.enviarCorreo(final List<CorreoVO> correos) al enviar cola de correo "
					+ e.getMessage());
			resultado.setError(e.toString());
		}

		return resultado;
	}

	private boolean isPrintLogMail() {
		return ConfiguracionCacheUtil.getInstance().isPrintLogProcesos("enviar_mail");
	}

	private void activarCagadorClases() {
		if (!isCargoComando || ConfiguracionCacheUtil.getInstance().getPwrConfUtil("jakarta.mail.activation")
				.equalsIgnoreCase("true")) {
			isCargoComando = true;
			MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
			mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
			mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
			mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
			mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
			mc.addMailcap("message/rfc822;; x-java-content- handler=com.sun.mail.handlers.message_rfc822");
			log.error("MensajeriaServiceImpl.mail.cargador.clases " + FechaUtil.obtenerFechaActual());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.gob.mapfre.pwr.rep.ejb.service.local.MensajeriaServiceLocal#
	 * enviarCorreoElectronico(pe.gob.mapfre.pwr.rep.model.vo.CorreoVO)
	 */
	@Override
	public String enviarCorreoElectronico(final CorreoVO correo) {
		return enviarCorreoElectronico(correo, "mailserver");
	}

	@Override
	public String enviarCorreoElectronico(final CorreoVO correo, String key) {
		String resultado = "";
		this.activarCagadorClases();
		boolean isPrintLog = isPrintLogMail();
		if (isPrintLog) {
			if (StringUtil.isNullOrEmpty(correo.getUUID())) {
				correo.modificarUUID(UUIDUtil.generarElementUUID());
			}
			log.error(ENVIAR_CORREO_ELECTRONICO + correo.getUUID() + ".inicio=."
					+ ConfiguracionActiveDirectoryWSUtil.PROXY_ENABLED + "=> " + ConfiguracionActiveDirectoryWSUtil
							.getProxyBoolean(ConfiguracionActiveDirectoryWSUtil.PROXY_ENABLED));
			log.error(ENVIAR_CORREO_ELECTRONICO + correo.getUUID() + ".inicio==> " + FechaUtil.obtenerFechaActual());
			log.error(ENVIAR_CORREO_ELECTRONICO + correo.getUUID() + ".proceso.correo.getAsunto()==> "
					+ correo.getAsunto());
			log.error(ENVIAR_CORREO_ELECTRONICO + correo.getUUID() + ".proceso.correo.getContenido()==> "
					+ correo.getContenido());
			if (correo.getArchivosAdjuntos() != null && correo.getArchivosAdjuntos().length > 0) {
				log.error(ENVIAR_CORREO_ELECTRONICO + correo.getUUID()
						+ ".proceso.correo.getArchivosAdjuntos().length==> " + correo.getArchivosAdjuntos().length);
			}
			log.error(ENVIAR_CORREO_ELECTRONICO + correo.getUUID() + ".proceso.correo.getDetalleCorreo().size==> "
					+ correo.getDetalleCorreo().size());
			for (var objDet : correo.getDetalleCorreo()) {
				log.error(ENVIAR_CORREO_ELECTRONICO + correo.getUUID()
						+ ".proceso.correo.getDetalleCorreo().getEmail()==> " + objDet.getEmail());
				log.error(ENVIAR_CORREO_ELECTRONICO + correo.getUUID()
						+ ".proceso.correo.getDetalleCorreo().getTipo()==> " + objDet.getTipo());
			}
		}
		// verificar que los datos sean correctos
		if (correo.getDetalleCorreo().size() > 0) {
			if (!correo.getAsunto().isEmpty()) {
				if (!correo.getContenido().isEmpty()) {
					try {
						String pwrMail = null;
						if (!StringUtil.isNullOrEmpty(MailMessages.getParametro(key + ".mail.smtp.user"))) {
							pwrMail = MailMessages.getParametro(key + ".mail.smtp.user");
						}

						String pwrName = MailMessages.getParametro(key + ".mail.name.from");
						String pwrPass = null;
						if (!StringUtil.isNullOrEmpty(MailMessages.getParametro(key + ".mail.smtp.pass"))) {
							pwrPass = MailMessages.getParametro(key + ".mail.smtp.pass");
						}
						Properties props = new Properties();
						if (!StringUtil.isNullOrEmpty(MailMessages.getParametro(key + ".mail.smtp.host"))) {
							props.setProperty("mail.smtp.host", MailMessages.getParametro(key + ".mail.smtp.host"));
						}
						if (!StringUtil.isNullOrEmpty(MailMessages.getParametro(key + ".mail.smtp.port"))) {
							props.setProperty("mail.smtp.port", MailMessages.getParametro(key + ".mail.smtp.port"));
						}
						if (!StringUtil.isNullOrEmpty(pwrMail)) {
							props.setProperty("mail.smtp.user", pwrMail);
						}
						if (!StringUtil.isNullOrEmpty(MailMessages.getParametro(key + ".mail.smtp.auth"))) {
							props.setProperty("mail.smtp.auth", MailMessages.getParametro(key + ".mail.smtp.auth"));
						}
						if (!StringUtil.isNullOrEmpty(MailMessages.getParametro(MAIL_SMTP_STARTTLS_ENABLE))) {
							props.setProperty(MAIL_SMTP_STARTTLS_ENABLE,
									MailMessages.getParametro(MAIL_SMTP_STARTTLS_ENABLE));
						}
						if (isPrintLog) {
							log.error(ENVIAR_CORREO_ELECTRONICO + correo.getUUID() + ".proceso.pwrMail==> " + pwrMail);
							log.error(ENVIAR_CORREO_ELECTRONICO + correo.getUUID() + ".proceso.pwrPass==> " + pwrPass);
						}
						SMTPAuthenticator authenticator = null;
						if (!StringUtil.isNullOrEmpty(pwrMail) && !StringUtil.isNullOrEmpty(pwrPass)) {
							authenticator = new SMTPAuthenticator(pwrMail, pwrPass);
						}
						Session session = null;
						if (authenticator != null) {
							session = Session.getInstance(props, authenticator);
						} else {
							session = Session.getDefaultInstance(props, null);
						}
						MimeMessage messageToClient = new MimeMessage(session);
						String pwrMailForm = pwrMail;
						if (correo.getConfiguracionReporteFromCorreoVO() != null
								&& correo.getConfiguracionReporteFromCorreoVO().getCodigoForm() != null) {
							if (RespuestaNaturalType.SI.getKey().toString()
									.equalsIgnoreCase(correo.getConfiguracionReporteFromCorreoVO().getFlagLDAP())) {
								pwrMailForm = correo.getUsuarioCorreoCorporativo();
								pwrName = pwrName + " " + correo.getUsuarioNombreCompleto();
							} else {
								pwrMailForm = correo.getConfiguracionReporteFromCorreoVO().getEmail();
							}
						}
						if (isPrintLog) {
							log.error(ENVIAR_CORREO_ELECTRONICO + correo.getUUID() + ".proceso.pwrMailForm==> "
									+ pwrMailForm);
							log.error(ENVIAR_CORREO_ELECTRONICO + correo.getUUID() + ".proceso.pwrName==> " + pwrName);
						}
						if (!StringUtil.isNullOrEmpty(pwrMailForm)) {
							messageToClient.setFrom(new InternetAddress(pwrMailForm, pwrName));
						} else {
							if (isPrintLog) {
								log.error(ENVIAR_CORREO_ELECTRONICO + correo.getUUID()
										+ ".proceso.pwrMailForm.defaulft==> "
										+ MailMessages.getParametro(key + ".mail.name.from.mail.defaulft"));
							}
							messageToClient.setFrom(new InternetAddress(
									MailMessages.getParametro(key + ".mail.name.from.mail.defaulft"), pwrName));
						}

						Multipart multipart = new MimeMultipart();
						MimeBodyPart cuerpoCorreo = new MimeBodyPart();

						for (var detCorreo : correo.getDetalleCorreo()) {
							String clientMail = detCorreo.getEmail();
							if (TipoDetalleCorreoType.DESTINATARIO.getKey().equals(detCorreo.getTipo())) {
								messageToClient.addRecipient(Message.RecipientType.TO,
										new InternetAddress(clientMail, clientMail));
							}
							if (TipoDetalleCorreoType.CON_COPIA.getKey().equals(detCorreo.getTipo())) {
								messageToClient.addRecipient(Message.RecipientType.CC,
										new InternetAddress(clientMail, clientMail));
							}
							if (TipoDetalleCorreoType.CON_COPIA_OCULTA.getKey().equals(detCorreo.getTipo())) {
								messageToClient.addRecipient(Message.RecipientType.BCC,
										new InternetAddress(clientMail, clientMail));
							}
						}
						messageToClient.setSubject(correo.getAsunto());
						cuerpoCorreo.setContent(correo.getContenido(), "text/html");
						multipart.addBodyPart(cuerpoCorreo);

						// adjuntat archivo:
						File[] archivosAdjuntos = correo.getArchivosAdjuntos();

						if (archivosAdjuntos != null && archivosAdjuntos.length > 0) {
							int index = 1;
							for (var archivo : archivosAdjuntos) {
								MimeBodyPart adjuntoCorreo = new MimeBodyPart();
								adjuntoCorreo.attachFile(archivo);
								String keyIndex = "reName" + index;
								if (correo.getParametrosMap().containsKey(keyIndex)) {
									adjuntoCorreo.setFileName(correo.getParametrosMap().get(keyIndex));
								} else {
									adjuntoCorreo.setFileName(archivo.getName());
								}

								multipart.addBodyPart(adjuntoCorreo);
								if (isPrintLog) {
									log.error(ENVIAR_CORREO_ELECTRONICO + correo.getUUID()
											+ ".proceso.correo.archivosAdjuntos().getName().exists==> "
											+ archivo.exists());
									log.error(ENVIAR_CORREO_ELECTRONICO + correo.getUUID()
											+ ".proceso.correo.archivosAdjuntos().getName()==> " + archivo.getName());
									log.error(ENVIAR_CORREO_ELECTRONICO + correo.getUUID()
											+ ".proceso.correo.archivosAdjuntos().getAbsolutePath()==> "
											+ archivo.getAbsolutePath());
								}
								index++;
							}
						}
						messageToClient.setContent(multipart);
						messageToClient.setSentDate(new java.util.Date());
						// Enviamos los mensajes.
						if (authenticator != null) {
							Transport t = session.getTransport("smtp");
							t.connect(pwrMail, pwrPass);
							t.sendMessage(messageToClient, messageToClient.getAllRecipients());
							t.close();
						} else {
							// Enviamos los mensajes.
							Transport.send(messageToClient);
						}
						log.info("Envio correo correctamente");
						if (isPrintLog) {
							log.error(ENVIAR_CORREO_ELECTRONICO + correo.getUUID()
									+ ".proceso.Envio correo correctamente==> " + FechaUtil.obtenerFechaActual());
						}
					} catch (MessagingException ex) {
						log.error(
								"Error MensajeriaServiceImpl.enviarCorreoElectronico(final CorreoVO correo) no se pudo enviar el correo "
										+ correo.getUUID() + " MessagingException ex : " + ex.getMessage());
						if (isPrintLog) {
							log.error("enviarCorreoElectronico", ex);
						}
						resultado = ex.getMessage();
					} catch (Exception e) {
						log.error(
								"Error MensajeriaServiceImpl.enviarCorreoElectronico(final CorreoVO correo) no se pudo enviar el correo "
										+ correo.getUUID() + " Exception e : " + e.getMessage());
						if (isPrintLog) {
							log.error("enviarCorreoElectronico", e);
						}
						resultado = e.getMessage();
					}

				} else {
					// registrar fallo
					log.error(
							"Error MensajeriaServiceImpl.enviarCorreoElectronico(final CorreoVO correo) no tiene contenido");
				}
			} else {
				// no tiene asunto
				log.error("Error MensajeriaServiceImpl.enviarCorreoElectronico(final CorreoVO correo) no tiene asunto");
			}
		} else {
			log.error(
					"Error MensajeriaServiceImpl.enviarCorreoElectronico(final CorreoVO correo) no tiene destinatario");
		}
		return resultado;
	}

	/**
	 * Obtener mensaje by tipo.
	 *
	 * @param asuntoDetalleCorreoType el asunto detalle correo type
	 * @param codigoSolicitud         el codigo solicitud
	 * @return the string
	 */
	@Override
	public String obtenerMensajeByTipo(AsuntoDetalleCorreoType asuntoDetalleCorreoType, String codigoSolicitud,
			Map<String, Object> dataModel) {
		if (dataModel == null) {
			dataModel = new HashMap<>();
		}
		dataModel.put("codigoSolicitud", codigoSolicitud);
		StringBuilder resultado = new StringBuilder(ProcesarPlantillaUtil.procesarPlantillaByFreemarkerDo(dataModel,
				ConstanteConfigUtil.RUTA_GENERAL_TEMPLANTE, asuntoDetalleCorreoType.getTemplate()));
		return resultado.toString();
	}

	@Override
	public void envioCorreoByTipo(String email, String codigoSolicitud, AsuntoDetalleCorreoType asuntoDetalleCorreoType,
			Map<String, Object> dataModel) {
		List<String> destinatario = new ArrayList<>();
		if (!StringUtil.isNullOrEmpty(email)) {
			destinatario.add(email);
		}
		/*
		 * if (!StringUtil.isNullOrEmpty(usuario.getCorreoPersonal())) {
		 * destinatario.add(usuario.getCorreoPersonal()); }
		 */
		envioCorreoByTipo(destinatario, codigoSolicitud, asuntoDetalleCorreoType, dataModel);
	}

	/**
	 * Envio correo by tipo.
	 *
	 * @param destinatario            el destinatario
	 * @param codigoSolicitud         el codigo solicitud
	 * @param asuntoDetalleCorreoType el asunto detalle correo type
	 * @throws Exception the exception
	 */
	@Override
	public void envioCorreoByTipo(List<String> destinatario, String codigoSolicitud,
			AsuntoDetalleCorreoType asuntoDetalleCorreoType, Map<String, Object> dataModel) {
		List<DetalleCorreoVO> detalleCorreo = new ArrayList<>();
		for (var correo : destinatario) {
			DetalleCorreoVO correoDestino = new DetalleCorreoVO(correo, TipoDetalleCorreoType.DESTINATARIO.getKey());
			detalleCorreo.add(correoDestino);
		}
		String asunto = getDescription(null, asuntoDetalleCorreoType.getValue()) + codigoSolicitud;
		List<CorreoVO> correos = new ArrayList<>();
		correos.add(new CorreoVO(asunto, obtenerMensajeByTipo(asuntoDetalleCorreoType, codigoSolicitud, dataModel),
				detalleCorreo));
		enviarCorreo(correos);
	}

	@Override
	public void envioCorreoByTipo(List<String> destinatario, AsuntoDetalleCorreoType asuntoDetalleCorreoType,
			Map<String, Object> dataModel, File[] archivosAdjuntos) {
		List<DetalleCorreoVO> detalleCorreo = new ArrayList<>();
		for (var correo : destinatario) {
			DetalleCorreoVO correoDestino = new DetalleCorreoVO(correo, TipoDetalleCorreoType.DESTINATARIO.getKey());
			detalleCorreo.add(correoDestino);
		}
		String asunto = (String) dataModel.get("asunto");
		List<CorreoVO> correos = new ArrayList<>();
		correos.add(new CorreoVO(asunto, obtenerMensajeByTipo(asuntoDetalleCorreoType, "", dataModel), detalleCorreo));
		correos.get(0).setArchivosAdjuntos(archivosAdjuntos);
		enviarCorreo(correos);
	}

	/**
	 * Obtiene description.
	 *
	 * @param locale el locale
	 * @param value  el value
	 * @return description
	 */
	public String getDescription(Locale locale, String value) {
		return ResourceUtil.getString(locale, ConstanteTypeUtil.BUNDLE_NAME_TYPE, value);
	}
}