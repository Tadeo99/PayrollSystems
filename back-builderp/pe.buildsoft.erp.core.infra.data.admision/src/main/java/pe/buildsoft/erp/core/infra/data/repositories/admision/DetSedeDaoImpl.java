package pe.buildsoft.erp.core.infra.data.repositories.admision;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.admision.DetSede;
import pe.buildsoft.erp.core.domain.interfaces.repositories.admision.DetSedeDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericAdmisionDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.CollectionUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class DetSedeDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Wed Oct 21 20:17:19 COT 2020
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class DetSedeDaoImpl extends GenericAdmisionDAOImpl<String, DetSede> implements DetSedeDaoLocal {

	@Override
	public Map<String, List<DetSede>> listarDetSedeMap(List<String> listaIdSede) {
		Map<String, List<DetSede>> resultado = new HashMap<>();
		if (CollectionUtil.isEmpty(listaIdSede)) {
			return resultado;
		}
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		jpaql.append(
				" select o from DetSede o left join fetch o.grado g  left join fetch o.sede ");
		jpaql.append(" where o.sede.idSede in (:listaIdSede) ");
		parametros.put("listaIdSede", listaIdSede);
		var query = createQuery(jpaql.toString(), parametros);
		List<DetSede> listaDetSedes = query.getResultList();
		for (var obj : listaDetSedes) {
			String key = obj.getSede().getIdSede();
			if (!resultado.containsKey(key)) {
				List<DetSede> value = new ArrayList<>();
				value.add(obj);// "grado:{itemByNivel}"
				resultado.put(key, value);
			} else {
				List<DetSede> value = resultado.get(key);
				value.add(obj);// "grado:{itemByNivel}"
				resultado.put(key, value);
			}
		}
		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.admision.dao.local.DetSedeDaoLocal#listarDetSede
	 * (pe.com.builderp.core.service.admision.model.jpa.DetSede)
	 */
	@Override
	public List<DetSede> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		return query.getResultList();
	}

	/**
	 * Generar query lista DetSede.
	 *
	 * @param DetSede    el detSede
	 * @param esContador el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idDetSede) from DetSede o where 1=1 ");
		} else {
			jpaql.append(" select o from DetSede o left join fetch o.grado g where 1=1 ");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getId())) {
			jpaql.append(" and o.sede.idSede = :idSede ");
			parametros.put("idSede", filtro.getId());
		}
		if (!esContador) {
			//jpaql.append(" ORDER BY " + filtro.getSortFields() + " " + filtro.getSortDirections());
		}
		return createQuery(jpaql.toString(), parametros);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.admision.dao.local.DetSedeDaoLocal#contarListar{
	 * entity.getClassName()}(pe.com.builderp.core.service.admision.model.jpa.
	 * DetSede)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.admision.dao.local.DetSedeDaoLocal#
	 * generarIdDetSede()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

	@Override
	public void eliminarBySede(String idSede) {
		var parametros = new HashMap<String, Object>();
		parametros.put("idSede", idSede);
		var query = createQuery("delete from DetSede o where o.sede.idSede=:idSede", parametros);
		query.executeUpdate();
	}
}