package pe.buildsoft.erp.core.infra.data.repositories.planilla;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.planilla.VariableConfDet;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.VariableConfDetConsumerDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericPlanillaBatchDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * La Class VariableConfDetConsumerDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class VariableConfDetConsumerDaoImpl extends GenericPlanillaBatchDAOImpl<String, VariableConfDet>
		implements VariableConfDetConsumerDaoLocal {

	@Override
	public Map<String, Map<String, String>> listarMap(BaseSearch filtro) {
		var resultado = new LinkedHashMap<String, Map<String, String>>();
		var query = generarQuery(filtro, false);
		List<VariableConfDet> listaTmp = query.getResultList();
		for (var obj : listaTmp) {
			String key = obj.getVariableConf().getNombre();
			if (!resultado.containsKey(key)) {
				var value = new LinkedHashMap<String, String>();
				value.put(obj.getVariable(), obj.getFormula());
				resultado.put(key, value);
			} else {
				var value = resultado.get(key);
				value.put(obj.getVariable(), obj.getFormula());
			}
		}
		return resultado;
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
			jpaql.append(" ORDER BY o.variableConf.idVariableConf , o.orden asc ");
		}
		return createQuery(jpaql.toString(), parametros);
	}
}