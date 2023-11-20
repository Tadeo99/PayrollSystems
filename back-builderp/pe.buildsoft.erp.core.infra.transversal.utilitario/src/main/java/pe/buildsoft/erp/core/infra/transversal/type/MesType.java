package pe.buildsoft.erp.core.infra.transversal.type;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * La Enum MesType.
 * <ul>
 * <li>Copyright 2014 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Thu Jul 31 10:21:30 COT 2014
 * @since BuildErp 1.0
 */
public enum MesType {

	/** El ENERO. */
	ENERO(1L, "enero"),

	/** El FEBRERO. */
	FEBRERO(2L, "febrero"),

	/** El MARZO. */
	MARZO(3L, "marzo"),

	/** El ABRIL. */
	ABRIL(4L, "abril"),

	/** El MAYO. */
	MAYO(5L, "mayo"),

	/** El JUNIO. */
	JUNIO(6L, "junio"),

	/** El JULIO. */
	JULIO(7L, "julio"),

	/** El AGOSTO. */
	AGOSTO(8L, "agosto"),

	/** El SEPTIEMBRE. */
	SEPTIEMBRE(9L, "septiembre"),

	/** El OCTRUBRE. */
	OCTRUBRE(10L, "octubre"),

	/** El NOVIEMBRE. */
	NOVIEMBRE(11L, "noviembre"),

	/** El DICIEMBRE. */
	DICIEMBRE(12L, "diciembre");

	/** La Constante LOO_KUP_MAP. */
	private static final Map<Long, MesType> LOO_KUP_MAP = new HashMap<>();

	static {
		for (var s : EnumSet.allOf(MesType.class)) {
			LOO_KUP_MAP.put(s.getKey(), s);
		}
	}

	/** El key. */
	private Long key;

	/** El value. */
	private String value;

	/**
	 * Instancia un nuevo dia semana type.
	 *
	 * @param key   el key
	 * @param value el value
	 */
	private MesType(Long key, String value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * Gets the.
	 *
	 * @param key el key
	 * @return the dia semana type
	 */
	public static MesType get(Long key) {
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
