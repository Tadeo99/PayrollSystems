package pe.buildsoft.erp.core.infra.data.repositories.matricula;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.matricula.DetMatricula;
import pe.buildsoft.erp.core.domain.interfaces.repositories.matricula.DetMatriculaDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericMatriculaDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class DetMatriculaDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:55 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class DetMatriculaDaoImpl extends GenericMatriculaDAOImpl<String, DetMatricula> implements DetMatriculaDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.matricula.dao.local.DetMatriculaDaoLocal#
	 * listarDetMatricula(pe.com.builderp.core.service.matricula.model.jpa.
	 * DetMatricula)
	 */
	@Override
	public List<DetMatricula> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	/**
	 * Generar query lista DetMatricula.
	 *
	 * @param DetMatriculaDTO el detMatricula
	 * @param esContador      el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idDetMatricula) from DetMatricula o where 1=1 ");
		} else {
			jpaql.append(" select o from DetMatricula o where 1=1 ");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.descripcionCurso) like :search ");
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		}
		if (!esContador) {
			jpaql.append(" ORDER BY 1 ");
		}
		return createQuery(jpaql.toString(), parametros);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.matricula.dao.local.DetMatriculaDaoLocal#
	 * contarListar{entity.getClassName()}(pe.com.builderp.core.service.matricula.
	 * model.jpa.DetMatriculaDTO)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.matricula.dao.local.DetMatriculaDaoLocal#
	 * generarIdDetMatricula()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

	@Override
	public List<DetMatricula> listar(String idMatricula) {
		var parametros = new HashMap<String, Object>();
		var ejecutarQuery = false;
		var jpaql = new StringBuilder();
		jpaql.append(
				"from DetMatricula dm left join fetch dm.matricula mat  join fetch mat.cargaAcademica car  left join fetch dm.detMallaCurricular dte  left join fetch car.itemByTurno where 1 = 1 ");
		if (StringUtil.isNotNullOrBlank(idMatricula)) {
			ejecutarQuery = true;
			jpaql.append(" and dm.matricula.idMatricula=:idMatricula ");
			parametros.put("idMatricula", idMatricula);
		}
		jpaql.append(" order by dm.descripcionCurso");
		if (ejecutarQuery) {
			var query = createQuery(jpaql.toString(), parametros);
			return query.getResultList();
		}
		return Collections.emptyList();
	}
}