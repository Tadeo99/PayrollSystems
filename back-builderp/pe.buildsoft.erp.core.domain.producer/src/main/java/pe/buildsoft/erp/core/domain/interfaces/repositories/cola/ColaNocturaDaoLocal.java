package pe.buildsoft.erp.core.domain.interfaces.repositories.cola;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.cola.ColaNoctura;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.state.EstadoSolicitudEjecucionEstate;

/**
 * La Class ColaNocturaDaoLocal.
 * <ul>
 * <li>Copyright 2015 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 2.1, Thu Dec 21 12:16:30 COT 2017
 * @since BuildErp 1.0
 */
@Local
public interface ColaNocturaDaoLocal extends GenericDAOLocal<String, ColaNoctura> {

	String actualizarEstadoColaNoctura(String idColaNocturna,
			EstadoSolicitudEjecucionEstate estadoSolicitudEjecucionEstate);

	/**
	 * Listar cola noctura.
	 *
	 * @param filtro el cola noctura
	 * @return the list
	 * @throws Exception the exception
	 */
	List<ColaNoctura> listar(BaseSearch filtro);

	/**
	 * contar lista cola noctura.
	 *
	 * @param filtro el cola noctura
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);

	/**
	 * Generar id colaNoctura.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();
}