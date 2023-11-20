package pe.buildsoft.erp.core.api.controller.matricula;

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
import pe.buildsoft.erp.core.application.entidades.matricula.CriterioEvaluacionDTO;
import pe.buildsoft.erp.core.application.interfaces.matricula.MatriculaAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;

/**
 * La Class CriterioEvaluacionRestController.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Jun 10 00:13:45 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Path("/criterioEvaluacion")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CriterioEvaluacionRestController extends GenericServiceRestImpl
		implements ICriterioEvaluacionRestController {

	private Logger log = LoggerFactory.getLogger(CriterioEvaluacionRestController.class);

	@Inject
	private MatriculaAppServiceLocal servicioApp;

	@POST
	public Response crear(CriterioEvaluacionDTO obj) {
		return controladorAccion(obj, AccionType.CREAR);
	}

	@PUT
	@Path("/{id}")
	public Response modificar(CriterioEvaluacionDTO obj) {
		return controladorAccion(obj, AccionType.MODIFICAR);
	}

	@DELETE
	@Path("/{id}")
	public Response eliminar(@PathParam("id") String id) {
		CriterioEvaluacionDTO obj = new CriterioEvaluacionDTO();
		obj.setIdCriterioEvaluacion(id);
		return controladorAccion(obj, AccionType.ELIMINAR);
	}

	private Response controladorAccion(CriterioEvaluacionDTO obj, AccionType accionType) {
		RespuestaWSVO<CriterioEvaluacionDTO> resultado = new RespuestaWSVO<>();
		try {
			resultado.setObjetoResultado(servicioApp.controladorAccionCriterioEvaluacion(obj, accionType));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, accionType);
	}

	@GET
	@Path("/{id}")
	public Response finById(@PathParam("id") String id) {
		CriterioEvaluacionDTO filtro = new CriterioEvaluacionDTO();
		filtro.setIdCriterioEvaluacion(id);
		return controladorAccion(filtro, AccionType.FIND_BY_ID);
	}

	@GET
	public Response paginador(@Context HttpHeaders headers, @Context UriInfo info) {
		RespuestaWSVO<CriterioEvaluacionDTO> resultado = new RespuestaWSVO<>();
		try {
			var filtro = to(info);
			if (filtro.getStartRow() == 0)
				resultado.setContador(servicioApp.contarListarCriterioEvaluacion(filtro));
			if (resultado.isData() || filtro.getStartRow() > 0) {
				resultado.setListaResultado(servicioApp.listarCriterioEvaluacion(filtro));
			}
		} catch (Exception e) {
			log.error("paginador", e);
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null);
	}

	@GET
	@Path("/listaCriterioEvaluacion/{estado}/{idDetMallaCurricular}")
	public Response listaCriterioEvaluacion(@PathParam("estado") String estado,
			@PathParam("idDetMallaCurricular") String id) {
		RespuestaWSVO<CriterioEvaluacionDTO> resultado = new RespuestaWSVO<>();
		try {
			resultado.setListaResultado(servicioApp.listarCriterioEvaluacion(estado, id, null));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null);
	}

}