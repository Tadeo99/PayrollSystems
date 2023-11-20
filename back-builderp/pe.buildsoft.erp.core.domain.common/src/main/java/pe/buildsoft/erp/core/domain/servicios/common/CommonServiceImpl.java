package pe.buildsoft.erp.core.domain.servicios.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.inject.Inject;
import jakarta.ejb.Stateless;

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
import pe.buildsoft.erp.core.domain.interfaces.repositories.common.AnhioDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.common.ConfiguracionAtributoDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.common.ConfiguracionAtributoValueDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.common.ItemDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.common.ListaItemsDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.common.ListaValoresDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.common.ParametroDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.common.UbigeoDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.servicios.common.CommonServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.FileVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.SelectItemVO;
import pe.buildsoft.erp.core.infra.transversal.state.EstadoGeneralState;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.CollectionUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.FechaUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * La Class CommonServiceImpl.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue Apr 18 13:43:23 COT 2017
 * @since SIAA-CORE 2.1
 */
@Stateless
public class CommonServiceImpl implements CommonServiceLocal {

	/** El servicio lista items dao impl. */
	@Inject
	private ListaItemsDaoLocal listaItemsDaoImpl;

	/** El servicio item dao impl. */
	@Inject
	private ItemDaoLocal itemDaoImpl;

	/** El servicio ubigeo dao impl. */
	@Inject
	private UbigeoDaoLocal ubigeoDaoImpl;

	/** El servicio lista valores dao impl. */
	@Inject
	private ListaValoresDaoLocal listaValoresDaoImpl;

	/** El servicio configuracion cuenta dao impl. */
	// @Inject
	// private ConfiguracionCuentaDaoLocal configuracionCuentaDaoImpl;
	// //TODO:CAMBIAR_FORMA_NATAN

	/** El servicio parametro dao impl. */
	@Inject
	private ParametroDaoLocal parametroDaoImpl;

	/** El servicio configuracion atributo dao impl. */
	@Inject
	private ConfiguracionAtributoDaoLocal configuracionAtributoDaoImpl;

	/** El servicio configuracion atributo value dao impl. */
	@Inject
	private ConfiguracionAtributoValueDaoLocal configuracionAtributoValueDaoImpl;

	/** El servicio anhio dao impl. */
	@Inject
	private AnhioDaoLocal anhioDaoImpl;

