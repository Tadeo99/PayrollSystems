package pe.buildsoft.erp.core.domain.interfaces.repositories.migrador;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.migrador.ConfiguracionReporteTxt;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class ConfiguracionReporteTxtDaoLocal.
 * <ul>
 * <li>Copyright 2018 MAPFRE -
 * MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Thu Mar 14 10:56:05 COT 2019
 * @since BuildErp 1.0
 */
@Local
public interface ConfiguracionReporteTxtDaoLocal  extends GenericDAOLocal<String,ConfiguracionReporteTxt> {
	/**
	 * Listar configuracion reporte txt.
	 *
	 * @param filtro el configuracion reporte txt
	 * @return the list
	 * @throws Exception the exception
	 */
	List<ConfiguracionReporteTxt> listar(BaseSearch filtro);
	
	/**
	 * contar lista configuracion reporte txt.
	 *
	 * @param filtro el configuracion reporte txt
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);
	/**
	 * Generar id configuracionReporteTxt.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();
}