package pe.buildsoft.erp.core.api.controller.security;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import pe.buildsoft.erp.core.application.entidades.security.MenuDTO;

/**
 * La Class MenuRestController.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Mon Oct 16 14:31:01 COT 2017
 * @since SIAA-CORE 2.1
 */

@Tag(name = "/menu", description = "Security Service")
@OpenAPIDefinition(info = @Info(title = "Servicios de Security", version = "0.0", description = "Security Service"), tags = {
		@Tag(name = "Security Service", description = "Servicios de Security") })
@Path("/menu")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface IMenuRestController {

	@POST
	Response crear(@Context HttpHeaders httpHeaders, MenuDTO obj);

	@PUT
	@Path("/{id}")
	Response modificar(@PathParam("id") Long id, @Context HttpHeaders httpHeaders, MenuDTO obj);

	@GET
	@Path("/{id}")
	Response eliminar(@Context HttpHeaders httpHeaders, @PathParam("id") Long id);

	@GET
	@Path("/{id}")
	Response finById(@PathParam("id") Long id);

	@GET
	Response paginador(@Context HttpHeaders headers, @Context UriInfo info);
}