package pe.buildsoft.erp.core.domain.interfaces.servicios;

import java.io.File;
import java.util.List;
import java.util.Map;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.infra.transversal.entidades.vo.CorreoVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RegistroMensajeriaOutputVO;
import pe.buildsoft.erp.core.infra.transversal.type.AsuntoDetalleCorreoType;

/**
 * La Class MensajeriaServiceLocal.
 * <ul>
 * <li>Copyright 2014 MAPFRE- OSCE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Tue Apr 29 19:20:13 COT 2014
 * @since Rep v1..0
 */
@Local
public interface MensajeriaServiceLocal {

	/**
	 * Enviar correo.
	 *
	 * @param correos el correos
	 * @return the registro mensajeria output vo
	 */
	RegistroMensajeriaOutputVO enviarCorreo(List<CorreoVO> correos);

	/**
	 * Enviar correo electronico.
	 *
	 * @param correo the correo
	 */
	String enviarCorreoElectronico(final CorreoVO correo);

	String enviarCorreoElectronico(final CorreoVO correo, String key);

	String obtenerMensajeByTipo(AsuntoDetalleCorreoType asuntoDetalleCorreoType, String codigoSolicitud,
			Map<String, Object> dataModel);

	void envioCorreoByTipo(String email, String codigoSolicitud, AsuntoDetalleCorreoType asuntoDetalleCorreoType,
			Map<String, Object> dataModel);

	void envioCorreoByTipo(List<String> destinatario, String codigoSolicitud,
			AsuntoDetalleCorreoType asuntoDetalleCorreoType, Map<String, Object> dataModel);

	void envioCorreoByTipo(List<String> destinatario, AsuntoDetalleCorreoType asuntoDetalleCorreoType,
			Map<String, Object> dataModel, File[] archivosAdjuntos);
}