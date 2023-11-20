package pe.buildsoft.erp.core.api.controller.pago.reporte;

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
import pe.buildsoft.erp.core.application.entidades.pago.ControlPagoDTO;
import pe.buildsoft.erp.core.domain.entidades.pago.vo.SunatDatosVO;

/**
 * La Class ControlPagoRestController.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 21:45:29 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Tag(name = "/reporte/pago", description = "Pago Service")
@OpenAPIDefinition(info = @Info(title = "Servicios de Pago", version = "0.0", description = "Pago Service"),tags = { @Tag(name = "Pago Service", description = "Servicios de Pago") })
@Path("/reporte/pago")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface IReporteControlPagoRestController {

	// TODO:PASAR A REPORTE
	@POST
	@Path("/individual")
	Response generarReportePagoIndividual(@Context HttpHeaders httpHeaders, ControlPagoDTO controlPago);

	@POST
	@Path("/txt")
	Response generarExtracionTXT(@Context HttpHeaders httpHeaders);

	@POST
	@Path("/comprobante")
	Response generarComprobante(@Context HttpHeaders httpHeaders, SunatDatosVO obj);

}