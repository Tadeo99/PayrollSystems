package pe.buildsoft.erp.core.api.controller.generador;

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
import pe.buildsoft.erp.core.application.entidades.generador.AtributoDTO;

/**
 * La Class IConfigTablaClassRestController .
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Mon Sep 26 16:30:14 PET 2022
 * @since BUILDERP-CORE 2.1
 */
@Tag(name = "/proyecto", description = "Generador Service")
@OpenAPIDefinition(info = @Info(title = "Servicios de Generador", version = "0.0", description = "Generador Service"), tags = {
		@Tag(name = "Generador Service", description = "Servicios de Generador") })
@Path("/proyecto")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface IAtributoRestController {

	@POST
	@Path("/{idProyecto}/modelo/{idModelo}/atributo")
	Response crear(@PathParam("idProyecto") String idProyecto, @PathParam("idModelo") String idModelo, AtributoDTO obj);

	@PUT
	@Path("/{idProyecto}/modelo/{idModelo}/atributo/{id}")
	Response modificar(@PathParam("idProyecto") String idProyecto, @PathParam("idModelo") String idModelo,
			AtributoDTO obj);

	@DELETE
	@Path("/{idProyecto}/modelo/{idModelo}/atributo/{id}")
	Response eliminar(@PathParam("idProyecto") String idProyecto, @PathParam("idModelo") String idModelo,
			@PathParam("id") String id);

	@GET
	@Path("/{idProyecto}/modelo/{idModelo}/atributo/{id}")
	Response finById(@PathParam("idProyecto") String idProyecto, @PathParam("idModelo") String idModelo,
			@PathParam("id") String id);

	@GET
	@Path("/{idProyecto}/modelo/{idModelo}/atributos")
	Response paginador(@PathParam("idProyecto") String idProyecto, @PathParam("idModelo") String idModelo,
			@Context HttpHeaders headers, @Context UriInfo info);
}