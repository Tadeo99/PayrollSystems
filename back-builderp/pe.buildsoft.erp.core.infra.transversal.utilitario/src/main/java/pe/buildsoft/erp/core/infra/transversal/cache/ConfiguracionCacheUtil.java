/*
 * 
 */
package pe.buildsoft.erp.core.infra.transversal.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import pe.buildsoft.erp.core.infra.transversal.utilitario.ConstanteConfigUtil;

public class ConfiguracionCacheUtil {

	/** La Constante JNDI_PROPERTIES. */
	private static final String MAIL_SERVER_PROPERTIES = "mailserver.properties";

	/** La Constante pwr_CONF_UTIL_PROPERTIES. */
	private static final String PWR_CONF_UTIL_PROPERTIES = "pwrconfutil.properties";

	/** La configurador cache utl. */
	private static ConfiguracionCacheUtil configuracionCacheUtil = null;

	/** El objeto properties. */
	private static Properties propiedadesMailServer = new Properties();

	/** El objeto properties. */
	private static Properties propiedadespwrConfUtil = new Properties();

	private static Map<String, String> dataIdJuegoLogMap = new HashMap<>();
	private static Map<String, String> pirntLogMap = new HashMap<>();
	private static Map<String, String> desactivarServicioBuildErpMap = new HashMap<>();
	private static Map<String, String> dataIdOpcionSolicitudReporteHeavyMap = new HashMap<>();
	private static Map<String, String> dataPrimaParentescoRamo = new HashMap<>();

	private static Map<String, String> codigoPlacaPermitidoMap = new HashMap<>();

	/** El flag cargo listado. */
	private boolean flagCargoListado = false;

	/**
	 * Instancia un nuevo administracion cache utl.
	 */
	public ConfiguracionCacheUtil() {

	}

	/**
	 * Instanciar.
	 *
	 * @return the configurador cache utl
	 */
	public static ConfiguracionCacheUtil getInstance() {
		if (configuracionCacheUtil == null) {
			createInstance();
		} else if (!configuracionCacheUtil.isFlagCargoListado()) {
			configuracionCacheUtil.sincronizarData();
		}
		return configuracionCacheUtil;
	}

	/**
	 * Creates the instance.
	 */
	private static synchronized void createInstance() {
		if (configuracionCacheUtil == null) {
			configuracionCacheUtil = new ConfiguracionCacheUtil();
			configuracionCacheUtil.sincronizarData();
		}
	}

	/**
	 * Sincronizar data.
	 *
	 * @return the string
	 */
	public String sincronizarData() {
		return sincronizarProperties();
	}

	/**
	 * Sincronizar properties.
	 *
	 * @return the string
	 */
	private synchronized String sincronizarProperties() {
		try {

			var inMailServer = obtenerArchivo(ConstanteConfigUtil.RUTA_GENERAL_CONFIG + MAIL_SERVER_PROPERTIES);
			propiedadesMailServer = new Properties();
			propiedadesMailServer.load(inMailServer);
			inMailServer.close();

			var inpwrConfUtil = obtenerArchivo(ConstanteConfigUtil.RUTA_GENERAL_CONFIG + PWR_CONF_UTIL_PROPERTIES);
			propiedadespwrConfUtil = new Properties();
			propiedadespwrConfUtil.load(inpwrConfUtil);
			inpwrConfUtil.close();

			generarConfLogTramaJuegoUtil();
			generarPinrtLogProcesoUtil();
			generarDesactivarServicioBuildErprocesoUtil();
			generarCodigoPlacaPermitidoUtil();
			generarSolicitudReporteHeavyUtil();
			generarProductoSaludPrimaParentescoRamo();
			flagCargoListado = true;
		} catch (Exception e) {
			flagCargoListado = false;
			return e.toString();
		}
		return null;

	}

