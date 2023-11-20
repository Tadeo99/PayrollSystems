package pe.buildsoft.erp.core.application.servicios.hora.reporte;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;

import pe.buildsoft.erp.core.application.interfaces.hora.reporte.ReporteRegistroHoraAppServiceLocal;
import pe.buildsoft.erp.core.domain.entidades.rrhh.hora.vo.RegistroHoraVO;
import pe.buildsoft.erp.core.domain.interfaces.servicios.hora.reporte.ReporteRegistroHoraServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@TransactionManagement(TransactionManagementType.BEAN)
public class ReporteRegistroHoraAppServiceImpl implements ReporteRegistroHoraAppServiceLocal  {
	
	@Inject
	private ReporteRegistroHoraServiceLocal servicio;
	
	@Override
	public List<RegistroHoraVO> obtenerRegistroHora(BaseSearch basePaginator,String idPersonal,String idPeriodo) {
		return servicio.obtenerRegistroHora(basePaginator,idPersonal,idPeriodo);
	}
	
	@Override
	public int contarReporteRegistroHora(BaseSearch basePaginator,String idPersonal,String idPeriodo) {
		return servicio.contarReporteRegistroHora(basePaginator,idPersonal,idPeriodo);
	}
	
}
