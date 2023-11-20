package pe.buildsoft.erp.core.infra.data.repositories.security;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.security.PrivilegioGrupoUsuario;
import pe.buildsoft.erp.core.domain.interfaces.repositories.security.PrivilegioGrupoUsuarioDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericSecurityDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class PrivilegioGrupoUsuarioDaoImpl.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue Apr 18 19:53:45 COT 2017
 * @since SIAA-CORE 2.1
 */
@Stateless
public class PrivilegioGrupoUsuarioDaoImpl extends GenericSecurityDAOImpl<String, PrivilegioGrupoUsuario>
		implements PrivilegioGrupoUsuarioDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.edu.siaa.core.ejb.dao.seguridad.local.PrivilegioGrupoUsuarioDaoLocal#
	 * listarPrivilegioGrupoUsuario(pe.com.edu.siaa.core.model.jpa.seguridad.
	 * PrivilegioGrupoUsuario)
	 */
	@Override
	public List<PrivilegioGrupoUsuario> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		if (filtro.getOffSet() > 0) {
			query.setFirstResult(filtro.getStartRow());
			query.setMaxResults(filtro.getOffSet());
		}
		return query.getResultList();
	}

	/**
	 * Generar query lista PrivilegioGrupoUsuario.
	 *
	 * @param PrivilegioGrupoUsuario el privilegioGrupoUsuario
	 * @param esContador             el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idPrivilegioGrupoUsuario) from PrivilegioGrupoUsuario o where 1=1 ");
		} else {
			jpaql.append(" select o from PrivilegioGrupoUsuario o left join fetch o.privilegio  where 1=1 ");
		}
		jpaql.append(" and o.grupoUsuario.idGrupoUsuario =:idGrupoUsuario ");

		if (!StringUtil.isNullOrEmpty(filtro.getEstado())) {
			jpaql.append(" and upper(o.estado) = :estado ");
			parametros.put("estado", filtro.getEstado().toUpperCase());
		}
		parametros.put("idGrupoUsuario", filtro.getId());
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.idPrivilegioGrupoUsuario) like :search ");
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
	 * @see
	 * pe.com.edu.siaa.core.ejb.dao.seguridad.local.PrivilegioGrupoUsuarioDaoLocal#
	 * contarListar{entity.getClassName()}(pe.com.edu.siaa.core.model.jpa.seguridad.
	 * PrivilegioGrupoUsuario)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.edu.siaa.core.ejb.dao.seguridad.local.PrivilegioGrupoUsuarioDaoLocal#
	 * generarIdPrivilegioGrupoUsuario()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

}