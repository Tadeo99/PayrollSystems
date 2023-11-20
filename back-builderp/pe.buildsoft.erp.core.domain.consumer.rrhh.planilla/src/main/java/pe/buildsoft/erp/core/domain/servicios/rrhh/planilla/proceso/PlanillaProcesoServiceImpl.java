package pe.buildsoft.erp.core.domain.servicios.rrhh.planilla.proceso;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Engine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import pe.buildsoft.erp.core.domain.entidades.planilla.ConceptosTipoPlanilla;
import pe.buildsoft.erp.core.domain.entidades.planilla.DetallePersonalConcepto;
import pe.buildsoft.erp.core.domain.entidades.planilla.PeriodoPlanilla;
import pe.buildsoft.erp.core.domain.entidades.planilla.Planilla;
import pe.buildsoft.erp.core.domain.entidades.planilla.TipoPlanilla;
import pe.buildsoft.erp.core.domain.entidades.planilla.vo.CalculoPlanillaVO;
import pe.buildsoft.erp.core.domain.interfaces.servicios.rrhh.planilla.proceso.PlanillaProcesoServiceLocal;
import pe.buildsoft.erp.core.infra.data.entidades.webclient.rest.rrhh.escalafon.vo.ConceptoCaluldadosVO;
import pe.buildsoft.erp.core.infra.data.entidades.webclient.rest.rrhh.escalafon.vo.PersonalVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.type.MesType;
import pe.buildsoft.erp.core.infra.transversal.type.RespuestaNaturalType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ObjectUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@TransactionManagement(TransactionManagementType.BEAN)
public class PlanillaProcesoServiceImpl extends PlanillaProcesoServiceUtil implements PlanillaProcesoServiceLocal {

	private static Logger log = LoggerFactory.getLogger(PlanillaProcesoServiceImpl.class);

	private Engine manager;

	public PlanillaProcesoServiceImpl() {
		//
		manager = Engine.newBuilder().option("engine.WarnInterpreterOnly", "false").build();
	}

