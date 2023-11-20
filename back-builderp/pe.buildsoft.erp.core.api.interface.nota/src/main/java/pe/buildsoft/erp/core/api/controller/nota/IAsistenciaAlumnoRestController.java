package pe.buildsoft.erp.core.api.controller.nota;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.buildsoft.erp.core.application.entidades.nota.AsistenciaAlumnoDTO;

/**
 * La Class AsistenciaAlumnoRestController.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Jun 10 00:13:45 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Tag(name = "/asistenciaAlumno", description = "Nota Service")
@OpenAPIDefinition(info = @Info(title = "Servicios de Nota", version = "0.0", description = "Nota Service"),tags = { @Tag(name = "Nota Service", description = "Servicios de Nota") })
@Path("/asistenciaAlumno")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface IAsistenciaAlumnoRestController {

	@POST
	Response registrar(List<AsistenciaAlumnoDTO> listaObj);

	@PUT
	@Path("/{id}")
	Response modificar(AsistenciaAlumnoDTO obj);

	@POST
	@Path("/obtenerAsistenciaAlumno")
	Response obtenerAsistencia(AsistenciaAlumnoDTO filtro);
}