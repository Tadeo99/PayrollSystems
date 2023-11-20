/**
 * 
 */
package pe.buildsoft.erp.core.application.servicios.migrador;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import pe.buildsoft.erp.core.application.entidades.migrador.HeaderReporteDTO;
import pe.buildsoft.erp.core.application.interfaces.migrador.MigradorProcessReporteAppServiceLocal;
import pe.buildsoft.erp.core.domain.interfaces.servicios.migrador.MigradorProcessReporteServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.utilitario.BaseTransfer;

/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Class MigradorProcessReporteServiceImpl.
 *
 * @author BuildSoft
 * @version 1.0 , 04/10/2019
 * @since BuildErp 1.0
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@TransactionManagement(TransactionManagementType.BEAN)
public class MigradorProcessReporteAppServiceImpl extends BaseTransfer implements MigradorProcessReporteAppServiceLocal {

	/** La log. */
	private Logger log = LoggerFactory.getLogger(MigradorProcessReporteAppServiceImpl.class);

	@Inject
	private MigradorProcessReporteServiceLocal servicio;

	@Override
	public String exportarExcel(String archivoName, String userName, String idSolicitudReporte,
			List<Object> parametroInType, Map<String, Object> propiedadesMap) {
		return servicio.exportarExcel(archivoName, userName, idSolicitudReporte, parametroInType, propiedadesMap);
	}

	@Override
	public String exportarTxt(String archivoName, String userName, String idSolicitudReporte,
			List<Object> parametroInType, Map<String, Object> propiedadesMap) {
		return servicio.exportarTxt(archivoName, userName, idSolicitudReporte, parametroInType, propiedadesMap);
	}

	@Override
	public String migrarSPDatosReporte(String userName, String idSolicitudReporte, List<Object> parametroInType,
			Map<String, Object> propiedadesMap) {
		return servicio.migrarSPDatosReporte(userName, idSolicitudReporte, parametroInType, propiedadesMap);
	}

	@Override
	public String exportarExcel(String archivoName, String userName, String idSolicitudReporte,
			List<Object> parametroInType, Map<String, Object> propiedadesMap, String jNDIConexion) {
		return servicio.exportarExcel(archivoName, userName, idSolicitudReporte, parametroInType, propiedadesMap,
				jNDIConexion);
	}

	@Override
	public String exportarTxt(String archivoName, String userName, String idSolicitudReporte,
			List<Object> parametroInType, Map<String, Object> propiedadesMap, String jNDIConexion) {
		return servicio.exportarTxt(archivoName, userName, idSolicitudReporte, parametroInType, propiedadesMap,
				jNDIConexion);
	}

	@Override
	public String migrarSPDatosReporte(String userName, String idSolicitudReporte, List<Object> parametroInType,
			Map<String, Object> propiedadesMap, String jNDIConexion) {
		return servicio.migrarSPDatosReporte(userName, idSolicitudReporte, parametroInType, propiedadesMap,
				jNDIConexion);
	}

	@Override
	public String migrarSPDatosReporte(String userName, String idSolicitudReporte, List<Object> parametroInType,
			List<Integer> parametroOutType, Map<String, Object> propiedadesMap, String jNDIConexion) {
		return servicio.migrarSPDatosReporte(userName, idSolicitudReporte, parametroInType, parametroOutType,
				propiedadesMap, jNDIConexion);
	}

	@Override
	public String exportarExcel(String archivoName, String userName, String idSolicitudReporte,
			List<Object> parametroInType, List<Integer> parametroOutType, Map<String, Object> propiedadesMap,
			String jNDIConexion) {
		return servicio.exportarExcel(archivoName, userName, idSolicitudReporte, parametroInType, parametroOutType,
				propiedadesMap, jNDIConexion);
	}

	@Override
	public String exportarTxt(String archivoName, String userName, String idSolicitudReporte,
			List<Object> parametroInType, List<Integer> parametroOutType, Map<String, Object> propiedadesMap,
			String jNDIConexion) {
		return servicio.exportarTxt(archivoName, userName, idSolicitudReporte, parametroInType, parametroOutType,
				propiedadesMap, jNDIConexion);
	}

	@Override
	public String generarReporteExel(Map<String, Object> propiedadesMap, String archivoName,
			List<Map<String, Object>> listaDataMap, String userName) {
		return servicio.generarReporteExel(propiedadesMap, archivoName, listaDataMap, userName);
	}

	@Override
	public String generarReporteTxt(Map<String, Object> propiedadesMap, String archivoName,
			List<Map<String, Object>> listaDataMap, String userName) {
		return servicio.generarReporteTxt(propiedadesMap, archivoName, listaDataMap, userName);
	}

	@Override
	public String eliminarDataTemporalReporte(String nombreTablaTMP, String idSolicitudReporte, String userName,
			String jNDIConexion) {
		return servicio.eliminarDataTemporalReporte(nombreTablaTMP, idSolicitudReporte, userName, jNDIConexion);
	}

	@Override
	public List<Map<String, Object>> listarReporteTMP(String nombreTablaTMP, String idSolicitudReporte, String userName,
			int offset, int startRow, String jNDIConexion) {
		return servicio.listarReporteTMP(nombreTablaTMP, idSolicitudReporte, userName, offset, startRow, jNDIConexion);
	}

	@Override
	public int contarReporteTMP(String nombreTablaTMP, String idSolicitudReporte, String userName,
			String jNDIConexion) {
		return servicio.contarReporteTMP(nombreTablaTMP, idSolicitudReporte, userName, jNDIConexion);
	}

	@Override
	public List<HeaderReporteDTO> listarHeaderReporte(String codigoReporte) {
		try {
			return toList(this.servicio.listarHeaderReporte(codigoReporte), HeaderReporteDTO.class);
		} catch (Exception e) {
			log.error("listarHeaderReporte",e);
		}
		return new ArrayList<>();
	}

	@Override
	public List<Map<String, Object>> listarReporteTMP(String nombreTablaTMP, String idSolicitudReporte, String userName,
			String jNDIConexion) {
		return servicio.listarReporteTMP(nombreTablaTMP, idSolicitudReporte, userName, jNDIConexion);
	}

	@Override
	public String obtenerValorProperties(String key) {
		return servicio.obtenerValorProperties(key);
	}

	@Override
	public boolean isObtenerValorProperties(String key) {
		return servicio.isObtenerValorProperties(key);
	}
}
