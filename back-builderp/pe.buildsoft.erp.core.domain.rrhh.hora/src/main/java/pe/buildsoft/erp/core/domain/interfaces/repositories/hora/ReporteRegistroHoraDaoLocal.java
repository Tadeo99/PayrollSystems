package pe.buildsoft.erp.core.domain.interfaces.repositories.hora;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.rrhh.hora.vo.RegistroHoraVO;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

@Local
public interface ReporteRegistroHoraDaoLocal extends GenericDAOLocal<String, RegistroHoraVO> {

	List<RegistroHoraVO> listar(BaseSearch filtro, String idPersonal, String idPeriodo);

	int contar(BaseSearch filtro, String idPersonal, String idPeriodo);

}
