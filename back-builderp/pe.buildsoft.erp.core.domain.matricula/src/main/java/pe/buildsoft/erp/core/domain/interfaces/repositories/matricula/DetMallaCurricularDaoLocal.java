package pe.buildsoft.erp.core.domain.interfaces.repositories.matricula;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.matricula.DetMallaCurricular;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class DetMallaCurricularDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:55 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface DetMallaCurricularDaoLocal extends GenericDAOLocal<String, DetMallaCurricular> {
	/**
	 * Listar det malla curricular.
	 *
	 * @param filtro el det malla curricular
	 * @return the list
	 * @throws Exception the exception
	 */
	List<DetMallaCurricular> listar(BaseSearch filtro);

	/**
	 * contar lista det malla curricular.
	 *
	 * @param filtro el det malla curricular
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);

	/**
	 * Generar id detMallaCurricular.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();

	List<DetMallaCurricular> get(BaseSearch filtro);
}