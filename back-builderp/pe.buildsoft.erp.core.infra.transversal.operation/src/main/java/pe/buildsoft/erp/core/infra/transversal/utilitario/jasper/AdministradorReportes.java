package pe.buildsoft.erp.core.infra.transversal.utilitario.jasper;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import org.apache.commons.digester.annotations.rules.SetProperty;//SWFACTORY 17-12-2015
import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.oasis.JROdsExporter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.fill.JRAbstractLRUVirtualizer;
import net.sf.jasperreports.engine.fill.JRSwapFileVirtualizer;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSwapFile;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.web.util.WebHtmlResourceHandler;
import pe.buildsoft.erp.core.infra.transversal.cache.SwapVirtualizadorCacheUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.JasperPrintVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ParametroReporteVO;
import pe.buildsoft.erp.core.infra.transversal.type.CarpetaType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ArchivoUtilidades;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ConstanteConfigUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ConstanteGenerarReporteUtil;

/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Class AdministradorReportes.
 *
 * @author BuildSoft.
 * @version 1.0 , 07/04/2015
 * @since BuildErp 1.0
 */
public class AdministradorReportes implements Serializable {

	/** La Constante serialVersionUID. */
	private static final long serialVersionUID = -2426599120451342988L;

	/** La log. */
	private static Logger log = LoggerFactory.getLogger(AdministradorReportes.class);

	/** La Constante RUTA_RECURSOS. */
	public static final String RUTA_RECURSOS = ConstanteConfigUtil.RUTA_RECURSOS;

	/** La Constante RUTA_TEMP. */
	public static final String RUTA_REPORTE_GENERADO = ConstanteConfigUtil.SEPARADOR_FILE
			+ CarpetaType.REPORTE_GENERADO.getKey();

	static {
		String ruta = ConstanteConfigUtil.RUTA_RECURSOS_BYTE_BUFFER + "tmp";
		File dir = new File(ruta);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		System.setProperty("java.io.tmpdir", ruta);
	}

	/**
	 * Generar jasper print.
	 *
	 * @param parametroReporteVO el parametro reporte vo
	 * @return the jasper print
	 * @throws Exception
	 */
	private static JasperPrintVO generarJasperPrint(ParametroReporteVO parametroReporteVO) throws Exception {
		JasperPrint jasperPrint = null;
		JRAbstractLRUVirtualizer virtualizer = null;
		JasperPrintVO jasperPrintVO = new JasperPrintVO();
		File source = new File(parametroReporteVO.getRutaReportesAbsoluto() + parametroReporteVO.getJasperFile());
		// IMPLEMENTACION DEL VIRTUALIZADOR
		JRSwapFile swapFile = generarRutaSwap(parametroReporteVO);
		virtualizer = new JRSwapFileVirtualizer(3, swapFile, true);
		if (parametroReporteVO.isOnline()) {
			SwapVirtualizadorCacheUtil.getInstance().put(parametroReporteVO.getUserName(),
					parametroReporteVO.getParametros().get("codigoSolicitud") + "", virtualizer);
		}
		parametroReporteVO.getParametros().put(JRParameter.REPORT_VIRTUALIZER, virtualizer);

		for (int i = 0; i < parametroReporteVO.getSubreportes().length; i++) {
			// Carga cada subreporte
			JasperReport subreporte = (JasperReport) JRLoader
					.loadObject(new File(source.getParent(), parametroReporteVO.getSubreportes()[i]));
			parametroReporteVO.getParametros().put("SUBREPORTE_" + i, parametroReporteVO.getRutaReportesAbsoluto());
		}

		JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(source.getPath());
		jasperPrint = JasperFillManager.fillReport(jasperReport, parametroReporteVO.getParametros(),
				parametroReporteVO.getCn());

		jasperPrintVO.setJasperPrint(jasperPrint);
		jasperPrintVO.setVirtualizer(virtualizer);
		return jasperPrintVO;
	}

	private static JRSwapFile generarRutaSwap(ParametroReporteVO parametroReporteVO) {
		String rutaSession = ConstanteConfigUtil.RUTA_RECURSOS_SWAP_FILE
				+ ConstanteConfigUtil.generarRuta(parametroReporteVO.getUserName());
		if (parametroReporteVO.isOnline()) {
			rutaSession = ConstanteConfigUtil.RUTA_SESSION_TEMP
					+ ConstanteConfigUtil.generarRuta(parametroReporteVO.getUserName());
		}
		File file = new File(rutaSession + parametroReporteVO.getParametros().get("codigoSolicitud"));
		if (!file.exists()) {
			file.mkdirs();
		}
		JRSwapFile swapFile = new JRSwapFile(rutaSession + parametroReporteVO.getParametros().get("codigoSolicitud"),
				100, 100);
		return swapFile;
	}