	/**
	 * Obtener archivo.
	 *
	 * @param pathFile el path file
	 * @return the input stream
	 */
	public static InputStream obtenerArchivo(String pathFile) {
		try {
			var f = new File(pathFile);
			return new FileInputStream(f);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Gets the mail server.
	 *
	 * @param key the key
	 * @return the mail server
	 */
	public static String getMailServer(String key) {
		if (propiedadesMailServer.containsKey(key)) {
			return propiedadesMailServer.getProperty(key);
		} else {
			return "!" + key + "!";
		}
	}

	/**
	 * Gets the mail server.
	 *
	 * @param key the key
	 * @return the mail server
	 */
	public static String getPwrConfUtil(String key) {
		if (propiedadespwrConfUtil.containsKey(key)) {
			return propiedadespwrConfUtil.getProperty(key);
		} else {
			return "!" + key + "!";
		}
	}

	public static boolean containsKeyPwrConfUtil(String key) {
		return propiedadespwrConfUtil.containsKey(key);
	}

	private static void generarConfLogTramaJuegoUtil() {
		var key = "imprimir.log.configurador.trama.registrar.data";
		dataIdJuegoLogMap = new HashMap<>();
		if (propiedadespwrConfUtil.containsKey(key)) {
			var dataIdJuego = propiedadespwrConfUtil.getProperty(key).split(",", -1);
			if (dataIdJuego != null) {
				for (var keyData : dataIdJuego) {
					dataIdJuegoLogMap.put(keyData, keyData);
				}
			}
		}
	}

	private static void generarPinrtLogProcesoUtil() {
		var key = "imprimir.log.procesos";
		pirntLogMap = new HashMap<>();
		if (propiedadespwrConfUtil.containsKey(key)) {
			var dataPrintLogProceso = propiedadespwrConfUtil.getProperty(key).split(",", -1);
			if (dataPrintLogProceso != null) {
				for (var keyData : dataPrintLogProceso) {
					pirntLogMap.put(keyData, keyData);
				}
			}
		}
	}

	private static void generarDesactivarServicioBuildErprocesoUtil() {
		var key = "desactivar.servicio.BuildErp";
		desactivarServicioBuildErpMap = new HashMap<>();
		if (propiedadespwrConfUtil.containsKey(key)) {
			var dataPrintLogProceso = propiedadespwrConfUtil.getProperty(key).split(",", -1);
			if (dataPrintLogProceso != null) {
				for (var keyData : dataPrintLogProceso) {
					desactivarServicioBuildErpMap.put(keyData.toUpperCase(), keyData);
				}
			}
		}
	}

	private static void generarCodigoPlacaPermitidoUtil() {
		var key = "codigo.placa.permitido";
		codigoPlacaPermitidoMap = new HashMap<>();
		if (propiedadespwrConfUtil.containsKey(key)) {
			var dataPrintLogProceso = propiedadespwrConfUtil.getProperty(key).split(",", -1);
			if (dataPrintLogProceso != null) {
				for (var keyData : dataPrintLogProceso) {
					codigoPlacaPermitidoMap.put(keyData.toUpperCase(), keyData);
				}
			}
		}
	}

	private static void generarProductoSaludPrimaParentescoRamo() {
		var key = "producto.salud.prima.parentesco.ramo";
		dataPrimaParentescoRamo = new HashMap<>();
		if (propiedadespwrConfUtil.containsKey(key)) {
			var dataPrintLogProceso = propiedadespwrConfUtil.getProperty(key).split(",", -1);
			if (dataPrintLogProceso != null) {
				for (var keyData : dataPrintLogProceso) {
					dataPrimaParentescoRamo.put(keyData, keyData);
				}
			}
		}
	}

	private static void generarSolicitudReporteHeavyUtil() {
		var key = "solicitud.reporte.cola.proceso.heavy.idopcion";
		dataIdOpcionSolicitudReporteHeavyMap = new HashMap<>();
		if (propiedadespwrConfUtil.containsKey(key)) {
			var dataIdOpcion = propiedadespwrConfUtil.getProperty(key).split(",", -1);
			if (dataIdOpcion != null) {
				for (var keyData : dataIdOpcion) {
					dataIdOpcionSolicitudReporteHeavyMap.put(keyData, keyData);
				}
			}
		}
	}

	public static boolean isGenerarSolicitudHeavyByOpcion(Object key) {
		return dataIdOpcionSolicitudReporteHeavyMap.containsKey(key.toString());
	}

	public static boolean isGenerarLogTramaJuego(Object key) {
		return dataIdJuegoLogMap.containsKey(key.toString());
	}

	public static boolean isPrintLogProcesos(Object key) {
		return pirntLogMap.containsKey(key.toString());
	}

	public static boolean isDesactivarServicioBuildErp(String key) {
		return desactivarServicioBuildErpMap.containsKey(key.toUpperCase());
	}

	public static boolean isCodigoPlacaPermitido(String key) {
		return codigoPlacaPermitidoMap.containsKey(key.toUpperCase());
	}

	/**
	 * Gets the pwr conf util int.
	 *
	 * @param key the key
	 * @return the pwr conf util int
	 */
	public static int getPwrConfUtilInt(String key) {
		if (propiedadespwrConfUtil.containsKey(key)) {
			try {
				return Integer.parseInt(propiedadespwrConfUtil.getProperty(key));
			} catch (Exception e) {
				return 0;
			}

		} else {
			return 0;
		}

	}

	public static boolean isElementoTrue(String key) {
		if (propiedadespwrConfUtil.containsKey(key)) {
			try {
				return propiedadespwrConfUtil.getProperty(key).equalsIgnoreCase("true");
			} catch (Exception e) {
				return false;
			}

		} else {
			return false;
		}

	}

	/**
	 * Comprueba si es flag cargo listado.
	 *
	 * @return true, si es flag cargo listado
	 */
	public boolean isFlagCargoListado() {
		return flagCargoListado;
	}

	/**
	 * Establece el flag cargo listado.
	 *
	 * @param flagCargoListado el new flag cargo listado
	 */
	public void setFlagCargoListado(boolean flagCargoListado) {
		this.flagCargoListado = flagCargoListado;
	}

	/**
	 * Gets the data id juego log map.
	 *
	 * @return the data id juego log map
	 */
	public static Map<String, String> getDataIdJuegoLogMap() {
		return dataIdJuegoLogMap;
	}

	/**
	 * Gets the data prima parentesco ramo.
	 *
	 * @return the data prima parentesco ramo
	 */
	public static Map<String, String> getDataPrimaParentescoRamo() {
		return dataPrimaParentescoRamo;
	}
}
