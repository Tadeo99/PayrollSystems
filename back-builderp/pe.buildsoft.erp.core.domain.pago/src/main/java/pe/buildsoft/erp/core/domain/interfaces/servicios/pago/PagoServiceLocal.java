package pe.buildsoft.erp.core.domain.interfaces.servicios.pago;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.pago.CatalogoCuenta;
import pe.buildsoft.erp.core.domain.entidades.pago.Clasificacion;
import pe.buildsoft.erp.core.domain.entidades.pago.ControlPago;
import pe.buildsoft.erp.core.domain.entidades.pago.CuentaBancariaEntidad;
import pe.buildsoft.erp.core.domain.entidades.pago.CuotaConcepto;
import pe.buildsoft.erp.core.domain.entidades.pago.DetPlanPagos;
import pe.buildsoft.erp.core.domain.entidades.pago.Empresa;
import pe.buildsoft.erp.core.domain.entidades.pago.PlanPagos;
import pe.buildsoft.erp.core.domain.entidades.pago.TipoDocSunatEntidad;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;

/**
 * La Class PagoServiceLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 21:45:13 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface PagoServiceLocal {

	/**
	 * Controlador accion cuota concepto.
	 *
	 * @param obj        el cuota concepto
	 * @param accionType el accion type
	 * @return the cuota concepto
	 * @throws Exception the exception
	 */
	CuotaConcepto controladorAccionCuotaConcepto(CuotaConcepto obj, AccionType accionType);

	/**
	 * Listar cuota concepto.
	 *
	 * @param filtro el cuota concepto
	 * @return the list
	 * @throws Exception the exception
	 */
	List<CuotaConcepto> listarCuotaConcepto(BaseSearch filtro);

	/**
	 * contar lista cuota concepto.
	 *
	 * @param filtro el cuota concepto
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarCuotaConcepto(BaseSearch filtro);

	/**
	 * Controlador accion cuenta bancaria entidad.
	 *
	 * @param obj        el cuenta bancaria entidad
	 * @param accionType el accion type
	 * @return the cuenta bancaria entidad
	 * @throws Exception the exception
	 */
	CuentaBancariaEntidad controladorAccionCuentaBancariaEntidad(CuentaBancariaEntidad obj, AccionType accionType);

	/**
	 * Listar cuenta bancaria entidad.
	 *
	 * @param filtro el cuenta bancaria entidad
	 * @return the list
	 * @throws Exception the exception
	 */
	List<CuentaBancariaEntidad> listarCuentaBancariaEntidad(BaseSearch filtro);

	/**
	 * contar lista cuenta bancaria entidad.
	 *
	 * @param filtro el cuenta bancaria entidad
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarCuentaBancariaEntidad(BaseSearch filtro);

	/**
	 * Controlador accion clasificacion.
	 *
	 * @param obj        el clasificacion
	 * @param accionType el accion type
	 * @return the clasificacion
	 * @throws Exception the exception
	 */
	Clasificacion controladorAccionClasificacion(Clasificacion obj, AccionType accionType);

	/**
	 * Listar clasificacion.
	 *
	 * @param filtro el clasificacion
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Clasificacion> listarClasificacion(BaseSearch filtro);

	/**
	 * contar lista clasificacion.
	 *
	 * @param filtro el clasificacion
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarClasificacion(BaseSearch filtro);

	/**
	 * Controlador accion control pago.
	 *
	 * @param obj        el control pago
	 * @param accionType el accion type
	 * @return the control pago
	 * @throws Exception the exception
	 */
	String registrarPago(ControlPago obj);

	/**
	 * Listar control pago.
	 *
	 * @param filtro el control pago
	 * @return the list
	 * @throws Exception the exception
	 */
	List<ControlPago> listarControlPago(BaseSearch filtro);

	/**
	 * contar lista control pago.
	 *
	 * @param filtro el control pago
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarControlPago(BaseSearch filtro);

	/**
	 * Controlador accion catalogo cuenta.
	 *
	 * @param obj        el catalogo cuenta
	 * @param accionType el accion type
	 * @return the catalogo cuenta
	 * @throws Exception the exception
	 */
	CatalogoCuenta controladorAccionCatalogoCuenta(CatalogoCuenta obj, AccionType accionType);

	/**
	 * Listar catalogo cuenta.
	 *
	 * @param filtro el catalogo cuenta
	 * @return the list
	 * @throws Exception the exception
	 */
	List<CatalogoCuenta> listarCatalogoCuenta(BaseSearch filtro);

	/**
	 * contar lista catalogo cuenta.
	 *
	 * @param filtro el catalogo cuenta
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarCatalogoCuenta(BaseSearch filtro);

	/**
	 * Controlador accion empresa.
	 *
	 * @param obj        el empresa
	 * @param accionType el accion type
	 * @return the empresa
	 * @throws Exception the exception
	 */
	Empresa controladorAccionEmpresa(Empresa obj, AccionType accionType);

	/**
	 * Listar empresa.
	 *
	 * @param filtro el empresa
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Empresa> listarEmpresa(BaseSearch filtro);

	/**
	 * contar lista empresa.
	 *
	 * @param filtro el empresa
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarEmpresa(BaseSearch filtro);

	/**
	 * Controlador accion tipo doc sunat entidad.
	 *
	 * @param obj        el tipo doc sunat entidad
	 * @param accionType el accion type
	 * @return the tipo doc sunat entidad
	 * @throws Exception the exception
	 */
	TipoDocSunatEntidad controladorAccionTipoDocSunatEntidad(TipoDocSunatEntidad obj, AccionType accionType);

	/**
	 * Listar tipo doc sunat entidad.
	 *
	 * @param filtro el tipo doc sunat entidad
	 * @return the list
	 * @throws Exception the exception
	 */
	List<TipoDocSunatEntidad> listarTipoDocSunatEntidad(BaseSearch filtro);

	/**
	 * contar lista tipo doc sunat entidad.
	 *
	 * @param filtro el tipo doc sunat entidad
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarTipoDocSunatEntidad(BaseSearch filtro);

	/**
	 * Controlador accion plan pagos.
	 *
	 * @param obj        el plan pagos
	 * @param accionType el accion type
	 * @return the plan pagos
	 * @throws Exception the exception
	 */
	void registrarPlanPago(PlanPagos obj);

	/**
	 * Listar plan pagos.
	 *
	 * @param filtro el plan pagos
	 * @return the list
	 * @throws Exception the exception
	 */
	PlanPagos obtenerPlanPagosByIdAlumno(PlanPagos filtro);

	DetPlanPagos registrarDetallePlanPagos(DetPlanPagos obj, PlanPagos planPagos, AccionType accionType);

	/**
	 * Listar det plan pagos.
	 *
	 * @param filtro el det plan pagos
	 * @return the list
	 * @throws Exception the exception
	 */
	List<DetPlanPagos> listarDetPlanPagos(BaseSearch filtro);

	/**
	 * contar lista det plan pagos.
	 *
	 * @param filtro el det plan pagos
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarDetPlanPagos(BaseSearch filtro);

	List<DetPlanPagos> listarConceptoPagoAlumnoSemestre(Long idAnho, Long idPeriodo, String idAlumno,
			boolean flagFaltaMontoResta);

	List<TipoDocSunatEntidad> listarTipoDocByItem(TipoDocSunatEntidad filtro);

	TipoDocSunatEntidad findTipoDocSunatEntidad(TipoDocSunatEntidad filtro);

	CatalogoCuenta findCatalogoCuenta(CatalogoCuenta filtro);

	Clasificacion findClasificacion(Clasificacion filtro);

	Empresa findEmpresa(Empresa filtro);

	DetPlanPagos findDetPlanPagos(DetPlanPagos filtro);
}