package pe.buildsoft.erp.core.infra.transversal.utilitario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pe.buildsoft.erp.core.infra.transversal.cache.ConfiguracionCacheUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.AtributoEntityVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ValueDataVO;

/**
 * La Class TransferDataOperUtil.
 * <ul>
 * <li>Copyright 2014 MAPFRE- mapfre. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Tue Apr 29 17:13:19 COT 2014
 * @since BuildErp v1.0
 */
public class TransferDataOperUtil extends TransferDataObjectValidarOperUtil implements Serializable {

	private static final String CANTIDAD_DATA = "cantidadData";

	private static final String FILA_DATA = "filaData";

	private static final String CAMPO_MAPPING_FORMATO_SUB_OBJECT_MAP = "campoMappingFormatoSubObjectMap";

	private static final String CAMPO_MAPPING_EXCEL_SUB_OBJECT_MAP = "campoMappingExcelSubObjectMap";

	private static final String GRUPO_SUB_OBJECTO_MAP = "grupoSubObjectoMap";

	private static final String DEFAULT_CHARSET = "UTF-8";

	/** La Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El log. */
	private static Logger log = LoggerFactory.getLogger(TransferDataOperUtil.class);

	/**
	 * Instancia un nuevo data export excel.
	 */
	public TransferDataOperUtil() {
		//
	}

	/**
	 * Transfer objeto entity trama.
	 *
	 * @param <T>         el tipo generico
	 * @param ressul      el ressul
	 * @param entityClass el entity class
	 * @return the t
	 */
	public static <T> T toTrama(Map<String, ValueDataVO> ressul, Class<T> entityClass) {
		try {
			if (ressul == null) {
				return null;
			}
			T resultado = entityClass.getDeclaredConstructor().newInstance();
			List<AtributoEntityVO> listaAtributos = obtenerListaAtributos(
					entityClass.getName().replace("DTO", "").replace("dto", "jpa"));
			for (var objAtr : listaAtributos) {
				if (ressul.containsKey(objAtr.getNombreColumna())) {
					Field f = getField(resultado, objAtr.getNombreAtributo());
					Object value = null;
					if (objAtr.getClasssAtributoType().isAssignableFrom(OffsetDateTime.class)) {
						value = ressul.get(objAtr.getNombreColumna()).getData();
					} else {
						value = obtenerValor(ressul.get(objAtr.getNombreColumna()).getData() + "", objAtr, false);
					}
					try {
						setField(f, resultado, value);
					} catch (Exception e) {
						//
					}
				}
			}
			return resultado;
		} catch (Exception e) {
			log.error(
					"Error TransferDataOperUtil.toTrama() al parsear " + entityClass.getName() + "  " + e.getMessage());
		}
		return null;
	}

	/**
	 * Metodo que trasnfiere datos de un mapa a un objeto por el atributo valor de
	 * la clase.
	 *
	 * @param <T>                 el tipo generico
	 * @param listaObjectValueMap el lista object value map
	 * @param entityClass         el entity class
	 * @return the t
	 */
	public static <T> T toVOTrama(Map<String, Map<String, Object>> listaObjectValueMap, Class<T> entityClass) {
		try {
			Map<String, Object> ressul = listaObjectValueMap.get(entityClass.getName());
			if (ressul == null) {
				return entityClass.getDeclaredConstructor().newInstance();
			}
			T resultado = entityClass.getDeclaredConstructor().newInstance();
			List<AtributoEntityVO> listaAtributos = obtenerListaAtributos(entityClass);
			for (var objAtr : listaAtributos) {
				if (ressul.containsKey(objAtr.getNombreAtributo())) {
					try {
						Field f = getField(resultado, objAtr.getNombreAtributo());
						Object value = obtenerValor(ressul.get(objAtr.getNombreAtributo()) + "", objAtr, true);
						if (value != null) {
							if (ARTIFICIO_CLASS.equals(value.toString())) {
								if (listaObjectValueMap.containsKey(objAtr.getClasssAtributoType().getName())) {
									value = toVOTrama(listaObjectValueMap, objAtr.getClasssAtributoType());
									setField(f, resultado, value);
								}
							} else {
								setField(f, resultado, value);
							}
						}
					} catch (Exception e) {
						//
					}

				}
			}
			return resultado;
		} catch (Exception e) {
			log.error("Error TransferDataOperUtil.toVOTrama() al parsear " + entityClass.getName() + "  "
					+ e.getMessage());
		}
		return null;
	}

	/**
	 * Transfer objeto entity dto.
	 *
	 * @param <T>                  el tipo generico
	 * @param campoMappingExcelMap el campo mapping excel map
	 * @param cellDataList         el cell data list
	 * @param entityClassDTO       el entity class dto
	 * @return the t
	 */
	public static <T> List<T> toXlsDTO(Map<String, Integer> campoMappingExcelMap, HSSFWorkbook workBook,
			Class<T> entityClassDTO, int hoja, int filaData) {
		return toXlsDTO(campoMappingExcelMap, workBook, new HashMap<String, String>(), new HashMap<String, Object>(),
				entityClassDTO, hoja, filaData);
	}

