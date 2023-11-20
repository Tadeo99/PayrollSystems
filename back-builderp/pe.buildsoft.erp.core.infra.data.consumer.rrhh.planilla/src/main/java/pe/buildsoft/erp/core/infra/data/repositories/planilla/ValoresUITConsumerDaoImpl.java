package pe.buildsoft.erp.core.infra.data.repositories.planilla;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import pe.buildsoft.erp.core.domain.entidades.planilla.ValoresUIT;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.ValoresUITConsumerDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericPlanillaBatchDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.state.EstadoGeneralState;
import pe.buildsoft.erp.core.infra.transversal.utilitario.CollectionUtil;

/**
 * La Class ValoresUITDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class ValoresUITConsumerDaoImpl extends GenericPlanillaBatchDAOImpl<String, ValoresUIT> implements ValoresUITConsumerDaoLocal {

	@Override
	public ValoresUIT findActivo(Long idAnhio) {
		var jpaql = new StringBuilder();
		var parametros = new HashMap<String, Object>();
		parametros.put("idAnhio", idAnhio);
		jpaql.append(" from ValoresUIT o  where 1=1 ");
		jpaql.append(" and o.idAnhio = :idAnhio ");
		var query = createQuery(jpaql.toString(), parametros);
		List<ValoresUIT> listaValoresUIT = query.getResultList();
		if (!CollectionUtil.isEmpty(listaValoresUIT)) {
			return listaValoresUIT.get(0);
		}
		return null;
	}

}