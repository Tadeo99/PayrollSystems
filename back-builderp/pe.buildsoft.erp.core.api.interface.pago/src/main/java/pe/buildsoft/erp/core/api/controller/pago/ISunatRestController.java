package pe.buildsoft.erp.core.api.controller.pago;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * La Class EmpresaRestController.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 21:45:31 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Tag(name = "/sunat", description = "Pago Service")
@OpenAPIDefinition(info = @Info(title = "Servicios de Pago", version = "0.0", description = "Pago Service"),tags = { @Tag(name = "Pago Service", description = "Servicios de Pago") })
@Path("/sunat")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ISunatRestController {

	@GET
	@Path("/dni/{nroDoc}")
	Response getDni(@PathParam("nroDoc") String nroDoc);

	@GET
	@Path("/ruc/{nroDoc}")
	Response getRuc(@PathParam("nroDoc") String nroDoc);

}