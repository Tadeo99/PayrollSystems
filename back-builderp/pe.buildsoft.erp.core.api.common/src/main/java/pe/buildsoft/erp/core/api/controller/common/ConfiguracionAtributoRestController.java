package pe.buildsoft.erp.core.api.controller.common;

import java.util.ArrayList;
import java.util.List;

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
import pe.buildsoft.erp.core.application.entidades.common.ConfiguracionAtributoDTO;
import pe.buildsoft.erp.core.application.interfaces.common.CommonAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;

/**
 * La Class ConfiguracionAtributoRestController.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Dec 28 12:02:21 COT 2017
 * @since SIAA-CORE 2.1
 */
@Path("/configuracionAtributo")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ConfiguracionAtributoRestController extends GenericServiceRestImpl implements IConfiguracionAtributoRestController {

	private Logger log = LoggerFactory.getLogger(ConfiguracionAtributoRestController.class);

	@Inject
	private CommonAppServiceLocal servicioApp;

	@POST
	public Response crear(List<ConfiguracionAtributoDTO> listaObj) {
		RespuestaWSVO<String> resultado = new RespuestaWSVO<>();
		try {
			servicioApp.registrarConfiguracionAtributoValue(listaObj);
			resultado.setListaResultado(new ArrayList<>());
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null);
	}

	@POST
	@Path("/eliminacion")
	public Response eliminarConfiguracionAtributoValue(List<ConfiguracionAtributoDTO> filtro) {
		RespuestaWSVO<String> resultado = new RespuestaWSVO<>();
		try {
			servicioApp.eliminarConfiguracionAtributoValue(filtro);
			resultado.setListaResultado(new ArrayList<>());
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null);
	}

	@POST
	public Response crear(ConfiguracionAtributoDTO obj) {
		return controladorAccion(obj, AccionType.CREAR);
	}

	@PUT
	@Path("/{id}")
	public Response modificar(ConfiguracionAtributoDTO obj) {
		return controladorAccion(obj, AccionType.MODIFICAR);
	}

	@DELETE
	@Path("/{id}")
	public Response eliminar(@PathParam("id") String id) {
		ConfiguracionAtributoDTO obj = new ConfiguracionAtributoDTO();
		obj.setIdConfiguracionAtributo(id);
		return controladorAccion(obj, AccionType.ELIMINAR);
	}

	private Response controladorAccion(ConfiguracionAtributoDTO obj, AccionType accionType) {
		RespuestaWSVO<ConfiguracionAtributoDTO> resultado = new RespuestaWSVO<>();
		try {
			resultado.setObjetoResultado(servicioApp.controladorAccionConfiguracionAtributo(obj, accionType));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, accionType);
	}

	@GET
	@Path("/{id}")
	public Response finById(@PathParam("id") String id) {
		ConfiguracionAtributoDTO obj = new ConfiguracionAtributoDTO();
		obj.setIdConfiguracionAtributo(id);
		return controladorAccion(obj, AccionType.FIND_BY_ID);
	}

	@GET
	public Response paginador(@Context HttpHeaders headers, @Context UriInfo info) {
		RespuestaWSVO<ConfiguracionAtributoDTO> resultado = new RespuestaWSVO<>();
		try {
			var filtro = to(info);
			if (filtro.getStartRow() == 0)
				resultado.setContador(servicioApp.contarListarConfiguracionAtributo(filtro));

			if (resultado.isData() || filtro.getStartRow() > 0) {
				resultado.setListaResultado(servicioApp.listarConfiguracionAtributo(filtro));
			}
		} catch (Exception e) {
			log.error("paginador", e);
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null);
	}

}