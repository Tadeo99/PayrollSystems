package pe.buildsoft.erp.core.application.interfaces.security;

import java.util.List;

import pe.buildsoft.erp.core.application.entidades.security.ConfiguracionMenuDTO;
import pe.buildsoft.erp.core.application.entidades.security.EntidadDTO;
import pe.buildsoft.erp.core.application.entidades.security.GrupoUsuarioDTO;
import pe.buildsoft.erp.core.application.entidades.security.MenuDTO;
import pe.buildsoft.erp.core.application.entidades.security.PrivilegioDTO;
import pe.buildsoft.erp.core.application.entidades.security.PrivilegioMenuDTO;
import pe.buildsoft.erp.core.application.entidades.security.PropertiesDTO;
import pe.buildsoft.erp.core.application.entidades.security.PropertiesLenguajeDTO;
import pe.buildsoft.erp.core.application.entidades.security.SistemaDTO;
import pe.buildsoft.erp.core.application.entidades.security.TipoUsuarioDTO;
import pe.buildsoft.erp.core.application.entidades.security.UsuarioDTO;
import pe.buildsoft.erp.core.application.entidades.security.UsuarioEntidadDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.SelectItemVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;

/**
 * La Class SeguridadServiceLocal.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue Apr 18 13:43:23 COT 2017
 * @since SIAA-CORE 2.1
 */
public interface SeguridadAppServiceLocal {

	List<SelectItemVO> listarSelectItem(String groupName);

	UsuarioDTO obtenerUsuarioByUserName(String userName);

	UsuarioDTO obtenerUsuarioByCodigoExterno(String codigoExterno);

	/**
	 * Controlador accion entidad.
	 *
	 * @param entidad    el entidad
	 * @param accionType el accion type
	 * @return the entidad @ the exception
	 */
	EntidadDTO controladorAccionEntidad(EntidadDTO obj, AccionType accionType);

	/**
	 * Listar entidad.
	 *
	 * @param entidad el entidad
	 * @return the list @ the exception
	 */
	List<EntidadDTO> listarEntidad(BaseSearch filtro);

	/**
	 * contar lista entidad.
	 *
	 * @param entidad el entidad
	 * @return the list @ the exception
	 */
	int contarListarEntidad(BaseSearch filtro);

	/**
	 * Controlador accion tipo usuario.
	 *
	 * @param tipoUsuario el tipo usuario
	 * @param accionType  el accion type
	 * @return the tipo usuario @ the exception
	 */
	TipoUsuarioDTO controladorAccionTipoUsuario(TipoUsuarioDTO obj, AccionType accionType);

	/**
	 * Listar tipo usuario.
	 *
	 * @param tipoUsuario el tipo usuario
	 * @return the list @ the exception
	 */
	List<TipoUsuarioDTO> listarTipoUsuario(BaseSearch filtro);

	/**
	 * contar lista tipo usuario.
	 *
	 * @param tipoUsuario el tipo usuario
	 * @return the list @ the exception
	 */
	int contarListarTipoUsuario(BaseSearch filtro);

	/**
	 * Controlador accion usuario.
	 *
	 * @param usuario    el usuario
	 * @param accionType el accion type
	 * @return the usuario @ the exception
	 */
	UsuarioDTO controladorAccionUsuario(UsuarioDTO obj, AccionType accionType);

	UsuarioDTO integracionUsuario(UsuarioDTO obj);

	/**
	 * Listar usuario.
	 *
	 * @param usuario el usuario
	 * @return the list @ the exception
	 */
	List<UsuarioDTO> listarUsuario(BaseSearch filtro);

	/**
	 * contar lista usuario.
	 *
	 * @param usuario el usuario
	 * @return the list @ the exception
	 */
	int contarListarUsuario(BaseSearch filtro);

	/**
	 * Obtener properties lenguaje.
	 *
	 * @param propertiesFiltro el properties filtro
	 * @return the list @ the exception
	 */
	List<PropertiesDTO> obtenerPropertiesLenguaje(BaseSearch filtro);

