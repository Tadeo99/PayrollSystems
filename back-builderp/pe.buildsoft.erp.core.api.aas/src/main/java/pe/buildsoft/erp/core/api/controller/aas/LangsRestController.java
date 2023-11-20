package pe.buildsoft.erp.core.api.controller.aas;

import java.util.HashMap;
import java.util.Map;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HEAD;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.buildsoft.erp.core.api.controller.interfaces.aas.ILangsRestController;
import pe.buildsoft.erp.core.application.cache.ICacheAppUtil;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ConstantesConfiguracion;


/**
 * La Class PropertiesRestController.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue Apr 18 13:43:33 COT 2017
 * @since SIAA-CORE 2.1
 */
@Path("/langs")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LangsRestController extends GenericServiceRestImpl implements ILangsRestController {
 
	
	@Inject
	private ICacheAppUtil cacheUtil;
	
	@HEAD
	public Response ping() {
		return ping("langs");
	}
	
	@GET
	@Path("/i18n/{langs}")
	public Response i18n(@PathParam("langs") String langs){
		Map<String,String> resultado = new HashMap<>();
		 try {
			 langs = langs.replace(".json", "");
			 resultado =  cacheUtil.getPropertiesIdeomaMap().get(langs);
		} catch (Exception e) {
			resultado.put("error.general", e.getMessage());
			return internalServerError(resultado);
		}
		return ok(resultado);
	}
	
	@GET
	@Path("/getProperties/{key}")
	public Response getProperties(@PathParam("key") String key){
		String resultado = "";
		 try {
			 String langs = ConstantesConfiguracion.ES_PE;
			 resultado =  cacheUtil.getPropertiesIdeomaMap().get(langs).get(key);
		} catch (Exception e) {
			resultado = ("error.general" + e.getMessage());
			return internalServerError(resultado);
		}
		 return ok(resultado);
	}

}