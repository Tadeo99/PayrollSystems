package pe.buildsoft.erp.core.infra.transversal.type;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
* La Enum TipoDetalleCorreoType.
* <ul>
* <li>Copyright 2014 MAPFRE -
* MAPFRE. Todos los derechos reservados.</li>
* </ul>
*
* @author BuildSoft.
* @version 1.0, Wed Jul 30 17:20:50 COT 2014
* @since BuildErp 1.0
*/
public enum TipoDetalleCorreoType {

	 /** El DESTINATARIO. */
	 DESTINATARIO("D","Destinatario"),
	 
    /** El CON_COPIA. */
 	CON_COPIA("CC","CC"),
 	
 	 /** El CON_COPIA_OCULTA. */
	 CON_COPIA_OCULTA("CCO","CCO");

 	
	/** La Constante LOO_KUP_MAP. */
	private static final Map<String, TipoDetalleCorreoType> LOO_KUP_MAP = new HashMap<>();
	
	static {
		for (var s : EnumSet.allOf(TipoDetalleCorreoType.class)) {
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
	private TipoDetalleCorreoType(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	/**
	 * Gets the.
	 *
	 * @param key el key
	 * @return the accion type
	 */
	public static TipoDetalleCorreoType get(String key) {
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
