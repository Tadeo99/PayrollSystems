package pe.buildsoft.erp.core.domain.interfaces.servicios.hora.reporte;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.rrhh.hora.vo.RegistroHoraVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

@Local
public interface ReporteRegistroHoraServiceLocal {

	List<RegistroHoraVO> obtenerRegistroHora(BaseSearch filtro, String idPersonal, String idPeriodo);

	int contarReporteRegistroHora(BaseSearch filtro, String idPersonal, String idPeriodo);

}
