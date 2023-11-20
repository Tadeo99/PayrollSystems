package pe.buildsoft.erp.core.infra.transversal.utilitario.exel;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
 
/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul> 
 * 
 * La Class TransferUtilExcel.
 *
 * @author BuildSoft.
 * @version 1.0 , 02/05/2016
 * @since BuildErp 1.0
 */
public class TransferUtilExcel {
  
    /**
     * Copy sheets.
     *
     * @param newSheet el new sheet
     * @param sheet el sheet
     */
    public static void copySheets(HSSFSheet newSheet, HSSFSheet sheet){   
        copySheets(newSheet, sheet, true);   
    } 
    /**
     * Copy sheets.
     *
     * @param newSheet el new sheet
     * @param sheet el sheet
     */
    public static void copySheetsXLSX(SXSSFSheet newSheet, SXSSFSheet sheet){   
        copySheetsXLSX(newSheet, sheet, true);   
    } 
       
    /**
     * Copy sheets.
     *
     * @param newSheet el new sheet
     * @param sheet el sheet
     * @param copyStyle el copy style
     */
    public static void copySheetsXLSX(SXSSFSheet newSheet, SXSSFSheet sheet, boolean copyStyle){   
        int maxColumnNum = 0;   
        Map<Integer, XSSFCellStyle> styleMap = (copyStyle) ? new HashMap<Integer, XSSFCellStyle>() : null;   
        for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {   
        	SXSSFRow srcRow = (SXSSFRow)sheet.getRow(i);   
        	SXSSFRow destRow = (SXSSFRow) newSheet.createRow(i);   
            if (srcRow != null) {   
                copyRowXLSX(sheet, newSheet, srcRow, destRow, styleMap);   
                if (srcRow.getLastCellNum() > maxColumnNum) {   
                    maxColumnNum = srcRow.getLastCellNum();   
                }   
            }   
        }   
        for (int i = 0; i <= maxColumnNum; i++) {   
            newSheet.setColumnWidth(i, sheet.getColumnWidth(i));   
        }   
    }   
   
