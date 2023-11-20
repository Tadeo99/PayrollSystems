package pe.buildsoft.erp.core.domain.interfaces.repositories.cola;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.cola.SolicitudReporte;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;

/**
 * La Class SolicitudReporteDaoLocal.
 * <ul>
 * <li>Copyright 2014 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Wed Jul 30 17:22:02 COT 2014
 * @since BuildErp 1.0
 */
@Local
public interface SolicitudReporteDaoLocal extends GenericDAOLocal<String, SolicitudReporte> {

	/**
	 * Generar id solicitudReporte.
	 *
	 * @return the long
	 * @throws Exception the exception
	 */
	String generarId();
}