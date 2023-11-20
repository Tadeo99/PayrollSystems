package pe.buildsoft.erp.core.infra.data.repositories.nota;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.nota.CursoNotaUnidadProm;
import pe.buildsoft.erp.core.domain.interfaces.repositories.nota.CursoNotaUnidadPromDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericNotaDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class CursoNotaUnidadPromDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Jun 10 00:13:46 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class CursoNotaUnidadPromDaoImpl extends GenericNotaDAOImpl<String, CursoNotaUnidadProm>
		implements CursoNotaUnidadPromDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.nota.dao.local.CursoNotaUnidadPromDaoLocal#
	 * listarCursoNotaUnidadProm(pe.com.builderp.core.service.nota.model.jpa.
	 * CursoNotaUnidadProm)
	 */
	@Override
	public List<CursoNotaUnidadProm> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		return query.getResultList();
	}

	/**
	 * Generar query lista CursoNotaUnidadProm.
	 *
	 * @param CursoNotaUnidadProm el cursoNotaUnidadProm
	 * @param esContador          el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idCursoNotaUnidad) from CursoNotaUnidadProm o where 1=1 ");
		} else {
			jpaql.append(" select o from CursoNotaUnidadProm o where 1=1 ");
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
	 * @see pe.com.builderp.core.service.nota.dao.local.CursoNotaUnidadPromDaoLocal#
	 * contarListar{entity.getClassName()}(pe.com.builderp.core.service.nota.model.
	 * jpa.CursoNotaUnidadProm)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.nota.dao.local.CursoNotaUnidadPromDaoLocal#
	 * generarIdCursoNotaUnidadProm()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

	@Override
	public Map<String, List<CursoNotaUnidadProm>> getMap(List<String> listaIdDetRegistroNota) {
		Map<String, List<CursoNotaUnidadProm>> resultado = new HashMap<>();
		var parametros = new HashMap<String, Object>();
		var ejecutarQuery = false;
		var jpaql = new StringBuilder();
		jpaql.append("from CursoNotaUnidadProm cn  left join fetch cn.detRegistroNota where 1 = 1 ");
		if (listaIdDetRegistroNota != null && !listaIdDetRegistroNota.isEmpty()) {
			ejecutarQuery = true;
			jpaql.append(" and cn.detRegistroNota.idDetRegistroNota in (:listaIdDetRegistroNota) ");
			parametros.put("listaIdDetRegistroNota", listaIdDetRegistroNota);
		}
		if (ejecutarQuery) {
			var query = createQuery(jpaql.toString(), parametros);
			List<CursoNotaUnidadProm> listaCursoNota = query.getResultList();
			for (var idDetRegistroNota : listaIdDetRegistroNota) {
				if (!resultado.containsKey(idDetRegistroNota)) {
					List<CursoNotaUnidadProm> listaCursoNotaValue = new ArrayList<>();
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

	@Override
	public CursoNotaUnidadProm find(String idDetRegistroNota) {
		CursoNotaUnidadProm resultado = null;
		var jpaql = new StringBuilder();
		jpaql.append("from CursoNotaUnidadProm cn  left join fetch cn.detRegistroNota ");
		jpaql.append(" where cn.detRegistroNota.idDetRegistroNota =:idDetRegistroNota ");
		var query = createQuery(jpaql.toString(), null);
		query.setParameter("idDetRegistroNota", idDetRegistroNota);
		List<CursoNotaUnidadProm> listaAlumno = query.getResultList();
		if (listaAlumno != null && !listaAlumno.isEmpty()) {
			resultado = listaAlumno.get(0);
		}
		return resultado;
	}
}