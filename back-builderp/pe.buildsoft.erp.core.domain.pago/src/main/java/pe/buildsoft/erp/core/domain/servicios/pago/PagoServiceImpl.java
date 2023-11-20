package pe.buildsoft.erp.core.domain.servicios.pago;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.inject.Inject;
import jakarta.ejb.Stateless;

import org.apache.commons.beanutils.BeanUtils;

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
import pe.buildsoft.erp.core.domain.interfaces.repositories.pago.CatalogoCuentaDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.pago.ClasificacionDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.pago.ControlPagoDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.pago.CuentaBancariaEntidadDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.pago.CuotaConceptoDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.pago.DetControlPagoDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.pago.DetPlanPagosDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.pago.EmpresaDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.pago.PlanPagosDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.pago.TipoDocSunatEntidadDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.servicios.pago.PagoServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.CollectionUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.FechaUtil;
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
public class PagoServiceImpl implements PagoServiceLocal {

	/** El servicio det control pago dao impl. */
	@Inject
	private DetControlPagoDaoLocal detControlPagoDaoImpl;

	/** El servicio cuota concepto dao impl. */
	@Inject
	private CuotaConceptoDaoLocal cuotaConceptoDaoImpl;

	/** El servicio cuenta bancaria entidad dao impl. */
	@Inject
	private CuentaBancariaEntidadDaoLocal cuentaBancariaEntidadDaoImpl;

	/** El servicio clasificacion dao impl. */
	@Inject
	private ClasificacionDaoLocal clasificacionDaoImpl;

	/** El servicio control pago dao impl. */
	@Inject
	private ControlPagoDaoLocal controlPagoDaoImpl;

	/** El servicio catalogo cuenta dao impl. */
	@Inject
	private CatalogoCuentaDaoLocal catalogoCuentaDaoImpl;

	/** El servicio empresa dao impl. */
	@Inject
	private EmpresaDaoLocal empresaDaoImpl;

	/** El servicio tipo doc sunat entidad dao impl. */
	@Inject
	private TipoDocSunatEntidadDaoLocal tipoDocSunatEntidadDaoImpl;

	/** El servicio plan pagos dao impl. */
	@Inject
	private PlanPagosDaoLocal planPagosDaoImpl;

	/** El servicio det plan pagos dao impl. */
	@Inject
	private DetPlanPagosDaoLocal detPlanPagosDaoImpl;

