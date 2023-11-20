package pe.buildsoft.erp.core.domain.interfaces.repositories.matricula;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.matricula.CriterioEvaluacion;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class CriterioEvaluacionDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Jun 10 00:13:45 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface CriterioEvaluacionDaoLocal extends GenericDAOLocal<String, CriterioEvaluacion> {
	/**
	 * Listar criterio evaluacion.
	 *
	 * @param filtro el criterio evaluacion
	 * @return the list
	 * @throws Exception the exception
	 */
	List<CriterioEvaluacion> listar(BaseSearch filtro);

	/**
	 * contar lista criterio evaluacion.
	 *
	 * @param filtro el criterio evaluacion
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);

	/**
	 * Generar id criterioEvaluacion.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();

	List<CriterioEvaluacion> listar(String estado, String idDetMallaCurricular, String idCriterioEvaluacionPadre);
}