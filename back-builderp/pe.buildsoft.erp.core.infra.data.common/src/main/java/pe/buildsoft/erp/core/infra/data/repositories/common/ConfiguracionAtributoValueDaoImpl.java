package pe.buildsoft.erp.core.infra.data.repositories.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.common.ConfiguracionAtributoValue;
import pe.buildsoft.erp.core.domain.interfaces.repositories.common.ConfiguracionAtributoValueDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericCommonDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class ConfiguracionAtributoValueDaoImpl.
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
public class ConfiguracionAtributoValueDaoImpl extends  GenericCommonDAOImpl<String, ConfiguracionAtributoValue> implements ConfiguracionAtributoValueDaoLocal  {

	private final Logger log = LoggerFactory.getLogger(ConfiguracionAtributoValueDaoImpl.class);
	
    /* (non-Javadoc)
     * @see pe.com.builderp.core.facturacion.ejb.dao.venta.local.ConfiguracionAtributoValueDaoLocal#listarConfiguracionAtributoValue(pe.com.builderp.core.facturacion.model.jpa.venta.ConfiguracionAtributoValue)
     */  
    @Override	 
    public Map<String,List<ConfiguracionAtributoValue>> listar(List<String> listaIdConfiguracionAtributo,String idTupla) {
    	Map<String,List<ConfiguracionAtributoValue>> resultado = new HashMap<>();
        var query = generarQuery(listaIdConfiguracionAtributo,idTupla);
        List<ConfiguracionAtributoValue> listaTemp = query.getResultList();
        for (var objValue : listaTemp) {
			String key = objValue.getConfiguracionAtributo().getIdConfiguracionAtributo();
			if (!resultado.containsKey(key)) {
				List<ConfiguracionAtributoValue> value = new ArrayList<>();
				value.add(objValue);
				resultado.put(key, value );
			} else {
				List<ConfiguracionAtributoValue> value = resultado.get(key);
				value.add(objValue);
				resultado.put(key, value );
			}
		}
        return resultado;
    }   
   
    @Override	 
    public boolean eliminar(List<String> listaIdConfiguracionAtributo,String idTupla) {
    	boolean resultado = true;
    	try {
            var parametros = new HashMap<String, Object>();
            var jpaql = new StringBuilder();
            jpaql.append(" delete from ConfiguracionAtributoValue o where 1=1 ");         
            jpaql.append(" and o.configuracionAtributo.idConfiguracionAtributo in (:listaIdConfiguracionAtributo) ");
            jpaql.append(" and o.idTuplaEntidad = :idTuplaEntidad");
            parametros.put("listaIdConfiguracionAtributo", listaIdConfiguracionAtributo);
            parametros.put("idTuplaEntidad", idTupla);
            var query = createQuery(jpaql.toString(), parametros);
            query.executeUpdate();
        } catch (Exception e) {
        	log.error("eliminarConfiguracionAtributoValue",e);
            resultado = false;
        }
    	return resultado;
    }
    
    /**
     * Generar query lista ConfiguracionAtributoValue.
     *
     * @param ConfiguracionAtributoValue el configuracionAtributoValue
     * @param esContador el es contador
     * @return the query
     */
    private Query generarQuery(List<String> listaIdConfiguracionAtributo,String idTupla) {
        var parametros = new HashMap<String, Object>();
        var jpaql = new StringBuilder();
        jpaql.append(" select o from ConfiguracionAtributoValue o left join fetch o.configuracionAtributo where 1=1 ");
        jpaql.append(" and o.configuracionAtributo.idConfiguracionAtributo in (:listaIdConfiguracionAtributo) ");
        jpaql.append(" and o.idTuplaEntidad = :idTuplaEntidad");
        parametros.put("listaIdConfiguracionAtributo", listaIdConfiguracionAtributo);
        parametros.put("idTuplaEntidad", idTupla);
       return createQuery(jpaql.toString(), parametros);
    }

    /* (non-Javadoc)
     * @see pe.com.builderp.core.facturacion.ejb.dao.venta.local.ConfiguracionAtributoValueDaoLocal#generarIdConfiguracionAtributoValue()
     */
	 @Override
    public String generarId() {
        return UUIDUtil.generarElementUUID();
    }
}