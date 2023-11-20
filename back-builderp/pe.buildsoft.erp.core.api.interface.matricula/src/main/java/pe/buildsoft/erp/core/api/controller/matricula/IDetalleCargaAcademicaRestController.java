package pe.buildsoft.erp.core.api.controller.matricula;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class DetalleCargaAcademicaRestController.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 21:45:25 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Tag(name = "/detalleCargaAcademica", description = "Matricula Service")
@OpenAPIDefinition(info = @Info(title = "Servicios de Matricula", version = "0.0", description = "Matricula Service"),tags = { @Tag(name = "Matricula Service", description = "Servicios de Matricula") })
@Path("/detalleCargaAcademica")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface IDetalleCargaAcademicaRestController {

	@POST
	@Path("/obtenerDetCargaLectivaVO")
	Response obtenerDetCargaLectivaVO(BaseSearch filtro);

	@GET
	@Path("/obtenerCursosPosiblesLlevar")
	Response obtenerCursosPosiblesLlevar(@Context UriInfo info);

	@POST
	@Path("/listarCursosByDocente")
	Response listarCursosByDocente(BaseSearch filtro);
}