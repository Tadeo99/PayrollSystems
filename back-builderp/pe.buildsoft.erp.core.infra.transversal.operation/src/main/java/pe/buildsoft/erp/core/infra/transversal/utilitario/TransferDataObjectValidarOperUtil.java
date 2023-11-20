package pe.buildsoft.erp.core.infra.transversal.utilitario;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;

import pe.buildsoft.erp.core.infra.transversal.entidades.vo.AtributoEntityVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ValueDataVO;
import pe.buildsoft.erp.core.infra.transversal.type.RespuestaNaturalType;

/**
 * La Class TransferDataObjectValidarUtil.
 * <ul>
 * <li>Copyright 2014 MAPFRE- mapfre. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0,27/10/2016
 * @since BuildErp v1.0
 */
public class TransferDataObjectValidarOperUtil extends TransferDataUtil implements Serializable {
	private static final String NO_EXISTE_EN_LA_FILA2 = ") no existe en la fila ";

	private static final String NO_EXISTE_EN_LA_FILA = " no existe en la fila ";

	private static final String ERROR_AL_OBTENER_LA_INFORMACION_LA_POSICION = "${ERROR} al obtener la información, la posición ";

	private static final String SEPARADOR = ";";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** La Constante ARTIFICIO_CLASS. */
	public static final String ARTIFICIO_CLASS = "$class";

	/** La Constante CARACTER_EXTRANHO. */
	public static final String CARACTER_EXTRANHO = "ï»¿";

	public static void defaultLocaleProcess() {
		Locale.setDefault(Locale.US);
	}

	public static boolean validarDataExel(HSSFRow hssfRow, Map<String, Object> campoMappingExcelMap) {
		boolean resultado = false;
		for (var objAtr : campoMappingExcelMap.entrySet()) {
			int index = Integer.parseInt(objAtr.getValue() + "");
			if (index < 0) {
				continue;
			}
			try {
				HSSFCell hssfCell = hssfRow.getCell(index);
				hssfCell.setCellType(CellType.STRING);
				if (!hssfCell.toString().trim().equals("")) {
					resultado = true;
					break;
				}
			} catch (Exception e) {
				resultado = false;
			}
		}
		return resultado;
	}

	public static boolean validarDataExel(XSSFRow hssfRow, Map<String, Object> campoMappingExcelMap) {
		boolean resultado = false;
		for (var objAtr : campoMappingExcelMap.entrySet()) {
			int index = Integer.parseInt(objAtr.getValue() + "");
			if (index < 0) {
				continue;
			}
			try {
				XSSFCell hssfCell = hssfRow.getCell(index);
				hssfCell.setCellType(CellType.STRING);
				if (!hssfCell.toString().trim().equals("")) {
					resultado = true;
					break;
				}
			} catch (Exception e) {
				resultado = false;
			}
		}
		return resultado;
	}

	public static boolean validarCSV(String[] data, Map<String, Object> campoMappingCVSMap) {
		boolean resultado = false;
		for (var objAtr : campoMappingCVSMap.entrySet()) {
			int index = Integer.parseInt(objAtr.getValue() + "");
			try {
				String hssfCell = data[index];
				if (hssfCell.contains(CARACTER_EXTRANHO)) {
					hssfCell = hssfCell.substring(lengthCaracterExtranho());
				}
				if (hssfCell != null && !hssfCell.trim().equals("")) {
					resultado = true;
					break;
				}
			} catch (Exception e) {
				resultado = false;
			}
		}
		return resultado;
	}

	protected static StringBuilder generarKeyAgrupador(Map<String, ValueDataVO> resultadoTemp,
			Map<String, Character> configuracionTramaDetalleMap) {
		StringBuilder key = new StringBuilder();
		for (var mapValue : resultadoTemp.entrySet()) {
			// validar
			Character configuracionTramaDetalle = configuracionTramaDetalleMap.get(mapValue.getKey());
			boolean isAgrupador = RespuestaNaturalType.SI.getKey().equals(configuracionTramaDetalle);
			if (isAgrupador) {
				key.append(mapValue.getValue() + "");
			}
		}
		return key;
	}

