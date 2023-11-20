package  pe.buildsoft.erp.core.infra.transversal.type;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

 /**
 * La Enum TipoVariableType.
 * <ul>
 * <li>Copyright 2015 MAPFRE -
 * MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Fri Aug 07 14:41:15 COT 2015
 * @since BuildErp 1.0
 */
public enum TipoVariableType {

    /** El TRAMA_SOLICITUD. */
 	CONSTANTE("C" , "tipoVariable.constante"),
	
    /** El TRAMA_RESPUESTA_ERROR. */
 	PARAMETRO("P" , "tipoVariable.parametro"),
	
    /** El TRAMA_RESPUESTA_SOLICITUD. */
 	FECHA("F" , "tipoVariable.fecha"),
 	
 	/** El FECHA_CALCULADA */
 	FECHA_CALCULADA("A" , "tipoVariable.fechaCalculada");
	
	/** La Constante LOO_KUP_MAP. */
	private static final Map<String, TipoVariableType> LOO_KUP_MAP = new HashMap<>();
	
	static {
		for (var s : EnumSet.allOf(TipoVariableType.class)) {
			LOO_KUP_MAP.put(s.getKey(), s);
		}
	}

	/** El key. */
	private String key;
	
	/** El value. */
	private String value;

	/**
	 * Instancia un nuevo tipo trama type.
	 *
	 * @param key el key
	 * @param value el value
	 */
	private TipoVariableType(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	/**
	 * Gets the.
	 *
	 * @param key el key
	 * @return the tipo trama type
	 */
	public static TipoVariableType get(String key) {
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
