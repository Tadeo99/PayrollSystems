package pe.buildsoft.erp.core.infra.transversal.type;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * <ul>
 * <li>Copyright 2012 BUILD SOFT S.A.C - BS. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Enum TipoComponenteType.
 *
 * @author ndavilal.
 * @version 1.0 , 31/08/2013
 * @since SIAA 2.0
 */
public enum TipoComponenteType {

	// Los datos se encuentrar registrados en item
	/** EL LABEL. */
	LABEL(1L, "Label"),

	/** El Input. */
	INPUT(2L, "Input"),

	/** El BUTTON. */
	BUTTON(3L, "Button");

	/** La Constante looKup. */
	private static final Map<Long, TipoComponenteType> LOO_KUP_MAP = new HashMap<>();

	static {
		for (var s : EnumSet.allOf(TipoComponenteType.class)) {
			LOO_KUP_MAP.put(s.getKey(), s);
		}
	}

	/** El key. */
	private Long key;

	/** El value. */
	private String value;

	/**
	 * Instancia un nuevo tipo asinatura type.
	 *
	 * @param key   el key
	 * @param value el value
	 */
	private TipoComponenteType(Long key, String value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * Gets the.
	 *
	 * @param key el key
	 * @return the tipo asinatura type
	 */
	public static TipoComponenteType get(Long key) {
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
	 * Obtiene nombre.
	 *
	 * @return nombre
	 */
	public String getValue() {
		return value;
	}

}