	protected static boolean validarTXT(String data, Map<String, Object> campoMappingTXTMap) {
		boolean resultado = false;
		for (var objAtr : campoMappingTXTMap.entrySet()) {
			String index = objAtr.getValue() + "";
			try {
				String[] begin = index.split(SEPARADOR);
				int beginIndex = Integer.parseInt(begin[0]);
				if (beginIndex < 0) {
					continue;
				}
				int endIndex = Integer.parseInt(begin[1]);
				if (endIndex < 0) {
					continue;
				}
				// Inicio aumentado el nuevo requerimiento
				if (data.length() < endIndex) {
					endIndex = data.length();
				}
				// Fin aumentado el nuevo requerimiento
				String hssfCell = data.substring(beginIndex, endIndex);
				if (hssfCell.contains(CARACTER_EXTRANHO)) {
					hssfCell = hssfCell.substring(lengthCaracterExtranho());
				}
				if (hssfCell != null && !hssfCell.trim().equals("")) {
					resultado = true;
					break;
				}
			} catch (Exception e) {
				resultado = false;
			}
		}

		return resultado;
	}

	protected static boolean validarTXTCoordenada(String data, Map<String, Object> campoMappingTXTMap) {
		boolean resultado = false;
		for (var objAtr : campoMappingTXTMap.entrySet()) {
			String index = objAtr.getValue() + "";
			int filaData = 0;
			try {
				String[] begin = index.split(SEPARADOR);
				filaData = Integer.parseInt(begin[0]);
				if (filaData < 0) {
					continue;
				}
				int beginIndex = Integer.parseInt(begin[1]);
				if (beginIndex < 0) {
					continue;
				}
				int endIndex = Integer.parseInt(begin[2]);
				if (endIndex < 0) {
					continue;
				}
				// Inicio aumentado el nuevo requerimiento
				if (data.length() < endIndex) {
					endIndex = data.length();
				}
				// Fin aumentado el nuevo requerimiento
				String hssfCell = data.substring(beginIndex, endIndex);
				if (hssfCell.contains(CARACTER_EXTRANHO)) {
					hssfCell = hssfCell.substring(lengthCaracterExtranho());
				}
				if (hssfCell != null && !hssfCell.trim().equals("")) {
					resultado = true;
					break;
				}
			} catch (Exception e) {
				resultado = false;
			}
		}
		return resultado;
	}

	/**
	 * Obtener valor xls.
	 *
	 * @param hssfRow el hssf row
	 * @param index   el index
	 * @param objAt   el obj at
	 * @return the object
	 */
	protected static Object obtenerValorXls(HSSFRow hssfRow, int index, AtributoEntityVO objAt) {
		Object resultado = null;
		if (index < 0) {
			return resultado;
		}
		HSSFCell hssfCell = null;
		try {
			hssfCell = hssfRow.getCell(index);
			hssfCell.setCellType(CellType.STRING);
		} catch (Exception e) {
			// log.error("no existe index " + (index + 1) + " " + e.toString());
		}
		try {
			if (hssfCell != null && !hssfCell.toString().trim().equals("")) {
				resultado = obtenerValor(hssfCell.toString(), objAt, false);
			}
		} catch (Exception e) {
			// log.error("no se puede parsear a " + objAt.getClasssAtributo() + " obtenido
			// de la columna : " + (index + 1)+ " --> " + e.toString());
		}
		return resultado;
	}

	/**
	 * Obtener valor xlsx.
	 *
	 * @param hssfRow el hssf row
	 * @param format  the format
	 * @param index   el index
	 * @param objAt   el obj at
	 * @return the object
	 */
	public static Object obtenerValorXlsx(XSSFRow hssfRow, String formatoFecha, int index, AtributoEntityVO objAt) {
		Object resultado = null;
		if (index < 0) {
			return resultado;
		}
		XSSFCell hssfCell = null;
		try {
			hssfCell = hssfRow.getCell(index);
			if (StringUtil.isNullOrEmpty(formatoFecha)) {
				hssfCell.setCellType(CellType.STRING);
			}
		} catch (Exception e) {
			// log.error("no existe index " + (index + 1) + " " + e.toString());
		}
		try {
			if (hssfCell != null && !hssfCell.toString().trim().equals("")) {
				resultado = obtenerValor(hssfCell.toString(), formatoFecha, objAt, false);
			}
		} catch (Exception e) {
			// log.error("no se puede parsear a " + objAt.getClasssAtributo() + " obtenido
			// de la columna : " + (index + 1)+ " --> " + e.toString());
		}
		return resultado;
	}