	/**
	 * Listar grupo usuario usuario.
	 *
	 * @param grupoUsuarioUsuario el grupo usuario usuario
	 * @return the list @ the exception
	 */
	List<GrupoUsuarioDTO> obtenerGrupoUsuarioCheck(BaseSearch filtro, String idUsuario);

	/**
	 * Controlador accion grupo usuario.
	 *
	 * @param grupoUsuario el grupo usuario
	 * @param accionType   el accion type
	 * @return the grupo usuario @ the exception
	 */
	GrupoUsuarioDTO controladorAccionGrupoUsuario(GrupoUsuarioDTO obj, AccionType accionType);

	/**
	 * Listar grupo usuario.
	 *
	 * @param grupoUsuario el grupo usuario
	 * @return the list @ the exception
	 */
	List<GrupoUsuarioDTO> listarGrupoUsuario(BaseSearch filtro);

	/**
	 * contar lista grupo usuario.
	 *
	 * @param grupoUsuario el grupo usuario
	 * @return the list @ the exception
	 */
	int contarListarGrupoUsuario(BaseSearch filtro);

	/**
	 * Controlador accion privilegio menu.
	 *
	 * @param privilegioMenu el privilegio menu
	 * @param accionType     el accion type
	 * @return the privilegio menu @ the exception
	 */
	PrivilegioMenuDTO controladorAccionPrivilegioMenu(PrivilegioMenuDTO obj, AccionType accionType);

	/**
	 * Listar privilegio menu.
	 *
	 * @param privilegioMenu el privilegio menu
	 * @return the list @ the exception
	 */
	List<PrivilegioMenuDTO> listarPrivilegioMenu(BaseSearch filtro);

	/**
	 * contar lista privilegio menu.
	 *
	 * @param privilegioMenu el privilegio menu
	 * @return the list @ the exception
	 */
	int contarListarPrivilegioMenu(BaseSearch filtro);

	/**
	 * Controlador accion properties lenguaje.
	 *
	 * @param propertiesLenguaje el properties lenguaje
	 * @param accionType         el accion type
	 * @return the properties lenguaje @ the exception
	 */
	PropertiesLenguajeDTO controladorAccionPropertiesLenguaje(PropertiesLenguajeDTO obj, AccionType accionType);

	/**
	 * Listar properties lenguaje.
	 *
	 * @param propertiesLenguaje el properties lenguaje
	 * @return the list @ the exception
	 */
	List<PropertiesLenguajeDTO> listarPropertiesLenguaje(BaseSearch filtro);

	/**
	 * contar lista properties lenguaje.
	 *
	 * @param propertiesLenguaje el properties lenguaje
	 * @return the list @ the exception
	 */
	int contarListarPropertiesLenguaje(BaseSearch filtro);

	/**
	 * Controlador accion menu personalizado.
	 *
	 * @param menuPersonalizado el menu personalizado
	 * @param accionType        el accion type
	 * @return the menu personalizado @ the exception
	 */
	void asociarMenuPersonalizadoByUsuario(List<MenuDTO> filtro, String userName);

	List<MenuDTO> obtenerMenuCheck(BaseSearch menu, String idUsuario);

	/**
	 * Controlador accion privilegio personalizado.
	 *
	 * @param privilegioPersonalizado el privilegio personalizado
	 * @param accionType              el accion type
	 * @return the privilegio personalizado @ the exception
	 */
	void asociarPrivilegioPersonalizadoByUsuario(List<PrivilegioDTO> filtro, String userName);

	/**
	 * Listar privilegio personalizado.
	 *
	 * @param privilegioPersonalizado el privilegio personalizado
	 * @return the list @ the exception
	 */
	List<PrivilegioMenuDTO> obtenerPrivilegioMenuCheck(BaseSearch filtro, String idUsuario);

	/**
	 * Controlador accion properties.
	 *
	 * @param properties el properties
	 * @param accionType el accion type
	 * @return the properties @ the exception
	 */
	PropertiesDTO controladorAccionProperties(PropertiesDTO obj, AccionType accionType);

	/**
	 * Listar properties.
	 *
	 * @param properties el properties
	 * @return the list @ the exception
	 */
	List<PropertiesDTO> listarProperties(BaseSearch filtro);

