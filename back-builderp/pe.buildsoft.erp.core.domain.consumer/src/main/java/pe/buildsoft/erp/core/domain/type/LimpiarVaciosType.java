package pe.buildsoft.erp.core.domain.type;

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
public enum LimpiarVaciosType {

	/** La trim derecha. */
	TRIM_DERECHA("D"),

	/** La trim izquierda. */
	TRIM_IZQUIERDA("I"),
	
	/** La trim. */
	TRIM("A");

	/** La key. */
	private String key;
	
	/** La Constante LOO_KUP_MAP. */
	private static final Map<String, LimpiarVaciosType> LOO_KUP_MAP = new HashMap<>();
	
	static {
		for (var s : EnumSet.allOf(LimpiarVaciosType.class)) {
			LOO_KUP_MAP.put(s.getKey(), s);
		}
	}

	/**
	 * Instancia un nuevo redondear.
	 *
	 * @param key el key
	 */
	private LimpiarVaciosType(String key) {
		this.key = key;
	}

	/**
	 * Obtiene key.
	 *
	 * @return key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Obtiene loo kup map.
	 *
	 * @return loo kup map
	 */
	public static Map<String, LimpiarVaciosType> getLooKupMap() {
		return LOO_KUP_MAP;
	}
	
}
