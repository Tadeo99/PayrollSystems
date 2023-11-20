package pe.buildsoft.erp.core.infra.transversal.state;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

 /**
 * La Enum EstadoSolicitudEjecucionEstate.
 * <ul>
 * <li>Copyright 2014 MAPFRE -
 * MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Thu Jul 31 10:21:30 COT 2014
 * @since BuildErp 1.0
 */
public enum EstadoSolicitudEjecucionEstate {

    /** El PENDIENTE. */
 	PENDIENTE("P" , "estadoSolicitudEjecucion.pendiente"),
	
    /** El EN_PROCESO. */
 	EN_PROCESO("E" , "estadoSolicitudEjecucion.en_proceso"),
	
    /** El TERMINADO. */
 	TERMINADO("T" , "estadoSolicitudEjecucion.terminado"),
	
    /** El CANCELADO. */
 	CANCELADO("C" , "estadoSolicitudEjecucion.cancelado"),
	
    /** El FALLO_EJECUFION. */
 	FALLO_EJECUCION("F" , "estadoSolicitudEjecucion.fallo_ejecufion"),
 	
 	/** El EN_COLA. */
 	EN_COLA("Q" , "estadoSolicitudEjecucion.en_cola");
	
	/** La Constante LOO_KUP_MAP. */
	private static final Map<String, EstadoSolicitudEjecucionEstate> LOO_KUP_MAP = new HashMap<String, EstadoSolicitudEjecucionEstate>();
	
	static {
		for (var s : EnumSet.allOf(EstadoSolicitudEjecucionEstate.class)) {
			LOO_KUP_MAP.put(s.getKey(), s);
		}
	}

	/** El key. */
	private String key;
	
	/** El value. */
	private String value;

	/**
	 * Instancia un nuevo estado solicitud ejecucion estate.
	 *
	 * @param key el key
	 * @param value el value
	 */
	private EstadoSolicitudEjecucionEstate(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	/**
	 * Gets the.
	 *
	 * @param key el key
	 * @return the estado solicitud ejecucion estate
	 */
	public static EstadoSolicitudEjecucionEstate get(String key) {
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
