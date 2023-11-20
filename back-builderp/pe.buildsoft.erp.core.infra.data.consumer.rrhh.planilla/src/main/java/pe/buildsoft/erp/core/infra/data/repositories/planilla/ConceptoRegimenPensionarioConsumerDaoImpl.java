package pe.buildsoft.erp.core.infra.data.repositories.planilla;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import pe.buildsoft.erp.core.domain.entidades.planilla.ConceptoRegimenPensionario;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.ConceptoRegimenPensionarioConsumerDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericPlanillaBatchDAOImpl;

/**
 * La Class ConceptoRegimenPensionarioDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class ConceptoRegimenPensionarioConsumerDaoImpl
		extends GenericPlanillaBatchDAOImpl<String, ConceptoRegimenPensionario>
		implements ConceptoRegimenPensionarioConsumerDaoLocal {

	@Override
	public Map<Long, ConceptoRegimenPensionario> listarMap(Long idMesDevengado, Long anhio) {
		var resultado = new HashMap<Long, ConceptoRegimenPensionario>();
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		jpaql.append("select o from ConceptoRegimenPensionario o  where 1 = 1 ");
		if (anhio != null && anhio > 0) {
			jpaql.append(" and o.idAnhio =:idAnhio ");
			parametros.put("idAnhio", anhio);
		}
		if (idMesDevengado != null && idMesDevengado > 0) {
			jpaql.append(" and o.idItemByMesByDevengue = :idItem ");
			parametros.put("idItem", idMesDevengado);
		}
		jpaql.append(" order by o.idAnhio , o.idItemByMesByDevengue ");
		List<ConceptoRegimenPensionario> listaTmp = createQuery(jpaql.toString(), parametros).getResultList();
		for (var obj : listaTmp) {
			resultado.put(obj.getIdItemByRegimenPensionario(), obj);
		}
		return resultado;
	}
}