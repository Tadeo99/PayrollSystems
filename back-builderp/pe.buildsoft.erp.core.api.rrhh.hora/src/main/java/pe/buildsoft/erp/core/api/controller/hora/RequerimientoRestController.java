package pe.buildsoft.erp.core.api.controller.hora;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
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
import pe.buildsoft.erp.core.application.entidades.hora.RequerimientoDTO;
import pe.buildsoft.erp.core.application.interfaces.hora.RegistroHoraAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;

/**
 * La Class RegistroCabeceraRestController.
 * <ul>
 * <li>Copyright 2021 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Thu Feb 24 15:16:44 COT 2022
 * @since BuildErp 1.0registroCabeceraRestController
 */
@Path("/requerimiento")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RequerimientoRestController extends GenericServiceRestImpl implements IRequerimientoRestController {

	private Logger log = LoggerFactory.getLogger(RequerimientoRestController.class);

	@Inject
	private RegistroHoraAppServiceLocal servicioApp;

	@POST
	public Response crear(RequerimientoDTO obj) {
		return controladorAccion(obj, AccionType.CREAR);
	}

	@PUT
	@Path("/{id}")
	public Response modificar(RequerimientoDTO obj) {
		return controladorAccion(obj, AccionType.MODIFICAR);
	}

	@GET
	@Path("/{id}")
	public Response eliminar(@PathParam("id") String id) {
		RequerimientoDTO requerimiento = new RequerimientoDTO();
		requerimiento.setIdRequerimiento(id);
		return controladorAccion(requerimiento, AccionType.ELIMINAR);
	}

	private Response controladorAccion(RequerimientoDTO obj, AccionType accionType) {
		RespuestaWSVO<RequerimientoDTO> resultado = new RespuestaWSVO<>();
		try {
			resultado.setObjetoResultado(servicioApp.controladorAccionRequerimiento(obj, accionType));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, accionType);
	}

	@GET
	public Response paginador(@Context HttpHeaders headers, @Context UriInfo info) {
		RespuestaWSVO<RequerimientoDTO> resultado = new RespuestaWSVO<>();
		try {
			BaseSearch data = to(info);
			if (data.getStartRow() == 0)
				resultado.setContador(servicioApp.contarListarRequerimiento(data));
			if (resultado.isData() || data.getStartRow() > 0) {
				resultado.setListaResultado(servicioApp.listarRequerimiento(data));
			}
		} catch (Exception e) {
			log.error("paginador",e);
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null);
	}

}