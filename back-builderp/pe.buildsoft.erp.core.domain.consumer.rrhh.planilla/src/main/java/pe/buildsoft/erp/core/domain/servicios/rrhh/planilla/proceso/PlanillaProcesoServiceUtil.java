package pe.buildsoft.erp.core.domain.servicios.rrhh.planilla.proceso;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ejb.AsyncResult;
import jakarta.inject.Inject;
import pe.buildsoft.erp.core.domain.drools.IProcesarReglaUtil;
import pe.buildsoft.erp.core.domain.entidades.planilla.ConceptoRegimenPensionario;
import pe.buildsoft.erp.core.domain.entidades.planilla.ConceptosTipoPlanilla;
import pe.buildsoft.erp.core.domain.entidades.planilla.DetallePersonalConcepto;
import pe.buildsoft.erp.core.domain.entidades.planilla.DetallePlanilla;
import pe.buildsoft.erp.core.domain.entidades.planilla.DetallePlanillaConcepto;
import pe.buildsoft.erp.core.domain.entidades.planilla.EPSConf;
import pe.buildsoft.erp.core.domain.entidades.planilla.EPSPersonal;
import pe.buildsoft.erp.core.domain.entidades.planilla.PeriodoPlanilla;
import pe.buildsoft.erp.core.domain.entidades.planilla.Planilla;
import pe.buildsoft.erp.core.domain.entidades.planilla.TareoPersonal;
import pe.buildsoft.erp.core.domain.entidades.planilla.TipoPlanilla;
import pe.buildsoft.erp.core.domain.entidades.planilla.ValoresUIT;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.AdelantoConsumerDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.ConceptoRegimenPensionarioConsumerDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.ConceptoTrabajadorConsumerDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.ConceptosTipoPlanillaConsumerDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.DetallePersonalConceptoConsumerDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.DetallePlanillaConceptoConsumerDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.DetallePlanillaConsumerDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.EPSConfConsumerDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.EPSPersonalConsumerDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.FeriadoConsumerDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.InformaOtrosIngreso5taConsumerDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.PeriodoPlanillaConsumerDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.PlanillaConsumerDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.TareoPersonalConsumerDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.ValoresUITConsumerDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.VariableConfDetConsumerDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.webclient.rest.common.CommonServiceClient;
import pe.buildsoft.erp.core.domain.interfaces.webclient.rest.rrhh.escalafon.RRHHEscalafonServiceClient;
import pe.buildsoft.erp.core.domain.planilla.type.VariableAplicacionType;
import pe.buildsoft.erp.core.infra.data.entidades.webclient.rest.common.vo.ParametroVO;
import pe.buildsoft.erp.core.infra.data.entidades.webclient.rest.rrhh.escalafon.vo.ConceptoCaluldadosVO;
import pe.buildsoft.erp.core.infra.data.entidades.webclient.rest.rrhh.escalafon.vo.PersonalVO;
import pe.buildsoft.erp.core.infra.transversal.cache.ICache;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.paginator.IDataProvider;
import pe.buildsoft.erp.core.infra.transversal.paginator.LazyLoadingList;
import pe.buildsoft.erp.core.infra.transversal.type.MesType;
import pe.buildsoft.erp.core.infra.transversal.type.RespuestaNaturalType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.BaseTransfer;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ObjectUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

public class PlanillaProcesoServiceUtil extends BaseTransfer {

	protected static Logger log = LoggerFactory.getLogger(PlanillaProcesoServiceUtil.class);

	protected static final String CONCEPTO_INGRESOS = "I";
	protected static final String CONCEPTO_DESCUENTOS = "D";
	protected static final String CONCEPTO_APORTACIONES_TRABAJADROR = "T";
	protected static final String CONCEPTO_APORTACIONES_EMPLEADOR = "A";

	@Inject
	protected ICache cache;

	/** El servicio valores u i t dao impl. */
	@Inject
	protected ValoresUITConsumerDaoLocal valoresUITDao;

	@Inject
	protected RRHHEscalafonServiceClient rrhhEscalafonClient;

	/** El servicio detalle planilla dao impl. */
	@Inject
	protected DetallePlanillaConsumerDaoLocal detallePlanillaDao;

	/** El servicio planilla dao impl. */
	@Inject
	protected PlanillaConsumerDaoLocal planillaDao;

	/** El servicio concepto regimen pensionario dao impl. */
	@Inject
	protected ConceptoRegimenPensionarioConsumerDaoLocal conceptoRegimenPensionarioDao;

	/** El servicio adelanto dao impl. */
	@Inject
	protected AdelantoConsumerDaoLocal adelantoDao;

	/** El servicio concepto trabajador dao impl. */
	@Inject
	protected ConceptoTrabajadorConsumerDaoLocal conceptoTrabajadorDao;

	/** El servicio conceptos tipo planilla dao impl. */
	@Inject
	protected ConceptosTipoPlanillaConsumerDaoLocal conceptosTipoPlanillaDao;

	/** El servicio detalle planilla concepto dao impl. */
	@Inject
	protected DetallePlanillaConceptoConsumerDaoLocal detallePlanillaConceptoDao;

	@Inject
	protected DetallePersonalConceptoConsumerDaoLocal detallePersonalConceptoDao;

	@Inject
	protected IProcesarReglaUtil procesarReglaUtil;

	@Inject
	protected TareoPersonalConsumerDaoLocal tareoPersonalDao;

	@Inject
	protected PeriodoPlanillaConsumerDaoLocal periodoPlanillaDao;

	@Inject
	protected CommonServiceClient commonServiceClient;

	/** El servicio eps personal dao impl. */
	@Inject
	protected EPSPersonalConsumerDaoLocal epsPersonalDao;

	@Inject
	protected EPSConfConsumerDaoLocal epsConfDao;

	@Inject
	protected FeriadoConsumerDaoLocal feriadoDao;

	@Inject
	protected VariableConfDetConsumerDaoLocal variableConfDetDao;

	/** El servicio renta empresa ant dao impl. */
	@Inject
	protected InformaOtrosIngreso5taConsumerDaoLocal informaOtrosIngreso5taDao;

