package pe.buildsoft.erp.core.infra.transversal.type;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

 /**
 * La Enum DiaSemanaType.
 * <ul>
 * <li>Copyright 2014 MAPFRE -
 * MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Thu Jul 31 10:21:30 COT 2014
 * @since BuildErp 1.0
 */
public enum DiaSemanaType {

    /** El LUNES. */
 	LUNES("LUN" , "diaSemana.lunes"),
	
    /** El MARTES. */
 	MARTES("MAR" , "diaSemana.martes"),
	
    /** El MIERCOLES. */
 	MIERCOLES("MIE" , "diaSemana.miercoles"),
	
    /** El JUEVES. */
 	JUEVES("JUE" , "diaSemana.jueves"),
	
    /** El VIERNES. */
 	VIERNES("VIE" , "diaSemana.viernes"),
 	
 	/** El SABADO. */
 	SABADO("SAB","diaSemana.sabado"),
	
    /** El DOMINGO. */
 	DOMINGO("DOM" , "diaSemana.domingo");
	
	/** La Constante LOO_KUP_MAP. */
	private static final Map<String, DiaSemanaType> LOO_KUP_MAP = new HashMap<>();
	
	static {
		for (var s : EnumSet.allOf(DiaSemanaType.class)) {
			LOO_KUP_MAP.put(s.getKey(), s);
		}
	}

	/** El key. */
	private String key;
	
	/** El value. */
	private String value;

	/**
	 * Instancia un nuevo dia semana type.
	 *
	 * @param key el key
	 * @param value el value
	 */
	private DiaSemanaType(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	/**
	 * Gets the.
	 *
	 * @param key el key
	 * @return the dia semana type
	 */
	public static DiaSemanaType get(String key) {
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
