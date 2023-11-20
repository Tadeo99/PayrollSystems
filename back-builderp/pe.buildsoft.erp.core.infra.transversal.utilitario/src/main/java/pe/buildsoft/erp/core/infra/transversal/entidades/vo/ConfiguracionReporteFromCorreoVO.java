package pe.buildsoft.erp.core.infra.transversal.entidades.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;



/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul> 
 * 
 * La Class ConfiguracionReporteFromCorreoVO.
 *
 * @author BuildSoft.
 * @version 1.0 , 06/04/2015
 * @since BuildErp 1.0
 */
@Getter
@Setter
public class ConfiguracionReporteFromCorreoVO  extends BaseEntidad implements Serializable {
 
	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** La codigo form. */
	private String codigoForm;
	
	/** La flag ldap. */
	private String flagLDAP;
	
	/** La nombre form. */
	private String nombreForm;
	
	/** La email. */
	private String email;
	
	/**
	 * Instancia un nuevo configuracion reporte from correo vo.
	 */
	public ConfiguracionReporteFromCorreoVO() {
		super();
	}

	/**
	 * Instancia un nuevo configuracion reporte from correo vo.
	 *
	 * @param codigoForm el codigo form
	 * @param flagLDAP el flag ldap
	 * @param nombreForm el nombre form
	 * @param email el email
	 */
	public ConfiguracionReporteFromCorreoVO(String codigoForm, String flagLDAP,
			String nombreForm,String email) {
		super();
		this.codigoForm = codigoForm;
		this.flagLDAP = flagLDAP;
		this.nombreForm = nombreForm;
		this.email = email;
	}
}