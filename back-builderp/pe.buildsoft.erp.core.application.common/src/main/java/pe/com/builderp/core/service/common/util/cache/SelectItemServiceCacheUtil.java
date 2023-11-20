package pe.com.builderp.core.service.common.util.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.inject.Inject;
import pe.buildsoft.erp.core.application.entidades.common.ItemDTO;
import pe.buildsoft.erp.core.application.entidades.common.UbigeoDTO;
import pe.buildsoft.erp.core.application.interfaces.common.CommonAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.cache.ICache;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.SelectItemVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.type.ListaItemType;
import pe.buildsoft.erp.core.infra.transversal.type.TipoUbigeoType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.CollectionUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * <ul>
 * <li>Copyright 2012 BUILD SOFT S.A.C - BS. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Class SelectItemServiceCacheUtil.
 *
 * @author ndavilal.
 * @version 1.0 , 25/03/2012
 * @since SIAA 2.0
 */
@Startup
@Singleton
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@TransactionManagement(TransactionManagementType.BEAN)
public class SelectItemServiceCacheUtil implements ISelectItemServiceCacheUtil {

	/**
	 * Logger para el registro de errores.
	 */

	private Logger log = LoggerFactory.getLogger(SelectItemServiceCacheUtil.class);

	/** El service common impl. */
	@Inject
	private CommonAppServiceLocal servicioApp;

	@Inject
	private ICache cacheUtil;

	/** La lista item select item map. */
	private Map<Long, List<SelectItemVO>> listaItemSelectItemVOMap = new HashMap<>();

	/** La escuela nro ciclo map. */
	private Map<String, Integer> escuelaNroCicloMap = new HashMap<>();

	/** La item map. */
	private Map<Long, ItemDTO> itemMap = new HashMap<>();

	/** La item map. */
	private Map<String, ItemDTO> itemByCodigoMap = new HashMap<>();

	/** La ubigeo map. */
	private Map<String, List<SelectItemVO>> ubigeoMap = new HashMap<>();

	/** La ubigeo all map. */
	private Map<String, UbigeoDTO> ubigeoAllMap = new HashMap<>();

	/** La lista item select item map. */
	private Map<String, List<SelectItemVO>> listaSelectItemVOMap = new HashMap<>();

	/**
	 * Instancia un nuevo select item service m bean.
	 */
	public SelectItemServiceCacheUtil() {
		super();
	}

	@PostConstruct
	public void initialize() {
		inicializar();
	}

	/**
	 * Inicializar.
	 */
	public String inicializar() {
		String resultado = "";
		cargarUbigeo();
		try {
			List<ListaItemType> listaItemType = new ArrayList<>();
			for (var objType : ListaItemType.values()) {
				listaItemType.add(objType);
			}
			itemMap = new HashMap<>();
			itemByCodigoMap = new HashMap<>();
			List<ItemDTO> listaItemTemp = servicioApp.listarItem();
			for (var item : listaItemTemp) {
				itemMap.put(item.getIdItem(), item);
				cacheUtil.put("item" + item.getIdItem(), item);
				itemByCodigoMap.put(item.getListaItems().getIdListaItems() + "" + item.getCodigoExterno(), item);
			}
			listaItemSelectItemVOMap = servicioApp.convertirListaItemSelectItemMap(listaItemTemp);
			generarListaLenguajeSelect();
		} catch (Exception e) {
			log.error("ERROR", e);
			resultado = e.getMessage();
		}
		return resultado;
	}