    /**
     * Copy sheets.
     *
     * @param newSheet el new sheet
     * @param sheet el sheet
     * @param copyStyle el copy style
     */
    public static void copySheets(HSSFSheet newSheet, HSSFSheet sheet, boolean copyStyle){   
        int maxColumnNum = 0;   
        Map<Integer, HSSFCellStyle> styleMap = (copyStyle) ? new HashMap<Integer, HSSFCellStyle>() : null;   
        for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {   
            HSSFRow srcRow = sheet.getRow(i);   
            HSSFRow destRow = newSheet.createRow(i);   
            if (srcRow != null) {   
                copyRow(sheet, newSheet, srcRow, destRow, styleMap);   
                if (srcRow.getLastCellNum() > maxColumnNum) {   
                    maxColumnNum = srcRow.getLastCellNum();   
                }   
            }   
        }   
        for (int i = 0; i <= maxColumnNum; i++) {   
            newSheet.setColumnWidth(i, sheet.getColumnWidth(i));   
        }   
    }   
    /**
     * Copy row.
     *
     * @param srcSheet el src sheet
     * @param destSheet el dest sheet
     * @param srcRow el src row
     * @param destRow el dest row
     * @param styleMap el style map
     */
    public static void copyRow(HSSFSheet srcSheet, HSSFSheet destSheet, HSSFRow srcRow, HSSFRow destRow, Map<Integer, HSSFCellStyle> styleMap) {   
        // manage a list of merged zone in order to not insert two times a merged zone
      Set<CellRangeAddressWrapper> mergedRegions = new TreeSet<CellRangeAddressWrapper>();   
        destRow.setHeight(srcRow.getHeight());   
        // reckoning delta rows
        int deltaRows = destRow.getRowNum()-srcRow.getRowNum();
        // pour chaque row
        for (int j = srcRow.getFirstCellNum(); j <= srcRow.getLastCellNum(); j++) {   
            HSSFCell oldCell = srcRow.getCell(j);   // ancienne cell
            HSSFCell newCell = destRow.getCell(j);  // new cell 
            if (oldCell != null) {   
                if (newCell == null) {   
                    newCell = destRow.createCell(j);   
                }   
                // copy chaque cell
                copyCell(oldCell, newCell, styleMap);   
                // copy les informations de fusion entre les cellules
                //System.out.println("row num: " + srcRow.getRowNum() + " , col: " + (short)oldCell.getColumnIndex());
                CellRangeAddress mergedRegion = getMergedRegion(srcSheet, srcRow.getRowNum(), (short)oldCell.getColumnIndex());   
                 
                if (mergedRegion != null) { 
                  //System.out.println("Selected merged region: " + mergedRegion.toString());
                  CellRangeAddress newMergedRegion = new CellRangeAddress(mergedRegion.getFirstRow()+deltaRows, mergedRegion.getLastRow()+deltaRows, mergedRegion.getFirstColumn(),  mergedRegion.getLastColumn());
                    //System.out.println("New merged region: " + newMergedRegion.toString());
                    CellRangeAddressWrapper wrapper = new CellRangeAddressWrapper(newMergedRegion);
                    if (isNewMergedRegion(wrapper, mergedRegions)) {
                        mergedRegions.add(wrapper);
                        destSheet.addMergedRegion(wrapper.range);   
                    }   
                }   
            }   
        }              
    }   
        
    
    public static void copyRowXLSX(SXSSFSheet srcSheet, SXSSFSheet destSheet, SXSSFRow srcRow, SXSSFRow destRow, Map<Integer, XSSFCellStyle> styleMap) {   
        // manage a list of merged zone in order to not insert two times a merged zone
      Set<CellRangeAddressWrapper> mergedRegions = new TreeSet<CellRangeAddressWrapper>();   
        destRow.setHeight(srcRow.getHeight());   
        // reckoning delta rows
        int deltaRows = destRow.getRowNum()-srcRow.getRowNum();
        // pour chaque row
        for (int j = srcRow.getFirstCellNum(); j <= srcRow.getLastCellNum(); j++) {   
        	SXSSFCell oldCell = (SXSSFCell)srcRow.getCell(j);   // ancienne cell
        	SXSSFCell newCell = (SXSSFCell)destRow.getCell(j);  // new cell 
            if (oldCell != null) {   
                if (newCell == null) {   
                    newCell = (SXSSFCell) destRow.createCell(j);   
                }   
                // copy chaque cell
                copyCell(oldCell, newCell, styleMap);   
                // copy les informations de fusion entre les cellules
                //System.out.println("row num: " + srcRow.getRowNum() + " , col: " + (short)oldCell.getColumnIndex());
                CellRangeAddress mergedRegion = getMergedRegionXLSX(srcSheet, srcRow.getRowNum(), (short)oldCell.getColumnIndex());   
                 
                if (mergedRegion != null) { 
                  //System.out.println("Selected merged region: " + mergedRegion.toString());
                  CellRangeAddress newMergedRegion = new CellRangeAddress(mergedRegion.getFirstRow()+deltaRows, mergedRegion.getLastRow()+deltaRows, mergedRegion.getFirstColumn(),  mergedRegion.getLastColumn());
                    //System.out.println("New merged region: " + newMergedRegion.toString());
                    CellRangeAddressWrapper wrapper = new CellRangeAddressWrapper(newMergedRegion);
                    if (isNewMergedRegion(wrapper, mergedRegions)) {
                        mergedRegions.add(wrapper);
                        destSheet.addMergedRegion(wrapper.range);   
                    }   
                }   
            }   
        }              
    }   
       
 
    /**
     * Copy cell.
     *
     * @param oldCell el old cell
     * @param newCell el new cell
     * @param styleMap el style map
     */
    public static void copyCell(HSSFCell oldCell, HSSFCell newCell, Map<Integer, HSSFCellStyle> styleMap) {   
        if(styleMap != null) {   
            if(oldCell.getSheet().getWorkbook() == newCell.getSheet().getWorkbook()){   
                newCell.setCellStyle(oldCell.getCellStyle());   
            } else{   
                int stHashCode = oldCell.getCellStyle().hashCode();   
                HSSFCellStyle newCellStyle = styleMap.get(stHashCode);   
                if(newCellStyle == null){   
                    newCellStyle = newCell.getSheet().getWorkbook().createCellStyle();   
                    newCellStyle.cloneStyleFrom(oldCell.getCellStyle());   
                    styleMap.put(stHashCode, newCellStyle);   
                }   
                newCell.setCellStyle(newCellStyle);   
            }   
        }   
        switch(oldCell.getCellType()) {   
            case STRING:   
                newCell.setCellValue(oldCell.getStringCellValue());   
                break;   
          case NUMERIC:   
                newCell.setCellValue(oldCell.getNumericCellValue());   
                break;   
            case BLANK:   
                newCell.setCellType(CellType.BLANK);   
                break;   
            case BOOLEAN:   
                newCell.setCellValue(oldCell.getBooleanCellValue());   
                break;   
            case ERROR:   
                newCell.setCellErrorValue(oldCell.getErrorCellValue());   
                break;   
            case FORMULA:   
                newCell.setCellFormula(oldCell.getCellFormula());   
                break;   
            default:   
                break;   
        }   
            
    }   
    
