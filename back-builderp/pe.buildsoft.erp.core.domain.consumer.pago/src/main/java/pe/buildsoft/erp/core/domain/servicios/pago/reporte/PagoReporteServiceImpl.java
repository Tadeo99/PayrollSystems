package pe.buildsoft.erp.core.domain.servicios.pago.reporte;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.inject.Inject;
import jakarta.ejb.Stateless;

import pe.buildsoft.erp.core.domain.entidades.pago.ControlPago;
import pe.buildsoft.erp.core.domain.entidades.pago.DetControlPago;
import pe.buildsoft.erp.core.domain.interfaces.repositories.pago.ControlPagoDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.pago.DetControlPagoDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.servicios.GenerarReporteServiceLocal;
import pe.buildsoft.erp.core.domain.interfaces.servicios.pago.reporte.PagoReporteServiceLocal;
import pe.buildsoft.erp.core.domain.type.NombreReporteType;
import pe.buildsoft.erp.core.infra.transversal.cache.ICache;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ParametroReporteVO;
import pe.buildsoft.erp.core.infra.transversal.type.TipoReporteGenerarType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.NumerosUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class PagoServiceImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 21:45:13 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class PagoReporteServiceImpl implements PagoReporteServiceLocal {

	/** El servicio control pago dao impl. */
	@Inject
	private ControlPagoDaoLocal controlPagoDaoImpl;

	/** El servicio det control pago dao impl. */
	@Inject
	private DetControlPagoDaoLocal detControlPagoDaoImpl;

	@Inject
	private ICache cacheUtil;

	@Inject
	private GenerarReporteServiceLocal generarReporteServiceImpl;

	@Override
	public String generarReportePagoIndividual(ControlPago filtro) {
		String fileName = UUIDUtil.generarElementUUID();
		String userName = cacheUtil.getUserName(filtro.getAuthToken());
		var parametros = new HashMap<String, Object>();
		parametros.put("ruta", "");
		List<ControlPago> listaControlPago = new ArrayList<>();
		ControlPago controlPagoObj = filtro;
		controlPagoObj = this.obtenerControlPagoById(controlPagoObj.getIdControlPago()).get(0);
		parametros.put("montoLetra", NumerosUtil.Convertir(controlPagoObj.getMontoTotal().toString(), true));
		String userNombres = cacheUtil.getUserNombres(filtro.getAuthToken());
		parametros.put("nombreCajero", userNombres);

		listaControlPago.add(controlPagoObj);
		NombreReporteType reporte = NombreReporteType.JR_REP_BOLETA_PAGOS_REALIZADOS_ALUMNO;
		ParametroReporteVO objParam = new ParametroReporteVO(parametros, listaControlPago, reporte.getCarpeta(),
				reporte.getKey(), true, TipoReporteGenerarType.PDF.getKey(), fileName);
		objParam.setUserName(userName);
		objParam.setOnline(filtro.isOnline());
		return generarReporteServiceImpl.obtenerParametroReporteBigMemory(objParam);
	}

	private List<ControlPago> obtenerControlPagoById(String idcontrolPago) {
		List<ControlPago> resultado = new ArrayList<>();
		List<ControlPago> listaTemp = controlPagoDaoImpl.listarById(idcontrolPago);
		for (ControlPago obj : listaTemp) {
			/*
			 * obj.getAlumno().setDescripcionView(obj.getAlumno().getNombres() + " " +
			 * obj.getAlumno().getApellidoPaterno() + " " +
			 * obj.getAlumno().getApellidoMaterno());
			 */
			obj.setControlPagoDetControlPagoList(listarDetControlPagoByControlPago(obj.getIdControlPago()));
			// controlPago.getEntidad().setDescripcionView(controlPago.getEntidad().getNombre());
			resultado.add(obj);
		}
		return resultado;
	}

	private List<DetControlPago> listarDetControlPagoByControlPago(String idControlPago) {
		return this.detControlPagoDaoImpl.listarById(idControlPago);
	}

}