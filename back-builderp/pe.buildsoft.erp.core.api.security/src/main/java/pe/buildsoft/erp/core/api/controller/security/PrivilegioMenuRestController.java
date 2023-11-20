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
import pe.buildsoft.erp.core.application.entidades.security.PrivilegioMenuDTO;
import pe.buildsoft.erp.core.application.interfaces.security.SeguridadAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;

/**
 * La Class PrivilegioMenuRestController.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Mon Oct 16 14:31:02 COT 2017
 * @since SIAA-CORE 2.1
 */
@Path("/privilegioMenu")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PrivilegioMenuRestController extends GenericServiceRestImpl implements IPrivilegioMenuRestController {

	private Logger log = LoggerFactory.getLogger(PrivilegioMenuRestController.class);

	@Inject
	private SeguridadAppServiceLocal servicioApp;

	@POST
	public Response crear(PrivilegioMenuDTO obj) {
		return controladorAccion(obj, AccionType.CREAR);
	}

	@PUT
	@Path("/{id}")
	public Response modificar(PrivilegioMenuDTO obj) {
		return controladorAccion(obj, AccionType.MODIFICAR);
	}

	@DELETE
	@Path("/{id}")
	public Response eliminar(@PathParam("id") String id) {
		var obj = new PrivilegioMenuDTO();
		obj.setIdPrivilegioMenu(id);
		return controladorAccion(obj, AccionType.ELIMINAR);
	}

	private Response controladorAccion(PrivilegioMenuDTO obj, AccionType accionType) {
		var resultado = new RespuestaWSVO<PrivilegioMenuDTO>();
		try {
			resultado.setObjetoResultado(servicioApp.controladorAccionPrivilegioMenu(obj, accionType));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, accionType);
	}

	@GET
	@Path("/{id}")
	public Response finById(@PathParam("id") String id) {
		var obj = new PrivilegioMenuDTO();
		obj.setIdPrivilegioMenu(id);
		return controladorAccion(obj, AccionType.FIND_BY_ID);
	}

	@GET
	public Response paginador(@Context HttpHeaders headers, @Context UriInfo info) {
		var resultado = new RespuestaWSVO<PrivilegioMenuDTO>();
		try {
			var filtro = to(info);
			if (filtro.getStartRow() == 0 || resultado.getContador() == 0)
				resultado.setContador(servicioApp.contarListarPrivilegioMenu(filtro));

			if (resultado.isData() || filtro.getStartRow() > 0) {
				resultado.setListaResultado(servicioApp.listarPrivilegioMenu(filtro));
			}
		} catch (Exception e) {
			log.error("paginador", e);
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null, info);
	}

}