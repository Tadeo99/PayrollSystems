package pe.buildsoft.erp.core.infra.data.repositories.nota;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import pe.buildsoft.erp.core.domain.entidades.nota.RegistroNota;
import pe.buildsoft.erp.core.domain.interfaces.repositories.nota.RegistroNotaSharedDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericMatriculaDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

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
public class RegistroNotaSharedDaoImpl extends GenericMatriculaDAOImpl<String, RegistroNota>
		implements RegistroNotaSharedDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.nota.dao.local.RegistroNotaDaoLocal#
	 * generarIdRegistroNota()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

	@Override
	public RegistroNota get(String idMatricula) {
		var parametros = new HashMap<String, Object>();
		var ejecutarQuery = false;
		var jpaql = new StringBuilder();
		jpaql.append(" from RegistroNota o where 1 = 1 ");
		if (StringUtil.isNotNullOrBlank(idMatricula)) {
			jpaql.append(" and o.matricula = :idMatricula ");
			parametros.put("idMatricula", idMatricula);
			ejecutarQuery = true;
		}
		if (ejecutarQuery) {
			var query = createQuery(jpaql.toString(), parametros);
			List<RegistroNota> resulTemp = query.getResultList();
			if (resulTemp != null && !resulTemp.isEmpty()) {
				return resulTemp.get(0);
			}
		}
		return null;
	}
}