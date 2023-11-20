package pe.buildsoft.erp.core.domain.interfaces.repositories.security;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.security.ConfiguracionMenu;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class ConfiguracionMenuDaoLocal.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue Apr 18 11:25:15 COT 2017
 * @since SIAA-CORE 2.1
 */
@Local
public interface ConfiguracionMenuDaoLocal  extends GenericDAOLocal<String,ConfiguracionMenu> {
	
	/**
	 * Listar configuracion menu.
	 *
	 * @param menu el menu
	 * @return the list
	 * @ the exception
	 */
	List<ConfiguracionMenu> obtenerConfiguracionMenu(Long idMenu) ;
	
	/**
	 * Listar configuracion menu.
	 *
	 * @param filtro el configuracion menu
	 * @return the list
	 * @ the exception
	 */
	List<ConfiguracionMenu> listar(BaseSearch filtro) ;
	
	/**
	 * contar lista configuracion menu.
	 *
	 * @param filtro el configuracion menu
	 * @return the list
	 * @ the exception
	 */
	int contar(BaseSearch filtro);
	/**
	 * Generar id configuracionMenu.
	 *
	 * @return the Long
	 * @ the exception
	 */
	String generarId() ;
}