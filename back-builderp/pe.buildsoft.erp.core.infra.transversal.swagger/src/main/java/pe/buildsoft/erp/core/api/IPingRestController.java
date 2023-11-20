package pe.buildsoft.erp.core.api;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
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

@Tag(name = "/ping", description = "Swagger Service")
@OpenAPIDefinition(info = @Info(title = "Servicios de Swagger", version = "0.0", description = "Swagger Service"), tags = {
		@Tag(name = "Swagger Service", description = "Servicios de Swagger") })
@Path("/ping")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public interface IPingRestController {

	@GET
	@Operation(summary = "Verificar Ping", description = "Verificar el estado del servicio")
	@APIResponse(description = "Resultado ping")
	@APIResponse(responseCode = "400", description = "Ping not found")
	Response paginador(@Context HttpHeaders headers, @Context UriInfo info);
}