	@Override
	public ConfiguracionAtributo controladorAccionConfiguracionAtributo(ConfiguracionAtributo obj,
			AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdConfiguracionAtributo(this.configuracionAtributoDaoImpl.generarId());
			this.configuracionAtributoDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.configuracionAtributoDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.configuracionAtributoDaoImpl.find(ConfiguracionAtributo.class, obj.getIdConfiguracionAtributo());
			this.configuracionAtributoDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.configuracionAtributoDaoImpl.find(ConfiguracionAtributo.class, obj.getIdConfiguracionAtributo());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<ConfiguracionAtributo> listarConfiguracionAtributo(BaseSearch filtro) {
		return this.configuracionAtributoDaoImpl.listar(filtro);
	}

	@Override
	public Map<String, List<ConfiguracionAtributoValue>> listarConfiguracionAtributoValueMap(
			List<String> listaIdConfiguracionAtributo, String idTupla) {
		return this.configuracionAtributoValueDaoImpl.listar(listaIdConfiguracionAtributo, idTupla);
	}

	@Override
	public int contarListarConfiguracionAtributo(BaseSearch filtro) {
		return this.configuracionAtributoDaoImpl.contar(filtro);
	}

	@Override
	public void registrarConfiguracionAtributoValue(List<ConfiguracionAtributo> listaDataDinamic) {
		this.eliminarConfiguracionAtributoValue(listaDataDinamic);
		List<ConfiguracionAtributoValue> listaConfiguracionAtributoValue = new ArrayList<>();
		for (var objData : listaDataDinamic) {
			ConfiguracionAtributoValue objDataValue = parsearConfiguracionAtributoValue(objData);
			objDataValue.setValue(null);
			TipoComponenteAtributoType tipoComponenteAtributoType = TipoComponenteAtributoType
					.get(objData.getItemByIdComponte().getNombre());
			switch (tipoComponenteAtributoType) {
			case INPUT:
			case TEXTAREA:
				if (TipoDatoType.NUMERICO.getKey().equals(objData.getTipoDato())) {
					if (StringUtil.isNotNullOrBlank(objData.getValueNumeric())) {
						objDataValue.setValue(objData.getValueNumeric().toString());
					}
				} else {
					objDataValue.setValue(objData.getValueText());
				}
				listaConfiguracionAtributoValue.add(objDataValue);
				break;
			case DATEPICKER:
				if (StringUtil.isNotNullOrBlank(objData.getValueDate())) {
					objDataValue.setValue(
							FechaUtil.obtenerFechaFormatoPersonalizado(objData.getValueDate(), objData.getFormato()));
				}
				listaConfiguracionAtributoValue.add(objDataValue);
				break;
			case RADIO_BUTTONS:
			case SELECT:
				if (StringUtil.isNotNullOrBlank(objData.getValueNumeric())) {
					objDataValue.setValue(objData.getValueNumeric().toString());
				}
				listaConfiguracionAtributoValue.add(objDataValue);
				break;
			case MODAL:
				if (StringUtil.isNotNullOrBlank(objData.getItemAtributoValue())) {
					objDataValue.setValue(objData.getItemAtributoValue().getIdItem().toString());
				}
				listaConfiguracionAtributoValue.add(objDataValue);
				break;
			case SLIDE_TOGGLE:
			case CHECKBOX:
				if (StringUtil.isNotNullOrBlank(objData.getValueBoolean())) {
					objDataValue.setValue(objData.getValueBoolean().toString());
				}
				listaConfiguracionAtributoValue.add(objDataValue);
				break;
			case SELECT_MULTIPLE:
			case MODAL_MULTIPLE:
				for (var objId : objData.getListaSelectItemSelectedVO()) {
					ConfiguracionAtributoValue objValueDet = parsearConfiguracionAtributoValue(objData);
					objValueDet.setValue(objId.toString());
					listaConfiguracionAtributoValue.add(objValueDet);
				}
				break;

			default:
				break;
			}
		}
		registrarConfiguracionAtributoValueData(listaConfiguracionAtributoValue);
	}

	@Override
	public void eliminarConfiguracionAtributoValue(List<ConfiguracionAtributo> listaDataDinamic) {
		List<String> listaIdConfiguracionAtributo = new ArrayList<>();
		for (var objData : listaDataDinamic) {
			listaIdConfiguracionAtributo.add(objData.getIdConfiguracionAtributo());
		}
		this.configuracionAtributoValueDaoImpl.eliminar(listaIdConfiguracionAtributo,
				listaDataDinamic.get(0).getId().toString());
	}

	private ConfiguracionAtributoValue parsearConfiguracionAtributoValue(ConfiguracionAtributo objData) {
		ConfiguracionAtributoValue objDataValue = new ConfiguracionAtributoValue();
		objDataValue.setIdTuplaEntidad(objData.getId().toString());
		objDataValue.setConfiguracionAtributo(new ConfiguracionAtributo());
		objDataValue.getConfiguracionAtributo().setIdConfiguracionAtributo(objData.getIdConfiguracionAtributo());
		return objDataValue;
	}

	private void registrarConfiguracionAtributoValueData(
			List<ConfiguracionAtributoValue> listaConfiguracionAtributoValue) {
		for (var objDataValue : listaConfiguracionAtributoValue) {
			objDataValue.setIdConfiguracionAtributoValue(this.configuracionAtributoValueDaoImpl.generarId());
			this.configuracionAtributoValueDaoImpl.save(objDataValue);
		}

	}

	@Override
	public void subirImagen(FileVO fileVO) {
		// Base64ImageUtils.decoder(fileVO.getDataBase64(),
		// fileVO.getRuta());//:TODO:FALTA
	}

	@Override
	public String obtenerImagenEncodeBase64(FileVO fileVO) {
		return null;// Base64ImageUtils.encoder(fileVO.getRuta());//:TODO:FALTA
	}

	@Override
	public Parametro controladorAccionParametro(Parametro obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdParametro(this.parametroDaoImpl.generarId());
			this.parametroDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.parametroDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.parametroDaoImpl.find(Parametro.class, obj.getIdParametro());
			this.parametroDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.parametroDaoImpl.find(Parametro.class, obj.getIdParametro());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<Parametro> listarParametro(BaseSearch filtro) {
		return this.parametroDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarParametro(BaseSearch filtro) {
		return this.parametroDaoImpl.contar(filtro);
	}

	@Override
	public List<SelectItemVO> listarUbigeoSelectItem(List<Ubigeo> listaUbigeo) {
		List<SelectItemVO> resultado = new ArrayList<>();
		for (var ubigeoResul : listaUbigeo) {
			resultado.add(new SelectItemVO(ubigeoResul.getIdUbigeo(), ubigeoResul.getDescripcion(),
					ubigeoResul.getDescripcion()));
		}
		return resultado;
	}

	@Override
	public Map<Long, List<SelectItemVO>> convertirListaItemSelectItemMap(List<Item> listaItem) {
		Map<Long, List<SelectItemVO>> resultado = new HashMap<>();
		if (!CollectionUtil.isEmpty(listaItem)) {
			for (var items : listaItem) {
				Long idListaItemActual = items.getListaItems().getIdListaItems();
				if (!resultado.containsKey(idListaItemActual)) {
					List<SelectItemVO> value = new ArrayList<>();
					value.add(new SelectItemVO(items.getIdItem(), items.getNombre(), items.getCodigo() + ""));
					resultado.put(idListaItemActual, value);
				} else {
					List<SelectItemVO> value = resultado.get(idListaItemActual);
					value.add(new SelectItemVO(items.getIdItem(), items.getNombre(), items.getCodigo() + ""));
					resultado.put(idListaItemActual, value);
				}
			}

		}
		return resultado;
	}

	@Override
	public List<Item> listarItem() {
		return this.itemDaoImpl.listar();
	}

	@Override
	public ListaItems controladorAccionListaItems(ListaItems obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdListaItems(this.listaItemsDaoImpl.generarId());
			this.listaItemsDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.listaItemsDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.listaItemsDaoImpl.find(ListaItems.class, obj.getIdListaItems());
			this.listaItemsDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.listaItemsDaoImpl.find(ListaItems.class, obj.getIdListaItems());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<ListaItems> listarListaItems(BaseSearch filtro) {
		return this.listaItemsDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarListaItems(BaseSearch filtro) {
		return this.listaItemsDaoImpl.contar(filtro);
	}

	@Override
	public Item controladorAccionItem(Item obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdItem(this.itemDaoImpl.generarId());
			this.itemDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.itemDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.itemDaoImpl.find(Item.class, obj.getIdItem());
			this.itemDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.itemDaoImpl.find(Item.class, obj.getIdItem());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<Item> listarItem(BaseSearch filtro) {
		return this.itemDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarItem(BaseSearch filtro) {
		return this.itemDaoImpl.contar(filtro);
	}

	@Override
	public Ubigeo controladorAccionUbigeo(Ubigeo obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdUbigeo(this.ubigeoDaoImpl.generarId());
			this.ubigeoDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.ubigeoDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.ubigeoDaoImpl.find(Ubigeo.class, obj.getIdUbigeo());
			this.ubigeoDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.ubigeoDaoImpl.find(Ubigeo.class, obj.getIdUbigeo());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<Ubigeo> listarUbigeo(BaseSearch filtro) {
		return this.ubigeoDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarUbigeo(BaseSearch filtro) {
		return this.ubigeoDaoImpl.contar(filtro);
	}

	@Override
	public ListaValores controladorAccionListaValores(ListaValores obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdListaValores(this.listaValoresDaoImpl.generarId());
			this.listaValoresDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.listaValoresDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.listaValoresDaoImpl.find(ListaValores.class, obj.getIdListaValores());
			this.listaValoresDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.listaValoresDaoImpl.find(ListaValores.class, obj.getIdListaValores());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<ListaValores> listarListaValores(BaseSearch filtro) {
		return this.listaValoresDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarListaValores(BaseSearch filtro) {
		return this.listaValoresDaoImpl.contar(filtro);
	}

	@Override
	public Anhio controladorAccionAnhio(Anhio obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdAnhio(this.anhioDaoImpl.generarId());
			this.anhioDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.anhioDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.anhioDaoImpl.find(Anhio.class, obj.getIdAnhio());
			this.anhioDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.anhioDaoImpl.find(Anhio.class, obj.getIdAnhio());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<Anhio> listarAnhio(BaseSearch filtro) {
		return this.anhioDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarAnhio(BaseSearch filtro) {
		return this.anhioDaoImpl.contar(filtro);
	}

	@Override
	public Anhio obtenerAnhioyEstado(EstadoGeneralState estadoAnhoState) {
		return anhioDaoImpl.obtenerAnhioyEstado(estadoAnhoState);
	}

}