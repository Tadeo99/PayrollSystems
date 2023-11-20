package pe.buildsoft.erp.core.domain.type;

import java.math.RoundingMode;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Enum Redondear.
 *
* @author ndavilal
* @version 1.0, Thu Jul 31 10:21:30 COT 2017
* @since SIAA-CORE 2.1
 */
public enum RedondearType {

	/** La exceso. */
	EXCESO("E", RoundingMode.UP),

	/** La defecto. */
	DEFECTO("D", RoundingMode.DOWN);

	/** La key. */
	private String key;
	/** La value. */
	private RoundingMode value;
	
	private static final Map<String, RedondearType> LOO_KUP_MAP = new HashMap<String, RedondearType>();
	
	static {
		for (var s : EnumSet.allOf(RedondearType.class)) {
			LOO_KUP_MAP.put(s.getKey(), s);
		}
	}

	/**
	 * Instancia un nuevo redondear.
	 *
	 * @param key el key
	 * @param value            el value
	 */
	private RedondearType(String key, RoundingMode value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * Obtiene value.
	 *
	 * @return value
	 */
	public RoundingMode getValue() {
		return value;
	}

	/**
	 * Obtiene key.
	 *
	 * @return key
	 */
	public String getKey() {
		return key;
	}

	public static Map<String, RedondearType> getLooKupMap() {
		return LOO_KUP_MAP;
	}
	
}