	// REVISAR CONCEPTO ESTATICO USAR CODIGO O VARIABLE
	@Override
	public String generarPlanilla(String authToken, Planilla filtro) throws Exception {

		var tipoPlanilla = filtro.getTipoPlanilla();
		filtro.getPeriodoPlanilla().setIdAnhio(filtro.getIdAnhio());
		filtro.getPeriodoPlanilla().setIdItemByPeriodoMes(filtro.getIdItemByPeriodoMes());
		var periodo = getPeriodoPlanilla(filtro.getPeriodoPlanilla());

		epsPersonalDao.eliminar(periodo);

		if (!StringUtil.isNullOrEmpty(filtro)) {
			eliminarRegistroPlanillas(filtro);
		}
		var resultado = "";
		var listadoPersonal = listarPersonalPagina(authToken, tipoPlanilla.getIdItemByCategoriaTrabajador());
		listadoPersonal.size();// truco paginador

		var filtroMap = new HashMap<String, Object>();
		filtroMap.put("idItemByCategoriaTrabajador", tipoPlanilla.getIdItemByCategoriaTrabajador());
		var listaIdPersonal = rrhhEscalafonClient.obtenterPersonalIds("", filtroMap).getListaResultado();

		CalculoPlanillaVO objCalc = new CalculoPlanillaVO();
		getCalculoPlanillaVO(authToken, filtro, objCalc, tipoPlanilla, periodo, listaIdPersonal);
		var conceptosMap = new HashMap<String, Map<String, BigDecimal>>();

		// esperar
		objCalc.isTerminoFuture();

		var variableAllMap = getVariableMap(listadoPersonal, objCalc, periodo, null);
		var showBoletaMap = getShowBoletaMap(objCalc.getListarConcepto());
		var interpreteMap = new HashMap<String, Context>();
		var listaConceptoMap = new HashMap<String, List<ConceptoCaluldadosVO>>();
		for (var obj : listadoPersonal) {
			// ScriptEngine interprete = manager.getEngineByExtension("js");
			Context interprete = Context.newBuilder("js").allowAllAccess(true).engine(manager).build();
			/*
			 * Bindings bindings = interprete.getBindings(ScriptContext.ENGINE_SCOPE);
			 * bindings.put("polyglot.js.allowHostAccess", true);
			 * bindings.put("polyglot.js.allowHostClassLookup", (Predicate<String>) s ->
			 * true); bindings.put("engine.WarnInterpreterOnly", false);
			 */
			interpreteMap.put(obj.getIdPersonal(), interprete);

			Map<String, Object> variableMap = variableAllMap.get(obj.getIdPersonal());

			var detConceptoMap = objCalc.getDetConceptoMap().get(obj.getIdPersonal());
			if (detConceptoMap == null) {
				detConceptoMap = new HashMap<String, DetallePersonalConcepto>();
			}

			var conceptoTrajadorStaticoMap = objCalc.getConceptoTrabajorStaticoMap().get(obj.getIdPersonal());
			if (conceptoTrajadorStaticoMap == null) {
				conceptoTrajadorStaticoMap = new HashMap<String, BigDecimal>();
			}

			calcularAllConcepto(obj, objCalc.getListarConcepto(), variableMap, detConceptoMap,
					conceptoTrajadorStaticoMap, interprete);

			variableMap = obtenerDataVariable(obj, variableMap);

			var listaConcepto = new ArrayList<ConceptoCaluldadosVO>();
			for (var objCon : obj.getListaIngreso()) {
				listaConcepto.add(objCon);
			}
			for (var objCon : obj.getListaDescuento()) {
				listaConcepto.add(objCon);
			}
			for (var objCon : obj.getListaAporteTrabajador()) {
				listaConcepto.add(objCon);
			}
			for (var objCon : obj.getListaAporteEmpleador()) {
				listaConcepto.add(objCon);
			}
			for (var objCon : listaConcepto) {
				if (StringUtil.isNotNullOrBlank(objCon.getFormula())) {
					if (!detConceptoMap.containsKey(objCon.getIdConceptosTipoPlanilla())) {
						objCon.setMonto(calcularFormula(obj, objCon, variableMap, interprete));
					}
					variableMap.put(objCon.getVariable(), objCon.getMonto());
				}
			}
			listaConceptoMap.put(obj.getIdPersonal(), listaConcepto);
		}

		// inicio sumatorias
		var variableTotalesMap = new HashMap<String, Object>();
		for (var obj : listadoPersonal) {
			Map<String, Object> variableMap = variableAllMap.get(obj.getIdPersonal());
			var listaConcepto = listaConceptoMap.get(obj.getIdPersonal());
			for (var objCon : listaConcepto) {
				if (objCon.getVariable().contains("Totales")) {
					BigDecimal valueTotal = (BigDecimal) variableTotalesMap.get(objCon.getVariable());
					if (valueTotal == null) {
						valueTotal = BigDecimal.ZERO;
					}
					BigDecimal valueIndividual = ObjectUtil
							.objectToBigDecimal(variableMap.get(objCon.getVariable().replaceAll("Totales", "")));
					if (valueIndividual == null) {
						valueIndividual = BigDecimal.ZERO;
					}
					variableTotalesMap.put(objCon.getVariable(), valueIndividual.add(valueTotal));
				}

			}
		}
		// fin sumatorias
		for (var obj : listadoPersonal) {
			// inicio calculando eps
			Context interprete = interpreteMap.get(obj.getIdPersonal());
			Map<String, Object> variableMap = variableAllMap.get(obj.getIdPersonal());
			variableMap.putAll(variableTotalesMap);
			var detConceptoMap = objCalc.getDetConceptoMap().get(obj.getIdPersonal());
			if (detConceptoMap == null) {
				detConceptoMap = new HashMap<String, DetallePersonalConcepto>();
			}

			var conceptoMapTemp = new HashMap<String, BigDecimal>();
			var listaConcepto = listaConceptoMap.get(obj.getIdPersonal());

			// getVariableEpsPersonalMap(obj, variableMap, registrarEPS(obj, periodo,
			// variableMap, interprete));

			variableMap.put(MesType.get(periodo.getIdItemByPeriodoMes()).getValue() + "Planilla",
					sumaTotal(obj.getListaIngreso()));

			getVariableGrupoFormulatMap(obj, variableMap, objCalc.getVariableGrupoFormulaMap(), interprete, true);

			for (var objCon : listaConcepto) {
				String keyConcepto = objCon.getIdConceptosTipoPlanilla();
				if (StringUtil.isNotNullOrBlank(objCon.getFormula())) {
					if (!detConceptoMap.containsKey(keyConcepto)) {
						objCon.setMonto(calcularFormula(obj, objCon, variableMap, interprete));
					}
					variableMap.put(objCon.getVariable(), objCon.getMonto());
				}
				if (RespuestaNaturalType.SI.getKey().toString().equalsIgnoreCase(objCon.getPersistente())
						|| (RespuestaNaturalType.SI.getKey().toString().equalsIgnoreCase(objCon.getShowBoleta()))) {
					conceptoMapTemp.put(keyConcepto, objCon.getMonto());
				}
			}
			conceptosMap.put(obj.getIdPersonal(), conceptoMapTemp);
			// fin calculando eps
		}

		periodo.setIdItemByPeriodoMes(filtro.getIdItemByPeriodoMes());
		periodo.setIdAnhio(filtro.getIdAnhio());
		var objPlanilla = generarObjetoPlanilla(tipoPlanilla, periodo);
		objPlanilla = this.planillaDao.save(objPlanilla);
		generarDetallePlanillaConcepto(generarDetallePlanilla(objPlanilla, listadoPersonal), conceptosMap,
				showBoletaMap);
		return resultado;
	}

