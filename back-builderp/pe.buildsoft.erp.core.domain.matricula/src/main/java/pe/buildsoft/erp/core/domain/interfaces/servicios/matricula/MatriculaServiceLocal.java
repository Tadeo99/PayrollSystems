package pe.buildsoft.erp.core.domain.interfaces.servicios.matricula;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Local;

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
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;

/**
 * La Class MatriculaServiceLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:51 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface MatriculaServiceLocal {

	/**
	 * Controlador accion criterio evaluacion.
	 *
	 * @param obj        el criterio evaluacion
	 * @param accionType el accion type
	 * @return the criterio evaluacion @ the exception
	 */
	CriterioEvaluacion controladorAccionCriterioEvaluacion(CriterioEvaluacion obj, AccionType accionType);

	/**
	 * Listar criterio evaluacion.
	 *
	 * @param filtro el criterio evaluacion
	 * @return the list @ the exception
	 */
	List<CriterioEvaluacion> listarCriterioEvaluacion(BaseSearch filtro);

	/**
	 * contar lista criterio evaluacion.
	 *
	 * @param filtro el criterio evaluacion
	 * @return the list @ the exception
	 */
	int contarListarCriterioEvaluacion(BaseSearch filtro);

	/**
	 * Controlador accion malla curricular.
	 *
	 * @param obj        el malla curricular
	 * @param accionType el accion type
	 * @return the malla curricular @ the exception
	 */
	MallaCurricular controladorAccionMallaCurricular(MallaCurricular obj, AccionType accionType);

	/**
	 * Listar malla curricular.
	 *
	 * @param filtro el malla curricular
	 * @return the list @ the exception
	 */
	List<MallaCurricular> listarMallaCurricular(BaseSearch filtro);

	/**
	 * contar lista malla curricular.
	 *
	 * @param filtro el malla curricular
	 * @return the list @ the exception
	 */
	int contarListarMallaCurricular(BaseSearch filtro);

	/**
	 * Controlador accion alumno.
	 *
	 * @param obj        el alumno
	 * @param accionType el accion type
	 * @return the alumno @ the exception
	 */
	Alumno controladorAccionAlumno(Alumno obj, AccionType accionType);

	/**
	 * Listar alumno.
	 *
	 * @param filtro el alumno
	 * @return the list @ the exception
	 */
	List<Alumno> listarAlumno(BaseSearch filtro);

	/**
	 * contar lista alumno.
	 *
	 * @param filtro el alumno
	 * @return the list @ the exception
	 */
	int contarListarAlumno(BaseSearch filtro);

	/**
	 * Controlador accion pabellon.
	 *
	 * @param obj        el pabellon
	 * @param accionType el accion type
	 * @return the pabellon @ the exception
	 */
	Pabellon controladorAccionPabellon(Pabellon obj, AccionType accionType);

	/**
	 * Listar pabellon.
	 *
	 * @param filtro el pabellon
	 * @return the list @ the exception
	 */
	List<Pabellon> listarPabellon(BaseSearch filtro);

	/**
	 * contar lista pabellon.
	 *
	 * @param filtro el pabellon
	 * @return the list @ the exception
	 */
	int contarListarPabellon(BaseSearch filtro);

	/**
	 * Controlador accion pre requisito.
	 *
	 * @param obj        el pre requisito
	 * @param accionType el accion type
	 * @return the pre requisito @ the exception
	 */
	PreRequisito controladorAccionPreRequisito(PreRequisito obj, AccionType accionType);

	/**
	 * Listar pre requisito.
	 *
	 * @param filtro el pre requisito
	 * @return the list @ the exception
	 */
	List<PreRequisito> listarPreRequisito(BaseSearch filtro);

	/**
	 * contar lista pre requisito.
	 *
	 * @param filtro el pre requisito
	 * @return the list @ the exception
	 */
	int contarListarPreRequisito(BaseSearch filtro);

	/**
	 * Controlador accion calendario academico.
	 *
	 * @param obj        el calendario academico
	 * @param accionType el accion type
	 * @return the calendario academico @ the exception
	 */
	CalendarioAcademico controladorAccionCalendarioAcademico(CalendarioAcademico obj, AccionType accionType);

	/**
	 * Listar calendario academico.
	 *
	 * @param filtro el calendario academico
	 * @return the list @ the exception
	 */
	List<CalendarioAcademico> listarCalendarioAcademico(BaseSearch filtro);

	/**
	 * contar lista calendario academico.
	 *
	 * @param filtro el calendario academico
	 * @return the list @ the exception
	 */
	int contarListarCalendarioAcademico(BaseSearch filtro);

	/**
	 * Controlador accion grupo.
	 *
	 * @param obj        el grupo
	 * @param accionType el accion type
	 * @return the grupo @ the exception
	 */
	Grupo controladorAccionGrupo(Grupo obj, AccionType accionType);

	/**
	 * Listar grupo.
	 *
	 * @param filtro el grupo
	 * @return the list @ the exception
	 */
	List<Grupo> listarGrupo(BaseSearch filtro);

	/**
	 * contar lista grupo.
	 *
	 * @param filtro el grupo
	 * @return the list @ the exception
	 */
	int contarListarGrupo(BaseSearch filtro);

	/**
	 * Controlador accion carga academica.
	 *
	 * @param obj        el carga academica
	 * @param accionType el accion type
	 * @return the carga academica @ the exception
	 */
	CargaAcademica eliminarCargaAcademica(CargaAcademica obj);

	/**
	 * Listar carga academica.
	 *
	 * @param filtro el carga academica
	 * @return the list @ the exception
	 */
	List<CargaAcademica> listarCargaAcademica(BaseSearch filtro);

	/**
	 * contar lista carga academica.
	 *
	 * @param filtro el carga academica
	 * @return the list @ the exception
	 */
	int contarListarCargaAcademica(BaseSearch filtro);

	/**
	 * Controlador accion det malla curricular.
	 *
	 * @param obj        el det malla curricular
	 * @param accionType el accion type
	 * @return the det malla curricular @ the exception
	 */
	DetMallaCurricular controladorAccionDetMallaCurricular(DetMallaCurricular obj, AccionType accionType);

	/**
	 * Listar det malla curricular.
	 *
	 * @param filtro el det malla curricular
	 * @return the list @ the exception
	 */
	List<DetMallaCurricular> listarDetMallaCurricular(BaseSearch filtro);

	/**
	 * contar lista det malla curricular.
	 *
	 * @param filtro el det malla curricular
	 * @return the list @ the exception
	 */
	int contarListarDetMallaCurricular(BaseSearch filtro);

	/**
	 * Controlador accion aula.
	 *
	 * @param obj        el aula
	 * @param accionType el accion type
	 * @return the aula @ the exception
	 */
	Aula controladorAccionAula(Aula obj, AccionType accionType);

	/**
	 * Listar aula.
	 *
	 * @param filtro el aula
	 * @return the list @ the exception
	 */
	List<Aula> listarAula(BaseSearch filtro);

	/**
	 * contar lista aula.
	 *
	 * @param filtro el aula
	 * @return the list @ the exception
	 */
	int contarListarAula(BaseSearch filtro);

	/**
	 * Controlador accion periodo.
	 *
	 * @param obj        el periodo
	 * @param accionType el accion type
	 * @return the periodo @ the exception
	 */
	Periodo controladorAccionPeriodo(Periodo obj, AccionType accionType);

	/**
	 * Listar periodo.
	 *
	 * @param filtro el periodo
	 * @return the list @ the exception
	 */
	List<Periodo> listarPeriodo(BaseSearch filtro);

	/**
	 * contar lista periodo.
	 *
	 * @param filtro el periodo
	 * @return the list @ the exception
	 */
	int contarListarPeriodo(BaseSearch filtro);

	/**
	 * Controlador accion matricula.
	 *
	 * @param obj        el matricula
	 * @param accionType el accion type
	 * @return the matricula @ the exception
	 */
	Matricula controladorAccionMatricula(Matricula obj, AccionType accionType);

	/**
	 * Listar matricula.
	 *
	 * @param filtro el matricula
	 * @return the list @ the exception
	 */
	List<Matricula> listarMatricula(BaseSearch filtro);

	/**
	 * contar lista matricula.
	 *
	 * @param filtro el matricula
	 * @return the list @ the exception
	 */
	int contarListarMatricula(BaseSearch filtro);

	/**
	 * Controlador accion det matricula.
	 *
	 * @param obj        el det matricula
	 * @param accionType el accion type
	 * @return the det matricula @ the exception
	 */
	DetMatricula controladorAccionDetMatricula(DetMatricula obj, AccionType accionType);

	/**
	 * Listar det matricula.
	 *
	 * @param filtro el det matricula
	 * @return the list @ the exception
	 */
	List<DetMatricula> listarDetMatricula(BaseSearch filtro);

	/**
	 * contar lista det matricula.
	 *
	 * @param filtro el det matricula
	 * @return the list @ the exception
	 */
	int contarListarDetMatricula(BaseSearch filtro);

	/**
	 * Controlador accion horario.
	 *
	 * @param obj        el horario
	 * @param accionType el accion type
	 * @return the horario @ the exception
	 */
	Horario controladorAccionHorario(Horario obj, AccionType accionType);

	/**
	 * Listar horario.
	 *
	 * @param filtro el horario
	 * @return the list @ the exception
	 */
	List<Horario> listarHorario(BaseSearch filtro);

	/**
	 * contar lista horario.
	 *
	 * @param filtro el horario
	 * @return the list @ the exception
	 */
	int contarListarHorario(BaseSearch filtro);

	/**
	 * Controlador accion unidad.
	 *
	 * @param obj        el unidad
	 * @param accionType el accion type
	 * @return the unidad @ the exception
	 */
	Unidad controladorAccionUnidad(Unidad obj, AccionType accionType);

	/**
	 * Listar unidad.
	 *
	 * @param filtro el unidad
	 * @return the list @ the exception
	 */
	List<Unidad> listarUnidad(BaseSearch filtro);

	/**
	 * contar lista unidad.
	 *
	 * @param filtro el unidad
	 * @return the list @ the exception
	 */
	int contarListarUnidad(BaseSearch filtro);

	List<DetMallaCurricular> obtenerDetMallaCurricular(BaseSearch filtro);

	List<DetalleCargaAcademica> obtenerDetCargaAcademica(BaseSearch filtro);

	void agregarCargaAcademica(CargaAcademica obj);

	List<DetalleCargaAcademica> obtenerCursosPosiblesLlevar(Map<String, Object> parametroMap);

	Matricula registrarMatricula(Matricula matricula);

	List<DetalleCargaAcademica> listarDetCargaAcademicaByDocente(BaseSearch filtro);

	Matricula obtenerMatricula(Long idAnhio, String idAlumno, Long idTurno, Long idPeriodo);

	List<CriterioEvaluacion> listarCriterioEvaluacion(String estado, String idDetMallaCurricular,
			String idCriterioEvaluacionPadre);

	void eliminarRegistrarMatricula(String idMatricula, String userName);

	List<DetMatricula> obtenerListMatricula(String idMatricula);

	Periodo findPeriodo(Periodo filtro);

	Unidad findUnidad(Unidad filtro);

	Pabellon findPabellon(Pabellon filtro);

	Grupo findGrupo(Grupo filtro);
}