package pe.buildsoft.erp.core.application.interfaces.cola;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.infra.transversal.entidades.SolicitudReporteDTO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;


/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul> 
 * 
 * La Interface ReporteServiceLocal.
 *
 * @author BuildSoft.
 * @version 1.0 , 07/04/2015
 * @since BuildErp 1.0
 */
@Local
public interface ReporteAppServiceLocal {
	
	/**
	 * Controlador accion solicitud reporte.
	 *
	 * @param solicitudReporte el solicitud reporte
	 * @param accionType el accion type
	 * @return the solicitud reporte
	 * @throws Exception the exception
	 */
	SolicitudReporteDTO controladorAccionSolicitudReporte(SolicitudReporteDTO solicitudReporte,AccionType accionType) throws Exception; 
	
	/**
	 * Obtener ultimo codigo solicitud by usuario.
	 *
	 * @param usuarioDTO el usuario dto
	 * @return the codigo solicitud reporte dto
	 */
	String obtenerUltimoCodigoSolicitudByUsuario(Long idUsuario);
	
}