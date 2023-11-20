package pe.buildsoft.erp.core.infra.transversal.utilitario.csv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;

import pe.buildsoft.erp.core.infra.transversal.utilitario.ConstanteConfigUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * <ul>
 * <li>Copyright 2018 MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Class GenerarCSVUtil.
 *
 * @author BuildSoft S.A.C.
 * @version 1.0 , 10/10/2018
 * @since BuildErp 1.0
 */
//Inicio BUIDSOFT 01 Requerimiento telecobranza pendiente
public class GenerarCSVUtil {

	/**
	 * Logger para el registro de errores.
	 */
	private static Logger log = LoggerFactory.getLogger(GenerarCSVUtil.class);

	/** La Constante RUTA_RECURSOS. */
	public static final String RUTA_RECURSOS_BYTE_BUFFER = ConstanteConfigUtil.RUTA_RECURSOS_BYTE_BUFFER;

	public static void generarCSV(List<Map<String, Object>> listaHeader, List<Map<String, Object>> listaData,
			String carpeta, String archivoName) {
		ICSVWriter writer = null;
		try {
			// Inicio BuildSoft Reporte Fon Bienes
			File archivoXLS = new File(RUTA_RECURSOS_BYTE_BUFFER
					+ (StringUtil.isNullOrEmpty(carpeta) ? "" : ConstanteConfigUtil.generarRuta(carpeta)));
			// Fin BuildSoft Reporte Fon Bienes
			if (!archivoXLS.isFile()) {
				archivoXLS.mkdirs();
			}
			// Inicio BuildSoft Reporte Fon Bienes
			writer = new CSVWriterBuilder(new FileWriter(RUTA_RECURSOS_BYTE_BUFFER
					+ (StringUtil.isNullOrEmpty(carpeta) ? "" : ConstanteConfigUtil.generarRuta(carpeta)) + ""
					+ archivoName + ".csv")).withSeparator(';').build();
			synchronized (writer) {
				// Fin BuildSoft Reporte Fon Bienes
				String[] header = new String[listaHeader.size()];
				int i = 0;
				for (var objHeader : listaHeader) {
					header[i] = objHeader.get("VALUE_HEADER") + "";
					i++;
				}
				writer.writeNext(header);
				writer.flush();
				int contador = 0;
				for (var objDataTemp : listaData) {
					i = 0;
					String[] data = new String[listaHeader.size()];
					for (var objHeader : listaHeader) {
						data[i] = objDataTemp.get(objHeader.get("KEY_HEADER")) + "";
						i++;
					}
					contador++;
					writer.writeNext(data);
					if (contador == 20000) {
						contador = 0;
						writer.flush();
					}
				}
				writer.close();
			}
		} catch (Exception e) {
			log.error("generarCSV", e);
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e1) {
					log.error("generarCSV.e1", e1);
				}
			}
		}
	}
}