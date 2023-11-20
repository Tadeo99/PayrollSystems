/*
 * 
 */
package pe.buildsoft.erp.core.application.entidades.aas.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * <ul>
 * <li>Copyright 2012 BUILD SOFT S.A.C - BS. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Class MsgBean.
 *
 * @author ndavilal.
 * @version 1.0 , 22/03/2014
 * @since SIAA 2.0
 */
@Getter
@Setter
public class ConfiguracionFormularioVO implements Serializable {

	/** La Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	

	/** El required map. */
	private Map<String,Boolean> requiredInputMap = new HashMap<>();
	
	/** El required map. */
	private Map<String,Boolean> requiredGrillaMap = new HashMap<>();
	
	/** El readonly map. */
	private Map<String,Boolean> readonlyInputMap = new HashMap<>();
	
	/** El readonly map. */
	private Map<String,Boolean> readonlyGrillaMap = new HashMap<>();

	/** El rendered map. */
	private Map<String,Boolean> renderedLabelMap = new HashMap<>();
	
	/** El rendered map. */
	private Map<String,Boolean> renderedInputMap = new HashMap<>();
	
	/** El rendered map. */
	private Map<String,Boolean> renderedButtonMap = new HashMap<>();
	
	/** El rendered map. */
	private Map<String,Boolean> renderedGrillaMap = new HashMap<>();
	
	/** El rendered map. */
	private Map<String,Boolean> renderedButtonGrillaMap = new HashMap<>();
	
	/** El disabled map. */
	private Map<String,Boolean> disabledInputMap = new HashMap<>();
	
	/** El disabled map. */
	private Map<String,Boolean> disabledInputGrillaMap = new HashMap<>();
	
	/** El disabled map. */
	private Map<String,Boolean> disabledButtonMap = new HashMap<>();
	
	/** El disabled map. */
	private Map<String,Boolean> disabledButtonGrillaMap = new HashMap<>();


	
	/**
	 * Instancia un nuevo faces home.
	 */
	public ConfiguracionFormularioVO() {
		super();
	}
	
}
