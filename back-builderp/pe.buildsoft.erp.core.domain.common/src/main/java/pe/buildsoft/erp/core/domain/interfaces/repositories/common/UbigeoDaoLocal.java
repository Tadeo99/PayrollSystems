package pe.buildsoft.erp.core.domain.interfaces.repositories.common;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.common.Ubigeo;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class UbigeoDaoLocal.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue Apr 18 11:25:12 COT 2017
 * @since SIAA-CORE 2.1
 */
@Local
public interface UbigeoDaoLocal  extends GenericDAOLocal<String,Ubigeo> {
	/**
	 * Listar ubigeo.
	 *
	 * @param ubigeo el ubigeo
	 * @return the list
	 * @ the exception
	 */
	List<Ubigeo> listar(BaseSearch filtro) ;
	
	/**
	 * contar lista ubigeo.
	 *
	 * @param ubigeo el ubigeo
	 * @return the list
	 * @ the exception
	 */
	int contar(BaseSearch filtro);
	/**
	 * Generar id ubigeo.
	 *
	 * @return the String
	 * @ the exception
	 */
	String generarId() ;
}