package pe.buildsoft.erp.core.domain.cache;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import pe.buildsoft.erp.core.infra.transversal.utilitario.FechaUtil;

/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Class ColaInstanceCacheUtil.
 *
 * @author BuildSoft.
 * @version 1.0 , 09/01/2018
 * @since BuildErp 1.0
 */
public class ColaInstanceCacheUtil implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** La regla cache util. */
	private static ColaInstanceCacheUtil colaInstanceCacheUtil;

	private static Map<String, Map<String, String>> colaInstanceMap = new HashMap<>();

	/**
	 * Instanciar.
	 *
	 * @return the cola instance cache util
	 */
	public static ColaInstanceCacheUtil getInstance() {
		if (colaInstanceCacheUtil == null) {
			createInstance();
		}
		return colaInstanceCacheUtil;
	}

	public boolean isDone(String idSolicitudReporte) {
		return colaInstanceMap.containsKey(idSolicitudReporte);
	}

	/**
	 * Creates the instance.
	 */
	private static synchronized void createInstance() {
		if (colaInstanceCacheUtil == null) {
			colaInstanceCacheUtil = new ColaInstanceCacheUtil();
		}
	}

	public static synchronized void put(String key, String idMensaje) {
		incremento(key, idMensaje);
	}

	public static synchronized void incremento(String key, String idMensaje) {
		if (!colaInstanceMap.containsKey(key)) {
			Map<String, String> value = new HashMap<>();
			value.put(idMensaje, FechaUtil.obtenerFechaFormatoCompleto(FechaUtil.obtenerFechaActual()));
			colaInstanceMap.put(key, value);
		} else {
			Map<String, String> value = colaInstanceMap.get(key);
			value.put(idMensaje, FechaUtil.obtenerFechaFormatoCompleto(FechaUtil.obtenerFechaActual()));
			colaInstanceMap.put(key, value);
		}
	}

	public static synchronized void disminuir(String key, String idMensaje) {
		if (!colaInstanceMap.containsKey(key)) {
			Map<String, String> value = new HashMap<>();
			colaInstanceMap.put(key, value);
		} else {
			Map<String, String> value = colaInstanceMap.get(key);
			value.remove(idMensaje);
			colaInstanceMap.put(key, value);
		}
	}

	public static synchronized void updateKeyMensaje(String key, String idMensaje, String data) {
		if (colaInstanceMap.containsKey(key)) {
			Map<String, String> value = colaInstanceMap.get(key);
			if (value.containsKey(idMensaje)) {
				value.put(idMensaje, value.get(idMensaje) + " ==> " + data);
			}
		}
	}

	public static synchronized void remove(String key) {
		colaInstanceMap.remove(key);
	}

	public static synchronized int getContarColaInstance(String key) {
		if (colaInstanceMap.containsKey(key)) {
			return colaInstanceMap.get(key).size();
		}
		return 0;
	}

	public static Map<String, Map<String, String>> getColaInstanceMap() {
		return colaInstanceMap;
	}
}