	/*
	 * protected EPSPersonal registrarEPS(PersonalVO obj, PeriodoPlanilla periodo,
	 * Map<String, Object> variableMap, Context interprete) {
	 * 
	 * if (obj.getIdPersonal().equalsIgnoreCase("42bef914feab49a2b76dcc094c19a1e4"))
	 * { log.info("valiando personal getVariableMap " + obj.getNombres()); }
	 * EPSPersonal objPersist = new EPSPersonal();
	 * objPersist.setIdEPSPersonal(epsPersonalDao.generarId());
	 * objPersist.setIdPersonal(obj.getIdPersonal());
	 * objPersist.setIdItemByEps(obj.getIdItemByEps());
	 * objPersist.setIdItemByMes(periodo.getIdItemByPeriodoMes());
	 * objPersist.setIdAnhio(periodo.getIdAnhio());
	 * 
	 * ConceptoCaluldadosVO objCon = new ConceptoCaluldadosVO();
	 * objCon.setVariable("planMonto");
	 * objCon.setFormula("REDONDEAR(planBase*cantAfiliados,2)");
	 * objPersist.setPlanMonto(calcularFormula(obj, objCon, variableMap,
	 * interprete)); variableMap.put("planMonto", objPersist.getPlanMonto());
	 * 
	 * objCon = new ConceptoCaluldadosVO(); objCon.setVariable("creditoPct");
	 * objCon.setFormula(
	 * "REDONDEAR((ingresoTotalAfectoTotales*(9/100)*(25/100)/cantAfiliadosTotales)*cantAfiliados,2)"
	 * ); objPersist.setCreditoPct(calcularFormula(obj, objCon, variableMap,
	 * interprete)); variableMap.put("creditoPct", objPersist.getCreditoPct());
	 * 
	 * objCon = new ConceptoCaluldadosVO(); objCon.setVariable("sueldoPct");
	 * objCon.setFormula("REDONDEAR((ingresoTotalAfecto)*(2.25/100),2)");
	 * objPersist.setSueldoPct(calcularFormula(obj, objCon, variableMap,
	 * interprete)); variableMap.put("sueldoPct", objPersist.getSueldoPct());
	 * 
	 * objCon = new ConceptoCaluldadosVO(); objCon.setVariable("essaludPct");
	 * objCon.setFormula("REDONDEAR((ingresoTotalAfecto)*(9/100),2)");
	 * objPersist.setEssaludPct(calcularFormula(obj, objCon, variableMap,
	 * interprete)); variableMap.put("essaludPct", objPersist.getEssaludPct());
	 * 
	 * objCon = new ConceptoCaluldadosVO(); objCon.setVariable("afiliados");
	 * objCon.setFormula("cantAfiliados");
	 * objPersist.setAfiliados(calcularFormula(obj, objCon, variableMap,
	 * interprete).intValue()); variableMap.put("afiliados",
	 * objPersist.getAfiliados());
	 * 
	 * objCon = new ConceptoCaluldadosVO(); objCon.setVariable("descontar");
	 * objCon.setFormula("REDONDEAR(planMonto-creditoPct,2)");
	 * objPersist.setDescontar(calcularFormula(obj, objCon, variableMap,
	 * interprete)); variableMap.put("descontar", objPersist.getDescontar());
	 * 
	 * objCon = new ConceptoCaluldadosVO();
	 * objCon.setVariable("descontarTrabajador"); //
	 * objCon.setFormula("REDONDEAR(planBase*1,2) - //
	 * REDONDEAR((ingresoTotalAfectoTotales*(9/100)*(25/100)/cantAfiliadosTotales)*1
	 * ,2)"); // var xx = calcularFormula(obj, objCon, variableMap, interprete);
	 * objCon.setFormula(
	 * "REDONDEAR(descontar-(REDONDEAR(planBase*1,2) - REDONDEAR((ingresoTotalAfectoTotales*(9/100)*(25/100)/cantAfiliadosTotales)*1,2)),2)"
	 * ); objPersist.setDescontarTrabajador(calcularFormula(obj, objCon,
	 * variableMap, interprete)); variableMap.put("descontarTrabajador",
	 * objPersist.getDescontarTrabajador()); epsPersonalDao.save(objPersist); return
	 * objPersist; }
	 */

