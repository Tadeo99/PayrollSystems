package pe.buildsoft.erp.core.domain.interfaces.repositories.security;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.security.Usuario;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class UsuarioDaoLocal.
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
public interface UsuarioDaoLocal  extends GenericDAOLocal<String,Usuario> {
	
	Usuario obtenerUsuarioByCodigoExterno(String codigoExterno) ;
	
	Usuario obtenerUsuarioByUserName(String userName) ;
	/**
	 * Validar login.
	 *
	 * @param userName el user name
	 * @param userPassword el user password
	 * @return the persona
	 * @ the exception
	 */
	Usuario validarLogin(String userName,String userPassword) ;
	/**
	 * Listar usuario.
	 *
	 * @param usuario el usuario
	 * @return the list
	 * @ the exception
	 */
	List<Usuario> listar(BaseSearch filtro) ;
	
	/**
	 * contar lista usuario.
	 *
	 * @param usuario el usuario
	 * @return the list
	 * @ the exception
	 */
	int contar(BaseSearch filtro);
	/**
	 * Generar id usuario.
	 *
	 * @return the String
	 * @ the exception
	 */
	String generarId() ;
}