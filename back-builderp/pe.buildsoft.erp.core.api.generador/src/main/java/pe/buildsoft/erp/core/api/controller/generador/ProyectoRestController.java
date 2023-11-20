package pe.buildsoft.erp.core.api.controller.generador;

import jakarta.inject.Inject;
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
import pe.buildsoft.erp.core.application.entidades.generador.ProyectoDTO;
import pe.buildsoft.erp.core.application.interfaces.generador.GeneradorAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;

/**
 * La Class ProyectoRestController.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Mon Sep 26 18:17:28 PET 2022
 * @since BUILDERP-CORE 2.1
 */
@Path("/proyecto")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProyectoRestController extends GenericServiceRestImpl implements IProyectoRestController {

	@Inject
	private GeneradorAppServiceLocal servicioApp;

	@POST
	public Response crear(ProyectoDTO obj) {
		return controladorAccion(obj, AccionType.CREAR);
	}

	@PUT
	@Path("/{id}")
	public Response modificar(ProyectoDTO obj) {
		return controladorAccion(obj, AccionType.MODIFICAR);
	}

	@DELETE
	@Path("/{id}")
	public Response eliminar(@PathParam("id") String id) {
		var obj = new ProyectoDTO();
		obj.setIdProyecto(id);
		return controladorAccion(obj, AccionType.ELIMINAR);
	}

	private Response controladorAccion(ProyectoDTO obj, AccionType accionType) {
		var resultado = new RespuestaWSVO<>();
		try {
			resultado.setObjetoResultado(servicioApp.controladorAccionProyecto(obj, accionType));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, accionType);
	}

	@GET
	@Path("/{id}")
	public Response finById(@PathParam("id") String id) {
		var filtro = new ProyectoDTO();
		filtro.setIdProyecto(id);
		return controladorAccion(filtro, AccionType.FIND_BY_ID);
	}

	@GET
	public Response paginador(@Context HttpHeaders headers, @Context UriInfo info) {
		var resultado = new RespuestaWSVO<ProyectoDTO>();
		try {
			var filtro = to(info);
			if (filtro.getStartRow() == 0 || resultado.getContador() == 0)
				resultado.setContador(servicioApp.contarListarProyecto(filtro));
			if (resultado.isData() || filtro.getStartRow() > 0) {
				resultado.setListaResultado(servicioApp.listarProyecto(filtro));
			}
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null, info);
	}
}