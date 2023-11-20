package pe.buildsoft.erp.core.infra.data.repositories.planilla;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import pe.buildsoft.erp.core.domain.entidades.planilla.DetallePersonalConcepto;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.DetallePersonalConceptoConsumerDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericPlanillaBatchDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.utilitario.CollectionUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * La Class DetallePersonalConceptoDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class DetallePersonalConceptoConsumerDaoImpl extends GenericPlanillaBatchDAOImpl<String, DetallePersonalConcepto>
		implements DetallePersonalConceptoConsumerDaoLocal {

	@Override
	public Map<String, Map<String, DetallePersonalConcepto>> listarMap(List<String> listaIdPersonal,
			String idTipoPlanilla, String idPeriodo) {
		Map<String, Map<String, DetallePersonalConcepto>> resultado = new HashMap<>();
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		var ejecutarQuery = false;
		jpaql.append(
				"from DetallePersonalConcepto o left join fetch o.conceptosTipoPlanilla ctp join fetch ctp.conceptoPdt  ");
		jpaql.append(" left join fetch o.personalConcepto pc  where 1 = 1  ");
		if (!CollectionUtil.isEmpty(listaIdPersonal)) {
			ejecutarQuery = true;
			jpaql.append(" and o.personalConcepto.idPersonal in(:idPersonal) ");
			parametros.put("idPersonal", listaIdPersonal);
		}
		if (StringUtil.isNotNullOrBlank(idTipoPlanilla)) {
			ejecutarQuery = true;
			jpaql.append(" and o.personalConcepto.tipoPlanilla.idTipoPlanilla =:idTipoPlanilla ");
			parametros.put("idTipoPlanilla", idTipoPlanilla);
		}
		if (StringUtil.isNotNullOrBlank(idPeriodo)) {
			ejecutarQuery = true;
			jpaql.append(" and o.personalConcepto.periodoPlanilla.idPeriodoPlanilla =:idPeriodo ");
			parametros.put("idPeriodo", idPeriodo);
		}
		jpaql.append(" and o.monto > 0 ");
		if (ejecutarQuery) {
			var query = createQuery(jpaql.toString(), parametros);
			List<DetallePersonalConcepto> listaTemp = query.getResultList();
			for (var obj : listaTemp) {
				var key = obj.getPersonalConcepto().getIdPersonal();
				var keyConcepto = obj.getConceptosTipoPlanilla().getIdConceptosTipoPlanilla();
				if (!resultado.containsKey(key)) {
					var objValue = new HashMap<String, DetallePersonalConcepto>();
					if (!objValue.containsKey(keyConcepto)) {
						objValue.put(keyConcepto, obj);
					}
					resultado.put(key, objValue);
				} else {
					var objValue = resultado.get(key);
					if (!objValue.containsKey(keyConcepto)) {
						objValue.put(keyConcepto, obj);
					}
				}

			}
		}
		return resultado;
	}

}