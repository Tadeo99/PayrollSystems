package pe.buildsoft.erp.core.application.servicios.escalafon;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import pe.buildsoft.erp.core.application.entidades.common.ItemDTO;
import pe.buildsoft.erp.core.application.entidades.common.UbigeoDTO;
import pe.buildsoft.erp.core.application.entidades.escalafon.AsociarCentroCostoDTO;
import pe.buildsoft.erp.core.application.entidades.escalafon.BeneficiariosDTO;
import pe.buildsoft.erp.core.application.entidades.escalafon.CarreraDTO;
import pe.buildsoft.erp.core.application.entidades.escalafon.CentroCostoDTO;
import pe.buildsoft.erp.core.application.entidades.escalafon.ContratoDTO;
import pe.buildsoft.erp.core.application.entidades.escalafon.CuentaBancariaPersonalDTO;
import pe.buildsoft.erp.core.application.entidades.escalafon.DetalleContradoDTO;
import pe.buildsoft.erp.core.application.entidades.escalafon.DireccionPersonalDTO;
import pe.buildsoft.erp.core.application.entidades.escalafon.EmpresaDTO;
import pe.buildsoft.erp.core.application.entidades.escalafon.HistorialBasicoDTO;
import pe.buildsoft.erp.core.application.entidades.escalafon.HistorialCargoAreaDTO;
import pe.buildsoft.erp.core.application.entidades.escalafon.InstitucionDTO;
import pe.buildsoft.erp.core.application.entidades.escalafon.PeriodoLaboraPersonalDTO;
import pe.buildsoft.erp.core.application.entidades.escalafon.PersonalDTO;
import pe.buildsoft.erp.core.application.interfaces.escalafon.EscalafonAppServiceLocal;
import pe.buildsoft.erp.core.domain.entidades.escalafon.AsociarCentroCosto;
import pe.buildsoft.erp.core.domain.entidades.escalafon.Beneficiarios;
import pe.buildsoft.erp.core.domain.entidades.escalafon.Carrera;
import pe.buildsoft.erp.core.domain.entidades.escalafon.CentroCosto;
import pe.buildsoft.erp.core.domain.entidades.escalafon.Contrato;
import pe.buildsoft.erp.core.domain.entidades.escalafon.CuentaBancariaPersonal;
import pe.buildsoft.erp.core.domain.entidades.escalafon.DetalleContrado;
import pe.buildsoft.erp.core.domain.entidades.escalafon.DireccionPersonal;
import pe.buildsoft.erp.core.domain.entidades.escalafon.HistorialBasico;
import pe.buildsoft.erp.core.domain.entidades.escalafon.HistorialCargoArea;
import pe.buildsoft.erp.core.domain.entidades.escalafon.Institucion;
import pe.buildsoft.erp.core.domain.entidades.escalafon.PeriodoLaboraPersonal;
import pe.buildsoft.erp.core.domain.entidades.escalafon.Personal;
import pe.buildsoft.erp.core.domain.interfaces.servicios.escalafon.EscalafonServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.cache.AtributosEntityCacheUtil;
import pe.buildsoft.erp.core.infra.transversal.cache.ICache;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.SelectItemVO;
import pe.buildsoft.erp.core.infra.transversal.state.EstadoGeneralState;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.type.RespuestaNaturalType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.BaseTransfer;
import pe.buildsoft.erp.core.infra.transversal.utilitario.CollectionUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * La Class EscalafonServiceImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:10 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class EscalafonAppServiceImpl extends BaseTransfer implements EscalafonAppServiceLocal {

	private static final String CACHE_ITEM = "item";
	private static final String CACHE_UBIGEO = "ubigeo";

	private static final String PERSONAL = "personal";

	/** El servicio historial cargo area dao impl. */
	@Inject
	private EscalafonServiceLocal servicio;

	@Inject
	private ICache cacheUtil;

	public EscalafonAppServiceImpl() {
		AtributosEntityCacheUtil.getInstance().sincronizarAtributos("pe.buildsoft.erp.core.domain.entidades.escalafon");
	}

	@Override
	public List<SelectItemVO> listarSelectItem(String groupName, BaseSearch filtro) {
		switch (groupName) {
			case "institucion":
				return listarInstitucionSelectItem();
			case "centroCosto":
				return listarCentroCostoSelectItem();
			case "personal":
				return listarPersonalSelectItem(filtro);
			default:
				break;
		}
		return new ArrayList<SelectItemVO>();
	}

	@Override
	public int contarSelectItem(String groupName, BaseSearch filtro) {
		int resultado = 0;
		switch (groupName) {
			case "personal":
				resultado = servicio.contarListarPersonal(filtro);
				break;

			default:
				break;
		}
		return resultado;
	}

	private List<SelectItemVO> listarPersonalSelectItem(BaseSearch filtro) {
		if (!StringUtil.isNotNullOrBlank(filtro.getSortFields())) {
			filtro.setSortFields("nombres");
		}
		if (StringUtil.isNotNullOrBlank(filtro.getSortFields()) && filtro.getSortFields().equalsIgnoreCase("id")) {
			filtro.setSortFields("nroDoc");
		}
		if (StringUtil.isNotNullOrBlank(filtro.getSortFields()) && filtro.getSortFields().equalsIgnoreCase("nombre")) {
			filtro.setSortFields("nombres");
		}

		if (!StringUtil.isNotNullOrBlank(filtro.getSortDirections())) {
			filtro.setSortDirections("asc");
		}
		List<SelectItemVO> resultado = new ArrayList<>();
		List<Personal> resulTmp = servicio.listarPersonal(filtro);
		for (var obj : resulTmp) {
			resultado.add(
					new SelectItemVO(obj.getIdPersonal(), obj.getNroDoc() + " " + obj.getNombres(), obj.getEstado()));
		}
		return resultado;
	}

	private List<SelectItemVO> listarInstitucionSelectItem() {
		var filtro = new BaseSearch();
		filtro.setSortFields("idInstitucion");
		filtro.setSortDirections("asc");
		filtro.setEstado(EstadoGeneralState.ACTIVO.getKey());
		var resultado = new ArrayList<SelectItemVO>();
		var resulTmp = listarInstitucion(filtro);
		for (var obj : resulTmp) {
			resultado.add(new SelectItemVO(obj.getIdInstitucion(), obj.getItemByTipoInstitucion().getNombre()));
		}
		return resultado;
	}

	private List<SelectItemVO> listarCentroCostoSelectItem() {
		var filtro = new BaseSearch();
		filtro.setSortFields("idCentroCosto");
		filtro.setSortDirections("asc");
		filtro.setEstado(EstadoGeneralState.ACTIVO.getKey());
		var resultado = new ArrayList<SelectItemVO>();
		var resulTmp = listarCentroCosto(filtro);
		for (var obj : resulTmp) {
			resultado.add(new SelectItemVO(obj.getIdCentroCosto(), obj.getNombre()));
		}
		return resultado;
	}

	@Override
	public HistorialCargoAreaDTO controladorAccionHistorialCargoArea(HistorialCargoAreaDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionHistorialCargoArea(to(obj, HistorialCargoArea.class), accionType),
				HistorialCargoAreaDTO.class);
	}

	@Override
	public List<HistorialCargoAreaDTO> listarHistorialCargoArea(BaseSearch filtro) {
		var resultado = toList(servicio.listarHistorialCargoArea(filtro), HistorialCargoAreaDTO.class, PERSONAL);
		var completeMap = new HashMap<String, String>();
		completeMap.put("idItemByCargo", "itemByCargo");
		completeMap.put("idItemByArea", "itemByArea");
		cacheUtil.completarData(resultado, completeMap, CACHE_ITEM);
		for (var obj : resultado) {
			if (obj.getItemByCargo() == null) {
				obj.setItemByCargo(new ItemDTO());
			}
			if (obj.getItemByArea() == null) {
				obj.setItemByArea(new ItemDTO());
			}
			obj.getPersonal().setDescripcionView(obj.getPersonal().getNombres() + " "
					+ obj.getPersonal().getApellidoPaterno() + " " + obj.getPersonal().getApellidoMaterno());
			generarDescripcionViewItem(obj.getItemByCargo());
			generarDescripcionViewItem(obj.getItemByArea());
		}
		return resultado;
	}

	@Override
	public int contarListarHistorialCargoArea(BaseSearch filtro) {
		return servicio.contarListarHistorialCargoArea(filtro);
	}

	@Override
	public CentroCostoDTO controladorAccionCentroCosto(CentroCostoDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionCentroCosto(to(obj, CentroCosto.class), accionType),
				CentroCostoDTO.class);
	}

	@Override
	public List<CentroCostoDTO> listarCentroCosto(BaseSearch filtro) {
		return toList(servicio.listarCentroCosto(filtro), CentroCostoDTO.class);
	}

	@Override
	public int contarListarCentroCosto(BaseSearch filtro) {
		return servicio.contarListarCentroCosto(filtro);
	}

	@Override
	public CentroCostoDTO findCentroCosto(CentroCostoDTO filtro) {
		return toDTO(servicio.findCentroCosto(to(filtro, CentroCosto.class)), CentroCostoDTO.class);
	}

	@Override
	public PersonalDTO controladorAccionPersonal(PersonalDTO obj, AccionType accionType) throws IOException {
		var objPersist = to(obj, Personal.class);
		objPersist.setUsuarioSession("");
		return completarDatosPersonal(
				toDTO(servicio.controladorAccionPersonal(objPersist, accionType), PersonalDTO.class, "carrera"));

	}

	private Map<String, CuentaBancariaPersonalDTO> cuentaBancariaPersonalMap(List<String> listaIdPersonal) {
		var resultado = new HashMap<String, CuentaBancariaPersonalDTO>();
		var filtro = new BaseSearch();
		filtro.setListaIdPersonal(listaIdPersonal);
		var listaCuentaBancaria = this.listarCuentaBancariaPersonal(filtro);
		for (var obj : listaCuentaBancaria) {
			String key = StringUtil.generarKey(obj.getPersonal().getIdPersonal(), obj.getEsCts());
			resultado.put(key, obj);
		}
		return resultado;
	}

	@Override
	public List<PersonalDTO> listarPersonal(BaseSearch filtro) {
		var completeMap = new HashMap<String, String>();
		completeMap.put("idItemByCategoriaTrabajador", "itemByCategoriaTrabajador");
		var resultado = toList(servicio.listarPersonal(filtro), PersonalDTO.class);
		cacheUtil.completarData(resultado, completeMap, CACHE_ITEM);
		for (var obj : resultado) {
			generarDescripcionViewPersonal(obj);
		}
		return resultado;
	}

	@Override
	public List<String> listarPersonalIds(BaseSearch filtro) {
		return servicio.listarPersonalIds(filtro);
	}

	private PersonalDTO generarDescripcionViewPersonal(PersonalDTO obj) {
		obj.setDescripcionView(obj.getNombres() + " " + obj.getApellidoPaterno() + " " + obj.getApellidoMaterno());
		return obj;
	}

	private PersonalDTO completarDatosPersonal(PersonalDTO obj) {
		completeDatapersonal(obj);
		var listaIdPersonal = new ArrayList<String>();
		listaIdPersonal.add(obj.getIdPersonal());
		var direccionPersonalMap = new HashMap<String, DireccionPersonalDTO>();
		var cuentaBancariaPersonalMap = new HashMap<String, CuentaBancariaPersonalDTO>();
		if (!CollectionUtil.isEmpty(listaIdPersonal)) {
			direccionPersonalMap = (HashMap<String, DireccionPersonalDTO>) direccionPersonalMap(listaIdPersonal);
			cuentaBancariaPersonalMap = (HashMap<String, CuentaBancariaPersonalDTO>) cuentaBancariaPersonalMap(
					listaIdPersonal);
		}
		generarDescripcionViewPersonal(obj);
		obj.setDireccion1(new DireccionPersonalDTO());
		obj.setDireccion2(new DireccionPersonalDTO());
		obj.setCuentaBancariaCts(new CuentaBancariaPersonalDTO());
		obj.setCuentaBancariaPago(new CuentaBancariaPersonalDTO());
		var key = StringUtil.generarKey(obj.getIdPersonal(), RespuestaNaturalType.SI.getKey());
		if (direccionPersonalMap.containsKey(key)) {
			obj.setDireccion1(direccionPersonalMap.get(key));
		}
		if (cuentaBancariaPersonalMap.containsKey(key)) {
			obj.setCuentaBancariaCts(cuentaBancariaPersonalMap.get(key));
		}
		key = StringUtil.generarKey(obj.getIdPersonal(), RespuestaNaturalType.NO.getKey());
		if (direccionPersonalMap.containsKey(key)) {
			obj.setDireccion2(direccionPersonalMap.get(key));
		}
		if (cuentaBancariaPersonalMap.containsKey(key)) {
			obj.setCuentaBancariaPago(cuentaBancariaPersonalMap.get(key));
		}

		generarDescripcionViewUbigeo(obj.getLugarNacimiento());
		generarDescripcionViewEmpresa(obj.getEmpleadorDestacaPersonalTercero());
		/** item */
		generarDescripcionViewItem(obj.getItemByDocIdentidad());
		generarDescripcionViewItem(obj.getItemByEstadoCivil());
		generarDescripcionViewItem(obj.getItemByPaisEmisor());
		generarDescripcionViewItem(obj.getItemByNacionalidad());
		generarDescripcionViewItem(obj.getItemBySituacion());
		generarDescripcionViewItem(obj.getItemByAfp());
		generarDescripcionViewItem(obj.getItemByRegimenLaboral());
		generarDescripcionViewItem(obj.getItemByRegimenPensionario());
		generarDescripcionViewItem(obj.getItemByConvenioEvitarDobleTributacion());
		generarDescripcionViewItem(obj.getItemByCategoriaTrabajador());
		generarDescripcionViewItem(obj.getItemBySctrSalud());
		generarDescripcionViewItem(obj.getItemByRegimenAsegSalud());
		generarDescripcionViewItem(obj.getItemBySctrPension());
		generarDescripcionViewItem(obj.getItemByEps());
		generarDescripcionViewItem(obj.getItemByServicoMedico());
		generarDescripcionViewItem(obj.getItemByPeriocidadIngreso());
		generarDescripcionViewItem(obj.getItemByTipoPago());
		generarDescripcionViewItem(obj.getItemBySituacionEspecial());
		generarDescripcionViewItem(obj.getItemByCategoriaOcupacional());
		generarDescripcionViewItem(obj.getItemByOcupacion());
		generarDescripcionViewItem(obj.getItemByOcupacionRegimenPublico());
		generarDescripcionViewItem(obj.getItemByTipoPension());
		generarDescripcionViewItem(obj.getItemByRegimenPensionarioPension());
		generarDescripcionViewItem(obj.getItemByTipoPagoPension());
		generarDescripcionViewItem(obj.getItemByTipoCentroFormacionProfesional());
		generarDescripcionViewItem(obj.getItemByTipoModalidadFormativa());
		generarDescripcionViewItem(obj.getItemBySituacionEducativa());
		return obj;
	}

	private PersonalDTO completeDatapersonal(PersonalDTO resultado) {
		var completeMap = new HashMap<String, String>();
		completeMap.put("idItemByDocIdentidad", "itemByDocIdentidad");
		completeMap.put("idItemByEstadoCivil", "itemByEstadoCivil");
		completeMap.put("idItemByPaisEmisor", "itemByPaisEmisor");
		completeMap.put("idItemByNacionalidad", "itemByNacionalidad");
		completeMap.put("idItemBySituacion", "itemBySituacion");
		completeMap.put("idItemByAfp", "itemByAfp");
		completeMap.put("idItemByRegimenLaboral", "itemByRegimenLaboral");
		completeMap.put("idItemByRegimenPensionario", "itemByRegimenPensionario");
		completeMap.put("idItemByConvenioEvitarDobleTributacion", "itemByConvenioEvitarDobleTributacion");
		completeMap.put("idItemByCategoriaTrabajador", "itemByCategoriaTrabajador");
		completeMap.put("idItemBySctrSalud", "itemBySctrSalud");
		completeMap.put("idItemByRegimenAsegSalud", "itemByRegimenAsegSalud");
		completeMap.put("idItemBySctrPension", "itemBySctrPension");
		completeMap.put("idItemByEps", "itemByEps");
		completeMap.put("idItemByServicoMedico", "itemByServicoMedico");
		completeMap.put("idItemByPeriocidadIngreso", "itemByPeriocidadIngreso");
		completeMap.put("idItemByTipoPago", "itemByTipoPago");
		completeMap.put("idItemBySituacionEspecial", "itemBySituacionEspecial");
		completeMap.put("idItemByCategoriaOcupacional", "itemByCategoriaOcupacional");
		completeMap.put("idItemByOcupacion", "itemByOcupacion");
		completeMap.put("idItemByOcupacionRegimenPublico", "itemByOcupacionRegimenPublico");
		completeMap.put("idItemByTipoPension", "itemByTipoPension");
		completeMap.put("idItemByRegimenPensionarioPension", "itemByRegimenPensionarioPension");
		completeMap.put("idItemByTipoPagoPension", "itemByTipoPagoPension");
		completeMap.put("idItemByTipoCentroFormacionProfesional", "itemByTipoCentroFormacionProfesional");
		completeMap.put("idItemByTipoModalidadFormativa", "itemByTipoModalidadFormativa");
		completeMap.put("idItemBySituacionEducativa", "itemBySituacionEducativa");
		cacheUtil.completarData(resultado, completeMap, CACHE_ITEM);
		completeMap = new HashMap<String, String>();
		completeMap.put("idLugarNacimiento", "lugarNacimiento");
		cacheUtil.completarData(resultado, completeMap, "ubigeo");
		return resultado;
	}

	private EmpresaDTO generarDescripcionViewEmpresa(EmpresaDTO obj) {
		if (obj != null && obj.getRazonSocial() != null) {
			obj.setDescripcionView(obj.getRazonSocial());
		}
		return obj;
	}

	private UbigeoDTO generarDescripcionViewUbigeo(UbigeoDTO obj) {
		if (obj != null && obj.getIdUbigeo() != null) {
			obj.setDescripcionView(obj.getIdUbigeo() + " " + obj.getDescripcion());
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
	public int contarListarPersonal(BaseSearch filtro) {
		return servicio.contarListarPersonal(filtro);
	}

	@Override
	public PersonalDTO findPersonal(PersonalDTO filtro) {
		return toDTO(servicio.findPersonal(to(filtro, Personal.class)), PersonalDTO.class);
	}

	private Map<String, DireccionPersonalDTO> direccionPersonalMap(List<String> listaIdPersonal) {
		var resultado = new HashMap<String, DireccionPersonalDTO>();
		var filtro = new BaseSearch();
		filtro.setListaIdPersonal(listaIdPersonal);
		var listaDireccion = this.listarDireccionPersonal(filtro);
		for (var obj : listaDireccion) {
			String key = StringUtil.generarKey(obj.getPersonal().getIdPersonal(), obj.getDomiciliado());
			resultado.put(key, obj);
		}
		return resultado;
	}

	@Override
	public DetalleContradoDTO controladorAccionDetalleContrado(DetalleContradoDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionDetalleContrado(to(obj, DetalleContrado.class), accionType),
				DetalleContradoDTO.class);
	}

	@Override
	public List<DetalleContradoDTO> listarDetalleContrado(BaseSearch filtro) {
		return toList(servicio.listarDetalleContrado(filtro), DetalleContradoDTO.class);
	}

	@Override
	public int contarListarDetalleContrado(BaseSearch filtro) {
		return servicio.contarListarDetalleContrado(filtro);
	}

	@Override
	public ContratoDTO controladorAccionContrato(ContratoDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionContrato(to(obj, Contrato.class), accionType), ContratoDTO.class);
	}

	@Override
	public List<ContratoDTO> listarContrato(BaseSearch filtro) {
		var resultado = toList(servicio.listarContrato(filtro), ContratoDTO.class, PERSONAL);
		var completeMap = new HashMap<String, String>();
		completeMap.put("idItemByTipoContrato", "itemByTipoContrato");
		cacheUtil.completarData(resultado, completeMap, CACHE_ITEM);
		for (var obj : resultado) {
			if (obj.getItemByTipoContrato() == null) {
				obj.setItemByTipoContrato(new ItemDTO());
			}
			obj.getPersonal().setDescripcionView(obj.getPersonal().getNombres() + " "
					+ obj.getPersonal().getApellidoPaterno() + " " + obj.getPersonal().getApellidoMaterno());
			generarDescripcionViewItem(obj.getItemByTipoContrato());
		}
		return resultado;
	}

	@Override
	public int contarListarContrato(BaseSearch filtro) {
		return servicio.contarListarContrato(filtro);
	}

	@Override
	public CuentaBancariaPersonalDTO controladorAccionCuentaBancariaPersonal(CuentaBancariaPersonalDTO obj,
			AccionType accionType) {
		return toDTO(
				servicio.controladorAccionCuentaBancariaPersonal(to(obj, CuentaBancariaPersonal.class), accionType),
				CuentaBancariaPersonalDTO.class);
	}

	@Override
	public List<CuentaBancariaPersonalDTO> listarCuentaBancariaPersonal(BaseSearch filtro) {
		var resultado = toList(servicio.listarCuentaBancariaPersonal(filtro), CuentaBancariaPersonalDTO.class,
				PERSONAL);

		var completeMap = new HashMap<String, String>();
		completeMap.put("idItemByBanco", "itemByBanco");
		completeMap.put("idItemByMoneda", "itemByMoneda");
		completeMap.put("idItemByTipoCuenta", "itemByTipoCuenta");
		completeMap.put("idItemByTipoDepositoCuenta", "itemByTipoDepositoCuenta");
		cacheUtil.completarData(resultado, completeMap, CACHE_ITEM);

		for (var obj : resultado) {
			generarDescripcionViewItem(obj.getItemByBanco());
			generarDescripcionViewItem(obj.getItemByMoneda());
			generarDescripcionViewItem(obj.getItemByTipoCuenta());
			generarDescripcionViewItem(obj.getItemByTipoDepositoCuenta());
		}
		return resultado;
	}

	@Override
	public int contarListarCuentaBancariaPersonal(BaseSearch filtro) {
		return servicio.contarListarCuentaBancariaPersonal(filtro);
	}

	@Override
	public HistorialBasicoDTO controladorAccionHistorialBasico(HistorialBasicoDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionHistorialBasico(to(obj, HistorialBasico.class), accionType),
				HistorialBasicoDTO.class);
	}

	@Override
	public List<HistorialBasicoDTO> listarHistorialBasico(BaseSearch filtro) {
		var resultado = toList(servicio.listarHistorialBasico(filtro), HistorialBasicoDTO.class, "personalResponsable");
		for (var obj : resultado) {
			if (obj.getPersonalResponsable() != null) {
				obj.getPersonalResponsable()
						.setDescripcionView(obj.getPersonalResponsable().getNombres() + " "
								+ obj.getPersonalResponsable().getApellidoPaterno() + " "
								+ obj.getPersonalResponsable().getApellidoMaterno());
			}
		}
		return resultado;
	}

	@Override
	public int contarListarHistorialBasico(BaseSearch filtro) {
		return servicio.contarListarHistorialBasico(filtro);
	}

	@Override
	public AsociarCentroCostoDTO controladorAccionAsociarCentroCosto(AsociarCentroCostoDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionAsociarCentroCosto(to(obj, AsociarCentroCosto.class), accionType),
				AsociarCentroCostoDTO.class);
	}

	@Override
	public List<AsociarCentroCostoDTO> listarAsociarCentroCosto(BaseSearch filtro) {
		return toList(servicio.listarAsociarCentroCosto(filtro), AsociarCentroCostoDTO.class, "centroCosto");
	}

	@Override
	public int contarListarAsociarCentroCosto(BaseSearch filtro) {
		return servicio.contarListarAsociarCentroCosto(filtro);
	}

	@Override
	public CarreraDTO controladorAccionCarrera(CarreraDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionCarrera(to(obj, Carrera.class), accionType), CarreraDTO.class);
	}

	@Override
	public List<CarreraDTO> listarCarrera(BaseSearch filtro) {
		var resultado = toList(servicio.listarCarrera(filtro), CarreraDTO.class, "institucion");
		var completeMap = new HashMap<String, String>();
		completeMap.put("idItemByTipoInstitucion", "itemByTipoInstitucion");
		for (var objData : resultado) {
			cacheUtil.completarData(objData.getInstitucion(), completeMap, CACHE_ITEM);
			objData.getInstitucion()
					.setDescripcionView(objData.getInstitucion().getItemByTipoInstitucion().getNombre());
		}
		return resultado;
	}

	@Override
	public int contarListarCarrera(BaseSearch filtro) {
		return servicio.contarListarCarrera(filtro);
	}

	@Override
	public CarreraDTO findCarrera(CarreraDTO filtro) {
		return toDTO(servicio.findCarrera(to(filtro, Carrera.class)), CarreraDTO.class);
	}

	@Override
	public InstitucionDTO controladorAccionInstitucion(InstitucionDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionInstitucion(to(obj, Institucion.class), accionType),
				InstitucionDTO.class);
	}

	@Override
	public List<InstitucionDTO> listarInstitucion(BaseSearch filtro) {
		var resultado = toList(servicio.listarInstitucion(filtro), InstitucionDTO.class);
		var completeMap = new HashMap<String, String>();
		completeMap.put("idItemByTipoInstitucion", "itemByTipoInstitucion");
		completeMap.put("idItemByRegimen", "itemByRegimen");
		cacheUtil.completarData(resultado, completeMap, CACHE_ITEM);

		for (var objData : resultado) {
			generarDescripcionViewItem(objData.getItemByTipoInstitucion());
			generarDescripcionViewItem(objData.getItemByRegimen());
		}
		return resultado;
	}

	@Override
	public int contarListarInstitucion(BaseSearch filtro) {
		return this.servicio.contarListarInstitucion(filtro);
	}

	@Override
	public DireccionPersonalDTO controladorAccionDireccionPersonal(DireccionPersonalDTO obj, AccionType accionType) {
		var resul = new ArrayList<DireccionPersonalDTO>();
		resul.add(toDTO(servicio.controladorAccionDireccionPersonal(to(obj, DireccionPersonal.class), accionType),
				DireccionPersonalDTO.class));
		return completarDataDireccion(resul).get(0);
	}

	@Override
	public List<DireccionPersonalDTO> listarDireccionPersonal(BaseSearch filtro) {
		var resultado = toList(this.servicio.listarDireccionPersonal(filtro), DireccionPersonalDTO.class, "personal");
		return completarDataDireccion(resultado);
	}

	private List<DireccionPersonalDTO> completarDataDireccion(List<DireccionPersonalDTO> resultado) {
		var completeMap = new HashMap<String, String>();
		completeMap.put("idItemByTipoVia", "itemByTipoVia");
		completeMap.put("idItemByZona", "itemByZona");
		completeMap.put("idItemByProcedenciaDireccion", "itemByProcedenciaDireccion");
		cacheUtil.completarData(resultado, completeMap, CACHE_ITEM);

		completeMap = new HashMap<String, String>();
		completeMap.put("idUbigeo", "ubigeo");
		cacheUtil.completarData(resultado, completeMap, CACHE_UBIGEO);

		for (var obj : resultado) {
			generarDescripcionViewItem(obj.getItemByZona());
			generarDescripcionViewItem(obj.getItemByProcedenciaDireccion());
			generarDescripcionViewItem(obj.getItemByTipoVia());
			if (obj.getUbigeo() != null) {
				obj.getUbigeo()
						.setDescripcionView(obj.getUbigeo().getIdUbigeo() + "" + obj.getUbigeo().getDescripcion());
			}
		}
		return resultado;
	}

	@Override
	public int contarListarDireccionPersonal(BaseSearch filtro) {
		return this.servicio.contarListarDireccionPersonal(filtro);
	}

	@Override
	public BeneficiariosDTO controladorAccionBeneficiarios(BeneficiariosDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionBeneficiarios(to(obj, Beneficiarios.class), accionType),
				BeneficiariosDTO.class);
	}

	@Override
	public List<BeneficiariosDTO> listarBeneficiarios(BaseSearch filtro) {
		return toList(this.servicio.listarBeneficiarios(filtro), BeneficiariosDTO.class);
	}

	@Override
	public int contarListarBeneficiarios(BaseSearch filtro) {
		return this.servicio.contarListarBeneficiarios(filtro);
	}

	@Override
	public PeriodoLaboraPersonalDTO controladorAccionPeriodoLaboraPersonal(PeriodoLaboraPersonalDTO obj,
			AccionType accionType) {

		return toDTO(servicio.controladorAccionPeriodoLaboraPersonal(to(obj, PeriodoLaboraPersonal.class), accionType),
				PeriodoLaboraPersonalDTO.class);

	}

	@Override
	public List<PeriodoLaboraPersonalDTO> listarPeriodoLaboraPersonal(BaseSearch filtro) {
		var resultado = toList(this.servicio.listarPeriodoLaboraPersonal(filtro), PeriodoLaboraPersonalDTO.class);
		var completeMap = new HashMap<String, String>();
		completeMap.put("idItemByMotivoCese", "itemByMotivoCese");
		cacheUtil.completarData(resultado, completeMap, CACHE_ITEM);

		for (var obj : resultado) {
			generarDescripcionViewItem(obj.getItemByMotivoCese());
		}
		return resultado;
	}

	@Override
	public int contarListarPeriodoLaboraPersonal(BaseSearch filtro) {
		return this.servicio.contarListarPeriodoLaboraPersonal(filtro);
	}

	@Override
	public Map<String, BigDecimal> obtenerBasicoPersonalMap(Long idCategoriaTrabajador) {
		return this.servicio.obtenerBasicoPersonalMap(idCategoriaTrabajador);
	}

}