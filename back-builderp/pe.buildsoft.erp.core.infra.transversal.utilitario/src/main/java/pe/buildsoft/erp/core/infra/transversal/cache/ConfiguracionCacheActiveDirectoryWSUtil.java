/*
 * 
 */
package pe.buildsoft.erp.core.infra.transversal.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import pe.buildsoft.erp.core.infra.transversal.utilitario.ConstanteConfigUtil;

/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Class ConfiguracionCacheActiveDirectoryWSUtil.
 *
 * @author BuildSoft.
 * @version 1.0 , 01/04/2015
 * @since BuildErp 1.0
 */
public class ConfiguracionCacheActiveDirectoryWSUtil {

	/** La Constante JNDI_PROPERTIES. */
	private static final String WEB_SERVICE_PROPERTIES = "webservice.properties";

	/** La Constante PROXY_PROPERTIES. */
	private static final String PROXY_PROPERTIES = "proxy.properties";

	/** La configurador cache utl. */
	private static ConfiguracionCacheActiveDirectoryWSUtil configuracionCacheUtil = null;

	/** El objeto properties. */
	private static Properties propiedadesWebService = new Properties();

	/** El objeto properties. */
	private static Properties propiedadesProxy = new Properties();

	/** El flag cargo listado. */
	private boolean flagCargoListado = false;

	/**
	 * Instancia un nuevo administracion cache utl.
	 */
	public ConfiguracionCacheActiveDirectoryWSUtil() {

	}

	/**
	 * Instanciar.
	 *
	 * @return the configurador cache utl
	 */
	public static ConfiguracionCacheActiveDirectoryWSUtil getInstance() {
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
			configuracionCacheUtil = new ConfiguracionCacheActiveDirectoryWSUtil();
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
			//
			var config = ConstanteConfigUtil.RUTA_GENERAL_CONFIG;
			var inWebService = obtenerArchivo(config + WEB_SERVICE_PROPERTIES);
			propiedadesWebService = new Properties();
			propiedadesWebService.load(inWebService);
			inWebService.close();

			var inpwrProxy = obtenerArchivo(config + PROXY_PROPERTIES);
			propiedadesProxy = new Properties();
			propiedadesProxy.load(inpwrProxy);
			inpwrProxy.close();

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
			// in
		}
		return null;
	}

	/**
	 * Gets the web service.
	 *
	 * @param key the key
	 * @return the web service
	 */
	public static String getWebService(String key) {
		if (propiedadesWebService.containsKey(key)) {
			return propiedadesWebService.getProperty(key);
		} else {
			return "!" + key + "!";
		}
	}

	public static boolean istWebService(String key) {
		return propiedadesWebService.containsKey(key);
	}

	/**
	 * Gets the proxy.
	 *
	 * @param key the key
	 * @return the mail server
	 */
	public static String getProxy(String key) {
		if (propiedadesProxy.containsKey(key)) {
			return propiedadesProxy.getProperty(key);
		} else {
			return "!" + key + "!";
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

}
