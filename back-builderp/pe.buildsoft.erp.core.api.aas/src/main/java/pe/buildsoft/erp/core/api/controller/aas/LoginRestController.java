package pe.buildsoft.erp.core.api.controller.aas;

import java.security.GeneralSecurityException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HEAD;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.CacheControl;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.buildsoft.erp.core.api.controller.interfaces.aas.ILoginRestController;
import pe.buildsoft.erp.core.application.cache.IAppAuthenticator;
import pe.buildsoft.erp.core.application.cache.ICacheAppUtil;
import pe.buildsoft.erp.core.application.entidades.aas.vo.ConfiguracionFormularioVO;
import pe.buildsoft.erp.core.application.entidades.security.EntidadDTO;
import pe.buildsoft.erp.core.application.entidades.security.UsuarioDTO;
import pe.buildsoft.erp.core.application.interfaces.aas.AasAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.User;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;
import pe.buildsoft.erp.core.infra.transversal.utilitario.AppHttpHeaderNames;

/**
 * La Class LoginController.
 * <ul>
 * <li>Copyright 2016 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Mon Jan 16 21:01:47 COT 2017
 * @since ERP-CRIS 1.0
 */
@Path("/login")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LoginRestController extends GenericServiceRestImpl implements ILoginRestController {
	private Logger log = LoggerFactory.getLogger(LoginRestController.class);

	private static final String EL_AUTH_TOKEN_NO_ES_INCORRECTO = "El authToken no es incorrecto ==> ";

	@Inject
	private IAppAuthenticator appAuthenticator;

	@Inject
	private ICacheAppUtil cacheUtil;

	@Inject
	private AasAppServiceLocal aasServiceLocal;

	@HEAD
	public Response ping() {
		return ping("login");
	}

	@POST
	@Path("/actualizarCache")
	public Response actualizarCache(String authToken) {
		RespuestaWSVO<String> resultado = new RespuestaWSVO<>();
		try {
			cacheUtil.setCargoCache(false);
			resultado.setObjetoResultado("OK");
		} catch (Exception ex) {
			resultado.setError(true);
			resultado.setCodigoError(Response.Status.UNAUTHORIZED.getStatusCode() + "");
			resultado.setMensajeError(EL_AUTH_TOKEN_NO_ES_INCORRECTO + ex.getMessage());
		}
		return respuesta(resultado, null);
	}

	@GET
	@Path("/usuario/{serviceKey}/{authToken}")
	public Response getUsuario(@PathParam("serviceKey") String serviceKey, @PathParam("authToken") String authToken) {
		RespuestaWSVO<UsuarioDTO> resultado = new RespuestaWSVO<>();
		try {
			resultado.setObjetoResultado(appAuthenticator.getUsuario(serviceKey, authToken));
		} catch (Exception ex) {
			log.error("getUsuario", ex);
			resultado.setError(true);
			resultado.setCodigoError(Response.Status.UNAUTHORIZED.getStatusCode() + "");
			resultado.setMensajeError(EL_AUTH_TOKEN_NO_ES_INCORRECTO + ex.getMessage());
		}
		return respuesta(resultado, null);
	}

	@POST
	public Response login(@Context HttpHeaders httpHeaders, User user) {
		RespuestaWSVO<UsuarioDTO> resultado = new RespuestaWSVO<>();
		String serviceKey = httpHeaders.getRequestHeaders().getFirst(AppHttpHeaderNames.SERVICE_KEY);
		try {
			Map<String, Object> resulTempMap = appAuthenticator.login(serviceKey, user.getEmail(), user.getPassword());
			String authToken = (String) resulTempMap.get("authToken");
			resultado.setObjetoResultado((UsuarioDTO) resulTempMap.get("usuario"));
			resultado.setAccessToken(authToken);
		} catch (Exception ex) {
			log.error("login", ex);
			resultado.setError(true);
			resultado.setCodigoError(Response.Status.UNAUTHORIZED.getStatusCode() + "");
			resultado.setMensajeError("El usuario o password es incorrecto ==> " + ex.getMessage());
		}
		return respuesta(resultado, null);
	}

	/*
	 * @POST
	 * 
	 * @Path("/validate") public Response validate(String jwt) {
	 * RespuestaWSVO<String> resultado = new RespuestaWSVO<>(); try { String resul =
	 * JWTokenUtility.validate(jwt); resultado.setObjetoResultado(resul); } catch
	 * (Exception ex) { resultado.setObjetoResultado("${ERROR}" + ex.getMessage());
	 * resultado.setError(true);
	 * resultado.setCodigoError(Response.Status.UNAUTHORIZED.getStatusCode() + "");
	 * resultado.setMensajeError(EL_AUTH_TOKEN_NO_ES_INCORRECTO + ex.getMessage());
	 * } return respuesta(resultado, null); }
	 */

	@POST
	@Path("/dataStore")
	public Response dataStore(@Context HttpHeaders httpHeaders) {
		RespuestaWSVO<UsuarioDTO> resultado = new RespuestaWSVO<>();
		String serviceKey = httpHeaders.getRequestHeaders().getFirst(AppHttpHeaderNames.SERVICE_KEY);
		String authToken = httpHeaders.getRequestHeaders().getFirst(AppHttpHeaderNames.AUTH_TOKEN);
		try {
			UsuarioDTO usuarioDTO = appAuthenticator.getUsuario(serviceKey, authToken);
			if (usuarioDTO != null) {
				resultado.setObjetoResultado(usuarioDTO);
			} else {
				resultado.setError(true);
				resultado.setCodigoError(Response.Status.UNAUTHORIZED.getStatusCode() + "");
				resultado.setMensajeError("Usted no tiene privilegios");
			}

		} catch (Exception ex) {
			resultado.setError(true);
			resultado.setCodigoError(Response.Status.UNAUTHORIZED.getStatusCode() + "");
			resultado.setMensajeError("Usted no tiene privilegios ==> " + ex.getMessage());
		}
		return respuesta(resultado, null);
	}

	@POST
	@Path("/dataStoreConf")
	public Response dataStoreMsg(@Context HttpHeaders httpHeaders) {
		RespuestaWSVO<ConfiguracionFormularioVO> resultado = new RespuestaWSVO<>();
		String serviceKey = httpHeaders.getRequestHeaders().getFirst(AppHttpHeaderNames.SERVICE_KEY);
		String authToken = httpHeaders.getRequestHeaders().getFirst(AppHttpHeaderNames.AUTH_TOKEN);
		try {
			UsuarioDTO usuarioDTO = appAuthenticator.getUsuario(serviceKey, authToken);
			if (usuarioDTO != null) {
				ConfiguracionFormularioVO cofigVO = cacheUtil.getConfiguracionFormularioVO();
				resultado.setObjetoResultado(cofigVO);
			} else {
				resultado.setError(true);
				resultado.setCodigoError(Response.Status.UNAUTHORIZED.getStatusCode() + "");
				resultado.setMensajeError("Usted no tiene privilegios");
			}
		} catch (Exception ex) {
			resultado.setError(true);
			resultado.setCodigoError(Response.Status.UNAUTHORIZED.getStatusCode() + "");
			resultado.setMensajeError("Usted no tiene privilegios ==> " + ex.getMessage());
		}
		return respuesta(resultado, null);
	}

	@PUT
	public Response logout(@Context HttpHeaders httpHeaders) {
		try {
			String serviceKey = httpHeaders.getRequestHeaders().getFirst(AppHttpHeaderNames.SERVICE_KEY);
			String authToken = httpHeaders.getRequestHeaders().getFirst(AppHttpHeaderNames.AUTH_TOKEN);
			appAuthenticator.logout(serviceKey, authToken);
			return getNoCacheResponseBuilder(Response.Status.NO_CONTENT).build();
		} catch (final GeneralSecurityException ex) {
			return getNoCacheResponseBuilder(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	private Response.ResponseBuilder getNoCacheResponseBuilder(Response.Status status) {
		CacheControl cc = new CacheControl();
		cc.setNoCache(true);
		cc.setMaxAge(-1);
		cc.setMustRevalidate(true);

		return Response.status(status).cacheControl(cc);
	}

	@POST
	@Path("/finByIdEntidad")
	public Response finByIdEntidad(EntidadDTO entidadDTO) {
		RespuestaWSVO<EntidadDTO> resultado = new RespuestaWSVO<>();
		try {
			resultado.setObjetoResultado(aasServiceLocal.finByIdEntidad(entidadDTO));
		} catch (Exception ex) {
			resultado.setError(true);
			resultado.setCodigoError(Response.Status.UNAUTHORIZED.getStatusCode() + "");
			resultado.setMensajeError(EL_AUTH_TOKEN_NO_ES_INCORRECTO + ex.getMessage());
		}
		return respuesta(resultado, null);
	}

}