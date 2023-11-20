package pe.buildsoft.erp.core.infra.transversal.utilitario;

import java.io.Serializable;

import pe.buildsoft.erp.core.infra.transversal.cache.ConfiguracionCacheActiveDirectoryWSUtil;

/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Class ConfiguracionActiveDirectoryWSUtil.
 *
 * @author BuildSoft.
 * @version 1.0 , 01/04/2015
 * @since BuildErp 1.0
 */
public final class ConfiguracionActiveDirectoryWSUtil implements Serializable {

	/** La Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	// Sincronizado con el properties :webservice.properties
	/** El URI_SERVICIO_WEB. */


	// Sincronicar con el propertis:proxy.properties
	/** La Constante PROXY_ENABLED. */
	public static final String PROXY_ENABLED = "proxy.enabled";

	/** La Constante PROXY_HOST_LOCAL. */
	public static final String PROXY_HOST_LOCAL = "proxy.host.local";

	/** La Constante PROXY_HOST_PORT_LOCAL. */
	public static final String PROXY_HOST_PORT_LOCAL = "proxy.host.port.local";

	/** La Constante PROXY_HOST_USER_LOCAL. */
	public static final String PROXY_HOST_USER_LOCAL = "proxy.host.user.local";

	/** La Constante PROXY_PASSWORD_LOCAL. */
	public static final String PROXY_PASSWORD_LOCAL = "proxy.host.password.local";

	/** La Constante PROXY_SET_LOCAL. */
	public static final String PROXY_SET_LOCAL = "proxy.host.set.local";
	
	
	
	/**
	 * Instancia un nuevo configuracion active directory ws util.
	 */
	private ConfiguracionActiveDirectoryWSUtil() {
	}

	/**
	 * Obtiene web service.
	 *
	 * @param key el key
	 * @return web service
	 */
	public static String getWebService(String key) {
		return ConfiguracionCacheActiveDirectoryWSUtil.getInstance().getWebService(key);
	}

	public static boolean isWebServiceKey(String key) {
		return ConfiguracionCacheActiveDirectoryWSUtil.getInstance().istWebService(key);
	}

	/**
	 * Obtiene proxy.
	 *
	 * @param key el key
	 * @return proxy
	 */
	public static String getProxy(String key) {
		return ConfiguracionCacheActiveDirectoryWSUtil.getInstance().getProxy(key);
	}

	/**
	 * Obtiene proxy boolean.
	 *
	 * @param key el key
	 * @return proxy boolean
	 */
	public static boolean getProxyBoolean(String key) {
		return ConfiguracionCacheActiveDirectoryWSUtil.getInstance().getProxy(key).equalsIgnoreCase("true");
	}
}
