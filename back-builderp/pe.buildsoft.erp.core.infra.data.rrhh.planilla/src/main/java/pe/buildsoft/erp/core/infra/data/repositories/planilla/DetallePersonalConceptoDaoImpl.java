package pe.buildsoft.erp.core.infra.data.repositories.planilla;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.planilla.DetallePersonalConcepto;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.DetallePersonalConceptoDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericPlanillaDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class DetallePersonalConceptoDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class DetallePersonalConceptoDaoImpl extends GenericPlanillaDAOImpl<String, DetallePersonalConcepto>
		implements DetallePersonalConceptoDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.rrhh.planilla.dao.local.
	 * DetallePersonalConceptoDaoLocal#listarDetallePersonalConcepto(pe.com.builderp
	 * .core.service.rrhh.planilla.model.jpa.DetallePersonalConcepto)
	 */
	@Override
	public List<DetallePersonalConcepto> listar(DetallePersonalConcepto filtro) {
		var query = generarQuery(filtro, false);
		return query.getResultList();
	}

	/**
	 * Generar query lista DetallePersonalConcepto.
	 *
	 * @param DetallePersonalConcepto el detallePersonalConcepto
	 * @param esContador              el es contador
	 * @return the query
	 */
	private Query generarQuery(DetallePersonalConcepto filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idDetallePersonalConcepto) from DetallePersonalConcepto o where 1=1 ");
		} else {
			jpaql.append(" select o from DetallePersonalConcepto o  ");
			jpaql.append(" left join fetch o.conceptosTipoPlanilla ctp ");
			jpaql.append(" left join fetch ctp.tipoPlanilla left join fetch ctp.conceptoPdt ");
			jpaql.append(" where 1=1");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getPersonalConcepto().getTipoPlanilla().getIdTipoPlanilla())) {
			jpaql.append(" and o.personalConcepto.tipoPlanilla.idTipoPlanilla=:idTipoPlanilla");
			parametros.put("idTipoPlanilla", filtro.getPersonalConcepto().getTipoPlanilla().getIdTipoPlanilla());
		}
		if (!StringUtil.isNullOrEmpty(filtro.getPersonalConcepto().getIdPersonal())) {
			jpaql.append(" and o.personalConcepto.idPersonal=:idPersonal");
			parametros.put("idPersonal", filtro.getPersonalConcepto().getIdPersonal());
		}
		if (!StringUtil.isNullOrEmpty(filtro.getPersonalConcepto().getPeriodoPlanilla().getIdPeriodoPlanilla())) {
			jpaql.append(" and o.personalConcepto.periodoPlanilla.idPeriodoPlanilla=:idPeriodoPlanilla");
			parametros.put("idPeriodoPlanilla",
					filtro.getPersonalConcepto().getPeriodoPlanilla().getIdPeriodoPlanilla());
		}
		if (!esContador) {
			jpaql.append(
					" ORDER BY REPLACE(REPLACE(REPLACE(REPLACE(o.conceptosTipoPlanilla.conceptoPdt.tipo,'A','4') ,'T','3') ,'D','2') ,'I','1'), o.conceptosTipoPlanilla.orden ");
		}
		return createQuery(jpaql.toString(), parametros);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.rrhh.planilla.dao.local.
	 * DetallePersonalConceptoDaoLocal#contarListar{entity.getClassName()}(pe.com.
	 * builderp.core.service.rrhh.planilla.model.jpa.DetallePersonalConcepto)
	 */
	@Override
	public int contar(DetallePersonalConcepto filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.rrhh.planilla.dao.local.
	 * DetallePersonalConceptoDaoLocal#generarIdDetallePersonalConcepto()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

	@Override
	public Map<String, DetallePersonalConcepto> listarMap(String idPersonal, String idTipoPlanilla, String idPeriodo) {
		Map<String, DetallePersonalConcepto> resultado = new HashMap<>();
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		var ejecutarQuery = false;
		jpaql.append(
				"from DetallePersonalConcepto o left join fetch o.conceptosTipoPlanilla ctp join fetch ctp.conceptoPdt  ");
		jpaql.append(
				" left join fetch o.personalConcepto pc join fetch pc.personal per  left join fetch pc.periodoPlanilla  left join fetch pc.tipoPlanilla  where 1 = 1 ");
		if (StringUtil.isNotNullOrBlank(idPersonal)) {
			ejecutarQuery = true;
			jpaql.append(" and o.personalConcepto.personal.idPersonal =:idPersonal ");
			parametros.put("idPersonal", idPersonal);
		}
		if (StringUtil.isNotNullOrBlank(idTipoPlanilla)) {
			ejecutarQuery = true;
			jpaql.append(" and o.personalConcepto.tipoPlanilla.idTipoPlanilla =:idTipoPlanilla ");
			parametros.put("idTipoPlanilla", idTipoPlanilla);
		}
		if (StringUtil.isNotNullOrBlank(idPeriodo)) {
			ejecutarQuery = true;
			jpaql.append(" and o.personalConcepto.periodoPlanilla.idPeriodoPlanilla =:idPeriodo ");
			parametros.put("idPeriodo", idPeriodo);
		}

		if (ejecutarQuery) {
			var query = createQuery(jpaql.toString(), parametros);
			List<DetallePersonalConcepto> listaTemp = query.getResultList();
			for (DetallePersonalConcepto cronograma : listaTemp) {
				String key = cronograma.getConceptosTipoPlanilla().getConceptoPdt().getIdConceptoPdt();
				if (!resultado.containsKey(key)) {
					resultado.put(key, cronograma);
				}
			}
		}
		return resultado;
	}

}