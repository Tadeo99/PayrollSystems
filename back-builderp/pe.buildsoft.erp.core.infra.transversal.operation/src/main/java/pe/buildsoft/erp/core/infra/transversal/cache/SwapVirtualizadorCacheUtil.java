package pe.buildsoft.erp.core.infra.transversal.cache;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.jasperreports.engine.fill.JRAbstractLRUVirtualizer;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ArchivoUtilidades;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ConstanteConfigUtil;

/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul> 
 * 
 * La Class SwapVirtualizadorCacheUtil.
 *
 * @author BuildSoft.
 * @version 1.0 , 28/08/2016
 * @since BuildErp 1.0
 */
public class SwapVirtualizadorCacheUtil {

	/** La log. */
	private static Logger log = LoggerFactory.getLogger(SwapVirtualizadorCacheUtil.class);
	
	/** La regla cache util. */
	private static SwapVirtualizadorCacheUtil swapVirtualizador;

	/** El jRAbstractLRUVirtualizerList list. */
	private static Map<String, Map<String,JRAbstractLRUVirtualizer>> jRAbstractLRUVirtualizerMap = new HashMap<>();
	
	
	/**
	 * Instanciar.
	 *
	 * @return the configurador cache utl
	 */
	public static SwapVirtualizadorCacheUtil getInstance() {
		if (swapVirtualizador == null) {
			createInstance();
		} 
		return swapVirtualizador;
	}
	
	 /**
     * Creates the instance.
     */
    private static synchronized void createInstance() {
    	if (swapVirtualizador == null) {
    		swapVirtualizador = new SwapVirtualizadorCacheUtil();
		}
    }

    public static synchronized void put(String usuario,String uuid, JRAbstractLRUVirtualizer jRAbstractLRUVirtualizer) {
    	if (!jRAbstractLRUVirtualizerMap.containsKey(usuario)) {
    		Map<String, JRAbstractLRUVirtualizer> value = new HashMap<>();
    		value.put(uuid,jRAbstractLRUVirtualizer);
    		jRAbstractLRUVirtualizerMap.put(usuario, value);
    	} else {
    		Map<String,JRAbstractLRUVirtualizer> value = jRAbstractLRUVirtualizerMap.get(usuario);
    		value.put(uuid,jRAbstractLRUVirtualizer);
    		jRAbstractLRUVirtualizerMap.put(usuario, value);
    	}
    }
    public static synchronized void cleanup(String usuario) {
    	try {
    		if (jRAbstractLRUVirtualizerMap.containsKey(usuario)) {
    			Map<String,JRAbstractLRUVirtualizer> value = jRAbstractLRUVirtualizerMap.get(usuario);
    			if (value != null) {
    				for (var jrAbstractLRUVirtualizerMap : value.entrySet()) {
    					eliminarTemp(usuario, jrAbstractLRUVirtualizerMap.getKey(), jrAbstractLRUVirtualizerMap.getValue());
					}
    			}
    		}
    		jRAbstractLRUVirtualizerMap.remove(usuario);
		} catch (Exception e) {
			log.error("Error.cleanUp --> " + e.getMessage());
		}
    }
    private static void eliminarTemp(String usuario,String uuid, JRAbstractLRUVirtualizer jRAbstractLRUVirtualizer) {
    	try {
    		jRAbstractLRUVirtualizer.cleanup();
			String rutaSession = ConstanteConfigUtil.RUTA_SESSION_TEMP + ConstanteConfigUtil.generarRuta(usuario) ;
			ArchivoUtilidades.limpiarArchivoAllDirectory(rutaSession + uuid);
		} catch (Exception e) {
			log.error("Error.cleanUp.tractLRUVirtualizer.cleanup() --> " + e.getMessage());
		}
    }
    public static synchronized void cleanup(String usuario,String uuid) {
    	if (jRAbstractLRUVirtualizerMap.containsKey(usuario)) {
			Map<String,JRAbstractLRUVirtualizer> valueMap = jRAbstractLRUVirtualizerMap.get(usuario);
			if (valueMap != null) {
			    if (valueMap.containsKey(uuid)) {
			    	eliminarTemp(usuario, uuid, valueMap.get(uuid));
			    }
			    valueMap.remove(uuid);
			}
    	}
    }
	public static Map<String, Map<String,JRAbstractLRUVirtualizer>> getjRAbstractLRUVirtualizerMap() {
		return jRAbstractLRUVirtualizerMap;
	}	
}