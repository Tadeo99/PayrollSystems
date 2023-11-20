package pe.buildsoft.erp.core.infra.transversal.type;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

 /**
 * La Enum MarcaInhType.
 * <ul>
 * <li>Copyright 2015 MAPFRE -
 * MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Fri Apr 10 09:37:34 COT 2015
 * @since BuildErp 1.0
 */
public enum MarcaRangoType {

    /** El SI. */
 	SI("S" , "marcaInh.si"),
	
    /** El NO. */
 	NO("N" , "marcaInh.no");
	
	/** La Constante LOO_KUP_MAP. */
	private static final Map<String, MarcaRangoType> LOO_KUP_MAP = new HashMap<>();
	
	static {
		for (var s : EnumSet.allOf(MarcaRangoType.class)) {
			LOO_KUP_MAP.put(s.getKey(), s);
		}
	}

	/** El key. */
	private String key;
	
	/** El value. */
	private String value;

	/**
	 * Instancia un nuevo marca inh type.
	 *
	 * @param key el key
	 * @param value el value
	 */
	private MarcaRangoType(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	/**
	 * Gets the.
	 *
	 * @param key el key
	 * @return the marca inh type
	 */
	public static MarcaRangoType get(String key) {
		return LOO_KUP_MAP.get(key);
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
	 * Obtiene value.
	 *
	 * @return value
	 */
	public String getValue() {
		return value;
	}
	
}
