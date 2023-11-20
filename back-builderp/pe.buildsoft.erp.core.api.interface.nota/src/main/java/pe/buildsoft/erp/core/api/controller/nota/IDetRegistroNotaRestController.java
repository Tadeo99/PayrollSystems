package pe.buildsoft.erp.core.api.controller.nota;

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
import pe.buildsoft.erp.core.application.entidades.nota.DetRegistroNotaDTO;

/**
 * La Class DetRegistroNotaRestController.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Jun 10 00:13:46 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Tag(name = "/detRegistroNota", description = "Nota Service")
@OpenAPIDefinition(info = @Info(title = "Servicios de Nota", version = "0.0", description = "Nota Service"),tags = { @Tag(name = "Nota Service", description = "Servicios de Nota") })
@Path("/detRegistroNota")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface IDetRegistroNotaRestController {

	@POST
	Response crear(DetRegistroNotaDTO detRegistroNota);

	@PUT
	@Path("/{id}")
	Response modificar(DetRegistroNotaDTO detRegistroNota);

	@DELETE
	@Path("/{id}")
	Response eliminar(@PathParam("id") String id);

	@GET
	Response paginador(@Context HttpHeaders headers, @Context UriInfo info);

	@GET
	@Path("/obtenerRegistroNota/{idCursoCarga}/{idAlumno}/{esActaEvaluacionFinalAplazado}")
	Response obtenerRegistroNota(@PathParam("idCursoCarga") String idCursoCarga, @PathParam("idAlumno") String idAlumno,
			@PathParam("esActaEvaluacionFinalAplazado") Boolean esActaEvaluacionFinalAplazado);
}