package pe.buildsoft.erp.core.domain.interfaces.servicios.nota;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.nota.AsistenciaAlumno;
import pe.buildsoft.erp.core.domain.entidades.nota.CursoNotaPeriodo;
import pe.buildsoft.erp.core.domain.entidades.nota.CursoNotaPeriodoProm;
import pe.buildsoft.erp.core.domain.entidades.nota.CursoNotaUnidad;
import pe.buildsoft.erp.core.domain.entidades.nota.CursoNotaUnidadProm;
import pe.buildsoft.erp.core.domain.entidades.nota.DetRegistroNota;
import pe.buildsoft.erp.core.domain.entidades.nota.RegistroNota;
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
@Local
public interface NotaServiceLocal {

	void registrarAsistencia(List<AsistenciaAlumno> listaObj);

	List<AsistenciaAlumno> obtenerAsistenciaAlumno(AsistenciaAlumno filtro);

	/**
	 * Controlador accion curso nota periodo.
	 *
	 * @param cursoNotaPeriodo el curso nota periodo
	 * @param accionType       el accion type
	 * @return the curso nota periodo
	 * @throws Exception the exception
	 */
	CursoNotaPeriodo controladorAccionCursoNotaPeriodo(CursoNotaPeriodo obj, AccionType accionType);

	/**
	 * Listar curso nota periodo.
	 *
	 * @param cursoNotaPeriodo el curso nota periodo
	 * @return the list
	 * @throws Exception the exception
	 */
	List<CursoNotaPeriodo> listarCursoNotaPeriodo(BaseSearch filtro);

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
	CursoNotaPeriodoProm controladorAccionCursoNotaPeriodoProm(CursoNotaPeriodoProm obj, AccionType accionType);

	/**
	 * Listar curso nota periodo prom.
	 *
	 * @param cursoNotaPeriodoProm el curso nota periodo prom
	 * @return the list
	 * @throws Exception the exception
	 */
	List<CursoNotaPeriodoProm> listarCursoNotaPeriodoProm(BaseSearch filtro);

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
	CursoNotaUnidad controladorAccionCursoNotaUnidad(CursoNotaUnidad obj, AccionType accionType);

	/**
	 * Listar curso nota unidad.
	 *
	 * @param cursoNotaUnidad el curso nota unidad
	 * @return the list
	 * @throws Exception the exception
	 */
	List<CursoNotaUnidad> listarCursoNotaUnidad(BaseSearch filtro);

	/**
	 * contar lista curso nota unidad.
	 *
	 * @param cursoNotaUnidad el curso nota unidad
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarCursoNotaUnidad(BaseSearch obj);

	/**
	 * Controlador accion curso nota unidad prom.
	 *
	 * @param cursoNotaUnidadProm el curso nota unidad prom
	 * @param accionType          el accion type
	 * @return the curso nota unidad prom
	 * @throws Exception the exception
	 */
	CursoNotaUnidadProm controladorAccionCursoNotaUnidadProm(CursoNotaUnidadProm obj, AccionType accionType);

	/**
	 * Listar curso nota unidad prom.
	 *
	 * @param cursoNotaUnidadProm el curso nota unidad prom
	 * @return the list
	 * @throws Exception the exception
	 */
	List<CursoNotaUnidadProm> listarCursoNotaUnidadProm(BaseSearch filtro);

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
	DetRegistroNota controladorAccionDetRegistroNota(DetRegistroNota obj, AccionType accionType);

	/**
	 * Listar det registro nota.
	 *
	 * @param detRegistroNota el det registro nota
	 * @return the list
	 * @throws Exception the exception
	 */
	List<DetRegistroNota> listarDetRegistroNota(BaseSearch filtro);

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
	RegistroNota controladorAccionRegistroNota(RegistroNota obj, AccionType accionType);

	/**
	 * Listar registro nota.
	 *
	 * @param registroNota el registro nota
	 * @return the list
	 * @throws Exception the exception
	 */
	List<RegistroNota> listarRegistroNota(BaseSearch filtro);

	/**
	 * contar lista registro nota.
	 *
	 * @param registroNota el registro nota
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarRegistroNota(BaseSearch filtro);

	List<DetRegistroNota> obtenerRegistroNota(String idDetMallaCurricular, String idAlumno,
			Boolean esActaEvaluacionFinalAplazado);

	void registrarNota(List<DetRegistroNota> listaObj, String userName);

}