package pe.buildsoft.erp.core.infra.data.repositories.escalafon;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.escalafon.HistorialBasico;
import pe.buildsoft.erp.core.domain.interfaces.repositories.escalafon.HistorialBasicoDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericEscalafonDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class HistorialBasicoDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Wed Jul 22 00:55:19 COT 2020
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class HistorialBasicoDaoImpl extends GenericEscalafonDAOImpl<String, HistorialBasico>
		implements HistorialBasicoDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.rrhh.dao.local.HistorialBasicoDaoLocal#
	 * listarHistorialBasico(pe.com.builderp.core.service.rrhh.model.jpa.
	 * HistorialBasico)
	 */
	@Override
	public List<HistorialBasico> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	/**
	 * Generar query lista HistorialBasico.
	 *
	 * @param HistorialBasico el historialBasico
	 * @param esContador      el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idHistorialBasico) from HistorialBasico o where 1=1 ");
		} else {
			jpaql.append(" select o from HistorialBasico o where 1=1 ");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getId())) {
			jpaql.append(" and o.personal.idPersonal = :idPersonal ");
			parametros.put("idPersonal", filtro.getId());
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
	 * @see pe.com.builderp.core.service.rrhh.dao.local.HistorialBasicoDaoLocal#
	 * contarListar{entity.getClassName()}(pe.com.builderp.core.service.rrhh.model.
	 * jpa.HistorialBasico)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.rrhh.dao.local.HistorialBasicoDaoLocal#
	 * generarIdHistorialBasico()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

	// TODO:VER CAMPO ESTADO ACTIVO PARA MEJOR FILTRO
	@Override
	public Map<String, BigDecimal> obtenerBasicoPersonalMap(Long idCategoriaTrabajador) {
		var resultado = new HashMap<String, BigDecimal>();
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		jpaql.append(
				"select o.personal.idPersonal,o.valorActual, max(o.fechaCambio) from HistorialBasico o where 1 = 1 ");
		jpaql.append(
				" and exists (select per.idPersonal from Personal per where per.idPersonal = o.personal.idPersonal  and per.idItemByCategoriaTrabajador =:idCategoriaTrabajador) ");
		jpaql.append(" group by o.personal.idPersonal,o.valorActual ");
		parametros.put("idCategoriaTrabajador", idCategoriaTrabajador);
		// jpaql.append(" and o.fechaCambio = (select max(per.fechaCambio) from
		// HistorialBasico per where per.personal.idPersonal = o.personal.idPersonal)
		// ");

		var query = createQuery(jpaql.toString(), parametros);
		List<Object[]> resul = query.getResultList();
		for (var objects : resul) {
			var keyPersonal = (String) objects[0];
			if (!resultado.containsKey(keyPersonal)) {
				resultado.put(keyPersonal, (BigDecimal) objects[1]);
			}
		}
		return resultado;
	}

}