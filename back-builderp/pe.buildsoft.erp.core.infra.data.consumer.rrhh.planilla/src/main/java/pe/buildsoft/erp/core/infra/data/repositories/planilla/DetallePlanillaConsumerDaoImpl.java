package pe.buildsoft.erp.core.infra.data.repositories.planilla;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import pe.buildsoft.erp.core.domain.entidades.planilla.DetallePlanilla;
import pe.buildsoft.erp.core.domain.entidades.planilla.Planilla;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.DetallePlanillaConsumerDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericPlanillaBatchDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class DetallePlanillaDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class DetallePlanillaConsumerDaoImpl extends GenericPlanillaBatchDAOImpl<String, DetallePlanilla>
		implements DetallePlanillaConsumerDaoLocal {

	@Override
	public List<DetallePlanilla> get(BaseSearch filtro) {
		var parametros = new HashMap<String, Object>();
		var ejecutarQuery = false;
		var jpaql = new StringBuilder();
		jpaql.append("from DetallePlanilla o left join fetch o.planilla pla  ");
		jpaql.append(" where 1 = 1  ");

		if (filtro.getIdAnhio() != null) {
			ejecutarQuery = true;
			jpaql.append(" and o.planilla.idAnhio  = :idAnhio ");
			parametros.put("idAnhio", filtro.getIdAnhio());
		}
		if (StringUtil.isNotNullOrBlank(filtro.getIdItemByPeriodoMes())) {
			ejecutarQuery = true;
			jpaql.append(" and o.planilla.idItemByPeriodoMes  = :itemByPeriodoMes ");
			parametros.put("itemByPeriodoMes", filtro.getIdItemByPeriodoMes());
		}
		if (StringUtil.isNotNullOrBlank(filtro.getIdItemByTipoTrabajador())) {
			ejecutarQuery = true;
			jpaql.append(" and o.planilla.idItemByTipoTrabajador  = :idItemTipoPersonal ");
			parametros.put("idItemTipoPersonal", filtro.getIdItemByTipoTrabajador());
		}
		if (StringUtil.isNotNullOrBlank(filtro.getIdTipoPlanilla())) {
			ejecutarQuery = true;
			jpaql.append(" and o.planilla.tipoPlanilla.idTipoPlanilla = :idTipoPlanilla ");
			parametros.put("idTipoPlanilla", filtro.getIdTipoPlanilla());
		}
		if (StringUtil.isNotNullOrBlank(filtro.getIdPersonal())) {
			ejecutarQuery = true;
			jpaql.append(" and o.idPersonal = :idPersonal ");
			parametros.put("idPersonal", filtro.getIdPersonal());
		}

		/*if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(
					" and (TRANSLATE(UPPER(o.personal.nombres || ' ' || o.personal.apellidoPaterno || ' ' || o.personal.apellidoMaterno ), :discriminaTildeMAC, :discriminaTildeMAT) like UPPER(translate(:search, :discriminaTildeMIC, :discriminaTildeMIT)) or (upper(o.personal.codigoUnico) like :search) or (upper(o.personal.nroDoc) like :search) )");
			parametros.putAll(obtenerParametroDiscriminarTilde());
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		}*/

		if (ejecutarQuery) {
			var query = createQuery(jpaql.toString(), parametros);
			return query.getResultList();
		}
		return Collections.emptyList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.rrhh.planilla.dao.local.DetallePlanillaDaoLocal#
	 * generarIdDetallePlanilla()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

	@Override
	public boolean eliminar(Planilla filtro) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		jpaql.append(" delete from DetallePlanilla o ");
		jpaql.append("  where exists ( ");
		jpaql.append("   select dp.idDetallePlanilla from DetallePlanilla dp ");
		jpaql.append("   where dp.idDetallePlanilla=o.idDetallePlanilla  ");
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