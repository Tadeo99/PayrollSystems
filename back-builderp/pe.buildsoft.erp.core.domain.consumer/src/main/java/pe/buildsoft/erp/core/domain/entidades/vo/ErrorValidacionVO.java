package pe.buildsoft.erp.core.domain.entidades.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * <ul>
 * <li>Copyright 2017 ndavilal. Todos los derechos reservados.</li>
 * </ul> 
 * 
 * La Class ErrorDroolsVO.
 *
 * @author ndavilal
 * @version 1.0 , 06/06/2016
 * @since SIAA-CORE 2.1
 */
@Getter
@Setter
public class ErrorValidacionVO extends BaseEntidad implements Serializable {

	/** La Constante serialVersionUID. */
	private static final long serialVersionUID = 9166621199365741020L;
	
	/** La id correlativo error. */
	private Long idCorrelativoError;
	
	/** La linea error. */
	private Long lineaError;
	
	/** La tipo error. */
	private String tipoError;
	
	/** La descripcion error. */
	private String descripcionError;
	
	/** La error tecnico. */
	private String errorTecnico;
	
	/** La clase error. */
	private String claseError;
	
	/**
	 * Instancia un nuevo error regla vo.
	 */
	public ErrorValidacionVO() {
		super();
	}
	
	
}
