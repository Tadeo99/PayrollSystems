package pe.buildsoft.erp.core.domain.servicios.nota;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import pe.buildsoft.erp.core.domain.entidades.matricula.DetMatricula;
import pe.buildsoft.erp.core.domain.entidades.matricula.DetalleCargaAcademica;
import pe.buildsoft.erp.core.domain.entidades.matricula.Unidad;
import pe.buildsoft.erp.core.domain.entidades.nota.AsistenciaAlumno;
import pe.buildsoft.erp.core.domain.entidades.nota.CursoNotaPeriodo;
import pe.buildsoft.erp.core.domain.entidades.nota.CursoNotaPeriodoProm;
import pe.buildsoft.erp.core.domain.entidades.nota.CursoNotaUnidad;
import pe.buildsoft.erp.core.domain.entidades.nota.CursoNotaUnidadProm;
import pe.buildsoft.erp.core.domain.entidades.nota.DetRegistroNota;
import pe.buildsoft.erp.core.domain.entidades.nota.RegistroNota;
import pe.buildsoft.erp.core.domain.interfaces.repositories.matricula.DetMatriculaDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.nota.AsistenciaAlumnoDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.nota.CursoNotaPeriodoDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.nota.CursoNotaPeriodoPromDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.nota.CursoNotaUnidadDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.nota.CursoNotaUnidadPromDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.nota.DetRegistroNotaDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.nota.RegistroNotaDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.servicios.nota.NotaServiceLocal;
import pe.buildsoft.erp.core.domain.nota.type.EstadoAsistenciaType;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.type.DiaSemanaType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.CollectionUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.FechaUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

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
public class NotaServiceImpl implements NotaServiceLocal {

	private static final String NOTA_NSP = "-3";

	private static final int CANTIDAD_PROMEDIO_UNIDAD = 4;

	/** El servicio asistencia alumno dao impl. */
	@Inject
	private AsistenciaAlumnoDaoLocal asistenciaAlumnoDaoImpl;

	/** El servicio curso nota periodo dao impl. */
	@Inject
	private CursoNotaPeriodoDaoLocal cursoNotaPeriodoDaoImpl;

	/** El servicio curso nota periodo prom dao impl. */
	@Inject
	private CursoNotaPeriodoPromDaoLocal cursoNotaPeriodoPromDaoImpl;

	/** El servicio curso nota unidad dao impl. */
	@Inject
	private CursoNotaUnidadDaoLocal cursoNotaUnidadDaoImpl;

	/** El servicio curso nota unidad prom dao impl. */
	@Inject
	private CursoNotaUnidadPromDaoLocal cursoNotaUnidadPromDaoImpl;

	/** El servicio det registro nota dao impl. */
	@Inject
	private DetRegistroNotaDaoLocal detRegistroNotaDaoImpl;

	/** El servicio registro nota dao impl. */
	@Inject
	private RegistroNotaDaoLocal registroNotaDaoImpl;

	/** El servicio det matricula dao impl. */
	@Inject
	private DetMatriculaDaoLocal detMatriculaDaoImpl;

	@Override
	public void registrarAsistencia(List<AsistenciaAlumno> listaObj) {
		for (var objData : listaObj) {
			if (!StringUtil.isNullOrEmpty(objData.getFechaHorario())) {
				objData.setIdItemByDia(getDiaItem(objData.getFechaHorario()));
			}
			if (!StringUtil.isNotNullOrBlank(objData.getIdAsistenciaAlumno())) {
				objData.setIdAsistenciaAlumno(asistenciaAlumnoDaoImpl.generarId());
				objData.setFechaCreacion(FechaUtil.obtenerFechaActual());
				this.asistenciaAlumnoDaoImpl.save(objData);
			} else {
				objData.setFechaModificacion(FechaUtil.obtenerFechaActual());
				this.asistenciaAlumnoDaoImpl.update(objData);
			}
		}
	}

	private Long getDiaItem(OffsetDateTime date) {
		DiaSemanaType diaType = FechaUtil.getNombreDiaSemana(date);
		Long resultado = null;
		switch (diaType) {
		case DOMINGO:
			resultado = 30l;
			break;
		case LUNES:
			resultado = 24l;
			break;
		case MARTES:
			resultado = 25l;
			break;
		case MIERCOLES:
			resultado = 26l;
			break;
		case JUEVES:
			resultado = 27l;
			break;
		case VIERNES:
			resultado = 28l;
			break;
		case SABADO:
			resultado = 29l;
			break;
		}
		return resultado;
	}