	// https://www.defontana.com/pe/todo-sobre-el-impuesto-a-la-renta-de-quinta-categoria/
	/*
	 * protected Map<String, String> getFormulaRenta5ta(PeriodoPlanilla periodo) {
	 * Map<String, String> resultado = new LinkedHashMap<>();
	 * 
	 * String formula = "";
	 * 
	 * for (MesType mes : MesType.values()) { String keyPlanillaMes = mes.getValue()
	 * + "Planilla"; formula = " " + keyPlanillaMes + " > 0 ?  " + keyPlanillaMes +
	 * "  : remuneracion + asignacionFamiliar"; resultado.put(mes.getValue(),
	 * formula); }
	 * 
	 * formula = "julio*1.0675"; resultado.put("gratiJulio", formula);
	 * 
	 * formula = "diciembre*1.0675";
	 * 
	 * resultado.put("gratiDiciembre", formula);
	 * 
	 * formula = "0"; resultado.put("ingresoEmpAnt", formula);
	 * 
	 * formula =
	 * "enero + febrero + marzo + abril + mayo + junio + julio + gratiJulio + agosto + septiembre + octubre + noviembre + diciembre + gratiDiciembre + ingresoEmpAnt"
	 * ; resultado.put("totalGeneral5ta", formula);
	 * 
	 * formula = "totalGeneral5ta > 7*uit ?  7*uit : 0";
	 * resultado.put("deduccion7UIT", formula);
	 * 
	 * formula = "totalGeneral5ta > 7*uit ?  totalGeneral5ta- deduccion7UIT : 0";
	 * resultado.put("baseImponibleRenta", formula);
	 * 
	 * formula = "baseImponibleRenta <= 5*uit ?  baseImponibleRenta : 5*uit";
	 * resultado.put("hasta5UIT", formula);
	 * 
	 * formula = "hasta5UIT*(8/100)"; resultado.put("hasta5UIT8PCT", formula);
	 * 
	 * formula =
	 * "(baseImponibleRenta-hasta5UIT8PCT) > (20*uit-5*uit)? (20*uit-5*uit): (baseImponibleRenta-hasta5UIT8PCT)"
	 * ; resultado.put("exceso5UITHasta20UIT", formula);
	 * 
	 * formula = "exceso5UITHasta20UIT*(14/100)";
	 * resultado.put("exceso5UITHasta20UIT14PCT", formula);
	 * 
	 * formula = "baseImponibleRenta-hasta5UIT-exceso5UITHasta20UIT";
	 * resultado.put("exceso20UITHasta35UIT", formula);
	 * 
	 * formula = "baseImponibleRenta-hasta5UIT-exceso5UITHasta20UIT";
	 * resultado.put("exceso20UITHasta35UIT", formula);
	 * 
	 * formula = "exceso20UITHasta35UIT*(17/100)";
	 * resultado.put("exceso20UITHasta35UIT17PCT", formula);
	 * 
	 * formula = "0"; resultado.put("exceso35UITHasta45UIT", formula);
	 * 
	 * formula = "0"; resultado.put("exceso35UITHasta45UIT20PCT", formula);
	 * 
	 * formula =
	 * "REDONDEAR(hasta5UIT8PCT+exceso5UITHasta20UIT14PCT+exceso20UITHasta35UIT17PCT+exceso35UITHasta45UIT20PCT,0)";
	 * resultado.put("impuestoRentaPagar", formula);
	 * 
	 * formula = "0"; resultado.put("impuestoRetenidoEmpreAnt", formula);
	 * 
	 * // calculo de retencion
	 * 
	 * // -De enero a marzo, el impuesto anual se divide entre 12. formula =
	 * "(impuestoRentaPagar-impuestoRetenidoEmpreAnt)/12";
	 * resultado.put("eneroRetenido", formula); resultado.put("febreroRetenido",
	 * formula); resultado.put("marzoRetenido", formula);
	 * 
	 * // -En abril, al impuesto anual se le deducen las retenciones efectuadas de
	 * // enero a marzo. El resultado se divide entre 9. formula =
	 * "(impuestoRentaPagar-impuestoRetenidoEmpreAnt-eneroRetenido-febreroRetenido-marzoRetenido)/9";
	 * resultado.put("abrilRetenido", formula);
	 * 
	 * // -De mayo a julio, al impuesto anual se le deducen las retenciones
	 * efectuadas // en los meses de enero a abril. El resultado se divide entre 8.
	 * formula =
	 * "(impuestoRentaPagar-impuestoRetenidoEmpreAnt-eneroRetenido-febreroRetenido-marzoRetenido-abrilRetenido)/8";
	 * resultado.put("mayoRetenido", formula); resultado.put("junioRetenido",
	 * formula); resultado.put("julioRetenido", formula);
	 * 
	 * // -En agosto, al impuesto anual se le deducen las retenciones efectuadas en
	 * los // meses de enero a julio. El resultado se divide entre 5. formula =
	 * "(impuestoRentaPagar-impuestoRetenidoEmpreAnt-eneroRetenido-febreroRetenido-marzoRetenido-abrilRetenido-mayoRetenido-junioRetenido-julioRetenido)/5";
	 * resultado.put("agostoRetenido", formula);
	 * 
	 * // -De setiembre a noviembre, al impuesto anual se le deducen las retenciones
	 * // efectuadas en los meses de enero a agosto. El resultado se divide entre 4.
	 * formula =
	 * "(impuestoRentaPagar-impuestoRetenidoEmpreAnt-eneroRetenido-febreroRetenido-marzoRetenido-abrilRetenido-mayoRetenido-junioRetenido-julioRetenido-agostoRetenido)/4";
	 * resultado.put("setiembreRetenido", formula); resultado.put("octubreRetenido",
	 * formula); resultado.put("noviembreRetenido", formula);
	 * 
	 * // En diciembre, con motivo de la regularización anual, al impuesto anual se
	 * le // deducirá las retenciones efectuadas en los meses de enero a noviembre
	 * del // mismo ejercicio. formula =
	 * "(impuestoRentaPagar-impuestoRetenidoEmpreAnt-eneroRetenido-febreroRetenido-marzoRetenido-abrilRetenido-mayoRetenido-junioRetenido-julioRetenido-agostoRetenido-setiembreRetenido-octubreRetenido-noviembreRetenido)";
	 * resultado.put("diciembreRetenido", formula);
	 * 
	 * // meses retenido resultado.put("renta5ta", "0"); for (MesType mes :
	 * MesType.values()) { if (mes.getKey() == periodo.getIdItemByPeriodoMes()) {
	 * formula = "REDONDEAR(" + mes.getValue() + "renta5ta,2)";
	 * resultado.put("renta5ta", formula); } }
	 * 
	 * return resultado; }
	 */

	protected BigDecimal getCalcularVariableGrupo(PersonalVO obj, Map<String, Object> variableMap,
			Map<String, String> formulaMap, Context interprete, String keyVariableGrupo) {
		BigDecimal resultado = BigDecimal.ZERO;
		boolean isMostrarVariable = false;
		if (obj.getIdPersonal().equalsIgnoreCase("42bef914feab49a2b76dcc094c19a1e4x")) {
			if (keyVariableGrupo.equalsIgnoreCase("renta5ta")) {
				isMostrarVariable = true;
			}
		}
		for (Map.Entry<String, String> objFor : formulaMap.entrySet()) {
			variableMap.put(objFor.getKey(), "0");
		}
		for (Map.Entry<String, String> objFor : formulaMap.entrySet()) {
			ConceptoCaluldadosVO objCon = new ConceptoCaluldadosVO();
			objCon.setVariable(objFor.getKey());
			objCon.setFormula(objFor.getValue());

			variableMap.put(objFor.getKey(), calcularFormula(obj, objCon, variableMap, interprete));
			if (isMostrarVariable) {
				log.info("valiando personal getVariableMap " + objFor.getKey() + " : "
						+ variableMap.get(objFor.getKey()));
			}
		}
		resultado = ObjectUtil.objectToBigDecimal(variableMap.get(keyVariableGrupo));
		return resultado;
	}

