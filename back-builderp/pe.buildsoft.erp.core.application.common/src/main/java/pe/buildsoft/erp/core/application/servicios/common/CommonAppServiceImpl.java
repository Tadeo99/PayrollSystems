package pe.buildsoft.erp.core.application.servicios.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import pe.buildsoft.erp.core.application.entidades.common.AnhioDTO;
import pe.buildsoft.erp.core.application.entidades.common.ConfiguracionAtributoDTO;
import pe.buildsoft.erp.core.application.entidades.common.ItemDTO;
import pe.buildsoft.erp.core.application.entidades.common.ListaItemsDTO;
import pe.buildsoft.erp.core.application.entidades.common.ListaValoresDTO;
import pe.buildsoft.erp.core.application.entidades.common.ParametroDTO;
import pe.buildsoft.erp.core.application.entidades.common.UbigeoDTO;
import pe.buildsoft.erp.core.application.interfaces.common.CommonAppServiceLocal;
import pe.buildsoft.erp.core.domain.common.type.TipoComponenteAtributoType;
import pe.buildsoft.erp.core.domain.common.type.TipoDatoType;
import pe.buildsoft.erp.core.domain.entidades.common.Anhio;
import pe.buildsoft.erp.core.domain.entidades.common.ConfiguracionAtributo;
import pe.buildsoft.erp.core.domain.entidades.common.ConfiguracionAtributoValue;
import pe.buildsoft.erp.core.domain.entidades.common.Item;
import pe.buildsoft.erp.core.domain.entidades.common.ListaItems;
import pe.buildsoft.erp.core.domain.entidades.common.ListaValores;
import pe.buildsoft.erp.core.domain.entidades.common.Parametro;
import pe.buildsoft.erp.core.domain.entidades.common.Ubigeo;
import pe.buildsoft.erp.core.domain.interfaces.servicios.common.CommonServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.cache.AtributosEntityCacheUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.FileVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.SelectItemVO;
import pe.buildsoft.erp.core.infra.transversal.state.EstadoGeneralState;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.BaseTransfer;
import pe.buildsoft.erp.core.infra.transversal.utilitario.CollectionUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.FechaUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ObjectUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.com.builderp.core.service.common.util.cache.ISelectItemServiceCacheUtil;

/**
 * La Class CommonAppServiceImpl.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue Apr 18 13:43:23 COT 2017
 * @since SIAA-CORE 2.1
 */
@Stateless
public class CommonAppServiceImpl extends BaseTransfer implements CommonAppServiceLocal {

	private Logger log = LoggerFactory.getLogger(CommonAppServiceImpl.class);

	private static final String SUBLIBRO = "SUBLIBRO";

	private static final String LIBRO = "LIBRO";

	private static final String LISTA_ITEMS = "listaItems";

	private static final String UBIGEO_BY_DEPENDENCIA = "ubigeoByDependencia";

	@Inject
	private CommonServiceLocal servicio;

	@Inject
	private ISelectItemServiceCacheUtil cacheItem;

	public CommonAppServiceImpl() {
		AtributosEntityCacheUtil.getInstance().sincronizarAtributos("pe.buildsoft.erp.core.domain.entidades.common",
				"pe.buildsoft.erp.core.domain.entidades.security");
	}

	@Override
	public List<SelectItemVO> listarSelectItem(String groupName, BaseSearch filtro) {
		List<SelectItemVO> resultado = new ArrayList<>();
		switch (groupName) {
		case "anhio":
			resultado = listarAnhioSelectItem(filtro);
			break;

		case "departamento":
			resultado = listarDepartamentoSelectItem(filtro);
			break;

		case "provincia":
			resultado = listarProvinciaSelectItem(filtro);
			break;

		case "distrito":
			resultado = listarDistritoSelectItem(filtro);
			break;

		case "item":
			resultado = listarItemSelectItem(filtro);
			break;

		default:
			break;
		}
		return resultado;
	}

	@Override
	public int contarSelectItem(String groupName, BaseSearch filtro) {
		var resultado = 0;
		switch (groupName) {
		case "anhio":
			resultado = servicio.contarListarAnhio(filtro);
			break;

		case "item":
			resultado = servicio.contarListarItem(filtro);
			break;

		default:
			break;
		}
		return resultado;
	}

	private List<SelectItemVO> listarDistritoSelectItem(BaseSearch filtro) {
		return cacheItem.obtenerSeleItemVOByKey("ubigeoDistrito");
	}

	private List<SelectItemVO> listarItemSelectItem(BaseSearch filtro) {
		List<SelectItemVO> resultado = new ArrayList<>();
		var resulTmp = servicio.listarItem(filtro);
		for (var obj : resulTmp) {
			resultado.add(new SelectItemVO(obj.getIdItem(), obj.getNombre()));
		}
		return resultado;
	}

