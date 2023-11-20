package pe.buildsoft.erp.core.application.servicios.pago.proceso;

import jakarta.inject.Inject;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;

import pe.buildsoft.erp.core.application.interfaces.IServiceApp;
import pe.buildsoft.erp.core.domain.interfaces.servicios.pago.proceso.PagoProcesoServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.utilitario.BaseTransfer;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@TransactionManagement(TransactionManagementType.BEAN)
public class PagoProcesoAppServiceImpl extends BaseTransfer implements IServiceApp {

	@Inject
	private PagoProcesoServiceLocal servicio;

	@Override
	public String procesar(Object objFiltro) {
		return "";//servicio.generarReportePagoIndividual(to(obj, ControlPago.class));
	}

}
