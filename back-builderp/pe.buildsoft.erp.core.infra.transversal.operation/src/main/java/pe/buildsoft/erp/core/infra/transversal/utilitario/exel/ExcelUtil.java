package pe.buildsoft.erp.core.infra.transversal.utilitario.exel;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ValueDataVO;
import pe.buildsoft.erp.core.infra.transversal.utilitario.TransferDataOperUtil;

/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul> 
 * 
 * La Class ExcelUtil.
 *
 * @author BuildSoft.
 * @version 1.0 , 07/04/2015
 * @since BuildErp 1.0
 */
public class ExcelUtil {

	
	public  static <T> List<T>  toXlsDTO(byte[] datosArchivo,int hoja,int filaData,Map<String,Integer> campoMappingExcelMap,Class<T> entityClassDTO) throws IOException{
		return TransferDataOperUtil.toXlsDTO(campoMappingExcelMap, leerExcel(datosArchivo), entityClassDTO, hoja, filaData);
	}
	public  static <T> List<T>  toXlsDTO(byte[] datosArchivo,int hoja,int filaData,Map<String,Integer> campoMappingExcelMap,Map<String,String> campoMappingFormatoMap,Map<String, Object> parametroMap,Class<T> entityClassDTO) throws IOException{
		return TransferDataOperUtil.toXlsDTO(campoMappingExcelMap, leerExcel(datosArchivo), campoMappingFormatoMap,parametroMap,entityClassDTO, hoja, filaData);
	}
	public  static <T> List<T>  toExcelList(byte[] datosArchivo,int hoja,int filaData,Map<String,Integer> campoMappingExcelMap,Map<String,String> campoMappingFormatoMap,Map<String, Object> parametroMap,Class<T> entityClassDTO) throws IOException{
		try {
			return toXlsxDTO(datosArchivo, hoja, filaData, campoMappingExcelMap,campoMappingFormatoMap,parametroMap, entityClassDTO);
		} catch (Exception e) {
			return toXlsDTO(datosArchivo, hoja, filaData, campoMappingExcelMap,campoMappingFormatoMap,parametroMap,entityClassDTO);
		}
		
	}
	public  static <T> List<T>  toExcelList(byte[] datosArchivo,int hoja,int filaData,Map<String,Integer> campoMappingExcelMap,Class<T> entityClassDTO) throws IOException{
		try {
			return toXlsDTO(datosArchivo, hoja, filaData, campoMappingExcelMap, entityClassDTO);
		} catch (Exception e) {
			return toXlsxDTO(datosArchivo, hoja, filaData, campoMappingExcelMap, entityClassDTO);
		}
	}
	public  static <T> List<T>  toXlsDTO(HSSFWorkbook  hSSFWorkbook,int hoja,int filaData,Map<String,Integer> campoMappingExcelMap,Class<T> entityClassDTO){
		return TransferDataOperUtil.toXlsDTO(campoMappingExcelMap, hSSFWorkbook, entityClassDTO, hoja, filaData);
	}
	public  static <T> List<T>  toXlsxDTO(byte[] datosArchivo,int hoja,int filaData,Map<String,Integer> campoMappingExcelMap,Class<T> entityClassDTO) throws IOException{
		return TransferDataOperUtil.toXlsxDTO(campoMappingExcelMap, leerExcelXLSX(datosArchivo), entityClassDTO,hoja, filaData);
	}
	
	public  static <T> List<T>  toXlsxDTO(byte[] datosArchivo,int hoja,int filaData,Map<String,Integer> campoMappingExcelMap,Map<String,String> campoMappingFormatoMap,Map<String, Object> parametroMap, Class<T> entityClassDTO) throws IOException{
		return TransferDataOperUtil.toXlsxDTO(campoMappingExcelMap, campoMappingFormatoMap,parametroMap,leerExcelXLSX(datosArchivo), entityClassDTO,hoja, filaData);
	}