	private List<SelectItemVO> listarProvinciaSelectItem(BaseSearch filtro) {
		return cacheItem.obtenerSeleItemVOByKey("ubigeoProvincia");
	}

	private List<SelectItemVO> listarDepartamentoSelectItem(BaseSearch filtro) {
		return cacheItem.obtenerSeleItemVOByKey("ubigeoDepartamento");
	}

	private List<SelectItemVO> listarAnhioSelectItem(BaseSearch filtro) {
		if (!StringUtil.isNotNullOrBlank(filtro.getSortFields())) {
			filtro.setSortFields("idAnhio");
		}
		if (!StringUtil.isNotNullOrBlank(filtro.getSortDirections())) {
			filtro.setSortDirections("asc");
		}
		List<SelectItemVO> resultado = new ArrayList<>();
		var resulTmp = servicio.listarAnhio(filtro);
		for (var obj : resulTmp) {
			resultado
					.add(new SelectItemVO(obj.getIdAnhio(), obj.getIdAnhio() + " " + obj.getNombre(), obj.getEstado()));
		}
		return resultado;
	}

	@Override
	public ConfiguracionAtributoDTO controladorAccionConfiguracionAtributo(ConfiguracionAtributoDTO obj,
			AccionType accionType) {
		return toDTO(servicio.controladorAccionConfiguracionAtributo(to(obj, ConfiguracionAtributo.class), accionType),
				ConfiguracionAtributoDTO.class);
	}

	@Override
	public List<ConfiguracionAtributoDTO> listarConfiguracionAtributo(BaseSearch filtro) {
		var resultado = toList(this.servicio.listarConfiguracionAtributo(filtro), ConfiguracionAtributoDTO.class,
				"itemByIdNombreEntidad", "itemByIdComponte", "listaItem");
		if (StringUtil.isNotNullOrBlank(filtro.getId())) {
			generarDataValue(filtro.getIdTupla(), resultado);
		}
		return resultado;
	}

	private List<ConfiguracionAtributoDTO> generarDataValue(String idTupla, List<ConfiguracionAtributoDTO> resultado) {
		try {
			List<String> listaIdConfiguracionAtributo = new ArrayList<>();
			for (var objData : resultado) {
				generarDescripcionViewItem(objData.getItemByIdNombreEntidad());
				generarDescripcionViewItem(objData.getItemByIdComponte());
				generarDescripcionViewItem(objData.getItemByIdComponte());
				generarDescripcionViewListaItems(objData.getListaItem());
				listaIdConfiguracionAtributo.add(objData.getIdConfiguracionAtributo());
			}
			var listaDataValueMap = servicio.listarConfiguracionAtributoValueMap(listaIdConfiguracionAtributo, idTupla);
			for (var objData : resultado) {
				var key = objData.getIdConfiguracionAtributo();
				if (listaDataValueMap.containsKey(key)) {
					var dataValue = listaDataValueMap.get(key);
					var tipoComponenteAtributoType = TipoComponenteAtributoType
							.get(objData.getItemByIdComponte().getNombre());
					switch (tipoComponenteAtributoType) {
					case INPUT:
					case TEXTAREA:
						if (TipoDatoType.NUMERICO.getKey().equals(objData.getTipoDato())) {
							if (StringUtil.isNotNullOrBlank(dataValue.get(0).getValue())) {
								objData.setValueNumeric(ObjectUtil.objectToBigDecimal(dataValue.get(0).getValue()));
							}
						} else {
							objData.setValueText(dataValue.get(0).getValue());
						}
						break;
					case DATEPICKER:
						if (StringUtil.isNotNullOrBlank(dataValue.get(0).getValue())) {
							objData.setValueDate(FechaUtil.obtenerFechaFormatoPersonalizado(dataValue.get(0).getValue(),
									objData.getFormato()));
						}
						break;
					case RADIO_BUTTONS:
					case SELECT:
						if (StringUtil.isNotNullOrBlank(dataValue.get(0).getValue())) {
							objData.setValueNumeric(ObjectUtil.objectToBigDecimal(dataValue.get(0).getValue()));
						}
						break;
					case MODAL:
						if (StringUtil.isNotNullOrBlank(dataValue.get(0).getValue())) {
							objData.setItemAtributoValue(
									cacheItem.obtenerItem(ObjectUtil.objectToLong(dataValue.get(0).getValue())));
							generarDescripcionViewItem(objData.getItemAtributoValue());
						}
						break;
					case SLIDE_TOGGLE:
					case CHECKBOX:
						objData.setValueBoolean(Boolean.valueOf(dataValue.get(0).getValue()));
						break;
					case SELECT_MULTIPLE:
					case MODAL_MULTIPLE:
						if (objData.getItemByIdComponte().getNombre()
								.equals(TipoComponenteAtributoType.MODAL_MULTIPLE.getKey())) {
							objData.setItemAtributoValue(new ItemDTO());
						}
						objData.setListaSelectItemSelectedVO(new ArrayList<>());
						int index = 0;
						for (var objValue : dataValue) {
							index++;
							if (objData.getItemByIdComponte().getNombre()
									.equals(TipoComponenteAtributoType.MODAL_MULTIPLE.getKey())) {
								var item = cacheItem.obtenerItem(ObjectUtil.objectToLong(objValue.getValue()));
								if (index == 1) {
									objData.getItemAtributoValue().setDescripcionView(item.getNombre());
								} else {
									objData.getItemAtributoValue()
											.setDescripcionView(objData.getItemAtributoValue().getDescripcionView()
													+ "," + item.getNombre());
								}
							}
							objData.getListaSelectItemSelectedVO().add(ObjectUtil.objectToLong(objValue.getValue()));
						}
						break;

					default:
						break;
					}
				}
			}
		} catch (Exception e) {
			log.error("generarDataValue", e);
		}
		return resultado;
	}

