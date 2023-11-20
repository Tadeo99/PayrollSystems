package pe.buildsoft.erp.core.api.controller.common;

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
import pe.buildsoft.erp.core.application.entidades.common.ListaValoresDTO;

/**
 * La Class ListaValoresRestController.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue Apr 18 13:43:39 COT 2017
 * @since SIAA-CORE 2.1
 */
@Tag(name = "/listaValores", description = "Common Service")
@OpenAPIDefinition(info = @Info(title = "Servicios de Common", version = "0.0", description = "Common Service"),tags = { @Tag(name = "Common Service", description = "Servicios de Common") })
@Path("/listaValores")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface IListaValoresRestController {

	@POST
	Response crear(ListaValoresDTO obj);

	@PUT
	@Path("/{id}")
	Response modificar(ListaValoresDTO obj);

	@GET
	@Path("/{id}")
	Response eliminar(@PathParam("id") Long id);

	@GET
	@Path("/{id}")
	Response finById(@PathParam("id") Long id);

	@GET
	Response paginador(@Context HttpHeaders headers, @Context UriInfo info);
}