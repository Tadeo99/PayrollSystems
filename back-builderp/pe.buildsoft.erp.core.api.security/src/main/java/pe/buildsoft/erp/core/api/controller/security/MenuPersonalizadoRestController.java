package pe.buildsoft.erp.core.api.controller.security;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import pe.buildsoft.erp.core.application.entidades.security.MenuDTO;
import pe.buildsoft.erp.core.application.entidades.security.MenuPersonalizadoDTO;
import pe.buildsoft.erp.core.application.interfaces.security.SeguridadAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.cache.ICache;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;
import pe.buildsoft.erp.core.infra.transversal.utilitario.AppHttpHeaderNames;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ObjectUtil;

/**
 * La Class MenuPersonalizadoRestController.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Mon Oct 16 14:31:02 COT 2017
 * @since SIAA-CORE 2.1
 */
@Path("/menuPersonalizado")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MenuPersonalizadoRestController extends GenericServiceRestImpl
		implements IMenuPersonalizadoRestController {

	private Logger log = LoggerFactory.getLogger(MenuPersonalizadoRestController.class);

	@Inject
	private ICache cacheUtil;

	@Inject
	private SeguridadAppServiceLocal servicioApp;

	@POST
	public Response asociarMenuPersonalizado(@Context HttpHeaders httpHeaders, List<MenuDTO> listaMenu) {
		var resultado = new RespuestaWSVO<MenuPersonalizadoDTO>();
		try {
			var authToken = httpHeaders.getRequestHeaders().getFirst(AppHttpHeaderNames.AUTH_TOKEN);
			var userName = cacheUtil.getUserName(authToken);
			servicioApp.asociarMenuPersonalizadoByUsuario(listaMenu, userName);
			resultado.setObjetoResultado(new MenuPersonalizadoDTO());
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null);
	}

	@GET
	public Response paginador(@Context HttpHeaders headers, @Context UriInfo info) {
		var resultado = new RespuestaWSVO<MenuDTO>();
		try {
			var filtro = to(info);
			if (filtro.getStartRow() == 0 || resultado.getContador() == 0)
				resultado.setContador(servicioApp.contarListarMenu(filtro));

			if (resultado.isData() || filtro.getStartRow() > 0) {
				resultado.setListaResultado(obtenerMenuCheck(filtro, info.getQueryParameters().getFirst("idUsuario")));
			}
		} catch (Exception e) {
			log.error("paginador", e);
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null, info);
	}

	private List<MenuDTO> obtenerMenuCheck(BaseSearch menu, String idUsuario) {
		return servicioApp.obtenerMenuCheck(menu, idUsuario);
	}

	protected BaseSearch to(@Context UriInfo info) {
		var resultado = toRest(info, BaseSearch.class);
		if (info.getQueryParameters().containsKey("idSistema")) {
			var idSistema = ObjectUtil.objectToLong(info.getQueryParameters().getFirst("idSistema"));
			resultado.setIdNivel(idSistema + "");
		}
		return resultado;
	}
}