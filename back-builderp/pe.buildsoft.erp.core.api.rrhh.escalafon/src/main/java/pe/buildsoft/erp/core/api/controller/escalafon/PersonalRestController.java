package pe.buildsoft.erp.core.api.controller.escalafon;

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
import pe.buildsoft.erp.core.application.entidades.escalafon.PersonalDTO;
import pe.buildsoft.erp.core.application.interfaces.escalafon.EscalafonAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.cache.ICache;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;

/**
 * La Class PersonalRestController.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Path("/personal")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PersonalRestController extends GenericServiceRestImpl implements IPersonalRestController {

	private Logger log = LoggerFactory.getLogger(PersonalRestController.class);

	@Inject
	private EscalafonAppServiceLocal servicioApp;

	@Inject
	private ICache cacheUtil;

	@POST
	public Response crear(@Context HttpHeaders headers, PersonalDTO obj) {
		return controladorAccion(headers, obj, AccionType.CREAR);
	}

	@PUT
	@Path("/{id}")
	public Response modificar(@Context HttpHeaders headers, PersonalDTO obj) {
		return controladorAccion(headers, obj, AccionType.MODIFICAR);
	}

	@DELETE
	@Path("/{id}")
	public Response eliminar(@Context HttpHeaders headers, @PathParam("id") String id) {
		var obj = new PersonalDTO();
		obj.setIdPersonal(id);
		return controladorAccion(headers, obj, AccionType.ELIMINAR);
	}

	private Response controladorAccion(@Context HttpHeaders headers, PersonalDTO obj, AccionType accionType) {
		var resultado = new RespuestaWSVO<PersonalDTO>();
		try {
			obj.setUsuarioSession(cacheUtil.getUserName(getToken(headers)));
			boolean validar = true;
			if (accionType.equals(AccionType.CREAR) || accionType.equals(AccionType.MODIFICAR)) {
				var personaValidar = servicioApp.findPersonal(obj);
				var idPersonal = obj.getIdPersonal();
				if (idPersonal == null) {
					idPersonal = "";
				}
				var idPersonalValidar = personaValidar.getIdPersonal();
				if (idPersonalValidar == null) {
					idPersonalValidar = "";
				}
				if (!idPersonal.equals(idPersonalValidar)) {
					if (personaValidar.getNroDoc() != null) {
						validar = false;
						resultado.setError(true);
						resultado.setMensajeError("El N° Documento  " + personaValidar.getNroDoc() + " ya Existe");
					}
				}
			}
			if (validar) {
				resultado.setObjetoResultado(servicioApp.controladorAccionPersonal(obj, accionType));
			}
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, accionType);
	}

	@GET
	@Path("/{id}")
	public Response finById(@Context HttpHeaders headers, @PathParam("id") String id) {
		var obj = new PersonalDTO();
		obj.setIdPersonal(id);
		return controladorAccion(headers, obj, AccionType.FIND_BY_ID);
	}

	@GET
	public Response paginador(@Context HttpHeaders headers, @Context UriInfo info) {
		var resultado = new RespuestaWSVO<PersonalDTO>();
		try {
			var filtro = to(info);
			List<String> listaIdPersonal = info.getQueryParameters().get("idPersonal");
			filtro.setListaIdPersonal(listaIdPersonal);
			if (filtro.getStartRow() == 0 || resultado.getContador() == 0)
				resultado.setContador(servicioApp.contarListarPersonal(filtro));
			if (resultado.isData() || filtro.getStartRow() > 0) {
				resultado.setListaResultado(servicioApp.listarPersonal(filtro));
			}
		} catch (Exception e) {
			log.error("paginador", e);
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null, info);
	}

	@GET
	@Path("/ids")
	public Response getIds(@Context HttpHeaders headers, @Context UriInfo info) {
		var resultado = new RespuestaWSVO<String>();
		try {
			var filtro = to(info);
			resultado.setListaResultado(servicioApp.listarPersonalIds(filtro));
		} catch (Exception e) {
			log.error("paginador", e);
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null, info);
	}

}