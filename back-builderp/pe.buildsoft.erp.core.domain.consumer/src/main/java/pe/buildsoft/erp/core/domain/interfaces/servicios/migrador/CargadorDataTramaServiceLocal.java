package pe.buildsoft.erp.core.domain.interfaces.servicios.migrador;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.migrador.HeaderReadReporte;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.FileVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaCargaDataTramaVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ValueDataVO;

/**
 * La Class CargadorDataTramaServiceLocal.
 * <ul>
 * <li>Copyright 2018 MAPFRE -
 * MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Mon Jul 29 14:01:07 COT 2019
 * @since BuildErp 1.0
 */
@Local
public interface CargadorDataTramaServiceLocal {
	
	List<Map<String,Object>> listarHeaderReadReporte(String codigoProceso);
	
	List<HeaderReadReporte> listarHeaderReadReporteByCodigo(String codigoProceso);
	
	String generarArchivoCargaDataTramaError(List<Map<String,Object>> listaHeader,List<Map<String,ValueDataVO>> listaDataMapTemp,String archivoName) throws Exception;
	
	String generarArchivoCargaDataTrama(List<Map<String,Object>> listaHeader,List<Map<String,ValueDataVO>> listaDataMapTemp,String archivoName) throws Exception;
	
	RespuestaCargaDataTramaVO procesarCargaDataTrama(List<HeaderReadReporte>  listaHeadeReadReporte,Map<String, Object> parametroMap,FileVO fileVO,String codigoProceso) throws Exception;
	
	RespuestaCargaDataTramaVO registrarTramaData(Map<String,Object> parametroMap, RespuestaCargaDataTramaVO respuestaCargaDataTramaVO) throws Exception; 
}