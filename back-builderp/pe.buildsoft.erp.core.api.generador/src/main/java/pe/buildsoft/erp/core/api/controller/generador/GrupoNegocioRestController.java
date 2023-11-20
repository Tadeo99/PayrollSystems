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
import pe.buildsoft.erp.core.application.entidades.generador.GrupoNegocioDTO;
import pe.buildsoft.erp.core.application.interfaces.generador.GeneradorAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;

/**
 * La Class GrupoNegocioRestController.
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
public class GrupoNegocioRestController extends GenericServiceRestImpl implements IGrupoNegocioRestController {

	@Inject
	private GeneradorAppServiceLocal servicioApp;

	@POST
	@Path("/{idProyecto}/grupoNegocio")
	public Response crear(@PathParam("idProyecto") String idProyecto, GrupoNegocioDTO obj) {
		return controladorAccion(idProyecto, obj, AccionType.CREAR);
	}

	@PUT
	@Path("/{idProyecto}/grupoNegocio/{id}")
	public Response modificar(@PathParam("idProyecto") String idProyecto, GrupoNegocioDTO obj) {
		return controladorAccion(idProyecto, obj, AccionType.MODIFICAR);
	}

	@DELETE
	@Path("/{idProyecto}/grupoNegocio/{id}")
	public Response eliminar(@PathParam("idProyecto") String idProyecto, @PathParam("id") String id) {
		var obj = new GrupoNegocioDTO();
		obj.setIdGrupoNegocio(id);
		return controladorAccion(idProyecto, obj, AccionType.ELIMINAR);
	}

	private Response controladorAccion(String idProyecto, GrupoNegocioDTO obj, AccionType accionType) {
		var resultado = new RespuestaWSVO<>();
		try {
			obj.setIdProyecto(idProyecto);
			resultado.setObjetoResultado(servicioApp.controladorAccionGrupoNegocio(obj, accionType));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, accionType);
	}

	@GET
	@Path("/{idProyecto}/grupoNegocio/{id}")
	public Response finById(@PathParam("idProyecto") String idProyecto, @PathParam("id") String id) {
		var filtro = new GrupoNegocioDTO();
		filtro.setIdGrupoNegocio(id);
		return controladorAccion(idProyecto, filtro, AccionType.FIND_BY_ID);
	}

	@GET
	@Path("/{idProyecto}/grupoNegocios")
	public Response paginador(@PathParam("idProyecto") String idProyecto, @Context HttpHeaders headers,
			@Context UriInfo info) {
		var resultado = new RespuestaWSVO<GrupoNegocioDTO>();
		try {
			var filtro = to(info);
			filtro.setId(idProyecto);
			if (filtro.getStartRow() == 0 || resultado.getContador() == 0)
				resultado.setContador(servicioApp.contarListarGrupoNegocio(filtro));
			if (resultado.isData() || filtro.getStartRow() > 0) {
				resultado.setListaResultado(servicioApp.listarGrupoNegocio(filtro));
			}
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null, info);
	}

}