package pe.buildsoft.erp.core.infra.data.repositories.planilla;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.planilla.TipoPlanilla;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.TipoPlanillaDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericPlanillaDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class TipoPlanillaDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class TipoPlanillaDaoImpl extends GenericPlanillaDAOImpl<String, TipoPlanilla> implements TipoPlanillaDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.rrhh.planilla.dao.local.TipoPlanillaDaoLocal#
	 * listarTipoPlanilla(pe.com.builderp.core.service.rrhh.planilla.model.jpa.
	 * TipoPlanilla)
	 */
	@Override
	public List<TipoPlanilla> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		if (filtro.getOffSet() > 0) {
			query.setFirstResult(filtro.getStartRow());
			query.setMaxResults(filtro.getOffSet());
		}
		return query.getResultList();
	}

	/**
	 * Generar query lista TipoPlanilla.
	 *
	 * @param TipoPlanilla el tipoPlanilla
	 * @param esContador   el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idTipoPlanilla) from TipoPlanilla o where 1=1 ");
		} else {
			jpaql.append(" select o from TipoPlanilla o  ");
			jpaql.append(" where 1=1 ");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.descripcion) like :search ");
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		} else {
			if (!StringUtil.isNullOrEmpty(filtro.getCodigo())) {
				jpaql.append(" and upper(o.codigo) like :codigo ");
				parametros.put("codigo", "%" + filtro.getCodigo().toUpperCase() + "%");
			}
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
	 * pe.com.builderp.core.service.rrhh.planilla.dao.local.TipoPlanillaDaoLocal#
	 * contarListar{entity.getClassName()}(pe.com.builderp.core.service.rrhh.
	 * planilla.model.jpa.TipoPlanilla)
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
	 * pe.com.builderp.core.service.rrhh.planilla.dao.local.TipoPlanillaDaoLocal#
	 * generarIdTipoPlanilla()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

}