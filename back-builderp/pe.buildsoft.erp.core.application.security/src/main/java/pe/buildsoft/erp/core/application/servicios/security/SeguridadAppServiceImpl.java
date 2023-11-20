package pe.buildsoft.erp.core.application.servicios.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import pe.buildsoft.erp.core.application.entidades.common.ItemDTO;
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
import pe.buildsoft.erp.core.application.interfaces.security.SeguridadAppServiceLocal;
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
import pe.buildsoft.erp.core.domain.interfaces.servicios.security.SeguridadServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.cache.AtributosEntityCacheUtil;
import pe.buildsoft.erp.core.infra.transversal.cache.ICache;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.SelectItemVO;
import pe.buildsoft.erp.core.infra.transversal.state.EstadoGeneralState;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.BaseTransfer;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * La Class SeguridadAppServiceImpl.
 * <ul>
 * <li>Copyright 2021 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Mon Sep 06 16:48:51 COT 2021
 * @since BuildErp 1.0
 */
@Stateless
public class SeguridadAppServiceImpl extends BaseTransfer implements SeguridadAppServiceLocal {

	private static final String CLASS_TIPO_USUARIO = "tipoUsuario";

	private Logger log = LoggerFactory.getLogger(SeguridadAppServiceImpl.class);

	/** El servicio grupo usuario usuario dao impl. */
	@Inject
	private SeguridadServiceLocal servicio;

	@Inject
	private ICache cacheUtil;

	public SeguridadAppServiceImpl() {
		AtributosEntityCacheUtil.getInstance().sincronizarAtributos("pe.buildsoft.erp.core.domain.entidades.security");
	}

	@Override
	public List<SelectItemVO> listarSelectItem(String groupName) {
		List<SelectItemVO> resultado = new ArrayList<>();
		switch (groupName) {
		case "tipoUsuario":
			resultado = listarTipoUsuarioSelectItem();
			break;
		case "sistema":
			resultado = listarSistemaSelectItem();
			break;
		case "grupoUsuario":
			resultado = listarGrupoUsuarioSelectItem();
			break;

		case "entidad":
			resultado = listarEntidadSelectItem();
			break;

		case "menu":
			resultado = listarMenuSelectItem();
			break;

		default:
			break;
		}
		return resultado;
	}

	private List<SelectItemVO> listarMenuSelectItem() {
		var filtro = new BaseSearch();
		filtro.setSortFields("idMenu");
		filtro.setSortDirections("asc");
		List<SelectItemVO> resultado = new ArrayList<>();
		List<Menu> resulTmp = servicio.listarMenu(filtro);
		for (var obj : resulTmp) {
			resultado.add(new SelectItemVO(obj.getIdMenu(), obj.getNombre(), obj.getSistema().getIdSistema() + ""));
		}
		return resultado;
	}

	private List<SelectItemVO> listarEntidadSelectItem() {
		var filtro = new BaseSearch();
		filtro.setSortFields("idEntidad");
		filtro.setSortDirections("asc");
		List<SelectItemVO> resultado = new ArrayList<>();
		List<Entidad> resulTmp = servicio.listarEntidad(filtro);
		for (var obj : resulTmp) {
			resultado.add(new SelectItemVO(obj.getIdEntidad(), obj.getNombre()));
		}
		return resultado;
	}

	private List<SelectItemVO> listarGrupoUsuarioSelectItem() {
		var filtro = new BaseSearch();
		filtro.setSortFields("idGrupoUsuario");
		filtro.setSortDirections("asc");
		List<SelectItemVO> resultado = new ArrayList<>();
		List<GrupoUsuario> resulTmp = servicio.listarGrupoUsuario(filtro);
		for (var obj : resulTmp) {
			resultado.add(new SelectItemVO(obj.getIdGrupoUsuario(), obj.getDescripcion()));
		}
		return resultado;
	}

	private List<SelectItemVO> listarSistemaSelectItem() {
		var filtro = new BaseSearch();
		filtro.setSortFields("idSistema");
		filtro.setSortDirections("asc");
		List<SelectItemVO> resultado = new ArrayList<>();
		List<Sistema> resulTmp = servicio.listarSistema(filtro);
		for (var obj : resulTmp) {
			resultado.add(new SelectItemVO(obj.getIdSistema(), obj.getNombre()));
		}
		return resultado;
	}

	private List<SelectItemVO> listarTipoUsuarioSelectItem() {
		var filtro = new BaseSearch();
		filtro.setSortFields("descripcion");
		filtro.setSortDirections("asc");
		List<SelectItemVO> resultado = new ArrayList<>();
		List<TipoUsuario> resulTmp = servicio.listarTipoUsuario(filtro);
		for (var obj : resulTmp) {
			resultado.add(new SelectItemVO(obj.getIdTipoUsuario(), obj.getDescripcion()));
		}
		return resultado;
	}

