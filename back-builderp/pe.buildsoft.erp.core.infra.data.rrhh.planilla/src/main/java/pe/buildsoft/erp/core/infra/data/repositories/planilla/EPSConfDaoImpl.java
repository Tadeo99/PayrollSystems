package pe.buildsoft.erp.core.infra.data.repositories.planilla;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.planilla.EPSConf;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.EPSConfDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericPlanillaDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class EPSConfDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class EPSConfDaoImpl extends GenericPlanillaDAOImpl<String, EPSConf> implements EPSConfDaoLocal {

	@Override
	public List<EPSConf> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		if (query != null) {
			query.setFirstResult(filtro.getStartRow());
			query.setMaxResults(filtro.getOffSet());
			return query.getResultList();
		}
		return new ArrayList<>();
	}

	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		boolean ejecutarQuery = true;
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append("select count(o.idEPSConf) from EPSConf o  ");
		} else {
			jpaql.append("select o from EPSConf o  ");
		}
		jpaql.append(" where 1 = 1");
		if (filtro != null) {
			if (!StringUtil.isNullOrEmptyNumeric(filtro.getIdItemByEps())) {
				ejecutarQuery = true;
				jpaql.append(" and o.idItemByEps = :idItem ");
				parametros.put("idItem", filtro.getIdItemByEps());
			}
		}
		if (!esContador) {
			// jpaql.append(" order by o.itemByRegimenPensionario.nombre asc ");
			jpaql.append(" ORDER BY " + filtro.getSortFields() + " " + filtro.getSortDirections());
		}
		if (ejecutarQuery) {
			return createQuery(jpaql.toString(), parametros);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.rrhh.planilla.dao.local.
	 * ConceptoRegimenPensionarioDaoLocal#generarIdConceptoRegimenPensionario()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		if (query != null) {
			return ((Long) query.getSingleResult()).intValue();
		}
		return 0;
	}
}