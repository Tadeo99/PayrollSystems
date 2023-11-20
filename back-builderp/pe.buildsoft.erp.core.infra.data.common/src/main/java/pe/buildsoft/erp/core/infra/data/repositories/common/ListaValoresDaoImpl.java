package pe.buildsoft.erp.core.infra.data.repositories.common;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.common.ListaValores;
import pe.buildsoft.erp.core.domain.interfaces.repositories.common.ListaValoresDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericCommonDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * La Class ListaValoresDaoImpl.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue Apr 18 19:53:48 COT 2017
 * @since SIAA-CORE 2.1
 */
@Stateless
public class ListaValoresDaoImpl extends  GenericCommonDAOImpl<Long, ListaValores> implements ListaValoresDaoLocal  {

    /* (non-Javadoc)
     * @see pe.com.edu.siaa.core.ejb.dao.seguridad.local.ListaValoresDaoLocal#listarListaValores(pe.com.edu.siaa.core.model.jpa.seguridad.ListaValores)
     */  
    @Override	 
    public List<ListaValores> listar(BaseSearch filtro) {
        var query = generarQuery(filtro, false);
        query.setFirstResult(filtro.getStartRow());
        query.setMaxResults(filtro.getOffSet());
        return query.getResultList();
    }   
   
    /**
     * Generar query lista ListaValores.
     *
     * @param ListaValores el listaValores
     * @param esContador el es contador
     * @return the query
     */
    private Query generarQuery(BaseSearch filtro, boolean esContador) {
        var parametros = new HashMap<String, Object>();
        var jpaql = new StringBuilder();
        if (esContador) {
            jpaql.append(" select count(o.idListaValores) from ListaValores o where 1=1 ");
        } else {
            jpaql.append(" select o from ListaValores o where 1=1 ");           
        }
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
	          jpaql.append(" and upper(o.nombre) like :search ");
	          parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
	    } else {
			if (!StringUtil.isNullOrEmpty(filtro.getEstado())) {
				jpaql.append(" and upper(o.estado) like :estado ");
				parametros.put("estado", "%" + filtro.getEstado().toUpperCase() + "%");
			}
		}
        if (!esContador) {
        	jpaql.append(" ORDER BY " + filtro.getSortFields() + " " + filtro.getSortDirections());
        }
       return createQuery(jpaql.toString(), parametros);
        
    }

    /* (non-Javadoc)
     * @see pe.com.edu.siaa.core.ejb.dao.seguridad.local.ListaValoresDaoLocal#contarListar{entity.getClassName()}(pe.com.edu.siaa.core.model.jpa.seguridad.ListaValores)
     */
	@Override
    public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
    }
    /* (non-Javadoc)
     * @see pe.com.edu.siaa.core.ejb.dao.seguridad.local.ListaValoresDaoLocal#generarIdListaValores()
     */
	 @Override
    public Long generarId() {
        var resultado = 1L;
        var query = createQuery("select max(o.idListaValores) from ListaValores o", null);
        var listLong =  query.getResultList();
        if (listLong != null && !listLong.isEmpty() && listLong.get(0) != null)  {
            Long ultimoIdGenerado = Long.valueOf(listLong.get(0).toString());
            if (!StringUtil.isNullOrEmpty(ultimoIdGenerado)) {
                resultado = resultado + ultimoIdGenerado;
            }
        }
        return resultado;
    }
}