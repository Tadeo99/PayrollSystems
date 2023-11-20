package pe.buildsoft.erp.core.application.servicios.pago;

import java.util.ArrayList;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import pe.buildsoft.erp.core.application.entidades.common.AnhioDTO;
import pe.buildsoft.erp.core.application.entidades.common.ItemDTO;
import pe.buildsoft.erp.core.application.entidades.escalafon.EmpresaDTO;
import pe.buildsoft.erp.core.application.entidades.pago.CatalogoCuentaDTO;
import pe.buildsoft.erp.core.application.entidades.pago.ClasificacionDTO;
import pe.buildsoft.erp.core.application.entidades.pago.ControlPagoDTO;
import pe.buildsoft.erp.core.application.entidades.pago.CuentaBancariaEntidadDTO;
import pe.buildsoft.erp.core.application.entidades.pago.CuotaConceptoDTO;
import pe.buildsoft.erp.core.application.entidades.pago.DetControlPagoDTO;
import pe.buildsoft.erp.core.application.entidades.pago.DetPlanPagosDTO;
import pe.buildsoft.erp.core.application.entidades.pago.PlanPagosDTO;
import pe.buildsoft.erp.core.application.entidades.pago.TipoDocSunatEntidadDTO;
import pe.buildsoft.erp.core.application.interfaces.pago.PagoAppServiceLocal;
import pe.buildsoft.erp.core.domain.entidades.pago.CatalogoCuenta;
import pe.buildsoft.erp.core.domain.entidades.pago.Clasificacion;
import pe.buildsoft.erp.core.domain.entidades.pago.ControlPago;
import pe.buildsoft.erp.core.domain.entidades.pago.CuentaBancariaEntidad;
import pe.buildsoft.erp.core.domain.entidades.pago.CuotaConcepto;
import pe.buildsoft.erp.core.domain.entidades.pago.DetControlPago;
import pe.buildsoft.erp.core.domain.entidades.pago.DetPlanPagos;
import pe.buildsoft.erp.core.domain.entidades.pago.Empresa;
import pe.buildsoft.erp.core.domain.entidades.pago.PlanPagos;
import pe.buildsoft.erp.core.domain.entidades.pago.TipoDocSunatEntidad;
import pe.buildsoft.erp.core.domain.interfaces.servicios.pago.PagoServiceLocal;
import pe.buildsoft.erp.core.domain.pago.type.FlagConceptoPagoFraccionadoType;
import pe.buildsoft.erp.core.infra.transversal.cache.AtributosEntityCacheUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.SelectItemVO;
import pe.buildsoft.erp.core.infra.transversal.state.EstadoGeneralState;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.BaseTransfer;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * La Class PagoServiceImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 21:45:13 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class PagoAppServiceImpl extends BaseTransfer implements PagoAppServiceLocal {

	@Inject
	private PagoServiceLocal servicio;

	public PagoAppServiceImpl() {
		AtributosEntityCacheUtil.getInstance().sincronizarAtributos("pe.buildsoft.erp.core.domain.entidades.pago",
				"pe.buildsoft.erp.core.domain.entidades.escalafon", "pe.buildsoft.erp.core.domain.entidades.common",
				"pe.buildsoft.erp.core.domain.entidades.planilla", "pe.buildsoft.erp.core.domain.entidades.matricula",
				"pe.buildsoft.erp.core.domain.entidades.admision");
	}

	@Override
	public List<SelectItemVO> listarSelectItem(String groupName) {
		List<SelectItemVO> resultado = new ArrayList<>();
		switch (groupName) {
			case "empresa":
				resultado = listarEmpresaSelectItem();
				break;
			default:
				break;
		}
		return resultado;
	}

	private List<SelectItemVO> listarEmpresaSelectItem() {
		var filtro = new BaseSearch();
		filtro.setSortFields("idEmpresa");
		filtro.setSortDirections("asc");
		filtro.setEstado(EstadoGeneralState.ACTIVO.getKey());
		List<SelectItemVO> resultado = new ArrayList<>();
		List<EmpresaDTO> resulTmp = listarEmpresa(filtro);
		for (var obj : resulTmp) {
			resultado.add(new SelectItemVO(obj.getIdEmpresa(), obj.getRuc() + " " +  obj.getRazonSocial()));
		}
		return resultado;
	}

	@Override
	public CuotaConceptoDTO controladorAccionCuotaConcepto(CuotaConceptoDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionCuotaConcepto(to(obj, CuotaConcepto.class), accionType),
				CuotaConceptoDTO.class);
	}

	@Override
	public List<CuotaConceptoDTO> listarCuotaConcepto(BaseSearch filtro) {
		List<CuotaConceptoDTO> listTemp = toList(this.servicio.listarCuotaConcepto(filtro), CuotaConceptoDTO.class,
				"anhio", "itemByNivel", "catalogoCuenta");
		for (var obj : listTemp) {
			obj.getCatalogoCuenta().setDescripcionView(" " + obj.getCatalogoCuenta().getCuenta());
			generarDescripcionViewItem(obj.getItemByNivel());
			generarDescripcionViewAnhio(obj.getAnhio());
		}
		return listTemp;
	}

	private ItemDTO generarDescripcionViewItem(ItemDTO obj) {
		if (obj != null && StringUtil.isNotNullOrBlank(obj.getCodigoExterno())) {
			obj.setDescripcionView(obj.getCodigoExterno() + " " + obj.getNombre());
		}
		return obj;
	}

	private AnhioDTO generarDescripcionViewAnhio(AnhioDTO obj) {
		obj.setDescripcionView(obj.getIdAnhio() + " " + obj.getNombre());
		return obj;
	}

	@Override
	public int contarListarCuotaConcepto(BaseSearch filtro) {
		return this.servicio.contarListarCuotaConcepto(filtro);
	}

	@Override
	public CuentaBancariaEntidadDTO controladorAccionCuentaBancariaEntidad(CuentaBancariaEntidadDTO obj,
			AccionType accionType) {
		return toDTO(servicio.controladorAccionCuentaBancariaEntidad(to(obj, CuentaBancariaEntidad.class), accionType),
				CuentaBancariaEntidadDTO.class);
	}

	@Override
	public List<CuentaBancariaEntidadDTO> listarCuentaBancariaEntidad(BaseSearch filtro) {
		List<CuentaBancariaEntidadDTO> resultado = toList(this.servicio.listarCuentaBancariaEntidad(filtro),
				CuentaBancariaEntidadDTO.class, "itemByBanco", "itemByMoneda", "itemByTipoCuenta", "titular");
		for (var obj : resultado) {
			// cuentaBancariaEntidadDTO.getEntidad().setDescripcionView(cuentaBancariaEntidadDTO.getEntidad().getNombre());
			obj.getTitular().setDescripcionView(obj.getTitular().getNombres() + " "
					+ obj.getTitular().getApellidoPaterno() + " " + obj.getTitular().getApellidoMaterno());
			generarDescripcionViewItem(obj.getItemByBanco());
			generarDescripcionViewItem(obj.getItemByMoneda());
			generarDescripcionViewItem(obj.getItemByTipoCuenta());
		}
		return resultado;
	}

	@Override
	public int contarListarCuentaBancariaEntidad(BaseSearch filtro) {
		return this.servicio.contarListarCuentaBancariaEntidad(filtro);
	}

	@Override
	public ClasificacionDTO controladorAccionClasificacion(ClasificacionDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionClasificacion(to(obj, Clasificacion.class), accionType),
				ClasificacionDTO.class);
	}

	@Override
	public List<ClasificacionDTO> listarClasificacion(BaseSearch filtro) {
		List<ClasificacionDTO> resultado = toList(this.servicio.listarClasificacion(filtro), ClasificacionDTO.class);
		for (var obj : resultado) {
			// clasificacionDTO.getEntidad().setDescripcionView(clasificacionDTO.getEntidad().getNombre());
			// clasificacionDTO.getSede().setDescripcionView(clasificacionDTO.getSede().getNombre());
			// generarDescripcionViewItem(obj.getItemByTipoClasificacion());
		}
		return resultado;
	}

	@Override
	public int contarListarClasificacion(BaseSearch filtro) {
		return this.servicio.contarListarClasificacion(filtro);
	}

	@Override
	public ClasificacionDTO findClasificacion(ClasificacionDTO filtro) {
		return toDTO(servicio.findClasificacion(to(filtro, Clasificacion.class)), ClasificacionDTO.class);
	}

	@Override
	public String registrarPago(ControlPagoDTO obj) {
		ControlPago objEntity = to(obj, ControlPago.class);
		objEntity.setControlPagoDetControlPagoList(
				toListEntity(obj.getControlPagoDetControlPagoList(), DetControlPago.class));
		return servicio.registrarPago(objEntity);
	}

	@Override
	public List<ControlPagoDTO> listarControlPago(BaseSearch filtro) {
		List<ControlPagoDTO> resultado = new ArrayList<>();
		List<ControlPago> listaTemp = servicio.listarControlPago(filtro);
		for (var obj : listaTemp) {
			ControlPagoDTO objDTO = toDTO(obj, ControlPagoDTO.class, "tipoDocSunat", "alumno:{grado}", "periodo",
					"itemByTipoMoneda", "anhio", "empresa");
			/*
			 * objDTO.getAlumno().setDescripcionView(obj.getAlumno().getNombres() + " " +
			 * obj.getAlumno().getApellidoPaterno() + " " +
			 * obj.getAlumno().getApellidoMaterno());
			 */
			objDTO.setControlPagoDetControlPagoList(
					toList(obj.getControlPagoDetControlPagoList(), DetControlPagoDTO.class));
			// controlPagoDTO.getEntidad().setDescripcionView(obj.getEntidad().getNombre());
			resultado.add(objDTO);
		}
		return resultado;
	}

	@Override
	public List<TipoDocSunatEntidadDTO> listarTipoDocByItem(TipoDocSunatEntidadDTO filtro) {
		return toList(this.servicio.listarTipoDocByItem(to(filtro, TipoDocSunatEntidad.class)),
				TipoDocSunatEntidadDTO.class, "itemByTipoDocSunat");
	}

	@Override
	public int contarListarControlPago(BaseSearch filtro) {
		return this.servicio.contarListarControlPago(filtro);
	}

	@Override
	public CatalogoCuentaDTO controladorAccionCatalogoCuenta(CatalogoCuentaDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionCatalogoCuenta(to(obj, CatalogoCuenta.class), accionType),
				CatalogoCuentaDTO.class);
	}

	@Override
	public List<CatalogoCuentaDTO> listarCatalogoCuenta(BaseSearch filtro) {
		List<CatalogoCuentaDTO> resultado = toList(this.servicio.listarCatalogoCuenta(filtro), CatalogoCuentaDTO.class,
				"clasificacion");
		for (var obj : resultado) {
			obj.getClasificacion().setDescripcionView(obj.getClasificacion().getDescripcion());
		}
		return resultado;
	}

	@Override
	public int contarListarCatalogoCuenta(BaseSearch filtro) {
		return this.servicio.contarListarCatalogoCuenta(filtro);
	}

	@Override
	public CatalogoCuentaDTO findCatalogoCuenta(CatalogoCuentaDTO filtro) {
		return toDTO(servicio.findCatalogoCuenta(to(filtro, CatalogoCuenta.class)), CatalogoCuentaDTO.class);
	}

	@Override
	public EmpresaDTO controladorAccionEmpresa(EmpresaDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionEmpresa(to(obj, Empresa.class), accionType), EmpresaDTO.class);
	}

	@Override
	public List<EmpresaDTO> listarEmpresa(BaseSearch filtro) {
		List<EmpresaDTO> resultado = toList(this.servicio.listarEmpresa(filtro), EmpresaDTO.class);
		for (var obj : resultado) {
			generarDescripcionViewItem(obj.getItemByTipoVia());
			generarDescripcionViewItem(obj.getItemByZona());
		}
		return resultado;
	}

	public int contarListarEmpresa(BaseSearch filtro) {
		return this.servicio.contarListarEmpresa(filtro);
	}

	@Override
	public EmpresaDTO findEmpresa(EmpresaDTO filtro) {
		return toDTO(servicio.findEmpresa(to(filtro, Empresa.class)), EmpresaDTO.class);
	}

	@Override
	public TipoDocSunatEntidadDTO controladorAccionTipoDocSunatEntidad(TipoDocSunatEntidadDTO obj,
			AccionType accionType) {
		return toDTO(servicio.controladorAccionTipoDocSunatEntidad(to(obj, TipoDocSunatEntidad.class), accionType),
				TipoDocSunatEntidadDTO.class);
	}

	@Override
	public List<TipoDocSunatEntidadDTO> listarTipoDocSunatEntidad(BaseSearch filtro) {
		var resultado = toList(this.servicio.listarTipoDocSunatEntidad(filtro),
				TipoDocSunatEntidadDTO.class);
		for (var obj : resultado) {
			// tipoDocSunatEntidadDTO.getEntidad().setDescripcionView(tipoDocSunatEntidadDTO.getEntidad().getNombre());
			generarDescripcionViewItem(obj.getItemByTipoDocSunat());
		}
		return resultado;
	}

	@Override
	public int contarListarTipoDocSunatEntidad(BaseSearch filtro) {
		return this.servicio.contarListarTipoDocSunatEntidad(filtro);
	}

	@Override
	public TipoDocSunatEntidadDTO findTipoDocSunatEntidad(TipoDocSunatEntidadDTO filtro) {
		return toDTO(servicio.findTipoDocSunatEntidad(to(filtro, TipoDocSunatEntidad.class)),
				TipoDocSunatEntidadDTO.class);
	}

	@Override
	public void registrarPlanPago(PlanPagosDTO obj) {
		var objEntity = to(obj, PlanPagos.class);
		objEntity.setPlanPagosDetPlanPagosList(toListEntity(obj.getPlanPagosDetPlanPagosList(), DetPlanPagos.class));
		servicio.registrarPlanPago(objEntity);
	}

	@Override
	public PlanPagosDTO obtenerPlanPagosByIdAlumno(PlanPagosDTO filtro) {
		PlanPagosDTO resultado = new PlanPagosDTO();
		PlanPagos obj = servicio.obtenerPlanPagosByIdAlumno(to(filtro, PlanPagos.class));
		if (!StringUtil.isNullOrEmpty(obj)) {
			resultado = toDTO(obj, PlanPagosDTO.class, "alumno", "periodo", "anhio");
			/*
			 * AlumnoDTO alumnoTemp = toDTO(obj.getAlumno(), AlumnoDTO.class,
			 * "grado:{itemByNivel}"); GradoDTO gradoTemp =
			 * toDTO(obj.getAlumno().getGrado(), GradoDTO.class, "itemByNivel");
			 * alumnoTemp.setGrado(gradoTemp);
			 */
			// SACAR DE CACHE
			// resultado.setAlumno(alumnoTemp);
		}
		return resultado;
	}

	@Override
	public DetPlanPagosDTO findDetPlanPagos(DetPlanPagosDTO filtro) {
		return toDTO(servicio.findDetPlanPagos(to(filtro, DetPlanPagos.class)), DetPlanPagosDTO.class,
				"cuotaConcepto:{catalogoCuenta}");
	}

	@Override
	public List<DetPlanPagosDTO> listarDetPlanPagos(BaseSearch filtro) {
		List<DetPlanPagosDTO> resultado = new ArrayList<>();
		List<DetPlanPagos> resultadoTemp = servicio.listarDetPlanPagos(filtro);
		for (var objData : resultadoTemp) {
			DetPlanPagosDTO objDTO = toDTO(objData, DetPlanPagosDTO.class);
			CuotaConceptoDTO cuotaConcepto = toDTO(objData.getCuotaConcepto(), CuotaConceptoDTO.class, "anhio",
					"itemByNivel", "catalogoCuenta:{clasificacion}");
			objDTO.setCuotaConcepto(cuotaConcepto);
			objDTO.setDetPlanPagosList(new ArrayList<>());
			for (var objDataDet : objData.getDetPlanPagosList()) {
				DetPlanPagosDTO objDet = toDTO(objDataDet, DetPlanPagosDTO.class);
				CuotaConceptoDTO objDetC = toDTO(objDataDet.getCuotaConcepto(), CuotaConceptoDTO.class, "anhio",
						"itemByNivel", "catalogoCuenta:{clasificacion}");
				objDet.setCuotaConcepto(objDetC);

				objDTO.getDetPlanPagosList().add(objDet);
			}
			resultado.add(objDTO);
		}

		return resultado;
	}

	@Override
	public int contarListarDetPlanPagos(BaseSearch filtro) {
		return this.servicio.contarListarDetPlanPagos(filtro);
	}

	@Override
	public List<DetPlanPagosDTO> listarConceptoPagoAlumnoSemestre(Long idAnho, Long idPeriodo, String idAlumno,
			boolean flagFaltaMontoResta) {
		List<DetPlanPagosDTO> resultado = new ArrayList<>();
		List<DetPlanPagos> resultadoTemp = servicio.listarConceptoPagoAlumnoSemestre(idAnho, idPeriodo, idAlumno,
				flagFaltaMontoResta);
		for (var objData : resultadoTemp) {
			DetPlanPagosDTO obj = toDTO(objData, DetPlanPagosDTO.class, "planPagos:{periodo}");
			CuotaConceptoDTO objC = toDTO(objData.getCuotaConcepto(), CuotaConceptoDTO.class, "anhio", "itemByNivel",
					"catalogoCuenta:{clasificacion}");
			obj.setCuotaConcepto(objC);
			resultado.add(obj);
		}
		return resultado;
	}

	@Override
	public List<DetControlPagoDTO> obtenerConceptoPagoAlumnoSemestre(Long idAnho, Long idPeriodo, String idAlumno,
			boolean flagFaltaMontoResta) {
		List<DetControlPagoDTO> listaDetControlPagoDTO = new ArrayList<>();
		List<DetPlanPagosDTO> listaDetPlanPagos = listarConceptoPagoAlumnoSemestre(idAnho, idPeriodo, idAlumno,
				flagFaltaMontoResta);
		for (var obj : listaDetPlanPagos) {
			if (obj.getFlagFraccionado().equals(FlagConceptoPagoFraccionadoType.NO.getKey())) {
				DetControlPagoDTO objDet = new DetControlPagoDTO();
				objDet.setId(obj.getIdDetPlanPagos());
				objDet.setCuotaConcepto(obj.getCuotaConcepto());
				objDet.setDescripcionConcepto(obj.getCuotaConcepto().getCatalogoCuenta().getCuenta() + " - ("
						+ obj.getCuotaConcepto().getAnhio().getIdAnhio() + ")" + " - "
						+ obj.getPlanPagos().getPeriodo().getDescripcion());
				objDet.setAplicaIgv(obj.getAplicaIgv());
				if (obj.getMontoResta() != null) {
					objDet.setMonto(obj.getMontoResta());
				} else {
					objDet.setMonto(obj.getCuota());
				}
				listaDetControlPagoDTO.add(objDet);
			} else if (obj.getFlagFraccionado().equals(FlagConceptoPagoFraccionadoType.SI.getKey())) {
				DetControlPagoDTO objDet = new DetControlPagoDTO();
				objDet.setId(obj.getIdDetPlanPagos());
				objDet.setCuotaConcepto(obj.getCuotaConcepto());
				objDet.setDescripcionConcepto(obj.getCuotaConcepto().getCatalogoCuenta().getCuenta() + " "
						+ obj.getNroCuota() + " - (" + obj.getCuotaConcepto().getItemByNivel().getNombre() + " - "
						+ obj.getCuotaConcepto().getAnhio().getIdAnhio() + ")" + " - "
						+ obj.getPlanPagos().getPeriodo().getDescripcion());
				objDet.setNroCuota(obj.getNroCuota());
				objDet.setAplicaIgv(obj.getAplicaIgv());
				if (obj.getMontoResta() != null) {
					objDet.setMonto(obj.getMontoResta());
				} else {
					objDet.setMonto(obj.getCuota());
				}
				listaDetControlPagoDTO.add(objDet);

			}
		}
		return listaDetControlPagoDTO;
	}

}