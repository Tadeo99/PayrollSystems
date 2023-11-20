package pe.buildsoft.erp.core.domain.interfaces.repositories.matricula;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.matricula.Unidad;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class UnidadDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:55 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface UnidadDaoLocal extends GenericDAOLocal<Long, Unidad> {
	/**
	 * Listar unidad.
	 *
	 * @param filtro el unidad
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Unidad> listar(BaseSearch filtro);

	/**
	 * contar lista unidad.
	 *
	 * @param filtro el unidad
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);

	/**
	 * Generar id unidad.
	 *
	 * @return the Long
	 * @throws Exception the exception
	 */
	Long generarId();

	Unidad find(Unidad filtro);
}