package pe.buildsoft.erp.core.domain.interfaces.repositories.security;

import java.util.List;

import jakarta.ejb.Local;
import pe.buildsoft.erp.core.domain.entidades.security.UsuarioEntidad;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class UsuarioEntidadDaoLocal.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue Apr 18 11:25:18 COT 2017
 * @since SIAA-CORE 2.1
 */
@Local
public interface UsuarioEntidadDaoLocal extends GenericDAOLocal<String, UsuarioEntidad> {
	/**
	 * Listar usuario entidad.
	 *
	 * @param usuarioEntidad el usuario entidad
	 * @return the list @ the exception
	 */
	List<UsuarioEntidad> listar(BaseSearch filtro);

	/**
	 * contar lista usuario entidad.
	 *
	 * @param usuarioEntidad el usuario entidad
	 * @return the list @ the exception
	 */
	int contar(BaseSearch filtro);

	/**
	 * Generar id usuarioEntidad.
	 *
	 * @return the Long @ the exception
	 */
	String generarId();

	boolean updateEstadoInactivo(String idUsuario);
}