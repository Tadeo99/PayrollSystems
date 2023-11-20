package pe.buildsoft.erp.core.domain.type;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Enum CodigoAreaDroolsType.
 *
 * @author ndavilal
 * @version 1.0, Thu Jul 31 10:21:30 COT 2017
 * @since SIAA-CORE 2.1
 */
public enum TipoErrorDroolsType {

	/** La ER r_101. */
	SISTEMA("Sistema"),

	/** La ER r_102. */
	SINTAXIS("Sintaxis");

	/** La Constante LOO_KUP_MAP. */
	private static final Map<String, TipoErrorDroolsType> LOO_KUP_MAP = new HashMap<>();

	static {
		for (var s : EnumSet.allOf(TipoErrorDroolsType.class)) {
			LOO_KUP_MAP.put(s.value, s);
		}
	}

	/** El key. */
	private String value;

	/**
	 * Instancia un nuevo orden registro type.
	 *
	 * @param value el value
	 */
	private TipoErrorDroolsType(String value) {
		this.value = value;
	}

	/**
	 * Gets the.
	 *
	 * @param key el key
	 * @return the orden registro type
	 */
	public static TipoErrorDroolsType get(String key) {
		return LOO_KUP_MAP.get(key);
	}

	/**
	 * Obtiene value.
	 *
	 * @return value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Obtiene loo kup map.
	 *
	 * @return loo kup map
	 */
	public static Map<String, TipoErrorDroolsType> getLooKupMap() {
		return LOO_KUP_MAP;
	}
}
