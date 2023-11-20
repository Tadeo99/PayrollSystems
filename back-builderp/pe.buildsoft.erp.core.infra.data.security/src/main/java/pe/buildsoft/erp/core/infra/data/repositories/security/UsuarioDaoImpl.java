package pe.buildsoft.erp.core.infra.data.repositories.security;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.security.Usuario;
import pe.buildsoft.erp.core.domain.interfaces.repositories.security.UsuarioDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericSecurityDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.state.EstadoGeneralState;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class UsuarioDaoImpl.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Dec 14 00:27:45 COT 2017
 * @since SIAA-CORE 2.1
 */
@Stateless
public class UsuarioDaoImpl extends  GenericSecurityDAOImpl<String, Usuario> implements UsuarioDaoLocal  {

	@Override
	public Usuario obtenerUsuarioByCodigoExterno(String codigoExterno)  {
		Usuario resultado = null;
		var parametros = new HashMap<String, Object>();
		parametros.put("codigoExterno", codigoExterno);
		
		var query = createQuery("from Usuario p left join fetch p.tipoUsuario  where   p.codigoExterno =:codigoExterno ", parametros);
		
		List<Usuario> listaUsuario = query.getResultList();
		if (listaUsuario != null && !listaUsuario.isEmpty()) {
			resultado = listaUsuario.get(0);
		}
		return resultado;
	}
	
	@Override
	public Usuario obtenerUsuarioByUserName(String userName)  {
		Usuario resultado = null;
		var parametros = new HashMap<String, Object>();
		parametros.put("userName", userName);
		
		var query = createQuery("from Usuario p left join fetch p.tipoUsuario  where   p.userName =:userName ", parametros);
		
		List<Usuario> listaUsuario = query.getResultList();
		if (listaUsuario != null && !listaUsuario.isEmpty()) {
			resultado = listaUsuario.get(0);
		}
		return resultado;
	}
	
	@Override
	public Usuario validarLogin(String userName,String userPassword)  {
		Usuario resultado = null;
		var parametros = new HashMap<String, Object>();
		parametros.put("userName", userName);
		parametros.put("userPassword", userPassword);
		parametros.put("estado", EstadoGeneralState.ACTIVO.getKey());
		var jpaql = new StringBuilder();
		jpaql.append("from Usuario p left join fetch p.tipoUsuario");
		jpaql.append(" where p.userName =:userName and p.userPassword =:userPassword and p.estado =:estado ");
		var query = createQuery(jpaql.toString(), parametros);
		List<Usuario> listaUsuario = query.getResultList();
		if (listaUsuario != null && !listaUsuario.isEmpty()) {
			resultado = listaUsuario.get(0);
		}
		return resultado;
	}
    /* (non-Javadoc)
     * @see pe.com.builderp.core.ejb.dao.seguridad.local.UsuarioDaoLocal#listarUsuario(pe.com.builderp.core.model.jpa.seguridad.Usuario)
     */  
    @Override	 
    public List<Usuario> listar(BaseSearch filtro) {
        var query = generarQuery(filtro, false);
        query.setFirstResult(filtro.getStartRow());
        query.setMaxResults(filtro.getOffSet());
        return query.getResultList();
    }   
   
    /**
     * Generar query lista Usuario.
     *
     * @param Usuario el usuario
     * @param esContador el es contador
     * @return the query
     */
    private Query generarQuery(BaseSearch filtro, boolean esContador) {
        var parametros = new HashMap<String, Object>();
        var jpaql = new StringBuilder();
        if (esContador) {
            jpaql.append(" select count(o.idUsuario) from Usuario o where 1=1 ");
        } else {
            jpaql.append(" select o from Usuario o left join fetch  o.tipoUsuario where 1=1 ");           
        }
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
	          jpaql.append(" and (TRANSLATE(UPPER(o.nombre || ' ' || o.apellidoPaterno || ' ' || o.apellidoMaterno ), :discriminaTildeMAC, :discriminaTildeMAT) like UPPER(translate(:search, :discriminaTildeMIC, :discriminaTildeMIT)) or (upper(o.codigoExterno) like :search) ");
			  jpaql.append(" or ( TRANSLATE(UPPER(o.userName), :discriminaTildeMAC, :discriminaTildeMAT) like UPPER(translate(:search, :discriminaTildeMIC, :discriminaTildeMIT)) ) ) ");
			  parametros.putAll(obtenerParametroDiscriminarTilde());	
		      parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
	    } 
        if (!esContador) {
        	jpaql.append(" ORDER BY " + filtro.getSortFields() + " " + filtro.getSortDirections());
        }
       return createQuery(jpaql.toString(), parametros);
        
    }

    /* (non-Javadoc)
     * @see pe.com.builderp.core.ejb.dao.seguridad.local.UsuarioDaoLocal#contarListar{entity.getClassName()}(pe.com.builderp.core.model.jpa.seguridad.Usuario)
     */
	@Override
    public int contar(BaseSearch filtro) {
        var query = generarQuery(filtro, true);
        return ((Long) query.getSingleResult()).intValue();
    }
    /* (non-Javadoc)
     * @see pe.com.builderp.core.ejb.dao.seguridad.local.UsuarioDaoLocal#generarIdUsuario()
     */
	 @Override
    public String generarId() {
        return UUIDUtil.generarElementUUID();
    }
   
}