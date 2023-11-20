package pe.buildsoft.erp.core.infra.transversal.type;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
* La Enum ColaDisponibleType.
* <ul>
* <li>Copyright 2017 MAPFRE -
* MAPFRE. Todos los derechos reservados.</li>
* </ul>
*
* @author BuildSoft.
* @version 1.0, Thu Jul 31 10:21:30 COT 2014
* @since BuildErp 1.0
*/
public enum ColaDisponibleType {

	 /** El PREFERENCIAL. */
	 PREFERENCIAL(1L,"PREFERENCIAL"),
	 
 	/** El REGULAR. */
	 REGULAR(2L,"REGULAR"),
	 
	 /** El PESADO. */
	 PESADO(3L,"PESADO"),
	 
	 /** El NOCTURNO. */
	 NOCTURNO(4L,"NOCTURNO");
 	
	/** La Constante LOO_KUP_MAP. */
	private static final Map<Long, ColaDisponibleType> LOO_KUP_MAP = new HashMap<>();
	
	static {
		for (var s : EnumSet.allOf(ColaDisponibleType.class)) {
			LOO_KUP_MAP.put(s.getKey(), s);
		}
	}

	/** El key. */
	private Long key;
	
	/** El value. */
	private String value;

	/**
	 * Instancia un nuevo accion type.
	 *
	 * @param key el key
	 * @param value el value
	 */
	private ColaDisponibleType(Long key, String value) {
		this.key = key;
		this.value = value;
	}
	
	/**
	 * Gets the.
	 *
	 * @param key el key
	 * @return the accion type
	 */
	public static ColaDisponibleType get(Long key) {
		return LOO_KUP_MAP.get(key);
	}

	/**
	 * Obtiene key.
	 *
	 * @return key
	 */
	public Long getKey() {
		return key;
	}

	/**
	 * Obtiene value.
	 *
	 * @return value
	 */
	public String getValue() {
		return value;
	}
	
}