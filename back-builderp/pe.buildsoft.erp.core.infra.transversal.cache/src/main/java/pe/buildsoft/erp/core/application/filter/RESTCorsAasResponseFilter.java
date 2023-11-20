package pe.buildsoft.erp.core.application.filter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.ext.Provider;
import pe.buildsoft.erp.core.infra.transversal.utilitario.AppHttpHeaderNames;

@Provider
@PreMatching
public class RESTCorsAasResponseFilter implements ContainerResponseFilter {

	private final Logger log = LoggerFactory.getLogger(RESTCorsAasResponseFilter.class);

	@Override
	public void filter(ContainerRequestContext req, ContainerResponseContext res) throws IOException {
		log.info("Executing REST response filter");
		res.getHeaders().add("Access-Control-Allow-Origin", "*");
		res.getHeaders().add("Access-Control-Allow-Headers",
				"origin, content-type, accept, authorization, " + AppHttpHeaderNames.SERVICE_KEY + " , "
						+ AppHttpHeaderNames.AUTH_TOKEN + "," + AppHttpHeaderNames.VERSION + "," + AppHttpHeaderNames.KEY_WEBSOCKECT + "");
		res.getHeaders().add("Access-Control-Allow-Credentials", "true");
		res.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, HEAD");
		res.getHeaders().add("Access-Control-Max-Age", "1209600");
	}
}