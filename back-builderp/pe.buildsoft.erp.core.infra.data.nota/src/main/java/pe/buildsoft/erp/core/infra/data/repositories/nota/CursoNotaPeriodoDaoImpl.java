package pe.buildsoft.erp.core.infra.data.repositories.nota;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.nota.CursoNotaPeriodo;
import pe.buildsoft.erp.core.domain.interfaces.repositories.nota.CursoNotaPeriodoDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericNotaDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class CursoNotaPeriodoDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Jun 10 00:13:45 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class CursoNotaPeriodoDaoImpl extends GenericNotaDAOImpl<String, CursoNotaPeriodo>
		implements CursoNotaPeriodoDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.nota.dao.local.CursoNotaPeriodoDaoLocal#
	 * listarCursoNotaPeriodo(pe.com.builderp.core.service.nota.model.jpa.
	 * CursoNotaPeriodo)
	 */
	@Override
	public List<CursoNotaPeriodo> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		return query.getResultList();
	}

	/**
	 * Generar query lista CursoNotaPeriodo.
	 *
	 * @param CursoNotaPeriodoDTO el cursoNotaPeriodo
	 * @param esContador          el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idCursoNotaCompProm) from CursoNotaPeriodo o where 1=1 ");
		} else {
			jpaql.append(" select o from CursoNotaPeriodo o where 1=1 ");
		}
		if (!StringUtil.isNullOrEmptyNumeric(filtro.getNota())) {
			jpaql.append(" and o.nota = :nota ");
			parametros.put("nota", filtro.getNota());
		}
		if (!esContador) {
			jpaql.append(" ORDER BY " + filtro.getSortFields() + " " + filtro.getSortDirections());
		}
		return createQuery(jpaql.toString(), parametros);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.nota.dao.local.CursoNotaPeriodoDaoLocal#
	 * contarListar{entity.getClassName()}(pe.com.builderp.core.service.nota.model.
	 * jpa.CursoNotaPeriodoDTO)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.nota.dao.local.CursoNotaPeriodoDaoLocal#
	 * generarIdCursoNotaPeriodo()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

	@Override
	public void updateNota(CursoNotaPeriodo cursoNota) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		jpaql.append("update CursoNotaPeriodo set nota =:notaParam where 1 = 1 ");
		jpaql.append(" and idCursoNotaCompProm =:idCursoNotaParam ");
		parametros.put("idCursoNotaParam", cursoNota.getIdCursoNotaCompProm());
		parametros.put("notaParam", cursoNota.getNota());
		var query = createQuery(jpaql.toString(), parametros);
		query.executeUpdate();
	}

	@Override
	public Map<String, List<CursoNotaPeriodo>> getMap(List<String> listaIdDetRegistroNota) {
		Map<String, List<CursoNotaPeriodo>> resultado = new HashMap<>();
		var parametros = new HashMap<String, Object>();
		var ejecutarQuery = false;
		var jpaql = new StringBuilder();
		jpaql.append(
				"from CursoNotaPeriodo cn left join fetch cn.criterioEvaluacion cpn  left join fetch cn.detRegistroNota where 1 = 1 ");
		if (listaIdDetRegistroNota != null && !listaIdDetRegistroNota.isEmpty()) {
			ejecutarQuery = true;
			jpaql.append(" and cn.detRegistroNota.idDetRegistroNota in (:listaIdDetRegistroNota) ");
			parametros.put("listaIdDetRegistroNota", listaIdDetRegistroNota);
		}
		if (ejecutarQuery) {
			var query = createQuery(jpaql.toString(), parametros);
			List<CursoNotaPeriodo> listaCursoNota = query.getResultList();
			for (var idDetRegistroNota : listaIdDetRegistroNota) {
				if (!resultado.containsKey(idDetRegistroNota)) {
					List<CursoNotaPeriodo> listaCursoNotaValue = new ArrayList<>();
					for (var objCursoNota : listaCursoNota) {
						if (idDetRegistroNota.equals(objCursoNota.getDetRegistroNota().getIdDetRegistroNota())) {
							listaCursoNotaValue.add(objCursoNota);
						}
					}
					resultado.put(idDetRegistroNota, listaCursoNotaValue);
				}
			}

		}
		return resultado;
	}

}