package pe.buildsoft.erp.core.api.controller.common;

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
import pe.buildsoft.erp.core.application.entidades.common.AnhioDTO;
import pe.buildsoft.erp.core.application.interfaces.common.CommonAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.state.EstadoGeneralState;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;

/**
 * La Class AnhioRestController.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 21:45:25 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Path("/anhio")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AnhioRestController extends GenericServiceRestImpl implements IAnhioRestController {

	private Logger log = LoggerFactory.getLogger(AnhioRestController.class);

	@Inject
	private CommonAppServiceLocal servicioApp;

	@POST
	public Response crear(AnhioDTO obj) {
		return controladorAccion(obj, AccionType.CREAR);
	}

	@PUT
	@Path("/{id}")
	public Response modificar(AnhioDTO obj) {
		return controladorAccion(obj, AccionType.MODIFICAR);
	}

	@DELETE
	@Path("/{id}")
	public Response eliminar(@PathParam("id") Long id) {
		AnhioDTO obj = new AnhioDTO();
		obj.setIdAnhio(id);
		return controladorAccion(obj, AccionType.ELIMINAR);
	}

	private Response controladorAccion(AnhioDTO obj, AccionType accionType) {
		RespuestaWSVO<AnhioDTO> resultado = new RespuestaWSVO<>();
		try {
			resultado.setObjetoResultado(servicioApp.controladorAccionAnhio(obj, accionType));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, accionType);
	}

	@GET
	@Path("/{id}")
	public Response finById(@PathParam("id") Long id) {
		AnhioDTO filtro = new AnhioDTO();
		filtro.setIdAnhio(id);
		return controladorAccion(filtro, AccionType.FIND_BY_ID);
	}

	@GET
	public Response paginador(@Context HttpHeaders headers, @Context UriInfo info) {
		RespuestaWSVO<AnhioDTO> resultado = new RespuestaWSVO<>();
		try {
			var filtro = to(info);
			if (filtro.getStartRow() == 0 || resultado.getContador() == 0)
				resultado.setContador(servicioApp.contarListarAnhio(filtro));
			if (resultado.isData() || filtro.getStartRow() > 0) {
				resultado.setListaResultado(servicioApp.listarAnhio(filtro));
			}
		} catch (Exception e) {
			log.error("paginador", e);
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null,info);
	}

	@GET
	@Path("/findByEstado") // quitar
	public Response obtenerAnhioyEstado(@Context UriInfo info) {
		RespuestaWSVO<AnhioDTO> resultado = new RespuestaWSVO<>();
		try {
			var filtro = to(info);
			EstadoGeneralState estadoAnhoState = EstadoGeneralState.get(filtro.getEstado());
			resultado.setObjetoResultado(servicioApp.obtenerAnhioyEstado(estadoAnhoState));
		} catch (Exception e) {
			log.error("findByEstado", e);
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null);
	}
}