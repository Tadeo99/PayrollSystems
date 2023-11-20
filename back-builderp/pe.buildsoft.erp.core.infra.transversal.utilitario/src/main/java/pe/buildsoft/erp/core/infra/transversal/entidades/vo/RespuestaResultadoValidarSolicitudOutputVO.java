package pe.buildsoft.erp.core.infra.transversal.entidades.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;



/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul> 
 * 
 * La Class RespuestaResultadoValidarSolicitudOutputVO.
 *
 * @author BuildSoft.
 * @version 1.0 , 06/04/2015
 * @since BuildErp 1.0
 */
@Getter
@Setter
public class RespuestaResultadoValidarSolicitudOutputVO implements Serializable {

	/** La Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** La error. */
	private List<String> error;
	
	/** La estado. */
	private Integer estado;
	
	/** La descripcion estado. */
	private String descripcionEstado;
	
	/**
	 * Comprueba si es error.
	 *
	 * @return true, si es error
	 */
	public boolean isError() {
		return error != null && error.size() > 0;
	}

	/**
	 * Obtiene errores.
	 *
	 * @return errores
	 */
	public String getErrores() {
		StringBuilder errores = new StringBuilder();
		for (var errors : error) {
			errores.append(errors);
			errores.append("<br/>");
		}
		return errores.toString();		
	}	
	
}
