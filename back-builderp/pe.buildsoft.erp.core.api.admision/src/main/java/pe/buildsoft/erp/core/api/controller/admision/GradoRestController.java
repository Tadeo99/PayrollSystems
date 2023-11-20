package pe.buildsoft.erp.core.api.controller.admision;

import java.util.List;
import java.util.Map;

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
import pe.buildsoft.erp.core.api.controller.interfaces.admision.IGradoRestController;
import pe.buildsoft.erp.core.application.entidades.admision.GradoDTO;
import pe.buildsoft.erp.core.application.interfaces.admision.AdmisionAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.SelectItemVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ObjectUtil;

/**
 * La Class GradoRestController.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:13 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Path("/grado")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GradoRestController extends GenericServiceRestImpl implements IGradoRestController {

	private Logger log = LoggerFactory.getLogger(GradoRestController.class);

	@Inject
	private AdmisionAppServiceLocal servicioApp;

	@POST
	public Response crear(GradoDTO obj) {
		return controladorAccion(obj, AccionType.CREAR);
	}

	@PUT
	@Path("/{id}")
	public Response modificar(GradoDTO obj) {
		return controladorAccion(obj, AccionType.MODIFICAR);
	}

	@DELETE
	@Path("/{id}")
	public Response eliminar(@PathParam("id") Long id) {
		GradoDTO obj = new GradoDTO();
		obj.setIdGrado(id);
		return controladorAccion(obj, AccionType.ELIMINAR);
	}

	private Response controladorAccion(GradoDTO obj, AccionType accionType) {
		RespuestaWSVO<GradoDTO> resultado = new RespuestaWSVO<>();
		try {
			resultado.setObjetoResultado(servicioApp.controladorAccionGrado(obj, accionType));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, accionType);
	}

	@GET
	@Path("/{id}")
	public Response finById(@PathParam("id") Long id) {
		GradoDTO filtro = new GradoDTO();
		filtro.setIdGrado(id);
		return controladorAccion(filtro, AccionType.FIND_BY_ID);
	}

	@GET
	public Response paginador(@Context HttpHeaders headers, @Context UriInfo info) {
		RespuestaWSVO<GradoDTO> resultado = new RespuestaWSVO<>();
		try {
			var filtro = to(info);
			if (filtro.getStartRow() == 0 || resultado.getContador() == 0)
				resultado.setContador(servicioApp.contarListarGrado(filtro));
			if (resultado.isData() || filtro.getStartRow() > 0) {
				resultado.setListaResultado(servicioApp.listarGrado(filtro));
			}
		} catch (Exception e) {
			log.error("paginador",e);
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null,info);
	}

	@GET
	@Path("/items")
	public Response listaItemSelectItemMap(@Context UriInfo info) {
		RespuestaWSVO<Map<Long, List<SelectItemVO>>> resultado = new RespuestaWSVO<>();
		var filtro = to(info);
		try {
			resultado.setObjetoResultado(servicioApp.listarGradoMap(filtro));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null);
	}

	protected BaseSearch to(@Context UriInfo info) {
		BaseSearch resultado = super.to(info);
		if (info.getQueryParameters().containsKey("idItem")) {
			Long idItem = ObjectUtil.objectToLong(info.getQueryParameters().get("idItem"));
			resultado.setIdNivel(idItem + "");
		}
		return resultado;
	}
}