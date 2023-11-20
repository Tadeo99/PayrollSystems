package pe.buildsoft.erp.core.domain.interfaces.servicios.matricula.reporte;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.matricula.vo.FichaMatriculaVO;

@Local
public interface MatriculaReporteServiceLocal {
	
	String generarReporteFichaMatricula(FichaMatriculaVO filtro);


}
	