package pe.buildsoft.erp.core.infra.data.repositories.security;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.security.MenuPersonalizado;
import pe.buildsoft.erp.core.domain.interfaces.repositories.security.MenuPersonalizadoDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericSecurityDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class MenuPersonalizadoDaoImpl.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue Apr 18 19:53:37 COT 2017
 * @since SIAA-CORE 2.1
 */
@Stateless
public class MenuPersonalizadoDaoImpl extends GenericSecurityDAOImpl<String, MenuPersonalizado>
		implements MenuPersonalizadoDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.edu.siaa.core.ejb.dao.seguridad.local.MenuPersonalizadoDaoLocal#
	 * listarMenuPersonalizado(pe.com.edu.siaa.core.model.jpa.seguridad.
	 * MenuPersonalizado)
	 */
	@Override
	public List<MenuPersonalizado> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	/**
	 * Generar query lista MenuPersonalizado.
	 *
	 * @param MenuPersonalizado el menuPersonalizado
	 * @param esContador        el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idMenuPersonalizado) from MenuPersonalizado o where 1=1 ");
		} else {
			jpaql.append(" select o from MenuPersonalizado o left join fetch o.menu  where 1=1 ");
		}
		jpaql.append(" and o.persona.idUsuario =:idUsuario ");
		parametros.put("idUsuario", filtro.getId() + "");
		if (!StringUtil.isNullOrEmpty(filtro.getEstado())) {
			jpaql.append(" and upper(o.estado) = :estado ");
			parametros.put("estado", filtro.getEstado().toUpperCase());
		}
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.idMenuPersonalizado) like :search ");
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		}
		if (!esContador) {
			jpaql.append(" ORDER BY 1 ");
		}
		return createQuery(jpaql.toString(), parametros);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.edu.siaa.core.ejb.dao.seguridad.local.MenuPersonalizadoDaoLocal#
	 * contarListar{entity.getClassName()}(pe.com.edu.siaa.core.model.jpa.seguridad.
	 * MenuPersonalizado)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.edu.siaa.core.ejb.dao.seguridad.local.MenuPersonalizadoDaoLocal#
	 * generarIdMenuPersonalizado()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

}