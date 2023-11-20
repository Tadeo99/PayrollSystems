package pe.buildsoft.erp.core.domain.servicios.rrhh.hora.reporte;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.inject.Inject;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;

import pe.buildsoft.erp.core.domain.entidades.rrhh.hora.vo.RegistroHoraReporteVO;
import pe.buildsoft.erp.core.domain.interfaces.servicios.migrador.MigradorProcessReporteServiceLocal;
import pe.buildsoft.erp.core.domain.interfaces.servicios.rrhh.hora.reporte.HoraReporteServiceLocal;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@TransactionManagement(TransactionManagementType.BEAN)
public class HoraReporteServiceImpl implements HoraReporteServiceLocal {

	@Inject
	private MigradorProcessReporteServiceLocal serviceMigrador;

	private int obtenerValorProperties(String key) {
		return serviceMigrador.obtenerValorPropertiesInt(key);
	}

	@Override
	public String procesarReporteRegistroHora(RegistroHoraReporteVO obj) {
		Map<String, Object> config = new HashMap<>();
		List<Object> parametros = addParameterCamotePoliza(obj);
		String idSolicitud = obj.getIdSolicitudReporte();
		String usuario = obj.getUsuario();
//		config.put("isOnline", obj.isOnline());
		config.put("isOnline", true);
		config.put("nombreSpMigrador", "plani.spreprephora");
		config.put("codigoReporte", "headerreporte001");
		config.put("nombreTablaTMP", "plani.tblrephoratmp");
		config.put("titulo", "ReporteRegistroHora");
		return serviceMigrador.exportarExcel(obj.getArchivoName(), usuario, idSolicitud, parametros, config);
	}

	private List<Object> addParameterCamotePoliza(RegistroHoraReporteVO obj) {
		List<Object> resultado = new ArrayList<>();
		resultado.add(obj.getIdSolicitudReporte());
		resultado.add(obj.getUsuario());
		resultado.add(obtenerValorProperties("reporteHora.log"));// properties
		resultado.add(obj.getIdPeriodo());
		return resultado;
	}

}
