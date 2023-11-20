package pe.buildsoft.erp.core.infra.data.repositories.matricula;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import pe.buildsoft.erp.core.domain.entidades.matricula.DetMatricula;
import pe.buildsoft.erp.core.domain.interfaces.repositories.matricula.DetMatriculaDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericNotaDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

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
public class DetMatriculaDaoImpl extends GenericNotaDAOImpl<String, DetMatricula> implements DetMatriculaDaoLocal {

	@Override
	public List<DetMatricula> get(String idDetMallaCurricular, String idAlumno) {
		var parametros = new HashMap<String, Object>();
		var ejecutarQuery = false;
		var jpaql = new StringBuilder();
		jpaql.append("from DetMatricula o left join fetch o.matricula mat left join fetch mat.alumno a   where 1 = 1 ");
		if (StringUtil.isNotNullOrBlank(idDetMallaCurricular)) {
			ejecutarQuery = true;
			jpaql.append(" and o.detMallaCurricular.idDetMallaCurricular=:idDetMallaCurricular ");
			parametros.put("idDetMallaCurricular", idDetMallaCurricular);
			if (StringUtil.isNotNullOrBlank(idAlumno)) {
				jpaql.append(" and a.idAlumno = :idAlumno ");
				parametros.put("idAlumno", idAlumno);
			}
		}
		jpaql.append(" order by o.matricula.alumno.apellidoPaterno asc");
		if (ejecutarQuery) {
			var query = createQuery(jpaql.toString(), parametros);
			return query.getResultList();
		}
		return Collections.emptyList();
	}

}