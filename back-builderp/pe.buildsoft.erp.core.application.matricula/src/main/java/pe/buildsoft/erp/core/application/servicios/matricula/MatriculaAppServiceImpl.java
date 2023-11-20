package pe.buildsoft.erp.core.application.servicios.matricula;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.inject.Inject;
import jakarta.ejb.Stateless;

import pe.buildsoft.erp.core.application.entidades.admision.GradoDTO;
import pe.buildsoft.erp.core.application.entidades.admision.SeccionDTO;
import pe.buildsoft.erp.core.application.entidades.common.AnhioDTO;
import pe.buildsoft.erp.core.application.entidades.common.ItemDTO;
import pe.buildsoft.erp.core.application.entidades.common.UbigeoDTO;
import pe.buildsoft.erp.core.application.entidades.escalafon.PersonalDTO;
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
import pe.buildsoft.erp.core.application.interfaces.matricula.MatriculaAppServiceLocal;
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
import pe.buildsoft.erp.core.domain.interfaces.servicios.matricula.MatriculaServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.cache.AtributosEntityCacheUtil;
import pe.buildsoft.erp.core.infra.transversal.cache.ICache;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.BaseTransfer;
import pe.buildsoft.erp.core.infra.transversal.utilitario.CollectionUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ObjectUtil;
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
public class MatriculaAppServiceImpl extends BaseTransfer implements MatriculaAppServiceLocal {

	private static final String CACHE_SECCION = "seccion";
	private static final String CACHE_ANHIO = "anhio";

	private static final String DET_MALLA_CURRICULAR = "detMallaCurricular";
	/** El servicio malla curricular dao impl. */
	@Inject
	private MatriculaServiceLocal servicio;

	@Inject
	private ICache cacheUtil;

	public MatriculaAppServiceImpl() {
		AtributosEntityCacheUtil.getInstance().sincronizarAtributos("pe.buildsoft.erp.core.domain.entidades.escalafon",
				"pe.buildsoft.erp.core.domain.entidades.common", "pe.buildsoft.erp.core.domain.entidades.planilla",
				"pe.buildsoft.erp.core.domain.entidades.matricula", "pe.buildsoft.erp.core.domain.entidades.admision");
	}

