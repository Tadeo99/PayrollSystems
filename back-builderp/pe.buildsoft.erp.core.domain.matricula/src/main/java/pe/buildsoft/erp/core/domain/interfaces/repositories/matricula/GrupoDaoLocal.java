package pe.buildsoft.erp.core.domain.interfaces.repositories.matricula;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.matricula.Grupo;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class GrupoDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:55 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface GrupoDaoLocal extends GenericDAOLocal<Long, Grupo> {
	/**
	 * Listar grupo.
	 *
	 * @param filtro el grupo
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Grupo> listar(BaseSearch filtro);

	/**
	 * contar lista grupo.
	 *
	 * @param filtro el grupo
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);

	/**
	 * Generar id grupo.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	Long generarId();

	Grupo find(Grupo grupo);
}