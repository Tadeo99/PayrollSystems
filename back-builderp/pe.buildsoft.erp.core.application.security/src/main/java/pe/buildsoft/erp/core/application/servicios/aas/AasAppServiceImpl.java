package pe.buildsoft.erp.core.application.servicios.aas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import pe.buildsoft.erp.core.application.entidades.aas.vo.ConfiguracionMenuVO;
import pe.buildsoft.erp.core.application.entidades.aas.vo.NavigationItemVO;
import pe.buildsoft.erp.core.application.entidades.common.ItemDTO;
import pe.buildsoft.erp.core.application.entidades.security.ConfiguracionMenuDTO;
import pe.buildsoft.erp.core.application.entidades.security.EntidadDTO;
import pe.buildsoft.erp.core.application.entidades.security.MenuDTO;
import pe.buildsoft.erp.core.application.entidades.security.PropertiesDTO;
import pe.buildsoft.erp.core.application.entidades.security.UsuarioDTO;
import pe.buildsoft.erp.core.application.interfaces.aas.AasAppServiceLocal;
import pe.buildsoft.erp.core.domain.entidades.security.ConfiguracionMenu;
import pe.buildsoft.erp.core.domain.entidades.security.PropertiesLenguaje;
import pe.buildsoft.erp.core.domain.entidades.security.UsuarioEntidad;
import pe.buildsoft.erp.core.domain.interfaces.servicios.aas.AasServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.cache.AtributosEntityCacheUtil;
import pe.buildsoft.erp.core.infra.transversal.cache.ICache;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.SelectItemVO;
import pe.buildsoft.erp.core.infra.transversal.type.TipoComponenteType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.BaseTransfer;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * La Class AasServiceImpl.
 * <ul>
 * <li>Copyright 2019 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, 08/01/2019
 * @since SIAA-CORE 2.1
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class AasAppServiceImpl extends BaseTransfer implements AasAppServiceLocal {

	/** El servicio aas service impl. */
	@Inject
	private AasServiceLocal servicio;

	@Inject
	private ICache cacheUtil;

	public AasAppServiceImpl() {
		AtributosEntityCacheUtil.getInstance().sincronizarAtributos("pe.buildsoft.erp.core.domain.entidades.security",
				"pe.buildsoft.erp.core.domain.entidades.common");
	}

	@Override
	public EntidadDTO finByIdEntidad(EntidadDTO entidadDTO) {
		return to(servicio.finByIdEntidad(entidadDTO.getIdEntidad()), EntidadDTO.class);
	}

	@Override
	public UsuarioDTO validarLogin(String userName, String userPassword) {
		UsuarioDTO resultado = toDTO(servicio.validarLogin(userName, userPassword), UsuarioDTO.class, "tipoUsuario");
		if (resultado != null && resultado.getIdUsuario() != null) {
			resultado.setPrivilegiosMap(servicio.obtenerPrivilegiosUsuario(resultado.getIdUsuario()));
			resultado.setListaMenu(obtenerMenuUsuario(resultado.getIdUsuario()));
			List<String> listaIdUsuario = new ArrayList<>();
			listaIdUsuario.add(resultado.getIdUsuario());
			resultado.setEntidadSelect(obtenerEntidadDisponible(resultado.getIdUsuario()));
			resultado.setIdEntidadSelect((String) resultado.getEntidadSelect().get(0).getId());
		}
		return resultado;
	}

	private List<SelectItemVO> obtenerEntidadDisponible(String idUsuario) {
		List<SelectItemVO> resultado = new ArrayList<>();
		var filtro = new BaseSearch();
		filtro.setId(idUsuario);
		List<UsuarioEntidad> listaTmp = servicio.listarUsuarioEntidad(filtro);
		for (var obj : listaTmp) {
			resultado.add(new SelectItemVO(obj.getEntidad().getIdEntidad(), obj.getEntidad().getNombre()));
		}
		return resultado;
	}

	@Override
	public List<ConfiguracionMenuVO> obtenerConfiguracionMenu(Long idMenu) {
		List<ConfiguracionMenuVO> resultado = new ArrayList<>();
		Map<Long, List<ConfiguracionMenuDTO>> resultadoMap = new HashMap<>();
		List<ConfiguracionMenu> listaConfiguracionMenuTemp = servicio.obtenerConfiguracionMenu(idMenu);
		List<ConfiguracionMenuDTO> listaConfiguracionMenu = toList(listaConfiguracionMenuTemp,
				ConfiguracionMenuDTO.class, "itemByComponente", "properties");
		List<Long> listaIdProperties = new ArrayList<>();
		for (var obj : listaConfiguracionMenu) {
			listaIdProperties.add(obj.getProperties().getIdProperties());
		}
		if (listaConfiguracionMenu != null) {
			for (var objConf : listaConfiguracionMenu) {
				if (!resultadoMap.containsKey(objConf.getItemByComponente().getIdItem())) {
					List<ConfiguracionMenuDTO> value = new ArrayList<>();
					value.add(objConf);
					resultadoMap.put(objConf.getItemByComponente().getIdItem(), value);
					ConfiguracionMenuVO configuracionMenuDTO = new ConfiguracionMenuVO();
					configuracionMenuDTO.setId(objConf.getItemByComponente().getIdItem());
					configuracionMenuDTO.setCodigo(objConf.getItemByComponente().getCodigo());
					configuracionMenuDTO.setDescripcion(objConf.getItemByComponente().getNombre());
					resultado.add(configuracionMenuDTO);
				} else {
					List<ConfiguracionMenuDTO> value = resultadoMap.get(objConf.getItemByComponente().getIdItem());
					value.add(objConf);
					resultadoMap.put(objConf.getItemByComponente().getIdItem(), value);
				}
			}
			// cargando lista componente con su lista configuracion menu
			generarReglaView(resultado, resultadoMap);

		}

		return resultado;
	}

	private List<ConfiguracionMenuVO> generarReglaView(List<ConfiguracionMenuVO> resultado,
			Map<Long, List<ConfiguracionMenuDTO>> resultadoMap) {
		for (var obj : resultado) {
			TipoComponenteType componenteType = TipoComponenteType.get(obj.getCodigo());
			obj.setListaConfiguracionMenus(resultadoMap.get(obj.getId()));
			for (var objConfi : obj.getListaConfiguracionMenus()) {
				if (componenteType != null) {
					switch (componenteType) {
					case LABEL:
						objConfi.setShowRequired(false);
						objConfi.setShowReadonly(false);
						objConfi.setShowRendered(true);
						objConfi.setShowDisabled(false);
						break;
					case INPUT:
						objConfi.setShowRequired(true);
						objConfi.setShowReadonly(true);
						objConfi.setShowRendered(true);
						objConfi.setShowDisabled(true);
						break;

					case BUTTON:
						objConfi.setShowRequired(false);
						objConfi.setShowReadonly(false);
						objConfi.setShowRendered(true);
						objConfi.setShowDisabled(true);
						break;

					default:
						break;
					}
				}
			}
		}
		return resultado;

	}

	@Override
	public Map<String, Map<String, String>> obtenerPropertiesLenguajeAllMap() {
		Map<String, Map<String, String>> resultado = new HashMap<>();
		List<PropertiesLenguaje> resul = servicio.obtenerPropertiesLenguajeAllMap();
		for (var objData : resul) {
			String key = objData.getProperties().getName();
			ItemDTO lenguaje = (ItemDTO) cacheUtil.get("item" + objData.getIdItemByLenguaje());
			String keyIdeoma = lenguaje.getCodigoExterno();
			if (!resultado.containsKey(keyIdeoma)) {
				Map<String, String> ideomaMap = new HashMap<>();
				ideomaMap.put(key, objData.getValue());
				resultado.put(keyIdeoma, ideomaMap);
			} else {
				Map<String, String> ideomaMap = resultado.get(keyIdeoma);
				ideomaMap.put(key, objData.getValue());
				resultado.put(keyIdeoma, ideomaMap);
			}
		}
		return resultado;
	}

	@Override
	public List<PropertiesDTO> obtenerPropertiesLenguaje(BaseSearch filtro) {
		return toList(this.servicio.obtenerPropertiesLenguaje(filtro), PropertiesDTO.class);
	}

	public List<NavigationItemVO> obtenerMenuUsuario(String idUsuario) {
		List<NavigationItemVO> resultado = new ArrayList<>();
		List<MenuDTO> listaMenu = generarMenuGrupo(
				toList(servicio.obtenerMenuUsuario(idUsuario), MenuDTO.class, "sistema", "menuPadre"));
		if (listaMenu != null) {
			resultado = generarMenu(listaMenu);
		}
		return resultado;
	}

	private List<MenuDTO> generarMenuGrupo(List<MenuDTO> listaMenu) {
		List<MenuDTO> resultado = new ArrayList<>();
		Map<Long, MenuDTO> resultadoMap = new HashMap<>();
		for (var obj : listaMenu) {
			Long key = obj.getSistema().getIdSistema();
			if (!resultadoMap.containsKey(key)) {
				MenuDTO value = new MenuDTO();
				value.setMenuPadre(new MenuDTO());
				value.setIdMenu(100 * obj.getIdMenu());
				obj.setMenuPadre(value);
				value.setIcono(obj.getSistema().getIcono());
				value.setNombre(obj.getSistema().getNombre());
				value.setDescripcion(obj.getSistema().getDescripcion());
				value.setMenuHijos(new ArrayList<MenuDTO>());
				value.getMenuHijos().add(obj);
				resultadoMap.put(key, value);
				resultado.add(value);
			} else {
				MenuDTO value = resultadoMap.get(key);
				obj.setMenuPadre(value);
				value.getMenuHijos().add(obj);
			}
		}
		return resultado;

	}

	/**
	 * Generar menu.
	 *
	 * @param listaMenu el lista menu
	 * @return the list
	 */
	private List<NavigationItemVO> generarMenu(List<MenuDTO> listaMenu) {
		List<NavigationItemVO> resultado = new ArrayList<>();
		for (var menu : listaMenu) {
			if (menu.getMenuPadre().getIdMenu() == null) {
				NavigationItemVO obj = parseMenu(menu);
				obj.setChildren(generarSubMenu(menu.getMenuHijos(), menu.getIdMenu()));
				resultado.add(obj);
			}
		}
		return resultado;
	}

	private NavigationItemVO parseMenu(MenuDTO menu) {
		NavigationItemVO obj = new NavigationItemVO();
		obj.setIcon(menu.getIcono());
		obj.setId(menu.getNombre().toLowerCase());
		if (menu.getMenuPadre() != null && StringUtil.isNotNullOrBlank(menu.getMenuPadre().getIdMenu())) {
			obj.setId(menu.getMenuPadre().getNombre().toLowerCase() + "." + menu.getNombre().toLowerCase());
		}
		obj.setTitle(menu.getNombre());
		obj.setLink(menu.getUrl());
		obj.setType("basic");
		if (!StringUtil.isNotNullOrBlank(menu.getUrl())) {
			obj.setType("collapsable");
		}
		if (!obj.getId().contains(".")) {
			obj.setType("collapsable");// group
		}
		return obj;
	}

	/**
	 * Generar sub menu.
	 *
	 * @param listaMenu   el lista menu
	 * @param idMenuPadre el id menu padre
	 * @return the list
	 */
	private List<NavigationItemVO> generarSubMenu(List<MenuDTO> listaMenu, Long idMenuPadre) {
		List<NavigationItemVO> resultado = new ArrayList<>();
		for (var menu : listaMenu) {
			if (menu.getMenuPadre().getIdMenu() != null && (menu.getMenuPadre().getIdMenu().equals(idMenuPadre))) {
				NavigationItemVO obj = parseMenu(menu);
				obj.setChildren(generarSubMenu(listaMenu, menu.getIdMenu()));
				resultado.add(obj);
			}
		}
		return resultado;
	}

}