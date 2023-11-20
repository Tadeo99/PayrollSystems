package pe.buildsoft.erp.core.api.controller.common;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.FileVO;

/**
 * La Class FileUploadRestController.
 * <ul>
 * <li>Copyright 2018 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, 05/04/2018 03:13 pm
 * @since SIAA-CORE 2.1
 */
@Tag(name = "/fileUpload", description = "Common Service")
@OpenAPIDefinition(info = @Info(title = "Servicios de Common", version = "0.0", description = "Common Service"),tags = { @Tag(name = "Common Service", description = "Servicios de Common") })
@Path("/fileUpload")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface IFileUploadRestController {

	@POST
	@Path("/alumno")
	Response subirImagen(FileVO file);

	@POST
	@Path("/personal")
	Response subirImagenPersonal(FileVO file);

	@GET
	@Path("/alumno/{fotoImg}")
	Response obtenerImagenEncodeBase64(@PathParam("fotoImg") String fotoImg);

	@GET
	@Path("/personal/{fotoImg}")
	Response obtenerImagenEncodeBase64Personal(@PathParam("fotoImg") String fotoImg);

}