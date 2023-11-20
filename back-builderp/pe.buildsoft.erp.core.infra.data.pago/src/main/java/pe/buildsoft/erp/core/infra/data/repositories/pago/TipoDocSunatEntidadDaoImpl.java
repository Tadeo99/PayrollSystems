package pe.buildsoft.erp.core.infra.data.repositories.pago;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.pago.TipoDocSunatEntidad;
import pe.buildsoft.erp.core.domain.interfaces.repositories.pago.TipoDocSunatEntidadDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericPagoDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class TipoDocSunatEntidadDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 21:45:31 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class TipoDocSunatEntidadDaoImpl extends GenericPagoDAOImpl<String, TipoDocSunatEntidad>
		implements TipoDocSunatEntidadDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.pago.dao.local.TipoDocSunatEntidadDaoLocal#
	 * listarTipoDocSunatEntidad(pe.com.builderp.core.service.pago.model.jpa.
	 * TipoDocSunatEntidad)
	 */
	@Override
	public List<TipoDocSunatEntidad> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	/**
	 * Generar query lista TipoDocSunatEntidad.
	 *
	 * @param TipoDocSunatEntidad el tipoDocSunatEntidad
	 * @param esContador          el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idTipoDocSunatEntidad) from TipoDocSunatEntidad o where 1=1 ");
		} else {
			jpaql.append(" select o from TipoDocSunatEntidad  o where 1=1 ");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getIdEntidadSelect())) {
			jpaql.append(" and o.entidad =:idEntidad ");
			parametros.put("idEntidad", filtro.getIdEntidadSelect());
		}

		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.serie || o.idItemByTipoDocSunat) like :search ");
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
	 * @see pe.com.builderp.core.service.pago.dao.local.TipoDocSunatEntidadDaoLocal#
	 * contarListar{entity.getClassName()}(pe.com.builderp.core.service.pago.model.
	 * jpa.TipoDocSunatEntidad)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.pago.dao.local.TipoDocSunatEntidadDaoLocal#
	 * generarIdTipoDocSunatEntidad()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

	@Override
	public String update(Long idTipoDocSunat, String idEntidad, String nroDoc, String serie) {
		String resultado = "";
		TipoDocSunatEntidad tipoDocSunat = get(idTipoDocSunat, idEntidad, serie);
		if (!StringUtil.isNotNullOrBlank(nroDoc)) {
			// generar el nro doc
			resultado = (tipoDocSunat.getCorrela().trim());
			Long nroDocCalc = Long.parseLong(tipoDocSunat.getCorrela().trim());
			nroDocCalc++;
			tipoDocSunat
					.setCorrela(StringUtil.completeLeft(nroDocCalc, '0', tipoDocSunat.getCorrela().trim().length()));
			update(tipoDocSunat);
		} else {
			// actualizando correla
			Long nroDocCalc = Long.parseLong(nroDoc.trim());
			nroDocCalc++;
			tipoDocSunat.setCorrela(StringUtil.completeLeft(nroDocCalc, '0', nroDoc.trim().length()));
			update(tipoDocSunat);
		}
		return resultado;
	}

	@Override
	public TipoDocSunatEntidad get(Long idTipoDocSunat, String idEntidad, String serie) {
		TipoDocSunatEntidad resultado = new TipoDocSunatEntidad();
		List<TipoDocSunatEntidad> resultadoList = new ArrayList<>();
		Map<String, Object> parametro = new HashMap<>();
		var jpaql = new StringBuilder();
		jpaql.append(" select o from TipoDocSunatEntidad o ");
		jpaql.append(" where o.idItemByTipoDocSunat = :idTipoDocSunat ");
		jpaql.append(" and upper(o.serie) = :serie");
		if (idEntidad != null) {
			jpaql.append(" and o.entidad = :idEntidad ");
			parametro.put("idEntidad", idEntidad);
		}
		parametro.put("idTipoDocSunat", idTipoDocSunat);
		parametro.put("serie", serie.toUpperCase());
		var query = createQuery(jpaql.toString(), parametro);
		resultadoList = query.getResultList();
		if (resultadoList != null && !resultadoList.isEmpty()) {
			resultado = resultadoList.get(0);
		}
		return resultado;
	}

	@Override
	public List<TipoDocSunatEntidad> listarByItem(TipoDocSunatEntidad filtro) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		jpaql.append("from TipoDocSunatEntidad o where 1=1  ");
		if (!StringUtil.isNullOrEmpty(filtro.getIdEntidad())) {
			jpaql.append(" and o.idEntidad = :idEntidad ");
			parametros.put("idEntidad", filtro.getIdEntidad());
		}
		if (!StringUtil.isNullOrEmpty(filtro.getIdItemByTipoDocSunat())) {
			jpaql.append(" and o.idItemByTipoDocSunat = :idTipoDocSunat ");
			parametros.put("idTipoDocSunat", filtro.getIdItemByTipoDocSunat());
		}
		jpaql.append(" ORDER BY o.serie asc ");
		var query = this.createQuery(jpaql.toString(), parametros);
		return query.getResultList();
	}

}