	/**
	 * Obtener value csv.
	 *
	 * @param hssfRow      el hssf row
	 * @param index        el index
	 * @param objAt        el obj at
	 * @param formatoFecha el formato fecha
	 * @param filaData     el fila data
	 * @param parametroMap el parametro map
	 * @return the value data vo
	 */
	protected static ValueDataVO obtenerValueCSV(String[] hssfRow, int index, String objAt, String formatoFecha,
			int filaData, Map<String, Object> parametroMap) {
		ValueDataVO resultado = new ValueDataVO();
		resultado.setFila("" + (filaData));
		if (index < 0) {
			return resultado;
		}
		String hssfCell = null;
		try {
			hssfCell = hssfRow[index];
			if (hssfCell.contains(CARACTER_EXTRANHO)) {
				hssfCell = hssfCell.substring(lengthCaracterExtranho());
			}
		} catch (Exception e) {
			if (ResourceUtil.esSimulacion(parametroMap.get(ConstanteConfiguracionTramaUtil.ES_SIMULACION))) {
				resultado.setData(ERROR_AL_OBTENER_LA_INFORMACION_LA_POSICION + (index + 1) + NO_EXISTE_EN_LA_FILA);
			} else {
				resultado.setData("${ERROR}:Posición(" + (index + 1) + ") no existe en la fila : " + (filaData));
			}
		}
		try {
			if (hssfCell != null && !hssfCell.trim().equals("")) {
				resultado = obtenerValueParse(hssfCell, objAt, formatoFecha, filaData, parametroMap);
			}
		} catch (Exception e) {
			if (ResourceUtil.esSimulacion(parametroMap.get(ConstanteConfiguracionTramaUtil.ES_SIMULACION))) {
				resultado.setData(ERROR_AL_OBTENER_LA_INFORMACION_LA_POSICION + (index + 1) + NO_EXISTE_EN_LA_FILA);
			} else {
				resultado.setData("${ERROR}:Posición(" + (index + 1) + ") no existe  en la fila : " + (filaData));
			}
		}
		return resultado;
	}

	/**
	 * Obtener value posicion.
	 *
	 * @param data         el data
	 * @param index        el index
	 * @param objAt        el obj at
	 * @param formatoFecha el formato fecha
	 * @param filaData     el fila data
	 * @return the value data vo
	 */
	protected static ValueDataVO obtenerValuePosicion(String data, String index, String objAt, String formatoFecha,
			int filaData, Map<String, Object> parametroMap) {
		ValueDataVO resultado = new ValueDataVO();
		resultado.setFila((filaData) + "");
		String hssfCell = null;
		String vIindex = "";
		try {
			String[] begin = index.split(SEPARADOR);
			int beginIndex = Integer.parseInt(begin[0]);
			if (beginIndex < 0) {
				return resultado;
			}
			vIindex = "" + (beginIndex + 1);
			int endIndex = Integer.parseInt(begin[1]);
			if (endIndex < 0) {
				return resultado;
			}
			vIindex = vIindex + SEPARADOR + (endIndex + 1);
			// Inicio aumentado el nuevo requerimiento
			if (data.length() < endIndex) {
				endIndex = data.length();
			}
			// Fin aumentado el nuevo requerimiento
			hssfCell = data.substring(beginIndex, endIndex);
			if (hssfCell.contains(CARACTER_EXTRANHO)) {
				hssfCell = hssfCell.substring(lengthCaracterExtranho());
			}
		} catch (Exception e) {
			if (ResourceUtil.esSimulacion(parametroMap.get(ConstanteConfiguracionTramaUtil.ES_SIMULACION))) {
				resultado.setData(ERROR_AL_OBTENER_LA_INFORMACION_LA_POSICION + recortarCadenaValuePosicion(vIindex)
						+ NO_EXISTE_EN_LA_FILA);
			} else {
				resultado.setData("${ERROR}:Posición(" + (vIindex) + NO_EXISTE_EN_LA_FILA2 + (filaData));
			}
		}
		try {
			if (hssfCell != null && !hssfCell.trim().equals("")) {
				resultado = obtenerValueParse(hssfCell, objAt, formatoFecha, filaData, parametroMap);
			}
		} catch (Exception e) {
			if (ResourceUtil.esSimulacion(parametroMap.get(ConstanteConfiguracionTramaUtil.ES_SIMULACION))) {
				resultado.setData(ERROR_AL_OBTENER_LA_INFORMACION_LA_POSICION + recortarCadenaValuePosicion(vIindex)
						+ NO_EXISTE_EN_LA_FILA);
			} else {
				resultado.setData("${ERROR}:Posición(" + (index + 1) + NO_EXISTE_EN_LA_FILA2 + (filaData));
			}

		}
		return resultado;
	}

