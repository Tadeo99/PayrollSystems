package pe.buildsoft.erp.core.api.controller.escalafon;

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
import pe.buildsoft.erp.core.application.entidades.escalafon.CuentaBancariaPersonalDTO;
import pe.buildsoft.erp.core.application.entidades.escalafon.PersonalDTO;
import pe.buildsoft.erp.core.application.interfaces.escalafon.EscalafonAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;

/**
 * La Class CuentaBancariaPersonalRestController.
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
public class CuentaBancariaPersonalRestController extends GenericServiceRestImpl
		implements ICuentaBancariaPersonalRestController {

	private Logger log = LoggerFactory.getLogger(CuentaBancariaPersonalRestController.class);

	@Inject
	private EscalafonAppServiceLocal servicioApp;

	@POST
	@Path("/{idPersonal}/cuentaBancaria")
	public Response crear(@PathParam("idPersonal") String idPersonal, CuentaBancariaPersonalDTO obj) {
		return controladorAccion(idPersonal, obj, AccionType.CREAR);
	}

	@PUT
	@Path("/{idPersonal}/cuentaBancaria/{id}")
	public Response modificar(@PathParam("idPersonal") String idPersonal, CuentaBancariaPersonalDTO obj) {
		return controladorAccion(idPersonal, obj, AccionType.MODIFICAR);
	}

	@DELETE
	@Path("/{idPersonal}/cuentaBancaria/{id}")
	public Response eliminar(@PathParam("idPersonal") String idPersonal, @PathParam("id") String id) {
		var obj = new CuentaBancariaPersonalDTO();
		obj.setIdCuentaBancariaPersonal(id);
		return controladorAccion(idPersonal, obj, AccionType.ELIMINAR);
	}

	private Response controladorAccion(String idPersonal, CuentaBancariaPersonalDTO obj, AccionType accionType) {
		var resultado = new RespuestaWSVO<CuentaBancariaPersonalDTO>();
		try {
			obj.setPersonal(new PersonalDTO());
			obj.getPersonal().setIdPersonal(idPersonal);
			resultado.setObjetoResultado(servicioApp.controladorAccionCuentaBancariaPersonal(obj, accionType));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, accionType);
	}

	@GET
	@Path("/{idPersonal}/cuentaBancaria/{id}")
	public Response finById(@PathParam("idPersonal") String idPersonal, @PathParam("id") String id) {
		var filtro = new CuentaBancariaPersonalDTO();
		filtro.setIdCuentaBancariaPersonal(id);
		return controladorAccion(idPersonal, filtro, AccionType.FIND_BY_ID);
	}

	@GET
	@Path("/{idPersonal}/cuentaBancarias")
	public Response paginador(@PathParam("idPersonal") String idPersonal, @Context HttpHeaders headers,
			@Context UriInfo info) {
		var resultado = new RespuestaWSVO<CuentaBancariaPersonalDTO>();
		try {
			var filtro = to(info);
			if (filtro.getStartRow() == 0)
				resultado.setContador(servicioApp.contarListarCuentaBancariaPersonal(filtro));
			if (resultado.isData() || filtro.getStartRow() > 0) {
				resultado.setListaResultado(servicioApp.listarCuentaBancariaPersonal(filtro));
			}
		} catch (Exception e) {
			log.error("paginador", e);
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null);
	}
}