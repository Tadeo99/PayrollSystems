package pe.buildsoft.erp.core.application.interfaces.hora;
import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.application.entidades.hora.PeriodoDTO;
import pe.buildsoft.erp.core.application.entidades.hora.RegistroHoraDTO;
import pe.buildsoft.erp.core.application.entidades.hora.RegistroHoraDetDTO;
import pe.buildsoft.erp.core.application.entidades.hora.RegistroHoraDetVO;
import pe.buildsoft.erp.core.application.entidades.hora.RequerimientoDTO;
import pe.buildsoft.erp.core.application.entidades.hora.RequerimientoPersonalDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;

/**
 * La Class AasServiceLocal.
 * <ul>
 * <li>Copyright 2019 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue 08/01/2019
 * @since SIAA-CORE 2.1
 */
@Local
public interface RegistroHoraAppServiceLocal {

	RegistroHoraDTO registrarHora(RegistroHoraDTO obj,AccionType accionType); 
	
	List<RegistroHoraDTO> listarRegistroHora(BaseSearch filtro);

	int contarListarRegistroHora(BaseSearch filtro);
	
	List<RegistroHoraDetDTO> listarRegistroHoraDet(BaseSearch filtro);

	List<RegistroHoraDetVO> obtenerRegistroHora(String idPersonal,String idPeriodo,Long numSemana,String estadoPeriodo);

	PeriodoDTO controladorAccionPeriodo(PeriodoDTO obj,AccionType accionType); 
	
	List<PeriodoDTO> listarPeriodo(BaseSearch filtro);

	int contarListarPeriodo(BaseSearch filtro);

	
	RequerimientoDTO controladorAccionRequerimiento(RequerimientoDTO obj, AccionType accionType);
	
	List<RequerimientoDTO> listarRequerimiento(BaseSearch filtro);
	
	int contarListarRequerimiento(BaseSearch filtro);
	
	
	RequerimientoPersonalDTO controladorAccionRequerimientoPersonal(RequerimientoPersonalDTO obj, AccionType accionType);
	
	List<RequerimientoPersonalDTO> listarRequerimientoPersonal(BaseSearch filtro);
	
	int contarListarRequerimientoPersonal(BaseSearch filtro);
	
}