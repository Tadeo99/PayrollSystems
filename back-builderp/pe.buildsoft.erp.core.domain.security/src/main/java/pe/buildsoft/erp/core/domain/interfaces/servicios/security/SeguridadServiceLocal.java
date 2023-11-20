package pe.buildsoft.erp.core.domain.interfaces.servicios.security;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Local;
import pe.buildsoft.erp.core.domain.entidades.security.ConfiguracionMenu;
import pe.buildsoft.erp.core.domain.entidades.security.Entidad;
import pe.buildsoft.erp.core.domain.entidades.security.GrupoUsuario;
import pe.buildsoft.erp.core.domain.entidades.security.GrupoUsuarioMenu;
import pe.buildsoft.erp.core.domain.entidades.security.GrupoUsuarioUsuario;
import pe.buildsoft.erp.core.domain.entidades.security.Menu;
import pe.buildsoft.erp.core.domain.entidades.security.MenuPersonalizado;
import pe.buildsoft.erp.core.domain.entidades.security.Privilegio;
import pe.buildsoft.erp.core.domain.entidades.security.PrivilegioGrupoUsuario;
import pe.buildsoft.erp.core.domain.entidades.security.PrivilegioMenu;
import pe.buildsoft.erp.core.domain.entidades.security.PrivilegioPersonalizado;
import pe.buildsoft.erp.core.domain.entidades.security.Properties;
import pe.buildsoft.erp.core.domain.entidades.security.PropertiesLenguaje;
import pe.buildsoft.erp.core.domain.entidades.security.Sistema;
import pe.buildsoft.erp.core.domain.entidades.security.TipoUsuario;
import pe.buildsoft.erp.core.domain.entidades.security.Usuario;
import pe.buildsoft.erp.core.domain.entidades.security.UsuarioEntidad;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
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
@Local
public interface SeguridadServiceLocal {

	Usuario obtenerUsuarioByUserName(String userName);

	Usuario obtenerUsuarioByCodigoExterno(String codigoExterno);

	/**
	 * Controlador accion entidad.
	 *
	 * @param entidad    el entidad
	 * @param accionType el accion type
	 * @return the entidad @ the exception
	 */
	Entidad controladorAccionEntidad(Entidad obj, AccionType accionType);

	/**
	 * Listar entidad.
	 *
	 * @param entidad el entidad
	 * @return the list @ the exception
	 */
	List<Entidad> listarEntidad(BaseSearch filtro);

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
	TipoUsuario controladorAccionTipoUsuario(TipoUsuario obj, AccionType accionType);

	/**
	 * Listar tipo usuario.
	 *
	 * @param tipoUsuario el tipo usuario
	 * @return the list @ the exception
	 */
	List<TipoUsuario> listarTipoUsuario(BaseSearch filtro);

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
	Usuario controladorAccionUsuario(Usuario obj, AccionType accionType);

	Usuario integracionUsuario(Usuario obj, AccionType accionType);

	/**
	 * Listar usuario.
	 *
	 * @param usuario el usuario
	 * @return the list @ the exception
	 */
	List<Usuario> listarUsuario(BaseSearch filtro);

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
	List<Properties> obtenerPropertiesLenguaje(BaseSearch filtro);

	/**
	 * Listar grupo usuario usuario.
	 *
	 * @param grupoUsuarioUsuario el grupo usuario usuario
	 * @return the list @ the exception
	 */
	List<GrupoUsuarioUsuario> listarGrupoUsuarioUsuario(BaseSearch filtro);

	Map<Long, GrupoUsuarioUsuario> listarGrupoUsuarioUsuarioMap(String idUsuario, String estado);

	/**
	 * Controlador accion grupo usuario.
	 *
	 * @param grupoUsuario el grupo usuario
	 * @param accionType   el accion type
	 * @return the grupo usuario @ the exception
	 */
	GrupoUsuario controladorAccionGrupoUsuario(GrupoUsuario obj, AccionType accionType);

	/**
	 * Listar grupo usuario.
	 *
	 * @param grupoUsuario el grupo usuario
	 * @return the list @ the exception
	 */
	List<GrupoUsuario> listarGrupoUsuario(BaseSearch filtro);

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
	PrivilegioMenu controladorAccionPrivilegioMenu(PrivilegioMenu obj, AccionType accionType);

