package pe.buildsoft.erp.core.domain.planilla.type;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * <ul>
 * <li>Copyright 2012 BUILD SOFT S.A.C - BS. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Enum VariableAplicacionType.
 *
 * @author ndavilal.
 * @version 1.0 , 11/07/2023
 * @since BuildErp 1.0
 */
public enum VariableAplicacionType {

	/** EL REMUNERACION. */
	REMUNERACION("remuneracion", "REMUNERACION", "Personal"),
	/** El ASIGNACION_FAMILIAR. */
	ASIGNACION_FAMILIAR("asignacionFamiliar", "ASIGNACION FAMILIAR", "Personal"),
	/** El PRES_ALIMENTARIA. */
	PRES_ALIMENTARIA("presAlimentarias", "PRES ALIMENTARIA", "Personal"),
	/** El AFILIADOS. */
	AFILIADOS("cantAfiliados", "cantAfiliados", "Personal"),
	/** El EPS. */
	EPS("EPS", "EPS", "Personal"),

	/** El ES_COMISION_MIXTA. */
	ES_COMISION_MIXTA("esComisionMixta", "esComisionMixta", "Personal"),

	/** El ES_SNP. */
	ES_SNP("esSNP", "esSNP", "Personal"),

	COMISION_FIJA("comisionFija", "COMISION FIJA", "ConceptoRegimenPensionario"),
	COMISION_SOBRE_FLUJO("comisionSobreFlujo", "COMISION SOBRE FLUJO", "ConceptoRegimenPensionario"),
	COMISION_FIJA_MIXTO("comisionSobreFlujoMixto", "COMISION SOBRE MIXTO", "ConceptoRegimenPensionario"),
	COMISION_SOBRE_SALDO_MIXTO("comisionAnualSobreSaldoMixto", "COMISION SOBRE SALDO MIXTO",
			"ConceptoRegimenPensionario"),
	PRIMA_SEGUROS("primaSeguros", "PRIMA SEGUROS", "ConceptoRegimenPensionario"),
	APORTE_OBLIGATORIO("aporteObligatorio", "APORTE OBLIGATORIO", "ConceptoRegimenPensionario"),
	REMUMERACION_MAXIMA_ASEGURABLE("remuneracionMaximaAsegurable", "REMUMERACION MAXIMA ASEGURABLE",
			"ConceptoRegimenPensionario"),

	/** El PLAN_BASE. */
	PLAN_BASE("planBase", "PLAN_BASE", "EPSConf"),
	/** El PLAN_ADICIONAL_1. */
	PLAN_ADICIONAL_1("planAdicional1", "PLAN ADICIONAL 1", "EPSConf"),
	/** El PLAN_ADICIONAL_2. */
	PLAN_ADICIONAL_2("planAdicional2", "PLAN ADICIONAL 2", "EPSConf"),
	/** El PLAN_ADICIONAL_3. */
	PLAN_ADICIONAL_3("planAdicional3", "PLAN ADICIONAL 3", "EPSConf"),
	/** El PLAN_ADICIONAL_4. */
	PLAN_ADICIONAL_4("planAdicional4", "PLAN ADICIONAL 4", "EPSConf"),

	/** El PLAN_MONTO. */
	// PLAN_MONTO("planMonto", "PLAN MONTO", "EPSPersonal"),
	/** El CREDITO_PCT. */
	// CREDITO_PCT("creditoPct", "CREDITO PCT", "EPSPersonal"),
	/** El SUELDO_PCT. */
	// SUELDO_PCT("sueldoPct", "SUELDO PCT", "EPSPersonal"),
	/** El ESSALUD_PCT. */
	// ESSALUD_PCT("essaludPct", "ESSALUD_PCT", "EPSPersonal"),
	/** El DESCONTAR. */
	// DESCONTAR("descontar", "DESCONTAR", "EPSPersonal"),
	/** El DESCONTAR_TRABAJADOR. */
	// DESCONTAR_TRABAJADOR("descontarTrabajador", "DESCONTAR TRABAJADOR",
	// "EPSPersonal"),

