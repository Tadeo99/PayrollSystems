package pe.buildsoft.erp.core.domain.interfaces.repositories.pago;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.pago.Clasificacion;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class ClasificacionDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 21:45:29 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface ClasificacionDaoLocal extends GenericDAOLocal<Long, Clasificacion> {
	/**
	 * Listar clasificacion.
	 *
	 * @param filtro el clasificacion
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Clasificacion> listar(BaseSearch filtro);

	/**
	 * contar lista clasificacion.
	 *
	 * @param filtro el clasificacion
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);

	/**
	 * Generar id clasificacion.
	 *
	 * @return the Long
	 * @throws Exception the exception
	 */
	Long generarId();

	Clasificacion find(Clasificacion filtro);
}