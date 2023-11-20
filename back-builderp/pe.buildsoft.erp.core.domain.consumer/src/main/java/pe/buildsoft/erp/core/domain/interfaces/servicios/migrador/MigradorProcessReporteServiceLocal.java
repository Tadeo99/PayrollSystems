/**
 * 
 */
package pe.buildsoft.erp.core.domain.interfaces.servicios.migrador;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.migrador.HeaderReporte;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.CabeceraReporteVO;

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
public interface MigradorProcessReporteServiceLocal {
	
	//Para exportar reportes
	String exportarExcel(String archivoName, String userName,String idSolicitudReporte, List<Object> parametroInType,Map<String, Object> propiedadesMap);
	String exportarTxt(String archivoName, String userName,String idSolicitudReporte, List<Object> parametroInType,Map<String, Object> propiedadesMap);
	String exportarExcel(String archivoName, String userName,String idSolicitudReporte, List<Object> parametroInType, Map<String, Object> propiedadesMap,String jndiConexion);
	String exportarTxt(String archivoName, String userName,String idSolicitudReporte, List<Object> parametroInType, Map<String, Object> propiedadesMap,String jndiConexion);
	String exportarExcel(String archivoName, String userName,String idSolicitudReporte, List<Object> parametroInType, List<Integer> parametroOutType,Map<String, Object> propiedadesMap,String jndiConexion);
	String exportarTxt(String archivoName, String userName,String idSolicitudReporte, List<Object> parametroInType, List<Integer> parametroOutType,Map<String, Object> propiedadesMap,String jndiConexion);
	String generarReporteExel(Map<String, Object> propiedadesMap, String archivoName,List<Map<String, Object>> listaDataMap,String userName );
	String generarReporteTxt(Map<String, Object> propiedadesMap, String archivoName,List<Map<String, Object>> listaDataMap,String userName );
	
	//Inicio Para mostrar a demanda en la grilla
	String migrarSPDatosReporte( String userName,String idSolicitudReporte, List<Object> parametroInType,Map<String, Object> propiedadesMap);
	String migrarSPDatosReporte(String userName,String idSolicitudReporte, List<Object> parametroInType, Map<String, Object> propiedadesMap,String jndiConexion);
	String migrarSPDatosReporte(String userName,String idSolicitudReporte, List<Object> parametroInType, List<Integer> parametroOutType,Map<String, Object> propiedadesMap,String jndiConexion);
	String eliminarDataTemporalReporte(String nombreTablaTMP,String idSolicitudReporte,String userName,String jndiConexion);
	String eliminarDataTemporalReporte(String nombreTablaTMP,String idSolicitudReporte,String userName,String filtro,String jndiConexion);
    List<Map<String, Object>> listarReporteTMP(String nombreTablaTMP,String idSolicitudReporte,String userName,int offset,int startRow,String jndiConexion);
	int contarReporteTMP(String nombreTablaTMP,String idSolicitudReporte,String userName,String jndiConexion);
	List<HeaderReporte> listarHeaderReporte(String codigoReporte);
	//Fin Para mostrar a demanda en la grilla
	String obtenerValorProperties(String key);
	int obtenerValorPropertiesInt(String key);
	boolean isObtenerValorProperties(String key);
	List<Map<String, Object>> listarReporteTMP(	String nombreTablaTMP,String idSolicitudReporte,String userName,String jndiConexion);
	List<Map<String, Object>> listarReporteTMPOrderBy(	String nombreTablaTMP,String idSolicitudReporte,String userName,String orderBy,String jndiConexion);
	List<Map<String, Object>> listarReporteTMPOrderBy(	String nombreTablaTMP,String idSolicitudReporte,String userName,String filtro,String orderBy,String jndiConexion);
	
	String generarZip(String archivoName,String extension, CabeceraReporteVO objFiltro, String... files);
	String generarZip(String archivoName,String extension,Map<String,Object> reporteGeneradoMap, CabeceraReporteVO objFiltro, String... files);
}