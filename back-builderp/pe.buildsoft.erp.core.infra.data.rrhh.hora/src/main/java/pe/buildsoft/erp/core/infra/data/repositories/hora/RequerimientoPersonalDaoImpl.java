package pe.buildsoft.erp.core.infra.data.repositories.hora;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.hora.RequerimientoPersonal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.hora.RequerimientoPersonalDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericHoraDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class PeriodoDaoImpl.
 * <ul>
 * <li>Copyright 2021 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Thu Feb 24 15:07:25 COT 2022
 * @since BuildErp 1.0
 */
@Stateless
public class RequerimientoPersonalDaoImpl extends GenericHoraDAOImpl<String, RequerimientoPersonal>
		implements RequerimientoPersonalDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.ejb.dao.seguridad.local.requerimientoDaoLocal#
	 * listarrequerimiento(pe.com.builderp.core.model.jpa.seguridad.requerimiento)
	 */
	@Override
	public List<RequerimientoPersonal> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		if (filtro.getId() != null) {
			query.setFirstResult(filtro.getStartRow());
			query.setMaxResults(filtro.getOffSet());
		}
		return query.getResultList();
	}

	/**
	 * Generar query lista requerimiento.
	 *
	 * @param requerimiento el requerimiento
	 * @param esContador    el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idRequerimientoPersonal) from RequerimientoPersonal o where 1=1 ");
		} else {
			jpaql.append(
					" select o from RequerimientoPersonal o left join fetch o.personal per left join fetch o.periodo p left join fetch o.requerimiento req where 1=1 ");
		}

		if (!StringUtil.isNullOrEmpty(filtro.getId())) {
			jpaql.append(" and o.requerimiento.idRequerimiento = :idRequerimiento ");
			parametros.put("idRequerimiento", filtro.getId());
		}
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(
					" and upper(o.personal.nombres) like :search or upper(o.personal.apellidoPaterno) like :search");
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getEstado())) {
			if (filtro.getEstado().equals("C")) {
				jpaql.append(" and req.estado NOT IN ('E','C') ");
			} else {
				jpaql.append(" and req.estado = 'A' ");
			}
		}
		if (!StringUtil.isNullOrEmpty(filtro.getIdRequerimiento())) {
			jpaql.append(" and o.requerimiento.idRequerimiento = :idRequerimiento ");
			parametros.put("idRequerimiento", filtro.getIdRequerimiento());
		}
		if (filtro.getIdPersonal() != null) {
			jpaql.append(" and o.personal.idPersonal = :idPersonal ");
			parametros.put("idPersonal", filtro.getIdPersonal());
		}
		if (filtro.getIdPeriodo() != null) {
			jpaql.append(" and o.periodo.idPeriodo = :idPeriodo ");
			parametros.put("idPeriodo", filtro.getIdPeriodo());
		}
		if (!esContador) {
			if (filtro.getId() != null) {
				jpaql.append(" ORDER BY o.personal.apellidoPaterno");
			} else {
				jpaql.append(" ORDER BY o.requerimiento.nombre ");
			}
		}
		return createQuery(jpaql.toString(), parametros);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.ejb.dao.seguridad.local.requerimientoDaoLocal#
	 * contarListar{entity.getClassName()}(pe.com.builderp.core.model.jpa.seguridad.
	 * requerimiento)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.ejb.dao.seguridad.local.requerimientoDaoLocal#
	 * generarIdrequerimiento()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

}