package pe.buildsoft.erp.core.domain.servicios.security;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
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
import pe.buildsoft.erp.core.domain.interfaces.repositories.security.ConfiguracionMenuDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.security.EntidadDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.security.GrupoUsuarioDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.security.GrupoUsuarioMenuDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.security.GrupoUsuarioUsuarioDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.security.MenuDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.security.MenuPersonalizadoDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.security.PrivilegioDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.security.PrivilegioGrupoUsuarioDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.security.PrivilegioMenuDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.security.PrivilegioPersonalizadoDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.security.PropertiesDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.security.PropertiesLenguajeDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.security.SistemaDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.security.TipoUsuarioDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.security.UsuarioDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.security.UsuarioEntidadDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.servicios.security.SeguridadServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.state.EstadoGeneralState;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.CollectionUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.FechaUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ObjectUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.cryto.EncriptarUtil;

/**
 * La Class SeguridadServiceImpl.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue Apr 18 13:43:23 COT 2017
 * @since SIAA-CORE 2.1
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class SeguridadServiceImpl implements SeguridadServiceLocal {

	private Logger log = LoggerFactory.getLogger(SeguridadServiceImpl.class);

	private static final long LENGUAJE_SPANISH = 526L;

	/** El servicio grupo usuario usuario dao impl. */
	@Inject
	private GrupoUsuarioUsuarioDaoLocal grupoUsuarioUsuarioDaoImpl;

	/** El servicio grupo usuario dao impl. */
	@Inject
	private GrupoUsuarioDaoLocal grupoUsuarioDaoImpl;

	/** El servicio privilegio menu dao impl. */
	@Inject
	private PrivilegioMenuDaoLocal privilegioMenuDaoImpl;

	/** El servicio properties lenguaje dao impl. */
	@Inject
	private PropertiesLenguajeDaoLocal propertiesLenguajeDaoImpl;

	/** El servicio menu personalizado dao impl. */
	@Inject
	private MenuPersonalizadoDaoLocal menuPersonalizadoDaoImpl;

	/** El servicio privilegio personalizado dao impl. */
	@Inject
	private PrivilegioPersonalizadoDaoLocal privilegioPersonalizadoDaoImpl;

	/** El servicio persona dao impl. */
	@Inject
	private UsuarioDaoLocal usuarioDaoImpl;

	/** El servicio properties dao impl. */
	@Inject
	private PropertiesDaoLocal propertiesDaoImpl;

	/** El servicio privilegio dao impl. */
	@Inject
	private PrivilegioDaoLocal privilegioDaoImpl;

	/** El servicio menu dao impl. */
	@Inject
	private MenuDaoLocal menuDaoImpl;

	/** El servicio configuracion menu dao impl. */
	@Inject
	private ConfiguracionMenuDaoLocal configuracionMenuDaoImpl;

	/** El servicio sistema dao impl. */
	@Inject
	private SistemaDaoLocal sistemaDaoImpl;

	/** El servicio privilegio grupo usuario dao impl. */
	@Inject
	private PrivilegioGrupoUsuarioDaoLocal privilegioGrupoUsuarioDaoImpl;

	/** El servicio usuario entidad dao impl. */
	@Inject
	private UsuarioEntidadDaoLocal usuarioEntidadDaoImpl;

	/** El servicio grupo usuario menu dao impl. */
	@Inject
	private GrupoUsuarioMenuDaoLocal grupoUsuarioMenuDaoImpl;

	/** El servicio entidad dao impl. */
	@Inject
	private EntidadDaoLocal entidadDaoImpl;

	/** El servicio tipo usuario dao impl. */
	@Inject
	private TipoUsuarioDaoLocal tipoUsuarioDaoImpl;

	@Override
	public Usuario obtenerUsuarioByUserName(String userName) {
		return this.usuarioDaoImpl.obtenerUsuarioByUserName(userName);
	}

	@Override
	public Usuario obtenerUsuarioByCodigoExterno(String codigoExterno) {
		return this.usuarioDaoImpl.obtenerUsuarioByCodigoExterno(codigoExterno);
	}

	@Override
	public Entidad controladorAccionEntidad(Entidad obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdEntidad(this.entidadDaoImpl.generarId());
			obj.setFechaCreacion(FechaUtil.obtenerFechaActual());
			this.entidadDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.entidadDaoImpl.update(obj);
			break;
		case ELIMINAR:
			obj = this.entidadDaoImpl.find(Entidad.class, obj.getIdEntidad());
			this.entidadDaoImpl.delete(obj);
			break;
		case FIND_BY_ID:
			obj = this.entidadDaoImpl.find(Entidad.class, obj.getIdEntidad());
			break;
		default:
			break;
		}

		return obj;
	}

	@Override
	public List<Entidad> listarEntidad(BaseSearch filtro) {
		return this.entidadDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarEntidad(BaseSearch filtro) {
		return this.entidadDaoImpl.contar(filtro);
	}

	@Override
	public TipoUsuario controladorAccionTipoUsuario(TipoUsuario obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdTipoUsuario(this.tipoUsuarioDaoImpl.generarId());
			this.tipoUsuarioDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.tipoUsuarioDaoImpl.update(obj);
			break;
		case ELIMINAR:
			obj = this.tipoUsuarioDaoImpl.find(TipoUsuario.class, obj.getIdTipoUsuario());
			this.tipoUsuarioDaoImpl.delete(obj);
			break;
		case FIND_BY_ID:
			obj = this.tipoUsuarioDaoImpl.find(TipoUsuario.class, obj.getIdTipoUsuario());
			break;
		default:
			break;
		}

		return obj;
	}

	@Override
	public List<TipoUsuario> listarTipoUsuario(BaseSearch filtro) {
		return this.tipoUsuarioDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarTipoUsuario(BaseSearch filtro) {
		return this.tipoUsuarioDaoImpl.contar(filtro);
	}

	@Override
	public Usuario controladorAccionUsuario(Usuario obj, AccionType accionType) {
		boolean isAsociarPermiso = false;
		switch (accionType) {
		case CREAR:
			isAsociarPermiso = true;
			obj.setIdUsuario(this.usuarioDaoImpl.generarId());
			if (StringUtil.isNotNullOrBlank(obj.getUserPassword())) {
				obj.setUserPassword(EncriptarUtil.encriptar(obj.getUserPassword()));
			} else {
				obj.setUserPassword(EncriptarUtil.encriptar(obj.getUserName()));
			}
			this.usuarioDaoImpl.save(obj);
			break;
		case MODIFICAR:
			isAsociarPermiso = true;
			if (StringUtil.isNotNullOrBlank(obj.getUserPassword())) {
				obj.setUserPassword(EncriptarUtil.encriptar(obj.getUserPassword()));
			} else {
				obj.setUserPassword(obj.getUserPasswordEncriptado());
			}
			this.usuarioDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.usuarioDaoImpl.find(Usuario.class, obj.getIdUsuario());
			this.usuarioDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.usuarioDaoImpl.find(Usuario.class, obj.getIdUsuario());
			break;

		default:
			break;
		}
		if (isAsociarPermiso) {
			asociarGrupoUsuarioByUsuario(obj.getGrupoUsuarios(), obj, obj.getUsuarioSession());
			asociarAccionUsuarioEntidad(obj.getEntidades(), obj);
		}
		return obj;
	}

	@Override
	public Usuario integracionUsuario(Usuario obj, AccionType accionType) {
		Usuario usuarioEntityBD = null;
		if (!AccionType.CREAR.equals(accionType)) {
			usuarioEntityBD = this.usuarioDaoImpl.obtenerUsuarioByCodigoExterno(obj.getCodigoExterno());
		}
		if (usuarioEntityBD == null) {
			usuarioEntityBD = new Usuario();
			accionType = AccionType.CREAR;
		}
		switch (accionType) {
		case CREAR:
			obj.setIdUsuario(this.usuarioDaoImpl.generarId());
			if (StringUtil.isNotNullOrBlank(obj.getUserPassword())) {
				obj.setUserPassword(EncriptarUtil.encriptar(obj.getUserPassword()));
			} else {
				obj.setUserPassword(EncriptarUtil.encriptar(obj.getUserName()));
			}
			this.usuarioDaoImpl.save(obj);
			// Inicio creando usuario
			UsuarioEntidad usuarioEntidad = new UsuarioEntidad();
			usuarioEntidad.setIdUsuarioEntidad(this.usuarioEntidadDaoImpl.generarId());
			usuarioEntidad.setEntidad(new Entidad());
			usuarioEntidad.getEntidad().setIdEntidad(obj.getIdEntidadSelect());
			usuarioEntidad.setUsuario(obj);
			usuarioEntidad.setEstado(EstadoGeneralState.ACTIVO.getKey());
			this.usuarioEntidadDaoImpl.save(usuarioEntidad);
			GrupoUsuarioUsuario grupoUsuarioRegistrar = new GrupoUsuarioUsuario();
			parseGrupoUsuarioUsuario(grupoUsuarioRegistrar, obj, obj.getUsuarioSession(),
					ObjectUtil.objectToLong(obj.getId()), true);
			grupoUsuarioRegistrar.setUsuario(obj);
			grupoUsuarioRegistrar.setIdGrupoUsuarioUsuario(this.grupoUsuarioUsuarioDaoImpl.generarId());
			grupoUsuarioRegistrar.setEstado(EstadoGeneralState.ACTIVO.getKey());
			this.grupoUsuarioUsuarioDaoImpl.save(grupoUsuarioRegistrar);
			// Fin creando usuario
			break;
		case MODIFICAR:
			obj.setIdUsuario(usuarioEntityBD.getIdUsuario());
			obj.setTipoUsuario(new TipoUsuario());
			obj.getTipoUsuario().setIdTipoUsuario(usuarioEntityBD.getTipoUsuario().getIdTipoUsuario());
			obj.setEstado(usuarioEntityBD.getEstado());
			if (StringUtil.isNotNullOrBlank(obj.getUserPassword())) {
				obj.setUserPassword(EncriptarUtil.encriptar(obj.getUserPassword()));
			} else {
				obj.setUserPassword(usuarioEntityBD.getUserPassword());
			}

			this.usuarioDaoImpl.update(obj);
			break;

		case ELIMINAR:
			BaseSearch usuarioEntidadFiltro = new BaseSearch();
			usuarioEntidadFiltro.setId(usuarioEntityBD.getIdUsuario());
			List<UsuarioEntidad> listaUsuarioEntidad = this.usuarioEntidadDaoImpl.listar(usuarioEntidadFiltro);
			for (var objEntidad : listaUsuarioEntidad) {
				this.usuarioEntidadDaoImpl.delete(objEntidad);
			}
			BaseSearch grupoUsuarioUsuarioFiltro = new BaseSearch();
			grupoUsuarioUsuarioFiltro.setId(usuarioEntityBD.getIdUsuario());
			List<GrupoUsuarioUsuario> listaGrupoUsuarioAsignado = this.grupoUsuarioUsuarioDaoImpl
					.listar(grupoUsuarioUsuarioFiltro);
			for (var grupoUsuarioUsuario : listaGrupoUsuarioAsignado) {
				this.grupoUsuarioUsuarioDaoImpl.delete(grupoUsuarioUsuario);
			}
			this.usuarioDaoImpl.delete(usuarioEntityBD);
			break;

		case FIND_BY_ID:
			obj = usuarioEntityBD;
			break;

		default:
			break;
		}
		return obj;
	}

	@Override
	public List<Usuario> listarUsuario(BaseSearch filtro) {
		return this.usuarioDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarUsuario(BaseSearch filtro) {
		return this.usuarioDaoImpl.contar(filtro);
	}

	@Override
	public List<Properties> obtenerPropertiesLenguaje(BaseSearch filtro) {
		return listarProperties(filtro);
	}

	private void asociarGrupoUsuarioByUsuario(List<Long> listaGrupoUsuario, Usuario usuario, String userName) {
		this.grupoUsuarioUsuarioDaoImpl.updateEstadoInactivo(usuario.getIdUsuario());
		if (!CollectionUtil.isEmpty(listaGrupoUsuario)) {
			Map<Long, GrupoUsuarioUsuario> listaGrupoMapBD = listarGrupoUsuarioUsuarioMap(usuario.getIdUsuario(), null);
			for (var grupoUsuario : listaGrupoUsuario) {
				Long idGrupoUsuario = grupoUsuario;
				if (!listaGrupoMapBD.containsKey(idGrupoUsuario)) {
					saveGrupoUsuarioUsuarioActivo(idGrupoUsuario, usuario, userName);
				} else {
					updateGrupoUsuarioUsuarioActivo(listaGrupoMapBD, idGrupoUsuario, usuario, userName);
				}
			}
		}
	}

	private void saveGrupoUsuarioUsuarioActivo(Long idGrupoUsuario, Usuario usuario, String userName) {
		GrupoUsuarioUsuario grupoUsuarioUsuario = new GrupoUsuarioUsuario();
		grupoUsuarioUsuario.setIdGrupoUsuarioUsuario(this.grupoUsuarioUsuarioDaoImpl.generarId());
		grupoUsuarioUsuario.setEstado(EstadoGeneralState.ACTIVO.getKey());
		parseGrupoUsuarioUsuario(grupoUsuarioUsuario, usuario, userName, idGrupoUsuario, true);
		this.grupoUsuarioUsuarioDaoImpl.save(grupoUsuarioUsuario);
	}

	private void updateGrupoUsuarioUsuarioActivo(Map<Long, GrupoUsuarioUsuario> listaGrupoMapBD, Long idGrupoUsuario,
			Usuario usuario, String userName) {
		GrupoUsuarioUsuario grupoUsuarioUsuario = listaGrupoMapBD.get(idGrupoUsuario);
		if (!(EstadoGeneralState.ACTIVO.getKey().equalsIgnoreCase(grupoUsuarioUsuario.getEstado()))) {
			grupoUsuarioUsuario.setEstado(EstadoGeneralState.ACTIVO.getKey());
			GrupoUsuarioUsuario resultadoEntity = grupoUsuarioUsuario;
			parseGrupoUsuarioUsuario(resultadoEntity, usuario, userName, idGrupoUsuario, false);
			this.grupoUsuarioUsuarioDaoImpl.update(resultadoEntity);
		}
	}

	private GrupoUsuarioUsuario parseGrupoUsuarioUsuario(GrupoUsuarioUsuario grupoUsuarioUsuario, Usuario usuario,
			String userName, Long idGrupoUsuario, boolean isNew) {
		grupoUsuarioUsuario.setUsuario(usuario);
		grupoUsuarioUsuario.setGrupoUsuario(new GrupoUsuario());
		if (isNew) {
			grupoUsuarioUsuario.setFechaCreacion(FechaUtil.obtenerFechaActual());
			grupoUsuarioUsuario.setUsuarioCreacion(userName);
		}
		grupoUsuarioUsuario.getGrupoUsuario().setIdGrupoUsuario(idGrupoUsuario);
		return grupoUsuarioUsuario;
	}

	@Override
	public List<GrupoUsuarioUsuario> listarGrupoUsuarioUsuario(BaseSearch filtro) {
		return this.grupoUsuarioUsuarioDaoImpl.listar(filtro);
	}

	@Override
	public Map<Long, GrupoUsuarioUsuario> listarGrupoUsuarioUsuarioMap(String idUsuario, String estado) {
		var filtro = new BaseSearch();
		filtro.setId(idUsuario);
		filtro.setEstado(estado);
		Map<Long, GrupoUsuarioUsuario> grupoUsuarioMap = new HashMap<>();
		List<GrupoUsuarioUsuario> listaTem = listarGrupoUsuarioUsuario(filtro);
		for (var objData : listaTem) {
			grupoUsuarioMap.put(objData.getGrupoUsuario().getIdGrupoUsuario(), objData);
		}
		return grupoUsuarioMap;
	}

	@Override
	public GrupoUsuario controladorAccionGrupoUsuario(GrupoUsuario obj, AccionType accionType) {
		boolean isAsociarMenu = false;
		switch (accionType) {
		case CREAR:
			isAsociarMenu = true;
			obj.setIdGrupoUsuario(this.grupoUsuarioDaoImpl.generarId());
			this.grupoUsuarioDaoImpl.save(obj);
			break;
		case MODIFICAR:
			isAsociarMenu = true;
			this.grupoUsuarioDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.grupoUsuarioDaoImpl.find(GrupoUsuario.class, obj.getIdGrupoUsuario());
			this.grupoUsuarioDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.grupoUsuarioDaoImpl.find(GrupoUsuario.class, obj.getIdGrupoUsuario());
			break;

		default:
			break;
		}
		if (isAsociarMenu) {
			asociarMenuByGrupoUsuario(obj.getMenus(), obj, obj.getUsuarioSession());
		}
		return obj;
	}

	@Override
	public List<GrupoUsuario> listarGrupoUsuario(BaseSearch filtro) {
		return this.grupoUsuarioDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarGrupoUsuario(BaseSearch filtro) {
		return this.grupoUsuarioDaoImpl.contar(filtro);
	}

	@Override
	public PrivilegioMenu controladorAccionPrivilegioMenu(PrivilegioMenu obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdPrivilegioMenu(this.privilegioMenuDaoImpl.generarId());
			this.privilegioMenuDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.privilegioMenuDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.privilegioMenuDaoImpl.find(PrivilegioMenu.class, obj.getIdPrivilegioMenu());
			this.privilegioMenuDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.privilegioMenuDaoImpl.find(PrivilegioMenu.class, obj.getIdPrivilegioMenu());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<PrivilegioMenu> listarPrivilegioMenu(BaseSearch filtro) {
		return this.privilegioMenuDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarPrivilegioMenu(BaseSearch filtro) {
		return this.privilegioMenuDaoImpl.contar(filtro);
	}

	@Override
	public PropertiesLenguaje controladorAccionPropertiesLenguaje(PropertiesLenguaje obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdPropertiesLenguaje(this.propertiesLenguajeDaoImpl.generarId());
			this.propertiesLenguajeDaoImpl.save(obj);
			break;

		case MODIFICAR:
			this.propertiesLenguajeDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.propertiesLenguajeDaoImpl.find(PropertiesLenguaje.class, obj.getIdPropertiesLenguaje());
			this.propertiesLenguajeDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.propertiesLenguajeDaoImpl.find(PropertiesLenguaje.class, obj.getIdPropertiesLenguaje());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<PropertiesLenguaje> listarPropertiesLenguaje(BaseSearch filtro) {
		return this.propertiesLenguajeDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarPropertiesLenguaje(BaseSearch filtro) {
		return this.propertiesLenguajeDaoImpl.contar(filtro);
	}

	@Override
	public void asociarMenuPersonalizadoByUsuario(List<Menu> listaMenu, String userName) {
		if (!CollectionUtil.isEmpty(listaMenu)) {
			Menu objData = listaMenu.get(0);
			String idUsuario = objData.getId() + "";
			Map<Long, MenuPersonalizado> listaGrupoMapBD = listarMenuPersonalizadoMap(idUsuario, null);
			for (var menu : listaMenu) {
				Long idMenu = menu.getIdMenu();
				if (menu.isChecked()) {
					if (!listaGrupoMapBD.containsKey(idMenu)) {
						saveMenuPersonalizadoActivo(idMenu, idUsuario, userName);
					} else {
						updateMenuPersonalizadoActivo(listaGrupoMapBD, idMenu, idUsuario, userName, menu);
					}
				} else if (!menu.isChecked() && listaGrupoMapBD.containsKey(idMenu)) {
					updateMenuPersonalizadoInactivo(listaGrupoMapBD, idMenu, idUsuario, userName);
				}
			}
		}
	}

	private void saveMenuPersonalizadoActivo(Long idMenu, String idUsuario, String userName) {
		MenuPersonalizado menuPersonalizado = new MenuPersonalizado();
		menuPersonalizado.setIdMenuPersonalizado(this.menuPersonalizadoDaoImpl.generarId());
		menuPersonalizado.setEstado(EstadoGeneralState.ACTIVO.getKey());
		parseMenuPersonalizado(menuPersonalizado, idUsuario, userName, idMenu, true);
		this.menuPersonalizadoDaoImpl.save(menuPersonalizado);
	}

	private void updateMenuPersonalizadoActivo(Map<Long, MenuPersonalizado> listaGrupoMapBD, Long idMenu,
			String idUsuario, String userName, Menu menu) {
		MenuPersonalizado menuPersonalizado = listaGrupoMapBD.get(menu.getIdMenu());
		if (!(EstadoGeneralState.ACTIVO.getKey().equalsIgnoreCase(menuPersonalizado.getEstado()))) {
			menuPersonalizado.setEstado(EstadoGeneralState.ACTIVO.getKey());
			MenuPersonalizado resultadoEntity = menuPersonalizado;
			parseMenuPersonalizado(resultadoEntity, idUsuario, userName, idMenu, false);
			this.menuPersonalizadoDaoImpl.update(resultadoEntity);
		}
	}

	private void updateMenuPersonalizadoInactivo(Map<Long, MenuPersonalizado> listaGrupoMapBD, Long idMenu,
			String idUsuario, String userName) {
		MenuPersonalizado menuPersonalizado = listaGrupoMapBD.get(idMenu);
		if (!(EstadoGeneralState.INACTIVO.getKey().equalsIgnoreCase(menuPersonalizado.getEstado()))) {
			menuPersonalizado.setEstado(EstadoGeneralState.INACTIVO.getKey());
			MenuPersonalizado resultadoEntity = menuPersonalizado;
			parseMenuPersonalizado(resultadoEntity, idUsuario, userName, idMenu, false);
			this.menuPersonalizadoDaoImpl.update(resultadoEntity);

		}
	}

	private MenuPersonalizado parseMenuPersonalizado(MenuPersonalizado menuPersonalizado, String idUsuario,
			String userName, Long idMenu, boolean isNew) {
		menuPersonalizado.setPersona(new Usuario());
		menuPersonalizado.getPersona().setIdUsuario(idUsuario);
		menuPersonalizado.setMenu(new Menu());
		if (isNew) {
			menuPersonalizado.setFechaCreacion(FechaUtil.obtenerFechaActual());
			menuPersonalizado.setUsuarioCreacion(userName);
		}
		menuPersonalizado.getMenu().setIdMenu(idMenu);
		return menuPersonalizado;
	}

	@Override
	public List<MenuPersonalizado> listarMenuPersonalizado(BaseSearch filtro) {
		return this.menuPersonalizadoDaoImpl.listar(filtro);
	}

	@Override
	public Map<Long, MenuPersonalizado> listarMenuPersonalizadoMap(String idUsuario, String estado) {
		BaseSearch menuPersonalizado = new BaseSearch();
		menuPersonalizado.setId(idUsuario);
		menuPersonalizado.setEstado(estado);
		Map<Long, MenuPersonalizado> menuPersonalizadoMap = new HashMap<>();
		List<MenuPersonalizado> listaTem = listarMenuPersonalizado(menuPersonalizado);
		for (var objData : listaTem) {
			menuPersonalizadoMap.put(objData.getMenu().getIdMenu(), objData);
		}
		return menuPersonalizadoMap;
	}

	@Override
	public void asociarPrivilegioPersonalizadoByUsuario(List<Privilegio> listaPrivilegio, String userName) {
		if (!CollectionUtil.isEmpty(listaPrivilegio)) {
			Privilegio objData = listaPrivilegio.get(0);
			String idUsuario = objData.getId() + "";
			Map<Long, PrivilegioPersonalizado> listaPrivilegioMapBD = listarPrivilegioPersonalizadoMap(idUsuario, null);
			for (var privilegio : listaPrivilegio) {
				Long idPrivilegio = privilegio.getIdPrivilegio();
				if (privilegio.isChecked()) {
					if (!listaPrivilegioMapBD.containsKey(idPrivilegio)) {
						savePrivilegioPersonalizadoActivo(idPrivilegio, idUsuario, userName);
					} else {
						updatePrivilegioPersonalizadoActivo(listaPrivilegioMapBD, idPrivilegio, idUsuario, userName);
					}
				} else if (!privilegio.isChecked() && listaPrivilegioMapBD.containsKey(idPrivilegio)) {
					updatePrivilegioPersonalizadoInactivo(listaPrivilegioMapBD, idPrivilegio, idUsuario, userName);
				}
			}
		}
	}

	private void savePrivilegioPersonalizadoActivo(Long idPrivilegio, String idUsuario, String userName) {
		PrivilegioPersonalizado obj = new PrivilegioPersonalizado();
		obj.setIdPrivilegioPersonalizado(this.privilegioPersonalizadoDaoImpl.generarId());
		obj.setEstado(EstadoGeneralState.ACTIVO.getKey());
		parsePrivilegioPersonalizado(obj, idUsuario, userName, idPrivilegio, true);
		this.privilegioPersonalizadoDaoImpl.save(obj);
	}

	private void updatePrivilegioPersonalizadoActivo(Map<Long, PrivilegioPersonalizado> listaPrivilegioMapBD,
			Long idPrivilegio, String idUsuario, String userName) {
		PrivilegioPersonalizado privilegioPersonalizado = listaPrivilegioMapBD.get(idPrivilegio);
		if (!(EstadoGeneralState.ACTIVO.getKey().equalsIgnoreCase(privilegioPersonalizado.getEstado()))) {
			privilegioPersonalizado.setEstado(EstadoGeneralState.ACTIVO.getKey());
			PrivilegioPersonalizado resultadoEntity = privilegioPersonalizado;
			parsePrivilegioPersonalizado(resultadoEntity, idUsuario, userName, idPrivilegio, false);
			this.privilegioPersonalizadoDaoImpl.update(resultadoEntity);
		}
	}

	private void updatePrivilegioPersonalizadoInactivo(Map<Long, PrivilegioPersonalizado> listaPrivilegioMapBD,
			Long idPrivilegio, String idUsuario, String userName) {
		PrivilegioPersonalizado privilegioPersonalizado = listaPrivilegioMapBD.get(idPrivilegio);
		if (!(EstadoGeneralState.INACTIVO.getKey().equalsIgnoreCase(privilegioPersonalizado.getEstado()))) {
			privilegioPersonalizado.setEstado(EstadoGeneralState.INACTIVO.getKey());
			PrivilegioPersonalizado resultadoEntity = privilegioPersonalizado;
			parsePrivilegioPersonalizado(resultadoEntity, idUsuario, userName, idPrivilegio, false);
			this.privilegioPersonalizadoDaoImpl.update(resultadoEntity);

		}
	}

	private PrivilegioPersonalizado parsePrivilegioPersonalizado(PrivilegioPersonalizado privilegioPersonalizado,
			String idUsuario, String userName, Long idPrivilegio, boolean isNew) {
		privilegioPersonalizado.setUsuario(new Usuario());
		privilegioPersonalizado.getUsuario().setIdUsuario(idUsuario);
		privilegioPersonalizado.setPrivilegio(new Privilegio());
		privilegioPersonalizado.getPrivilegio().setIdPrivilegio(idPrivilegio);
		return privilegioPersonalizado;
	}

	public PrivilegioPersonalizado controladorAccionPrivilegioPersonalizado(PrivilegioPersonalizado obj,
			AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdPrivilegioPersonalizado(this.privilegioPersonalizadoDaoImpl.generarId());
			this.privilegioPersonalizadoDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.privilegioPersonalizadoDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.privilegioPersonalizadoDaoImpl.find(PrivilegioPersonalizado.class,
					obj.getIdPrivilegioPersonalizado());
			this.privilegioPersonalizadoDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.privilegioPersonalizadoDaoImpl.find(PrivilegioPersonalizado.class,
					obj.getIdPrivilegioPersonalizado());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<PrivilegioPersonalizado> listarPrivilegioPersonalizado(BaseSearch filtro) {
		return this.privilegioPersonalizadoDaoImpl.listar(filtro);
	}

	@Override
	public Map<Long, PrivilegioPersonalizado> listarPrivilegioPersonalizadoMap(String idUsuario, String estado) {
		BaseSearch privilegioPersonalizado = new BaseSearch();
		privilegioPersonalizado.setId(idUsuario);
		privilegioPersonalizado.setEstado(estado);
		Map<Long, PrivilegioPersonalizado> resultado = new HashMap<>();
		List<PrivilegioPersonalizado> listaTem = this.privilegioPersonalizadoDaoImpl.listar(privilegioPersonalizado);
		for (var objData : listaTem) {
			resultado.put(objData.getPrivilegio().getIdPrivilegio(), objData);
		}
		return resultado;
	}

	@Override
	public Properties controladorAccionProperties(Properties obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdProperties(this.propertiesDaoImpl.generarId());
			this.propertiesDaoImpl.save(obj);
			/*
			 * PropertiesLenguaje propertiesLenguaje = new PropertiesLenguaje();
			 * propertiesLenguaje.setProperties(resultadoEntity);
			 * propertiesLenguaje.setValue(properties.getValue());
			 * propertiesLenguaje.setIdPropertiesLenguaje(propertiesLenguajeDaoImpl.
			 * generarIdPropertiesLenguaje()); propertiesLenguaje.setItemByLenguaje(new
			 * Item(LENGUAJE_SPANISH));
			 * this.propertiesLenguajeDaoImpl.save(propertiesLenguaje);
			 */
			break;
		case MODIFICAR:
			this.propertiesDaoImpl.update(obj);
			// this.propertiesLenguajeDaoImpl.actualizarPropertiesLenguaje(properties);
			break;

		case ELIMINAR:
			obj = this.propertiesDaoImpl.find(Properties.class, obj.getIdProperties());
			this.propertiesDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.propertiesDaoImpl.find(Properties.class, obj.getIdProperties());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<Properties> listarProperties(BaseSearch filtro) {
		return this.propertiesDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarProperties(BaseSearch filtro) {
		return this.propertiesDaoImpl.contar(filtro);
	}

	@Override
	public Privilegio controladorAccionPrivilegio(Privilegio obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdPrivilegio(this.privilegioDaoImpl.generarId());
			this.privilegioDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.privilegioDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.privilegioDaoImpl.find(Privilegio.class, obj.getIdPrivilegio());
			this.privilegioDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.privilegioDaoImpl.find(Privilegio.class, obj.getIdPrivilegio());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<Privilegio> listarPrivilegio(BaseSearch filtro) {
		return this.privilegioDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarPrivilegio(BaseSearch filtro) {
		return this.privilegioDaoImpl.contar(filtro);
	}

	@Override
	public Menu controladorAccionMenu(Menu obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdMenu(this.menuDaoImpl.generarId());
			obj.setFechaCreacion(FechaUtil.obtenerFechaActual());
			this.menuDaoImpl.save(obj);
			break;
		case MODIFICAR:
			obj.setFechaModificacion(FechaUtil.obtenerFechaActual());
			this.menuDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.menuDaoImpl.find(Menu.class, obj.getIdMenu());
			this.menuDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.menuDaoImpl.find(Menu.class, obj.getIdMenu());
			break;

		default:
			break;
		}
		return obj;
	}

	@Override
	public List<Menu> listarMenu(BaseSearch filtro) {
		return this.menuDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarMenu(BaseSearch filtro) {
		return this.menuDaoImpl.contar(filtro);
	}

	@Override
	public ConfiguracionMenu controladorAccionConfiguracionMenu(ConfiguracionMenu obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdConfiguracionMenu(this.configuracionMenuDaoImpl.generarId());
			this.configuracionMenuDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.configuracionMenuDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.configuracionMenuDaoImpl.find(ConfiguracionMenu.class, obj.getIdConfiguracionMenu());
			this.configuracionMenuDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.configuracionMenuDaoImpl.find(ConfiguracionMenu.class, obj.getIdConfiguracionMenu());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<ConfiguracionMenu> listarConfiguracionMenu(BaseSearch filtro) {
		return this.configuracionMenuDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarConfiguracionMenu(BaseSearch filtro) {
		return this.configuracionMenuDaoImpl.contar(filtro);
	}

	@Override
	public Sistema controladorAccionSistema(Sistema obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdSistema(this.sistemaDaoImpl.generarId());
			this.sistemaDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.sistemaDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.sistemaDaoImpl.find(Sistema.class, obj.getIdSistema());
			this.sistemaDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.sistemaDaoImpl.find(Sistema.class, obj.getIdSistema());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<Sistema> listarSistema(BaseSearch filtro) {
		return this.sistemaDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarSistema(BaseSearch filtro) {
		return this.sistemaDaoImpl.contar(filtro);
	}

	@Override
	public void asociarPrivilegioByGrupoUsuario(List<Privilegio> listaPrivilegio, String userName) {
		if (!CollectionUtil.isEmpty(listaPrivilegio)) {
			Privilegio objData = listaPrivilegio.get(0);
			Long idGrupoUsuario = ObjectUtil.objectToLong(objData.getId());
			Map<Long, PrivilegioGrupoUsuario> listaPrivilegioMapBD = listarPrivilegioGrupoUsuarioMap(idGrupoUsuario,
					null);
			for (var privilegio : listaPrivilegio) {
				Long idPrivilegio = privilegio.getIdPrivilegio();
				if (privilegio.isChecked()) {
					if (!listaPrivilegioMapBD.containsKey(idPrivilegio)) {
						savePrivilegioGrupoUsuarioActivo(idPrivilegio, idGrupoUsuario, userName);
					} else {
						updatePrivilegioGrupoUsuarioActivo(listaPrivilegioMapBD, idPrivilegio, idGrupoUsuario,
								userName);
					}
				} else if (!privilegio.isChecked() && listaPrivilegioMapBD.containsKey(idPrivilegio)) {
					updatePrivilegioGrupoUsuarioInactivo(listaPrivilegioMapBD, idPrivilegio, idGrupoUsuario, userName);
				}
			}
		}
	}

	private void savePrivilegioGrupoUsuarioActivo(Long idPrivilegio, Long idGrupoUsuario, String userName) {
		PrivilegioGrupoUsuario privilegioGrupoUsuario = new PrivilegioGrupoUsuario();
		privilegioGrupoUsuario.setIdPrivilegioGrupoUsuario(this.privilegioGrupoUsuarioDaoImpl.generarId());
		privilegioGrupoUsuario.setEstado(EstadoGeneralState.ACTIVO.getKey());
		parsePrivilegioGrupoUsuario(privilegioGrupoUsuario, idGrupoUsuario, userName, idPrivilegio, true);
		this.privilegioGrupoUsuarioDaoImpl.save(privilegioGrupoUsuario);
	}

	private void updatePrivilegioGrupoUsuarioActivo(Map<Long, PrivilegioGrupoUsuario> listaPrivilegioMapBD,
			Long idPrivilegio, Long idGrupoUsuario, String userName) {
		PrivilegioGrupoUsuario privilegioGrupoUsuario = listaPrivilegioMapBD.get(idPrivilegio);
		if (!(EstadoGeneralState.ACTIVO.getKey().equalsIgnoreCase(privilegioGrupoUsuario.getEstado()))) {
			privilegioGrupoUsuario.setEstado(EstadoGeneralState.ACTIVO.getKey());
			PrivilegioGrupoUsuario resultadoEntity = privilegioGrupoUsuario;
			parsePrivilegioGrupoUsuario(resultadoEntity, idGrupoUsuario, userName, idPrivilegio, false);
			this.privilegioGrupoUsuarioDaoImpl.update(resultadoEntity);
		}
	}

	private void updatePrivilegioGrupoUsuarioInactivo(Map<Long, PrivilegioGrupoUsuario> listaPrivilegioMapBD,
			Long idPrivilegio, Long idGrupoUsuario, String userName) {
		PrivilegioGrupoUsuario privilegioGrupoUsuario = listaPrivilegioMapBD.get(idPrivilegio);
		if (!(EstadoGeneralState.INACTIVO.getKey().equalsIgnoreCase(privilegioGrupoUsuario.getEstado()))) {
			privilegioGrupoUsuario.setEstado(EstadoGeneralState.INACTIVO.getKey());
			PrivilegioGrupoUsuario resultadoEntity = privilegioGrupoUsuario;
			parsePrivilegioGrupoUsuario(resultadoEntity, idGrupoUsuario, userName, idPrivilegio, false);
			this.privilegioGrupoUsuarioDaoImpl.update(resultadoEntity);

		}
	}

	private PrivilegioGrupoUsuario parsePrivilegioGrupoUsuario(PrivilegioGrupoUsuario obj, Long idGrupoUsuario,
			String userName, Long idPrivilegio, boolean isNew) {
		obj.setGrupoUsuario(new GrupoUsuario());
		obj.getGrupoUsuario().setIdGrupoUsuario(idGrupoUsuario);
		obj.setPrivilegio(new Privilegio());
		obj.getPrivilegio().setIdPrivilegio(idPrivilegio);
		return obj;
	}

	@Override
	public List<PrivilegioGrupoUsuario> listarPrivilegioGrupoUsuario(BaseSearch filtro) {
		return this.privilegioGrupoUsuarioDaoImpl.listar(filtro);
	}

	@Override
	public Map<Long, PrivilegioGrupoUsuario> listarPrivilegioGrupoUsuarioMap(Long idGrupoUsuario, String estado) {
		var filtro = new BaseSearch();
		filtro.setId(idGrupoUsuario);
		filtro.setEstado(estado);
		Map<Long, PrivilegioGrupoUsuario> resultado = new HashMap<>();
		List<PrivilegioGrupoUsuario> listaTem = this.privilegioGrupoUsuarioDaoImpl.listar(filtro);
		for (var objData : listaTem) {
			resultado.put(objData.getPrivilegio().getIdPrivilegio(), objData);
		}
		return resultado;
	}

	@Override
	public int contarListarPrivilegioGrupoUsuario(BaseSearch filtro) {
		return this.privilegioGrupoUsuarioDaoImpl.contar(filtro);
	}

	private void asociarAccionUsuarioEntidad(List<String> entidades, Usuario usuario) {
		this.usuarioEntidadDaoImpl.updateEstadoInactivo(usuario.getIdUsuario());
		var filtro = new BaseSearch();
		filtro.setId(usuario.getIdUsuario());
		Map<String, UsuarioEntidad> usuarioEntidadMAp = new HashMap<>();
		List<UsuarioEntidad> usuarioEntidadList = this.listarUsuarioEntidad(filtro);
		for (var objG : usuarioEntidadList) {
			usuarioEntidadMAp.put(objG.getEntidad().getIdEntidad(), objG);
		}
		for (var entidad : entidades) {
			if (!usuarioEntidadMAp.containsKey(entidad)) {
				UsuarioEntidad obj = new UsuarioEntidad();
				obj.setIdUsuarioEntidad(this.usuarioEntidadDaoImpl.generarId());
				obj.setEntidad(new Entidad());
				obj.getEntidad().setIdEntidad(entidad);
				obj.setUsuario(usuario);
				obj.setEstado(EstadoGeneralState.ACTIVO.getKey());
				this.usuarioEntidadDaoImpl.save(obj);
			} else {
				UsuarioEntidad obj = usuarioEntidadMAp.get(entidad);
				obj.setEstado(EstadoGeneralState.ACTIVO.getKey());
				this.usuarioEntidadDaoImpl.update(obj);
			}
		}
	}

	@Override
	public List<UsuarioEntidad> listarUsuarioEntidad(BaseSearch filtro) {
		return this.usuarioEntidadDaoImpl.listar(filtro);
	}

	private void asociarMenuByGrupoUsuario(List<Long> listaMenu, GrupoUsuario grupoUsuario, String userName) {
		this.grupoUsuarioMenuDaoImpl.updateEstadoInactivo(grupoUsuario.getIdGrupoUsuario());
		if (!CollectionUtil.isEmpty(listaMenu)) {
			Map<Long, GrupoUsuarioMenu> listaGrupoMapBD = listarGrupoUsuarioMenuMap(grupoUsuario.getIdGrupoUsuario(),
					null);
			for (var menu : listaMenu) {
				if (!listaGrupoMapBD.containsKey(menu)) {
					saveGrupoUsuarioMenuActivo(menu, grupoUsuario, userName);
				} else {
					updateGrupoUsuarioMenuActivo(listaGrupoMapBD, menu, grupoUsuario, userName);
				}
			}
		}
	}

	private void saveGrupoUsuarioMenuActivo(Long idMenu, GrupoUsuario grupoUsuario, String userName) {
		GrupoUsuarioMenu objDataPersis = new GrupoUsuarioMenu();
		objDataPersis.setIdGrupoUsuarioMenu(this.grupoUsuarioMenuDaoImpl.generarId());
		objDataPersis.setEstado(EstadoGeneralState.ACTIVO.getKey());
		parseGrupoUsuarioMenu(objDataPersis, grupoUsuario, userName, idMenu, true);
		this.grupoUsuarioMenuDaoImpl.save(objDataPersis);
	}

	private void updateGrupoUsuarioMenuActivo(Map<Long, GrupoUsuarioMenu> listaGrupoMapBD, Long idMenu,
			GrupoUsuario grupoUsuario, String userName) {
		GrupoUsuarioMenu objDataPersist = listaGrupoMapBD.get(idMenu);
		if (!(EstadoGeneralState.ACTIVO.getKey().equalsIgnoreCase(objDataPersist.getEstado()))) {
			objDataPersist.setEstado(EstadoGeneralState.ACTIVO.getKey());
			GrupoUsuarioMenu resultadoEntity = objDataPersist;
			parseGrupoUsuarioMenu(resultadoEntity, grupoUsuario, userName, idMenu, false);
			this.grupoUsuarioMenuDaoImpl.update(resultadoEntity);
		}
	}

	private GrupoUsuarioMenu parseGrupoUsuarioMenu(GrupoUsuarioMenu objDataPersis, GrupoUsuario grupoUsuario,
			String userName, Long idMenu, boolean isNew) {
		objDataPersis.setGrupoUsuario(grupoUsuario);
		objDataPersis.setMenu(new Menu());
		if (isNew) {
			objDataPersis.setFechaCreacion(FechaUtil.obtenerFechaActual());
			objDataPersis.setUsuarioCreacion(userName);
		}
		objDataPersis.getMenu().setIdMenu(idMenu);
		return objDataPersis;
	}

	@Override
	public List<GrupoUsuarioMenu> listarGrupoUsuarioMenu(BaseSearch filtro) {
		return this.grupoUsuarioMenuDaoImpl.listar(filtro);
	}

	private Map<Long, GrupoUsuarioMenu> listarGrupoUsuarioMenuMap(Long idGrupoUsuario, String estado) {
		var filtro = new BaseSearch();
		filtro.setId(idGrupoUsuario);
		filtro.setEstado(estado);
		Map<Long, GrupoUsuarioMenu> resultadoMap = new HashMap<>();
		List<GrupoUsuarioMenu> listaTem = this.grupoUsuarioMenuDaoImpl.listar(filtro);
		for (var objData : listaTem) {
			resultadoMap.put(objData.getMenu().getIdMenu(), objData);
		}
		return resultadoMap;
	}
}