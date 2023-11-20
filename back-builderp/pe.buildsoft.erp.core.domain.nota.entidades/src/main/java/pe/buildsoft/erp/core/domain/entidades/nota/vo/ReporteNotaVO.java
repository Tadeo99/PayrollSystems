package pe.buildsoft.erp.core.domain.entidades.nota.vo;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.CabeceraReporteVO;

@Getter
@Setter
public class ReporteNotaVO extends CabeceraReporteVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tipoReporte; 
	private String idEscuela;
	private String idAnhoSemestre;
}
