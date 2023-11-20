package pe.buildsoft.erp.core.domain.interfaces.repositories.escalafon;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.escalafon.Personal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class PersonalDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface PersonalDaoLocal  extends GenericDAOLocal<String,Personal> {

	/**
	 * Listar personal.
	 *
	 * @param personal el personal
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Personal> listar(BaseSearch filtro);
	List<String> listarIds(BaseSearch filtro);
	
	/**
	 * contar lista personal.
	 *
	 * @param personal el personal
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);
	/**
	 * Generar id personal.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();
	
	List<Personal> listarPagina(Long idCategoriaTrabajador) ;
	
	Personal findPersonal(Personal filtro);
	Personal find(String idPersonal);
	
}