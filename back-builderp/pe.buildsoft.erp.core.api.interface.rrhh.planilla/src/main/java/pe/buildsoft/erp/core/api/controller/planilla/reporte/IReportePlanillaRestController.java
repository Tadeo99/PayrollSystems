package pe.buildsoft.erp.core.api.controller.planilla.reporte;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.buildsoft.erp.core.application.entidades.planilla.DetallePlanillaDTO;

/**
 * La Class ReportePlanillaRestController.
 * <ul>
 * <li>Copyright 2021 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Thu Feb 24 15:16:44 COT 2022
 * @since BuildErp 1.0
 */
@Tag(name = "/reporte/planilla", description = "Planilla Service")
@OpenAPIDefinition(info = @Info(title = "Servicios de Planilla", version = "0.0", description = "Planilla Service"),tags = { @Tag(name = "Planilla Service", description = "Servicios de Planilla") })
@Path("/reporte/planilla")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface IReportePlanillaRestController {

	@POST
	@Path("/boleta/individual")
	Response generarReportePlanillaBoletaIndividual(@Context HttpHeaders headers, DetallePlanillaDTO filtro);

	@POST
	@Path("/boleta/masivo")
	Response generarReportePlanillaBoletaMasiva(@Context HttpHeaders headers, DetallePlanillaDTO filtro);

}