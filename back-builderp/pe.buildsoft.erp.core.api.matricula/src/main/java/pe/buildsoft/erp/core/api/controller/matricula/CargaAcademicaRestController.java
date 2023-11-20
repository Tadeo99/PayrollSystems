package pe.buildsoft.erp.core.api.controller.matricula;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import pe.buildsoft.erp.core.application.entidades.matricula.CargaAcademicaDTO;
import pe.buildsoft.erp.core.application.entidades.matricula.DetalleCargaAcademicaDTO;
import pe.buildsoft.erp.core.application.interfaces.matricula.MatriculaAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.cache.ICache;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * La Class CargaAcademicaRestController.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 21:45:25 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Path("/cargaAcademica")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CargaAcademicaRestController extends GenericServiceRestImpl implements ICargaAcademicaRestController {

	private Logger log = LoggerFactory.getLogger(CargaAcademicaRestController.class);

	@Inject
	private MatriculaAppServiceLocal servicioApp;

	@Inject
	private ICache cacheUtil;

	@POST
	public Response agregarCursoCargaLectiva(@Context HttpHeaders headers, CargaAcademicaDTO obj) {
		RespuestaWSVO<DetalleCargaAcademicaDTO> resultado = new RespuestaWSVO<>();
		if (!StringUtil.isNotNullOrBlank(obj.getIdCargaAcademica())) {
			obj.setUsuarioCreacion(cacheUtil.getUserName(getToken(headers)));
		} else {
			obj.setUsuarioModificacion(cacheUtil.getUserName(getToken(headers)));
		}
		try {
			servicioApp.agregarCargaAcademica(obj);
			resultado.setObjetoResultado(obj.getCargaAcademicaDetalleCargaAcademicaList().get(0));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, AccionType.CREAR);
	}

	@DELETE
	@Path("/{id}")
	public Response eliminar(@PathParam("id") String id) {
		RespuestaWSVO<CargaAcademicaDTO> resultado = new RespuestaWSVO<>();
		CargaAcademicaDTO obj = new CargaAcademicaDTO();
		obj.setIdCargaAcademica(id);
		try {
			resultado.setObjetoResultado(servicioApp.eliminarCargaAcademica(obj));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, AccionType.ELIMINAR);
	}

	@GET
	public Response paginador(@Context HttpHeaders headers, @Context UriInfo info) {
		RespuestaWSVO<CargaAcademicaDTO> resultado = new RespuestaWSVO<>();
		try {
			var filtro = to(info);
			if (filtro.getStartRow() == 0)
				resultado.setContador(servicioApp.contarListarCargaAcademica(filtro));
			if (resultado.isData() || filtro.getStartRow() > 0) {
				resultado.setListaResultado(servicioApp.listarCargaAcademica(filtro));
			}
		} catch (Exception e) {
			log.error("paginador", e);
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null);
	}

}