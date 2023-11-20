package pe.buildsoft.erp.core.application.servicios.nota.reporte;

import jakarta.inject.Inject;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;

import pe.buildsoft.erp.core.application.interfaces.IServiceApp;
import pe.buildsoft.erp.core.domain.entidades.nota.vo.ReporteNotaVO;
import pe.buildsoft.erp.core.domain.interfaces.servicios.nota.reporte.NotaReporteServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.utilitario.BaseTransfer;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@TransactionManagement(TransactionManagementType.BEAN)
public class NotaReporteAppServiceImpl extends BaseTransfer implements IServiceApp {

	@Inject
	private NotaReporteServiceLocal servicio;

	@Override
	public String procesar(Object objFiltro) {
		return servicio.generarReporteMultiple((ReporteNotaVO) objFiltro);
	}

}
