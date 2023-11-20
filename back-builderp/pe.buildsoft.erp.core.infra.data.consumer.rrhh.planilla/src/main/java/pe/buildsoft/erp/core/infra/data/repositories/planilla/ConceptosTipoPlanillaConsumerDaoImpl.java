package pe.buildsoft.erp.core.infra.data.repositories.planilla;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.planilla.ConceptosTipoPlanilla;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.ConceptosTipoPlanillaConsumerDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericPlanillaBatchDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * La Class ConceptosTipoPlanillaDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class ConceptosTipoPlanillaConsumerDaoImpl extends GenericPlanillaBatchDAOImpl<String, ConceptosTipoPlanilla>
		implements ConceptosTipoPlanillaConsumerDaoLocal {

	@Override
	public List<ConceptosTipoPlanilla> getFormulaConceptosTipo() {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		jpaql.append(
				"select o from ConceptosTipoPlanilla o left join fetch  o.tipoPlanilla left join fetch o.conceptoPdt  ");
		jpaql.append("  where 1 = 1 and o.formula is not null ");
		return createQuery(jpaql.toString(), parametros).getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.rrhh.planilla.dao.local.
	 * ConceptosTipoPlanillaDaoLocal#listarConceptosTipoPlanilla(pe.com.builderp.
	 * core.service.rrhh.planilla.model.jpa.ConceptosTipoPlanilla)
	 */
	@Override
	public List<ConceptosTipoPlanilla> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		if (filtro.getOffSet() > 0) {
			query.setFirstResult(filtro.getStartRow());
			query.setMaxResults(filtro.getOffSet());
		}
		return query.getResultList();
	}

	/**
	 * Generar query lista ConceptosTipoPlanilla.
	 *
	 * @param ConceptosTipoPlanilla el conceptosTipoPlanilla
	 * @param esContador            el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append("select count(o.idConceptosTipoPlanilla) from ConceptosTipoPlanilla o where 1=1 ");
		} else {
			jpaql.append(
					" select o from ConceptosTipoPlanilla o left join fetch  o.tipoPlanilla left join fetch o.conceptoPdt  where 1=1 ");
		}
		jpaql.append(" and o.tipoPlanilla.idTipoPlanilla =:idTipoPlanilla ");
		parametros.put("idTipoPlanilla", filtro.getId());

		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.descripcion) like :search ");
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		}
		if (!esContador) {
			jpaql.append(
					" ORDER BY REPLACE(REPLACE(REPLACE(REPLACE(o.conceptoPdt.tipo,'A','4') ,'T','3') ,'D','2') ,'I','1'), o.orden ");
		}
		return createQuery(jpaql.toString(), parametros);
	}

}