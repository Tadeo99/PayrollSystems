package pe.buildsoft.erp.core.domain.interfaces.repositories.nota;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Local;
import pe.buildsoft.erp.core.domain.entidades.nota.CursoNotaPeriodo;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class CursoNotaPeriodoDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Jun 10 00:13:45 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface CursoNotaPeriodoDaoLocal  extends GenericDAOLocal<String,CursoNotaPeriodo> {
	/**
	 * Listar curso nota periodo.
	 *
	 * @param filtro el curso nota periodo
	 * @return the list
	 * @throws Exception the exception
	 */
	List<CursoNotaPeriodo> listar(BaseSearch filtro);
	
	/**
	 * contar lista curso nota periodo.
	 *
	 * @param filtro el curso nota periodo
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);
	/**
	 * Generar id cursoNotaPeriodo.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();
	
	Map<String,List<CursoNotaPeriodo>> getMap(List<String> listaIdDetRegistroNota);
	
	void updateNota(CursoNotaPeriodo obj); 
}