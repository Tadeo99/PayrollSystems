package pe.buildsoft.erp.core.application.interfaces.escalafon;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import jakarta.ejb.Local;
import pe.buildsoft.erp.core.application.entidades.escalafon.AsociarCentroCostoDTO;
import pe.buildsoft.erp.core.application.entidades.escalafon.BeneficiariosDTO;
import pe.buildsoft.erp.core.application.entidades.escalafon.CarreraDTO;
import pe.buildsoft.erp.core.application.entidades.escalafon.CentroCostoDTO;
import pe.buildsoft.erp.core.application.entidades.escalafon.ContratoDTO;
import pe.buildsoft.erp.core.application.entidades.escalafon.CuentaBancariaPersonalDTO;
import pe.buildsoft.erp.core.application.entidades.escalafon.DetalleContradoDTO;
import pe.buildsoft.erp.core.application.entidades.escalafon.DireccionPersonalDTO;
import pe.buildsoft.erp.core.application.entidades.escalafon.HistorialBasicoDTO;
import pe.buildsoft.erp.core.application.entidades.escalafon.HistorialCargoAreaDTO;
import pe.buildsoft.erp.core.application.entidades.escalafon.InstitucionDTO;
import pe.buildsoft.erp.core.application.entidades.escalafon.PeriodoLaboraPersonalDTO;
import pe.buildsoft.erp.core.application.entidades.escalafon.PersonalDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.SelectItemVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;

