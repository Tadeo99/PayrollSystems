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
import pe.buildsoft.erp.core.application.entidades.planilla.InformaOtrosIngreso5taDTO;
import pe.buildsoft.erp.core.application.interfaces.planilla.PlanillaAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;

/**
 * La Class InformaOtrosIngreso5taRestController.
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
public class InformaOtrosIngreso5taRestController extends GenericServiceRestImpl
		implements IInformaOtrosIngreso5taRestController {

	private Logger log = LoggerFactory.getLogger(InformaOtrosIngreso5taRestController.class);

	@Inject
	private PlanillaAppServiceLocal servicioApp;

	@POST
	@Path("/{idPersonal}/informaOtrosIngreso5ta")
	public Response crear(@PathParam("idPersonal") String idPersonal, InformaOtrosIngreso5taDTO obj) {
		return controladorAccion(idPersonal, obj, AccionType.CREAR);
	}

	@PUT
	@Path("/{idPersonal}/informaOtrosIngreso5ta/{id}")
	public Response modificar(@PathParam("idPersonal") String idPersonal, InformaOtrosIngreso5taDTO obj) {
		return controladorAccion(idPersonal, obj, AccionType.MODIFICAR);
	}

	@DELETE
	@Path("/{idPersonal}/informaOtrosIngreso5ta/{id}")
	public Response eliminar(@PathParam("idPersonal") String idPersonal, @PathParam("id") String id) {
		var obj = new InformaOtrosIngreso5taDTO();
		obj.setIdInformaOtrosIngreso5ta(id);
		return controladorAccion(idPersonal, obj, AccionType.ELIMINAR);
	}

	private Response controladorAccion(String idPersonal, InformaOtrosIngreso5taDTO obj, AccionType accionType) {
		var resultado = new RespuestaWSVO<InformaOtrosIngreso5taDTO>();
		try {
			obj.setIdPersonal(idPersonal);
			resultado.setObjetoResultado(servicioApp.controladorAccionInformaOtrosIngreso5ta(obj, accionType));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, accionType);
	}

	@GET
	@Path("/{idPersonal}/informaOtrosIngreso5ta/{id}")
	public Response finById(@PathParam("idPersonal") String idPersonal, @PathParam("id") String id) {
		var filtro = new InformaOtrosIngreso5taDTO();
		filtro.setIdInformaOtrosIngreso5ta(id);
		return controladorAccion(idPersonal, filtro, AccionType.FIND_BY_ID);
	}

	@GET
	@Path("/{idPersonal}/informaOtrosIngreso5tas")
	public Response paginador(@PathParam("idPersonal") String idPersonal, @Context HttpHeaders headers,
			@Context UriInfo info) {
		var resultado = new RespuestaWSVO<InformaOtrosIngreso5taDTO>();
		try {
			var filtro = to(info);
			filtro.setId(idPersonal);
			if (filtro.getStartRow() == 0 || resultado.getContador() == 0)
				resultado.setContador(servicioApp.contarListarInformaOtrosIngreso5ta(filtro));
			if (resultado.isData() || filtro.getStartRow() > 0) {
				resultado.setListaResultado(servicioApp.listarInformaOtrosIngreso5ta(filtro));
			}
		} catch (Exception e) {
			log.error("paginador", e);
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null, info);
	}
}