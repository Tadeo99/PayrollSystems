package pe.buildsoft.erp.core.infra.data.repositories.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.security.Privilegio;
import pe.buildsoft.erp.core.domain.interfaces.repositories.security.PrivilegioDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericSecurityDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.state.EstadoGeneralState;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * La Class PrivilegioDaoImpl.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue Apr 18 19:53:39 COT 2017
 * @since SIAA-CORE 2.1
 */
@Stateless
public class PrivilegioDaoImpl extends GenericSecurityDAOImpl<Long, Privilegio> implements PrivilegioDaoLocal {

	@Override
	public List<Privilegio> obtenerPrivilegioByUsuario(String idUsuario) {
		List<Privilegio> resultado = new ArrayList<>();
		var parametros = new HashMap<String, Object>();
		var ejecutarQuery = false;
		var jpaql = new StringBuilder();
		if (idUsuario != null) {
			ejecutarQuery = true;
		}
		jpaql.append(
				"select p.* from segur.PrivilegioGrupoUsuario gup , segur.Privilegio p where gup.estado=:estadoActivo and gup.idPrivilegio  = p.idPrivilegio and gup.idGrupoUsuario in (select idGrupoUsuario from segur.GrupoUsuarioUsuario gu where gu.estado=:estadoActivo and gu.idUsuario =:idUsuario) ");
		jpaql.append("union ");
		jpaql.append(
				"select p.* from segur.PrivilegioPersonalizado pp,segur.Privilegio p where pp.estado=:estadoActivo and pp.idPrivilegio = p.idPrivilegio and pp.idUsuario =:idUsuario ");
		jpaql.append(" order by nombre ");
		parametros.put("estadoActivo", EstadoGeneralState.ACTIVO.getKey());
		if (idUsuario != null) {
			parametros.put("idUsuario", idUsuario);
		}
		if (ejecutarQuery) {
			var query = createNativeQuery(jpaql.toString(), Privilegio.class, parametros);
			resultado = (List<Privilegio>) query.getResultList();
		}

		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.edu.siaa.core.ejb.dao.seguridad.local.PrivilegioDaoLocal#
	 * listarPrivilegio(pe.com.edu.siaa.core.model.jpa.seguridad.Privilegio)
	 */
	@Override
	public List<Privilegio> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	/**
	 * Generar query lista Privilegio.
	 *
	 * @param Privilegio el privilegio
	 * @param esContador el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idPrivilegio) from Privilegio o where 1=1 ");
		} else {
			jpaql.append(" select o from Privilegio o where 1=1 ");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.nombre) like :search ");
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		}
		if (!esContador) {
			jpaql.append(" ORDER BY "+ filtro.getSortFields() +" " + filtro.getSortDirections());
		}
		return createQuery(jpaql.toString(), parametros);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.edu.siaa.core.ejb.dao.seguridad.local.PrivilegioDaoLocal#contarListar{
	 * entity.getClassName()}(pe.com.edu.siaa.core.model.jpa.seguridad.Privilegio)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.edu.siaa.core.ejb.dao.seguridad.local.PrivilegioDaoLocal#
	 * generarIdPrivilegio()
	 */
	@Override
	public Long generarId() {
		var resultado = 1L;
		var query = createQuery("select max(o.idPrivilegio) from Privilegio o", null);
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