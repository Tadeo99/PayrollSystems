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
import pe.buildsoft.erp.core.application.entidades.planilla.PersonalConceptoDTO;
import pe.buildsoft.erp.core.application.interfaces.planilla.PlanillaAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ObjectUtil;

/**
 * La Class PersonalConceptoRestController.
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
public class PersonalConceptoRestController extends GenericServiceRestImpl implements IPersonalConceptoRestController {

	@Inject
	private PlanillaAppServiceLocal servicioApp;

	@POST
	@Path("/{idPersonal}/concepto")
	public Response crear(@PathParam("idPersonal") String idPersonal, PersonalConceptoDTO obj) {
		return controladorAccion(idPersonal, obj, AccionType.CREAR);
	}

	@PUT
	@Path("/{idPersonal}/concepto/{id}")
	public Response modificar(@PathParam("idPersonal") String idPersonal, PersonalConceptoDTO obj) {
		return controladorAccion(idPersonal, obj, AccionType.MODIFICAR);
	}

	@DELETE
	@Path("/{idPersonal}/concepto/{id}")
	public Response eliminar(@PathParam("idPersonal") String idPersonal, @PathParam("id") String id) {
		var obj = new PersonalConceptoDTO();
		obj.setIdPersonalConcepto(id);
		return controladorAccion(idPersonal, obj, AccionType.ELIMINAR);
	}

	private Response controladorAccion(String idPersonal, PersonalConceptoDTO obj, AccionType accionType) {
		var resultado = new RespuestaWSVO<PersonalConceptoDTO>();
		try {
			obj.setIdPersonal(idPersonal);
			resultado.setObjetoResultado(servicioApp.controladorAccionPersonalConcepto(obj, accionType));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, accionType);
	}

	@GET
	@Path("/conceptos")
	public Response paginador(@Context HttpHeaders headers, @Context UriInfo info) {
		var resultado = new RespuestaWSVO<PersonalConceptoDTO>();
		try {
			var filtro = to(info);
			filtro.setAuthToken(getToken(headers));
			resultado.setListaResultado(servicioApp.listarPersonalConcepto(filtro));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null, info);
	}

	protected BaseSearch to(@Context UriInfo info) {
		var resultado = toRest(info, BaseSearch.class);
		var idPeriodoPlanilla = info.getQueryParameters().getFirst("idPeriodoPlanilla");
		var idTipoPlanilla = info.getQueryParameters().getFirst("idTipoPlanilla");
		var idItemByCategoriaTrabajador = ObjectUtil
				.objectToLong(info.getQueryParameters().getFirst("idItemByCategoriaTrabajador"));

		resultado.setIdPeriodoPlanilla(idPeriodoPlanilla);
		resultado.setIdTipoPlanilla(idTipoPlanilla);

		resultado.setIdItemByCategoriaTrabajador(idItemByCategoriaTrabajador);
		return resultado;
	}
}