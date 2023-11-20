package pe.buildsoft.erp.core.application.interfaces.hora.reporte;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.rrhh.hora.vo.RegistroHoraVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
@Local
public interface ReporteRegistroHoraAppServiceLocal {
	
	List<RegistroHoraVO> obtenerRegistroHora(BaseSearch basePaginator,String idPersonal,String idPeriodo);
	int contarReporteRegistroHora(BaseSearch BaseSearch,String idPersonal,String idPeriodo);
	
}
