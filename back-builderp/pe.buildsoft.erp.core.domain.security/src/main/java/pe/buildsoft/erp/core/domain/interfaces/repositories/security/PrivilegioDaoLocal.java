package pe.buildsoft.erp.core.domain.interfaces.repositories.security;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.security.Privilegio;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class PrivilegioDaoLocal.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue Apr 18 11:25:11 COT 2017
 * @since SIAA-CORE 2.1
 */
@Local
public interface PrivilegioDaoLocal  extends GenericDAOLocal<Long,Privilegio> {
	
	/**
	 * Obtener privilegio by usuario.
	 *
	 * @param usuario el usuario
	 * @return the list
	 * @ the exception
	 */
	List<Privilegio> obtenerPrivilegioByUsuario(String idUsuario) ;
	/**
	 * Listar privilegio.
	 *
	 * @param privilegio el privilegio
	 * @return the list
	 * @ the exception
	 */
	List<Privilegio> listar(BaseSearch filtro) ;
	
	/**
	 * contar lista privilegio.
	 *
	 * @param privilegio el privilegio
	 * @return the list
	 * @ the exception
	 */
	int contar(BaseSearch filtro);
	/**
	 * Generar id privilegio.
	 *
	 * @return the Long
	 * @ the exception
	 */
	Long generarId() ;
}