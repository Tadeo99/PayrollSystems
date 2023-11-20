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
import pe.buildsoft.erp.core.application.entidades.matricula.MatriculaDTO;
import pe.buildsoft.erp.core.application.interfaces.matricula.MatriculaAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.cache.ICache;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;
import pe.buildsoft.erp.core.infra.transversal.utilitario.AppHttpHeaderNames;

/**
 * La Class MatriculaRestController.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 21:45:26 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Path("/matricula")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MatriculaRestController extends GenericServiceRestImpl implements IMatriculaRestController {

	private Logger log = LoggerFactory.getLogger(MatriculaRestController.class);

	@Inject
	private MatriculaAppServiceLocal servicioApp;

	@Inject
	private ICache cacheUtil;

	@POST
	public Response crear(@Context HttpHeaders headers, MatriculaDTO obj) {
		RespuestaWSVO<MatriculaDTO> resultado = new RespuestaWSVO<>();
		obj.setUsuarioSession(cacheUtil.getUserName(getToken(headers)));
		try {
			resultado.setObjetoResultado(servicioApp.registrarMatricula(obj));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, AccionType.CREAR);
	}

	@PUT
	@Path("/{id}")
	public Response modificar(MatriculaDTO obj) {
		return controladorAccion(obj, AccionType.MODIFICAR);
	}

	@DELETE
	@Path("/{id}")
	public Response eliminar(@Context HttpHeaders headers, @PathParam("id") String id) {
		RespuestaWSVO<String> resultado = new RespuestaWSVO<>();
		String authToken = headers.getRequestHeaders().getFirst(AppHttpHeaderNames.AUTH_TOKEN);
		try {
			servicioApp.eliminarRegistrarMatricula(id, authToken);
			resultado.setObjetoResultado("ok");
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, AccionType.ELIMINAR);
	}

	private Response controladorAccion(MatriculaDTO matricula, AccionType accionType) {
		RespuestaWSVO<MatriculaDTO> resultado = new RespuestaWSVO<>();
		try {
			resultado.setObjetoResultado(servicioApp.controladorAccionMatricula(matricula, accionType));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, AccionType.ELIMINAR);
	}

	@GET
	public Response paginador(@Context HttpHeaders headers, @Context UriInfo info) {
		RespuestaWSVO<MatriculaDTO> resultado = new RespuestaWSVO<>();
		try {
			var filtro = to(info);
			if (filtro.getStartRow() == 0)
				resultado.setContador(servicioApp.contarListarMatricula(filtro));
			if (resultado.isData() || filtro.getStartRow() > 0) {
				resultado.setListaResultado(servicioApp.listarMatricula(filtro));
			}
		} catch (Exception e) {
			log.error("paginador", e);
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null);
	}

	@GET
	@Path("/{idAnhio}/{idAlumno}/{idTurno}")
	public Response obtenerMatricula(@PathParam("idAnhio") Long idAnhio, @PathParam("idAlumno") String idAlumno,
			@PathParam("idTurno") Long idTurno) {
		RespuestaWSVO<MatriculaDTO> resultado = new RespuestaWSVO<>();
		try {
			resultado.setObjetoResultado(servicioApp.obtenerMatricula(idAnhio, idAlumno, idTurno, null));
		} catch (Exception e) {
			log.error("obtenerMatricula", e);
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null);
	}
}