package pe.buildsoft.erp.core.application.interfaces.aas;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.application.entidades.aas.vo.ConfiguracionMenuVO;
import pe.buildsoft.erp.core.application.entidades.security.EntidadDTO;
import pe.buildsoft.erp.core.application.entidades.security.PropertiesDTO;
import pe.buildsoft.erp.core.application.entidades.security.UsuarioDTO;
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
public interface AasAppServiceLocal {
	
	EntidadDTO finByIdEntidad(EntidadDTO entidadDTO);
	
	/**
	 * Validar login.
	 *
	 * @param userName el user name
	 * @param userPassword el user password
	 * @return the persona
	 * @ the exception
	 */
	UsuarioDTO validarLogin(String userName,String userPassword) ;
	
	/**
	 * Obtener configuracion menu.
	 *
	 * @param menu el menu
	 * @return the list
	 * @ the exception
	 */
	List<ConfiguracionMenuVO> obtenerConfiguracionMenu(Long idMenu) ;
	
	/**
	 * Obtener properties lenguaje all map.
	 *
	 * @return the map
	 */
	Map<String,Map<String,String>> obtenerPropertiesLenguajeAllMap();
	
	/**
	 * Obtener properties lenguaje.
	 *
	 * @param filtro el properties filtro
	 * @return the list
	 * @ the exception
	 */
	List<PropertiesDTO> obtenerPropertiesLenguaje(BaseSearch filtro) ;
	
}