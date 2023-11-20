package pe.buildsoft.erp.core.domain.interfaces.servicios.cola;

import java.util.Map;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.cola.SolicitudReporte;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ConfiguracionReporteFromCorreoVO;
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
public interface ReporteServiceLocal {
	
	Map<String,ConfiguracionReporteFromCorreoVO> obtenerConfiguracionReporteFromCorreoMap();
	
	/**
	 * Controlador accion solicitud reporte.
	 *
	 * @param solicitudReporte el solicitud reporte
	 * @param accionType el accion type
	 * @return the solicitud reporte
	 * @throws Exception the exception
	 */
	SolicitudReporte controladorAccionSolicitudReporte(SolicitudReporte solicitudReporte,AccionType accionType); 

	/**
	 * Obtener ultimo codigo solicitud by usuario.
	 *
	 * @param usuario el usuario 
	 * @return the codigo solicitud reporte 
	 */
	String obtenerUltimoCodigoSolicitudByUsuario(Long idUsuario);
}