	public static <T> T toXlsDTO(Map<String, Integer> campoMappingExcelMap, HSSFRow hssfRowData,
			Map<String, String> campoMappingFormatoMap, Map<String, Object> parametroMap, Class<T> entityClassDTO,
			int hoja, int filaData) {
		T resultado = null;
		try {
			resultado = entityClassDTO.getDeclaredConstructor().newInstance();
			if (hssfRowData == null) {
				return resultado;
			}
			List<AtributoEntityVO> listaAtributos = obtenerListaAtributos(entityClassDTO);
			Map<String, Object> resulObjectComplejo = obtenerAtributosComplejo(campoMappingExcelMap,
					campoMappingFormatoMap);
			Map<String, String> grupoSubObjectoMap = (Map<String, String>) resulObjectComplejo
					.get(GRUPO_SUB_OBJECTO_MAP);
			Map<String, Map<String, Integer>> campoMappingExcelSubObjectMap = (Map<String, Map<String, Integer>>) resulObjectComplejo
					.get(CAMPO_MAPPING_EXCEL_SUB_OBJECT_MAP);
			Map<String, Map<String, String>> campoMappingFormatoSubObjectMap = (Map<String, Map<String, String>>) resulObjectComplejo
					.get(CAMPO_MAPPING_FORMATO_SUB_OBJECT_MAP);

			for (var objAtr : listaAtributos) {
				if (campoMappingExcelMap.containsKey(objAtr.getNombreAtributo())) {
					Field f = getField(resultado, objAtr.getNombreAtributo());
					Object value = obtenerValorXls(hssfRowData, campoMappingExcelMap.get(objAtr.getNombreAtributo()),
							objAtr);
					setField(f, resultado, value);
				}
			}
			for (var subObject : grupoSubObjectoMap.entrySet()) {
				Field f = getField(resultado, subObject.getKey());
				if (f != null) {
					Object valueTransfer = f.get(resultado);
					if (valueTransfer == null) {
						valueTransfer = f.getType().getDeclaredConstructor().newInstance();
					}
					Object value = toXlsDTO(campoMappingExcelSubObjectMap.get(subObject.getKey()), hssfRowData,
							campoMappingFormatoSubObjectMap.get(subObject.getKey()), parametroMap, f.getType(), hoja,
							filaData);
					setField(f, resultado, value);
				}
			}

		} catch (Exception e) {
			log.error("Error TransferDataOperUtil.toXlsDTO() al parsear " + entityClassDTO.getName() + "  "
					+ e.getMessage());
		}
		return resultado;
	}

	private static Map<String, Object> obtenerAtributosComplejo(Map<String, Integer> campoMappingExcelMap,
			Map<String, String> campoMappingFormatoMap) {
		Map<String, Object> resultado = new HashMap<>();
		Map<String, String> grupoSubObjectoMap = new HashMap<>();
		Map<String, Map<String, Integer>> campoMappingExcelSubObjectMap = new HashMap<>();
		Map<String, Map<String, String>> campoMappingFormatoSubObjectMap = new HashMap<>();
		for (var subKey : campoMappingExcelMap.keySet()) {
			if (subKey.contains(".")) {
				String subKeyProcesar = subKey.replace(".", ";");
				String[] subKeys = subKeyProcesar.split(";", -1);
				if (subKeys != null) {
					String subObjecto = subKeys[0];
					if (!grupoSubObjectoMap.containsKey(subObjecto)) {
						Map<String, Integer> campoMappingValue = new HashMap<>();
						String abributoKey = subKey.substring(subObjecto.length() + 1);
						campoMappingValue.put(abributoKey, campoMappingExcelMap.get(subKey));// aqui se puede hacer
																								// recursivo
																								// subObject.obj.obj2.atributo
						Map<String, String> campoMappingFormatoValue = new HashMap<>();
						campoMappingFormatoValue.put(abributoKey, campoMappingFormatoMap.get(subKey));
						grupoSubObjectoMap.put(subObjecto, subObjecto);
						campoMappingExcelSubObjectMap.put(subObjecto, campoMappingValue);
						campoMappingFormatoSubObjectMap.put(subObjecto, campoMappingFormatoValue);
					} else {
						Map<String, Integer> campoMappingValue = campoMappingExcelSubObjectMap.get(subObjecto);
						String abributoKey = subKey.substring(subObjecto.length() + 1);
						campoMappingValue.put(abributoKey, campoMappingExcelMap.get(subKey));
						Map<String, String> campoMappingFormatoValue = campoMappingFormatoSubObjectMap.get(subObjecto);
						campoMappingFormatoValue.put(abributoKey, campoMappingFormatoMap.get(subKey));
						grupoSubObjectoMap.put(subObjecto, subObjecto);
						campoMappingExcelSubObjectMap.put(subObjecto, campoMappingValue);
						campoMappingFormatoSubObjectMap.put(subObjecto, campoMappingFormatoValue);
					}
				}

			}
		}
		resultado.put(GRUPO_SUB_OBJECTO_MAP, grupoSubObjectoMap);
		resultado.put(CAMPO_MAPPING_EXCEL_SUB_OBJECT_MAP, campoMappingExcelSubObjectMap);
		resultado.put(CAMPO_MAPPING_FORMATO_SUB_OBJECT_MAP, campoMappingFormatoSubObjectMap);
		return resultado;
	}

