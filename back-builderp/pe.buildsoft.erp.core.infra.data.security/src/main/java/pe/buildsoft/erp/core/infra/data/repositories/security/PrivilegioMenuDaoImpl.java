package pe.buildsoft.erp.core.infra.data.repositories.security;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.security.PrivilegioMenu;
import pe.buildsoft.erp.core.domain.interfaces.repositories.security.PrivilegioMenuDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericSecurityDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.CollectionUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class PrivilegioMenuDaoImpl.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue Apr 18 19:53:35 COT 2017
 * @since SIAA-CORE 2.1
 */
@Stateless
public class PrivilegioMenuDaoImpl extends GenericSecurityDAOImpl<String, PrivilegioMenu>
		implements PrivilegioMenuDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.edu.siaa.core.ejb.dao.seguridad.local.PrivilegioMenuDaoLocal#
	 * listarPrivilegioMenu(pe.com.edu.siaa.core.model.jpa.seguridad.PrivilegioMenu)
	 */
	@Override
	public List<PrivilegioMenu> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	/**
	 * Generar query lista PrivilegioMenu.
	 *
	 * @param PrivilegioMenu el privilegioMenu
	 * @param esContador     el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		if (CollectionUtil.isEmpty(filtro.getListaIdMenu())) {
			filtro.getListaIdMenu().add(-1L);
		}
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idPrivilegioMenu) from PrivilegioMenu o where 1=1 ");
		} else {
			jpaql.append(
					" select o from PrivilegioMenu o  left join fetch o.menu left join fetch o.privilegio where 1=1 ");
		}
		jpaql.append(" and o.menu.idMenu in (:listaIdMenu) ");
		parametros.put("listaIdMenu", filtro.getListaIdMenu());
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.privilegio.nombre) like :search ");
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		} else {
			if (!StringUtil.isNullOrEmpty(filtro.getEstado())) {
				jpaql.append(" and upper(o.estado) like :estado ");
				parametros.put("estado", "%" + filtro.getEstado().toUpperCase() + "%");
			}
		}
		if (!esContador) {
			jpaql.append(" ORDER BY 1 ");
		}
		return createQuery(jpaql.toString(), parametros);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.edu.siaa.core.ejb.dao.seguridad.local.PrivilegioMenuDaoLocal#
	 * contarListar{entity.getClassName()}(pe.com.edu.siaa.core.model.jpa.seguridad.
	 * PrivilegioMenu)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.edu.siaa.core.ejb.dao.seguridad.local.PrivilegioMenuDaoLocal#
	 * generarIdPrivilegioMenu()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

}