	private PersonalVO calcularAllConcepto(PersonalVO obj, List<ConceptosTipoPlanilla> listarConcepto,
			Map<String, Object> variableMap, Map<String, DetallePersonalConcepto> detConceptoMap,
			Map<String, BigDecimal> conceptoTrajadorStaticoMap, Context interprete) {
		obj.setListaIngreso(calcularConceptoCalculados(obj, listarConcepto, CONCEPTO_INGRESOS, variableMap,
				detConceptoMap, conceptoTrajadorStaticoMap, interprete));

		obj.setListaDescuento(calcularConceptoCalculados(obj, listarConcepto, CONCEPTO_DESCUENTOS, variableMap,
				detConceptoMap, conceptoTrajadorStaticoMap, interprete));

		obj.setListaAporteTrabajador(calcularConceptoCalculados(obj, listarConcepto, CONCEPTO_APORTACIONES_TRABAJADROR,
				variableMap, detConceptoMap, conceptoTrajadorStaticoMap, interprete));

		obj.setListaAporteEmpleador(calcularConceptoCalculados(obj, listarConcepto, CONCEPTO_APORTACIONES_EMPLEADOR,
				variableMap, detConceptoMap, conceptoTrajadorStaticoMap, interprete));
		return obj;
	}

	private Map<String, Map<String, Object>> getVariableMap(List<PersonalVO> listadoPersonal, CalculoPlanillaVO objCalc,
			PeriodoPlanilla periodo, Context interprete) {
		var resultado = new HashMap<String, Map<String, Object>>();
		for (var obj : listadoPersonal) {
			var variableMap = new HashMap<String, Object>();
			getVariablePersonalMap(obj, variableMap);
			getVariableConceptoRegimenPensionarioMap(obj, variableMap, objCalc.getConceptoRegimenPensionarioMap());
			getVariableTareoPersonalMap(obj, variableMap, objCalc.getTareoPersonalMap());
			getVariablePeriodoMap(obj, variableMap, periodo);
			getVariableValoresUITMap(obj, variableMap, objCalc.getUit());
			getVariableEpsConfMap(obj, variableMap, objCalc.getEpsConfMap().get(obj.getIdItemByEps()));
			// getVariableEpsPersonalMap(obj, variableMap, new EPSPersonal());
			getVariableAdelantoMap(obj, variableMap, objCalc.getAdelantosMap());
			getVariableRentaEmpresaAntMap(obj, variableMap, objCalc.getRentaEmpresaAntMap());
			getVariableFeriadoMap(periodo, variableMap, objCalc.getFeriadoMap());
			getVariableParametroMap(obj, variableMap, objCalc.getListaParametro());
			getVariablePlanillaAntMap(obj, variableMap, objCalc.getPlanillaAntMap(), periodo);
			getVariableRenta5taAntMap(obj, variableMap, objCalc.getRenta5taAntMap());

			getVariableGrupoFormulatMap(obj, variableMap, objCalc.getVariableGrupoFormulaMap(), interprete, false);
			getMesMap(obj, variableMap);
			resultado.put(obj.getIdPersonal(), variableMap);
		}
		return resultado;
	}