	@Override
	public CriterioEvaluacionDTO controladorAccionCriterioEvaluacion(CriterioEvaluacionDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionCriterioEvaluacion(to(obj, CriterioEvaluacion.class), accionType),
				CriterioEvaluacionDTO.class);
	}

	@Override
	public List<CriterioEvaluacionDTO> listarCriterioEvaluacion(BaseSearch filtro) {
		List<CriterioEvaluacionDTO> resultado = toList(this.servicio.listarCriterioEvaluacion(filtro),
				CriterioEvaluacionDTO.class, "criterioEvaluacionPadre", DET_MALLA_CURRICULAR);
		for (var obj : resultado) {
			obj.getCriterioEvaluacionPadre().setDescripcionView(obj.getCodigo() + " " + obj.getNombre());
		}
		return resultado;
	}

	@Override
	public List<CriterioEvaluacionDTO> listarCriterioEvaluacion(String estado, String idDetMallaCurricular,
			String idCriterioEvaluacionPadre) {
		List<CriterioEvaluacionDTO> resultado = new ArrayList<>();
		List<CriterioEvaluacionDTO> listadoCriterio = toList(
				this.servicio.listarCriterioEvaluacion(estado, idDetMallaCurricular, idCriterioEvaluacionPadre),
				CriterioEvaluacionDTO.class, "criterioEvaluacionPadre", DET_MALLA_CURRICULAR);
		if (listadoCriterio != null) {
			resultado = generarCriterioEvaluacion(listadoCriterio);
		}
		return resultado;
	}

	private List<CriterioEvaluacionDTO> generarCriterioEvaluacion(List<CriterioEvaluacionDTO> listadoCriterio) {
		List<CriterioEvaluacionDTO> resultado = new ArrayList<>();
		for (var criterioEvaluacion : listadoCriterio) {
			if (criterioEvaluacion.getCriterioEvaluacionPadre().getIdCriterioEvaluacion() == null) {
				criterioEvaluacion.setCriterioEvaluacionHijos(
						generarSubCriterioEvaluacion(listadoCriterio, criterioEvaluacion.getIdCriterioEvaluacion()));
				resultado.add(criterioEvaluacion);
			}
		}
		return resultado;
	}

	private List<CriterioEvaluacionDTO> generarSubCriterioEvaluacion(List<CriterioEvaluacionDTO> listadoCriterio,
			String idCriterioPadre) {
		List<CriterioEvaluacionDTO> resultado = new ArrayList<>();
		Map<String, CriterioEvaluacionDTO> criterioEvaluacionMap = new HashMap<>();
		for (var criterioEvaluacion : listadoCriterio) {
			if (criterioEvaluacion.getCriterioEvaluacionPadre().getIdCriterioEvaluacion() != null) {
				if (criterioEvaluacion.getCriterioEvaluacionPadre().getIdCriterioEvaluacion().equals(idCriterioPadre)) {
					criterioEvaluacion.setCriterioEvaluacionHijos(generarSubCriterioEvaluacion(listadoCriterio,
							criterioEvaluacion.getIdCriterioEvaluacion()));
					resultado.add(criterioEvaluacion);
				}
			}

			if (!criterioEvaluacion.getCriterioEvaluacionHijos().isEmpty()) {
				for (var obj : criterioEvaluacion.getCriterioEvaluacionHijos()) {
					criterioEvaluacionMap.put(obj.getCriterioEvaluacionPadre().getIdCriterioEvaluacion(),
							obj.getCriterioEvaluacionPadre());
				}
			}

			if (!criterioEvaluacionMap.containsKey(criterioEvaluacion.getIdCriterioEvaluacion())) {
				// System.out.println("AQUIXX" +criterioEvaluacionMap);
			}
		}
		// System.out.println("AQUIXX" +criterioEvaluacionMap.size());
		return resultado;
	}

	@Override
	public int contarListarCriterioEvaluacion(BaseSearch filtro) {
		return this.servicio.contarListarCriterioEvaluacion(filtro);
	}

	@Override
	public MallaCurricularDTO controladorAccionMallaCurricular(MallaCurricularDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionMallaCurricular(to(obj, MallaCurricular.class), accionType),
				MallaCurricularDTO.class);
	}

	@Override
	public List<MallaCurricularDTO> listarMallaCurricular(BaseSearch filtro) {
		List<MallaCurricularDTO> resultado = toList(this.servicio.listarMallaCurricular(filtro),
				MallaCurricularDTO.class, "anhio", "grado:{itemByNivel}");
		for (var obj : resultado) {
			generarDescripcionViewAnhio(obj.getAnhio());
			generarDescripcionViewGrado(obj.getGrado());
		}
		return resultado;
	}

	private GradoDTO generarDescripcionViewGrado(GradoDTO grado) {
		if (grado != null && grado.getIdGrado() != null) {
			grado.setDescripcionView(grado.getItemByNivel().getNombre() + " " + grado.getNombre());
		}
		return grado;
	}

	@Override
	public int contarListarMallaCurricular(BaseSearch filtro) {
		return this.servicio.contarListarMallaCurricular(filtro);
	}

	@Override
	public AlumnoDTO controladorAccionAlumno(AlumnoDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionAlumno(to(obj, Alumno.class), accionType), AlumnoDTO.class);
	}

	@Override
	public List<AlumnoDTO> listarAlumno(BaseSearch filtro) {
		List<AlumnoDTO> resultado = toList(this.servicio.listarAlumno(filtro), AlumnoDTO.class, "grado:{itemByNivel}",
				"itemByDocIdentidad", "lugarNacimiento", "itemByNacionalidad", "sede", "entidad");
		for (var obj : resultado) {
			generarDescripcionViewGrado(obj.getGrado());
			generarDescripcionViewItem(obj.getItemByDocIdentidad());
			generarDescripcionViewItem(obj.getItemByNacionalidad());
			generarDescripcionViewUbigeo(obj.getLugarNacimiento());
			if (obj.getSede() != null) {
				obj.getSede().setDescripcionView(obj.getSede().getNombre());
				// obj.getEntidad().setDescripcionView(obj.getEntidad().getNombre());
			}
		}
		return resultado;
	}

	@Override
	public int contarListarAlumno(BaseSearch filtro) {
		return this.servicio.contarListarAlumno(filtro);
	}

	@Override
	public PabellonDTO controladorAccionPabellon(PabellonDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionPabellon(to(obj, Pabellon.class), accionType), PabellonDTO.class);
	}

	@Override
	public List<PabellonDTO> listarPabellon(BaseSearch filtro) {
		List<PabellonDTO> resultado = toList(this.servicio.listarPabellon(filtro), PabellonDTO.class);
		for (var objData : resultado) {
			generarDescripcionViewPabellon(objData);
		}
		return resultado;
	}

	private PabellonDTO generarDescripcionViewPabellon(PabellonDTO obj) {
		obj.setDescripcionView(obj.getDescripcion());
		return obj;
	}

	@Override
	public int contarListarPabellon(BaseSearch filtro) {
		return this.servicio.contarListarPabellon(filtro);
	}

	@Override
	public PabellonDTO findPabellon(PabellonDTO filtro) {
		return toDTO(servicio.findPabellon(to(filtro, Pabellon.class)), PabellonDTO.class);
	}

	@Override
	public PreRequisitoDTO controladorAccionPreRequisito(PreRequisitoDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionPreRequisito(to(obj, PreRequisito.class), accionType),
				PreRequisitoDTO.class);
	}

	@Override
	public List<PreRequisitoDTO> listarPreRequisito(BaseSearch filtro) {
		return toList(this.servicio.listarPreRequisito(filtro), PreRequisitoDTO.class);
	}

	@Override
	public int contarListarPreRequisito(BaseSearch filtro) {
		return this.servicio.contarListarPreRequisito(filtro);
	}

	@Override
	public CalendarioAcademicoDTO controladorAccionCalendarioAcademico(CalendarioAcademicoDTO obj,
			AccionType accionType) {
		return toDTO(servicio.controladorAccionCalendarioAcademico(to(obj, CalendarioAcademico.class), accionType),
				CalendarioAcademicoDTO.class);
	}

	@Override
	public List<CalendarioAcademicoDTO> listarCalendarioAcademico(BaseSearch filtro) {
		return toList(this.servicio.listarCalendarioAcademico(filtro), CalendarioAcademicoDTO.class);
	}

	@Override
	public int contarListarCalendarioAcademico(BaseSearch filtro) {
		return this.servicio.contarListarCalendarioAcademico(filtro);
	}

	@Override
	public GrupoDTO controladorAccionGrupo(GrupoDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionGrupo(to(obj, Grupo.class), accionType), GrupoDTO.class);
	}

	@Override
	public List<GrupoDTO> listarGrupo(BaseSearch filtro) {
		List<GrupoDTO> resultado = toList(this.servicio.listarGrupo(filtro), GrupoDTO.class);
		for (var obj : resultado) {
			generarDescripcionViewGrupo(obj);
		}
		return resultado;
	}

	private GrupoDTO generarDescripcionViewGrupo(GrupoDTO obj) {
		obj.setDescripcionView(obj.getNombre());
		return obj;
	}

	@Override
	public int contarListarGrupo(BaseSearch filtro) {
		return this.servicio.contarListarGrupo(filtro);
	}

	@Override
	public GrupoDTO findGrupo(GrupoDTO filtro) {
		return toDTO(servicio.findGrupo(to(filtro, Grupo.class)), GrupoDTO.class);
	}

	@Override
	public CargaAcademicaDTO eliminarCargaAcademica(CargaAcademicaDTO obj) {
		return toDTO(servicio.eliminarCargaAcademica(to(obj, CargaAcademica.class)), CargaAcademicaDTO.class);
	}

	@Override
	public List<CargaAcademicaDTO> listarCargaAcademica(BaseSearch filtro) {
		List<CargaAcademicaDTO> resultado = new ArrayList<>();
		List<CargaAcademica> listaTemo = this.servicio.listarCargaAcademica(filtro);
		for (var obj : listaTemo) {
			CargaAcademicaDTO cargaAcademicaDTO = toDTO(obj, CargaAcademicaDTO.class, "aula", "personalByTutor",
					"personalByCoTutor", "personalByCoordinador");
			resultado.add(cargaAcademicaDTO);
		}

		Map<String, String> completeMap = new HashMap<>();
		completeMap.put("idSeccion", "seccion");
		completeMap.put("idItemByTurno", "itemByTurno");
		completeMap.put("idAnhio", "anhio");
		cacheUtil.completarData(resultado, completeMap, CACHE_SECCION);

		for (var obj : resultado) {
			generarDescripcionViewAnhio(obj.getAnhio());
			generarDescripcionViewAula(obj.getAula());
			generarDescripcionViewItem(obj.getItemByTurno());
			generarDescripcionViewGrado(obj.getSeccion().getGrado());
			generarDescripcionViewSeccion(obj.getSeccion());
			generarDescripcionViewPersonal(obj.getPersonalByTutor());
			generarDescripcionViewPersonal(obj.getPersonalByCoTutor());
			generarDescripcionViewPersonal(obj.getPersonalByCoordinador());
		}
		return resultado;
	}

	private PersonalDTO generarDescripcionViewPersonal(PersonalDTO obj) {
		obj.setDescripcionView(obj.getNombres() + " " + obj.getApellidoPaterno() + " " + obj.getApellidoMaterno());
		return obj;
	}

	private SeccionDTO generarDescripcionViewSeccion(SeccionDTO obj) {
		if (obj != null && obj.getIdSeccion() != null) {
			obj.setDescripcionView(obj.getGrado().getDescripcionView() + " - " + obj.getNombre());
		}
		return obj;
	}

	private UbigeoDTO generarDescripcionViewUbigeo(UbigeoDTO obj) {
		if (obj != null && obj.getIdUbigeo() != null) {
			obj.setDescripcionView(obj.getIdUbigeo() + " " + obj.getDescripcion());
		}
		return obj;
	}

	private ItemDTO generarDescripcionViewItem(ItemDTO item) {
		if (item != null && item.getIdItem() != null) {
			item.setDescripcionView(item.getCodigoExterno() + " " + item.getNombre());
		}
		return item;
	}

	@Override
	public int contarListarCargaAcademica(BaseSearch filtro) {
		return this.servicio.contarListarCargaAcademica(filtro);
	}

	private AnhioDTO generarDescripcionViewAnhio(AnhioDTO obj) {
		obj.setDescripcionView(obj.getIdAnhio() + " " + obj.getNombre());
		return obj;
	}

	@Override
	public DetMallaCurricularDTO controladorAccionDetMallaCurricular(DetMallaCurricularDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionDetMallaCurricular(to(obj, DetMallaCurricular.class), accionType),
				DetMallaCurricularDTO.class);
	}

	@Override
	public List<DetMallaCurricularDTO> listarDetMallaCurricular(BaseSearch filtro) {
		List<DetMallaCurricularDTO> resultado = toList(this.servicio.listarDetMallaCurricular(filtro),
				DetMallaCurricularDTO.class, "detMallaCurricularPadre", "mallaCurricular");
		for (var obj : resultado) {
			obj.getDetMallaCurricularPadre()
					.setDescripcionView(obj.getCodigoAsignatura() + " " + obj.getDescripcionCurso());
		}
		return resultado;

	}

	@Override
	public List<DetMallaCurricularDTO> obtenerDetMallaCurricular(BaseSearch filtro) {
		List<DetMallaCurricularDTO> resultado = new ArrayList<>();
		List<DetMallaCurricular> listadoDetMalla = this.servicio.obtenerDetMallaCurricular(filtro);
		for (var obj : listadoDetMalla) {
			DetMallaCurricularDTO detMallaDTO = toDTO(obj, DetMallaCurricularDTO.class, "detMallaCurricularPadre");
			detMallaDTO.setDetMallaCurricularPadre(
					toDTO(obj.getDetMallaCurricularPadre(), DetMallaCurricularDTO.class, "detMallaCurricularPadre"));
			resultado.add(detMallaDTO);
		}
		return resultado;
	}

	@Override
	public int contarListarDetMallaCurricular(BaseSearch filtro) {
		return this.servicio.contarListarDetMallaCurricular(filtro);
	}

	@Override
	public AulaDTO controladorAccionAula(AulaDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionAula(to(obj, Aula.class), accionType), AulaDTO.class);
	}

	@Override
	public List<AulaDTO> listarAula(BaseSearch filtro) {
		List<AulaDTO> resultado = toList(this.servicio.listarAula(filtro), AulaDTO.class, "pabellon");
		for (var obj : resultado) {
			obj.setDescripcionView(obj.getDescripcion());
			generarDescripcionViewPabellon(obj.getPabellon());
		}
		return resultado;
	}

	private AulaDTO generarDescripcionViewAula(AulaDTO obj) {
		obj.setDescripcionView(obj.getDescripcion());
		return obj;
	}

	@Override
	public int contarListarAula(BaseSearch filtro) {
		return this.servicio.contarListarAula(filtro);
	}

	@Override
	public PeriodoDTO controladorAccionPeriodo(PeriodoDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionPeriodo(to(obj, Periodo.class), accionType), PeriodoDTO.class);
	}

	@Override
	public List<PeriodoDTO> listarPeriodo(BaseSearch filtro) {
		return toList(this.servicio.listarPeriodo(filtro), PeriodoDTO.class);
	}

	@Override
	public int contarListarPeriodo(BaseSearch filtro) {
		return this.servicio.contarListarPeriodo(filtro);
	}

	@Override
	public PeriodoDTO findPeriodo(PeriodoDTO filtro) {
		return toDTO(servicio.findPeriodo(to(filtro, Periodo.class)), PeriodoDTO.class);
	}

	@Override
	public MatriculaDTO controladorAccionMatricula(MatriculaDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionMatricula(to(obj, Matricula.class), accionType), MatriculaDTO.class);
	}

	@Override
	public List<MatriculaDTO> listarMatricula(BaseSearch filtro) {
		List<MatriculaDTO> resultado = new ArrayList<>();
		List<Matricula> listaTemp = this.servicio.listarMatricula(filtro);
		for (var obj : listaTemp) {
			MatriculaDTO matriculaDTO = toDTO(obj, MatriculaDTO.class, "periodo", "alumno", "cargaAcademica");
			matriculaDTO.setCargaAcademica(toDTO(obj.getCargaAcademica(), CargaAcademicaDTO.class));
			resultado.add(matriculaDTO);
		}

		Map<String, String> completeMap = new HashMap<>();
		completeMap.put("idAnhio", "anhio");
		cacheUtil.completarData(resultado, completeMap, CACHE_ANHIO);
		completeMap = new HashMap<>();
		completeMap.put("idSeccion", "seccion");
		for (var obj : resultado) {
			cacheUtil.completarData(obj.getCargaAcademica(), completeMap, CACHE_SECCION);
			obj.getPeriodo().setDescripcionView(obj.getPeriodo().getDescripcion());
			generarDescripcionViewGrado(obj.getCargaAcademica().getSeccion().getGrado());
			generarDescripcionViewAlumno(obj.getAlumno());
		}
		return resultado;
	}

	private AlumnoDTO generarDescripcionViewAlumno(AlumnoDTO obj) {
		obj.setDescripcionView(obj.getNombres() + " " + obj.getApellidoPaterno() + " " + obj.getApellidoMaterno());
		return obj;
	}

	@Override
	public int contarListarMatricula(BaseSearch filtro) {
		return this.servicio.contarListarMatricula(filtro);
	}

	@Override
	public DetMatriculaDTO controladorAccionDetMatricula(DetMatriculaDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionDetMatricula(to(obj, DetMatricula.class), accionType),
				DetMatriculaDTO.class);
	}

	@Override
	public List<DetMatriculaDTO> listarDetMatricula(BaseSearch filtro) {
		return toList(this.servicio.listarDetMatricula(filtro), DetMatriculaDTO.class);
	}

	@Override
	public int contarListarDetMatricula(BaseSearch filtro) {
		return this.servicio.contarListarDetMatricula(filtro);
	}

	@Override
	public HorarioDTO controladorAccionHorario(HorarioDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionHorario(to(obj, Horario.class), accionType), HorarioDTO.class);
	}

	@Override
	public List<HorarioDTO> listarHorario(BaseSearch filtro) {
		return toList(this.servicio.listarHorario(filtro), HorarioDTO.class);
	}

	@Override
	public int contarListarHorario(BaseSearch filtro) {
		return this.servicio.contarListarHorario(filtro);
	}

	@Override
	public UnidadDTO controladorAccionUnidad(UnidadDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionUnidad(to(obj, Unidad.class), accionType), UnidadDTO.class);
	}

	@Override
	public List<UnidadDTO> listarUnidad(BaseSearch filtro) {
		List<UnidadDTO> resultado = toList(this.servicio.listarUnidad(filtro), UnidadDTO.class, "periodo");
		for (var obj : resultado) {
			obj.getPeriodo().setDescripcionView(obj.getPeriodo().getDescripcion());
		}
		return resultado;
	}

	@Override
	public int contarListarUnidad(BaseSearch filtro) {
		return this.servicio.contarListarUnidad(filtro);
	}

	@Override
	public UnidadDTO findUnidad(UnidadDTO filtro) {
		return toDTO(servicio.findUnidad(to(filtro, Unidad.class)), UnidadDTO.class);
	}

	// TODO:VER OPTIMIZACION
	@Override
	public List<DetalleCargaAcademicaDTO> obtenerDetCargaAcademica(BaseSearch filtro) {
		List<DetalleCargaAcademicaDTO> resultado = new ArrayList<>();
		Map<String, DetalleCargaAcademicaDTO> detCargaAcademicaMap = new HashMap<>();
		Map<String, DetMallaCurricularDTO> detCargaAcademicaPadreMap = new HashMap<>();
		Map<String, List<DetalleCargaAcademicaDTO>> detMallaMap = new HashMap<>();
		List<DetalleCargaAcademicaDTO> lisdetCL = toList(servicio.obtenerDetCargaAcademica(filtro),
				DetalleCargaAcademicaDTO.class, "grupo", "detMallaCurricular:{detMallaCurricularPadre}",
				"personalByDocentePrincipal", "personalByDocenteAuxiliar", "mallacurricular");

		BaseSearch detMallaCurri = new BaseSearch();
		detMallaCurri.setIdGrado(filtro.getIdGrado());
		detMallaCurri.setIdAnhio(filtro.getIdAnhio());

		List<DetMallaCurricularDTO> lisDetMallaCurricular = obtenerDetMallaCurricular(detMallaCurri);

		for (var detCTemp : lisdetCL) {
			String key = detCTemp.getDetMallaCurricular().getDetMallaCurricularPadre().getIdDetMallaCurricular();
			if (StringUtil.isNotNullOrBlank(key)) {
				if (!detMallaMap.containsKey(key)) {
					List<DetalleCargaAcademicaDTO> res = new ArrayList<>();
					res.add(detCTemp);
					detMallaMap.put(key, res);
				} else {
					List<DetalleCargaAcademicaDTO> res = detMallaMap.get(key);
					res.add(detCTemp);
				}
				if (!detCargaAcademicaPadreMap.containsKey(key)) {
					detCargaAcademicaPadreMap.put(key, detCTemp.getDetMallaCurricular().getDetMallaCurricularPadre());
				}
			}
		}

		for (var entryMap : detCargaAcademicaPadreMap.entrySet()) {
			DetalleCargaAcademicaDTO obj = new DetalleCargaAcademicaDTO();
			obj.setDetMallaCurricular(entryMap.getValue());
			obj.setPersonalByDocentePrincipal(new PersonalDTO());
			obj.setPersonalByDocenteAuxiliar(new PersonalDTO());
			obj.setGrupo(new GrupoDTO());
			lisdetCL.add(obj);
		}

		Map<String, DetMallaCurricularDTO> detMallaValidarMap = new HashMap<>();
		for (var detCTemp : lisdetCL) {
			String key = detCTemp.getDetMallaCurricular().getIdDetMallaCurricular();
			if (StringUtil.isNullOrEmpty(detCTemp.getDetMallaCurricular().getDetMallaCurricularPadre())) {
				// if(!StringUtil.isNotNullOrBlank(detCTemp.getPersonalByDocentePrincipal().getIdPersonal()))
				// {

				if (detMallaMap.containsKey(key)) {
					detCTemp.setEsPadre(true);
					detCTemp.setDetalleCargaAcademicaList(detMallaMap.get(key));
					for (var objTemp : detCTemp.getDetalleCargaAcademicaList()) {
						String keyDet = objTemp.getDetMallaCurricular().getIdDetMallaCurricular();
						detMallaValidarMap.put(keyDet, objTemp.getDetMallaCurricular());
					}
				}
				// }
				BaseSearch detMallaCurriTemp = new BaseSearch();
				detMallaCurriTemp.setIdDetMallaCurricular(key);
				List<DetMallaCurricularDTO> lisDetMallaCurricularTemp = obtenerDetMallaCurricular(detMallaCurriTemp);

				if (!CollectionUtil.isEmpty(lisDetMallaCurricularTemp)) {
					for (var objTemp : lisDetMallaCurricularTemp) {
						String keyId = objTemp.getIdDetMallaCurricular();
						if (!detMallaValidarMap.containsKey(keyId)) {
							DetalleCargaAcademicaDTO objT = new DetalleCargaAcademicaDTO();
							objT.setDetMallaCurricular(objTemp);
							objT.setPersonalByDocentePrincipal(new PersonalDTO());
							objT.setPersonalByDocenteAuxiliar(new PersonalDTO());
							objT.setGrupo(new GrupoDTO());
							detCTemp.getDetalleCargaAcademicaList().add(objT);
						}
					}
				}
			}

			detCargaAcademicaMap.put(key, detCTemp);
		}

		for (var objData : lisDetMallaCurricular) {
			String key = objData.getIdDetMallaCurricular();
			if (detCargaAcademicaMap.containsKey(key)) {
				if (StringUtil.isNullOrEmpty(objData.getDetMallaCurricularPadre())) {
					resultado.add(detCargaAcademicaMap.get(key));
				}
			} else {
				if (StringUtil.isNullOrEmpty(objData.getDetMallaCurricularPadre())) {
					DetalleCargaAcademicaDTO obj = new DetalleCargaAcademicaDTO();
					obj.setDetMallaCurricular(objData);
					BaseSearch detMCurrTmp = new BaseSearch();
					detMCurrTmp.setIdDetMallaCurricular(key);
					List<DetMallaCurricularDTO> lisDetMallaCurricularTemp = obtenerDetMallaCurricular(detMCurrTmp);

					if (!CollectionUtil.isEmpty(lisDetMallaCurricularTemp)) {
						List<DetalleCargaAcademicaDTO> resultadoTemp = new ArrayList<>();
						for (var objTemp : lisDetMallaCurricularTemp) {
							DetalleCargaAcademicaDTO objT = new DetalleCargaAcademicaDTO();
							objT.setDetMallaCurricular(objTemp);
							objT.setPersonalByDocentePrincipal(new PersonalDTO());
							objT.setPersonalByDocenteAuxiliar(new PersonalDTO());
							objT.setGrupo(new GrupoDTO());
							resultadoTemp.add(objT);
						}
						obj.setEsPadre(true);
						obj.setDetalleCargaAcademicaList(resultadoTemp);
					}

					obj.setPersonalByDocentePrincipal(new PersonalDTO());
					obj.setPersonalByDocenteAuxiliar(new PersonalDTO());
					obj.setGrupo(new GrupoDTO());
					resultado.add(obj);
				}

			}
		}
		return resultado;
	}

	@Override
	public void agregarCargaAcademica(CargaAcademicaDTO obj) {
		CargaAcademica objE = to(obj, CargaAcademica.class, "seccion");
		objE.setNombre("Carga Academica " + " - " + obj.getSeccion().getDescripcionView());
		objE.setCargaAcademicaDetalleCargaAcademicaList(new ArrayList<>());

		if (!CollectionUtil.isEmpty(obj.getCargaAcademicaDetalleCargaAcademicaList())) {
			for (var detaCarVO : obj.getCargaAcademicaDetalleCargaAcademicaList()) {
				DetalleCargaAcademica detaCarE = to(detaCarVO, DetalleCargaAcademica.class);
				detaCarE.setDetalleCargaAcademicaList(
						toListEntity(detaCarVO.getDetalleCargaAcademicaList(), DetalleCargaAcademica.class));
				objE.getCargaAcademicaDetalleCargaAcademicaList().add(detaCarE);
			}

		}

		servicio.agregarCargaAcademica(objE);
	}

	@Override
	public List<DetalleCargaAcademicaDTO> listarDetCargaAcademicaByDocente(BaseSearch filtro) {
		List<DetalleCargaAcademicaDTO> resultado = new ArrayList<>();
		List<DetalleCargaAcademica> listaTemp = this.servicio.listarDetCargaAcademicaByDocente(filtro);

		Map<String, String> completeMap = new HashMap<>();
		completeMap.put("idSeccion", "seccion");
		completeMap.put("idItemByTurno", "itemByTurno");
		for (var obj : listaTemp) {
			DetalleCargaAcademicaDTO objData = toDTO(obj, DetalleCargaAcademicaDTO.class, "grupo",
					"detMallaCurricular:{mallaCurricular}", "personalByDocentePrincipal", "personalByDocenteAuxiliar",
					"cargaAcademica");

			cacheUtil.completarData(objData.getCargaAcademica(), completeMap, CACHE_SECCION);

			resultado.add(objData);
		}
		return resultado;
	}

	@Override
	public List<DetalleCargaAcademicaDTO> obtenerCursosPosiblesLlevar(Map<String, Object> parametroMap) {
		List<DetalleCargaAcademicaDTO> resultado = new ArrayList<>();
		List<DetalleCargaAcademicaDTO> listaDetalleCargaAcademica = toList(
				servicio.obtenerCursosPosiblesLlevar(parametroMap), DetalleCargaAcademicaDTO.class,
				"detMallaCurricular:{detMallaCurricularPadre}", "grupo", "cargaAcademica:{seccion}",
				"personalByDocentePrincipal");
		Map<String, List<DetalleCargaAcademicaDTO>> detMallaMap = new HashMap<>();
		Map<String, DetMallaCurricularDTO> detCargaAcademicaPadreMap = new HashMap<>();

		BaseSearch filtroDet = new BaseSearch();
		filtroDet.setIdGrado(ObjectUtil.objectToLong(parametroMap.get("idGrado".toUpperCase())));
		filtroDet.setIdAnhio(ObjectUtil.objectToLong(parametroMap.get("idAnhio".toUpperCase())));

		List<DetMallaCurricularDTO> lisDetMallaCurricular = obtenerDetMallaCurricular(filtroDet);

		Map<String, DetalleCargaAcademicaDTO> detCargaAcademicaMap = new HashMap<>();

		if (!CollectionUtil.isEmpty(listaDetalleCargaAcademica)) {
			CargaAcademicaDTO objCarga = listaDetalleCargaAcademica.get(0).getCargaAcademica();
			for (var cargaLectiva : listaDetalleCargaAcademica) {
				String key = cargaLectiva.getDetMallaCurricular().getDetMallaCurricularPadre()
						.getIdDetMallaCurricular();
				if (StringUtil.isNotNullOrBlank(key)) {
					if (!detMallaMap.containsKey(key)) {
						List<DetalleCargaAcademicaDTO> res = new ArrayList<>();
						res.add(cargaLectiva);
						detMallaMap.put(key, res);
					} else {
						List<DetalleCargaAcademicaDTO> res = detMallaMap.get(key);
						res.add(cargaLectiva);
					}
					if (!detCargaAcademicaPadreMap.containsKey(key)) {
						detCargaAcademicaPadreMap.put(key,
								cargaLectiva.getDetMallaCurricular().getDetMallaCurricularPadre());
					}
				}
			}
			for (var entryMap : detCargaAcademicaPadreMap.entrySet()) {
				DetalleCargaAcademicaDTO obj = new DetalleCargaAcademicaDTO();
				obj.setCargaAcademica(objCarga);
				obj.setDetMallaCurricular(entryMap.getValue());
				obj.setPersonalByDocentePrincipal(new PersonalDTO());
				obj.setPersonalByDocenteAuxiliar(new PersonalDTO());
				obj.setGrupo(new GrupoDTO());
				listaDetalleCargaAcademica.add(obj);
			}
			for (var cargaLectiva : listaDetalleCargaAcademica) {
				String key = cargaLectiva.getDetMallaCurricular().getIdDetMallaCurricular();
				if (StringUtil.isNullOrEmpty(cargaLectiva.getDetMallaCurricular().getDetMallaCurricularPadre())
						&& detMallaMap.containsKey(key)) {
					cargaLectiva.setEsPadre(true);
					cargaLectiva.setDetalleCargaAcademicaList(detMallaMap.get(key));
				}
				detCargaAcademicaMap.put(key, cargaLectiva);
			}
			for (var objData : lisDetMallaCurricular) {
				String key = objData.getIdDetMallaCurricular();
				if (detCargaAcademicaMap.containsKey(key)
						&& StringUtil.isNullOrEmpty(objData.getDetMallaCurricularPadre())) {
					resultado.add(detCargaAcademicaMap.get(key));
				}
			}
		}
		return resultado;
	}

	// TODO:REVISAR ALGORITMO
	@Override
	public MatriculaDTO registrarMatricula(MatriculaDTO obj) {
		Matricula objE = to(obj, Matricula.class);
		objE.setListaDetalleCargaAcademica(new ArrayList<>());

		for (var objDet : obj.getListaDetalleCargaAcademica()) {
			DetalleCargaAcademica objDetE = to(objDet, DetalleCargaAcademica.class);

			if (!CollectionUtil.isEmpty(objDet.getDetalleCargaAcademicaList())) {
				objDetE.setDetalleCargaAcademicaList(
						toListEntity(objDet.getDetalleCargaAcademicaList(), DetalleCargaAcademica.class));
			}
			objE.getListaDetalleCargaAcademica().add(objDetE);
		}
		return toDTO(servicio.registrarMatricula(objE), MatriculaDTO.class);

	}

	@Override
	public MatriculaDTO obtenerMatricula(Long idAnhio, String idAlumno, Long idTurno, Long idPerido) {
		Matricula obj = servicio.obtenerMatricula(idAnhio, idAlumno, idTurno, idPerido);
		MatriculaDTO resultado = toDTO(obj, MatriculaDTO.class, "cargaAcademica:{seccion}", "alumno");
		resultado.setCargaAcademica(
				toDTO(obj.getCargaAcademica(), CargaAcademicaDTO.class, "seccion:{grado}", "itemByTurno", "anhio"));
		return resultado;
	}

	@Override
	public List<DetMatriculaDTO> obtenerListMatricula(String idMatricula) {
		List<DetMatriculaDTO> resultado = new ArrayList<>();
		List<DetMatricula> listaDetPlanillaTemp = this.servicio.obtenerListMatricula(idMatricula);
		for (var obj : listaDetPlanillaTemp) {
			DetMatriculaDTO objData = toDTO(obj, DetMatriculaDTO.class, DET_MALLA_CURRICULAR, "matricula");
			objData.setMatricula(toDTO(obj.getMatricula(), MatriculaDTO.class, "cargaAcademica"));
			objData.getMatricula().setCargaAcademica(toDTO(obj.getMatricula().getCargaAcademica(),
					CargaAcademicaDTO.class, "seccion:{grado}", "itemByTurno", "anhio"));

			resultado.add(objData);
		}

		return resultado;
	}

	@Override
	public void eliminarRegistrarMatricula(String idMatricula, String userName) {
		servicio.eliminarRegistrarMatricula(idMatricula, userName);
	}

}