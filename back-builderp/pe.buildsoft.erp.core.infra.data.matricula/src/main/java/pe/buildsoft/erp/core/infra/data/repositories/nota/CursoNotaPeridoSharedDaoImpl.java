package pe.buildsoft.erp.core.infra.data.repositories.nota;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import pe.buildsoft.erp.core.domain.entidades.nota.CursoNotaPeriodoTemp;
import pe.buildsoft.erp.core.domain.interfaces.repositories.nota.CursoNotaPeiodoSharedDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericMatriculaDAOImpl;

/**
 * La Class RegistroNotaDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Jun 10 00:13:46 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class CursoNotaPeridoSharedDaoImpl extends GenericMatriculaDAOImpl<String, CursoNotaPeriodoTemp>
		implements CursoNotaPeiodoSharedDaoLocal {

	@Override
	public List<CursoNotaPeriodoTemp> get(List<String> listaIdDetRegistroNota) {
		var parametros = new HashMap<String, Object>();
		var ejecutarQuery = false;
		var jpaql = new StringBuilder();
		jpaql.append("from CursoNotaPeriodo cn where 1 = 1 ");
		if (listaIdDetRegistroNota != null && !listaIdDetRegistroNota.isEmpty()) {
			ejecutarQuery = true;
			jpaql.append(" and cn.detRegistroNota.idDetRegistroNota in (:listaIdDetRegistroNota) ");
			parametros.put("listaIdDetRegistroNota", listaIdDetRegistroNota);
		}
		if (ejecutarQuery) {
			var query = createQuery(jpaql.toString(), parametros);
			return query.getResultList();
		}
		return new ArrayList<>();
	}
}