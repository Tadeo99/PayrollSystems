package pe.buildsoft.erp.core.domain.interfaces.repositories.migrador;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.migrador.HeaderReadReporte;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class HeaderReadReporteDaoLocal.
 * <ul>
 * <li>Copyright 2018 MAPFRE -
 * MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Wed Aug 07 14:48:19 COT 2019
 * @since BuildErp 1.0
 */
@Local
public interface HeaderReadReporteDaoLocal  extends GenericDAOLocal<String,HeaderReadReporte> {
	
	List<Map<String,Object>> listar(String codigoReporte);
	
	/**
	 * Listar header read reporte.
	 *
	 * @param filtro el header read reporte
	 * @return the list
	 * @throws Exception the exception
	 */
	List<HeaderReadReporte> listar(BaseSearch filtro);
	
	/**
	 * contar lista header read reporte.
	 *
	 * @param filtro el header read reporte
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);
	/**
	 * Generar id headerReadReporte.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();
}