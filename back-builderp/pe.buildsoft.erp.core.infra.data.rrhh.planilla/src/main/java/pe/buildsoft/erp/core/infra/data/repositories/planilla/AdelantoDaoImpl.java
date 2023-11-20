package pe.buildsoft.erp.core.infra.data.repositories.planilla;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.planilla.Adelanto;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.AdelantoDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericPlanillaDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class AdelantoDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class AdelantoDaoImpl extends GenericPlanillaDAOImpl<String, Adelanto> implements AdelantoDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.rrhh.planilla.dao.local.AdelantoDaoLocal#
	 * listarAdelanto(pe.com.builderp.core.service.rrhh.planilla.model.jpa.Adelanto)
	 */
	@Override
	public List<Adelanto> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	/**
	 * Generar query lista Adelanto.
	 *
	 * @param Adelanto   el adelanto
	 * @param esContador el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idAdelanto) from Adelanto o where 1=1 ");
		} else {
			jpaql.append(" select o from Adelanto o   ");
			jpaql.append(" left join fetch o.conceptoPdt where 1=1");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getId())) {
			jpaql.append(" and o.idPersonal = :id ");
			parametros.put("id", filtro.getId());
		}
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(
					" and TRANSLATE(UPPER(o.conceptoPdt.descripcion), :discriminaTildeMAC, :discriminaTildeMAT) like UPPER(translate(:search, :discriminaTildeMIC, :discriminaTildeMIT))");
			parametros.putAll(obtenerParametroDiscriminarTilde());
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
	 * @see pe.com.builderp.core.service.rrhh.planilla.dao.local.AdelantoDaoLocal#
	 * contarListar{entity.getClassName()}(pe.com.builderp.core.service.rrhh.
	 * planilla.model.jpa.Adelanto)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.rrhh.planilla.dao.local.AdelantoDaoLocal#
	 * generarIdAdelanto()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

	// TODO:FALTA PERIODO DE PLANILLA
	@Override
	public Map<String, BigDecimal> obtnerAdelantosMap(Long idCategoriaTrabajador) {
		Map<String, BigDecimal> resultado = new HashMap<>();
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		jpaql.append("select o from Adelanto o  where 1 = 1 ");
		if (!StringUtil.isNullOrEmpty(idCategoriaTrabajador)) {
			jpaql.append(
					" and exists (select per.idPersonal from Personal per where per.idPersonal = o.personal.idPersonal  and per.itemByCategoriaTrabajador.idItem=:idCategoriaTrabajador) ");
			parametros.put("idCategoriaTrabajador", idCategoriaTrabajador);
		}
		var query = createQuery(jpaql.toString(), parametros);
		List<Adelanto> resul = query.getResultList();
		for (Adelanto objData : resul) {
			String key = objData.getIdPersonal();
			if (!resultado.containsKey(key)) {
				resultado.put(key, objData.getMonto());
			} else {
				resultado.put(key, objData.getMonto());
			}
		}

		return resultado;
	}

}