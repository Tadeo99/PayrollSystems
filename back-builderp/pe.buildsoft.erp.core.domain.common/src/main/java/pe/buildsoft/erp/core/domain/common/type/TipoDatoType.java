package pe.buildsoft.erp.core.domain.common.type;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;


/**
 * <ul>
 * <li>Copyright 2012 BUILD SOFT S.A.C - BS. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Enum TipoDatoType.
 *
 * @author ndavilal.
 * @version 1.0 , 26/04/2019
 * @since BuildErp 1.0
 */
public enum TipoDatoType {
	
    /** EL NUMERICO. */
 	NUMERICO("N","NUMERICO"),
 	
 	/** El DATE. */
 	DATE("D","DATE"),
 	
	/** El TEXTO. */
 	TEXTO("T","TEXTO"),
 	
 	/** El BOOLEAN. */
 	BOOLEAN("B","BOOLEAN");
 	
	/** La Constante looKup. */
	private static final Map<String, TipoDatoType> LOO_KUP_MAP = new HashMap<>();
	
	static {
		for (var s : EnumSet.allOf(TipoDatoType.class)) {
			LOO_KUP_MAP.put(s.getKey(), s);
		}
	}

	/** El key. */
	private String key;
	
	/** El value. */
	private String value;

	/**
	 * Instancia un nuevo tipo asinatura type.
	 *
	 * @param key el key
	 * @param value el value
	 */
	private TipoDatoType(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	/**
	 * Gets the.
	 *
	 * @param key el key
	 * @return the tipo asinatura type
	 */
	public static TipoDatoType get(String key) {
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
	 * Obtiene nombre.
	 *
	 * @return nombre
	 */
	public String getValue() {
		return value;
	}
}