	// RENTA_5TA("renta5ta", "RENTA 5TA", "Renta5ta"),
	/*
	 * PLAN_MONTO_TOTAL("planMontoTotal", "PLAN MONTO TOTAL", "EPSPersonal Total"),
	 * CREDITO_PCT_TOTAL("creditoPctTotal", "CREDITO PCT TOTAL",
	 * "EPSPersonal Total"), SUELDO_PCT_TOTAL("sueldoPctTotal", "SUELDO PCT TOTAL",
	 * "EPSPersonal Total"), ESSALUD_PCT_TOTAL("essaludPctTotal",
	 * "ESSALUD PCT TOTAL", "EPSPersonal Total"), DESCONTAR_TOTAL("descontarTotal",
	 * "DESCONTAR TOTAL", "EPSPersonal Total"),
	 * DESCONTAR_TRABAJADOR_TOTAL("descontarTrabajadorTotal",
	 * "DESCONTAR TRABAJADOR TOTAL", "EPSPersonal Total"),
	 */
	/** El DIAS_LABORADOS. */
	DIAS_LABORADOS("diasLab", "DIAS LABORADOS", "TareoPersonal"),
	DIAS_TRABAJADOS("diasTra", "DIAS TRABAJADOS", "TareoPersonal"),
	DOMINICAL("dominical", "DIAS TRABAJADOS", "TareoPersonal"),
	HORAS_NORMAL("horasNormal", "HORAS NORMAL", "TareoPersonal"),
	HORAS_EXRTRAS25("horasExtras25", "HORAS EXRTRAS25", "TareoPersonal"),
	HORAS_EXRTRAS35("horasExtras35", "HORAS EXRTRAS35", "TareoPersonal"),
	HORAS_EXRTRAS100("horasExtras100", "HORAS EXRTRAS100", "TareoPersonal"),

	HORAS_NOCTURNA("horasNocturna", "HORAS NOCTURNA", "TareoPersonal"),
	VACACIONES("vacaciones", "VACACIONES", "TareoPersonal"),
	PERMISO_SIN_GOCE_HABER("permisoSinGoceHaber", "PERMISO SIN GOCE HABER", "TareoPersonal"),
	FALTA("falta", "FALTA", "TareoPersonal"), SUBSIDIO("subsidio", "SUBSIDIO", "TareoPersonal"),
	TARDANZA("tardanza", "TARDANZA", "TareoPersonal"), RMV("rmv", "RMV", "TareoPersonal"),
	BONIF_NOCTURNA("bonifiNocturna", "BONIF NOCTURNA", "TareoPersonal"),
	/*
	 * DIAS_LABORADOS_TOTAL("diasLabTotal", "DIAS LABORADOS TOTAL",
	 * "TareoPersonal Total"), DIAS_TRABAJADOS_TOTAL("diasTraTotal",
	 * "DIAS TRABAJADOS TOTAL", "TareoPersonal Total"),
	 * DOMINICAL_TOTAL("dominicalTotal", "DIAS TRABAJADOS TOTAL",
	 * "TareoPersonal Total"), HORAS_NORMAL_TOTAL("horasNormalTotal",
	 * "HORAS NORMAL TOTAL", "TareoPersonal Total"),
	 * HORAS_EXRTRAS25_TOTAL("horasExtras25Total", "HORAS EXRTRAS25 TOTAL",
	 * "TareoPersonal Total"), HORAS_EXRTRAS35_TOTAL("horasExtras35Total",
	 * "HORAS EXRTRAS35 TOTAL", "TareoPersonal Total"),
	 * HORAS_EXRTRAS100_TOTAL("horasExtras100Total", "HORAS EXRTRAS100 TOTAL",
	 * "TareoPersonal Total"), HORAS_NOCTURNA_TOTAL("horasNocturnaTotal",
	 * "HORAS NOCTURNA TOTAL", "TareoPersonal Total"),
	 */
	DIAS_NETOS("diasNetos", "DIAS NETOS", "PeriodoPlanilla"),

	MONTO_ADELANTO("adelanto", "MONTO_ADELANTO", "Adelanto"),
	MONTO_RENTA_EMPRESA_ANT("rentaEmpresaAnt", "rentaEmpresaAnt", "RentaEmpresaAnt"),

	UIT("uit", "UIT", "ValoresUIT"),

	FERIADO("feriado", "feriado", "Feriado"),

	MES_ACTUAL("mesActual", "mesActual", "Planilla"),

	MES("mes", "mes", "Mes");

	/** La Constante looKup. */
	private static final Map<String, VariableAplicacionType> LOO_KUP_MAP = new HashMap<>();

	static {
		for (var s : EnumSet.allOf(VariableAplicacionType.class)) {
			LOO_KUP_MAP.put(s.getKey(), s);
		}
	}

	/** El key. */
	private String key;

	/** El value. */
	private String value;

	private String descripcion;

	/**
	 * Instancia un nuevo tipo asinatura type.
	 *
	 * @param key   el key
	 * @param value el value
	 */
	private VariableAplicacionType(String key, String value, String descripcion) {
		this.key = key;
		this.value = value;
		this.descripcion = descripcion;
	}

	/**
	 * Gets the.
	 *
	 * @param key el key
	 * @return the tipo asinatura type
	 */
	public static VariableAplicacionType get(String key) {
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

	/**
	 * Obtiene descripcion.
	 *
	 * @return descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
}