	@Override
	public List<AsistenciaAlumno> obtenerAsistenciaAlumno(AsistenciaAlumno filtro) {
		List<AsistenciaAlumno> resultado = asistenciaAlumnoDaoImpl.listar(filtro);

		Map<String, String> asistenciaRegistroMap = new HashMap<>();
		if (resultado != null) {
			for (var asistencia : resultado) {
				asistenciaRegistroMap.put(asistencia.getAlumno().getIdAlumno(), "");
			}
		} else {
			resultado = new ArrayList<>();
		}

		String idDetMallaCurricular = filtro.getDetalleCargaAcademica().getDetMallaCurricular()
				.getIdDetMallaCurricular();
		List<DetMatricula> listaTemp = detMatriculaDaoImpl.get(idDetMallaCurricular, filtro.getAlumno().getIdAlumno());
		if (listaTemp != null) {
			for (var objDet : listaTemp) {
				if (!asistenciaRegistroMap.containsKey(objDet.getMatricula().getAlumno().getIdAlumno())) {
					AsistenciaAlumno asistenciaPersist = new AsistenciaAlumno();
					asistenciaPersist.setAlumno(objDet.getMatricula().getAlumno());
					asistenciaPersist.setDetalleCargaAcademica(new DetalleCargaAcademica());
					asistenciaPersist.getDetalleCargaAcademica()
							.setIdDetalleCargaAcademica(filtro.getDetalleCargaAcademica().getIdDetalleCargaAcademica());
					asistenciaPersist.setIdItemByDia(1L);
					asistenciaPersist.setEstado(EstadoAsistenciaType.DESIDIR.getKey());
					asistenciaPersist.setJustificacion("");
					asistenciaPersist.setFechaHorario(filtro.getFechaHorario());
					if (!StringUtil.isNotNullOrBlank(filtro.getFechaHorario())) {
						asistenciaPersist.setFechaHorario(FechaUtil.obtenerFechaActual());
					}
					// asistenciaPersist.setUsuarioCreacion(userName);
					resultado.add(asistenciaPersist);
				}
			}
		}
		return resultado;

	}

