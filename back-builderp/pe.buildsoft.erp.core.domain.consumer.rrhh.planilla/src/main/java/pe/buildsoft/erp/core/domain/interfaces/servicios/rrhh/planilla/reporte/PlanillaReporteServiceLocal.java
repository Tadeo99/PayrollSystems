package pe.buildsoft.erp.core.domain.interfaces.servicios.rrhh.planilla.reporte;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.planilla.DetallePlanilla;

@Local
public interface PlanillaReporteServiceLocal {
	
	String generarReportePlanillaBoletaIndividual(DetallePlanilla filtro,boolean isOnline);

	String generarReportePlanillaBoletaMasiva(DetallePlanilla filtro,boolean isOnline);

}
	