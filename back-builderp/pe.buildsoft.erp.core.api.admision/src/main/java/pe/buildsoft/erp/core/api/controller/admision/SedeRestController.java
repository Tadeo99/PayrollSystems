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
import pe.buildsoft.erp.core.api.controller.interfaces.admision.ISedeRestController;
import pe.buildsoft.erp.core.application.entidades.admision.SedeDTO;
import pe.buildsoft.erp.core.application.interfaces.admision.AdmisionAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.state.EstadoGeneralState;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;

/**
 * La Class SedeRestController.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Wed Oct 21 20:17:20 COT 2020
 * @since BUILDERP-CORE 2.1
 */
@Path("/sede")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SedeRestController extends GenericServiceRestImpl implements ISedeRestController {

	private Logger log = LoggerFactory.getLogger(SedeRestController.class);

	@Inject
	private AdmisionAppServiceLocal servicioApp;

	@POST
	public Response crear(SedeDTO obj) {
		return controladorAccion(obj, AccionType.CREAR);
	}

	@PUT
	@Path("/{id}")
	public Response modificar(SedeDTO obj) {
		return controladorAccion(obj, AccionType.MODIFICAR);
	}

	@DELETE
	@Path("/{id}")
	public Response eliminar(@PathParam("id") String id) {
		SedeDTO obj = new SedeDTO();
		obj.setIdSede(id);
		return controladorAccion(obj, AccionType.ELIMINAR);
	}

	private Response controladorAccion(SedeDTO obj, AccionType accionType) {
		RespuestaWSVO<SedeDTO> resultado = new RespuestaWSVO<>();
		try {
			resultado.setObjetoResultado(servicioApp.controladorAccionSede(obj, accionType));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, accionType);
	}

	@GET
	@Path("/{id}")
	public Response finById(@PathParam("id") String id) {
		SedeDTO filtro = new SedeDTO();
		filtro.setIdSede(id);
		return controladorAccion(filtro, AccionType.FIND_BY_ID);
	}

	@GET
	public Response paginador(@Context HttpHeaders headers, @Context UriInfo info) {
		RespuestaWSVO<SedeDTO> resultado = new RespuestaWSVO<>();
		try {
			var filtro = to(info);
			if (filtro.getStartRow() == 0 || resultado.getContador() == 0)
				resultado.setContador(servicioApp.contarListarSede(filtro));
			if (resultado.isData() || filtro.getStartRow() > 0) {
				resultado.setListaResultado(servicioApp.listarSede(filtro));
			}
		} catch (Exception e) {
			log.error("paginador", e);
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null, info);
	}

	@GET
	@Path("/vacantes/{idUbigeo}")
	public Response vacantesDisponibles(@PathParam("idUbigeo") String idUbigeo) {
		RespuestaWSVO<SedeDTO> resultado = new RespuestaWSVO<>();
		BaseSearch sede = new BaseSearch();
		sede.setEstado(EstadoGeneralState.ACTIVO.getKey());
		sede.setIdUbigeo(idUbigeo);
		try {
			resultado.setListaResultado(servicioApp.vacantesDisponibles(sede));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null);
	}

}