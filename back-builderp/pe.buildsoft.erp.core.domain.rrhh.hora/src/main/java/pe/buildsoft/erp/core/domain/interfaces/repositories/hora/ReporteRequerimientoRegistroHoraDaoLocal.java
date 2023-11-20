package pe.buildsoft.erp.core.domain.interfaces.repositories.hora;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.rrhh.hora.vo.RegistroHoraVO;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;

/**
 * La Class RequerimientoDaoLocal.
 * <ul>
 * <li>Copyright 2021 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Thu Feb 24 15:07:25 COT 2022
 * @since BuildErp 1.0
 */
@Local
public interface ReporteRequerimientoRegistroHoraDaoLocal extends GenericDAOLocal<String, RegistroHoraVO> {

	Map<String, List<RegistroHoraVO>> listar(List<String> listaIdPersonal, List<String> listaIdCentroCosto,
			String idPeriodo);

}