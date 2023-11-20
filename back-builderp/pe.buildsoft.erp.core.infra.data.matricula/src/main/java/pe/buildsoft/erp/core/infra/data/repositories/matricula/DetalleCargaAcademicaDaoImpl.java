package pe.buildsoft.erp.core.infra.data.repositories.matricula;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import pe.buildsoft.erp.core.domain.entidades.matricula.DetalleCargaAcademica;
import pe.buildsoft.erp.core.domain.interfaces.repositories.matricula.DetalleCargaAcademicaDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericMatriculaDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ObjectUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class DetalleCargaAcademicaDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:54 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class DetalleCargaAcademicaDaoImpl extends GenericMatriculaDAOImpl<String, DetalleCargaAcademica>
		implements DetalleCargaAcademicaDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.matricula.dao.local.
	 * DetalleCargaAcademicaDaoLocal#listarDetalleCargaAcademica(pe.com.builderp.
	 * core.service.matricula.model.jpa.DetalleCargaAcademica)
	 */

	@Override
	public List<DetalleCargaAcademica> get(BaseSearch filtro) {
		var parametros = new HashMap<String, Object>();
		var ejecutarQuery = false;
		var jpaql = new StringBuilder();
		jpaql.append("from DetalleCargaAcademica dca  ");
		jpaql.append(" left join fetch dca.detMallaCurricular dmc ");
		jpaql.append(" left join fetch dca.personalByDocentePrincipal ");
		jpaql.append(" left join fetch dca.personalByDocenteAuxiliar ");
		jpaql.append(" left join fetch dca.cargaAcademica ca ");
		jpaql.append(" left join fetch dca.grupo");
		jpaql.append(" left join fetch ca.itemByTurno  ");
		// jpaql.append(" left join fetch ca.idEntidad ");
		jpaql.append(" left join fetch ca.seccion secc left join fetch secc.grado mc");
		jpaql.append(" where 1 = 1 ");
		if (filtro != null) {
			if (StringUtil.isNotNullOrBlank(filtro.getIdAnhio())) {
				ejecutarQuery = true;
				jpaql.append(" and dca.cargaAcademica.anhio.idAnhio = :idAnhio ");
				parametros.put("idAnhio", filtro.getIdAnhio());
			}
			if (StringUtil.isNotNullOrBlank(filtro.getIdPersonalByDocentePrincipal())) {
				ejecutarQuery = true;
				jpaql.append(" and dca.personalByDocentePrincipal.idPersonal = :idPersonalPrincipal ");
				parametros.put("idPersonalPrincipal", filtro.getIdPersonalByDocentePrincipal());
			}
			if (!StringUtil.isNullOrEmpty(filtro.getIdEntidad())) {
				jpaql.append(" and dca.cargaAcademica.idEntidad = :idEntidad ");
				parametros.put("idEntidad", filtro.getIdEntidad());
			}
			if (StringUtil.isNotNullOrBlank(filtro.getIdCargaAcademica())) {
				ejecutarQuery = true;
				jpaql.append(" and dca.cargaAcademica.idCargaAcademica = :idCargaAcademica ");
				parametros.put("idCargaAcademica", filtro.getIdCargaAcademica());
			}

			if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
				jpaql.append(" and upper(dca.detMallaCurricular.descripcionCurso) like :search ");
				parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
			}

			if (StringUtil.isNotNullOrBlank(filtro.getTipoPeriodo())) {
				ejecutarQuery = true;
				jpaql.append(" and dca.cargaAcademica.tipoPeriodo = :tipoPeriodo ");
				parametros.put("tipoPeriodo", filtro.getTipoPeriodo());
			}
			if (StringUtil.isNotNullOrBlank(filtro.getIdSeccion())) {
				ejecutarQuery = true;
				jpaql.append(" and dca.cargaAcademica.seccion.idSeccion = :idSeccion ");
				parametros.put("idSeccion", filtro.getIdSeccion());
			}
			if (StringUtil.isNotNullOrBlank(filtro.getIdGrado())) {
				ejecutarQuery = true;
				jpaql.append(" and dca.detMallaCurricular.mallaCurricular.grado.idGrado = :grado ");
				parametros.put("grado", filtro.getIdGrado());
			}
			if (StringUtil.isNotNullOrBlank(filtro.getIdMallaCurricular())) {
				ejecutarQuery = true;
				jpaql.append(" and dca.detMallaCurricular.mallaCurricular.idMallaCurricular = :idMallaCurricular ");
				parametros.put("idMallaCurricular", filtro.getIdMallaCurricular());
			}

			if (StringUtil.isNotNullOrBlank(filtro.getIdItemByTurno())) {
				ejecutarQuery = true;
				jpaql.append(" and dca.cargaAcademica.itemByTurno.idItem = :turno ");
				parametros.put("turno", filtro.getIdItemByTurno());
			}
			if (StringUtil.isNotNullOrBlank(filtro.getIdAula())) {
				ejecutarQuery = true;
				jpaql.append(" and dca.cargaAcademica.aula.idAula = :idAula ");
				parametros.put("idAula", filtro.getIdAula());
			}
		}
		if (StringUtil.isNotNullOrBlank(filtro.getIdDetalleCargaAcademica())) {
			ejecutarQuery = true;
			jpaql.append(" and dca.idDetalleCargaAcademica = :idDetalleCargaAcademica ");
			parametros.put("idDetalleCargaAcademica", filtro.getIdDetalleCargaAcademica());
		}
		jpaql.append(" order by dca.detMallaCurricular.codigoAsignatura");
		if (ejecutarQuery) {
			var query = createQuery(jpaql.toString(), parametros);
			return query.getResultList();
		}
		return Collections.emptyList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.matricula.dao.local.
	 * DetalleCargaAcademicaDaoLocal#generarIdDetalleCargaAcademica()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

	@Override
	public List<DetalleCargaAcademica> listar(Map<String, Object> parametrosMap) {
		String idEntidad = (String) parametrosMap.get("idEntidad".toUpperCase());
		Long idAnhio = ObjectUtil.objectToLong(parametrosMap.get("idAnhio".toUpperCase()));
		Long idGrado = ObjectUtil.objectToLong(parametrosMap.get("idGrado".toUpperCase()));
		Long idSeccion = ObjectUtil.objectToLong(parametrosMap.get("idSeccion".toUpperCase()));
		String tipo = (String) parametrosMap.get("tipo".toUpperCase());
		String idCargaAcademica = (String) parametrosMap.get("idCargaAcademica".toUpperCase());
		Long idTurno = ObjectUtil.objectToLong(parametrosMap.get("idTurno".toUpperCase()));

		var parametros = new HashMap<String, Object>();
		var ejecutarQuery = false;
		var jpaql = new StringBuilder();
		jpaql.append("from DetalleCargaAcademica dcl  ");
		jpaql.append(" left join fetch dcl.detMallaCurricular dpe");
		jpaql.append(" left join fetch dcl.grupo");
		jpaql.append(" left join fetch dcl.cargaAcademica cl ");
		jpaql.append(" where 1 = 1 ");

		if (StringUtil.isNotNullOrBlank(idEntidad)) {
			ejecutarQuery = true;
			jpaql.append(" and dcl.cargaAcademica.idEntidad = :idEntidad ");
			parametros.put("idEntidad", idEntidad);
		}
		if (!StringUtil.isNullOrEmptyNumeric(idGrado)) {
			ejecutarQuery = true;
			jpaql.append(" and dcl.cargaAcademica.seccion.grado.idGrado = :idGrado ");
			parametros.put("idGrado", idGrado);
		}

		if (!StringUtil.isNullOrEmptyNumeric(idSeccion)) {
			ejecutarQuery = true;
			jpaql.append(" and dcl.cargaAcademica.seccion.idSeccion = :idSeccion ");
			parametros.put("idSeccion", idSeccion);
		}

		if (!StringUtil.isNullOrEmptyNumeric(idAnhio)) {
			ejecutarQuery = true;
			jpaql.append(" and dcl.cargaAcademica.anhio.idAnhio = :idAnhio ");
			parametros.put("idAnhio", idAnhio);
		}

		if (!StringUtil.isNullOrEmptyNumeric(idTurno)) {
			ejecutarQuery = true;
			jpaql.append(" and dcl.cargaAcademica.itemByTurno.idItem = :idTurno ");
			parametros.put("idTurno", idTurno);
		}

		if (StringUtil.isNotNullOrBlank(tipo)) {
			ejecutarQuery = true;
			jpaql.append(" and dcl.cargaAcademica.tipoPeriodo = :tipo ");
			parametros.put("tipo", tipo);
		}

		if (StringUtil.isNotNullOrBlank(idCargaAcademica)) {
			ejecutarQuery = true;
			jpaql.append(" and dcl.cargaAcademica.idCargaAcademica = :idCargaAcademica ");
			parametros.put("idCargaAcademica", idCargaAcademica);
		}

		jpaql.append(" order by dcl.detMallaCurricular.descripcionCurso");
		if (ejecutarQuery) {
			var query = createQuery(jpaql.toString(), parametros);
			return query.getResultList();
		}
		return Collections.emptyList();
	}

}