	/**
	 * Generar jasper printsin conexion.
	 *
	 * @param parametroReporteVO el parametro reporte vo
	 * @return the jasper print
	 */
	private static JasperPrintVO generarJasperPrintsinConexion(ParametroReporteVO parametroReporteVO) {
		JRDataSource jrDataSource = null;
		JRAbstractLRUVirtualizer virtualizer = null;
		JasperPrint jasperPrint = null;
		JasperPrintVO jasperPrintVO = new JasperPrintVO();
		File source = new File(parametroReporteVO.getRutaReportesAbsoluto() + parametroReporteVO.getJasperFile());
		try {
			if (parametroReporteVO.getLista() != null && parametroReporteVO.getLista().size() > 0) {
				jrDataSource = new JRBeanCollectionDataSource(parametroReporteVO.getLista());
			} else {
				jrDataSource = new WebappDataSource();
			}
			for (int i = 0; i < parametroReporteVO.getSubreportes().length; i++) {
				// Carga cada subreporte
				JasperReport subreporte = (JasperReport) JRLoader
						.loadObject(new File(source.getParent(), parametroReporteVO.getSubreportes()[i]));
			}
			if (!parametroReporteVO.getParametros().containsKey("codigoSolicitud")) {
				parametroReporteVO.getParametros().put("codigoSolicitud", parametroReporteVO.getFileName());
			}
			JRSwapFile swapFile = generarRutaSwap(parametroReporteVO);
			virtualizer = new JRSwapFileVirtualizer(3, swapFile, true);
			if (parametroReporteVO.isOnline()) {
				SwapVirtualizadorCacheUtil.getInstance().put(parametroReporteVO.getUserName(),
						parametroReporteVO.getParametros().get("codigoSolicitud") + "", virtualizer);
			}
			parametroReporteVO.getParametros().put(JRParameter.REPORT_VIRTUALIZER, virtualizer);

			JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(source.getPath());
			jasperPrint = JasperFillManager.fillReport(jasperReport, parametroReporteVO.getParametros(), jrDataSource);
			if (jasperPrint.getPages().size() <= 500) {
				for (Iterator<JRPrintPage> iterator = jasperPrint.getPages().iterator(); iterator.hasNext();) { // Remueve
																												// páginas
																												// blancas
																												// si
																												// existieran
					JRPrintPage page = iterator.next();
					if (page.getElements().isEmpty()) {
						iterator.remove();
					}
				}
			}
			jasperPrintVO.setJasperPrint(jasperPrint);
			jasperPrintVO.setVirtualizer(virtualizer);
		} catch (Exception excepcion) {
			log.error("generarJasperPrintsinConexion", excepcion);
			excepcion.printStackTrace();
		}

		return jasperPrintVO;
	}

	/**
	 * Generar reporte array pdf.
	 *
	 * @param parametroReporteVO el parametro reporte vo
	 * @return the byte[]
	 * @throws Exception the exception
	 */
	public static Map<String, Object> generarReporteArrayPdf(ParametroReporteVO parametroReporteVO) throws Exception {
		Map<String, Object> resultado = new HashMap<>();
		JasperPrintVO jaspertPrintVirtualizadorVO = null;
		try {
			if (parametroReporteVO.getCn() != null) {
				jaspertPrintVirtualizadorVO = generarJasperPrint(parametroReporteVO);
			} else {
				jaspertPrintVirtualizadorVO = generarJasperPrintsinConexion(parametroReporteVO);
			}

			boolean obtenerByte = isObtenerByte(parametroReporteVO);
			if (obtenerByte) {
				if (jaspertPrintVirtualizadorVO != null) {
					generarReporteVirtualizerPdf(parametroReporteVO, jaspertPrintVirtualizadorVO);
				}
			}
			if (parametroReporteVO.isOnline()) {
				resultado.put(ConstanteGenerarReporteUtil.JASPER_PRINT, jaspertPrintVirtualizadorVO.getJasperPrint());
			}
			if (parametroReporteVO.isBigMemory() && parametroReporteVO.isOnline()) {
				String nombreArchivo = parametroReporteVO.getFileName() + ".pdf";
				ArchivoUtilidades.copyArchivoBigMemory(nombreArchivo, nombreArchivo, parametroReporteVO.getUserName(),
						false);
			}
		} catch (JRException e) {
			log.error("generarReporteArrayPdf", e);
		}
		return resultado;
	}

