package pe.buildsoft.erp.core.infra.data.repositories.matricula;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.matricula.DetMallaCurricular;
import pe.buildsoft.erp.core.domain.interfaces.repositories.matricula.DetMallaCurricularDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericMatriculaDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class DetMallaCurricularDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:55 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class DetMallaCurricularDaoImpl extends GenericMatriculaDAOImpl<String, DetMallaCurricular>
		implements DetMallaCurricularDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.matricula.dao.local.DetMallaCurricularDaoLocal#
	 * listarDetMallaCurricular(pe.com.builderp.core.service.matricula.model.jpa.
	 * DetMallaCurricular)
	 */
	@Override
	public List<DetMallaCurricular> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	/**
	 * Generar query lista DetMallaCurricular.
	 *
	 * @param DetMallaCurricularDTO el detMallaCurricular
	 * @param esContador            el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		boolean ejecutoBusqueda = false;
		if (esContador) {
			jpaql.append(" select count(o.idDetMallaCurricular) from DetMallaCurricular o where 1=1 ");
		} else {
			jpaql.append(" select o from DetMallaCurricular o left join fetch o.detMallaCurricularPadre  ");
			jpaql.append(" left join fetch o.mallaCurricular  where 1=1 ");
		}
		jpaql.append(" and o.mallaCurricular.idMallaCurricular =:idMallaCurricular ");
		parametros.put("idMallaCurricular", filtro.getIdPadreView());

		if (!StringUtil.isNullOrEmpty(filtro.getId())) {// comodin
			jpaql.append(" and o.detMallaCurricularPadre.idDetMallaCurricular = :idDetMallaCurricularDependencia ");
			parametros.put("idDetMallaCurricularDependencia", filtro.getId());
			ejecutoBusqueda = true;
		}

		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.descripcionCurso) like :search ");
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		}
		if (!ejecutoBusqueda) {
			jpaql.append(" and o.detMallaCurricularPadre.idDetMallaCurricular is null ");
		}
		if (!esContador) {
			jpaql.append(" ORDER BY o.codigoAsignatura ");
		}
		return createQuery(jpaql.toString(), parametros);
	}

	// agregando
	@Override
	public List<DetMallaCurricular> get(BaseSearch filtro) {
		List<DetMallaCurricular> resultado = new ArrayList<>();
		var parametros = new HashMap<String, Object>();
		var ejecutarQuery = false;
		var jpaql = new StringBuilder();
		jpaql.append("select dmc from DetMallaCurricular dmc left join fetch dmc.mallaCurricular where 1 = 1 ");
		if (filtro != null) {
			if (!StringUtil.isNullOrEmptyNumeric(filtro.getIdGrado())) {
				ejecutarQuery = true;
				jpaql.append(" and dmc.mallaCurricular.grado.idGrado = :idGrado ");
				parametros.put("idGrado", filtro.getIdGrado());
			}
			if (!StringUtil.isNullOrEmptyNumeric(filtro.getIdAnhio())) {
				ejecutarQuery = true;
				jpaql.append(" and dmc.mallaCurricular.anhio.idAnhio = :idAnhio ");
				parametros.put("idAnhio", filtro.getIdAnhio());
			}

			if (!StringUtil.isNullOrEmpty(filtro.getIdDetMallaCurricular())) {
				ejecutarQuery = true;
				jpaql.append(" and dmc.detMallaCurricularPadre.idDetMallaCurricular = :idDetMallaCurricular ");
				parametros.put("idDetMallaCurricular", filtro.getIdDetMallaCurricular());
			}

		}
		jpaql.append(" order by dmc.codigoAsignatura");
		if (ejecutarQuery) {
			var query = createQuery(jpaql.toString(), parametros);
			resultado = query.getResultList();
		}
		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.matricula.dao.local.DetMallaCurricularDaoLocal#
	 * contarListar{entity.getClassName()}(pe.com.builderp.core.service.matricula.
	 * model.jpa.DetMallaCurricularDTO)
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
	 * pe.com.builderp.core.service.matricula.dao.local.DetMallaCurricularDaoLocal#
	 * generarIdDetMallaCurricular()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

}