package pe.buildsoft.erp.core.infra.transversal.type;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

 /**
 * La Enum TipoOpcionType.
 * <ul>
 * <li>Copyright 2014 MAPFRE -
 * MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Thu Jul 31 10:21:30 COT 2014
 * @since BuildErp 1.0
 */
public enum TipoOpcionType {

    /** El MANTENIMIENTO. */
 	MANTENIMIENTO("M" , "tipoOpcion.mantenimiento"),
	
    /** El REPORTE. */
 	REPORTE("R" , "tipoOpcion.reporte"),
	
    /** El PROCESO. */
 	PROCESO("P" , "tipoOpcion.proceso");
	
	/** La Constante LOO_KUP_MAP. */
	private static final Map<String, TipoOpcionType> LOO_KUP_MAP = new HashMap<>();
	
	static {
		for (var s : EnumSet.allOf(TipoOpcionType.class)) {
			LOO_KUP_MAP.put(s.getKey(), s);
		}
	}

	/** El key. */
	private String key;
	
	/** El value. */
	private String value;

	/**
	 * Instancia un nuevo tipo opcion type.
	 *
	 * @param key el key
	 * @param value el value
	 */
	private TipoOpcionType(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	/**
	 * Gets the.
	 *
	 * @param key el key
	 * @return the tipo opcion type
	 */
	public static TipoOpcionType get(String key) {
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
