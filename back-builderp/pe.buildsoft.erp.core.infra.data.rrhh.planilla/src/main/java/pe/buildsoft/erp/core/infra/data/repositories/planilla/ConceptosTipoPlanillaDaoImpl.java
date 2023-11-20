package pe.buildsoft.erp.core.infra.data.repositories.planilla;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.planilla.ConceptosTipoPlanilla;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.ConceptosTipoPlanillaDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericPlanillaDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

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
public class ConceptosTipoPlanillaDaoImpl extends GenericPlanillaDAOImpl<String, ConceptosTipoPlanilla>
		implements ConceptosTipoPlanillaDaoLocal {

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
			jpaql.append(" select o from ConceptosTipoPlanilla o left join fetch   ");
			jpaql.append(" o.tipoPlanilla left join fetch o.conceptoPdt  where 1=1");
		}
		jpaql.append(" and o.tipoPlanilla.idTipoPlanilla =:idTipoPlanilla ");
		parametros.put("idTipoPlanilla", filtro.getId());

		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.descripcion) like :search ");
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		} 
		if (!StringUtil.isNullOrEmpty(filtro.getTipo())) {
			jpaql.append(" and o.conceptoPdt.tipo = :tipo ");
			parametros.put("tipo", filtro.getTipo());
		}
		if (!esContador) {
			jpaql.append(
					" ORDER BY REPLACE(REPLACE(REPLACE(REPLACE(o.conceptoPdt.tipo,'A','4') ,'T','3') ,'D','2') ,'I','1'), o.orden ");
		}
		return createQuery(jpaql.toString(), parametros);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.rrhh.planilla.dao.local.
	 * ConceptosTipoPlanillaDaoLocal#contarListar{entity.getClassName()}(pe.com.
	 * builderp.core.service.rrhh.planilla.model.jpa.ConceptosTipoPlanilla)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.rrhh.planilla.dao.local.
	 * ConceptosTipoPlanillaDaoLocal#generarIdConceptosTipoPlanilla()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

	@Override
	public List<ConceptosTipoPlanilla> listarByTipoPlanilla(String idTipoPlanilla) {
		var parametros = new HashMap<String, Object>();
		var ejecutarQuery = false;
		var jpaql = new StringBuilder();
		jpaql.append(
				"  from ConceptosTipoPlanilla  o left join fetch  o.tipoPlanilla left join fetch o.conceptoPdt where 1=1  ");
		if (StringUtil.isNotNullOrBlank(idTipoPlanilla)) {
			ejecutarQuery = true;
			jpaql.append(" and o.tipoPlanilla.idTipoPlanilla = :idTipoPlanilla ");
			parametros.put("idTipoPlanilla", idTipoPlanilla);
		}
		jpaql.append(
				" ORDER BY REPLACE(REPLACE(REPLACE(REPLACE(o.conceptoPdt.tipo,'A','4') ,'T','3') ,'D','2') ,'I','1'), o.orden ");
		if (ejecutarQuery) {
			var query = createQuery(jpaql.toString(), parametros);
			return query.getResultList();
		}
		return Collections.emptyList();
	}

}