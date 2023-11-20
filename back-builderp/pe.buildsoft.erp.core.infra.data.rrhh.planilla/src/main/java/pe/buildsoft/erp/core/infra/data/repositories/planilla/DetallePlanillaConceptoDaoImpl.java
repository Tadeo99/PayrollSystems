package pe.buildsoft.erp.core.infra.data.repositories.planilla;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import pe.buildsoft.erp.core.domain.entidades.planilla.DetallePlanillaConcepto;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.DetallePlanillaConceptoDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericPlanillaDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

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
public class DetallePlanillaConceptoDaoImpl extends GenericPlanillaDAOImpl<String, DetallePlanillaConcepto>
		implements DetallePlanillaConceptoDaoLocal {

	@Override
	public List<DetallePlanillaConcepto> get(DetallePlanillaConcepto filtro) {
		var parametros = new HashMap<String, Object>();
		var ejecutarQuery = false;
		var jpaql = new StringBuilder();
		jpaql.append("from DetallePlanillaConcepto o left join fetch ");
		jpaql.append(" o.detallePlanilla left join fetch o.concepto ct left join fetch ct.conceptoPdt where 1 = 1");

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
}