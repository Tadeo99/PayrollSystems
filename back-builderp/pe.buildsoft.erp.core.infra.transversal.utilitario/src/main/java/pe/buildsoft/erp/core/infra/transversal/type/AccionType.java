package pe.buildsoft.erp.core.infra.transversal.type;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
* La Enum AccionType.
* <ul>
* <li>Copyright 2014 MAPFRE -
* MAPFRE. Todos los derechos reservados.</li>
* </ul>
*
* @author BuildSoft.
* @version 1.0, Thu Jul 31 10:21:30 COT 2014
* @since BuildErp 1.0
*/
public enum AccionType {

    /** El CREAR. */
 	CREAR("C","Crear"),
 	
	 /** El MODIFICAR. */
	 MODIFICAR("M","Modificar"),
	 
 	/** EL FIND_BY_ID. */
 	FIND_BY_ID("BYID","Buscar By Id"),
 	
 	/** EL ELIMINAR. */
 	ELIMINAR("E","Eliminar"),
 	
 	/** EL FIND_BY_NOMBRE. */
 	FIND_BY_NOMBRE("BYNOM","Buscar By Nombre"),
 	
 	ACTIVAR("A","Activar"),
 	
 	/** EL FIND_BY_CODIGO. */
 	FIND_BY_CODIGO("BYCOD","Buscar By Codigo");
 	
	/** La Constante LOO_KUP_MAP. */
	private static final Map<String, AccionType> LOO_KUP_MAP = new HashMap<>();
	
	static {
		for (AccionType s : EnumSet.allOf(AccionType.class)) {
			LOO_KUP_MAP.put(s.getKey(), s);
		}
	}

	/** El key. */
	private String key;
	
	/** El value. */
	private String value;

	/**
	 * Instancia un nuevo accion type.
	 *
	 * @param key el key
	 * @param value el value
	 */
	private AccionType(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	/**
	 * Gets the.
	 *
	 * @param key el key
	 * @return the accion type
	 */
	public static AccionType get(String key) {
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
