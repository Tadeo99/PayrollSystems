package pe.buildsoft.erp.core.application.interfaces.matricula;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.application.entidades.matricula.AlumnoDTO;
import pe.buildsoft.erp.core.application.entidades.matricula.AulaDTO;
import pe.buildsoft.erp.core.application.entidades.matricula.CalendarioAcademicoDTO;
import pe.buildsoft.erp.core.application.entidades.matricula.CargaAcademicaDTO;
import pe.buildsoft.erp.core.application.entidades.matricula.CriterioEvaluacionDTO;
import pe.buildsoft.erp.core.application.entidades.matricula.DetMallaCurricularDTO;
import pe.buildsoft.erp.core.application.entidades.matricula.DetMatriculaDTO;
import pe.buildsoft.erp.core.application.entidades.matricula.DetalleCargaAcademicaDTO;
import pe.buildsoft.erp.core.application.entidades.matricula.GrupoDTO;
import pe.buildsoft.erp.core.application.entidades.matricula.HorarioDTO;
import pe.buildsoft.erp.core.application.entidades.matricula.MallaCurricularDTO;
import pe.buildsoft.erp.core.application.entidades.matricula.MatriculaDTO;
import pe.buildsoft.erp.core.application.entidades.matricula.PabellonDTO;
import pe.buildsoft.erp.core.application.entidades.matricula.PeriodoDTO;
import pe.buildsoft.erp.core.application.entidades.matricula.PreRequisitoDTO;
import pe.buildsoft.erp.core.application.entidades.matricula.UnidadDTO;
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
public interface MatriculaAppServiceLocal {

	/**
	 * Controlador accion criterio evaluacion.
	 *
	 * @param obj        el criterio evaluacion
	 * @param accionType el accion type
	 * @return the criterio evaluacion
	 * @throws Exception the exception
	 */
	CriterioEvaluacionDTO controladorAccionCriterioEvaluacion(CriterioEvaluacionDTO obj, AccionType accionType);

	/**
	 * Listar criterio evaluacion.
	 *
	 * @param filtro el criterio evaluacion
	 * @return the list
	 * @throws Exception the exception
	 */
	List<CriterioEvaluacionDTO> listarCriterioEvaluacion(BaseSearch filtro);

	/**
	 * contar lista criterio evaluacion.
	 *
	 * @param filtro el criterio evaluacion
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarCriterioEvaluacion(BaseSearch filtro);

	/**
	 * Controlador accion malla curricular.
	 *
	 * @param obj        el malla curricular
	 * @param accionType el accion type
	 * @return the malla curricular
	 * @throws Exception the exception
	 */
	MallaCurricularDTO controladorAccionMallaCurricular(MallaCurricularDTO obj, AccionType accionType);

	/**
	 * Listar malla curricular.
	 *
	 * @param filtro el malla curricular
	 * @return the list
	 * @throws Exception the exception
	 */
	List<MallaCurricularDTO> listarMallaCurricular(BaseSearch filtro);

	/**
	 * contar lista malla curricular.
	 *
	 * @param filtro el malla curricular
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarMallaCurricular(BaseSearch filtro);

	/**
	 * Controlador accion alumno.
	 *
	 * @param obj        el alumno
	 * @param accionType el accion type
	 * @return the alumno
	 * @throws Exception the exception
	 */
	AlumnoDTO controladorAccionAlumno(AlumnoDTO obj, AccionType accionType);

	/**
	 * Listar alumno.
	 *
	 * @param filtro el alumno
	 * @return the list
	 * @throws Exception the exception
	 */
	List<AlumnoDTO> listarAlumno(BaseSearch filtro);

	/**
	 * contar lista alumno.
	 *
	 * @param filtro el alumno
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarAlumno(BaseSearch filtro);

	/**
	 * Controlador accion pabellon.
	 *
	 * @param obj        el pabellon
	 * @param accionType el accion type
	 * @return the pabellon
	 * @throws Exception the exception
	 */
	PabellonDTO controladorAccionPabellon(PabellonDTO obj, AccionType accionType);

	/**
	 * Listar pabellon.
	 *
	 * @param filtro el pabellon
	 * @return the list
	 * @throws Exception the exception
	 */
	List<PabellonDTO> listarPabellon(BaseSearch filtro);

