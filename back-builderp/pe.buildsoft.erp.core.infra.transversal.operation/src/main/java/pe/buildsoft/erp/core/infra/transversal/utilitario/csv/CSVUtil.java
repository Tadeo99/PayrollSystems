package pe.buildsoft.erp.core.infra.transversal.utilitario.csv;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ValueDataVO;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ConstanteConfiguracionTramaUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.TransferDataOperUtil;

/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Class CSVUtil.
 *
 * @author BuildSoft.
 * @version 1.0 , 07/04/2015
 * @since BuildErp 1.0
 */
public class CSVUtil {

	/**
	 * Logger para el registro de errores.
	 */
	private static Logger log = LoggerFactory.getLogger(CSVUtil.class);

	public static List<Map<String, ValueDataVO>> toCSVMap(BufferedReader br, int filaData,
			Map<String, Object> campoMappingCVSMap, Map<String, String> campoMappingExcelTypeMap,
			Map<String, String> campoMappingFormatoMap, String csvSplitBy, Integer cantidadData,
			Map<String, Object> parametroMap, Map<String, Character> configuracionTramaDetalleMap) throws Exception {
		parametroMap.put(ConstanteConfiguracionTramaUtil.FILA_DATA_ORIGINAL, filaData);
		parametroMap.put("filaData", filaData);
		parametroMap.put("cantidadData", cantidadData);
		parametroMap.put("csvSplitBy", csvSplitBy);
		return TransferDataOperUtil.toCSVMap(campoMappingCVSMap, br, campoMappingExcelTypeMap, campoMappingFormatoMap,
				parametroMap, configuracionTramaDetalleMap);
	}

	public static BufferedReader leerCVS(String pathFile) {
		BufferedReader resultado = null;
		InputStream in = null;
		try {
			File f = new File(pathFile);
			in = new FileInputStream(f);
		} catch (Exception e) {
			log.error("leerCVS", e);
		}

		try {
			resultado = new BufferedReader(new InputStreamReader(in, "latin1"));
		} catch (Exception e) {
			log.error("leerCVS", e);
		}

		return resultado;
	}

	public static BufferedReader leerCVS(byte[] datosArchivo) {
		BufferedReader resultado = null;
		InputStream in = null;
		try {
			in = new ByteArrayInputStream(datosArchivo);
		} catch (Exception e) {
			log.error("leerCVS", e);
		}
		try {
			resultado = new BufferedReader(new InputStreamReader(in, "latin1"));
		} catch (Exception e) {
			log.error("leerCVS", e);
		}

		return resultado;
	}
}