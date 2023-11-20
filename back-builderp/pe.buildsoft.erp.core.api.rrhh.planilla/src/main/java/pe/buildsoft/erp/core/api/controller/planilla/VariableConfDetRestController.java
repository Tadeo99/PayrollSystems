package pe.buildsoft.erp.core.api.controller.planilla;

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
import pe.buildsoft.erp.core.application.entidades.planilla.VariableConfDTO;
import pe.buildsoft.erp.core.application.entidades.planilla.VariableConfDetDTO;
import pe.buildsoft.erp.core.application.interfaces.planilla.PlanillaAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;

/**
 * La Class VariableConfRestController.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Path("/tipoPlanilla")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class VariableConfDetRestController extends GenericServiceRestImpl implements IVariableConfDetRestController {

	@Inject
	private PlanillaAppServiceLocal servicioApp;

	@POST
	@Path("/{idTipoPlanilla}/variableGrupo/{variableGrupoId}/variable")
	public Response crear(@PathParam("idTipoPlanilla") String idTipoPlanilla,
			@PathParam("variableGrupoId") String variableGrupoId, VariableConfDetDTO obj) {
		return controladorAccion(variableGrupoId, obj, AccionType.CREAR);
	}

	@PUT
	@Path("/{idTipoPlanilla}/variableGrupo/{variableGrupoId}/variable/{id}")
	public Response modificar(@PathParam("idTipoPlanilla") String idTipoPlanilla,
			@PathParam("variableGrupoId") String variableGrupoId, VariableConfDetDTO obj) {
		return controladorAccion(variableGrupoId, obj, AccionType.MODIFICAR);
	}

	@DELETE
	@Path("/{idTipoPlanilla}/variableGrupo/{variableGrupoId}/variable/{id}")
	public Response eliminar(@PathParam("idTipoPlanilla") String idTipoPlanilla,
			@PathParam("variableGrupoId") String variableGrupoId, @PathParam("id") String id) {
		var filtro = new VariableConfDetDTO();
		filtro.setIdVariableConfDet(id);
		return controladorAccion(variableGrupoId, filtro, AccionType.ELIMINAR);
	}

	private Response controladorAccion(String variableGrupoId, VariableConfDetDTO obj, AccionType accionType) {
		var resultado = new RespuestaWSVO<VariableConfDetDTO>();
		try {
			obj.setVariableConf(new VariableConfDTO());
			obj.getVariableConf().setIdVariableConf(variableGrupoId);
			resultado.setObjetoResultado(servicioApp.controladorAccionVariableConfDet(obj, accionType));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, accionType);
	}

	@GET
	@Path("/{idTipoPlanilla}/variableGrupo/{variableGrupoId}/variable/{id}")
	public Response finById(@PathParam("idTipoPlanilla") String idTipoPlanilla,
			@PathParam("variableGrupoId") String variableGrupoId, @PathParam("id") String id) {
		var filtro = new VariableConfDetDTO();
		filtro.setIdVariableConfDet(id);
		return controladorAccion(variableGrupoId, filtro, AccionType.FIND_BY_ID);
	}

	@GET
	@Path("/{idTipoPlanilla}/variableGrupo/{variableGrupoId}/variables")
	public Response paginador(@PathParam("idTipoPlanilla") String idTipoPlanilla,
			@PathParam("variableGrupoId") String variableGrupoId, @Context HttpHeaders headers, @Context UriInfo info) {
		var resultado = new RespuestaWSVO<VariableConfDetDTO>();
		var filtro = to(info);
		filtro.setIdPadreView(variableGrupoId);
		filtro.setId(idTipoPlanilla);
		try {
			if (filtro.getStartRow() == 0 || resultado.getContador() == 0) {
				resultado.setContador(servicioApp.contarListarVariableConfDet(filtro));
			}
			if (resultado.isData() || filtro.getStartRow() > 0) {
				resultado.setListaResultado(servicioApp.listarVariableConfDet(filtro));
			}
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null, info);
	}

}