	/**
	 * contar lista properties.
	 *
	 * @param properties el properties
	 * @return the list @ the exception
	 */
	int contarListarProperties(BaseSearch filtro);

	/**
	 * Controlador accion privilegio.
	 *
	 * @param privilegio el privilegio
	 * @param accionType el accion type
	 * @return the privilegio @ the exception
	 */
	PrivilegioDTO controladorAccionPrivilegio(PrivilegioDTO obj, AccionType accionType);

	/**
	 * Listar privilegio.
	 *
	 * @param privilegio el privilegio
	 * @return the list @ the exception
	 */
	List<PrivilegioDTO> listarPrivilegio(BaseSearch filtro);

	/**
	 * contar lista privilegio.
	 *
	 * @param privilegio el privilegio
	 * @return the list @ the exception
	 */
	int contarListarPrivilegio(BaseSearch filtro);

	/**
	 * Controlador accion menu.
	 *
	 * @param menu       el menu
	 * @param accionType el accion type
	 * @return the menu @ the exception
	 */
	MenuDTO controladorAccionMenu(MenuDTO obj, AccionType accionType);

	/**
	 * Listar menu.
	 *
	 * @param menu el menu
	 * @return the list @ the exception
	 */
	List<MenuDTO> listarMenu(BaseSearch filtro);

	/**
	 * contar lista menu.
	 *
	 * @param menu el menu
	 * @return the list @ the exception
	 */
	int contarListarMenu(BaseSearch filtro);

	/**
	 * Controlador accion configuracion menu.
	 *
	 * @param configuracionMenu el configuracion menu
	 * @param accionType        el accion type
	 * @return the configuracion menu @ the exception
	 */
	ConfiguracionMenuDTO controladorAccionConfiguracionMenu(ConfiguracionMenuDTO obj, AccionType accionType);

	/**
	 * Listar configuracion menu.
	 *
	 * @param configuracionMenu el configuracion menu
	 * @return the list @ the exception
	 */
	List<ConfiguracionMenuDTO> listarConfiguracionMenu(BaseSearch filtro);

	/**
	 * contar lista configuracion menu.
	 *
	 * @param configuracionMenu el configuracion menu
	 * @return the list @ the exception
	 */
	int contarListarConfiguracionMenu(BaseSearch filtro);

	/**
	 * Controlador accion sistema.
	 *
	 * @param sistema    el sistema
	 * @param accionType el accion type
	 * @return the sistema @ the exception
	 */
	SistemaDTO controladorAccionSistema(SistemaDTO obj, AccionType accionType);

	/**
	 * Listar sistema.
	 *
	 * @param sistema el sistema
	 * @return the list @ the exception
	 */
	List<SistemaDTO> listarSistema(BaseSearch filtro);

	/**
	 * contar lista sistema.
	 *
	 * @param sistema el sistema
	 * @return the list @ the exception
	 */
	int contarListarSistema(BaseSearch filtro);

	/**
	 * Controlador accion privilegio grupo usuario.
	 *
	 * @param privilegioGrupoUsuario el privilegio grupo usuario
	 * @param accionType             el accion type
	 * @return the privilegio grupo usuario @ the exception
	 */
	void asociarPrivilegioByGrupoUsuario(List<PrivilegioDTO> filtro, String userName);

	/**
	 * Listar privilegio grupo usuario.
	 *
	 * @param privilegioGrupoUsuario el privilegio grupo usuario
	 * @return the list @ the exception
	 */
	List<PrivilegioMenuDTO> obtenerPrivilegioMenuCheck(BaseSearch filtro, Long idGrupoUsuario);

	/**
	 * contar lista privilegio grupo usuario.
	 *
	 * @param privilegioGrupoUsuario el privilegio grupo usuario
	 * @return the list @ the exception
	 */
	int contarListarPrivilegioGrupoUsuario(BaseSearch filtro);

	/**
	 * Listar usuario entidad.
	 *
	 * @param usuarioEntidad el usuario entidad
	 * @return the list @ the exception
	 */
	List<UsuarioEntidadDTO> listarUsuarioEntidad(BaseSearch filtro);

}