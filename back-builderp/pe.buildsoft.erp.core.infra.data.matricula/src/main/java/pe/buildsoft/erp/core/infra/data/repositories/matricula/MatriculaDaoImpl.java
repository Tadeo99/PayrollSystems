package pe.buildsoft.erp.core.infra.data.repositories.matricula;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.matricula.Matricula;
import pe.buildsoft.erp.core.domain.interfaces.repositories.matricula.MatriculaDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericMatriculaDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.CollectionUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ObjectUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

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
public class MatriculaDaoImpl extends GenericMatriculaDAOImpl<String, Matricula> implements MatriculaDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.matricula.dao.local.MatriculaDaoLocal#
	 * listarMatricula(pe.com.builderp.core.service.matricula.model.jpa.Matricula)
	 */
	@Override
	public List<Matricula> listarMatricula(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	/**
	 * Generar query lista Matricula.
	 *
	 * @param MatriculaDTO el matricula
	 * @param esContador   el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idMatricula) from Matricula o where 1=1 ");
		} else {
			jpaql.append(
					" select o from Matricula  o left join fetch o.periodo left join fetch o.alumno left join fetch o.anhio left join fetch o.cargaAcademica where 1=1 ");
		}

		if (!StringUtil.isNullOrEmpty(filtro.getId())) {
			jpaql.append(" and o.cargaAcademica.itemByTurno.idItem = :idTurno ");
			parametros.put("idTurno", ObjectUtil.objectToLong(filtro.getId()));
		}
		if (!StringUtil.isNullOrEmpty(filtro.getIdAnhio())) {
			jpaql.append(" and o.anhio.idAnhio = :idAnhio ");
			parametros.put("idAnhio", ObjectUtil.objectToLong(filtro.getIdAnhio()));
		}
		if (!StringUtil.isNullOrEmpty(filtro.getIdEntidadSelect())) {
			jpaql.append(" and o.alumno.entidad =:idEntidadSelect");
			parametros.put("idEntidadSelect", filtro.getIdEntidadSelect());
		}

		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(
					" and (TRANSLATE(UPPER(o.alumno.nombres || ' ' || o.alumno.apellidoPaterno || ' ' || o.alumno.apellidoMaterno ), :discriminaTildeMAC, :discriminaTildeMAT) like UPPER(translate(:search, :discriminaTildeMIC, :discriminaTildeMIT)) or (upper(o.alumno.codigo) like :search) or (upper(o.alumno.nroDoc) like :search) )");
			parametros.putAll(obtenerParametroDiscriminarTilde());
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
	 * @see pe.com.builderp.core.service.matricula.dao.local.MatriculaDaoLocal#
	 * contarListar{entity.getClassName()}(pe.com.builderp.core.service.matricula.
	 * model.jpa.MatriculaDTO)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.matricula.dao.local.MatriculaDaoLocal#
	 * generarIdMatricula()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

	@Override
	public Matricula get(Long idAnhio, String idAlumno, Long idTurno, Long idPerido) {
		Matricula resultado = new Matricula();
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		jpaql.append(
				"from Matricula m   left join fetch m.cargaAcademica  left join fetch m.periodo  where m.alumno.idAlumno=:idAlumno");
		jpaql.append(" and  m.cargaAcademica.anhio.idAnhio =:idAnhio ");
		parametros.put("idAlumno", idAlumno);
		if (!StringUtil.isNullOrEmptyNumeric(idTurno)) {
			jpaql.append(" and m.cargaAcademica.itemByTurno.idItem =:idTurno ");
			parametros.put("idTurno", idTurno);
		}

		if (!StringUtil.isNullOrEmptyNumeric(idPerido)) {
			jpaql.append(" and m.periodo.idPeriodo =:idPerido ");
			parametros.put("idPerido", idPerido);
		}

		parametros.put("idAnhio", idAnhio);
		var query = createQuery(jpaql.toString(), parametros);

		List<Matricula> matriculaActual = query.getResultList();
		if (!CollectionUtil.isEmpty(matriculaActual)) {
			resultado = matriculaActual.get(0);
		}
		return resultado;
	}

	@Override
	public Matricula find(String idMatricula) {
		Matricula resultado = new Matricula();
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		jpaql.append(" select o from Matricula o left join fetch o.alumno  ");
		jpaql.append(" where o.idMatricula = :idMatricula ");
		parametros.put("idMatricula", idMatricula);

		var query = createQuery(jpaql.toString(), parametros);
		List<Matricula> listaTemp = query.getResultList();
		if (!CollectionUtil.isEmpty(listaTemp)) {
			resultado = listaTemp.get(0);
		}
		return resultado;
	}
}