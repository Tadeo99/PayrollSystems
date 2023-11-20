package pe.buildsoft.erp.core.infra.transversal.type;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * <ul>
 * <li>Copyright 2012 BUILD SOFT S.A.C - BS. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Enum TipoUbigeoType.
 *
 * @author ndavilal.
 * @version 1.0 , 20/05/2012
 * @since SIAA 2.0
 */
public enum TipoUbigeoType {

     /** El DEPARTAMENTO. */
     DEPARTAMENTO("DE","Departamento"),
 	
	 /** La PROVINCIA. */
 	PROVINCIA("PR","Provincia"),
	 
 	 /** El DISTRITO. */
 	 DISTRITO("DI","Distrito");
     
	/** La Constante LOO_KUP_MAP. */
	private static final Map<String, TipoUbigeoType> LOO_KUP_MAP = new HashMap<>();
	
	static {
		for (var s : EnumSet.allOf(TipoUbigeoType.class)) {
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
	private TipoUbigeoType(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	/**
	 * Gets the.
	 *
	 * @param key el key
	 * @return the accion type
	 */
	public static TipoUbigeoType get(String key) {
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
