package pe.buildsoft.erp.core.infra.data.repositories.generador;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.generador.EquivalenciaType;
import pe.buildsoft.erp.core.domain.interfaces.repositories.generador.EquivalenciaTypeDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericGeneradorDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class ConfigTypeEquivalenciaDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Mon Sep 26 12:14:56 PET 2022
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class EquivalenciaTypeDaoImpl extends GenericGeneradorDAOImpl<String, EquivalenciaType>
		implements EquivalenciaTypeDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.generador.dao.local.
	 * ConfigTypeEquivalenciaDaoLocal#listar(pe.com.builderp.core.service.generador.
	 * model.jpa.ConfigTypeEquivalencia)
	 */
	@Override
	public List<EquivalenciaType> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	/**
	 * Generar query lista ConfigTypeEquivalencia.
	 *
	 * @param EquivalenciaType el configTypeEquivalencia
	 * @param esContador       el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idEquivalenciaType) from EquivalenciaType o where 1=1 ");
		} else {
			jpaql.append(" select o from EquivalenciaType o left join fetch o.tecnologiaEquivalente where 1=1 ");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.tecnologia.nombre) like :search ");
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getId())) {
			jpaql.append(" and o.tecnologia.idTecnologia = :id ");
			parametros.put("id", filtro.getId());
		}
		if (!esContador) {
			jpaql.append(" ORDER BY o." + filtro.getSortFields() + " " + filtro.getSortDirections());
		}
		return createQuery(jpaql.toString(), parametros);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.generador.dao.local.
	 * ConfigTypeEquivalenciaDaoLocal#contarListar{entity.getClassName()}(pe.com.
	 * builderp.core.service.generador.model.jpa.ConfigTypeEquivalenciaDTO)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.generador.dao.local.
	 * ConfigTypeEquivalenciaDaoLocal#generarIdConfigTypeEquivalencia()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

}