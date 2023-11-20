package  pe.buildsoft.erp.core.infra.transversal.type;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

 /**
 * La Enum CampoFijoType.
 * <ul>
 * <li>Copyright 2015 MAPFRE -
 * MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Wed Aug 26 16:11:23 COT 2015
 * @since BuildErp 1.0
 */
public enum CampoFijoType {

    /** El NUMERO_LOTE. */
 	NUMERO_LOTE(1L , "campoFijo.numero_lote"),
	
    /** El FECHA_LOTE. */
 	FECHA_LOTE(2L , "campoFijo.fecha_lote"),
	
    /** El NUMERO_RIESGO. */
 	NUMERO_RIESGO(3L , "campoFijo.numero_riesgo"),
	
    /** El NUMERO_SECUENCIAL. */
 	NUMERO_SECUENCIAL(4L , "campoFijo.numero_secuencial"),
	
    /** El CAMPO_ASOCIADO. */
 	CAMPO_ASOCIADO(5L , "campoFijo.campo_asociado"),
 	
 	/** El CAMPO_ASOCIADO_JOIN. */
 	CAMPO_ASOCIADO_JOIN(6L , "campoFijo.campo_asociado_join"),

	 /** La campo suma. */
 	CAMPO_SUMA(7L , "campoFijo.sumatoria"),
	 
 	/** La campo promedio. */
 	CAMPO_PROMEDIO(8L , "campoFijo.promedio"),
	 
 	/** La campo contador. */
 	CAMPO_CONTADOR(9L , "campoFijo.contador"),
 	
 	/** La campo contador. */
 	CAMPO_ASOCIAR_CANAL(10L , "campoFijo.asociar.canal"),
 	
 	CAMPO_UUID(11L , "campoFijo.UUID");
 	
	
	/** La Constante LOO_KUP_MAP. */
	private static final Map<Long, CampoFijoType> LOO_KUP_MAP = new HashMap<>();
	
	static {
		for (var s : EnumSet.allOf(CampoFijoType.class)) {
			LOO_KUP_MAP.put(s.getKey(), s);
		}
	}

	/** El key. */
	private Long key;
	
	/** El value. */
	private String value;

	/**
	 * Instancia un nuevo campo fijo type.
	 *
	 * @param key el key
	 * @param value el value
	 */
	private CampoFijoType(Long key, String value) {
		this.key = key;
		this.value = value;
	}
	
	/**
	 * Gets the.
	 *
	 * @param key el key
	 * @return the campo fijo type
	 */
	public static CampoFijoType get(Long key) {
		return LOO_KUP_MAP.get(key);
	}

	/**
	 * Obtiene key.
	 *
	 * @return key
	 */
	public Long getKey() {
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
