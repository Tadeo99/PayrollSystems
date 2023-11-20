package pe.buildsoft.erp.core.api.controller.planilla;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import pe.buildsoft.erp.core.application.entidades.aas.vo.NavigationItemVO;
import pe.buildsoft.erp.core.application.interfaces.planilla.PlanillaAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;

/**
 * La Class ConfigVariableAplicacionRestController.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Path("/tipoPlanilla")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class VariableAplicacionRestController extends GenericServiceRestImpl
		implements IVariableAplicacionRestController {

	private Logger log = LoggerFactory.getLogger(VariableAplicacionRestController.class);

	@Inject
	private PlanillaAppServiceLocal servicioApp;

	@GET
	@Path("/{idTipoPlanilla}/variables/aplicacion")
	public Response paginador(@PathParam("idTipoPlanilla") String idTipoPlanilla, @Context HttpHeaders headers,
			@Context UriInfo info) {
		var resultado = new RespuestaWSVO<NavigationItemVO>();
		try {
			var filtro = to(info);
			filtro.setAuthToken(getToken(headers));
			filtro.setId(idTipoPlanilla);
			resultado.setListaResultado(servicioApp.listarVariableAplicacion(filtro));
		} catch (Exception e) {
			log.error("paginador", e);
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null, info);
	}

}