	/**
	 * Generar reporte array xls.
	 *
	 * @param parametroReporteVO el parametro reporte vo
	 * @return the byte[]
	 * @throws Exception the exception
	 */
	public static Map<String, Object> generarReporteArrayXls(ParametroReporteVO parametroReporteVO) throws Exception {
		Map<String, Object> resultado = new HashMap<String, Object>();
		JasperPrintVO jaspertPrintVirtualizadorVO = null;
		if (parametroReporteVO.getCn() != null) {
			jaspertPrintVirtualizadorVO = generarJasperPrint(parametroReporteVO);
		} else {
			jaspertPrintVirtualizadorVO = generarJasperPrintsinConexion(parametroReporteVO);
		}
		boolean isObtenerByte = isObtenerByte(parametroReporteVO);
		if (isObtenerByte) {
			if (jaspertPrintVirtualizadorVO != null) {
				generarReporteVirtualizerXls(parametroReporteVO, jaspertPrintVirtualizadorVO);
			}
		}
		if (parametroReporteVO.isOnline()) {
			resultado.put(ConstanteGenerarReporteUtil.JASPER_PRINT, jaspertPrintVirtualizadorVO.getJasperPrint());
		}
		if (parametroReporteVO.isBigMemory()) {
			String nombreArchivo = parametroReporteVO.getFileName() + ".xls";
			ArchivoUtilidades.copyArchivoBigMemory(nombreArchivo, nombreArchivo, parametroReporteVO.getUserName(),
					false);
		}
		parametroReporteVO = null;
		jaspertPrintVirtualizadorVO = null;
		return resultado;
	}

	/**
	 * Generar reporte array xls.
	 *
	 * @param parametroReporteVO el parametro reporte vo
	 * @return the byte[]
	 * @throws Exception the exception
	 */
	public static Map<String, Object> generarReporteArrayXlsx(ParametroReporteVO parametroReporteVO) throws Exception {
		Map<String, Object> resultado = new HashMap<String, Object>();
		JasperPrintVO jaspertPrintVirtualizadorVO = null;
		if (parametroReporteVO.getCn() != null) {
			jaspertPrintVirtualizadorVO = generarJasperPrint(parametroReporteVO);
		} else {
			jaspertPrintVirtualizadorVO = generarJasperPrintsinConexion(parametroReporteVO);
		}
		boolean isObtenerByte = isObtenerByte(parametroReporteVO);
		if (isObtenerByte) {
			if (jaspertPrintVirtualizadorVO != null) {
				generarReporteVirtualizerXlsx(parametroReporteVO, jaspertPrintVirtualizadorVO);
			}
		}

		if (parametroReporteVO.isOnline()) {
			resultado.put(ConstanteGenerarReporteUtil.JASPER_PRINT, jaspertPrintVirtualizadorVO.getJasperPrint());
		}
		if (parametroReporteVO.isBigMemory()) {
			String nombreArchivo = parametroReporteVO.getFileName() + ".xlsx";
			ArchivoUtilidades.copyArchivoBigMemory(nombreArchivo, nombreArchivo, parametroReporteVO.getUserName(),
					false);
		}
		parametroReporteVO = null;
		jaspertPrintVirtualizadorVO = null;
		return resultado;
	}

	/**
	 * Generar reporte array docx bean.
	 *
	 * @param parametroReporteVO el parametro reporte vo
	 * @return the byte[]
	 * @throws Exception the exception
	 */
	public static Map<String, Object> generarReporteArrayDocx(ParametroReporteVO parametroReporteVO) throws Exception {
		Map<String, Object> resultado = new HashMap<String, Object>();
		JasperPrintVO jaspertPrintVirtualizadorVO = null;
		if (parametroReporteVO.getCn() != null) {
			jaspertPrintVirtualizadorVO = generarJasperPrint(parametroReporteVO);
		} else {
			jaspertPrintVirtualizadorVO = generarJasperPrintsinConexion(parametroReporteVO);
		}
		boolean isObtenerByte = isObtenerByte(parametroReporteVO);
		if (isObtenerByte) {
			if (jaspertPrintVirtualizadorVO != null) {
				generarReporteVirtualizerDocx(parametroReporteVO, jaspertPrintVirtualizadorVO);
			}
		}

		if (parametroReporteVO.isOnline()) {
			resultado.put(ConstanteGenerarReporteUtil.JASPER_PRINT, jaspertPrintVirtualizadorVO.getJasperPrint());
		}
		if (parametroReporteVO.isBigMemory()) {
			String nombreArchivo = parametroReporteVO.getFileName() + ".docx";
			ArchivoUtilidades.copyArchivoBigMemory(nombreArchivo, nombreArchivo, parametroReporteVO.getUserName(),
					false);
		}
		jaspertPrintVirtualizadorVO = null;
		jaspertPrintVirtualizadorVO = null;
		return resultado;
	}

