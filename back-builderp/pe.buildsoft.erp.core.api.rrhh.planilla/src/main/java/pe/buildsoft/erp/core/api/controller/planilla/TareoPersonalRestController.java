package pe.buildsoft.erp.core.api.controller.planilla;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import pe.buildsoft.erp.core.application.entidades.planilla.TareoPersonalDTO;
import pe.buildsoft.erp.core.application.interfaces.planilla.PlanillaAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ObjectUtil;

/**
 * La Class TareoPersonalRestController.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Path("/personal")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TareoPersonalRestController extends GenericServiceRestImpl implements ITareoPersonalRestController {

	private Logger log = LoggerFactory.getLogger(TareoPersonalRestController.class);

	@Inject
	private PlanillaAppServiceLocal servicioApp;

	@POST
	@Path("/{idPersonal}/tareo")
	public Response crear(@PathParam("idPersonal") String idPersonal, TareoPersonalDTO obj) {
		return controladorAccion(idPersonal, obj, AccionType.CREAR);
	}

	@PUT
	@Path("/{idPersonal}/tareo/{id}")
	public Response modificar(@PathParam("idPersonal") String idPersonal, TareoPersonalDTO obj) {
		return controladorAccion(idPersonal, obj, AccionType.MODIFICAR);
	}

	@DELETE
	@Path("/{idPersonal}/tareo/{id}")
	public Response eliminar(@PathParam("idPersonal") String idPersonal, @PathParam("id") String id) {
		var obj = new TareoPersonalDTO();
		obj.setIdTareoPersonal(id);
		return controladorAccion(idPersonal, obj, AccionType.ELIMINAR);
	}

	private Response controladorAccion(String idPersonal, TareoPersonalDTO obj, AccionType accionType) {
		var resultado = new RespuestaWSVO<TareoPersonalDTO>();
		try {
			obj.setIdPersonal(idPersonal);
			resultado.setObjetoResultado(servicioApp.controladorAccionTareoPersonal(obj, accionType));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, accionType);
	}

	@GET
	@Path("/{idPersonal}/tareo/{id}")
	public Response finById(@PathParam("idPersonal") String idPersonal, @PathParam("id") String id) {
		var filtro = new TareoPersonalDTO();
		filtro.setIdTareoPersonal(id);
		return controladorAccion(idPersonal, filtro, AccionType.FIND_BY_ID);
	}

	@GET
	@Path("/tareos")
	public Response paginador(@Context HttpHeaders headers, @Context UriInfo info) {
		var resultado = new RespuestaWSVO<TareoPersonalDTO>();
		try {
			var filtro = to(info);
			filtro.setAuthToken(getToken(headers));
			resultado.setListaResultado(servicioApp.listarTareoPersonal(filtro));
		} catch (Exception e) {
			log.error("paginador", e);
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null, info);
	}

	protected BaseSearch to(@Context UriInfo info) {
		var resultado = toRest(info, BaseSearch.class);
		var idPersonal = info.getQueryParameters().getFirst("idPersonal");
		var idAnhio = ObjectUtil.objectToLong(info.getQueryParameters().getFirst("idAnhio"));
		var idItemByMes = ObjectUtil.objectToLong(info.getQueryParameters().getFirst("idItemByMes"));
		var idItemByCategoriaTrabajador = ObjectUtil
				.objectToLong(info.getQueryParameters().getFirst("idItemByCategoriaTrabajador"));
		var idItemByPeriocidad = ObjectUtil.objectToLong(info.getQueryParameters().getFirst("idItemByPeriocidad"));

		resultado.setIdAnhio(idAnhio);
		resultado.setIdItemByMes(idItemByMes);
		resultado.setIdItemByCategoriaTrabajador(idItemByCategoriaTrabajador);
		resultado.setIdItemByPeriocidad(idItemByPeriocidad);
		resultado.setIdPersonal(idPersonal);
		return resultado;
	}
}