	protected Map<String, Object> obtenerDataVariable(PersonalVO obj, Map<String, Object> dataTupaMap) {
		for (var objData : obj.getListaIngreso()) {
			if ((objData.getMonto() != null && StringUtil.isNullOrEmptyNumeriCero(objData.getMonto()))) {
				dataTupaMap.put(objData.getVariable(), objData.getMonto());
			}
		}
		for (var objData : obj.getListaDescuento()) {
			if ((objData.getMonto() != null && StringUtil.isNullOrEmptyNumeriCero(objData.getMonto()))) {
				dataTupaMap.put(objData.getVariable(), objData.getMonto());
			}
		}
		for (var objData : obj.getListaAporteTrabajador()) {
			if ((objData.getMonto() != null && StringUtil.isNullOrEmptyNumeriCero(objData.getMonto()))) {
				dataTupaMap.put(objData.getVariable(), objData.getMonto());
			}
		}
		for (var objData : obj.getListaAporteEmpleador()) {
			if ((objData.getMonto() != null && StringUtil.isNullOrEmptyNumeriCero(objData.getMonto()))) {
				dataTupaMap.put(objData.getVariable(), objData.getMonto());
			}
		}
		return dataTupaMap;
	}

	protected Planilla generarObjetoPlanilla(TipoPlanilla tipoPlanilla, PeriodoPlanilla periodo) {
		var objPlanilla = new Planilla();
		objPlanilla.setIdPlanilla(this.planillaDao.generarId());
		objPlanilla.setTipoPlanilla(new TipoPlanilla());
		objPlanilla.getTipoPlanilla().setIdTipoPlanilla(tipoPlanilla.getIdTipoPlanilla());
		objPlanilla.setIdItemByPeriodoMes(periodo.getIdItemByPeriodoMes());
		objPlanilla.setIdItemByTipoTrabajador(tipoPlanilla.getIdItemByCategoriaTrabajador());
		// objPlanilla.setAnhio(new Anhio());
		// objPlanilla.getAnhio().setIdAnhio(periodo.getAnhio().getIdAnhio());
		objPlanilla.setIdAnhio(periodo.getIdAnhio());
		objPlanilla.setPeriodoPlanilla(new PeriodoPlanilla());
		objPlanilla.getPeriodoPlanilla().setIdPeriodoPlanilla(periodo.getIdPeriodoPlanilla());
		return objPlanilla;
	}

	protected List<DetallePlanillaConcepto> generarDetallePlanillaConcepto(List<DetallePlanilla> listaDetallePlanilla,
			Map<String, Map<String, BigDecimal>> conceptosMap, Map<String, String> showBoletaMap) {
		var resultado = new ArrayList<DetallePlanillaConcepto>();
		for (var obj : listaDetallePlanilla) {
			var concpetoMapTemp = conceptosMap.get(obj.getIdPersonal());
			for (var entryMap : concpetoMapTemp.entrySet()) {
				if (entryMap.getValue().compareTo(BigDecimal.ZERO) > 0
						|| showBoletaMap.containsKey(entryMap.getKey())) {
					var objDet = new DetallePlanillaConcepto();
					objDet.setIdDetallePlanillaConcepto(this.detallePlanillaConceptoDao.generarId());
					objDet.setConcepto(new ConceptosTipoPlanilla());
					objDet.getConcepto().setIdConceptosTipoPlanilla(entryMap.getKey());
					objDet.setMonto(entryMap.getValue());
					objDet.setDetallePlanilla(obj);
					objDet = this.detallePlanillaConceptoDao.save(objDet);
					resultado.add(objDet);
				}
			}
		}
		return resultado;
	}

	protected BigDecimal sumaTotal(List<ConceptoCaluldadosVO> listaConcepto) {
		var resultado = BigDecimal.ZERO;
		for (var objIng : listaConcepto) {
			if (RespuestaNaturalType.SI.getKey().toString().equalsIgnoreCase(objIng.getPersistente())) {
				resultado = resultado.add(objIng.getMonto());
			}
		}
		return resultado;
	}

	protected List<DetallePlanilla> generarDetallePlanilla(Planilla planilla, List<PersonalVO> listaPersonal) {
		var resultado = new ArrayList<DetallePlanilla>();
		for (var obj : listaPersonal) {
			var detPlanilla = new DetallePlanilla();
			var ingresos = sumaTotal(obj.getListaIngreso());
			var listaConceptoDesc = new ArrayList<ConceptoCaluldadosVO>();
			listaConceptoDesc.addAll(obj.getListaDescuento());
			listaConceptoDesc.addAll(obj.getListaAporteTrabajador());
			var descuentos = sumaTotal(listaConceptoDesc);
			var aportaciones = sumaTotal(obj.getListaAporteEmpleador());
			var netoPagar = BigDecimal.ZERO;

			netoPagar = netoPagar.add(ingresos.subtract(descuentos));
			detPlanilla.setIdDetallePlanilla(this.detallePlanillaDao.generarId());
			detPlanilla.setPlanilla(planilla);
			detPlanilla.setIdPersonal(obj.getIdPersonal());
			detPlanilla.setTotalIngreso(ingresos);
			detPlanilla.setTotalDescuento(descuentos);
			detPlanilla.setTotalAportaciones(aportaciones);
			detPlanilla.setNetoPagar(netoPagar);
			detPlanilla = this.detallePlanillaDao.save(detPlanilla);
			resultado.add(detPlanilla);
		}
		return resultado;
	}

