package pe.buildsoft.erp.core.domain.interfaces.repositories.nota;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.nota.DetRegistroNota;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class DetRegistroNotaDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Jun 10 00:13:46 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface DetRegistroNotaDaoLocal extends GenericDAOLocal<String, DetRegistroNota> {
	/**
	 * Listar det registro nota.
	 *
	 * @param filtro el det registro nota
	 * @return the list
	 * @throws Exception the exception
	 */
	List<DetRegistroNota> listar(BaseSearch filtro);

	/**
	 * contar lista det registro nota.
	 *
	 * @param filtro el det registro nota
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);

	/**
	 * Generar id detRegistroNota.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();

	List<DetRegistroNota> getByCurso(String idDetMallaCurricular, String idAlumno,
			Boolean esActaEvaluacionFinalAplazado);
}