package pe.buildsoft.erp.core.domain.interfaces.repositories.nota;

import java.util.List;

import jakarta.ejb.Local;
import pe.buildsoft.erp.core.domain.entidades.nota.CursoNotaPeriodoProm;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class CursoNotaPeriodoPromDaoLocal.
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
public interface CursoNotaPeriodoPromDaoLocal  extends GenericDAOLocal<String,CursoNotaPeriodoProm> {
	/**
	 * Listar curso nota periodo prom.
	 *
	 * @param filtro el curso nota periodo prom
	 * @return the list
	 * @throws Exception the exception
	 */
	List<CursoNotaPeriodoProm> listar(BaseSearch filtro);
	
	/**
	 * contar lista curso nota periodo prom.
	 *
	 * @param filtro el curso nota periodo prom
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);
	/**
	 * Generar id cursoNotaPeriodoProm.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();
}