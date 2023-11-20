package pe.buildsoft.erp.core.infra.data.repositories.nota;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.nota.CursoNotaUnidad;
import pe.buildsoft.erp.core.domain.interfaces.repositories.nota.CursoNotaUnidadDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericNotaDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class CursoNotaUnidadDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Jun 10 00:13:45 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class CursoNotaUnidadDaoImpl extends GenericNotaDAOImpl<String, CursoNotaUnidad>
		implements CursoNotaUnidadDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.nota.dao.local.CursoNotaUnidadDaoLocal#
	 * listarCursoNotaUnidad(pe.com.builderp.core.service.nota.model.jpa.
	 * CursoNotaUnidad)
	 */
	@Override
	public List<CursoNotaUnidad> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		return query.getResultList();
	}

	/**
	 * Generar query lista CursoNotaUnidad.
	 *
	 * @param CursoNotaUnidadDTO el cursoNotaUnidad
	 * @param esContador         el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idCursoNota) from CursoNotaUnidad o where 1=1 ");
		} else {
			jpaql.append(" select o from CursoNotaUnidad o where 1=1 ");
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
	 * @see pe.com.builderp.core.service.nota.dao.local.CursoNotaUnidadDaoLocal#
	 * contarListar{entity.getClassName()}(pe.com.builderp.core.service.nota.model.
	 * jpa.CursoNotaUnidadDTO)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.nota.dao.local.CursoNotaUnidadDaoLocal#
	 * generarIdCursoNotaUnidad()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

	@Override
	public void updateNota(CursoNotaUnidad obj) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		jpaql.append("update CursoNotaUnidad set nota =:notaParam where 1 = 1 ");
		jpaql.append(" and idCursoNota =:idCursoNota ");
		parametros.put("idCursoNota", obj.getIdCursoNota());
		parametros.put("notaParam", obj.getNota());
		var query = createQuery(jpaql.toString(), parametros);
		query.executeUpdate();
	}

	@Override
	public Map<String, List<CursoNotaUnidad>> getMap(List<String> listaIdDetRegistroNota) {
		Map<String, List<CursoNotaUnidad>> resultado = new HashMap<>();
		var parametros = new HashMap<String, Object>();
		var ejecutarQuery = false;
		var jpaql = new StringBuilder();
		jpaql.append(
				"from CursoNotaUnidad cn left join fetch cn.criterioEvaluacion cpn  left join fetch cn.detRegistroNota where 1 = 1 ");
		if (listaIdDetRegistroNota != null && !listaIdDetRegistroNota.isEmpty()) {
			ejecutarQuery = true;
			jpaql.append(" and cn.detRegistroNota.idDetRegistroNota in (:listaIdDetRegistroNota) ");
			parametros.put("listaIdDetRegistroNota", listaIdDetRegistroNota);
		}
		if (ejecutarQuery) {
			var query = createQuery(jpaql.toString(), parametros);
			List<CursoNotaUnidad> listaCursoNota = query.getResultList();
			for (var idDetRegistroNota : listaIdDetRegistroNota) {
				if (!resultado.containsKey(idDetRegistroNota)) {
					List<CursoNotaUnidad> listaCursoNotaValue = new ArrayList<>();
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