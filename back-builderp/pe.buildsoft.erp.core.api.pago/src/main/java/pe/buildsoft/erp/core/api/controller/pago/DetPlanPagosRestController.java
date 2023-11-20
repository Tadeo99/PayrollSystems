package pe.buildsoft.erp.core.api.controller.pago;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import pe.buildsoft.erp.core.application.entidades.pago.DetPlanPagosDTO;
import pe.buildsoft.erp.core.application.entidades.pago.PlanPagosDTO;
import pe.buildsoft.erp.core.application.interfaces.pago.PagoAppServiceLocal;
import pe.buildsoft.erp.core.domain.entidades.pago.DetPlanPagos;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;

/**
 * La Class DetPlanPagosRestController.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 21:45:33 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Path("/detPlanPagos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DetPlanPagosRestController extends GenericServiceRestImpl implements IDetPlanPagosRestController {

	private Logger log = LoggerFactory.getLogger(DetPlanPagosRestController.class);

	@Inject
	private PagoAppServiceLocal servicioApp;

	// TODO:REVISIAR METODO FALTA
	@DELETE
	@Path("/{id}")
	public Response eliminar(@PathParam("id") String id) {
		RespuestaWSVO<String> resultado = new RespuestaWSVO<>();
		try {
			DetPlanPagos obj = new DetPlanPagos();
			obj.setIdDetPlanPagos(id);
			// FsservicioApp.registrarDetallePlanPagos(obj, null, AccionType.ELIMINAR);
		} catch (Exception e) {
			// parsearResultadoError(e, resultado);
			resultado.setError(true);
			resultado.setMensajeError("No se puede eliminar ==> Detalle Plan tiene pagos ! ");
		}
		return respuesta(resultado, AccionType.ELIMINAR);
	}

	@GET
	public Response paginador(@Context HttpHeaders headers, @Context UriInfo info) {
		RespuestaWSVO<DetPlanPagosDTO> resultado = new RespuestaWSVO<>();
		try {
			var filtro = to(info);
			if (filtro.getStartRow() == 0 || resultado.getContador() == 0)
				resultado.setContador(servicioApp.contarListarDetPlanPagos(filtro));
			if (resultado.isData() || filtro.getStartRow() > 0) {
				resultado.setListaResultado(servicioApp.listarDetPlanPagos(filtro));
			}
		} catch (Exception e) {
			log.error("paginador", e);
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null, info);
	}

	@POST
	@Path("/find")
	public Response find(PlanPagosDTO filtro) {
		RespuestaWSVO<DetPlanPagosDTO> resultado = new RespuestaWSVO<>();
		try {
			DetPlanPagosDTO obj = filtro.getPlanPagosDetPlanPagosList().get(0);
			obj.setPlanPagos(filtro);
			resultado.setObjetoResultado(servicioApp.findDetPlanPagos(obj));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null);
	}

}