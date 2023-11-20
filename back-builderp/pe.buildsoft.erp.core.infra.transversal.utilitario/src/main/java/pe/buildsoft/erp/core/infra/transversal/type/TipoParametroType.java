package  pe.buildsoft.erp.core.infra.transversal.type;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

 /**
 * La Enum TipoTramaType.
 * <ul>
 * <li>Copyright 2015 MAPFRE -
 * MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Fri Aug 07 14:41:15 COT 2015
 * @since BuildErp 1.0
 */
public enum TipoParametroType {

    /** El NUMERO_REFERENCIA. */
 	NUMERO_REFERENCIA("1" , "tipoParametro.numeroReferencia"),
	
    /** El NUMERO_POLIZA. */
 	NUMERO_POLIZA("2" , "tipoParametro.numeroPoliza"),
 	
 	//Inicio BuildSoft Configuracion de caja raiz
	 /** El NUMERO_LOTE. */
 	NUMERO_LOTE("3" , "tipoParametro.numeroLote");
	//Fin BuildSoft Configuracion de caja raiz
	
	/** La Constante LOO_KUP_MAP. */
	private static final Map<String, TipoParametroType> LOO_KUP_MAP = new HashMap<>();
	
	static {
		for (var s : EnumSet.allOf(TipoParametroType.class)) {
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
	private TipoParametroType(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	/**
	 * Gets the.
	 *
	 * @param key el key
	 * @return the tipo trama type
	 */
	public static TipoParametroType get(String key) {
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
