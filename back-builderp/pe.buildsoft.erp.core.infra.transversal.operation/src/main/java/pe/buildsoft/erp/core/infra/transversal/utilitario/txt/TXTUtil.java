package pe.buildsoft.erp.core.infra.transversal.utilitario.txt;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.mozilla.universalchardet.UniversalDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ValueDataVO;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ConstanteConfigUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.TransferDataOperUtil;

/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Class TXTUtil.
 *
 * @author BuildSoft.
 * @version 1.0 , 07/04/2015
 * @since BuildErp 1.0
 */
public class TXTUtil {

	private static final String DELIMITADOR_DATA = "delimitadorData";
	private static final String CANTIDAD_DATA = "cantidadData";
	private static final String FILA_DATA = "filaData";
	/**
	 * Logger para el registro de errores.
	 */
	private static Logger log = LoggerFactory.getLogger(TXTUtil.class);

	public static List<Map<String, ValueDataVO>> toTXTMap(BufferedReader br, int filaData,
			Map<String, Object> campoMappingExcelMap, Map<String, String> campoMappingExcelTypeMap,
			Map<String, String> campoMappingFormatoMap, Integer cantidadData, Map<String, Object> parametroMap,
			Map<String, Character> configuracionTramaDetalleMap) {
		parametroMap.put(FILA_DATA, filaData);
		parametroMap.put(CANTIDAD_DATA, cantidadData);
		return TransferDataOperUtil.toTXTMap(campoMappingExcelMap, br, campoMappingExcelTypeMap, campoMappingFormatoMap,
				parametroMap, configuracionTramaDetalleMap);
	}

	public static List<Map<String, ValueDataVO>> transferObjetoEntityCoordenadaTXTMapDTO(BufferedReader br,
			int filaData, Map<String, Object> campoMappingExcelMap, Map<String, String> campoMappingExcelTypeMap,
			Map<String, String> campoMappingFormatoMap, Integer cantidadData, boolean isCabecera,
			String delimitadorData, Map<String, Object> parametroMap,
			Map<String, Character> configuracionTramaDetalleMap) {
		parametroMap.put(FILA_DATA, filaData);
		parametroMap.put(CANTIDAD_DATA, cantidadData);
		parametroMap.put(DELIMITADOR_DATA, delimitadorData);
		return TransferDataOperUtil.toCoordenadaTXTMapDTO(campoMappingExcelMap, br, campoMappingExcelTypeMap,
				campoMappingFormatoMap, isCabecera, parametroMap, configuracionTramaDetalleMap);
	}

	public static List<Map<String, ValueDataVO>> transferObjetoEntityTXTMapDTO(BufferedReader br, int filaData,
			Map<String, Object> campoMappingExcelMap, Map<String, String> campoMappingExcelTypeMap,
			Map<String, String> campoMappingFormatoMap, String txtSplitBy, Integer cantidadData,
			Map<String, Object> parametroMap, Map<String, Character> configuracionTramaDetalleMap) throws Exception {
		parametroMap.put(FILA_DATA, filaData);
		parametroMap.put("txtSplitBy", txtSplitBy);
		parametroMap.put(CANTIDAD_DATA, cantidadData);
		return TransferDataOperUtil.toTXTSeparadorMapDTO(campoMappingExcelMap, br, campoMappingExcelTypeMap,
				campoMappingFormatoMap, parametroMap, configuracionTramaDetalleMap);
	}

	public static BufferedReader leerTXT(String pathFile, boolean imprimirLog) {
		BufferedReader resultado = null;
		String encoding = "";
		try {
			encoding = detectarCodificacion(pathFile);
			if (!StringUtil.isNullOrEmpty(encoding)) {
				if (imprimirLog) {
					log.error("leerTXT.linea encoding del  " + pathFile + " es " + encoding);
				}
				resultado = new BufferedReader(new InputStreamReader(new FileInputStream(pathFile), encoding));
			} else {
				log.error("leerTXT.linea no se encontro encoding para el archivo " + pathFile);
				log.error("leerTXT.linea se procedera a leer el archivo con el encoding por defecto ");
				resultado = new BufferedReader(new InputStreamReader(new FileInputStream(pathFile)));
			}
		} catch (Exception e) {
			log.error("leerTXT", e);
		}
		if (imprimirLog) {
			try {
				String temp = "";
				int linea = 1;
				BufferedReader resultadoTemp = null;
				resultadoTemp = new BufferedReader(new InputStreamReader(new FileInputStream(pathFile)));
				while ((temp = resultadoTemp.readLine()) != null) {
					Charset.forName(encoding).encode(temp);
					log.error("leerTXT.linea " + linea + temp);
					linea++;
				}
			} catch (Exception e) {
				log.error("leerTXT.Error en leer buffer del log " + e.getMessage());
			}
		}
		return resultado;
	}

	public static BufferedReader leerTXT(byte[] datosArchivo) {
		BufferedReader resultado = null;
		String encoding = "";
		try {
			encoding = null;// detectarCodificacion(pathFile);
			if (!StringUtil.isNullOrEmpty(encoding)) {
				resultado = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(datosArchivo), encoding));
			} else {
				resultado = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(datosArchivo)));
			}
		} catch (Exception e) {
			log.error("leerTXT", e);
		}
		return resultado;
	}

	public static String detectarCodificacion(String strRutaArchivoP) {
		byte[] buf;
		java.io.FileInputStream fis;
		UniversalDetector detector;
		int nread;
		String encoding = "";
		try {
			buf = new byte[4096];
			fis = new java.io.FileInputStream(strRutaArchivoP);
			detector = new UniversalDetector(null);
			while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
				detector.handleData(buf, 0, nread);
			}
			detector.dataEnd();
			encoding = detector.getDetectedCharset();
			detector.reset();
			fis = null;
			buf = null;
			return encoding;
		} catch (IOException e) {
			log.error("Error en - Clase(" + new Exception().getStackTrace()[0].getClassName() + ") " + "- Método("
					+ new Exception().getStackTrace()[0].getMethodName() + ") \n" + e);
		}
		return encoding;
	}

	// TODO:REVISAR
	public static void escribirArchivoXML(List<Map<String, Object>> listaData, String userName, String nombreArchivo,
			String extension, String separador) {
		FileWriter fichero = null;
		PrintWriter pw = null;
		String rutaArchivo2 = ConstanteConfigUtil.RUTA_DATOS_EXTRACION
				+ ConstanteConfigUtil.generarRuta(userName, "DATA") + nombreArchivo + "." + extension;
		File objFile = new File(rutaArchivo2);
		if (objFile.exists()) {
			System.out.println("ExisteArc");
			objFile.delete();
		}
		try {
			String rutaArchivo = ConstanteConfigUtil.RUTA_DATOS_EXTRACION
					+ ConstanteConfigUtil.generarRuta(userName, "DATA");
			File file = new File(rutaArchivo);

			if (!file.exists()) {
				file.mkdirs();
			}
			rutaArchivo = rutaArchivo + nombreArchivo + "." + extension;
			fichero = new FileWriter(rutaArchivo, true);
			pw = new PrintWriter(fichero);

			for (var map : listaData) {
				Collection<Object> listaDataMap = map.values();
				StringBuilder data = new StringBuilder();
				for (var dataMap : listaDataMap) {
					data.append(dataMap);
					if (StringUtil.isNotNullOrBlank(separador)) {
						data.append(separador);
					}
				}
				pw.println(data);
			}

		} catch (Exception e) {
			log.error("escribirArchivo", e);
		} finally {
			try {

				if (null != fichero)
					fichero.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}