package pe.buildsoft.erp.core.application.servicios.pago.reporte;

import jakarta.inject.Inject;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;

import pe.buildsoft.erp.core.application.entidades.pago.ControlPagoDTO;
import pe.buildsoft.erp.core.application.interfaces.IServiceApp;
import pe.buildsoft.erp.core.domain.entidades.pago.ControlPago;
import pe.buildsoft.erp.core.domain.interfaces.servicios.pago.reporte.PagoReporteServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.utilitario.BaseTransfer;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@TransactionManagement(TransactionManagementType.BEAN)
public class PagoReporteAppServiceImpl extends BaseTransfer implements IServiceApp {

	@Inject
	private PagoReporteServiceLocal servicio;

	@Override
	public String procesar(Object objFiltro) {
		ControlPagoDTO obj = (ControlPagoDTO) objFiltro;
		return servicio.generarReportePagoIndividual(to(obj, ControlPago.class));
	}

}
