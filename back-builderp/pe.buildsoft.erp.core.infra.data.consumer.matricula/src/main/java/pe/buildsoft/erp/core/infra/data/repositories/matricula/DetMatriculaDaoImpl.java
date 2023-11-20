package pe.buildsoft.erp.core.infra.data.repositories.matricula;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import pe.buildsoft.erp.core.domain.entidades.matricula.DetMatricula;
import pe.buildsoft.erp.core.domain.interfaces.repositories.matricula.DetMatriculaDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericMatriculaBatchDAOImpl;
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
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@TransactionManagement(TransactionManagementType.BEAN)
public class DetMatriculaDaoImpl extends GenericMatriculaBatchDAOImpl<String, DetMatricula> implements DetMatriculaDaoLocal {

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