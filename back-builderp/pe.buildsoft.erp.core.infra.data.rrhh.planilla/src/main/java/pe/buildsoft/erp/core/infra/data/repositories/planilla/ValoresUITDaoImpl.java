package pe.buildsoft.erp.core.infra.data.repositories.planilla;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.planilla.ValoresUIT;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.ValoresUITDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericPlanillaDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.state.EstadoGeneralState;
import pe.buildsoft.erp.core.infra.transversal.utilitario.CollectionUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

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
public class ValoresUITDaoImpl extends GenericPlanillaDAOImpl<String, ValoresUIT> implements ValoresUITDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.rrhh.planilla.dao.local.ValoresUITDaoLocal#
	 * listarValoresUIT(pe.com.builderp.core.service.rrhh.planilla.model.jpa.
	 * ValoresUIT)
	 */
	@Override
	public List<ValoresUIT> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	/**
	 * Generar query lista ValoresUIT.
	 *
	 * @param ValoresUIT el valoresUIT
	 * @param esContador el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idUit) from ValoresUIT o where 1=1 ");
		} else {
			jpaql.append(" select o from ValoresUIT o where 1=1 ");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.baseLegal || o.valor ) like :search ");
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		}
		if (!esContador) {
			jpaql.append(" ORDER BY " + filtro.getSortFields() + " " + filtro.getSortDirections());
		}
		return createQuery(jpaql.toString(), parametros);
	}

	@Override
	public ValoresUIT find(ValoresUIT filtro) {
		ValoresUIT resultado = new ValoresUIT();
		var parametros = new HashMap<String, Object>();
		var ejecutarQuery = false;
		var jpaql = new StringBuilder();
		jpaql.append(" from ValoresUIT o  where 1=1 ");
		if (!StringUtil.isNullOrEmpty(filtro.getIdAnhio())) {
			ejecutarQuery = true;
			jpaql.append(" and o.idAnhio=:idAnhio");
			parametros.put("idAnhio", filtro.getIdAnhio());
		}
		if (ejecutarQuery) {
			var query = createQuery(jpaql.toString(), parametros);
			List<ValoresUIT> listaValoresUIT = query.getResultList();
			if (listaValoresUIT != null && !listaValoresUIT.isEmpty()) {
				resultado = listaValoresUIT.get(0);
			}
		}
		return resultado;
	}

	@Override
	public ValoresUIT findActivo() {
		var jpaql = new StringBuilder();
		var parametros = new HashMap<String, Object>();
		parametros.put("estadoActivo", EstadoGeneralState.ACTIVO.getKey());
		jpaql.append(" from ValoresUIT o  where 1=1 ");
		jpaql.append(
				" and exists  (select a.idAnhio from Anhio a where a.idAnhio = o.anhio  and a.estado =:estadoActivo ) ");
		var query = createQuery(jpaql.toString(), parametros);
		List<ValoresUIT> listaValoresUIT = query.getResultList();
		if (!CollectionUtil.isEmpty(listaValoresUIT)) {
			return listaValoresUIT.get(0);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.rrhh.planilla.dao.local.ValoresUITDaoLocal#
	 * contarListar{entity.getClassName()}(pe.com.builderp.core.service.rrhh.
	 * planilla.model.jpa.ValoresUIT)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.rrhh.planilla.dao.local.ValoresUITDaoLocal#
	 * generarIdValoresUIT()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

}