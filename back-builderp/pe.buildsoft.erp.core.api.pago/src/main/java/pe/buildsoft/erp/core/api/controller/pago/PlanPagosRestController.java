package pe.buildsoft.erp.core.api.controller.pago;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.buildsoft.erp.core.application.entidades.pago.PlanPagosDTO;
import pe.buildsoft.erp.core.application.interfaces.pago.PagoAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.cache.ICache;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * La Class PlanPagosRestController.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 21:45:31 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Path("/planPagos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PlanPagosRestController extends GenericServiceRestImpl implements IPlanPagosRestController {

	@Inject
	private PagoAppServiceLocal servicioApp;

	@Inject
	private ICache cacheUtil;

	@POST
	public Response crear(@Context HttpHeaders httpHeaders, PlanPagosDTO obj) {
		RespuestaWSVO<PlanPagosDTO> resultado = new RespuestaWSVO<>();
		if (!StringUtil.isNotNullOrBlank(obj.getIdPlanPagos())) {
			obj.setUsuarioCreacion(cacheUtil.getUserName(getToken(httpHeaders)));
		} else {
			obj.setUsuarioModificacion(cacheUtil.getUserName(getToken(httpHeaders)));
		}
		try {
			servicioApp.registrarPlanPago(obj);
			resultado.setObjetoResultado(obj);
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, AccionType.CREAR);
	}

	@POST
	@Path("/obtenerPlanPagosByIdAlumno")
	public Response listarPlanPagos(PlanPagosDTO planPagos) {
		RespuestaWSVO<PlanPagosDTO> resultado = new RespuestaWSVO<>();
		try {
			resultado.setObjetoResultado(servicioApp.obtenerPlanPagosByIdAlumno(planPagos));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null);
	}

}