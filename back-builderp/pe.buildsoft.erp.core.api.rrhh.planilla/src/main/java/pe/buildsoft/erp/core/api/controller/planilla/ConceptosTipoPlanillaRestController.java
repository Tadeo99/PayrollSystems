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
import pe.buildsoft.erp.core.application.entidades.planilla.ConceptosTipoPlanillaDTO;
import pe.buildsoft.erp.core.application.entidades.planilla.TipoPlanillaDTO;
import pe.buildsoft.erp.core.application.interfaces.planilla.PlanillaAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;

/**
 * La Class ConceptosTipoPlanillaRestController.
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
public class ConceptosTipoPlanillaRestController extends GenericServiceRestImpl
		implements IConceptosTipoPlanillaRestController {

	@Inject
	private PlanillaAppServiceLocal servicioApp;

	@POST
	@Path("/{idTipoPlanilla}/concepto")
	public Response crear(@PathParam("idTipoPlanilla") String idTipoPlanilla, ConceptosTipoPlanillaDTO obj) {
		return controladorAccion(idTipoPlanilla, obj, AccionType.CREAR);
	}

	@PUT
	@Path("/{idTipoPlanilla}/concepto/{id}")
	public Response modificar(@PathParam("idTipoPlanilla") String idTipoPlanilla, ConceptosTipoPlanillaDTO obj) {
		return controladorAccion(idTipoPlanilla, obj, AccionType.MODIFICAR);
	}

	@DELETE
	@Path("/{idTipoPlanilla}/concepto/{id}")
	public Response eliminar(@PathParam("idTipoPlanilla") String idTipoPlanilla, @PathParam("id") String id) {
		var filtro = new ConceptosTipoPlanillaDTO();
		filtro.setIdConceptosTipoPlanilla(id);
		return controladorAccion(idTipoPlanilla, filtro, AccionType.ELIMINAR);
	}

	private Response controladorAccion(String idTipoPlanilla, ConceptosTipoPlanillaDTO obj, AccionType accionType) {
		var resultado = new RespuestaWSVO<ConceptosTipoPlanillaDTO>();
		try {
			obj.setTipoPlanilla(new TipoPlanillaDTO());
			obj.getTipoPlanilla().setIdTipoPlanilla(idTipoPlanilla);
			resultado.setObjetoResultado(servicioApp.controladorAccionConceptosTipoPlanilla(obj, accionType));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, accionType);
	}

	@GET
	@Path("/{idTipoPlanilla}/concepto/{id}")
	public Response finById(@PathParam("idTipoPlanilla") String idTipoPlanilla, @PathParam("id") String id) {
		var filtro = new ConceptosTipoPlanillaDTO();
		filtro.setIdConceptosTipoPlanilla(id);
		return controladorAccion(idTipoPlanilla, filtro, AccionType.FIND_BY_ID);
	}

	@GET
	@Path("/{idTipoPlanilla}/conceptos")
	public Response paginador(@PathParam("idTipoPlanilla") String idTipoPlanilla, @Context HttpHeaders headers,
			@Context UriInfo info) {
		var resultado = new RespuestaWSVO<ConceptosTipoPlanillaDTO>();
		var filtro = to(info);
		filtro.setId(idTipoPlanilla);
		try {
			if (filtro.getStartRow() == 0 || resultado.getContador() == 0) {
				resultado.setContador(servicioApp.contarListarConceptosTipoPlanilla(filtro));
			}
			if (resultado.isData() || filtro.getStartRow() > 0) {
				resultado.setListaResultado(servicioApp.listarConceptosTipoPlanilla(filtro));
			}
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null, info);
	}

}