	/**
	 * Listar privilegio menu.
	 *
	 * @param privilegioMenu el privilegio menu
	 * @return the list @ the exception
	 */
	List<PrivilegioMenu> listarPrivilegioMenu(BaseSearch filtro);

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
	PropertiesLenguaje controladorAccionPropertiesLenguaje(PropertiesLenguaje obj, AccionType accionType);

	/**
	 * Listar properties lenguaje.
	 *
	 * @param propertiesLenguaje el properties lenguaje
	 * @return the list @ the exception
	 */
	List<PropertiesLenguaje> listarPropertiesLenguaje(BaseSearch filtro);

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
	void asociarMenuPersonalizadoByUsuario(List<Menu> filtro, String userName);

	/**
	 * Listar menu personalizado.
	 *
	 * @param menuPersonalizado el menu personalizado
	 * @return the list @ the exception
	 */
	List<MenuPersonalizado> listarMenuPersonalizado(BaseSearch filtro);

	Map<Long, MenuPersonalizado> listarMenuPersonalizadoMap(String idUsuario, String estado);

	/**
	 * Controlador accion privilegio personalizado.
	 *
	 * @param privilegioPersonalizado el privilegio personalizado
	 * @param accionType              el accion type
	 * @return the privilegio personalizado @ the exception
	 */
	void asociarPrivilegioPersonalizadoByUsuario(List<Privilegio> filtro, String userName);

	/**
	 * Listar privilegio personalizado.
	 *
	 * @param privilegioPersonalizado el privilegio personalizado
	 * @return the list @ the exception
	 */
	List<PrivilegioPersonalizado> listarPrivilegioPersonalizado(BaseSearch filtro);

	Map<Long, PrivilegioPersonalizado> listarPrivilegioPersonalizadoMap(String idUsuario, String estado);

	/**
	 * Controlador accion properties.
	 *
	 * @param properties el properties
	 * @param accionType el accion type
	 * @return the properties @ the exception
	 */
	Properties controladorAccionProperties(Properties obj, AccionType accionType);

	/**
	 * Listar properties.
	 *
	 * @param properties el properties
	 * @return the list @ the exception
	 */
	List<Properties> listarProperties(BaseSearch filtro);

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
	Privilegio controladorAccionPrivilegio(Privilegio obj, AccionType accionType);

	/**
	 * Listar privilegio.
	 *
	 * @param privilegio el privilegio
	 * @return the list @ the exception
	 */
	List<Privilegio> listarPrivilegio(BaseSearch filtro);

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
	Menu controladorAccionMenu(Menu obj, AccionType accionType);

	/**
	 * Listar menu.
	 *
	 * @param menu el menu
	 * @return the list @ the exception
	 */
	List<Menu> listarMenu(BaseSearch filtro);

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
	ConfiguracionMenu controladorAccionConfiguracionMenu(ConfiguracionMenu obj, AccionType accionType);

	/**
	 * Listar configuracion menu.
	 *
	 * @param configuracionMenu el configuracion menu
	 * @return the list @ the exception
	 */
	List<ConfiguracionMenu> listarConfiguracionMenu(BaseSearch filtro);

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
	Sistema controladorAccionSistema(Sistema obj, AccionType accionType);

	/**
	 * Listar sistema.
	 *
	 * @param sistema el sistema
	 * @return the list @ the exception
	 */
	List<Sistema> listarSistema(BaseSearch filtro);

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
	void asociarPrivilegioByGrupoUsuario(List<Privilegio> filtro, String userName);

	/**
	 * Listar privilegio grupo usuario.
	 *
	 * @param privilegioGrupoUsuario el privilegio grupo usuario
	 * @return the list @ the exception
	 */
	List<PrivilegioGrupoUsuario> listarPrivilegioGrupoUsuario(BaseSearch filtro);

	Map<Long, PrivilegioGrupoUsuario> listarPrivilegioGrupoUsuarioMap(Long idGrupoUsuario, String estado);

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
	List<UsuarioEntidad> listarUsuarioEntidad(BaseSearch filtro);

	/**
	 * Listar grupo usuario menu.
	 *
	 * @param grupoUsuarioMenu el grupo usuario menu
	 * @return the list @ the exception
	 */
	List<GrupoUsuarioMenu> listarGrupoUsuarioMenu(BaseSearch filtro);
}