package pe.buildsoft.erp.core.infra.transversal.utilitario;

/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Class ConstanteConfiguracionTramaUtil.
 *
 * @author BuildSoft.
 * @version 1.0 , 29/02/2016
 * @since BuildErp 1.0
 */
public class ConstanteConfiguracionTramaUtil {

	private ConstanteConfiguracionTramaUtil() {
		throw new IllegalStateException("Utility ConstanteConfiguracionTramaUtil class");
	}

	public static final String USUARIO = "usuario";
	public static final String CAMPO_EXCLUSION_C_ORIGEN = "c_origen";
	public static final String GESTION_TRAMA = "GT";
	public static final String NOMENCLATURA = "nomenclatura";
	public static final String GENERA_SECUENCIA = "genera_secuencia";
	public static final String NUMERO_SOLICITUD = "numeroSolicitud";
	public static final String NUMERO_POLIZA_PARAM = "numeroPoliza";
	public static final String FECHA_CALCULADA_FORMATO_PARAM = "fechaCalculadaFormato";
	public static final String FECHA_CALCULADA_PARAMETRO = "fechaCalculadaParametro"; // mejora fecha calculada
	public static final String CARPETA_MANUAL = "manual";
	public static final String CARPETA_AUTOMATIC = "automatic";

	public static final String TASMCTRLOTE_MPE = "TASMCTRL_MPE";
	public static final String TASMEEROR_MPE = "TASMEERR_MPE";
	public static final String TASMCTRTRAMA_MPE = "TASMCTRT_MPE";
	public static final String TABLA_INTERMEDIA_EDGP = "TASMEDGP_MPE";
	public static final String CAMPO_EXCLUSION_C_USUARIO = "c_usuario";
	public static final String CAMPO_EXCLUSION_N_ID_JUEGO = "n_id_juego";
	public static final String CAMPO_EXCLUSION_C_NOM_ARCHIVO = "c_nom_archivo";
	public static final String CAMPO_EXCLUSION_C_NUM_REF = "c_num_ref";
	public static final String ID_CONTROL_PROCESO = "idControlProceso";
	public static final String INDICADOR_INTERNO_ACTIVIDAD = "indicadorInternoActividad";

	public static final String ES_SIMULACION = "simulacion";
	public static final String ARCHIVO_SIMULAR = "ARCHIVO_SIMULAR";
	public static final String ARCHIVO_SIMULAR_RUTA_NAME = "ARCHIVO_SIMULAR_RUTA_NAME";

	public static final String FLAG_FIN = "IF";
	public static final String FLAG_INICIO = "II";
	public static final String FILA_DATA_ORIGINAL = "filDataOriginal";
	public static final String ARTIFICIO_NO_HAY_TRAMAS = "${noHayTramas}";
	public static final String ARTIFICIO_ERROR_NOMENCLATURA = "${errorNomenclatura}";
	public static final String ARTIFICIO_ERROR_TECNICO = "${errorTecnico}";
	public static final String ES_IMPRIMIR = "esImprimirLog";
	public static final String CAMPO_NAME = "nombreCampo";
	public static final String TABLA_NAME = "nombreTabla";
	public static final String TIPO_ERROR = "tipoError";
	public static final String TIPO_ERROR_TRAMA = "tipoErrorTrama";
	public static final String NUMERO_HOJA = "numeroHoja";
}