	/**
	 * Obtener coordenada valor.
	 *
	 * @param dataList     el data list
	 * @param dataValue    el data value
	 * @param index        el index
	 * @param objAt        el obj at
	 * @param formatoFecha el formato fecha
	 * @param isCabecera   el is cabecera
	 * @return the value data vo
	 */
	protected static ValueDataVO obtenerCoordenadaValor(List<String> dataList, String dataValue, String index,
			String objAt, String formatoFecha, boolean isCabecera, Map<String, Object> parametroMap) {
		ValueDataVO resultado = new ValueDataVO();
		String hssfCell = null;
		int filaData = 0;
		String vIindex = "";
		try {
			String[] begin = index.split(SEPARADOR);
			filaData = Integer.parseInt(begin[0]);
			if (filaData < 0) {
				return resultado;
			}
			vIindex = "" + (filaData);
			int beginIndex = Integer.parseInt(begin[1]);
			if (beginIndex < 0) {
				return resultado;
			}
			vIindex = vIindex + SEPARADOR + (beginIndex + 1);
			int endIndex = Integer.parseInt(begin[2]);
			if (endIndex < 0) {
				return resultado;
			}
			vIindex = vIindex + SEPARADOR + (endIndex + 1);
			if (isCabecera) {
				if (dataList.size() >= filaData) {
					String data = dataList.get(filaData);
					// Inicio aumentado el nuevo requerimiento
					if (data.length() < endIndex) {
						endIndex = data.length();
					}
					// Fin aumentado el nuevo requerimiento
					hssfCell = data.substring(beginIndex, endIndex);
					if (hssfCell.contains(CARACTER_EXTRANHO)) {
						hssfCell = hssfCell.substring(lengthCaracterExtranho());
					}
				}

			} else {
				// Inicio aumentado el nuevo requerimiento
				if (dataValue.length() < endIndex) {
					endIndex = dataValue.length();
				}
				// Fin aumentado el nuevo requerimiento
				hssfCell = dataValue.substring(beginIndex, endIndex);
				if (hssfCell.contains(CARACTER_EXTRANHO)) {
					hssfCell = hssfCell.substring(lengthCaracterExtranho());
				}
			}
			resultado.setFila((filaData + 1) + ""); // SE SUMA + 1 PARA PRESENTAR AL USUARIO

		} catch (Exception e) {
			resultado.setFila((filaData + 1) + ""); // SE SUMA + 1 PARA PRESENTAR AL USUARIO
			if (ResourceUtil.esSimulacion(parametroMap.get(ConstanteConfiguracionTramaUtil.ES_SIMULACION))) {
				resultado.setData(ERROR_AL_OBTENER_LA_INFORMACION_LA_POSICION + recortarCadenaCoordenadaValor(vIindex)
						+ NO_EXISTE_EN_LA_FILA);
			} else {
				resultado.setData("${ERROR}:Posición(" + (vIindex) + NO_EXISTE_EN_LA_FILA2 + (filaData + 1));
			}
		}
		try {
			if (hssfCell != null && !hssfCell.trim().equals("")) {
				resultado = obtenerValueParse(hssfCell, objAt, formatoFecha, filaData, parametroMap);
			}
		} catch (Exception e) {
			if (ResourceUtil.esSimulacion(parametroMap.get(ConstanteConfiguracionTramaUtil.ES_SIMULACION))) {
				resultado.setData(ERROR_AL_OBTENER_LA_INFORMACION_LA_POSICION + recortarCadenaCoordenadaValor(vIindex)
						+ NO_EXISTE_EN_LA_FILA);
			} else {
				resultado.setData("${ERROR}:Posición(" + (index + 1) + NO_EXISTE_EN_LA_FILA2 + (filaData));
			}

		}
		return resultado;
	}

