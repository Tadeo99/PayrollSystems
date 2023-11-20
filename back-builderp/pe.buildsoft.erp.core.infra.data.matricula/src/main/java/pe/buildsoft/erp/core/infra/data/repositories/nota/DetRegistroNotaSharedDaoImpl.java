package pe.buildsoft.erp.core.infra.data.repositories.nota;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import pe.buildsoft.erp.core.domain.entidades.nota.DetRegistroNota;
import pe.buildsoft.erp.core.domain.interfaces.repositories.nota.DetRegistroNotaSharedDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericMatriculaDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class DetRegistroNotaDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Jun 10 00:13:46 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class DetRegistroNotaSharedDaoImpl extends GenericMatriculaDAOImpl<String, DetRegistroNota>
		implements DetRegistroNotaSharedDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.nota.dao.local.DetRegistroNotaDaoLocal#
	 * generarIdDetRegistroNota()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

	@Override
	public List<DetRegistroNota> listar(String idRegistroNota) {
		var parametros = new HashMap<String, Object>();
		var ejecutarQuery = false;
		var jpaql = new StringBuilder();
		jpaql.append("from DetRegistroNota drn where 1 = 1 ");

		if (StringUtil.isNotNullOrBlank(idRegistroNota)) {
			ejecutarQuery = true;
			jpaql.append(" and drn.registroNota.idRegistroNota =:idRegistroNota  ");
			parametros.put("idRegistroNota", idRegistroNota);
		}
		if (ejecutarQuery) {
			var query = createQuery(jpaql.toString(), parametros);
			return query.getResultList();
		}
		return Collections.emptyList();
	}
}