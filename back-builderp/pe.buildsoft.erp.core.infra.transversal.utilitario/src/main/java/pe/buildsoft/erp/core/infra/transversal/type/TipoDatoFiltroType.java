package pe.buildsoft.erp.core.infra.transversal.type;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
* La Enum ColaDisponibleType.
* <ul>
* <li>Copyright 2017 MAPFRE -
* MAPFRE. Todos los derechos reservados.</li>
* </ul>
*
* @author BuildSoft.
* @version 1.0, Thu Jul 31 10:21:30 COT 2014
* @since BuildErp 1.0
*/
public enum TipoDatoFiltroType {
//tipo Dato filtro = {1=Cadena,2=Numerico,3=Fecha,4=Hora}

	 /** El CADENA. */
	 CADENA("1","CADENA"),
	 
 	/** El NUMERICO. */
	 NUMERICO("2","NUMERICO"),
	 
	 /** El FECHA. */
	 FECHA("3","FECHA"),
	 
	 /** El HORA. */
	 HORA("4","HORA");
 	
	/** La Constante LOO_KUP_MAP. */
	private static final Map<String, TipoDatoFiltroType> LOO_KUP_MAP = new HashMap<>();
	
	static {
		for (var s : EnumSet.allOf(TipoDatoFiltroType.class)) {
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
	private TipoDatoFiltroType(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	/**
	 * Gets the.
	 *
	 * @param key el key
	 * @return the accion type
	 */
	public static TipoDatoFiltroType get(String key) {
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