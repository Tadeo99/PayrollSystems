/*
 * 
 */
package pe.buildsoft.erp.core.infra.transversal.utilitario;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


public class CadenaCacheUtil {

	
	private static final String CONFIGURACION_CARACTER_EXTRANIO_KEY = "configuracion.caracter.extranio";

	/** La Constante pwr_CONF_UTIL_PROPERTIES. */
	private static final String CADENA_UTIL_PROPERTIES = "cadenautil.properties";
	
	/** La configurador cache utl. */
	private static CadenaCacheUtil cadenaCacheUtil = null;
	
	/** El objeto properties. */
	private static Properties propiedadesCaracterExtranio =  new Properties();
	
	/** La caracter extranio list. */
	private static List<String> caracterExtranioList = new ArrayList<String>();
	
	private static Map<String,List<String>> caracterExtranioListMap = new HashMap<String,List<String>>();
	
  	/** El flag cargo listado. */
	private boolean flagCargoListado = false;
	
	/**
	 * Instancia un nuevo administracion cache utl.
	 */
	public CadenaCacheUtil() {
		
	}
	
	/**
	 * Instanciar.
	 *
	 * @return the configurador cache utl
	 */
	public static CadenaCacheUtil getInstance() {
		if (cadenaCacheUtil == null) {
			createInstance();
		} else if (!cadenaCacheUtil.isFlagCargoListado()) {
			cadenaCacheUtil.sincronizarData();
		}
		return cadenaCacheUtil;
	}
	 /**
     * Creates the instance.
     */
    private static synchronized void createInstance() {
    	if (cadenaCacheUtil == null) {
			cadenaCacheUtil = new CadenaCacheUtil();
			cadenaCacheUtil.sincronizarData();
		}
    }

	
	/**
	 * Sincronizar data.
	 *
	 * @return the string
	 */
	public  String sincronizarData() {
		return sincronizarProperties();
	}

	
	
	/**
	 * Sincronizar properties.
	 *
	 * @return the string
	 */
	private synchronized String sincronizarProperties() {
		try {
			InputStream inpwrConfUtil = obtenerArchivo(ConstanteConfigUtil.RUTA_GENERAL_CONFIG +  CADENA_UTIL_PROPERTIES);
			propiedadesCaracterExtranio = new Properties();
			propiedadesCaracterExtranio.load(inpwrConfUtil);
			inpwrConfUtil.close();
			
			cargarListaCaracterExtranio();
			flagCargoListado = true;
		} catch (Exception e) {
			flagCargoListado = false;
			return e.toString();
		}
		return null;
		
	}
	
	/**
	 * Cargar lista caracter extranio.
	 */
	private static void cargarListaCaracterExtranio() {
		caracterExtranioList = new ArrayList<String>();
		String key = CONFIGURACION_CARACTER_EXTRANIO_KEY;
		String listaCadenaCaracterExtranio = propiedadesCaracterExtranio.getProperty(key);
		String [] caracterExtranioArray = listaCadenaCaracterExtranio.split(",",-1);
		for (int i = 0; i < caracterExtranioArray.length; i++) {
			caracterExtranioList.add(caracterExtranioArray[i]);
		}
		for (var objPr : propiedadesCaracterExtranio.entrySet()) {
			if (!key.equals(objPr.getKey().toString())) {
				List<String> caracterExtranioListTemp = new ArrayList<String>();
				String [] caracterExtranioArrayTemp = objPr.getValue().toString().split(",",-1);
				for (int i = 0; i < caracterExtranioArrayTemp.length; i++) {
					caracterExtranioListTemp.add(caracterExtranioArrayTemp[i]);
				}
				caracterExtranioListMap.put(objPr.getKey().toString(), caracterExtranioListTemp);
			}
			
		}
	}
   
    /**
     * Obtener archivo.
     *
     * @param pathFile el path file
     * @return the input stream
     */
    public static InputStream obtenerArchivo(String pathFile) throws Exception {
		InputStream is = null;
		File f = new File(pathFile);
		is = new FileInputStream(f);		
		return is;
	}

	/**
	 * Obtiene pwr conf util.
	 *
	 * @param key el key
	 * @return pwr conf util
	 */
	public static String getPropertiesCaracterExtranio(String key) {
		if (propiedadesCaracterExtranio.containsKey(key)) {
			return propiedadesCaracterExtranio.getProperty(key);
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
	 * Obtiene caracter extranio list.
	 *
	 * @return caracter extranio list
	 */
	public static List<String> getCaracterExtranioList() {
		return caracterExtranioList;
	}
	
	public static List<String> getCaracterExtranioList(int bloque, String tipo) {
		String key = CONFIGURACION_CARACTER_EXTRANIO_KEY + "." + bloque + "." + tipo;
		if (caracterExtranioListMap.containsKey(key)) {
			return caracterExtranioListMap.get(key);
		}
		return new ArrayList<String>();
	}
}
