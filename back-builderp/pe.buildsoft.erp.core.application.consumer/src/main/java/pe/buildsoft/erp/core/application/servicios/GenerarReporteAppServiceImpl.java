package pe.buildsoft.erp.core.application.servicios;

import java.util.Map;

import jakarta.inject.Inject;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;

import net.sf.jasperreports.engine.JasperPrint;
import pe.buildsoft.erp.core.application.interfaces.GenerarReporteAppServiceLocal;
import pe.buildsoft.erp.core.domain.interfaces.servicios.GenerarReporteServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.FileVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ParametroReporteVO;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class GenerarReporteAppServiceImpl implements GenerarReporteAppServiceLocal {

	/** La reporte service impl. */
	@Inject
	private GenerarReporteServiceLocal servicio;

	@Override
	public FileVO generarReporte(ParametroReporteVO parametroReporteVO) throws Exception {
		return servicio.generarReporte(parametroReporteVO);
	}

	@Override
	public FileVO generarReporte(JasperPrint jasperPrint, String formato, String context, String ruta) {
		return servicio.generarReporte(jasperPrint, formato, context, ruta);
	}

	@Override
	public Map<String, Object> generarReporteArrayPdf(ParametroReporteVO parametroReporteVO) throws Exception {
		return servicio.generarReporteArrayPdf(parametroReporteVO);
	}

	@Override
	public Map<String, Object> generarReporteArrayHtml(ParametroReporteVO parametroReporteVO) throws Exception {
		return servicio.generarReporteArrayHtml(parametroReporteVO);
	}

	@Override
	public Map<String, Object> generarReporteArrayXls(ParametroReporteVO parametroReporteVO) throws Exception {
		return servicio.generarReporteArrayXls(parametroReporteVO);
	}

	@Override
	public Map<String, Object> generarReporteArrayRtf(ParametroReporteVO parametroReporteVO) throws Exception {
		return servicio.generarReporteArrayRtf(parametroReporteVO);
	}

	@Override
	public Map<String, Object> generarReporteArrayOdt(ParametroReporteVO parametroReporteVO) throws Exception {
		return servicio.generarReporteArrayOdt(parametroReporteVO);
	}

	@Override
	public Map<String, Object> generarReporteArrayOds(ParametroReporteVO parametroReporteVO) throws Exception {
		return servicio.generarReporteArrayOds(parametroReporteVO);
	}

	@Override
	public Map<String, Object> generarReporteArrayDocx(ParametroReporteVO parametroReporteVO) throws Exception {
		return servicio.generarReporteArrayDocx(parametroReporteVO);
	}

	@Override
	public Map<String, Object> generarReporteArrayPptx(ParametroReporteVO parametroReporteVO) throws Exception {
		return servicio.generarReporteArrayPptx(parametroReporteVO);
	}

	@Override
	public Map<String, Object> generarReporteArrayXlsx(ParametroReporteVO parametroReporteVO) throws Exception {
		return servicio.generarReporteArrayXlsx(parametroReporteVO);
	}

}