	@Override
	public CuotaConcepto controladorAccionCuotaConcepto(CuotaConcepto obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdCuotaConcepto(this.cuotaConceptoDaoImpl.generarId());
			obj.setFechaCreacion(FechaUtil.obtenerFechaActual());
			this.cuotaConceptoDaoImpl.save(obj);
			break;
		case MODIFICAR:
			obj.setFechaModificacion(FechaUtil.obtenerFechaActual());
			this.cuotaConceptoDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.cuotaConceptoDaoImpl.find(CuotaConcepto.class, obj.getIdCuotaConcepto());
			this.cuotaConceptoDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.cuotaConceptoDaoImpl.find(CuotaConcepto.class, obj.getIdCuotaConcepto());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<CuotaConcepto> listarCuotaConcepto(BaseSearch filtro) {
		return this.cuotaConceptoDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarCuotaConcepto(BaseSearch filtro) {
		return this.cuotaConceptoDaoImpl.contar(filtro);
	}

	@Override
	public CuentaBancariaEntidad controladorAccionCuentaBancariaEntidad(CuentaBancariaEntidad obj,
			AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdCuentaBancariaEntidad(this.cuentaBancariaEntidadDaoImpl.generarId());
			this.cuentaBancariaEntidadDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.cuentaBancariaEntidadDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.cuentaBancariaEntidadDaoImpl.find(CuentaBancariaEntidad.class, obj.getIdCuentaBancariaEntidad());
			this.cuentaBancariaEntidadDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.cuentaBancariaEntidadDaoImpl.find(CuentaBancariaEntidad.class, obj.getIdCuentaBancariaEntidad());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<CuentaBancariaEntidad> listarCuentaBancariaEntidad(BaseSearch filtro) {
		return this.cuentaBancariaEntidadDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarCuentaBancariaEntidad(BaseSearch filtro) {
		return this.cuentaBancariaEntidadDaoImpl.contar(filtro);
	}

	@Override
	public Clasificacion controladorAccionClasificacion(Clasificacion obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdClasificacion(this.clasificacionDaoImpl.generarId());
			this.clasificacionDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.clasificacionDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.clasificacionDaoImpl.find(Clasificacion.class, obj.getIdClasificacion());
			this.clasificacionDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.clasificacionDaoImpl.find(Clasificacion.class, obj.getIdClasificacion());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<Clasificacion> listarClasificacion(BaseSearch filtro) {
		return this.clasificacionDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarClasificacion(BaseSearch filtro) {
		return this.clasificacionDaoImpl.contar(filtro);
	}

	@Override
	public Clasificacion findClasificacion(Clasificacion filtro) {
		return clasificacionDaoImpl.find(filtro);
	}

	@Override
	public String registrarPago(ControlPago obj) {
		if (!StringUtil.isNotNullOrBlank(obj.getIdControlPago())) {
			obj.setFechaCreacion(FechaUtil.obtenerFechaActual());
			if (StringUtil.isNotNullOrBlank(obj.getIdTipoDocSunat())) {
				String nroDocCalc = tipoDocSunatEntidadDaoImpl.update(obj.getIdTipoDocSunat(), obj.getIdEntidadSelect(),
						null, obj.getSerie()); // OJO AQUI
				if (!StringUtil.isNotNullOrBlank(obj.getNroDoc())) {
					// generar el nro doc
					obj.setNroDoc(nroDocCalc);
				}
			}
			obj.setIdControlPago(this.controlPagoDaoImpl.generarId());
			this.controlPagoDaoImpl.save(obj);

		} else {
			obj.setFechaModificacion(FechaUtil.obtenerFechaActual());
			this.controlPagoDaoImpl.update(obj);
		}

		if (!CollectionUtil.isEmpty(obj.getControlPagoDetControlPagoList())) {
			for (var objDet : obj.getControlPagoDetControlPagoList()) {
				if (objDet.isChecked()) {
					if (!StringUtil.isNotNullOrBlank(objDet.getIdDetControlPago())) {
						objDet.setMonto(objDet.getMonto().subtract(objDet.getMontoResta()));
						objDet.setFechaCreacion(FechaUtil.obtenerFechaActual());
						registrarDetControlPago(objDet, obj, AccionType.CREAR);

						if (StringUtil.isNotNullOrBlank(objDet.getId())) {
							DetPlanPagos detPlanPagos = detPlanPagosDaoImpl.find(DetPlanPagos.class,
									objDet.getId() + "");
							detPlanPagos.setMontoResta(objDet.getMontoResta());
							if (detPlanPagos.getMontoResta().compareTo(BigDecimal.ZERO) > 0) {
								detPlanPagos.setEstado("PENDIENTE");
							} else {
								detPlanPagos.setEstado("CANCELADO");
							}
							detPlanPagosDaoImpl.update(detPlanPagos);
						}

					} else {
						// registrarDetalleCargaAcademica(detCargaAcademica, objEntity,
						// AccionType.MODIFICAR);
					}

				} else {
					// registrarDetalleCargaAcademica(detCargaAcademica, null, AccionType.ELIMINAR);
				}
			}
		}
		return obj.getIdControlPago();
	}

	@Override
	public List<ControlPago> listarControlPago(BaseSearch filtro) {
		List<ControlPago> resultado = controlPagoDaoImpl.listar(filtro);
		// TODO:REVISAR ALGORITMO MALO
		for (var obj : resultado) {
			obj.setControlPagoDetControlPagoList(listarDetControlPagoByControlPago(obj.getIdControlPago()));
			obj.setVarCantidad(this.detControlPagoDaoImpl.listar(obj.getIdControlPago()).size());
		}
		return resultado;
	}

	private List<DetControlPago> listarDetControlPagoByControlPago(String idControlPago) {
		return this.detControlPagoDaoImpl.listarById(idControlPago);
	}

	@Override
	public List<TipoDocSunatEntidad> listarTipoDocByItem(TipoDocSunatEntidad filtro) {
		return this.tipoDocSunatEntidadDaoImpl.listarByItem(filtro);
	}

	@Override
	public int contarListarControlPago(BaseSearch filtro) {
		return this.controlPagoDaoImpl.contar(filtro);
	}

	private DetControlPago registrarDetControlPago(DetControlPago objDet, ControlPago obj, AccionType accionType) {
		objDet.setControlPago(obj);
		switch (accionType) {
		case CREAR:
			objDet.setIdDetControlPago(this.detControlPagoDaoImpl.generarId());
			this.detControlPagoDaoImpl.save(objDet);
			break;
		case MODIFICAR:
			this.detControlPagoDaoImpl.update(objDet);
			break;
		case ELIMINAR:
			objDet = this.detControlPagoDaoImpl.find(DetControlPago.class, objDet.getIdDetControlPago());
			this.detControlPagoDaoImpl.delete(objDet);
			break;
		default:
			break;
		}
		return objDet;
	}

	@Override
	public CatalogoCuenta controladorAccionCatalogoCuenta(CatalogoCuenta obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdCatalogoCuenta(this.catalogoCuentaDaoImpl.generarId());
			obj.setFechaCreacion(FechaUtil.obtenerFechaActual());
			this.catalogoCuentaDaoImpl.save(obj);
			break;
		case MODIFICAR:
			obj.setFechaModificacion(FechaUtil.obtenerFechaActual());
			this.catalogoCuentaDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.catalogoCuentaDaoImpl.find(CatalogoCuenta.class, obj.getIdCatalogoCuenta());
			this.catalogoCuentaDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.catalogoCuentaDaoImpl.find(CatalogoCuenta.class, obj.getIdCatalogoCuenta());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<CatalogoCuenta> listarCatalogoCuenta(BaseSearch filtro) {
		return this.catalogoCuentaDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarCatalogoCuenta(BaseSearch filtro) {
		return this.catalogoCuentaDaoImpl.contar(filtro);
	}

	@Override
	public CatalogoCuenta findCatalogoCuenta(CatalogoCuenta filtro) {
		return catalogoCuentaDaoImpl.find(filtro);
	}

	@Override
	public Empresa controladorAccionEmpresa(Empresa obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdEmpresa(this.empresaDaoImpl.generarId());
			this.empresaDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.empresaDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.empresaDaoImpl.find(Empresa.class, obj.getIdEmpresa());
			this.empresaDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.empresaDaoImpl.find(Empresa.class, obj.getIdEmpresa());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<Empresa> listarEmpresa(BaseSearch filtro) {
		return this.empresaDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarEmpresa(BaseSearch filtro) {
		return this.empresaDaoImpl.contar(filtro);
	}

	@Override
	public Empresa findEmpresa(Empresa filtro) {
		return empresaDaoImpl.find(filtro);
	}

	@Override
	public TipoDocSunatEntidad controladorAccionTipoDocSunatEntidad(TipoDocSunatEntidad obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdTipoDocSunatEntidad(this.tipoDocSunatEntidadDaoImpl.generarId());
			this.tipoDocSunatEntidadDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.tipoDocSunatEntidadDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.tipoDocSunatEntidadDaoImpl.find(TipoDocSunatEntidad.class, obj.getIdTipoDocSunatEntidad());
			this.tipoDocSunatEntidadDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.tipoDocSunatEntidadDaoImpl.find(TipoDocSunatEntidad.class, obj.getIdTipoDocSunatEntidad());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<TipoDocSunatEntidad> listarTipoDocSunatEntidad(BaseSearch filtro) {
		return this.tipoDocSunatEntidadDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarTipoDocSunatEntidad(BaseSearch filtro) {
		return this.tipoDocSunatEntidadDaoImpl.contar(filtro);
	}

	@Override
	public TipoDocSunatEntidad findTipoDocSunatEntidad(TipoDocSunatEntidad filtro) {
		return tipoDocSunatEntidadDaoImpl.get(filtro.getIdItemByTipoDocSunat(), null, filtro.getSerie());
	}

	@Override
	public void registrarPlanPago(PlanPagos obj) {
		if (!StringUtil.isNotNullOrBlank(obj.getIdPlanPagos())) {
			obj.setFechaCreacion(FechaUtil.obtenerFechaActual());
			obj.setIdPlanPagos(this.planPagosDaoImpl.generarId());
			this.planPagosDaoImpl.save(obj);

		} else {
			obj.setFechaModificacion(FechaUtil.obtenerFechaActual());
			this.planPagosDaoImpl.update(obj);
		}

		if (!CollectionUtil.isEmpty(obj.getPlanPagosDetPlanPagosList())) {
			for (var objDet : obj.getPlanPagosDetPlanPagosList()) {
				if (!StringUtil.isNotNullOrBlank(objDet.getIdDetPlanPagos())) {
					registrarDetallePlanPagos(objDet, obj, AccionType.CREAR);
				} else {
					registrarDetallePlanPagos(objDet, obj, AccionType.MODIFICAR);
				}
			}
		}
	}

	@Override
	public PlanPagos obtenerPlanPagosByIdAlumno(PlanPagos filtro) {
		return planPagosDaoImpl.get(filtro);
	}

	@Override
	public DetPlanPagos registrarDetallePlanPagos(DetPlanPagos objDet, PlanPagos obj, AccionType accionType) {
		objDet.setPlanPagos(obj);
		switch (accionType) {
		case CREAR:
			objDet.setIdDetPlanPagos(this.detPlanPagosDaoImpl.generarId());
			this.detPlanPagosDaoImpl.save(objDet);
			break;
		case MODIFICAR:
			this.detPlanPagosDaoImpl.update(objDet);
			break;
		case ELIMINAR:
			objDet = this.detPlanPagosDaoImpl.find(DetPlanPagos.class, objDet.getIdDetPlanPagos());
			this.detPlanPagosDaoImpl.delete(objDet);
			break;
		default:
			break;
		}
		return objDet;
	}

	@Override
	public DetPlanPagos findDetPlanPagos(DetPlanPagos filtro) {
		return detPlanPagosDaoImpl.find(filtro);
	}

	@Override
	public List<DetPlanPagos> listarDetPlanPagos(BaseSearch filtro) {
		List<DetPlanPagos> resultado = new ArrayList<>();
		List<DetPlanPagos> resultadoTemp = detPlanPagosDaoImpl.listar(filtro);
		Map<String, CuotaConcepto> parametrosCuotaConcepto = new HashMap<>();
		for (var objData : resultadoTemp) {
			String key = objData.getCuotaConcepto().getIdCuotaConcepto();
			parametrosCuotaConcepto.put(key, objData.getCuotaConcepto());
		}

		// TODO:REVISAR ALGORITMO MALO
		for (var entryMap : parametrosCuotaConcepto.entrySet()) {
			try {
				DetPlanPagos detPlanPagosT = new DetPlanPagos();// entryMap.getValue();
				List<DetPlanPagos> listaDetPlanPagos = new ArrayList<>();
				BigDecimal cuota = new BigDecimal("0.00");
				for (var objData : resultadoTemp) {
					DetPlanPagos detPlanPagosT2 = (DetPlanPagos) BeanUtils.cloneBean(objData);
					CuotaConcepto cuotaConcepto = (CuotaConcepto) BeanUtils.cloneBean(objData.getCuotaConcepto());
					detPlanPagosT2.setCuotaConcepto(cuotaConcepto);
					if (detPlanPagosT2.getCuotaConcepto().getIdCuotaConcepto().equals(entryMap.getKey())) {
						cuota = cuota.add(detPlanPagosT2.getCuota());
						detPlanPagosT.setCuota(detPlanPagosT2.getCuota());
						listaDetPlanPagos.add(detPlanPagosT2);
					}
				}
				detPlanPagosT.setDetPlanPagosList(listaDetPlanPagos);
				if (detPlanPagosT.getDetPlanPagosList().size() > 2) {
					detPlanPagosT.setEsLista("S");
					detPlanPagosT.setCuota(cuota);
					detPlanPagosT.setCuotaConcepto(detPlanPagosT.getDetPlanPagosList().get(0).getCuotaConcepto());
				} else {
					detPlanPagosT = detPlanPagosT.getDetPlanPagosList().get(0);
				}
				resultado.add(detPlanPagosT);
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
		return resultado;
	}

	@Override
	public int contarListarDetPlanPagos(BaseSearch filtro) {
		return this.detPlanPagosDaoImpl.contar(filtro);
	}

	@Override
	public List<DetPlanPagos> listarConceptoPagoAlumnoSemestre(Long idAnho, Long idPeriodo, String idAlumno,
			boolean flagFaltaMontoResta) {
		return detPlanPagosDaoImpl.listar(idAnho, idPeriodo, idAlumno, flagFaltaMontoResta);
	}

}