package pe.buildsoft.erp.core.infra.transversal.utilitario.exel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanMap;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ExcelComboDataVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ExcelGrupoDataVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ExcelHederDataVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ExcelHederTitleVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ValueDataVO;
import pe.buildsoft.erp.core.infra.transversal.utilitario.CollectionUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ConstanteConfigUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.FechaUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ObjectUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Class DataExportExcelPersonalizadoUtil.
 *
 * @author BuildSoft.
 * @version 1.0 , 07/04/2015
 * @since BuildErp 1.0
 */
public class DataExportExcelPersonalizadoUtil extends DataExportExcel implements Serializable {

	private static final String RANGO_CELDA = "!$A$1:$A$";

	private static final String IS_BLOQUEO = "isBloqueo";

	private static final String IS_FORMULA2 = "isFormula";

	private static final String IS_FREEZE_PANE = "isFreezePane";

	private static final String PROPIEDAD_CELDA_MAP = "propiedadCeldaMap";

	private static final String LISTA_TITULO_FINAL = "listaTituloFinal";

	private static final String WRITE_EXCEL = "writeExcel";

	private static final String HIDDEN = "hidden";

	private static final String ELEMENTO_NO_VALIDO = "Elemento no válido";

	private static final String MENSAJE = "Mensaje";

	private static final String WRAP_TEXT = "wrapText";

	private static final String CALCULAR_WITCH_DEMANDA = "calcularWitchDemanda";

	private static final String ANEXAR_HOJA_POSITION = "anexarHojaPosition";

	private static final String NOMBRE_ARCHIVO = "nombreArchivo";

	private static final String FORMAT = "Format";

	private static final String ROW_INICIO = "rowInicio";

	private static final String PRINT_TITLE_VIEW = "printTitleView";

	private static final String ANEXAR_HOJA_EXISTENTE = "anexarHojaExistente";

	private static final String COMBO_DATA = "comboData";

	private static final String ALING2 = "aling";

	private static final String FONT_HEIGHT_IN_POINTS = "fontHeightInPoints";

	private static final String POSICION_CELDA = "posicionCelda";

	private static final String NUMERIC = "Numeric";

	private static final String XLSX = ".xlsx";

	private static final String HOJA_NAME = "hojaName";

	/** La Constante DD_MM_YYY_HH_MM_SS. */
	private static final String DD_MM_YYY_HH_MM_SS = "dd/mm/yyy hh:mm:ss";

	/** La Constante serialVersionUID. */
	private static final long serialVersionUID = 6359062834392294265L;

	/** La Constante NOMBRE_LETRA. */
	public static final String NOMBRE_LETRA = "Arial";

	/** La Constante ROW_INFO_INDEX. */
	private static final String ROW_INFO_INDEX = "rowInfo.index";
	private static final String CELL_TYPE_STRING = "S";
	private static final String CELL_TYPE_INTEGER = "I";

	/** La Constante MAXIMO_RANGE_EXCEL. */
	private static final Integer MAXIMO_RANGE_EXCEL = 65535;

	private static final Integer MAXIMO_RANGE_EXCEL_XLSX = 1000000;

	/** La Constante CANTIDAD_FILAS_USADO_CABECERA. */
	private static final Integer CANTIDAD_FILAS_USADO_CABECERA = 1;

	/** El log. */
	private static Logger log = LoggerFactory.getLogger(DataExportExcelPersonalizadoUtil.class);

	/** La Constante RUTA_RECURSOS. */
	public static final String RUTA_RECURSOS_BYTE_BUFFER = ConstanteConfigUtil.RUTA_RECURSOS_BYTE_BUFFER;

	/** The Constant IS_FORMULA. */
	private static final String IS_FORMULA = "${FORMULA}";

	/**
	 * Instancia un nuevo data export excel.
	 */

	static {
		generarRutaTemp();
	}

	public DataExportExcelPersonalizadoUtil() {

	}

	private static void generarRutaTemp() {
//		File dir = new File(ConstanteConfigUtil.RUTA_RECURSOS_DATA_POI_BUFFER);
//		if (dir.exists()) {
//			  ArchivoUtilidades.limpiarArchivoAllDirectory(ConstanteConfigUtil.RUTA_RECURSOS_DATA_POI_BUFFER);
//		}
//		dir.mkdirs();
//		TempFile.setTempFileCreationStrategy(new DefaultTempFileCreationStrategy(dir));
	}

	/**
	 * Generar excel.
	 *
	 * @param listaHeaderData el lista header data
	 * @param listaData       the lista data
	 * @param archivoName     el archivo name
	 * @param titulo          el titulo
	 * @param propiedadesMap  el propiedades map
	 * @return true, en caso de exito
	 */
	public static byte[] generarExcel(List<ExcelHederDataVO> listaHeaderData, List<?> listaData, String archivoName,
			String titulo, Map<String, Object> propiedadesMap) {
		byte[] resultado = null;
		try {
			// Inicio Agregar coombo
			int hojaActiva = 0;
			Map<String, Integer> campoPosicionMap = new HashMap<>();
			boolean isCombo = propiedadesMap.containsKey(COMBO_DATA);
			boolean anexarHojaExistente = propiedadesMap.containsKey(ANEXAR_HOJA_EXISTENTE);
			int posicionCellCabecera = 0;
			if (isCombo) {
				for (var cellHeaderVO : listaHeaderData) {
					String nombreColumna = cellHeaderVO.getNameAtribute();
					if (!campoPosicionMap.containsKey(nombreColumna)) {
						campoPosicionMap.put(nombreColumna, posicionCellCabecera);
					}
					posicionCellCabecera++;
				}
			}
			// Fin Agregar coombo
			File archivoXLS = new File(RUTA_RECURSOS_BYTE_BUFFER);
			if (!archivoXLS.isFile()) {
				archivoXLS.mkdirs();
			}
			HSSFWorkbook workbook = new HSSFWorkbook();
			if (isCombo) {
				generarComboHoja(workbook, propiedadesMap);
			}
			int cantidadData = listaData.size();
			int cantidadHojas = 1;
			int contador = 0;
			if (cantidadData > MAXIMO_RANGE_EXCEL) {
				BigDecimal bCantidadData = new BigDecimal(cantidadData);
				BigDecimal maxRange = new BigDecimal(MAXIMO_RANGE_EXCEL);
				BigDecimal bCantidadHojas = bCantidadData.divide(maxRange, 2, BigDecimal.ROUND_UP);
				bCantidadHojas = bCantidadHojas.setScale(0, BigDecimal.ROUND_UP);
				cantidadHojas = bCantidadHojas.intValue();
			}
			CellStyle cellDateStyle = generarStyleDate(workbook);
			Map<String, CellStyle> cellStyleMap = new HashMap<>();
			HSSFCellStyle style = workbook.createCellStyle();
			style.setDataFormat(workbook.createDataFormat().getFormat("############"));

			// indicando un patron de formato
			CellStyle titleStyle = generarStyleTitle(workbook);
			for (int cantidadDataPaginadorHoja = 1; cantidadDataPaginadorHoja <= cantidadHojas; cantidadDataPaginadorHoja++) {
				String tituloFinal = titulo;
				if (propiedadesMap != null && propiedadesMap.containsKey(HOJA_NAME)) {
					tituloFinal = propiedadesMap.get(HOJA_NAME) + "";
				}
				if (cantidadHojas > 1) {
					tituloFinal = tituloFinal + cantidadDataPaginadorHoja;
				}
				HSSFSheet sheet = workbook.createSheet(tituloFinal);
				hojaActiva++;
				int posicionRow = 0;
				int incrementroRow = 1;
				if (propiedadesMap != null && propiedadesMap.containsKey(PRINT_TITLE_VIEW)) {
					Row filaTitle = sheet.createRow(posicionRow);
					Cell heraderTitleCell = filaTitle.createCell((listaHeaderData.size() / 2));
					heraderTitleCell.setCellValue(titulo);
					posicionRow = posicionRow + incrementroRow;
				}
				// creando cabecera del datos
				if (propiedadesMap != null && propiedadesMap.containsKey(ROW_INICIO)) {
					posicionRow = Integer.parseInt(propiedadesMap.get(ROW_INICIO) + "") - 1;
				}
				Row fila = sheet.createRow(posicionRow);
				posicionCellCabecera = 0;
				int incremetoCellCabecera = 1;
				for (var cellHeaderVO : listaHeaderData) {
					String cellHeader = cellHeaderVO.getNameHeader();
					Cell heraderCell = fila.createCell(posicionCellCabecera);
					heraderCell.setCellValue(cellHeader);
					heraderCell.setCellStyle(titleStyle);
					posicionCellCabecera = posicionCellCabecera + incremetoCellCabecera;
				}
				posicionRow = posicionRow + incrementroRow;
				// llenando la data
				int primeraFila = posicionRow;
				int i = 0;
				int fromIndex = fromIndex(cantidadDataPaginadorHoja);
				int toIndex = toIndex(cantidadData, cantidadDataPaginadorHoja);
				for (var cellData : listaData.subList(fromIndex, toIndex)) {
					Row filaDet = sheet.createRow(i + primeraFila);
					posicionCellCabecera = 0;
					incremetoCellCabecera = 1;
					for (var cellHeaderVO : listaHeaderData) {
						String nombreColumna = cellHeaderVO.getNameAtribute();
						Object value = null;
						if (!nombreColumna.equals(ROW_INFO_INDEX)) {
							value = atributoValueComplejo(cellData, nombreColumna);
						} else {
							value = (contador + 1);
						}
						if (esFecha(nombreColumna)) {
							Object valueDate = verificarFornatoFecha(nombreColumna, value);
							if (esFechaData(valueDate)) {
								Cell cellDetalle = filaDet.createCell(posicionCellCabecera);
								cellDetalle.setCellValue((LocalDateTime) valueDate);
								if (propiedadesMap != null && propiedadesMap.containsKey(nombreColumna + FORMAT)) {
									if (!cellStyleMap.containsKey(nombreColumna)) {
										DataFormat format = workbook.createDataFormat();
										CellStyle cellDateStyleFormat = workbook.createCellStyle();
										cellDateStyleFormat.setDataFormat(
												format.getFormat(propiedadesMap.get(nombreColumna + FORMAT) + ""));
										cellDetalle.setCellStyle(cellDateStyleFormat);
										cellStyleMap.put(nombreColumna, cellDateStyle);
									} else {
										cellDetalle.setCellStyle(cellStyleMap.get(nombreColumna));
									}

								} else {
									cellDetalle.setCellStyle(cellDateStyle);
								}

								posicionCellCabecera = posicionCellCabecera + incremetoCellCabecera;
							} else {
								Cell cellDetalle = filaDet.createCell(posicionCellCabecera);
								cellDetalle.setCellValue(value == null ? "" : value.toString());
								posicionCellCabecera = posicionCellCabecera + incremetoCellCabecera;
							}
						} else if (!StringUtil.isNullOrEmpty(cellHeaderVO.getTypeCell())) {
							Cell cellDetalle = filaDet.createCell(posicionCellCabecera);
							if (CELL_TYPE_STRING.equals(cellHeaderVO.getTypeCell())) {
								cellDetalle.setCellType(CellType.STRING);
							} else {
								cellDetalle.setCellType(CellType.NUMERIC);
							}

							if (CellType.NUMERIC == cellDetalle.getCellType()) {
								if (value == null) {
									cellDetalle.setCellValue("");
								} else {
									if (CELL_TYPE_INTEGER.equals(cellHeaderVO.getTypeCell())) {
										cellDetalle.setCellStyle(style);
										cellDetalle.setCellValue(Long.parseLong(value.toString()));
									} else {
										String valor = value.toString().replace(',', '.');
										cellDetalle.setCellValue(Double.parseDouble(valor));
									}
								}
							} else {
								cellDetalle.setCellValue(value == null ? "" : value.toString());
							}
							posicionCellCabecera = posicionCellCabecera + incremetoCellCabecera;
						} else {
							Cell cellDetalle = filaDet.createCell(posicionCellCabecera);
							cellDetalle.setCellValue(value == null ? "" : value.toString());
							posicionCellCabecera = posicionCellCabecera + incremetoCellCabecera;
						}
					}
					i++;
					contador++;
				}
				// Inicio agregar combo
				if (isCombo) {
					int hoja = 0;
					int cantidadRegistros = 100;
					if (listaData != null && !listaData.isEmpty()) {
						cantidadRegistros = listaData.size();
					}
					List<ExcelComboDataVO> listaDataCombo = (List<ExcelComboDataVO>) propiedadesMap.get(COMBO_DATA);
					if (listaDataCombo == null) {
						listaDataCombo = new ArrayList<>();
					}
					for (var excelComboDataVO : listaDataCombo) {
						hojaActiva++;
						String nombreColumna = excelComboDataVO.getNombreCampo();
						Name namedCell = workbook.createName();
						namedCell.setNameName(HIDDEN + hoja);
						namedCell.setRefersToFormula(
								HIDDEN + hoja + RANGO_CELDA + excelComboDataVO.getListaExcelComboData().size());

						DVConstraint constraint = DVConstraint.createFormulaListConstraint(HIDDEN + hoja);
						CellRangeAddressList addressList = new CellRangeAddressList(posicionRow, cantidadRegistros,
								campoPosicionMap.get(nombreColumna), campoPosicionMap.get(nombreColumna));
						HSSFDataValidation validation = new HSSFDataValidation(addressList, constraint);

						validation.setSuppressDropDownArrow(false);
						validation.setEmptyCellAllowed(false);
						validation.setShowPromptBox(false);
						validation.createErrorBox(MENSAJE, ELEMENTO_NO_VALIDO);

						sheet.addValidationData(validation);
						hoja++;
					}
					propiedadesMap.remove(COMBO_DATA);// limpiando data
				}
				// fin agregar combo
				int autoSizeColunm = 0;// 2
				int incrementoSize = 1;

				for (int ih = 0; ih < listaHeaderData.size(); ih++) {
					sheet.autoSizeColumn(autoSizeColunm, true);
					autoSizeColunm = autoSizeColunm + incrementoSize;
				}

			}
			boolean anexarHojaProcesar = false;
			if (isCombo) {
				if (anexarHojaExistente) {
					anexarHojaProcesar = true;
				} else {
					HSSFSheet sheet = workbook.createSheet("Instruccion");
					Row row = sheet.createRow(0);
					Cell cell = row.createCell(0);
					cell.setCellStyle(titleStyle);
					cell.setCellValue("Debe Seleccionar lista Existente");
					sheet.autoSizeColumn(0, true);
				}
				hojaActiva++;
			} else {
				if (anexarHojaExistente) {
					anexarHojaProcesar = true;
				}
			}
			if (anexarHojaProcesar) {
				String nombreArchivo = (String) propiedadesMap.get(NOMBRE_ARCHIVO);
				int anexarHojaPosition = (Integer) propiedadesMap.get(ANEXAR_HOJA_POSITION);
				File rutaArchivo = new File(ConstanteConfigUtil.RUTA_GENERAL_TEMPLANTE + nombreArchivo);
				HSSFWorkbook workbookAnexar = ExcelUtil.leerExcel(rutaArchivo);
				HSSFSheet sheetAnexar = workbookAnexar.getSheetAt(anexarHojaPosition - 1);
				if (sheetAnexar != null) {
					HSSFSheet sheet = workbook.createSheet(sheetAnexar.getSheetName());
					TransferUtilExcel.copySheets(sheet, sheetAnexar);
				}
			}
			workbook.setActiveSheet(hojaActiva - 1);

			FileOutputStream out = new FileOutputStream(RUTA_RECURSOS_BYTE_BUFFER + "" + archivoName + ".xls");
			workbook.write(out);
			workbook.close();
			out.close();
			ExcelUtil.defaultLocaleProcess();
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			resultado = null;
		}
		return resultado;
	}

