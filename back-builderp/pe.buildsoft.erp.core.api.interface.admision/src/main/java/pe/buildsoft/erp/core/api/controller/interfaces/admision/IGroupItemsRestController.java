package pe.buildsoft.erp.core.api.controller.interfaces.admision;

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
 * La Class TipoUsuarioRestController.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Dec 14 00:27:45 COT 2017
 * @since SIAA-CORE 2.1
 */
@Tag(name = "/group/items", description = "Admision Service")
@OpenAPIDefinition(info = @Info(title = "Servicios de Admision", version = "0.0", description = "Security Admision"), tags = {
		@Tag(name = "Admision Service", description = "Servicios de Admision") })
@Path("/group/items")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface IGroupItemsRestController {

	@GET
	Response paginador(@Context HttpHeaders headers, @Context UriInfo info);
}