package pe.buildsoft.erp.core.infra.data.repositories.nota;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.matricula.Alumno;
import pe.buildsoft.erp.core.domain.entidades.matricula.DetMatricula;
import pe.buildsoft.erp.core.domain.entidades.matricula.Matricula;
import pe.buildsoft.erp.core.domain.entidades.nota.DetRegistroNota;
import pe.buildsoft.erp.core.domain.entidades.nota.RegistroNota;
import pe.buildsoft.erp.core.domain.interfaces.repositories.nota.DetRegistroNotaDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericNotaDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
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
public class DetRegistroNotaDaoImpl extends GenericNotaDAOImpl<String, DetRegistroNota>
		implements DetRegistroNotaDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.nota.dao.local.DetRegistroNotaDaoLocal#
	 * listarDetRegistroNota(pe.com.builderp.core.service.nota.model.jpa.
	 * DetRegistroNota)
	 */
	@Override
	public List<DetRegistroNota> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	/**
	 * Generar query lista DetRegistroNota.
	 *
	 * @param DetRegistroNota el detRegistroNota
	 * @param esContador      el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idDetRegistroNota) from DetRegistroNota o where 1=1 ");
		} else {
			jpaql.append(" select o from DetRegistroNota o where 1=1 ");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.idDetRegistroNota) like :search ");
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
	 * @see pe.com.builderp.core.service.nota.dao.local.DetRegistroNotaDaoLocal#
	 * contarListar{entity.getClassName()}(pe.com.builderp.core.service.nota.model.
	 * jpa.DetRegistroNota)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

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
	public List<DetRegistroNota> getByCurso(String idDetMallaCurricular, String idAlumno,
			Boolean esActaEvaluacionFinalAplazado) {
		List<DetRegistroNota> resultado = new ArrayList<>();
		var parametros = new HashMap<String, Object>();
		var ejecutarQuery = false;
		var jpaql = new StringBuilder();
		jpaql.append("select drn.idDetRegistroNota,drn.registroNota.idRegistroNota, drn.detMatricula.idDetMatricula ");
		jpaql.append(", drn.notaLetra ");
		jpaql.append(", drn.detMatricula.matricula.alumno.nombres ");
		jpaql.append(", drn.detMatricula.matricula.alumno.apellidoPaterno ");
		jpaql.append(", drn.detMatricula.matricula.alumno.apellidoMaterno ");
		jpaql.append(", drn.detMatricula.matricula.idMatricula,drn.notaLetraByNotaAplazado ");
		jpaql.append(", drn.detMatricula.matricula.alumno.idAlumno ");
		jpaql.append("  from DetRegistroNota drn left join  drn.registroNota   where 1 = 1 ");
		if (StringUtil.isNotNullOrBlank(idDetMallaCurricular)) {
			ejecutarQuery = true;
			jpaql.append(" and drn.detMatricula.detMallaCurricular.idDetMallaCurricular=:idDetMallaCurricular ");
			parametros.put("idDetMallaCurricular", idDetMallaCurricular);
		}
		if (StringUtil.isNotNullOrBlank(idAlumno)) {
			ejecutarQuery = true;
			jpaql.append(" and drn.detMatricula.matricula.alumno.idAlumno =:idAlumno  ");
			parametros.put("idAlumno", idAlumno);
		}
		jpaql.append(
				" order by drn.detMatricula.matricula.alumno.apellidoPaterno,drn.detMatricula.matricula.alumno.apellidoMaterno,drn.detMatricula.matricula.alumno.nombres ");
		if (ejecutarQuery) {
			var query = createQuery(jpaql.toString(), parametros);
			List<Object[]> lisObject = query.getResultList();
			for (var objects : lisObject) {
				DetRegistroNota obj = new DetRegistroNota();
				obj.setIdDetRegistroNota(objects[0].toString());
				obj.setRegistroNota(new RegistroNota());
				obj.getRegistroNota().setIdRegistroNota(objects[1].toString());
				obj.setDetMatricula(new DetMatricula(objects[2].toString()));
				obj.setNotaLetra(new BigDecimal(objects[3].toString()));

				// detRegistroNota.getNotaLetra().setAbreviatura(objects[10].toString());
				// detRegistroNota.getNotaLetra().setDescripcion(objects[11].toString());

				obj.getDetMatricula().setMatricula(new Matricula(objects[7].toString()));
				obj.getDetMatricula().getMatricula().setAlumno(new Alumno());
				// detRegistroNota.getDetMatricula().getMatricula().getAlumno().setCodigoCarnet(objects[4].toString());
				// detRegistroNota.getDetMatricula().getMatricula().getAlumno().setPostulante(new
				// Postulante());
				obj.getDetMatricula().getMatricula().getAlumno().setNombres(objects[4].toString());
				obj.getDetMatricula().getMatricula().getAlumno().setApellidoPaterno(objects[5].toString());
				obj.getDetMatricula().getMatricula().getAlumno().setApellidoMaterno(objects[6].toString());
				if (objects[8] != null) {
					obj.setNotaLetraByNotaAplazado(new BigDecimal(objects[8].toString()));
					// detRegistroNota.getNotaLetraByNotaAplazado().setAbreviatura(objects[12].toString());
					// detRegistroNota.getNotaLetraByNotaAplazado().setDescripcion(objects[13].toString());
				} else {
					obj.setNotaLetraByNotaAplazado(new BigDecimal("0.00"));
				}

				// Auditoria
				// detRegistroNota.setFechaCreacion((Date)objects[14]);
				// detRegistroNota.setFechaModificacion((Date)objects[15]);
				// detRegistroNota.setFechaNotaAplazado((Date)objects[16]);
				// detRegistroNota.setUsuarioCreacion((String)objects[17]);
				// detRegistroNota.setUsuarioModificacion((String)objects[18]);
				// detRegistroNota.getDetMatricula().getMatricula().getAlumno().setIdAlumno((String)objects[19]);
				// detRegistroNota.getDetMatricula().getMatricula().getAlumno().getPostulante().getPersona().setFoto(objects[20].toString());

				resultado.add(obj);
			}

		}
		return resultado;
	}

}