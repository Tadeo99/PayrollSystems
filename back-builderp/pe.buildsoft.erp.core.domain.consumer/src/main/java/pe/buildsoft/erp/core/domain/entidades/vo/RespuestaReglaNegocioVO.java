package pe.buildsoft.erp.core.domain.entidades.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
public class RespuestaReglaNegocioVO extends BaseEntidad implements Serializable {

	/** La Constante serialVersionUID. */
	private static final long serialVersionUID = 9166621199365741020L;
	
	
	/** La respuesta validacion. */
	private boolean respuestaValidacion;
	
	/** La error regla negocio v os. */
	private List<ErrorValidacionVO> errorReglaNegocioList = new ArrayList<>();
	
	/**
	 * Instancia un nuevo error regla vo.
	 */
	public RespuestaReglaNegocioVO() {
		super();
	}

}
