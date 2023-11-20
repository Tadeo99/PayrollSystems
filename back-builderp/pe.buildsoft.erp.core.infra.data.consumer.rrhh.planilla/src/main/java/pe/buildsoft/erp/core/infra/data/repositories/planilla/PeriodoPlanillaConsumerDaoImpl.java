package pe.buildsoft.erp.core.infra.data.repositories.planilla;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.planilla.PeriodoPlanilla;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.PeriodoPlanillaConsumerDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericPlanillaBatchDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ObjectUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * La Class PeriodoPlanillaConsumerDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class PeriodoPlanillaConsumerDaoImpl extends GenericPlanillaBatchDAOImpl<String, PeriodoPlanilla>
		implements PeriodoPlanillaConsumerDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.rrhh.planilla.dao.local.PeriodoPlanillaDaoLocal#
	 * listarPeriodoPlanilla(pe.com.builderp.core.service.rrhh.planilla.model.jpa.
	 * PeriodoPlanilla)
	 */
	@Override
	public List<PeriodoPlanilla> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		if (filtro.getOffSet() > 0) {
			query.setFirstResult(filtro.getStartRow());
			query.setMaxResults(filtro.getOffSet());
		}
		return query.getResultList();
	}

	/**
	 * Generar query lista PeriodoPlanilla.
	 *
	 * @param PeriodoPlanilla el periodoPlanilla
	 * @param esContador      el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idPeriodoPlanilla) from PeriodoPlanilla o where 1=1 ");
		} else {
			jpaql.append(" select o from PeriodoPlanilla o where 1=1 ");
		}
		if (!StringUtil.isNullOrEmptyNumeric(filtro.getId())) {
			jpaql.append(" and o.idAnhio=:idAnhio ");
			parametros.put("idAnhio", ObjectUtil.objectToLong(filtro.getId()));
		}
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.descripcion) like :search ");
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		}
		if (!esContador) {
			jpaql.append(" ORDER BY " + filtro.getSortFields() + " " + filtro.getSortDirections());
		}
		return createQuery(jpaql.toString(), parametros);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.rrhh.planilla.dao.local.PeriodoPlanillaDaoLocal#
	 * contarListar{entity.getClassName()}(pe.com.builderp.core.service.rrhh.
	 * planilla.model.jpa.PeriodoPlanilla)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

}