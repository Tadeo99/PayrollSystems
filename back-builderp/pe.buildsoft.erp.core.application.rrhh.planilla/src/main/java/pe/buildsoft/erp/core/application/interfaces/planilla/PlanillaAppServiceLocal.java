package pe.buildsoft.erp.core.application.interfaces.planilla;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Local;
import pe.buildsoft.erp.core.application.entidades.aas.vo.NavigationItemVO;
import pe.buildsoft.erp.core.application.entidades.planilla.AdelantoDTO;
import pe.buildsoft.erp.core.application.entidades.planilla.ConceptoFijosTrabajadorDTO;
import pe.buildsoft.erp.core.application.entidades.planilla.ConceptoPdtDTO;
import pe.buildsoft.erp.core.application.entidades.planilla.ConceptoRegimenPensionarioDTO;
import pe.buildsoft.erp.core.application.entidades.planilla.ConceptosTipoPlanillaDTO;
import pe.buildsoft.erp.core.application.entidades.planilla.DetRentaQuintaCategoriaDTO;
import pe.buildsoft.erp.core.application.entidades.planilla.DetallePersonalConceptoDTO;
import pe.buildsoft.erp.core.application.entidades.planilla.DetallePlanillaDTO;
import pe.buildsoft.erp.core.application.entidades.planilla.EPSConfDTO;
import pe.buildsoft.erp.core.application.entidades.planilla.EPSPersonalDTO;
import pe.buildsoft.erp.core.application.entidades.planilla.FeriadoDTO;
import pe.buildsoft.erp.core.application.entidades.planilla.InformaOtrosIngreso5taDTO;
import pe.buildsoft.erp.core.application.entidades.planilla.PeriodoPlanillaDTO;
import pe.buildsoft.erp.core.application.entidades.planilla.PersonalConceptoDTO;
import pe.buildsoft.erp.core.application.entidades.planilla.RentaQuintaCategoriaDTO;
import pe.buildsoft.erp.core.application.entidades.planilla.TareoPersonalDTO;
import pe.buildsoft.erp.core.application.entidades.planilla.TipoPlanillaDTO;
import pe.buildsoft.erp.core.application.entidades.planilla.VacacionesDTO;
import pe.buildsoft.erp.core.application.entidades.planilla.ValoresUITDTO;
import pe.buildsoft.erp.core.application.entidades.planilla.VariableConfDTO;
import pe.buildsoft.erp.core.application.entidades.planilla.VariableConfDetDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.SelectItemVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;

