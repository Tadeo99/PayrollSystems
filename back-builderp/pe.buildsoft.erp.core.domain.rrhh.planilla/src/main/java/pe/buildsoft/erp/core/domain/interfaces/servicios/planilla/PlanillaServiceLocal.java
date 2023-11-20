package pe.buildsoft.erp.core.domain.interfaces.servicios.planilla;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Local;
import pe.buildsoft.erp.core.domain.entidades.planilla.Adelanto;
import pe.buildsoft.erp.core.domain.entidades.planilla.ConceptoFijosTrabajador;
import pe.buildsoft.erp.core.domain.entidades.planilla.ConceptoPdt;
import pe.buildsoft.erp.core.domain.entidades.planilla.ConceptoRegimenPensionario;
import pe.buildsoft.erp.core.domain.entidades.planilla.ConceptosTipoPlanilla;
import pe.buildsoft.erp.core.domain.entidades.planilla.DetRentaQuintaCategoria;
import pe.buildsoft.erp.core.domain.entidades.planilla.DetallePersonalConcepto;
import pe.buildsoft.erp.core.domain.entidades.planilla.DetallePlanilla;
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
public interface PlanillaServiceLocal {
	/**
	 * Controlador accion concepto pdt.
	 *
	 * @param obj        el concepto pdt
	 * @param accionType el accion type
	 * @return the concepto pdt
	 * @throws Exception the exception
	 */
	ConceptoPdt controladorAccionConceptoPdt(ConceptoPdt obj, AccionType accionType);

	/**
	 * Listar concepto pdt.
	 *
	 * @param filtro el concepto pdt
	 * @return the list
	 * @throws Exception the exception
	 */
	List<ConceptoPdt> listarConceptoPdt(BaseSearch filtro);

	List<ConceptoPdt> obtenerFormulaConceptoPdt();

	/**
	 * contar lista concepto pdt.
	 *
	 * @param filtro el concepto pdt
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarConceptoPdt(BaseSearch filtro);

	/**
	 * Controlador accion adelanto.
	 *
	 * @param obj        el adelanto
	 * @param accionType el accion type
	 * @return the adelanto
	 * @throws Exception the exception
	 */
	Adelanto controladorAccionAdelanto(Adelanto obj, AccionType accionType);

	/**
	 * Listar adelanto.
	 *
	 * @param filtro el adelanto
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Adelanto> listarAdelanto(BaseSearch filtro);

	/**
	 * contar lista adelanto.
	 *
	 * @param filtro el adelanto
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarAdelanto(BaseSearch filtro);

	/**
	 * Controlador accion concepto trabajabador.
	 *
	 * @param obj        el concepto trabajabador
	 * @param accionType el accion type
	 * @return the concepto trabajabador
	 * @throws Exception the exception
	 */
	ConceptoFijosTrabajador controladorAccionConceptoFijosTrabajador(ConceptoFijosTrabajador obj,
			AccionType accionType);

	/**
	 * Listar concepto trabajabador.
	 *
	 * @param filtro el concepto trabajabador
	 * @return the list
	 * @throws Exception the exception
	 */
	List<ConceptoFijosTrabajador> listarConceptoFijosTrabajador(BaseSearch filtro);

	/**
	 * contar lista concepto trabajabador.
	 *
	 * @param filtro el concepto trabajabador
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarConceptoFijosTrajabador(BaseSearch filtro);

	/**
	 * Controlador accion conceptos tipo planilla.
	 *
	 * @param conceptosTipoPlanilla el conceptos tipo planilla
	 * @param accionType            el accion type
	 * @return the conceptos tipo planilla
	 * @throws Exception the exception
	 */
	ConceptosTipoPlanilla controladorAccionConceptosTipoPlanilla(ConceptosTipoPlanilla obj, AccionType accionType);

	/**
	 * Listar conceptos tipo planilla.
	 *
	 * @param filtro el conceptos tipo planilla
	 * @return the list
	 * @throws Exception the exception
	 */
	List<ConceptosTipoPlanilla> listarConceptosTipoPlanilla(BaseSearch filtro);

	// List<ConceptosTipoPlanilla> obtenerFormulaConceptosTipoPlanilla();

	/**
	 * contar lista conceptos tipo planilla.
	 *
	 * @param filtro el conceptos tipo planilla
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarConceptosTipoPlanilla(BaseSearch filtro);

	/**
	 * Controlador accion valores u i t.
	 *
	 * @param obj        el valores u i t
	 * @param accionType el accion type
	 * @return the valores u i t
	 * @throws Exception the exception
	 */
	ValoresUIT controladorAccionValoresUIT(ValoresUIT obj, AccionType accionType);

	/**
	 * Listar valores u i t.
	 *
	 * @param filtro el valores u i t
	 * @return the list
	 * @throws Exception the exception
	 */
	List<ValoresUIT> listarValoresUIT(BaseSearch filtro);

