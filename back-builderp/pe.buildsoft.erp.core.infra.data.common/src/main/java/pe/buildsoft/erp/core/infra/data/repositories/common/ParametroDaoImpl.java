package pe.buildsoft.erp.core.infra.data.repositories.common;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.common.Parametro;
import pe.buildsoft.erp.core.domain.interfaces.repositories.common.ParametroDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericCommonDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class ParametroDaoImpl.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Dec 14 00:27:42 COT 2017
 * @since SIAA-CORE 2.1
 */
@Stateless
public class ParametroDaoImpl extends GenericCommonDAOImpl<String, Parametro> implements ParametroDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.ejb.dao.common.local.ParametroDaoLocal#listarParametro(
	 * pe.com.builderp.core.model.jpa.common.Parametro)
	 */
	@Override
	public List<Parametro> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		if (filtro.getOffSet() > 0) {
			query.setFirstResult(filtro.getStartRow());
			query.setMaxResults(filtro.getOffSet());
		}
		return query.getResultList();
	}

	/**
	 * Generar query lista Parametro.
	 *
	 * @param Parametro  el parametro
	 * @param esContador el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idParametro) from Parametro o where 1=1 ");
		} else {
			jpaql.append(" select o from Parametro o where 1=1 ");
		}

		if (!StringUtil.isNullOrEmpty(filtro.getIdEntidadSelect())) {
			jpaql.append(" and o.entidad =:idEntidad ");
			parametros.put("idEntidad", filtro.getIdEntidadSelect());
		}

		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.descripcion) like :search ");
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		} else {
			if (!StringUtil.isNullOrEmpty(filtro.getEstado())) {
				jpaql.append(" and o.estado =:estado ");
				parametros.put("estado", filtro.getEstado());
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
	 * pe.com.builderp.core.ejb.dao.common.local.ParametroDaoLocal#contarListar{
	 * entity.getClassName()}(pe.com.builderp.core.model.jpa.common.Parametro)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.ejb.dao.common.local.ParametroDaoLocal#
	 * generarIdParametro()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

}