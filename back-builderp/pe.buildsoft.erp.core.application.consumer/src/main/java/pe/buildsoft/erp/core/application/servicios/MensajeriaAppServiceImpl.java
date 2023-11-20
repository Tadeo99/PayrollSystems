package pe.buildsoft.erp.core.application.servicios;

import java.io.File;
import java.util.List;
import java.util.Map;

import jakarta.inject.Inject;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;

import pe.buildsoft.erp.core.application.interfaces.MensajeriaAppServiceLocal;
import pe.buildsoft.erp.core.domain.interfaces.servicios.MensajeriaServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.CorreoVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RegistroMensajeriaOutputVO;
import pe.buildsoft.erp.core.infra.transversal.type.AsuntoDetalleCorreoType;

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
public class MensajeriaAppServiceImpl implements MensajeriaAppServiceLocal {

	@Inject
	private MensajeriaServiceLocal servicio;

	@Override
	public RegistroMensajeriaOutputVO enviarCorreo(final List<CorreoVO> correos) {
		return servicio.enviarCorreo(correos);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.gob.mapfre.pwr.rep.ejb.service.local.MensajeriaServiceLocal#
	 * enviarCorreoElectronico(pe.gob.mapfre.pwr.rep.model.vo.CorreoVO)
	 */
	@Override
	public String enviarCorreoElectronico(final CorreoVO correo) {
		return servicio.enviarCorreoElectronico(correo);
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
		return servicio.obtenerMensajeByTipo(asuntoDetalleCorreoType, codigoSolicitud, dataModel);
	}

	@Override
	public void envioCorreoByTipo(String email, String codigoSolicitud, AsuntoDetalleCorreoType asuntoDetalleCorreoType,
			Map<String, Object> dataModel) {
		servicio.envioCorreoByTipo(email, codigoSolicitud, asuntoDetalleCorreoType, dataModel);
	}

	/**
	 * Envio correo by tipo.
	 *
	 * @param destinatario            el destinatario
	 * @param codigoSolicitud         el codigo solicitud
	 * @param asuntoDetalleCorreoType el asunto detalle correo type
	 * @the exception
	 */
	@Override
	public void envioCorreoByTipo(List<String> destinatario, String codigoSolicitud,
			AsuntoDetalleCorreoType asuntoDetalleCorreoType, Map<String, Object> dataModel) {
		servicio.envioCorreoByTipo(destinatario, codigoSolicitud, asuntoDetalleCorreoType, dataModel);
	}

	@Override
	public void envioCorreoByTipo(List<String> destinatario, AsuntoDetalleCorreoType asuntoDetalleCorreoType,
			Map<String, Object> dataModel, File[] archivosAdjuntos) {
		servicio.envioCorreoByTipo(destinatario, asuntoDetalleCorreoType, dataModel, archivosAdjuntos);
	}
}