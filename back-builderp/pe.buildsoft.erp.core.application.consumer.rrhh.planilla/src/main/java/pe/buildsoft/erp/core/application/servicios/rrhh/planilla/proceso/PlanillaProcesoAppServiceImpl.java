package pe.buildsoft.erp.core.application.servicios.rrhh.planilla.proceso;

import jakarta.inject.Inject;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;

import pe.buildsoft.erp.core.application.entidades.planilla.PlanillaDTO;
import pe.buildsoft.erp.core.application.interfaces.IServiceApp;
import pe.buildsoft.erp.core.domain.entidades.planilla.Planilla;
import pe.buildsoft.erp.core.domain.interfaces.servicios.rrhh.planilla.proceso.PlanillaProcesoServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.cache.AtributosEntityCacheUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.BaseTransfer;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@TransactionManagement(TransactionManagementType.BEAN)
public class PlanillaProcesoAppServiceImpl extends BaseTransfer implements IServiceApp {

	@Inject
	private PlanillaProcesoServiceLocal servicio;

	public PlanillaProcesoAppServiceImpl() {
		AtributosEntityCacheUtil.getInstance().sincronizarAtributos("pe.buildsoft.erp.core.domain.entidades.escalafon",
				"pe.buildsoft.erp.core.domain.entidades.common", "pe.buildsoft.erp.core.domain.entidades.planilla",
				"pe.buildsoft.erp.core.domain.entidades.security");
	}

	@Override
	public String procesar(Object objFiltro) throws Exception {
		PlanillaDTO obj = (PlanillaDTO) objFiltro;
		return servicio.generarPlanilla("", to(obj, Planilla.class,"tipoPlanilla"));
	}

}
