package pe.buildsoft.erp.core.domain.servicios.matricula;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.inject.Inject;
import jakarta.ejb.Stateless;

import pe.buildsoft.erp.core.domain.entidades.matricula.Alumno;
import pe.buildsoft.erp.core.domain.entidades.matricula.Aula;
import pe.buildsoft.erp.core.domain.entidades.matricula.CalendarioAcademico;
import pe.buildsoft.erp.core.domain.entidades.matricula.CargaAcademica;
import pe.buildsoft.erp.core.domain.entidades.matricula.CriterioEvaluacion;
import pe.buildsoft.erp.core.domain.entidades.matricula.DetMallaCurricular;
import pe.buildsoft.erp.core.domain.entidades.matricula.DetMatricula;
import pe.buildsoft.erp.core.domain.entidades.matricula.DetalleCargaAcademica;
import pe.buildsoft.erp.core.domain.entidades.matricula.Grupo;
import pe.buildsoft.erp.core.domain.entidades.matricula.Horario;
import pe.buildsoft.erp.core.domain.entidades.matricula.MallaCurricular;
import pe.buildsoft.erp.core.domain.entidades.matricula.Matricula;
import pe.buildsoft.erp.core.domain.entidades.matricula.Pabellon;
import pe.buildsoft.erp.core.domain.entidades.matricula.Periodo;
import pe.buildsoft.erp.core.domain.entidades.matricula.PreRequisito;
import pe.buildsoft.erp.core.domain.entidades.matricula.Unidad;
import pe.buildsoft.erp.core.domain.entidades.nota.CursoNotaPeriodoTemp;
import pe.buildsoft.erp.core.domain.entidades.nota.DetRegistroNota;
import pe.buildsoft.erp.core.domain.entidades.nota.RegistroNota;
import pe.buildsoft.erp.core.domain.interfaces.repositories.matricula.AlumnoDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.matricula.AulaDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.matricula.CalendarioAcademicoDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.matricula.CargaAcademicaDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.matricula.CriterioEvaluacionDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.matricula.DetMallaCurricularDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.matricula.DetMatriculaDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.matricula.DetalleCargaAcademicaDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.matricula.GrupoDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.matricula.HorarioDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.matricula.MallaCurricularDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.matricula.MatriculaDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.matricula.PabellonDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.matricula.PeriodoDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.matricula.PreRequisitoDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.matricula.UnidadDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.nota.CursoNotaPeiodoSharedDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.nota.DetRegistroNotaSharedDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.nota.RegistroNotaSharedDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.servicios.matricula.MatriculaServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.state.EstadoGeneralState;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.CollectionUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.FechaUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * La Class MatriculaServiceImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:51 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class MatriculaServiceImpl implements MatriculaServiceLocal {

	/** El servicio malla curricular dao impl. */
	@Inject
	private MallaCurricularDaoLocal mallaCurricularDaoImpl;

	/** El servicio alumno dao impl. */
	@Inject
	private AlumnoDaoLocal alumnoDaoImpl;

	/** El servicio pabellon dao impl. */
	@Inject
	private PabellonDaoLocal pabellonDaoImpl;

	/** El servicio detalle carga academica dao impl. */
	@Inject
	private DetalleCargaAcademicaDaoLocal detalleCargaAcademicaDaoImpl;

	/** El servicio pre requisito dao impl. */
	@Inject
	private PreRequisitoDaoLocal preRequisitoDaoImpl;

	/** El servicio calendario academico dao impl. */
	@Inject
	private CalendarioAcademicoDaoLocal calendarioAcademicoDaoImpl;

	/** El servicio grupo dao impl. */
	@Inject
	private GrupoDaoLocal grupoDaoImpl;

	/** El servicio carga academica dao impl. */
	@Inject
	private CargaAcademicaDaoLocal cargaAcademicaDaoImpl;

	/** El servicio det malla curricular dao impl. */
	@Inject
	private DetMallaCurricularDaoLocal detMallaCurricularDaoImpl;

	/** El servicio aula dao impl. */
	@Inject
	private AulaDaoLocal aulaDaoImpl;

	/** El servicio periodo dao impl. */
	@Inject
	private PeriodoDaoLocal periodoDaoImpl;

	/** El servicio matricula dao impl. */
	@Inject
	private MatriculaDaoLocal matriculaDaoImpl;

	/** El servicio det matricula dao impl. */
	@Inject
	private DetMatriculaDaoLocal detMatriculaDaoImpl;

	/** El servicio horario dao impl. */
	@Inject
	private HorarioDaoLocal horarioDaoImpl;

	/** El servicio unidad dao impl. */
	@Inject
	private UnidadDaoLocal unidadDaoImpl;

	/** El servicio criterio evaluacion dao impl. */
	@Inject
	private CriterioEvaluacionDaoLocal criterioEvaluacionDaoImpl;

	@Inject
	private RegistroNotaSharedDaoLocal registroNotaDaoImpl;

	@Inject
	private DetRegistroNotaSharedDaoLocal detRegistroNotaDaoImpl;

	@Inject
	private CursoNotaPeiodoSharedDaoLocal cursoNotaDaoImpl;

	@Override
	public CriterioEvaluacion controladorAccionCriterioEvaluacion(CriterioEvaluacion obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdCriterioEvaluacion(this.criterioEvaluacionDaoImpl.generarId());
			this.criterioEvaluacionDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.criterioEvaluacionDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.criterioEvaluacionDaoImpl.find(CriterioEvaluacion.class, obj.getIdCriterioEvaluacion());
			this.criterioEvaluacionDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.criterioEvaluacionDaoImpl.find(CriterioEvaluacion.class, obj.getIdCriterioEvaluacion());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<CriterioEvaluacion> listarCriterioEvaluacion(BaseSearch filtro) {
		return this.criterioEvaluacionDaoImpl.listar(filtro);
	}

	@Override
	public List<CriterioEvaluacion> listarCriterioEvaluacion(String estado, String idDetMallaCurricular,
			String idCriterioEvaluacionPadre) {
		return this.criterioEvaluacionDaoImpl.listar(estado, idDetMallaCurricular, idCriterioEvaluacionPadre);
	}

	@Override
	public int contarListarCriterioEvaluacion(BaseSearch filtro) {
		return this.criterioEvaluacionDaoImpl.contar(filtro);
	}

	@Override
	public MallaCurricular controladorAccionMallaCurricular(MallaCurricular obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdMallaCurricular(this.mallaCurricularDaoImpl.generarId());
			this.mallaCurricularDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.mallaCurricularDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.mallaCurricularDaoImpl.find(MallaCurricular.class, obj.getIdMallaCurricular());
			this.mallaCurricularDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.mallaCurricularDaoImpl.find(MallaCurricular.class, obj.getIdMallaCurricular());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<MallaCurricular> listarMallaCurricular(BaseSearch filtro) {
		return this.mallaCurricularDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarMallaCurricular(BaseSearch filtro) {
		return this.mallaCurricularDaoImpl.contar(filtro);
	}

	@Override
	public Alumno controladorAccionAlumno(Alumno obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdAlumno(this.alumnoDaoImpl.generarId());
			obj.setFechaCreacion(FechaUtil.obtenerFechaActual());
			this.alumnoDaoImpl.save(obj);
			break;
		case MODIFICAR:
			obj.setFechaModificacion(FechaUtil.obtenerFechaActual());
			this.alumnoDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.alumnoDaoImpl.find(Alumno.class, obj.getIdAlumno());
			this.alumnoDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.alumnoDaoImpl.find(Alumno.class, obj.getIdAlumno());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<Alumno> listarAlumno(BaseSearch filtro) {
		return this.alumnoDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarAlumno(BaseSearch filtro) {
		return this.alumnoDaoImpl.contar(filtro);
	}

	@Override
	public Pabellon controladorAccionPabellon(Pabellon obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdPabellon(this.pabellonDaoImpl.generarId());
			this.pabellonDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.pabellonDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.pabellonDaoImpl.find(Pabellon.class, obj.getIdPabellon());
			this.pabellonDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.pabellonDaoImpl.find(Pabellon.class, obj.getIdPabellon());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<Pabellon> listarPabellon(BaseSearch filtro) {
		return this.pabellonDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarPabellon(BaseSearch filtro) {
		return this.pabellonDaoImpl.contar(filtro);
	}

	@Override
	public Pabellon findPabellon(Pabellon filtro) {
		return pabellonDaoImpl.find(filtro);
	}

	private DetalleCargaAcademica registrarDetalleCargaAcademica(DetalleCargaAcademica obj, CargaAcademica objCL,
			AccionType accionType) {
		obj.setCargaAcademica(objCL);
		switch (accionType) {
		case CREAR:
			obj.setIdDetalleCargaAcademica(this.detalleCargaAcademicaDaoImpl.generarId());
			this.detalleCargaAcademicaDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.detalleCargaAcademicaDaoImpl.update(obj);
			break;
		case ELIMINAR:
			obj = this.detalleCargaAcademicaDaoImpl.find(DetalleCargaAcademica.class, obj.getIdDetalleCargaAcademica());
			this.detalleCargaAcademicaDaoImpl.delete(obj);
			break;
		default:
			break;
		}
		return obj;
	}

	@Override
	public PreRequisito controladorAccionPreRequisito(PreRequisito obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdPreRequisito(this.preRequisitoDaoImpl.generarId());
			this.preRequisitoDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.preRequisitoDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.preRequisitoDaoImpl.find(PreRequisito.class, obj.getIdPreRequisito());
			this.preRequisitoDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.preRequisitoDaoImpl.find(PreRequisito.class, obj.getIdPreRequisito());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<PreRequisito> listarPreRequisito(BaseSearch filtro) {
		return this.preRequisitoDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarPreRequisito(BaseSearch filtro) {
		return this.preRequisitoDaoImpl.contar(filtro);
	}

	@Override
	public CalendarioAcademico controladorAccionCalendarioAcademico(CalendarioAcademico obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdCalendarioAcademico(this.calendarioAcademicoDaoImpl.generarId());
			this.calendarioAcademicoDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.calendarioAcademicoDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.calendarioAcademicoDaoImpl.find(CalendarioAcademico.class, obj.getIdCalendarioAcademico());
			this.calendarioAcademicoDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.calendarioAcademicoDaoImpl.find(CalendarioAcademico.class, obj.getIdCalendarioAcademico());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<CalendarioAcademico> listarCalendarioAcademico(BaseSearch filtro) {
		return this.calendarioAcademicoDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarCalendarioAcademico(BaseSearch filtro) {
		return this.calendarioAcademicoDaoImpl.contar(filtro);
	}

	@Override
	public Grupo controladorAccionGrupo(Grupo obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdGrupo(this.grupoDaoImpl.generarId());
			this.grupoDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.grupoDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.grupoDaoImpl.find(Grupo.class, obj.getIdGrupo());
			this.grupoDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.grupoDaoImpl.find(Grupo.class, obj.getIdGrupo());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<Grupo> listarGrupo(BaseSearch filtro) {
		return this.grupoDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarGrupo(BaseSearch filtro) {
		return this.grupoDaoImpl.contar(filtro);
	}

	@Override
	public Grupo findGrupo(Grupo filtro) {
		return grupoDaoImpl.find(filtro);
	}

	// TODO:REVISAR ETO
	@Override
	public CargaAcademica eliminarCargaAcademica(CargaAcademica obj) {
		obj = this.cargaAcademicaDaoImpl.find(CargaAcademica.class, obj.getIdCargaAcademica());
		this.cargaAcademicaDaoImpl.delete(obj);
		return obj;
	}

	@Override
	public List<CargaAcademica> listarCargaAcademica(BaseSearch filtro) {
		return this.cargaAcademicaDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarCargaAcademica(BaseSearch filtro) {
		return this.cargaAcademicaDaoImpl.contar(filtro);
	}

	@Override
	public DetMallaCurricular controladorAccionDetMallaCurricular(DetMallaCurricular obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdDetMallaCurricular(this.detMallaCurricularDaoImpl.generarId());
			this.detMallaCurricularDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.detMallaCurricularDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.detMallaCurricularDaoImpl.find(DetMallaCurricular.class, obj.getIdDetMallaCurricular());
			this.detMallaCurricularDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.detMallaCurricularDaoImpl.find(DetMallaCurricular.class, obj.getIdDetMallaCurricular());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<DetMallaCurricular> listarDetMallaCurricular(BaseSearch filtro) {
		return this.detMallaCurricularDaoImpl.listar(filtro);

	}

	@Override
	public List<DetMallaCurricular> obtenerDetMallaCurricular(BaseSearch filtro) {
		return this.detMallaCurricularDaoImpl.get(filtro);
	}

	@Override
	public int contarListarDetMallaCurricular(BaseSearch filtro) {
		return this.detMallaCurricularDaoImpl.contar(filtro);
	}

	@Override
	public Aula controladorAccionAula(Aula obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdAula(this.aulaDaoImpl.generarIdAula());
			this.aulaDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.aulaDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.aulaDaoImpl.find(Aula.class, obj.getIdAula());
			this.aulaDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.aulaDaoImpl.find(Aula.class, obj.getIdAula());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<Aula> listarAula(BaseSearch filtro) {
		return this.aulaDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarAula(BaseSearch filtro) {
		return this.aulaDaoImpl.contar(filtro);
	}

	@Override
	public Periodo controladorAccionPeriodo(Periodo obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdPeriodo(this.periodoDaoImpl.generarId());
			this.periodoDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.periodoDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.periodoDaoImpl.find(Periodo.class, obj.getIdPeriodo());
			this.periodoDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.periodoDaoImpl.find(Periodo.class, obj.getIdPeriodo());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<Periodo> listarPeriodo(BaseSearch filtro) {
		return this.periodoDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarPeriodo(BaseSearch filtro) {
		return this.periodoDaoImpl.contar(filtro);
	}

	@Override
	public Periodo findPeriodo(Periodo filtro) {
		return periodoDaoImpl.find(filtro);
	}

	@Override
	public Matricula controladorAccionMatricula(Matricula obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdMatricula(this.matriculaDaoImpl.generarId());
			this.matriculaDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.matriculaDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.matriculaDaoImpl.find(Matricula.class, obj.getIdMatricula());
			this.matriculaDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.matriculaDaoImpl.find(Matricula.class, obj.getIdMatricula());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<Matricula> listarMatricula(BaseSearch filtro) {
		return this.matriculaDaoImpl.listarMatricula(filtro);
	}

	@Override
	public int contarListarMatricula(BaseSearch filtro) {
		return this.matriculaDaoImpl.contar(filtro);
	}

	@Override
	public DetMatricula controladorAccionDetMatricula(DetMatricula obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdDetMatricula(this.detMatriculaDaoImpl.generarId());
			this.detMatriculaDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.detMatriculaDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.detMatriculaDaoImpl.find(DetMatricula.class, obj.getIdDetMatricula());
			this.detMatriculaDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.detMatriculaDaoImpl.find(DetMatricula.class, obj.getIdDetMatricula());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<DetMatricula> listarDetMatricula(BaseSearch filtro) {
		return this.detMatriculaDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarDetMatricula(BaseSearch filtro) {
		return this.detMatriculaDaoImpl.contar(filtro);
	}

	@Override
	public Horario controladorAccionHorario(Horario obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdHorario(this.horarioDaoImpl.generarId());
			this.horarioDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.horarioDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.horarioDaoImpl.find(Horario.class, obj.getIdHorario());
			this.horarioDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.horarioDaoImpl.find(Horario.class, obj.getIdHorario());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<Horario> listarHorario(BaseSearch filtro) {
		return this.horarioDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarHorario(BaseSearch filtro) {
		return this.horarioDaoImpl.contar(filtro);
	}

	@Override
	public Unidad controladorAccionUnidad(Unidad obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdUnidad(this.unidadDaoImpl.generarId());
			this.unidadDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.unidadDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.unidadDaoImpl.find(Unidad.class, obj.getIdUnidad());
			this.unidadDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.unidadDaoImpl.find(Unidad.class, obj.getIdUnidad());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<Unidad> listarUnidad(BaseSearch filtro) {
		return this.unidadDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarUnidad(BaseSearch filtro) {
		return this.unidadDaoImpl.contar(filtro);
	}

	@Override
	public Unidad findUnidad(Unidad filtro) {
		return unidadDaoImpl.find(filtro);
	}

	private List<DetalleCargaAcademica> obtenerDetCargaAcademicaTemp(BaseSearch filtro) {
		BaseSearch filtroDet = new BaseSearch();
		filtroDet.setIdCargaAcademica(filtro.getIdCargaAcademica());
		filtroDet.setIdItemByTurno(filtro.getIdItemByTurno());
		filtroDet.setIdSeccion(filtro.getIdSeccion());
		filtroDet.setIdAnhio(filtro.getIdAnhio());
		filtroDet.setIdEntidad(filtro.getIdEntidad());
		return this.detalleCargaAcademicaDaoImpl.get(filtro);
	}

	@Override
	public List<DetalleCargaAcademica> obtenerDetCargaAcademica(BaseSearch filtro) {
		return this.obtenerDetCargaAcademicaTemp(filtro);
	}

	@Override
	public void agregarCargaAcademica(CargaAcademica obj) {
		if (!StringUtil.isNotNullOrBlank(obj.getIdCargaAcademica())) {
			obj.setFechaCreacion(FechaUtil.obtenerFechaActual());
			obj.setIdCargaAcademica(this.cargaAcademicaDaoImpl.generarId());
			this.cargaAcademicaDaoImpl.save(obj);

		} else {
			obj.setFechaModificacion(FechaUtil.obtenerFechaActual());
			this.cargaAcademicaDaoImpl.update(obj);
		}
		if (!CollectionUtil.isEmpty(obj.getCargaAcademicaDetalleCargaAcademicaList())) {
			for (var detaCarVO : obj.getCargaAcademicaDetalleCargaAcademicaList()) {
				if (!CollectionUtil.isEmpty(detaCarVO.getDetalleCargaAcademicaList())) {
					for (var detaCarVOTemp : detaCarVO.getDetalleCargaAcademicaList()) {
						registrarDetCargaAcademica(detaCarVOTemp, obj);
					}
				} else {
					registrarDetCargaAcademica(detaCarVO, obj);
				}
			}
		}
	}

	private void registrarDetCargaAcademica(DetalleCargaAcademica obj, CargaAcademica objEntity) {
		if (obj.isChecked()) {
			if (!StringUtil.isNotNullOrBlank(obj.getIdDetalleCargaAcademica())) {
				registrarDetalleCargaAcademica(obj, objEntity, AccionType.CREAR);
			} else {
				registrarDetalleCargaAcademica(obj, objEntity, AccionType.MODIFICAR);
			}
		} else {
			registrarDetalleCargaAcademica(obj, null, AccionType.ELIMINAR);
		}
	}

	@Override
	public List<DetalleCargaAcademica> listarDetCargaAcademicaByDocente(BaseSearch filtro) {
		return this.detalleCargaAcademicaDaoImpl.get(filtro);
	}

	@Override
	public List<DetalleCargaAcademica> obtenerCursosPosiblesLlevar(Map<String, Object> parametroMap) {
		return detalleCargaAcademicaDaoImpl.listar(parametroMap);
	}

	@Override
	public Matricula registrarMatricula(Matricula obj) {
		String userName = obj.getUsuarioSession();
		// Eliminado el detalle de matricula y nota
		// eliminarRegistrarMatricula(matricula.getIdMatricula(),false);

		RegistroNota registroNota = new RegistroNota();
		obj.setFechaMatricula(FechaUtil.obtenerFechaActual());
		if (!StringUtil.isNotNullOrBlank(obj.getIdMatricula())) {
			if (!StringUtil.isNullOrEmpty(obj.getFechaCreacion())) {
				registroNota.setFechaCreacion(obj.getFechaCreacion());
			} else {
				registroNota.setFechaCreacion(FechaUtil.obtenerFechaActual());
				obj.setFechaCreacion(FechaUtil.obtenerFechaActual());
			}

			obj.setUsuarioCreacion(userName);
			obj.setIdMatricula(matriculaDaoImpl.generarId());
			obj = matriculaDaoImpl.save(obj);
			// Generando nota en cero
			registroNota.setUsuarioCreacion(userName);
			registroNota.setIdRegistroNota(obj.getIdMatricula());
			registroNota.setMatricula(obj.getIdMatricula());
			registroNota.setNotaFinal(BigDecimal.ZERO);
			registroNota.setUsuarioCreacion(userName);
			registroNota = registroNotaDaoImpl.save(registroNota);

		} else {
			registroNota = registroNotaDaoImpl.get(obj.getIdMatricula());
			obj.setUsuarioModificacion(userName);
			obj.setFechaModificacion(FechaUtil.obtenerFechaActual());
			registroNota.setFechaModificacion(FechaUtil.obtenerFechaActual());
			registroNota.setIdRegistroNota(obj.getIdMatricula());
			registroNota.setMatricula(obj.getIdMatricula());
			registroNota.setNotaFinal(BigDecimal.ZERO);
			registroNota.setUsuarioModificacion(userName);
			registroNota = registroNotaDaoImpl.update(registroNota);
			obj = matriculaDaoImpl.update(obj);
		}
		for (var detCargaLectiva : obj.getListaDetalleCargaAcademica()) {

			if (!CollectionUtil.isEmpty(detCargaLectiva.getDetalleCargaAcademicaList())) {
				for (var detCargaLectivaTemp : detCargaLectiva.getDetalleCargaAcademicaList()) {
					DetMatricula detMatricula = new DetMatricula();
					detMatricula.setMatricula(obj);
					detMatricula.setDescripcionCurso(detCargaLectivaTemp.getDetMallaCurricular().getDescripcionCurso());
					detMatricula.setDetMallaCurricular(new DetMallaCurricular());
					detMatricula.getDetMallaCurricular().setIdDetMallaCurricular(
							detCargaLectivaTemp.getDetMallaCurricular().getIdDetMallaCurricular());
					detMatricula.setIdDetMatricula(detMatriculaDaoImpl.generarId());
					detMatricula.setEstado(EstadoGeneralState.ACTIVO.getKey());
					detMatricula.setCodigoAsignatura("1");
					detMatriculaDaoImpl.save(detMatricula);
					// Generando nota en cero
					DetRegistroNota detRegistroNota = new DetRegistroNota();
					detRegistroNota.setIdDetRegistroNota(detMatricula.getIdDetMatricula());
					detRegistroNota.setDetMatricula(detMatricula);
					detRegistroNota.setNotaLetra(new BigDecimal("0"));
					detRegistroNota.setFechaCreacion(obj.getFechaCreacion());
					detRegistroNota.setUsuarioCreacion(userName);
					detRegistroNota.setRegistroNota(registroNota);
					detRegistroNotaDaoImpl.save(detRegistroNota);
				}
			} else {
				DetMatricula detMatricula = new DetMatricula();
				detMatricula.setMatricula(obj);
				detMatricula.setDescripcionCurso(detCargaLectiva.getDetMallaCurricular().getDescripcionCurso());
				detMatricula.setDetMallaCurricular(new DetMallaCurricular());
				detMatricula.getDetMallaCurricular()
						.setIdDetMallaCurricular(detCargaLectiva.getDetMallaCurricular().getIdDetMallaCurricular());
				detMatricula.setIdDetMatricula(detMatriculaDaoImpl.generarId());
				detMatricula.setEstado(EstadoGeneralState.ACTIVO.getKey());
				detMatricula.setCodigoAsignatura("1");
				detMatriculaDaoImpl.save(detMatricula);
				// Generando nota en cero
				DetRegistroNota detRegistroNota = new DetRegistroNota();
				detRegistroNota.setIdDetRegistroNota(detMatricula.getIdDetMatricula());
				detRegistroNota.setDetMatricula(detMatricula);
				detRegistroNota.setNotaLetra(new BigDecimal("0"));
				detRegistroNota.setFechaCreacion(obj.getFechaCreacion());
				detRegistroNota.setUsuarioCreacion(userName);
				detRegistroNota.setRegistroNota(registroNota);
				detRegistroNotaDaoImpl.save(detRegistroNota);
			}
		}
		return obj;
	}

	@Override
	public Matricula obtenerMatricula(Long idAnhio, String idAlumno, Long idTurno, Long idPerido) {
		return matriculaDaoImpl.get(idAnhio, idAlumno, idTurno, idPerido);
	}

	@Override
	public List<DetMatricula> obtenerListMatricula(String idMatricula) {
		return this.detMatriculaDaoImpl.listar(idMatricula);
	}

	@Override
	public void eliminarRegistrarMatricula(String idMatricula, String userName) {
		// Eliminado el detalle de matricula y nota
		eliminarRegistrarMatricula(idMatricula, true);
	}

	private void eliminarRegistrarMatricula(String idMatricula, boolean isAll) {
		if (StringUtil.isNotNullOrBlank(idMatricula)) {
			List<String> listaIdDetRegistroNota = new ArrayList<>();
			List<DetRegistroNota> listaDetRegistroNota = detRegistroNotaDaoImpl.listar(idMatricula);
			for (var detRegistroNotaDelete : listaDetRegistroNota) {
				listaIdDetRegistroNota.add(detRegistroNotaDelete.getIdDetRegistroNota());
			}
			List<CursoNotaPeriodoTemp> listaCursoNota = cursoNotaDaoImpl.get(listaIdDetRegistroNota);
			if (!CollectionUtil.isEmpty(listaCursoNota)) {
				for (var cursoNota : listaCursoNota) {
					cursoNotaDaoImpl.delete(cursoNota);
				}
			}

			for (var detRegistroNotaDelete : listaDetRegistroNota) {
				detRegistroNotaDaoImpl.delete(detRegistroNotaDelete);
			}
			List<DetMatricula> listaDetMatricula = detMatriculaDaoImpl.listar(idMatricula);
			for (var detMatriculaDelete : listaDetMatricula) {
				detMatriculaDaoImpl.delete(detMatriculaDelete);
			}
			//
			if (isAll) {
				RegistroNota registroNotaEliminar = registroNotaDaoImpl.get(idMatricula);
				if (registroNotaEliminar != null) {
					registroNotaDaoImpl.delete(registroNotaEliminar);
				}
				Matricula matriculaEliminar = matriculaDaoImpl.find(Matricula.class, idMatricula);
				matriculaDaoImpl.delete(matriculaEliminar);
			}

		}
	}
}