	/**
	 * Obtener valor xls.
	 *
	 * @param hssfRow      el hssf row
	 * @param index        el index
	 * @param objAt        el obj at
	 * @param formatoFecha el formato fecha
	 * @param filaData     el fila data
	 * @return the object
	 */
	protected static ValueDataVO obtenerValorXls(HSSFRow hssfRow, int index, String objAt, String formatoFecha,
			int filaData, Map<String, Object> parametroMap) {
		ValueDataVO resultado = new ValueDataVO();
		resultado.setFila("" + (filaData));
		if (index < 0) {
			return resultado;
		}
		HSSFCell hssfCell = null;
		try {
			hssfCell = hssfRow.getCell(index);
			if (StringUtil.isNullOrEmpty(formatoFecha)) {
				hssfCell.setCellType(CellType.STRING);
			}
		} catch (Exception e) {
			resultado.setFila((filaData) + "");
			if (ResourceUtil.esSimulacion(parametroMap.get(ConstanteConfiguracionTramaUtil.ES_SIMULACION))) {
				resultado.setData(ERROR_AL_OBTENER_LA_INFORMACION_LA_POSICION + (index + 1) + NO_EXISTE_EN_LA_FILA);
			} else {
				resultado.setData("${ERROR}:Posición(" + (index + 1) + NO_EXISTE_EN_LA_FILA2 + (filaData));
			}
		}
		try {
			if (hssfCell != null && !hssfCell.toString().trim().equals("")) {
				resultado = obtenerValueParse(hssfCell.toString(), objAt, formatoFecha, filaData, parametroMap);
			}
		} catch (Exception e) {
			if (ResourceUtil.esSimulacion(parametroMap.get(ConstanteConfiguracionTramaUtil.ES_SIMULACION))) {
				resultado.setData(ERROR_AL_OBTENER_LA_INFORMACION_LA_POSICION + (index + 1) + NO_EXISTE_EN_LA_FILA);
			} else {
				resultado.setData("${ERROR}:Posición(" + (index + 1) + NO_EXISTE_EN_LA_FILA2 + (filaData));
			}
		}
		return resultado;
	}

