package pe.buildsoft.erp.core.application.servicios.rrhh.hora.reporte;

import jakarta.inject.Inject;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;

import pe.buildsoft.erp.core.application.interfaces.IServiceApp;
import pe.buildsoft.erp.core.domain.entidades.rrhh.hora.vo.RegistroHoraReporteVO;
import pe.buildsoft.erp.core.domain.interfaces.servicios.rrhh.hora.reporte.HoraReporteServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.utilitario.BaseTransfer;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@TransactionManagement(TransactionManagementType.BEAN)
public class HoraReporteAppServiceImpl extends BaseTransfer implements IServiceApp {

	@Inject
	private HoraReporteServiceLocal servicio;

	@Override
	public String procesar(Object objFiltro) {
		return servicio.procesarReporteRegistroHora((RegistroHoraReporteVO)objFiltro);
	}

}
