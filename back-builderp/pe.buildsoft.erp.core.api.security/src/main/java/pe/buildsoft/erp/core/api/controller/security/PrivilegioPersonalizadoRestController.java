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
import pe.buildsoft.erp.core.application.entidades.security.PrivilegioDTO;
import pe.buildsoft.erp.core.application.entidades.security.PrivilegioMenuDTO;
import pe.buildsoft.erp.core.application.entidades.security.PrivilegioPersonalizadoDTO;
import pe.buildsoft.erp.core.application.interfaces.security.SeguridadAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.cache.ICache;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;
import pe.buildsoft.erp.core.infra.transversal.utilitario.AppHttpHeaderNames;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ObjectUtil;

/**
 * La Class PrivilegioPersonalizadoRestController.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Mon Oct 16 14:31:02 COT 2017
 * @since SIAA-CORE 2.1
 */
@Path("/privilegioPersonalizado")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PrivilegioPersonalizadoRestController extends GenericServiceRestImpl
		implements IPrivilegioPersonalizadoRestController {

	private Logger log = LoggerFactory.getLogger(PrivilegioPersonalizadoRestController.class);

	@Inject
	private SeguridadAppServiceLocal servicioApp;

	@Inject
	private ICache cacheUtil;

	@POST
	public Response asociarPrivilegioPersonalizado(@Context HttpHeaders httpHeaders,
			List<PrivilegioDTO> listaPrivilegio) {
		var resultado = new RespuestaWSVO<PrivilegioPersonalizadoDTO>();
		try {
			var authToken = httpHeaders.getRequestHeaders().getFirst(AppHttpHeaderNames.AUTH_TOKEN);
			var userName = cacheUtil.getUserName(authToken);
			servicioApp.asociarPrivilegioPersonalizadoByUsuario(listaPrivilegio, userName);
			resultado.setObjetoResultado(new PrivilegioPersonalizadoDTO());
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null);
	}

	@GET
	public Response paginador(@Context HttpHeaders headers, @Context UriInfo info) {
		var resultado = new RespuestaWSVO<PrivilegioMenuDTO>();
		try {
			var filtro = to(info);
			if (filtro.getStartRow() == 0 || resultado.getContador() == 0)
				resultado.setContador(servicioApp.contarListarPrivilegioMenu(filtro));
			if (resultado.isData() || filtro.getStartRow() > 0) {
				resultado.setListaResultado(obtenerPrivilegioMenuCheck(filtro, filtro.getId() + ""));
			}
		} catch (Exception e) {
			log.error("paginador", e);
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null, info);
	}

	private List<PrivilegioMenuDTO> obtenerPrivilegioMenuCheck(BaseSearch filtro, String idUsuario) {
		return servicioApp.obtenerPrivilegioMenuCheck(filtro, idUsuario);
	}

	protected BaseSearch to(@Context UriInfo info) {
		var resultado = toRest(info, BaseSearch.class);
		if (info.getQueryParameters().containsKey("idMenu")) {
			var listaIdMenu = info.getQueryParameters().get("idMenu");
			for (var idMenu : listaIdMenu) {
				resultado.getListaIdMenu().add(ObjectUtil.objectToLong(idMenu));
			}
		}
		return resultado;
	}
}