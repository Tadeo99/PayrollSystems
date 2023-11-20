package pe.buildsoft.erp.core.domain.servicios.nota.reporte;

import java.util.HashMap;
import java.util.Map;

import jakarta.inject.Inject;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pe.buildsoft.erp.core.domain.entidades.nota.vo.ReporteNotaVO;
import pe.buildsoft.erp.core.domain.interfaces.servicios.GenerarReporteServiceLocal;
import pe.buildsoft.erp.core.domain.interfaces.servicios.nota.reporte.NotaReporteServiceLocal;
import pe.buildsoft.erp.core.domain.nota.type.TipoReporteNotaType;
import pe.buildsoft.erp.core.domain.type.NombreReporteType;
import pe.buildsoft.erp.core.infra.transversal.cache.ICache;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ParametroReporteVO;
import pe.buildsoft.erp.core.infra.transversal.type.TipoReporteGenerarType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class NotaServiceImpl.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue Apr 18 13:43:23 COT 2017
 * @since SIAA-CORE 2.1
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@TransactionManagement(TransactionManagementType.BEAN)
public class NotaReporteServiceImpl implements NotaReporteServiceLocal {

	private Logger log = LoggerFactory.getLogger(NotaReporteServiceImpl.class);

	@Inject
	private GenerarReporteServiceLocal generarReporteServiceImpl;

	@Inject
	private ICache cache;

	@Override
	public String generarReporteMultiple(ReporteNotaVO filtro) {
		String fileName = UUIDUtil.generarElementUUID();
		String userName = cache.getUserName(filtro.getAuthToken());
		String codigoGeneradoReporte = "";
		var parametros = new HashMap<String, Object>();
		NombreReporteType reporte = null;
		if (filtro.getTipoReporte() != null) {
			switch (TipoReporteNotaType.get(filtro.getTipoReporte())) {
			case RECORD_NOTA_MASIVA:
				reporte = NombreReporteType.JR_REP_RECORD_NOTA_MASIVO;
				parametros.put("Id_AhnoSemestre", filtro.getIdAnhoSemestre());
				parametros.put("ruta", "");
				parametros.put("Id_Escuela", filtro.getIdEscuela());
				parametros.put("ruta_logo", "");
				parametros = obtenerParametroRecordNota(parametros, "");
				break;
			case REPORTE_RESUMEN_NOTA_POR_SEMESTRE:
				reporte = NombreReporteType.JR_REP_RESUMEN_NOTA_BY_SEMESTRE;
				parametros.put("Id_AhnoSemestre", filtro.getIdAnhoSemestre());
				parametros.put("ruta_logo", "");
				parametros = obtenerParametroRecordNota(parametros, "");
				break;
			default:
				break;
			}
			ParametroReporteVO objParam = new ParametroReporteVO(parametros, null, reporte.getCarpeta(),
					reporte.getKey(), true, TipoReporteGenerarType.PDF.getKey(), fileName);
			objParam.setUserName(userName);
			objParam.setOnline(filtro.isOnline());
			codigoGeneradoReporte = generarReporteServiceImpl.obtenerParametroReporteBigMemory(objParam);
		}

		return codigoGeneradoReporte;
	}

	/**
	 * Obtener parametro record nota.
	 *
	 * @param parametros el parametros
	 * @return the map
	 */
	private HashMap<String, Object> obtenerParametroRecordNota(HashMap<String, Object> parametros, String authToken) {
		return parametros;
	}

}