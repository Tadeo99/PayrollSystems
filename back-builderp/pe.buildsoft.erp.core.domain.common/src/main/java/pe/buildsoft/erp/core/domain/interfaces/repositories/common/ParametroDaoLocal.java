package pe.buildsoft.erp.core.domain.interfaces.repositories.common;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.common.Parametro;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class ParametroDaoLocal.
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
public interface ParametroDaoLocal  extends GenericDAOLocal<String,Parametro> {
	/**
	 * Listar parametro.
	 *
	 * @param parametro el parametro
	 * @return the list
	 * @ the exception
	 */
	List<Parametro> listar(BaseSearch filtro) ;
	
	/**
	 * contar lista parametro.
	 *
	 * @param parametro el parametro
	 * @return the list
	 * @ the exception
	 */
	int contar(BaseSearch filtro);
	/**
	 * Generar id parametro.
	 *
	 * @return the String
	 * @ the exception
	 */
	String generarId() ;
}