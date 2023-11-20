/**
 * 
 */
package pe.buildsoft.erp.core.infra.transversal.utilitario;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * La Class ResourceUtil.
 * <ul>
 * <li>Copyright 2014 MAPFRE-
 * mapfre. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Wed Apr 30 11:24:54 COT 2014
 * @since Rep v1..0
 */
public final class ResourceUtil {
	
	
	
	/**
	 * Instancia un nuevo resource util.
	 */
	private ResourceUtil() {
		
	}
	
	/**
	 * Obtiene string.
	 *
	 * @param bundleName el bundle name
	 * @param key el key
	 * @return string
	 */
	public static String getString(String bundleName, String key) {
		String baseMsg;

		try {
				baseMsg = ResourceBundle.getBundle(bundleName).getString(key);
		} catch (MissingResourceException e) {
			System.err.println("no existe key " + key);
			return '!' + key + '!';
		}

		return baseMsg;
	}

	/**
	 * Obtiene string.
	 *
	 * @param bundleName el bundle name
	 * @param key el key
	 * @param parameter el parameter
	 * @return string
	 */
	public static String getString(String bundleName, String key,
			Object parameter) {
		String baseMsg;

		try {
			Locale locale = null;
            Locale localeSession = ResourceUtil.obtenerLocaleSession();
            if (localeSession != null) {
                locale = localeSession;
            }
            if (locale != null) {
            	baseMsg = ResourceBundle.getBundle(bundleName,locale).getString(key);
            } else {
            	baseMsg = ResourceBundle.getBundle(bundleName).getString(key);
            }
		} catch (MissingResourceException e) {
			System.err.println("no existe key " + key);
			return '!' + key + '!';
		}

		return MessageFormat.format(baseMsg, parameter);
	}

	/**
	 * Obtiene string.
	 *
	 * @param bundleName el bundle name
	 * @param key el key
	 * @param parameters el parameters
	 * @return string
	 */
	public static String getString(String bundleName, String key, Object... parameters) {
		String baseMsg;

		try {
			Locale locale = null;
            Locale localeSession = ResourceUtil.obtenerLocaleSession();
            if (localeSession != null) {
                locale = localeSession;
            }
            if (locale != null) {
            	baseMsg = ResourceBundle.getBundle(bundleName,locale).getString(key);
            } else {
            	baseMsg = ResourceBundle.getBundle(bundleName).getString(key);
            }
		} catch (MissingResourceException e) {
			System.err.println("no existe key " + key);
			return '!' + key + '!';
		}

		return MessageFormat.format(baseMsg, parameters);
	}

	/**
	 * Obtiene string.
	 *
	 * @param locale el locale
	 * @param bundleName el bundle name
	 * @param key el key
	 * @return string
	 */
	public static String getString(Locale locale, String bundleName, String key) {
		String baseMsg;

		Locale localeSession = obtenerLocaleSession();
		if (localeSession != null) {
			locale = localeSession;
		}
		
		try {
			baseMsg = ResourceBundle.getBundle(bundleName, locale).getString(key);
		} catch (MissingResourceException e) {
			System.err.println("no existe key " + key);
			return '!' + key + '!';
		}

		return baseMsg;
	}

	/**
	 * Obtener locale session.
	 *
	 * @return the locale
	 */
	public static Locale obtenerLocaleSession() {
		Locale resultado  = new Locale("es", "PE");
		return resultado;
	}
	
	
	/**
	 * Obtiene string.
	 *
	 * @param locale el locale
	 * @param bundleName el bundle name
	 * @param key el key
	 * @param parameter el parameter
	 * @return string
	 */
	public static String getString(Locale locale, String bundleName,
			String key, Object parameter) {
		String baseMsg;
		Locale localeSession = obtenerLocaleSession();
		if (localeSession != null) {
			locale = localeSession;
		}
		try {
			baseMsg = ResourceBundle.getBundle(bundleName, locale).getString(
					key);
		} catch (MissingResourceException e) {
			System.err.println("no existe key " + key);
			return '!' + key + '!';
		}

		return MessageFormat.format(baseMsg, parameter);
	}

	/**
	 * Obtiene string.
	 *
	 * @param locale el locale
	 * @param bundleName el bundle name
	 * @param key el key
	 * @param parameters el parameters
	 * @return string
	 */
	public static String getString(Locale locale, String bundleName,
			String key, Object... parameters) {
		String baseMsg;
		Locale localeSession = obtenerLocaleSession();
		if (localeSession != null) {
			locale = localeSession;
		}
		try {
			baseMsg = ResourceBundle.getBundle(bundleName, locale).getString(
					key);
		} catch (MissingResourceException e) {
			System.err.println("no existe key " + key);
			return '!' + key + '!';
		}

		return MessageFormat.format(baseMsg, parameters);
	}

	
	 /**
 	 * Es simulacion.
 	 *
 	 * @param value el value
 	 * @return true, en caso de exito
 	 */
 	public static boolean esSimulacion(Object value) {
		   boolean resultado = false;
		   if (!StringUtil.isNullOrEmpty(value)) {
			   resultado = Boolean.valueOf(value.toString());
		   }
		   return resultado;
	 }
	
}
