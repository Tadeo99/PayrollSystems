package pe.buildsoft.erp.core.infra.data.repositories.planilla;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.planilla.DetallePlanilla;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.DetallePlanillaDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericPlanillaDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.CollectionUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

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
public class DetallePlanillaDaoImpl extends GenericPlanillaDAOImpl<String, DetallePlanilla>
		implements DetallePlanillaDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.rrhh.planilla.dao.local.DetallePlanillaDaoLocal#
	 * listarDetallePlanilla(pe.com.builderp.core.service.rrhh.planilla.model.jpa.
	 * DetallePlanilla)
	 */
	@Override
	public List<DetallePlanilla> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		if (query != null) {
			if (filtro.getOffSet() > 0) {
				//query.setFirstResult(filtro.getStartRow());
				//query.setMaxResults(filtro.getOffSet());
			}
			return query.getResultList();
		}
		return Collections.emptyList();
	}

	/**
	 * Generar query lista DetallePlanilla.
	 *
	 * @param DetallePlanilla el detallePlanilla
	 * @param esContador      el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var ejecutarQuery = false;
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idDetallePlanilla) from DetallePlanilla o where 1=1 ");
		} else {
			jpaql.append(" from DetallePlanilla o left join fetch o.planilla pla");
			jpaql.append(" where 1=1 ");
		}
		if (!StringUtil.isNullOrEmptyNumeric(filtro.getIdAnhio())) {
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
		if (!CollectionUtil.isEmpty(filtro.getListaIdPersonal())) {
			jpaql.append(" and o.idPersonal in (:listaIdPersonal) ");
			parametros.put("listaIdPersonal", filtro.getListaIdPersonal());
		}
		/*
		 * if (!StringUtil.isNullOrEmpty(filtro.getSearch())) { jpaql.append(
		 * " and (TRANSLATE(UPPER(o.personal.nombres || ' ' || o.personal.apellidoPaterno || ' ' || o.personal.apellidoMaterno ), :discriminaTildeMAC, :discriminaTildeMAT) like UPPER(translate(:search, :discriminaTildeMIC, :discriminaTildeMIT)) or (upper(o.personal.codigoUnico) like :search) or (upper(o.personal.nroDoc) like :search) )"
		 * ); parametros.putAll(obtenerParametroDiscriminarTilde());
		 * parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%"); }
		 */
		if (!esContador) {
			jpaql.append(" ORDER BY o.idPersonal  ");
		}
		if (ejecutarQuery) {
			return createQuery(jpaql.toString(), parametros);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.rrhh.planilla.dao.local.DetallePlanillaDaoLocal#
	 * contarListar{entity.getClassName()}(pe.com.builderp.core.service.rrhh.
	 * planilla.model.jpa.DetallePlanilla)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		if (query != null) {
			return ((Long) query.getSingleResult()).intValue();
		}
		return 0;
	}

}