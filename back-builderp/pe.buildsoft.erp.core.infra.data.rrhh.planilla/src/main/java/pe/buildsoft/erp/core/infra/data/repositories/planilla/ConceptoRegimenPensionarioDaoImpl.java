package pe.buildsoft.erp.core.infra.data.repositories.planilla;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.planilla.ConceptoRegimenPensionario;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.ConceptoRegimenPensionarioDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericPlanillaDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class ConceptoRegimenPensionarioDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class ConceptoRegimenPensionarioDaoImpl extends GenericPlanillaDAOImpl<String, ConceptoRegimenPensionario>
		implements ConceptoRegimenPensionarioDaoLocal {

	@Override
	public List<ConceptoRegimenPensionario> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		if (query != null) {
			query.setFirstResult(filtro.getStartRow());
			query.setMaxResults(filtro.getOffSet());
			return query.getResultList();
		}
		return new ArrayList<>();
	}

	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		boolean ejecutarQuery = true;
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append("select count(o.idConceptoRegimenPensionario) from ConceptoRegimenPensionario o  ");
		} else {
			jpaql.append("select o from ConceptoRegimenPensionario o  ");
		}
		jpaql.append(" where 1 = 1");
		if (filtro != null) {
			if (!StringUtil.isNullOrEmptyNumeric(filtro.getIdAnhio())) {
				ejecutarQuery = true;
				jpaql.append(" and o.idAnhio =:idAnhio ");
				parametros.put("idAnhio", filtro.getIdAnhio());
			}
			if (!StringUtil.isNullOrEmptyNumeric(filtro.getIdItemByMesByDevengue())) {
				ejecutarQuery = true;
				jpaql.append(" and o.idItemByMesByDevengue = :idItem ");
				parametros.put("idItem", filtro.getIdItemByMesByDevengue());
			}
			if (!StringUtil.isNullOrEmptyNumeric(filtro.getIdItemByRegimenPensionario())) {
				ejecutarQuery = true;
				jpaql.append(" and o.idItemByRegimenPensionario = :idItemByRegimenPensionario ");
				parametros.put("idItemByRegimenPensionario", filtro.getIdItemByRegimenPensionario());
			}
		}
		if (!esContador) {
			// jpaql.append(" order by o.itemByRegimenPensionario.nombre asc ");
			jpaql.append(" ORDER BY " + filtro.getSortFields() + " " + filtro.getSortDirections());
		}
		if (ejecutarQuery) {
			return createQuery(jpaql.toString(), parametros);
		}
		return null;
	}

	@Override
	public Map<Long, ConceptoRegimenPensionario> listarMap(Long idMesDevengado, Long anhio) {
		Map<Long, ConceptoRegimenPensionario> resultado = new HashMap<>();
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		jpaql.append("select o from ConceptoRegimenPensionario o ");
		jpaql.append("  where 1 = 1");
		if (anhio != null && anhio > 0) {
			jpaql.append(" and o.anhio.idAnhio =:idAnhio ");
			parametros.put("idAnhio", anhio);
		}
		if (idMesDevengado != null && idMesDevengado > 0) {
			jpaql.append(" and o.idItemByMesByDevengue = :idItem ");
			parametros.put("idItem", idMesDevengado);
		}
		jpaql.append(" order by o.idAnhio , o.idItemByMesByDevengue ");
		List<ConceptoRegimenPensionario> listaTmp = createQuery(jpaql.toString(), parametros).getResultList();
		for (ConceptoRegimenPensionario obj : listaTmp) {
			resultado.put(obj.getIdItemByRegimenPensionario(), obj);
		}
		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.rrhh.planilla.dao.local.
	 * ConceptoRegimenPensionarioDaoLocal#generarIdConceptoRegimenPensionario()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		if (query != null) {
			return ((Long) query.getSingleResult()).intValue();
		}
		return 0;
	}
}