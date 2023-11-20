package pe.buildsoft.erp.core.infra.transversal.entidades.vo;


import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExcelGrupoDataVO implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -5366374995964846317L;
	private String tipo;
	private int columnaInicio;
	private int columnaFin;
	private int filaInicio;
	private int filaFin;

	public ExcelGrupoDataVO(String tipo) {
		this.tipo = tipo;
	}

	
	@Override
	public String toString() {
		return "ExcelGrupoDataVO{" +
				"ENTIDAD=" + tipo+
				" columnaInicio=" + columnaInicio +
				", columnaFin=" + columnaFin +
				", filaInicio=" + filaInicio +
				", filaFin=" + filaFin +
				'}';
	}
}