	public static byte[] generarExcelXLSX(List<ExcelHederDataVO> listaHeaderData, List<?> listaData, String archivoName,
			String titulo, Map<String, Object> propiedadesMap) {
		byte[] resultado = null;
		try {
			Map<Integer, Integer> columnWidtMaxMap = new HashMap<>();
			boolean calcularWitchDemanda = propiedadesMap.containsKey(CALCULAR_WITCH_DEMANDA);
			// Inicio Agregar coombo
			Map<String, Integer> campoPosicionMap = new HashMap<>();
			boolean isCombo = propiedadesMap.containsKey(COMBO_DATA);
			boolean anexarHojaExistente = propiedadesMap.containsKey(ANEXAR_HOJA_EXISTENTE);
			int posicionCellCabecera = 0;
			if (isCombo) {
				for (var cellHeaderVO : listaHeaderData) {
					String nombreColumna = cellHeaderVO.getNameAtribute();
					if (!campoPosicionMap.containsKey(nombreColumna)) {
						campoPosicionMap.put(nombreColumna, posicionCellCabecera);
					}
					posicionCellCabecera++;
				}
			}
			// Fin Agregar coombo
			File archivoXLS = new File(RUTA_RECURSOS_BYTE_BUFFER);
			if (!archivoXLS.isFile()) {
				archivoXLS.mkdirs();
			}
			SXSSFWorkbook workbook = new SXSSFWorkbook(100);
			workbook.setCompressTempFiles(true); // temp files will be gzipped
			if (isCombo) {
				generarComboHojaXLSX(workbook, propiedadesMap);
			}
			int cantidadData = listaData.size();
			int cantidadHojas = 1;
			int contador = 0;
			if (cantidadData > MAXIMO_RANGE_EXCEL_XLSX) {
				BigDecimal bCantidadData = new BigDecimal(cantidadData);
				BigDecimal maxRange = new BigDecimal(MAXIMO_RANGE_EXCEL_XLSX);
				BigDecimal bCantidadHojas = bCantidadData.divide(maxRange, 2, BigDecimal.ROUND_UP);
				bCantidadHojas = bCantidadHojas.setScale(0, BigDecimal.ROUND_UP);
				cantidadHojas = bCantidadHojas.intValue();
			}
			CellStyle cellDateStyle = generarStyleDate(workbook);
			Map<String, CellStyle> cellStyleMap = new HashMap<>();
			// indicando un patron de formato
			CellStyle titleStyle = generarStyleTitle(workbook);
			for (int cantidadDataPaginadorHoja = 1; cantidadDataPaginadorHoja <= cantidadHojas; cantidadDataPaginadorHoja++) {
				String tituloFinal = titulo;
				if (propiedadesMap != null && propiedadesMap.containsKey(HOJA_NAME)) {
					tituloFinal = propiedadesMap.get(HOJA_NAME) + "";
				}
				if (cantidadHojas > 1) {
					tituloFinal = tituloFinal + cantidadDataPaginadorHoja;
				}
				if (tituloFinal.length() > 30) {
					tituloFinal = "A" + cantidadDataPaginadorHoja;
				}

				if (StringUtil.isNullOrEmpty(tituloFinal)) {
					tituloFinal = "A" + cantidadDataPaginadorHoja;
				}
				log.info("titulo final " + tituloFinal);
				SXSSFSheet sheet = workbook.createSheet(tituloFinal); // CREA UNA HOJA
				sheet.setRandomAccessWindowSize(100);// keep 100 rows in memory, exceeding rows will be flushed to disk
				int posicionRow = 0;
				int incrementroRow = 1;
				if (propiedadesMap != null && propiedadesMap.containsKey(PRINT_TITLE_VIEW)) {
					SXSSFRow filaTitle = sheet.createRow(posicionRow);
					SXSSFCell heraderTitleCell = filaTitle.createCell((listaHeaderData.size() / 2));
					heraderTitleCell.setCellValue(titulo);
					posicionRow = posicionRow + incrementroRow;
				}
				// creando cabecera del datos
				if (propiedadesMap != null && propiedadesMap.containsKey(ROW_INICIO)) {
					posicionRow = Integer.parseInt(propiedadesMap.get(ROW_INICIO) + "") - 1;
				}
				SXSSFRow fila = sheet.createRow(posicionRow);
				posicionCellCabecera = 0;
				int incremetoCellCabecera = 1;
				int columnIndex = 0;
				for (var cellHeaderVO : listaHeaderData) {
					String cellHeader = cellHeaderVO.getNameHeader();
					SXSSFCell heraderCell = fila.createCell(posicionCellCabecera);
					heraderCell.setCellValue(cellHeader);
					heraderCell.setCellStyle(titleStyle);
					posicionCellCabecera = posicionCellCabecera + incremetoCellCabecera;

					if (calcularWitchDemanda) {
						int widtMaxActual = ObjectUtil.objectToString(cellHeaderVO.getNameHeader()).length();
						double porcentaje = 0.20;
						if (!columnWidtMaxMap.containsKey(columnIndex)) {
							widtMaxActual = obtenerWidt(widtMaxActual, porcentaje);
							columnWidtMaxMap.put(columnIndex, widtMaxActual);
						}
					}
					columnIndex++;
				}
				posicionRow = posicionRow + incrementroRow;
				// llenando la data
				int primeraFila = posicionRow;
				int i = 0;
				int fromIndex = fromIndexXlsx(cantidadDataPaginadorHoja);
				int toIndex = toIndexXlsx(cantidadData, cantidadDataPaginadorHoja);
				for (var cellData : listaData.subList(fromIndex, toIndex)) {
					SXSSFRow filaDet = sheet.createRow(i + primeraFila);
					posicionCellCabecera = 0;
					incremetoCellCabecera = 1;
					columnIndex = 0;
					for (var cellHeaderVO : listaHeaderData) {
						String nombreColumna = cellHeaderVO.getNameAtribute();
						Object value = null;
						if (!nombreColumna.equals(ROW_INFO_INDEX)) {
							value = atributoValueComplejo(cellData, nombreColumna);
						} else {
							value = (contador + 1);
						}
						if (esFecha(nombreColumna)) {
							Object valueDate = verificarFornatoFecha(nombreColumna, value);
							if (esFechaData(valueDate)) {
								SXSSFCell cellDetalle = filaDet.createCell(posicionCellCabecera);
								cellDetalle.setCellValue((LocalDateTime) valueDate);
								if (propiedadesMap != null && propiedadesMap.containsKey(nombreColumna + FORMAT)) {
									if (!cellStyleMap.containsKey(nombreColumna)) {
										DataFormat format = workbook.createDataFormat();
										CellStyle cellDateStyleFormat = generarStyleDate(workbook);
										cellDateStyleFormat.setDataFormat(
												format.getFormat(propiedadesMap.get(nombreColumna + FORMAT) + ""));
										value = FechaUtil.obtenerFechaFormatoPersonalizado((OffsetDateTime) valueDate,
												propiedadesMap.get(nombreColumna + FORMAT) + "");
										cellDetalle.setCellStyle(cellDateStyleFormat);
										cellStyleMap.put(nombreColumna, cellDateStyle);
									} else {
										cellDetalle.setCellStyle(cellStyleMap.get(nombreColumna));
									}

								} else {
									cellDetalle.setCellStyle(cellDateStyle);
								}

								posicionCellCabecera = posicionCellCabecera + incremetoCellCabecera;
							} else {
								SXSSFCell cellDetalle = filaDet.createCell(posicionCellCabecera);
								cellDetalle.setCellValue(value == null ? "" : value.toString());
								posicionCellCabecera = posicionCellCabecera + incremetoCellCabecera;
							}
						} else {
							SXSSFCell cellDetalle = filaDet.createCell(posicionCellCabecera);
							cellDetalle.setCellValue(value == null ? "" : value.toString());
							posicionCellCabecera = posicionCellCabecera + incremetoCellCabecera;
						}

						if (calcularWitchDemanda) {
							int widtMaxActual = ObjectUtil.objectToString(cellHeaderVO.getNameHeader()).length();
							double porcentaje = 0.20;
							if (!columnWidtMaxMap.containsKey(columnIndex)) {
								widtMaxActual = obtenerWidt(widtMaxActual, porcentaje);
								columnWidtMaxMap.put(columnIndex, widtMaxActual);
							}
							int widtMax = columnWidtMaxMap.get(columnIndex);
							widtMaxActual = ObjectUtil.objectToString(value).length();
							widtMaxActual = obtenerWidt(widtMaxActual, porcentaje);
							if (widtMax < widtMaxActual) {
								columnWidtMaxMap.put(columnIndex, widtMaxActual);
							}
						}
						columnIndex++;
					}
					i++;
					contador++;
				}
				// Inicio agregar combo
				if (isCombo) {
					int hoja = 0;
					int cantidadRegistros = 100;
					if (listaData != null && !listaData.isEmpty()) {
						cantidadRegistros = listaData.size();
					}
					List<ExcelComboDataVO> listaDataCombo = (List<ExcelComboDataVO>) propiedadesMap.get(COMBO_DATA);
					if (listaDataCombo == null) {
						listaDataCombo = new ArrayList<>();
					}
					for (var excelComboDataVO : listaDataCombo) {
						String nombreColumna = excelComboDataVO.getNombreCampo();
						XSSFName namedCell = (XSSFName) workbook.createName();
						namedCell.setNameName(HIDDEN + hoja);
						namedCell.setRefersToFormula(
								HIDDEN + hoja + RANGO_CELDA + excelComboDataVO.getListaExcelComboData().size());

						DataValidationHelper dvHelper = sheet.getDataValidationHelper();
						DataValidationConstraint dataValidation = dvHelper.createFormulaListConstraint(HIDDEN + hoja);
						CellRangeAddressList addressList = new CellRangeAddressList(posicionRow, cantidadRegistros,
								campoPosicionMap.get(nombreColumna), campoPosicionMap.get(nombreColumna));
						DataValidation validation = dvHelper.createValidation(dataValidation, addressList);

						validation.setSuppressDropDownArrow(true);
						validation.setEmptyCellAllowed(true);
						validation.setShowPromptBox(true);
						validation.createErrorBox(MENSAJE, ELEMENTO_NO_VALIDO);

						sheet.addValidationData(validation);
						hoja++;
					}
					propiedadesMap.remove(COMBO_DATA);// limpiando data
				}
				// fin agregar combo
				int autoSizeColunm = 0;// 2
				int incrementoSize = 1;

				for (int ih = 0; ih < listaHeaderData.size(); ih++) {
					if (calcularWitchDemanda) {
						try {
							int width = columnWidtMaxMap.get(autoSizeColunm);
							width *= 256;
							int maxColumnWidth = 255 * 256; // The maximum column width for an individual cell is 255
															// characters
							if (width > maxColumnWidth) {
								width = maxColumnWidth;
							}
							sheet.setColumnWidth(autoSizeColunm, width);
						} catch (Exception e) {
							// log.error("ERROR autoSizeColunm -->" + autoSizeColunm);
						}
					} else {
						sheet.autoSizeColumn(autoSizeColunm, true);
					}
					autoSizeColunm = autoSizeColunm + incrementoSize;
				}

			}
			boolean anexarHojaProcesar = false;
			if (isCombo) {
				if (anexarHojaExistente) {
					anexarHojaProcesar = true;
				}
			} else {
				if (anexarHojaExistente) {
					anexarHojaProcesar = true;
				}
			}
			if (anexarHojaProcesar) {
				String nombreArchivo = (String) propiedadesMap.get(NOMBRE_ARCHIVO);
				int anexarHojaPosition = (Integer) propiedadesMap.get(ANEXAR_HOJA_POSITION);
				File rutaArchivo = new File(ConstanteConfigUtil.RUTA_GENERAL_TEMPLANTE + nombreArchivo);
				SXSSFWorkbook sXSSFWorkbookAnexar = ExcelUtil.leerExcelsXlsx(rutaArchivo);
				SXSSFSheet sheetAnexar = sXSSFWorkbookAnexar.getSheetAt(anexarHojaPosition - 1);
				if (sheetAnexar != null) {
					SXSSFSheet sheet = workbook.createSheet(sheetAnexar.getSheetName());
					sheet.setRandomAccessWindowSize(10);// keep 100 rows in memory, exceeding rows will be flushed to
														// disk
					TransferUtilExcel.copySheetsXLSX(sheet, sheetAnexar);
				}
			}
			FileOutputStream out = new FileOutputStream(RUTA_RECURSOS_BYTE_BUFFER + "" + archivoName + XLSX);
			workbook.write(out);
			workbook.dispose();
			out.close();
			ExcelUtil.defaultLocaleProcess();
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			resultado = null;
		}
		return resultado;
	}

