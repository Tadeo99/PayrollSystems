package pe.buildsoft.erp.core.api.controller.pago;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

/**
 * La Class IGroupItemsRestController.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Dec 14 00:27:45 COT 2017
 * @since SIAA-CORE 2.1
 */
@Tag(name = "/group", description = "Pago Service")
@OpenAPIDefinition(info = @Info(title = "Servicios de Pago", version = "0.0", description = "Pago Service"), tags = {
		@Tag(name = "Pago Service", description = "Servicios de Pago") })
@Path("/group")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface IGroupItemsRestController {

	@GET
	@Path("/items")
	Response paginador(@Context HttpHeaders headers, @Context UriInfo info);
}