	@Override
	public List<ConceptosTipoPlanilla> obtenerFormulaConceptosTipoPlanilla() {
		return conceptosTipoPlanillaDao.getFormulaConceptosTipo();
	}

	// Inicio objetos Future
	private CalculoPlanillaVO getCalculoPlanillaVO(String authToken, Planilla filtro, CalculoPlanillaVO objCalc,
			TipoPlanilla tipoPlanilla, PeriodoPlanilla periodo, List<String> listaIdPersonal) {

		objCalc.setListarConceptoFuture(obtenerConceptoTipoPlanillaFuture(tipoPlanilla.getIdTipoPlanilla()));

		objCalc.setListarTareoFuture(obtenerTareoFuture(periodo));

		// TODO:FALTA PERIODO DE PLANILLA
		objCalc.setConceptoTrabajorStaticoMapFuture(
				conceptoTrajadorStaticoMapFuture(tipoPlanilla.getIdItemByCategoriaTrabajador(), listaIdPersonal));

		// TODO:FALTA PERIODO DE PLANILLA
		objCalc.setAdelantosMapFuture(
				obtnerAdelantosMapFuture(tipoPlanilla.getIdItemByCategoriaTrabajador(), listaIdPersonal));

		objCalc.setConceptoRegimenPensionarioMapFuture(obtenerConceptoRegimenPensionarioMapFuture(null, null));

		var filtroEps = new BaseSearch();
		filtroEps.setIdAnhio(periodo.getIdAnhio());
		filtroEps.setIdItemByMes(periodo.getIdItemByPeriodoMes());
		filtroEps.setListaIdPersonal(listaIdPersonal);
		objCalc.setEpsPersonalMapFuture(obtenerEpsPersopnalMapFuture(filtroEps));

		objCalc.setListaParametrolMapFuture(listaParametroFuture(authToken));

		objCalc.setValoresUITFurure(valoresUITFurure(filtro));

		objCalc.setDetConceptoMapFuture(getDetConceptoMapFuture(tipoPlanilla, periodo, listaIdPersonal));

		var filtroConf = new BaseSearch();
		objCalc.setEpsConfMapFuture(obtenerEpsConfMapFuture(filtroConf));

		objCalc.setFeriadoMapFuture(getFeriadoMap(periodo));

		objCalc.setPlanillaAntMapFuture(getPlanillaAntMapFuture(tipoPlanilla, periodo, listaIdPersonal));

		objCalc.setRenta5taAntMapFuture(getRenta5taAntMapFuture(tipoPlanilla, periodo, listaIdPersonal));

		objCalc.setVariableGrupoFormulaMapFuture(getVariableGrupoFormulatMapFuture(tipoPlanilla));

		objCalc.setRentaEmpresaAntMapFuture(obtnerRentaEmpresaAntMapFuture(periodo.getIdAnhio(), listaIdPersonal));

		return objCalc;
	}

}
