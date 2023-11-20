package pe.buildsoft.erp.core.infra.data.repositories.planilla;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import pe.buildsoft.erp.core.domain.entidades.planilla.EPSPersonal;
import pe.buildsoft.erp.core.domain.entidades.planilla.PeriodoPlanilla;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.EPSPersonalConsumerDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericPlanillaBatchDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.CollectionUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class EPSPersonalConsumerDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class EPSPersonalConsumerDaoImpl extends GenericPlanillaBatchDAOImpl<String, EPSPersonal>
		implements EPSPersonalConsumerDaoLocal {

	@Override
	public boolean eliminar(PeriodoPlanilla periodo) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		jpaql.append("delete from EPSPersonal o  where 1 = 1 ");
		if (!StringUtil.isNullOrEmptyNumeric(periodo.getIdItemByPeriodoMes())) {
			jpaql.append(" and o.idItemByMes = :itemByMes ");
			parametros.put("itemByMes", periodo.getIdItemByPeriodoMes());
		}
		if (!StringUtil.isNullOrEmptyNumeric(periodo.getIdAnhio())
				&& StringUtil.isNotNullOrBlank(periodo.getIdAnhio())) {
			jpaql.append(" and o.idAnhio = :idAnhio ");
			parametros.put("idAnhio", periodo.getIdAnhio());
		}
		return createQuery(jpaql.toString(), parametros).executeUpdate() > 0;
	}

	@Override
	public Map<String, EPSPersonal> listarMap(BaseSearch filtro) {
		var resultado = new HashMap<String, EPSPersonal>();
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		jpaql.append("select o from EPSPersonal o  where 1 = 1 ");
		if (!StringUtil.isNullOrEmptyNumeric(filtro.getIdItemByEps())) {
			jpaql.append(" and o.idItemByEps = :idItemByEps ");
			parametros.put("idItemByEps", filtro.getIdItemByEps());
		}
		if (!StringUtil.isNullOrEmptyNumeric(filtro.getIdAnhio()) && StringUtil.isNotNullOrBlank(filtro.getIdAnhio())) {
			jpaql.append(" and o.idAnhio = :idAnhio ");
			parametros.put("idAnhio", filtro.getIdAnhio());
		}
		if (!CollectionUtil.isEmpty(filtro.getListaIdPersonal())) {
			jpaql.append(" and o.idPersonal in (:listaIdPersonal) ");
			parametros.put("listaIdPersonal", filtro.getListaIdPersonal());
		}
		List<EPSPersonal> listaTmp = createQuery(jpaql.toString(), parametros).getResultList();
		for (var obj : listaTmp) {
			resultado.put(obj.getIdPersonal(), obj);
		}
		return resultado;
	}

	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}
}