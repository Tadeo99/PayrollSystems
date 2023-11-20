package pe.buildsoft.erp.core.domain.type;


import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
* La Enum NombreReporteType.
* <ul>
* <li>Copyright 2017 ndavilal -
* MAPFRE. Todos los derechos reservados.</li>
* </ul>
*
* @author ndavilal
* @version 1.0, Thu Jul 31 10:21:30 COT 2017
* @since SIAA-CORE 2.1
*/
public enum PlanillaReporteType {

	 /** El JR_REP_FICHA_MATRICULA_INDIVIDUAL. */
	JR_REP_PLANILLA_BOLETA_INDIVIDUAL("jrReportePlanillaBoletaIndividual.jasper","ReportePlanillaBoletaIndividual","jrReportePlanillaBoletaIndividual",""),
	
	JR_REP_PLANILLA_BOLETA_MASIVA("jrReportePlanillaBoletaMasiva.jasper","ReportePlanillaBoletaMasiva","jrReportePlanillaBoletaMasiva",""),
 	
	 /** El NULO. */
	 NULO("","","","")
	 ;
 	
	 
	/** La Constante LOO_KUP_MAP. */
	private static final Map<String, PlanillaReporteType> LOO_KUP_MAP = new HashMap<>();
	
	static {
		for (var s : EnumSet.allOf(PlanillaReporteType.class)) {
			LOO_KUP_MAP.put(s.getKey(), s);
		}
	}

	/** El key. */
	private String key;
	
	/** El value. */
	private String value;
	
	private String carperta;
	
	private String codigoReporte;

	
	/**
	 * Instancia un nuevo nombre reporte type.
	 *
	 * @param key el key
	 * @param value el value
	 * @param carperta el carperta
	 * @param codigoReporte el codigo reporte
	 */
	private PlanillaReporteType(String key, String value,String carperta,String codigoReporte) {
		this.key = key;
		this.value = value;
		this.carperta = carperta;
		this.codigoReporte = codigoReporte;
	}
	
	/**
	 * Gets the.
	 *
	 * @param key el key
	 * @return the accion type
	 */
	public static PlanillaReporteType get(String key) {
		if (LOO_KUP_MAP.containsKey(key)) {
			return LOO_KUP_MAP.get(key);
		} 
		return NULO;
		
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
	 * Obtiene carpeta.
	 *
	 * @return carpeta
	 */
	public String getCarpeta() {
		return carperta;
	}

	/**
	 * Obtiene codigo reporte.
	 *
	 * @return codigo reporte
	 */
	public String getCodigoReporte() {
		return codigoReporte;
	}	
}
