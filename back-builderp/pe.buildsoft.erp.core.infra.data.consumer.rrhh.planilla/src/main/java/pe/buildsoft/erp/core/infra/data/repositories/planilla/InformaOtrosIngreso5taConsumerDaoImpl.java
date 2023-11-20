package pe.buildsoft.erp.core.infra.data.repositories.planilla;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import pe.buildsoft.erp.core.domain.entidades.planilla.InformaOtrosIngreso5ta;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.InformaOtrosIngreso5taConsumerDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericPlanillaBatchDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * La Class InformaOtrosIngreso5taDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Wed Jul 22 00:55:19 COT 2020
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class InformaOtrosIngreso5taConsumerDaoImpl extends GenericPlanillaBatchDAOImpl<String, InformaOtrosIngreso5ta>
		implements InformaOtrosIngreso5taConsumerDaoLocal {

	@Override
	public Map<String, BigDecimal> listarMap(Long idAnhio, List<String> listaIdPersonal) {
		var resultado = new HashMap<String, BigDecimal>();
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		jpaql.append("select o from InformaOtrosIngreso5ta o  where 1 = 1 ");
		if (!StringUtil.isNullOrEmpty(idAnhio)) {
			jpaql.append(" and o.idPersonal in (:listaIdPersonal)");
			parametros.put("listaIdPersonal", listaIdPersonal);
		}
		jpaql.append(" and o.idAnhio = :idAnhio");
		parametros.put("idAnhio", idAnhio);
		var query = createQuery(jpaql.toString(), parametros);
		List<InformaOtrosIngreso5ta> resul = query.getResultList();
		for (var objData : resul) {
			String key = objData.getIdPersonal();
			if (!resultado.containsKey(key)) {
				resultado.put(key, objData.getImporte());
			} else {
				resultado.put(key, objData.getImporte());
			}
		}

		return resultado;
	}

}