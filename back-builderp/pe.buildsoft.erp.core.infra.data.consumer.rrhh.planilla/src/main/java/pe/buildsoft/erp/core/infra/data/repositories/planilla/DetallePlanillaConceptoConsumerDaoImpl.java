package pe.buildsoft.erp.core.infra.data.repositories.planilla;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import pe.buildsoft.erp.core.domain.entidades.planilla.DetallePlanillaConcepto;
import pe.buildsoft.erp.core.domain.entidades.planilla.Planilla;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.DetallePlanillaConceptoConsumerDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericPlanillaBatchDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class DetallePlanillaConceptoDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class DetallePlanillaConceptoConsumerDaoImpl extends GenericPlanillaBatchDAOImpl<String, DetallePlanillaConcepto>
		implements DetallePlanillaConceptoConsumerDaoLocal {

	@Override
	public List<DetallePlanillaConcepto> get(DetallePlanillaConcepto filtro) {
		var parametros = new HashMap<String, Object>();
		var ejecutarQuery = false;
		var jpaql = new StringBuilder();
		jpaql.append(
				"from DetallePlanillaConcepto o left join fetch o.detallePlanilla left join fetch o.concepto ct left join fetch ct.conceptoPdt where 1 = 1 ");

		if (StringUtil.isNotNullOrBlank(filtro.getDetallePlanilla().getIdDetallePlanilla())) {
			ejecutarQuery = true;
			jpaql.append(" and o.detallePlanilla.idDetallePlanilla  = :idDetallePlanilla ");
			parametros.put("idDetallePlanilla", filtro.getDetallePlanilla().getIdDetallePlanilla());
		}

		if (StringUtil.isNotNullOrBlank(filtro.getConcepto().getConceptoPdt().getTipo())) {
			ejecutarQuery = true;
			if (filtro.getConcepto().getConceptoPdt().getTipo().equals("D")) {
				jpaql.append(" and o.concepto.conceptoPdt.tipo in (:tipoC,'T')");
			} else {
				jpaql.append(" and o.concepto.conceptoPdt.tipo  = :tipoC ");
			}
			parametros.put("tipoC", filtro.getConcepto().getConceptoPdt().getTipo());
		}

		if (ejecutarQuery) {
			var query = createQuery(jpaql.toString(), parametros);
			return query.getResultList();
		}
		return Collections.emptyList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.rrhh.planilla.dao.local.
	 * DetallePlanillaConceptoDaoLocal#generarIdDetallePlanillaConcepto()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

	@Override
	public boolean eliminar(Planilla filtro) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		jpaql.append(" delete from DetallePlanillaConcepto o ");
		jpaql.append("  where exists ( ");
		jpaql.append("   select dp.idDetallePlanilla from DetallePlanilla dp ");
		jpaql.append("   where dp.idDetallePlanilla=o.detallePlanilla.idDetallePlanilla  ");
		jpaql.append("   and dp.planilla.idAnhio  = :idAnhio ");
		jpaql.append("   and dp.planilla.idItemByPeriodoMes  = :itemByPeriodoMes ");
		jpaql.append("   and dp.planilla.tipoPlanilla.idTipoPlanilla = :idTipoPlanilla ");
		jpaql.append(" ) ");
		parametros.put("itemByPeriodoMes", filtro.getIdItemByPeriodoMes());
		parametros.put("idAnhio", filtro.getIdAnhio());
		parametros.put("idTipoPlanilla", filtro.getTipoPlanilla().getIdTipoPlanilla());
		var query = createQuery(jpaql.toString(), parametros);
		return query.executeUpdate() > 0;
	}
}