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
import pe.buildsoft.erp.core.application.entidades.planilla.ConceptoRegimenPensionarioDTO;
import pe.buildsoft.erp.core.application.interfaces.planilla.PlanillaAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;

/**
 * La Class ConceptoRegimenPensionarioRestController.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Path("/conceptoRegimenPensionario")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ConceptoRegimenPensionarioRestController extends GenericServiceRestImpl
		implements IConceptoRegimenPensionarioRestController {

	private Logger log = LoggerFactory.getLogger(ConceptoRegimenPensionarioRestController.class);

	@Inject
	private PlanillaAppServiceLocal servicioApp;

	@POST
	public Response crear(ConceptoRegimenPensionarioDTO obj) {
		return controladorAccion(obj, AccionType.CREAR);
	}

	@PUT
	@Path("/{id}")
	public Response modificar(ConceptoRegimenPensionarioDTO obj) {
		return controladorAccion(obj, AccionType.MODIFICAR);
	}

	@DELETE
	@Path("/{id}")
	public Response eliminar(@PathParam("id") String id) {
		var obj = new ConceptoRegimenPensionarioDTO();
		obj.setIdConceptoRegimenPensionario(id);
		return controladorAccion(obj, AccionType.ELIMINAR);
	}

	private Response controladorAccion(ConceptoRegimenPensionarioDTO obj, AccionType accionType) {
		var resultado = new RespuestaWSVO<ConceptoRegimenPensionarioDTO>();
		try {
			resultado.setObjetoResultado(servicioApp.controladorAccionConceptoRegimenPensionario(obj, accionType));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, accionType);
	}

	@GET
	@Path("/{id}")
	public Response finById(@PathParam("id") String id) {
		var filtro = new ConceptoRegimenPensionarioDTO();
		filtro.setIdConceptoRegimenPensionario(id);
		return controladorAccion(filtro, AccionType.FIND_BY_ID);
	}

	@GET
	public Response paginador(@Context HttpHeaders headers, @Context UriInfo info) {
		var resultado = new RespuestaWSVO<ConceptoRegimenPensionarioDTO>();
		try {
			var filtro = to(info);
			if (filtro.getStartRow() == 0 || resultado.getContador() == 0)
				resultado.setContador(servicioApp.contarListarConceptoRegimenPensionario(filtro));
			if (resultado.isData() || filtro.getStartRow() > 0) {
				resultado.setListaResultado(servicioApp.listarConceptoRegimenPensionario(filtro));
			}
		} catch (Exception e) {
			log.error("paginador", e);
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null, info);
	}

}