	protected List<ConceptoCaluldadosVO> calcularConceptoCalculados(PersonalVO personal,
			List<ConceptosTipoPlanilla> listarConcepto, String tipo, Map<String, Object> variableMap,
			Map<String, DetallePersonalConcepto> detConceptoMap, Map<String, BigDecimal> conceptoTrajadorStaticoMap,
			Context interprete) {
		var resultado = new ArrayList<ConceptoCaluldadosVO>();
		var resultadoTmp = new ArrayList<ConceptoCaluldadosVO>();

		for (var obj : listarConcepto) {
			if (obj.getConceptoPdt().getTipo().equals(tipo)) {
				var objDataa = new ConceptoCaluldadosVO();
				var key = obj.getCodigo();
				String keyConcepto = obj.getIdConceptosTipoPlanilla();
				// objDataa.setIdConceptoPDT(keyConcepto);
				objDataa.setIdConceptosTipoPlanilla(keyConcepto);
				objDataa.setVariable(key);
				objDataa.setPersistente(obj.getPersistente());
				objDataa.setShowBoleta(obj.getShowBoleta());
				//
				objDataa.setFormula(obj.getFormula().trim());

				if (detConceptoMap.containsKey(keyConcepto)) {
					if ((!StringUtil.isNullOrEmptyNumeriCero(detConceptoMap.get(keyConcepto).getMonto()))) {
						objDataa.setMonto(detConceptoMap.get(keyConcepto).getMonto());
					}
				} else {
					if (conceptoTrajadorStaticoMap != null && conceptoTrajadorStaticoMap.containsKey(key)) {
						objDataa.setMonto(conceptoTrajadorStaticoMap.get(key));
					} else {
						objDataa.setMonto(calcularFormula(personal, objDataa, variableMap, interprete));
						variableMap.put(objDataa.getVariable(), objDataa.getMonto());

					}
				}

				if ((objDataa.getMonto() != null && !StringUtil.isNullOrEmptyNumeriCero(objDataa.getMonto()))
						|| (!StringUtil.isNullOrEmpty(objDataa.getFormula()))) {
					resultadoTmp.add(objDataa);
				}
			}
		}
		// data inicial
		for (var objData : resultadoTmp) {
			if ((objData.getMonto() != null && !StringUtil.isNullOrEmptyNumeriCero(objData.getMonto()))) {
				variableMap.put(objData.getVariable(), objData.getMonto());
			}
		}
		variableMap = obtenerDataVariable(personal, variableMap);

		if (personal.getIdPersonal().equalsIgnoreCase("42bef914feab49a2b76dcc094c19a1e4x")) {
			log.info("valiando personal getVariableMap " + personal.getNombres());
		}
		for (var objData : resultadoTmp) {
			String keyConcepto = objData.getIdConceptosTipoPlanilla();
			if (!detConceptoMap.containsKey(keyConcepto)) {
				if (StringUtil.isNotNullOrBlank(objData.getFormula())) {
					objData.setMonto(calcularFormula(personal, objData, variableMap, interprete));
					variableMap.put(objData.getVariable(), objData.getMonto());
				}
			}
			resultado.add(objData);
		}
		return resultado;
	}

	protected BigDecimal calcularFormula(PersonalVO obj, ConceptoCaluldadosVO objCon, Map<String, Object> parametroMap,
			Context interprete) {
		Value bindings = interprete.getBindings("js");
		String variable = objCon.getVariable();
		String formula = objCon.getFormula();
		BigDecimal resultado = BigDecimal.ZERO;

		if (obj != null && obj.getIdPersonal().equalsIgnoreCase("42bef914feab49a2b76dcc094c19a1e4")) {
			if (variable.equalsIgnoreCase("renta5tax")) {
				log.info("valiando personal variable " + variable + " ");
			}
		}
		String funciones = """
				var BigDecimal = Java.type('java.math.BigDecimal');
				var RoundingMode = Java.type('java.math.RoundingMode');
				function REDONDEAR(numeroTmp,digito) {
				var numero = new BigDecimal(numeroTmp);
				           return  numero.setScale(digito, RoundingMode.HALF_UP);
				          }

				var r = 0;
				""";
		try {
			String formulaBase = "\n r = " + formula + " ;\n r";
			if (StringUtil.isNotNullOrBlank(formula)) {
				interprete.eval("js", funciones);
				for (var objVar : parametroMap.entrySet()) {
					if (objVar.getKey().equalsIgnoreCase("renta5ta")) {
						// log.info("valiando personal " + objVar.getKey());
					}
					bindings.putMember(objVar.getKey(), ObjectUtil.objectToBigDecimal(objVar.getValue()).doubleValue());
				}
				parametroMap.get("totalGeneral5ta");
				if (obj != null && obj.getIdPersonal().equalsIgnoreCase("42bef914feab49a2b76dcc094c19a1e4")) {
					if (variable.equalsIgnoreCase("renta5ta")) {
						// log.info("valiando personal variable " + variable + " ");
					}
				}
				Object resul = interprete.eval("js", formulaBase);
				if (!resul.toString().equalsIgnoreCase("NaN")) {
					resultado = ObjectUtil.objectToBigDecimal(resul);
				}
			}
		} catch (Exception se) {
			resultado = BigDecimal.ZERO;
		}
		return resultado;
	}

	protected List<PersonalVO> listarPersonalPagina(String authToken, Long idCategoriaTrabajador) {
		return buscarPaginadoPersonal(authToken, 5000, idCategoriaTrabajador);
	}

	protected List<PersonalVO> buscarPaginadoPersonal(String authToken, int cantidadPagina,
			final Long idCategoriaOcupacional) {
		var filtroMap = new HashMap<String, Object>();
		filtroMap.put("idItemByCategoriaTrabajador", idCategoriaOcupacional);
		var dataProvider = new IDataProvider<PersonalVO>() {
			protected int total = 0;
			protected int cuenta = 0;

			@Override
			public List<PersonalVO> getBufferedData(int startRow, int offset) {
				List<PersonalVO> lista = new ArrayList<>();
				filtroMap.put("startRow", startRow);
				filtroMap.put("offSet", offset);
				try {
					lista = rrhhEscalafonClient.paginarPersonal(authToken, filtroMap).getListaResultado();
				} catch (Exception e) {
					lista = new ArrayList<>();
				}
				return lista;
			}

			@Override
			public int getTotalResultsNumber() {
				if (total == 0 && cuenta == 0) {
					try {
						total = (int) rrhhEscalafonClient.paginarPersonal(authToken, filtroMap).getContador();
					} catch (Exception e) {
						total = 0;
					}
					cuenta++;
				}
				return total;
			}
		};
		return new LazyLoadingList<>(dataProvider, cantidadPagina);
	}

	// Inicio objetos Future

	protected Future<Map<String, Map<String, String>>> getVariableGrupoFormulatMapFuture(TipoPlanilla tipoPlanilla) {
		try {
			var filtro = new BaseSearch();
			filtro.setId(tipoPlanilla.getIdTipoPlanilla());
			return new AsyncResult<>(variableConfDetDao.listarMap(filtro));
		} catch (Exception e) {
			log.error("getVariableGrupoFormulatMapFuture", e);
		}
		return null;

	}

	protected Future<Map<String, Map<String, BigDecimal>>> getRenta5taAntMapFuture(TipoPlanilla tipoPlanilla,
			PeriodoPlanilla periodo, List<String> listaIdPersonal) {
		try {
			return new AsyncResult<>(planillaDao.listarRenta5taAntMap(listaIdPersonal, tipoPlanilla.getIdTipoPlanilla(),
					periodo.getIdAnhio()));
		} catch (Exception e) {
			log.error("getRenta5taAntMapFuture", e);
		}
		return null;

	}

