package pe.buildsoft.erp.core.infra.data.repositories.security;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.security.GrupoUsuario;
import pe.buildsoft.erp.core.domain.interfaces.repositories.security.GrupoUsuarioDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericSecurityDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * La Class GrupoUsuarioDaoImpl.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue Apr 18 19:53:34 COT 2017
 * @since SIAA-CORE 2.1
 */
@Stateless
public class GrupoUsuarioDaoImpl extends GenericSecurityDAOImpl<Long, GrupoUsuario> implements GrupoUsuarioDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.edu.siaa.core.ejb.dao.seguridad.local.GrupoUsuarioDaoLocal#
	 * listarGrupoUsuario(pe.com.edu.siaa.core.model.jpa.seguridad.GrupoUsuario)
	 */
	@Override
	public List<GrupoUsuario> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		if (filtro.getOffSet() > 0) {
			query.setFirstResult(filtro.getStartRow());
			query.setMaxResults(filtro.getOffSet());
		}
		return query.getResultList();
	}

	/**
	 * Generar query lista GrupoUsuario.
	 *
	 * @param GrupoUsuario el grupoUsuario
	 * @param esContador   el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idGrupoUsuario) from GrupoUsuario o where 1=1 ");
		} else {
			jpaql.append(" select o from GrupoUsuario o where 1=1 ");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.descripcion) like :search ");
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		}
		if (!esContador) {
			jpaql.append(" ORDER BY " + filtro.getSortFields() + " " + filtro.getSortDirections());
		}
		return createQuery(jpaql.toString(), parametros);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.edu.siaa.core.ejb.dao.seguridad.local.GrupoUsuarioDaoLocal#
	 * contarListar{entity.getClassName()}(pe.com.edu.siaa.core.model.jpa.seguridad.
	 * GrupoUsuario)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.edu.siaa.core.ejb.dao.seguridad.local.GrupoUsuarioDaoLocal#
	 * generarIdGrupoUsuario()
	 */
	@Override
	public Long generarId() {
		var resultado = 1L;
		var query = createQuery("select max(o.idGrupoUsuario) from GrupoUsuario o", null);
		var listLong = query.getResultList();
		if (listLong != null && !listLong.isEmpty() && listLong.get(0) != null) {
			Long ultimoIdGenerado = Long.valueOf(listLong.get(0).toString());
			if (!StringUtil.isNullOrEmpty(ultimoIdGenerado)) {
				resultado = resultado + ultimoIdGenerado;
			}
		}
		return resultado;
	}

}