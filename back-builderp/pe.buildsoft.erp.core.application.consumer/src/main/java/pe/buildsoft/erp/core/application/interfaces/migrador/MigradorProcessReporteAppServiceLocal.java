/**
 * 
 */
package pe.buildsoft.erp.core.application.interfaces.migrador;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.application.entidades.migrador.HeaderReporteDTO;

/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Interface CubosServiceLocal.
 *
 * @author BuildSoft.
 * @version 1.0 , 29/03/2016
 * @since BuildErp 1.0
 */
@Local
public interface MigradorProcessReporteAppServiceLocal {

	// Para exportar reportes
	String exportarExcel(String archivoName, String userName, String idSolicitudReporte, List<Object> parametroInType,
			Map<String, Object> propiedadesMap);

	String exportarTxt(String archivoName, String userName, String idSolicitudReporte, List<Object> parametroInType,
			Map<String, Object> propiedadesMap);

	String exportarExcel(String archivoName, String userName, String idSolicitudReporte, List<Object> parametroInType,
			Map<String, Object> propiedadesMap, String JNDIConexion);

	String exportarTxt(String archivoName, String userName, String idSolicitudReporte, List<Object> parametroInType,
			Map<String, Object> propiedadesMap, String JNDIConexion);

	String exportarExcel(String archivoName, String userName, String idSolicitudReporte, List<Object> parametroInType,
			List<Integer> parametroOutType, Map<String, Object> propiedadesMap, String JNDIConexion);

	String exportarTxt(String archivoName, String userName, String idSolicitudReporte, List<Object> parametroInType,
			List<Integer> parametroOutType, Map<String, Object> propiedadesMap, String JNDIConexion);

	String generarReporteExel(Map<String, Object> propiedadesMap, String archivoName,
			List<Map<String, Object>> listaDataMap, String userName);

	String generarReporteTxt(Map<String, Object> propiedadesMap, String archivoName,
			List<Map<String, Object>> listaDataMap, String userName);

	// Inicio Para mostrar a demanda en la grilla
	String migrarSPDatosReporte(String userName, String idSolicitudReporte, List<Object> parametroInType,
			Map<String, Object> propiedadesMap);

	String migrarSPDatosReporte(String userName, String idSolicitudReporte, List<Object> parametroInType,
			Map<String, Object> propiedadesMap, String JNDIConexion);

	String migrarSPDatosReporte(String userName, String idSolicitudReporte, List<Object> parametroInType,
			List<Integer> parametroOutType, Map<String, Object> propiedadesMap, String JNDIConexion);

	String eliminarDataTemporalReporte(String nombreTablaTMP, String idSolicitudReporte, String userName,
			String JNDIConexion);

	List<Map<String, Object>> listarReporteTMP(String nombreTablaTMP, String idSolicitudReporte, String userName,
			int offset, int startRow, String JNDIConexion);

	int contarReporteTMP(String nombreTablaTMP, String idSolicitudReporte, String userName, String JNDIConexion);

	List<HeaderReporteDTO> listarHeaderReporte(String codigoReporte);

	// Fin Para mostrar a demanda en la grilla
	String obtenerValorProperties(String key);

	boolean isObtenerValorProperties(String key);

	List<Map<String, Object>> listarReporteTMP(String nombreTablaTMP, String idSolicitudReporte, String userName,
			String jNDIConexion);
}