	/**
	 * Cargar ubigeo.
	 */
	public void cargarUbigeo() {
		try {
			ubigeoMap = new HashMap<>();
			BaseSearch filter = new BaseSearch();
			filter.setSortDirections("asc");
			filter.setSortFields("descripcion");
			List<UbigeoDTO> listaUbigeoTempAll = servicioApp.listarUbigeo(filter);
			List<UbigeoDTO> listaDepartamentoTemp = new ArrayList<>();
			List<UbigeoDTO> listaUbigeoProvincia = new ArrayList<>();
			List<UbigeoDTO> listaUbigeoDistrito = new ArrayList<>();
			for (var ubigeo : listaUbigeoTempAll) {
				ubigeoAllMap.put(ubigeo.getIdUbigeo(), ubigeo);
				cacheUtil.put("ubigeo" + ubigeo.getIdUbigeo(), ubigeo);
				if (TipoUbigeoType.DEPARTAMENTO.getKey().equals(ubigeo.getTipo())) {
					listaDepartamentoTemp.add(ubigeo);
				}
				if (TipoUbigeoType.PROVINCIA.getKey().equals(ubigeo.getTipo())) {
					listaUbigeoProvincia.add(ubigeo);
				}
				if (TipoUbigeoType.DISTRITO.getKey().equals(ubigeo.getTipo())) {
					listaUbigeoDistrito.add(ubigeo);
				}
			}
			List<SelectItemVO> listaDepatamento = servicioApp.listarUbigeoSelectItem(listaDepartamentoTemp);
			List<SelectItemVO> listaProvincia = servicioApp.listarUbigeoSelectItem(listaUbigeoProvincia);
			List<SelectItemVO> listaDistrito = servicioApp.listarUbigeoSelectItem(listaUbigeoDistrito);
			this.listaSelectItemVOMap.put("ubigeoDepartamento", listaDepatamento);
			this.listaSelectItemVOMap.put("ubigeoProvincia", listaProvincia);
			this.listaSelectItemVOMap.put("ubigeoDistrito", listaDistrito);
			filter.setTipo(TipoUbigeoType.PROVINCIA.getKey());
			for (var departamento : listaDepatamento) {
				if (departamento.getId() == null) {
					continue;
				}
				boolean encontroDependencia = false;
				for (var ubigeo : listaUbigeoProvincia) {
					if (ubigeo.getUbigeoByDependencia() == null) {
						continue;
					}
					if (departamento.getId().toString().equals(ubigeo.getUbigeoByDependencia().getIdUbigeo())) {
						encontroDependencia = true;
						if (!ubigeoMap.containsKey(departamento.getId().toString())) {
							List<SelectItemVO> listaProvinciaValue = new ArrayList<>();
							listaProvinciaValue.add(new SelectItemVO(ubigeo.getIdUbigeo(), ubigeo.getDescripcion(),
									ubigeo.getUbigeoByDependencia().getIdUbigeo()));
							ubigeoMap.put(departamento.getId().toString(), listaProvinciaValue);
						} else {
							List<SelectItemVO> listaProvinciaValue = ubigeoMap.get(departamento.getId().toString());
							listaProvinciaValue.add(new SelectItemVO(ubigeo.getIdUbigeo(), ubigeo.getDescripcion(),
									ubigeo.getUbigeoByDependencia().getIdUbigeo()));
							ubigeoMap.put(departamento.getId().toString(), listaProvinciaValue);
						}
					}
				}
				if (!encontroDependencia) {
					List<SelectItemVO> listaProvinciaValue = new ArrayList<>();
					ubigeoMap.put(departamento.getId().toString(), listaProvinciaValue);
				}
			}
			filter.setTipo(TipoUbigeoType.DISTRITO.getKey());

			for (var provincia : listaUbigeoProvincia) {
				boolean encontroDependencia = false;
				for (var ubigeo : listaUbigeoDistrito) {
					if (ubigeo.getUbigeoByDependencia() == null) {
						continue;
					}
					if (provincia.getIdUbigeo().equals(ubigeo.getUbigeoByDependencia().getIdUbigeo())) {
						encontroDependencia = true;
						if (!ubigeoMap.containsKey(provincia.getIdUbigeo())) {
							List<SelectItemVO> listaDistritoValue = new ArrayList<>();
							listaDistritoValue.add(new SelectItemVO(ubigeo.getIdUbigeo(), ubigeo.getDescripcion(),
									ubigeo.getUbigeoByDependencia().getIdUbigeo()));
							ubigeoMap.put(provincia.getIdUbigeo(), listaDistritoValue);
						} else {
							List<SelectItemVO> listaDistritoValue = ubigeoMap.get(provincia.getIdUbigeo());
							listaDistritoValue.add(new SelectItemVO(ubigeo.getIdUbigeo(), ubigeo.getDescripcion(),
									ubigeo.getUbigeoByDependencia().getIdUbigeo()));
							ubigeoMap.put(provincia.getIdUbigeo(), listaDistritoValue);
						}
					}
				}
				if (!encontroDependencia) {
					List<SelectItemVO> listaDistritoValue = new ArrayList<>();
					ubigeoMap.put(provincia.getIdUbigeo(), listaDistritoValue);
				}
			}
		} catch (Exception e) {
			log.error("ERROR", e);
		}
	}

	/**
	 * Generar lista lenguaje select.
	 */
	private void generarListaLenguajeSelect() {
		List<SelectItemVO> listaLenguajeSelect = new ArrayList<>();
		List<SelectItemVO> listaLenguajeSelectTemp = listaItemSelectItemVOMap.get(ListaItemType.LENGUAJE.getKey());
		if (listaLenguajeSelectTemp != null) {
			for (var selectItemVO : listaLenguajeSelectTemp) {
				if (selectItemVO.getId() == null) {
					listaLenguajeSelect.add(selectItemVO);
				} else {
					ItemDTO item = itemMap.get(Long.valueOf(selectItemVO.getId().toString()));
					listaLenguajeSelect.add(
							new SelectItemVO(item.getCodigoExterno(), item.getNombre(), selectItemVO.getDescripcion()));
				}
			}
		}
		this.listaSelectItemVOMap.put("lenguaje", listaLenguajeSelect);
	}