	public  static <T> List<T>  toXlsxDTO(XSSFWorkbook  xSSFWorkbook,int hoja,int filaData,Map<String,Integer> campoMappingExcelMap,Class<T> entityClassDTO){
		return TransferDataOperUtil.toXlsxDTO(campoMappingExcelMap, xSSFWorkbook, entityClassDTO,hoja, filaData);
	}
	
	public  static <T> List<T>  toXlsxDTO(XSSFWorkbook  xSSFWorkbook,int hoja,int filaData,Map<String,Integer> campoMappingExcelMap,Map<String,String> campoMappingFormatoMap,Map<String, Object> parametroMap,Class<T> entityClassDTO){
		return TransferDataOperUtil.toXlsxDTO(campoMappingExcelMap,campoMappingFormatoMap,parametroMap, xSSFWorkbook, entityClassDTO,hoja, filaData);
	}
	
	public  static List<Map<String,ValueDataVO>> toXlsMap(HSSFWorkbook  hSSFWorkbook,int hoja,int filaData,Map<String,Object> campoMappingExcelMap,Map<String,String> campoMappingExcelTypeMap,Map<String,String> campoMappingFormatoMap, Integer cantidadData,Map<String, Object> parametroMap, Map<String,Character> configuracionTramaDetalleMap){
		parametroMap.put("hoja", hoja);
		parametroMap.put("filaData", filaData);
		parametroMap.put("cantidadData", cantidadData);
		return TransferDataOperUtil.toXlsMap(campoMappingExcelMap, hSSFWorkbook,campoMappingExcelTypeMap,campoMappingFormatoMap,parametroMap,configuracionTramaDetalleMap);
	}
	
	public  static List<Map<String,ValueDataVO>> toXlsxMap(XSSFWorkbook  xSSFWorkbook,int hoja,int filaData,Map<String,Object> campoMappingExcelMap,Map<String,String> campoMappingExcelTypeMap,Map<String,String> campoMappingFormatoMap, Integer cantidadData,Map<String, Object> parametroMap, Map<String,Character> configuracionTramaDetalleMap){
		parametroMap.put("hoja", hoja);
		parametroMap.put("filaData", filaData);
		parametroMap.put("cantidadData", cantidadData);
		return TransferDataOperUtil.toXlsxMap(campoMappingExcelMap, xSSFWorkbook,campoMappingExcelTypeMap,campoMappingFormatoMap,parametroMap,configuracionTramaDetalleMap);
	}
	
	public static void defaultLocale(){
		Locale ES = new Locale("es", "PE");
		Locale.setDefault(ES);
	}
	
	public static void defaultLocaleProcess(){
		TransferDataOperUtil.defaultLocaleProcess();
	}
	
	public static  HSSFWorkbook leerExcel(File rutaArchivo) throws IOException{
		defaultLocale();
		FileInputStream fileInputStream = new FileInputStream(rutaArchivo);
		return new HSSFWorkbook(fileInputStream);
	}
	
	public static  HSSFWorkbook leerExcel(byte[] datosArchivo) throws IOException{
		defaultLocale();
		InputStream fileExcel = new ByteArrayInputStream(datosArchivo);
		/** Create a workbook desde InputStream **/
		return new HSSFWorkbook(fileExcel);
	}
	public static  XSSFWorkbook leerExcelXLSX(byte[] datosArchivo) throws IOException{
		defaultLocale();
		InputStream fileExcel = new ByteArrayInputStream(datosArchivo);
		/** Create a workbook desde InputStream **/
		return  new XSSFWorkbook(fileExcel);
	}
	
	
	
	public static  XSSFWorkbook leerExcelXlsx(File rutaArchivo) throws IOException {
		defaultLocale();
		FileInputStream fileInputStream = new FileInputStream(rutaArchivo);
		return  new XSSFWorkbook(fileInputStream);
	}
	public static  SXSSFWorkbook leerExcelsXlsx(File rutaArchivo) throws IOException{
		XSSFWorkbook xSSFWorkbook = leerExcelXlsx(rutaArchivo);
		return new SXSSFWorkbook(xSSFWorkbook);
	}
}