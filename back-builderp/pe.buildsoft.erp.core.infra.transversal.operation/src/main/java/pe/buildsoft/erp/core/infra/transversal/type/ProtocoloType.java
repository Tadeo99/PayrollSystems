package  pe.buildsoft.erp.core.infra.transversal.type;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

 /**
 * La Enum TipoProcesoType.
 * <ul>
 * <li>Copyright 2015 MAPFRE -
 * MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Thu Jul 30 15:10:21 COT 2015
 * @since BuildErp 1.0
 */
public enum ProtocoloType {

    /** PROTOCOLO FTP */
 	FTP("1" , "protocolo.ftp"),
	
    /** PROTOCOLO SFTP */
 	SFTP("2" , "protocolo.sftp");
	
	/** La Constante LOO_KUP_MAP. */
	private static final Map<String, ProtocoloType> LOO_KUP_MAP = new HashMap<String, ProtocoloType>();
	
	static {
		for (var s : EnumSet.allOf(ProtocoloType.class)) {
			LOO_KUP_MAP.put(s.getKey(), s);
		}
	}

	/** El key. */
	private String key;
	
	/** El value. */
	private String value;

	/**
	 * Instancia un nuevo protocolo type.
	 *
	 * @param key el key
	 * @param value el value
	 */
	private ProtocoloType(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	/**
	 * Gets the.
	 *
	 * @param key el key
	 * @return the tipo proceso type
	 */
	public static ProtocoloType get(String key) {
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
