package pe.buildsoft.erp.core.domain.interfaces.repositories.nota;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Local;
import pe.buildsoft.erp.core.domain.entidades.nota.CursoNotaUnidadProm;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class CursoNotaUnidadPromDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Jun 10 00:13:46 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface CursoNotaUnidadPromDaoLocal  extends GenericDAOLocal<String,CursoNotaUnidadProm> {
	/**
	 * Listar curso nota unidad prom.
	 *
	 * @param filtro el curso nota unidad prom
	 * @return the list
	 * @throws Exception the exception
	 */
	List<CursoNotaUnidadProm> listar(BaseSearch filtro);
	
	/**
	 * contar lista curso nota unidad prom.
	 *
	 * @param filtro el curso nota unidad prom
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);
	/**
	 * Generar id cursoNotaUnidadProm.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();
	
	Map<String,List<CursoNotaUnidadProm>> getMap(List<String> listaIdDetRegistroNota);
	
	CursoNotaUnidadProm find(String idDetRegistroNota);
}