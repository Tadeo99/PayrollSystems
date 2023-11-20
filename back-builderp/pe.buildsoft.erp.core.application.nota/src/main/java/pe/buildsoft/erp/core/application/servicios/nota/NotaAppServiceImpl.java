package pe.buildsoft.erp.core.application.servicios.nota;

import java.util.ArrayList;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import pe.buildsoft.erp.core.application.entidades.nota.AsistenciaAlumnoDTO;
import pe.buildsoft.erp.core.application.entidades.nota.CursoNotaPeriodoDTO;
import pe.buildsoft.erp.core.application.entidades.nota.CursoNotaPeriodoPromDTO;
import pe.buildsoft.erp.core.application.entidades.nota.CursoNotaUnidadDTO;
import pe.buildsoft.erp.core.application.entidades.nota.CursoNotaUnidadPromDTO;
import pe.buildsoft.erp.core.application.entidades.nota.DetRegistroNotaDTO;
import pe.buildsoft.erp.core.application.entidades.nota.RegistroNotaDTO;
import pe.buildsoft.erp.core.application.interfaces.nota.NotaAppServiceLocal;
import pe.buildsoft.erp.core.domain.entidades.matricula.CriterioEvaluacion;
import pe.buildsoft.erp.core.domain.entidades.nota.AsistenciaAlumno;
import pe.buildsoft.erp.core.domain.entidades.nota.CursoNotaPeriodo;
import pe.buildsoft.erp.core.domain.entidades.nota.CursoNotaPeriodoProm;
import pe.buildsoft.erp.core.domain.entidades.nota.CursoNotaUnidad;
import pe.buildsoft.erp.core.domain.entidades.nota.CursoNotaUnidadProm;
import pe.buildsoft.erp.core.domain.entidades.nota.DetRegistroNota;
import pe.buildsoft.erp.core.domain.entidades.nota.RegistroNota;
import pe.buildsoft.erp.core.domain.interfaces.servicios.nota.NotaServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.cache.AtributosEntityCacheUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.BaseTransfer;

