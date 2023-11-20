package pe.buildsoft.erp.core.application.interfaces.nota;

import java.util.List;

import pe.buildsoft.erp.core.application.entidades.nota.AsistenciaAlumnoDTO;
import pe.buildsoft.erp.core.application.entidades.nota.CursoNotaPeriodoDTO;
import pe.buildsoft.erp.core.application.entidades.nota.CursoNotaPeriodoPromDTO;
import pe.buildsoft.erp.core.application.entidades.nota.CursoNotaUnidadDTO;
import pe.buildsoft.erp.core.application.entidades.nota.CursoNotaUnidadPromDTO;
import pe.buildsoft.erp.core.application.entidades.nota.DetRegistroNotaDTO;
import pe.buildsoft.erp.core.application.entidades.nota.RegistroNotaDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;

/**
 * La Class NotaServiceLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Jun 10 00:13:45 COT 2021
 * @since BUILDERP-CORE 2.1
 */
public interface NotaAppServiceLocal {

	void registrarAsistencia(List<AsistenciaAlumnoDTO> listaObj);

	List<AsistenciaAlumnoDTO> obtenerAsistenciaAlumno(AsistenciaAlumnoDTO filtro);

	/**
	 * Controlador accion curso nota periodo.
	 *
	 * @param cursoNotaPeriodo el curso nota periodo
	 * @param accionType       el accion type
	 * @return the curso nota periodo
	 * @throws Exception the exception
	 */
	CursoNotaPeriodoDTO controladorAccionCursoNotaPeriodo(CursoNotaPeriodoDTO obj, AccionType accionType);

	/**
	 * Listar curso nota periodo.
	 *
	 * @param cursoNotaPeriodo el curso nota periodo
	 * @return the list
	 * @throws Exception the exception
	 */
	List<CursoNotaPeriodoDTO> listarCursoNotaPeriodo(BaseSearch filtro);

	/**
	 * contar lista curso nota periodo.
	 *
	 * @param cursoNotaPeriodo el curso nota periodo
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarCursoNotaPeriodo(BaseSearch filtro);

	/**
	 * Controlador accion curso nota periodo prom.
	 *
	 * @param cursoNotaPeriodoProm el curso nota periodo prom
	 * @param accionType           el accion type
	 * @return the curso nota periodo prom
	 * @throws Exception the exception
	 */
	CursoNotaPeriodoPromDTO controladorAccionCursoNotaPeriodoProm(CursoNotaPeriodoPromDTO obj, AccionType accionType);

	/**
	 * Listar curso nota periodo prom.
	 *
	 * @param cursoNotaPeriodoProm el curso nota periodo prom
	 * @return the list
	 * @throws Exception the exception
	 */
	List<CursoNotaPeriodoPromDTO> listarCursoNotaPeriodoProm(BaseSearch filtro);

	/**
	 * contar lista curso nota periodo prom.
	 *
	 * @param cursoNotaPeriodoProm el curso nota periodo prom
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarCursoNotaPeriodoProm(BaseSearch filtro);

	/**
	 * Controlador accion curso nota unidad.
	 *
	 * @param cursoNotaUnidad el curso nota unidad
	 * @param accionType      el accion type
	 * @return the curso nota unidad
	 * @throws Exception the exception
	 */
	CursoNotaUnidadDTO controladorAccionCursoNotaUnidad(CursoNotaUnidadDTO obj, AccionType accionType);

	/**
	 * Listar curso nota unidad.
	 *
	 * @param cursoNotaUnidad el curso nota unidad
	 * @return the list
	 * @throws Exception the exception
	 */
	List<CursoNotaUnidadDTO> listarCursoNotaUnidad(BaseSearch filtro);

	/**
	 * contar lista curso nota unidad.
	 *
	 * @param cursoNotaUnidad el curso nota unidad
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarCursoNotaUnidad(BaseSearch filtro);

	/**
	 * Controlador accion curso nota unidad prom.
	 *
	 * @param cursoNotaUnidadProm el curso nota unidad prom
	 * @param accionType          el accion type
	 * @return the curso nota unidad prom
	 * @throws Exception the exception
	 */
	CursoNotaUnidadPromDTO controladorAccionCursoNotaUnidadProm(CursoNotaUnidadPromDTO obj, AccionType accionType);

	/**
	 * Listar curso nota unidad prom.
	 *
	 * @param cursoNotaUnidadProm el curso nota unidad prom
	 * @return the list
	 * @throws Exception the exception
	 */
	List<CursoNotaUnidadPromDTO> listarCursoNotaUnidadProm(BaseSearch filtro);

	/**
	 * contar lista curso nota unidad prom.
	 *
	 * @param cursoNotaUnidadProm el curso nota unidad prom
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarCursoNotaUnidadProm(BaseSearch filtro);

	/**
	 * Controlador accion det registro nota.
	 *
	 * @param detRegistroNota el det registro nota
	 * @param accionType      el accion type
	 * @return the det registro nota
	 * @throws Exception the exception
	 */
	DetRegistroNotaDTO controladorAccionDetRegistroNota(DetRegistroNotaDTO obj, AccionType accionType);

	/**
	 * Listar det registro nota.
	 *
	 * @param detRegistroNota el det registro nota
	 * @return the list
	 * @throws Exception the exception
	 */
	List<DetRegistroNotaDTO> listarDetRegistroNota(BaseSearch filtro);

	/**
	 * contar lista det registro nota.
	 *
	 * @param detRegistroNota el det registro nota
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarDetRegistroNota(BaseSearch filtro);

	/**
	 * Controlador accion registro nota.
	 *
	 * @param registroNota el registro nota
	 * @param accionType   el accion type
	 * @return the registro nota
	 * @throws Exception the exception
	 */
	RegistroNotaDTO controladorAccionRegistroNota(RegistroNotaDTO obj, AccionType accionType);

	/**
	 * Listar registro nota.
	 *
	 * @param registroNota el registro nota
	 * @return the list
	 * @throws Exception the exception
	 */
	List<RegistroNotaDTO> listarRegistroNota(BaseSearch filtro);

	/**
	 * contar lista registro nota.
	 *
	 * @param registroNota el registro nota
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarRegistroNota(BaseSearch filtro);

	List<DetRegistroNotaDTO> obtenerRegistroNota(String idDetMallaCurricular, String idAlumno,
			Boolean esActaEvaluacionFinalAplazado);

	void registrarNota(List<DetRegistroNotaDTO> listaObj, String userName);

}