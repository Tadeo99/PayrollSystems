package pe.buildsoft.erp.core.api.controller.interfaces.aas;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.Operation;
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
 * La Class EntidadRestController.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Dec 14 00:27:42 COT 2017
 * @since SIAA-CORE 2.1
 */

@Tag(name = "/entidad", description = "Security Service")
@OpenAPIDefinition(info = @Info(title = "Servicios de AAS", version = "0.0", description = "AAS Service"), tags = {
		@Tag(name = "AAS Service", description = "Servicios de AAS") })
@Path("/entidad")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public interface IEntidadRestController {

	@GET
	@Operation(summary = "Finds Pets by status",
    description = "Multiple status values can be provided with comma separated strings")
	Response paginador(@Context HttpHeaders headers, @Context UriInfo info);
}