package pe.buildsoft.erp.core.domain.type;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

 /**
 * La Enum TipoProcesamientoType.
 * <ul>
 * <li>Copyright 2014 MAPFRE -
 * MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Thu Jul 31 10:21:30 COT 2014
 * @since BuildErp 1.0
 */
public enum TipoProcesamientoType {

    /** El BATCH. */
 	BATCH('B' , "tipoProcesaiento.batch"),
	
    /** El EN_LINEA. */
 	EN_LINEA('L' , "tipoProcesaiento.en_linea");
	
	/** La Constante LOO_KUP_MAP. */
	private static final Map<Character, TipoProcesamientoType> LOO_KUP_MAP = new HashMap<Character, TipoProcesamientoType>();
	
	static {
		for (var s : EnumSet.allOf(TipoProcesamientoType.class)) {
			LOO_KUP_MAP.put(s.getKey(), s);
		}
	}

	/** El key. */
	private Character key;
	
	/** El value. */
	private String value;

	/**
	 * Instancia un nuevo tipo procesamiento type.
	 *
	 * @param key el key
	 * @param value el value
	 */
	private TipoProcesamientoType(Character key, String value) {
		this.key = key;
		this.value = value;
	}
	
	/**
	 * Gets the.
	 *
	 * @param key el key
	 * @return the tipo procesamiento type
	 */
	public static TipoProcesamientoType get(Character key) {
		return LOO_KUP_MAP.get(key);
	}

	/**
	 * Obtiene key.
	 *
	 * @return key
	 */
	public Character getKey() {
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
