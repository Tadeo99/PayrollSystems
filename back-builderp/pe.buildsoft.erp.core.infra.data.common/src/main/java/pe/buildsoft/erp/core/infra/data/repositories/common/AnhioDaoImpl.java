package pe.buildsoft.erp.core.infra.data.repositories.common;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.common.Anhio;
import pe.buildsoft.erp.core.domain.interfaces.repositories.common.AnhioDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericCommonDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.state.EstadoGeneralState;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * La Class AnhioDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:55 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class AnhioDaoImpl extends GenericCommonDAOImpl<Long, Anhio> implements AnhioDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.matricula.dao.local.AnhioDaoLocal#listarAnhio(pe
	 * .com.builderp.core.service.matricula.model.jpa.Anhio)
	 */
	@Override
	public List<Anhio> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		if (filtro.getOffSet() > 0) {
			query.setFirstResult(filtro.getStartRow());
			query.setMaxResults(filtro.getOffSet());
		}
		return query.getResultList();
	}

	/**
	 * Generar query lista Anhio.
	 *
	 * @param AnhioDTO   el anhio
	 * @param esContador el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idAnhio) from Anhio o where 1=1 ");
		} else {
			jpaql.append(" select o from Anhio o where 1=1 ");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.nombre) like :search ");
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		} else {
			if (!StringUtil.isNullOrEmpty(filtro.getEstado())) {
				// jpaql.append(" and upper(o.estado) like :estado ");
				// parametros.put("estado", "%" + filtro.getEstado().toUpperCase() + "%");
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
	 * pe.com.builderp.core.service.matricula.dao.local.AnhioDaoLocal#contarListar{
	 * entity.getClassName()}(pe.com.builderp.core.service.matricula.model.jpa.
	 * AnhioDTO)
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
	 * pe.com.builderp.core.service.matricula.dao.local.AnhioDaoLocal#generarIdAnhio
	 * ()
	 */
	@Override
	public Long generarId() {
		var resultado = 1L;
		var query = createQuery("select max(o.idAnhio) from Anhio o", null);
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
	public Anhio obtenerAnhioyEstado(EstadoGeneralState estadoAnhoState) {
		Anhio resultado = null;
		var parametros = new HashMap<String, Object>();
		parametros.put("estado", estadoAnhoState.getKey());
		var query = createQuery("from Anhio a where   a.estado =:estado", parametros);

		List<Anhio> listaAnhio = query.getResultList();
		if (listaAnhio != null && !listaAnhio.isEmpty()) {
			resultado = listaAnhio.get(0);
		}
		return resultado;
	}

}