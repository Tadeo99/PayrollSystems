package pe.buildsoft.erp.core.infra.data.repositories.planilla;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.planilla.Feriado;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.FeriadoConsumerDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericPlanillaBatchDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class FeriadoDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class FeriadoConsumerDaoImpl extends GenericPlanillaBatchDAOImpl<String, Feriado>
		implements FeriadoConsumerDaoLocal {

	@Override
	public Map<Long, Integer> listarMap(BaseSearch filtro) {
		var resultado = new HashMap<Long, Integer>();
		var query = generarQuery(filtro, false);
		List<Feriado> listaTmp = query.getResultList();
		int cantidad = 0;
		for (Feriado obj : listaTmp) {
			if (!resultado.containsKey(obj.getIdMes())) {
				cantidad++;
				resultado.put(obj.getIdMes(), cantidad);
			} else {
				cantidad = resultado.get(obj.getIdMes());
				cantidad++;
				resultado.put(obj.getIdMes(), cantidad);
			}
		}
		return resultado;
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
			jpaql.append(" select count(o.idFeriado) from Feriado o ");
		} else {
			jpaql.append(" select o from Feriado o   ");
		}
		jpaql.append("  where 1 = 1 ");
		if (!StringUtil.isNullOrEmpty(filtro.getEstado())) {
			jpaql.append(" and o.estado = :estado ");
			parametros.put("estado", filtro.getEstado());
		}
		if (!StringUtil.isNullOrEmpty(filtro.getIdAnhio())) {
			jpaql.append(" and o.idAnhio = :idAnhio ");
			parametros.put("idAnhio", filtro.getIdAnhio());
		}
		if (!StringUtil.isNullOrEmpty(filtro.getIdItemByMes())) {
			jpaql.append(" and o.idMes = :idMes ");
			parametros.put("idMes", filtro.getIdItemByMes());
		}
		if (!esContador) {
			// jpaql.append(" ORDER BY " + filtro.getSortFields() + " " +
			// filtro.getSortDirections());
		}
		return createQuery(jpaql.toString(), parametros);
	}

}