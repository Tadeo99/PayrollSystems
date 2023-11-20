package pe.buildsoft.erp.core.api.controller.pago;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

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
import pe.buildsoft.erp.core.application.entidades.pago.PlanPagosDTO;

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
@Tag(name = "/detPlanPagos", description = "Pago Service")
@OpenAPIDefinition(info = @Info(title = "Servicios de Pago", version = "0.0", description = "Pago Service"),tags = { @Tag(name = "Pago Service", description = "Servicios de Pago") })
@Path("/detPlanPagos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface IDetPlanPagosRestController {

	// TODO:REVISIAR METODO FALTA
	@DELETE
	@Path("/{id}")
	Response eliminar(@PathParam("id") String id);

	@GET
	Response paginador(@Context HttpHeaders headers, @Context UriInfo info);

	@POST
	@Path("/find")
	Response find(PlanPagosDTO filtro);

}