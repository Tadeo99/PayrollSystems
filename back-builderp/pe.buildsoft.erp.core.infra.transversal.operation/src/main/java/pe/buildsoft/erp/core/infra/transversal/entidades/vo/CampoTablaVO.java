package pe.buildsoft.erp.core.infra.transversal.entidades.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CampoTablaVO implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nombreCampo;
	private String type;
	private String length;
	private String isNull;
	private String campoAsociado;
	
	
	public CampoTablaVO() {
		super();
	}


	public CampoTablaVO(String nombreCampo, String type, String length,
			String isNull, String campoAsociado) {
		super();
		this.nombreCampo = nombreCampo;
		this.type = type;
		this.length = length;
		this.isNull = isNull;
		this.campoAsociado = campoAsociado;
	}

}
