package pe.buildsoft.erp.core.api.controller.hora;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import pe.buildsoft.erp.core.application.entidades.hora.RegistroHoraDTO;
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
 * @since BuildErp 1.0
 */
@Path("/registroHora")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RegistroHoraRestController extends GenericServiceRestImpl implements IRegistroHoraRestController {

	private Logger log = LoggerFactory.getLogger(RegistroHoraRestController.class);

	@Inject
	private RegistroHoraAppServiceLocal servicioApp;

	@POST
	public Response crear(RegistroHoraDTO obj) {
		return registrarHora(obj, AccionType.CREAR);
	}

	@PUT
	@Path("/{id}")
	public Response modificar(RegistroHoraDTO obj) {
		return registrarHora(obj, AccionType.MODIFICAR);
	}

	private Response registrarHora(RegistroHoraDTO obj, AccionType accionType) {
		RespuestaWSVO<RegistroHoraDTO> resultado = new RespuestaWSVO<>();
		try {
			resultado.setObjetoResultado(servicioApp.registrarHora(obj, accionType));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, accionType);
	}

	@GET
	public Response paginador(@Context HttpHeaders headers, @Context UriInfo info) {
		RespuestaWSVO<RegistroHoraDTO> resultado = new RespuestaWSVO<>();
		try {
			BaseSearch data = to(info);
			if (data.getStartRow() == 0) {
				resultado.setContador(servicioApp.contarListarRegistroHora(data));
			}
			if (resultado.isData() || data.getStartRow() > 0) {
				resultado.setListaResultado(servicioApp.listarRegistroHora(data));
			}
		} catch (Exception e) {
			log.error("paginador", e);
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null);
	}

	protected BaseSearch to(@Context UriInfo info) {
		BaseSearch resultado = toRest(info, BaseSearch.class);
		if (info.getQueryParameters().containsKey("estadoList")) {
			resultado.setListaEstado(info.getQueryParameters().get("estadoList"));
		}
		if (info.getQueryParameters().containsKey("idPeriodo")) {
			String obj = info.getQueryParameters().get("idPeriodo").get(0);
			resultado.setIdPeriodo(obj);
		}
		if (info.getQueryParameters().containsKey("idPersonal")) {
			String obj = info.getQueryParameters().getFirst("idPersonal");
			resultado.setIdPersonal(obj);
		}
		return resultado;
	}
}