package pe.buildsoft.erp.core.domain.interfaces.repositories.admision;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.admision.Seccion;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class SeccionDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Wed Apr 21 12:29:28 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface SeccionDaoLocal  extends GenericDAOLocal<Long,Seccion> {
	/**
	 * Listar seccion.
	 *
	 * @param filtro el seccionDTO
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Seccion> listar(BaseSearch filtro);
	
	/**
	 * contar lista seccion.
	 *
	 * @param filtro el seccion
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);
	/**
	 * Generar id seccion.
	 *
	 * @return the Long
	 * @throws Exception the exception
	 */
	Long generarId();
}