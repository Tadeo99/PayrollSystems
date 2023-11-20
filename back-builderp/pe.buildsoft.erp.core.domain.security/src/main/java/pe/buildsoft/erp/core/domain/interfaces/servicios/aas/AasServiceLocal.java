package pe.buildsoft.erp.core.domain.interfaces.servicios.aas;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Local;
import pe.buildsoft.erp.core.domain.entidades.security.ConfiguracionMenu;
import pe.buildsoft.erp.core.domain.entidades.security.Entidad;
import pe.buildsoft.erp.core.domain.entidades.security.Menu;
import pe.buildsoft.erp.core.domain.entidades.security.Properties;
import pe.buildsoft.erp.core.domain.entidades.security.PropertiesLenguaje;
import pe.buildsoft.erp.core.domain.entidades.security.Usuario;
import pe.buildsoft.erp.core.domain.entidades.security.UsuarioEntidad;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class AasServiceLocal.
 * <ul>
 * <li>Copyright 2019 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue 08/01/2019
 * @since SIAA-CORE 2.1
 */
@Local
public interface AasServiceLocal /*extends  UserDetailsService*/ {
	
	Entidad finByIdEntidad(String idEntidad);
	
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
	 * Obtener properties lenguaje all map.
	 *
	 * @return the map
	 */
	List<PropertiesLenguaje> obtenerPropertiesLenguajeAllMap();
	
	/**
	 * Obtener properties lenguaje.
	 *
	 * @param filtro el properties filtro
	 * @return the list
	 * @ the exception
	 */
	List<Properties> obtenerPropertiesLenguaje(BaseSearch filtro) ;
	
	Map<String,Boolean> obtenerPrivilegiosUsuario(String idUsuario);
	
	List<UsuarioEntidad> listarUsuarioEntidad(BaseSearch filtro);
	
	List<ConfiguracionMenu>  obtenerConfiguracionMenu(Long idMenu);
	
	List<Menu> obtenerMenuUsuario(String idUsuario);
}