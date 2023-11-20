package pe.buildsoft.erp.core.infra.transversal.utilitario;

import pe.buildsoft.erp.core.infra.transversal.cache.ConfiguracionCacheUtil;

/**
 * La Class ConfiguracionJMSUtil.
 * <ul>
 * <li>Copyright 2014 MAPFRE-
 * mapfre. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Fri Apr 25 18:56:23 COT 2014
 * @since PWR v1.0
 */
public class ConfiguracionUtil {
	
	//Sincronizado con el properties :pwrconfutil.properties
	/** El CANTIDAD_INTENTOS_COLA. */
	public static String CANTIDAD_INTENTOS_COLA = "cantidad.intentos.cola";
	/** El CANTIDAD_PAGINA. */
	public static String CANTIDAD_PAGINA = "cantidad.pagina";
	/** El CANTIDAD_PAGINA_MODAL. */
	public static String CANTIDAD_PAGINA_MODAL = "cantidad.pagina.modal";
	/** El CANTIDAD_PAGINA_INICIO_ACCESO_DIRECTO. */
	public static String CANTIDAD_PAGINA_INICIO_ACCESO_DIRECTO = "cantidad.pagina.inicio.acceso.directo";
	
	public static String REPORTE_DETALLE_PRODUCCION_VALIDACION_FECHA_MESES_PERMITIDOS = "reporteDetalleProduccion.validacion.fecha.meses.permitidos";
	
	public static String REPORTE_DETALLE_PRODUCCION_CUBOS_VALIDACION_FECHA_MESES_PERMITIDOS = "reporteDetalleProduccionCubos.validacion.fecha.meses.permitidos";	
	
	public static String MENU_OPCION_TITLE_LENGTH = "menu.opcion.title.length";
	
	public static String CARACTER_FORMATEO_NUMERICO= "caracter.formateo.numerico";
	
	public static String START_ROW = "reporteDetalleResumenProduccion.startrow";
	
	public static String OFFSET = "reporteDetalleResumenProduccion.offset";
	
	public static String OFFSETINDEX = "reporteDetalleResumenProduccion.offsetindex";
	
	public static String HORA_INICIAL = "reporteDetalleResumenProduccion.hora";
	
	public static String MINUTO_INCIAL = "reporteDetalleResumenProduccion.minuto";
	
	public static String SEGUNDO_INICIAL = "reporteDetalleResumenProduccion.segundo";
	
	public static String INTERVALO = "reporteDetalleResumenProduccion.intervalo";
	
	public static String START_ROW_TAB_CONTROL = "reporteDetalleTableroControl.startrow";
	
	public static String OFFSET_TAB_CONTROL = "reporteDetalleTableroControl.offset";
	
    public static String HORA_INICIAL_TAB_CONTROL = "reporteDetalleTableroControl.hora";
	
	public static String MINUTO_INCIAL_TAB_CONTROL = "reporteDetalleTableroControl.minuto";
	
	public static String SEGUNDO_INICIAL_TAB_CONTROL = "reporteDetalleTableroControl.segundo";
	
	public static String INTERVALO_TAB_CONTROL = "reporteDetalleTableroControl.intervalo";
	
	public static String FECHA_INICIO_MIGRACION_TABLERO_CONTROL = "rangoIncio.migracion.tableroControl";
	
	public static String FECHA_FIN_MIGRACION_TABLERO_CONTROL = "rangoFin.migracion.tableroControl";
	
	
	
	/**
	 * Obtiene PWR conf util int.
	 *
	 * @param key el key
	 * @return PWR conf util int
	 */
	public static int getPWRConfUtilInt(String key) {
		return ConfiguracionCacheUtil.getInstance().getPwrConfUtilInt(key);
	}
	public static String getPwrConfUtil(String key) {
		return ConfiguracionCacheUtil.getInstance().getPwrConfUtil(key);
	}
}
