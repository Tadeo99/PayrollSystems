package pe.buildsoft.erp.core.infra.transversal.utilitario.txt;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pe.buildsoft.erp.core.infra.transversal.entidades.migrador.ConfiguracionReporteTxtDTO;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ConstanteConfigUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.FechaUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.NumerosUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Class DataExportTXTUtil.
 *
 * @author BuildSoft S.A.C.
 * @version 1.0 , 14/03/2019
 * @since BuildErp 1.0
 */
//Inicio BuildSoft Reporte SBS MU2019020179
public class DataExportTXTUtil implements Serializable {

	private static final String TIPO_DATO_FECHA = "Fecha";

	private static final String TIPO_DATO_NUMERO = "Numero";

	private static final String DEFAULT_CHARSET = "UTF-8";

	private static final String CABECERA = "${CABECERA}";

	private static final String CHARSET = "Charset";

	private static final String CHARSETVAL = "CharsetVal";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** El log. */
	private static Logger log = LoggerFactory.getLogger(DataExportTXTUtil.class);

	/** La Constante RUTA_RECURSOS. */
	public static final String RUTA_RECURSOS_BYTE_BUFFER = ConstanteConfigUtil.RUTA_RECURSOS_BYTE_BUFFER;

	/**
	 * Instancia un nuevo data export txt.
	 */
	public DataExportTXTUtil() {
		// Constructor
	}

	public static void generarTXTMap(List<ConfiguracionReporteTxtDTO> listaConfReporteTXT,
			List<Map<String, Object>> listaDataMap, String archivoName, Map<String, Object> propiedadesMap) {
		OutputStreamWriter osw = null;
		StringBuilder error = new StringBuilder();
		String charset = DEFAULT_CHARSET;
		if (propiedadesMap.containsKey(CHARSET)) {
			charset = (String) propiedadesMap.get(CHARSET);
		}
		String charsetVal = DEFAULT_CHARSET;
		if (propiedadesMap.containsKey(CHARSETVAL)) {
			charsetVal = (String) propiedadesMap.get(CHARSETVAL);
		}
		try {
			osw = new OutputStreamWriter(new FileOutputStream(RUTA_RECURSOS_BYTE_BUFFER + "" + archivoName),
					Charset.forName(charset).newEncoder());
			synchronized (osw) {
				int contador = 0;
				if (propiedadesMap.containsKey(CABECERA)) {
					if (propiedadesMap.containsKey(CHARSET)) {
						osw.write(new String(propiedadesMap.get(CABECERA).toString().getBytes(charsetVal),
								(String) propiedadesMap.get(CHARSET)) + "\r\n");
					} else {
						osw.write(propiedadesMap.get(CABECERA) + "\r\n");
					}
				}
				for (var dataMap : listaDataMap) {
					error = new StringBuilder();
					error.append(dataMap.toString());
					StringBuilder data = new StringBuilder();
					for (var conf : listaConfReporteTXT) {
						error.append(conf.getKeyHeader());
						error.append(":");
						Object value = null;
						value = dataMap.get(conf.getKeyHeader());
						if (StringUtil.isNullOrEmpty(value)) {
							value = "";
						}
						if (!propiedadesMap.containsKey("NO_COMPLETE_DATA")) {
							value = completarDatos(value, conf, propiedadesMap);
						}
						data.append(value);
					}
					data.append("\r\n");
					osw.write(data.toString());

					contador++;
					if (contador == 2000) {
						contador = 0;
						osw.flush();
					}
					error = null;
				}
				osw.flush();
			}
		} catch (Exception ex) {
			if (error != null && error.toString().length() > 0) {
				log.error("Error.generarTXT==> " + error.toString() + "==> " + ex.getMessage());
			}
			log.error("generarTXTMap", ex);
		} finally {
			if (osw != null) {
				try {
					osw.close();
				} catch (Exception e) {
					log.error("generarTXTMap", e);
				}
			}
		}
	}

	private static Object completarDatos(Object data, ConfiguracionReporteTxtDTO objConf,
			Map<String, Object> propiedadesMap) throws IOException {
		Object resultado = null;
		if (propiedadesMap.containsKey(objConf.getKeyHeader())
				&& propiedadesMap.get(objConf.getKeyHeader()) instanceof Map) {
			Map<String, Object> traduccionMap = (Map<String, Object>) propiedadesMap.get(objConf.getKeyHeader());
			if (traduccionMap.containsKey(data.toString())) {
				data = traduccionMap.get(data.toString());
			} else {
				data = traduccionMap.get(data.toString() + "_${DEFAULT}");
			}
		}
		if (propiedadesMap.containsKey(objConf.getKeyHeader() + "_${FORMAT}")) {
			data = data.toString().substring(0, objConf.getFormato().length());
		}

		String charsetVal = DEFAULT_CHARSET;
		if (propiedadesMap.containsKey(CHARSETVAL)) {
			charsetVal = (String) propiedadesMap.get(CHARSETVAL);
		}

		if (TIPO_DATO_NUMERO.equalsIgnoreCase(objConf.getTipoDato())) {
			if (StringUtil.isNullOrEmpty(data)) {
				data = "0.00";
			}
			BigDecimal bigDecimal = new BigDecimal(data.toString());
			if (objConf.getLogitudDecimal().intValue() == 0) {
				bigDecimal = NumerosUtil.redondear(bigDecimal, 0, RoundingMode.HALF_DOWN);
			} else {
				bigDecimal = NumerosUtil.redondear(bigDecimal, objConf.getLogitudDecimal().intValue(),
						RoundingMode.HALF_UP);
			}
			resultado = bigDecimal.toString();
			if (propiedadesMap.containsKey(CHARSET)) {
				resultado = new String(bigDecimal.toString().getBytes(charsetVal),
						(String) propiedadesMap.get(CHARSET));
			}
			resultado = StringUtil.completeLeft(resultado.toString(), ' ', objConf.getLogitudColumna().intValue());
			String formatoData = resultado.toString().replace(".", "");
			resultado = formatoData;

		} else if (TIPO_DATO_FECHA.equalsIgnoreCase(objConf.getTipoDato())) {
			if (!StringUtil.isNullOrEmpty(objConf.getFormato())) {
				data = FechaUtil.obtenerFechaFormatoPersonalizado((OffsetDateTime) data, objConf.getFormato());
			}
			if (propiedadesMap.containsKey(CHARSET)) {
				data = new String(data.toString().getBytes(charsetVal), (String) propiedadesMap.get(CHARSET));
			}
			if (data.toString().length() > objConf.getLogitudColumna()) {
				data = data.toString().substring(0, objConf.getLogitudColumna().intValue());
			}

			String formato = "%-" + objConf.getLogitudColumna() + "s";
			resultado = String.format(formato, data.toString());
		} else {
			if (propiedadesMap.containsKey(CHARSET)) {
				data = new String(data.toString().getBytes(charsetVal), (String) propiedadesMap.get(CHARSET));
			}
			if (data.toString().length() > objConf.getLogitudColumna()) {
				data = data.toString().substring(0, objConf.getLogitudColumna().intValue());
			}

			String formato = "%-" + objConf.getLogitudColumna() + "s";
			resultado = String.format(formato, data.toString());
		}

		return resultado;
	}
}
