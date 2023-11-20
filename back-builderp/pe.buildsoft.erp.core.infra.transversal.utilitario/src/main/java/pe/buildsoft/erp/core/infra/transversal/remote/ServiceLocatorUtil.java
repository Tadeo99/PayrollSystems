package pe.buildsoft.erp.core.infra.transversal.remote;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * La Class ServiceLocatorUtil.
 * <ul>
 * <li>Copyright 2014 MAPFRE-
 * mapfre. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Fri Apr 25 18:56:23 COT 2014
 * @since Rep v1..0
 */
@SuppressWarnings("unchecked")
public final class ServiceLocatorUtil {

	/** El service locator. */
	private static ServiceLocatorUtil serviceLocator = null;
	
	/** El contexto. */
	private static InitialContext context = null;
	
	/** El service cache. */
	@SuppressWarnings("rawtypes")
	private static HashMap serviceCache = null;
	
	/** El objeto Properties. */
	private Properties propiedades = null;
	
	

	/**
	 * OffsetDateTimeiates a new service locator util.
	 *
	 * @throws NamingException the naming exception
	 */
	@SuppressWarnings("rawtypes")
	private ServiceLocatorUtil() throws NamingException {
		sincronizarData();
	}

	/**
	 * Sincronizar data.
	 * @throws NamingException 
	 */
	public void sincronizarData() throws NamingException {
		try {
			context = new InitialContext();
			serviceCache = new HashMap(3);
		} catch (Exception e) {
			System.err.println("Error al Sincronizar Data ServiceLocatorUtil " + e.getMessage());
		}
	}
	/**
	 * M&eacute;todo que implementa el patr�n Singleton.
	 *
	 * @return Instancia del objeto Singleton
	 * @throws NamingException the naming exception
	 */
	public static ServiceLocatorUtil getInstance() throws NamingException {
		if (serviceLocator == null) {
			serviceLocator = new ServiceLocatorUtil();
		}
		return serviceLocator;
	}

	/**
	 * M&eacute;todo que busca y obtiene la referencia del servicio de negocio
	 * en el contexto.
	 *
	 * @param jndiName Representa al nombre jndi de la referencia del servicio de
	 * negocio
	 * @return La referencia del servicio de negocio
	 * @throws NamingException Se lanza esta excepci&oacute;n en caso de que no se encuentre
	 * el nombre jdni de la referencia en el contexto
	 */
	public Object getService(String jndiName) throws NamingException {
		//if (!serviceCache.containsKey(jndiName)) {
			serviceCache.put(jndiName, context.lookup(jndiName));
			// esta guardando el nombre y la direccion del objeto no es un
			// nombre context.loogiup(jndiname)--> se saltea el context eso es
			// bueno -- esta en una imagen
		//}

		return serviceCache.get(jndiName);
	}

	/**
	 * Obtener propiedad jndi.
	 *
	 * @return the properties
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public InitialContext getContext() throws IOException {
		return context;
	}
}
