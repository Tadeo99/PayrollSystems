package pe.buildsoft.erp.core.infra.data.repositories.planilla;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.planilla.ConceptoPdt;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.ConceptoPdtDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericPlanillaDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class ConceptoPdtDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class ConceptoPdtDaoImpl extends GenericPlanillaDAOImpl<String, ConceptoPdt> implements ConceptoPdtDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.rrhh.planilla.dao.local.ConceptoPdtDaoLocal#
	 * listarConceptoPdt(pe.com.builderp.core.service.rrhh.planilla.model.jpa.
	 * ConceptoPdt)
	 */
	@Override
	public List<ConceptoPdt> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	@Override
	public List<ConceptoPdt> obtenerFormulaConceptoPdt() {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		jpaql.append(" select o from ConceptoPdt  o left join fetch o.conceptoPdtPadre");
		jpaql.append("  where 1=1 and formula is not  null ");
		return createQuery(jpaql.toString(), parametros).getResultList();
	}

	/**
	 * Generar query lista ConceptoPdt.
	 *
	 * @param ConceptoPdt el conceptoPdt
	 * @param esContador  el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		boolean ejecutoBusqueda = false;
		if (esContador) {
			jpaql.append(" select count(o.idConceptoPdt) from ConceptoPdt o where 1=1 ");
		} else {
			jpaql.append(" select o from ConceptoPdt o left join fetch o.conceptoPdtPadre where 1=1 ");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getId())) {// comodin
			jpaql.append(" and o.conceptoPdtPadre.idConceptoPdt = :idConceptoPdtDependencia ");
			parametros.put("idConceptoPdtDependencia", filtro.getId());
			ejecutoBusqueda = true;
		}
		if (!StringUtil.isNullOrEmpty(filtro.getTipo())) {
			jpaql.append(" and o.tipo = :tipo ");
			parametros.put("tipo", filtro.getTipo());
		}
		if (!StringUtil.isNullOrEmpty(filtro.getIdPadreView())) {
			jpaql.append(" and o.visible = :visible ");
			parametros.put("visible", filtro.getIdPadreView());
		}
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.descripcion) like :search ");
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		}
		if (!ejecutoBusqueda) {
			jpaql.append(" and o.conceptoPdtPadre.idConceptoPdt is null ");
		}
		if (!esContador) {
			jpaql.append(" ORDER BY o." + filtro.getSortFields() + " " + filtro.getSortDirections());
		}
		return createQuery(jpaql.toString(), parametros);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.rrhh.planilla.dao.local.ConceptoPdtDaoLocal#
	 * contarListar{entity.getClassName()}(pe.com.builderp.core.service.rrhh.
	 * planilla.model.jpa.ConceptoPdt)
	 */
	@Override
	public int contar(BaseSearch conceptoPdt) {
		var query = generarQuery(conceptoPdt, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.rrhh.planilla.dao.local.ConceptoPdtDaoLocal#
	 * generarIdConceptoPdt()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

	@Override
	public ConceptoPdt find(ConceptoPdt filtro) {
		ConceptoPdt resultado = new ConceptoPdt();
		var parametros = new HashMap<String, Object>();
		var ejecutarQuery = false;
		var jpaql = new StringBuilder();
		jpaql.append(" from ConceptoPdt o  where 1=1 ");
		if (!StringUtil.isNullOrEmpty(filtro.getCodigo())) {
			ejecutarQuery = true;
			jpaql.append(" and upper(o.codigo)=upper(:codigo)");
			parametros.put("codigo", filtro.getCodigo());
		}
		if (ejecutarQuery) {
			var query = createQuery(jpaql.toString(), parametros);
			List<ConceptoPdt> listaObj = query.getResultList();
			if (listaObj != null && !listaObj.isEmpty()) {
				resultado = listaObj.get(0);
			}
		}
		return resultado;
	}
}