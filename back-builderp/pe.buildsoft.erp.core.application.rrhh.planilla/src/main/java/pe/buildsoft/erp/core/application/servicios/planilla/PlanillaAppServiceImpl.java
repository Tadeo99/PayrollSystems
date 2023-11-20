
package pe.buildsoft.erp.core.application.servicios.planilla;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import pe.buildsoft.erp.core.application.entidades.aas.vo.NavigationItemVO;
import pe.buildsoft.erp.core.application.entidades.common.AnhioDTO;
import pe.buildsoft.erp.core.application.entidades.common.ItemDTO;
import pe.buildsoft.erp.core.application.entidades.planilla.AdelantoDTO;
import pe.buildsoft.erp.core.application.entidades.planilla.ConceptoFijosTrabajadorDTO;
import pe.buildsoft.erp.core.application.entidades.planilla.ConceptoPdtDTO;
import pe.buildsoft.erp.core.application.entidades.planilla.ConceptoRegimenPensionarioDTO;
import pe.buildsoft.erp.core.application.entidades.planilla.ConceptosTipoPlanillaDTO;
import pe.buildsoft.erp.core.application.entidades.planilla.DetRentaQuintaCategoriaDTO;
import pe.buildsoft.erp.core.application.entidades.planilla.DetallePersonalConceptoDTO;
import pe.buildsoft.erp.core.application.entidades.planilla.DetallePlanillaConceptoDTO;
import pe.buildsoft.erp.core.application.entidades.planilla.DetallePlanillaDTO;
import pe.buildsoft.erp.core.application.entidades.planilla.EPSConfDTO;
import pe.buildsoft.erp.core.application.entidades.planilla.EPSPersonalDTO;
import pe.buildsoft.erp.core.application.entidades.planilla.FeriadoDTO;
import pe.buildsoft.erp.core.application.entidades.planilla.InformaOtrosIngreso5taDTO;
import pe.buildsoft.erp.core.application.entidades.planilla.PeriodoPlanillaDTO;
import pe.buildsoft.erp.core.application.entidades.planilla.PersonalConceptoDTO;
import pe.buildsoft.erp.core.application.entidades.planilla.PersonalResponseVo;
import pe.buildsoft.erp.core.application.entidades.planilla.RentaQuintaCategoriaDTO;
import pe.buildsoft.erp.core.application.entidades.planilla.TareoPersonalDTO;
import pe.buildsoft.erp.core.application.entidades.planilla.TipoPlanillaDTO;
import pe.buildsoft.erp.core.application.entidades.planilla.VacacionesDTO;
import pe.buildsoft.erp.core.application.entidades.planilla.ValoresUITDTO;
import pe.buildsoft.erp.core.application.entidades.planilla.VariableConfDTO;
import pe.buildsoft.erp.core.application.entidades.planilla.VariableConfDetDTO;
import pe.buildsoft.erp.core.application.entidades.security.MenuDTO;
import pe.buildsoft.erp.core.application.interfaces.planilla.PlanillaAppServiceLocal;
import pe.buildsoft.erp.core.domain.entidades.planilla.Adelanto;
import pe.buildsoft.erp.core.domain.entidades.planilla.ConceptoFijosTrabajador;
import pe.buildsoft.erp.core.domain.entidades.planilla.ConceptoPdt;
import pe.buildsoft.erp.core.domain.entidades.planilla.ConceptoRegimenPensionario;
import pe.buildsoft.erp.core.domain.entidades.planilla.ConceptosTipoPlanilla;
import pe.buildsoft.erp.core.domain.entidades.planilla.DetRentaQuintaCategoria;
import pe.buildsoft.erp.core.domain.entidades.planilla.DetallePersonalConcepto;
import pe.buildsoft.erp.core.domain.entidades.planilla.EPSConf;
import pe.buildsoft.erp.core.domain.entidades.planilla.EPSPersonal;
import pe.buildsoft.erp.core.domain.entidades.planilla.Feriado;
import pe.buildsoft.erp.core.domain.entidades.planilla.InformaOtrosIngreso5ta;
import pe.buildsoft.erp.core.domain.entidades.planilla.PeriodoPlanilla;
import pe.buildsoft.erp.core.domain.entidades.planilla.PersonalConcepto;
import pe.buildsoft.erp.core.domain.entidades.planilla.RentaQuintaCategoria;
import pe.buildsoft.erp.core.domain.entidades.planilla.TareoPersonal;
import pe.buildsoft.erp.core.domain.entidades.planilla.TipoPlanilla;
import pe.buildsoft.erp.core.domain.entidades.planilla.Vacaciones;
import pe.buildsoft.erp.core.domain.entidades.planilla.ValoresUIT;
import pe.buildsoft.erp.core.domain.entidades.planilla.VariableConf;
import pe.buildsoft.erp.core.domain.entidades.planilla.VariableConfDet;
import pe.buildsoft.erp.core.domain.interfaces.servicios.planilla.PlanillaServiceLocal;
import pe.buildsoft.erp.core.domain.interfaces.webclient.rest.common.CommonServiceClient;
import pe.buildsoft.erp.core.domain.interfaces.webclient.rest.rrhh.escalafon.RRHHEscalafonServiceClient;
import pe.buildsoft.erp.core.infra.data.entidades.webclient.rest.common.vo.ParametroVO;
import pe.buildsoft.erp.core.infra.data.entidades.webclient.rest.rrhh.escalafon.vo.PersonalVO;
import pe.buildsoft.erp.core.infra.transversal.cache.AtributosEntityCacheUtil;
import pe.buildsoft.erp.core.infra.transversal.cache.ICache;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.SelectItemVO;
import pe.buildsoft.erp.core.infra.transversal.state.EstadoGeneralState;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.BaseTransfer;
import pe.buildsoft.erp.core.infra.transversal.utilitario.CollectionUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.FechaUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * La Class PlanillaServiceImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:10 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class PlanillaAppServiceImpl extends BaseTransfer implements PlanillaAppServiceLocal {

	private final Logger log = LoggerFactory.getLogger(PlanillaAppServiceImpl.class);

	private static final String TIPO_PLANILLA = "tipoPlanilla";

	private static final String CONCEPTO_PDT = "conceptoPdt";

	private static final String CACHE_ITEM = "item";

	/** El servicio impl. */
	@Inject
	private PlanillaServiceLocal servicio;

	@Inject
	private RRHHEscalafonServiceClient rrhhPersonalClient;

	@Inject
	private CommonServiceClient commonServiceClient;

	@Inject
	private ICache cacheUtil;

	public PlanillaAppServiceImpl() {
		AtributosEntityCacheUtil.getInstance().sincronizarAtributos("pe.buildsoft.erp.core.domain.entidades.escalafon",
				"pe.buildsoft.erp.core.domain.entidades.common", "pe.buildsoft.erp.core.domain.entidades.planilla",
				"pe.buildsoft.erp.core.domain.entidades.security");
	}

	@Override
	public List<SelectItemVO> listarSelectItem(String groupName, BaseSearch filtro) {
		switch (groupName) {
			case "tipoPlanilla":
				return listarTipoPlanillaSelectItem(filtro);
			case "periodoPlanilla":
				return listarPeriodoPlanillaSelectItem(filtro);
			default:
				break;
		}
		return new ArrayList<SelectItemVO>();
	}

	private List<SelectItemVO> listarTipoPlanillaSelectItem(BaseSearch filtro) {
		filtro.setSortFields("idTipoPlanilla");
		filtro.setSortDirections("asc");
		filtro.setEstado(EstadoGeneralState.ACTIVO.getKey());
		var resultado = new ArrayList<SelectItemVO>();
		var resulTmp = servicio.listarTipoPlanilla(filtro);
		for (var obj : resulTmp) {
			resultado.add(new SelectItemVO(obj.getIdTipoPlanilla(), obj.getDescripcion(),
					obj.getIdItemByCategoriaTrabajador() + ""));
		}
		return resultado;
	}

	private List<SelectItemVO> listarPeriodoPlanillaSelectItem(BaseSearch filtro) {
		filtro.setSortFields("idPeriodoPlanilla");
		filtro.setSortDirections("asc");
		filtro.setEstado(EstadoGeneralState.ACTIVO.getKey());
		var resultado = new ArrayList<SelectItemVO>();
		var resulTmp = servicio.listarPeriodoPlanilla(filtro);
		for (var obj : resulTmp) {
			resultado.add(new SelectItemVO(obj.getIdPeriodoPlanilla(), obj.getDescripcion(),
					obj.getIdItemByPeriodoMes() + ""));
		}
		return resultado;
	}

	@Override
	public ConceptoPdtDTO controladorAccionConceptoPdt(ConceptoPdtDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionConceptoPdt(to(obj, ConceptoPdt.class), accionType),
				ConceptoPdtDTO.class);
	}

	@Override
	public List<ConceptoPdtDTO> listarConceptoPdt(BaseSearch filtro) {
		var resultado = toList(this.servicio.listarConceptoPdt(filtro), ConceptoPdtDTO.class, "conceptoPdtPadre");
		for (var obj : resultado) {
			generarDescripcionViewConceptoPdt(obj);
			generarDescripcionViewConceptoPdt(obj.getConceptoPdtPadre());
		}
		return resultado;
	}

	@Override
	public List<ConceptoPdtDTO> obtenerFormulaConceptoPdt() {
		return toList(this.servicio.obtenerFormulaConceptoPdt(), ConceptoPdtDTO.class, "conceptoPdtPadre");
	}

	@Override
	public int contarListarConceptoPdt(BaseSearch filtro) {
		return this.servicio.contarListarConceptoPdt(filtro);
	}

	@Override
	public ConceptoPdtDTO findConceptoPdt(ConceptoPdtDTO obj) {
		return toDTO(servicio.findConceptoPdt(to(obj, ConceptoPdt.class)), ConceptoPdtDTO.class);
	}

	@Override
	public AdelantoDTO controladorAccionAdelanto(AdelantoDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionAdelanto(to(obj, Adelanto.class), accionType), AdelantoDTO.class);
	}

	@Override
	public List<AdelantoDTO> listarAdelanto(BaseSearch filtro) {
		var resultado = toList(this.servicio.listarAdelanto(filtro), AdelantoDTO.class, "conceptoPdt");
		for (var obj : resultado) {
			generarDescripcionViewConceptoPdt(obj.getConceptoPdt());
		}
		return resultado;
	}

	@Override
	public int contarListarAdelanto(BaseSearch filtro) {
		return this.servicio.contarListarAdelanto(filtro);
	}

	@Override
	public ConceptoFijosTrabajadorDTO controladorAccionConceptoFijosTrabajador(ConceptoFijosTrabajadorDTO obj,
			AccionType accionType) {
		return toDTO(
				servicio.controladorAccionConceptoFijosTrabajador(to(obj, ConceptoFijosTrabajador.class), accionType),
				ConceptoFijosTrabajadorDTO.class);
	}

	@Override
	public List<ConceptoFijosTrabajadorDTO> listarConceptoFijosTrabajador(BaseSearch filtro) {
		var resultado = toList(this.servicio.listarConceptoFijosTrabajador(filtro), ConceptoFijosTrabajadorDTO.class,
				CONCEPTO_PDT);
		for (var obj : resultado) {
			generarDescripcionViewConceptoPdt(obj.getConceptoPdt());
		}
		return resultado;
	}

	@Override
	public int contarListarConceptoFijosTrajabador(BaseSearch filtro) {
		return this.servicio.contarListarConceptoFijosTrajabador(filtro);
	}

	@Override
	public ConceptoFijosTrabajadorDTO findConceptoFijosTrabajador(ConceptoFijosTrabajadorDTO filtro) {
		return toDTO(servicio.findConceptoTrabajador(to(filtro, ConceptoFijosTrabajador.class)),
				ConceptoFijosTrabajadorDTO.class, CONCEPTO_PDT);
	}

	@Override
	public ConceptosTipoPlanillaDTO controladorAccionConceptosTipoPlanilla(ConceptosTipoPlanillaDTO obj,
			AccionType accionType) {
		return toDTO(servicio.controladorAccionConceptosTipoPlanilla(to(obj, ConceptosTipoPlanilla.class), accionType),
				ConceptosTipoPlanillaDTO.class);
	}

	@Override
	public List<ConceptosTipoPlanillaDTO> listarConceptosTipoPlanilla(BaseSearch filtro) {
		var resultado = toList(this.servicio.listarConceptosTipoPlanilla(filtro), ConceptosTipoPlanillaDTO.class,
				TIPO_PLANILLA, CONCEPTO_PDT);
		for (var obj : resultado) {
			generarDescripcionViewConceptoTipoPlanilla(obj.getTipoPlanilla());
			generarDescripcionViewConceptoPdt(obj.getConceptoPdt());
		}

		return resultado;
	}

	/*
	 * @Override public List<ConceptosTipoPlanillaDTO>
	 * obtenerFormulaConceptosTipoPlanilla() { return
	 * toList(this.servicio.obtenerFormulaConceptosTipoPlanilla(),
	 * ConceptosTipoPlanillaDTO.class, TIPO_PLANILLA, CONCEPTO_PDT); }
	 */

	@Override
	public int contarListarConceptosTipoPlanilla(BaseSearch filtro) {
		return this.servicio.contarListarConceptosTipoPlanilla(filtro);
	}

	@Override
	public ValoresUITDTO controladorAccionValoresUIT(ValoresUITDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionValoresUIT(to(obj, ValoresUIT.class), accionType), ValoresUITDTO.class);
	}

	@Override
	public List<ValoresUITDTO> listarValoresUIT(BaseSearch filtro) {
		var resultado = toList(this.servicio.listarValoresUIT(filtro), ValoresUITDTO.class);
		for (var obj : resultado) {
			generarDescripcionViewAnhio(obj.getAnhio());
		}
		return resultado;
	}

	@Override
	public int contarListarValoresUIT(BaseSearch filtro) {
		return this.servicio.contarListarValoresUIT(filtro);
	}

	@Override
	public ValoresUITDTO findValorUIT(ValoresUITDTO filtro) {
		return toDTO(servicio.findValorUIT(to(filtro, ValoresUIT.class)), ValoresUITDTO.class);
	}

	@Override
	public List<ConceptoRegimenPensionarioDTO> agregarConceptoRegimenPensionario(
			List<ConceptoRegimenPensionarioDTO> listaObj) {
		servicio.agregarConceptoRegimenPensionario(toListEntity(listaObj, ConceptoRegimenPensionario.class));
		return listaObj;
	}

	@Override
	public ConceptoRegimenPensionarioDTO controladorAccionConceptoRegimenPensionario(ConceptoRegimenPensionarioDTO obj,
			AccionType accionType) {
		return toDTO(servicio.controladorAccionConceptoRegimenPensionario(to(obj, ConceptoRegimenPensionario.class),
				accionType), ConceptoRegimenPensionarioDTO.class);
	}

	@Override
	public List<ConceptoRegimenPensionarioDTO> listarConceptoRegimenPensionario(BaseSearch filtro) {
		var completeMap = new HashMap<String, String>();
		completeMap.put("idItemByRegimenPensionario", "itemByRegimenPensionario");
		completeMap.put("idItemByMesByDevengue", "itemByMesByDevengue");
		var resultado = toList(servicio.listarConceptoRegimenPensionario(filtro), ConceptoRegimenPensionarioDTO.class);
		cacheUtil.completarData(resultado, completeMap, CACHE_ITEM);
		return resultado;
	}

	@Override
	public int contarListarConceptoRegimenPensionario(BaseSearch filtro) {
		return servicio.contarListarConceptoRegimenPensionario(filtro);
	}

	@Override
	public RentaQuintaCategoriaDTO controladorAccionRentaQuintaCategoria(RentaQuintaCategoriaDTO obj,
			AccionType accionType) {
		return toDTO(servicio.controladorAccionRentaQuintaCategoria(to(obj, RentaQuintaCategoria.class), accionType),
				RentaQuintaCategoriaDTO.class);
	}

	@Override
	public List<RentaQuintaCategoriaDTO> listarRentaQuintaCategoria(BaseSearch filtro) {
		return toList(this.servicio.listarRentaQuintaCategoria(filtro), RentaQuintaCategoriaDTO.class);
	}

	@Override
	public int contarListarRentaQuintaCategoria(BaseSearch filtro) {
		return this.servicio.contarListarRentaQuintaCategoria(filtro);
	}

	private ItemDTO generarDescripcionViewItem(ItemDTO item) {
		if (item != null) {
			item.setDescripcionView(item.getCodigoExterno() + " " + item.getNombre());
		}
		return item;
	}

	private ConceptoPdtDTO generarDescripcionViewConceptoPdt(ConceptoPdtDTO obj) {
		if (obj != null) {
			obj.setDescripcionView(obj.getCodigo() + " " + obj.getDescripcion());
		}
		return obj;
	}

	private TipoPlanillaDTO generarDescripcionViewConceptoTipoPlanilla(TipoPlanillaDTO obj) {
		if (obj != null) {
			obj.setDescripcionView(obj.getCodigo() + " " + obj.getDescripcion());
		}
		return obj;
	}

	private AnhioDTO generarDescripcionViewAnhio(AnhioDTO obj) {
		if (obj != null) {
			obj.setDescripcionView(obj.getIdAnhio() + " " + obj.getNombre());
		}
		return obj;
	}

	@Override
	public List<DetallePlanillaDTO> listarDetallePlanilla(BaseSearch filtro) throws Exception {
		var resultado = new ArrayList<DetallePlanillaDTO>();
		var personalMap = obtenerPersonalByCategoriaTrabajadorMap(filtro);
		if (StringUtil.isNotNullOrBlank(filtro.getSearch())) {
			filtro.setListaIdPersonal(new ArrayList<>(personalMap.keySet()));
		}
		var listaDetPlanillaTemp = servicio.listarDetallePlanilla(filtro);
		for (var obj : listaDetPlanillaTemp) {
			var objDetP = toDTO(obj, DetallePlanillaDTO.class, "planilla:{tipoPlanilla}");
			objDetP.setPersonal(toPojo(personalMap.get(objDetP.getIdPersonal()), PersonalResponseVo.class));
			var listaDetTipo = new ArrayList<DetallePlanillaConceptoDTO>();
			for (var objDet : obj.getDetallePlanillaDetallePlanillaConceptoList()) {
				var objTCDTO = toDTO(objDet, DetallePlanillaConceptoDTO.class, "concepto:{conceptoPdt}");
				var listaDet = toList(objDet.getDetallePlanillaDetallePlanillaConceptoList(),
						DetallePlanillaConceptoDTO.class, "detallePlanilla:{planilla}", "concepto:{conceptoPdt}");
				objTCDTO.setDetallePlanillaDetallePlanillaConceptoList(listaDet);
				listaDetTipo.add(objTCDTO);
			}
			objDetP.setDetallePlanillaDetallePlanillaConceptoList(listaDetTipo);
			resultado.add(objDetP);
		}
		return resultado;
	}

	@Override
	public int contarListarDetallePlanilla(BaseSearch filtro) throws Exception {
		if (StringUtil.isNotNullOrBlank(filtro.getSearch())) {
			var personalMap = obtenerPersonalByCategoriaTrabajadorMap(filtro);
			filtro.setListaIdPersonal(new ArrayList<>(personalMap.keySet()));
		}
		return this.servicio.contarListarDetallePlanilla(filtro);
	}

	@Override
	public PeriodoPlanillaDTO controladorAccionPeriodoPlanilla(PeriodoPlanillaDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionPeriodoPlanilla(to(obj, PeriodoPlanilla.class), accionType),
				PeriodoPlanillaDTO.class);
	}

	@Override
	public List<PeriodoPlanillaDTO> listarPeriodoPlanilla(BaseSearch filtro) {
		var resultado = toList(this.servicio.listarPeriodoPlanilla(filtro), PeriodoPlanillaDTO.class);
		for (var obj : resultado) {
			generarDescripcionViewAnhio(obj.getAnhio());
			generarDescripcionViewItem(obj.getItemByPeriodoMes());
		}
		return resultado;
	}

	@Override
	public int contarListarPeriodoPlanilla(BaseSearch filtro) {
		return this.servicio.contarListarPeriodoPlanilla(filtro);
	}

	@Override
	public PeriodoPlanillaDTO findPeriodoPlanilla(PeriodoPlanillaDTO filtro) {
		return toDTO(servicio.findPeriodoPlanilla(to(filtro, PeriodoPlanilla.class)), PeriodoPlanillaDTO.class);
	}

	@Override
	public TareoPersonalDTO controladorAccionTareoPersonal(TareoPersonalDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionTareoPersonal(to(obj, TareoPersonal.class), accionType),
				TareoPersonalDTO.class);
	}

	@Override
	public List<TareoPersonalDTO> listarTareoPersonal(BaseSearch filtro) throws Exception {
		var resultado = new ArrayList<TareoPersonalDTO>();
		if (!StringUtil.isNullOrEmptyNumeric(filtro.getIdItemByCategoriaTrabajador())
				&& !StringUtil.isNullOrEmptyNumeric(filtro.getIdItemByMes())
				&& !StringUtil.isNullOrEmptyNumeric(filtro.getIdAnhio())) {
			var personalDataMap = obtenerPersonalByCategoriaTrabajadorMapPer(filtro);
			var personalMap = (Map<String, PersonalVO>) personalDataMap.get("personalMap");
			var listPersonal = (List<PersonalVO>) personalDataMap.get("listPersonal");
			if (StringUtil.isNotNullOrBlank(filtro.getSearch())) {
				filtro.setListaIdPersonal(new ArrayList<>(personalMap.keySet()));
			}
			var listTareoPersonalTmp = this.servicio.listarTareoPersonal(filtro);
			var tareoPersonalRegistroMap = new HashMap<String, TareoPersonalDTO>();

			for (var obj : listTareoPersonalTmp) {
				var objTemp = toDTO(obj, TareoPersonalDTO.class);
				tareoPersonalRegistroMap.put(obj.getIdPersonal(), objTemp);
			}
			if (listPersonal != null) {
				for (var personal : listPersonal) {
					if (!tareoPersonalRegistroMap.containsKey(personal.getIdPersonal())) {
						var obj = new TareoPersonalDTO();
						obj.setIdItemByMes(filtro.getIdItemByMes());
						obj.setIdAnhio(filtro.getIdAnhio());
						obj.setIdItemByCategoriaOcupacional(filtro.getIdItemByCategoriaTrabajador());
						obj.setIdPersonal(personal.getIdPersonal());
						resultado.add(obj);
					} else {
						resultado.add(tareoPersonalRegistroMap.get(personal.getIdPersonal()));
					}
				}
			}
			for (var objTemp : resultado) {
				objTemp.setPersonal(toPojo(personalMap.get(objTemp.getIdPersonal()), PersonalResponseVo.class));
			}
		}
		return resultado;
	}

	@Override
	public int contarListarTareoPersonal(BaseSearch filtro) {
		return servicio.contarListarTareoPersonal(filtro);
	}

	@Override
	public TipoPlanillaDTO controladorAccionTipoPlanilla(TipoPlanillaDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionTipoPlanilla(to(obj, TipoPlanilla.class), accionType),
				TipoPlanillaDTO.class);
	}

	@Override
	public List<TipoPlanillaDTO> listarTipoPlanilla(BaseSearch filtro) {
		var resultado = toList(this.servicio.listarTipoPlanilla(filtro), TipoPlanillaDTO.class);
		for (var tipoPlanillaDTO : resultado) {
			generarDescripcionViewItem(tipoPlanillaDTO.getItemByCategoriaTrabajador());
			generarDescripcionViewItem(tipoPlanillaDTO.getItemByTipoMoneda());
		}
		return resultado;

	}

	@Override
	public int contarListarTipoPlanilla(BaseSearch filtro) {
		return this.servicio.contarListarTipoPlanilla(filtro);
	}

	@Override
	public DetRentaQuintaCategoriaDTO controladorAccionDetRentaQuintaCategoria(DetRentaQuintaCategoriaDTO obj,
			AccionType accionType) {
		return toDTO(
				servicio.controladorAccionDetRentaQuintaCategoria(to(obj, DetRentaQuintaCategoria.class), accionType),
				DetRentaQuintaCategoriaDTO.class);
	}

	@Override
	public List<DetRentaQuintaCategoriaDTO> listarDetRentaQuintaCategoria(BaseSearch filtro) {
		return toList(this.servicio.listarDetRentaQuintaCategoria(filtro), DetRentaQuintaCategoriaDTO.class);
	}

	@Override
	public int contarListarDetRentaQuintaCategoria(BaseSearch filtro) {
		return this.servicio.contarListarDetRentaQuintaCategoria(filtro);
	}

	private Map<String, PersonalVO> obtenerPersonalByCategoriaTrabajadorMap(BaseSearch filtro) throws Exception {
		return (Map<String, PersonalVO>) obtenerPersonalByCategoriaTrabajadorMapPer(filtro).get("personalMap");
	}

	private Map<String, Object> obtenerPersonalByCategoriaTrabajadorMapPer(BaseSearch filtro) throws Exception {
		Map<String, Object> filtroMap = new HashMap<String, Object>();

		filtroMap.put("idItemByCategoriaTrabajador", filtro.getIdItemByCategoriaTrabajador());
		filtroMap.put("idItemByEps", filtro.getIdItemByEps());
		if (!CollectionUtil.isEmpty(filtro.getListaIdPersonal())) {
			filtroMap.put("idPersonal", filtro.getListaIdPersonal());
		}
		filtroMap.put("search", filtro.getSearch() == null ? "" : filtro.getSearch());
		return this.rrhhPersonalClient.obtenerPersonalDataMap(filtro.getAuthToken(), filtroMap);

	}

	@Override
	public List<PersonalConceptoDTO> listarPersonalConcepto(BaseSearch filtro) throws Exception {
		var resultado = new ArrayList<PersonalConceptoDTO>();
		if (StringUtil.isNotNullOrBlank(filtro.getIdPeriodoPlanilla())
				&& StringUtil.isNotNullOrBlank(filtro.getIdTipoPlanilla())
				&& !StringUtil.isNullOrEmptyNumeric(filtro.getIdItemByCategoriaTrabajador())) {

			var personalDataMap = obtenerPersonalByCategoriaTrabajadorMapPer(filtro);
			var personalMap = (Map<String, PersonalVO>) personalDataMap.get("personalMap");
			var listPersonal = (List<PersonalVO>) personalDataMap.get("listPersonal");
			if (StringUtil.isNotNullOrBlank(filtro.getSearch())) {
				filtro.setListaIdPersonal(new ArrayList<>(personalMap.keySet()));
			}
			var listPersonalConceptoTmp = this.servicio.listarPersonalConcepto(filtro);
			var personalConceptoRegistroMap = new HashMap<String, PersonalConceptoDTO>();

			for (var obj : listPersonalConceptoTmp) {
				var objTemp = toDTO(obj, PersonalConceptoDTO.class, TIPO_PLANILLA, "periodoPlanilla");
				personalConceptoRegistroMap.put(obj.getIdPersonal(), objTemp);
			}
			if (listPersonal != null) {
				for (var personal : listPersonal) {
					if (!personalConceptoRegistroMap.containsKey(personal.getIdPersonal())) {
						var obj = new PersonalConceptoDTO();
						obj.setIdPersonal(personal.getIdPersonal());
						obj.setTipoPlanilla(new TipoPlanillaDTO());
						obj.getTipoPlanilla().setIdTipoPlanilla(filtro.getIdTipoPlanilla());
						obj.setPeriodoPlanilla(new PeriodoPlanillaDTO());
						obj.getPeriodoPlanilla().setIdPeriodoPlanilla(filtro.getIdPeriodoPlanilla());
						resultado.add(obj);
					} else {
						resultado.add(personalConceptoRegistroMap.get(personal.getIdPersonal()));
					}
				}
			}
			for (var objTemp : resultado) {
				objTemp.setPersonal(toPojo(personalMap.get(objTemp.getIdPersonal()), PersonalResponseVo.class));
				var listaDet = this.servicio.obtenerDetallePersonalConcepto(filtro.getIdTipoPlanilla(),
						objTemp.getIdPersonal(), objTemp.getPeriodoPlanilla().getIdPeriodoPlanilla());
				var listaTmp = new ArrayList<DetallePersonalConceptoDTO>();
				for (var objD : listaDet) {
					var objDTO = toDTO(objD, DetallePersonalConceptoDTO.class,
							"conceptosTipoPlanilla:{conceptoPdt;tipoPlanilla}");
					objDTO.setDetallePersonalConceptoList(
							toList(objD.getDetallePersonalConceptoList(), DetallePersonalConceptoDTO.class,
									"personalConcepto", "conceptosTipoPlanilla:{conceptoPdt;tipoPlanilla}"));
					listaTmp.add(objDTO);
				}
				objTemp.setDetallePersonalConceptoList(listaTmp);
			}
		}
		return resultado;
	}

	@Override
	public int contarListarPersonalConcepto(BaseSearch filtro) {
		return this.servicio.contarListarPersonalConcepto(filtro);
	}

	@Override
	public Map<String, DetallePersonalConceptoDTO> listarDetallePersonalConceptoMap(String idPersonal,
			String idTipoPlanilla, String idPeriodo) {
		var resultado = new HashMap<String, DetallePersonalConceptoDTO>();
		var temMap = servicio.listarDetallePersonalConceptoMap(idPersonal, idTipoPlanilla, idPeriodo);
		for (var entry : temMap.entrySet()) {
			resultado.put(entry.getKey(), toDTO(temMap.get(entry.getKey()), DetallePersonalConceptoDTO.class));
		}
		return resultado;
	}

	@Override
	public void registrarPersonalConcepto(List<PersonalConceptoDTO> listaObj) {
		var listaObjE = new ArrayList<PersonalConcepto>();
		for (var objData : listaObj) {
			var obj = to(objData, PersonalConcepto.class);
			var listaConcepto = new ArrayList<DetallePersonalConcepto>();
			for (var objC : objData.getDetallePersonalConceptoList()) {
				var objD = to(objC, DetallePersonalConcepto.class);
				objD.setDetallePersonalConceptoList(
						toListEntity(objC.getDetallePersonalConceptoList(), DetallePersonalConcepto.class));
				listaConcepto.add(objD);
			}
			obj.setDetallePersonalConceptoList(listaConcepto);
			listaObjE.add(obj);
		}
		servicio.registrarPersonalConcepto(listaObjE);

	}

	@Override
	public PersonalConceptoDTO controladorAccionPersonalConcepto(PersonalConceptoDTO objData, AccionType accionType) {
		var obj = to(objData, PersonalConcepto.class);
		var listaConcepto = new ArrayList<DetallePersonalConcepto>();
		for (var objC : objData.getDetallePersonalConceptoList()) {
			var objD = to(objC, DetallePersonalConcepto.class);
			objD.setDetallePersonalConceptoList(
					toListEntity(objC.getDetallePersonalConceptoList(), DetallePersonalConcepto.class));
			listaConcepto.add(objD);
		}
		obj.setDetallePersonalConceptoList(listaConcepto);
		return toDTO(servicio.controladorAccionPersonalConcepto(obj, accionType), PersonalConceptoDTO.class);
	}

	@Override
	public EPSConfDTO controladorAccionEPSConf(EPSConfDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionEPSConf(to(obj, EPSConf.class), accionType), EPSConfDTO.class);
	}

	@Override
	public List<EPSConfDTO> listarEPSConf(BaseSearch filtro) {
		var completeMap = new HashMap<String, String>();
		completeMap.put("idItemByEps", "itemByEps");
		var resultado = toList(servicio.listarEPSConf(filtro), EPSConfDTO.class);
		cacheUtil.completarData(resultado, completeMap, CACHE_ITEM);
		return resultado;
	}

	@Override
	public int contarListarEPSConf(BaseSearch filtro) {
		return servicio.contarListarEPSConf(filtro);
	}

	@Override
	public List<EPSPersonalDTO> listarEPSPersonal(BaseSearch filtro) throws Exception {
		var resultado = new ArrayList<EPSPersonalDTO>();
		if (!StringUtil.isNullOrEmptyNumeric(filtro.getIdItemByEps())
				&& !StringUtil.isNullOrEmptyNumeric(filtro.getIdItemByMes())
				&& !StringUtil.isNullOrEmptyNumeric(filtro.getIdAnhio())) {
			var personalDataMap = obtenerPersonalByCategoriaTrabajadorMapPer(filtro);
			var personalMap = (Map<String, PersonalVO>) personalDataMap.get("personalMap");
			var listPersonal = (List<PersonalVO>) personalDataMap.get("listPersonal");
			if (StringUtil.isNotNullOrBlank(filtro.getSearch())) {
				filtro.setListaIdPersonal(new ArrayList<>(personalMap.keySet()));
			}
			var listTareoPersonalTmp = this.servicio.listarEPSPersonal(filtro);
			var epsPersonalRegistroMap = new HashMap<String, EPSPersonalDTO>();

			for (var obj : listTareoPersonalTmp) {
				var objTemp = toDTO(obj, EPSPersonalDTO.class);
				epsPersonalRegistroMap.put(obj.getIdPersonal(), objTemp);
			}
			if (listPersonal != null) {
				for (var personal : listPersonal) {
					if (!epsPersonalRegistroMap.containsKey(personal.getIdPersonal())) {
						var obj = new EPSPersonalDTO();
						obj.setIdItemByMes(filtro.getIdItemByMes());
						obj.setIdAnhio(filtro.getIdAnhio());
						obj.setIdPersonal(personal.getIdPersonal());
						resultado.add(obj);
					} else {
						resultado.add(epsPersonalRegistroMap.get(personal.getIdPersonal()));
					}
				}
			}
			for (var objTemp : resultado) {
				objTemp.setPersonal(toPojo(personalMap.get(objTemp.getIdPersonal()), PersonalResponseVo.class));
			}
		}
		return resultado;
	}

	@Override
	public int contarListarEPSPersonal(BaseSearch filtro) {
		return servicio.contarListarEPSPersonal(filtro);
	}

	@Override
	public EPSPersonalDTO controladorAccionEPSPersonal(EPSPersonalDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionEPSPersonal(to(obj, EPSPersonal.class), accionType),
				EPSPersonalDTO.class);
	}

	@Override
	public VacacionesDTO controladorAccionVacaciones(VacacionesDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionVacaciones(to(obj, Vacaciones.class), accionType), VacacionesDTO.class);
	}

	@Override
	public List<VacacionesDTO> listarVacaciones(BaseSearch filtro) throws Exception {
		var resultado = new ArrayList<VacacionesDTO>();
		if (!StringUtil.isNotNullOrBlank(filtro.getSearch())) {
			resultado = (ArrayList<VacacionesDTO>) toList(servicio.listarVacaciones(filtro), VacacionesDTO.class);
			var listaIdPersonal = new ArrayList<String>();
			for (var obj : resultado) {
				if (!listaIdPersonal.contains(obj.getIdPersonal())) {
					listaIdPersonal.add(obj.getIdPersonal());
				}
				filtro.setListaIdPersonal(listaIdPersonal);
			}
		}
		var personalDataMap = obtenerPersonalByCategoriaTrabajadorMapPer(filtro);
		var personalMap = (Map<String, PersonalVO>) personalDataMap.get("personalMap");
		if (StringUtil.isNotNullOrBlank(filtro.getSearch())) {
			filtro.setListaIdPersonal(new ArrayList<>(personalMap.keySet()));
			resultado = (ArrayList<VacacionesDTO>) toList(servicio.listarVacaciones(filtro), VacacionesDTO.class);
		}
		for (var obj : resultado) {
			obj.setPersonal(toPojo(personalMap.get(obj.getIdPersonal()), PersonalResponseVo.class));
			obj.setDias(FechaUtil.restaFechas(obj.getFechaInicio(), obj.getFechaFin()));
		}
		return resultado;
	}

	@Override
	public int contarListarVacaciones(BaseSearch filtro) {
		return servicio.contarListarVacaciones(filtro);
	}

	@Override
	public List<NavigationItemVO> listarVariableAplicacion(BaseSearch filtro) throws Exception {
		filtro.setOffSet(0);
		var listaVariable = servicio.listarVariableAplicacion();
		filtro.setTipo("");
		var listaConceto = listarConceptosTipoPlanilla(filtro);
		listaVariable.addAll(generarConceptoVariable(listaConceto));
		boolean isSoloGrupo = !StringUtil.isNotNullOrBlank(filtro.getIdPadreView());
		listaVariable.addAll(generarVariableConf(listarVariableConfDet(filtro), isSoloGrupo));
		var filtroMap = new HashMap<String, Object>();
		filtroMap.put("sortFields", "codigo");
		filtroMap.put("sortDirections", "asc");
		var listaParametro = commonServiceClient.listaParametro(filtro.getAuthToken(), filtroMap);
		return generarMenu(generarMenuGrupo(listaVariable, listaParametro));
	}

	private List<MenuDTO> generarMenuGrupo(List<SelectItemVO> listaVariable, RespuestaWSVO<ParametroVO> parametro) {
		List<MenuDTO> resultado = new ArrayList<>();
		for (var obj : listaVariable) {
			String key = obj.getDescripcion();
			MenuDTO padre = getMenu(key, key, "heroicons_solid:variable");
			for (var objH : obj.getListaData()) {
				MenuDTO hijo = getMenu(objH.getId(), objH.getId().toString(), "heroicons_solid:variable");
				hijo.setMenuPadre(padre);
				padre.getMenuHijos().add(hijo);
			}
			resultado.add(padre);
		}
		if (!parametro.isError() && parametro.isData()) {
			String key = "Parametro";
			MenuDTO padre = getMenu(key, key, "heroicons_solid:variable");
			for (var objH : parametro.getListaResultado()) {
				MenuDTO hijo = getMenu(objH.getCodigo(), objH.getCodigo(), "heroicons_solid:variable");
				hijo.setMenuPadre(padre);
				padre.getMenuHijos().add(hijo);
			}
			resultado.add(padre);
		}
		return resultado;
	}

	private String getNombreConcepto(String key) {
		if ("I".equalsIgnoreCase(key)) {
			return "Concepto Ingreso";
		} else if ("D".equalsIgnoreCase(key)) {
			return "Concepto Descuento";
		} else if ("T".equalsIgnoreCase(key)) {
			return "Concepto Aportaciones Trabajador";
		} else if ("A".equalsIgnoreCase(key)) {
			return "Concepto Aportaciones Empleador";
		}
		return "Concepto";
	}

	public List<SelectItemVO> generarConceptoVariable(List<ConceptosTipoPlanillaDTO> listaConcepto) {
		var resultado = new ArrayList<SelectItemVO>();
		var resultadoMap = new HashMap<String, SelectItemVO>();
		for (var obj : listaConcepto) {
			String key = getNombreConcepto(obj.getConceptoPdt().getTipo());
			if (!resultadoMap.containsKey(key)) {
				var objVar = new SelectItemVO(obj.getCodigo(), obj.getDescripcion(), key);
				var value = new ArrayList<SelectItemVO>();
				value.add(new SelectItemVO(obj.getCodigo(), obj.getDescripcion(), key));
				objVar.setListaData(value);
				resultado.add(objVar);
				resultadoMap.put(key, objVar);
			} else {
				var objVar = resultadoMap.get(key);
				var value = objVar.getListaData();
				value.add(new SelectItemVO(obj.getCodigo(), obj.getDescripcion(), key));
			}
		}
		return resultado;
	}

	public List<SelectItemVO> generarVariableConf(List<VariableConfDetDTO> lista, boolean isSoloGrupo) {
		var resultado = new ArrayList<SelectItemVO>();
		var resultadoMap = new HashMap<String, SelectItemVO>();
		var resultadoPadreMap = new HashMap<String, String>();
		if (isSoloGrupo) {
			String keyPadre = "Variable Grupo";
			var objVar = new SelectItemVO(keyPadre, keyPadre, keyPadre);
			var value = new ArrayList<SelectItemVO>();
			objVar.setListaData(value);
			resultado.add(objVar);
			resultadoMap.put(keyPadre, objVar);
			for (var objDet : lista) {
				var obj = objDet.getVariableConf();
				String key = obj.getIdVariableConf();
				if (!resultadoPadreMap.containsKey(key)) {
					objVar.getListaData().add(new SelectItemVO(obj.getNombre(), obj.getNombre(), keyPadre));
					resultadoPadreMap.put(key, "");
				}
			}
		}
		if (!isSoloGrupo) {
			for (var obj : lista) {
				String key = "Variable Grupo " + obj.getVariableConf().getNombre();
				if (!resultadoMap.containsKey(key)) {
					var objVar = new SelectItemVO(obj.getVariable(), obj.getVariable(), key);
					var value = new ArrayList<SelectItemVO>();
					value.add(new SelectItemVO(obj.getVariable(), obj.getVariable(), key));
					objVar.setListaData(value);
					resultado.add(objVar);
					resultadoMap.put(key, objVar);
				} else {
					var objVar = resultadoMap.get(key);
					var value = objVar.getListaData();
					value.add(new SelectItemVO(obj.getVariable(), obj.getVariable(), key));
				}
			}
		}
		return resultado;
	}

	private MenuDTO getMenu(Object id, String nombre, String icono) {
		var value = new MenuDTO();
		value.setMenuPadre(new MenuDTO());
		value.setId(id);
		value.setIcono(icono);
		value.setNombre(nombre);
		value.setDescripcion(nombre);
		value.setMenuHijos(new ArrayList<MenuDTO>());
		return value;
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
			if (menu.getMenuPadre().getId() == null) {
				NavigationItemVO obj = parseMenu(menu);
				obj.setChildren(generarSubMenu(menu.getMenuHijos(), menu.getId()));
				resultado.add(obj);
			}
		}
		return resultado;
	}

	private NavigationItemVO parseMenu(MenuDTO menu) {
		NavigationItemVO obj = new NavigationItemVO();
		obj.setIcon(menu.getIcono());
		obj.setId(menu.getNombre().toLowerCase());
		if (menu.getMenuPadre() != null && StringUtil.isNotNullOrBlank(menu.getMenuPadre().getId())) {
			obj.setId(menu.getMenuPadre().getNombre().toLowerCase() + "." + menu.getNombre().toLowerCase());
		}
		obj.setTitle(menu.getNombre());
		obj.setLink(menu.getUrl());
		obj.setType("basic");
		if (obj.getId().contains(".")) {
			obj.setType("basic");
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
	private List<NavigationItemVO> generarSubMenu(List<MenuDTO> listaMenu, Object idMenuPadre) {
		List<NavigationItemVO> resultado = new ArrayList<>();
		for (var menu : listaMenu) {
			if (menu.getMenuPadre().getId() != null && (menu.getMenuPadre().getId().equals(idMenuPadre))) {
				NavigationItemVO obj = parseMenu(menu);
				obj.setChildren(generarSubMenu(listaMenu, menu.getId()));
				resultado.add(obj);
			}
		}
		return resultado;
	}

	@Override
	public FeriadoDTO controladorAccionFeriado(FeriadoDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionFeriado(to(obj, Feriado.class), accionType), FeriadoDTO.class);
	}

	@Override
	public List<FeriadoDTO> listarFeriado(BaseSearch filtro) throws Exception {
		var completeMap = new HashMap<String, String>();
		completeMap.put("idMes", "itemByMes");
		var resultado = toList(servicio.listarFeriado(filtro), FeriadoDTO.class);
		cacheUtil.completarData(resultado, completeMap, CACHE_ITEM);
		return resultado;
	}

	@Override
	public int contarListarFeriado(BaseSearch filtro) {
		return servicio.contarListarFeriado(filtro);
	}

	@Override
	public VariableConfDTO controladorAccionVariableConf(VariableConfDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionVariableConf(to(obj, VariableConf.class), accionType),
				VariableConfDTO.class);
	}

	@Override
	public List<VariableConfDTO> listarVariableConf(BaseSearch filtro) {
		return toList(servicio.listarVariableConf(filtro), VariableConfDTO.class);
	}

	@Override
	public int contarListarVariableConf(BaseSearch filtro) {
		return servicio.contarListarVariableConf(filtro);
	}

	@Override
	public VariableConfDetDTO controladorAccionVariableConfDet(VariableConfDetDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionVariableConfDet(to(obj, VariableConfDet.class), accionType),
				VariableConfDetDTO.class);
	}

	@Override
	public List<VariableConfDetDTO> listarVariableConfDet(BaseSearch filtro) {
		return toList(servicio.listarVariableConfDet(filtro), VariableConfDetDTO.class, "variableConf");
	}

	@Override
	public int contarListarVariableConfDet(BaseSearch filtro) {
		return servicio.contarListarVariableConfDet(filtro);
	}

	@Override
	public InformaOtrosIngreso5taDTO controladorAccionInformaOtrosIngreso5ta(InformaOtrosIngreso5taDTO obj,
			AccionType accionType) {
		return toDTO(
				servicio.controladorAccionInformaOtrosIngreso5ta(to(obj, InformaOtrosIngreso5ta.class), accionType),
				InformaOtrosIngreso5taDTO.class);
	}

	@Override
	public List<InformaOtrosIngreso5taDTO> listarInformaOtrosIngreso5ta(BaseSearch filtro) {
		return toList(servicio.listarInformaOtrosIngreso5ta(filtro), InformaOtrosIngreso5taDTO.class);
	}

	@Override
	public int contarListarInformaOtrosIngreso5ta(BaseSearch filtro) {
		return servicio.contarListarInformaOtrosIngreso5ta(filtro);
	}
}