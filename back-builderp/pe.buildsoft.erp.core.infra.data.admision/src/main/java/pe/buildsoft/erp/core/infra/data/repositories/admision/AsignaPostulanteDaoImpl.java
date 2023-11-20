package pe.buildsoft.erp.core.infra.data.repositories.admision;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.admision.AsignaPostulante;
import pe.buildsoft.erp.core.domain.entidades.admision.vo.BaseSearchVO;
import pe.buildsoft.erp.core.domain.interfaces.repositories.admision.AsignaPostulanteDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericAdmisionDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.utilitario.CollectionUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class AsignaPostulanteDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Wed Oct 21 20:17:19 COT 2020
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class AsignaPostulanteDaoImpl extends GenericAdmisionDAOImpl<String, AsignaPostulante>
		implements AsignaPostulanteDaoLocal {

	@Override
	public Map<String, Long> listarAsignaPostulanteMap(List<String> listaIdSede) {
		Map<String, Long> resultado = new HashMap<>();
		if (CollectionUtil.isEmpty(listaIdSede)) {
			return resultado;
		}
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		jpaql.append(" select o.sede,o.grado.idGrado,count(1) as contador ");
		jpaql.append(" from AsignaPostulante o  ");
		jpaql.append(" where o.sede in (:listaIdSede) ");
		jpaql.append(" group by o.sede,o.grado.idGrado");
		parametros.put("listaIdSede", listaIdSede);
		var query = createQuery(jpaql.toString(), parametros);
		List<Object[]> listaObject = query.getResultList();
		for (var obj : listaObject) {
			String key = StringUtil.generarKey(obj, 2);
			if (!resultado.containsKey(key)) {
				resultado.put(key, Long.valueOf(obj[2] + ""));
			}
		}
		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.admision.dao.local.AsignaPostulanteDaoLocal#
	 * listarAsignaPostulante(pe.com.builderp.core.service.admision.model.jpa.
	 * AsignaPostulante)
	 */
	@Override
	public List<AsignaPostulante> listar(BaseSearchVO filtro) {
		var query = generarQuery(filtro, false);
		if (filtro.getOffSet() > 0) {
			query.setFirstResult(filtro.getStartRow());
			query.setMaxResults(filtro.getOffSet());
		}
		return query.getResultList();
	}

	/**
	 * Generar query.
	 *
	 * @param AsignaPostulante el asignaPostulante
	 * @param esContador       el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearchVO filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idAsignaPostulante) from AsignaPostulante o where 1=1 ");
		} else {
			jpaql.append(" select o from AsignaPostulante o left join fetch o.postulante p  where 1=1 ");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.idAsignaPostulante) like :search ");
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		} else {
			if (!StringUtil.isNullOrEmpty(filtro.getNroDoc())) {
				jpaql.append(" and o.postulante.nroDoc =:nroDoc ");
				parametros.put("nroDoc", filtro.getNroDoc());
			}
		}
		if (!esContador && StringUtil.isNotNullOrBlank(filtro.getSortFields())) {
			jpaql.append(" ORDER BY " + filtro.getSortFields() + " " + filtro.getSortDirections());
		}
		return createQuery(jpaql.toString(), parametros);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.admision.dao.local.AsignaPostulanteDaoLocal#
	 * contarListar{entity.getClassName()}(pe.com.builderp.core.service.admision.
	 * model.jpa.AsignaPostulante)
	 */
	@Override
	public int contarListar(BaseSearchVO filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.admision.dao.local.AsignaPostulanteDaoLocal#
	 * generarIdAsignaPostulante()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

}