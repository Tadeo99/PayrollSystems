package pe.buildsoft.erp.core.api.controller.escalafon;

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
import pe.buildsoft.erp.core.application.entidades.escalafon.CarreraDTO;
import pe.buildsoft.erp.core.application.interfaces.escalafon.EscalafonAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;

/**
 * La Class CarreraRestController.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:12 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Path("/carrera")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CarreraRestController extends GenericServiceRestImpl implements ICarreraRestController {

	private Logger log = LoggerFactory.getLogger(CarreraRestController.class);

	@Inject
	private EscalafonAppServiceLocal servicioApp;

	@POST
	public Response crear(CarreraDTO obj) {
		return controladorAccion(obj, AccionType.CREAR);
	}

	@PUT
	@Path("/{id}")
	public Response modificar(CarreraDTO obj) {
		return controladorAccion(obj, AccionType.MODIFICAR);
	}

	@GET
	@Path("/{id}")
	public Response eliminar(@PathParam("id") Long id) {
		var obj = new CarreraDTO();
		obj.setIdCarrera(id);
		return controladorAccion(obj, AccionType.ELIMINAR);
	}

	private Response controladorAccion(CarreraDTO obj, AccionType accionType) {
		var resultado = new RespuestaWSVO<CarreraDTO>();
		try {
			resultado.setObjetoResultado(servicioApp.controladorAccionCarrera(obj, accionType));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, accionType);
	}

	@GET
	@Path("/{id}")
	public Response finById(@PathParam("id") Long id) {
		var filtro = new CarreraDTO();
		filtro.setIdCarrera(id);
		return controladorAccion(filtro, AccionType.FIND_BY_ID);
	}

	@GET
	public Response paginador(@Context HttpHeaders headers, @Context UriInfo info) {
		var resultado = new RespuestaWSVO<CarreraDTO>();
		try {
			var filtro = to(info);
			if (filtro.getStartRow() == 0 || resultado.getContador() == 0)
				resultado.setContador(servicioApp.contarListarCarrera(filtro));
			if (resultado.isData() || filtro.getStartRow() > 0) {
				resultado.setListaResultado(servicioApp.listarCarrera(filtro));
			}
		} catch (Exception e) {
			log.error("paginador", e);
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null, info);
	}

	// TODO:VER NATAN NO USAR ESTO
	@POST
	@Path("/findCarrera")
	public Response findCarrera(CarreraDTO filtro) {
		var resultado = new RespuestaWSVO<CarreraDTO>();
		try {
			resultado.setObjetoResultado(servicioApp.findCarrera(filtro));
		} catch (Exception e) {
			log.error("findCarrera", e);
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null);
	}
}