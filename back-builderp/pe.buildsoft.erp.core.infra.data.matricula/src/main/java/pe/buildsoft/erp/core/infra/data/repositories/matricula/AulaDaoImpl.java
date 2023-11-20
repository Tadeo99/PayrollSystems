package pe.buildsoft.erp.core.infra.data.repositories.matricula;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.matricula.Aula;
import pe.buildsoft.erp.core.domain.interfaces.repositories.matricula.AulaDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericMatriculaDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * La Class AulaDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:55 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class AulaDaoImpl extends GenericMatriculaDAOImpl<Long, Aula> implements AulaDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.matricula.dao.local.AulaDaoLocal#listarAula(pe.
	 * com.builderp.core.service.matricula.model.jpa.Aula)
	 */
	@Override
	public List<Aula> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	/**
	 * Generar query lista Aula.
	 *
	 * @param AulaDTO    el aula
	 * @param esContador el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idAula) from Aula o where 1=1 ");
		} else {
			jpaql.append(" select o from Aula o left join fetch o.pabellon where 1=1 ");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getIdEntidadSelect())) {
			jpaql.append(" and o.pabellon.entidad =:idEntidadSelect");
			parametros.put("idEntidadSelect", filtro.getIdEntidadSelect());
		}
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and (upper(o.descripcion) like :search) or (upper( o.abreviatura ) like :search) ");
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		}
		if (!esContador) {
			jpaql.append(" ORDER BY " + filtro.getSortFields() + " " + filtro.getSortDirections());
		}
		return createQuery(jpaql.toString(), parametros);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.matricula.dao.local.AulaDaoLocal#contarListar{
	 * entity.getClassName()}(pe.com.builderp.core.service.matricula.model.jpa.
	 * AulaDTO)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.matricula.dao.local.AulaDaoLocal#generarIdAula()
	 */
	@Override
	public Long generarIdAula() {
		var resultado = 1L;
		var query = createQuery("select max(o.idAula) from Aula o", null);
		var listLong = query.getResultList();
		if (listLong != null && !listLong.isEmpty() && listLong.get(0) != null) {
			Long ultimoIdGenerado = Long.valueOf(listLong.get(0).toString());
			if (!StringUtil.isNullOrEmpty(ultimoIdGenerado)) {
				resultado = resultado + ultimoIdGenerado;
			}
		}
		return resultado;
	}

}