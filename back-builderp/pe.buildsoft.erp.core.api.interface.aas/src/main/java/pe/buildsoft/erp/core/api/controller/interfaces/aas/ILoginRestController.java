package pe.buildsoft.erp.core.api.controller.interfaces.aas;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HEAD;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.buildsoft.erp.core.application.entidades.security.EntidadDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.User;

/**
 * La Class LoginController.
 * <ul>
 * <li>Copyright 2016 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Mon Jan 16 21:01:47 COT 2017
 * @since ERP-CRIS 1.0
 */
@Tag(name = "/login", description = "AAS Service")
@OpenAPIDefinition(info = @Info(title = "Servicios de AAS", version = "0.0", description = "AAS Service"), tags = {
		@Tag(name = "AAS Service", description = "Servicios de AAS") })
@Path("/login")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ILoginRestController {

	@HEAD
	Response ping();

	@POST
	@Path("/actualizarCache")
	Response actualizarCache(String authToken);

	@GET
	@Path("/usuario/{serviceKey}/{authToken}")
	Response getUsuario(@PathParam("serviceKey") String serviceKey, @PathParam("authToken") String authToken);

	@POST
	Response login(@Context HttpHeaders httpHeaders, User user);

	/*
	 * @POST
	 * 
	 * @Path("/validate") public Response validate(String jwt) ;
	 */

	@POST
	@Path("/dataStore")
	Response dataStore(@Context HttpHeaders httpHeaders);

	@POST
	@Path("/dataStoreConf")
	Response dataStoreMsg(@Context HttpHeaders httpHeaders);

	@PUT
	Response logout(@Context HttpHeaders httpHeaders);

	@POST
	@Path("/finByIdEntidad")
	Response finByIdEntidad(EntidadDTO entidadDTO);

}