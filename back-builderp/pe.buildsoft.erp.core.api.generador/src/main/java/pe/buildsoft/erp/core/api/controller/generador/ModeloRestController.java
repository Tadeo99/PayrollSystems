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
import pe.buildsoft.erp.core.application.entidades.generador.ModeloDTO;
import pe.buildsoft.erp.core.application.interfaces.generador.GeneradorAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;

/**
 * La Class ModeloRestController.
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
public class ModeloRestController extends GenericServiceRestImpl implements IModeloRestController {

	@Inject
	private GeneradorAppServiceLocal servicioApp;

	@POST
	@Path("/{idProyecto}/modelo")
	public Response crear(@PathParam("idProyecto") String idProyecto, ModeloDTO obj) {
		return controladorAccion(idProyecto, obj, AccionType.CREAR);
	}

	@PUT
	@Path("/{idProyecto}/modelo/{id}")
	public Response modificar(@PathParam("idProyecto") String idProyecto, ModeloDTO obj) {
		return controladorAccion(idProyecto, obj, AccionType.MODIFICAR);
	}

	@DELETE
	@Path("/{idProyecto}/modelo/{id}")
	public Response eliminar(@PathParam("idProyecto") String idProyecto, @PathParam("id") String id) {
		var obj = new ModeloDTO();
		obj.setIdModelo(id);
		return controladorAccion(idProyecto, obj, AccionType.ELIMINAR);
	}

	private Response controladorAccion(String idProyecto, ModeloDTO obj, AccionType accionType) {
		var resultado = new RespuestaWSVO<>();
		try {
			resultado.setObjetoResultado(servicioApp.controladorAccionModelo(obj, accionType));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, accionType);
	}

	@GET
	@Path("/{idProyecto}/modelo/{id}")
	public Response finById(@PathParam("idProyecto") String idProyecto, @PathParam("id") String id) {
		var filtro = new ModeloDTO();
		filtro.setIdModelo(id);
		return controladorAccion(idProyecto, filtro, AccionType.FIND_BY_ID);
	}

	@GET
	@Path("/{idProyecto}/modelos")
	public Response paginador(@PathParam("idProyecto") String idProyecto, @Context HttpHeaders headers,
			@Context UriInfo info) {
		var resultado = new RespuestaWSVO<ModeloDTO>();
		try {
			var filtro = to(info);
			filtro.setId(idProyecto);
			if (filtro.getStartRow() == 0 || resultado.getContador() == 0)
				resultado.setContador(servicioApp.contarListarModelo(filtro));
			if (resultado.isData() || filtro.getStartRow() > 0) {
				resultado.setListaResultado(servicioApp.listarModelo(filtro));
			}
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null, info);
	}

}