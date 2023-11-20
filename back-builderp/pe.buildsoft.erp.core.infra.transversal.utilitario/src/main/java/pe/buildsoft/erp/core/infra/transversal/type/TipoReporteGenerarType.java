package pe.buildsoft.erp.core.infra.transversal.type;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;


/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul> 
 * 
 * La Enum TipoReporteGenerarType.
 *
 * @author BuildSoft.
 * @version 1.0 , 06/04/2015
 * @since BuildErp 1.0
 */
public enum  TipoReporteGenerarType {

	TXT("txt","tipoReporteGenerar.txt","text/html",true),
	

	NINGUNO("N/A","tipoReporteGenerar","application/octet-stream",true),
	
	/** EL PDF. */
	PDF("pdf","tipoReporteGenerar.pdf","application/pdf",true),
	
	/** EL HTML. */
	HTML("html","tipoReporteGenerar.html", "text/html",true),
	
	/** EL XLS. */
	XLS("xls","tipoReporteGenerar.xls","application/vnd.ms-excel",true),
	
	/** EL RTF. */
	RTF("rtf","tipoReporteGenerar.rtf","application/rtf",true),
	
	/** EL ODT. */
	ODT("odt","tipoReporteGenerar.odt","application/vnd.oasis.opendocument.text",true),
	
	/** EL ODS. */
	ODS("ods","tipoReporteGenerar.ods","application/vnd.oasis.opendocument.spreadsheet",true),
	
	/** EL DOCX. */
	DOCX("docx","tipoReporteGenerar.docx","application/vnd.openxmlformats-officedocument.wordprocessingml.document",true),
	
	/** EL PPTX. */
	PPTX("pptx","tipoReporteGenerar.pptx","application/vnd.openxmlformats-officedocument.presentationml.presentation",true),
	
	/** EL XLSX. */
	XLSX("xlsx","tipoReporteGenerar.xlsx","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",true),
	
	/*SWFACTORY INICIO 17-12-2015*/
	/** EL XLSX. */
	XLSX2("xlsx2","tipoReporteGenerar.xlsx","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",false),
	/*SWFACTORY FIN*/
	
	/** EL ZIP. */ 
	ZIP("zip","tipoReporteGenerar.zip","application/zip",false),
	
	CSV("csv","tipoReporteGenerar.csv","text/csv",false);
	
	/** La Constante LOO_KUP_MAP. */
	private static final Map<String, TipoReporteGenerarType> LOO_KUP_MAP = new HashMap<>();

	static {
		for (TipoReporteGenerarType s : EnumSet.allOf(TipoReporteGenerarType.class)) {
			LOO_KUP_MAP.put(s.getKey(), s);
		}
	}	

	/** El key. */
	private String key;
	
	/** El value. */
	private String value;
	
	/** La content type. */
	private String contentType;
	
	private boolean esMostrarCombo;

	
	/**
	 * Instancia un nuevo tipo reporte generar type.
	 *
	 * @param key el key
	 * @param value el value
	 * @param contentType el content type
	 */
	private TipoReporteGenerarType(String key,String value,String contentType,boolean esMostrarCombo) {
		this.key = key;
		this.value = value;
		this.contentType = contentType;
		this.esMostrarCombo = esMostrarCombo;
	}
	
	/**
	 * Gets the.
	 *
	 * @param key el key
	 * @return the tipo reporte generar type
	 */
	public static TipoReporteGenerarType get(String key) {
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

	/**
	 * Obtiene content type.
	 *
	 * @return content type
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * Establece el content type.
	 *
	 * @param contentType el new content type
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public boolean isEsMostrarCombo() {
		return esMostrarCombo;
	}
	
}
