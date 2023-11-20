package  pe.buildsoft.erp.core.infra.transversal.type;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

 /**
 * La Enum TipoArchivoProcesarType.
 * <ul>
 * <li>Copyright 2015 MAPFRE -
 * MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Thu Jul 30 15:10:21 COT 2015
 * @since BuildErp 1.0
 */
public enum TipoArchivoProcesarType {

    /** El EXCEL. */
 	EXCEL_XLS("1" , "tipoArchivoProcesar.excel.xls","xls"),
 	
 	 /** El EXCEL_XLSX. */
 	EXCEL_XLSX("4" , "tipoArchivoProcesar.excel.xlsx","xlsx"),
	
    /** El CVS. */
 	CSV("2" , "tipoArchivoProcesar.csv","csv"),
	
    /** El TXT. */
 	TXT("3" , "tipoArchivoProcesar.txt","txt"),
 	
 	 /** El TXT. */
 	PDF("5" , "tipoArchivoProcesar.pdf","pdf");
	
	/** La Constante LOO_KUP_MAP. */
	private static final Map<String, TipoArchivoProcesarType> LOO_KUP_MAP = new HashMap<>();
	
	static {
		for (var s : EnumSet.allOf(TipoArchivoProcesarType.class)) {
			LOO_KUP_MAP.put(s.getKey(), s);
		}
	}

	/** El key. */
	private String key;
	
	/** El value. */
	private String value;
	
	private String extension;

	/**
	 * Instancia un nuevo tipo archivo procesar type.
	 *
	 * @param key el key
	 * @param value el value
	 */
	private TipoArchivoProcesarType(String key, String value,String extension) {
		this.key = key;
		this.value = value;
		this.extension = extension;
	}
	
	/**
	 * Gets the.
	 *
	 * @param key el key
	 * @return the tipo archivo procesar type
	 */
	public static TipoArchivoProcesarType get(String key) {
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
	
	public String getExtension() {
		return extension;
	}
	
}
