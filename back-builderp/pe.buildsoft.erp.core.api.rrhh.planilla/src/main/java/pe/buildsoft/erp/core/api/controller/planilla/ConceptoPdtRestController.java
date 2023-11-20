package pe.buildsoft.erp.core.api.controller.planilla;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HEAD;
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
import pe.buildsoft.erp.core.application.entidades.planilla.ConceptoPdtDTO;
import pe.buildsoft.erp.core.application.interfaces.planilla.PlanillaAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;

/**
 * La Class ConceptoPdtRestController.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Path("/conceptoPdt")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ConceptoPdtRestController extends GenericServiceRestImpl implements IConceptoPdtRestController {

	private Logger log = LoggerFactory.getLogger(ConceptoPdtRestController.class);

	@Inject
	private PlanillaAppServiceLocal servicio;

	/*
	 * @Resource(name = "procesarReglaUtil") ProcesarReglaUtil procesarReglaUtil;
	 */

	@HEAD
	public Response ping() {
		return ping("conceptoPdt");
	}

	@POST
	public Response crear(ConceptoPdtDTO obj) {
		return controladorAccion(obj, AccionType.CREAR);
	}

	/*
	 * @POST
	 * 
	 * @Path("/formula") public Response formula(String data) {
	 * RespuestaWSVO<ErrorValidacionVO> resultado = new RespuestaWSVO<>(); try {
	 * resultado.setListaResultado(procesarReglaUtil.validarReglas(data)); } catch
	 * (Exception e) { parsearResultadoError(e, resultado); } return
	 * respuesta(resultado, null); }
	 */

	@PUT
	@Path("/{id}")
	public Response modificar(ConceptoPdtDTO obj) {
		return controladorAccion(obj, AccionType.MODIFICAR);
	}

	@DELETE
	@Path("/{id}")
	public Response eliminar(@PathParam("id") String id) {
		var obj = new ConceptoPdtDTO();
		obj.setIdConceptoPdt(id);
		return controladorAccion(obj, AccionType.ELIMINAR);
	}

	private Response controladorAccion(ConceptoPdtDTO obj, AccionType accionType) {
		var resultado = new RespuestaWSVO<ConceptoPdtDTO>();
		try {
			resultado.setObjetoResultado(servicio.controladorAccionConceptoPdt(obj, accionType));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, accionType);
	}

	@GET
	@Path("/{id}")
	public Response finById(@PathParam("id") String id) {
		var filtro = new ConceptoPdtDTO();
		filtro.setIdConceptoPdt(id);
		return controladorAccion(filtro, AccionType.FIND_BY_ID);
	}

	@GET
	public Response paginador(@Context HttpHeaders headers, @Context UriInfo info) {
		var resultado = new RespuestaWSVO<ConceptoPdtDTO>();
		var filtro = to(info);
		try {
			if (filtro.getStartRow() == 0 || resultado.getContador() == 0) {
				resultado.setContador(servicio.contarListarConceptoPdt(filtro));
			}
			if (resultado.isData() || filtro.getStartRow() > 0) {
				resultado.setListaResultado(servicio.listarConceptoPdt(filtro));
			}
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null, info);
	}

	@POST // TODO:VER SI UTILIZAR
	@Path("/find")
	public Response find(ConceptoPdtDTO conceptoPdt) {
		var resultado = new RespuestaWSVO<ConceptoPdtDTO>();
		try {
			resultado.setObjetoResultado(servicio.findConceptoPdt(conceptoPdt));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null);
	}
}