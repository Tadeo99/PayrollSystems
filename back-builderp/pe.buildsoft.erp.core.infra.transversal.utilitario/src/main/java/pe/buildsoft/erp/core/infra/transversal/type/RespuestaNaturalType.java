package pe.buildsoft.erp.core.infra.transversal.type;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

 /**
 * La Enum RespuestaNaturalType.
 * <ul>
 * <li>Copyright 2014 MAPFRE -
 * MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Thu Jul 31 10:21:30 COT 2014
 * @since BuildErp 1.0
 */
public enum RespuestaNaturalType {

    /** El SI. */
 	SI('S' , "respuestaNatural.si"),
    /** El NO. */
  	NO('N' , "respuestaNatural.no");
	
	/** La Constante LOO_KUP_MAP. */
	private static final Map<Character, RespuestaNaturalType> LOO_KUP_MAP = new HashMap<>();
	
	static {
		for (var s : EnumSet.allOf(RespuestaNaturalType.class)) {
			LOO_KUP_MAP.put(s.getKey(), s);
		}
	}

	/** El key. */
	private Character key;
	
	/** El value. */
	private String value;

	/**
	 * Instancia un nuevo respuesta natural type.
	 *
	 * @param key el key
	 * @param value el value
	 */
	private RespuestaNaturalType(Character key, String value) {
		this.key = key;
		this.value = value;
	}
	
	/**
	 * Gets the.
	 *
	 * @param key el key
	 * @return the respuesta natural type
	 */
	public static RespuestaNaturalType get(Character key) {
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
