package pe.buildsoft.erp.core.domain.interfaces.repositories.admision;

import java.util.List;

import jakarta.ejb.Local;
import pe.buildsoft.erp.core.domain.entidades.admision.Sede;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class SedeDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Wed Oct 21 20:17:20 COT 2020
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface SedeDaoLocal extends GenericDAOLocal<String, Sede> {
	/**
	 * Listar sede.
	 *
	 * @param filtro el sedeDTO
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Sede> listar(BaseSearch filtro);

	/**
	 * contar lista sede.
	 *
	 * @param filtro el sede
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);

	/**
	 * Generar id sede.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();
}