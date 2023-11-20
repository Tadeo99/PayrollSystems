package pe.buildsoft.erp.core.domain.interfaces.repositories.nota;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Local;
import pe.buildsoft.erp.core.domain.entidades.nota.CursoNotaUnidad;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class CursoNotaUnidadDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Jun 10 00:13:45 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface CursoNotaUnidadDaoLocal extends GenericDAOLocal<String, CursoNotaUnidad> {
	/**
	 * Listar curso nota unidad.
	 *
	 * @param filtro el curso nota unidad
	 * @return the list
	 * @throws Exception the exception
	 */
	List<CursoNotaUnidad> listar(BaseSearch filtro);

	/**
	 * contar lista curso nota unidad.
	 *
	 * @param filtro el curso nota unidad
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);

	/**
	 * Generar id cursoNotaUnidad.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();

	void updateNota(CursoNotaUnidad obj);

	Map<String, List<CursoNotaUnidad>> getMap(List<String> listaIdDetRegistroNota);
}