package pe.buildsoft.erp.core.infra.transversal.entidades.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;


/**
 * La Class RegistroMensajeriaOutputVO.
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
public class RegistroMensajeriaOutputVO implements Serializable {

	/** La Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** La error. */
	private String error;
	
	/** La estado. */
	private Integer estado;
	
	/** La descripcion estado. */
	private String descripcionEstado;

}
