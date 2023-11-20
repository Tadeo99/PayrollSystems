package pe.buildsoft.erp.core.infra.transversal.entidades;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * La Class BaseGeneric.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * Mapfre. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Wed Sep 30 11:24:54 COT 2017
 * @since SIAA-CORE 2.1
 */
@Getter
@Setter
public class BaseGeneric implements Serializable {
	
	/** La Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** La offset. */
	private int offSet;
	
	/** La start row. */
	private int startRow;
	
	private String search;
	
	private boolean check;
	
	private transient Object id;
	
	private String descripcionView;
	
	private transient Object idPadreView;
	
	private String idEntidadSelect = "";
	
	private boolean esEliminado = false;

	private String authToken;
	
	private Integer position = 0;
	
}
