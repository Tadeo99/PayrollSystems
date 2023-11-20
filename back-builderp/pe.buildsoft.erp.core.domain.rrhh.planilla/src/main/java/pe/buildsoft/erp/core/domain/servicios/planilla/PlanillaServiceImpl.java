
package pe.buildsoft.erp.core.domain.servicios.planilla;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import pe.buildsoft.erp.core.domain.entidades.planilla.Adelanto;
import pe.buildsoft.erp.core.domain.entidades.planilla.ConceptoFijosTrabajador;
import pe.buildsoft.erp.core.domain.entidades.planilla.ConceptoPdt;
import pe.buildsoft.erp.core.domain.entidades.planilla.ConceptoRegimenPensionario;
import pe.buildsoft.erp.core.domain.entidades.planilla.ConceptosTipoPlanilla;
import pe.buildsoft.erp.core.domain.entidades.planilla.DetRentaQuintaCategoria;
import pe.buildsoft.erp.core.domain.entidades.planilla.DetallePersonalConcepto;
import pe.buildsoft.erp.core.domain.entidades.planilla.DetallePlanilla;
import pe.buildsoft.erp.core.domain.entidades.planilla.DetallePlanillaConcepto;
import pe.buildsoft.erp.core.domain.entidades.planilla.EPSConf;
import pe.buildsoft.erp.core.domain.entidades.planilla.EPSPersonal;
import pe.buildsoft.erp.core.domain.entidades.planilla.Feriado;
import pe.buildsoft.erp.core.domain.entidades.planilla.InformaOtrosIngreso5ta;
import pe.buildsoft.erp.core.domain.entidades.planilla.PeriodoPlanilla;
import pe.buildsoft.erp.core.domain.entidades.planilla.PersonalConcepto;
import pe.buildsoft.erp.core.domain.entidades.planilla.RentaQuintaCategoria;
import pe.buildsoft.erp.core.domain.entidades.planilla.TareoPersonal;
import pe.buildsoft.erp.core.domain.entidades.planilla.TipoPlanilla;
import pe.buildsoft.erp.core.domain.entidades.planilla.Vacaciones;
import pe.buildsoft.erp.core.domain.entidades.planilla.ValoresUIT;
import pe.buildsoft.erp.core.domain.entidades.planilla.VariableConf;
import pe.buildsoft.erp.core.domain.entidades.planilla.VariableConfDet;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.AdelantoDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.ConceptoPdtDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.ConceptoRegimenPensionarioDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.ConceptoTrabajadorDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.ConceptosTipoPlanillaDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.DetRentaQuintaCategoriaDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.DetallePersonalConceptoDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.DetallePlanillaConceptoDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.DetallePlanillaDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.EPSConfDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.EPSPersonalDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.FeriadoDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.InformaOtrosIngreso5taDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.PeriodoPlanillaDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.PersonalConceptoDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.RentaQuintaCategoriaDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.TareoPersonalDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.TipoPlanillaDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.VacacionesDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.ValoresUITDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.VariableConfDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.VariableConfDetDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.servicios.planilla.PlanillaServiceLocal;
import pe.buildsoft.erp.core.domain.planilla.type.VariableAplicacionType;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.SelectItemVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.type.MesType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * La Class PlanillaServiceImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:10 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class PlanillaServiceImpl implements PlanillaServiceLocal {

	private final Logger log = LoggerFactory.getLogger(PlanillaServiceImpl.class);

	/** El servicio concepto pdt dao impl. */
	@Inject
	private ConceptoPdtDaoLocal conceptoPdtDao;

	/** El servicio adelanto dao impl. */
	@Inject
	private AdelantoDaoLocal adelantoDao;

	/** El servicio concepto trabajador dao impl. */
	@Inject
	private ConceptoTrabajadorDaoLocal conceptoTrabajadorDao;

	/** El servicio conceptos tipo planilla dao impl. */
	@Inject
	private ConceptosTipoPlanillaDaoLocal conceptosTipoPlanillaDao;

	/** El servicio valores u i t dao impl. */
	@Inject
	private ValoresUITDaoLocal valoresUITDao;

	/** El servicio concepto regimen pensionario dao impl. */
	@Inject
	private ConceptoRegimenPensionarioDaoLocal conceptoRegimenPensionarioDao;

	/** El servicio renta quinta categoria dao impl. */
	@Inject
	private RentaQuintaCategoriaDaoLocal rentaQuintaCategoriaDao;

	/** El servicio detalle planilla concepto dao impl. */
	@Inject
	private DetallePlanillaConceptoDaoLocal detallePlanillaConceptoDao;

	/** El servicio detalle planilla dao impl. */
	@Inject
	private DetallePlanillaDaoLocal detallePlanillaDao;

	/** El servicio periodo planilla dao impl. */
	@Inject
	private PeriodoPlanillaDaoLocal periodoPlanillaDao;

	/** El servicio asistencia personal dao impl. */
	@Inject
	private TareoPersonalDaoLocal tareoPersonalDao;

	/** El servicio tipo planilla dao impl. */
	@Inject
	private TipoPlanillaDaoLocal tipoPlanillaDao;

	/** El servicio det renta quinta categoria dao impl. */
	@Inject
	private DetRentaQuintaCategoriaDaoLocal detRentaQuintaCategoriaDao;

	@Inject
	private PersonalConceptoDaoLocal personalConceptoDao;

	@Inject
	private DetallePersonalConceptoDaoLocal detallePersonalConceptoDao;

	/** El servicio eps conf dao impl. */
	@Inject
	private EPSConfDaoLocal epsConfDao;

	/** El servicio eps personal dao impl. */
	@Inject
	private EPSPersonalDaoLocal epsPersonalDao;

	/** El servicio vacaciones dao impl. */
	@Inject
	private VacacionesDaoLocal vacacionesDao;

	/** El servicio feriado dao impl. */
	@Inject
	private FeriadoDaoLocal feriadoDao;

	/** El servicio variable conf dao impl. */
	@Inject
	private VariableConfDaoLocal variableConfDao;

	/** El servicio variable conf det dao impl. */
	@Inject
	private VariableConfDetDaoLocal variableConfDetDao;

	/** El servicio informa otros ingreso5ta dao impl. */
	@Inject
	private InformaOtrosIngreso5taDaoLocal informaOtrosIngreso5taDao;

	@Override
	public ConceptoPdt controladorAccionConceptoPdt(ConceptoPdt obj, AccionType accionType) {
		switch (accionType) {
			case CREAR:
				obj.setIdConceptoPdt(this.conceptoPdtDao.generarId());
				this.conceptoPdtDao.save(obj);
				break;
			case MODIFICAR:
				this.conceptoPdtDao.update(obj);
				break;

			case ELIMINAR:
				obj = this.conceptoPdtDao.find(ConceptoPdt.class, obj.getIdConceptoPdt());
				this.conceptoPdtDao.delete(obj);
				break;

			case FIND_BY_ID:
				obj = this.conceptoPdtDao.find(ConceptoPdt.class, obj.getIdConceptoPdt());
				break;

			default:
				break;
		}

		return obj;
	}

	@Override
	public List<ConceptoPdt> listarConceptoPdt(BaseSearch filtro) {
		return this.conceptoPdtDao.listar(filtro);
	}

	@Override
	public List<ConceptoPdt> obtenerFormulaConceptoPdt() {
		return this.conceptoPdtDao.obtenerFormulaConceptoPdt();
	}

	@Override
	public int contarListarConceptoPdt(BaseSearch filtro) {
		return this.conceptoPdtDao.contar(filtro);
	}

	@Override
	public ConceptoPdt findConceptoPdt(ConceptoPdt filtro) {
		return conceptoPdtDao.find(filtro);
	}

	@Override
	public Adelanto controladorAccionAdelanto(Adelanto obj, AccionType accionType) {
		switch (accionType) {
			case CREAR:
				obj.setIdAdelanto(this.adelantoDao.generarId());
				this.adelantoDao.save(obj);
				break;
			case MODIFICAR:
				this.adelantoDao.update(obj);
				break;

			case ELIMINAR:
				obj = this.adelantoDao.find(Adelanto.class, obj.getIdAdelanto());
				this.adelantoDao.delete(obj);
				break;

			case FIND_BY_ID:
				obj = this.adelantoDao.find(Adelanto.class, obj.getIdAdelanto());
				break;

			default:
				break;
		}

		return obj;
	}

	@Override
	public List<Adelanto> listarAdelanto(BaseSearch filtro) {
		return this.adelantoDao.listar(filtro);
	}

	@Override
	public int contarListarAdelanto(BaseSearch filtro) {
		return this.adelantoDao.contar(filtro);
	}

	@Override
	public ConceptoFijosTrabajador controladorAccionConceptoFijosTrabajador(ConceptoFijosTrabajador obj,
			AccionType accionType) {
		switch (accionType) {
			case CREAR:
				obj.setIdConceptoTrabajador(this.conceptoTrabajadorDao.generarId());
				this.conceptoTrabajadorDao.save(obj);
				break;
			case MODIFICAR:
				this.conceptoTrabajadorDao.update(obj);
				break;

			case ELIMINAR:
				obj = this.conceptoTrabajadorDao.find(ConceptoFijosTrabajador.class, obj.getIdConceptoTrabajador());
				this.conceptoTrabajadorDao.delete(obj);
				break;

			case FIND_BY_ID:
				obj = this.conceptoTrabajadorDao.find(ConceptoFijosTrabajador.class, obj.getIdConceptoTrabajador());
				break;

			default:
				break;
		}

		return obj;
	}

	@Override
	public List<ConceptoFijosTrabajador> listarConceptoFijosTrabajador(BaseSearch filtro) {
		return this.conceptoTrabajadorDao.listar(filtro);
	}

	@Override
	public int contarListarConceptoFijosTrajabador(BaseSearch filtro) {
		return this.conceptoTrabajadorDao.contar(filtro);
	}

	@Override
	public ConceptoFijosTrabajador findConceptoTrabajador(ConceptoFijosTrabajador filtro) {
		return conceptoTrabajadorDao.find(filtro);
	}

	@Override
	public ConceptosTipoPlanilla controladorAccionConceptosTipoPlanilla(ConceptosTipoPlanilla obj,
			AccionType accionType) {
		switch (accionType) {
			case CREAR:
				obj.setIdConceptosTipoPlanilla(this.conceptosTipoPlanillaDao.generarId());
				this.conceptosTipoPlanillaDao.save(obj);
				break;
			case MODIFICAR:
				this.conceptosTipoPlanillaDao.update(obj);
				break;
			case ELIMINAR:
				obj = this.conceptosTipoPlanillaDao.find(ConceptosTipoPlanilla.class, obj.getIdConceptosTipoPlanilla());
				this.conceptosTipoPlanillaDao.delete(obj);
				break;
			default:
				break;
		}
		return obj;
	}

	@Override
	public List<ConceptosTipoPlanilla> listarConceptosTipoPlanilla(BaseSearch filtro) {
		return this.conceptosTipoPlanillaDao.listar(filtro);
	}

	/*
	 * @Override public List<ConceptosTipoPlanilla>
	 * obtenerFormulaConceptosTipoPlanilla() { return
	 * this.conceptosTipoPlanillaDaoImpl.getFormulaConceptosTipo(); }
	 */

	@Override
	public int contarListarConceptosTipoPlanilla(BaseSearch filtro) {
		return this.conceptosTipoPlanillaDao.contar(filtro);
	}

	@Override
	public ValoresUIT controladorAccionValoresUIT(ValoresUIT obj, AccionType accionType) {
		switch (accionType) {
			case CREAR:
				obj.setIdUit(this.valoresUITDao.generarId());
				this.valoresUITDao.save(obj);
				break;
			case MODIFICAR:
				this.valoresUITDao.update(obj);
				break;

			case ELIMINAR:
				obj = this.valoresUITDao.find(ValoresUIT.class, obj.getIdUit());
				this.valoresUITDao.delete(obj);
				break;

			case FIND_BY_ID:
				obj = this.valoresUITDao.find(ValoresUIT.class, obj.getIdUit());
				break;

			default:
				break;
		}

		return obj;
	}

	@Override
	public List<ValoresUIT> listarValoresUIT(BaseSearch filtro) {
		return this.valoresUITDao.listar(filtro);
	}

	@Override
	public int contarListarValoresUIT(BaseSearch filtro) {
		return this.valoresUITDao.contar(filtro);
	}

	@Override
	public ValoresUIT findValorUIT(ValoresUIT filtro) {
		return valoresUITDao.find(filtro);
	}

	@Override
	public List<ConceptoRegimenPensionario> agregarConceptoRegimenPensionario(
			List<ConceptoRegimenPensionario> listaObj) {
		for (var objEntity : listaObj) {
			if (!objEntity.isEsEliminado()) {
				if (!StringUtil.isNotNullOrBlank(objEntity.getIdConceptoRegimenPensionario())) {
					objEntity.setIdConceptoRegimenPensionario(this.conceptoRegimenPensionarioDao.generarId());
					this.conceptoRegimenPensionarioDao.save(objEntity);

				} else {
					this.conceptoRegimenPensionarioDao.update(objEntity);
				}
			} else {
				objEntity = this.conceptoRegimenPensionarioDao.find(ConceptoRegimenPensionario.class,
						objEntity.getIdConceptoRegimenPensionario());
				this.conceptoRegimenPensionarioDao.delete(objEntity);
			}
		}

		return listaObj;
	}

	@Override
	public ConceptoRegimenPensionario controladorAccionConceptoRegimenPensionario(ConceptoRegimenPensionario obj,
			AccionType accionType) {
		switch (accionType) {
			case CREAR:
				obj.setIdConceptoRegimenPensionario(this.conceptoRegimenPensionarioDao.generarId());
				this.conceptoRegimenPensionarioDao.save(obj);
				break;
			case MODIFICAR:
				this.conceptoRegimenPensionarioDao.update(obj);
				break;

			case ELIMINAR:
				obj = this.conceptoRegimenPensionarioDao.find(ConceptoRegimenPensionario.class,
						obj.getIdConceptoRegimenPensionario());
				this.conceptoRegimenPensionarioDao.delete(obj);
				break;

			case FIND_BY_ID:
				obj = this.conceptoRegimenPensionarioDao.find(ConceptoRegimenPensionario.class,
						obj.getIdConceptoRegimenPensionario());
				break;

			default:
				break;
		}

		return obj;
	}

	@Override
	public List<ConceptoRegimenPensionario> listarConceptoRegimenPensionario(BaseSearch filtro) {
		return this.conceptoRegimenPensionarioDao.listar(filtro);
	}

	@Override
	public int contarListarConceptoRegimenPensionario(BaseSearch filtro) {
		return this.conceptoRegimenPensionarioDao.contar(filtro);
	}

	@Override
	public RentaQuintaCategoria controladorAccionRentaQuintaCategoria(RentaQuintaCategoria obj, AccionType accionType) {
		switch (accionType) {
			case CREAR:
				obj.setIdRentaQuintaCategoria(this.rentaQuintaCategoriaDao.generarId());
				this.rentaQuintaCategoriaDao.save(obj);
				break;
			case MODIFICAR:
				this.rentaQuintaCategoriaDao.update(obj);
				break;

			case ELIMINAR:
				obj = this.rentaQuintaCategoriaDao.find(RentaQuintaCategoria.class, obj.getIdRentaQuintaCategoria());
				this.rentaQuintaCategoriaDao.delete(obj);
				break;

			case FIND_BY_ID:
				obj = this.rentaQuintaCategoriaDao.find(RentaQuintaCategoria.class, obj.getIdRentaQuintaCategoria());
				break;

			default:
				break;
		}

		return obj;
	}

	@Override
	public List<RentaQuintaCategoria> listarRentaQuintaCategoria(BaseSearch filtro) {
		return this.rentaQuintaCategoriaDao.listar(filtro);
	}

	@Override
	public int contarListarRentaQuintaCategoria(BaseSearch filtro) {
		return this.rentaQuintaCategoriaDao.contar(filtro);
	}

	@Override
	public List<DetallePlanilla> listarDetallePlanilla(BaseSearch filtro) {
		var resultado = detallePlanillaDao.listar(filtro);
		for (var obj : resultado) {
			var filtroDet = new DetallePlanillaConcepto();
			filtroDet.setDetallePlanilla(obj);
			filtroDet.setConcepto(new ConceptosTipoPlanilla());
			filtroDet.getConcepto().setConceptoPdt(new ConceptoPdt());
			obj.setDetallePlanillaDetallePlanillaConceptoList(obtenerDetallePlanillaConcepto(filtroDet));
		}
		return resultado;
	}

	@Override
	public int contarListarDetallePlanilla(BaseSearch filtro) {
		return this.detallePlanillaDao.contar(filtro);
	}

	@Override
	public PeriodoPlanilla controladorAccionPeriodoPlanilla(PeriodoPlanilla obj, AccionType accionType) {
		switch (accionType) {
			case CREAR:
				obj.setIdPeriodoPlanilla(this.periodoPlanillaDao.generarId());
				this.periodoPlanillaDao.save(obj);
				break;
			case MODIFICAR:
				this.periodoPlanillaDao.update(obj);
				break;

			case ELIMINAR:
				obj = this.periodoPlanillaDao.find(PeriodoPlanilla.class, obj.getIdPeriodoPlanilla());
				this.periodoPlanillaDao.delete(obj);
				break;

			case FIND_BY_ID:
				obj = this.periodoPlanillaDao.find(PeriodoPlanilla.class, obj.getIdPeriodoPlanilla());
				break;

			default:
				break;
		}

		return obj;
	}

	@Override
	public List<PeriodoPlanilla> listarPeriodoPlanilla(BaseSearch filtro) {
		return this.periodoPlanillaDao.listar(filtro);
	}

	@Override
	public int contarListarPeriodoPlanilla(BaseSearch filtro) {
		return this.periodoPlanillaDao.contar(filtro);
	}

	@Override
	public PeriodoPlanilla findPeriodoPlanilla(PeriodoPlanilla filtro) {
		return periodoPlanillaDao.find(filtro);
	}

	@Override
	public TareoPersonal controladorAccionTareoPersonal(TareoPersonal obj, AccionType accionType) {
		switch (accionType) {
			case CREAR:
				obj.setIdTareoPersonal(this.tareoPersonalDao.generarId());
				this.tareoPersonalDao.save(obj);
				break;
			case MODIFICAR:
				this.tareoPersonalDao.update(obj);
				break;

			case ELIMINAR:
				obj = this.tareoPersonalDao.find(TareoPersonal.class, obj.getIdTareoPersonal());
				this.tareoPersonalDao.delete(obj);
				break;

			case FIND_BY_ID:
				obj = this.tareoPersonalDao.find(TareoPersonal.class, obj.getIdTareoPersonal());
				break;

			default:
				break;
		}
		return obj;
	}

	/*
	 * @Override public List<AsistenciaPersonal>
	 * obtenerAsistenciaPersonal(BaseSearch filtro) throws Exception {
	 * List<AsistenciaPersonal> resultado =
	 * asistenciaPersonalDaoImpl.listar(filtro); Map<String, String>
	 * asistenciaRegistroMap = new HashMap<>(); if (resultado != null) { for
	 * (AsistenciaPersonal asistencia : resultado) {
	 * asistenciaRegistroMap.put(asistencia.getIdPersonal(), ""); } } else {
	 * resultado = new ArrayList<>(); } List<PersonalVo> listaTemp =
	 * rrhhPersonalClientImpl.obtenerPersonal(filtro.getIdItemByCategoriaOcupacional
	 * (), filtro.getIdPersonal()); if (listaTemp != null) { try { for (var objPers
	 * : listaTemp) { if
	 * (!asistenciaRegistroMap.containsKey(objPers.getIdPersonal())) {
	 * AsistenciaPersonal asistenciaPersist = new AsistenciaPersonal();
	 * asistenciaPersist.setIdPersonal(objPers.getIdPersonal());
	 * asistenciaPersist.setIdItemByCategoriaOcupacional(filtro.
	 * getIdItemByCategoriaOcupacional());
	 * asistenciaPersist.setIdItemByMes(filtro.getIdItemByMes());
	 * asistenciaPersist.setIdAnhio(filtro.getIdAnhio()); //
	 * asistenciaPersist.setAnhio(filtro.getAnhio());
	 * asistenciaPersist.setIdItemByPeriocidad(filtro.getIdItemByPeriocidad());
	 * resultado.add((AsistenciaPersonal) BeanUtils.cloneBean(asistenciaPersist)); }
	 * } } catch (Exception e) { log.error("obtenerAsistenciaPersonal", e); }
	 * 
	 * } return resultado; }
	 */
	@Override
	public List<TareoPersonal> listarTareoPersonal(BaseSearch filtro) {
		return tareoPersonalDao.listar(filtro);
	}

	@Override
	public int contarListarTareoPersonal(BaseSearch filtro) {
		return tareoPersonalDao.contar(filtro);
	}

	@Override
	public TipoPlanilla controladorAccionTipoPlanilla(TipoPlanilla obj, AccionType accionType) {
		switch (accionType) {
			case CREAR:
				obj.setIdTipoPlanilla(this.tipoPlanillaDao.generarId());
				this.tipoPlanillaDao.save(obj);
				break;
			case MODIFICAR:
				this.tipoPlanillaDao.update(obj);
				break;

			case ELIMINAR:
				obj = this.tipoPlanillaDao.find(TipoPlanilla.class, obj.getIdTipoPlanilla());
				this.tipoPlanillaDao.delete(obj);
				break;

			case FIND_BY_ID:
				obj = this.tipoPlanillaDao.find(TipoPlanilla.class, obj.getIdTipoPlanilla());
				break;

			default:
				break;
		}
		return obj;
	}

	@Override
	public List<TipoPlanilla> listarTipoPlanilla(BaseSearch filtro) {
		return this.tipoPlanillaDao.listar(filtro);

	}

	@Override
	public int contarListarTipoPlanilla(BaseSearch filtro) {
		return this.tipoPlanillaDao.contar(filtro);
	}

	@Override
	public DetRentaQuintaCategoria controladorAccionDetRentaQuintaCategoria(DetRentaQuintaCategoria obj,
			AccionType accionType) {
		switch (accionType) {
			case CREAR:
				obj.setIdDetRentaQuintaCategoria(this.detRentaQuintaCategoriaDao.generarId());
				this.detRentaQuintaCategoriaDao.save(obj);
				break;
			case MODIFICAR:
				this.detRentaQuintaCategoriaDao.update(obj);
				break;

			case ELIMINAR:
				obj = this.detRentaQuintaCategoriaDao.find(DetRentaQuintaCategoria.class,
						obj.getIdDetRentaQuintaCategoria());
				this.detRentaQuintaCategoriaDao.delete(obj);
				break;

			case FIND_BY_ID:
				obj = this.detRentaQuintaCategoriaDao.find(DetRentaQuintaCategoria.class,
						obj.getIdDetRentaQuintaCategoria());
				break;

			default:
				break;
		}

		return obj;
	}

	@Override
	public List<DetRentaQuintaCategoria> listarDetRentaQuintaCategoria(BaseSearch filtro) {
		return this.detRentaQuintaCategoriaDao.listar(filtro);
	}

	@Override
	public int contarListarDetRentaQuintaCategoria(BaseSearch filtro) {
		return this.detRentaQuintaCategoriaDao.contar(filtro);
	}

	// TODO:FALTA OPTIMIZAR
	private List<DetallePlanillaConcepto> obtenerDetallePlanillaConcepto(DetallePlanillaConcepto filtro) {
		var resultado = new ArrayList<DetallePlanillaConcepto>();
		String[] listTipoConceptPDT = { "I", "D", "A" };
		for (var objTC : listTipoConceptPDT) {
			var filtroDet = new DetallePlanillaConcepto();
			filtroDet.setConcepto(new ConceptosTipoPlanilla());
			filtroDet.getConcepto().setConceptoPdt(new ConceptoPdt());
			filtroDet.getConcepto().getConceptoPdt().setTipo(objTC);
			filtroDet.setDetallePlanilla(filtro.getDetallePlanilla());
			var listaObj = detallePlanillaConceptoDao.get(filtroDet);
			var detObj = new DetallePlanillaConcepto();
			detObj.setConcepto(new ConceptosTipoPlanilla());
			detObj.getConcepto().setConceptoPdt(new ConceptoPdt());
			detObj.getConcepto().getConceptoPdt().setTipo(objTC);
			detObj.setDetallePlanillaDetallePlanillaConceptoList(listaObj);
			resultado.add(detObj);
		}
		return resultado;
	}

	@Override
	public List<PersonalConcepto> listarPersonalConcepto(BaseSearch filtro) throws Exception {
		if (filtro.getIdPeriodoPlanilla() != null && filtro.getIdTipoPlanilla() != null) {
			return this.personalConceptoDao.listar(filtro);
		}
		return new ArrayList<PersonalConcepto>();
	}

	@Override
	public int contarListarPersonalConcepto(BaseSearch filtro) {
		return this.personalConceptoDao.contar(filtro);
	}

	@Override
	public List<DetallePersonalConcepto> obtenerDetallePersonalConcepto(String idTipoPlanilla, String idPersonal,
			String idPeriodoPlanilla) {
		var resultado = new ArrayList<DetallePersonalConcepto>();
		var filtro = new DetallePersonalConcepto();
		filtro.setPersonalConcepto(new PersonalConcepto());
		filtro.getPersonalConcepto().setTipoPlanilla(new TipoPlanilla());
		filtro.getPersonalConcepto().getTipoPlanilla().setIdTipoPlanilla(idTipoPlanilla);
		// filtro.getPersonalConcepto().setPersonal(new Personal());
		filtro.getPersonalConcepto().setIdPersonal(idPersonal);
		filtro.getPersonalConcepto().setPeriodoPlanilla(new PeriodoPlanilla());
		filtro.getPersonalConcepto().getPeriodoPlanilla().setIdPeriodoPlanilla(idPeriodoPlanilla);
		var listdetallePersonalConcepto = this.detallePersonalConceptoDao.listar(filtro);

		var detallePersonalConceptoRegistroMap = new HashMap<String, String>();

		if (listdetallePersonalConcepto != null) {
			for (var pc : listdetallePersonalConcepto) {
				detallePersonalConceptoRegistroMap.put(pc.getConceptosTipoPlanilla().getIdConceptosTipoPlanilla(), "");
			}
		} else {
			listdetallePersonalConcepto = new ArrayList<>();

		}

		var listConceptosTipoPlanilla = this.conceptosTipoPlanillaDao.listarByTipoPlanilla(idTipoPlanilla);
		if (listConceptosTipoPlanilla != null) {
			for (var objCTP : listConceptosTipoPlanilla) {
				if (!detallePersonalConceptoRegistroMap.containsKey(objCTP.getIdConceptosTipoPlanilla())) {
					var objDPC = new DetallePersonalConcepto();
					objDPC.setConceptosTipoPlanilla(objCTP);
					listdetallePersonalConcepto.add(objDPC);
				}
			}
		}

		String[] listTipoConceptPDT = { "I", "D", "A" };
		for (var objConceptoPDT : listTipoConceptPDT) {
			var objTemp = new DetallePersonalConcepto();
			objTemp.setId(objConceptoPDT);
			var conceptoList = new ArrayList<DetallePersonalConcepto>();
			for (var temp : listdetallePersonalConcepto) {
				if (objConceptoPDT.equals(temp.getConceptosTipoPlanilla().getConceptoPdt().getTipo())) {
					conceptoList.add(temp);
				}
				if (objConceptoPDT.equals("D")
						&& temp.getConceptosTipoPlanilla().getConceptoPdt().getTipo().equals("T")) {
					conceptoList.add(temp);
				}
			}
			objTemp.setDetallePersonalConceptoList(conceptoList);
			resultado.add(objTemp);
		}

		return resultado;
	}

	@Override
	public Map<String, DetallePersonalConcepto> listarDetallePersonalConceptoMap(String idPersonal,
			String idTipoPlanilla, String idPeriodo) {
		return detallePersonalConceptoDao.listarMap(idPersonal, idTipoPlanilla, idPeriodo);
	}

	@Override
	public void registrarPersonalConcepto(List<PersonalConcepto> listaObj) {
		for (var objData : listaObj) {
			if (!StringUtil.isNotNullOrBlank(objData.getIdPersonalConcepto())) {
				objData.setIdPersonalConcepto(personalConceptoDao.generarId());
				this.personalConceptoDao.save(objData);
			} else {
				this.personalConceptoDao.update(objData);
			}
			// registrar detalle
			for (var temp : objData.getDetallePersonalConceptoList()) {
				for (var objDPC : temp.getDetallePersonalConceptoList()) {
					if (!StringUtil.isNotNullOrBlank(objDPC.getIdDetallePersonalConcepto())) {
						if (objDPC.getMonto() != null) {
							registrarDetallePersonalConcepto(objDPC, objData, AccionType.CREAR);
						}
					} else {
						registrarDetallePersonalConcepto(objDPC, objData, AccionType.MODIFICAR);
					}
				}

			}
		}
	}

	@Override
	public PersonalConcepto controladorAccionPersonalConcepto(PersonalConcepto obj, AccionType accionType) {
		switch (accionType) {
			case CREAR:
				obj.setIdPersonalConcepto(this.personalConceptoDao.generarId());
				this.personalConceptoDao.save(obj);
				break;
			case MODIFICAR:
				this.personalConceptoDao.update(obj);
				break;

			case ELIMINAR:
				obj = this.personalConceptoDao.find(PersonalConcepto.class, obj.getIdPersonalConcepto());
				this.personalConceptoDao.delete(obj);
				break;

			case FIND_BY_ID:
				obj = this.personalConceptoDao.find(PersonalConcepto.class, obj.getIdPersonalConcepto());
				break;

			default:
				break;
		}
		// registrar detalle
		for (var temp : obj.getDetallePersonalConceptoList()) {
			for (var objDPC : temp.getDetallePersonalConceptoList()) {
				if (!StringUtil.isNotNullOrBlank(objDPC.getIdDetallePersonalConcepto())) {
					if (objDPC.getMonto() != null) {
						registrarDetallePersonalConcepto(objDPC, obj, AccionType.CREAR);
					}
				} else {
					registrarDetallePersonalConcepto(objDPC, obj, AccionType.MODIFICAR);
				}
			}

		}
		return obj;
	}

	private DetallePersonalConcepto registrarDetallePersonalConcepto(DetallePersonalConcepto obj,
			PersonalConcepto objData, AccionType accionType) {
		obj.setPersonalConcepto(objData);
		switch (accionType) {
			case CREAR:
				obj.setIdDetallePersonalConcepto(detallePersonalConceptoDao.generarId());
				this.detallePersonalConceptoDao.save(obj);
				break;
			case MODIFICAR:
				this.detallePersonalConceptoDao.update(obj);
				break;
			case ELIMINAR:
				obj = this.detallePersonalConceptoDao.find(DetallePersonalConcepto.class,
						obj.getIdDetallePersonalConcepto());
				this.detallePersonalConceptoDao.delete(obj);
				break;
			default:
				break;
		}
		return obj;
	}

	@Override
	public EPSConf controladorAccionEPSConf(EPSConf obj, AccionType accionType) {
		switch (accionType) {
			case CREAR:
				obj.setIdEPSConf(this.epsConfDao.generarId());
				this.epsConfDao.save(obj);
				break;
			case MODIFICAR:
				this.epsConfDao.update(obj);
				break;

			case ELIMINAR:
				obj = this.epsConfDao.find(EPSConf.class, obj.getIdEPSConf());
				this.epsConfDao.delete(obj);
				break;

			case FIND_BY_ID:
				obj = this.epsConfDao.find(EPSConf.class, obj.getIdEPSConf());
				break;

			default:
				break;
		}

		return obj;
	}

	@Override
	public List<EPSConf> listarEPSConf(BaseSearch filtro) {
		return this.epsConfDao.listar(filtro);
	}

	@Override
	public int contarListarEPSConf(BaseSearch filtro) {
		return this.epsConfDao.contar(filtro);
	}

	@Override
	public List<EPSPersonal> listarEPSPersonal(BaseSearch filtro) throws Exception {
		return this.epsPersonalDao.listar(filtro);
	}

	@Override
	public int contarListarEPSPersonal(BaseSearch filtro) {
		return this.epsPersonalDao.contar(filtro);
	}

	@Override
	public EPSPersonal controladorAccionEPSPersonal(EPSPersonal obj, AccionType accionType) {
		switch (accionType) {
			case CREAR:
				obj.setIdEPSPersonal(this.epsPersonalDao.generarId());
				this.epsPersonalDao.save(obj);
				break;
			case MODIFICAR:
				this.epsPersonalDao.update(obj);
				break;

			case ELIMINAR:
				obj = this.epsPersonalDao.find(EPSPersonal.class, obj.getIdEPSPersonal());
				this.epsPersonalDao.delete(obj);
				break;

			case FIND_BY_ID:
				obj = this.epsPersonalDao.find(EPSPersonal.class, obj.getIdEPSPersonal());
				break;

			default:
				break;
		}

		return obj;
	}

	@Override
	public Vacaciones controladorAccionVacaciones(Vacaciones obj, AccionType accionType) {
		switch (accionType) {
			case CREAR:
				obj.setIdVacaciones(this.vacacionesDao.generarId());
				this.vacacionesDao.save(obj);
				break;
			case MODIFICAR:
				this.vacacionesDao.update(obj);
				break;

			case ELIMINAR:
				obj = this.vacacionesDao.find(Vacaciones.class, obj.getIdVacaciones());
				this.vacacionesDao.delete(obj);
				break;

			case FIND_BY_ID:
				obj = this.vacacionesDao.find(Vacaciones.class, obj.getIdVacaciones());
				break;

			default:
				break;
		}
		return obj;
	}

	@Override
	public List<Vacaciones> listarVacaciones(BaseSearch filtro) {
		return this.vacacionesDao.listar(filtro);
	}

	@Override
	public int contarListarVacaciones(BaseSearch filtro) {
		return this.vacacionesDao.contar(filtro);
	}

	@Override
	public Feriado controladorAccionFeriado(Feriado obj, AccionType accionType) {
		switch (accionType) {
			case CREAR:
				obj.setIdFeriado(this.feriadoDao.generarId());
				this.feriadoDao.save(obj);
				break;
			case MODIFICAR:
				this.feriadoDao.update(obj);
				break;

			case ELIMINAR:
				obj = this.feriadoDao.find(Feriado.class, obj.getIdFeriado());
				this.feriadoDao.delete(obj);
				break;

			case FIND_BY_ID:
				obj = this.feriadoDao.find(Feriado.class, obj.getIdFeriado());
				break;

			default:
				break;
		}
		return obj;
	}

	@Override
	public List<Feriado> listarFeriado(BaseSearch filtro) {
		return this.feriadoDao.listar(filtro);
	}

	@Override
	public int contarListarFeriado(BaseSearch filtro) {
		return this.feriadoDao.contar(filtro);
	}

	@Override
	public List<SelectItemVO> listarVariableAplicacion() {
		var resultado = new ArrayList<SelectItemVO>();
		var resultadoMap = new HashMap<String, SelectItemVO>();
		for (var obj : VariableAplicacionType.values()) {
			String key = obj.getDescripcion();
			if (!resultadoMap.containsKey(key)) {
				var objVar = getSelectItemVODet(obj, key.equalsIgnoreCase("Planilla"), key.equalsIgnoreCase("Mes"));
				resultado.add(objVar);
				resultadoMap.put(key, objVar);
			} else {
				var objVar = resultadoMap.get(key);
				var value = objVar.getListaData();
				value.add(new SelectItemVO(obj.getKey(), obj.getValue(), obj.getDescripcion()));
			}
		}
		return resultado;
	}

	private SelectItemVO getSelectItemVODet(VariableAplicacionType obj, boolean isAumentarMes, boolean isSoloMes) {
		var objVar = new SelectItemVO(obj.getKey(), obj.getValue(), obj.getDescripcion());
		var value = new ArrayList<SelectItemVO>();
		if (!isSoloMes) {
			value.add(new SelectItemVO(obj.getKey(), obj.getValue(), obj.getDescripcion()));
		}
		if (isAumentarMes) {
			for (MesType mes : MesType.values()) {
				String keyMes = mes.getValue() + "Planilla";
				value.add(new SelectItemVO(keyMes, keyMes, obj.getDescripcion()));
			}
			for (MesType mes : MesType.values()) {
				String keyMes = mes.getValue() + "Renta5ta";
				value.add(new SelectItemVO(keyMes, keyMes, obj.getDescripcion()));
			}
		}
		if (isSoloMes) {
			for (MesType mes : MesType.values()) {
				String keyMes = mes.getValue() + "Mes";
				value.add(new SelectItemVO(keyMes, mes.getKey() + "", obj.getDescripcion()));
			}
		}
		objVar.setListaData(value);
		return objVar;
	}

	@Override
	public VariableConf controladorAccionVariableConf(VariableConf obj, AccionType accionType) {
		switch (accionType) {
			case CREAR:
				obj.setIdVariableConf(this.variableConfDao.generarId());
				this.variableConfDao.save(obj);
				break;
			case MODIFICAR:
				this.variableConfDao.update(obj);
				break;

			case ELIMINAR:
				obj = this.variableConfDao.find(VariableConf.class, obj.getIdVariableConf());
				this.variableConfDao.delete(obj);
				break;

			case FIND_BY_ID:
				obj = this.variableConfDao.find(VariableConf.class, obj.getIdVariableConf());
				break;

			default:
				break;
		}
		return obj;
	}

	@Override
	public List<VariableConf> listarVariableConf(BaseSearch filtro) {
		return this.variableConfDao.listar(filtro);
	}

	@Override
	public int contarListarVariableConf(BaseSearch filtro) {
		return this.variableConfDao.contar(filtro);
	}

	@Override
	public VariableConfDet controladorAccionVariableConfDet(VariableConfDet obj, AccionType accionType) {
		switch (accionType) {
			case CREAR:
				obj.setIdVariableConfDet(this.variableConfDetDao.generarId());
				this.variableConfDetDao.save(obj);
				break;
			case MODIFICAR:
				this.variableConfDetDao.update(obj);
				break;

			case ELIMINAR:
				obj = this.variableConfDetDao.find(VariableConfDet.class, obj.getIdVariableConfDet());
				this.variableConfDetDao.delete(obj);
				break;

			case FIND_BY_ID:
				obj = this.variableConfDetDao.find(VariableConfDet.class, obj.getIdVariableConfDet());
				break;

			default:
				break;
		}
		return obj;
	}

	@Override
	public List<VariableConfDet> listarVariableConfDet(BaseSearch filtro) {
		return this.variableConfDetDao.listar(filtro);
	}

	@Override
	public int contarListarVariableConfDet(BaseSearch filtro) {
		return this.variableConfDetDao.contar(filtro);
	}

	@Override
	public InformaOtrosIngreso5ta controladorAccionInformaOtrosIngreso5ta(InformaOtrosIngreso5ta obj,
			AccionType accionType) {
		switch (accionType) {
			case CREAR:
				obj.setIdInformaOtrosIngreso5ta(this.informaOtrosIngreso5taDao.generarId());
				this.informaOtrosIngreso5taDao.save(obj);
				break;
			case MODIFICAR:
				this.informaOtrosIngreso5taDao.update(obj);
				break;

			case ELIMINAR:
				obj = this.informaOtrosIngreso5taDao.find(InformaOtrosIngreso5ta.class,
						obj.getIdInformaOtrosIngreso5ta());
				this.informaOtrosIngreso5taDao.delete(obj);
				break;

			case FIND_BY_ID:
				obj = this.informaOtrosIngreso5taDao.find(InformaOtrosIngreso5ta.class,
						obj.getIdInformaOtrosIngreso5ta());
				break;

			default:
				break;
		}

		return obj;
	}

	@Override
	public List<InformaOtrosIngreso5ta> listarInformaOtrosIngreso5ta(BaseSearch filtro) {
		return this.informaOtrosIngreso5taDao.listar(filtro);
	}

	@Override
	public int contarListarInformaOtrosIngreso5ta(BaseSearch filtro) {
		return this.informaOtrosIngreso5taDao.contar(filtro);
	}
}