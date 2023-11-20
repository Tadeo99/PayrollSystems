package pe.buildsoft.erp.core.infra.transversal.entidades.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.type.ColaDisponibleType;

@Getter
@Setter
public class RespuestaAsignacionColaVO implements Serializable {

	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	private ColaDisponibleType colaDisponibleType = null;
	private Long codigoJuego = null;

	public RespuestaAsignacionColaVO() {
		super();
	}

	public RespuestaAsignacionColaVO(ColaDisponibleType colaDisponibleType, Long codigoJuego) {
		super();
		this.colaDisponibleType = colaDisponibleType;
		this.codigoJuego = codigoJuego;
	}


}
