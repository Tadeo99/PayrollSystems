package pe.buildsoft.erp.core.infra.data.repositories.escalafon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.escalafon.CentroCosto;
import pe.buildsoft.erp.core.domain.interfaces.repositories.escalafon.CentroCostoDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericEscalafonDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class CentroCostoDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Wed Jul 22 00:55:18 COT 2020
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class CentroCostoDaoImpl extends GenericEscalafonDAOImpl<String, CentroCosto> implements CentroCostoDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.rrhh.dao.local.CentroCostoDaoLocal#
	 * listarCentroCosto(pe.com.builderp.core.service.rrhh.model.jpa.CentroCosto)
	 */
	@Override
	public List<CentroCosto> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		if (filtro.getOffSet() > 0) {
			query.setFirstResult(filtro.getStartRow());
			query.setMaxResults(filtro.getOffSet());
		}
		return query.getResultList();
	}

	/**
	 * Generar query lista CentroCosto.
	 *
	 * @param CentroCosto el centroCosto
	 * @param esContador  el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		Map<String, Object> parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idCentroCosto) from CentroCosto o where 1=1 ");
		} else {
			jpaql.append(" select o from CentroCosto o where 1=1 ");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.nombre || o.codigo) like :search ");
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
	 * @see
	 * pe.com.builderp.core.service.rrhh.dao.local.CentroCostoDaoLocal#contarListar{
	 * entity.getClassName()}(pe.com.builderp.core.service.rrhh.model.jpa.
	 * CentroCosto)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.rrhh.dao.local.CentroCostoDaoLocal#
	 * generarIdCentroCosto()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

	@Override
	public CentroCosto findCentroCosto(CentroCosto filtro) {// TODO:REVISAR NATAN OPT
		var resultado = new CentroCosto();
		var parametros = new HashMap<String, Object>();
		var ejecutarQuery = false;
		var jpaql = new StringBuilder();
		jpaql.append(" from CentroCosto o  where 1=1 ");
		if (!StringUtil.isNullOrEmpty(filtro.getCodigo())) {
			ejecutarQuery = true;
			jpaql.append(" and upper(o.codigo)=upper(:codigo)");
			parametros.put("codigo", filtro.getCodigo());
		}
		if (!StringUtil.isNullOrEmpty(filtro.getNombre())) {
			ejecutarQuery = true;
			jpaql.append(" and upper(o.nombre)=upper(:nombre)");
			parametros.put("nombre", filtro.getNombre());
		}
		if (ejecutarQuery) {
			var query = createQuery(jpaql.toString(), parametros);
			List<CentroCosto> listaObj = query.getResultList();
			if (listaObj != null && listaObj.size() > 0) {
				resultado = listaObj.get(0);
			}
		}
		return resultado;
	}

}