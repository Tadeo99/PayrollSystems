package pe.buildsoft.erp.core.domain.interfaces.repositories.admision;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.admision.Grado;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class GradoDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Wed Oct 21 20:17:19 COT 2020
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface GradoDaoLocal extends GenericDAOLocal<Long, Grado> {
	/**
	 * Listar grado.
	 *
	 * @param filtro el gradoDTO
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Grado> listar(BaseSearch filtro);

	/**
	 * contar lista grado.
	 *
	 * @param filtro el grado
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);

	/**
	 * Generar id grado.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	Long generarId();
}