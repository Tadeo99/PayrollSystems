package pe.buildsoft.erp.core.application.servicios.hora;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.inject.Inject;
import jakarta.ejb.Stateless;

import pe.buildsoft.erp.core.application.entidades.common.ItemDTO;
import pe.buildsoft.erp.core.application.entidades.escalafon.CentroCostoDTO;
import pe.buildsoft.erp.core.application.entidades.escalafon.PersonalDTO;
import pe.buildsoft.erp.core.application.entidades.hora.PeriodoDTO;
import pe.buildsoft.erp.core.application.entidades.hora.RegistroHoraDTO;
import pe.buildsoft.erp.core.application.entidades.hora.RegistroHoraDetDTO;
import pe.buildsoft.erp.core.application.entidades.hora.RegistroHoraDetVO;
import pe.buildsoft.erp.core.application.entidades.hora.RequerimientoDTO;
import pe.buildsoft.erp.core.application.entidades.hora.RequerimientoPersonalDTO;
import pe.buildsoft.erp.core.application.interfaces.hora.RegistroHoraAppServiceLocal;
import pe.buildsoft.erp.core.domain.entidades.escalafon.Personal;
import pe.buildsoft.erp.core.domain.entidades.hora.Periodo;
import pe.buildsoft.erp.core.domain.entidades.hora.RegistroHora;
import pe.buildsoft.erp.core.domain.entidades.hora.RegistroHoraDet;
import pe.buildsoft.erp.core.domain.entidades.hora.Requerimiento;
import pe.buildsoft.erp.core.domain.entidades.hora.RequerimientoPersonal;
import pe.buildsoft.erp.core.domain.interfaces.servicios.hora.RegistroHoraServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.cache.AtributosEntityCacheUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.type.DiaSemanaType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.BaseTransfer;
import pe.buildsoft.erp.core.infra.transversal.utilitario.FechaUtil;

/**
 * La Class CamoteMantenedorServiceImpl.
 * <ul>
 * <li>Copyright 2021 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Mon Sep 06 16:48:51 COT 2021
 * @since BuildErp 1.0
 */
@Stateless
public class RegistroHoraAppServiceImpl extends BaseTransfer implements RegistroHoraAppServiceLocal {
	private static final String REQUERIMIENTO = "requerimiento";
	private static final String PERIODO = "periodo";
	private static final String PERSONAL = "personal";

	@Inject
	private RegistroHoraServiceLocal servicio;

	public RegistroHoraAppServiceImpl() {
		AtributosEntityCacheUtil.getInstance().sincronizarAtributos("pe.buildsoft.erp.core.domain.entidades.escalafon",
				"pe.buildsoft.erp.core.domain.entidades.hora");
	}

	@Override
	public RegistroHoraDTO registrarHora(RegistroHoraDTO obj, AccionType accionType) {
		RegistroHora objData = to(obj, RegistroHora.class);
		objData.setRegistroHoraDetRegistroHoraList(new ArrayList<>());
		for (var objDet : obj.getRegistroHoraDetRegistroHoraList()) {
			RegistroHoraDet objDetPersist = to(objDet, RegistroHoraDet.class);
			objDetPersist.setRequerimiento(new Requerimiento());
			objDetPersist.getRequerimiento().setIdRequerimiento(objDet.getIdRequerimiento());
			objData.getRegistroHoraDetRegistroHoraList().add(objDetPersist);
		}
		return toDTO(servicio.registrarHora(objData, accionType), RegistroHoraDTO.class);
	}

	@Override
	public List<RegistroHoraDTO> listarRegistroHora(BaseSearch filtro) {
		List<RegistroHoraDTO> resultado = toList(servicio.listarRegistroHora(filtro), RegistroHoraDTO.class, PERIODO,
				PERSONAL);
		for (var objData : resultado) {
			generarDescripcionViewPeriodo(objData.getPeriodo());
			generarDescripcionViewPersonal(objData.getPersonal());
		}
		return resultado;
	}

	private PersonalDTO generarDescripcionViewPersonal(PersonalDTO obj) {
		if (obj.getIdPersonal() != null) {
			obj.setDescripcionView(obj.getNombres() + " " + obj.getApellidoPaterno() + " " + obj.getApellidoMaterno());
		}
		return obj;
	}

	private PeriodoDTO generarDescripcionViewPeriodo(PeriodoDTO item) {
		if (item.getIdPeriodo() != null) {
			if (item.getEstado().equals("A")) {
				item.setDescripcionView(item.getNombre() + " - " + "Abierto");
			}
			if (item.getEstado().equals("C")) {
				item.setDescripcionView(item.getNombre() + " - " + "Cerrado");
			}
			if (item.getEstado().equals("T")) {
				item.setDescripcionView(item.getNombre() + " - " + "Activo");
			}
		}
		return item;
	}

	@Override
	public int contarListarRegistroHora(BaseSearch filtro) {
		return servicio.contarListarRegistroHora(filtro);
	}

	@Override
	public List<RegistroHoraDetDTO> listarRegistroHoraDet(BaseSearch filtro) {
		return toList(servicio.listarRegistroHoraDet(filtro), RegistroHoraDetDTO.class, "registroHora", REQUERIMIENTO);
	}

