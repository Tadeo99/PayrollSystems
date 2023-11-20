package pe.buildsoft.erp.core.domain.interfaces.repositories.security;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.security.PrivilegioGrupoUsuario;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class PrivilegioGrupoUsuarioDaoLocal.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue Apr 18 11:25:17 COT 2017
 * @since SIAA-CORE 2.1
 */
@Local
public interface PrivilegioGrupoUsuarioDaoLocal  extends GenericDAOLocal<String,PrivilegioGrupoUsuario> {
	/**
	 * Listar privilegio grupo usuario.
	 *
	 * @param privilegioGrupoUsuario el privilegio grupo usuario
	 * @return the list
	 * @ the exception
	 */
	List<PrivilegioGrupoUsuario> listar(BaseSearch filtro) ;
	
	/**
	 * contar lista privilegio grupo usuario.
	 *
	 * @param privilegioGrupoUsuario el privilegio grupo usuario
	 * @return the list
	 * @ the exception
	 */
	int contar(BaseSearch filtro);
	/**
	 * Generar id privilegioGrupoUsuario.
	 *
	 * @return the Long
	 * @ the exception
	 */
	String generarId() ;
}