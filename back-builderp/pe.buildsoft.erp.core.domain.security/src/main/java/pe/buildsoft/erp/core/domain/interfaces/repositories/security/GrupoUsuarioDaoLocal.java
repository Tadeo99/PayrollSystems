package pe.buildsoft.erp.core.domain.interfaces.repositories.security;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.security.GrupoUsuario;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class GrupoUsuarioDaoLocal.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue Apr 18 11:25:06 COT 2017
 * @since SIAA-CORE 2.1
 */
@Local
public interface GrupoUsuarioDaoLocal  extends GenericDAOLocal<Long,GrupoUsuario> {
	/**
	 * Listar grupo usuario.
	 *
	 * @param grupoUsuario el grupo usuario
	 * @return the list
	 * @ the exception
	 */
	List<GrupoUsuario> listar(BaseSearch filtro) ;
	
	/**
	 * contar lista grupo usuario.
	 *
	 * @param grupoUsuario el grupo usuario
	 * @return the list
	 * @ the exception
	 */
	int contar(BaseSearch filtro);
	/**
	 * Generar id grupoUsuario.
	 *
	 * @return the Long
	 * @ the exception
	 */
	Long generarId() ;
}