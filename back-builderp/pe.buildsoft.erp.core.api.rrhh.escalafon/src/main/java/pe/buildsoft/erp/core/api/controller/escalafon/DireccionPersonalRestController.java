package pe.buildsoft.erp.core.api.controller.escalafon;

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
import pe.buildsoft.erp.core.application.entidades.escalafon.DireccionPersonalDTO;
import pe.buildsoft.erp.core.application.entidades.escalafon.PersonalDTO;
import pe.buildsoft.erp.core.application.interfaces.escalafon.EscalafonAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;

/**
 * La Class DireccionPersonalRestController.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:12 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Path("/personal")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DireccionPersonalRestController extends GenericServiceRestImpl
		implements IDireccionPersonalRestController {

	private Logger log = LoggerFactory.getLogger(DireccionPersonalRestController.class);

	@Inject
	private EscalafonAppServiceLocal servicioApp;

	@POST
	@Path("/{idPersonal}/direccion")
	public Response crear(@PathParam("idPersonal") String idPersonal, DireccionPersonalDTO obj) {
		return controladorAccion(idPersonal, obj, AccionType.CREAR);
	}

	@PUT
	@Path("/{idPersonal}/direccion/{id}")
	public Response modificar(@PathParam("idPersonal") String idPersonal, DireccionPersonalDTO obj) {
		return controladorAccion(idPersonal, obj, AccionType.MODIFICAR);
	}

	@DELETE
	@Path("/{idPersonal}/direccion/{id}")
	public Response eliminar(@PathParam("idPersonal") String idPersonal, @PathParam("id") String id) {
		var obj = new DireccionPersonalDTO();
		obj.setIdDireccionPersonal(id);
		return controladorAccion(idPersonal, obj, AccionType.ELIMINAR);
	}

	private Response controladorAccion(String idPersonal, DireccionPersonalDTO obj, AccionType accionType) {
		var resultado = new RespuestaWSVO<DireccionPersonalDTO>();
		try {
			obj.setPersonal(new PersonalDTO());
			obj.getPersonal().setIdPersonal(idPersonal);
			resultado.setObjetoResultado(servicioApp.controladorAccionDireccionPersonal(obj, accionType));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, accionType);
	}

	@GET
	@Path("/{idPersonal}/direccion/{id}")
	public Response finById(@PathParam("idPersonal") String idPersonal, @PathParam("id") String id) {
		var filtro = new DireccionPersonalDTO();
		filtro.setIdDireccionPersonal(id);
		filtro.setPersonal(new PersonalDTO());
		filtro.getPersonal().setIdPersonal(idPersonal);
		return controladorAccion(idPersonal, filtro, AccionType.FIND_BY_ID);
	}

	@GET
	@Path("/{idPersonal}/direccions")
	public Response paginador(@PathParam("idPersonal") String idPersonal, @Context HttpHeaders headers,
			@Context UriInfo info) {
		var resultado = new RespuestaWSVO<DireccionPersonalDTO>();
		try {
			var filtro = to(info);
			if (filtro.getStartRow() == 0)
				resultado.setContador(servicioApp.contarListarDireccionPersonal(filtro));
			if (resultado.isData() || filtro.getStartRow() > 0) {
				resultado.setListaResultado(servicioApp.listarDireccionPersonal(filtro));
			}
		} catch (Exception e) {
			log.error("paginador", e);
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null);
	}

}