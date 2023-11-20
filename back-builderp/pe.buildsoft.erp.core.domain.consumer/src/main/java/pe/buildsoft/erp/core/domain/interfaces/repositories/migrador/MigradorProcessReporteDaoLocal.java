/**
 * 
 */
package pe.buildsoft.erp.core.domain.interfaces.repositories.migrador;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ConfiguracionTramaDataVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ConfiguracionTramaDetalleVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaCargaDataTramaVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ValueDataVO;
import pe.buildsoft.erp.core.infra.transversal.type.TipoCampoType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.BigMemoryManager;

/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul> 
 * 
 * La Interface MigradorProcessReporteDaoLocal.
 *
 * @author BuildSoft.
 * @version 1.0 , 04/10/2019
 * @since BuildErp 1.0
 */
@Local
public interface MigradorProcessReporteDaoLocal  extends GenericDAOLocal<Object, Object> {
	
	String migrarSPDatosReporte(String nombreSpMigrador,String idSolicitudReporte, List<Object> parametroInType,List<Integer> parametroOutType, String JNDIConexion);

	String eliminarDataTemporalReporte(String nombreTablaTMP,String idSolicitudReporte,String userName,String filtro,String JNDIConexion);
	
	List<Map<String, Object>> listarReporteTMP(String nombreTablaTMP,String idSolicitudReporte,String userName,String filtro,int offset,int startRow,String JNDIConexion);
	
	List<Map<String, Object>> listarReporteTMPByOrden(String nombreTablaTMP,String idSolicitudReporte,String userName,String filtro,int offset,int startRow,String ordenBy,String JNDIConexion);
	
	int contarReporteTMP(String nombreTablaTMP,String idSolicitudReporte,String userName,String filtro,String JNDIConexion);
	
	RespuestaCargaDataTramaVO registrarTramaData(Map<String, Object> parametroMap,RespuestaCargaDataTramaVO respuestaCargaDataTramaVO) throws Exception ;
	
	TipoCampoType obtenerTipoCampo(ConfiguracionTramaDetalleVO filtro);
	
	BigMemoryManager<String,ConfiguracionTramaDataVO> generarTuplaPersistente(List<Map<String, ValueDataVO>> listaDataResulGrupoMap, Map<Integer, Boolean> errorTuplaMap, String[] schemaTableName, Map<String, ConfiguracionTramaDetalleVO> configuracionTramaDetalleMap, Map<String, String> campoNoPersistente,Map<String,Object> parametroMap);
}
