package pe.buildsoft.erp.core.domain.interfaces.repositories.cola;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.cola.Cola;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class ColaDaoLocal.
 * <ul>
 * <li>Copyright 2015 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 2.1, Thu Dec 21 12:16:30 COT 2017
 * @since BuildErp 1.0
 */
@Local
public interface ColaDaoLocal extends GenericDAOLocal<Long, Cola> {
	/**
	 * Listar cola.
	 *
	 * @param filtro el colaDTO
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Cola> listar(BaseSearch filtro);

	/**
	 * contar lista cola.
	 *
	 * @param filtro el cola
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);

	/**
	 * Generar id cola.
	 *
	 * @return the Long
	 * @throws Exception the exception
	 */
	Long generarId();
}