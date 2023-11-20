package pe.buildsoft.erp.core.api.controller.admision;

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
import pe.buildsoft.erp.core.api.controller.interfaces.admision.IApoderadoRestController;
import pe.buildsoft.erp.core.application.entidades.admision.ApoderadoDTO;
import pe.buildsoft.erp.core.application.interfaces.admision.AdmisionAppServiceLocal;
import pe.buildsoft.erp.core.domain.entidades.admision.vo.BaseSearchVO;
import pe.buildsoft.erp.core.infra.transversal.cache.ICache;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;

/**
 * La Class ApoderadoRestController.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Wed Oct 21 20:17:19 COT 2020
 * @since BUILDERP-CORE 2.1
 */
@Path("/apoderado")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ApoderadoRestController extends GenericServiceRestImpl implements IApoderadoRestController {

	private Logger log = LoggerFactory.getLogger(ApoderadoRestController.class);

	@Inject
	private AdmisionAppServiceLocal servicioApp;

	@Inject
	private ICache cacheUtil;

	@POST
	public Response crear(@Context HttpHeaders headers, ApoderadoDTO obj) {
		return controladorAccion(headers, obj, AccionType.CREAR);
	}

	@PUT
	@Path("/{id}")
	public Response modificar(@Context HttpHeaders headers, ApoderadoDTO obj) {
		return controladorAccion(headers, obj, AccionType.MODIFICAR);
	}

	@DELETE
	@Path("/{id}")
	public Response eliminar(@Context HttpHeaders headers, @PathParam("id") String id) {
		ApoderadoDTO obj = new ApoderadoDTO();
		obj.setIdApoderado(id);
		return controladorAccion(headers, obj, AccionType.ELIMINAR);
	}

	private Response controladorAccion(@Context HttpHeaders headers, ApoderadoDTO obj, AccionType accionType) {
		RespuestaWSVO<ApoderadoDTO> resultado = new RespuestaWSVO<>();
		try {
			obj.setAuthToken(getToken(headers));
			obj.setIdEntidadSelect(cacheUtil.getEntidadSelect(obj.getAuthToken()));
			resultado.setObjetoResultado(servicioApp.controladorAccionApoderado(obj, accionType));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, accionType);
	}

	@GET
	@Path("/{id}")
	public Response finById(@Context HttpHeaders headers, @PathParam("id") String id) {
		ApoderadoDTO filtro = new ApoderadoDTO();
		filtro.setIdApoderado(id);
		return controladorAccion(headers, filtro, AccionType.FIND_BY_ID);
	}

	@GET
	public Response paginador(@Context HttpHeaders headers, @Context UriInfo info) {
		RespuestaWSVO<ApoderadoDTO> resultado = new RespuestaWSVO<>();
		try {
			BaseSearchVO filtro = toRest(info, BaseSearchVO.class);
			if (filtro.getStartRow() == 0 || resultado.getContador() == 0)
				resultado.setContador(servicioApp.contarListarApoderado(filtro));
			if (resultado.isData() || filtro.getStartRow() > 0) {
				resultado.setListaResultado(servicioApp.listarApoderado(filtro));
			}
		} catch (Exception e) {
			log.error("paginador", e);
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null, info);
	}

}