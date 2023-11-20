package pe.buildsoft.erp.core.infra.data.repositories.escalafon;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.escalafon.AsociarCentroCosto;
import pe.buildsoft.erp.core.domain.interfaces.repositories.escalafon.AsociarCentroCostoDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericEscalafonDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class AsociarCentroCostoDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Wed Jul 22 00:55:18 COT 2020
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class AsociarCentroCostoDaoImpl extends GenericEscalafonDAOImpl<String, AsociarCentroCosto>
		implements AsociarCentroCostoDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.rrhh.dao.local.AsociarCentroCostoDaoLocal#
	 * listarAsociarCentroCosto(pe.com.builderp.core.service.rrhh.model.jpa.
	 * AsociarCentroCosto)
	 */
	@Override
	public List<AsociarCentroCosto> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	/**
	 * Generar query lista AsociarCentroCosto.
	 *
	 * @param AsociarCentroCosto el asociarCentroCosto
	 * @param esContador         el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idAsociarCentroCosto) from AsociarCentroCosto o where 1=1 ");
		} else {
			jpaql.append(" select o from AsociarCentroCosto o left join fetch o.centroCosto where 1=1 ");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.idAsociarCentroCosto) like :search ");
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
	 * @see pe.com.builderp.core.service.rrhh.dao.local.AsociarCentroCostoDaoLocal#
	 * contarListar{entity.getClassName()}(pe.com.builderp.core.service.rrhh.model.
	 * jpa.AsociarCentroCosto)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.rrhh.dao.local.AsociarCentroCostoDaoLocal#
	 * generarIdAsociarCentroCosto()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

}