	/**
	 * contar lista pabellon.
	 *
	 * @param filtro el pabellon
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarPabellon(BaseSearch filtro);

	/**
	 * Controlador accion pre requisito.
	 *
	 * @param obj        el pre requisito
	 * @param accionType el accion type
	 * @return the pre requisito
	 * @throws Exception the exception
	 */
	PreRequisitoDTO controladorAccionPreRequisito(PreRequisitoDTO obj, AccionType accionType);

	/**
	 * Listar pre requisito.
	 *
	 * @param filtro el pre requisito
	 * @return the list
	 * @throws Exception the exception
	 */
	List<PreRequisitoDTO> listarPreRequisito(BaseSearch filtro);

	/**
	 * contar lista pre requisito.
	 *
	 * @param filtro el pre requisito
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarPreRequisito(BaseSearch filtro);

	/**
	 * Controlador accion calendario academico.
	 *
	 * @param obj        el calendario academico
	 * @param accionType el accion type
	 * @return the calendario academico
	 * @throws Exception the exception
	 */
	CalendarioAcademicoDTO controladorAccionCalendarioAcademico(CalendarioAcademicoDTO obj, AccionType accionType);

	/**
	 * Listar calendario academico.
	 *
	 * @param filtro el calendario academico
	 * @return the list
	 * @throws Exception the exception
	 */
	List<CalendarioAcademicoDTO> listarCalendarioAcademico(BaseSearch filtro);

	/**
	 * contar lista calendario academico.
	 *
	 * @param filtro el calendario academico
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarCalendarioAcademico(BaseSearch filtro);

	/**
	 * Controlador accion grupo.
	 *
	 * @param obj        el grupo
	 * @param accionType el accion type
	 * @return the grupo
	 * @throws Exception the exception
	 */
	GrupoDTO controladorAccionGrupo(GrupoDTO obj, AccionType accionType);

	/**
	 * Listar grupo.
	 *
	 * @param filtro el grupo
	 * @return the list
	 * @throws Exception the exception
	 */
	List<GrupoDTO> listarGrupo(BaseSearch filtro);

	/**
	 * contar lista grupo.
	 *
	 * @param filtro el grupo
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarGrupo(BaseSearch filtro);

	/**
	 * Controlador accion carga academica.
	 *
	 * @param obj        el carga academica
	 * @param accionType el accion type
	 * @return the carga academica
	 * @throws Exception the exception
	 */
	CargaAcademicaDTO eliminarCargaAcademica(CargaAcademicaDTO obj);

	/**
	 * Listar carga academica.
	 *
	 * @param filtro el carga academica
	 * @return the list
	 * @throws Exception the exception
	 */
	List<CargaAcademicaDTO> listarCargaAcademica(BaseSearch filtro);

	/**
	 * contar lista carga academica.
	 *
	 * @param filtro el carga academica
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarCargaAcademica(BaseSearch filtro);

	/**
	 * Controlador accion det malla curricular.
	 *
	 * @param obj        el det malla curricular
	 * @param accionType el accion type
	 * @return the det malla curricular
	 * @throws Exception the exception
	 */
	DetMallaCurricularDTO controladorAccionDetMallaCurricular(DetMallaCurricularDTO obj, AccionType accionType);

	/**
	 * Listar det malla curricular.
	 *
	 * @param filtro el det malla curricular
	 * @return the list
	 * @throws Exception the exception
	 */
	List<DetMallaCurricularDTO> listarDetMallaCurricular(BaseSearch filtro);

	/**
	 * contar lista det malla curricular.
	 *
	 * @param filtro el det malla curricular
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarDetMallaCurricular(BaseSearch filtro);

	/**
	 * Controlador accion aula.
	 *
	 * @param obj        el aula
	 * @param accionType el accion type
	 * @return the aula
	 * @throws Exception the exception
	 */
	AulaDTO controladorAccionAula(AulaDTO obj, AccionType accionType);

	/**
	 * Listar aula.
	 *
	 * @param filtro el aula
	 * @return the list
	 * @throws Exception the exception
	 */
	List<AulaDTO> listarAula(BaseSearch filtro);

	/**
	 * contar lista aula.
	 *
	 * @param filtro el aula
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarAula(BaseSearch filtro);

	/**
	 * Controlador accion periodo.
	 *
	 * @param obj        el periodo
	 * @param accionType el accion type
	 * @return the periodo
	 * @throws Exception the exception
	 */
	PeriodoDTO controladorAccionPeriodo(PeriodoDTO obj, AccionType accionType);

