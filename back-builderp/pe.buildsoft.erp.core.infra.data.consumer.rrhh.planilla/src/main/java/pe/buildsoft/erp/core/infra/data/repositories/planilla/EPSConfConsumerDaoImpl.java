package pe.buildsoft.erp.core.infra.data.repositories.planilla;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.planilla.EPSConf;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.EPSConfConsumerDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericPlanillaBatchDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

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
public class EPSConfConsumerDaoImpl extends GenericPlanillaBatchDAOImpl<String, EPSConf>
		implements EPSConfConsumerDaoLocal {

	@Override
	public Map<Long, EPSConf> listarMap(BaseSearch filtro) {
		var resultado = new HashMap<Long, EPSConf>();
		var query = generarQuery(filtro, false);
		List<EPSConf> listaTemp = query.getResultList();
		for (EPSConf epsConf : listaTemp) {
			resultado.put(epsConf.getIdItemByEps(), epsConf);
		}
		return resultado;
	}

	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append("select count(o.idEPSConf) from EPSConf o  ");
		} else {
			jpaql.append("select o from EPSConf o  ");
		}
		jpaql.append(" where 1 = 1");
		if (filtro != null) {
			if (!StringUtil.isNullOrEmptyNumeric(filtro.getIdItemByEps())) {
				jpaql.append(" and o.idItemByEps = :idItem ");
				parametros.put("idItem", filtro.getIdItemByEps());
			}
		}
		return createQuery(jpaql.toString(), parametros);
	}

}