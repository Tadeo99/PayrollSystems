package pe.buildsoft.erp.core.infra.transversal.entidades.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * La Class DetalleCorreoVO.
 * <ul>
 * <li>Copyright 2014 MAPFRE-
 * mapfre. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Mon Aug 11 09:00:30 COT 2014
 * @since BuildErp 1.0
 */
@Getter
@Setter
public class DetalleCorreoVO implements Serializable {
 
	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** El email. */
	private String email;
	
	/** El tipo. */
	private String tipo;
	
	/**
	 * Instancia un nuevo detalle correo.
	 */
	public DetalleCorreoVO() {
	}
	
	/**
	 * Instancia un nuevo detalle correo vo.
	 *
	 * @param email el email
	 * @param tipo el tipo
	 */
	public DetalleCorreoVO(String email, String tipo ) {
		super();
		this.email = email;
		this.tipo = tipo;
	}
}