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
public enum CodigoAreaDroolsType {

	/** La ER r_101. */
	ERR_101("ERR 101"),

	/** La ER r_102. */
	ERR_102("ERR 102"),

	/** La ER r_103. */
	ERR_103("ERR 103"),

	/** La ER r_104. */
	ERR_104("ERR 104"),

	/** La ER r_105. */
	ERR_105("ERR 105"),

	ERR_EXP_001("ERR EXP 001"),

	ERR_107("ERR 107"),

	ERR_001("ERR 001");

	/** La Constante LOO_KUP_MAP. */
	private static final Map<String, CodigoAreaDroolsType> LOO_KUP_MAP = new HashMap<>();

	static {
		for (var s : EnumSet.allOf(CodigoAreaDroolsType.class)) {
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
	private CodigoAreaDroolsType(String value) {
		this.value = value;
	}

	/**
	 * Gets the.
	 *
	 * @param key el key
	 * @return the orden registro type
	 */
	public static CodigoAreaDroolsType get(String key) {
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
	public static Map<String, CodigoAreaDroolsType> getLooKupMap() {
		return LOO_KUP_MAP;
	}
}