/**
 * La Class EscalafonServiceLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:10 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface EscalafonAppServiceLocal {

	List<SelectItemVO> listarSelectItem(String groupName, BaseSearch filtro);

	int contarSelectItem(String groupName, BaseSearch filtro);

	/**
	 * Controlador accion historial cargo area.
	 *
	 * @param obj        el historial cargo area
	 * @param accionType el accion type
	 * @return the historial cargo area
	 * @throws Exception the exception
	 */
	HistorialCargoAreaDTO controladorAccionHistorialCargoArea(HistorialCargoAreaDTO obj, AccionType accionType);

	/**
	 * Listar historial cargo area. Controlador accion asociar centro costo.
	 *
	 * @param obj        el asociar centro costo
	 * @param accionType el accion type
	 * @return the asociar centro costo
	 * @throws Exception the exception
	 */
	AsociarCentroCostoDTO controladorAccionAsociarCentroCosto(AsociarCentroCostoDTO obj, AccionType accionType);

	/**
	 * Listar asociar centro costo.
	 *
	 * @param filtro el asociar centro costo
	 * @return the list
	 * @throws Exception the exception
	 */
	List<AsociarCentroCostoDTO> listarAsociarCentroCosto(BaseSearch filtro);

	/**
	 * contar lista asociar centro costo.
	 *
	 * @param filtro el asociar centro costo
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarAsociarCentroCosto(BaseSearch filtro);

	/**
	 * Listar asociar fecha ingreso empresa.
	 *
	 * @param filtro el historial cargo area
	 * @return the list
	 * @throws Exception the exception
	 */
	List<HistorialCargoAreaDTO> listarHistorialCargoArea(BaseSearch filtro);

	/**
	 * contar lista historial cargo area.
	 *
	 * @param filtro el historial cargo area
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarHistorialCargoArea(BaseSearch filtro);

	/**
	 * Controlador accion centro costo.
	 *
	 * @param obj        el centro costo
	 * @param accionType el accion type
	 * @return the centro costo
	 * @throws Exception the exception
	 */
	CentroCostoDTO controladorAccionCentroCosto(CentroCostoDTO obj, AccionType accionType);

	/**
	 * Listar centro costo.
	 *
	 * @param filtro el centro costo
	 * @return the list
	 * @throws Exception the exception
	 */
	List<CentroCostoDTO> listarCentroCosto(BaseSearch filtro);

	/**
	 * contar lista centro costo.
	 *
	 * @param filtro el centro costo
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarCentroCosto(BaseSearch filtro);

	/**
	 * Controlador accion personal.
	 *
	 * @param personal   el personal
	 * @param accionType el accion type
	 * @return the personal
	 * @throws Exception the exception
	 */
	PersonalDTO controladorAccionPersonal(PersonalDTO obj, AccionType accionType) throws IOException;

	/**
	 * Listar personal.
	 *
	 * @param personal el personal
	 * @return the list
	 * @throws Exception the exception
	 */
	List<PersonalDTO> listarPersonal(BaseSearch filtro);

	List<String> listarPersonalIds(BaseSearch filtro);

	/**
	 * contar lista personal.
	 *
	 * @param personal el personal
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarPersonal(BaseSearch filtro);

	/**
	 * Controlador accion detalle contrado.
	 *
	 * @param detalleContrado el detalle contrado
	 * @param accionType      el accion type
	 * @return the detalle contrado
	 * @throws Exception the exception
	 */
	DetalleContradoDTO controladorAccionDetalleContrado(DetalleContradoDTO detalleContrado, AccionType accionType);

	/**
	 * Listar detalle contrado.
	 *
	 * @param filtro el detalle contrado
	 * @return the list
	 * @throws Exception the exception
	 */
	List<DetalleContradoDTO> listarDetalleContrado(BaseSearch filtro);

	/**
	 * contar lista detalle contrado.
	 *
	 * @param filtro el detalle contrado
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarDetalleContrado(BaseSearch filtro);

	/**
	 * Controlador accion contrato.
	 *
	 * @param obj        el contrato
	 * @param accionType el accion type
	 * @return the contrato
	 * @throws Exception the exception
	 */
	ContratoDTO controladorAccionContrato(ContratoDTO obj, AccionType accionType);

	/**
	 * Listar contrato.
	 *
	 * @param filtro el contrato
	 * @return the list
	 * @throws Exception the exception
	 */
	List<ContratoDTO> listarContrato(BaseSearch filtro);

	/**
	 * contar lista contrato.
	 *
	 * @param filtro el contrato
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarContrato(BaseSearch filtro);

	/**
	 * Controlador accion cuenta bancaria personal.
	 *
	 * @param obj        el cuenta bancaria personal
	 * @param accionType el accion type
	 * @return the cuenta bancaria personal
	 * @throws Exception the exception
	 */
	CuentaBancariaPersonalDTO controladorAccionCuentaBancariaPersonal(CuentaBancariaPersonalDTO obj,
			AccionType accionType);

	/**
	 * Listar cuenta bancaria personal.
	 *
	 * @param filtro el cuenta bancaria personal
	 * @return the list
	 * @throws Exception the exception
	 */
	List<CuentaBancariaPersonalDTO> listarCuentaBancariaPersonal(BaseSearch filtro);

	/**
	 * contar lista cuenta bancaria personal.
	 *
	 * @param filtro el cuenta bancaria personal
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarCuentaBancariaPersonal(BaseSearch filtro);

	/**
	 * Controlador accion historial basico.
	 *
	 * @param obj        el historial basico
	 * @param accionType el accion type
	 * @return the historial basico
	 * @throws Exception the exception
	 */
	HistorialBasicoDTO controladorAccionHistorialBasico(HistorialBasicoDTO obj, AccionType accionType);

	/**
	 * Listar historial basico.
	 *
	 * @param filtro el historial basico
	 * @return the list
	 * @throws Exception the exception
	 */
	List<HistorialBasicoDTO> listarHistorialBasico(BaseSearch filtro);

	/**
	 * contar lista historial basico.
	 *
	 * @param filtro el historial basico
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarHistorialBasico(BaseSearch filtro);

	/**
	 * Controlador accion carrera.
	 *
	 * @param obj        el carrera
	 * @param accionType el accion type
	 * @return the carrera
	 * @throws Exception the exception
	 */
	CarreraDTO controladorAccionCarrera(CarreraDTO obj, AccionType accionType);

	/**
	 * Listar carrera.
	 *
	 * @param filtro el carrera
	 * @return the list
	 * @throws Exception the exception
	 */
	List<CarreraDTO> listarCarrera(BaseSearch filtro);

	/**
	 * contar lista carrera.
	 *
	 * @param filtro el carrera
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarCarrera(BaseSearch filtro);

	/**
	 * Controlador accion institucion.
	 *
	 * @param obj        el institucion
	 * @param accionType el accion type
	 * @return the institucion
	 * @throws Exception the exception
	 */
	InstitucionDTO controladorAccionInstitucion(InstitucionDTO obj, AccionType accionType);

	/**
	 * Listar institucion.
	 *
	 * @param filtro el institucion
	 * @return the list
	 * @throws Exception the exception
	 */
	List<InstitucionDTO> listarInstitucion(BaseSearch filtro);

	/**
	 * contar lista institucion.
	 *
	 * @param filtro el institucion
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarInstitucion(BaseSearch filtro);

	/**
	 * Controlador accion direccion personal.
	 *
	 * @param obj        el direccion personal
	 * @param accionType el accion type
	 * @return the direccion personal
	 * @throws Exception the exception
	 */
	DireccionPersonalDTO controladorAccionDireccionPersonal(DireccionPersonalDTO obj, AccionType accionType);

	/**
	 * Listar direccion personal.
	 *
	 * @param filtro el direccion personal
	 * @return the list
	 * @throws Exception the exception
	 */
	List<DireccionPersonalDTO> listarDireccionPersonal(BaseSearch filtro);

	/**
	 * contar lista direccion personal.
	 *
	 * @param filtro el direccion personal
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarDireccionPersonal(BaseSearch filtro);

	/**
	 * Controlador accion beneficiarios.
	 *
	 * @param obj        el beneficiarios
	 * @param accionType el accion type
	 * @return the beneficiarios
	 * @throws Exception the exception
	 */
	BeneficiariosDTO controladorAccionBeneficiarios(BeneficiariosDTO obj, AccionType accionType);

	/**
	 * Listar beneficiarios.
	 *
	 * @param filtro el beneficiarios
	 * @return the list
	 * @throws Exception the exception
	 */
	List<BeneficiariosDTO> listarBeneficiarios(BaseSearch filtro);

	/**
	 * contar lista beneficiarios.
	 *
	 * @param filtro el beneficiarios
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarBeneficiarios(BaseSearch filtro);

	/**
	 * Controlador accion periodo labora personal.
	 *
	 * @param obj        el periodo labora personal
	 * @param accionType el accion type
	 * @return the periodo labora personal
	 * @throws Exception the exception
	 */
	PeriodoLaboraPersonalDTO controladorAccionPeriodoLaboraPersonal(PeriodoLaboraPersonalDTO obj,
			AccionType accionType);

	/**
	 * Listar periodo labora personal.
	 *
	 * @param filtro el periodo labora personal
	 * @return the list
	 * @throws Exception the exception
	 */
	List<PeriodoLaboraPersonalDTO> listarPeriodoLaboraPersonal(BaseSearch filtro);

	/**
	 * contar lista periodo labora personal.
	 *
	 * @param filtro el periodo labora personal
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarPeriodoLaboraPersonal(BaseSearch filtro);

	PersonalDTO findPersonal(PersonalDTO filtro);

	CarreraDTO findCarrera(CarreraDTO filtro);

	CentroCostoDTO findCentroCosto(CentroCostoDTO filtro);

	Map<String, BigDecimal> obtenerBasicoPersonalMap(Long idCategoriaTrabajador);
}