	/**
	 * contar lista valores u i t.
	 *
	 * @param filtro el valores u i t
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarValoresUIT(BaseSearch filtro);

	/**
	 * Controlador accion concepto regimen pensionario.
	 *
	 * @param conceptoRegimenPensionario el concepto regimen pensionario
	 * @param accionType                 el accion type
	 * @return the concepto regimen pensionario
	 * @throws Exception the exception
	 */
	List<ConceptoRegimenPensionario> agregarConceptoRegimenPensionario(List<ConceptoRegimenPensionario> listaObj);

	ConceptoRegimenPensionario controladorAccionConceptoRegimenPensionario(ConceptoRegimenPensionario obj,
			AccionType accionType);

	/**
	 * Listar concepto regimen pensionario.
	 *
	 * @param filtro el concepto regimen pensionario
	 * @return the list
	 * @throws Exception the exception
	 */
	List<ConceptoRegimenPensionario> listarConceptoRegimenPensionario(BaseSearch filtro);

	int contarListarConceptoRegimenPensionario(BaseSearch filtro);

	/**
	 * Controlador accion renta quinta categoria.
	 *
	 * @param obj        el renta quinta categoria
	 * @param accionType el accion type
	 * @return the renta quinta categoria
	 * @throws Exception the exception
	 */
	RentaQuintaCategoria controladorAccionRentaQuintaCategoria(RentaQuintaCategoria obj, AccionType accionType);

	/**
	 * Listar renta quinta categoria.
	 *
	 * @param filtro el renta quinta categoria
	 * @return the list
	 * @throws Exception the exception
	 */
	List<RentaQuintaCategoria> listarRentaQuintaCategoria(BaseSearch filtro);

	/**
	 * contar lista renta quinta categoria.
	 *
	 * @param filtro el renta quinta categoria
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarRentaQuintaCategoria(BaseSearch filtro);

	/**
	 * Listar detalle planilla.
	 *
	 * @param filtro el detalle planilla
	 * @return the list
	 * @throws Exception the exception
	 */
	List<DetallePlanilla> listarDetallePlanilla(BaseSearch filtro);

	/**
	 * contar lista detalle planilla.
	 *
	 * @param filtro el detalle planilla
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarDetallePlanilla(BaseSearch filtro);

	/**
	 * Controlador accion periodo planilla.
	 *
	 * @param obj        el periodo planilla
	 * @param accionType el accion type
	 * @return the periodo planilla
	 * @throws Exception the exception
	 */
	PeriodoPlanilla controladorAccionPeriodoPlanilla(PeriodoPlanilla obj, AccionType accionType);

	/**
	 * Listar periodo planilla.
	 *
	 * @param filtro el periodo planilla
	 * @return the list
	 * @throws Exception the exception
	 */
	List<PeriodoPlanilla> listarPeriodoPlanilla(BaseSearch filtro);

	/**
	 * contar lista periodo planilla.
	 *
	 * @param filtro el periodo planilla
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarPeriodoPlanilla(BaseSearch filtro);

	List<TareoPersonal> listarTareoPersonal(BaseSearch filtro);

	int contarListarTareoPersonal(BaseSearch filtro);

	TareoPersonal controladorAccionTareoPersonal(TareoPersonal obj, AccionType accionType);

	/**
	 * Controlador accion tipo planilla.
	 *
	 * @param obj        el tipo planilla
	 * @param accionType el accion type
	 * @return the tipo planilla
	 * @throws Exception the exception
	 */
	TipoPlanilla controladorAccionTipoPlanilla(TipoPlanilla obj, AccionType accionType);

	/**
	 * Listar tipo planilla.
	 *
	 * @param filtro el tipo planilla
	 * @return the list
	 * @throws Exception the exception
	 */
	List<TipoPlanilla> listarTipoPlanilla(BaseSearch filtro);

	/**
	 * contar lista tipo planilla.
	 *
	 * @param filtro el tipo planilla
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarTipoPlanilla(BaseSearch filtro);

	/**
	 * Controlador accion det renta quinta categoria.
	 *
	 * @param obj        el det renta quinta categoria
	 * @param accionType el accion type
	 * @return the det renta quinta categoria
	 * @throws Exception the exception
	 */
	DetRentaQuintaCategoria controladorAccionDetRentaQuintaCategoria(DetRentaQuintaCategoria obj,
			AccionType accionType);

	/**
	 * Listar det renta quinta categoria.
	 *
	 * @param filtro el det renta quinta categoria
	 * @return the list
	 * @throws Exception the exception
	 */
	List<DetRentaQuintaCategoria> listarDetRentaQuintaCategoria(BaseSearch filtro);

