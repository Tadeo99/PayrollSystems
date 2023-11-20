package pe.buildsoft.erp.core.infra.data.repositories.common;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.common.ConfiguracionAtributo;
import pe.buildsoft.erp.core.domain.interfaces.repositories.common.ConfiguracionAtributoDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericCommonDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class ConfiguracionAtributoDaoImpl.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Dec 28 12:32:56 COT 2017
 * @since SIAA-CORE 2.1
 */
@Stateless
public class ConfiguracionAtributoDaoImpl extends  GenericCommonDAOImpl<String, ConfiguracionAtributo> implements ConfiguracionAtributoDaoLocal  {

    /* (non-Javadoc)
     * @see pe.com.builderp.core.facturacion.ejb.dao.venta.local.ConfiguracionAtributoDaoLocal#listarConfiguracionAtributo(pe.com.builderp.core.facturacion.model.jpa.venta.ConfiguracionAtributo)
     */  
    @Override	 
    public List<ConfiguracionAtributo> listar(BaseSearch filtro) {
        var query = generarQuery(filtro, false);
        if (filtro.getOffSet() > 0) {
        	query.setFirstResult(filtro.getStartRow());
        	query.setMaxResults(filtro.getOffSet());
        }
        return query.getResultList();
    }   
   
    /**
     * Generar query lista ConfiguracionAtributo.
     *
     * @param ConfiguracionAtributo el configuracionAtributo
     * @param esContador el es contador
     * @return the query
     */
    private Query generarQuery(BaseSearch filtro, boolean esContador) {
        var parametros = new HashMap<String, Object>();
        var jpaql = new StringBuilder();
        if (esContador) {
            jpaql.append(" select count(o.idConfiguracionAtributo) from ConfiguracionAtributo o where 1=1 ");
        } else {
            jpaql.append(" select o from ConfiguracionAtributo o left join fetch o.itemByIdNombreEntidad left join fetch o.itemByIdComponte  left join fetch o.listaItem where 1=1 ");           
        }
        if (!StringUtil.isNullOrEmpty(filtro.getId())) {
			jpaql.append(" and upper(o.itemByIdNombreEntidad.nombre) like :nombreEntidad ");
			parametros.put("nombreEntidad", filtro.getId().toString().toUpperCase());
		}
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
	          jpaql.append(" and upper(o.nombreAtributo) like :search ");
	          parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
	    } else {
		}
        if (!esContador) {
            jpaql.append(" ORDER BY o.itemByIdNombreEntidad.nombre,o.orden ");
        }
       return createQuery(jpaql.toString(), parametros);
        
    }

    /* (non-Javadoc)
     * @see pe.com.builderp.core.facturacion.ejb.dao.venta.local.ConfiguracionAtributoDaoLocal#contarListar{entity.getClassName()}(pe.com.builderp.core.facturacion.model.jpa.venta.ConfiguracionAtributo)
     */
	@Override
    public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
    }
    /* (non-Javadoc)
     * @see pe.com.builderp.core.facturacion.ejb.dao.venta.local.ConfiguracionAtributoDaoLocal#generarIdConfiguracionAtributo()
     */
	 @Override
    public String generarId() {
        return UUIDUtil.generarElementUUID();
    }
   
}