	protected Future<Map<String, Map<String, BigDecimal>>> getPlanillaAntMapFuture(TipoPlanilla tipoPlanilla,
			PeriodoPlanilla periodo, List<String> listaIdPersonal) {
		try {
			return new AsyncResult<>(planillaDao.listarPlanillaAntMap(listaIdPersonal, tipoPlanilla.getIdTipoPlanilla(),
					periodo.getIdAnhio()));
		} catch (Exception e) {
			log.error("getPlanillaAntMapFuture", e);
		}
		return null;

	}

	protected Future<Map<Long, Integer>> getFeriadoMap(PeriodoPlanilla periodo) {
		try {
			BaseSearch filtro = new BaseSearch();
			filtro.setIdAnhio(periodo.getIdAnhio());
			filtro.setIdItemByMes(periodo.getIdItemByPeriodoMes());
			return new AsyncResult<>(feriadoDao.listarMap(filtro));
		} catch (Exception e) {
			log.error("getFeriadoMap", e);
		}
		return null;
	}

	protected Future<Map<String, Map<String, DetallePersonalConcepto>>> getDetConceptoMapFuture(
			TipoPlanilla tipoPlanilla, PeriodoPlanilla periodo, List<String> listaIdPersonal) {
		try {
			return new AsyncResult<>(detallePersonalConceptoDao.listarMap(listaIdPersonal,
					tipoPlanilla.getIdTipoPlanilla(), periodo.getIdPeriodoPlanilla()));
		} catch (Exception e) {
			log.error("getDetConceptoMapFuture", e);
		}
		return null;

	}

	protected Future<ValoresUIT> valoresUITFurure(Planilla filtro) {
		try {
			return new AsyncResult<>(valoresUITDao.findActivo(filtro.getIdAnhio()));
		} catch (Exception e) {
			log.error("valoresUITFurure", e);
		}
		return null;
	}

	protected Future<List<Object>> listaParametroFuture(String authToken) {
		try {
			var filtroMap = new HashMap<String, Object>();
			filtroMap.put("sortFields", "codigo");
			filtroMap.put("sortDirections", "asc");
			var lista = commonServiceClient.listaParametro(authToken, filtroMap);
			var resaul = new ArrayList<Object>();
			if (lista.isData()) {
				for (ParametroVO obj : lista.getListaResultado()) {
					resaul.add(obj);
				}
			}
			return new AsyncResult<>(resaul);
		} catch (Exception e) {
			log.error("listaParametroFuture", e);
		}

		return null;
	}

	protected Future<Map<Long, ConceptoRegimenPensionario>> obtenerConceptoRegimenPensionarioMapFuture(
			Long idMesDevengado, Long anhio) {
		try {
			return new AsyncResult<>(this.conceptoRegimenPensionarioDao.listarMap(idMesDevengado, anhio));
		} catch (Exception e) {
			log.error("obtenerBasicoPersonalMap", e);
		}

		return null;
	}

	protected Future<Map<Long, EPSConf>> obtenerEpsConfMapFuture(BaseSearch filtro) {
		try {
			// this.epsPersonalConsumerDao.listarMap(filtro))
			return new AsyncResult<>(epsConfDao.listarMap(filtro));
		} catch (Exception e) {
			log.error("obtenerEpsConfMapFuture", e);
		}

		return null;
	}

	protected Future<Map<String, EPSPersonal>> obtenerEpsPersopnalMapFuture(BaseSearch filtro) {
		try {
			// this.epsPersonalConsumerDao.listarMap(filtro))
			return new AsyncResult<>(new HashMap<String, EPSPersonal>());
		} catch (Exception e) {
			log.error("obtenerEpsPersopnalMapFuture", e);
		}

		return null;
	}

	protected Future<Map<String, BigDecimal>> obtnerAdelantosMapFuture(Long idCategoriaTrabajador,
			List<String> listaIdPersonal) {
		try {
			return new AsyncResult<>(this.adelantoDao.obtnerAdelantosMap(idCategoriaTrabajador, listaIdPersonal));
		} catch (Exception e) {
			log.error("obtnerAdelantosMapFuture", e);
		}

		return null;
	}

	protected Future<Map<String, BigDecimal>> obtnerRentaEmpresaAntMapFuture(Long idAnhio,
			List<String> listaIdPersonal) {
		try {
			return new AsyncResult<>(this.informaOtrosIngreso5taDao.listarMap(idAnhio, listaIdPersonal));
		} catch (Exception e) {
			log.error("obtnerRentaEmpresaAntMapFuture", e);
		}

		return null;
	}

	protected Future<Map<String, Map<String, BigDecimal>>> conceptoTrajadorStaticoMapFuture(Long idCategoriaTrabajador,
			List<String> listaIdPersonal) {
		try {
			return new AsyncResult<>(
					this.conceptoTrabajadorDao.conceptoTrajadorStaticoMap(idCategoriaTrabajador, listaIdPersonal));
		} catch (Exception e) {
			log.error("conceptoTrajadorStaticoMapFuture", e);
		}

		return null;
	}

	protected Future<List<ConceptosTipoPlanilla>> obtenerConceptoTipoPlanillaFuture(String idTipoPlanilla) {
		try {
			return new AsyncResult<>(obtenerConceptoTipoPlanilla(idTipoPlanilla));
		} catch (Exception e) {
			log.error("obtenerConceptoTipoPlanillaFuture", e);
		}

		return null;
	}

	protected Future<Map<String, TareoPersonal>> obtenerTareoFuture(PeriodoPlanilla filtro) {
		try {
			return new AsyncResult<>(obtenerTareoPersonal(filtro));
		} catch (Exception e) {
			log.error("obtenerTareoFuture", e);
		}

		return null;
	}

	protected List<ConceptosTipoPlanilla> obtenerConceptoTipoPlanilla(String idTipoPlanilla) {
		var filtro = new BaseSearch();
		filtro.setId(idTipoPlanilla);
		// filtro.setListaIdPersonal(listaIdPersonal);
		return this.listarConceptosTipoPlanilla(filtro);
	}

	protected List<ConceptosTipoPlanilla> listarConceptosTipoPlanilla(BaseSearch filtro) {
		return this.conceptosTipoPlanillaDao.listar(filtro);
	}