	/**
	 * Generar reporte array html.
	 *
	 * @param parametroReporteVO el parametro reporte vo
	 * @return the map
	 * @throws Exception the exception
	 */
	public static Map<String, Object> generarReporteArrayHtml(ParametroReporteVO parametroReporteVO) throws Exception {
		Map<String, Object> resultado = new HashMap<String, Object>();
		JasperPrintVO jaspertPrintVirtualizadorVO = null;
		if (parametroReporteVO.getCn() != null) {
			jaspertPrintVirtualizadorVO = generarJasperPrint(parametroReporteVO);
		} else {
			jaspertPrintVirtualizadorVO = generarJasperPrintsinConexion(parametroReporteVO);
			if (!parametroReporteVO.isOnline()) {
				jaspertPrintVirtualizadorVO.getVirtualizer().cleanup();
			}
		}
		boolean isObtenerByte = isObtenerByte(parametroReporteVO);
		if (isObtenerByte) {
			if (jaspertPrintVirtualizadorVO != null) {
				generarReporteVirtualizerHtml(parametroReporteVO, jaspertPrintVirtualizadorVO);
			}
		}

		if (parametroReporteVO.isOnline()) {
			resultado.put(ConstanteGenerarReporteUtil.JASPER_PRINT, jaspertPrintVirtualizadorVO.getJasperPrint());
		}
		if (parametroReporteVO.isBigMemory()) {
			String nombreArchivo = parametroReporteVO.getFileName() + ".html";
			ArchivoUtilidades.copyArchivoBigMemory(nombreArchivo, nombreArchivo, parametroReporteVO.getUserName(),
					false);
		}
		if (!parametroReporteVO.isOnline()) {
			limpiarRutaSwap(parametroReporteVO);
		}
		parametroReporteVO = null;
		jaspertPrintVirtualizadorVO = null;
		return resultado;
	}

	/**
	 * Generar reporte array rtf.
	 *
	 * @param parametroReporteVO el parametro reporte vo
	 * @return the map
	 * @throws Exception the exception
	 */
	public static Map<String, Object> generarReporteArrayRtf(ParametroReporteVO parametroReporteVO) throws Exception {
		Map<String, Object> resultado = new HashMap<String, Object>();
		JasperPrintVO jaspertPrintVirtualizadorVO = null;
		if (parametroReporteVO.getCn() != null) {
			jaspertPrintVirtualizadorVO = generarJasperPrint(parametroReporteVO);
		} else {
			jaspertPrintVirtualizadorVO = generarJasperPrintsinConexion(parametroReporteVO);
		}
		boolean isObtenerByte = isObtenerByte(parametroReporteVO);
		if (isObtenerByte) {
			if (jaspertPrintVirtualizadorVO != null) {
				generarReporteVirtualizerRtf(parametroReporteVO, jaspertPrintVirtualizadorVO);
			}
		}

		if (parametroReporteVO.isOnline()) {
			resultado.put(ConstanteGenerarReporteUtil.JASPER_PRINT, jaspertPrintVirtualizadorVO.getJasperPrint());
		}

		if (parametroReporteVO.isBigMemory()) {
			String nombreArchivo = parametroReporteVO.getFileName() + ".rtf";
			ArchivoUtilidades.copyArchivoBigMemory(nombreArchivo, nombreArchivo, parametroReporteVO.getUserName(),
					false);
		}
		parametroReporteVO = null;
		jaspertPrintVirtualizadorVO = null;
		return resultado;
	}

