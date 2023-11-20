package pe.buildsoft.erp.core.domain.type;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * La Enum NombreReporteType.
 * <ul>
 * <li>Copyright 2017 ndavilal - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Jul 31 10:21:30 COT 2017
 * @since SIAA-CORE 2.1
 */
public enum NombreReporteType {

	/** El JR_REP_FICHA_MATRICULA_INDIVIDUAL. */
	JR_REP_FICHA_MATRICULA_INDIVIDUAL("jrFichaMatriculaIndividual.jasper", "FichaMatriculaIndividual",
			"jrFichaMatriculaIndividual", ""),

	/** El JR_REP_RECORD_NOTA_INDIVIDUAL. */
	JR_REP_RECORD_NOTA_INDIVIDUAL("jrRecordNotaIndividual.jasper", "RecordNotaIndividual", "jrRecordNotaIndividual",
			""),

	/** El JR_REP_REPORTE_NOTA_ORDENADO_BY_SEMESTRE. */
	JR_REP_REPORTE_NOTA_ORDENADO_BY_SEMESTRE("jrReporteNotaOrdenadoBySemestre.jasper", "ReporteNotaOrdenadoBySemestre",
			"jrReporteNotaOrdenadoBySemestre", ""),

	/** El JR_REP_REPORTE_NOTA_ORDENADO_BY_CICLO. */
	JR_REP_REPORTE_NOTA_ORDENADO_BY_CICLO("jrReporteNotaOrdenadoByCiclo.jasper", "ReporteNotaOrdenadoByCiclo",
			"jrReporteNotaOrdenadoByCiclo", ""),

	/** El JR_REP_REPORTE_CETIFICADO_ESTUDIO_BY_RANGO_CICLO. */
	JR_REP_REPORTE_CETIFICADO_ESTUDIO_BY_RANGO_CICLO("jrRepCertificadoEstudio.jasper", "ReporteCertificadoByRangoCiclo",
			"jrReporteCertificadoByRangoCiclo", ""),

	/** El JR_REP_REPORTE_ACTA_EVALUACION_FINAL. */
	JR_REP_REPORTE_ACTA_EVALUACION_FINAL("jrRepActaEvaluacionFinal.jasper", "ReporteActaEvaluacionFinal",
			"jrReporteActaEvaluacionFinal", ""),

	/** El JR_REP_RECORD_NOTA_MASIVO. */
	JR_REP_RECORD_NOTA_MASIVO("jrRecordNotaMasivo.jasper", "RecordNotaMasivo", "jrRecordNotaMasivo", ""),

	/** El JR_REP_RESUMEN_NOTA_BY_SEMESTRE. */
	JR_REP_RESUMEN_NOTA_BY_SEMESTRE("jrResumenPorEscuelaCurso.jasper", "ResumenPorEscuelaCurso",
			"jrResumenPorEscuelaCurso", ""),

	/** El NULO. */
	NULO("", "", "", "");

	/** La Constante LOO_KUP_MAP. */
	private static final Map<String, NombreReporteType> LOO_KUP_MAP = new HashMap<>();

	static {
		for (var s : EnumSet.allOf(NombreReporteType.class)) {
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
	 * @param key           el key
	 * @param value         el value
	 * @param carperta      el carperta
	 * @param codigoReporte el codigo reporte
	 */
	private NombreReporteType(String key, String value, String carperta, String codigoReporte) {
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
	public static NombreReporteType get(String key) {
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