package pe.buildsoft.erp.core.domain.servicios;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import net.sf.jasperreports.engine.JasperPrint;
import pe.buildsoft.erp.core.domain.interfaces.repositories.ReporteDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.servicios.GenerarReporteServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.cache.ICache;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.FileVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ParametroReporteVO;
import pe.buildsoft.erp.core.infra.transversal.type.RutaReporteType;
import pe.buildsoft.erp.core.infra.transversal.type.TipoReporteGenerarType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ConstanteGenerarReporteUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.jasper.AdministradorReportes;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class GenerarReporteServiceImpl implements GenerarReporteServiceLocal {

	private Logger log = LoggerFactory.getLogger(GenerarReporteServiceImpl.class);

	/** La reporte dao impl. */
	@Inject
	private ReporteDaoLocal reporteDaoImpl;
	
	@Inject
	private ICache sessionUtil;

	@Override
	public String obtenerParametroReporteBigMemory(ParametroReporteVO parametroReporteVO) {
		String resultado = parametroReporteVO.getFileName();
		RutaReporteType rutaReporteType = RutaReporteType.JASPER;
		parametroReporteVO.setRutaReporteType(rutaReporteType);
		parametroReporteVO.setBigMemory(true);
		parametroReporteVO.setCrearArchivo(false);
		try {
			generarReporte(parametroReporteVO);
		} catch (Exception e) {
			resultado = "${ERROR}" + e.getMessage();
			log.error("obtenerParametroReporteBigMemory", e);
		}
		return resultado;
	}

	@Override
	public FileVO generarReporte(ParametroReporteVO parametroReporteVO) throws Exception {
		FileVO resultado = new FileVO();
		String codigo = "";
		String contentType = "";
		String filename = parametroReporteVO.getFileName();
		byte[] reporteGenerado = null;
		Map<String, Object> reporteGeneradoTemp = null;
		TipoReporteGenerarType tipoReporteGenerarType = TipoReporteGenerarType.get(parametroReporteVO.getFormato());
		switch (tipoReporteGenerarType) {
		case PDF:
			reporteGeneradoTemp = generarReporteArrayPdf(parametroReporteVO);
			break;
		case HTML:
			reporteGeneradoTemp = generarReporteArrayHtml(parametroReporteVO);
			break;

		case XLS:
			reporteGeneradoTemp = generarReporteArrayXls(parametroReporteVO);
			break;

		case RTF:
			reporteGeneradoTemp = generarReporteArrayRtf(parametroReporteVO);
			break;

		case ODT:
			reporteGeneradoTemp = generarReporteArrayOdt(parametroReporteVO);
			break;

		case ODS:
			reporteGeneradoTemp = generarReporteArrayOds(parametroReporteVO);
			break;

		case DOCX:
			reporteGeneradoTemp = generarReporteArrayDocx(parametroReporteVO);
			break;

		case PPTX:
			reporteGeneradoTemp = generarReporteArrayPptx(parametroReporteVO);
			break;

		case XLSX:
			reporteGeneradoTemp = generarReporteArrayXlsx(parametroReporteVO);
			break;

		default:
			break;
		}
		contentType = tipoReporteGenerarType.getContentType();
		if (tipoReporteGenerarType != null) {
			filename += "." + tipoReporteGenerarType.getKey();
		}
		if (reporteGeneradoTemp != null) {
			if (reporteGeneradoTemp.containsKey(ConstanteGenerarReporteUtil.BYTE)) {
				reporteGenerado = (byte[]) reporteGeneradoTemp.get(ConstanteGenerarReporteUtil.BYTE);
			}
		}
		//resultado.setReporteGeneradoMap(reporteGeneradoTemp);
		byte[] bufferFinal = reporteGenerado;
		codigo = filename;
		if (reporteGeneradoTemp != null && bufferFinal != null) {
			if (reporteGeneradoTemp.containsKey(ConstanteGenerarReporteUtil.BYTE)) {
				resultado.setData(bufferFinal);
				resultado.setLength(bufferFinal.length);
			}
		}
		resultado.setName(filename);
		resultado.setDataBig(filename);
		resultado.setMime(contentType);
		if (!parametroReporteVO.isOnline()) {
			resultado.setUserName(parametroReporteVO.getUserName());
		}
		generarDescarga(parametroReporteVO.getFileName(),resultado);
		return resultado;
	}
	
	private void generarDescarga(String key,FileVO objeto) {
		sessionUtil.put(key, objeto);
	}

	@Override
	public FileVO generarReporte(JasperPrint jasperPrint, String formato, String context, String ruta) {
		FileVO resultado = new FileVO();
		String contentType = "";
		TipoReporteGenerarType tipoReporteGenerarType = TipoReporteGenerarType.get(formato);
		switch (tipoReporteGenerarType) {
		case PDF:
			AdministradorReportes.generarReporteArrayPdf(jasperPrint, ruta);
			break;
		case HTML:
			AdministradorReportes.generarReporteArrayHtml(jasperPrint, context, ruta);
			break;

		case XLS:
			AdministradorReportes.generarReporteArrayXls(jasperPrint, ruta);
			break;

		case RTF:
			AdministradorReportes.generarReporteArrayRtf(jasperPrint, ruta);
			break;

		case ODT:
			AdministradorReportes.generarReporteArrayOdt(jasperPrint, ruta);
			break;

		case ODS:
			AdministradorReportes.generarReporteArrayOds(jasperPrint, ruta);
			break;

		case DOCX:
			AdministradorReportes.generarReporteArrayDocx(jasperPrint, ruta);
			break;

		case PPTX:
			AdministradorReportes.generarReporteArrayPptx(jasperPrint, ruta);
			break;

		case XLSX:
			AdministradorReportes.generarReporteArrayXlsx(jasperPrint, ruta);
			break;
		/* SWFACTORY INICIO 17-12-2015 */
		case XLSX2:
			AdministradorReportes.generarReporteArrayXlsx2(jasperPrint, ruta);
			break;
		/* SWFACTORY FIN */
		default:
			break;
		}
		contentType = tipoReporteGenerarType.getContentType();
		resultado.setDataBig(UUIDUtil.generarElementUUID());
		resultado.setMime(contentType);
		return resultado;
	}

	@Override
	public Map<String, Object> generarReporteArrayPdf(ParametroReporteVO parametroReporteVO) throws Exception {
		if (parametroReporteVO.isBean()) {
			return reporteDaoImpl.generarReporteArrayPdfBean(parametroReporteVO);
		} else {
			return reporteDaoImpl.generarReporteArrayPdf(parametroReporteVO);
		}
	}

	@Override
	public Map<String, Object> generarReporteArrayHtml(ParametroReporteVO parametroReporteVO) throws Exception {
		if (parametroReporteVO.isBean()) {
			return reporteDaoImpl.generarReporteArrayHtmlBean(parametroReporteVO);
		} else {
			return reporteDaoImpl.generarReporteArrayHtml(parametroReporteVO);
		}
	}

	@Override
	public Map<String, Object> generarReporteArrayXls(ParametroReporteVO parametroReporteVO) throws Exception {
		if (parametroReporteVO.isBean()) {
			return reporteDaoImpl.generarReporteArrayXlsBean(parametroReporteVO);
		} else {
			return reporteDaoImpl.generarReporteArrayXls(parametroReporteVO);
		}
	}

	@Override
	public Map<String, Object> generarReporteArrayRtf(ParametroReporteVO parametroReporteVO) throws Exception {
		if (parametroReporteVO.isBean()) {
			return reporteDaoImpl.generarReporteArrayRtfBean(parametroReporteVO);
		} else {
			return reporteDaoImpl.generarReporteArrayRtf(parametroReporteVO);
		}
	}

	@Override
	public Map<String, Object> generarReporteArrayOdt(ParametroReporteVO parametroReporteVO) throws Exception {
		if (parametroReporteVO.isBean()) {
			return reporteDaoImpl.generarReporteArrayOdtBean(parametroReporteVO);
		} else {
			return reporteDaoImpl.generarReporteArrayOdt(parametroReporteVO);
		}
	}

	@Override
	public Map<String, Object> generarReporteArrayOds(ParametroReporteVO parametroReporteVO) throws Exception {
		if (parametroReporteVO.isBean()) {
			return reporteDaoImpl.generarReporteArrayOdsBean(parametroReporteVO);
		} else {
			return reporteDaoImpl.generarReporteArrayOds(parametroReporteVO);
		}
	}

	@Override
	public Map<String, Object> generarReporteArrayDocx(ParametroReporteVO parametroReporteVO) throws Exception {
		if (parametroReporteVO.isBean()) {
			return reporteDaoImpl.generarReporteArrayDocxBean(parametroReporteVO);
		} else {
			return reporteDaoImpl.generarReporteArrayDocx(parametroReporteVO);
		}
	}

	@Override
	public Map<String, Object> generarReporteArrayPptx(ParametroReporteVO parametroReporteVO) throws Exception {
		if (parametroReporteVO.isBean()) {
			return reporteDaoImpl.generarReporteArrayPptxBean(parametroReporteVO);
		} else {
			return reporteDaoImpl.generarReporteArrayPptx(parametroReporteVO);
		}
	}

	@Override
	public Map<String, Object> generarReporteArrayXlsx(ParametroReporteVO parametroReporteVO) throws Exception {
		if (parametroReporteVO.isBean()) {
			return reporteDaoImpl.generarReporteArrayXlsxBean(parametroReporteVO);
		} else {
			return reporteDaoImpl.generarReporteArrayXlsx(parametroReporteVO);
		}
	}

}