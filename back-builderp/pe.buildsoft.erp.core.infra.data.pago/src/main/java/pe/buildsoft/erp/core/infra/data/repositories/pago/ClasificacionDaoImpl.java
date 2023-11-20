package pe.buildsoft.erp.core.infra.data.repositories.pago;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.pago.Clasificacion;
import pe.buildsoft.erp.core.domain.interfaces.repositories.pago.ClasificacionDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericPagoDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * La Class ClasificacionDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 21:45:29 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class ClasificacionDaoImpl extends GenericPagoDAOImpl<Long, Clasificacion> implements ClasificacionDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.pago.dao.local.ClasificacionDaoLocal#
	 * listarClasificacion(pe.com.builderp.core.service.pago.model.jpa.
	 * Clasificacion)
	 */
	@Override
	public List<Clasificacion> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	/**
	 * Generar query lista Clasificacion.
	 *
	 * @param Clasificacion el clasificacion
	 * @param esContador    el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idClasificacion) from Clasificacion o where 1=1 ");
		} else {
			jpaql.append(" select o from Clasificacion o where 1=1 ");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getIdEntidadSelect())) {
			jpaql.append(" and o.entidad =:idEntidadSelect");
			parametros.put("idEntidadSelect", filtro.getIdEntidadSelect());
		}
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.descripcion) like :search ");
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
	 * @see pe.com.builderp.core.service.pago.dao.local.ClasificacionDaoLocal#
	 * contarListar{entity.getClassName()}(pe.com.builderp.core.service.pago.model.
	 * jpa.Clasificacion)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.pago.dao.local.ClasificacionDaoLocal#
	 * generarIdClasificacion()
	 */
	@Override
	public Long generarId() {
		var resultado = 1L;
		var query = createQuery("select max(o.idClasificacion) from Clasificacion o", null);
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
	public Clasificacion find(Clasificacion filtro) {
		Clasificacion resultado = new Clasificacion();
		var parametros = new HashMap<String, Object>();
		var ejecutarQuery = false;
		var jpaql = new StringBuilder();
		jpaql.append(" from Clasificacion o  where 1=1 ");
		if (!StringUtil.isNullOrEmpty(filtro.getDescripcion())) {
			ejecutarQuery = true;
			jpaql.append(" and upper(o.descripcion)=upper(:descripcion)");
			parametros.put("descripcion", filtro.getDescripcion());
		}
		if (!StringUtil.isNullOrEmpty(filtro.getEntidad())) {
			ejecutarQuery = true;
			jpaql.append(" and o.entidad =:idEntidad");
			parametros.put("idEntidad", filtro.getEntidad());
		}
		if (!StringUtil.isNullOrEmpty(filtro.getIdSede())) {
			ejecutarQuery = true;
			jpaql.append(" and o.idSede =:idSede");
			parametros.put("idSede", filtro.getIdSede());
		}
		if (ejecutarQuery) {
			var query = createQuery(jpaql.toString(), parametros);
			List<Clasificacion> listaObj = query.getResultList();
			if (listaObj != null && !listaObj.isEmpty()) {
				resultado = listaObj.get(0);
			}
		}
		return resultado;
	}

}