	public static <T> List<T> toXlsDTO(Map<String, Integer> campoMappingExcelMap, HSSFWorkbook workBook,
			Map<String, String> campoMappingFormatoMap, Map<String, Object> parametroMap, Class<T> entityClassDTO,
			int hoja, int filaData) {
		List<T> resultado = new ArrayList<>();
		try {
			if (workBook == null) {
				return resultado;
			}
			List<AtributoEntityVO> listaAtributos = obtenerListaAtributos(entityClassDTO);
			Map<String, Object> resulObjectComplejo = obtenerAtributosComplejo(campoMappingExcelMap,
					campoMappingFormatoMap);
			Map<String, String> grupoSubObjectoMap = (Map<String, String>) resulObjectComplejo
					.get(GRUPO_SUB_OBJECTO_MAP);
			Map<String, Map<String, Integer>> campoMappingExcelSubObjectMap = (Map<String, Map<String, Integer>>) resulObjectComplejo
					.get(CAMPO_MAPPING_EXCEL_SUB_OBJECT_MAP);
			Map<String, Map<String, String>> campoMappingFormatoSubObjectMap = (Map<String, Map<String, String>>) resulObjectComplejo
					.get(CAMPO_MAPPING_FORMATO_SUB_OBJECT_MAP);
			HSSFSheet hssfSheet = workBook.getSheetAt(hoja - 1);
			Iterator rowIterator = (Iterator) hssfSheet.rowIterator();
			int contador = 0;
			while (rowIterator.hasNext()) {
				contador++;
				HSSFRow hssfRow = (HSSFRow) rowIterator.next();
				if (contador >= filaData) {
					T resultadoTemp = entityClassDTO.getDeclaredConstructor().newInstance();
					for (var objAtr : listaAtributos) {
						if (campoMappingExcelMap.containsKey(objAtr.getNombreAtributo())) {
							Field f = getField(resultadoTemp, objAtr.getNombreAtributo());
							Object value = obtenerValorXls(hssfRow,
									campoMappingExcelMap.get(objAtr.getNombreAtributo()), objAtr);
							setField(f, resultadoTemp, value);
						}
					}
					for (var subObject : grupoSubObjectoMap.entrySet()) {
						Field f = getField(resultadoTemp, subObject.getKey());
						if (f != null) {
							Object valueTransfer = f.get(resultadoTemp);
							if (valueTransfer == null) {
								valueTransfer = f.getType().getDeclaredConstructor().newInstance();
							}
							Object value = toXlsDTO(campoMappingExcelSubObjectMap.get(subObject.getKey()), hssfRow,
									campoMappingFormatoSubObjectMap.get(subObject.getKey()), parametroMap, f.getType(),
									hoja, filaData);
							setField(f, resultadoTemp, value);
						}
					}
					resultado.add(resultadoTemp);
				}
			}
			if (workBook != null) {
				workBook.close();
			}
		} catch (Exception e) {
			log.error("Error TransferDataOperUtil.toXlsDTO() al parsear " + entityClassDTO.getName() + "  "
					+ e.getMessage());
		}
		return resultado;
	}

	/**
	 * Transfer objeto entity excel xlsx dto.
	 *
	 * @param <T>                  el tipo generico
	 * @param campoMappingExcelMap el campo mapping excel map
	 * @param cellDataList         el cell data list
	 * @param entityClassDTO       el entity class dto
	 * @return the list
	 */
	public static <T> List<T> toXlsxDTO(Map<String, Integer> campoMappingExcelMap, XSSFWorkbook workBook,
			Class<T> entityClassDTO, int hoja, int filaData) {
		return toXlsxDTO(campoMappingExcelMap, new HashMap<String, String>(), new HashMap<String, Object>(), workBook,
				entityClassDTO, hoja, filaData);
	}

	public static <T> T toXlsxDTO(Map<String, Integer> campoMappingExcelMap, Map<String, String> campoMappingFormatoMap,
			Map<String, Object> parametroMap, XSSFRow hssfRow, Class<T> entityClassDTO, int hoja, int filaData) {
		T resultado = null;
		try {
			resultado = entityClassDTO.getDeclaredConstructor().newInstance();
			if (hssfRow == null) {
				return resultado;
			}
			List<AtributoEntityVO> listaAtributos = obtenerListaAtributos(entityClassDTO);
			Map<String, Object> resulObjectComplejo = obtenerAtributosComplejo(campoMappingExcelMap,
					campoMappingFormatoMap);
			Map<String, String> grupoSubObjectoMap = (Map<String, String>) resulObjectComplejo
					.get(GRUPO_SUB_OBJECTO_MAP);
			Map<String, Map<String, Integer>> campoMappingExcelSubObjectMap = (Map<String, Map<String, Integer>>) resulObjectComplejo
					.get(CAMPO_MAPPING_EXCEL_SUB_OBJECT_MAP);
			Map<String, Map<String, String>> campoMappingFormatoSubObjectMap = (Map<String, Map<String, String>>) resulObjectComplejo
					.get(CAMPO_MAPPING_FORMATO_SUB_OBJECT_MAP);

			for (var objAtr : listaAtributos) {
				if (campoMappingExcelMap.containsKey(objAtr.getNombreAtributo())) {
					Field f = getField(resultado, objAtr.getNombreAtributo());
					Object value = obtenerValorXlsx(hssfRow, campoMappingFormatoMap.get(objAtr.getNombreAtributo()),
							campoMappingExcelMap.get(objAtr.getNombreAtributo()), objAtr);
					setField(f, resultado, value);
				}
			}
			for (var subObject : grupoSubObjectoMap.entrySet()) {
				Field f = getField(resultado, subObject.getKey());
				if (f != null) {
					Object valueTransfer = f.get(resultado);
					if (valueTransfer == null) {
						valueTransfer = f.getType().getDeclaredConstructor().newInstance();
					}
					Object value = toXlsxDTO(campoMappingExcelSubObjectMap.get(subObject.getKey()),
							campoMappingFormatoSubObjectMap.get(subObject.getKey()), parametroMap, hssfRow, f.getType(),
							hoja, filaData);
					setField(f, resultado, value);
				}
			}
		} catch (Exception e) {
			log.error("Error TransferDataOperUtil.toXlsxDTO() al parsear " + entityClassDTO.getName() + "  "
					+ e.getMessage());
		}
		return resultado;
	}

