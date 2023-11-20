package pe.buildsoft.erp.core.application.interfaces.pago;

import java.util.List;

import jakarta.ejb.Local;
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
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.SelectItemVO;
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
public interface PagoAppServiceLocal {

	List<SelectItemVO> listarSelectItem(String groupName);

	/**
	 * Controlador accion cuota concepto.
	 *
	 * @param obj        el cuota concepto
	 * @param accionType el accion type
	 * @return the cuota concepto
	 * @throws Exception the exception
	 */
	CuotaConceptoDTO controladorAccionCuotaConcepto(CuotaConceptoDTO obj, AccionType accionType);

	/**
	 * Listar cuota concepto.
	 *
	 * @param filtro el cuota concepto
	 * @return the list
	 * @throws Exception the exception
	 */
	List<CuotaConceptoDTO> listarCuotaConcepto(BaseSearch filtro);

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
	CuentaBancariaEntidadDTO controladorAccionCuentaBancariaEntidad(CuentaBancariaEntidadDTO obj,
			AccionType accionType);

	/**
	 * Listar cuenta bancaria entidad.
	 *
	 * @param filtro el cuenta bancaria entidad
	 * @return the list
	 * @throws Exception the exception
	 */
	List<CuentaBancariaEntidadDTO> listarCuentaBancariaEntidad(BaseSearch filtro);

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
	ClasificacionDTO controladorAccionClasificacion(ClasificacionDTO obj, AccionType accionType);

	/**
	 * Listar clasificacion.
	 *
	 * @param filtro el clasificacion
	 * @return the list
	 * @throws Exception the exception
	 */
	List<ClasificacionDTO> listarClasificacion(BaseSearch filtro);

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
	String registrarPago(ControlPagoDTO obj);

	/**
	 * Listar control pago.
	 *
	 * @param filtro el control pago
	 * @return the list
	 * @throws Exception the exception
	 */
	List<ControlPagoDTO> listarControlPago(BaseSearch filtro);

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
	CatalogoCuentaDTO controladorAccionCatalogoCuenta(CatalogoCuentaDTO obj, AccionType accionType);

	/**
	 * Listar catalogo cuenta.
	 *
	 * @param filtro el catalogo cuenta
	 * @return the list
	 * @throws Exception the exception
	 */
	List<CatalogoCuentaDTO> listarCatalogoCuenta(BaseSearch filtro);

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
	EmpresaDTO controladorAccionEmpresa(EmpresaDTO obj, AccionType accionType);

	/**
	 * Listar empresa.
	 *
	 * @param filtro el empresa
	 * @return the list
	 * @throws Exception the exception
	 */
	List<EmpresaDTO> listarEmpresa(BaseSearch filtro);

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
	TipoDocSunatEntidadDTO controladorAccionTipoDocSunatEntidad(TipoDocSunatEntidadDTO obj, AccionType accionType);

	/**
	 * Listar tipo doc sunat entidad.
	 *
	 * @param filtro el tipo doc sunat entidad
	 * @return the list
	 * @throws Exception the exception
	 */
	List<TipoDocSunatEntidadDTO> listarTipoDocSunatEntidad(BaseSearch filtro);

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
	void registrarPlanPago(PlanPagosDTO obj);

	/**
	 * Listar plan pagos.
	 *
	 * @param filtro el plan pagos
	 * @return the list
	 * @throws Exception the exception
	 */
	PlanPagosDTO obtenerPlanPagosByIdAlumno(PlanPagosDTO filtro);

	/**
	 * Listar det plan pagos.
	 *
	 * @param filtro el det plan pagos
	 * @return the list
	 * @throws Exception the exception
	 */
	List<DetPlanPagosDTO> listarDetPlanPagos(BaseSearch filtro);

	/**
	 * contar lista det plan pagos.
	 *
	 * @param filtro el det plan pagos
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarDetPlanPagos(BaseSearch filtro);

	List<DetPlanPagosDTO> listarConceptoPagoAlumnoSemestre(Long idAnho, Long idPeriodo, String idAlumno,
			boolean flagFaltaMontoResta);

	List<DetControlPagoDTO> obtenerConceptoPagoAlumnoSemestre(Long idAnho, Long idPeriodo, String idAlumno,
			boolean flagFaltaMontoResta);

	List<TipoDocSunatEntidadDTO> listarTipoDocByItem(TipoDocSunatEntidadDTO filtro);

	TipoDocSunatEntidadDTO findTipoDocSunatEntidad(TipoDocSunatEntidadDTO filtro);

	CatalogoCuentaDTO findCatalogoCuenta(CatalogoCuentaDTO filtro);

	ClasificacionDTO findClasificacion(ClasificacionDTO filtro);

	EmpresaDTO findEmpresa(EmpresaDTO filtro);

	DetPlanPagosDTO findDetPlanPagos(DetPlanPagosDTO filtro);
}