package pe.buildsoft.erp.core.domain.interfaces.servicios.escalafon;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import jakarta.ejb.Local;
import pe.buildsoft.erp.core.domain.entidades.escalafon.AsociarCentroCosto;
import pe.buildsoft.erp.core.domain.entidades.escalafon.Beneficiarios;
import pe.buildsoft.erp.core.domain.entidades.escalafon.Carrera;
import pe.buildsoft.erp.core.domain.entidades.escalafon.CentroCosto;
import pe.buildsoft.erp.core.domain.entidades.escalafon.Contrato;
import pe.buildsoft.erp.core.domain.entidades.escalafon.CuentaBancariaPersonal;
import pe.buildsoft.erp.core.domain.entidades.escalafon.DetalleContrado;
import pe.buildsoft.erp.core.domain.entidades.escalafon.DireccionPersonal;
import pe.buildsoft.erp.core.domain.entidades.escalafon.HistorialBasico;
import pe.buildsoft.erp.core.domain.entidades.escalafon.HistorialCargoArea;
import pe.buildsoft.erp.core.domain.entidades.escalafon.Institucion;
import pe.buildsoft.erp.core.domain.entidades.escalafon.PeriodoLaboraPersonal;
import pe.buildsoft.erp.core.domain.entidades.escalafon.Personal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
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
public interface EscalafonServiceLocal {
	/**
	 * Controlador accion historial cargo area.
	 *
	 * @param historialCargoArea el historial cargo area
	 * @param accionType         el accion type
	 * @return the historial cargo area
	 * @throws Exception the exception
	 */
	HistorialCargoArea controladorAccionHistorialCargoArea(HistorialCargoArea obj, AccionType accionType);

	/**
	 * Listar historial cargo area. ======= Controlador accion asociar centro costo.
	 *
	 * @param asociarCentroCosto el asociar centro costo
	 * @param accionType         el accion type
	 * @return the asociar centro costo
	 * @throws Exception the exception
	 */
	AsociarCentroCosto controladorAccionAsociarCentroCosto(AsociarCentroCosto obj, AccionType accionType);

	/**
	 * Listar asociar centro costo.
	 *
	 * @param asociarCentroCosto el asociar centro costo
	 * @return the list
	 * @throws Exception the exception
	 */
	List<AsociarCentroCosto> listarAsociarCentroCosto(BaseSearch filtro);

	/**
	 * contar lista asociar centro costo.
	 *
	 * @param asociarCentroCosto el asociar centro costo
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarAsociarCentroCosto(BaseSearch filtro);

	/**
	 * Listar asociar fecha ingreso empresa.
	 *
	 * @param historialCargoArea el historial cargo area
	 * @return the list
	 * @throws Exception the exception
	 */
	List<HistorialCargoArea> listarHistorialCargoArea(BaseSearch filtro);

	/**
	 * contar lista historial cargo area.
	 *
	 * @param historialCargoArea el historial cargo area
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarHistorialCargoArea(BaseSearch filtro);

	/**
	 * Controlador accion centro costo.
	 *
	 * @param centroCosto el centro costo
	 * @param accionType  el accion type
	 * @return the centro costo
	 * @throws Exception the exception
	 */
	CentroCosto controladorAccionCentroCosto(CentroCosto obj, AccionType accionType);

	/**
	 * Listar centro costo.
	 *
	 * @param centroCosto el centro costo
	 * @return the list
	 * @throws Exception the exception
	 */
	List<CentroCosto> listarCentroCosto(BaseSearch filtro);

	/**
	 * contar lista centro costo.
	 *
	 * @param centroCosto el centro costo
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
	Personal controladorAccionPersonal(Personal obj, AccionType accionType) throws IOException;

	/**
	 * Listar personal.
	 *
	 * @param personal el personal
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Personal> listarPersonal(BaseSearch filtro);
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
	DetalleContrado controladorAccionDetalleContrado(DetalleContrado obj, AccionType accionType);

	/**
	 * Listar detalle contrado.
	 *
	 * @param detalleContrado el detalle contrado
	 * @return the list
	 * @throws Exception the exception
	 */
	List<DetalleContrado> listarDetalleContrado(BaseSearch filtro);

	/**
	 * contar lista detalle contrado.
	 *
	 * @param detalleContrado el detalle contrado
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarDetalleContrado(BaseSearch filtro);

	/**
	 * Controlador accion contrato.
	 *
	 * @param contrato   el contrato
	 * @param accionType el accion type
	 * @return the contrato
	 * @throws Exception the exception
	 */
	Contrato controladorAccionContrato(Contrato obj, AccionType accionType);

	/**
	 * Listar contrato.
	 *
	 * @param contrato el contrato
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Contrato> listarContrato(BaseSearch filtro);

	/**
	 * contar lista contrato.
	 *
	 * @param contrato el contrato
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarContrato(BaseSearch filtro);

	/**
	 * Controlador accion cuenta bancaria personal.
	 *
	 * @param cuentaBancariaPersonal el cuenta bancaria personal
	 * @param accionType             el accion type
	 * @return the cuenta bancaria personal
	 * @throws Exception the exception
	 */
	CuentaBancariaPersonal controladorAccionCuentaBancariaPersonal(CuentaBancariaPersonal obj, AccionType accionType);

