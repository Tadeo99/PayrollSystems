package pe.buildsoft.erp.core.api.controller.planilla;

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
import pe.buildsoft.erp.core.application.entidades.planilla.ConceptosTipoPlanillaDTO;

/**
 * La Class ConceptosTipoPlanillaRestController.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Tag(name = "/tipoPlanilla", description = "Planilla Service")
@OpenAPIDefinition(info = @Info(title = "Servicios de Planilla", version = "0.0", description = "Planilla Service"), tags = {
		@Tag(name = "Planilla Service", description = "Servicios de Planilla") })
@Path("/tipoPlanilla")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface IConceptosTipoPlanillaRestController {
	@POST
	@Path("/{idTipoPlanilla}/conceptos")
	Response crear(@PathParam("idTipoPlanilla") String idTipoPlanilla, ConceptosTipoPlanillaDTO obj);

	@PUT
	@Path("/{idTipoPlanilla}/conceptos/{id}")
	Response modificar(@PathParam("idTipoPlanilla") String idTipoPlanilla, ConceptosTipoPlanillaDTO obj);

	@DELETE
	@Path("/{idTipoPlanilla}/conceptos/{id}")
	Response eliminar(@PathParam("idTipoPlanilla") String idTipoPlanilla, @PathParam("id") String id);

	@GET
	@Path("/{idTipoPlanilla}/conceptos/{id}")
	Response finById(@PathParam("idTipoPlanilla") String idTipoPlanilla, @PathParam("id") String id);

	@GET
	@Path("/{idTipoPlanilla}/conceptos")
	Response paginador(@PathParam("idTipoPlanilla") String idTipoPlanilla, @Context HttpHeaders headers,
			@Context UriInfo info);
}