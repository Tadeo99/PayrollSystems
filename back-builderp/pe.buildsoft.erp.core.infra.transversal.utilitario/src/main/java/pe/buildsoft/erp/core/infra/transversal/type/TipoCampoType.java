package pe.buildsoft.erp.core.infra.transversal.type;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * La Enum TipoCampoType.
 * <ul>
 * <li>Copyright 2015 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Thu Jul 30 15:10:21 COT 2015
 * @since BuildErp 1.0
 */
public enum TipoCampoType {

	/** El TEXTO. */
	TEXTO("1", "tipoCampo.texto"),

	/** El NUMERICO. */
	NUMERICO("2", "tipoCampo.numerico"),

	/** El FECHA. */
	FECHA("3", "tipoCampo.fecha");

	/** La Constante LOO_KUP_MAP. */
	private static final Map<String, TipoCampoType> LOO_KUP_MAP = new HashMap<>();

	static {
		for (var s : EnumSet.allOf(TipoCampoType.class)) {
			LOO_KUP_MAP.put(s.getKey(), s);
		}
	}

	/** El key. */
	private String key;

	/** El value. */
	private String value;

	/**
	 * Instancia un nuevo tipo campo type.
	 *
	 * @param key   el key
	 * @param value el value
	 */
	private TipoCampoType(String key, String value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * Gets the.
	 *
	 * @param key el key
	 * @return the tipo campo type
	 */
	public static TipoCampoType get(String key) {
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
