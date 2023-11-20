package pe.buildsoft.erp.core.domain.interfaces.repositories.migrador;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.migrador.HeaderReporte;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;

/**
 * La Class HeaderReporteDaoLocal.
 * <ul>
 * <li>Copyright 2018 MAPFRE -
 * MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Fri Sep 28 12:14:58 COT 2018
 * @since BuildErp 1.0
 */
@Local
public interface HeaderReporteDaoLocal  extends GenericDAOLocal<String,HeaderReporte> {
	
	/**
	 * Listar header reporte.
	 *
	 * @param headerReporte el header reporte
	 * @return the list
	 * @throws Exception the exception
	 */
	List<HeaderReporte> listar(String codigoReporte);
	
	/**
	 * Listar header reporte.
	 *
	 * @param headerReporte el header reporte
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Map<String,Object>> listarByCodigo(String codigoReporte);
	
}