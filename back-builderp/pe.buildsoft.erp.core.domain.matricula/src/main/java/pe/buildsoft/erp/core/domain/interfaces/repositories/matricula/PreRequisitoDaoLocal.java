package pe.buildsoft.erp.core.domain.interfaces.repositories.matricula;

import java.util.List;

import pe.buildsoft.erp.core.domain.entidades.matricula.PreRequisito;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class PreRequisitoDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:54 COT 2021
 * @since BUILDERP-CORE 2.1
 */
public interface PreRequisitoDaoLocal extends GenericDAOLocal<String, PreRequisito> {
	/**
	 * Listar pre requisito.
	 *
	 * @param filtro el pre requisito
	 * @return the list
	 * @throws Exception the exception
	 */
	List<PreRequisito> listar(BaseSearch filtro);

	/**
	 * contar lista pre requisito.
	 *
	 * @param filtro el pre requisito
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);

	/**
	 * Generar id preRequisito.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();
}