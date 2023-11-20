package pe.buildsoft.erp.core.domain.interfaces.repositories;

import java.util.Map;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ParametroReporteVO;

/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul> 
 * 
 * La Interface ReporteDaoLocal.
 *
 * @author BuildSoft.
 * @version 1.0 , 07/04/2015
 * @since BuildErp 1.0
 */
@Local
public interface ReporteDaoLocal {
	
	/**
	 * Generar reporte array pdf.
	 *
	 * @param parametroReporteVO el parametro reporte vo
	 * @return the Map<String,Object>
	 * @throws Exception the exception
	 */
	Map<String,Object> generarReporteArrayPdf(ParametroReporteVO parametroReporteVO) throws Exception;

	
	/**
	 * Generar reporte array pdf bean.
	 *
	 * @param parametroReporteVO el parametro reporte vo
	 * @return the Map<String,Object>
	 * @throws Exception the exception
	 */
	Map<String,Object> generarReporteArrayPdfBean(ParametroReporteVO parametroReporteVO)
			throws Exception;
	
	/**
	 * Generar reporte array html.
	 *
	 * @param parametroReporteVO el parametro reporte vo
	 * @return the Map<String,Object>
	 * @throws Exception the exception
	 */
	Map<String,Object> generarReporteArrayHtml(ParametroReporteVO parametroReporteVO) throws Exception;

	
	/**
	 * Generar reporte array html bean.
	 *
	 * @param parametroReporteVO el parametro reporte vo
	 * @return the Map<String,Object>
	 * @throws Exception the exception
	 */
	Map<String,Object> generarReporteArrayHtmlBean(ParametroReporteVO parametroReporteVO)
			throws Exception;

	/**
	 * Generar reporte array xls.
	 *
	 * @param parametroReporteVO el parametro reporte vo
	 * @return the Map<String,Object>
	 * @throws Exception the exception
	 */
	Map<String,Object> generarReporteArrayXls(ParametroReporteVO parametroReporteVO) throws Exception;

	/**
	 * Generar reporte array xls bean.
	 *
	 * @param parametroReporteVO el parametro reporte vo
	 * @return the Map<String,Object>
	 * @throws Exception the exception
	 */
	Map<String,Object> generarReporteArrayXlsBean(ParametroReporteVO parametroReporteVO)
			throws Exception;

	/**
	 * Generar reporte array rtf.
	 *
	 * @param parametroReporteVO el parametro reporte vo
	 * @return the Map<String,Object>
	 * @throws Exception the exception
	 */
	Map<String,Object> generarReporteArrayRtf(ParametroReporteVO parametroReporteVO) throws Exception;

	
	/**
	 * Generar reporte array rtf bean.
	 *
	 * @param parametroReporteVO el parametro reporte vo
	 * @return the Map<String,Object>
	 * @throws Exception the exception
	 */
	Map<String,Object> generarReporteArrayRtfBean(ParametroReporteVO parametroReporteVO)
			throws Exception;
	
	
	/**
	 * Generar reporte array odt.
	 *
	 * @param parametroReporteVO el parametro reporte vo
	 * @return the Map<String,Object>
	 * @throws Exception the exception
	 */
	Map<String,Object> generarReporteArrayOdt(ParametroReporteVO parametroReporteVO) throws Exception;

	
	/**
	 * Generar reporte array rtf bean.
	 *
	 * @param parametroReporteVO el parametro reporte vo
	 * @return the Map<String,Object>
	 * @throws Exception the exception
	 */
	Map<String,Object> generarReporteArrayOdtBean(ParametroReporteVO parametroReporteVO)
			throws Exception;
	
	/**
	 * Generar reporte array ods.
	 *
	 * @param parametroReporteVO el parametro reporte vo
	 * @return the Map<String,Object>
	 * @throws Exception the exception
	 */
	Map<String,Object> generarReporteArrayOds(ParametroReporteVO parametroReporteVO) throws Exception;

	
	/**
	 * Generar reporte array ods bean.
	 *
	 * @param parametroReporteVO el parametro reporte vo
	 * @return the Map<String,Object>
	 * @throws Exception the exception
	 */
	Map<String,Object> generarReporteArrayOdsBean(ParametroReporteVO parametroReporteVO)
			throws Exception;
	
	
	/**
	 * Generar reporte array docx.
	 *
	 * @param parametroReporteVO el parametro reporte vo
	 * @return the map
	 * @throws Exception the exception
	 */
	Map<String,Object> generarReporteArrayDocx(ParametroReporteVO parametroReporteVO) throws Exception;

	
	/**
	 * Generar reporte array docx bean.
	 *
	 * @param parametroReporteVO el parametro reporte vo
	 * @return the map
	 * @throws Exception the exception
	 */
	Map<String,Object> generarReporteArrayDocxBean(ParametroReporteVO parametroReporteVO)
			throws Exception;
	
	
	/**
	 * Generar reporte array pptx.
	 *
	 * @param parametroReporteVO el parametro reporte vo
	 * @return the map
	 * @throws Exception the exception
	 */
	Map<String,Object> generarReporteArrayPptx(ParametroReporteVO parametroReporteVO) throws Exception;

	
	/**
	 * Generar reporte array pptx bean.
	 *
	 * @param parametroReporteVO el parametro reporte vo
	 * @return the map
	 * @throws Exception the exception
	 */
	Map<String,Object> generarReporteArrayPptxBean(ParametroReporteVO parametroReporteVO)
			throws Exception;
	
	
	/**
	 * Generar reporte array xlsx.
	 *
	 * @param parametroReporteVO el parametro reporte vo
	 * @return the map
	 * @throws Exception the exception
	 */
	Map<String,Object> generarReporteArrayXlsx(ParametroReporteVO parametroReporteVO) throws Exception;

	
	/**
	 * Generar reporte array xlsx bean.
	 *
	 * @param parametroReporteVO el parametro reporte vo
	 * @return the map
	 * @throws Exception the exception
	 */
	Map<String,Object> generarReporteArrayXlsxBean(ParametroReporteVO parametroReporteVO)
			throws Exception;

}