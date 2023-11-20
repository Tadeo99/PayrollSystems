package pe.buildsoft.erp.core.infra.data.repositories.migrador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.migrador.HeaderReporte;
import pe.buildsoft.erp.core.domain.interfaces.repositories.migrador.HeaderReporteDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericCommonBatchDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ScriptSqlResulJDBCVO;

/**
 * La Class HeaderReporteDaoImpl.
 * <ul>
 * <li>Copyright 2018 MAPFRE -
 * MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Fri Sep 28 12:14:58 COT 2018
 * @since BuildErp 1.0
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@TransactionManagement(TransactionManagementType.BEAN)
public class HeaderReporteDaoImpl extends  GenericCommonBatchDAOImpl<String, HeaderReporte> implements HeaderReporteDaoLocal  {

	private Logger log = LoggerFactory.getLogger(HeaderReporteDaoImpl.class);
	
	
	/* (non-Javadoc)
     * @see pe.gob.mapfre.pwr.rep.ejb.dao.telecobranza.local.HeaderReporteDaoLocal#listarHeaderReporte(pe.gob.mapfre.pwr.rep.model.telecobranza.jpa.HeaderReporte)
     */  
    @Override	 
    public List<HeaderReporte> listar(String codigoReporte) {
    	return generarQueryListaHeaderReporte(codigoReporte).getResultList();
    }   
   
    /**
     * Generar query lista HeaderReporte.
     *
     * @param HeaderReporte el headerReporte
     * @param esContador el es contador
     * @return the query
     */
    private Query generarQueryListaHeaderReporte(String codigoReporte) {
        var parametros = new HashMap<String, Object>();
        var jpaql = new StringBuilder();
        jpaql.append(" select o from HeaderReporte o where 1=1 ");           
		jpaql.append(" and upper(o.codigoReporte) = :codigoReporte ");
		parametros.put("codigoReporte", codigoReporte.toUpperCase());
        jpaql.append(" ORDER BY o.codigoReporte, o.orden ");
        return createQuery(jpaql.toString(), parametros);
    }

    /* (non-Javadoc)
     * @see pe.gob.mapfre.pwr.rep.ejb.dao.telecobranza.local.HeaderReporteDaoLocal#listarHeaderReporte(pe.gob.mapfre.pwr.rep.model.telecobranza.jpa.HeaderReporte)
     */  
    @Override	 
    public List<Map<String,Object>> listarByCodigo(String codigoReporte) {
		try {
			ScriptSqlResulJDBCVO	data = executeQuery(new StringBuilder("select keyHeader,valueHeader,tipoFormato,valueFormato from commo.HeaderReporte where codigoReporte='" + codigoReporte +"' order by orden "), null);
			if (!data.isTieneError() && !data.getListaData().isEmpty()) {
				 return data.getListaData();
				 
			}
		} catch (Exception e) {
			log.error("listarByCodigo",e);
		}
        return new ArrayList<>();
    }   
   
  
   
}