	/**
	 * Generar excel xlsx per.
	 *
	 * @param listaHeaderData the lista header data
	 * @param listaData       the lista data
	 * @param archivoName     the archivo name
	 * @param titulo          the titulo
	 * @param propiedadesMap  the propiedades map
	 * @return the string
	 */
	public static String generarExcelXLSXPer(List<ExcelHederDataVO> listaHeaderData, List<?> listaData,
			String archivoName, String titulo, Map<String, Object> propiedadesMap) {
		String resultado = null;
		boolean isFormula = propiedadesMap.containsKey(IS_FORMULA2);
		boolean isBloqueo = propiedadesMap.containsKey(IS_BLOQUEO);
		boolean isFreezePane = propiedadesMap.containsKey(IS_FREEZE_PANE);
		Map<String, Map<String, String>> propiedadCeldaMap = new HashMap<>();
		if (propiedadesMap.containsKey(PROPIEDAD_CELDA_MAP)) {
			propiedadCeldaMap = (Map<String, Map<String, String>>) propiedadesMap.get(PROPIEDAD_CELDA_MAP);
		}
		try {
			boolean exluirCabecera = propiedadesMap.containsKey("exluirCabecera");
			List<ExcelHederTitleVO> listaTituloFinal = new ArrayList<>();
			if (propiedadesMap.containsKey(LISTA_TITULO_FINAL)) {
				listaTituloFinal = (List<ExcelHederTitleVO>) propiedadesMap.get(LISTA_TITULO_FINAL);
			}
			Map<Integer, Integer> columnWidtMaxMap = new HashMap<>();
			boolean calcularWitchDemanda = propiedadesMap.containsKey(CALCULAR_WITCH_DEMANDA);
			// Inicio Agregar coombo
			Map<String, Integer> campoPosicionMap = new HashMap<>();
			boolean isCombo = propiedadesMap.containsKey(COMBO_DATA);
			boolean anexarHojaExistente = propiedadesMap.containsKey(ANEXAR_HOJA_EXISTENTE);
			int posicionCellCabecera = 0;
			if (isCombo) {
				for (var cellHeaderVO : listaHeaderData) {
					String nombreColumna = cellHeaderVO.getNameAtribute();
					if (!campoPosicionMap.containsKey(nombreColumna)) {
						campoPosicionMap.put(nombreColumna, posicionCellCabecera);
					}
					posicionCellCabecera++;
				}
			}
			// Fin Agregar coombo
			File archivoXLS = new File(RUTA_RECURSOS_BYTE_BUFFER);
			if (!archivoXLS.isFile()) {
				archivoXLS.mkdirs();
			}
			SXSSFWorkbook workbook = new SXSSFWorkbook(100);
			workbook.setCompressTempFiles(true); // temp files will be gzipped
			if (isCombo) {
				generarComboHojaXLSX(workbook, propiedadesMap);
			}
			int cantidadData = listaData.size();
			int cantidadHojas = 1;
			int contador = 0;
			if (cantidadData > MAXIMO_RANGE_EXCEL) {
				BigDecimal bCantidadData = new BigDecimal(cantidadData);
				BigDecimal maxRange = new BigDecimal(MAXIMO_RANGE_EXCEL);
				BigDecimal bCantidadHojas = bCantidadData.divide(maxRange, 2, BigDecimal.ROUND_UP);
				bCantidadHojas = bCantidadHojas.setScale(0, BigDecimal.ROUND_UP);
				cantidadHojas = bCantidadHojas.intValue();
			}
			CellStyle cellDateStyle = generarStyleDate(workbook);
			Map<String, CellStyle> cellStyleMap = new HashMap<>();
			// indicando un patron de formato
			CellStyle titleStyle = generarStyleTitle(workbook, (short) 9);
			for (int cantidadDataPaginadorHoja = 1; cantidadDataPaginadorHoja <= cantidadHojas; cantidadDataPaginadorHoja++) {
				String tituloFinal = titulo;
				if (propiedadesMap != null && propiedadesMap.containsKey(HOJA_NAME)) {
					tituloFinal = propiedadesMap.get(HOJA_NAME) + "";
				}
				if (cantidadHojas > 1) {
					tituloFinal = tituloFinal + cantidadDataPaginadorHoja;
				}
				SXSSFSheet sheet = workbook.createSheet(tituloFinal); // CREA UNA HOJA
				sheet.setRandomAccessWindowSize(100);// keep 100 rows in memory, exceeding rows will be flushed to disk
				if (isBloqueo) {
					sheet.protectSheet(UUIDUtil.generarElementUUID());
				}
				int posicionRow = 0;
				int incrementroRow = 1;
				int maxPosicionRow = 0;
				if (!CollectionUtil.isEmpty(listaTituloFinal)) {
					for (var excelHederTitleVO : listaTituloFinal) {
						if (!excelHederTitleVO.isEsPiePagina()) {
							int posicionRowVar = excelHederTitleVO.getPosicionRow();
							int posicionCeldaVar = excelHederTitleVO.getPosicionCelda();
							if (posicionRowVar > 0) {
								posicionRowVar = posicionRowVar - 1;
							}
							if (posicionCeldaVar > 0) {
								posicionCeldaVar = posicionCeldaVar - 1;
							}
							if (posicionRowVar > maxPosicionRow) {
								maxPosicionRow = posicionRowVar;
							}
							SXSSFRow filaTitle = sheet.getRow(posicionRowVar);
							if (filaTitle == null) {
								filaTitle = sheet.createRow(posicionRowVar);
							}
							String tituloFinalPer = excelHederTitleVO.getNameHeader();
							SXSSFCell heraderTitleCell = null;
							if (posicionCeldaVar > 0) {
								heraderTitleCell = filaTitle.createCell(posicionCeldaVar);
							} else {
								heraderTitleCell = filaTitle.createCell(0);
							}
							heraderTitleCell.setCellValue(tituloFinalPer);

							CellStyle titleStyleVar = generarStyleTitle(workbook,
									excelHederTitleVO.getFontHeightInPoints());
							heraderTitleCell.setCellStyle(titleStyleVar);
							heraderTitleCell.getCellStyle().setAlignment(excelHederTitleVO.getAling());

							if (excelHederTitleVO.getVerticalAlignment() != null) {
								heraderTitleCell.getCellStyle()
										.setVerticalAlignment((excelHederTitleVO.getVerticalAlignment()));
							}
							heraderTitleCell.getCellStyle().setWrapText(excelHederTitleVO.isWrapText());

							excelHederTitleVO.setPosicionRow(posicionRowVar);
							excelHederTitleVO.setPosicionCelda(posicionCeldaVar);
							if (excelHederTitleVO.getRotacion() != 0) {
								heraderTitleCell.getCellStyle().setRotation((short) excelHederTitleVO.getRotacion());
							}
							if (excelHederTitleVO.getColumnIndex() > -1 && excelHederTitleVO.getWidth() > -1) {
								columnWidtMaxMap.put(excelHederTitleVO.getColumnIndex(), excelHederTitleVO.getWidth());
							}
						}
					}
					posicionRow = maxPosicionRow + posicionRow + incrementroRow;
				} else {
					if (propiedadesMap != null && propiedadesMap.containsKey(PRINT_TITLE_VIEW)) {
						SXSSFRow filaTitle = sheet.createRow(posicionRow);
						SXSSFCell heraderTitleCell = filaTitle.createCell(0);
						heraderTitleCell.setCellValue(titulo);
						posicionRow = posicionRow + incrementroRow;
					}
				}
				// creando cabecera del datos
				if (propiedadesMap != null && propiedadesMap.containsKey(ROW_INICIO)) {
					posicionRow = Integer.parseInt(propiedadesMap.get(ROW_INICIO) + "") - 1;
				}
				SXSSFRow fila = sheet.createRow(posicionRow);
				if (isFreezePane) {
					sheet.createFreezePane(0, posicionRow + 1);
				}
				posicionCellCabecera = 0;
				int incremetoCellCabecera = 1;
				int columnIndex = 0;
				for (var cellHeaderVO : listaHeaderData) {
					if (!exluirCabecera) {
						String cellHeader = cellHeaderVO.getNameHeader();
						SXSSFCell heraderCell = fila.createCell(posicionCellCabecera);
						heraderCell.setCellValue(cellHeader);
						heraderCell.setCellStyle(titleStyle);
						posicionCellCabecera = posicionCellCabecera + incremetoCellCabecera;
					}
					if (calcularWitchDemanda) {
						int widtMaxActual = ObjectUtil.objectToString(cellHeaderVO.getNameHeader()).length();
						double porcentaje = 0.20;
						if (!columnWidtMaxMap.containsKey(columnIndex)) {
							widtMaxActual = obtenerWidt(widtMaxActual, porcentaje);
							columnWidtMaxMap.put(columnIndex, widtMaxActual);
						}
					}
					columnIndex++;
				}

				for (var excelHederTitleVO : listaTituloFinal) {
					if (!excelHederTitleVO.isEsPiePagina()) {
						try {
							// int firstRow, int lastRow, int firstCol, int lastCol
							CellRangeAddress cellRangeAddress = null;
							if (excelHederTitleVO.getCantidadAgrupar() > 0
									&& excelHederTitleVO.getCantidadAgruparHorizontal() == 0) {
								cellRangeAddress = new CellRangeAddress(excelHederTitleVO.getPosicionRow().intValue(),
										excelHederTitleVO.getPosicionRow().intValue(),
										excelHederTitleVO.getPosicionCelda().intValue(),
										((excelHederTitleVO.getPosicionCelda().intValue()) - 1)
												+ excelHederTitleVO.getCantidadAgrupar().intValue());
								sheet.addMergedRegion(cellRangeAddress);
								generarMergeRegionBorder(cellRangeAddress, sheet);
							}
							if (excelHederTitleVO.getCantidadAgruparHorizontal() > 0
									&& excelHederTitleVO.getCantidadAgrupar() == 0) {
								cellRangeAddress = new CellRangeAddress(excelHederTitleVO.getPosicionRow().intValue(),
										((excelHederTitleVO.getPosicionRow().intValue() - 1)
												+ excelHederTitleVO.getCantidadAgruparHorizontal().intValue()),
										excelHederTitleVO.getPosicionCelda().intValue(),
										excelHederTitleVO.getPosicionCelda().intValue());
								sheet.addMergedRegion(cellRangeAddress);
								generarMergeRegionBorder(cellRangeAddress, sheet);
							}
							if (excelHederTitleVO.getCantidadAgruparHorizontal() > 0
									&& excelHederTitleVO.getCantidadAgrupar() > 0) {
								cellRangeAddress = new CellRangeAddress(excelHederTitleVO.getPosicionRow().intValue(),
										((excelHederTitleVO.getPosicionRow().intValue() - 1)
												+ excelHederTitleVO.getCantidadAgruparHorizontal().intValue()),
										excelHederTitleVO.getPosicionCelda().intValue(),
										((excelHederTitleVO.getPosicionCelda().intValue()) - 1)
												+ excelHederTitleVO.getCantidadAgrupar().intValue());
								sheet.addMergedRegion(cellRangeAddress);
								generarMergeRegionBorder(cellRangeAddress, sheet);
							}
						} catch (Exception e) {
							log.error("generarExcelXLSXPer", e);
						}
					}
				}
				// llenando la data
				int primeraFila = posicionRow + 1;
				int i = 0;
				int fromIndex = fromIndexXlsx(cantidadDataPaginadorHoja);
				int toIndex = toIndexXlsx(cantidadData, cantidadDataPaginadorHoja);
				for (var cellData : listaData.subList(fromIndex, toIndex)) {
					SXSSFRow filaDet = sheet.createRow(i + primeraFila);
					posicionCellCabecera = 0;
					incremetoCellCabecera = 1;
					columnIndex = 0;
					for (var cellHeaderVO : listaHeaderData) {
						SXSSFCell cellDetalle = null;
						String nombreColumna = cellHeaderVO.getNameAtribute();
						Object value = null;
						if (!nombreColumna.equals(ROW_INFO_INDEX) && !nombreColumna.contains(IS_FORMULA)) {
							value = atributoValueComplejo(cellData, nombreColumna);
						} else {
							if (nombreColumna.equals(ROW_INFO_INDEX)) {
								value = (contador + 1);
							}
						}
						if (esFecha(nombreColumna)) {
							Object valueDate = verificarFornatoFecha(nombreColumna, value);
							if (esFechaData(valueDate)) {
								cellDetalle = filaDet.createCell(posicionCellCabecera);
								cellDetalle.setCellValue((LocalDateTime) valueDate);
								if (propiedadesMap != null && propiedadesMap.containsKey(nombreColumna + FORMAT)) {
									if (!cellStyleMap.containsKey(nombreColumna)) {

										DataFormat format = workbook.createDataFormat();
										CellStyle cellStyle = workbook.createCellStyle();
										cellStyle.setDataFormat(
												format.getFormat(propiedadesMap.get(nombreColumna + FORMAT) + ""));
										cellStyleMap.put(nombreColumna, cellStyle);
									} else {
										cellDetalle.setCellStyle(cellStyleMap.get(nombreColumna));
									}

								} else {
									cellDetalle.setCellStyle(cellDateStyle);
								}

								cellDetalle.getCellStyle().setLocked(false);
								posicionCellCabecera = posicionCellCabecera + incremetoCellCabecera;
							} else {
								cellDetalle = filaDet.createCell(posicionCellCabecera);
								cellDetalle.setCellValue(value == null ? "" : value.toString());
								cellDetalle.getCellStyle().setLocked(false);
								posicionCellCabecera = posicionCellCabecera + incremetoCellCabecera;
							}
						} else {
							cellDetalle = filaDet.createCell(posicionCellCabecera);
							if (nombreColumna.contains(IS_FORMULA)) {
								cellDetalle.setCellType(CellType.FORMULA);
								cellDetalle.getCellStyle().setLocked(true);
								String[] nombreColumnaCalc = nombreColumna.split("=>", -1);
								String formula = nombreColumnaCalc[1];
								formula = formula.replace("${N}", "" + (filaDet.getRowNum() + 1));
								cellDetalle.setCellFormula(formula);
							} else {
								if (propiedadesMap != null && propiedadesMap.containsKey(nombreColumna + NUMERIC)) {
									if (!StringUtil.isNullOrEmptyNumeriCero(value)) {
										cellDetalle.setCellValue(Double.valueOf(value.toString()));
									} else {
										cellDetalle.setCellValue(value == null ? "" : value.toString());
									}
								} else {
									if (value instanceof Number) {
										cellDetalle.setCellValue(Double.parseDouble(value.toString()));
									} else {
										cellDetalle.setCellValue(value == null ? "" : value.toString());
									}
									cellDetalle.getCellStyle().setLocked(false);
								}

							}
							posicionCellCabecera = posicionCellCabecera + incremetoCellCabecera;
						}
						if (calcularWitchDemanda) {
							int widtMaxActual = ObjectUtil.objectToString(cellHeaderVO.getNameHeader()).length();
							double porcentaje = 0.20;
							if (!columnWidtMaxMap.containsKey(columnIndex)) {
								widtMaxActual = obtenerWidt(widtMaxActual, porcentaje);
								columnWidtMaxMap.put(columnIndex, widtMaxActual);
							}
							int widtMax = columnWidtMaxMap.get(columnIndex);
							widtMaxActual = ObjectUtil.objectToString(value).length();
							widtMaxActual = obtenerWidt(widtMaxActual, porcentaje);
							if (widtMax < widtMaxActual) {
								columnWidtMaxMap.put(columnIndex, widtMaxActual);
							}
						}
						String keyPropiedad = i + "";
						if (propiedadCeldaMap.containsKey(keyPropiedad) && cellDetalle != null) {
							int posicionCelda = -1;
							boolean aplicaCeldaAll = true;
							boolean styleMarco = true;
							boolean boldNone = propiedadCeldaMap.get(keyPropiedad).containsKey("boldNone");
							if (propiedadCeldaMap.get(keyPropiedad).containsKey(POSICION_CELDA)) {
								posicionCelda = Integer
										.parseInt(propiedadCeldaMap.get(keyPropiedad).get(POSICION_CELDA) + "");
								aplicaCeldaAll = false;
							}
							if (propiedadCeldaMap.get(keyPropiedad).containsKey("styleMarcoNone")) {
								styleMarco = false;
							}
							if (propiedadCeldaMap.get(keyPropiedad).containsKey("cellStyle")
									&& (aplicaCeldaAll || posicionCelda == columnIndex)) {
								short fontHeightInPoints = 9;
								if (propiedadCeldaMap.get(keyPropiedad).containsKey(FONT_HEIGHT_IN_POINTS)) {
									fontHeightInPoints = (short) Integer.parseInt(
											propiedadCeldaMap.get(keyPropiedad).get(FONT_HEIGHT_IN_POINTS) + "");
								}
								HorizontalAlignment aling = null;
								if (propiedadCeldaMap.get(keyPropiedad).containsKey(ALING2)) {
									try {
										aling = HorizontalAlignment.forInt(
												Integer.parseInt(propiedadCeldaMap.get(keyPropiedad).get(ALING2) + ""));
									} catch (Exception e) {
										aling = null;
									}

								}
								CellStyle titleStyleVar = generarStyleTitleData(workbook, fontHeightInPoints,
										styleMarco, !boldNone);
								cellDetalle.setCellStyle(titleStyleVar);
								if (aling != null) {
									cellDetalle.getCellStyle().setAlignment(aling);
								}
							}

						}
						columnIndex++;
					}

					contador++;
					int posicionCeldaData = 0;
					for (var cellHeaderVO : listaHeaderData) {
						try {
							if (cellHeaderVO.getCantidadAgrupar() > 0) {
								CellRangeAddress cellRangeAddress = new CellRangeAddress(i + primeraFila,
										i + primeraFila, posicionCeldaData,
										((posicionCeldaData) - 1) + cellHeaderVO.getCantidadAgrupar().intValue());
								sheet.addMergedRegion(cellRangeAddress);
							}
						} catch (Exception e) {
							log.error("generarExcelXLSXPer", e);
						}
						posicionCeldaData++;
					}
					i++;
				}
				// Inicio escribir pie de pagina
				if (!CollectionUtil.isEmpty(listaTituloFinal)) {
					for (var excelHederTitleVO : listaTituloFinal) {
						if (excelHederTitleVO.isEsPiePagina()) {
							int posicionRowVar = excelHederTitleVO.getPosicionRow();
							int posicionCeldaVar = excelHederTitleVO.getPosicionCelda();
							if (posicionRowVar > 0) {
								posicionRowVar = posicionRowVar - 1;
							}
							if (posicionCeldaVar > 0) {
								posicionCeldaVar = posicionCeldaVar - 1;
							}
							if (posicionRowVar > maxPosicionRow) {
								maxPosicionRow = posicionRowVar;
							}
							SXSSFRow filaTitle = sheet.getRow(posicionRowVar);
							if (filaTitle == null) {
								filaTitle = sheet.createRow(posicionRowVar);
							}
							String tituloFinalPer = excelHederTitleVO.getNameHeader();
							SXSSFCell heraderTitleCell = null;
							if (posicionCeldaVar > 0) {
								heraderTitleCell = filaTitle.createCell(posicionCeldaVar);
							} else {
								heraderTitleCell = filaTitle.createCell(0);
							}
							heraderTitleCell.setCellValue(tituloFinalPer);

							CellStyle titleStyleVar = generarStyleTitlePie(workbook,
									excelHederTitleVO.getFontHeightInPoints());
							heraderTitleCell.setCellStyle(titleStyleVar);
							heraderTitleCell.getCellStyle().setAlignment(excelHederTitleVO.getAling());

							if (excelHederTitleVO.getVerticalAlignment() != null) {
								heraderTitleCell.getCellStyle()
										.setVerticalAlignment(excelHederTitleVO.getVerticalAlignment());
							}
							heraderTitleCell.getCellStyle().setWrapText(excelHederTitleVO.isWrapText());

							excelHederTitleVO.setPosicionRow(posicionRowVar);
							excelHederTitleVO.setPosicionCelda(posicionCeldaVar);
							if (excelHederTitleVO.getRotacion() != 0) {
								heraderTitleCell.getCellStyle().setRotation((short) excelHederTitleVO.getRotacion());
							}
						}
					}
				}
				for (var excelHederTitleVO : listaTituloFinal) {
					if (excelHederTitleVO.isEsPiePagina()) {
						try {
							// int firstRow, int lastRow, int firstCol, int lastCol
							CellRangeAddress cellRangeAddress = null;
							if (excelHederTitleVO.getCantidadAgrupar() > 0
									&& excelHederTitleVO.getCantidadAgruparHorizontal() == 0) {
								cellRangeAddress = new CellRangeAddress(excelHederTitleVO.getPosicionRow().intValue(),
										excelHederTitleVO.getPosicionRow().intValue(),
										excelHederTitleVO.getPosicionCelda().intValue(),
										((excelHederTitleVO.getPosicionCelda().intValue()) - 1)
												+ excelHederTitleVO.getCantidadAgrupar().intValue());
								sheet.addMergedRegion(cellRangeAddress);
								generarMergeRegionBorderPie(cellRangeAddress, sheet);
							}
							if (excelHederTitleVO.getCantidadAgruparHorizontal() > 0
									&& excelHederTitleVO.getCantidadAgrupar() == 0) {
								cellRangeAddress = new CellRangeAddress(excelHederTitleVO.getPosicionRow().intValue(),
										((excelHederTitleVO.getPosicionRow().intValue() - 1)
												+ excelHederTitleVO.getCantidadAgruparHorizontal().intValue()),
										excelHederTitleVO.getPosicionCelda().intValue(),
										excelHederTitleVO.getPosicionCelda().intValue());
								sheet.addMergedRegion(cellRangeAddress);
								generarMergeRegionBorderPie(cellRangeAddress, sheet);
							}
							if (excelHederTitleVO.getCantidadAgruparHorizontal() > 0
									&& excelHederTitleVO.getCantidadAgrupar() > 0) {
								cellRangeAddress = new CellRangeAddress(excelHederTitleVO.getPosicionRow().intValue(),
										((excelHederTitleVO.getPosicionRow().intValue() - 1)
												+ excelHederTitleVO.getCantidadAgruparHorizontal().intValue()),
										excelHederTitleVO.getPosicionCelda().intValue(),
										((excelHederTitleVO.getPosicionCelda().intValue()) - 1)
												+ excelHederTitleVO.getCantidadAgrupar().intValue());
								sheet.addMergedRegion(cellRangeAddress);
								generarMergeRegionBorderPie(cellRangeAddress, sheet);
							}
						} catch (Exception e) {
							log.error("generarExcelXLSXPer", e);
						}
					}
				}
				// Fin escrubir pie de pagina
				// Inicio agregar combo
				if (isCombo) {
					int hoja = 0;
					int cantidadRegistros = 100;
					if (listaData != null && !listaData.isEmpty()) {
						cantidadRegistros = listaData.size();
					}
					List<ExcelComboDataVO> listaDataCombo = (List<ExcelComboDataVO>) propiedadesMap.get(COMBO_DATA);
					if (listaDataCombo == null) {
						listaDataCombo = new ArrayList<>();
					}
					for (var excelComboDataVO : listaDataCombo) {
						String nombreColumna = excelComboDataVO.getNombreCampo();
						XSSFName namedCell = (XSSFName) workbook.createName();
						namedCell.setNameName(HIDDEN + hoja);
						namedCell.setRefersToFormula(
								HIDDEN + hoja + RANGO_CELDA + excelComboDataVO.getListaExcelComboData().size());

						DataValidationHelper dvHelper = sheet.getDataValidationHelper();
						DataValidationConstraint dataValidation = dvHelper.createFormulaListConstraint(HIDDEN + hoja);
						CellRangeAddressList addressList = new CellRangeAddressList(posicionRow, cantidadRegistros,
								campoPosicionMap.get(nombreColumna), campoPosicionMap.get(nombreColumna));
						DataValidation validation = dvHelper.createValidation(dataValidation, addressList);
						validation.setSuppressDropDownArrow(true);
						validation.setEmptyCellAllowed(true);
						validation.setShowPromptBox(true);
						validation.createErrorBox(MENSAJE, ELEMENTO_NO_VALIDO);
						sheet.addValidationData(validation);
						hoja++;
					}
					propiedadesMap.remove(COMBO_DATA);// limpiando data
				}
				// fin agregar combo
				int autoSizeColunm = 0;// 2
				int incrementoSize = 1;
				for (int ih = 0; ih < listaHeaderData.size(); ih++) {
					if (calcularWitchDemanda) {
						try {
							int width = columnWidtMaxMap.get(autoSizeColunm);
							width *= 256;
							int maxColumnWidth = 255 * 256; // The maximum column width for an individual cell is 255
															// characters
							if (width > maxColumnWidth) {
								width = maxColumnWidth;
							}
							sheet.setColumnWidth(autoSizeColunm, width);
						} catch (Exception e) {
							// log.error("ERROR autoSizeColunm -->" + autoSizeColunm);
						}
					} else {
						sheet.autoSizeColumn(autoSizeColunm, true);
					}
					autoSizeColunm = autoSizeColunm + incrementoSize;
				}
			}
			boolean anexarHojaProcesar = false;
			if (isCombo) {
				if (anexarHojaExistente) {
					anexarHojaProcesar = true;
				}
			} else {
				if (anexarHojaExistente) {
					anexarHojaProcesar = true;
				}
			}
			if (anexarHojaProcesar) {
				String nombreArchivo = (String) propiedadesMap.get(NOMBRE_ARCHIVO);
				int anexarHojaPosition = (Integer) propiedadesMap.get(ANEXAR_HOJA_POSITION);
				File rutaArchivo = new File(ConstanteConfigUtil.RUTA_GENERAL_TEMPLANTE + nombreArchivo);

				SXSSFWorkbook sXSSFWorkbookAnexar = ExcelUtil.leerExcelsXlsx(rutaArchivo);
				SXSSFSheet sheetAnexar = sXSSFWorkbookAnexar.getSheetAt(anexarHojaPosition - 1);
				if (sheetAnexar != null) {
					SXSSFSheet sheet = workbook.createSheet(sheetAnexar.getSheetName());
					sheet.setRandomAccessWindowSize(100);// keep 100 rows in memory, exceeding rows will be flushed to
															// disk
					TransferUtilExcel.copySheetsXLSX(sheet, sheetAnexar);
				}

			}
			if (isFormula) {
				try {// SXSSFWorkbook
					XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook.getXSSFWorkbook());
				} catch (Exception e) {
					log.error("generarExcelXLSXPer", e);
				}
			}
			FileOutputStream out = new FileOutputStream(RUTA_RECURSOS_BYTE_BUFFER + "" + archivoName + XLSX);
			workbook.write(out);
			workbook.dispose();
			out.close();
			ExcelUtil.defaultLocaleProcess();
		} catch (Exception ex) {
			log.error("generarExcelXLSXPer" + ex.getMessage(), ex);
			resultado = null;
		}
		return resultado;
	}

	public static String generarExcelXLSXPerWithGroup(List<ExcelHederDataVO> listaHeaderData, List<?> listaData,
			String archivoName, String titulo, Map<String, Object> propiedadesMap) {
		String resultado = null;
		boolean isFormula = propiedadesMap.containsKey(IS_FORMULA2);
		boolean isBloqueo = propiedadesMap.containsKey(IS_BLOQUEO);
		boolean isFreezePane = propiedadesMap.containsKey(IS_FREEZE_PANE);
		Map<String, Map<String, String>> propiedadCeldaMap = new HashMap<>();
		if (propiedadesMap.containsKey(PROPIEDAD_CELDA_MAP)) {
			propiedadCeldaMap = (Map<String, Map<String, String>>) propiedadesMap.get(PROPIEDAD_CELDA_MAP);
		}
		try {
			log.error(" generarExcelXLSXPerWithGroup " + archivoName);
			boolean exluirCabecera = propiedadesMap.containsKey("exluirCabecera");
			List<ExcelHederTitleVO> listaTituloFinal = new ArrayList<>();
			if (propiedadesMap.containsKey(LISTA_TITULO_FINAL)) {
				listaTituloFinal = (List<ExcelHederTitleVO>) propiedadesMap.get(LISTA_TITULO_FINAL);
			}
			Map<Integer, Integer> columnWidtMaxMap = new HashMap<>();
			boolean calcularWitchDemanda = propiedadesMap.containsKey(CALCULAR_WITCH_DEMANDA);
			// Inicio Agregar coombo
			Map<String, Integer> campoPosicionMap = new HashMap<>();
			boolean isCombo = propiedadesMap.containsKey(COMBO_DATA);
			boolean anexarHojaExistente = propiedadesMap.containsKey(ANEXAR_HOJA_EXISTENTE);
			int posicionCellCabecera = 0;
			if (isCombo) {
				for (var cellHeaderVO : listaHeaderData) {
					String nombreColumna = cellHeaderVO.getNameAtribute();
					if (!campoPosicionMap.containsKey(nombreColumna)) {
						campoPosicionMap.put(nombreColumna, posicionCellCabecera);
					}
					posicionCellCabecera++;
				}
			}
			// Fin Agregar coombo
			File archivoXLS = new File(RUTA_RECURSOS_BYTE_BUFFER);
			if (!archivoXLS.isFile()) {
				archivoXLS.mkdirs();
			}
			SXSSFWorkbook workbook = new SXSSFWorkbook(100);
			workbook.setCompressTempFiles(true); // temp files will be gzipped
			if (isCombo) {
				generarComboHojaXLSX(workbook, propiedadesMap);
			}
			int cantidadData = listaData.size();
			int cantidadHojas = 1;
			int contador = 0;
			if (cantidadData > MAXIMO_RANGE_EXCEL_XLSX) {
				BigDecimal bCantidadData = new BigDecimal(cantidadData);
				BigDecimal maxRange = new BigDecimal(MAXIMO_RANGE_EXCEL_XLSX);
				BigDecimal bCantidadHojas = bCantidadData.divide(maxRange, 2, BigDecimal.ROUND_UP);
				bCantidadHojas = bCantidadHojas.setScale(0, BigDecimal.ROUND_UP);
				cantidadHojas = bCantidadHojas.intValue();
			}
			CellStyle cellStyle = generarStyleData(workbook);
			Map<String, CellStyle> cellStyleMap = new HashMap<>();
			boolean usarBorder = propiedadesMap.containsKey("usarBorder");
			CellStyle cellDateStyle = generarStyleDate(workbook);
			if (usarBorder) {
				cellDateStyle = generarStyleDateBorder(workbook);
			}
			// indicando un patron de formato
			CellStyle titleStyle = generarStyleTitle(workbook, (short) 9);
			titleStyle.setWrapText(true);
			for (int cantidadDataPaginadorHoja = 1; cantidadDataPaginadorHoja <= cantidadHojas; cantidadDataPaginadorHoja++) {
				String tituloFinal = titulo;
				if (propiedadesMap != null && propiedadesMap.containsKey(HOJA_NAME)) {
					tituloFinal = propiedadesMap.get(HOJA_NAME) + "";
				}
				if (cantidadHojas > 1) {
					tituloFinal = tituloFinal + cantidadDataPaginadorHoja;
				}
				SXSSFSheet sheet = workbook.createSheet(tituloFinal); // CREA UNA HOJA
				sheet.setRandomAccessWindowSize(100);// keep 100 rows in memory, exceeding rows will be flushed to disk
				if (isBloqueo) {
					sheet.protectSheet(UUIDUtil.generarElementUUID());
				}
				int posicionRow = 0;
				int incrementroRow = 1;
				int maxPosicionRow = 0;
				if (!CollectionUtil.isEmpty(listaTituloFinal)) {
					for (var excelHederTitleVO : listaTituloFinal) {
						if (!excelHederTitleVO.isEsPiePagina()) {
							int posicionRowVar = excelHederTitleVO.getPosicionRow();
							int posicionCeldaVar = excelHederTitleVO.getPosicionCelda();
							if (posicionRowVar > 0) {
								posicionRowVar = posicionRowVar - 1;
							}
							if (posicionCeldaVar > 0) {
								posicionCeldaVar = posicionCeldaVar - 1;
							}
							if (posicionRowVar > maxPosicionRow) {
								maxPosicionRow = posicionRowVar;
							}
							SXSSFRow filaTitle = sheet.getRow(posicionRowVar);
							if (filaTitle == null) {
								filaTitle = sheet.createRow(posicionRowVar);
							}
							String tituloFinalPer = excelHederTitleVO.getNameHeader();
							SXSSFCell heraderTitleCell = null;
							if (posicionCeldaVar > 0) {
								heraderTitleCell = filaTitle.createCell(posicionCeldaVar);
							} else {
								heraderTitleCell = filaTitle.createCell(0);
							}
							heraderTitleCell.setCellValue(tituloFinalPer);

							CellStyle titleStyleVar = generarStyleTitle(workbook,
									excelHederTitleVO.getFontHeightInPoints());
							heraderTitleCell.setCellStyle(titleStyleVar);
							heraderTitleCell.getCellStyle().setAlignment(excelHederTitleVO.getAling());

							if (excelHederTitleVO.getVerticalAlignment() != null) {
								heraderTitleCell.getCellStyle()
										.setVerticalAlignment((excelHederTitleVO.getVerticalAlignment()));
							}
							heraderTitleCell.getCellStyle().setWrapText(excelHederTitleVO.isWrapText());

							excelHederTitleVO.setPosicionRow(posicionRowVar);
							excelHederTitleVO.setPosicionCelda(posicionCeldaVar);
							if (excelHederTitleVO.getRotacion() != 0) {
								heraderTitleCell.getCellStyle().setRotation((short) excelHederTitleVO.getRotacion());
							}
							if (excelHederTitleVO.getColumnIndex() > -1 && excelHederTitleVO.getWidth() > -1) {
								columnWidtMaxMap.put(excelHederTitleVO.getColumnIndex(), excelHederTitleVO.getWidth());
							}
						}
					}
					posicionRow = maxPosicionRow + posicionRow + incrementroRow;
				} else {
					if (propiedadesMap != null && propiedadesMap.containsKey(PRINT_TITLE_VIEW)) {
						SXSSFRow filaTitle = sheet.createRow(posicionRow);
						SXSSFCell heraderTitleCell = filaTitle.createCell(0);
						heraderTitleCell.setCellValue(titulo);
						posicionRow = posicionRow + incrementroRow;
					}
				}
				// creando cabecera del datos
				if (propiedadesMap != null && propiedadesMap.containsKey(ROW_INICIO)) {
					posicionRow = Integer.parseInt(propiedadesMap.get(ROW_INICIO) + "") - 1;
				}

				SXSSFRow fila = sheet.createRow(posicionRow);
				if (isFreezePane) {
					sheet.createFreezePane(0, posicionRow + 1);
				}
				posicionCellCabecera = 0;
				int incremetoCellCabecera = 1;
				int columnIndex = 0;
				for (var cellHeaderVO : listaHeaderData) {
					if (!exluirCabecera) {
						String cellHeader = cellHeaderVO.getNameHeader();
						SXSSFCell heraderCell = fila.createCell(posicionCellCabecera);
						heraderCell.setCellValue(cellHeader);
						heraderCell.setCellStyle(titleStyle);
						posicionCellCabecera = posicionCellCabecera + incremetoCellCabecera;
					}
					if (calcularWitchDemanda) {
						int widtMaxActual = ObjectUtil.objectToString(cellHeaderVO.getNameHeader()).length();
						double porcentaje = 0.20;
						if (!columnWidtMaxMap.containsKey(columnIndex)) {
							widtMaxActual = obtenerWidt(widtMaxActual, porcentaje);
							columnWidtMaxMap.put(columnIndex, widtMaxActual);
						}
					}
					columnIndex++;
				}

				for (var excelHederTitleVO : listaTituloFinal) {
					if (!excelHederTitleVO.isEsPiePagina()) {
						try {
							// int firstRow, int lastRow, int firstCol, int lastCol
							CellRangeAddress cellRangeAddress = null;
							if (excelHederTitleVO.getCantidadAgrupar() > 0
									&& excelHederTitleVO.getCantidadAgruparHorizontal() == 0) {
								cellRangeAddress = new CellRangeAddress(excelHederTitleVO.getPosicionRow().intValue(),
										excelHederTitleVO.getPosicionRow().intValue(),
										excelHederTitleVO.getPosicionCelda().intValue(),
										((excelHederTitleVO.getPosicionCelda().intValue()) - 1)
												+ excelHederTitleVO.getCantidadAgrupar().intValue());
								sheet.addMergedRegion(cellRangeAddress);
								generarMergeRegionBorder(cellRangeAddress, sheet);
							}
							if (excelHederTitleVO.getCantidadAgruparHorizontal() > 0
									&& excelHederTitleVO.getCantidadAgrupar() == 0) {
								cellRangeAddress = new CellRangeAddress(excelHederTitleVO.getPosicionRow().intValue(),
										((excelHederTitleVO.getPosicionRow().intValue() - 1)
												+ excelHederTitleVO.getCantidadAgruparHorizontal().intValue()),
										excelHederTitleVO.getPosicionCelda().intValue(),
										excelHederTitleVO.getPosicionCelda().intValue());
								sheet.addMergedRegion(cellRangeAddress);
								generarMergeRegionBorder(cellRangeAddress, sheet);
							}
							if (excelHederTitleVO.getCantidadAgruparHorizontal() > 0
									&& excelHederTitleVO.getCantidadAgrupar() > 0) {
								cellRangeAddress = new CellRangeAddress(excelHederTitleVO.getPosicionRow().intValue(),
										((excelHederTitleVO.getPosicionRow().intValue() - 1)
												+ excelHederTitleVO.getCantidadAgruparHorizontal().intValue()),
										excelHederTitleVO.getPosicionCelda().intValue(),
										((excelHederTitleVO.getPosicionCelda().intValue()) - 1)
												+ excelHederTitleVO.getCantidadAgrupar().intValue());
								sheet.addMergedRegion(cellRangeAddress);
								generarMergeRegionBorder(cellRangeAddress, sheet);
							}
						} catch (Exception e) {
							log.error("generarExcelXLSXPerWithGroup", e);
						}
					}
				}
				// llenando la data
				int primeraFila = posicionRow + 1;
				int i = 0;
				int fromIndex = fromIndexXlsx(cantidadDataPaginadorHoja);
				int toIndex = toIndexXlsx(cantidadData, cantidadDataPaginadorHoja);

				List<ExcelGrupoDataVO> columnGroupList = new ArrayList<>();
				Map<String, Object> mapColumnValue = new HashMap<>();
				Map<Integer, ExcelGrupoDataVO> mapColumnGroup = new HashMap<>();
				boolean lastFileFin = false;
				for (var cellData : listaData.subList(fromIndex, toIndex)) {
					SXSSFRow filaDet = sheet.createRow(i + primeraFila);
					posicionCellCabecera = 0;
					incremetoCellCabecera = 1;
					columnIndex = 0;
					for (var cellHeaderVO : listaHeaderData) {
						if (columnIndex == 0)
							lastFileFin = false;
						SXSSFCell cellDetalle = null;
						String nombreColumna = cellHeaderVO.getNameAtribute();
						Object value = null;
						if (!nombreColumna.equals(ROW_INFO_INDEX) && !nombreColumna.contains(IS_FORMULA)) {
							value = atributoValueComplejo(cellData, nombreColumna);
						} else {
							if (nombreColumna.equals(ROW_INFO_INDEX)) {
								value = (contador + 1);
							}
						}
						if (propiedadesMap.containsKey(nombreColumna + "Agrupar")
								&& (lastFileFin || (mapColumnValue.get(nombreColumna) == null
										|| !mapColumnValue.get(nombreColumna).equals(value)))) {

							if (mapColumnGroup.get(columnIndex) == null) {
								ExcelGrupoDataVO columnGroup = new ExcelGrupoDataVO(String.valueOf(columnIndex));
								columnGroup.setFilaInicio(1);
								columnGroup.setColumnaFin(columnIndex);
								columnGroup.setColumnaInicio(columnIndex);
								mapColumnGroup.put(columnIndex, columnGroup);
							}

							mapColumnGroup.get(columnIndex).setFilaFin(i + primeraFila - 1);

							columnGroupList.add(mapColumnGroup.get(columnIndex));
							mapColumnValue.put(nombreColumna, value);
							ExcelGrupoDataVO columnGroup = new ExcelGrupoDataVO(String.valueOf(columnIndex));
							columnGroup.setFilaInicio(i + primeraFila);
							columnGroup.setColumnaInicio(columnIndex);
							columnGroup.setColumnaFin(columnIndex);
							mapColumnGroup.put(columnIndex, columnGroup);
							lastFileFin = true;

						}

						if (esFecha(nombreColumna)) {
							Object valueDate = verificarFornatoFecha(nombreColumna, value);
							if (esFechaData(valueDate)) {
								cellDetalle = filaDet.createCell(posicionCellCabecera);
								cellDetalle.setCellValue((LocalDateTime) valueDate);
								if (propiedadesMap != null && propiedadesMap.containsKey(nombreColumna + FORMAT)) {
									if (!cellStyleMap.containsKey(nombreColumna)) {
										DataFormat format = workbook.createDataFormat();
										CellStyle cellDatStyle = workbook.createCellStyle();
										cellDatStyle.setDataFormat(
												format.getFormat(propiedadesMap.get(nombreColumna + FORMAT) + ""));

										cellDetalle.setCellStyle(cellDatStyle);
										cellStyleMap.put(nombreColumna, cellDatStyle);
									} else {
										cellDetalle.setCellStyle(cellStyleMap.get(nombreColumna));
									}

								} else {
									cellDetalle.setCellStyle(cellDateStyle);
								}
								cellDetalle.getCellStyle().setLocked(false);
								posicionCellCabecera = posicionCellCabecera + incremetoCellCabecera;
							} else {
								cellDetalle = filaDet.createCell(posicionCellCabecera);
								cellDetalle.setCellValue(value == null ? "" : value.toString());

								if (usarBorder)
									cellDetalle.setCellStyle(cellStyle);
								else
									cellDetalle.getCellStyle().setLocked(false);

								posicionCellCabecera = posicionCellCabecera + incremetoCellCabecera;
							}
						} else {
							cellDetalle = filaDet.createCell(posicionCellCabecera);
							if (nombreColumna.contains(IS_FORMULA)) {
								cellDetalle.setCellType(CellType.FORMULA);

								if (usarBorder)
									cellDetalle.setCellStyle(cellStyle);
								else
									cellDetalle.getCellStyle().setLocked(false);

								String[] nombreColumnaCalc = nombreColumna.split("=>", -1);
								String formula = nombreColumnaCalc[1];
								formula = formula.replace("${N}", "" + (filaDet.getRowNum() + 1));
								cellDetalle.setCellFormula(formula);
							} else {
								if (propiedadesMap != null && propiedadesMap.containsKey(nombreColumna + NUMERIC)) {
									if (!StringUtil.isNullOrEmptyNumeriCero(value)) {
										cellDetalle.setCellValue(Double.valueOf(value.toString()));
									} else {
										cellDetalle.setCellValue(value == null ? "" : value.toString());
									}
								} else {
									if (value instanceof Number) {
										cellDetalle.setCellValue(Double.parseDouble(value.toString()));
									} else {
										cellDetalle.setCellValue(value == null ? "" : value.toString());
									}

									if (usarBorder)
										cellDetalle.setCellStyle(cellStyle);
									else
										cellDetalle.getCellStyle().setLocked(false);

								}

							}
							posicionCellCabecera = posicionCellCabecera + incremetoCellCabecera;
						}
						if (calcularWitchDemanda) {
							int widtMaxActual = ObjectUtil.objectToString(cellHeaderVO.getNameHeader()).length();
							double porcentaje = 0.20;
							if (!columnWidtMaxMap.containsKey(columnIndex)) {
								widtMaxActual = obtenerWidt(widtMaxActual, porcentaje);
								columnWidtMaxMap.put(columnIndex, widtMaxActual);
							}
							int widtMax = columnWidtMaxMap.get(columnIndex);
							widtMaxActual = ObjectUtil.objectToString(value).length();
							widtMaxActual = obtenerWidt(widtMaxActual, porcentaje);
							if (widtMax < widtMaxActual) {
								columnWidtMaxMap.put(columnIndex, widtMaxActual);
							}
						}
						String keyPropiedad = i + "";
						if (propiedadCeldaMap.containsKey(keyPropiedad) && cellDetalle != null) {
							int posicionCelda = -1;
							boolean aplicaCeldaAll = true;
							boolean styleMarco = true;
							boolean boldNone = propiedadCeldaMap.get(keyPropiedad).containsKey("boldNone");
							if (propiedadCeldaMap.get(keyPropiedad).containsKey(POSICION_CELDA)) {
								posicionCelda = Integer
										.parseInt(propiedadCeldaMap.get(keyPropiedad).get(POSICION_CELDA) + "");
								aplicaCeldaAll = false;
							}
							if (propiedadCeldaMap.get(keyPropiedad).containsKey("styleMarcoNone")) {
								styleMarco = false;
							}
							if (propiedadCeldaMap.get(keyPropiedad).containsKey("cellStyle")
									&& (aplicaCeldaAll || posicionCelda == columnIndex)) {
								short fontHeightInPoints = 9;
								if (propiedadCeldaMap.get(keyPropiedad).containsKey(FONT_HEIGHT_IN_POINTS)) {
									fontHeightInPoints = (short) Integer.parseInt(
											propiedadCeldaMap.get(keyPropiedad).get(FONT_HEIGHT_IN_POINTS) + "");
								}
								HorizontalAlignment aling = null;
								if (propiedadCeldaMap.get(keyPropiedad).containsKey(ALING2)) {
									try {
										aling = HorizontalAlignment.forInt(
												Integer.parseInt(propiedadCeldaMap.get(keyPropiedad).get(ALING2) + ""));
									} catch (Exception e) {
										aling = null;
									}
								}
								CellStyle titleStyleVar = generarStyleTitleData(workbook, fontHeightInPoints,
										styleMarco, !boldNone);
								cellDetalle.setCellStyle(titleStyleVar);
								if (aling != null) {
									cellDetalle.getCellStyle().setAlignment(aling);
								}
							}

						}
						columnIndex++;
					}

					contador++;
					int posicionCeldaData = 0;
					for (var cellHeaderVO : listaHeaderData) {
						try {
							if (cellHeaderVO.getCantidadAgrupar() > 0) {
								CellRangeAddress cellRangeAddress = new CellRangeAddress(i + primeraFila,
										i + primeraFila, posicionCeldaData,
										((posicionCeldaData) - 1) + cellHeaderVO.getCantidadAgrupar().intValue());
								sheet.addMergedRegion(cellRangeAddress);
							}
						} catch (Exception e) {
							log.error("generarExcelXLSXPerWithGroup", e);
						}
						posicionCeldaData++;
					}
					i++;
				}

				Set<Integer> keySet = mapColumnGroup.keySet();

				for (var next : keySet) {
					mapColumnGroup.get(next).setFilaFin(i + primeraFila - 1);
					columnGroupList.add(mapColumnGroup.get(next));
				}

				for (var next : columnGroupList) {
					if (next.getFilaInicio() > next.getFilaFin())
						continue;
					CellRangeAddress range = new CellRangeAddress(next.getFilaInicio(), next.getFilaFin(),
							next.getColumnaInicio(), next.getColumnaFin());
					sheet.addMergedRegion(range);
				}

				// Inicio escribir pie de pagina
				if (!CollectionUtil.isEmpty(listaTituloFinal)) {
					for (var excelHederTitleVO : listaTituloFinal) {
						if (excelHederTitleVO.isEsPiePagina()) {
							int posicionRowVar = excelHederTitleVO.getPosicionRow();
							int posicionCeldaVar = excelHederTitleVO.getPosicionCelda();
							if (posicionRowVar > 0) {
								posicionRowVar = posicionRowVar - 1;
							}
							if (posicionCeldaVar > 0) {
								posicionCeldaVar = posicionCeldaVar - 1;
							}
							if (posicionRowVar > maxPosicionRow) {
								maxPosicionRow = posicionRowVar;
							}
							SXSSFRow filaTitle = sheet.getRow(posicionRowVar);
							if (filaTitle == null) {
								filaTitle = sheet.createRow(posicionRowVar);
							}
							String tituloFinalPer = excelHederTitleVO.getNameHeader();
							SXSSFCell heraderTitleCell = null;
							if (posicionCeldaVar > 0) {
								heraderTitleCell = filaTitle.createCell(posicionCeldaVar);
							} else {
								heraderTitleCell = filaTitle.createCell(0);
							}
							heraderTitleCell.setCellValue(tituloFinalPer);

							CellStyle titleStyleVar = generarStyleTitlePie(workbook,
									excelHederTitleVO.getFontHeightInPoints());
							heraderTitleCell.setCellStyle(titleStyleVar);
							heraderTitleCell.getCellStyle().setAlignment(excelHederTitleVO.getAling());

							if (excelHederTitleVO.getVerticalAlignment() != null) {
								heraderTitleCell.getCellStyle()
										.setVerticalAlignment(excelHederTitleVO.getVerticalAlignment());
							}
							heraderTitleCell.getCellStyle().setWrapText(excelHederTitleVO.isWrapText());

							excelHederTitleVO.setPosicionRow(posicionRowVar);
							excelHederTitleVO.setPosicionCelda(posicionCeldaVar);
							if (excelHederTitleVO.getRotacion() != 0) {
								heraderTitleCell.getCellStyle().setRotation((short) excelHederTitleVO.getRotacion());
							}
						}
					}
				}
				for (var excelHederTitleVO : listaTituloFinal) {
					if (excelHederTitleVO.isEsPiePagina()) {
						try {
							// int firstRow, int lastRow, int firstCol, int lastCol
							CellRangeAddress cellRangeAddress = null;
							if (excelHederTitleVO.getCantidadAgrupar() > 0
									&& excelHederTitleVO.getCantidadAgruparHorizontal() == 0) {
								cellRangeAddress = new CellRangeAddress(excelHederTitleVO.getPosicionRow().intValue(),
										excelHederTitleVO.getPosicionRow().intValue(),
										excelHederTitleVO.getPosicionCelda().intValue(),
										((excelHederTitleVO.getPosicionCelda().intValue()) - 1)
												+ excelHederTitleVO.getCantidadAgrupar().intValue());
								sheet.addMergedRegion(cellRangeAddress);
								generarMergeRegionBorderPie(cellRangeAddress, sheet);
							}
							if (excelHederTitleVO.getCantidadAgruparHorizontal() > 0
									&& excelHederTitleVO.getCantidadAgrupar() == 0) {
								cellRangeAddress = new CellRangeAddress(excelHederTitleVO.getPosicionRow().intValue(),
										((excelHederTitleVO.getPosicionRow().intValue() - 1)
												+ excelHederTitleVO.getCantidadAgruparHorizontal().intValue()),
										excelHederTitleVO.getPosicionCelda().intValue(),
										excelHederTitleVO.getPosicionCelda().intValue());
								sheet.addMergedRegion(cellRangeAddress);
								generarMergeRegionBorderPie(cellRangeAddress, sheet);
							}
							if (excelHederTitleVO.getCantidadAgruparHorizontal() > 0
									&& excelHederTitleVO.getCantidadAgrupar() > 0) {
								cellRangeAddress = new CellRangeAddress(excelHederTitleVO.getPosicionRow().intValue(),
										((excelHederTitleVO.getPosicionRow().intValue() - 1)
												+ excelHederTitleVO.getCantidadAgruparHorizontal().intValue()),
										excelHederTitleVO.getPosicionCelda().intValue(),
										((excelHederTitleVO.getPosicionCelda().intValue()) - 1)
												+ excelHederTitleVO.getCantidadAgrupar().intValue());
								sheet.addMergedRegion(cellRangeAddress);
								generarMergeRegionBorderPie(cellRangeAddress, sheet);
							}
						} catch (Exception e) {
							log.error("generarExcelXLSXPerWithGroup", e);
						}
					}
				}
				// Fin escrubir pie de pagina
				// Inicio agregar combo
				if (isCombo) {
					int hoja = 0;
					int cantidadRegistros = 100;
					if (listaData != null && !listaData.isEmpty()) {
						cantidadRegistros = listaData.size();
					}
					List<ExcelComboDataVO> listaDataCombo = (List<ExcelComboDataVO>) propiedadesMap.get(COMBO_DATA);
					if (listaDataCombo == null) {
						listaDataCombo = new ArrayList<>();
					}
					for (var excelComboDataVO : listaDataCombo) {
						String nombreColumna = excelComboDataVO.getNombreCampo();
						XSSFName namedCell = (XSSFName) workbook.createName();
						namedCell.setNameName(HIDDEN + hoja);
						namedCell.setRefersToFormula(
								HIDDEN + hoja + RANGO_CELDA + excelComboDataVO.getListaExcelComboData().size());

						DataValidationHelper dvHelper = sheet.getDataValidationHelper();
						DataValidationConstraint dataValidation = dvHelper.createFormulaListConstraint(HIDDEN + hoja);
						CellRangeAddressList addressList = new CellRangeAddressList(posicionRow, cantidadRegistros,
								campoPosicionMap.get(nombreColumna), campoPosicionMap.get(nombreColumna));
						DataValidation validation = dvHelper.createValidation(dataValidation, addressList);
						validation.setSuppressDropDownArrow(true);
						validation.setEmptyCellAllowed(true);
						validation.setShowPromptBox(true);
						validation.createErrorBox(MENSAJE, ELEMENTO_NO_VALIDO);
						sheet.addValidationData(validation);
						hoja++;
					}
					propiedadesMap.remove(COMBO_DATA);// limpiando data
				}
				// fin agregar combo
				int autoSizeColunm = 0;// 2
				int incrementoSize = 1;
				for (int x = 0; x < listaHeaderData.size(); x++) {
					if (calcularWitchDemanda) {
						try {
							int width = columnWidtMaxMap.get(autoSizeColunm);
							width *= 256;
							int maxColumnWidth = 255 * 256; // The maximum column width for an individual cell is 255
															// characters
							if (width > maxColumnWidth) {
								width = maxColumnWidth;
							}
							sheet.setColumnWidth(autoSizeColunm, width);
						} catch (Exception e) {
							log.error("ERROR autoSizeColunm -->" + autoSizeColunm, e);
						}
					} else {
						sheet.autoSizeColumn(autoSizeColunm);
					}
					autoSizeColunm = autoSizeColunm + incrementoSize;
				}
				if (i != 0) {
					CellRangeAddress border = new CellRangeAddress(primeraFila, i + primeraFila - 1, 1,
							columnIndex - 1);
				}
			}
			boolean anexarHojaProcesar = false;
			if (isCombo) {
				if (anexarHojaExistente) {
					anexarHojaProcesar = true;
				}
			} else {
				if (anexarHojaExistente) {
					anexarHojaProcesar = true;
				}
			}
			if (anexarHojaProcesar) {
				String nombreArchivo = (String) propiedadesMap.get(NOMBRE_ARCHIVO);
				int anexarHojaPosition = (Integer) propiedadesMap.get(ANEXAR_HOJA_POSITION);
				File rutaArchivo = new File(ConstanteConfigUtil.RUTA_GENERAL_TEMPLANTE + nombreArchivo);

				SXSSFWorkbook sXSSFWorkbookAnexar = ExcelUtil.leerExcelsXlsx(rutaArchivo);
				SXSSFSheet sheetAnexar = sXSSFWorkbookAnexar.getSheetAt(anexarHojaPosition - 1);
				if (sheetAnexar != null) {
					SXSSFSheet sheet = workbook.createSheet(sheetAnexar.getSheetName());
					sheet.setRandomAccessWindowSize(100);// keep 100 rows in memory, exceeding rows will be flushed to
															// disk
					TransferUtilExcel.copySheetsXLSX(sheet, sheetAnexar);
				}

			}
			if (isFormula) {
				try {// SXSSFWorkbook
					XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook.getXSSFWorkbook());
				} catch (Exception e) {
					log.error("generarExcelXLSXPerWithGroup", e);
				}
			}
			log.error(" generarExcelXLSXPerWithGroup generando " + archivoName);
			FileOutputStream out = new FileOutputStream(RUTA_RECURSOS_BYTE_BUFFER + "" + archivoName + XLSX);
			workbook.write(out);
			workbook.dispose();
			out.close();
			ExcelUtil.defaultLocaleProcess();
		} catch (Exception ex) {
			log.error("generarExcelXLSXPerWithGroup" + ex.getMessage(), ex);
			resultado = null;
		}
		return resultado;
	}

	private static void generarMergeRegionBorderPie(CellRangeAddress range, Sheet sheet) {
		// RegionUtil.setBorderBottom(BorderStyle.THIN, range, sheet);
		// RegionUtil.setBorderTop(BorderStyle.THIN, range, sheet);
		// RegionUtil.setBorderLeft(BorderStyle.THIN, range, sheet);
		// RegionUtil.setBorderRight(BorderStyle.THIN, range, sheet);
		// RegionUtil.setBorderRight(BorderStyle.THIN, range, sheet);
	}

	private static CellStyle generarStyleTitlePie(CellStyle titleStyle, Font titleFont) {
		titleStyle.setFont(titleFont);
		return titleStyle;
	}

	private static CellStyle generarStyleTitlePie(SXSSFWorkbook workbook, short fontHeightInPoints) {
		Font titleFont = generarTitleFontPie(workbook, fontHeightInPoints);
		CellStyle titleStyle = workbook.createCellStyle();
		return generarStyleTitlePie(titleStyle, titleFont);
	}

	private static Font generarTitleFontPie(SXSSFWorkbook workbook, short fontHeightInPoints) {
		Font titleFont = workbook.createFont();
		return generarTitleFontPie(titleFont, fontHeightInPoints);
	}

	private static Font generarTitleFontPie(Font titleFont, short fontHeightInPoints) {
		titleFont.setFontName(NOMBRE_LETRA);
		titleFont.setFontHeightInPoints(fontHeightInPoints);
		titleFont.setBold(true);
		return titleFont;
	}

	private static CellStyle generarStyleDate(HSSFWorkbook workbook) {
		DataFormat format = workbook.createDataFormat();
		CellStyle cellDateStyle = workbook.createCellStyle();
		return generarStyleDate(cellDateStyle, format);
	}

	private static CellStyle generarStyleDateBorder(SXSSFWorkbook workbook) {
		DataFormat format = workbook.createDataFormat();
		CellStyle cellDateStyle = workbook.createCellStyle();
		cellDateStyle = generarStyleDate(cellDateStyle, format);
		cellDateStyle.setBorderTop(BorderStyle.THIN);
		cellDateStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		cellDateStyle.setBorderRight(BorderStyle.THIN);
		cellDateStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		cellDateStyle.setBorderBottom(BorderStyle.THIN);
		cellDateStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		cellDateStyle.setBorderLeft(BorderStyle.THIN);
		cellDateStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		return cellDateStyle;
	}

	private static CellStyle generarStyleDate(SXSSFWorkbook workbook) {
		DataFormat format = workbook.createDataFormat();
		CellStyle cellDateStyle = workbook.createCellStyle();
		return generarStyleDate(cellDateStyle, format);
	}

	private static CellStyle generarStyleTitle(HSSFWorkbook workbook) {
		Font titleFont = generarTitleFont(workbook);
		CellStyle titleStyle = workbook.createCellStyle();
		return generarStyleTitle(titleStyle, titleFont, true);
	}

	private static CellStyle generarStyleTitle(SXSSFWorkbook workbook) {
		Font titleFont = generarTitleFont(workbook);
		CellStyle titleStyle = workbook.createCellStyle();
		return generarStyleTitle(titleStyle, titleFont, true);
	}

	private static CellStyle generarStyleTitle(CellStyle titleStyle, Font titleFont, boolean styleMarco) {
		titleStyle.setFont(titleFont);
		if (styleMarco) {
			titleStyle.setBorderTop(BorderStyle.THIN);
			titleStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
			titleStyle.setBorderRight(BorderStyle.THIN);
			titleStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
			titleStyle.setBorderBottom(BorderStyle.THIN);
			titleStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			titleStyle.setBorderLeft(BorderStyle.THIN);
			titleStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		}
		return titleStyle;
	}

	private static Font generarTitleFont(HSSFWorkbook workbook) {
		Font titleFont = workbook.createFont();
		return generarTitleFont(titleFont);
	}

	private static Font generarTitleFont(Font titleFont) {
		titleFont.setFontName(NOMBRE_LETRA);
		titleFont.setFontHeightInPoints((short) 9);
		titleFont.setBold(true);
		titleFont.setColor(HSSFColorPredefined.BLACK.getIndex());
		return titleFont;
	}

	private static Font generarTitleFont(SXSSFWorkbook workbook) {
		Font titleFont = workbook.createFont();
		return generarTitleFont(titleFont);
	}

	private static HSSFWorkbook generarComboHoja(HSSFWorkbook workbook, Map<String, Object> propiedadesMap) {
		List<ExcelComboDataVO> listaData = (List<ExcelComboDataVO>) propiedadesMap.get(COMBO_DATA);
		int hoja = 0;
		for (var excelComboDataVO : listaData) {
			HSSFSheet hidden = workbook.createSheet(HIDDEN + hoja);
			int i = 0;
			for (var dataCombo : excelComboDataVO.getListaExcelComboData()) {
				Row row = hidden.createRow(i);
				Cell cell = row.createCell(0);
				cell.setCellValue(dataCombo);
				i++;
			}
			for (int ih = 0; ih < listaData.size(); ih++) {
				hidden.autoSizeColumn(ih, true);
			}
			workbook.setSheetHidden(hoja, true);
			hoja++;
		}
		return workbook;
	}

	private static SXSSFWorkbook generarComboHojaXLSX(SXSSFWorkbook workbook, Map<String, Object> propiedadesMap) {
		List<ExcelComboDataVO> listaData = (List<ExcelComboDataVO>) propiedadesMap.get(COMBO_DATA);
		int hoja = 0;
		for (var excelComboDataVO : listaData) {
			SXSSFSheet hidden = workbook.createSheet(HIDDEN + hoja);
			hidden.setRandomAccessWindowSize(100);// keep 100 rows in memory, exceeding rows will be flushed to disk
			int i = 0;
			for (var dataCombo : excelComboDataVO.getListaExcelComboData()) {
				Row row = hidden.createRow(i);
				Cell cell = row.createCell(0);
				cell.setCellValue(dataCombo);
				i++;
			}
			for (int ih = 0; ih < listaData.size(); ih++) {
				hidden.autoSizeColumn(ih, true);
			}
			workbook.setSheetHidden(hoja, true);
			hoja++;
		}
		return workbook;
	}

	/**
	 * Generar excel object.
	 *
	 * @param listaHeader    el lista header
	 * @param listaData      el lista data
	 * @param archivoName    el archivo name
	 * @param titulo         el titulo
	 * @param propiedadesMap el propiedades map
	 * @return the byte[]
	 */
	public static byte[] generarExcelObject(List<String> listaHeader, List<Object[]> listaData, String archivoName,
			String titulo, Map<String, String> propiedadesMap) {
		byte[] resultado = null;
		try {
			File archivoXLS = new File(RUTA_RECURSOS_BYTE_BUFFER);
			if (!archivoXLS.isFile()) {
				archivoXLS.mkdirs();
			}
			HSSFWorkbook workbook = new HSSFWorkbook();
			int cantidadData = listaData.size();
			int cantidadHojas = 1;
			if (cantidadData > MAXIMO_RANGE_EXCEL) {
				BigDecimal bCantidadData = new BigDecimal(cantidadData);
				BigDecimal maxRange = new BigDecimal(MAXIMO_RANGE_EXCEL);
				BigDecimal bCantidadHojas = bCantidadData.divide(maxRange, 2, BigDecimal.ROUND_UP);
				bCantidadHojas = bCantidadHojas.setScale(0, BigDecimal.ROUND_UP);
				cantidadHojas = bCantidadHojas.intValue();
			}
			CellStyle cellDateStyle = generarStyleDate(workbook);
			CellStyle titleStyle = generarStyleTitle(workbook);
			for (int cantidadDataPaginadorHoja = 1; cantidadDataPaginadorHoja <= cantidadHojas; cantidadDataPaginadorHoja++) {
				String tituloFinal = titulo;
				if (propiedadesMap.containsKey(HOJA_NAME)) {
					tituloFinal = propiedadesMap.get(HOJA_NAME);
				}
				if (cantidadHojas > 1) {
					tituloFinal = tituloFinal + cantidadDataPaginadorHoja;
				}
				HSSFSheet sheet = workbook.createSheet(tituloFinal);

				int posicionRow = 0;
				int incrementroRow = 1;
				if (propiedadesMap.containsKey(PRINT_TITLE_VIEW)) {
					Row filaTitle = sheet.createRow(posicionRow);
					Cell heraderTitleCell = filaTitle.createCell((listaHeader.size() / 2));
					heraderTitleCell.setCellValue(titulo);
					posicionRow = posicionRow + incrementroRow;
				}
				// creando cabecera del datos
				if (propiedadesMap.containsKey(ROW_INICIO)) {
					posicionRow = Integer.parseInt(propiedadesMap.get(ROW_INICIO)) - 1;
				}
				Row fila = sheet.createRow(posicionRow);
				int posicionCellCabecera = 0;
				int incremetoCellCabecera = 1;
				for (var cellHeader : listaHeader) {
					Cell heraderCell = fila.createCell(posicionCellCabecera);
					heraderCell.setCellValue(cellHeader);
					heraderCell.setCellStyle(titleStyle);
					posicionCellCabecera = posicionCellCabecera + incremetoCellCabecera;
				}
				posicionRow = posicionRow + incrementroRow;
				// llenando la data
				int primeraFila = posicionRow;
				int i = 0;
				int fromIndex = fromIndex(cantidadDataPaginadorHoja);
				int toIndex = toIndex(cantidadData, cantidadDataPaginadorHoja);
				for (var cellData : listaData.subList(fromIndex, toIndex)) {
					Row filaDet = sheet.createRow(i + primeraFila);
					posicionCellCabecera = 0;
					incremetoCellCabecera = 1;

					for (int k = 0; k < listaHeader.size(); k++) {
						Object value = null;
						value = cellData[k];
						if (esFechaData(value)) {
							Cell cellDetalle = filaDet.createCell(posicionCellCabecera);
							cellDetalle.setCellValue((LocalDateTime) value);
							cellDetalle.setCellStyle(cellDateStyle);
							posicionCellCabecera = posicionCellCabecera + incremetoCellCabecera;
						} else {
							Cell cellDetalle = filaDet.createCell(posicionCellCabecera);
							cellDetalle.setCellValue(value == null ? "" : value.toString());
							posicionCellCabecera = posicionCellCabecera + incremetoCellCabecera;
						}

					}
					i++;
				}

				int autoSizeColunm = 0;// 2
				int incrementoSize = 1;

				for (int ih = 0; ih < listaHeader.size(); ih++) {
					sheet.autoSizeColumn(autoSizeColunm);
					autoSizeColunm = autoSizeColunm + incrementoSize;
				}
			}

			FileOutputStream out = new FileOutputStream(RUTA_RECURSOS_BYTE_BUFFER + "" + archivoName + ".xls");
			workbook.write(out);
			workbook.close();
			out.close();
			ExcelUtil.defaultLocaleProcess();
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			resultado = null;
		}
		return resultado;
	}

	/**
	 * Generar excel con cabecera ovveridee.
	 * 
	 * @param listaHeader            lista de cabeceras.
	 * @param listaHeaderOverrideMap lista de cabeceras que se requieren
	 *                               sobreescribir.
	 * @param listaDataMap           lista de data
	 * @param archivoName
	 * @param titulo
	 * @param propiedadesMap
	 * @return bytes
	 */
	public static byte[] generarExcelObjectMap(List<String> listaHeader, Map<String, String> listaHeaderOverrideMap,
			List<Map<String, ValueDataVO>> listaDataMap, String archivoName, String titulo,
			Map<String, String> propiedadesMap) {
		byte[] resultado = null;
		try {
			File archivoXLS = new File(RUTA_RECURSOS_BYTE_BUFFER);
			if (!archivoXLS.isFile()) {
				archivoXLS.mkdirs();
			}
			HSSFWorkbook workbook = new HSSFWorkbook();
			int cantidadData = listaDataMap.size();
			int cantidadHojas = 1;
			if (cantidadData > MAXIMO_RANGE_EXCEL) {
				BigDecimal bCantidadData = new BigDecimal(cantidadData);
				BigDecimal maxRange = new BigDecimal(MAXIMO_RANGE_EXCEL);
				BigDecimal bCantidadHojas = bCantidadData.divide(maxRange, 2, BigDecimal.ROUND_UP);
				bCantidadHojas = bCantidadHojas.setScale(0, BigDecimal.ROUND_UP);
				cantidadHojas = bCantidadHojas.intValue();
			}

			CellStyle cellDateStyle = generarStyleDate(workbook);
			CellStyle titleStyle = generarStyleTitle(workbook);

			for (int cantidadDataPaginadorHoja = 1; cantidadDataPaginadorHoja <= cantidadHojas; cantidadDataPaginadorHoja++) {
				String tituloFinal = titulo;
				if (propiedadesMap.containsKey(HOJA_NAME)) {
					tituloFinal = propiedadesMap.get(HOJA_NAME);
				}
				if (cantidadHojas > 1) {
					tituloFinal = tituloFinal + cantidadDataPaginadorHoja;
				}
				HSSFSheet sheet = workbook.createSheet(tituloFinal);

				int posicionRow = 0;
				int incrementroRow = 1;
				if (propiedadesMap.containsKey(PRINT_TITLE_VIEW)) {
					Row filaTitle = sheet.createRow(posicionRow);
					Cell heraderTitleCell = filaTitle.createCell((listaHeader.size() / 2));
					heraderTitleCell.setCellValue(titulo);
					posicionRow = posicionRow + incrementroRow;
				}
				// creando cabecera del datos
				if (propiedadesMap.containsKey(ROW_INICIO)) {
					posicionRow = Integer.parseInt(propiedadesMap.get(ROW_INICIO)) - 1;
				}
				Row fila = sheet.createRow(posicionRow);
				sheet.createFreezePane(0, posicionRow + 1);
				int posicionCellCabecera = 0;
				int incremetoCellCabecera = 1;
				for (var cellHeaderKey : listaHeader) {
					String cellHeader = cellHeaderKey;
					if (listaHeaderOverrideMap.containsKey(cellHeaderKey)) {
						cellHeader = listaHeaderOverrideMap.get(cellHeaderKey);
					}
					Cell heraderCell = fila.createCell(posicionCellCabecera);
					heraderCell.setCellValue(cellHeader);
					heraderCell.setCellStyle(titleStyle);
					posicionCellCabecera = posicionCellCabecera + incremetoCellCabecera;
				}
				posicionRow = posicionRow + incrementroRow;
				// llenando la data
				int primeraFila = posicionRow;
				int i = 0;
				int fromIndex = fromIndex(cantidadDataPaginadorHoja);
				int toIndex = toIndex(cantidadData, cantidadDataPaginadorHoja);
				for (var dataMap : listaDataMap.subList(fromIndex, toIndex)) {
					Row filaDet = sheet.createRow(i + primeraFila);
					posicionCellCabecera = 0;
					incremetoCellCabecera = 1;

					for (var headerKey : listaHeader) {
						Object value = null;
						if (propiedadesMap.containsKey(headerKey)) {
							value = dataMap.get(headerKey);
							if (StringUtil.isNullOrEmpty(value)) {
								value = 0;
							}
						} else {
							value = dataMap.get(headerKey);
						}

						if (esFechaData(value)) {
							Cell cellDetalle = filaDet.createCell(posicionCellCabecera);
							cellDetalle.setCellValue((LocalDateTime) value);
							cellDetalle.setCellStyle(cellDateStyle);
							posicionCellCabecera = posicionCellCabecera + incremetoCellCabecera;
						} else {
							if (propiedadesMap != null && propiedadesMap.containsKey(headerKey + NUMERIC)) {
								Cell cellDetalle = filaDet.createCell(posicionCellCabecera);
								if (!StringUtil.isNullOrEmptyNumeric(value)) {
									cellDetalle.setCellValue(Double.valueOf(value.toString()));
								} else {
									cellDetalle.setCellValue(value == null ? "" : value.toString());
								}
								posicionCellCabecera = posicionCellCabecera + incremetoCellCabecera;
							} else {
								Cell cellDetalle = filaDet.createCell(posicionCellCabecera);
								if (esNumericoData(value)) {
									cellDetalle.setCellValue(Double.valueOf(value.toString()));
								} else {
									cellDetalle.setCellValue(value == null ? "" : value.toString());
								}
								posicionCellCabecera = posicionCellCabecera + incremetoCellCabecera;
							}
						}

					}
					i++;
				}

				int autoSizeColunm = 0;// 2
				int incrementoSize = 1;

				for (int ih = 0; ih < listaHeader.size(); ih++) {
					sheet.autoSizeColumn(autoSizeColunm);
					autoSizeColunm = autoSizeColunm + incrementoSize;
				}
			}
			FileOutputStream out = new FileOutputStream(RUTA_RECURSOS_BYTE_BUFFER + "" + archivoName + ".xls");
			workbook.write(out);
			workbook.close();
			out.close();
			ExcelUtil.defaultLocaleProcess();
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			resultado = null;
		}
		return resultado;
	}

	public static void generarExcelObjectXLSXMap(List<ExcelHederDataVO> listaHeader,
			List<Map<String, Object>> listaDataMap, String archivoName, String titulo,
			Map<String, String> propiedadesMap) {
		try {
			File archivoXLS = new File(RUTA_RECURSOS_BYTE_BUFFER);
			if (!archivoXLS.isFile()) {
				archivoXLS.mkdirs();
			}
			SXSSFWorkbook workbook = new SXSSFWorkbook(100);
			workbook.setCompressTempFiles(true); // temp files will be gzipped
			int cantidadData = listaDataMap.size();
			int cantidadHojas = 1;
			if (cantidadData > MAXIMO_RANGE_EXCEL_XLSX) {
				BigDecimal bCantidadData = new BigDecimal(cantidadData);
				BigDecimal maxRange = new BigDecimal(MAXIMO_RANGE_EXCEL_XLSX);
				BigDecimal bCantidadHojas = bCantidadData.divide(maxRange, 2, BigDecimal.ROUND_UP);
				bCantidadHojas = bCantidadHojas.setScale(0, BigDecimal.ROUND_UP);
				cantidadHojas = bCantidadHojas.intValue();
			}
			CellStyle cellDateStyle = generarStyleDate(workbook);
			CellStyle titleStyle = generarStyleTitle(workbook);
			log.info("generarExcelObjectMapBigMemory.cantidadHojas --> " + cantidadHojas);
			for (int cantidadDataPaginadorHoja = 1; cantidadDataPaginadorHoja <= cantidadHojas; cantidadDataPaginadorHoja++) {
				String tituloFinal = titulo;
				if (propiedadesMap.containsKey(HOJA_NAME)) {
					tituloFinal = propiedadesMap.get(HOJA_NAME);
				}
				log.info("generarExcelObjectMapBigMemory.cantidadDataPaginadorHoja --> " + cantidadDataPaginadorHoja);
				log.info("generarExcelObjectMapBigMemory.tituloFinal antes --> " + tituloFinal);
				if (cantidadDataPaginadorHoja > 1) {
					tituloFinal = cantidadDataPaginadorHoja + tituloFinal;
				}
				log.info("generarExcelObjectMapBigMemory.tituloFinal despues --> " + tituloFinal);
				SXSSFSheet sheet = workbook.createSheet(tituloFinal);
				sheet.setRandomAccessWindowSize(100);// keep 100 rows in memory, exceeding rows will be flushed to disk
				int posicionRow = 0;
				int incrementroRow = 1;
				if (propiedadesMap.containsKey(PRINT_TITLE_VIEW)) {
					SXSSFRow filaTitle = sheet.createRow(posicionRow);
					SXSSFCell heraderTitleCell = filaTitle.createCell(0);
					heraderTitleCell.setCellValue(titulo);
					heraderTitleCell.setCellStyle(titleStyle);
					if (listaHeader.size() > 1) {
						sheet.addMergedRegion(
								new CellRangeAddress(posicionRow, posicionRow, 0, listaHeader.size() - 1));
					}
					posicionRow = posicionRow + incrementroRow;
				}
				// creando cabecera del datos
				if (propiedadesMap.containsKey(ROW_INICIO)) {
					posicionRow = Integer.parseInt(propiedadesMap.get(ROW_INICIO)) - 1;
				}
				SXSSFRow fila = sheet.createRow(posicionRow);
				int posicionCellCabecera = 0;
				int incremetoCellCabecera = 1;
				for (var cellHeaderKey : listaHeader) {
					String cellHeader = cellHeaderKey.getNameHeader();
					SXSSFCell heraderCell = fila.createCell(posicionCellCabecera);
					heraderCell.setCellValue(cellHeader);
					heraderCell.setCellStyle(titleStyle);
					posicionCellCabecera = posicionCellCabecera + incremetoCellCabecera;
				}
				posicionRow = posicionRow + incrementroRow;
				// llenando la data
				int primeraFila = posicionRow;
				int i = 0;
				int fromIndex = fromIndexXlsx(cantidadDataPaginadorHoja);
				int toIndex = toIndexXlsx(cantidadData, cantidadDataPaginadorHoja);
				for (var dataMap : listaDataMap.subList(fromIndex, toIndex)) {
					SXSSFRow filaDet = sheet.createRow(i + primeraFila);
					posicionCellCabecera = 0;
					incremetoCellCabecera = 1;

					for (var headerKey : listaHeader) {
						Object value = null;
						if (propiedadesMap.containsKey(headerKey.getNameAtribute())) {
							value = dataMap.get(headerKey.getNameAtribute());
							if (StringUtil.isNullOrEmpty(value)) {
								value = 0;
							}
						} else {
							value = dataMap.get(headerKey.getNameAtribute());
						}

						if (esFechaData(value)) {
							SXSSFCell cellDetalle = filaDet.createCell(posicionCellCabecera);
							cellDetalle.setCellValue((LocalDateTime) value);
							cellDetalle.setCellStyle(cellDateStyle);
							posicionCellCabecera = posicionCellCabecera + incremetoCellCabecera;
						} else {
							if (propiedadesMap != null
									&& propiedadesMap.containsKey(headerKey.getNameAtribute() + NUMERIC)) {
								SXSSFCell cellDetalle = filaDet.createCell(posicionCellCabecera);
								if (!StringUtil.isNullOrEmptyNumeric(value)) {
									cellDetalle.setCellValue(Double.valueOf(value.toString()));
								} else {
									cellDetalle.setCellValue(value == null ? "" : value.toString());
								}
								posicionCellCabecera = posicionCellCabecera + incremetoCellCabecera;
							} else {
								SXSSFCell cellDetalle = filaDet.createCell(posicionCellCabecera);
								if (esNumericoData(value)) {
									cellDetalle.setCellValue(Double.valueOf(value.toString()));
								} else {
									cellDetalle.setCellValue(value == null ? "" : value.toString());
								}
								posicionCellCabecera = posicionCellCabecera + incremetoCellCabecera;
							}
						}

					}
					i++;
				}

				int autoSizeColunm = 0;// 2
				int incrementoSize = 1;

				for (int ih = 0; ih < listaHeader.size(); ih++) {
					sheet.autoSizeColumn(autoSizeColunm);
					autoSizeColunm = autoSizeColunm + incrementoSize;
				}
			}
			FileOutputStream out = new FileOutputStream(RUTA_RECURSOS_BYTE_BUFFER + "" + archivoName + XLSX);
			workbook.write(out);
			workbook.dispose();
			out.close();
			ExcelUtil.defaultLocaleProcess();
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
	}

	/**
	 * From index.
	 *
	 * @param dataPaginator el data paginator
	 * @return the int
	 */
	private static int fromIndex(Integer dataPaginator) {
		int pagina = 0;
		if (dataPaginator == null) {
			pagina = 1;
		} else {
			pagina = dataPaginator;
		}
		return ((pagina - 1) * (MAXIMO_RANGE_EXCEL - CANTIDAD_FILAS_USADO_CABECERA));
	}

	private static int fromIndexXlsx(Integer dataPaginator) {
		int pagina = 0;
		if (dataPaginator == null) {
			pagina = 1;
		} else {
			pagina = dataPaginator;
		}
		return ((pagina - 1) * (MAXIMO_RANGE_EXCEL_XLSX - CANTIDAD_FILAS_USADO_CABECERA));
	}

	/**
	 * To index.
	 *
	 * @param cantidadTotalData el cantidad total data
	 * @param dataPaginator     el data paginator
	 * @return the int
	 */
	private static int toIndex(int cantidadTotalData, Integer dataPaginator) {
		int pagina = 0;
		if (dataPaginator == null) {
			pagina = 1;
		} else {
			pagina = dataPaginator;
		}
		int toIndex = ((pagina - 1) * (MAXIMO_RANGE_EXCEL - CANTIDAD_FILAS_USADO_CABECERA))
				+ (MAXIMO_RANGE_EXCEL - CANTIDAD_FILAS_USADO_CABECERA);
		if (toIndex > cantidadTotalData) {
			toIndex = cantidadTotalData;
		}
		return toIndex;
	}

	private static int toIndexXlsx(int cantidadTotalData, Integer dataPaginator) {
		int pagina = 0;
		if (dataPaginator == null) {
			pagina = 1;
		} else {
			pagina = dataPaginator;
		}
		int toIndex = ((pagina - 1) * (MAXIMO_RANGE_EXCEL_XLSX - CANTIDAD_FILAS_USADO_CABECERA))
				+ (MAXIMO_RANGE_EXCEL_XLSX - CANTIDAD_FILAS_USADO_CABECERA);
		if (toIndex > cantidadTotalData) {
			toIndex = cantidadTotalData;
		}
		return toIndex;
	}

	/**
	 * Es fecha.
	 *
	 * @param columnaName el columna name
	 * @return true, en caso de exito
	 */
	private static boolean esFecha(String columnaName) {
		boolean resultado = false;
		if (columnaName.toUpperCase().contains("fecha".toUpperCase())) {
			resultado = true;
		}
		return resultado;
	}

	/**
	 * Es fecha data.
	 *
	 * @param valueDate el value date
	 * @return true, en caso de exito
	 */
	private static boolean esFechaData(Object valueDate) {
		boolean resultado = false;
		if (valueDate != null && (valueDate.getClass().isAssignableFrom(LocalDateTime.class)
				|| valueDate.getClass().isAssignableFrom(java.sql.Timestamp.class))) {
			resultado = true;
		}
		return resultado;
	}

	/**
	 * Verificar fornato fecha.
	 *
	 * @param columnaName el columna name
	 * @param value       el value
	 * @return the object
	 */
	private static Object verificarFornatoFecha(String columnaName, Object value) {
		Object resultado = value;
		if (esFecha(columnaName)) {
			try {
				OffsetDateTime date = FechaUtil.obtenerFechaFormatoCompleto(value.toString());
				resultado = date;
			} catch (Exception e) {
				resultado = value;
			}
		} else {
			resultado = value;
		}
		return resultado;
	}

	/**
	 * Atributo value complejo.
	 *
	 * @param object        el object
	 * @param nombreColumna el nombre columna
	 * @return the object
	 */
	private static Object atributoValueComplejo(Object object, String nombreColumna) {
		Object resultado = null;
		String nombreColumnaReplace = nombreColumna.replace(".", ":");
		String[] objeto = nombreColumnaReplace.split(":");
		int cantidadPropiedad = objeto.length;
		if (cantidadPropiedad == 1) {
			resultado = getValue(object, nombreColumna);
		}
		if (cantidadPropiedad > 1) {
			String propertyName = objeto[cantidadPropiedad - 1];
			Object object2 = object;
			for (var string : objeto) {
				if (!string.equals(propertyName)) {
					object2 = getValue(object2, string);
				}
			}
			resultado = atributoValueComplejo(object2, propertyName);
		}

		return resultado;
	}

	/**
	 * Obtiene value.
	 *
	 * @param object        el object
	 * @param nombreColumna el nombre columna
	 * @return value
	 */
	public static Object getValue(Object object, String nombreColumna) {
		Object resultado = null;
		try {
			BeanMap beanMap = new BeanMap(object);
			resultado = beanMap.get(nombreColumna);
		} catch (Exception e) {
			resultado = null;
		}

		return resultado;
	}

	/**
	 * Obtener widt.
	 *
	 * @param widtMaxActual el widt max actual
	 * @param porcentaje    el porcentaje
	 * @return the integer
	 */
	private static Integer obtenerWidt(int widtMaxActual, double porcentaje) {
		return (new BigDecimal(widtMaxActual + (widtMaxActual * porcentaje)).setScale(0, RoundingMode.HALF_UP)
				.intValue());
	}

	private static boolean esNumericoData(Object value) {
		boolean resultado = false;
		if (value == null) {
			return resultado;
		}
		if (value instanceof Number) {
			resultado = true;
		}
		return resultado;
	}

	public static byte[] generarExcelXLSXMap(List<String> listaHeaderData, List<?> listaData, String archivoName,
			String titulo, Map<String, Object> propiedadesMap) {
		byte[] resultado = null;
		try {
			Map<Integer, Integer> columnWidtMaxMap = new HashMap<>();
			boolean calcularWitchDemanda = propiedadesMap.containsKey(CALCULAR_WITCH_DEMANDA);
			// Inicio Agregar coombo
			int hojaActiva = 0;
			Map<String, Integer> campoPosicionMap = new HashMap<>();
			boolean isCombo = propiedadesMap.containsKey(COMBO_DATA);
			boolean anexarHojaExistente = propiedadesMap.containsKey(ANEXAR_HOJA_EXISTENTE);
			int posicionCellCabecera = 0;
			if (isCombo) {
				for (var nombreColumna : listaHeaderData) {
					if (!campoPosicionMap.containsKey(nombreColumna)) {
						campoPosicionMap.put(nombreColumna, posicionCellCabecera);
					}
					posicionCellCabecera++;
				}
			}
			// Fin Agregar coombo
			File archivoXLS = new File(RUTA_RECURSOS_BYTE_BUFFER);
			if (!archivoXLS.isFile()) {
				archivoXLS.mkdirs();
			}
			SXSSFWorkbook workbook = new SXSSFWorkbook(100);
			workbook.setCompressTempFiles(true); // temp files will be gzipped
			if (isCombo) {
				generarComboHojaXLSX(workbook, propiedadesMap);
			}
			int cantidadData = listaData.size();
			int cantidadHojas = 1;
			int contador = 0;
			if (cantidadData > MAXIMO_RANGE_EXCEL_XLSX) {
				BigDecimal bCantidadData = new BigDecimal(cantidadData);
				BigDecimal maxRange = new BigDecimal(MAXIMO_RANGE_EXCEL_XLSX);
				BigDecimal bCantidadHojas = bCantidadData.divide(maxRange, 2, BigDecimal.ROUND_UP);
				bCantidadHojas = bCantidadHojas.setScale(0, BigDecimal.ROUND_UP);
				cantidadHojas = bCantidadHojas.intValue();
			}

			CellStyle cellDateStyle = generarStyleDate(workbook);
			Map<String, CellStyle> cellStyleMap = new HashMap<>();
			// indicando un patron de formato
			CellStyle titleStyle = generarStyleTitle(workbook);
			for (int cantidadDataPaginadorHoja = 1; cantidadDataPaginadorHoja <= cantidadHojas; cantidadDataPaginadorHoja++) {
				String tituloFinal = titulo;
				if (propiedadesMap != null && propiedadesMap.containsKey(HOJA_NAME)) {
					tituloFinal = propiedadesMap.get(HOJA_NAME) + "";
				}
				if (cantidadHojas > 1) {
					tituloFinal = tituloFinal + cantidadDataPaginadorHoja;
				}
				SXSSFSheet sheet = workbook.createSheet(tituloFinal); // CREA UNA HOJA
				sheet.setRandomAccessWindowSize(100);// keep 100 rows in memory, exceeding rows will be flushed to disk
				hojaActiva++;
				int posicionRow = 0;
				int incrementroRow = 1;
				if (propiedadesMap != null && propiedadesMap.containsKey(PRINT_TITLE_VIEW)) {
					SXSSFRow filaTitle = sheet.createRow(posicionRow);
					SXSSFCell heraderTitleCell = filaTitle.createCell((listaHeaderData.size() / 2));
					heraderTitleCell.setCellValue(titulo);
					posicionRow = posicionRow + incrementroRow;
				}
				// creando cabecera del datos
				if (propiedadesMap != null && propiedadesMap.containsKey(ROW_INICIO)) {
					posicionRow = Integer.parseInt(propiedadesMap.get(ROW_INICIO) + "") - 1;
				}
				SXSSFRow fila = sheet.createRow(posicionRow);
				posicionCellCabecera = 0;
				int incremetoCellCabecera = 1;
				for (var cellHeader : listaHeaderData) {
					SXSSFCell heraderCell = fila.createCell(posicionCellCabecera);
					heraderCell.setCellValue(cellHeader);
					heraderCell.setCellStyle(titleStyle);
					posicionCellCabecera = posicionCellCabecera + incremetoCellCabecera;
				}
				posicionRow = posicionRow + incrementroRow;
				// llenando la data
				int primeraFila = posicionRow;
				int i = 0;
				int fromIndex = fromIndexXlsx(cantidadDataPaginadorHoja);
				int toIndex = toIndexXlsx(cantidadData, cantidadDataPaginadorHoja);
				for (var cellData : listaData.subList(fromIndex, toIndex)) {
					SXSSFRow filaDet = sheet.createRow(i + primeraFila);
					posicionCellCabecera = 0;
					incremetoCellCabecera = 1;
					int columnIndex = 0;
					for (var nombreColumna : listaHeaderData) {
						Object value = null;
						if (!nombreColumna.equals(ROW_INFO_INDEX)) {
							value = atributoValueComplejo(cellData, nombreColumna);
						} else {
							value = (contador + 1);
						}
						if (esFecha(nombreColumna)) {
							Object valueDate = verificarFornatoFecha(nombreColumna, value);
							if (esFechaData(valueDate)) {
								SXSSFCell cellDetalle = filaDet.createCell(posicionCellCabecera);
								cellDetalle.setCellValue((LocalDateTime) valueDate);
								if (propiedadesMap != null && propiedadesMap.containsKey(nombreColumna + FORMAT)) {
									if (!cellStyleMap.containsKey(nombreColumna)) {
										DataFormat format = workbook.createDataFormat();
										CellStyle cellDateStyleFormat = workbook.createCellStyle();
										cellDateStyleFormat.setDataFormat(
												format.getFormat(propiedadesMap.get(nombreColumna + FORMAT) + ""));

										cellDetalle.setCellStyle(cellDateStyleFormat);
										cellStyleMap.put(nombreColumna, cellDateStyleFormat);
									} else {
										cellDetalle.setCellStyle(cellStyleMap.get(nombreColumna));
									}

								} else {
									cellDetalle.setCellStyle(cellDateStyle);
								}

								posicionCellCabecera = posicionCellCabecera + incremetoCellCabecera;
							} else {
								SXSSFCell cellDetalle = filaDet.createCell(posicionCellCabecera);
								cellDetalle.setCellValue(value == null ? "" : value.toString());
								posicionCellCabecera = posicionCellCabecera + incremetoCellCabecera;
							}
						} else {
							SXSSFCell cellDetalle = filaDet.createCell(posicionCellCabecera);
							cellDetalle.setCellValue(value == null ? "" : value.toString());
							posicionCellCabecera = posicionCellCabecera + incremetoCellCabecera;
						}

						if (calcularWitchDemanda) {
							int widtMaxActual = ObjectUtil.objectToString(nombreColumna).length();
							double porcentaje = 0.20;
							if (!columnWidtMaxMap.containsKey(columnIndex)) {
								widtMaxActual = obtenerWidt(widtMaxActual, porcentaje);
								columnWidtMaxMap.put(columnIndex, widtMaxActual);
							}
							int widtMax = columnWidtMaxMap.get(columnIndex);
							widtMaxActual = ObjectUtil.objectToString(value).length();
							widtMaxActual = obtenerWidt(widtMaxActual, porcentaje);
							if (widtMax < widtMaxActual) {
								columnWidtMaxMap.put(columnIndex, widtMaxActual);
							}
						}
						columnIndex++;
					}
					i++;
					contador++;
				}
				// Inicio agregar combo
				if (isCombo) {
					int hoja = 0;
					int cantidadRegistros = 100;
					if (listaData != null && !listaData.isEmpty()) {
						cantidadRegistros = listaData.size();
					}
					List<ExcelComboDataVO> listaDataCombo = (List<ExcelComboDataVO>) propiedadesMap.get(COMBO_DATA);
					if (listaDataCombo == null) {
						listaDataCombo = new ArrayList<>();
					}
					for (var excelComboDataVO : listaDataCombo) {
						hojaActiva++;
						String nombreColumna = excelComboDataVO.getNombreCampo();
						XSSFName namedCell = (XSSFName) workbook.createName();
						namedCell.setNameName(HIDDEN + hoja);
						namedCell.setRefersToFormula(
								HIDDEN + hoja + RANGO_CELDA + excelComboDataVO.getListaExcelComboData().size());

						DVConstraint constraint = DVConstraint.createFormulaListConstraint(HIDDEN + hoja);
						CellRangeAddressList addressList = new CellRangeAddressList(posicionRow, cantidadRegistros,
								campoPosicionMap.get(nombreColumna), campoPosicionMap.get(nombreColumna));
						HSSFDataValidation validation = new HSSFDataValidation(addressList, constraint);

						validation.setSuppressDropDownArrow(false);
						validation.setEmptyCellAllowed(false);
						validation.setShowPromptBox(false);
						validation.createErrorBox(MENSAJE, ELEMENTO_NO_VALIDO);

						sheet.addValidationData(validation);
						hoja++;
					}
					propiedadesMap.remove(COMBO_DATA);// limpiando data
				}
				// fin agregar combo
				int autoSizeColunm = 0;// 2
				int incrementoSize = 1;

				for (int ih = 0; ih < listaHeaderData.size(); ih++) {
					if (calcularWitchDemanda) {
						try {
							int width = columnWidtMaxMap.get(autoSizeColunm);
							width *= 256;
							int maxColumnWidth = 255 * 256; // The maximum column width for an individual cell is 255
															// characters
							if (width > maxColumnWidth) {
								width = maxColumnWidth;
							}
							sheet.setColumnWidth(autoSizeColunm, width);
						} catch (Exception e) {
							// log.error("ERROR autoSizeColunm -->" +
							// autoSizeColunm);
						}
					} else {
						sheet.autoSizeColumn(autoSizeColunm, true);
					}
					autoSizeColunm = autoSizeColunm + incrementoSize;
				}

			}
			boolean anexarHojaProcesar = false;
			if (isCombo) {
				if (anexarHojaExistente) {
					anexarHojaProcesar = true;
				} else {
					SXSSFSheet sheet = workbook.createSheet("Instruccion");
					sheet.setRandomAccessWindowSize(10);// keep 100 rows in memory, exceeding rows will be flushed to
														// disk
					SXSSFRow row = sheet.createRow(0);
					SXSSFCell cell = row.createCell(0);
					cell.setCellStyle(titleStyle);
					cell.setCellValue("Debe Seleccionar lista Existente");
					sheet.autoSizeColumn(0, true);
				}
				hojaActiva++;
			} else {
				if (anexarHojaExistente) {
					anexarHojaProcesar = true;
				}
			}
			if (anexarHojaProcesar) {
				String nombreArchivo = (String) propiedadesMap.get(NOMBRE_ARCHIVO);
				int anexarHojaPosition = (Integer) propiedadesMap.get(ANEXAR_HOJA_POSITION);
				File rutaArchivo = new File(ConstanteConfigUtil.RUTA_GENERAL_TEMPLANTE + nombreArchivo);
				SXSSFWorkbook sXSSFWorkbookAnexar = ExcelUtil.leerExcelsXlsx(rutaArchivo);
				SXSSFSheet sheetAnexar = sXSSFWorkbookAnexar.getSheetAt(anexarHojaPosition - 1);
				if (sheetAnexar != null) {
					SXSSFSheet sheet = workbook.createSheet(sheetAnexar.getSheetName());
					sheet.setRandomAccessWindowSize(10);// keep 100 rows in memory, exceeding rows will be flushed to
														// disk
					TransferUtilExcel.copySheetsXLSX(sheet, sheetAnexar);
				}
			}
			workbook.setActiveSheet(hojaActiva - 1);
			FileOutputStream out = new FileOutputStream(RUTA_RECURSOS_BYTE_BUFFER + "" + archivoName + XLSX);
			workbook.write(out);
			workbook.dispose();
			out.close();
			ExcelUtil.defaultLocaleProcess();
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			resultado = null;
		}
		return resultado;
	}

	// Inicio BUIDSOFT 02 Requerimiento reporte detalle produccion excel
	public static void generarExcelXLSXMap(List<String> listaHeader, List<ExcelHederTitleVO> listaHeaderCabecera,
			List<Map<String, Object>> listaDataMap, String archivoName, String titulo,
			Map<String, String> propiedadesMap, SXSSFWorkbook workbook, List<ExcelHederTitleVO> paginaParametroList) {
		Map<String, Object> propiedadesFinalMap = new HashMap<>();
		propiedadesFinalMap.putAll(propiedadesMap);
		generarExcelXLSXPerMap(listaHeader, listaHeaderCabecera, listaDataMap, archivoName, titulo, propiedadesFinalMap,
				workbook, paginaParametroList);
	}

	// Fin BUIDSOFT 02 Requerimiento reporte detalle produccion excel
	// Inicio BUIDSOFT 02 Requerimiento reporte detalle produccion excel
	public static void generarExcelXLSXPerMap(List<String> listaHeader, List<ExcelHederTitleVO> listaHeaderCabecera,
			List<Map<String, Object>> listaDataMap, String archivoName, String titulo,
			Map<String, Object> propiedadesMap, SXSSFWorkbook workbookx, List<ExcelHederTitleVO> paginaParametroList) {
		// Fin BUIDSOFT 02 Requerimiento reporte detalle produccion excel
		boolean isFreezePane = propiedadesMap.containsKey(IS_FREEZE_PANE);
		// Inicio BuildSoft 01/10/2019 Reporte Siniestro Taller
		boolean isHeaderCabeceraUnionTitle = propiedadesMap.containsKey("isHeaderCabeceraUnionTitle");
		// Fin BuildSoft 01/10/2019 Reporte Siniestro Taller
		try {
			if (propiedadesMap.containsKey(WRITE_EXCEL)) {
				File archivoXLS = new File(RUTA_RECURSOS_BYTE_BUFFER);
				boolean isExisteArchivo = archivoXLS.exists();
				if (!isExisteArchivo || !archivoXLS.isFile())
					archivoXLS.mkdirs();
			}
			SXSSFWorkbook workbook = workbookx;
			synchronized (workbook) {
				workbook.setCompressTempFiles(true); // temp files will be gzipped
				boolean calcularWitchDemanda = Boolean.valueOf(propiedadesMap.containsKey(CALCULAR_WITCH_DEMANDA));
				Map<Integer, Integer> columnWidtMaxMap = new HashMap<>();
				int cantidadData = listaDataMap.size();
				int cantidadHojas = 1;
				if (cantidadData > MAXIMO_RANGE_EXCEL_XLSX) {
					BigDecimal bCantidadData = new BigDecimal(cantidadData);
					BigDecimal maxRange = new BigDecimal(MAXIMO_RANGE_EXCEL_XLSX);
					BigDecimal bCantidadHojas = bCantidadData.divide(maxRange, 2, BigDecimal.ROUND_UP);
					bCantidadHojas = bCantidadHojas.setScale(0, BigDecimal.ROUND_UP);
					cantidadHojas = bCantidadHojas.intValue();
				}
				Map<String, CellStyle> cellStyleMap = new HashMap<>();
				CellStyle cellDateStyle = generarStyleDateDMY(workbook);
				CellStyle titleStyle = generarStyleTitle(workbook);
				CellStyle numberDecimalStyle = generarStyleNumberDecimal(workbook);
				// Inicio BuildSoft 02/10/2019 reporte siniestro taller
				CellStyle numberDecimalStylePer = generarStyleNumberDecimalSytle(workbook);
				CellStyle numberStyle = generarStyleNumberStyle(workbook);

				// Fin BuildSoft 02/10/2019 reporte siniestro taller
				// Inicio BUIDSOFT 02 Requerimiento reporte detalle produccion excel
				Map<String, String> overrideHeaderMap = new HashMap<String, String>();
				if (propiedadesMap.containsKey("overrideHeaderMap")) {
					overrideHeaderMap = (Map<String, String>) propiedadesMap.get("overrideHeaderMap");
				}
				// Fin BUIDSOFT 02 Requerimiento reporte detalle produccion excel
				if (propiedadesMap.containsKey("escribirHojaParametro")) {
					String escribirHojaParametro = (String) propiedadesMap.get("escribirHojaParametro");
					String hojaParametroTitulo = (String) propiedadesMap.get("hojaParametroTitulo");
					if ("true".equals(escribirHojaParametro)) {
						SXSSFSheet sheet = workbook.createSheet(hojaParametroTitulo); // CREA UNA HOJA
						for (var excelHeaderTitle : paginaParametroList) {
							int posicionRowVar = excelHeaderTitle.getPosicionRow();
							int posicionCeldaVar = excelHeaderTitle.getPosicionCelda();
							if (posicionRowVar > 0) {
								posicionRowVar = posicionRowVar - 1;
							}
							if (posicionCeldaVar > 0) {
								posicionCeldaVar = posicionCeldaVar - 1;
							}
							SXSSFRow filaTitle = sheet.getRow(posicionRowVar);
							if (filaTitle == null) {
								filaTitle = sheet.createRow(posicionRowVar);
							}
							String tituloFinalPer = excelHeaderTitle.getNameHeader();
							SXSSFCell heraderTitleCell = null;
							SXSSFCell valorCell = null;
							if (posicionCeldaVar > 0) {
								heraderTitleCell = filaTitle.createCell(posicionCeldaVar);
								int posicionValue = posicionCeldaVar + 1;
								valorCell = filaTitle.createCell(posicionValue);
							} else {
								heraderTitleCell = filaTitle.createCell(0);
								valorCell = filaTitle.createCell(1);
							}
							heraderTitleCell.setCellValue(tituloFinalPer);
							if (valorCell != null) {
								valorCell.setCellValue(excelHeaderTitle.getValor());
							}
							CellStyle titleStyleVar = generarStyleTitle(workbook,
									excelHeaderTitle.getFontHeightInPoints());
							heraderTitleCell.setCellStyle(titleStyleVar);
							heraderTitleCell.getCellStyle().setAlignment(excelHeaderTitle.getAling()); // por defecto se
																										// alinea a la
																										// izquierda
							if (valorCell != null) {
								CellStyle titleStyleData = generarStyleData(workbook);
								valorCell.setCellStyle(titleStyleData);
								valorCell.getCellStyle().setAlignment(excelHeaderTitle.getAling());
							}
							if (excelHeaderTitle.getVerticalAlignment() != null) {
								heraderTitleCell.getCellStyle()
										.setVerticalAlignment(excelHeaderTitle.getVerticalAlignment()); // si se define
																										// otra
																										// alienacion
								if (valorCell != null) {
									valorCell.getCellStyle()
											.setVerticalAlignment(excelHeaderTitle.getVerticalAlignment());
								}
							}
							// autosizecolumna
							sheet.autoSizeColumn(heraderTitleCell.getColumnIndex());
							if (valorCell != null) {
								sheet.autoSizeColumn(valorCell.getColumnIndex());
							}
							excelHeaderTitle.setPosicionRow(posicionRowVar);
							excelHeaderTitle.setPosicionCelda(posicionCeldaVar);
						}

						for (var excelHederTitleVO : paginaParametroList) {
							try {
								CellRangeAddress range = null;
								if (excelHederTitleVO.getCantidadAgrupar() > 0
										&& excelHederTitleVO.getCantidadAgruparHorizontal() == 0) {
									range = new CellRangeAddress(excelHederTitleVO.getPosicionRow().intValue(),
											excelHederTitleVO.getPosicionRow().intValue(),
											excelHederTitleVO.getPosicionCelda().intValue(),
											((excelHederTitleVO.getPosicionCelda().intValue()) - 1)
													+ excelHederTitleVO.getCantidadAgrupar().intValue());
									sheet.addMergedRegion(range);
									generarMergeRegionBorder(range, sheet);
								}
								if (excelHederTitleVO.getCantidadAgruparHorizontal() > 0
										&& excelHederTitleVO.getCantidadAgrupar() == 0) {
									range = new CellRangeAddress(excelHederTitleVO.getPosicionRow().intValue(),
											((excelHederTitleVO.getPosicionRow().intValue() - 1)
													+ excelHederTitleVO.getCantidadAgruparHorizontal().intValue()),
											excelHederTitleVO.getPosicionCelda().intValue(),
											excelHederTitleVO.getPosicionCelda().intValue());
									sheet.addMergedRegion(new CellRangeAddress(
											excelHederTitleVO.getPosicionRow().intValue(),
											((excelHederTitleVO.getPosicionRow().intValue() - 1)
													+ excelHederTitleVO.getCantidadAgruparHorizontal().intValue()),
											excelHederTitleVO.getPosicionCelda().intValue(),
											excelHederTitleVO.getPosicionCelda().intValue()));
									generarMergeRegionBorder(range, sheet);
								}
								if (excelHederTitleVO.getCantidadAgruparHorizontal() > 0
										&& excelHederTitleVO.getCantidadAgrupar() > 0) {
									range = new CellRangeAddress(excelHederTitleVO.getPosicionRow().intValue(),
											((excelHederTitleVO.getPosicionRow().intValue() - 1)
													+ excelHederTitleVO.getCantidadAgruparHorizontal().intValue()),
											excelHederTitleVO.getPosicionCelda().intValue(),
											((excelHederTitleVO.getPosicionCelda().intValue()) - 1)
													+ excelHederTitleVO.getCantidadAgrupar().intValue());
									sheet.addMergedRegion(range);
									generarMergeRegionBorder(range, sheet);
								}
							} catch (Exception e) {
								log.error("generarExcelXLSXPerMap", e);
							}
						}
					}
				}

				log.info("generarExcelObjectMapBigMemory.cantidadHojas --> " + cantidadHojas);
				for (int cantidadDataPaginadorHoja = 1; cantidadDataPaginadorHoja <= cantidadHojas; cantidadDataPaginadorHoja++) {
					String tituloFinal = titulo;
					if (propiedadesMap.containsKey(HOJA_NAME)) {
						// Inicio BUIDSOFT 02 Requerimiento reporte detalle produccion excel
						tituloFinal = (String) propiedadesMap.get(HOJA_NAME);
						// Fin BUIDSOFT 02 Requerimiento reporte detalle produccion excel
					}
					if (cantidadDataPaginadorHoja > 1) {
						tituloFinal = cantidadDataPaginadorHoja + tituloFinal;
					}
					SXSSFSheet sheet = workbook.createSheet(tituloFinal);
					int posicionRow = 0; // aca coloco el numero de row donde me he quedado
					int incrementroRow = 1;

					if (propiedadesMap.containsKey(PRINT_TITLE_VIEW)) {
						SXSSFRow filaTitle = sheet.createRow(posicionRow);
						SXSSFCell heraderTitleCell = filaTitle.createCell(0);
						heraderTitleCell.setCellValue(titulo);
						heraderTitleCell.setCellStyle(titleStyle);
						if (listaHeader.size() > 1) {
							sheet.addMergedRegion(
									new CellRangeAddress(posicionRow, posicionRow, 0, listaHeader.size() - 1));
						}
						posicionRow = posicionRow + incrementroRow;
					}

					// creando cabecera del datos
					// Inicio BUIDSOFT 02 Requerimiento reporte detalle produccion excel
					if (propiedadesMap.containsKey(ROW_INICIO)) {
						posicionRow = Integer.parseInt(propiedadesMap.get(ROW_INICIO).toString()) - 1;
					}
					// Fin BUIDSOFT 02 Requerimiento reporte detalle produccion excel
					SXSSFRow fila = sheet.createRow(posicionRow);
					int posicionCellCabecera = 0;
					int incremetoCellCabecera = 1;
					int maxPosicionRow = 0;
					if (listaHeaderCabecera != null && !listaHeaderCabecera.isEmpty()) {
						for (var excelHeaderTitle : listaHeaderCabecera) {
							int posicionRowVar = excelHeaderTitle.getPosicionRow();
							int posicionCeldaVar = excelHeaderTitle.getPosicionCelda();
							if (posicionRowVar > 0) {
								posicionRowVar = posicionRowVar - 1;
							}
							if (posicionCeldaVar > 0) {
								posicionCeldaVar = posicionCeldaVar - 1;
							}
							if (posicionRowVar > maxPosicionRow) {
								maxPosicionRow = posicionRowVar;
							}
							SXSSFRow filaTitle = sheet.getRow(posicionRowVar);
							if (filaTitle == null) {
								filaTitle = sheet.createRow(posicionRowVar);
							}
							String tituloFinalPer = excelHeaderTitle.getNameHeader();
							SXSSFCell heraderTitleCell = null;
							if (posicionCeldaVar > 0) {
								heraderTitleCell = filaTitle.createCell(posicionCeldaVar);
							} else {
								heraderTitleCell = filaTitle.createCell(0);
							}
							heraderTitleCell.setCellValue(tituloFinalPer);

							CellStyle titleStyleVar = generarStyleTitle(workbook,
									excelHeaderTitle.getFontHeightInPoints());
							heraderTitleCell.setCellStyle(titleStyleVar);
							heraderTitleCell.getCellStyle().setAlignment(excelHeaderTitle.getAling()); // por defecto se
																										// alinea a la
																										// izquierda

							if (excelHeaderTitle.getVerticalAlignment() != null) {
								heraderTitleCell.getCellStyle()
										.setVerticalAlignment(excelHeaderTitle.getVerticalAlignment()); // si se define
																										// otra
																										// alienacion
							}
							excelHeaderTitle.setPosicionRow(posicionRowVar);
							excelHeaderTitle.setPosicionCelda(posicionCeldaVar);
							if (excelHeaderTitle.getColumnIndex() > -1 && excelHeaderTitle.getWidth() > -1) {
								columnWidtMaxMap.put(excelHeaderTitle.getColumnIndex(), excelHeaderTitle.getWidth());
							}
							// Inicio BuildSoft Reporte Comision
							if (propiedadesMap.containsKey(WRAP_TEXT)) {
								heraderTitleCell.getCellStyle().setWrapText(true);
							}
							// Fin BuildSoft Reporte Comision
						}
					} else {
						// Inicio BuildSoft 01/10/2019 Reporte Siniestro Taller
						isHeaderCabeceraUnionTitle = true;
					}
					if (isHeaderCabeceraUnionTitle) {
						if (propiedadesMap.containsKey("isHeaderCabeceraUnionTitle")
								&& propiedadesMap.containsKey(ROW_INICIO)) {
							maxPosicionRow = 0;
						}
						int columnIndex = 0;
						for (var cellHeader : listaHeader) {
							SXSSFCell heraderCell = fila.createCell(posicionCellCabecera);
							heraderCell.setCellValue(cellHeader);
							// Inicio BUIDSOFT 02 Requerimiento reporte detalle produccion excel
							if (overrideHeaderMap.containsKey(cellHeader)) {
								heraderCell.setCellValue(overrideHeaderMap.get(cellHeader));
							}
							// Fin BUIDSOFT 02 Requerimiento reporte detalle produccion excel
							heraderCell.setCellStyle(titleStyle);
							// Inicio BuildSoft Reporte Comision
							if (propiedadesMap.containsKey(WRAP_TEXT)) {
								heraderCell.getCellStyle().setWrapText(true);
							}
							// Fin BuildSoft Reporte Comision
							posicionCellCabecera = posicionCellCabecera + incremetoCellCabecera;
							// Inicio BuildSoft Mant OA
							if (calcularWitchDemanda) {
								int widtMaxActual = ObjectUtil.objectToString(heraderCell.getStringCellValue())
										.length();
								double porcentaje = 0.20;
								if (!columnWidtMaxMap.containsKey(columnIndex)) {
									widtMaxActual = obtenerWidt(widtMaxActual, porcentaje);
									columnWidtMaxMap.put(columnIndex, widtMaxActual);
								}
							}
							columnIndex++;
							// Fin BuildSoft Mant OA
						}
					}
					// Fin BuildSoft 01/10/2019 Reporte Siniestro Taller
					posicionRow = maxPosicionRow + posicionRow + incrementroRow;

					if (listaHeaderCabecera != null && !listaHeaderCabecera.isEmpty()) {
						for (var excelHederTitleVO : listaHeaderCabecera) {
							try {
								// int firstRow, int lastRow, int firstCol, int lastCol
								CellRangeAddress range = null;
								if (excelHederTitleVO.getCantidadAgrupar() > 0
										&& excelHederTitleVO.getCantidadAgruparHorizontal() == 0) {
									range = new CellRangeAddress(excelHederTitleVO.getPosicionRow().intValue(),
											excelHederTitleVO.getPosicionRow().intValue(),
											excelHederTitleVO.getPosicionCelda().intValue(),
											((excelHederTitleVO.getPosicionCelda().intValue()) - 1)
													+ excelHederTitleVO.getCantidadAgrupar().intValue());
									sheet.addMergedRegion(range);
									generarMergeRegionBorder(range, sheet);
								}
								if (excelHederTitleVO.getCantidadAgruparHorizontal() > 0
										&& excelHederTitleVO.getCantidadAgrupar() == 0) {
									range = new CellRangeAddress(excelHederTitleVO.getPosicionRow().intValue(),
											((excelHederTitleVO.getPosicionRow().intValue() - 1)
													+ excelHederTitleVO.getCantidadAgruparHorizontal().intValue()),
											excelHederTitleVO.getPosicionCelda().intValue(),
											excelHederTitleVO.getPosicionCelda().intValue());
									sheet.addMergedRegion(new CellRangeAddress(
											excelHederTitleVO.getPosicionRow().intValue(),
											((excelHederTitleVO.getPosicionRow().intValue() - 1)
													+ excelHederTitleVO.getCantidadAgruparHorizontal().intValue()),
											excelHederTitleVO.getPosicionCelda().intValue(),
											excelHederTitleVO.getPosicionCelda().intValue()));
									generarMergeRegionBorder(range, sheet);
								}
								if (excelHederTitleVO.getCantidadAgruparHorizontal() > 0
										&& excelHederTitleVO.getCantidadAgrupar() > 0) {
									range = new CellRangeAddress(excelHederTitleVO.getPosicionRow().intValue(),
											((excelHederTitleVO.getPosicionRow().intValue() - 1)
													+ excelHederTitleVO.getCantidadAgruparHorizontal().intValue()),
											excelHederTitleVO.getPosicionCelda().intValue(),
											((excelHederTitleVO.getPosicionCelda().intValue()) - 1)
													+ excelHederTitleVO.getCantidadAgrupar().intValue());
									sheet.addMergedRegion(range);
									generarMergeRegionBorder(range, sheet);
								}
							} catch (Exception e) {
								log.error("generarExcelXLSXPerMap", e);
							}
						}
					}

					int primeraFila = posicionRow;
					int i = 0;
					int fromIndex = fromIndexXlsx(cantidadDataPaginadorHoja);
					int toIndex = toIndexXlsx(cantidadData, cantidadDataPaginadorHoja);
					// Inicio Build Soft Mant OA
					if (isFreezePane) {
						int colSplit = 0;
						int rowSplit = posicionRow;
						if (!StringUtil.isNullOrEmptyNumeriCero(propiedadesMap.get("isFreezePaneColSplit"))) {
							colSplit = ObjectUtil.objectToInteger(propiedadesMap.get("isFreezePaneColSplit"));
						}
						if (!StringUtil.isNullOrEmptyNumeriCero(propiedadesMap.get("isFreezePaneRowSplit"))) {
							rowSplit = ObjectUtil.objectToInteger(propiedadesMap.get("isFreezePaneRowSplit"));
						}

						sheet.createFreezePane(colSplit, rowSplit);
					}
					CellStyle cswrapText = workbook.createCellStyle();
					cswrapText.setWrapText(true);
					// Fin Build Soft Mant OA
					for (var dataMap : listaDataMap.subList(fromIndex, toIndex)) {
						SXSSFRow filaDet = sheet.createRow(i + primeraFila);
						posicionCellCabecera = 0;
						incremetoCellCabecera = 1;
						int columnIndex = 0;
						for (var headerKey : listaHeader) {
							Object value = null;
							if (propiedadesMap.containsKey(headerKey)) {
								value = dataMap.get(headerKey);
								if (StringUtil.isNullOrEmpty(value)) {
									value = 0;
								}
							} else {
								value = dataMap.get(headerKey);
							}
							if (esFechaData(value)) {
								SXSSFCell cellDetalle = filaDet.createCell(posicionCellCabecera);
								cellDetalle.setCellValue((LocalDateTime) value);
								if (propiedadesMap != null && propiedadesMap.containsKey(headerKey + FORMAT)) {
									if (!cellStyleMap.containsKey(headerKey)) {
										DataFormat format = workbook.createDataFormat();
										CellStyle cellDateStyleFormat = workbook.createCellStyle();
										cellDateStyleFormat.setDataFormat(
												format.getFormat(propiedadesMap.get(headerKey + FORMAT) + ""));

										cellDetalle.setCellStyle(cellDateStyleFormat);
										cellStyleMap.put(headerKey, cellDateStyleFormat);
									} else {
										cellDetalle.setCellStyle(cellStyleMap.get(headerKey));
									}

								} else {
									cellDetalle.setCellStyle(cellDateStyle);
								}
								posicionCellCabecera = posicionCellCabecera + incremetoCellCabecera;
							} else {
								// Inicio BuildSoft 01/10/2019 reporte taller
								if (propiedadesMap != null && propiedadesMap.containsKey(headerKey + NUMERIC)) {
									SXSSFCell cellDetalle = filaDet.createCell(posicionCellCabecera);
									if (!StringUtil.isNullOrEmptyNumeriCero(value)) {
										cellDetalle.setCellValue(Double.valueOf(value.toString()));
									} else {
										cellDetalle.setCellValue(value == null ? "" : value.toString());
									}
									posicionCellCabecera = posicionCellCabecera + incremetoCellCabecera;
								} else {
									if (propiedadesMap != null && propiedadesMap.containsKey(headerKey + "Decimal")) {
										SXSSFCell cellDetalle = filaDet.createCell(posicionCellCabecera);
										if (!StringUtil.isNullOrEmptyNumeriCero(value)) {
											cellDetalle.setCellValue(Double.valueOf(value.toString()));
										} else {
											cellDetalle.setCellValue(Double.valueOf("0.00")); // se rellena con cero
										}
										cellDetalle.setCellStyle(numberDecimalStyle);
										posicionCellCabecera = posicionCellCabecera + incremetoCellCabecera;
									} else if (propiedadesMap != null
											&& propiedadesMap.containsKey(headerKey + "DecimalSytle")) {
										SXSSFCell cellDetalle = filaDet.createCell(posicionCellCabecera);
										if (!StringUtil.isNullOrEmptyNumeriCero(value)) {
											cellDetalle.setCellValue(Double.valueOf(value.toString()));
										} else {
											cellDetalle.setCellValue(Double.valueOf("0.00")); // se rellena con cero
										}
										cellDetalle.setCellStyle(numberDecimalStylePer);
										posicionCellCabecera = posicionCellCabecera + incremetoCellCabecera;
									} else if (propiedadesMap != null
											&& propiedadesMap.containsKey(headerKey + "NumericStyle")) {
										SXSSFCell cellDetalle = filaDet.createCell(posicionCellCabecera);
										if (!StringUtil.isNullOrEmptyNumeriCero(value)) {
											cellDetalle.setCellValue(Double.valueOf(value.toString()));
										} else {
											cellDetalle.setCellValue(Double.valueOf("0")); // se rellena con cero
										}
										cellDetalle.setCellStyle(numberStyle);
										posicionCellCabecera = posicionCellCabecera + incremetoCellCabecera;
									} else {
										SXSSFCell cellDetalle = filaDet.createCell(posicionCellCabecera);
										if (esNumericoData(value)) {
											cellDetalle.setCellValue(Double.valueOf(value.toString()));
										} else {
											cellDetalle.setCellValue(value == null ? "" : value.toString());
										}
										// Inicio BuildSoft
										if (propiedadesMap.containsKey(WRAP_TEXT + headerKey)) {
											cellDetalle.setCellStyle(cswrapText);
										}
										// Fin BuildSoft
										posicionCellCabecera = posicionCellCabecera + incremetoCellCabecera;
									}
								}
							}
							// Fin BuildSoft 01/10/2019 reporte taller
							if (calcularWitchDemanda && !propiedadesMap.containsKey("witch" + headerKey)) {
								int widtMaxActual = ObjectUtil.objectToString(headerKey).length();
								double porcentaje = 0.20;
								if (!columnWidtMaxMap.containsKey(columnIndex)) {
									widtMaxActual = obtenerWidt(widtMaxActual, porcentaje);
									columnWidtMaxMap.put(columnIndex, widtMaxActual);
								}
								int widtMax = columnWidtMaxMap.get(columnIndex);
								widtMaxActual = ObjectUtil.objectToString(value).length();
								widtMaxActual = obtenerWidt(widtMaxActual, porcentaje);
								if (widtMax < widtMaxActual) {
									columnWidtMaxMap.put(columnIndex, widtMaxActual);
								}
							} else {
								int widtMaxActual = ObjectUtil.objectToInteger(propiedadesMap.get("witch" + headerKey));
								double porcentaje = 0.20;
								widtMaxActual = obtenerWidt(widtMaxActual, porcentaje);
								columnWidtMaxMap.put(columnIndex, widtMaxActual);
							}
							columnIndex++;
						}
						i++;
					}

					int autoSizeColunm = 0;// 2
					int incrementoSize = 1;
					for (int ih = 0; ih < listaHeader.size(); ih++) {
						if (calcularWitchDemanda) {
							try {
								int width = columnWidtMaxMap.get(autoSizeColunm);
								width *= 256;
								int maxColumnWidth = 255 * 256; // The maximum column width for an individual cell is
																// 255 characters
								if (width > maxColumnWidth) {
									width = maxColumnWidth;
								}
								sheet.setColumnWidth(autoSizeColunm, width);
							} catch (Exception e) {
								// log.error("ERROR autoSizeColunm -->" +
								// autoSizeColunm);
							}
						} else {
							sheet.autoSizeColumn(autoSizeColunm, true);
						}
						autoSizeColunm = autoSizeColunm + incrementoSize;
					}
				}
				if (propiedadesMap.containsKey(WRITE_EXCEL)) {
					FileOutputStream out = new FileOutputStream(RUTA_RECURSOS_BYTE_BUFFER + "" + archivoName + XLSX);
					workbook.write(out);
					workbook.dispose();
					out.close();
					ExcelUtil.defaultLocaleProcess();
				}
			}
		} catch (Exception ex) {
			log.error("generarExcelXLSXPerMap", ex);
		}
	}

	public synchronized static void generarExcelXLSX(List<ExcelHederDataVO> listaHeaderData, List<?> listaData,
			String archivoName, String titulo, Map<String, Object> propiedadesMap, SXSSFWorkbook workbook,
			List<ExcelHederTitleVO> paginaParametroList) {
		try {
			if (propiedadesMap.containsKey(WRITE_EXCEL)) {
				File archivoXLS = new File(RUTA_RECURSOS_BYTE_BUFFER);
				if (!archivoXLS.isFile()) {
					archivoXLS.mkdirs();
				}
			}
			workbook.setCompressTempFiles(true); // temp files will be gzipped
			boolean calcularWitchDemanda = Boolean.valueOf(propiedadesMap.containsKey(CALCULAR_WITCH_DEMANDA));
			Map<Integer, Integer> columnWidtMaxMap = new HashMap<>();
			int cantidadData = listaData.size();
			int cantidadHojas = 1;
			if (cantidadData > MAXIMO_RANGE_EXCEL_XLSX) {
				BigDecimal bCantidadData = new BigDecimal(cantidadData);
				BigDecimal maxRange = new BigDecimal(MAXIMO_RANGE_EXCEL_XLSX);
				BigDecimal bCantidadHojas = bCantidadData.divide(maxRange, 2, BigDecimal.ROUND_UP);
				bCantidadHojas = bCantidadHojas.setScale(0, BigDecimal.ROUND_UP);
				cantidadHojas = bCantidadHojas.intValue();
			}
			Map<String, CellStyle> cellStyleMap = new HashMap<>();
			CellStyle cellDateStyle = generarStyleDateDMY(workbook);
			CellStyle titleStyle = generarStyleTitle(workbook);
			CellStyle numberDecimalStyle = generarStyleNumberDecimal(workbook);
			CellStyle numberStyle = generarStyleNumber(workbook);

			if (propiedadesMap.containsKey("escribirHojaParametro")) {
				String escribirHojaParametro = (String) propiedadesMap.get("escribirHojaParametro");
				String hojaParametroTitulo = (String) propiedadesMap.get("hojaParametroTitulo");
				if ("true".equals(escribirHojaParametro)) {
					SXSSFSheet sheet = workbook.createSheet(hojaParametroTitulo); // CREA UNA HOJA
					for (var excelHeaderTitle : paginaParametroList) {
						int posicionRowVar = excelHeaderTitle.getPosicionRow();
						int posicionCeldaVar = excelHeaderTitle.getPosicionCelda();
						if (posicionRowVar > 0) {
							posicionRowVar = posicionRowVar - 1;
						}
						if (posicionCeldaVar > 0) {
							posicionCeldaVar = posicionCeldaVar - 1;
						}
						SXSSFRow filaTitle = sheet.getRow(posicionRowVar);
						if (filaTitle == null) {
							filaTitle = sheet.createRow(posicionRowVar);
						}
						String tituloFinalPer = excelHeaderTitle.getNameHeader();
						SXSSFCell heraderTitleCell = null;
						SXSSFCell valorCell = null;
						if (posicionCeldaVar > 0) {
							heraderTitleCell = filaTitle.createCell(posicionCeldaVar);
							int posicionValue = posicionCeldaVar + 1;
							valorCell = filaTitle.createCell(posicionValue);
						} else {
							heraderTitleCell = filaTitle.createCell(0);
							valorCell = filaTitle.createCell(1);
						}
						heraderTitleCell.setCellValue(tituloFinalPer);
						if (valorCell != null) {
							valorCell.setCellValue(excelHeaderTitle.getValor());
						}
						CellStyle titleStyleVar = generarStyleTitle(workbook, excelHeaderTitle.getFontHeightInPoints());
						heraderTitleCell.setCellStyle(titleStyleVar);
						heraderTitleCell.getCellStyle().setAlignment(excelHeaderTitle.getAling()); // por defecto se
																									// alinea a la
																									// izquierda
						if (valorCell != null) {
							CellStyle titleStyleData = generarStyleData(workbook);
							valorCell.setCellStyle(titleStyleData);
							valorCell.getCellStyle().setAlignment(excelHeaderTitle.getAling());
						}
						if (excelHeaderTitle.getVerticalAlignment() != null) {
							heraderTitleCell.getCellStyle()
									.setVerticalAlignment(excelHeaderTitle.getVerticalAlignment()); // si se define otra
																									// alienacion
							if (valorCell != null) {
								valorCell.getCellStyle().setVerticalAlignment(excelHeaderTitle.getVerticalAlignment());
							}
						}
						// autosizecolumna
						sheet.autoSizeColumn(heraderTitleCell.getColumnIndex());
						if (valorCell != null) {
							sheet.autoSizeColumn(valorCell.getColumnIndex());
						}
						excelHeaderTitle.setPosicionRow(posicionRowVar);
						excelHeaderTitle.setPosicionCelda(posicionCeldaVar);
					}

					for (var excelHederTitleVO : paginaParametroList) {
						try {
							CellRangeAddress range = null;
							if (excelHederTitleVO.getCantidadAgrupar() > 0
									&& excelHederTitleVO.getCantidadAgruparHorizontal() == 0) {
								range = new CellRangeAddress(excelHederTitleVO.getPosicionRow().intValue(),
										excelHederTitleVO.getPosicionRow().intValue(),
										excelHederTitleVO.getPosicionCelda().intValue(),
										((excelHederTitleVO.getPosicionCelda().intValue()) - 1)
												+ excelHederTitleVO.getCantidadAgrupar().intValue());
								sheet.addMergedRegion(range);
								generarMergeRegionBorder(range, sheet);
							}
							if (excelHederTitleVO.getCantidadAgruparHorizontal() > 0
									&& excelHederTitleVO.getCantidadAgrupar() == 0) {
								range = new CellRangeAddress(excelHederTitleVO.getPosicionRow().intValue(),
										((excelHederTitleVO.getPosicionRow().intValue() - 1)
												+ excelHederTitleVO.getCantidadAgruparHorizontal().intValue()),
										excelHederTitleVO.getPosicionCelda().intValue(),
										excelHederTitleVO.getPosicionCelda().intValue());
								sheet.addMergedRegion(
										new CellRangeAddress(excelHederTitleVO.getPosicionRow().intValue(),
												((excelHederTitleVO.getPosicionRow().intValue() - 1)
														+ excelHederTitleVO.getCantidadAgruparHorizontal().intValue()),
												excelHederTitleVO.getPosicionCelda().intValue(),
												excelHederTitleVO.getPosicionCelda().intValue()));
								generarMergeRegionBorder(range, sheet);
							}
							if (excelHederTitleVO.getCantidadAgruparHorizontal() > 0
									&& excelHederTitleVO.getCantidadAgrupar() > 0) {
								range = new CellRangeAddress(excelHederTitleVO.getPosicionRow().intValue(),
										((excelHederTitleVO.getPosicionRow().intValue() - 1)
												+ excelHederTitleVO.getCantidadAgruparHorizontal().intValue()),
										excelHederTitleVO.getPosicionCelda().intValue(),
										((excelHederTitleVO.getPosicionCelda().intValue()) - 1)
												+ excelHederTitleVO.getCantidadAgrupar().intValue());
								sheet.addMergedRegion(range);
								generarMergeRegionBorder(range, sheet);
							}
						} catch (Exception e) {
							log.error("generarExcelXLSX", e);
						}
					}
				}
			}

			for (int cantidadDataPaginadorHoja = 1; cantidadDataPaginadorHoja <= cantidadHojas; cantidadDataPaginadorHoja++) {
				String tituloFinal = titulo;
				if (propiedadesMap != null && propiedadesMap.containsKey(HOJA_NAME)) {
					tituloFinal = propiedadesMap.get(HOJA_NAME) + "";
				}
				if (cantidadHojas > 1) {
					tituloFinal = tituloFinal + cantidadDataPaginadorHoja;
				}
				SXSSFSheet sheet = workbook.createSheet(tituloFinal); // CREA UNA HOJA
				sheet.setRandomAccessWindowSize(100);// keep 100 rows in memory, exceeding rows will be flushed to disk
				int posicionRow = 0;
				int incrementroRow = 1;
				if (propiedadesMap != null && propiedadesMap.containsKey(PRINT_TITLE_VIEW)) {
					SXSSFRow filaTitle = sheet.createRow(posicionRow);
					SXSSFCell heraderTitleCell = filaTitle.createCell((listaHeaderData.size() / 2));
					heraderTitleCell.setCellValue(titulo);
					posicionRow = posicionRow + incrementroRow;
				}
				// creando cabecera del datos
				if (propiedadesMap != null && propiedadesMap.containsKey(ROW_INICIO)) {
					posicionRow = Integer.parseInt(propiedadesMap.get(ROW_INICIO) + "") - 1;
				}
				SXSSFRow fila = sheet.createRow(posicionRow);
				int posicionCellCabecera = 0;
				int incremetoCellCabecera = 1;
				for (var cellHeaderVO : listaHeaderData) {
					String cellHeader = cellHeaderVO.getNameHeader();
					SXSSFCell heraderCell = fila.createCell(posicionCellCabecera);
					heraderCell.setCellValue(cellHeader);
					heraderCell.setCellStyle(titleStyle);
					posicionCellCabecera = posicionCellCabecera + incremetoCellCabecera;
				}
				posicionRow = posicionRow + incrementroRow;
				// llenando la data
				int primeraFila = posicionRow;
				int i = 0;
				int fromIndex = fromIndexXlsx(cantidadDataPaginadorHoja);
				int toIndex = toIndexXlsx(cantidadData, cantidadDataPaginadorHoja);
				for (var cellData : listaData.subList(fromIndex, toIndex)) {
					SXSSFRow filaDet = sheet.createRow(i + primeraFila);
					posicionCellCabecera = 0;
					incremetoCellCabecera = 1;
					int columnIndex = 0;
					for (var cellHeaderVO : listaHeaderData) {
						String nombreColumna = cellHeaderVO.getNameAtribute();
						Object value = null;
						value = atributoValueComplejo(cellData, nombreColumna);
						if (esFecha(nombreColumna)) {
							Object valueDate = verificarFornatoFecha(nombreColumna, value);
							if (esFechaData(valueDate)) {
								SXSSFCell cellDetalle = filaDet.createCell(posicionCellCabecera);
								cellDetalle.setCellValue((LocalDateTime) valueDate);
								if (propiedadesMap != null && propiedadesMap.containsKey(nombreColumna + FORMAT)) {
									if (!cellStyleMap.containsKey(nombreColumna)) {
										DataFormat format = workbook.createDataFormat();
										CellStyle cellDateStyleFormat = workbook.createCellStyle();
										cellDateStyleFormat.setDataFormat(
												format.getFormat(propiedadesMap.get(nombreColumna + FORMAT) + ""));
										cellDetalle.setCellStyle(cellDateStyleFormat);
										cellStyleMap.put(nombreColumna, cellDateStyleFormat);
									} else {
										cellDetalle.setCellStyle(cellStyleMap.get(nombreColumna));
									}

								} else {
									cellDetalle.setCellStyle(cellDateStyle);
								}
								posicionCellCabecera = posicionCellCabecera + incremetoCellCabecera;
							} else {
								SXSSFCell cellDetalle = filaDet.createCell(posicionCellCabecera);
								cellDetalle.setCellValue(value == null ? "" : value.toString());
								posicionCellCabecera = posicionCellCabecera + incremetoCellCabecera;
							}
						} else {
							if (propiedadesMap != null && propiedadesMap.containsKey(nombreColumna + "Decimal")) {
								SXSSFCell cellDetalle = filaDet.createCell(posicionCellCabecera);
								if (!StringUtil.isNullOrEmptyNumeriCero(value)) {
									cellDetalle.setCellValue(Double.valueOf(value.toString()));
								} else {
									cellDetalle.setCellValue(Double.valueOf("0.00")); // se rellena con cero
								}
								cellDetalle.setCellStyle(numberDecimalStyle);
								posicionCellCabecera = posicionCellCabecera + incremetoCellCabecera;
							} else if (propiedadesMap != null && propiedadesMap.containsKey(nombreColumna + NUMERIC)) {
								SXSSFCell cellDetalle = filaDet.createCell(posicionCellCabecera);
								if (!StringUtil.isNullOrEmptyNumeriCero(value)) {
									cellDetalle.setCellValue(Double.valueOf(value.toString()));
								} else {
									cellDetalle.setCellValue(Double.valueOf("0")); // se rellena con cero
								}
								cellDetalle.setCellStyle(numberStyle);
								posicionCellCabecera = posicionCellCabecera + incremetoCellCabecera;
							} else {
								SXSSFCell cellDetalle = filaDet.createCell(posicionCellCabecera);
								if (esNumericoData(value)) {
									cellDetalle.setCellValue(Double.valueOf(value.toString()));
								} else {
									cellDetalle.setCellValue(value == null ? "" : value.toString());
								}
								posicionCellCabecera = posicionCellCabecera + incremetoCellCabecera;
							}
						}

						if (calcularWitchDemanda) {
							int widtMaxActual = ObjectUtil.objectToString(cellHeaderVO.getNameHeader()).length();
							double porcentaje = 0.20;
							if (!columnWidtMaxMap.containsKey(columnIndex)) {
								widtMaxActual = obtenerWidt(widtMaxActual, porcentaje);
								columnWidtMaxMap.put(columnIndex, widtMaxActual);
							}
							int widtMax = columnWidtMaxMap.get(columnIndex);
							widtMaxActual = ObjectUtil.objectToString(value).length();
							widtMaxActual = obtenerWidt(widtMaxActual, porcentaje);
							if (widtMax < widtMaxActual) {
								columnWidtMaxMap.put(columnIndex, widtMaxActual);
							}
						}
						columnIndex++;
					}
					i++;
				}
				int autoSizeColunm = 0;// 2
				int incrementoSize = 1;
				for (int ih = 0; ih < listaHeaderData.size(); ih++) {
					if (calcularWitchDemanda) {
						try {
							int width = columnWidtMaxMap.get(autoSizeColunm);
							width *= 256;
							int maxColumnWidth = 255 * 256; // The maximum column width for an individual cell is 255
															// characters
							if (width > maxColumnWidth) {
								width = maxColumnWidth;
							}
							sheet.setColumnWidth(autoSizeColunm, width);
						} catch (Exception e) {
							// log.error("ERROR autoSizeColunm -->" + autoSizeColunm);
						}
					} else {
						sheet.autoSizeColumn(autoSizeColunm, true);
					}
					autoSizeColunm = autoSizeColunm + incrementoSize;
				}
			}
			if (propiedadesMap.containsKey(WRITE_EXCEL)) {
				FileOutputStream out = new FileOutputStream(RUTA_RECURSOS_BYTE_BUFFER + "" + archivoName + XLSX);
				workbook.write(out);
				workbook.dispose();
				out.close();
				ExcelUtil.defaultLocaleProcess();
			}
		} catch (Exception ex) {
			log.error("generarExcelXLSX" + ex.getMessage(), ex);
		}
	}

	private static CellStyle generarStyleDate(CellStyle cellDateStyle, DataFormat format) {
		cellDateStyle.setDataFormat(format.getFormat(DD_MM_YYY_HH_MM_SS));
		return cellDateStyle;
	}

	private static CellStyle generarStyleTitle(SXSSFWorkbook workbook, short fontHeightInPoints) {
		Font titleFont = generarTitleFont(workbook, fontHeightInPoints, true);
		CellStyle titleStyle = workbook.createCellStyle();
		return generarStyleTitle(titleStyle, titleFont, true);
	}

	private static CellStyle generarStyleTitleData(SXSSFWorkbook workbook, short fontHeightInPoints, boolean styleMarco,
			boolean bold) {
		Font titleFont = generarTitleFont(workbook, fontHeightInPoints, bold);
		CellStyle titleStyle = workbook.createCellStyle();
		return generarStyleTitle(titleStyle, titleFont, styleMarco);
	}

	private static Font generarTitleFont(Font titleFont, short fontHeightInPoints, boolean bold) {
		titleFont.setFontName(NOMBRE_LETRA);
		titleFont.setFontHeightInPoints(fontHeightInPoints);
		titleFont.setBold(bold);
		titleFont.setColor(HSSFColorPredefined.BLACK.getIndex());
		return titleFont;
	}

	private static Font generarTitleFont(SXSSFWorkbook workbook, short fontHeightInPoints, boolean bold) {
		Font titleFont = workbook.createFont();
		return generarTitleFont(titleFont, fontHeightInPoints, bold);
	}
}
