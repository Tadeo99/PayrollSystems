package pe.buildsoft.erp.core.application.servicios.matricula.reporte;

import jakarta.inject.Inject;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;

import pe.buildsoft.erp.core.application.interfaces.IServiceApp;
import pe.buildsoft.erp.core.domain.entidades.matricula.vo.FichaMatriculaVO;
import pe.buildsoft.erp.core.domain.interfaces.servicios.matricula.reporte.MatriculaReporteServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.utilitario.BaseTransfer;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@TransactionManagement(TransactionManagementType.BEAN)
public class MatriculaReporteAppServiceImpl extends BaseTransfer implements IServiceApp {

	@Inject
	private MatriculaReporteServiceLocal servicio;

	@Override
	public String procesar(Object objFiltro) {
		return servicio.generarReporteFichaMatricula((FichaMatriculaVO)objFiltro);
	}

}
