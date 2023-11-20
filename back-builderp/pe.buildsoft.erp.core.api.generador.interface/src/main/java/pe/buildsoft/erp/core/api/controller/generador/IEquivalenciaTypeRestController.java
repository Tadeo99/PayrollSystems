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
import pe.buildsoft.erp.core.application.entidades.generador.EquivalenciaTypeDTO;

/**
 * La Class IConfigTypeEquivalenciaRestController .
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Mon Sep 26 16:30:14 PET 2022
 * @since BUILDERP-CORE 2.1
 */
@Tag(name = "/tecnologia", description = "Generador Service")
@OpenAPIDefinition(info = @Info(title = "Servicios de Generador", version = "0.0", description = "Generador Service"), tags = {
		@Tag(name = "Generador Service", description = "Servicios de Generador") })
@Path("/tecnologia")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface IEquivalenciaTypeRestController {

	@POST
	@Path("/{idTecnologia}/equivalencia")
	Response crear(@PathParam("idTecnologia") String idTecnologia, EquivalenciaTypeDTO obj);

	@PUT
	@Path("/{idTecnologia}/equivalencia/{id}")
	Response modificar(@PathParam("idTecnologia") String idTecnologia, EquivalenciaTypeDTO obj);

	@DELETE
	@Path("/{idTecnologia}/equivalencia/{id}")
	Response eliminar(@PathParam("idTecnologia") String idTecnologia, @PathParam("id") String id);

	@GET
	@Path("/{idTecnologia}/equivalencia/{id}")
	Response finById(@PathParam("idTecnologia") String idTecnologia, @PathParam("id") String id);

	@GET
	@Path("/{idTecnologia}/equivalencias")
	Response paginador(@PathParam("idTecnologia") String idTecnologia, @Context HttpHeaders headers,
			@Context UriInfo info);
}