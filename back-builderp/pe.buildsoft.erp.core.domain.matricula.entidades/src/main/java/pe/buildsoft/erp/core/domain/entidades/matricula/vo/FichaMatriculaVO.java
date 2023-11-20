package pe.buildsoft.erp.core.domain.entidades.matricula.vo;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.CabeceraReporteVO;

@Getter
@Setter
public class FichaMatriculaVO extends CabeceraReporteVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idAnhio;
	private String idAlumno;
	private Long idPeriodo;
	private Long idTurno;
	private boolean copiaCorreo;
}