/**
 * La Class NotaServiceImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Jun 10 00:13:45 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class NotaAppServiceImpl extends BaseTransfer implements NotaAppServiceLocal {

	/** El servicio asistencia alumno dao impl. */
	@Inject
	private NotaServiceLocal servicio;

	public NotaAppServiceImpl() {
		AtributosEntityCacheUtil.getInstance().sincronizarAtributos("pe.buildsoft.erp.core.domain.entidades.nota",
				"pe.buildsoft.erp.core.domain.entidades.escalafon", "pe.buildsoft.erp.core.domain.entidades.common",
				"pe.buildsoft.erp.core.domain.entidades.planilla", "pe.buildsoft.erp.core.domain.entidades.matricula",
				"pe.buildsoft.erp.core.domain.entidades.admision");
	}

	@Override
	public void registrarAsistencia(List<AsistenciaAlumnoDTO> listaObj) {
		servicio.registrarAsistencia(toListEntity(listaObj, AsistenciaAlumno.class));
	}

	@Override
	public List<AsistenciaAlumnoDTO> obtenerAsistenciaAlumno(AsistenciaAlumnoDTO filtro) {
		return toList(
				servicio.obtenerAsistenciaAlumno(
						to(filtro, AsistenciaAlumno.class, "detalleCargaAcademica:{detMallaCurricular}")),
				AsistenciaAlumnoDTO.class, "alumno", "detalleCargaAcademica", "itemByDia");
	}

	@Override
	public CursoNotaPeriodoDTO controladorAccionCursoNotaPeriodo(CursoNotaPeriodoDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionCursoNotaPeriodo(to(obj, CursoNotaPeriodo.class), accionType),
				CursoNotaPeriodoDTO.class);
	}

	@Override
	public List<CursoNotaPeriodoDTO> listarCursoNotaPeriodo(BaseSearch filtro) {
		return toList(this.servicio.listarCursoNotaPeriodo(filtro), CursoNotaPeriodoDTO.class);
	}

	@Override
	public int contarListarCursoNotaPeriodo(BaseSearch filtro) {
		return this.servicio.contarListarCursoNotaPeriodo(filtro);
	}

	@Override
	public CursoNotaPeriodoPromDTO controladorAccionCursoNotaPeriodoProm(CursoNotaPeriodoPromDTO obj,
			AccionType accionType) {
		return toDTO(servicio.controladorAccionCursoNotaPeriodoProm(to(obj, CursoNotaPeriodoProm.class), accionType),
				CursoNotaPeriodoPromDTO.class);
	}

	@Override
	public List<CursoNotaPeriodoPromDTO> listarCursoNotaPeriodoProm(BaseSearch filtro) {
		return toList(this.servicio.listarCursoNotaPeriodoProm(filtro), CursoNotaPeriodoPromDTO.class);
	}

	@Override
	public int contarListarCursoNotaPeriodoProm(BaseSearch filtro) {
		return this.servicio.contarListarCursoNotaPeriodoProm(filtro);
	}

	@Override
	public CursoNotaUnidadDTO controladorAccionCursoNotaUnidad(CursoNotaUnidadDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionCursoNotaUnidad(to(obj, CursoNotaUnidad.class), accionType),
				CursoNotaUnidadDTO.class);
	}

	@Override
	public List<CursoNotaUnidadDTO> listarCursoNotaUnidad(BaseSearch filtro) {
		return toList(this.servicio.listarCursoNotaUnidad(filtro), CursoNotaUnidadDTO.class);
	}

	@Override
	public int contarListarCursoNotaUnidad(BaseSearch filtro) {
		return this.servicio.contarListarCursoNotaUnidad(filtro);
	}

	@Override
	public CursoNotaUnidadPromDTO controladorAccionCursoNotaUnidadProm(CursoNotaUnidadPromDTO obj,
			AccionType accionType) {
		return toDTO(servicio.controladorAccionCursoNotaUnidadProm(to(obj, CursoNotaUnidadProm.class), accionType),
				CursoNotaUnidadPromDTO.class);
	}

	@Override
	public List<CursoNotaUnidadPromDTO> listarCursoNotaUnidadProm(BaseSearch filtro) {
		return toList(this.servicio.listarCursoNotaUnidadProm(filtro), CursoNotaUnidadPromDTO.class);
	}

	@Override
	public int contarListarCursoNotaUnidadProm(BaseSearch filtro) {
		return this.servicio.contarListarCursoNotaUnidadProm(filtro);
	}

	@Override
	public DetRegistroNotaDTO controladorAccionDetRegistroNota(DetRegistroNotaDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionDetRegistroNota(to(obj, DetRegistroNota.class), accionType),
				DetRegistroNotaDTO.class);
	}

	@Override
	public List<DetRegistroNotaDTO> listarDetRegistroNota(BaseSearch filtro) {
		return toList(this.servicio.listarDetRegistroNota(filtro), DetRegistroNotaDTO.class);
	}

	@Override
	public int contarListarDetRegistroNota(BaseSearch filtro) {
		return this.servicio.contarListarDetRegistroNota(filtro);
	}

	@Override
	public RegistroNotaDTO controladorAccionRegistroNota(RegistroNotaDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionRegistroNota(to(obj, RegistroNota.class), accionType),
				RegistroNotaDTO.class);
	}

	@Override
	public List<RegistroNotaDTO> listarRegistroNota(BaseSearch filtro) {
		return toList(this.servicio.listarRegistroNota(filtro), RegistroNotaDTO.class);
	}

	@Override
	public int contarListarRegistroNota(BaseSearch filtro) {
		return this.servicio.contarListarRegistroNota(filtro);
	}

	@Override
	public List<DetRegistroNotaDTO> obtenerRegistroNota(String idDetMallaCurricular, String idAlumno,
			Boolean esActaEvaluacionFinalAplazado) {
		List<DetRegistroNotaDTO> resultado = new ArrayList<>();
		List<DetRegistroNota> resultadoTmp = servicio.obtenerRegistroNota(idDetMallaCurricular, idAlumno,
				esActaEvaluacionFinalAplazado);

		for (var objTmp : resultadoTmp) {

			DetRegistroNotaDTO obj = toDTO(objTmp, DetRegistroNotaDTO.class);
			obj.setDetRegistroNotaCursoNotaUnidadList(toList(objTmp.getDetRegistroNotaCursoNotaUnidadList(),
					CursoNotaUnidadDTO.class, "criterioEvaluacion"));

			obj.setDetRegistroNotaCursoNotaUnidadPromList(toList(objTmp.getDetRegistroNotaCursoNotaUnidadPromList(),
					CursoNotaUnidadPromDTO.class, "detRegistroNota"));

			resultado.add(obj);
		}

		return resultado;
	}

	@Override
	public void registrarNota(List<DetRegistroNotaDTO> listObj, String userName) {
		List<DetRegistroNota> listObjE = new ArrayList<>();
		for (var obj : listObj) {
			DetRegistroNota objE = to(obj, DetRegistroNota.class);
			List<CursoNotaUnidad> cursoNotaEvaluarUnidad = new ArrayList<>();
			for (var cursoNotaT : obj.getCursoNotaEvaluarUnidad()) {
				CursoNotaUnidad cursoNotaTE = to(cursoNotaT, CursoNotaUnidad.class, "criterioEvaluacion");
				cursoNotaTE.getCriterioEvaluacion().setCriterioEvaluacionHijos(toListEntity(
						cursoNotaT.getCriterioEvaluacion().getCriterioEvaluacionHijos(), CriterioEvaluacion.class));
				cursoNotaEvaluarUnidad.add(cursoNotaTE);
			}
			objE.setCursoNotaEvaluarUnidad(cursoNotaEvaluarUnidad);
			listObjE.add(objE);
		}
		servicio.registrarNota(listObjE, userName);

	}

}