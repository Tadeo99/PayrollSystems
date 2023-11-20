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
import pe.buildsoft.erp.core.application.entidades.generador.AtributoDTO;
import pe.buildsoft.erp.core.application.entidades.generador.ModeloDTO;
import pe.buildsoft.erp.core.application.interfaces.generador.GeneradorAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;

/**
 * La Class AtributoRestController.
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
public class AtributoRestController extends GenericServiceRestImpl implements IAtributoRestController {

	@Inject
	private GeneradorAppServiceLocal servicioApp;

	@POST
	@Path("/{idProyecto}/modelo/{idModelo}/atributo")
	public Response crear(@PathParam("idProyecto") String idProyecto, @PathParam("idModelo") String idModelo,
			AtributoDTO obj) {
		return controladorAccion(idModelo, obj, AccionType.CREAR);
	}

	@PUT
	@Path("/{idProyecto}/modelo/{idModelo}/atributo/{id}")
	public Response modificar(@PathParam("idProyecto") String idProyecto, @PathParam("idModelo") String idModelo,
			AtributoDTO obj) {
		return controladorAccion(idModelo, obj, AccionType.MODIFICAR);
	}

	@DELETE
	@Path("/{idProyecto}/modelo/{idModelo}/atributo/{id}")
	public Response eliminar(@PathParam("idProyecto") String idProyecto, @PathParam("idModelo") String idModelo,
			@PathParam("id") String id) {
		var obj = new AtributoDTO();
		obj.setIdAtributo(id);
		return controladorAccion(idModelo, obj, AccionType.ELIMINAR);
	}

	private Response controladorAccion(String idModelo, AtributoDTO obj, AccionType accionType) {
		var resultado = new RespuestaWSVO<>();
		try {
			obj.setModelo(new ModeloDTO());
			obj.getModelo().setIdModelo(idModelo);
			resultado.setObjetoResultado(servicioApp.controladorAccionAtributo(obj, accionType));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, accionType);
	}

	@GET
	@Path("/{idProyecto}/modelo/{idModelo}/atributo/{id}")
	public Response finById(@PathParam("idProyecto") String idProyecto, @PathParam("idModelo") String idModelo,
			@PathParam("id") String id) {
		var filtro = new AtributoDTO();
		filtro.setIdAtributo(id);
		return controladorAccion(idModelo, filtro, AccionType.FIND_BY_ID);
	}

	@GET
	@Path("/{idProyecto}/modelo/{idModelo}/atributos")
	public Response paginador(@PathParam("idProyecto") String idProyecto, @PathParam("idModelo") String idModelo,
			@Context HttpHeaders headers, @Context UriInfo info) {
		var resultado = new RespuestaWSVO<AtributoDTO>();
		try {
			var filtro = to(info);
			filtro.setId(idModelo);
			if (filtro.getStartRow() == 0 || resultado.getContador() == 0)
				resultado.setContador(servicioApp.contarListarAtributo(filtro));
			if (resultado.isData() || filtro.getStartRow() > 0) {
				resultado.setListaResultado(servicioApp.listarAtributo(filtro));
			}
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null, info);
	}

}