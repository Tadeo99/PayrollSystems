package pe.buildsoft.erp.core.infra.transversal.utilitario.exel;

import java.io.Serializable;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import pe.buildsoft.erp.core.infra.transversal.utilitario.FechaUtil;


/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul> 
 * 
 * La Class DataExportExcel.
 *
 * @author BuildSoft.
 * @version 1.0 , 24/01/2018
 * @since BuildErp 1.0
 */
public class DataExportExcel implements Serializable {
		
	/** La Constante serialVersionUID. */
	private static final long serialVersionUID = 1714685155450871397L;
	
	/** La Constante DD_MM_YYY. */
	private static final String DD_MM_YYYY = FechaUtil.DATE_DMY;

	/**
	 * Generar merge region border.
	 *
	 * @param range el range
	 * @param sheet el sheet
	 * @param workbook el workbook
	 */
	public static void generarMergeRegionBorder(CellRangeAddress range, Sheet sheet) {
		RegionUtil.setBorderBottom(BorderStyle.THIN, range, sheet);
		RegionUtil.setBorderTop(BorderStyle.THIN, range, sheet);
		RegionUtil.setBorderLeft(BorderStyle.THIN, range, sheet);
		RegionUtil.setBorderRight(BorderStyle.THIN, range, sheet);
		RegionUtil.setBorderRight(BorderStyle.THIN, range, sheet);
	}
	
	/**
	 * Generar style data.
	 *
	 * @param workbook el workbook
	 * @return the cell style
	 */
	public static CellStyle generarStyleData(SXSSFWorkbook workbook) {
		CellStyle titleStyle = workbook.createCellStyle();
		return generarStyleDataNormal(titleStyle);
	}
	
	/**
	 * Generar style data normal.
	 *
	 * @param titleStyle el title style
	 * @return the cell style
	 */
	private static CellStyle generarStyleDataNormal(CellStyle titleStyle) {
		titleStyle.setBorderTop(BorderStyle.THIN);
		titleStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		titleStyle.setBorderRight(BorderStyle.THIN);
		titleStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		titleStyle.setBorderBottom(BorderStyle.THIN);
		titleStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		titleStyle.setBorderLeft(BorderStyle.THIN);
		titleStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		return titleStyle;
	}

	
	/**
	 * Generar style date dmy.
	 *
	 * @param workbook el workbook
	 * @return the cell style
	 */
	public static CellStyle generarStyleDateDMY(SXSSFWorkbook workbook) {
		DataFormat format = workbook.createDataFormat();
		CellStyle cellDateStyle = workbook.createCellStyle();
		return generarStyleDate(cellDateStyle, format);
	}
	
	/**
	 * Generar style date.
	 *
	 * @param cellDateStyle el cell date style
	 * @param format el format
	 * @return the cell style
	 */
	private static CellStyle generarStyleDate(CellStyle cellDateStyle,DataFormat format) {
		cellDateStyle.setDataFormat(format.getFormat(DD_MM_YYYY));
		return cellDateStyle;
	}
	
	/**
	 * Generar style number.
	 *
	 * @param workbook el workbook
	 * @return the cell style
	 */
	public static CellStyle generarStyleNumberDecimal(SXSSFWorkbook workbook) {
		DataFormat format = workbook.createDataFormat();
		CellStyle cellDateStyle = workbook.createCellStyle();
		return generarStyleNumberPersonalizadoDecimal(cellDateStyle, format);
	}
	//Inicio BuildSoft 02/10/2019 reporte siniestro taller
	public static CellStyle generarStyleNumberDecimalSytle(SXSSFWorkbook workbook) {
		DataFormat format = workbook.createDataFormat();
		CellStyle cellDateStyle = workbook.createCellStyle();
		return generarStyleNumberPersonalizadoDecimalSytle(cellDateStyle, format);
	}
	//Fin BuildSoft 02/10/2019 reporte siniestro taller
	/**
	 * Generar style number personalizado.
	 *
	 * @param cellNumberStyle el cell number style
	 * @param format el format
	 * @return the cell style
	 */
	private static CellStyle generarStyleNumberPersonalizadoDecimal(CellStyle cellNumberStyle, DataFormat format) {
		cellNumberStyle.setDataFormat(format.getFormat("#,##0.00"));
		return cellNumberStyle;
	}
	//Inicio BuildSoft 02/10/2019 reporte siniestro taller
	private static CellStyle generarStyleNumberPersonalizadoDecimalSytle(CellStyle cellNumberStyle, DataFormat format) {
		cellNumberStyle.setDataFormat(format.getFormat("###0.00"));
		return cellNumberStyle;
	}
	//Fin BuildSoft 02/10/2019 reporte siniestro taller
	/**
	 * Generar style number.
	 *
	 * @param workbook el workbook
	 * @return the cell style
	 */
	public static CellStyle generarStyleNumber(SXSSFWorkbook workbook) {
		DataFormat format = workbook.createDataFormat();
		CellStyle cellDateStyle = workbook.createCellStyle();
		return generarStyleNumberPersonalizado(cellDateStyle, format);
	}
	//Inicio BuildSoft 02/10/2019 reporte siniestro taller
	public static CellStyle generarStyleNumberStyle(SXSSFWorkbook workbook) {
		DataFormat format = workbook.createDataFormat();
		CellStyle cellDateStyle = workbook.createCellStyle();
		return generarStyleNumberPersonalizadoSytle(cellDateStyle, format);
	}
	//Fin BuildSoft 02/10/2019 reporte siniestro taller
	/**
	 * Generar style number personalizado.
	 *
	 * @param cellNumberStyle el cell number style
	 * @param format el format
	 * @return the cell style
	 */
	private static CellStyle generarStyleNumberPersonalizado(CellStyle cellNumberStyle, DataFormat format) {
		cellNumberStyle.setDataFormat(format.getFormat("#,##0"));
		return cellNumberStyle;
	}
	//Inicio BuildSoft 02/10/2019 reporte siniestro taller
	private static CellStyle generarStyleNumberPersonalizadoSytle(CellStyle cellNumberStyle, DataFormat format) {
		cellNumberStyle.setDataFormat(format.getFormat("###0"));
		return cellNumberStyle;
	}
	//Fin BuildSoft 02/10/2019 reporte siniestro taller
}
