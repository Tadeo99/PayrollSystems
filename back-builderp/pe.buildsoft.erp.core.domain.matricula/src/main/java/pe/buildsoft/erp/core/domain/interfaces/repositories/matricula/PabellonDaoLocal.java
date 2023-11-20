package pe.buildsoft.erp.core.domain.interfaces.repositories.matricula;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.matricula.Pabellon;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class PabellonDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:54 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface PabellonDaoLocal extends GenericDAOLocal<Long, Pabellon> {
	/**
	 * Listar pabellon.
	 *
	 * @param filtro el pabellon
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Pabellon> listar(BaseSearch filtro);

	/**
	 * contar lista pabellon.
	 *
	 * @param filtro el pabellon
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);

	/**
	 * Generar id pabellon.
	 *
	 * @return the Long
	 * @throws Exception the exception
	 */
	Long generarId();

	Pabellon find(Pabellon filtro);
}