	private ListaItemsDTO generarDescripcionViewListaItems(ListaItemsDTO obj) {
		if (obj != null && obj.getIdListaItems() != null) {
			obj.setDescripcionView(obj.getIdListaItems() + " " + obj.getDescripcion());
		}
		return obj;
	}

	private ItemDTO generarDescripcionViewItem(ItemDTO item) {
		if (item != null && item.getIdItem() != null) {
			item.setDescripcionView(item.getCodigoExterno() + " " + item.getNombre());
		}
		return item;
	}

	@Override
	public int contarListarConfiguracionAtributo(BaseSearch filtro) {
		return this.servicio.contarListarConfiguracionAtributo(filtro);
	}

	@Override
	public void registrarConfiguracionAtributoValue(List<ConfiguracionAtributoDTO> listaDataDinamic) {
		this.servicio.registrarConfiguracionAtributoValue(toListEntity(listaDataDinamic, ConfiguracionAtributo.class));
	}

	@Override
	public void eliminarConfiguracionAtributoValue(List<ConfiguracionAtributoDTO> listaDataDinamic) {
		this.servicio.eliminarConfiguracionAtributoValue(toListEntity(listaDataDinamic, ConfiguracionAtributo.class));
	}

	@Override
	public void subirImagen(FileVO fileVO) {
		servicio.subirImagen(fileVO);
	}

	@Override
	public String obtenerImagenEncodeBase64(FileVO fileVO) {
		return servicio.obtenerImagenEncodeBase64(fileVO);
	}

