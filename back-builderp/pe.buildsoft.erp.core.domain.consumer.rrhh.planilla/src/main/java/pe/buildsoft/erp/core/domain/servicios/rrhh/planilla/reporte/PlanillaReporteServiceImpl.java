package pe.buildsoft.erp.core.domain.servicios.rrhh.planilla.reporte;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.inject.Inject;
import pe.buildsoft.erp.core.domain.entidades.planilla.ConceptoPdt;
import pe.buildsoft.erp.core.domain.entidades.planilla.ConceptosTipoPlanilla;
import pe.buildsoft.erp.core.domain.entidades.planilla.DetallePlanilla;
import pe.buildsoft.erp.core.domain.entidades.planilla.DetallePlanillaConcepto;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.DetallePlanillaConceptoConsumerDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.DetallePlanillaConsumerDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.servicios.GenerarReporteServiceLocal;
import pe.buildsoft.erp.core.domain.interfaces.servicios.rrhh.planilla.reporte.PlanillaReporteServiceLocal;
import pe.buildsoft.erp.core.domain.type.PlanillaReporteType;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ParametroReporteVO;
import pe.buildsoft.erp.core.infra.transversal.type.TipoReporteGenerarType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.BaseTransfer;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@TransactionManagement(TransactionManagementType.BEAN)
public class PlanillaReporteServiceImpl extends BaseTransfer implements PlanillaReporteServiceLocal {

	private static Logger log = LoggerFactory.getLogger(PlanillaReporteServiceImpl.class);

	@Inject
	private GenerarReporteServiceLocal generarReporteServiceImpl;

	/** El servicio detalle planilla dao impl. */
	@Inject
	private DetallePlanillaConsumerDaoLocal detallePlanillaDaoImpl;

	/** El servicio detalle planilla concepto dao impl. */
	@Inject
	private DetallePlanillaConceptoConsumerDaoLocal detallePlanillaConceptoDaoImpl;

	@Override
	public String generarReportePlanillaBoletaIndividual(DetallePlanilla filtro, boolean isOnline) {
		String fileName = filtro.getArchivoName();
		String userName = filtro.getUsuario();
		String codigoGeneradoReporte = fileName;
		try {
			var parametros = new HashMap<String, Object>();
			BaseSearch planillaFiltro = toPojo(filtro, BaseSearch.class);
			List<DetallePlanilla> lista = obtenerPlanilla(planillaFiltro, false);
			PlanillaReporteType reporte = PlanillaReporteType.JR_REP_PLANILLA_BOLETA_INDIVIDUAL;
			ParametroReporteVO objParam = new ParametroReporteVO(parametros, lista, reporte.getCarpeta(),
					reporte.getKey(), true, TipoReporteGenerarType.PDF.getKey(), fileName);
			objParam.setUserName(userName);
			objParam.setOnline(isOnline);
			codigoGeneradoReporte = generarReporteServiceImpl.obtenerParametroReporteBigMemory(objParam);
			return codigoGeneradoReporte;
		} catch (Exception e) {
			codigoGeneradoReporte = ("${ERROR}Ocurrio un error al generar el reporte boleta individual "
					+ e.getMessage().replace("\"", "'")).substring(0, 255);
			log.error("generarReportePlanillaBoletaIndividual", e);
		}
		return codigoGeneradoReporte;
	}

	@Override
	public String generarReportePlanillaBoletaMasiva(DetallePlanilla filtro, boolean isOnline) {
		String fileName = filtro.getArchivoName();
		String userName = filtro.getUsuario();
		String codigoGeneradoReporte = fileName;
		try {
			BaseSearch planillaFiltro = toPojo(filtro, BaseSearch.class);
			var parametros = new HashMap<String, Object>();
			List<DetallePlanilla> lista = new ArrayList<>();
			DetallePlanilla ojbTemp = new DetallePlanilla();
			ojbTemp.setPlanilla(filtro.getPlanilla());
			ojbTemp.getPlanilla().setIdAnhio(filtro.getPlanilla().getIdAnhio());
			ojbTemp.setDetallePlanillaMasiva(obtenerPlanilla(planillaFiltro, true));
			lista.add(ojbTemp);
			PlanillaReporteType reporte = PlanillaReporteType.JR_REP_PLANILLA_BOLETA_MASIVA;
			ParametroReporteVO objParam = new ParametroReporteVO(parametros, lista, reporte.getCarpeta(),
					reporte.getKey(), true, TipoReporteGenerarType.PDF.getKey(), fileName);
			objParam.setUserName(userName);
			objParam.setOnline(isOnline);
			codigoGeneradoReporte = generarReporteServiceImpl.obtenerParametroReporteBigMemory(objParam);
			return codigoGeneradoReporte;
		} catch (Exception e) {
			codigoGeneradoReporte = ("${ERROR}Ocurrio un error al generar el reporte boleta masivo "
					+ e.getMessage().replace("\"", "'")).substring(0, 255);
			log.error("generarReportePlanillaBoletaMasiva", e);
		}
		return codigoGeneradoReporte;
	}

	private List<DetallePlanilla> obtenerPlanilla(BaseSearch filtro, boolean masivo) {
		List<DetallePlanilla> resultado = detallePlanillaDaoImpl.get(filtro);
		for (var obj : resultado) {
			DetallePlanillaConcepto objDet = new DetallePlanillaConcepto();
			objDet.setDetallePlanilla(obj);
			objDet.setConcepto(new ConceptosTipoPlanilla());
			obj.setDetallePlanillaDetallePlanillaConceptoList(obtenerDetallePlanillaConcepto(objDet));
			if (masivo) {
				List<DetallePlanilla> resultadoMasivo = new ArrayList<>();
				resultadoMasivo.add(obj);
				obj.setDetallePlanillaMasiva(resultadoMasivo);
			}
		}
		return resultado;
	}

	private List<DetallePlanillaConcepto> obtenerDetallePlanillaConcepto(DetallePlanillaConcepto filtro) {
		List<DetallePlanillaConcepto> resultado = new ArrayList<>();
		String[] listTipoConceptPDT = { "I", "D", "A" };
		for (var objConceptoPDT : listTipoConceptPDT) {
			DetallePlanillaConcepto filtroDet = new DetallePlanillaConcepto();
			filtroDet.setConcepto(new ConceptosTipoPlanilla());
			filtroDet.getConcepto().setConceptoPdt(new ConceptoPdt());
			filtroDet.getConcepto().getConceptoPdt().setTipo(objConceptoPDT);
			filtroDet.setDetallePlanilla(filtro.getDetallePlanilla());
			List<DetallePlanillaConcepto> listaObj = detallePlanillaConceptoDaoImpl.get(filtroDet);
			DetallePlanillaConcepto detObj = new DetallePlanillaConcepto();
			detObj.setConcepto(new ConceptosTipoPlanilla());
			detObj.getConcepto().setConceptoPdt(new ConceptoPdt());
			detObj.getConcepto().getConceptoPdt().setTipo(objConceptoPDT);
			detObj.setDetallePlanillaDetallePlanillaConceptoList(listaObj);
			resultado.add(detObj);
		}
		return resultado;
	}

}
