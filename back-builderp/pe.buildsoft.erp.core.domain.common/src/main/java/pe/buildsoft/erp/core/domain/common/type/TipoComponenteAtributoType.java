package pe.buildsoft.erp.core.domain.common.type;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;


/**
 * <ul>
 * <li>Copyright 2012 BUILD SOFT S.A.C - BS. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Enum TipoDatoType.
 *
 * @author ndavilal.
 * @version 1.0 , 26/04/2019
 * @since BuildErp 1.0
 */
public enum TipoComponenteAtributoType {
	
    /** EL INPUT. */
	INPUT("INPUT","INPUT"),
 	
 	/** El DATE. */
	TEXTAREA("TEXTAREA","TEXTAREA"),
 	
	/** El DATEPICKER. */
	DATEPICKER("DATEPICKER","DATEPICKER"),
 	
 	/** El RADIO_BUTTONS. */
	RADIO_BUTTONS("RADIO-BUTTONS","RADIO-BUTTONS"),
	
	/** El SELECT. */
	SELECT("SELECT","SELECT"),
	
	/** El SLIDE-TOGGLE. */
	SLIDE_TOGGLE("SLIDE-TOGGLE","SLIDE-TOGGLE"),
	
	/** El CHECKBOX. */
	CHECKBOX("CHECKBOX","CHECKBOX"),
	
	/** El SELECT-MULTIPLE. */
	SELECT_MULTIPLE("SELECT-MULTIPLE","SELECT-MULTIPLE"),
	
	/** El MODAL. */
	MODAL("MODAL","MODAL"),
	
	/** El MODAL-MULTIPLE. */
	MODAL_MULTIPLE("MODAL-MULTIPLE","MODAL-MULTIPLE");
	
	
	
	/** La Constante looKup. */
	private static final Map<String, TipoComponenteAtributoType> LOO_KUP_MAP = new HashMap<>();
	
	static {
		for (var s : EnumSet.allOf(TipoComponenteAtributoType.class)) {
			LOO_KUP_MAP.put(s.getKey(), s);
		}
	}

	/** El key. */
	private String key;
	
	/** El value. */
	private String value;

	/**
	 * Instancia un nuevo tipo asinatura type.
	 *
	 * @param key el key
	 * @param value el value
	 */
	private TipoComponenteAtributoType(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	/**
	 * Gets the.
	 *
	 * @param key el key
	 * @return the tipo asinatura type
	 */
	public static TipoComponenteAtributoType get(String key) {
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
	 * Obtiene nombre.
	 *
	 * @return nombre
	 */
	public String getValue() {
		return value;
	}
}
