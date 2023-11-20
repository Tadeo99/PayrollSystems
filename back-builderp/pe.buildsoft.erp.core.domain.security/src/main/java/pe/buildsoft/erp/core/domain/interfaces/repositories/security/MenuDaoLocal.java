package pe.buildsoft.erp.core.domain.interfaces.repositories.security;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.security.Menu;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class MenuDaoLocal.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue Apr 18 11:25:13 COT 2017
 * @since SIAA-CORE 2.1
 */
@Local
public interface MenuDaoLocal  extends GenericDAOLocal<Long,Menu> {
	
	
	/**
	 * Obtener menu usuario.
	 *
	 * @param usuario el usuario
	 * @param idSistema el id sistema
	 * @return the list
	 * @ the exception
	 */
	List<Menu> obtenerMenuUsuario(String idUsuario) ;
	
	/**
	 * Listar menu.
	 *
	 * @param menu el menu
	 * @return the list
	 * @ the exception
	 */
	List<Menu> listar(BaseSearch filtro) ;
	
	/**
	 * contar lista menu.
	 *
	 * @param menu el menu
	 * @return the list
	 * @ the exception
	 */
	int contar(BaseSearch filtro);
	/**
	 * Generar id menu.
	 *
	 * @return the Long
	 * @ the exception
	 */
	Long generarId() ;
}