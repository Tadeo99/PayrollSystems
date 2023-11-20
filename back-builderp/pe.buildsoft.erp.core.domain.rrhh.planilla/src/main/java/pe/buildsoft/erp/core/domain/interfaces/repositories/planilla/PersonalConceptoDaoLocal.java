package pe.buildsoft.erp.core.domain.interfaces.repositories.planilla;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.planilla.PersonalConcepto;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class PersonalConceptoDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface PersonalConceptoDaoLocal extends GenericDAOLocal<String, PersonalConcepto> {
	/**
	 * Listar tipo planilla.
	 *
	 * @param filtro el tipo planilla
	 * @return the list
	 * @throws Exception the exception
	 */
	List<PersonalConcepto> listar(BaseSearch filtro);

	/**
	 * contar lista tipo planilla.
	 *
	 * @param filtro el tipo planilla
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);

	/**
	 * Generar id personalConcepto.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();
}