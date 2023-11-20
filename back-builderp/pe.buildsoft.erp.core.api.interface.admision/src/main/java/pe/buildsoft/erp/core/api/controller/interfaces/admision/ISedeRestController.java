package pe.buildsoft.erp.core.api.controller.interfaces.admision;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
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
import pe.buildsoft.erp.core.application.entidades.admision.SedeDTO;

/**
 * La Class SedeRestController.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Wed Oct 21 20:17:20 COT 2020
 * @since BUILDERP-CORE 2.1
 */
@Tag(name = "/sede", description = "Admision Service")
@OpenAPIDefinition(info = @Info(title = "Servicios de Admision", version = "0.0", description = "Admision Service"), tags = {
		@Tag(name = "Admision Service", description = "Servicios de Admision") })
@Path("/sede")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ISedeRestController {

	@POST
	Response crear(SedeDTO obj);

	@PUT
	@Path("/{id}")
	Response modificar(SedeDTO obj);

	@DELETE
	@Path("/{id}")
	Response eliminar(@PathParam("id") String id);

	@GET
	@Path("/{id}")
	Response finById(@PathParam("id") String id);

	@GET
	Response paginador(@Context HttpHeaders headers, @Context UriInfo info);

	@GET
	@Path("/vacantesDisponibles/{idUbigeo}")
	Response vacantesDisponibles(@PathParam("idUbigeo") String idUbigeo);

}