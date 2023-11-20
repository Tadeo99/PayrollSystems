package pe.buildsoft.erp.core.infra.data.repositories.security;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.security.UsuarioEntidad;
import pe.buildsoft.erp.core.domain.interfaces.repositories.security.UsuarioEntidadDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericSecurityDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.state.EstadoGeneralState;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class UsuarioEntidadDaoImpl.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue Apr 18 19:53:47 COT 2017
 * @since SIAA-CORE 2.1
 */
@Stateless
public class UsuarioEntidadDaoImpl extends GenericSecurityDAOImpl<String, UsuarioEntidad>
		implements UsuarioEntidadDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.edu.siaa.core.ejb.dao.seguridad.local.UsuarioEntidadDaoLocal#
	 * listarUsuarioEntidad(pe.com.edu.siaa.core.model.jpa.seguridad.UsuarioEntidad)
	 */
	@Override
	public List<UsuarioEntidad> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		if (filtro.getOffSet() > 0) {
			query.setFirstResult(filtro.getStartRow());
			query.setMaxResults(filtro.getOffSet());
		}
		return query.getResultList();
	}

	/**
	 * Generar query lista UsuarioEntidad.
	 *
	 * @param UsuarioEntidad el usuarioEntidad
	 * @param esContador     el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idUsuarioEntidad) from UsuarioEntidad o where 1=1 ");
		} else {
			jpaql.append(" select o from UsuarioEntidad o  left join fetch o.entidad where 1=1 ");
		}
		jpaql.append(" and o.usuario.idUsuario = :idUsuario ");
		parametros.put("idUsuario", filtro.getId() + "");

		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.entidad.nombre) like :search ");
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		} else {
			/*
			 * if (!StringUtil.isNullOrEmpty(filtro.getUsuario()) &&
			 * !StringUtil.isNullOrEmpty(filtro.getUsuario().getIdUsuario())) {
			 * jpaql.append(" and upper(o.usuario.idUsuario) like :idUsuario ");
			 * parametros.put("idUsuario", "%" +
			 * filtro.getUsuario().getIdUsuario().toUpperCase() + "%"); } if
			 * (!StringUtil.isNullOrEmpty(filtro.getEstado())) {
			 * jpaql.append(" and upper(o.estado) like :estado "); parametros.put("estado",
			 * "%" + filtro.getEstado().toUpperCase() + "%"); }
			 */
		}
		if (!esContador) {
			jpaql.append(" ORDER BY 1 ");
		}
		return createQuery(jpaql.toString(), parametros);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.edu.siaa.core.ejb.dao.seguridad.local.UsuarioEntidadDaoLocal#
	 * contarListar{entity.getClassName()}(pe.com.edu.siaa.core.model.jpa.seguridad.
	 * UsuarioEntidad)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.edu.siaa.core.ejb.dao.seguridad.local.UsuarioEntidadDaoLocal#
	 * generarIdUsuarioEntidad()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

	@Override
	public boolean updateEstadoInactivo(String idUsuario) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		jpaql.append(" update UsuarioEntidad o set o.estado =:estado  where o.usuario.idUsuario =:idUsuario ");
		parametros.put("idUsuario", idUsuario);
		parametros.put("estado", EstadoGeneralState.INACTIVO.getKey());
		var query = createQuery(jpaql.toString(), parametros);
		return query.executeUpdate() > 0;
	}

}