	@Override
	public List<RegistroHoraDetVO> obtenerRegistroHora(String idPersonal, String idPeriodo, Long numSemana,
			String estadoPeriodo) {
		List<RegistroHoraDetVO> resultado = new ArrayList<>();
		var filtro = generarRegistroHoraDet(idPersonal, idPeriodo, numSemana);
		BaseSearch objFiltroReq = new BaseSearch();
		objFiltroReq.setIdPersonal(idPersonal);
		objFiltroReq.setIdPeriodo(idPeriodo);
		if (estadoPeriodo != null && estadoPeriodo.equals("A")) {
			objFiltroReq.setEstado("A");
		}
		List<RequerimientoPersonal> listaReq = servicio.listarRequerimientoPersonal(objFiltroReq);
		List<RegistroHoraDet> listaData = servicio.listarRegistroHoraDet(filtro);
		Map<String, RegistroHoraDetVO> registoReqMap = new HashMap<>();
		for (var obj : listaData) {
			String key = obj.getRequerimiento().getIdRequerimiento();
			if (!registoReqMap.containsKey(key)) {
				RegistroHoraDetVO value = obtenerRegistroHoraDetVO(new RegistroHoraDetVO(), obj);
				value.setReq(obj.getRequerimiento().getNombre());
				value.setId(key);
				registoReqMap.put(key, value);
			} else {
				RegistroHoraDetVO value = registoReqMap.get(key);
				obtenerRegistroHoraDetVO(value, obj);
			}
		}
		for (var obj : listaReq) {
			String key = obj.getRequerimiento().getIdRequerimiento();
			if (registoReqMap.containsKey(key)) {
				resultado.add(registoReqMap.get(key));
			} else {
				RegistroHoraDetVO value = new RegistroHoraDetVO();
				value.setReq(obj.getRequerimiento().getNombre());
				value.setId(key);
				resultado.add(value);
			}

		}
		return resultado;
	}

	private RegistroHoraDetVO obtenerRegistroHoraDetVO(RegistroHoraDetVO value, RegistroHoraDet obj) {
		DiaSemanaType diaSemType = FechaUtil.getNombreDiaSemana(obj.getFechaImputacion());
		if (obj.getHorasImputadas() != null) {
			switch (diaSemType) {
			case DOMINGO:
				value.setDom(obj.getHorasImputadas().doubleValue());
				break;
			case LUNES:
				value.setLu(obj.getHorasImputadas().doubleValue());
				break;
			case MARTES:
				value.setMa(obj.getHorasImputadas().doubleValue());
				break;
			case MIERCOLES:
				value.setMi(obj.getHorasImputadas().doubleValue());
				break;
			case JUEVES:
				value.setJu(obj.getHorasImputadas().doubleValue());
				break;
			case VIERNES:
				value.setVi(obj.getHorasImputadas().doubleValue());
				break;
			case SABADO:
				value.setSa(obj.getHorasImputadas().doubleValue());
				break;
			}
		}
		return value;
	}

	private BaseSearch generarRegistroHoraDet(String idPersonal, String idPeriodo, Long numSemana) {
		var filtro = new BaseSearch();
		filtro.setIdPeriodo(idPeriodo);
		filtro.setIdPersonal(idPersonal);
		filtro.setNumSemana(numSemana);
		return filtro;
	}

	@Override
	public RequerimientoDTO controladorAccionRequerimiento(RequerimientoDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionRequerimiento(to(obj, Requerimiento.class), accionType),
				RequerimientoDTO.class);
	}

	@Override
	public List<RequerimientoDTO> listarRequerimiento(BaseSearch filtro) {
		List<RequerimientoDTO> resultado = toList(servicio.listarRequerimiento(filtro), RequerimientoDTO.class,
				PERSONAL, "centroCosto", "itemByTipoGobierno");
		for (var obj : resultado) {
			generarDescripcionViewPersonal(obj.getPersonal());
			generarDescripcionViewCentroCosto(obj.getCentroCosto());
		}
		return resultado;
	}

	private CentroCostoDTO generarDescripcionViewCentroCosto(CentroCostoDTO obj) {
		if (obj != null && obj.getIdCentroCosto() != null) {
			obj.setDescripcionView(obj.getCodigo() + " " + obj.getNombre());
		}
		return obj;
	}

	@Override
	public int contarListarRequerimiento(BaseSearch filtro) {
		return servicio.contarListarRequerimiento(filtro);
	}

	@Override
	public RequerimientoPersonalDTO controladorAccionRequerimientoPersonal(RequerimientoPersonalDTO obj,
			AccionType accionType) {
		return toDTO(servicio.controladorAccionRequerimientoPersonal(to(obj, RequerimientoPersonal.class), accionType),
				RequerimientoPersonalDTO.class);
	}

	@Override
	public List<RequerimientoPersonalDTO> listarRequerimientoPersonal(BaseSearch filtro) {
		return toList(servicio.listarRequerimientoPersonal(filtro), RequerimientoPersonalDTO.class, PERSONAL, PERIODO,
				REQUERIMIENTO);
	}

	@Override
	public int contarListarRequerimientoPersonal(BaseSearch filtro) {
		return servicio.contarListarRequerimientoPersonal(filtro);
	}

	@Override
	public PeriodoDTO controladorAccionPeriodo(PeriodoDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionPeriodo(to(obj, Periodo.class), accionType), PeriodoDTO.class);
	}

	@Override
	public List<PeriodoDTO> listarPeriodo(BaseSearch filtro) {
		return toList(servicio.listarPeriodo(filtro), PeriodoDTO.class);
	}

	@Override
	public int contarListarPeriodo(BaseSearch filtro) {
		return servicio.contarListarPeriodo(filtro);
	}
}