	@Override
	public ParametroDTO controladorAccionParametro(ParametroDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionParametro(to(obj, Parametro.class), accionType), ParametroDTO.class);
	}

	@Override
	public List<ParametroDTO> listarParametro(BaseSearch filtro) {
		return toList(this.servicio.listarParametro(filtro), ParametroDTO.class);
	}

	@Override
	public int contarListarParametro(BaseSearch filtro) {
		return this.servicio.contarListarParametro(filtro);
	}

	@Override
	public List<SelectItemVO> listarUbigeoSelectItem(List<UbigeoDTO> listaUbigeo) {
		List<SelectItemVO> resultado = new ArrayList<>();
		for (var ubigeoResul : listaUbigeo) {
			resultado.add(new SelectItemVO(ubigeoResul.getIdUbigeo(), ubigeoResul.getDescripcion(),
					ubigeoResul.getUbigeoByDependencia() != null ? ubigeoResul.getUbigeoByDependencia().getIdUbigeo()
							: ""));
		}
		return resultado;
	}

	@Override
	public Map<Long, List<SelectItemVO>> convertirListaItemSelectItemMap(List<ItemDTO> listaItem) {
		Map<Long, List<SelectItemVO>> resultado = new HashMap<>();
		if (!CollectionUtil.isEmpty(listaItem)) {
			for (var items : listaItem) {
				var idListaItemActual = items.getListaItems().getIdListaItems();
				if (!resultado.containsKey(idListaItemActual)) {
					var value = new ArrayList<SelectItemVO>();
					value.add(new SelectItemVO(items.getIdItem(), items.getNombre(), items.getCodigo() + ""));
					resultado.put(idListaItemActual, value);
				} else {
					var value = resultado.get(idListaItemActual);
					value.add(new SelectItemVO(items.getIdItem(), items.getNombre(), items.getCodigo() + ""));
					resultado.put(idListaItemActual, value);
				}
			}

		}
		return resultado;
	}

	@Override
	public List<ItemDTO> listarItem() {
		return toList(this.servicio.listarItem(), ItemDTO.class, LISTA_ITEMS);
	}

	@Override
	public ListaItemsDTO controladorAccionListaItems(ListaItemsDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionListaItems(to(obj, ListaItems.class), accionType), ListaItemsDTO.class);
	}

	@Override
	public List<ListaItemsDTO> listarListaItems(BaseSearch filtro) {
		return toList(this.servicio.listarListaItems(filtro), ListaItemsDTO.class);
	}

	@Override
	public int contarListarListaItems(BaseSearch filtro) {
		return this.servicio.contarListarListaItems(filtro);
	}

	@Override
	public ItemDTO controladorAccionItem(ItemDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionItem(to(obj, Item.class), accionType), ItemDTO.class);
	}

	@Override
	public List<ItemDTO> listarItem(BaseSearch filtro) {
		var resultado = toList(this.servicio.listarItem(filtro), ItemDTO.class);
		if (!StringUtil.isNullOrEmpty(filtro.getTipo())
				&& (LIBRO.equalsIgnoreCase(filtro.getTipo()) || SUBLIBRO.equalsIgnoreCase(filtro.getTipo()))) {
			var listaIdItem = new ArrayList<>();
			for (var itemDTO : resultado) {
				listaIdItem.add(itemDTO.getIdItem());
			}
			// TODO:CAMBIAR_FORMA_NATAN
			/*
			 * Map<Long,ConfiguracionCuenta> configuracionCuentaMap =
			 * this.configuracionCuentaDaoImpl.listarConfiguracionCuentaMap(listaIdItem,
			 * EstadoGeneralState.ACTIVO); for (var itemDTO : resultado) { if
			 * (configuracionCuentaMap.containsKey(itemDTO.getIdItem())) {
			 * itemDTO.setPlanContable(TransferDataObjectUtil.transferObjetoEntityDTO(
			 * configuracionCuentaMap.get(itemDTO.getIdItem()).getPlanContable(),
			 * PlanContableDTO.class)); } }
			 */
		}
		for (var itemDTO : resultado) {
			itemDTO.setDescripcionView(itemDTO.getCodigoExterno() + " " + itemDTO.getNombre());
		}
		return resultado;
	}

	@Override
	public int contarListarItem(BaseSearch filtro) {
		return this.servicio.contarListarItem(filtro);
	}

	@Override
	public UbigeoDTO controladorAccionUbigeo(UbigeoDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionUbigeo(to(obj, Ubigeo.class), accionType), UbigeoDTO.class);
	}

	@Override
	public List<UbigeoDTO> listarUbigeo(BaseSearch filtro) {
		var resultado = toList(this.servicio.listarUbigeo(filtro), UbigeoDTO.class, UBIGEO_BY_DEPENDENCIA);
		for (var obj : resultado) {
			obj.setDescripcionView(obj.getIdUbigeo() + "" + obj.getDescripcion());
			generarDescripcionViewUbigeo(obj.getUbigeoByDependencia());
		}
		return resultado;
	}

	private UbigeoDTO generarDescripcionViewUbigeo(UbigeoDTO obj) {
		if (obj != null) {
			obj.setDescripcionView(obj.getIdUbigeo() + " " + obj.getDescripcion());
		}
		return obj;
	}

	@Override
	public int contarListarUbigeo(BaseSearch filtro) {
		return this.servicio.contarListarUbigeo(filtro);
	}

	@Override
	public ListaValoresDTO controladorAccionListaValores(ListaValoresDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionListaValores(to(obj, ListaValores.class), accionType),
				ListaValoresDTO.class);
	}

	@Override
	public List<ListaValoresDTO> listarListaValores(BaseSearch filtro) {
		return toList(this.servicio.listarListaValores(filtro), ListaValoresDTO.class);
	}

	@Override
	public int contarListarListaValores(BaseSearch filtro) {
		return this.servicio.contarListarListaValores(filtro);
	}

	@Override
	public AnhioDTO controladorAccionAnhio(AnhioDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionAnhio(to(obj, Anhio.class), accionType), AnhioDTO.class);
	}

	@Override
	public List<AnhioDTO> listarAnhio(BaseSearch filtro) {
		var resultado = toList(this.servicio.listarAnhio(filtro), AnhioDTO.class);
		for (var obj : resultado) {
			generarDescripcionViewAnhio(obj);
		}
		return resultado;
	}

	@Override
	public int contarListarAnhio(BaseSearch filtro) {
		return this.servicio.contarListarAnhio(filtro);
	}

	private AnhioDTO generarDescripcionViewAnhio(AnhioDTO obj) {
		obj.setDescripcionView(obj.getIdAnhio() + " " + obj.getNombre());
		return obj;
	}

	@Override
	public AnhioDTO obtenerAnhioyEstado(EstadoGeneralState estadoAnhoState) {
		return toDTO(servicio.obtenerAnhioyEstado(estadoAnhoState), AnhioDTO.class);
	}

}