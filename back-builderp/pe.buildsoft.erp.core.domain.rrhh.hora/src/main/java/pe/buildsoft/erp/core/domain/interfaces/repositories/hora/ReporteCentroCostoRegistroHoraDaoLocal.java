package pe.buildsoft.erp.core.domain.interfaces.repositories.hora;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.rrhh.hora.vo.RegistroHoraVO;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;

@Local
public interface ReporteCentroCostoRegistroHoraDaoLocal extends GenericDAOLocal<String, RegistroHoraVO> {

	Map<String, List<RegistroHoraVO>> listar(List<String> listaIdPersonal, String idPeriodo);

}