	public static <T> List<T> toXlsxDTO(Map<String, Integer> campoMappingExcelMap,
			Map<String, String> campoMappingFormatoMap, Map<String, Object> parametroMap, XSSFWorkbook workBook,
			Class<T> entityClassDTO, int hoja, int filaData) {
		List<T> resultado = new ArrayList<>();
		try {
			if (workBook == null) {
				return resultado;
			}
			List<AtributoEntityVO> listaAtributos = obtenerListaAtributos(entityClassDTO);
			Map<String, Object> resulObjectComplejo = obtenerAtributosComplejo(campoMappingExcelMap,
					campoMappingFormatoMap);
			Map<String, String> grupoSubObjectoMap = (Map<String, String>) resulObjectComplejo
					.get(GRUPO_SUB_OBJECTO_MAP);
			Map<String, Map<String, Integer>> campoMappingExcelSubObjectMap = (Map<String, Map<String, Integer>>) resulObjectComplejo
					.get(CAMPO_MAPPING_EXCEL_SUB_OBJECT_MAP);
			Map<String, Map<String, String>> campoMappingFormatoSubObjectMap = (Map<String, Map<String, String>>) resulObjectComplejo
					.get(CAMPO_MAPPING_FORMATO_SUB_OBJECT_MAP);
			XSSFSheet hssfSheet = workBook.getSheetAt(hoja - 1);
			Iterator<XSSFRow> rowIterator = (Iterator) hssfSheet.rowIterator();
			int contador = 0;
			while (rowIterator.hasNext()) {
				contador++;
				XSSFRow hssfRow = rowIterator.next();
				if (contador >= filaData) {
					T resultadoTemp = entityClassDTO.getDeclaredConstructor().newInstance();
					for (var objAtr : listaAtributos) {
						if (campoMappingExcelMap.containsKey(objAtr.getNombreAtributo())) {
							Field f = getField(resultadoTemp, objAtr.getNombreAtributo());
							Object value = obtenerValorXlsx(hssfRow,
									campoMappingFormatoMap.get(objAtr.getNombreAtributo()),
									campoMappingExcelMap.get(objAtr.getNombreAtributo()), objAtr);
							setField(f, resultadoTemp, value);
						}
					}
					for (var subObject : grupoSubObjectoMap.entrySet()) {
						Field f = getField(resultadoTemp, subObject.getKey());
						if (f != null) {
							Object valueTransfer = f.get(resultadoTemp);
							if (valueTransfer == null) {
								valueTransfer = f.getType().getDeclaredConstructor().newInstance();
							}
							Object value = toXlsxDTO(campoMappingExcelSubObjectMap.get(subObject.getKey()),
									campoMappingFormatoSubObjectMap.get(subObject.getKey()), parametroMap, hssfRow,
									f.getType(), hoja, filaData);
							setField(f, resultadoTemp, value);
						}
					}
					resultado.add(resultadoTemp);
				}

			}
			if (workBook != null) {
				workBook.close();
			}
		} catch (Exception e) {
			log.error("Error TransferDataOperUtil.toXlsxDTO() al parsear " + entityClassDTO.getName() + "  "
					+ e.getMessage());
		}
		return resultado;
	}

	/**
	 * Transfer objeto entity map dto.
	 *
	 * @param campoMappingExcelMap     el campo mapping excel map
	 * @param dataList                 el data list
	 * @param campoMappingExcelTypeMap el campo mapping excel type map
	 * @param campoMappingFormatoMap   el campo mapping formato map
	 * @return the t
	 */
	public static List<Map<String, ValueDataVO>> toXlsMap(Map<String, Object> campoMappingExcelMap,
			HSSFWorkbook workBook, Map<String, String> campoMappingExcelTypeMap,
			Map<String, String> campoMappingFormatoMap, Map<String, Object> parametroMap,
			Map<String, Character> configuracionTramaDetalleMap) {
		List<Map<String, ValueDataVO>> resultado = new ArrayList<>();
		if (campoMappingFormatoMap == null) {
			campoMappingFormatoMap = new HashMap<>();
		}
		Map<String, String> grupoMap = new HashMap<>();
		try {
			int hoja = (Integer) parametroMap.get("hoja");
			int filaData = (Integer) parametroMap.get(FILA_DATA);
			Integer cantidadData = (Integer) parametroMap.get(CANTIDAD_DATA);
			HSSFSheet hssfSheet = workBook.getSheetAt(hoja - 1);
			Iterator rowIterator = (Iterator) hssfSheet.rowIterator();
			int contador = 0;
			int contadorData = 0;
			int filaDataProcesar = 0;
			while (rowIterator.hasNext()) {
				filaDataProcesar++;
				contador++;
				HSSFRow hssfRow = (HSSFRow) rowIterator.next();
				if (contador >= filaData) {
					contadorData++;
					boolean isValido = validarDataExel(hssfRow, campoMappingExcelMap);
					if (isValido) {
						Map<String, ValueDataVO> resultadoTemp = new HashMap<>();
						for (var objAtr : campoMappingExcelMap.entrySet()) {
							ValueDataVO value = obtenerValorXls(hssfRow, Integer.parseInt(objAtr.getValue() + ""),
									campoMappingExcelTypeMap.get(objAtr.getKey()),
									campoMappingFormatoMap.get(objAtr.getKey()), filaDataProcesar, parametroMap);
							resultadoTemp.put(objAtr.getKey(), value);
						}
						StringBuilder key = generarKeyAgrupador(resultadoTemp, configuracionTramaDetalleMap);
						// para agrupar
						if (StringUtil.isNullOrEmpty(key)) {
							if (!grupoMap.containsKey(key.toString())) {
								resultado.add(resultadoTemp);
								grupoMap.put(key.toString(), null);
							} else {
								resultado.add(resultadoTemp);
								grupoMap.put(key.toString(), null);
							}
						} else {
							if (!grupoMap.containsKey(key.toString())) {
								resultado.add(resultadoTemp);
								grupoMap.put(key.toString(), null);
							}
						}
					} else {
						if (parametroMap.containsKey("NoValidarExcel")) {
							isValido = true;
						} else {
							break;
						}

					}

				}
				if ((cantidadData != null) && contadorData == cantidadData.intValue()) {
					break;
				}
			}
			if (CollectionUtil.isEmpty(resultado)) {
				filaDataProcesar++;
				HSSFWorkbook workbook = new HSSFWorkbook();
				HSSFSheet sheet = workbook.createSheet("data");
				HSSFRow hssfRow = sheet.createRow(0);
				Map<String, ValueDataVO> resultadoTemp = new HashMap<>();
				for (var objAtr : campoMappingExcelMap.entrySet()) {
					ValueDataVO value = obtenerValorXls(hssfRow, Integer.parseInt(objAtr.getValue() + ""),
							campoMappingExcelTypeMap.get(objAtr.getKey()), campoMappingFormatoMap.get(objAtr.getKey()),
							filaDataProcesar, parametroMap);
					resultadoTemp.put(objAtr.getKey(), value);
				}
				resultado.add(resultadoTemp);
				workbook.close();
			}
			if (workBook != null) {
				workBook.close();
			}
		} catch (Exception e) {
			log.error("Error TransferDataOperUtil.toXlsMap() al parsear " + e.getMessage());
		}
		return resultado;
	}

