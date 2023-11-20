package pe.buildsoft.erp.core.api.controller.nota;

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
import pe.buildsoft.erp.core.application.entidades.nota.DetRegistroNotaDTO;
import pe.buildsoft.erp.core.application.interfaces.nota.NotaAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;

/**
 * La Class DetRegistroNotaRestController.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Jun 10 00:13:46 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Path("/detRegistroNota")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DetRegistroNotaRestController extends GenericServiceRestImpl implements IDetRegistroNotaRestController {

	private Logger log = LoggerFactory.getLogger(DetRegistroNotaRestController.class);

	@Inject
	private NotaAppServiceLocal servicioApp;

	@POST
	public Response crear(DetRegistroNotaDTO detRegistroNota) {
		return controladorAccion(detRegistroNota, AccionType.CREAR);
	}

	@PUT
	@Path("/{id}")
	public Response modificar(DetRegistroNotaDTO detRegistroNota) {
		return controladorAccion(detRegistroNota, AccionType.MODIFICAR);
	}

	@DELETE
	@Path("/{id}")
	public Response eliminar(@PathParam("id") String id) {
		DetRegistroNotaDTO obj = new DetRegistroNotaDTO();
		obj.setIdDetRegistroNota(id);
		return controladorAccion(obj, AccionType.ELIMINAR);
	}

	private Response controladorAccion(DetRegistroNotaDTO obj, AccionType accionType) {
		RespuestaWSVO<DetRegistroNotaDTO> resultado = new RespuestaWSVO<>();
		try {
			resultado.setObjetoResultado(servicioApp.controladorAccionDetRegistroNota(obj, accionType));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, accionType);
	}

	@GET
	public Response paginador(@Context HttpHeaders headers, @Context UriInfo info) {
		RespuestaWSVO<DetRegistroNotaDTO> resultado = new RespuestaWSVO<>();
		try {
			var filtro = to(info);
			if (filtro.getStartRow() == 0)
				resultado.setContador(servicioApp.contarListarDetRegistroNota(filtro));
			if (resultado.isData() || filtro.getStartRow() > 0) {
				resultado.setListaResultado(servicioApp.listarDetRegistroNota(filtro));
			}
		} catch (Exception e) {
			log.error("paginador",e);
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null);
	}

	@GET
	@Path("/obtenerRegistroNota/{idCursoCarga}/{idAlumno}/{esActaEvaluacionFinalAplazado}")
	public Response obtenerRegistroNota(@PathParam("idCursoCarga") String idCursoCarga,
			@PathParam("idAlumno") String idAlumno,
			@PathParam("esActaEvaluacionFinalAplazado") Boolean esActaEvaluacionFinalAplazado) {
		RespuestaWSVO<DetRegistroNotaDTO> resultado = new RespuestaWSVO<>();
		try {

			resultado.setListaResultado(
					servicioApp.obtenerRegistroNota(idCursoCarga, idAlumno, esActaEvaluacionFinalAplazado));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null);
	}

}