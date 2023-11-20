package pe.buildsoft.erp.core.api.controller.aas;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HEAD;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.buildsoft.erp.core.application.cache.IAppAuthenticator;
import pe.buildsoft.erp.core.application.cache.ICacheAppUtil;
import pe.buildsoft.erp.core.application.entidades.aas.vo.NavigationItemVO;
import pe.buildsoft.erp.core.application.entidades.aas.vo.NavigationVO;
import pe.buildsoft.erp.core.application.interfaces.aas.AasAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;
import pe.buildsoft.erp.core.infra.transversal.utilitario.AppHttpHeaderNames;

/**
 * La Class NavegationRestController.
 * <ul>
 * <li>Copyright 2016 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Mon Jan 16 21:01:47 COT 2017
 * @since ERP-CRIS 1.0
 */
@Path("/navigation")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class NavigationRestController extends GenericServiceRestImpl /* implements ILoginRestController */ {
	private Logger log = LoggerFactory.getLogger(NavigationRestController.class);

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

	@GET
	public Response get(@Context HttpHeaders httpHeaders) {
		NavigationVO resultado = new NavigationVO();
		String serviceKey = httpHeaders.getRequestHeaders().getFirst(AppHttpHeaderNames.SERVICE_KEY);
		String authToken = httpHeaders.getRequestHeaders().getFirst(AppHttpHeaderNames.AUTH_TOKEN);
		try {
			resultado.setDefaults(appAuthenticator.getUsuario(serviceKey, authToken).getListaMenu());
			getCompletar(resultado);
		} catch (Exception ex) {
			log.error("getUsuario", ex);
			return noAutorizado(resultado);
		}
		return ok(resultado);
	}

	private NavigationVO getCompletar(NavigationVO resul) throws Exception {
		List<NavigationItemVO> compact = new ArrayList<NavigationItemVO>();
		List<NavigationItemVO> futuristic = new ArrayList<NavigationItemVO>();
		List<NavigationItemVO> horizontal = new ArrayList<NavigationItemVO>();
		for (var obj : resul.getDefaults()) {
			NavigationItemVO objCompac = (NavigationItemVO) BeanUtils.cloneBean(obj);
			objCompac.setTooltip(objCompac.getTitle());
			objCompac.setType("aside");
			compact.add(objCompac);

			NavigationItemVO objFuturistic = (NavigationItemVO) BeanUtils.cloneBean(obj);
			objFuturistic.setTooltip(objCompac.getTitle());
			objFuturistic.setType("group");
			futuristic.add(objFuturistic);

			NavigationItemVO objHorizontal = (NavigationItemVO) BeanUtils.cloneBean(obj);
			objHorizontal.setTooltip(objCompac.getTitle());
			objHorizontal.setType("group");
			horizontal.add(objHorizontal);
		}
		resul.setCompact(compact);
		resul.setFuturistic(futuristic);
		resul.setHorizontal(horizontal);
		return resul;

	}

}