	/**
	 * Transfer objeto entity excel xlsx map dto.
	 *
	 * @param campoMappingExcelMap     el campo mapping excel map
	 * @param dataList                 el data list
	 * @param campoMappingExcelTypeMap el campo mapping excel type map
	 * @param campoMappingFormatoMap   el campo mapping formato map
	 * @return the list
	 */
	public static List<Map<String, ValueDataVO>> toXlsxMap(Map<String, Object> campoMappingExcelMap,
			XSSFWorkbook workBook, Map<String, String> campoMappingExcelTypeMap,
			Map<String, String> campoMappingFormatoMap, Map<String, Object> parametroMap,
			Map<String, Character> configuracionTramaDetalleMap) {
		List<Map<String, ValueDataVO>> resultado = new ArrayList<>();
		if (campoMappingFormatoMap == null) {
			campoMappingFormatoMap = new HashMap<>();
		}
		Map<String, String> grupoMap = new HashMap<>();
		try {
			int hoja = (Integer) parametroMap.get("hoja");
			int filaData = (Integer) parametroMap.get(FILA_DATA);
			Integer cantidadData = (Integer) parametroMap.get(CANTIDAD_DATA);
			XSSFSheet hssfSheet = workBook.getSheetAt(hoja - 1);
			Iterator<XSSFRow> rowIterator = (Iterator) hssfSheet.rowIterator();
			int contador = 0;
			int contadorData = 0;
			int filaDataProcesar = 0;
			while (rowIterator.hasNext()) {
				filaDataProcesar++;
				contador++;
				XSSFRow hssfRow = rowIterator.next();
				if (contador >= filaData) {
					contadorData++;
					boolean isValido = validarDataExel(hssfRow, campoMappingExcelMap);
					if (isValido) {
						Map<String, ValueDataVO> resultadoTemp = new HashMap<>();
						for (var objAtr : campoMappingExcelMap.entrySet()) {
							ValueDataVO value = obtenerValorXlsx(hssfRow, Integer.parseInt(objAtr.getValue() + ""),
									campoMappingExcelTypeMap.get(objAtr.getKey()),
									campoMappingFormatoMap.get(objAtr.getKey()), filaDataProcesar, parametroMap);
							resultadoTemp.put(objAtr.getKey(), value);
						}
						StringBuilder key = generarKeyAgrupador(resultadoTemp, configuracionTramaDetalleMap);
						// para agrupar
						if (StringUtil.isNullOrEmpty(key)) {
							if (!grupoMap.containsKey(key.toString())) {
								resultado.add(resultadoTemp);
								grupoMap.put(key.toString(), null);
							} else {
								resultado.add(resultadoTemp);
								grupoMap.put(key.toString(), null);
							}
						} else {
							if (!grupoMap.containsKey(key.toString())) {
								resultado.add(resultadoTemp);
								grupoMap.put(key.toString(), null);
							}
						}
					} else {
						break;
					}
				}
				if ((cantidadData != null) && contadorData == cantidadData.intValue()) {
					break;
				}
			}
			if (CollectionUtil.isEmpty(resultado)) {
				filaDataProcesar++;
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet sheet = workbook.createSheet("data");
				XSSFRow hssfRow = sheet.createRow(0);
				Map<String, ValueDataVO> resultadoTemp = new HashMap<>();
				for (var objAtr : campoMappingExcelMap.entrySet()) {
					ValueDataVO value = obtenerValorXlsx(hssfRow, Integer.parseInt(objAtr.getValue() + ""),
							campoMappingExcelTypeMap.get(objAtr.getKey()), campoMappingFormatoMap.get(objAtr.getKey()),
							filaDataProcesar, parametroMap);
					resultadoTemp.put(objAtr.getKey(), value);
				}
				resultado.add(resultadoTemp);
				workbook.close();
			}
			if (workBook != null) {
				workBook.close();
			}
		} catch (Exception e) {
			log.error("Error TransferDataOperUtil.toXlsxMap() al parsear " + e.getMessage());
		}
		return resultado;
	}

