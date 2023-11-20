package pe.buildsoft.erp.core.infra.data.repositories.planilla;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import pe.buildsoft.erp.core.domain.entidades.planilla.ConceptoFijosTrabajador;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.ConceptoTrabajadorConsumerDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericPlanillaBatchDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * La Class ConceptoTrabajadorDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class ConceptoTrabajadorConsumerDaoImpl extends GenericPlanillaBatchDAOImpl<String, ConceptoFijosTrabajador>
		implements ConceptoTrabajadorConsumerDaoLocal {

	@Override
	public Map<String, Map<String, BigDecimal>> conceptoTrajadorStaticoMap(Long idCategoriaTrabajador,
			List<String> listaIdPersonal) {
		var resultado = new HashMap<String, Map<String, BigDecimal>>();
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		jpaql.append("select o.idPersonal,o.conceptoPdt.codigo ,o.monto from ConceptoFijosTrabajador o where 1 = 1 ");
		if (!StringUtil.isNullOrEmpty(idCategoriaTrabajador)) {
			jpaql.append(" and o.idPersonal in (:listaIdPersonal)");
			// parametros.put("idCategoriaTrabajador", idCategoriaTrabajador);
			parametros.put("listaIdPersonal", listaIdPersonal);
		}
		var query = createQuery(jpaql.toString(), parametros);
		List<Object[]> resul = query.getResultList();
		for (var objects : resul) {
			var keyPersonal = (String) objects[0];
			var keyConcepto = (String) objects[1];
			if (!resultado.containsKey(keyPersonal)) {
				var conceptoMap = new HashMap<String, BigDecimal>();
				conceptoMap.put(keyConcepto, (BigDecimal) objects[2]);
				resultado.put(keyPersonal, conceptoMap);
			} else {
				var conceptoMap = resultado.get(keyPersonal);
				conceptoMap.put(keyConcepto, (BigDecimal) objects[2]);
				resultado.put(keyPersonal, conceptoMap);
			}
		}
		return resultado;
	}

}