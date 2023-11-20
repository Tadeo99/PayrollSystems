package pe.buildsoft.erp.core.infra.data.repositories.matricula;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import pe.buildsoft.erp.core.domain.entidades.matricula.Matricula;
import pe.buildsoft.erp.core.domain.entidades.matricula.vo.FichaMatriculaVO;
import pe.buildsoft.erp.core.domain.interfaces.repositories.matricula.MatriculaDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericMatriculaBatchDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.utilitario.CollectionUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * La Class MatriculaDaoImpl.
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
public class MatriculaDaoImpl extends GenericMatriculaBatchDAOImpl<String, Matricula> implements MatriculaDaoLocal {

	@Override
	public Matricula get(FichaMatriculaVO filtro) {
		Matricula resultado = new Matricula();
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		jpaql.append(
				"from Matricula m   left join fetch m.cargaAcademica  left join fetch m.periodo  where m.alumno.idAlumno=:idAlumno");
		jpaql.append(" and  m.cargaAcademica.anhio.idAnhio =:idAnhio ");
		parametros.put("idAlumno", filtro.getIdAlumno());
		if (!StringUtil.isNullOrEmptyNumeric(filtro.getIdTurno())) {
			jpaql.append(" and m.cargaAcademica.itemByTurno.idItem =:idTurno ");
			parametros.put("idTurno", filtro.getIdTurno());
		}

		if (!StringUtil.isNullOrEmptyNumeric(filtro.getIdPeriodo())) {
			jpaql.append(" and m.periodo.idPeriodo =:idPeriodo ");
			parametros.put("idPeriodo", filtro.getIdPeriodo());
		}

		parametros.put("idAnhio", filtro.getIdAnhio());
		var query = createQuery(jpaql.toString(), parametros);
		List<Matricula> matriculaActual = query.getResultList();
		if (!CollectionUtil.isEmpty(matriculaActual)) {
			resultado = matriculaActual.get(0);
		}
		return resultado;
	}

}