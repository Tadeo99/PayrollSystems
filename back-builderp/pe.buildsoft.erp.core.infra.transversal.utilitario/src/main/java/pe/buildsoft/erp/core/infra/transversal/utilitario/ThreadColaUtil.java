package pe.buildsoft.erp.core.infra.transversal.utilitario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * La Class TheadColaUtil.
 * <ul>
 * <li>Copyright 2014 MAPFRE- mapfre. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, 06/07/2016
 * @since Rep v1..0
 */
public class ThreadColaUtil implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** La log. */
	private static Logger log = LoggerFactory.getLogger(ThreadColaUtil.class);

	/** La sql maping cache utl. */
	private static ThreadColaUtil threadUtil = null;

	/** El sql sentencia map. */
	private static Map<String, List<Thread>> threadMap = new HashMap<String, List<Thread>>();

	public ThreadColaUtil() {

	}

	public static ThreadColaUtil getInstance() {
		if (threadUtil == null) {
			createInstance();
		}
		return threadUtil;
	}

	/**
	 * Creates the instance.
	 */
	private static synchronized void createInstance() {
		if (threadUtil == null) {
			threadUtil = new ThreadColaUtil();
		}
	}

	public void put(String usuario, String key, Thread thread) {
		key = usuario + key;
		if (!threadMap.containsKey(key)) {
			List<Thread> value = new ArrayList<Thread>();
			value.add(thread);
			threadMap.put(key, value);
		} else {
			List<Thread> value = threadMap.get(key);
			value.add(thread);
			threadMap.put(key, value);
		}
	}

	public String stop(String usuario, String key) {
		key = usuario + key;
		StringBuilder resultado = new StringBuilder();
		if (threadMap.containsKey(key)) {
			List<Thread> listaThread = threadMap.get(key);
			if (listaThread == null) {
				listaThread = new ArrayList<Thread>();
			}
			resultado.append("Procesos Eliminados : " + listaThread.size());
			resultado.append(" \n ");
			for (var thread : listaThread) {
				eliminar(resultado, thread, key);

			}
		} else {
			resultado.append("No existe procesos para eliminar ");
		}
		threadMap.remove(key);
		return resultado.toString();
	}

	public String stopForzar(String key) {
		StringBuilder resultado = new StringBuilder();
		if (threadMap.containsKey(key)) {
			List<Thread> listaThread = threadMap.get(key);
			if (listaThread == null) {
				listaThread = new ArrayList<Thread>();
			}
			resultado.append("Procesos Eliminados : " + listaThread.size());
			resultado.append(" \n ");
			for (var thread : listaThread) {
				eliminar(resultado, thread, key);

			}
		} else {
			try {
				for (var thread : Thread.getAllStackTraces().keySet()) {
					if (thread.getName().equalsIgnoreCase(key)) {
						eliminar(resultado, thread, key);
					}
				}
			} catch (Exception e) {
				log.error("Error al detener el hilo  del proceso " + key + " ");
			}
			resultado.append("No existe procesos para eliminar ");
		}
		threadMap.remove(key);
		return resultado.toString();
	}

	private StringBuilder eliminar(StringBuilder resultado, Thread thread, String key) {
		try {
			thread.stop();
		} catch (Exception e) {
			resultado.append("Error Error al detener el hilo " + thread.getId() + "  --> " + e.getMessage());
			resultado.append(" \n ");
			log.error("Error al detener el hilo " + thread.getId() + " del proceso " + key + " ");
			// log.error(e);
		}
		return resultado;
	}

	public void remove(String key) {
		threadMap.remove(key);
	}

	public void cancelar(String usuario, String key) {
		stop(usuario, key);
	}

	private void cancelarCola() {

	}

	public static Map<String, List<Thread>> getThreadMap() {
		return threadMap;
	}

}
