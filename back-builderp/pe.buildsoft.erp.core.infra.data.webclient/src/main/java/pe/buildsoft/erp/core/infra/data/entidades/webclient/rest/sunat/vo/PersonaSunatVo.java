package pe.buildsoft.erp.core.infra.data.entidades.webclient.rest.sunat.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonaSunatVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String dni;
	private String nombres;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String codVerifica;

}
