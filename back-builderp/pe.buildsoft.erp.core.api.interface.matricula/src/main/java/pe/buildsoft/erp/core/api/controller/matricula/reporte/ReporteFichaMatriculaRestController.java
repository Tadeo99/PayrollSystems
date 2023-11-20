package pe.buildsoft.erp.core.api.controller.matricula.reporte;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * La Class ReporteFichaMatriculaRestController.
 * <ul>
 * <li>Copyright 2021 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Thu Feb 24 15:16:44 COT 2022
 * @since BuildErp 1.0
 */
@Tag(name = "/reporte/matricula/ficha", description = "Matricula Reporte Service")
@OpenAPIDefinition(info = @Info(title = "Servicios de Matricula Reporte", version = "0.0", description = "Matricula Reporte Service"),tags = { @Tag(name = "Matricula Reporte Service", description = "Servicios de Matricula Reporte") })
@Path("/reporte/matricula/ficha")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ReporteFichaMatriculaRestController {

	@GET
	@Path("/{idAnhio}/{idAlumno}/{idPeriodo}")
	Response ejecutar(@Context HttpHeaders headers, @PathParam("idAnhio") Long idAnhio,
			@PathParam("idAlumno") String idAlumno, @PathParam("idPeriodo") Long idPeriodo);
}