	/**
	 * Listar periodo.
	 *
	 * @param filtro el periodo
	 * @return the list
	 * @throws Exception the exception
	 */
	List<PeriodoDTO> listarPeriodo(BaseSearch filtro);

	/**
	 * contar lista periodo.
	 *
	 * @param filtro el periodo
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarPeriodo(BaseSearch filtro);

	/**
	 * Controlador accion matricula.
	 *
	 * @param obj        el matricula
	 * @param accionType el accion type
	 * @return the matricula
	 * @throws Exception the exception
	 */
	MatriculaDTO controladorAccionMatricula(MatriculaDTO obj, AccionType accionType);

	/**
	 * Listar matricula.
	 *
	 * @param filtro el matricula
	 * @return the list
	 * @throws Exception the exception
	 */
	List<MatriculaDTO> listarMatricula(BaseSearch filtro);

	/**
	 * contar lista matricula.
	 *
	 * @param filtro el matricula
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarMatricula(BaseSearch filtro);

	/**
	 * Controlador accion det matricula.
	 *
	 * @param obj        el det matricula
	 * @param accionType el accion type
	 * @return the det matricula
	 * @throws Exception the exception
	 */
	DetMatriculaDTO controladorAccionDetMatricula(DetMatriculaDTO obj, AccionType accionType);

	/**
	 * Listar det matricula.
	 *
	 * @param filtro el det matricula
	 * @return the list
	 * @throws Exception the exception
	 */
	List<DetMatriculaDTO> listarDetMatricula(BaseSearch filtro);

	/**
	 * contar lista det matricula.
	 *
	 * @param filtro el det matricula
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarDetMatricula(BaseSearch filtro);

	/**
	 * Controlador accion horario.
	 *
	 * @param obj        el horario
	 * @param accionType el accion type
	 * @return the horario
	 * @throws Exception the exception
	 */
	HorarioDTO controladorAccionHorario(HorarioDTO obj, AccionType accionType);

	/**
	 * Listar horario.
	 *
	 * @param filtro el horario
	 * @return the list
	 * @throws Exception the exception
	 */
	List<HorarioDTO> listarHorario(BaseSearch filtro);

	/**
	 * contar lista horario.
	 *
	 * @param filtro el horario
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarHorario(BaseSearch filtro);

	/**
	 * Controlador accion unidad.
	 *
	 * @param obj        el unidad
	 * @param accionType el accion type
	 * @return the unidad
	 * @throws Exception the exception
	 */
	UnidadDTO controladorAccionUnidad(UnidadDTO obj, AccionType accionType);

	/**
	 * Listar unidad.
	 *
	 * @param filtro el unidad
	 * @return the list
	 * @throws Exception the exception
	 */
	List<UnidadDTO> listarUnidad(BaseSearch filtro);

	/**
	 * contar lista unidad.
	 *
	 * @param filtro el unidad
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarUnidad(BaseSearch filtro);

	List<DetMallaCurricularDTO> obtenerDetMallaCurricular(BaseSearch filtro);

	List<DetalleCargaAcademicaDTO> obtenerDetCargaAcademica(BaseSearch filtro);

	void agregarCargaAcademica(CargaAcademicaDTO obj);

	List<DetalleCargaAcademicaDTO> obtenerCursosPosiblesLlevar(Map<String, Object> parametroMap);

	MatriculaDTO registrarMatricula(MatriculaDTO obj);

	List<DetalleCargaAcademicaDTO> listarDetCargaAcademicaByDocente(BaseSearch filtro);

	MatriculaDTO obtenerMatricula(Long idAnhio, String idAlumno, Long idTurno, Long idPeriodo);

	List<CriterioEvaluacionDTO> listarCriterioEvaluacion(String estado, String idDetMallaCurricular,
			String idCriterioEvaluacionPadre);

	void eliminarRegistrarMatricula(String idMatricula, String userName);

	List<DetMatriculaDTO> obtenerListMatricula(String idMatricula);

	PeriodoDTO findPeriodo(PeriodoDTO filtro);

	UnidadDTO findUnidad(UnidadDTO filtro);

	PabellonDTO findPabellon(PabellonDTO filtro);

	GrupoDTO findGrupo(GrupoDTO filtro);
}