	/**
	 * Actualizar item.
	 *
	 * @param item       el item
	 * @param accionType el accion type
	 */
	public void actualizarItem(ItemDTO item, AccionType accionType) {
		List<SelectItemVO> listaAgregado = new ArrayList<>();
		List<SelectItemVO> listaSelectItemVOs = listaItemSelectItemVOMap.get(item.getListaItems().getIdListaItems());
		if (listaSelectItemVOs == null) {
			listaSelectItemVOs = new ArrayList<>();
		}
		itemMap.put(item.getIdItem(), item);
		boolean existe = false;
		int index = -1;
		int posicion = -1;
		for (var selectItemVO : listaSelectItemVOs) {
			posicion++;
			if (selectItemVO.getId() == null) {
				continue;
			}
			Long key = Long.valueOf(selectItemVO.getId().toString());
			if (item.getIdItem().equals(key)) {
				selectItemVO.setNombre(item.getNombre());
				selectItemVO.setDescripcion(item.getCodigo() + "");
				index = posicion;
				existe = true;
				break;
			}
		}
		if (!existe && !AccionType.ELIMINAR.equals(accionType)) {
			listaAgregado.add(new SelectItemVO(item.getIdItem(), item.getNombre(), item.getCodigo() + ""));
		}
		if (AccionType.ELIMINAR.equals(accionType) && index >= 0) {
			listaSelectItemVOs.remove(index);
		}
		listaSelectItemVOs.addAll(listaAgregado);
		CollectionUtil.ordenador(false, listaSelectItemVOs, "nombre");
		listaItemSelectItemVOMap.put(item.getListaItems().getIdListaItems(), listaSelectItemVOs);
		if (ListaItemType.LENGUAJE.getKey().equals(item.getListaItems().getIdListaItems())) {
			generarListaLenguajeSelect();
		}
	}

	/**
	 * Obtener item.
	 *
	 * @param id el id
	 * @return the item
	 */
	public ItemDTO obtenerItem(Long id) {
		return itemMap.get(id);
	}

	public ItemDTO obtenerItemByCodigo(String codigo) {
		return itemByCodigoMap.get(codigo);
	}

	/**
	 * Obtener ubigeo by id.
	 *
	 * @param id el id
	 * @return the ubigeo
	 */
	public UbigeoDTO obtenerUbigeoById(String id) {
		return ubigeoAllMap.get(id);
	}

	/**
	 * Obtener selec cbo item.
	 *
	 * @param itemType el item type
	 * @return the list
	 */
	public List<SelectItemVO> obtenerSelecCboItem(ListaItemType itemType) {
		return listaItemSelectItemVOMap.get(itemType.getKey());
	}

	public List<SelectItemVO> obtenerSelecCboItem(Long idListaItem) {
		return listaItemSelectItemVOMap.get(idListaItem);
	}

	public List<SelectItemVO> obtenerSeleItemVOByKey(String keySelectItemVO) {
		return listaSelectItemVOMap.get(keySelectItemVO);
	}

	/**
	 * Obtener selec cbo item.
	 *
	 * @param itemType el item type
	 * @param id       el id
	 * @return the list
	 */
	public List<SelectItemVO> obtenerSelecCboItem(ListaItemType itemType, Object id) {
		if (ListaItemType.CICLO.equals(itemType)) {
			List<SelectItemVO> resultado = new ArrayList<>();
			List<SelectItemVO> resultadoTemp = listaItemSelectItemVOMap.get(itemType.getKey());
			Integer nroCiclo = escuelaNroCicloMap.get(id.toString());
			for (var selectItemVO : resultadoTemp) {
				if (StringUtil.isNotNullOrBlank(selectItemVO.getDescripcion())) {
					Integer codigo = Integer.parseInt(selectItemVO.getDescripcion());
					if (codigo <= nroCiclo) {
						resultado.add(selectItemVO);
					}

				}
			}
			return resultado;
		}
		return listaItemSelectItemVOMap.get(itemType.getKey());
	}

	/**
	 * Obtener selec rb item.
	 *
	 * @param itemType el item type
	 * @return the list
	 */
	public List<SelectItemVO> obtenerSelecRbItem(ListaItemType itemType) {
		return listaItemSelectItemVOMap.get(itemType.getKey());
	}

	/**
	 * Obtener selec ubigeo dependencia.
	 *
	 * @param id el id
	 * @return the list
	 */
	public List<SelectItemVO> obtenerSelecUbigeoDependencia(String id) {
		return ubigeoMap.get(id);
	}

	public List<ItemDTO> converItemDTO(List<SelectItemVO> listaSelectItemVO) {
		List<ItemDTO> resultado = new ArrayList<>();
		for (var selectItemVO : listaSelectItemVO) {
			Long id = Long.valueOf(selectItemVO.getId() + "");
			resultado.add(obtenerItem(id));
		}
		return resultado;
	}

}