	/**
	 * Generar reporte array odt.
	 *
	 * @param parametroReporteVO el parametro reporte vo
	 * @return the map
	 * @throws Exception the exception
	 */
	public static Map<String, Object> generarReporteArrayOdt(ParametroReporteVO parametroReporteVO) throws Exception {
		Map<String, Object> resultado = new HashMap<String, Object>();
		JasperPrintVO jaspertPrintVirtualizadorVO = null;
		if (parametroReporteVO.getCn() != null) {
			jaspertPrintVirtualizadorVO = generarJasperPrint(parametroReporteVO);
		} else {
			jaspertPrintVirtualizadorVO = generarJasperPrintsinConexion(parametroReporteVO);
		}
		boolean isObtenerByte = isObtenerByte(parametroReporteVO);
		if (isObtenerByte) {
			if (jaspertPrintVirtualizadorVO != null) {
				generarReporteVirtualizerOdt(parametroReporteVO, jaspertPrintVirtualizadorVO);
			}
		}

		if (parametroReporteVO.isOnline()) {
			resultado.put(ConstanteGenerarReporteUtil.JASPER_PRINT, jaspertPrintVirtualizadorVO.getJasperPrint());
		}
		if (parametroReporteVO.isBigMemory()) {
			String nombreArchivo = parametroReporteVO.getFileName() + ".odt";
			ArchivoUtilidades.copyArchivoBigMemory(nombreArchivo, nombreArchivo, parametroReporteVO.getUserName(),
					false);
		}
		parametroReporteVO = null;
		jaspertPrintVirtualizadorVO = null;
		return resultado;
	}

	/**
	 * Generar reporte array ods.
	 *
	 * @param parametroReporteVO el parametro reporte vo
	 * @return the map
	 * @throws Exception the exception
	 */
	public static Map<String, Object> generarReporteArrayOds(ParametroReporteVO parametroReporteVO) throws Exception {
		Map<String, Object> resultado = new HashMap<String, Object>();
		JasperPrintVO jaspertPrintVirtualizadorVO = null;
		if (parametroReporteVO.getCn() != null) {
			jaspertPrintVirtualizadorVO = generarJasperPrint(parametroReporteVO);
		} else {
			jaspertPrintVirtualizadorVO = generarJasperPrintsinConexion(parametroReporteVO);
		}
		boolean isObtenerByte = isObtenerByte(parametroReporteVO);
		if (isObtenerByte) {
			if (jaspertPrintVirtualizadorVO != null) {
				generarReporteVirtualizerOds(parametroReporteVO, jaspertPrintVirtualizadorVO);
			}
		}
		if (parametroReporteVO.isOnline()) {
			resultado.put(ConstanteGenerarReporteUtil.JASPER_PRINT, jaspertPrintVirtualizadorVO.getJasperPrint());
		}

		if (parametroReporteVO.isBigMemory()) {
			String nombreArchivo = parametroReporteVO.getFileName() + ".ods";
			ArchivoUtilidades.copyArchivoBigMemory(nombreArchivo, nombreArchivo, parametroReporteVO.getUserName(),
					false);
		}
		parametroReporteVO = null;
		jaspertPrintVirtualizadorVO = null;
		return resultado;
	}

	/**
	 * Generar reporte array pptx.
	 *
	 * @param parametroReporteVO el parametro reporte vo
	 * @return the map
	 * @throws Exception the exception
	 */
	public static Map<String, Object> generarReporteArrayPptx(ParametroReporteVO parametroReporteVO) throws Exception {
		Map<String, Object> resultado = new HashMap<String, Object>();
		parametroReporteVO.getParametros().put("SUBREPORT_DIR", parametroReporteVO.getRutaReportesAbsoluto());
		JasperPrintVO jaspertPrintVirtualizadorVO = null;
		if (parametroReporteVO.getCn() != null) {
			jaspertPrintVirtualizadorVO = generarJasperPrint(parametroReporteVO);
		} else {
			jaspertPrintVirtualizadorVO = generarJasperPrintsinConexion(parametroReporteVO);
		}
		boolean isObtenerByte = isObtenerByte(parametroReporteVO);
		if (isObtenerByte) {
			if (jaspertPrintVirtualizadorVO != null) {
				generarReporteVirtualizerPptx(parametroReporteVO, jaspertPrintVirtualizadorVO);
			}
		}
		if (parametroReporteVO.isOnline()) {
			resultado.put(ConstanteGenerarReporteUtil.JASPER_PRINT, jaspertPrintVirtualizadorVO.getJasperPrint());
		}
		if (parametroReporteVO.isBigMemory()) {
			String nombreArchivo = parametroReporteVO.getFileName() + ".pptx";
			ArchivoUtilidades.copyArchivoBigMemory(nombreArchivo, nombreArchivo, parametroReporteVO.getUserName(),
					false);
		}
		parametroReporteVO = null;
		jaspertPrintVirtualizadorVO = null;
		return resultado;
	}

