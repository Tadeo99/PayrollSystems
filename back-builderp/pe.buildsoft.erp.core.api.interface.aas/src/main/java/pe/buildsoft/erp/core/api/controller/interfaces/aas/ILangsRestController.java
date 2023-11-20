package pe.buildsoft.erp.core.api.controller.interfaces.aas;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HEAD;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * La Class PropertiesRestController.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue Apr 18 13:43:33 COT 2017
 * @since SIAA-CORE 2.1
 */
@Tag(name = "/langs", description = "AAS Service")
@OpenAPIDefinition(info = @Info(title = "Servicios de AAS", version = "0.0", description = "AAS Service"), tags = {
		@Tag(name = "AAS Service", description = "Servicios de AAS") })
@Path("/langs")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ILangsRestController {

	@HEAD
	Response ping();

	@GET
	@Path("/i18n/{langs}")
	Response i18n(@PathParam("langs") String langs);

	@GET
	@Path("/getProperties/{key}")
	Response getProperties(@PathParam("key") String key);

}