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
import pe.buildsoft.erp.core.application.entidades.planilla.EPSPersonalDTO;
import pe.buildsoft.erp.core.application.interfaces.planilla.PlanillaAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ObjectUtil;

/**
 * La Class EpsPersonalRestController.
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
public class EpsPersonalRestController extends GenericServiceRestImpl implements IEpsPersonalRestController {

	private Logger log = LoggerFactory.getLogger(EpsPersonalRestController.class);

	@Inject
	private PlanillaAppServiceLocal servicioApp;

	@POST
	@Path("/{idPersonal}/eps")
	public Response crear(@PathParam("idPersonal") String idPersonal, EPSPersonalDTO obj) {
		return controladorAccion(idPersonal, obj, AccionType.CREAR);
	}

	@PUT
	@Path("/{idPersonal}/eps/{id}")
	public Response modificar(@PathParam("idPersonal") String idPersonal, EPSPersonalDTO obj) {
		return controladorAccion(idPersonal, obj, AccionType.MODIFICAR);
	}

	@DELETE
	@Path("/{idPersonal}/eps/{id}")
	public Response eliminar(@PathParam("idPersonal") String idPersonal, @PathParam("id") String id) {
		var obj = new EPSPersonalDTO();
		obj.setIdEPSPersonal(id);
		return controladorAccion(idPersonal, obj, AccionType.ELIMINAR);
	}

	private Response controladorAccion(String idPersonal, EPSPersonalDTO obj, AccionType accionType) {
		var resultado = new RespuestaWSVO<EPSPersonalDTO>();
		try {
			obj.setIdPersonal(idPersonal);
			resultado.setObjetoResultado(servicioApp.controladorAccionEPSPersonal(obj, accionType));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, accionType);
	}

	@GET
	@Path("/{idPersonal}/eps/{id}")
	public Response finById(@PathParam("idPersonal") String idPersonal, @PathParam("id") String id) {
		var filtro = new EPSPersonalDTO();
		filtro.setIdEPSPersonal(id);
		return controladorAccion(idPersonal, filtro, AccionType.FIND_BY_ID);
	}

	@GET
	@Path("/eps")
	public Response paginador(@Context HttpHeaders headers, @Context UriInfo info) {
		var resultado = new RespuestaWSVO<EPSPersonalDTO>();
		try {
			var filtro = to(info);
			filtro.setAuthToken(getToken(headers));
			resultado.setListaResultado(servicioApp.listarEPSPersonal(filtro));
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
		var idItemByEps = ObjectUtil.objectToLong(info.getQueryParameters().getFirst("idItemByEps"));

		resultado.setIdAnhio(idAnhio);
		resultado.setIdItemByMes(idItemByMes);
		resultado.setIdItemByEps(idItemByEps);
		resultado.setIdPersonal(idPersonal);
		return resultado;
	}
}