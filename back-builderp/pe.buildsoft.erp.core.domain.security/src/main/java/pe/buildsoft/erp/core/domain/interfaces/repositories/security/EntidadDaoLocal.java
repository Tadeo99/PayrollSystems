package pe.buildsoft.erp.core.domain.interfaces.repositories.security;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.security.Entidad;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class EntidadDaoLocal.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Dec 14 00:27:42 COT 2017
 * @since SIAA-CORE 2.1
 */
 @Local
public interface EntidadDaoLocal  extends GenericDAOLocal<String,Entidad> {
	/**
	 * Listar entidad.
	 *
	 * @param entidad el entidad
	 * @return the list
	 * @ the exception
	 */
	List<Entidad> listar(BaseSearch filtro) ;
	
	/**
	 * contar lista entidad.
	 *
	 * @param entidad el entidad
	 * @return the list
	 * @ the exception
	 */
	int contar(BaseSearch filtro);
	/**
	 * Generar id entidad.
	 *
	 * @return the String
	 * @ the exception
	 */
	String generarId() ;
}