package pe.buildsoft.erp.core.domain.interfaces.repositories.common;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.common.Anhio;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.state.EstadoGeneralState;

/**
 * La Class AnhioDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:55 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface AnhioDaoLocal extends GenericDAOLocal<Long, Anhio> {
	/**
	 * Listar anhio.
	 *
	 * @param filtro el anhio
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Anhio> listar(BaseSearch filtro);

	/**
	 * contar lista anhio.
	 *
	 * @param filtro el anhio
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);

	/**
	 * Generar id anhio.
	 *
	 * @return the Long
	 * @throws Exception the exception
	 */
	Long generarId();

	Anhio obtenerAnhioyEstado(EstadoGeneralState estadoAnhoState);
}