package pe.buildsoft.erp.core.domain.entidades.rrhh.hora.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.CabeceraReporteVO;

@Getter
@Setter
public class RegistroHoraReporteVO extends CabeceraReporteVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String idPeriodo;
}
