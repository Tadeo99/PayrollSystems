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
import pe.buildsoft.erp.core.application.entidades.generador.EquivalenciaTypeDTO;
import pe.buildsoft.erp.core.application.entidades.generador.TecnologiaDTO;
import pe.buildsoft.erp.core.application.interfaces.generador.GeneradorAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;

/**
 * La Class ConfigTypeEquivalenciaRestController.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Mon Sep 26 18:17:28 PET 2022
 * @since BUILDERP-CORE 2.1
 */
@Path("/tecnologia")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EquivalenciaTypeRestController extends GenericServiceRestImpl implements IEquivalenciaTypeRestController {

	@Inject
	private GeneradorAppServiceLocal servicioApp;

	@POST
	@Path("/{idTecnologia}/equivalencia")
	public Response crear(@PathParam("idTecnologia") String idTecnologia, EquivalenciaTypeDTO obj) {
		return controladorAccion(idTecnologia, obj, AccionType.CREAR);
	}

	@PUT
	@Path("/{idTecnologia}/equivalencia/{id}")
	public Response modificar(@PathParam("idTecnologia") String idTecnologia, EquivalenciaTypeDTO obj) {
		return controladorAccion(idTecnologia, obj, AccionType.MODIFICAR);
	}

	@DELETE
	@Path("/{idTecnologia}/equivalencia/{id}")
	public Response eliminar(@PathParam("idTecnologia") String idTecnologia, @PathParam("id") String id) {
		var obj = new EquivalenciaTypeDTO();
		obj.setIdEquivalenciaType(id);
		return controladorAccion(idTecnologia, obj, AccionType.ELIMINAR);
	}

	private Response controladorAccion(String idTecnologia, EquivalenciaTypeDTO obj, AccionType accionType) {
		var resultado = new RespuestaWSVO<>();
		try {
			obj.setTecnologia(new TecnologiaDTO());
			obj.getTecnologia().setIdTecnologia(idTecnologia);
			resultado.setObjetoResultado(servicioApp.controladorAccionEquivalenciaType(obj, accionType));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, accionType);
	}

	@GET
	@Path("/{idTecnologia}/equivalencia/{id}")
	public Response finById(@PathParam("idTecnologia") String idTecnologia, @PathParam("id") String id) {
		var filtro = new EquivalenciaTypeDTO();
		filtro.setIdEquivalenciaType(id);
		return controladorAccion(idTecnologia, filtro, AccionType.FIND_BY_ID);
	}

	@GET
	@Path("/{idTecnologia}/equivalencias")
	public Response paginador(@PathParam("idTecnologia") String idTecnologia, @Context HttpHeaders headers,
			@Context UriInfo info) {
		var resultado = new RespuestaWSVO<EquivalenciaTypeDTO>();
		try {
			var filtro = to(info);
			filtro.setId(idTecnologia);
			if (filtro.getStartRow() == 0 || resultado.getContador() == 0)
				resultado.setContador(servicioApp.contarListarEquivalenciaType(filtro));
			if (resultado.isData() || filtro.getStartRow() > 0) {
				resultado.setListaResultado(servicioApp.listarEquivalenciaType(filtro));
			}
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null, info);
	}
}