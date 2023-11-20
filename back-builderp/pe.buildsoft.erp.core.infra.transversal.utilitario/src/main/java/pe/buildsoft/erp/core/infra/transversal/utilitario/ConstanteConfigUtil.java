/*
 * 
 */
package pe.buildsoft.erp.core.infra.transversal.utilitario;

import java.net.URL;
import java.util.ResourceBundle;

import pe.buildsoft.erp.core.infra.transversal.type.RutaReporteType;




/**
 * <ul>
 * <li>Copyright 2014 buildErp. Todos los derechos reservados.</li>
 * </ul> 
 * 
 * La Class ConfiguracionCacheActiveDirectoryWSUtil.
 *
 * @author ndavilal
 * @version 1.0 , 01/04/2015
 * @since BuildErp-siaa 1.0
 */
public final class ConstanteConfigUtil {
	public static final String CONTEXT_REPLACE = "/buildErp";
	public static final String CONTEXT_SEARCH = "/buildErp/";
	public static final String CLIENTE_NAME = "bs";//TODO:esto debe estar en un properties
	
	public static final String SEPARADOR_FILE = "/";
	public static final boolean ES_LINUX = obtenerSistemaoperativoLinux();
	public static boolean IS_PRODUCCION = false;
	public static boolean IS_PRODUCCION_SVR = false;//TODO:SOLO PARA PRUEBAS	
	
	private static final String VERIFICADOR_RUTA_PROPERTIES = "verificadorRuta.properties";
	public static  String DISCO = obtenerDisco();
	public static final String RUTA_GENERAL = obtenerRutaGeneral();
	
	public static final String RUTA_SFS = "SFS_v1.3.4.2";
	public static final String RUTA_DATA_ENVIADO = "data_enviados";
	
	public static  String RUTA_GENERAL_TEMPLANTE = generarRuta(DISCO,RUTA_GENERAL,"buildErp",CLIENTE_NAME,"template");
	public static  String RUTA_GENERAL_CONFIG = generarRuta(DISCO,RUTA_GENERAL,"buildErp",CLIENTE_NAME,"config");
	public static  String RUTA_RECURSOS = generarRuta(DISCO,RUTA_GENERAL, "buildErp",CLIENTE_NAME) + "recursos";
	public static  String RUTA_REPORTE = generarRuta(RUTA_RECURSOS) + "reporte";//CAMBIAR
	public static  String RUTA_REPORTE_IMG = generarRuta(RUTA_RECURSOS,"reporte") + RutaReporteType.IMG.getKey();//CAMBIAR
	public static  String RUTA_REPORTE_CABECERA = generarRuta(RUTA_RECURSOS,"reporte") + RutaReporteType.CABECERA.getKey();//CAMBIAR
	public static  String RUTA_GENERAL_SQL = generarRuta(DISCO,RUTA_GENERAL,"buildErp",CLIENTE_NAME,"sql");
	public static  String RUTA_RECURSOS_LUCENE = generarRuta(DISCO, RUTA_GENERAL,"lucene");
	public static  String RUTA_LICENCIA = generarRuta(DISCO ,RUTA_GENERAL, "buildErp",CLIENTE_NAME,"licencia");
	public static  String RUTA_SERVIDOR = generarRuta(DISCO , RUTA_GENERAL,"buildErp",CLIENTE_NAME,"upload") + "temp";
	public static  String RUTA_RECURSOS_LOG = generarRuta(DISCO, RUTA_GENERAL,"buildErp",CLIENTE_NAME,"log");
	public static  String RUTA_SESSION_TEMP = generarRuta(DISCO, RUTA_GENERAL,"session");
	
	public static  String RUTA_DATOS_EXTRACION = generarRuta(DISCO, RUTA_SFS,"sunat_archivos");
	public static  String RUTA_DATOS_DATA_ENVIADO = generarRuta(DISCO, RUTA_DATA_ENVIADO);
	
	/** La Constante RUTA_RECURSOS. */
	public static String RUTA_RECURSOS_BYTE_BUFFER = generarRuta(DISCO , RUTA_GENERAL,"buildErp",CLIENTE_NAME,"bytebuffer");//C:/svr/buildErp/pwr/ByteBuffer/
	public static String RUTA_RECURSOS_DATA_BUFFER = generarRuta(DISCO , RUTA_GENERAL ,"buildErp",CLIENTE_NAME,"data_buffer");//C:/svr/buildErp/pwr/data_buffer/
	
	public static String RUTA_RECURSOS_REGLA = generarRuta(DISCO ,RUTA_GENERAL ,"buildErp",CLIENTE_NAME,"regla");
	
	public static String RUTA_RECURSOS_SWAP_FILE = generarRuta(DISCO, RUTA_GENERAL ,"buildErp",CLIENTE_NAME,"swap_file");//C:/svr/buildErp/pwr/data_buffer/
	
	public static String RUTA_RECURSOS_FOTO_ALUMN = generarRuta(DISCO ,RUTA_GENERAL ,"buildErp",CLIENTE_NAME,"recursos","img","fotosalum");
	
	public static String RUTA_RECURSOS_FOTO_PERSONAL = generarRuta(DISCO ,RUTA_GENERAL ,"buildErp",CLIENTE_NAME,"recursos","img","fotospersonal");

	/**
	 * Instancia un nuevo administracion cache utl.
	 */
	private ConstanteConfigUtil() {
		
	}
	public static String generarRuta(String... ruta) {
		StringBuilder resultado = new StringBuilder();
		for (var carpeta : ruta) {
			resultado.append(carpeta);
			resultado.append(SEPARADOR_FILE);
		}
		return resultado.toString();
	}
	private static boolean obtenerSistemaoperativoLinux(){
	   String sSistemaOperativo = System.getProperty("os.name");
	   if (sSistemaOperativo != null) {
		   return !sSistemaOperativo.toUpperCase().contains("WINDOWS");
	    }
		return false;
	}
	private static String obtenerDisco() {
		String resultado = "D:";
		if (!ES_LINUX) {
			try {
				URL url = 	ConstanteConfigUtil.class.getResource(VERIFICADOR_RUTA_PROPERTIES);
				resultado = url.getPath().substring(1, 2)+ ":";
			} catch (Exception e) {
				e.printStackTrace();
				return "D:";
			}
		} else {
			resultado = "";
		}
		return resultado;
	}
	private static String obtenerRutaGeneral() {
		String resultado = "svr";
		if (IS_PRODUCCION_SVR) {
			resultado = "svr_prod";
		}
		try {
			if (!IS_PRODUCCION_SVR) {
				String key = "ruta_general";
				if (ES_LINUX) {
					key = key + ".linux";
				}
				resultado  =  ResourceBundle.getBundle("pe.buildsoft.erp.core.infra.transversal.utilitario." +VERIFICADOR_RUTA_PROPERTIES.substring(0,15)).getString(key);
			}
			if (resultado == null || resultado.equals("")) {
				resultado = "svr";
				if (IS_PRODUCCION_SVR) {
					resultado = "svr_prod";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (IS_PRODUCCION_SVR) {
				return "svr_prod";
			}
			return "svr";
		}
		return resultado;
	}
	
}
