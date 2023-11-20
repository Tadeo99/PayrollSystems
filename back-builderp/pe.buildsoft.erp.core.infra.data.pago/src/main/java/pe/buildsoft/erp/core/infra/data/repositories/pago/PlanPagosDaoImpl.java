package pe.buildsoft.erp.core.infra.data.repositories.pago;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import pe.buildsoft.erp.core.domain.entidades.pago.PlanPagos;
import pe.buildsoft.erp.core.domain.interfaces.repositories.pago.PlanPagosDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericPagoDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class PlanPagosDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 21:45:31 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class PlanPagosDaoImpl extends GenericPagoDAOImpl<String, PlanPagos> implements PlanPagosDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.pago.dao.local.PlanPagosDaoLocal#listarPlanPagos
	 * (pe.com.builderp.core.service.pago.model.jpa.PlanPagos)
	 */
	@Override
	public PlanPagos get(PlanPagos filtro) {
		PlanPagos resultado = null;
		var parametros = new HashMap<String, Object>();
		var ejecutarQuery = false;
		var jpaql = new StringBuilder();
		jpaql.append("from PlanPagos o where 1 = 1 ");
		if (StringUtil.isNotNullOrBlank(filtro.getIdAnhio())) {
			ejecutarQuery = true;
			jpaql.append(" and o.idAnhio = :idAnhio ");
			parametros.put("idAnhio", filtro.getIdAnhio());
		}
		if (StringUtil.isNotNullOrBlank(filtro.getIdPeriodo())) {
			ejecutarQuery = true;
			jpaql.append(" and o.idPeriodo = :idPeriodo ");
			parametros.put("idPeriodo", filtro.getIdPeriodo());
		}
		if (StringUtil.isNotNullOrBlank(filtro.getIdAlumno())) {
			ejecutarQuery = true;
			jpaql.append(" and o.idAlumno = :idAlumno ");
			parametros.put("idAlumno", filtro.getIdAlumno());
		}
		if (ejecutarQuery) {
			var query = createQuery(jpaql.toString(), parametros);
			List<PlanPagos> listaAlumno = query.getResultList();
			if (listaAlumno != null && !listaAlumno.isEmpty()) {
				resultado = listaAlumno.get(0);
			}
		}

		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.pago.dao.local.PlanPagosDaoLocal#
	 * generarIdPlanPagos()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

}