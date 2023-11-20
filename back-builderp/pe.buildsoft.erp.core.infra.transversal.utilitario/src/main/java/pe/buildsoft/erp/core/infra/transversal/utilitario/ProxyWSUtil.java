package pe.buildsoft.erp.core.infra.transversal.utilitario;

/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Class ProxyWSUtil.
 *
 * @author BuildSoft.
 * @version 1.0 , 27/10/2015
 * @since BuildErp 1.0
 */
public class ProxyWSUtil extends BaseTransfer {

	/**
	 * Instancia un nuevo usuario active directory util.
	 */
	public ProxyWSUtil() {
		super();
	}

	/**
	 * Proxy.
	 */
	protected static void proxy() {
		if (ConfiguracionActiveDirectoryWSUtil.getProxyBoolean(ConfiguracionActiveDirectoryWSUtil.PROXY_ENABLED)) {
			String proxyHost = ConfiguracionActiveDirectoryWSUtil
					.getProxy(ConfiguracionActiveDirectoryWSUtil.PROXY_HOST_LOCAL);
			String proxyPort = ConfiguracionActiveDirectoryWSUtil
					.getProxy(ConfiguracionActiveDirectoryWSUtil.PROXY_HOST_PORT_LOCAL);
			String proxyUser = ConfiguracionActiveDirectoryWSUtil
					.getProxy(ConfiguracionActiveDirectoryWSUtil.PROXY_HOST_USER_LOCAL);
			String proxyPassword = ConfiguracionActiveDirectoryWSUtil
					.getProxy(ConfiguracionActiveDirectoryWSUtil.PROXY_PASSWORD_LOCAL);
			String proxySet = ConfiguracionActiveDirectoryWSUtil
					.getProxy(ConfiguracionActiveDirectoryWSUtil.PROXY_SET_LOCAL);

			System.getProperties().put("http.proxyHost", proxyHost);
			System.getProperties().put("http.proxyPort", proxyPort);
			if (proxyPassword != null && !proxyPassword.equals("")) {
				System.getProperties().put("http.proxyUser", proxyUser);
				System.getProperties().put("http.proxyPassword", proxyPassword);
			}
			if (proxySet != null && !proxySet.equals("")) {
				System.getProperties().put("http.proxySet", proxySet);
			}
		}
	}

}