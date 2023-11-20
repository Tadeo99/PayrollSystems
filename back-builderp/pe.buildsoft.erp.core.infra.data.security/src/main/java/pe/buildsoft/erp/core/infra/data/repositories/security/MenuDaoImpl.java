package pe.buildsoft.erp.core.infra.data.repositories.security;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.security.Menu;
import pe.buildsoft.erp.core.domain.interfaces.repositories.security.MenuDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericSecurityDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.state.EstadoGeneralState;
import pe.buildsoft.erp.core.infra.transversal.utilitario.CollectionUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ObjectUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.Utilidades;

/**
 * La Class MenuDaoImpl.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue Apr 18 19:53:43 COT 2017
 * @since SIAA-CORE 2.1
 */
@Stateless
public class MenuDaoImpl extends GenericSecurityDAOImpl<Long, Menu> implements MenuDaoLocal {

	@Override
	public List<Menu> obtenerMenuUsuario(String idUsuario) {
		List<Menu> resultado = new ArrayList<>();
		var parametros = new HashMap<String, Object>();
		var ejecutarQuery = false;
		var jpaql = new StringBuilder();
		if (StringUtil.isNotNullOrBlank(idUsuario)) {
			ejecutarQuery = true;
		}
		jpaql.append(Utilidades.obtenerWITHRecursivo() + " menuAccesos AS ");
		jpaql.append("( ");
		jpaql.append(
				"select m.* from segur.GrupoUsuarioMenu gum , segur.Menu m where gum.idMenu  = m.idMenu and gum.idGrupoUsuario in (select idGrupoUsuario from segur.GrupoUsuarioUsuario where idUsuario =:idUsuario and estado=:estadoActivo) ");
		jpaql.append(" and gum.estado=:estadoActivo ");
		jpaql.append("union ");
		jpaql.append(
				"select m.* from segur.MenuPersonalizado mp,segur.Menu m where mp.idMenu = m.idMenu and idUsuario =:idUsuario and  mp.estado=:estadoActivo and m.estado=:estadoActivo ");
		jpaql.append("union ALL ");
		jpaql.append("select  m.* from  segur.Menu m inner join menuAccesos ma ");
		jpaql.append("on m.idMenu = ma.idPadreMenu  ");
		jpaql.append(") ");
		jpaql.append("select distinct a.idMenu from menuAccesos a ");
		if (idUsuario != null) {
			parametros.put("idUsuario", idUsuario);
		}
		parametros.put("estadoActivo", EstadoGeneralState.ACTIVO.getKey() + "");
		if (ejecutarQuery) {
			var query = createNativeQuery(jpaql.toString(), parametros);
			List<BigDecimal> resultadoTemp = query.getResultList();
			if (!CollectionUtil.isEmpty(resultadoTemp)) {
				List<Long> listaIdMenu = new ArrayList<>();
				for (BigDecimal idMenu : resultadoTemp) {
					listaIdMenu.add(idMenu.longValue());
				}
				parametros = new HashMap<>();
				parametros.put("listaIdMenu", listaIdMenu);
				jpaql = new StringBuilder(
						"select o from Menu o left join fetch o.menuPadre left join fetch o.sistema  where o.idMenu in (:listaIdMenu) order by o.sistema.idSistema, o.idMenu");
				query = createQuery(jpaql.toString(), parametros);
				resultado = query.getResultList();
			}
		}

		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.edu.siaa.core.ejb.dao.seguridad.local.MenuDaoLocal#listarMenu(pe.com.
	 * edu.siaa.core.model.jpa.seguridad.Menu)
	 */
	@Override
	public List<Menu> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		if (filtro.getOffSet() > 0) {
			query.setFirstResult(filtro.getStartRow());
			query.setMaxResults(filtro.getOffSet());
		}
		return query.getResultList();
	}

	/**
	 * Generar query lista Menu.
	 *
	 * @param Menu       el menu
	 * @param esContador el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		boolean ejecutoBusqueda = false;
		if (esContador) {
			jpaql.append(" select count(o.idMenu) from Menu o where 1=1 ");
		} else {
			jpaql.append(" select o from Menu o left join fetch o.menuPadre  left join fetch o.sistema  where 1=1 ");
		}
//        if (!StringUtil.isNullOrEmptyNumeric(menu.getSistema().getIdSistema())) {//comodin
//        	jpaql.append(" and o.sistema.idSistema =:idSistema ");
//            parametros.put("idSistema", menu.getSistema().getIdSistema());
//        }
		if (!StringUtil.isNullOrEmptyNumeric(filtro.getIdSistema())) {
			jpaql.append(" and o.sistema.idSistema =:idSistema ");
			parametros.put("idSistema", filtro.getIdSistema());
		}

		if (!StringUtil.isNullOrEmptyNumeric(filtro.getId())) {// comodin
			jpaql.append(" and o.menuPadre.idMenu =:idMenuDependencia ");
			parametros.put("idMenuDependencia", ObjectUtil.objectToLong(filtro.getId()));
			ejecutoBusqueda = true;
		}
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.nombre) like :search ");
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		}

		if (!ejecutoBusqueda) {
			jpaql.append(" and o.menuPadre.idMenu is null ");
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
	 * pe.com.edu.siaa.core.ejb.dao.seguridad.local.MenuDaoLocal#contarListar{entity
	 * .getClassName()}(pe.com.edu.siaa.core.model.jpa.seguridad.Menu)
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
	 * pe.com.edu.siaa.core.ejb.dao.seguridad.local.MenuDaoLocal#generarIdMenu()
	 */
	@Override
	public Long generarId() {
		var resultado = 1L;
		var query = createQuery("select max(o.idMenu) from Menu o", null);
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