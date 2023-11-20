package pe.buildsoft.erp.core.infra.data.repositories.matricula;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.matricula.Unidad;
import pe.buildsoft.erp.core.domain.interfaces.repositories.matricula.UnidadDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericMatriculaDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ObjectUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * La Class UnidadDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:55 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class UnidadDaoImpl extends GenericMatriculaDAOImpl<Long, Unidad> implements UnidadDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.matricula.dao.local.UnidadDaoLocal#listarUnidad(
	 * pe.com.builderp.core.service.matricula.model.jpa.Unidad)
	 */
	@Override
	public List<Unidad> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	/**
	 * Generar query lista Unidad.
	 *
	 * @param UnidadDTO  el unidad
	 * @param esContador el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idUnidad) from Unidad o where 1=1 ");
		} else {
			jpaql.append(" select o from Unidad o  left join fetch o.periodo where 1=1 ");
		}

		if (!StringUtil.isNullOrEmpty(filtro.getId())) {
			jpaql.append(" and o.periodo.idPeriodo = :idPeriodo ");
			parametros.put("idPeriodo", ObjectUtil.objectToLong(filtro.getId()));
		}

		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.descripcion) like :search or upper(o.abreviatura) like :search  ");
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		}
		if (!esContador) {
			jpaql.append(" ORDER BY o.descripcion asc ");
		}
		return createQuery(jpaql.toString(), parametros);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.matricula.dao.local.UnidadDaoLocal#contarListar{
	 * entity.getClassName()}(pe.com.builderp.core.service.matricula.model.jpa.
	 * UnidadDTO)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.matricula.dao.local.UnidadDaoLocal#
	 * generarIdUnidad()
	 */
	@Override
	public Long generarId() {
		var resultado = 1L;
		var query = createQuery("select max(o.idUnidad) from Unidad o", null);
		var listLong = query.getResultList();
		if (listLong != null && !listLong.isEmpty() && listLong.get(0) != null) {
			Long ultimoIdGenerado = Long.valueOf(listLong.get(0).toString());
			if (!StringUtil.isNullOrEmpty(ultimoIdGenerado)) {
				resultado = resultado + ultimoIdGenerado;
			}
		}
		return resultado;
	}

	@Override
	public Unidad find(Unidad filtro) {
		Unidad resultado = new Unidad();
		var parametros = new HashMap<String, Object>();
		var ejecutarQuery = false;
		var jpaql = new StringBuilder();
		jpaql.append(" from Unidad o  where 1=1 ");
		if (!StringUtil.isNullOrEmpty(filtro.getDescripcion())) {
			ejecutarQuery = true;
			jpaql.append(" and upper(o.descripcion)=upper(:descripcion)");
			parametros.put("descripcion", filtro.getDescripcion());
		}
		if (!StringUtil.isNullOrEmpty(filtro.getPeriodo().getIdPeriodo())) {
			ejecutarQuery = true;
			jpaql.append(" and o.periodo.idPeriodo =:idPeriodo");
			parametros.put("idPeriodo", filtro.getPeriodo().getIdPeriodo());
		}
		if (ejecutarQuery) {
			var query = createQuery(jpaql.toString(), parametros);
			List<Unidad> listaObj = query.getResultList();
			if (listaObj != null && !listaObj.isEmpty()) {
				resultado = listaObj.get(0);
			}
		}
		return resultado;
	}
}