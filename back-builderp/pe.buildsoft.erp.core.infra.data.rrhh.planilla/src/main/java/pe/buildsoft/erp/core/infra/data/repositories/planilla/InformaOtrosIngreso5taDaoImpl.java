package pe.buildsoft.erp.core.infra.data.repositories.planilla;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.planilla.InformaOtrosIngreso5ta;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.InformaOtrosIngreso5taDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericPlanillaDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

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
public class InformaOtrosIngreso5taDaoImpl extends GenericPlanillaDAOImpl<String, InformaOtrosIngreso5ta>
		implements InformaOtrosIngreso5taDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.rrhh.dao.local.InformaOtrosIngreso5taDaoLocal#
	 * listarInformaOtrosIngreso5ta(pe.com.builderp.core.service.rrhh.model.jpa.
	 * InformaOtrosIngreso5ta)
	 */
	@Override
	public List<InformaOtrosIngreso5ta> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	/**
	 * Generar query lista InformaOtrosIngreso5ta.
	 *
	 * @param InformaOtrosIngreso5ta el informaOtrosIngreso5ta
	 * @param esContador             el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idInformaOtrosIngreso5ta) from InformaOtrosIngreso5ta o where 1=1 ");
		} else {
			jpaql.append(" select o from InformaOtrosIngreso5ta o where 1=1 ");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.idempresa) like :search ");
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getId())) {
			jpaql.append(" and o.idPersonal =:idPersonal ");
			parametros.put("idPersonal", filtro.getId());
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
	 * pe.com.builderp.core.service.rrhh.dao.local.InformaOtrosIngreso5taDaoLocal#
	 * contarListar{entity.getClassName()}(pe.com.builderp.core.service.rrhh.model.
	 * jpa.InformaOtrosIngreso5ta)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.rrhh.dao.local.InformaOtrosIngreso5taDaoLocal#
	 * generarIdInformaOtrosIngreso5ta()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

}