	/**
	 * contar lista det renta quinta categoria.
	 *
	 * @param filtro el det renta quinta categoria
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarDetRentaQuintaCategoria(BaseSearch filtro);

	ValoresUIT findValorUIT(ValoresUIT valoresUIT);

	ConceptoFijosTrabajador findConceptoTrabajador(ConceptoFijosTrabajador filtro);

	PeriodoPlanilla findPeriodoPlanilla(PeriodoPlanilla filtro);

	ConceptoPdt findConceptoPdt(ConceptoPdt filtro);

	List<PersonalConcepto> listarPersonalConcepto(BaseSearch filtro) throws Exception;

	int contarListarPersonalConcepto(BaseSearch filtro);

	List<DetallePersonalConcepto> obtenerDetallePersonalConcepto(String idTipoPlanilla, String idPersonal,
			String idPeriodoPlanilla);

	Map<String, DetallePersonalConcepto> listarDetallePersonalConceptoMap(String idPersonal, String idTipoPlanilla,
			String idPeriodo);

	void registrarPersonalConcepto(List<PersonalConcepto> listaObj);

	PersonalConcepto controladorAccionPersonalConcepto(PersonalConcepto obj, AccionType accionType);

	/**
	 * Controlador accion eps conf.
	 *
	 * @param obj        el eps conf
	 * @param accionType el accion type
	 * @return the tipo planilla @ the exception
	 */
	EPSConf controladorAccionEPSConf(EPSConf obj, AccionType accionType);

	/**
	 * Listar eps conf.
	 *
	 * @param filtro el eps conf
	 * @return the list @ the exception
	 */
	List<EPSConf> listarEPSConf(BaseSearch filtro);

	/**
	 * contar lista eps conf.
	 *
	 * @param filtro el eps conf
	 * @return the list @ the exception
	 */
	int contarListarEPSConf(BaseSearch filtro);

	List<EPSPersonal> listarEPSPersonal(BaseSearch filtro) throws Exception;

	int contarListarEPSPersonal(BaseSearch filtro);

	EPSPersonal controladorAccionEPSPersonal(EPSPersonal obj, AccionType accionType);

	/**
	 * Controlador accion vacaciones.
	 *
	 * @param obj        el vacaciones
	 * @param accionType el accion type
	 * @return the vacaciones
	 * @throws Exception the exception
	 */
	Vacaciones controladorAccionVacaciones(Vacaciones obj, AccionType accionType);

	/**
	 * Listar vaciones.
	 *
	 * @param filtro el vacaciones
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Vacaciones> listarVacaciones(BaseSearch filtro);

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
	 * @param obj        el vacaciones
	 * @param accionType el accion type
	 * @return the vacaciones
	 * @throws Exception the exception
	 */
	Feriado controladorAccionFeriado(Feriado obj, AccionType accionType);

	/**
	 * Listar feriado.
	 *
	 * @param filtro el feriado
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Feriado> listarFeriado(BaseSearch filtro);

	/**
	 * contar lista feriado.
	 *
	 * @param filtro el feriado
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarFeriado(BaseSearch filtro);

	List<SelectItemVO> listarVariableAplicacion();

	/**
	 * Controlador accion variable conf.
	 *
	 * @param obj        el variable conf
	 * @param accionType el accion type
	 * @return the variable conf
	 * @throws Exception the exception
	 */
	VariableConf controladorAccionVariableConf(VariableConf obj, AccionType accionType);

	/**
	 * Listar variable conf.
	 *
	 * @param filtro el variable conf
	 * @return the list
	 * @throws Exception the exception
	 */
	List<VariableConf> listarVariableConf(BaseSearch filtro);

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
	VariableConfDet controladorAccionVariableConfDet(VariableConfDet obj, AccionType accionType);

	/**
	 * Listar variable conf det.
	 *
	 * @param filtro el variable conf det
	 * @return the list
	 * @throws Exception the exception
	 */
	List<VariableConfDet> listarVariableConfDet(BaseSearch filtro);

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
	 * @param informaOtrosIngreso5ta el informa otros ingreso5ta
	 * @param accionType             el accion type
	 * @return the informa otros ingreso5ta
	 * @throws Exception the exception
	 */
	InformaOtrosIngreso5ta controladorAccionInformaOtrosIngreso5ta(InformaOtrosIngreso5ta obj, AccionType accionType);

	/**
	 * Listar informa otros ingreso5ta.
	 *
	 * @param informaOtrosIngreso5ta el informa otros ingreso5ta
	 * @return the list
	 * @throws Exception the exception
	 */
	List<InformaOtrosIngreso5ta> listarInformaOtrosIngreso5ta(BaseSearch filtro);

	/**
	 * contar lista informa otros ingreso5ta.
	 *
	 * @param informaOtrosIngreso5ta el informa otros ingreso5ta
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarInformaOtrosIngreso5ta(BaseSearch filtro);
}