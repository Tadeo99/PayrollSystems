package pe.buildsoft.erp.core.infra.data.repositories.matricula;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.matricula.MallaCurricular;
import pe.buildsoft.erp.core.domain.interfaces.repositories.matricula.MallaCurricularDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericMatriculaDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ObjectUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class MallaCurricularDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:54 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class MallaCurricularDaoImpl extends GenericMatriculaDAOImpl<String, MallaCurricular>
		implements MallaCurricularDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.matricula.dao.local.MallaCurricularDaoLocal#
	 * listarMallaCurricular(pe.com.builderp.core.service.matricula.model.jpa.
	 * MallaCurricular)
	 */
	@Override
	public List<MallaCurricular> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	/**
	 * Generar query lista MallaCurricular.
	 *
	 * @param MallaCurricularDTO el mallaCurricular
	 * @param esContador         el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idMallaCurricular) from MallaCurricular o where 1=1 ");
		} else {
			jpaql.append(
					" select o from MallaCurricular o  left join fetch o.anhio left join fetch o.grado g left join fetch g.itemByNivel  where 1=1 ");
		}

		if (!StringUtil.isNullOrEmpty(filtro.getId())) {
			jpaql.append(" and o.anhio.idAnhio = :idAnhio ");
			parametros.put("idAnhio", ObjectUtil.objectToLong(filtro.getId()));
		}

		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.grado.nombre) like :search ");
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		}
		if (!esContador) {
			jpaql.append(" ORDER BY o.grado.itemByNivel.idItem,o.grado.codigo ");
		}
		return createQuery(jpaql.toString(), parametros);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.matricula.dao.local.MallaCurricularDaoLocal#
	 * contarListar{entity.getClassName()}(pe.com.builderp.core.service.matricula.
	 * model.jpa.MallaCurricularDTO)
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
	 * pe.com.builderp.core.service.matricula.dao.local.MallaCurricularDaoLocal#
	 * generarIdMallaCurricular()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

}