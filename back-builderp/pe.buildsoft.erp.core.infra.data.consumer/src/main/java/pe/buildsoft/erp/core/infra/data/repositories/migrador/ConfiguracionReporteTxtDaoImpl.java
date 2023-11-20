package pe.buildsoft.erp.core.infra.data.repositories.migrador;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.migrador.ConfiguracionReporteTxt;
import pe.buildsoft.erp.core.domain.interfaces.repositories.migrador.ConfiguracionReporteTxtDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericCommonBatchDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class ConfiguracionReporteTxtDaoImpl.
 * <ul>
 * <li>Copyright 2018 MAPFRE -
 * MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Thu Mar 14 10:56:05 COT 2019
 * @since BuildErp 1.0
 */
@Stateless
public class ConfiguracionReporteTxtDaoImpl extends  GenericCommonBatchDAOImpl<String, ConfiguracionReporteTxt> implements ConfiguracionReporteTxtDaoLocal  {

    /* (non-Javadoc)
     * @see pe.gob.mapfre.pwr.rep.ejb.dao.telecobranza.local.ConfiguracionReporteTxtDaoLocal#listarConfiguracionReporteTxt(pe.gob.mapfre.pwr.rep.model.telecobranza.jpa.ConfiguracionReporteTxt)
     */  
    @Override	 
    public List<ConfiguracionReporteTxt> listar(BaseSearch filtro) {
        var query = generarQuery(filtro, false);
        query.setFirstResult(filtro.getStartRow());
        query.setMaxResults(filtro.getOffSet());
        return query.getResultList();
    }   
   
    /**
     * Generar query lista ConfiguracionReporteTxt.
     *
     * @param ConfiguracionReporteTxt el configuracionReporteTxt
     * @param esContador el es contador
     * @return the query
     */
    private Query generarQuery(BaseSearch filtro, boolean esContador) {
        var parametros = new HashMap<String, Object>();
        var jpaql = new StringBuilder();
        if (esContador) {
            jpaql.append(" select count(o.idConfiguracionReporteTxt) from ConfiguracionReporteTxt o where 1=1 ");
        } else {
            jpaql.append(" select o from ConfiguracionReporteTxt o where 1=1 ");           
        }
        if (!StringUtil.isNullOrEmpty(filtro.getCodigoReporte())) {
			jpaql.append(" and o.codigoReporte =:codigoReporte ");
			parametros.put("codigoReporte",  filtro.getCodigoReporte());
		}
		if (!StringUtil.isNullOrEmpty(filtro.getEstado())) {
			jpaql.append(" and upper(o.estado) like :estado ");
			parametros.put("estado", "%" + filtro.getEstado().toUpperCase() + "%");
		}
        if (!esContador) {
            jpaql.append(" ORDER BY o.orden ");
        }
        return createQuery(jpaql.toString(), parametros);
    }

    /* (non-Javadoc)
     * @see pe.gob.mapfre.pwr.rep.ejb.dao.telecobranza.local.ConfiguracionReporteTxtDaoLocal#contarListar{entity.getClassName()}(pe.gob.mapfre.pwr.rep.model.telecobranza.jpa.ConfiguracionReporteTxt)
     */
	@Override
    public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
        return ((Long) query.getSingleResult()).intValue();
    }
    /* (non-Javadoc)
     * @see pe.gob.mapfre.pwr.rep.ejb.dao.telecobranza.local.ConfiguracionReporteTxtDaoLocal#generarIdConfiguracionReporteTxt()
     */
	 @Override
    public String generarId() {
        return UUIDUtil.generarElementUUID();
    }
   
}