	// Fin objetos Future
	// inicio carga de variables
	protected Map<String, Object> getVariablePersonalMap(PersonalVO obj, Map<String, Object> variableMap) {
		for (var objVar : VariableAplicacionType.values()) {
			if ("Personal".equalsIgnoreCase(objVar.getDescripcion())) {
				Map<String, Object> dataMap = toVOMap(obj);
				variableMap.put(objVar.getKey(), BigDecimal.ZERO);
				String key = objVar.getKey();
				if ("EPS".equalsIgnoreCase(key)) {
					Long value = (Long) dataMap.get("idItemByEps");
					variableMap.put(key, StringUtil.isNullOrEmptyNumeric(value) ? 0 : 1);
				} else if ("esComisionMixta".equalsIgnoreCase(key)) {
					String value = (String) dataMap.get("esComisionMixta");
					variableMap.put(key, RespuestaNaturalType.SI.getKey().toString().equalsIgnoreCase(value) ? 1 : 0);
				} else if ("esSNP".equalsIgnoreCase(key)) {
					Long value = (Long) dataMap.get("idItemByRegimenPensionario");
					Long SNP = 110000L;// SNP LEY 199990
					variableMap.put(key, (SNP).equals(value) ? 1 : 0);
				} else {
					if (dataMap.containsKey(objVar.getKey())) {
						variableMap.put(objVar.getKey(), dataMap.get(objVar.getKey()));
					}
				}

			}
		}
		return variableMap;
	}

	protected Map<String, Object> getVariableConceptoRegimenPensionarioMap(PersonalVO obj,
			Map<String, Object> variableMap, Map<Long, ConceptoRegimenPensionario> conceptoRegimenPensionarioMap) {
		for (var objVar : VariableAplicacionType.values()) {
			if ("ConceptoRegimenPensionario".equalsIgnoreCase(objVar.getDescripcion())) {
				variableMap.put(objVar.getKey(), BigDecimal.ZERO);
				var conceptoRegPen = conceptoRegimenPensionarioMap.get(obj.getIdItemByRegimenPensionario());
				if (conceptoRegPen != null) {
					Map<String, Object> dataMap = toVOMap(conceptoRegPen);
					if (dataMap.containsKey(objVar.getKey())) {
						variableMap.put(objVar.getKey(), dataMap.get(objVar.getKey()));
					}
				}
			}
		}
		return variableMap;
	}

	protected Map<String, Object> getVariableEpsConfMap(PersonalVO obj, Map<String, Object> variableMap,
			EPSConf objData) {
		for (var objVar : VariableAplicacionType.values()) {
			if ("EPSConf".equalsIgnoreCase(objVar.getDescripcion())) {
				// EPSPersonal objData = epsPersonalMap.get(obj.getIdPersonal());
				variableMap.put(objVar.getKey(), BigDecimal.ZERO);
				if (objData != null) {
					Map<String, Object> dataMap = toVOMap(objData);
					if (dataMap.containsKey(objVar.getKey())) {
						variableMap.put(objVar.getKey(), dataMap.get(objVar.getKey()));
					}
				}
			}
		}
		return variableMap;
	}

	/*
	 * protected Map<String, Object> getVariableEpsPersonalMap(PersonalVO obj,
	 * Map<String, Object> variableMap, EPSPersonal objData) { for (var objVar :
	 * VariableAplicacionType.values()) { if
	 * ("EPSPersonal".equalsIgnoreCase(objVar.getDescripcion())) { // EPSPersonal
	 * objData = epsPersonalMap.get(obj.getIdPersonal());
	 * variableMap.put(objVar.getKey(), BigDecimal.ZERO); if (objData != null) {
	 * Map<String, Object> dataMap = toVOMap(objData); if
	 * (dataMap.containsKey(objVar.getKey())) { variableMap.put(objVar.getKey(),
	 * dataMap.get(objVar.getKey())); } } } } return variableMap; }
	 */

	protected Map<String, Object> getVariableGrupoFormulatMap(PersonalVO obj, Map<String, Object> variableMap,
			Map<String, Map<String, String>> variableGrupoFormulaMap, Context interprete, boolean isCalcula) {
		for (var objVar : variableGrupoFormulaMap.entrySet()) {
			variableMap.put(objVar.getKey(), BigDecimal.ZERO);
			if (isCalcula) {
				variableMap.put(objVar.getKey(),
						getCalcularVariableGrupo(obj, variableMap, objVar.getValue(), interprete, objVar.getKey()));
			}
		}
		return variableMap;
	}

	protected Map<String, Object> getMesMap(PersonalVO obj, Map<String, Object> variableMap) {
		for (MesType mes : MesType.values()) {
			String key = mes.getValue() + "Mes";
			variableMap.put(key, mes.getKey());
		}
		return variableMap;
	}

	protected Map<String, Object> getVariableTareoPersonalMap(PersonalVO obj, Map<String, Object> variableMap,
			Map<String, TareoPersonal> tareoPersonalMap) {
		for (var objVar : VariableAplicacionType.values()) {
			if ("TareoPersonal".equalsIgnoreCase(objVar.getDescripcion())) {
				variableMap.put(objVar.getKey(), BigDecimal.ZERO);
				var objTaPer = tareoPersonalMap.get(obj.getIdPersonal());
				if (objTaPer != null) {
					Map<String, Object> dataMap = toVOMap(objTaPer);
					if (dataMap.containsKey(objVar.getKey())) {
						variableMap.put(objVar.getKey(), dataMap.get(objVar.getKey()));
					}
				}
			}
		}
		return variableMap;
	}

	protected Map<String, Object> getVariableAdelantoMap(PersonalVO obj, Map<String, Object> variableMap,
			Map<String, BigDecimal> adelantosMap) {
		for (var objVar : VariableAplicacionType.values()) {
			if ("Adelanto".equalsIgnoreCase(objVar.getDescripcion())) {
				variableMap.put(objVar.getKey(), BigDecimal.ZERO);
				var adelanto = adelantosMap.get(obj.getIdPersonal());
				if (adelanto != null) {
					variableMap.put(objVar.getKey(), adelanto);
				}
			}
		}
		return variableMap;
	}

	protected Map<String, Object> getVariableRentaEmpresaAntMap(PersonalVO obj, Map<String, Object> variableMap,
			Map<String, BigDecimal> rentaEmpresaAntMap) {
		for (var objVar : VariableAplicacionType.values()) {
			if ("RentaEmpresaAnt".equalsIgnoreCase(objVar.getDescripcion())) {
				variableMap.put(objVar.getKey(), BigDecimal.ZERO);
				var adelanto = rentaEmpresaAntMap.get(obj.getIdPersonal());
				if (adelanto != null) {
					variableMap.put(objVar.getKey(), adelanto);
				}
			}
		}
		return variableMap;
	}

