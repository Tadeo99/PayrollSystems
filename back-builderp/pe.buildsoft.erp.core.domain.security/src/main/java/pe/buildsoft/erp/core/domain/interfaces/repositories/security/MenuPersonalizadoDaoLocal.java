package pe.buildsoft.erp.core.domain.interfaces.repositories.security;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.security.MenuPersonalizado;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class MenuPersonalizadoDaoLocal.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue Apr 18 11:25:09 COT 2017
 * @since SIAA-CORE 2.1
 */
@Local
public interface MenuPersonalizadoDaoLocal  extends GenericDAOLocal<String,MenuPersonalizado> {
	/**
	 * Listar menu personalizado.
	 *
	 * @param menuPersonalizado el menu personalizado
	 * @return the list
	 * @ the exception
	 */
	List<MenuPersonalizado> listar(BaseSearch filtro) ;
	
	/**
	 * contar lista menu personalizado.
	 *
	 * @param menuPersonalizado el menu personalizado
	 * @return the list
	 * @ the exception
	 */
	int contar(BaseSearch filtro);
	/**
	 * Generar id menuPersonalizado.
	 *
	 * @return the Long
	 * @ the exception
	 */
	String generarId() ;
}