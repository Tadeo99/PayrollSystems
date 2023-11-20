package pe.buildsoft.erp.core.api.controller.matricula;

import java.util.Map;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import pe.buildsoft.erp.core.application.entidades.matricula.DetalleCargaAcademicaDTO;
import pe.buildsoft.erp.core.application.interfaces.matricula.MatriculaAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;

/**
 * La Class DetalleCargaAcademicaRestController.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 21:45:25 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Path("/detalleCargaAcademica")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DetalleCargaAcademicaRestController extends GenericServiceRestImpl implements IDetalleCargaAcademicaRestController {

	@Inject
	private MatriculaAppServiceLocal servicioApp;

	@POST
	@Path("/obtenerDetCargaLectivaVO")
	public Response obtenerDetCargaLectivaVO(BaseSearch filtro) {
		RespuestaWSVO<DetalleCargaAcademicaDTO> resultado = new RespuestaWSVO<>();
		try {
			resultado.setListaResultado(servicioApp.obtenerDetCargaAcademica(filtro));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null);
	}

	@GET
	@Path("/obtenerCursosPosiblesLlevar")
	public Response obtenerCursosPosiblesLlevar(@Context UriInfo info) {
		RespuestaWSVO<DetalleCargaAcademicaDTO> resultado = new RespuestaWSVO<>();
		try {
			Map<String, Object> parametroMap = toMap(info);
			resultado.setListaResultado(servicioApp.obtenerCursosPosiblesLlevar(parametroMap));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null);
	}

	@POST
	@Path("/listarCursosByDocente")
	public Response listarCursosByDocente(BaseSearch filtro) {
		RespuestaWSVO<DetalleCargaAcademicaDTO> resultado = new RespuestaWSVO<>();
		try {
			resultado.setListaResultado(servicioApp.listarDetCargaAcademicaByDocente(filtro));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null);
	}
}