    public static void copyCell(SXSSFCell oldCell, SXSSFCell newCell, Map<Integer, XSSFCellStyle> styleMap) {   
        if(styleMap != null) {   
            if(oldCell.getSheet().getWorkbook() == newCell.getSheet().getWorkbook()){   
                newCell.setCellStyle(oldCell.getCellStyle());   
            } else{   
                int stHashCode = oldCell.getCellStyle().hashCode();   
                XSSFCellStyle newCellStyle = styleMap.get(stHashCode);   
                if(newCellStyle == null){   
                    newCellStyle = (XSSFCellStyle)newCell.getSheet().getWorkbook().createCellStyle();   
                    newCellStyle.cloneStyleFrom(oldCell.getCellStyle());   
                    styleMap.put(stHashCode, newCellStyle);   
                }   
                newCell.setCellStyle(newCellStyle);   
            }   
        }   
        switch(oldCell.getCellType()) {   
            case STRING:   
                newCell.setCellValue(oldCell.getStringCellValue());   
                break;   
          case NUMERIC:   
                newCell.setCellValue(oldCell.getNumericCellValue());   
                break;   
            case BLANK:   
                newCell.setCellType(CellType.BLANK);   
                break;   
            case BOOLEAN:   
                newCell.setCellValue(oldCell.getBooleanCellValue());   
                break;   
            case ERROR:   
                newCell.setCellErrorValue(oldCell.getErrorCellValue());   
                break;   
            case FORMULA:   
                newCell.setCellFormula(oldCell.getCellFormula());   
                break;   
            default:   
                break;   
        }   
            
    }   

    /**
     * Obtiene merged region.
     *
     * @param sheet el sheet
     * @param rowNum el row num
     * @param cellNum el cell num
     * @return merged region
     */
    public static CellRangeAddress getMergedRegion(HSSFSheet sheet, int rowNum, short cellNum) {   
        for (int i = 0; i < sheet.getNumMergedRegions(); i++) { 
            CellRangeAddress merged = sheet.getMergedRegion(i);   
            if (merged.isInRange(rowNum, cellNum)) {   
                return merged;   
            }   
        }   
        return null;   
    }   
    public static CellRangeAddress getMergedRegionXLSX(SXSSFSheet sheet, int rowNum, short cellNum) {   
    	for (int i = 0; i < sheet.getNumMergedRegions(); i++) { 
            CellRangeAddress merged = sheet.getMergedRegion(i);   
            if (merged.isInRange(rowNum, cellNum)) {   
                return merged;   
            }   
        }   
        return null;   
    }  

    /**
     * Comprueba si es new merged region.
     *
     * @param newMergedRegion el new merged region
     * @param mergedRegions el merged regions
     * @return true, si es new merged region
     */
    private static boolean isNewMergedRegion(CellRangeAddressWrapper newMergedRegion, Set<CellRangeAddressWrapper> mergedRegions) {
      return !mergedRegions.contains(newMergedRegion);   
    }   
        
} 
     
