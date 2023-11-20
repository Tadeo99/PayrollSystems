package pe.buildsoft.erp.core.domain.interfaces.repositories.security;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.security.TipoUsuario;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class TipoUsuarioDaoLocal.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Dec 14 00:27:45 COT 2017
 * @since SIAA-CORE 2.1
 */
@Local
public interface TipoUsuarioDaoLocal  extends GenericDAOLocal<Long,TipoUsuario> {
	/**
	 * Listar tipo usuario.
	 *
	 * @param tipoUsuario el tipo usuario
	 * @return the list
	 * @ the exception
	 */
	List<TipoUsuario> listar(BaseSearch filtro) ;
	
	/**
	 * contar lista tipo usuario.
	 *
	 * @param tipoUsuario el tipo usuario
	 * @return the list
	 * @ the exception
	 */
	int contar(BaseSearch filtro);
	/**
	 * Generar id tipoUsuario.
	 *
	 * @return the Long
	 * @ the exception
	 */
	Long generarId() ;
}