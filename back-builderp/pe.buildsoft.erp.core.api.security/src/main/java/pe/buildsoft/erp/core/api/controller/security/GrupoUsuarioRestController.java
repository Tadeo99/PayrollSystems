
package pe.buildsoft.erp.core.api.controller.security;

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
import pe.buildsoft.erp.core.application.entidades.security.GrupoUsuarioDTO;
import pe.buildsoft.erp.core.application.interfaces.security.SeguridadAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.cache.ICache;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;
import pe.buildsoft.erp.core.infra.transversal.utilitario.AppHttpHeaderNames;

/**
 * La Class GrupoUsuarioRestController.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Mon Oct 16 14:31:01 COT 2017
 * @since SIAA-CORE 2.1
 */

@Path("/grupoUsuario")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GrupoUsuarioRestController extends GenericServiceRestImpl implements IGrupoUsuarioRestController {

	private Logger log = LoggerFactory.getLogger(GrupoUsuarioRestController.class);

	@Inject
	private ICache cacheUtil;

	@Inject
	private SeguridadAppServiceLocal servicioApp;

	@POST
	public Response crear(@Context HttpHeaders headers, GrupoUsuarioDTO obj) {
		return controladorAccion(headers, obj, AccionType.CREAR);
	}

	@PUT
	@Path("/{id}")
	public Response modificar(@Context HttpHeaders headers, GrupoUsuarioDTO obj) {
		return controladorAccion(headers, obj, AccionType.MODIFICAR);
	}

	@DELETE
	@Path("/{id}")
	public Response eliminar(@Context HttpHeaders headers, @PathParam("id") Long id) {
		var obj = new GrupoUsuarioDTO();
		obj.setIdGrupoUsuario(id);
		return controladorAccion(headers, obj, AccionType.ELIMINAR);
	}

	private Response controladorAccion(@Context HttpHeaders headers, GrupoUsuarioDTO obj, AccionType accionType) {
		var resultado = new RespuestaWSVO<GrupoUsuarioDTO>();
		try {
			var authToken = headers.getRequestHeaders().getFirst(AppHttpHeaderNames.AUTH_TOKEN);
			var userName = cacheUtil.getUserName(authToken);
			obj.setUsuarioSession(userName);
			resultado.setObjetoResultado(servicioApp.controladorAccionGrupoUsuario(obj, accionType));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null);
	}

	@GET
	@Path("/{id}")
	public Response finById(@Context HttpHeaders headers, @PathParam("id") Long id) {
		var obj = new GrupoUsuarioDTO();
		obj.setIdGrupoUsuario(id);
		return controladorAccion(headers, obj, AccionType.FIND_BY_ID);
	}

	@GET
	public Response paginador(@Context HttpHeaders headers, @Context UriInfo info) {
		var resultado = new RespuestaWSVO<GrupoUsuarioDTO>();
		try {
			var filtro = to(info);
			if (filtro.getStartRow() == 0 || resultado.getContador() == 0)
				resultado.setContador(servicioApp.contarListarGrupoUsuario(filtro));

			if (resultado.isData() || filtro.getStartRow() > 0) {
				resultado.setListaResultado(servicioApp.listarGrupoUsuario(filtro));
			}
		} catch (Exception e) {
			log.error("paginador", e);
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null, info);
	}

}