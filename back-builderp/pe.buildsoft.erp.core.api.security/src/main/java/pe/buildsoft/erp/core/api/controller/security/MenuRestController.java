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
import pe.buildsoft.erp.core.application.entidades.security.MenuDTO;
import pe.buildsoft.erp.core.application.interfaces.security.SeguridadAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.cache.ICache;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ObjectUtil;

/**
 * La Class MenuRestController.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Mon Oct 16 14:31:01 COT 2017
 * @since SIAA-CORE 2.1
 */

@Path("/menu")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MenuRestController extends GenericServiceRestImpl implements IMenuRestController {

	private Logger log = LoggerFactory.getLogger(MenuRestController.class);

	@Inject
	private SeguridadAppServiceLocal servicioApp;

	@Inject
	private ICache cacheUtil;

	@POST
	public Response crear(@Context HttpHeaders httpHeaders, MenuDTO obj) {
		obj.setUsuarioCreacion(cacheUtil.getUserName(getToken(httpHeaders)));
		return controladorAccion(obj, AccionType.CREAR);
	}

	@PUT
	@Path("/{id}")
	public Response modificar(@PathParam("id") Long id, @Context HttpHeaders httpHeaders, MenuDTO obj) {
		obj.setUsuarioModificacion(cacheUtil.getUserName(getToken(httpHeaders)));
		return controladorAccion(obj, AccionType.MODIFICAR);
	}

	@DELETE
	@Path("/{id}")
	public Response eliminar(@Context HttpHeaders httpHeaders, @PathParam("id") Long id) {
		var menu = new MenuDTO();
		menu.setIdMenu(id);
		return controladorAccion(menu, AccionType.ELIMINAR);
	}

	private Response controladorAccion(MenuDTO obj, AccionType accionType) {
		var resultado = new RespuestaWSVO<MenuDTO>();
		try {
			resultado.setObjetoResultado(servicioApp.controladorAccionMenu(obj, accionType));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, accionType);
	}

	@GET
	@Path("/{id}")
	public Response finById(@PathParam("id") Long id) {
		var obj = new MenuDTO();
		obj.setIdMenu(id);
		return controladorAccion(obj, AccionType.FIND_BY_ID);
	}

	@GET
	public Response paginador(@Context HttpHeaders headers, @Context UriInfo info) {
		var resultado = new RespuestaWSVO<MenuDTO>();
		try {
			var filtro = to(info);
			if (filtro.getStartRow() == 0 || resultado.getContador() == 0)
				resultado.setContador(servicioApp.contarListarMenu(filtro));
			if (resultado.isData() || filtro.getStartRow() > 0) {
				resultado.setListaResultado(servicioApp.listarMenu(filtro));
			}
		} catch (Exception e) {
			log.error("paginador", e);
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null, info);
	}

	protected BaseSearch to(@Context UriInfo info) {
		var resultado = toRest(info, BaseSearch.class);
		// resultado.setSistema(new SistemaDTO());
		if (info.getQueryParameters().containsKey("idSistema")) {
			var idSistema = ObjectUtil.objectToLong(info.getQueryParameters().getFirst("idSistema"));
			resultado.setIdSistema(idSistema);
		}
		return resultado;
	}
}