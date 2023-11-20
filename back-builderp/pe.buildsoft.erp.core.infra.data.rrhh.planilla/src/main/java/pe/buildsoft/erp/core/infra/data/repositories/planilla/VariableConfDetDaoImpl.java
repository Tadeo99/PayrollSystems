package pe.buildsoft.erp.core.infra.data.repositories.planilla;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.planilla.VariableConfDet;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.VariableConfDetDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericPlanillaDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class VariableConfDetDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class VariableConfDetDaoImpl extends GenericPlanillaDAOImpl<String, VariableConfDet>
		implements VariableConfDetDaoLocal {

	@Override
	public List<VariableConfDet> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		if (filtro.getOffSet() > 0) {
			query.setFirstResult(filtro.getStartRow());
			query.setMaxResults(filtro.getOffSet());
		}
		return query.getResultList();
	}

	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append("select count(o.idVariableConfDet) from VariableConfDet o  ");
		} else {
			jpaql.append("select o from VariableConfDet o left join fetch o.variableConf  ");
		}
		jpaql.append(" where 1 = 1");
		if (!StringUtil.isNullOrEmpty(filtro.getIdPadreView())) {
			jpaql.append(" and o.variableConf.idVariableConf = :id ");
			parametros.put("id", filtro.getIdPadreView());
		}
		if (!StringUtil.isNullOrEmpty(filtro.getId())) {
			jpaql.append(" and o.variableConf.idTipoPlanilla = :idTipoPlanilla ");
			parametros.put("idTipoPlanilla", filtro.getId());
		}
		if (!esContador) {
			// jpaql.append(" order by o.itemByRegimenPensionario.nombre asc ");
			jpaql.append(" ORDER BY o.orden asc ");
		}
		return createQuery(jpaql.toString(), parametros);
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