	@Override
	public UsuarioDTO obtenerUsuarioByUserName(String userName) {
		return toDTO(this.servicio.obtenerUsuarioByUserName(userName), UsuarioDTO.class, CLASS_TIPO_USUARIO);
	}

	@Override
	public UsuarioDTO obtenerUsuarioByCodigoExterno(String codigoExterno) {
		return toDTO(this.servicio.obtenerUsuarioByCodigoExterno(codigoExterno), UsuarioDTO.class, CLASS_TIPO_USUARIO);
	}

	@Override
	public EntidadDTO controladorAccionEntidad(EntidadDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionEntidad(to(obj, Entidad.class), accionType), EntidadDTO.class);
	}

	@Override
	public List<EntidadDTO> listarEntidad(BaseSearch filtro) {
		List<EntidadDTO> resultado = toList(this.servicio.listarEntidad(filtro), EntidadDTO.class);
		Map<String, String> completeMap = new HashMap<>();
		completeMap.put("idItemByTipoVia", "itemByTipoVia");
		completeMap.put("idItemByZona", "itemByZona");
		cacheUtil.completarData(resultado, completeMap, "item");
		for (var obj : resultado) {
			generarDescripcionViewItem(obj.getItemByTipoVia());
			generarDescripcionViewItem(obj.getItemByZona());
		}
		return resultado;
	}

	private ItemDTO generarDescripcionViewItem(ItemDTO item) {
		if (item != null && item.getIdItem() != null) {
			item.setDescripcionView(item.getCodigoExterno() + " " + item.getNombre());
		}
		return item;
	}

	@Override
	public int contarListarEntidad(BaseSearch filtro) {
		return this.servicio.contarListarEntidad(filtro);
	}

	@Override
	public TipoUsuarioDTO controladorAccionTipoUsuario(TipoUsuarioDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionTipoUsuario(to(obj, TipoUsuario.class), accionType),
				TipoUsuarioDTO.class);
	}

	@Override
	public List<TipoUsuarioDTO> listarTipoUsuario(BaseSearch filtro) {
		return toList(this.servicio.listarTipoUsuario(filtro), TipoUsuarioDTO.class);
	}

	@Override
	public int contarListarTipoUsuario(BaseSearch filtro) {
		return this.servicio.contarListarTipoUsuario(filtro);
	}

	@Override
	public UsuarioDTO controladorAccionUsuario(UsuarioDTO obj, AccionType accionType) {
		Usuario objPersist = to(obj, Usuario.class);
		objPersist.setEntidades(obj.getEntidades());
		objPersist.setGrupoUsuarios(obj.getGrupoUsuarios());
		UsuarioDTO resultado = toDTO(servicio.controladorAccionUsuario(objPersist, accionType), UsuarioDTO.class);
		if (AccionType.FIND_BY_ID.equals(accionType)) {
			var filtro = new BaseSearch();
			filtro.setId(obj.getIdUsuario());
			filtro.setEstado(EstadoGeneralState.ACTIVO.getKey());
			List<GrupoUsuarioUsuario> grupoUsuarioList = this.servicio.listarGrupoUsuarioUsuario(filtro);
			for (var objG : grupoUsuarioList) {
				resultado.getGrupoUsuarios().add(objG.getGrupoUsuario().getIdGrupoUsuario());
			}
			List<UsuarioEntidad> usuarioEntidadList = this.servicio.listarUsuarioEntidad(filtro);
			for (var objG : usuarioEntidadList) {
				resultado.getEntidades().add(objG.getEntidad().getIdEntidad());
			}
		}
		return resultado;
	}

	@Override
	public UsuarioDTO integracionUsuario(UsuarioDTO obj) {
		AccionType accionType = AccionType.get(obj.getAccion());
		return toDTO(servicio.integracionUsuario(to(obj, Usuario.class), accionType), UsuarioDTO.class);
	}

	@Override
	public List<UsuarioDTO> listarUsuario(BaseSearch filtro) {
		return toList(this.servicio.listarUsuario(filtro), UsuarioDTO.class, CLASS_TIPO_USUARIO);
	}

	@Override
	public int contarListarUsuario(BaseSearch filtro) {
		return this.servicio.contarListarUsuario(filtro);
	}

	@Override
	public List<PropertiesDTO> obtenerPropertiesLenguaje(BaseSearch filtro) {
		return listarProperties(filtro);
	}

	@Override
	public List<GrupoUsuarioDTO> obtenerGrupoUsuarioCheck(BaseSearch filtro, String idUsuario) {
		List<GrupoUsuarioDTO> resultado = null;
		Map<Long, GrupoUsuarioUsuario> grupoUsuarioMap = this.servicio.listarGrupoUsuarioUsuarioMap(idUsuario,
				EstadoGeneralState.ACTIVO.getKey());
		resultado = listarGrupoUsuario(filtro);
		for (var objData : resultado) {
			if (grupoUsuarioMap.containsKey(objData.getIdGrupoUsuario())) {
				objData.setChecked(true);
			}

		}
		return resultado;
	}

	@Override
	public GrupoUsuarioDTO controladorAccionGrupoUsuario(GrupoUsuarioDTO obj, AccionType accionType) {
		GrupoUsuario objPersit = to(obj, GrupoUsuario.class);
		objPersit.setMenus(obj.getMenus());
		GrupoUsuarioDTO resultado = toDTO(servicio.controladorAccionGrupoUsuario(objPersit, accionType),
				GrupoUsuarioDTO.class);
		if (AccionType.FIND_BY_ID.equals(accionType)) {
			var filtro = new BaseSearch();
			filtro.setId(obj.getIdGrupoUsuario());
			filtro.setEstado(EstadoGeneralState.ACTIVO.getKey());
			List<GrupoUsuarioMenu> grupoUsuarioMenuList = this.servicio.listarGrupoUsuarioMenu(filtro);
			for (var objM : grupoUsuarioMenuList) {
				resultado.getMenus().add(objM.getMenu().getIdMenu());
			}
		}
		return resultado;
	}

	@Override
	public List<GrupoUsuarioDTO> listarGrupoUsuario(BaseSearch filtro) {
		return toList(this.servicio.listarGrupoUsuario(filtro), GrupoUsuarioDTO.class);
	}

	@Override
	public int contarListarGrupoUsuario(BaseSearch filtro) {
		return this.servicio.contarListarGrupoUsuario(filtro);
	}

	@Override
	public PrivilegioMenuDTO controladorAccionPrivilegioMenu(PrivilegioMenuDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionPrivilegioMenu(to(obj, PrivilegioMenu.class), accionType),
				PrivilegioMenuDTO.class);
	}

	@Override
	public List<PrivilegioMenuDTO> listarPrivilegioMenu(BaseSearch filtro) {
		return toList(this.servicio.listarPrivilegioMenu(filtro), PrivilegioMenuDTO.class, "menu", "privilegio");
	}

	@Override
	public int contarListarPrivilegioMenu(BaseSearch filtro) {
		return this.servicio.contarListarPrivilegioMenu(filtro);
	}

	@Override
	public PropertiesLenguajeDTO controladorAccionPropertiesLenguaje(PropertiesLenguajeDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionPropertiesLenguaje(to(obj, PropertiesLenguaje.class), accionType),
				PropertiesLenguajeDTO.class);
	}

	@Override
	public List<PropertiesLenguajeDTO> listarPropertiesLenguaje(BaseSearch filtro) {
		return toList(this.servicio.listarPropertiesLenguaje(filtro), PropertiesLenguajeDTO.class);
	}

	@Override
	public int contarListarPropertiesLenguaje(BaseSearch filtro) {
		return this.servicio.contarListarPropertiesLenguaje(filtro);
	}

	@Override
	public void asociarMenuPersonalizadoByUsuario(List<MenuDTO> filtro, String userName) {
		servicio.asociarMenuPersonalizadoByUsuario(toListEntity(filtro, Menu.class), userName);
	}

	@Override
	public List<MenuDTO> obtenerMenuCheck(BaseSearch filtro, String idUsuario) {
		List<MenuDTO> resultado = null;
		Map<Long, MenuPersonalizado> menuPersonalizadoMap = this.servicio.listarMenuPersonalizadoMap(idUsuario,
				EstadoGeneralState.ACTIVO.getKey());
		resultado = this.listarMenu(filtro);
		for (var objData : resultado) {
			if (menuPersonalizadoMap.containsKey(objData.getIdMenu())) {
				objData.setChecked(true);
			}

		}
		return resultado;
	}

	@Override
	public void asociarPrivilegioPersonalizadoByUsuario(List<PrivilegioDTO> filtro, String userName) {
		servicio.asociarPrivilegioPersonalizadoByUsuario(toListEntity(filtro, Privilegio.class), userName);
	}

	@Override
	public List<PrivilegioMenuDTO> obtenerPrivilegioMenuCheck(BaseSearch filtro, String idUsuario) {
		List<PrivilegioMenuDTO> resultado = null;
		Map<Long, PrivilegioPersonalizado> privilegioPersonalizadoMap = this.servicio
				.listarPrivilegioPersonalizadoMap(idUsuario, EstadoGeneralState.ACTIVO.getKey());
		resultado = listarPrivilegioMenu(filtro);
		for (var objData : resultado) {
			if (privilegioPersonalizadoMap.containsKey(objData.getPrivilegio().getIdPrivilegio())) {
				objData.setChecked(true);
			}
		}
		return resultado;
	}

	@Override
	public PropertiesDTO controladorAccionProperties(PropertiesDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionProperties(to(obj, Properties.class), accionType), PropertiesDTO.class);
	}

	@Override
	public List<PropertiesDTO> listarProperties(BaseSearch filtro) {
		return toList(this.servicio.listarProperties(filtro), PropertiesDTO.class);
	}

	@Override
	public int contarListarProperties(BaseSearch filtro) {
		return this.servicio.contarListarProperties(filtro);
	}

	@Override
	public PrivilegioDTO controladorAccionPrivilegio(PrivilegioDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionPrivilegio(to(obj, Privilegio.class), accionType), PrivilegioDTO.class);
	}

	@Override
	public List<PrivilegioDTO> listarPrivilegio(BaseSearch filtro) {
		return toList(this.servicio.listarPrivilegio(filtro), PrivilegioDTO.class);
	}

	@Override
	public int contarListarPrivilegio(BaseSearch filtro) {
		return this.servicio.contarListarPrivilegio(filtro);
	}

	@Override
	public MenuDTO controladorAccionMenu(MenuDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionMenu(to(obj, Menu.class), accionType), MenuDTO.class);
	}

	@Override
	public List<MenuDTO> listarMenu(BaseSearch filtro) {
		return toList(this.servicio.listarMenu(filtro), MenuDTO.class, "menuPadre", "sistema");
	}

	@Override
	public int contarListarMenu(BaseSearch filtro) {
		return this.servicio.contarListarMenu(filtro);
	}

	@Override
	public ConfiguracionMenuDTO controladorAccionConfiguracionMenu(ConfiguracionMenuDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionConfiguracionMenu(to(obj, ConfiguracionMenu.class), accionType),
				ConfiguracionMenuDTO.class);
	}

	@Override
	public List<ConfiguracionMenuDTO> listarConfiguracionMenu(BaseSearch filtro) {
		return toList(this.servicio.listarConfiguracionMenu(filtro), ConfiguracionMenuDTO.class);
	}

	@Override
	public int contarListarConfiguracionMenu(BaseSearch filtro) {
		return this.servicio.contarListarConfiguracionMenu(filtro);
	}

	@Override
	public SistemaDTO controladorAccionSistema(SistemaDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionSistema(to(obj, Sistema.class), accionType), SistemaDTO.class);
	}

	@Override
	public List<SistemaDTO> listarSistema(BaseSearch filtro) {
		return toList(this.servicio.listarSistema(filtro), SistemaDTO.class);
	}

	@Override
	public int contarListarSistema(BaseSearch filtro) {
		return this.servicio.contarListarSistema(filtro);
	}

	@Override
	public void asociarPrivilegioByGrupoUsuario(List<PrivilegioDTO> filtro, String userName) {
		servicio.asociarPrivilegioByGrupoUsuario(toListEntity(filtro, Privilegio.class), userName);
	}

	@Override
	public List<PrivilegioMenuDTO> obtenerPrivilegioMenuCheck(BaseSearch filtro, Long idGrupoUsuario) {
		List<PrivilegioMenuDTO> resultado = null;
		Map<Long, PrivilegioGrupoUsuario> privilegioPersonalizadoMap = this.servicio
				.listarPrivilegioGrupoUsuarioMap(idGrupoUsuario, EstadoGeneralState.ACTIVO.getKey());
		resultado = listarPrivilegioMenu(filtro);
		for (var objData : resultado) {
			if (privilegioPersonalizadoMap.containsKey(objData.getPrivilegio().getIdPrivilegio())) {
				objData.setChecked(true);
				objData.getPrivilegio().setChecked(true);
			}
		}
		return resultado;
	}

	@Override
	public int contarListarPrivilegioGrupoUsuario(BaseSearch filtro) {
		return this.servicio.contarListarPrivilegioGrupoUsuario(filtro);
	}

	@Override
	public List<UsuarioEntidadDTO> listarUsuarioEntidad(BaseSearch filtro) {
		return toList(this.servicio.listarUsuarioEntidad(filtro), UsuarioEntidadDTO.class, "entidad");
	}

}