	private static void generarReporteVirtualizerPdf(ParametroReporteVO parametroReporteVO,
			JasperPrintVO jaspertPrintVirtualizadorVO) throws Exception {
		String nombreArchivoSalida = generarRutaReporte(parametroReporteVO);
		generarReporteArrayPdf(jaspertPrintVirtualizadorVO.getJasperPrint(), nombreArchivoSalida);
		jaspertPrintVirtualizadorVO.getVirtualizer().cleanup(); // LIBERAR DISCO
		limpiarRutaSwap(parametroReporteVO);
	}

	public static void generarReporteArrayPdf(JasperPrint jasperPrint, String ruta) {
		try {
			JasperExportManager.exportReportToPdfFile(jasperPrint, ruta);
		} catch (JRException e) {
			log.error("generarReporteArrayPdf", e);
		}

	}

	public static void generarReporteVirtualizerXls(ParametroReporteVO parametroReporteVO,
			JasperPrintVO jaspertPrintVirtualizadorVO) throws Exception {
		String nombreArchivoSalida = generarRutaReporte(parametroReporteVO);
		generarReporteArrayXls(jaspertPrintVirtualizadorVO.getJasperPrint(), nombreArchivoSalida);
		jaspertPrintVirtualizadorVO.getVirtualizer().cleanup(); // LIBERAR DISCO
		limpiarRutaSwap(parametroReporteVO);
		jaspertPrintVirtualizadorVO = null;
	}