	/**
	 * Obtener valor xlsx.
	 *
	 * @param hssfRow      el hssf row
	 * @param index        el index
	 * @param objAt        el obj at
	 * @param formatoFecha el formato fecha
	 * @param filaData     el fila data
	 * @return the object
	 */
	protected static ValueDataVO obtenerValorXlsx(XSSFRow hssfRow, int index, String objAt, String formatoFecha,
			int filaData, Map<String, Object> parametroMap) {
		ValueDataVO resultado = new ValueDataVO();
		resultado.setFila("" + (filaData));
		if (index < 0) {
			return resultado;
		}
		XSSFCell hssfCell = null;
		try {
			hssfCell = hssfRow.getCell(index);
			if (StringUtil.isNullOrEmpty(formatoFecha) && hssfCell != null) {
				hssfCell.setCellType(CellType.STRING);
			}
		} catch (Exception e) {
			resultado.setFila((filaData) + "");
			if (ResourceUtil.esSimulacion(parametroMap.get(ConstanteConfiguracionTramaUtil.ES_SIMULACION))) {
				resultado.setData(ERROR_AL_OBTENER_LA_INFORMACION_LA_POSICION + (index + 1) + NO_EXISTE_EN_LA_FILA);
			} else {
				resultado.setData("${ERROR}:Posición(" + (index + 1) + NO_EXISTE_EN_LA_FILA2 + (filaData));
			}
		}
		try {
			if (hssfCell != null && !hssfCell.toString().trim().equals("")) {
				resultado = obtenerValueParse(hssfCell.toString(), objAt, formatoFecha, filaData, parametroMap);
			}
		} catch (Exception e) {
			if (ResourceUtil.esSimulacion(parametroMap.get(ConstanteConfiguracionTramaUtil.ES_SIMULACION))) {
				resultado.setData(ERROR_AL_OBTENER_LA_INFORMACION_LA_POSICION + (index + 1) + NO_EXISTE_EN_LA_FILA);
			} else {
				resultado.setData("${ERROR}:Posición(" + (index + 1) + NO_EXISTE_EN_LA_FILA2 + (filaData));
			}
		}
		return resultado;
	}

	/**
	 * Obtener value parse.
	 *
	 * @param resultadoValor el resultado valor
	 * @param objAt          el obj at
	 * @param formatoFecha   el formato fecha
	 * @param filaData       el fila data
	 * @return the object
	 * @throws Exception the exception
	 */
	public static ValueDataVO obtenerValueParse(String resultadoValor, String objAt, String formatoFecha, int filaData,
			Map<String, Object> parametroMap) throws Exception {
		ValueDataVO resultado = new ValueDataVO();
		resultado.setFila("" + (filaData != 0 ? filaData : ""));
		try {
			if (StringUtil.isNullOrEmpty(resultadoValor)) {
				return null;
			}
			resultadoValor = StringUtil.quitarCaracterExtranio(resultadoValor);
			resultadoValor = resultadoValor.trim();
			if (objAt.equals(Boolean.class.getName())) {
				resultado.setData(Boolean.valueOf(resultadoValor));
			} else if (objAt.equals(Integer.class.getName())) {
				resultado.setData(Integer.parseInt(resultadoValor));
			} else if (objAt.equals(Float.class.getName())) {
				resultado.setData(Float.parseFloat(resultadoValor));
			} else if (objAt.equals(Double.class.getName())) {
				resultado.setData(Double.parseDouble(resultadoValor));
			} else if (objAt.equals(Long.class.getName())) {
				resultado.setData(Long.parseLong(resultadoValor));
			} else if (objAt.equals(BigDecimal.class.getName())) {
				// convirtiendo datos numericos fomateados
				resultadoValor = Utilitario.reingenieriaFormateoNumerico(resultadoValor);
				resultado.setData(new BigDecimal(resultadoValor));
			} else if (objAt.equals(Character.class.getName())) {
				resultado.setData(resultadoValor.charAt(0));
			} else if (objAt.equals(OffsetDateTime.class.getName())) {
				resultado.setData(FechaUtil.obtenerFechaFormatoPersonalizado(resultadoValor, formatoFecha));
			} else if (objAt.equals(Collection.class.getName())) {
				resultado = null;
			} else {
				resultado.setData(resultadoValor);
			}
		} catch (Exception e) {

			if (ResourceUtil.esSimulacion(parametroMap.get(ConstanteConfiguracionTramaUtil.ES_SIMULACION))) {
				resultado.setData("${ERROR}${type} El tipo de dato del campo no corresponde a lo configurado");// +
																												// resultadoValor
																												// + "
			} else {
				if (parametroMap.containsKey("CARGA_TRAMA_PER")) {
					resultado.setData("${ERROR}${type}" + resultadoValor);
				} else {
					resultado.setData("${ERROR} :Tipo de Conversión no valido ${type} --> " + resultadoValor
							+ " en la fila " + (filaData));
				}
			}
			return resultado;
		}
		return resultado;
	}

}
