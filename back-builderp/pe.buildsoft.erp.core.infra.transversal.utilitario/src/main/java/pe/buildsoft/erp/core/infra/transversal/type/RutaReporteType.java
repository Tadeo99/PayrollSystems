package pe.buildsoft.erp.core.infra.transversal.type;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;


/**
 * <ul>
 * <li>Copyright 2017 ndavilal. Todos los derechos reservados.</li>
 * </ul> 
 * 
 * La Enum RutaReporteType.
 *
 * @author ndavilal
 * @version 1.0 , 06/04/2015
 * @since SIAA-CORE 2.1
 */
public enum  RutaReporteType {

	/** EL JASPER. */
	JASPER("jasper","jasper"),
	/** EL img. */
	IMG("images","images"),
	/** LA CABECERA. */
	CABECERA("cabecera","cabecera"),
	
	 /** El MATRICULA. */
 	MATRICULA("matricula","Matricula"),
 	
	 /** El NOTA. */
	 NOTA("nota","Nota"),
	 
	 /** El PAGO. */
	 PAGO("pago","Pago"),
	 
	 /** La ADMISION. */
 	ADMISION("admision","Admision");
 	
	
	/** La Constante LOO_KUP_MAP. */
	private static final Map<String, RutaReporteType> LOO_KUP_MAP = new HashMap<>();

	static {
		for (var s : EnumSet.allOf(RutaReporteType.class)) {
			LOO_KUP_MAP.put(s.getKey(), s);
		}
	}	

	/** El key. */
	private String key;
	
	/** El value. */
	private String value;

	
	/**
	 * Instancia un nuevo ruta reporte type.
	 *
	 * @param key el key
	 * @param value el value
	 */
	private RutaReporteType(String key,String value) {
		this.key = key;
		this.value = value;
	}
	
	/**
	 * Gets the.
	 *
	 * @param key el key
	 * @return the ruta reporte type
	 */
	public static RutaReporteType get(String key) {
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
