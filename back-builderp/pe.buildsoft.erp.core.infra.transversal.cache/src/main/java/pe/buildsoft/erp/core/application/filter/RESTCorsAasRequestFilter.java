package pe.buildsoft.erp.core.application.filter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import pe.buildsoft.erp.core.infra.transversal.cache.IJWT;
import pe.buildsoft.erp.core.infra.transversal.utilitario.AppHttpHeaderNames;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

@Provider
@PreMatching
public class RESTCorsAasRequestFilter implements ContainerRequestFilter {

	/** La log. */
	private static Logger log = LoggerFactory.getLogger(RESTCorsAasRequestFilter.class);

	@Inject
	private IJWT jwt;

	private boolean activarSeguridad = true;

	@Override
	public void filter(ContainerRequestContext requestCtx) throws IOException {
		// httpServletResponse.setHeader("X-Powered-By",".");
		boolean isCorrecto = true;
		String path = requestCtx.getUriInfo().getAbsolutePath().getPath();
		log.info("Filtering request path: " + path);
		// When HttpMethod comes as OPTIONS, just acknowledge that it accepts...
		if (requestCtx.getRequest().getMethod().equals("OPTIONS")) {
			log.info("HTTP Method (OPTIONS) - Detected!");
			// Just send a OK signal back to the browser
		} else {
			// Then check is the service key exists and is valid.
			String serviceKey = requestCtx.getHeaderString(AppHttpHeaderNames.SERVICE_KEY);
			/*
			 * if ( !cacheUtil.isServiceKeyValid( serviceKey ) ) { // Kick anyone without a
			 * valid service key
			 * httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
			 * httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
			 * "SERVICE_KEY INVALIDO"); isCorrecto = false; }
			 */
			// For any pther methods besides login, the authToken must be verified
			if (activarSeguridad && !path.contains("/login") && !path.contains("/langs/i18n") && !path.contains("/item")
					&& !path.contains("/grado") && !path.contains("/sede") && !path.contains("/apoderado")
					&& !path.contains("/personal")
					&& !path.contains("/parametro")
					// && !path.contains("/aas/api/entidad")
					&& !path.contains("/DescargarReporte")) {
				String authToken = requestCtx.getHeaderString(AppHttpHeaderNames.AUTH_TOKEN);
				// if it isn't valid, just kick them out.
				if (!StringUtil.isNotNullOrBlank(authToken) || !jwt.isAuthTokenValid(serviceKey, authToken)) {
					logout(serviceKey, authToken);
					// setHeaderForError(httpServletRequest, httpServletResponse);
					// httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
					// httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "TOKEN
					// INVALIDO");
					requestCtx.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
					isCorrecto = false;
				} else {
					try {
						validate(authToken);
						if (!jwt.isSessionActiva(serviceKey, authToken)) {
							logout(serviceKey, authToken);
							/*
							 * setHeaderForError(httpServletRequest, httpServletResponse);
							 * httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
							 * httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
							 * "TOKEN INVALIDO");
							 */
							requestCtx.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
							isCorrecto = false;
						}
					} catch (Exception e) {
						try {
							logout(serviceKey, authToken);
						} catch (Exception e2) {
							log.error("validate", e2);
						}
						requestCtx.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
						isCorrecto = false;
					}
				}
			}
			if (isCorrecto) {
				// filterChain.doFilter(httpServletRequest, httpServletResponse);
			}
		}
	}

	private void logout(String serviceKey, String authToken) {
		try {
			if (StringUtil.isNotNullOrBlank(authToken)) {
				jwt.logout(serviceKey, authToken);
			}
		} catch (Exception e2) {
			log.error("logout", e2);
		}
	}

	private String validate(String jwt) throws Exception {
		String data = this.jwt.validate(jwt);
		if (data != null && data.contains("${ERROR}")) {
			throw new Exception(data.replace("${ERROR}", ""));
		}
		return data;
	}
}