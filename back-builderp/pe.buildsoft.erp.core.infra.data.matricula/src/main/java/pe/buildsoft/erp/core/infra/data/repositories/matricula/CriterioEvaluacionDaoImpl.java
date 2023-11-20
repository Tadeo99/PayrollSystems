package pe.buildsoft.erp.core.infra.data.repositories.matricula;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.matricula.CriterioEvaluacion;
import pe.buildsoft.erp.core.domain.interfaces.repositories.matricula.CriterioEvaluacionDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericMatriculaDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class CriterioEvaluacionDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Jun 10 00:13:45 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class CriterioEvaluacionDaoImpl extends GenericMatriculaDAOImpl<String, CriterioEvaluacion>
		implements CriterioEvaluacionDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.matricula.dao.local.CriterioEvaluacionDaoLocal#
	 * listarCriterioEvaluacion(pe.com.builderp.core.service.matricula.model.jpa.
	 * CriterioEvaluacion)
	 */
	@Override
	public List<CriterioEvaluacion> listar(String estado, String idDetMallaCurricular,
			String idCriterioEvaluacionPadre) {
		var parametros = new HashMap<String, Object>();
		var ejecutarQuery = false;
		var jpaql = new StringBuilder();
		jpaql.append("from CriterioEvaluacion cn left join fetch cn.detMallaCurricular where 1 = 1 ");

		if (StringUtil.isNotNullOrBlank(estado)) {
			ejecutarQuery = true;
			jpaql.append(" and cn.estado = :estado ");
			parametros.put("estado", estado);
		}
		if (StringUtil.isNotNullOrBlank(idDetMallaCurricular)) {
			ejecutarQuery = true;
			jpaql.append(" and cn.detMallaCurricular.idDetMallaCurricular = :idDetMallaCurricular ");
			parametros.put("idDetMallaCurricular", idDetMallaCurricular);
		}
		if (StringUtil.isNotNullOrBlank(idCriterioEvaluacionPadre)) {
			ejecutarQuery = true;
			jpaql.append(" and cn.criterioEvaluacionPadre.idCriterioEvaluacion = :idCriterioEvaluacion ");
			parametros.put("idCriterioEvaluacion", idCriterioEvaluacionPadre);
		}

		jpaql.append(" order by cn.nroOrden asc ");
		if (ejecutarQuery) {
			var query = createQuery(jpaql.toString(), parametros);
			return query.getResultList();
		}
		return Collections.emptyList();
	}

	@Override
	public List<CriterioEvaluacion> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	/**
	 * Generar query lista CriterioEvaluacion.
	 *
	 * @param CriterioEvaluacionDTO el criterioEvaluacion
	 * @param esContador            el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		boolean ejecutoBusqueda = false;
		if (esContador) {
			jpaql.append(" select count(o.idCriterioEvaluacion) from CriterioEvaluacion o where 1=1 ");
		} else {
			jpaql.append(" select o from CriterioEvaluacion o where 1=1 ");
		}

		jpaql.append(" and o.detMallaCurricular.idDetMallaCurricular = :idDetMallaCurricular ");
		parametros.put("idDetMallaCurricular", filtro.getIdPadreView());

		if (!StringUtil.isNullOrEmpty(filtro.getId())) {// comodin
			jpaql.append(" and o.criterioEvaluacionPadre.idCriterioEvaluacion = :idCriterioEvaluacionDependencia ");
			parametros.put("idCriterioEvaluacionDependencia", filtro.getId());
			ejecutoBusqueda = true;
		}

		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.nombre) like :search ");
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		}
		if (!ejecutoBusqueda) {
			jpaql.append(" and o.criterioEvaluacionPadre.idCriterioEvaluacion is null ");
		}
		if (!esContador) {
			jpaql.append(" ORDER BY o.nroOrden asc ");
		}
		return createQuery(jpaql.toString(), parametros);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.matricula.dao.local.CriterioEvaluacionDaoLocal#
	 * contarListar{entity.getClassName()}(pe.com.builderp.core.service.matricula.
	 * model.jpa.CriterioEvaluacionDTO)
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
	 * pe.com.builderp.core.service.matricula.dao.local.CriterioEvaluacionDaoLocal#
	 * generarIdCriterioEvaluacion()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

}