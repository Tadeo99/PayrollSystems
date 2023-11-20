package pe.buildsoft.erp.core.application.servicios.rrhh.planilla.reporte;

import jakarta.inject.Inject;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;

import pe.buildsoft.erp.core.application.entidades.planilla.DetallePlanillaDTO;
import pe.buildsoft.erp.core.application.interfaces.IServiceApp;
import pe.buildsoft.erp.core.domain.entidades.planilla.DetallePlanilla;
import pe.buildsoft.erp.core.domain.interfaces.servicios.rrhh.planilla.reporte.PlanillaReporteServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.cache.AtributosEntityCacheUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.BaseTransfer;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@TransactionManagement(TransactionManagementType.BEAN)
public class PlanillaReporteAppServiceImpl extends BaseTransfer implements IServiceApp {

	@Inject
	private PlanillaReporteServiceLocal servicio;

	public PlanillaReporteAppServiceImpl() {
		AtributosEntityCacheUtil.getInstance().sincronizarAtributos("pe.buildsoft.erp.core.domain.entidades.escalafon",
				"pe.buildsoft.erp.core.domain.entidades.common", "pe.buildsoft.erp.core.domain.entidades.planilla",
				"pe.buildsoft.erp.core.domain.entidades.security");
	}

	@Override
	public String procesar(Object objFiltro) {
		DetallePlanillaDTO filtro = (DetallePlanillaDTO) objFiltro;
		if ("M".equals(filtro.getTipo())) {
			return servicio.generarReportePlanillaBoletaMasiva(
					to(filtro, DetallePlanilla.class, "planilla:{itemByPeriodoMes;itemByTipoTrabajador,anhio}","personal"), filtro.isOnline());
		}
		return servicio.generarReportePlanillaBoletaIndividual(
				to(filtro, DetallePlanilla.class, "planilla:{itemByPeriodoMes;itemByTipoTrabajador,anhio}","personal"), filtro.isOnline());

	}

}
