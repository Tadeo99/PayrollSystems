package pe.buildsoft.erp.core.api.controller.hora;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import pe.buildsoft.erp.core.application.entidades.hora.RequerimientoPersonalDTO;
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
@Path("/requerimientoPersonal")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RequerimientoPersonalRestController extends GenericServiceRestImpl
		implements IRequerimientoPersonalRestController {

	private Logger log = LoggerFactory.getLogger(RequerimientoPersonalRestController.class);

	@Inject
	private RegistroHoraAppServiceLocal servicioApp;

	@POST
	public Response crear(RequerimientoPersonalDTO obj) {
		return controladorAccion(obj, AccionType.CREAR);
	}

	private Response controladorAccion(RequerimientoPersonalDTO obj, AccionType accionType) {
		RespuestaWSVO<RequerimientoPersonalDTO> resultado = new RespuestaWSVO<>();
		try {
			resultado.setObjetoResultado(servicioApp.controladorAccionRequerimientoPersonal(obj, accionType));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, accionType);
	}

	@GET
	@Path("/{id}")
	public Response eliminar(@PathParam("id") String id) {
		RequerimientoPersonalDTO obj = new RequerimientoPersonalDTO();
		obj.setIdRequerimientoPersonal(id);
		return controladorAccion(obj, AccionType.ELIMINAR);
	}

	@GET
	public Response paginador(@Context HttpHeaders headers, @Context UriInfo info) {
		RespuestaWSVO<RequerimientoPersonalDTO> resultado = new RespuestaWSVO<>();
		try {
			BaseSearch data = to(info);
			if (data.getStartRow() == 0)
				resultado.setContador(servicioApp.contarListarRequerimientoPersonal(data));

			if (resultado.isData() || data.getStartRow() > 0) {
				resultado.setListaResultado(servicioApp.listarRequerimientoPersonal(data));
			}
		} catch (Exception e) {
			log.error("paginador",e);
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null);
	}

	protected BaseSearch to(@Context UriInfo info) {
		BaseSearch resultado = toRest(info, BaseSearch.class);
		if (info.getQueryParameters().containsKey("idPeriodo")) {
			String obj = info.getQueryParameters().getFirst("idPeriodo");
			resultado.setIdPeriodo(obj);
		}
		if (info.getQueryParameters().containsKey("idPersonal")) {
			String obj = info.getQueryParameters().getFirst("idPersonal");
			resultado.setIdPersonal(obj);
		}
		return resultado;
	}

}