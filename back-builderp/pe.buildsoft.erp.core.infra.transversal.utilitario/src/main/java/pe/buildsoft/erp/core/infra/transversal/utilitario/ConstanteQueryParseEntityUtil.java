package pe.buildsoft.erp.core.infra.transversal.utilitario;




/**
 * La Class ConstanteQueryParseEntityUtil.
 * <ul>
 * <li>Copyright 2014 MAPFRE-
 * mapfre. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Tue Apr 29 17:13:19 COT 2014
 * @since Rep v1..0
 */
public final class ConstanteQueryParseEntityUtil {

	
	/** La Constante MENSAJE_BUSQUEDA. */
	public static final String MENSAJE_BUSQUEDA = "mensaje.listarMensaje";
	
	/** La Constante INPUTA_CALCULA_BUSQUEDA. */
	public static final String  INPUTA_CALCULA_BUSQUEDA = "imputaCalcula.listarImputaCalcula";
	
	/** La Constante INPUTA_CALCULA_INSERT. */
	public static final String  INPUTA_CALCULA_INSERT = "imputaCalcula.insertImputaCalcula";
	
	/** La Constante DISCRIMINAR_TILDE_MAYUSCULA_CONVERT. */
	public static final String DISCRIMINAR_TILDE_MAYUSCULA_CONVERT = "ÁÉÍÓÚÀÈÌÒÙÄËÏÖÜÂÊÎÔÛ";
	
	/** La Constante DISCRIMINAR_TILDE_MAYUSCULA_TRASLATE. */
	public static final String DISCRIMINAR_TILDE_MAYUSCULA_TRASLATE = "AEIOUAEIOUAEIOUAEIOU";	
	
	/** La Constante DISCRIMINAR_TILDE_MINUSCULA_CONVERT. */
	public static final String DISCRIMINAR_TILDE_MINUSCULA_CONVERT = "áéíóúÁÉÍÓÚ";		
	
	/** La Constante DISCRIMINAR_TILDE_MINUSCULA_TRASLATE. */
	public static final String DISCRIMINAR_TILDE_MINUSCULA_TRASLATE = "aeiouAEIOU";	
	
	/** La Constante IS_ORACLE_RECURSIVE. */
	public static final boolean IS_ORACLE_RECURSIVE = true;
	
	public static final String CONFIGURACION_TRAMA_SELECT = "ConfiguracionTrama.select";
	public static final String CONFIGURACION_TRAMA_DETALLE_SELECT  = "ConfiguracionTramaDetalle.select";
	public static final String CONFIGURACION_TRAMA_ERROR_SELECT  = "ConfiguracionTramaError.select";
	public static final String CONFIGURACION_TRAMA_FTP_SELECT  = "ConfiguracionTramaFTP.select";
	public static final String CONFIGURACION_TRAMA_NOMENCLATURA_DETALLE_SELECT  = "ConfiguracionTramaNomenclaturaDetalle.select";
	

	
	/**
	 * Instancia un nuevo constante query parse entity util.
	 */
	private ConstanteQueryParseEntityUtil() {
		
	}
}
