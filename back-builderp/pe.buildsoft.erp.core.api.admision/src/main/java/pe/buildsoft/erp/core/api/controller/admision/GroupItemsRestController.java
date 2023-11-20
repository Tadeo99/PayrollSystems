package pe.buildsoft.erp.core.api.controller.admision;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import pe.buildsoft.erp.core.api.controller.interfaces.admision.IGroupItemsRestController;
import pe.buildsoft.erp.core.application.interfaces.admision.AdmisionAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.SelectItemVO;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;

/**
 * La Class GroupItemsRestController.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Dec 14 00:27:45 COT 2017
 * @since SIAA-CORE 2.1
 */
@Path("/group/items")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GroupItemsRestController extends GenericServiceRestImpl implements IGroupItemsRestController {

	private Logger log = LoggerFactory.getLogger(GroupItemsRestController.class);

	@Inject
	private AdmisionAppServiceLocal servicioApp;

	@GET
	public Response paginador(@Context HttpHeaders headers, @Context UriInfo info) {
		RespuestaWSVO<Map<String, List<SelectItemVO>>> resultado = new RespuestaWSVO<>();
		List<String> listaIdItems = info.getQueryParameters().get("groupName");
		try {
			Map<String, List<SelectItemVO>> resul = new HashMap<>();
			for (var id : listaIdItems) {
				resul.put(id, servicioApp.listarSelectItem(id));
			}
			resultado.setObjetoResultado(resul);
		} catch (Exception e) {
			log.error("paginador", e);
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null, info);
	}
}