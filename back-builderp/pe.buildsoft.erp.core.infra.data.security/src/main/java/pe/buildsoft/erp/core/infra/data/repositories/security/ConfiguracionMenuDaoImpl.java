package pe.buildsoft.erp.core.infra.data.repositories.security;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.security.ConfiguracionMenu;
import pe.buildsoft.erp.core.domain.interfaces.repositories.security.ConfiguracionMenuDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericSecurityDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class ConfiguracionMenuDaoImpl.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue Apr 18 19:53:44 COT 2017
 * @since SIAA-CORE 2.1
 */
@Stateless
public class ConfiguracionMenuDaoImpl extends GenericSecurityDAOImpl<String, ConfiguracionMenu>
		implements ConfiguracionMenuDaoLocal {

	@Override
	public List<ConfiguracionMenu> obtenerConfiguracionMenu(Long idMenu) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		jpaql.append(
				" select o from ConfiguracionMenu o left join fetch o.properties pro where 1 = 1 ");
		if (idMenu != null && StringUtil.isNotNullOrBlank(idMenu)) {
			jpaql.append(" and o.menu.idMenu = :idMenu ");
			parametros.put("idMenu", idMenu);
		}
		jpaql.append(" order by o.idItemByComponente, pro.descripcion ");
		var query = createQuery(jpaql.toString(), parametros);
		return query.getResultList();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.edu.siaa.core.ejb.dao.seguridad.local.ConfiguracionMenuDaoLocal#
	 * listarConfiguracionMenu(pe.com.edu.siaa.core.model.jpa.seguridad.
	 * ConfiguracionMenu)
	 */
	@Override
	public List<ConfiguracionMenu> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	/**
	 * Generar query lista ConfiguracionMenu.
	 *
	 * @param ConfiguracionMenu el configuracionMenu
	 * @param esContador        el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idConfiguracionMenu) from ConfiguracionMenu o where 1=1 ");
		} else {
			jpaql.append(" select o from ConfiguracionMenu o where 1=1 ");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.idConfiguracionMenu) like :search ");
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
	 * @see pe.com.edu.siaa.core.ejb.dao.seguridad.local.ConfiguracionMenuDaoLocal#
	 * contarListar{entity.getClassName()}(pe.com.edu.siaa.core.model.jpa.seguridad.
	 * ConfiguracionMenu)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.edu.siaa.core.ejb.dao.seguridad.local.ConfiguracionMenuDaoLocal#
	 * generarIdConfiguracionMenu()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

}