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
 * La Class AsignaPostulanteRestController.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Wed Oct 21 20:17:19 COT 2020
 * @since BUILDERP-CORE 2.1
 */
@Tag(name = "/asignaPostulante", description = "Admision Service")
@OpenAPIDefinition(info = @Info(title = "Servicios de Admision", version = "0.0", description = "Admision Service"), tags = {
		@Tag(name = "Admision Service", description = "Servicios de Admision") })
@Path("/asignaPostulante")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface IAsignaPostulanteRestController {

	@GET
	Response paginador(@Context HttpHeaders headers, @Context UriInfo info);
}