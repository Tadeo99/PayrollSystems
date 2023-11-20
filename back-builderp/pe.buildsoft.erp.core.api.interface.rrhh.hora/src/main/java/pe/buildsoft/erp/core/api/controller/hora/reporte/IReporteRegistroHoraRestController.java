package pe.buildsoft.erp.core.api.controller.hora.reporte;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import pe.buildsoft.erp.core.domain.entidades.rrhh.hora.vo.RegistroHoraReporteVO;

/**
 * La Class RegistroCabeceraRestController.
 * <ul>
 * <li>Copyright 2021 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Thu Feb 24 15:16:44 COT 2022
 * @since BuildErp 1.0
 */
@Path("/reporteRegistroHora")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface IReporteRegistroHoraRestController {

	@GET
	Response paginador(@Context HttpHeaders headers, @Context UriInfo info);

	@POST
	Response ejecutar(@Context HttpHeaders headers,RegistroHoraReporteVO data);
}