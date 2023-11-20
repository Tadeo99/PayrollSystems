package pe.buildsoft.erp.core.api.controller.escalafon;

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
import pe.buildsoft.erp.core.application.entidades.escalafon.CentroCostoDTO;

@Tag(name = "/centroCosto", description = "Escalafon Service")
@OpenAPIDefinition(info = @Info(title = "Servicios de Escalafon", version = "0.0", description = "Escalafon Service"),tags = { @Tag(name = "Escalafon Service", description = "Servicios de Escalafon") })
@Path("/centroCosto")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ICentroCostoRestController {

	@POST
	Response crear(CentroCostoDTO obj);

	@PUT
	@Path("/{id}")
	Response modificar(CentroCostoDTO obj);

	@DELETE
	@Path("/{id}")
	Response eliminar(@PathParam("id") String id);

	@GET
	@Path("/{id}")
	Response finById(@PathParam("id") String id);

	@GET
	Response paginador(@Context HttpHeaders headers, @Context UriInfo info);

	// TODO:VER NATAN NO USAR ESTO
	@POST
	@Path("/findCentroCosto")
	Response findPersonal(CentroCostoDTO filtro);
}
