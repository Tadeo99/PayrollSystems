package pe.buildsoft.erp.core.domain.interfaces.repositories.security;

import java.util.List;

import jakarta.ejb.Local;
import pe.buildsoft.erp.core.domain.entidades.security.GrupoUsuarioMenu;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class GrupoUsuarioMenuDaoLocal.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue Apr 18 11:25:21 COT 2017
 * @since SIAA-CORE 2.1
 */
@Local
public interface GrupoUsuarioMenuDaoLocal extends GenericDAOLocal<String, GrupoUsuarioMenu> {
	/**
	 * Listar grupo usuario menu.
	 *
	 * @param grupoUsuarioMenu el grupo usuario menu
	 * @return the list @ the exception
	 */
	List<GrupoUsuarioMenu> listar(BaseSearch filtro);

	/**
	 * contar lista grupo usuario menu.
	 *
	 * @param grupoUsuarioMenu el grupo usuario menu
	 * @return the list @ the exception
	 */
	int contar(BaseSearch filtro);

	/**
	 * Generar id grupoUsuarioMenu.
	 *
	 * @return the Long @ the exception
	 */
	String generarId();

	boolean updateEstadoInactivo(Long idGrupoUsuario);
}