	/**
	 * Listar cuenta bancaria personal.
	 *
	 * @param cuentaBancariaPersonal el cuenta bancaria personal
	 * @return the list
	 * @throws Exception the exception
	 */
	List<CuentaBancariaPersonal> listarCuentaBancariaPersonal(BaseSearch filtro);

	/**
	 * contar lista cuenta bancaria personal.
	 *
	 * @param cuentaBancariaPersonal el cuenta bancaria personal
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarCuentaBancariaPersonal(BaseSearch filtro);

	/**
	 * Controlador accion historial basico.
	 *
	 * @param historialBasico el historial basico
	 * @param accionType      el accion type
	 * @return the historial basico
	 * @throws Exception the exception
	 */
	HistorialBasico controladorAccionHistorialBasico(HistorialBasico obj, AccionType accionType);

	/**
	 * Listar historial basico.
	 *
	 * @param historialBasico el historial basico
	 * @return the list
	 * @throws Exception the exception
	 */
	List<HistorialBasico> listarHistorialBasico(BaseSearch filtro);

	/**
	 * contar lista historial basico.
	 *
	 * @param historialBasico el historial basico
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarHistorialBasico(BaseSearch filtro);

	/**
	 * Controlador accion carrera.
	 *
	 * @param carrera    el carrera
	 * @param accionType el accion type
	 * @return the carrera
	 * @throws Exception the exception
	 */
	Carrera controladorAccionCarrera(Carrera obj, AccionType accionType);

	/**
	 * Listar carrera.
	 *
	 * @param carrera el carrera
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Carrera> listarCarrera(BaseSearch filtro);

	/**
	 * contar lista carrera.
	 *
	 * @param carrera el carrera
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarCarrera(BaseSearch filtro);

	/**
	 * Controlador accion institucion.
	 *
	 * @param institucion el institucion
	 * @param accionType  el accion type
	 * @return the institucion
	 * @throws Exception the exception
	 */
	Institucion controladorAccionInstitucion(Institucion obj, AccionType accionType);

	/**
	 * Listar institucion.
	 *
	 * @param institucion el institucion
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Institucion> listarInstitucion(BaseSearch filtro);

	/**
	 * contar lista institucion.
	 *
	 * @param institucion el institucion
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarInstitucion(BaseSearch filtro);

	/**
	 * Controlador accion direccion personal.
	 *
	 * @param direccionPersonal el direccion personal
	 * @param accionType        el accion type
	 * @return the direccion personal
	 * @throws Exception the exception
	 */
	DireccionPersonal controladorAccionDireccionPersonal(DireccionPersonal direccionPersonal, AccionType accionType);

	/**
	 * Listar direccion personal.
	 *
	 * @param filtro el direccion personal
	 * @return the list
	 * @throws Exception the exception
	 */
	List<DireccionPersonal> listarDireccionPersonal(BaseSearch filtro);

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
	 * @param beneficiarios el beneficiarios
	 * @param accionType    el accion type
	 * @return the beneficiarios
	 * @throws Exception the exception
	 */
	Beneficiarios controladorAccionBeneficiarios(Beneficiarios obj, AccionType accionType);

	/**
	 * Listar beneficiarios.
	 *
	 * @param beneficiarios el beneficiarios
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Beneficiarios> listarBeneficiarios(BaseSearch filtro);

	/**
	 * contar lista beneficiarios.
	 *
	 * @param beneficiarios el beneficiarios
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarBeneficiarios(BaseSearch filtro);

	/**
	 * Controlador accion periodo labora personal.
	 *
	 * @param periodoLaboraPersonal el periodo labora personal
	 * @param accionType            el accion type
	 * @return the periodo labora personal
	 * @throws Exception the exception
	 */
	PeriodoLaboraPersonal controladorAccionPeriodoLaboraPersonal(PeriodoLaboraPersonal obj, AccionType accionType);

	/**
	 * Listar periodo labora personal.
	 *
	 * @param periodoLaboraPersonal el periodo labora personal
	 * @return the list
	 * @throws Exception the exception
	 */
	List<PeriodoLaboraPersonal> listarPeriodoLaboraPersonal(BaseSearch filtro);

	/**
	 * contar lista periodo labora personal.
	 *
	 * @param periodoLaboraPersonal el periodo labora personal
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarPeriodoLaboraPersonal(BaseSearch filtro);

	Personal findPersonal(Personal filtro);

	Carrera findCarrera(Carrera filtro);

	CentroCosto findCentroCosto(CentroCosto filtro);
	
	Map<String, BigDecimal> obtenerBasicoPersonalMap(Long idCategoriaTrabajador);
}