	protected Map<String, Object> getVariableFeriadoMap(PeriodoPlanilla obj, Map<String, Object> variableMap,
			Map<Long, Integer> feriadoMap) {
		for (var objVar : VariableAplicacionType.values()) {
			if ("Feriado".equalsIgnoreCase(objVar.getDescripcion())) {
				variableMap.put(objVar.getKey(), BigDecimal.ZERO);
				var feriado = feriadoMap.get(obj.getIdItemByPeriodoMes());
				if (feriado != null) {
					variableMap.put(objVar.getKey(), feriado);
				}
			}
		}
		return variableMap;
	}

	protected Map<String, Object> getVariableParametroMap(PersonalVO obj, Map<String, Object> variableMap,
			List<Object> param) {
		for (var objParam : param) {
			try {
				var objVar = (ParametroVO) objParam;
				variableMap.put(objVar.getCodigo(), ObjectUtil.objectToBigDecimal(objVar.getValor()));
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
		return variableMap;
	}

	protected Map<String, Object> getVariablePlanillaAntMap(PersonalVO obj, Map<String, Object> variableMap,
			Map<String, Map<String, BigDecimal>> planillaAntMap, PeriodoPlanilla periodo) {
		Map<String, BigDecimal> planillaAntValueMap = planillaAntMap.get(obj.getIdPersonal());
		variableMap.put(VariableAplicacionType.MES_ACTUAL.getKey(), periodo.getIdItemByPeriodoMes());
		for (MesType mes : MesType.values()) {
			String key = mes.getValue() + "Planilla";
			String keyMes = mes.getValue();
			variableMap.put(key, BigDecimal.ZERO);
			if (planillaAntValueMap != null) {
				if (planillaAntValueMap.containsKey(keyMes)) {
					variableMap.put(key, planillaAntValueMap.get(keyMes));
				}
			}
		}
		return variableMap;
	}

	protected Map<String, Object> getVariableRenta5taAntMap(PersonalVO obj, Map<String, Object> variableMap,
			Map<String, Map<String, BigDecimal>> renta5taAntMap) {
		Map<String, BigDecimal> renta5taAntValueMap = renta5taAntMap.get(obj.getIdPersonal());
		for (MesType mes : MesType.values()) {
			String key = mes.getValue() + "Renta5ta";
			String keyMes = mes.getValue();
			variableMap.put(key, BigDecimal.ZERO);
			if (renta5taAntValueMap != null) {
				if (renta5taAntValueMap.containsKey(keyMes)) {
					variableMap.put(key, renta5taAntValueMap.get(keyMes));
				}
			}
		}
		return variableMap;
	}

	protected Map<String, Object> getVariablePeriodoMap(PersonalVO obj, Map<String, Object> variableMap,
			PeriodoPlanilla periodo) {
		for (var objVar : VariableAplicacionType.values()) {
			if ("PeriodoPlanilla".equalsIgnoreCase(objVar.getDescripcion())) {
				variableMap.put(objVar.getKey(), BigDecimal.ZERO);
				Map<String, Object> dataMap = toVOMap(periodo);
				if (dataMap.containsKey(objVar.getKey())) {
					variableMap.put(objVar.getKey(), dataMap.get(objVar.getKey()));
				}
			}
		}
		return variableMap;
	}

	protected Map<String, Object> getVariableValoresUITMap(PersonalVO obj, Map<String, Object> variableMap,
			ValoresUIT uuit) {
		for (var objVar : VariableAplicacionType.values()) {
			if ("ValoresUIT".equalsIgnoreCase(objVar.getDescripcion())) {
				variableMap.put(objVar.getKey(), BigDecimal.ZERO);
				Map<String, Object> dataMap = toVOMap(uuit);
				if (dataMap.containsKey("valor")) {
					variableMap.put(objVar.getKey(),
							dataMap.get(objVar.getKey().equals("uit") ? "valor" : objVar.getKey()));
				}
			}
		}
		return variableMap;
	}

	protected Map<String, TareoPersonal> obtenerTareoPersonal(PeriodoPlanilla objFiltro) {
		var resultado = new HashMap<String, TareoPersonal>();
		var filtro = new BaseSearch();
		filtro.setIdItemByMes(objFiltro.getIdItemByPeriodoMes());
		filtro.setIdAnhio(objFiltro.getIdAnhio());
		// filtro.setListaIdPersonal(listaIdPersonal);
		List<TareoPersonal> listaTmp = this.tareoPersonalDao.listar(filtro);
		for (var obj : listaTmp) {
			resultado.put(obj.getIdPersonal(), obj);
		}
		return resultado;
	}

	// Fin carga variables
	protected Map<String, String> getShowBoletaMap(List<ConceptosTipoPlanilla> listarConcepto) {
		var resultado = new HashMap<String, String>();
		for (var obj : listarConcepto) {
			if (RespuestaNaturalType.SI.getKey().toString().equalsIgnoreCase(obj.getShowBoleta())) {
				resultado.put(obj.getIdConceptosTipoPlanilla(), "");
			}
		}
		return resultado;
	}

	protected void eliminarRegistroPlanillas(Planilla filtro) {
		// Eliminado de planilla y detPlanilla
		eliminarRegistrosPlanillas(filtro, true);
	}

	protected void eliminarRegistrosPlanillas(Planilla filtro, boolean isAll) {
		if (!StringUtil.isNullOrEmpty(filtro)) {
			detallePlanillaConceptoDao.eliminar(filtro);
			detallePlanillaDao.eliminar(filtro);
			//
			if (isAll) {
				planillaDao.eliminar(filtro);
			}

		}
	}

	protected PeriodoPlanilla getPeriodoPlanilla(PeriodoPlanilla filtro) {
		return periodoPlanillaDao.find(PeriodoPlanilla.class, filtro.getIdPeriodoPlanilla());
	}

	protected PeriodoPlanilla getTipoPlanilla(PeriodoPlanilla filtro) {
		return periodoPlanillaDao.find(PeriodoPlanilla.class, filtro.getIdPeriodoPlanilla());
	}

}