	public static void generarReporteArrayXls(JasperPrint jasperPrint, String ruta) {
		JRXlsExporter jrXlsExporter = new JRXlsExporter();
		File file = new File(ruta);
		try {
			jrXlsExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
			jrXlsExporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE, file);
			jrXlsExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			jrXlsExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
			jrXlsExporter.setParameter(JRXlsExporterParameter.IS_FONT_SIZE_FIX_ENABLED, Boolean.TRUE);
			jrXlsExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			jrXlsExporter.exportReport();
		} catch (JRException e) {
			log.error("generarReporteArrayXls", e);
		}
	}

	public static void generarReporteVirtualizerXlsx(ParametroReporteVO parametroReporteVO,
			JasperPrintVO jaspertPrintVirtualizadorVO) throws Exception {
		String nombreArchivoSalida = generarRutaReporte(parametroReporteVO);
		generarReporteArrayXlsx(jaspertPrintVirtualizadorVO.getJasperPrint(), nombreArchivoSalida);
		jaspertPrintVirtualizadorVO.getVirtualizer().cleanup(); // LIBERAR DISCO
		limpiarRutaSwap(parametroReporteVO);
		jaspertPrintVirtualizadorVO = null;
	}

	public static void generarReporteArrayXlsx(JasperPrint jasperPrint, String ruta) {
		JRXlsxExporter jrXlsExporter = new JRXlsxExporter();
		File file = new File(ruta);
		try {
			jrXlsExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
			jrXlsExporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE, file);
			jrXlsExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			jrXlsExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
			jrXlsExporter.setParameter(JRXlsExporterParameter.IS_FONT_SIZE_FIX_ENABLED, Boolean.TRUE);
			jrXlsExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			jrXlsExporter.exportReport();
		} catch (JRException e) {
			log.error("generarReporteArrayXlsx", e);
		}
	}

	public static void generarReporteVirtualizerDocx(ParametroReporteVO parametroReporteVO,
			JasperPrintVO jaspertPrintVirtualizadorVO) throws Exception {
		String nombreArchivoSalida = generarRutaReporte(parametroReporteVO);
		generarReporteArrayDocx(jaspertPrintVirtualizadorVO.getJasperPrint(), nombreArchivoSalida);
		jaspertPrintVirtualizadorVO.getVirtualizer().cleanup(); // LIBERAR DISCO
		limpiarRutaSwap(parametroReporteVO);
		jaspertPrintVirtualizadorVO = null;
	}

	public static void generarReporteArrayDocx(JasperPrint jasperPrint, String ruta) {
		try {
			List<JasperPrint> JasperPrintList = new ArrayList<JasperPrint>();
			JasperPrintList.add(jasperPrint);
			JRDocxExporter exporter = new JRDocxExporter(DefaultJasperReportsContext.getInstance());
			exporter.setExporterInput(SimpleExporterInput.getInstance(JasperPrintList));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(ruta));
			exporter.exportReport();
		} catch (JRException e) {
			log.error("generarReporteArrayDocx", e);
		}
	}

	public static void generarReporteVirtualizerHtml(ParametroReporteVO parametroReporteVO,
			JasperPrintVO jaspertPrintVirtualizadorVO) throws Exception {
		String nombreArchivoSalida = generarRutaReporte(parametroReporteVO);
		generarReporteArrayHtml(jaspertPrintVirtualizadorVO.getJasperPrint(),
				(String) parametroReporteVO.getParametros().get(ConstanteGenerarReporteUtil.CONTEXT_URL),
				nombreArchivoSalida);
		jaspertPrintVirtualizadorVO.getVirtualizer().cleanup(); // LIBERAR DISCO
		limpiarRutaSwap(parametroReporteVO);
		jaspertPrintVirtualizadorVO = null;
	}

	public static void generarReporteArrayHtml(JasperPrint jasperPrint, String context, String ruta) {
		try {
			HtmlExporter exporter = new HtmlExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			SimpleHtmlExporterOutput output = new SimpleHtmlExporterOutput(ruta);
			output.setImageHandler(
					new WebHtmlResourceHandler("" + context + "/resourcesweb/reporte/images/logo_mapfre_general.jpg"));
			exporter.setExporterOutput(output);
			exporter.exportReport();
		} catch (JRException e) {
			log.error("generarReporteArrayHtml", e);
		}
	}

	public static void generarReporteVirtualizerRtf(ParametroReporteVO parametroReporteVO,
			JasperPrintVO jaspertPrintVirtualizadorVO) throws Exception {
		String nombreArchivoSalida = generarRutaReporte(parametroReporteVO);
		generarReporteArrayRtf(jaspertPrintVirtualizadorVO.getJasperPrint(), nombreArchivoSalida);
		jaspertPrintVirtualizadorVO.getVirtualizer().cleanup(); // LIBERAR DISCO
		limpiarRutaSwap(parametroReporteVO);
		jaspertPrintVirtualizadorVO = null;
	}

	public static void generarReporteArrayRtf(JasperPrint jasperPrint, String ruta) {
		File file = new File(ruta);
		try {
			List<JasperPrint> JasperPrintList = new ArrayList<JasperPrint>();
			JasperPrintList.add(jasperPrint);
			JRRtfExporter exporter = new JRRtfExporter(DefaultJasperReportsContext.getInstance());
			exporter.setExporterInput(SimpleExporterInput.getInstance(JasperPrintList));
			exporter.setExporterOutput(new SimpleWriterExporterOutput(file));
			exporter.exportReport();
		} catch (JRException e) {
			log.error("generarReporteArrayRtf", e);
		}
	}

	public static void generarReporteVirtualizerOdt(ParametroReporteVO parametroReporteVO,
			JasperPrintVO jaspertPrintVirtualizadorVO) throws Exception {
		String nombreArchivoSalida = generarRutaReporte(parametroReporteVO);
		generarReporteArrayOdt(jaspertPrintVirtualizadorVO.getJasperPrint(), nombreArchivoSalida);
		jaspertPrintVirtualizadorVO.getVirtualizer().cleanup(); // LIBERAR DISCO
		limpiarRutaSwap(parametroReporteVO);
		jaspertPrintVirtualizadorVO = null;
	}

	public static void generarReporteArrayOdt(JasperPrint jasperPrint, String ruta) {
		File file = new File(ruta);
		try {
			List<JasperPrint> JasperPrintList = new ArrayList<JasperPrint>();
			JasperPrintList.add(jasperPrint);
			JROdtExporter exporter = new JROdtExporter(DefaultJasperReportsContext.getInstance());
			exporter.setExporterInput(SimpleExporterInput.getInstance(JasperPrintList));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(file));
			exporter.exportReport();
		} catch (JRException e) {
			log.error("generarReporteArrayOdt", e);
		}
	}

	public static void generarReporteVirtualizerOds(ParametroReporteVO parametroReporteVO,
			JasperPrintVO jaspertPrintVirtualizadorVO) throws Exception {
		String nombreArchivoSalida = generarRutaReporte(parametroReporteVO);
		generarReporteArrayOds(jaspertPrintVirtualizadorVO.getJasperPrint(), nombreArchivoSalida);
		jaspertPrintVirtualizadorVO.getVirtualizer().cleanup(); // LIBERAR DISCO
		limpiarRutaSwap(parametroReporteVO);
		jaspertPrintVirtualizadorVO = null;
	}

	public static void generarReporteArrayOds(JasperPrint jasperPrint, String ruta) {
		try {
			List<JasperPrint> JasperPrintList = new ArrayList<JasperPrint>();
			JasperPrintList.add(jasperPrint);
			JROdsExporter exporter = new JROdsExporter(DefaultJasperReportsContext.getInstance());
			exporter.setExporterInput(SimpleExporterInput.getInstance(JasperPrintList));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(ruta));
			exporter.exportReport();
		} catch (JRException e) {
			log.error("generarReporteArrayOds", e);
		}
	}

	public static void generarReporteVirtualizerPptx(ParametroReporteVO parametroReporteVO,
			JasperPrintVO jaspertPrintVirtualizadorVO) throws Exception {
		String nombreArchivoSalida = generarRutaReporte(parametroReporteVO);
		generarReporteArrayPptx(jaspertPrintVirtualizadorVO.getJasperPrint(), nombreArchivoSalida);
		jaspertPrintVirtualizadorVO.getVirtualizer().cleanup(); // LIBERAR DISCO
		limpiarRutaSwap(parametroReporteVO);
		jaspertPrintVirtualizadorVO = null;
	}

	public static void generarReporteArrayPptx(JasperPrint jasperPrint, String ruta) {
		try {
			List<JasperPrint> JasperPrintList = new ArrayList<JasperPrint>();
			JasperPrintList.add(jasperPrint);
			JRPptxExporter exporter = new JRPptxExporter(DefaultJasperReportsContext.getInstance());
			exporter.setExporterInput(SimpleExporterInput.getInstance(JasperPrintList));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(ruta));
			exporter.exportReport();
		} catch (JRException e) {
			log.error("generarReporteArrayPptx", e);
		}
	}

	public static void generarReporteArrayXlsx2(JasperPrint jasperPrint, String ruta) {

		String[] nombreColumnas = jasperPrint.getName().split(";");
		JRXlsxExporter jrXlsExporter = new JRXlsxExporter();
		File file = new File(ruta);
		try {

			jrXlsExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
			jrXlsExporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE, file);
			// jrXlsExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
			// Boolean.TRUE);
			jrXlsExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
			jrXlsExporter.setParameter(JRXlsExporterParameter.IS_FONT_SIZE_FIX_ENABLED, Boolean.TRUE);
			jrXlsExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			jrXlsExporter.setParameter(JRXlsExporterParameter.SHEET_NAMES, nombreColumnas);
			// jrXlsExporter.setParameter(JRXlsExporterParameter.SHEET_NAMES, new String[]
			// {"mI REPORTE"});
			jrXlsExporter.exportReport();
		} catch (JRException e) {
			log.error("generarReporteArrayXlsx2", e);
		}
	}

	private static boolean isObtenerByte(ParametroReporteVO parametroReporteVO) {
		boolean obtenerByte = false;
		if (parametroReporteVO.isOnline()) {
			obtenerByte = false;
		}
		if (parametroReporteVO.isCrearArchivo()) {
			obtenerByte = true;
		}
		if (parametroReporteVO.isBigMemory()) {
			obtenerByte = true;
		}
		return obtenerByte;
	}

	private static void limpiarRutaSwap(ParametroReporteVO parametroReporteVO) throws Exception {
		String rutaSession = ConstanteConfigUtil.RUTA_RECURSOS_SWAP_FILE
				+ ConstanteConfigUtil.generarRuta(parametroReporteVO.getUserName());
		if (parametroReporteVO.isOnline()) {
			rutaSession = ConstanteConfigUtil.RUTA_SESSION_TEMP
					+ ConstanteConfigUtil.generarRuta(parametroReporteVO.getUserName());
		}
		ArchivoUtilidades
				.limpiarArchivoAllDirectory(rutaSession + parametroReporteVO.getParametros().get("codigoSolicitud"));
	}

	public static String generarRutaReporte(ParametroReporteVO parametroReporteVO) {
		String nombreArchivoSalida = RUTA_RECURSOS;
		nombreArchivoSalida = nombreArchivoSalida + RUTA_REPORTE_GENERADO;
		nombreArchivoSalida = nombreArchivoSalida + ConstanteConfigUtil.SEPARADOR_FILE
				+ parametroReporteVO.getUserName();
		File file = new File(nombreArchivoSalida);
		if (!file.exists()) {
			file.mkdirs();
		}
		nombreArchivoSalida = nombreArchivoSalida + ConstanteConfigUtil.SEPARADOR_FILE
				+ parametroReporteVO.getFileName() + "." + parametroReporteVO.getFormato();
		return nombreArchivoSalida;
	}
}