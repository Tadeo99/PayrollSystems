package pe.buildsoft.erp.core.domain.interfaces.repositories.security;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.security.GrupoUsuarioUsuario;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class GrupoUsuarioUsuarioDaoLocal.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue Apr 18 11:25:04 COT 2017
 * @since SIAA-CORE 2.1
 */
@Local
public interface GrupoUsuarioUsuarioDaoLocal  extends GenericDAOLocal<String,GrupoUsuarioUsuario> {
	/**
	 * Listar grupo usuario usuario.
	 *
	 * @param grupoUsuarioUsuario el grupo usuario usuario
	 * @return the list
	 * @ the exception
	 */
	List<GrupoUsuarioUsuario> listar(BaseSearch filtro) ;
	
	/**
	 * contar lista grupo usuario usuario.
	 *
	 * @param grupoUsuarioUsuario el grupo usuario usuario
	 * @return the list
	 * @ the exception
	 */
	int contar(BaseSearch filtro);
	/**
	 * Generar id grupoUsuarioUsuario.
	 *
	 * @return the Long
	 * @ the exception
	 */
	String generarId() ;
	
	boolean updateEstadoInactivo(String idUsuario) ;
}