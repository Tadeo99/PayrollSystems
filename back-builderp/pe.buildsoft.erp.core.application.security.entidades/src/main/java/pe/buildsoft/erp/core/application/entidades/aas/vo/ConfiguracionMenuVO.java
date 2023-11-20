package pe.buildsoft.erp.core.application.entidades.aas.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.security.ConfiguracionMenuDTO;


/**
 * <ul>
 * <li>Copyright 2012 BUILD SOFT S.A.C - BS. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Class ConfiguracionMenuVO.
 *
 * @author ndavilal.
 * @version 1.0 , 31/08/2013
 * @since SIAA 2.0
 */
@Getter
@Setter
public class ConfiguracionMenuVO  implements Serializable {

	/** La Constante serialVersionUID. */
	private static final long serialVersionUID = 1;
	
	/** El id. */
	private Long id;
	
	/** El codigo. */
	private Long codigo;
		
	/** La descripcion. */
	private String descripcion;
		
	/** La lista configuracion menus. */
	private List<ConfiguracionMenuDTO> listaConfiguracionMenus = new ArrayList<>();
	
	/**
	 * Instancia un nuevo concepto pago dto.
	 */
	public ConfiguracionMenuVO() {
		super();
	}
	
}