	@Override
	public CursoNotaPeriodo controladorAccionCursoNotaPeriodo(CursoNotaPeriodo obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdCursoNotaCompProm(this.cursoNotaPeriodoDaoImpl.generarId());
			this.cursoNotaPeriodoDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.cursoNotaPeriodoDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.cursoNotaPeriodoDaoImpl.find(CursoNotaPeriodo.class, obj.getIdCursoNotaCompProm());
			this.cursoNotaPeriodoDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.cursoNotaPeriodoDaoImpl.find(CursoNotaPeriodo.class, obj.getIdCursoNotaCompProm());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<CursoNotaPeriodo> listarCursoNotaPeriodo(BaseSearch filtro) {
		return this.cursoNotaPeriodoDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarCursoNotaPeriodo(BaseSearch filtro) {
		return this.cursoNotaPeriodoDaoImpl.contar(filtro);
	}

	@Override
	public CursoNotaPeriodoProm controladorAccionCursoNotaPeriodoProm(CursoNotaPeriodoProm obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdCursoNotaPeriodo(this.cursoNotaPeriodoPromDaoImpl.generarId());
			this.cursoNotaPeriodoPromDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.cursoNotaPeriodoPromDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.cursoNotaPeriodoPromDaoImpl.find(CursoNotaPeriodoProm.class, obj.getIdCursoNotaPeriodo());
			this.cursoNotaPeriodoPromDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.cursoNotaPeriodoPromDaoImpl.find(CursoNotaPeriodoProm.class, obj.getIdCursoNotaPeriodo());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<CursoNotaPeriodoProm> listarCursoNotaPeriodoProm(BaseSearch filtro) {
		return this.cursoNotaPeriodoPromDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarCursoNotaPeriodoProm(BaseSearch filtro) {
		return this.cursoNotaPeriodoPromDaoImpl.contar(filtro);
	}

	@Override
	public CursoNotaUnidad controladorAccionCursoNotaUnidad(CursoNotaUnidad obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdCursoNota(this.cursoNotaUnidadDaoImpl.generarId());
			this.cursoNotaUnidadDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.cursoNotaUnidadDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.cursoNotaUnidadDaoImpl.find(CursoNotaUnidad.class, obj.getIdCursoNota());
			this.cursoNotaUnidadDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.cursoNotaUnidadDaoImpl.find(CursoNotaUnidad.class, obj.getIdCursoNota());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<CursoNotaUnidad> listarCursoNotaUnidad(BaseSearch filtro) {
		return this.cursoNotaUnidadDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarCursoNotaUnidad(BaseSearch filtro) {
		return this.cursoNotaUnidadDaoImpl.contar(filtro);
	}

	@Override
	public CursoNotaUnidadProm controladorAccionCursoNotaUnidadProm(CursoNotaUnidadProm obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdCursoNotaUnidad(this.cursoNotaUnidadPromDaoImpl.generarId());
			this.cursoNotaUnidadPromDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.cursoNotaUnidadPromDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.cursoNotaUnidadPromDaoImpl.find(CursoNotaUnidadProm.class, obj.getIdCursoNotaUnidad());
			this.cursoNotaUnidadPromDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.cursoNotaUnidadPromDaoImpl.find(CursoNotaUnidadProm.class, obj.getIdCursoNotaUnidad());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<CursoNotaUnidadProm> listarCursoNotaUnidadProm(BaseSearch filtro) {
		return this.cursoNotaUnidadPromDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarCursoNotaUnidadProm(BaseSearch filtro) {
		return this.cursoNotaUnidadPromDaoImpl.contar(filtro);
	}

	@Override
	public DetRegistroNota controladorAccionDetRegistroNota(DetRegistroNota obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdDetRegistroNota(this.detRegistroNotaDaoImpl.generarId());
			this.detRegistroNotaDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.detRegistroNotaDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.detRegistroNotaDaoImpl.find(DetRegistroNota.class, obj.getIdDetRegistroNota());
			this.detRegistroNotaDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.detRegistroNotaDaoImpl.find(DetRegistroNota.class, obj.getIdDetRegistroNota());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<DetRegistroNota> listarDetRegistroNota(BaseSearch filtro) {
		return this.detRegistroNotaDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarDetRegistroNota(BaseSearch filtro) {
		return this.detRegistroNotaDaoImpl.contar(filtro);
	}

	@Override
	public RegistroNota controladorAccionRegistroNota(RegistroNota obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdRegistroNota(this.registroNotaDaoImpl.generarId());
			this.registroNotaDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.registroNotaDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.registroNotaDaoImpl.find(RegistroNota.class, obj.getIdRegistroNota());
			this.registroNotaDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.registroNotaDaoImpl.find(RegistroNota.class, obj.getIdRegistroNota());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<RegistroNota> listarRegistroNota(BaseSearch filtro) {
		return this.registroNotaDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarRegistroNota(BaseSearch filtro) {
		return this.registroNotaDaoImpl.contar(filtro);
	}

	@Override
	public List<DetRegistroNota> obtenerRegistroNota(String idDetMallaCurricular, String idAlumno,
			Boolean esActaEvaluacionFinalAplazado) {
		List<DetRegistroNota> resultado = detRegistroNotaDaoImpl.getByCurso(idDetMallaCurricular, idAlumno,
				esActaEvaluacionFinalAplazado);
		List<String> listaIdDetRegistroNota = new ArrayList<>();
		Integer position = 1;
		for (var detRegistroNota : resultado) {
			detRegistroNota.setPosition(position);
			if (!listaIdDetRegistroNota.contains(detRegistroNota.getIdDetRegistroNota())) {
				listaIdDetRegistroNota.add(detRegistroNota.getIdDetRegistroNota());
			}
			position++;
		}

		Map<String, List<CursoNotaUnidad>> cursoNotaMap = cursoNotaUnidadDaoImpl.getMap(listaIdDetRegistroNota);
		Map<String, List<CursoNotaUnidadProm>> cursoNotaPromMap = cursoNotaUnidadPromDaoImpl
				.getMap(listaIdDetRegistroNota);
		for (var obj : resultado) {
			obj.setDetRegistroNotaCursoNotaUnidadList(cursoNotaMap.get(obj.getIdDetRegistroNota()));
			if (CollectionUtil.isEmpty(obj.getDetRegistroNotaCursoNotaUnidadList())) {
				obj.setDetRegistroNotaCursoNotaUnidadList(new ArrayList<>());
			}
			obj.setDetRegistroNotaCursoNotaUnidadPromList(cursoNotaPromMap.get(obj.getIdDetRegistroNota()));
			if (CollectionUtil.isEmpty(obj.getDetRegistroNotaCursoNotaUnidadPromList())) {
				CursoNotaUnidadProm cursoNotaUnidadPromTemp = new CursoNotaUnidadProm();
				cursoNotaUnidadPromTemp.setNota(new BigDecimal("0.00"));
				List<CursoNotaUnidadProm> list = new ArrayList<>();
				list.add(cursoNotaUnidadPromTemp);
				obj.setDetRegistroNotaCursoNotaUnidadPromList(list);
			}
		}

		return resultado;
	}

	@Override
	public void registrarNota(List<DetRegistroNota> listaObj, String userName) {
		List<String> listaIdMatricula = new ArrayList<>();
		Map<String, DetRegistroNota> listaDetRegistroNotaMap = new HashMap<>();
		if (listaObj != null && !listaObj.isEmpty()) {
			for (var obj : listaObj) {
				try {
					DetRegistroNota objClon = (DetRegistroNota) BeanUtils.cloneBean(obj);
					objClon.setNotaLetra((obj.getNotaLetra()));
					if (obj.getNotaLetraByNotaAplazado() != null) {
						objClon.setNotaLetraByNotaAplazado(obj.getNotaLetraByNotaAplazado());
					} else {
						obj.setNotaLetraByNotaAplazado(new BigDecimal("0.00"));
						objClon.setNotaLetraByNotaAplazado(new BigDecimal("0.00"));
					}
					listaDetRegistroNotaMap.put(obj.getIdDetRegistroNota(), objClon);
				} catch (Exception e) {
					// handle exception
				}

			}
		}
		Map<String, Boolean> listaDetRegistroNotaMoficiadoMap = new HashMap<>();
		Map<String, CursoNotaUnidad> listaNotaPromedioObligatorioMap = new HashMap<>();
		for (var obj : listaObj) {
			listaIdMatricula.add(obj.getDetMatricula().getMatricula().getIdMatricula());
			BigDecimal ponderadoCurso = new BigDecimal("0");
			BigDecimal sumaNotaUnidad = new BigDecimal("0");
			BigDecimal notaAplazado = null;
			if (obj.getNotaLetraByNotaAplazado() != null) {
				notaAplazado = obj.getNotaLetraByNotaAplazado();
			}
			Map<String, CursoNotaUnidad> listaNotaObligatorioMap = new HashMap<>();

			boolean isIngresoNota = false;
			CursoNotaUnidad cursonotaUnidadPromedio = new CursoNotaUnidad();
			BigDecimal promedioUnidad = new BigDecimal("0.00");
			int indexTemp = 0;

			for (var cursoNotaT : obj.getCursoNotaEvaluarUnidad()) {
				if (CollectionUtil.isEmpty(cursoNotaT.getCriterioEvaluacion().getCriterioEvaluacionHijos())) {
					if (!StringUtil.isNullOrEmpty(cursoNotaT.getNota())) {
						promedioUnidad = promedioUnidad.add(cursoNotaT.getNota());
					}
					indexTemp = indexTemp + 1;
				}
			}

			if (promedioUnidad.compareTo(BigDecimal.ZERO) > 0) {
				promedioUnidad = promedioUnidad.divide(new BigDecimal(indexTemp), 0, RoundingMode.HALF_UP);
			}

			for (var cursoNota : obj.getCursoNotaEvaluarUnidad()) {
				if (cursoNota.getNota() != null) {
					isIngresoNota = true;
				}
				cursoNota.setDetRegistroNota(obj);
				if (cursoNota.getCriterioEvaluacion().getIdCriterioEvaluacion().equals("PromUnidad")) {

				} else if (cursoNota.getCriterioEvaluacion().getIdCriterioEvaluacion().equals("PromBimestral")) {

				} else {
					cursonotaUnidadPromedio.setUnidad(cursoNota.getUnidad());
					listaNotaObligatorioMap.put(cursoNota.getCriterioEvaluacion().getIdCriterioEvaluacion(), cursoNota);
				}

			}
			cursonotaUnidadPromedio.setNota(promedioUnidad);
			listaNotaPromedioObligatorioMap.put(obj.getIdDetRegistroNota(), cursonotaUnidadPromedio);

			if (!isIngresoNota) {
				continue;
			}
			// Llenando las notas obligatorias y calculados
			List<CursoNotaUnidad> listaCursoNotas = new ArrayList<>();
			listaCursoNotas.addAll(listaNotaObligatorioMap.values());
			boolean isNSPTotal = false;
			int cantidadNSP = 0;
			for (var objNota : listaCursoNotas) {
				if (objNota.getNota() != null) {
					if (objNota.getNota().compareTo(new BigDecimal(NOTA_NSP)) == 0) {
						cantidadNSP++;
					} else {
						sumaNotaUnidad = sumaNotaUnidad.add(objNota.getNota());
					}
				}
				if (StringUtil.isNotNullOrBlank(objNota.getIdCursoNota())) {
					cursoNotaUnidadDaoImpl.updateNota(objNota);
				} else {
					objNota.setIdCursoNota(cursoNotaUnidadDaoImpl.generarId());
					cursoNotaUnidadDaoImpl.save(objNota);
				}
			}
			isNSPTotal = (listaNotaObligatorioMap.size() == cantidadNSP);
			if (!isNSPTotal) {
				ponderadoCurso = sumaNotaUnidad.divide(new BigDecimal(listaNotaObligatorioMap.size()), 0,
						RoundingMode.HALF_UP);
			} else {
				ponderadoCurso = new BigDecimal(NOTA_NSP);
			}
			// ver redondear y aplicar formula
			// ponderadoCurso = this.calcularPonderadoCurso(listaCursoNotas,
			// definirCriterioEvaluacionMap);
			obj.setNotaLetra(ponderadoCurso);
			DetRegistroNota detRegistroNotaClon = listaDetRegistroNotaMap.get(obj.getIdDetRegistroNota());
			if (notaAplazado != null) {
				obj.setNotaLetraByNotaAplazado(notaAplazado);
			}

			boolean isModificado = false;
			listaDetRegistroNotaMoficiadoMap.put(obj.getIdDetRegistroNota(), isModificado);
			if (verificarCambioDetRegistroNota(obj, detRegistroNotaClon)) {// pasamos al angular
				obj.setFechaModificacion(FechaUtil.obtenerFechaActual());
				obj.setUsuarioModificacion(userName);
				isModificado = true;
				listaDetRegistroNotaMoficiadoMap.put(obj.getIdDetRegistroNota(), isModificado);
			}
			if (notaAplazado != null) {
				obj.setNotaLetraByNotaAplazado((notaAplazado));
				if (isModificado) {
					obj.setFechaNotaAplazado(FechaUtil.obtenerFechaActual());
				}
			} else {
				if (isModificado) {
					obj.setNotaLetraByNotaAplazado(null);
				}
			}

			if (isModificado) {
				detRegistroNotaDaoImpl.update(obj);
			}
		}

		for (var entryMap : listaNotaPromedioObligatorioMap.entrySet()) {
			// TODO:VER MALO
			CursoNotaUnidadProm objCN = cursoNotaUnidadPromDaoImpl.find(entryMap.getKey());
			if (objCN != null) {
				objCN.setNota(entryMap.getValue().getNota());
				cursoNotaUnidadPromDaoImpl.update(objCN);
			} else {
				CursoNotaUnidadProm objTemp = new CursoNotaUnidadProm();
				objTemp.setIdCursoNotaUnidad(cursoNotaUnidadPromDaoImpl.generarId());
				objTemp.setDetRegistroNota(new DetRegistroNota());
				objTemp.getDetRegistroNota().setIdDetRegistroNota(entryMap.getKey());
				objTemp.setUnidad(new Unidad());
				objTemp.getUnidad().setIdUnidad(entryMap.getValue().getUnidad().getIdUnidad());
				objTemp.setNota(entryMap.getValue().getNota());
				cursoNotaUnidadPromDaoImpl.save(objTemp);
			}
		}

	}

	private boolean verificarCambioDetRegistroNota(DetRegistroNota obj, DetRegistroNota objClon) {
		boolean resultado = false;
		BigDecimal notaPonderado = obj.getNotaLetra();
		BigDecimal notaPonderadoClon = objClon.getNotaLetra();

		BigDecimal notaAplazado = BigDecimal.ZERO;
		if (obj.getNotaLetraByNotaAplazado() != null) {
			notaAplazado = obj.getNotaLetraByNotaAplazado();
		}
		BigDecimal notaAplazadoClon = BigDecimal.ZERO;
		if (objClon.getNotaLetraByNotaAplazado() != null) {
			notaAplazadoClon = objClon.getNotaLetraByNotaAplazado();
		}

		try {
			if (!(notaPonderado.compareTo(notaPonderadoClon) == 0)) {
				resultado = true;
			} else {
				if (!(notaAplazado.compareTo(notaAplazadoClon) == 0)) {
					resultado = true;
				}
			}
		} catch (Exception e) {
			// log.error(e);
		}

		return resultado;
	}
}