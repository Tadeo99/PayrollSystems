package pe.buildsoft.erp.core.api.controller.hora;

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
import pe.buildsoft.erp.core.application.entidades.hora.RequerimientoDTO;

/**
 * La Class RegistroCabeceraRestController.
 * <ul>
 * <li>Copyright 2021 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Thu Feb 24 15:16:44 COT 2022
 * @since BuildErp 1.0registroCabeceraRestController
 */
@Tag(name = "/requerimiento", description = "Hora Service")
@OpenAPIDefinition(info = @Info(title = "Servicios de Hora", version = "0.0", description = "Hora Service"),tags = { @Tag(name = "Hora Service", description = "Servicios de Hora") })
@Path("/requerimiento")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface IRequerimientoRestController {

	@POST
	Response crear(RequerimientoDTO obj);

	@PUT
	@Path("/{id}")
	Response modificar(RequerimientoDTO obj);

	@GET
	@Path("/{id}")
	Response eliminar(@PathParam("id") String id);

	@GET
	Response paginador(@Context HttpHeaders headers, @Context UriInfo info);

}