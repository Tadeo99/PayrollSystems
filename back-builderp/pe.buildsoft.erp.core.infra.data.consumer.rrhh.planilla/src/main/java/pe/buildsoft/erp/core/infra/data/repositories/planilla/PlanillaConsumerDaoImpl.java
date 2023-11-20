package pe.buildsoft.erp.core.infra.data.repositories.planilla;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.planilla.Planilla;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.PlanillaConsumerDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericPlanillaBatchDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.type.MesType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.CollectionUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ObjectUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class PlanillaDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class PlanillaConsumerDaoImpl extends GenericPlanillaBatchDAOImpl<String, Planilla>
		implements PlanillaConsumerDaoLocal {

	@Override
	public Map<String, Map<String, BigDecimal>> listarPlanillaAntMap(List<String> listaIdPersonal,
			String idTipoPlanilla, Long idAhnio) {
		Map<String, Map<String, BigDecimal>> resultado = new HashMap<>();
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		var ejecutarQuery = false;
		jpaql.append("select o.idPersonal, o.planilla.idItemByPeriodoMes, o.totalIngreso ");
		jpaql.append(" from DetallePlanilla o   where 1 = 1  ");
		if (!CollectionUtil.isEmpty(listaIdPersonal)) {
			ejecutarQuery = true;
			jpaql.append(" and o.idPersonal in(:idPersonal) ");
			parametros.put("idPersonal", listaIdPersonal);
		}
		if (StringUtil.isNotNullOrBlank(idTipoPlanilla)) {
			ejecutarQuery = true;
			jpaql.append(" and o.planilla.tipoPlanilla.idTipoPlanilla =:idTipoPlanilla ");
			parametros.put("idTipoPlanilla", idTipoPlanilla);
		}
		if (StringUtil.isNotNullOrBlank(idAhnio)) {
			ejecutarQuery = true;
			jpaql.append(" and o.planilla.idAnhio =:idAhnio ");
			parametros.put("idAhnio", idAhnio);
		}

		if (ejecutarQuery) {
			var query = createQuery(jpaql.toString(), parametros);
			List<Object[]> listaTemp = query.getResultList();
			for (var obj : listaTemp) {
				var key = ObjectUtil.objectToString(obj[0]);
				var keyConcepto = MesType.get(ObjectUtil.objectToLong(obj[1])).getValue();
				if (!resultado.containsKey(key)) {
					var objValue = new HashMap<String, BigDecimal>();
					if (!objValue.containsKey(keyConcepto)) {
						objValue.put(keyConcepto, ObjectUtil.objectToBigDecimal(obj[2]));
					}
					resultado.put(key, objValue);
				} else {
					var objValue = resultado.get(key);
					if (!objValue.containsKey(keyConcepto)) {
						objValue.put(keyConcepto, ObjectUtil.objectToBigDecimal(obj[2]));
					}
				}
			}
		}
		return resultado;
	}

	@Override
	public Map<String, Map<String, BigDecimal>> listarRenta5taAntMap(List<String> listaIdPersonal,
			String idTipoPlanilla, Long idAhnio) {
		Map<String, Map<String, BigDecimal>> resultado = new HashMap<>();
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		var ejecutarQuery = false;
		jpaql.append("select o.detallePlanilla.idPersonal, o.detallePlanilla.planilla.idItemByPeriodoMes,o.monto ");
		jpaql.append(" from DetallePlanillaConcepto o where 1 = 1 ");
		if (!CollectionUtil.isEmpty(listaIdPersonal)) {
			ejecutarQuery = true;
			jpaql.append(" and o.detallePlanilla.idPersonal in(:idPersonal) ");
			parametros.put("idPersonal", listaIdPersonal);
		}
		if (StringUtil.isNotNullOrBlank(idTipoPlanilla)) {
			ejecutarQuery = true;
			jpaql.append(" and o.detallePlanilla.planilla.tipoPlanilla.idTipoPlanilla =:idTipoPlanilla ");
			parametros.put("idTipoPlanilla", idTipoPlanilla);
		}
		if (StringUtil.isNotNullOrBlank(idAhnio)) {
			ejecutarQuery = true;
			jpaql.append(" and o.detallePlanilla.planilla.idAnhio =:idAhnio ");
			parametros.put("idAhnio", idAhnio);
		}
		jpaql.append(" and o.concepto.codigo =:codigoConcepto");
		parametros.put("codigoConcepto", "renta5ta");

		if (ejecutarQuery) {
			var query = createQuery(jpaql.toString(), parametros);
			List<Object[]> listaTemp = query.getResultList();
			for (var obj : listaTemp) {
				var key = ObjectUtil.objectToString(obj[0]);
				var keyConcepto = MesType.get(ObjectUtil.objectToLong(obj[1])).getValue();
				if (!resultado.containsKey(key)) {
					var objValue = new HashMap<String, BigDecimal>();
					if (!objValue.containsKey(keyConcepto)) {
						objValue.put(keyConcepto, ObjectUtil.objectToBigDecimal(obj[2]));
					}
					resultado.put(key, objValue);
				} else {
					var objValue = resultado.get(key);
					if (!objValue.containsKey(keyConcepto)) {
						objValue.put(keyConcepto, ObjectUtil.objectToBigDecimal(obj[2]));
					}
				}
			}
		}
		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.rrhh.planilla.dao.local.PlanillaDaoLocal#
	 * listarPlanilla(pe.com.builderp.core.service.rrhh.planilla.model.jpa.Planilla)
	 */
	@Override
	public List<Planilla> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	/**
	 * Generar query lista Planilla.
	 *
	 * @param Planilla   el planilla
	 * @param esContador el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idPlanilla) from Planilla o where 1=1 ");
		} else {
			jpaql.append(" select o from Planilla o where 1=1 ");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.idPlanilla) like :search ");
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		}
		if (!esContador) {
			jpaql.append(" ORDER BY 1 ");
		}
		return createQuery(jpaql.toString(), parametros);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.rrhh.planilla.dao.local.PlanillaDaoLocal#
	 * contarListar{entity.getClassName()}(pe.com.builderp.core.service.rrhh.
	 * planilla.model.jpa.Planilla)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.rrhh.planilla.dao.local.PlanillaDaoLocal#
	 * generarIdPlanilla()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

	@Override
	public boolean eliminar(Planilla filtro) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		jpaql.append(" delete from Planilla o ");
		jpaql.append("  where 1=1 ");
		jpaql.append("   and o.idAnhio  = :idAnhio ");
		jpaql.append("   and o.idItemByPeriodoMes  = :itemByPeriodoMes ");
		jpaql.append("   and o.tipoPlanilla.idTipoPlanilla = :idTipoPlanilla ");

		parametros.put("itemByPeriodoMes", filtro.getIdItemByPeriodoMes());
		parametros.put("idAnhio", filtro.getIdAnhio());
		parametros.put("idTipoPlanilla", filtro.getTipoPlanilla().getIdTipoPlanilla());

		var query = createQuery(jpaql.toString(), parametros);
		return query.executeUpdate() > 0;
	}

}