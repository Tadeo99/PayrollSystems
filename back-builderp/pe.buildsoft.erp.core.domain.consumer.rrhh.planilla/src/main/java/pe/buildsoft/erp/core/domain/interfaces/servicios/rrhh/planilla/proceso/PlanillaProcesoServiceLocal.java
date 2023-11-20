package pe.buildsoft.erp.core.domain.interfaces.servicios.rrhh.planilla.proceso;

import java.util.List;

import jakarta.ejb.Local;
import pe.buildsoft.erp.core.domain.entidades.planilla.ConceptosTipoPlanilla;
import pe.buildsoft.erp.core.domain.entidades.planilla.Planilla;

@Local
public interface PlanillaProcesoServiceLocal {

	String generarPlanilla(String authToken, Planilla filtro) throws Exception ;

	List<ConceptosTipoPlanilla> obtenerFormulaConceptosTipoPlanilla();
}