/**
 * La Class PlanillaServiceLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:10 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface PlanillaAppServiceLocal {

	List<SelectItemVO> listarSelectItem(String groupName, BaseSearch filtro);

	/**
	 * Controlador accion concepto pdt.
	 *
	 * @param obj        el concepto pdt
	 * @param accionType el accion type
	 * @return the concepto pdt @ the exception
	 */
	ConceptoPdtDTO controladorAccionConceptoPdt(ConceptoPdtDTO obj, AccionType accionType);

	/**
	 * Listar concepto pdt.
	 *
	 * @param filtro el concepto pdt
	 * @return the list @ the exception
	 */
	List<ConceptoPdtDTO> listarConceptoPdt(BaseSearch filtro);

	List<ConceptoPdtDTO> obtenerFormulaConceptoPdt();

	/**
	 * contar lista concepto pdt.
	 *
	 * @param filtro el concepto pdt
	 * @return the list @ the exception
	 */
	int contarListarConceptoPdt(BaseSearch filtro);

	/**
	 * Controlador accion adelanto.
	 *
	 * @param obj        el adelanto
	 * @param accionType el accion type
	 * @return the adelanto @ the exception
	 */
	AdelantoDTO controladorAccionAdelanto(AdelantoDTO obj, AccionType accionType);

	/**
	 * Listar adelanto.
	 *
	 * @param filtro el adelanto
	 * @return the list @ the exception
	 */
	List<AdelantoDTO> listarAdelanto(BaseSearch filtro);

	/**
	 * contar lista adelanto.
	 *
	 * @param filtro el adelanto
	 * @return the list @ the exception
	 */
	int contarListarAdelanto(BaseSearch filtro);

	/**
	 * Controlador accion concepto trabajabador.
	 *
	 * @param obj        el concepto trabajabador
	 * @param accionType el accion type
	 * @return the concepto trabajabador @ the exception
	 */
	ConceptoFijosTrabajadorDTO controladorAccionConceptoFijosTrabajador(ConceptoFijosTrabajadorDTO obj,
			AccionType accionType);

	/**
	 * Listar concepto trabajabador.
	 *
	 * @param filtro el concepto trabajabador
	 * @return the list @ the exception
	 */
	List<ConceptoFijosTrabajadorDTO> listarConceptoFijosTrabajador(BaseSearch filtro);

	/**
	 * contar lista concepto trabajabador.
	 *
	 * @param filtro el concepto trabajabador
	 * @return the list @ the exception
	 */
	int contarListarConceptoFijosTrajabador(BaseSearch filtro);

	ConceptosTipoPlanillaDTO controladorAccionConceptosTipoPlanilla(ConceptosTipoPlanillaDTO obj,
			AccionType accionType);

	/**
	 * Listar conceptos tipo planilla.
	 *
	 * @param filtro el conceptos tipo planilla
	 * @return the list @ the exception
	 */
	List<ConceptosTipoPlanillaDTO> listarConceptosTipoPlanilla(BaseSearch filtro);

	// List<ConceptosTipoPlanillaDTO> obtenerFormulaConceptosTipoPlanilla();

	/**
	 * contar lista conceptos tipo planilla.
	 *
	 * @param filtro el conceptos tipo planilla
	 * @return the list @ the exception
	 */
	int contarListarConceptosTipoPlanilla(BaseSearch filtro);

	/**
	 * Controlador accion valores u i t.
	 *
	 * @param filtro     el valores u i t
	 * @param accionType el accion type
	 * @return the valores u i t @ the exception
	 */
	ValoresUITDTO controladorAccionValoresUIT(ValoresUITDTO obj, AccionType accionType);

	/**
	 * Listar valores u i t.
	 *
	 * @param filtro el valores u i t
	 * @return the list @ the exception
	 */
	List<ValoresUITDTO> listarValoresUIT(BaseSearch filtro);

	/**
	 * contar lista valores u i t.
	 *
	 * @param filtro el valores u i t
	 * @return the list @ the exception
	 */
	int contarListarValoresUIT(BaseSearch filtro);

	/**
	 * Controlador accion concepto regimen pensionario.
	 *
	 * @param conceptoRegimenPensionario el concepto regimen pensionario
	 * @param accionType                 el accion type
	 * @return the concepto regimen pensionario @ the exception
	 */
	List<ConceptoRegimenPensionarioDTO> agregarConceptoRegimenPensionario(List<ConceptoRegimenPensionarioDTO> listaObj);

	ConceptoRegimenPensionarioDTO controladorAccionConceptoRegimenPensionario(ConceptoRegimenPensionarioDTO obj,
			AccionType accionType);

	/**
	 * Listar concepto regimen pensionario.
	 *
	 * @param filtro el concepto regimen pensionario
	 * @return the list @ the exception
	 */
	List<ConceptoRegimenPensionarioDTO> listarConceptoRegimenPensionario(BaseSearch filtro);

	int contarListarConceptoRegimenPensionario(BaseSearch filtro);

	/**
	 * Controlador accion renta quinta categoria.
	 *
	 * @param obj        el renta quinta categoria
	 * @param accionType el accion type
	 * @return the renta quinta categoria @ the exception
	 */
	RentaQuintaCategoriaDTO controladorAccionRentaQuintaCategoria(RentaQuintaCategoriaDTO obj, AccionType accionType);

	/**
	 * Listar renta quinta categoria.
	 *
	 * @param filtro el renta quinta categoria
	 * @return the list @ the exception
	 */
	List<RentaQuintaCategoriaDTO> listarRentaQuintaCategoria(BaseSearch filtro);

	/**
	 * contar lista renta quinta categoria.
	 *
	 * @param filtro el renta quinta categoria
	 * @return the list @ the exception
	 */
	int contarListarRentaQuintaCategoria(BaseSearch filtro);

	/**
	 * Listar detalle planilla.
	 *
	 * @param filtro el detalle planilla
	 * @return the list @ the exception
	 */
	List<DetallePlanillaDTO> listarDetallePlanilla(BaseSearch filtro) throws Exception;

	/**
	 * contar lista detalle planilla.
	 *
	 * @param filtro el detalle planilla
	 * @return the list @ the exception
	 */
	int contarListarDetallePlanilla(BaseSearch filtro) throws Exception;

	/**
	 * Controlador accion periodo planilla.
	 *
	 * @param obj        el periodo planilla
	 * @param accionType el accion type
	 * @return the periodo planilla @ the exception
	 */
	PeriodoPlanillaDTO controladorAccionPeriodoPlanilla(PeriodoPlanillaDTO obj, AccionType accionType);

	/**
	 * Listar periodo planilla.
	 *
	 * @param filtro el periodo planilla
	 * @return the list @ the exception
	 */
	List<PeriodoPlanillaDTO> listarPeriodoPlanilla(BaseSearch filtro);

	/**
	 * contar lista periodo planilla.
	 *
	 * @param filtro el periodo planilla
	 * @return the list @ the exception
	 */
	int contarListarPeriodoPlanilla(BaseSearch filtro);

	List<TareoPersonalDTO> listarTareoPersonal(BaseSearch filtro) throws Exception;

	int contarListarTareoPersonal(BaseSearch filtro);

	TareoPersonalDTO controladorAccionTareoPersonal(TareoPersonalDTO obj, AccionType accionType);

	/**
	 * Controlador accion tipo planilla.
	 *
	 * @param obj        el tipo planilla
	 * @param accionType el accion type
	 * @return the tipo planilla @ the exception
	 */
	TipoPlanillaDTO controladorAccionTipoPlanilla(TipoPlanillaDTO obj, AccionType accionType);

	/**
	 * Listar tipo planilla.
	 *
	 * @param filtro el tipo planilla
	 * @return the list @ the exception
	 */
	List<TipoPlanillaDTO> listarTipoPlanilla(BaseSearch filtro);

	/**
	 * contar lista tipo planilla.
	 *
	 * @param filtro el tipo planilla
	 * @return the list @ the exception
	 */
	int contarListarTipoPlanilla(BaseSearch filtro);

	/**
	 * Controlador accion det renta quinta categoria.
	 *
	 * @param obj        el det renta quinta categoria
	 * @param accionType el accion type
	 * @return the det renta quinta categoria @ the exception
	 */
	DetRentaQuintaCategoriaDTO controladorAccionDetRentaQuintaCategoria(DetRentaQuintaCategoriaDTO obj,
			AccionType accionType);

	/**
	 * Listar det renta quinta categoria.
	 *
	 * @param filtro el det renta quinta categoria
	 * @return the list @ the exception
	 */
	List<DetRentaQuintaCategoriaDTO> listarDetRentaQuintaCategoria(BaseSearch filtro);

	/**
	 * contar lista det renta quinta categoria.
	 *
	 * @param filtro el det renta quinta categoria
	 * @return the list @ the exception
	 */
	int contarListarDetRentaQuintaCategoria(BaseSearch filtro);

	ValoresUITDTO findValorUIT(ValoresUITDTO filtro);

	ConceptoFijosTrabajadorDTO findConceptoFijosTrabajador(ConceptoFijosTrabajadorDTO filtro);

	PeriodoPlanillaDTO findPeriodoPlanilla(PeriodoPlanillaDTO filtro);

	ConceptoPdtDTO findConceptoPdt(ConceptoPdtDTO filtro);

	List<PersonalConceptoDTO> listarPersonalConcepto(BaseSearch filtro) throws Exception;

	int contarListarPersonalConcepto(BaseSearch filtro);

	Map<String, DetallePersonalConceptoDTO> listarDetallePersonalConceptoMap(String idPersonal, String idTipoPlanilla,
			String idPeriodo);

	void registrarPersonalConcepto(List<PersonalConceptoDTO> listaObj);

	PersonalConceptoDTO controladorAccionPersonalConcepto(PersonalConceptoDTO obj, AccionType accionType);

	/**
	 * Controlador accion eps conf.
	 *
	 * @param obj        el eps conf
	 * @param accionType el accion type
	 * @return the tipo planilla @ the exception
	 */
	EPSConfDTO controladorAccionEPSConf(EPSConfDTO obj, AccionType accionType);

	/**
	 * Listar eps conf.
	 *
	 * @param filtro el eps conf
	 * @return the list @ the exception
	 */
	List<EPSConfDTO> listarEPSConf(BaseSearch filtro);

	/**
	 * contar lista eps conf.
	 *
	 * @param filtro el eps conf
	 * @return the list @ the exception
	 */
	int contarListarEPSConf(BaseSearch filtro);

	List<EPSPersonalDTO> listarEPSPersonal(BaseSearch filtro) throws Exception;

	int contarListarEPSPersonal(BaseSearch filtro);

	EPSPersonalDTO controladorAccionEPSPersonal(EPSPersonalDTO obj, AccionType accionType);

	/**
	 * Controlador accion vacaciones.
	 *
	 * @param obj        el vacaciones
	 * @param accionType el accion type
	 * @return the vacaciones
	 * @throws Exception the exception
	 */
	VacacionesDTO controladorAccionVacaciones(VacacionesDTO obj, AccionType accionType);

	/**
	 * Listar vaciones.
	 *
	 * @param filtro el vacaciones
	 * @return the list
	 * @throws Exception the exception
	 */
	List<VacacionesDTO> listarVacaciones(BaseSearch filtro) throws Exception;

	/**
	 * contar lista vacaciones.
	 *
	 * @param filtro el vacaciones
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarVacaciones(BaseSearch filtro);

	/**
	 * Controlador accion feriado.
	 *
	 * @param obj        el feriado
	 * @param accionType el accion type
	 * @return the vacaciones
	 * @throws Exception the exception
	 */
	FeriadoDTO controladorAccionFeriado(FeriadoDTO obj, AccionType accionType);

	/**
	 * Listar feriado.
	 *
	 * @param filtro el feriado
	 * @return the list
	 * @throws Exception the exception
	 */
	List<FeriadoDTO> listarFeriado(BaseSearch filtro) throws Exception;

	/**
	 * contar lista feriado.
	 *
	 * @param filtro el feriado
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarFeriado(BaseSearch filtro);

	List<NavigationItemVO> listarVariableAplicacion(BaseSearch filtro) throws Exception;

	/**
	 * Controlador accion variable conf.
	 *
	 * @param obj        el variable conf
	 * @param accionType el accion type
	 * @return the variable conf
	 * @throws Exception the exception
	 */
	VariableConfDTO controladorAccionVariableConf(VariableConfDTO obj, AccionType accionType);

	/**
	 * Listar variable conf.
	 *
	 * @param filtro el variable conf
	 * @return the list
	 * @throws Exception the exception
	 */
	List<VariableConfDTO> listarVariableConf(BaseSearch filtro);

	/**
	 * contar lista variable conf.
	 *
	 * @param filtro el variable conf
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarVariableConf(BaseSearch filtro);

	/**
	 * Controlador accion variable conf det.
	 *
	 * @param obj        el variable conf det
	 * @param accionType el accion type
	 * @return the variable conf det
	 * @throws Exception the exception
	 */
	VariableConfDetDTO controladorAccionVariableConfDet(VariableConfDetDTO obj, AccionType accionType);

	/**
	 * Listar variable conf det.
	 *
	 * @param filtro el variable conf det
	 * @return the list
	 * @throws Exception the exception
	 */
	List<VariableConfDetDTO> listarVariableConfDet(BaseSearch filtro);

	/**
	 * contar lista variable conf det.
	 *
	 * @param filtro el variable conf det
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarVariableConfDet(BaseSearch filtro);

	/**
	 * Controlador accion informa otros ingreso5ta.
	 *
	 * @param obj        el informa otros ingreso5ta
	 * @param accionType el accion type
	 * @return the informa otros ingreso5ta
	 * @throws Exception the exception
	 */
	InformaOtrosIngreso5taDTO controladorAccionInformaOtrosIngreso5ta(InformaOtrosIngreso5taDTO obj,
			AccionType accionType);

	/**
	 * Listar informa otros ingreso5ta.
	 *
	 * @param filtro el informa otros ingreso5ta
	 * @return the list
	 * @throws Exception the exception
	 */
	List<InformaOtrosIngreso5taDTO> listarInformaOtrosIngreso5ta(BaseSearch filtro);

	/**
	 * contar lista informa otros ingreso5ta.
	 *
	 * @param filtro el informa otros ingreso5ta
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarInformaOtrosIngreso5ta(BaseSearch filtro);
}