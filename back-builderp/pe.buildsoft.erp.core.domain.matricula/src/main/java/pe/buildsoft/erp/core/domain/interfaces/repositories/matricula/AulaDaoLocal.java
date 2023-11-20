package pe.buildsoft.erp.core.domain.interfaces.repositories.matricula;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.matricula.Aula;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class AulaDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:55 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface AulaDaoLocal extends GenericDAOLocal<Long, Aula> {
	/**
	 * Listar aula.
	 *
	 * @param filtro el aula
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Aula> listar(BaseSearch filtro);

	/**
	 * contar lista aula.
	 *
	 * @param filtro el aula
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);

	/**
	 * Generar id aula.
	 *
	 * @return the Long
	 * @throws Exception the exception
	 */
	Long generarIdAula();
}