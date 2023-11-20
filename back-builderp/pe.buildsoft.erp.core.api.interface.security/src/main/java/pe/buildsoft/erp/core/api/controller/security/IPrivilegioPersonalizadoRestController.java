package pe.buildsoft.erp.core.api.controller.security;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import pe.buildsoft.erp.core.application.entidades.security.PrivilegioDTO;

/**
 * La Class PrivilegioPersonalizadoRestController.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Mon Oct 16 14:31:02 COT 2017
 * @since SIAA-CORE 2.1
 */
@Tag(name = "/privilegioPersonalizado", description = "Security Service")
@OpenAPIDefinition(info = @Info(title = "Servicios de Security", version = "0.0", description = "Security Service"), tags = {
		@Tag(name = "Security Service", description = "Servicios de Security") })
@Path("/privilegioPersonalizado")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface IPrivilegioPersonalizadoRestController {

	@POST
	Response asociarPrivilegioPersonalizado(@Context HttpHeaders httpHeaders, List<PrivilegioDTO> listaPrivilegio);

	@GET
	Response paginador(@Context HttpHeaders headers, @Context UriInfo info);
}