	/**
	 * Transfer objeto entity csv map dto.
	 *
	 * @param campoMappingCVSMap     el campo mapping cvs map
	 * @param dataList               el data list
	 * @param campoMappingCSVTypeMap el campo mapping csv type map
	 * @param campoMappingFormatoMap el campo mapping formato map
	 * @param parametroMap           el parametro map
	 * @return the list
	 */
	public static List<Map<String, ValueDataVO>> toCSVMap(Map<String, Object> campoMappingCVSMap, BufferedReader br,
			Map<String, String> campoMappingCSVTypeMap, Map<String, String> campoMappingFormatoMap,
			Map<String, Object> parametroMap, Map<String, Character> configuracionTramaDetalleMap) {
		List<Map<String, ValueDataVO>> resultado = new ArrayList<>();
		if (campoMappingFormatoMap == null) {
			campoMappingFormatoMap = new HashMap<>();
		}
		Map<String, String> grupoMap = new HashMap<>();
		try {
			String cvsSplitBy = (String) parametroMap.get("cvsSplitBy");
			int filaData = (Integer) parametroMap.get(FILA_DATA);
			Integer cantidadData = (Integer) parametroMap.get(CANTIDAD_DATA);
			int contador = 0;
			int contadorData = 0;
			String line = "";
			if (StringUtil.isNullOrEmpty(cvsSplitBy)) {
				cvsSplitBy = ",";
			}
			int filaDataProcesar = Integer
					.parseInt(parametroMap.get(ConstanteConfiguracionTramaUtil.FILA_DATA_ORIGINAL) + ""); // OBTIENE LA
																											// FILA DE
																											// LECTURA
																											// DEL
																											// ARCHIVO
																											// CONFIGURADO
			while ((line = br.readLine()) != null) {
				contador++;
				if (contador >= filaData) {
					contadorData++;
					String[] data = line.split(cvsSplitBy, -1);// -1 para leer
					boolean isValido = validarCSV(data, campoMappingCVSMap);
					if (isValido) {
						Map<String, ValueDataVO> resultadoTemp = new HashMap<>();
						for (var objAtr : campoMappingCVSMap.entrySet()) {
							ValueDataVO value = obtenerValueCSV(data, Integer.parseInt(objAtr.getValue() + ""),
									campoMappingCSVTypeMap.get(objAtr.getKey()),
									campoMappingFormatoMap.get(objAtr.getKey()), filaDataProcesar, parametroMap);
							resultadoTemp.put(objAtr.getKey(), value);
						}
						StringBuilder key = generarKeyAgrupador(resultadoTemp, configuracionTramaDetalleMap);
						// para agrupar
						if (StringUtil.isNullOrEmpty(key)) {
							if (!grupoMap.containsKey(key.toString())) {
								resultado.add(resultadoTemp);
								grupoMap.put(key.toString(), null);
							} else {
								resultado.add(resultadoTemp);
								grupoMap.put(key.toString(), null);
							}
						} else {
							if (!grupoMap.containsKey(key.toString())) {
								resultado.add(resultadoTemp);
								grupoMap.put(key.toString(), null);
							}
						}
					} else {
						break;
					}
				}
				if ((cantidadData != null) && contadorData == cantidadData.intValue()) {
					break;
				}
			}
			if (CollectionUtil.isEmpty(resultado)) {
				String[] data = new String[0];// campoMappingTXTMap.size() obtener maximo index
				Map<String, ValueDataVO> resultadoTemp = new HashMap<>();
				for (var objAtr : campoMappingCVSMap.entrySet()) {
					ValueDataVO value = obtenerValueCSV(data, Integer.parseInt(objAtr.getValue() + ""),
							campoMappingCSVTypeMap.get(objAtr.getKey()), campoMappingFormatoMap.get(objAtr.getKey()),
							filaDataProcesar, parametroMap);
					resultadoTemp.put(objAtr.getKey(), value);
				}
				resultado.add(resultadoTemp);
			}
		} catch (Exception e) {
			log.error("Error TransferDataOperUtil.toCSVMap() al parsear " + e.getMessage());
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					log.error("toCSVMap", e);
				}
			}
		}
		grupoMap = null;
		return resultado;
	}

	private static boolean isConvertToCharsetUtf8() {
		return ConfiguracionCacheUtil.getInstance().isElementoTrue("convert.to.charset.utf8");
	}

	/**
	 * Transfer objeto entity txt separador map dto.
	 *
	 * @param campoMappingTXTMap     el campo mapping txt map
	 * @param dataList               el data list
	 * @param campoMappingTxtTypeMap el campo mapping txt type map
	 * @param campoMappingFormatoMap el campo mapping formato map
	 * @param parametroMap           el parametro map
	 * @return the list
	 */
	public static List<Map<String, ValueDataVO>> toTXTSeparadorMapDTO(Map<String, Object> campoMappingTXTMap,
			BufferedReader br, Map<String, String> campoMappingTxtTypeMap, Map<String, String> campoMappingFormatoMap,
			Map<String, Object> parametroMap, Map<String, Character> configuracionTramaDetalleMap) {
		List<Map<String, ValueDataVO>> resultado = new ArrayList<>();
		if (campoMappingFormatoMap == null) {
			campoMappingFormatoMap = new HashMap<>();
		}
		Map<String, String> grupoMap = new HashMap<>();
		try {
			String txtSplitBy = (String) parametroMap.get("txtSplitBy");
			int filaData = (Integer) parametroMap.get(FILA_DATA);
			Integer cantidadData = (Integer) parametroMap.get(CANTIDAD_DATA);
			int contador = 0;
			int contadorData = 0;
			String line = "";
			if (StringUtil.isNullOrEmpty(txtSplitBy)) {
				txtSplitBy = "\t";// tabuladores
			}
			int filaDataProcesar = 0;
			while ((line = br.readLine()) != null) {
				filaDataProcesar++;
				contador++;
				if (contador >= filaData) {
					contadorData++;
					if (isConvertToCharsetUtf8()) {
						line = new String(line.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);// incidencia
																											// encoding
					}
					String[] data = line.split(txtSplitBy, -1);// -1 para leer
					boolean isValido = validarCSV(data, campoMappingTXTMap);
					if (isValido) {
						Map<String, ValueDataVO> resultadoTemp = new HashMap<>();
						for (var objAtr : campoMappingTXTMap.entrySet()) {
							ValueDataVO value = obtenerValueCSV(data, Integer.parseInt(objAtr.getValue() + ""),
									campoMappingTxtTypeMap.get(objAtr.getKey()),
									campoMappingFormatoMap.get(objAtr.getKey()), filaDataProcesar, parametroMap);
							resultadoTemp.put(objAtr.getKey(), value);
						}
						StringBuilder key = generarKeyAgrupador(resultadoTemp, configuracionTramaDetalleMap);
						// para agrupar
						if (StringUtil.isNullOrEmpty(key)) {
							if (!grupoMap.containsKey(key.toString())) {
								resultado.add(resultadoTemp);
								grupoMap.put(key.toString(), null);
							} else {
								resultado.add(resultadoTemp);
								grupoMap.put(key.toString(), null);
							}
						} else {
							if (!grupoMap.containsKey(key.toString())) {
								resultado.add(resultadoTemp);
								grupoMap.put(key.toString(), null);
							}
						}
					} else {
						break;
					}
				}
				if ((cantidadData != null) && contadorData == cantidadData.intValue()) {
					break;
				}
			}
			if (CollectionUtil.isEmpty(resultado)) {
				filaDataProcesar++;
				String[] data = new String[0];// campoMappingTXTMap.size() obtener maximo index
				Map<String, ValueDataVO> resultadoTemp = new HashMap<>();
				for (var objAtr : campoMappingTXTMap.entrySet()) {
					ValueDataVO value = obtenerValueCSV(data, Integer.parseInt(objAtr.getValue() + ""),
							campoMappingTxtTypeMap.get(objAtr.getKey()), campoMappingFormatoMap.get(objAtr.getKey()),
							filaDataProcesar, parametroMap);
					resultadoTemp.put(objAtr.getKey(), value);
				}
				resultado.add(resultadoTemp);
			}
		} catch (Exception e) {
			log.error("Error TransferDataOperUtil.toTXTSeparadorMapDTO() al parsear " + e.getMessage());
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					log.error("toTXTSeparadorMapDTO", e);
				}
			}
		}
		grupoMap = null;
		return resultado;
	}

	/**
	 * Transfer objeto entity txt map dto.
	 *
	 * @param campoMappingTXTMap     el campo mapping txt map
	 * @param dataList               el data list
	 * @param campoMappingTXTTypeMap el campo mapping txt type map
	 * @param campoMappingFormatoMap el campo mapping formato map
	 * @return the list
	 */
	public static List<Map<String, ValueDataVO>> toTXTMap(Map<String, Object> campoMappingTXTMap, BufferedReader br,
			Map<String, String> campoMappingTXTTypeMap, Map<String, String> campoMappingFormatoMap,
			Map<String, Object> parametroMap, Map<String, Character> configuracionTramaDetalleMap) {
		List<Map<String, ValueDataVO>> resultado = new ArrayList<>();
		if (campoMappingFormatoMap == null) {
			campoMappingFormatoMap = new HashMap<>();
		}
		Map<String, List<Map<String, ValueDataVO>>> grupoMap = new HashMap<>();
		try {
			int filaData = (Integer) parametroMap.get(FILA_DATA);
			Integer cantidadData = (Integer) parametroMap.get(CANTIDAD_DATA);
			int contador = 0;
			int contadorData = 0;
			int filaDataProcesar = 0;
			String line = "";
			while ((line = br.readLine()) != null) {
				contador++;
				filaDataProcesar++;
				if (contador >= filaData) {
					contadorData++;
					String data = line;
					if (isConvertToCharsetUtf8()) {
						data = new String(line.getBytes(DEFAULT_CHARSET), DEFAULT_CHARSET);// incidencia encoding
					}
					boolean isValido = validarTXT(data, campoMappingTXTMap);
					if (isValido) {
						Map<String, ValueDataVO> resultadoTemp = new HashMap<>();
						for (var objAtr : campoMappingTXTMap.entrySet()) {
							ValueDataVO value = obtenerValuePosicion(data, objAtr.getValue() + "",
									campoMappingTXTTypeMap.get(objAtr.getKey()),
									campoMappingFormatoMap.get(objAtr.getKey()), filaDataProcesar, parametroMap);
							resultadoTemp.put(objAtr.getKey(), value);
						}
						StringBuilder key = generarKeyAgrupador(resultadoTemp, configuracionTramaDetalleMap);
						// para agrupar
						if (StringUtil.isNullOrEmpty(key)) {
							if (!grupoMap.containsKey(key.toString())) {
								resultado.add(resultadoTemp);
								grupoMap.put(key.toString(), null);
							} else {
								resultado.add(resultadoTemp);
								grupoMap.put(key.toString(), null);
							}
						} else {
							if (!grupoMap.containsKey(key.toString())) {
								resultado.add(resultadoTemp);
								grupoMap.put(key.toString(), null);
							}
						}
					} else {
						break;
					}
				}
				if ((cantidadData != null) && contadorData == cantidadData.intValue()) {
					break;
				}
			}
			if (CollectionUtil.isEmpty(resultado)) {
				filaDataProcesar++;
				String data = "";
				Map<String, ValueDataVO> resultadoTemp = new HashMap<>();
				for (var objAtr : campoMappingTXTMap.entrySet()) {
					ValueDataVO value = obtenerValuePosicion(data, objAtr.getValue() + "",
							campoMappingTXTTypeMap.get(objAtr.getKey()), campoMappingFormatoMap.get(objAtr.getKey()),
							filaDataProcesar, parametroMap);
					resultadoTemp.put(objAtr.getKey(), value);
				}
				resultado.add(resultadoTemp);
			}
		} catch (Exception e) {
			log.error("Error TransferDataOperUtil.toTXTMap() al parsear " + e.getMessage());
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					log.error("toTXTMap", e);
				}
			}
		}
		grupoMap = null;
		return resultado;
	}

	/**
	 * Transfer objeto entity coordenada txt map dto.
	 *
	 * @param campoMappingTXTMap     el campo mapping txt map
	 * @param dataList               el data list
	 * @param campoMappingTXTTypeMap el campo mapping txt type map
	 * @param campoMappingFormatoMap el campo mapping formato map
	 * @param isCabecera             el is cabecera
	 * @return the list
	 */
	public static List<Map<String, ValueDataVO>> toCoordenadaTXTMapDTO(Map<String, Object> campoMappingTXTMap,
			BufferedReader br, Map<String, String> campoMappingTXTTypeMap, Map<String, String> campoMappingFormatoMap,
			boolean isCabecera, Map<String, Object> parametroMap, Map<String, Character> configuracionTramaDetalleMap) {
		List<Map<String, ValueDataVO>> resultado = new ArrayList<>();
		if (campoMappingFormatoMap == null) {
			campoMappingFormatoMap = new HashMap<>();
		}
		Map<String, String> grupoMap = new HashMap<>();
		try {
			int filaData = (Integer) parametroMap.get(FILA_DATA);
			Integer cantidadData = (Integer) parametroMap.get(CANTIDAD_DATA);
			String delimitadorData = (String) parametroMap.get("delimitadorData");
			if (isCabecera) {
				int contador = 0;
				int contadorData = 0;
				String line = "";
				List<String> dataList = new ArrayList<>();
				while ((line = br.readLine()) != null) {
					contador++;
					if (contador >= filaData) {
						contadorData++;
						String data = line;
						if (isConvertToCharsetUtf8()) {
							data = new String(line.getBytes(DEFAULT_CHARSET), DEFAULT_CHARSET);// incidencia encoding
						}
						dataList.add(data);
					}
					if ((isCabecera && cantidadData != null) && cantidadData.compareTo(contadorData) == 0) {
						break;
					}
				}
				if (CollectionUtil.isEmpty(dataList)) {
					dataList.add("");
				}
				Map<String, ValueDataVO> resultadoTemp = new HashMap<>();
				for (var objAtr : campoMappingTXTMap.entrySet()) {
					ValueDataVO value = obtenerCoordenadaValor(dataList, null,
							campoMappingTXTMap.get(objAtr.getKey()) + "", campoMappingTXTTypeMap.get(objAtr.getKey()),
							campoMappingFormatoMap.get(objAtr.getKey()), isCabecera, parametroMap);
					resultadoTemp.put(objAtr.getKey(), value);
				}
				resultado.add(resultadoTemp);
			} else {
				int contador = 0;
				String line = "";
				while ((line = br.readLine()) != null) {
					contador++;
					if (contador >= filaData) {
						if (!isCabecera && !StringUtil.isNullOrEmpty(delimitadorData)
								&& line.contains(delimitadorData)) {
							break;
						}
						String data = line;
						if (isConvertToCharsetUtf8()) {
							data = new String(line.getBytes(DEFAULT_CHARSET), DEFAULT_CHARSET);// Incidencia Encoding
						}
						boolean isValido = validarTXTCoordenada(data, campoMappingTXTMap);
						if (isValido) {
							Map<String, ValueDataVO> resultadoTemp = new HashMap<>();
							for (var objAtr : campoMappingTXTMap.entrySet()) {
								ValueDataVO value = obtenerCoordenadaValor(null, data, objAtr.getValue() + "",
										campoMappingTXTTypeMap.get(objAtr.getKey()),
										campoMappingFormatoMap.get(objAtr.getKey()), isCabecera, parametroMap);
								resultadoTemp.put(objAtr.getKey(), value);
							}
							StringBuilder key = generarKeyAgrupador(resultadoTemp, configuracionTramaDetalleMap);
							// para agrupar
							if (StringUtil.isNullOrEmpty(key)) {
								if (!grupoMap.containsKey(key.toString())) {
									resultado.add(resultadoTemp);
									grupoMap.put(key.toString(), null);
								} else {
									resultado.add(resultadoTemp);
									grupoMap.put(key.toString(), null);
								}
							} else {
								if (!grupoMap.containsKey(key.toString())) {
									resultado.add(resultadoTemp);
									grupoMap.put(key.toString(), null);
								}
							}
						} else {
							break;
						}
					}
				}
				if (CollectionUtil.isEmpty(resultado)) {
					Map<String, ValueDataVO> resultadoTemp = new HashMap<>();
					for (var objAtr : campoMappingTXTMap.entrySet()) {
						ValueDataVO value = obtenerCoordenadaValor(null, "", objAtr.getValue() + "",
								campoMappingTXTTypeMap.get(objAtr.getKey()),
								campoMappingFormatoMap.get(objAtr.getKey()), isCabecera, parametroMap);
						resultadoTemp.put(objAtr.getKey(), value);
					}
					resultado.add(resultadoTemp);
				}
			}

		} catch (Exception e) {
			log.error("Error TransferDataOperUtil.toCoordenadaTXTMapDTO() al parsear " + e.getMessage());
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					log.error("toCoordenadaTXTMapDTO", e);
				}
			}
		}
		grupoMap = null;
		return resultado;
	}

}
