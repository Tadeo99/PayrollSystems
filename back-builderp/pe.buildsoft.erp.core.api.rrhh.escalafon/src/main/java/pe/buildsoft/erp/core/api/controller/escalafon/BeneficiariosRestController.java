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
import pe.buildsoft.erp.core.application.entidades.escalafon.BeneficiariosDTO;
import pe.buildsoft.erp.core.application.entidades.escalafon.PersonalDTO;
import pe.buildsoft.erp.core.application.interfaces.escalafon.EscalafonAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;

/**
 * La Class BeneficiariosRestController.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:12 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Path("/personal")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BeneficiariosRestController extends GenericServiceRestImpl implements IBeneficiariosRestController {

	private Logger log = LoggerFactory.getLogger(BeneficiariosRestController.class);

	@Inject
	private EscalafonAppServiceLocal servicioApp;

	@POST
	@Path("/{idPersonal}/beneficiario")
	public Response crear(@PathParam("idPersonal") String idPersonal, BeneficiariosDTO obj) {
		return controladorAccion(idPersonal, obj, AccionType.CREAR);
	}

	@PUT
	@Path("/{idPersonal}/beneficiario/{id}")
	public Response modificar(@PathParam("idPersonal") String idPersonal, BeneficiariosDTO obj) {
		return controladorAccion(idPersonal, obj, AccionType.MODIFICAR);
	}

	@DELETE
	@Path("/{idPersonal}/beneficiario/{id}")
	public Response eliminar(@PathParam("idPersonal") String idPersonal, @PathParam("id") String id) {
		var obj = new BeneficiariosDTO();
		obj.setIdBeneficiario(id);
		return controladorAccion(idPersonal, obj, AccionType.ELIMINAR);
	}

	private Response controladorAccion(String idPersonal, BeneficiariosDTO obj, AccionType accionType) {
		var resultado = new RespuestaWSVO<BeneficiariosDTO>();
		try {
			obj.setPersonal(new PersonalDTO());
			obj.getPersonal().setIdPersonal(idPersonal);
			resultado.setObjetoResultado(servicioApp.controladorAccionBeneficiarios(obj, accionType));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, accionType);
	}

	@GET
	@Path("/{idPersonal}/beneficiario/{id}")
	public Response finById(@PathParam("idPersonal") String idPersonal, @PathParam("id") String id) {
		var filtro = new BeneficiariosDTO();
		filtro.setIdBeneficiario(id);
		return controladorAccion(idPersonal, filtro, AccionType.FIND_BY_ID);
	}

	@GET
	@Path("/{idPersonal}/beneficiarios")
	public Response paginador(@PathParam("idPersonal") String idPersonal, @Context HttpHeaders headers,
			@Context UriInfo info) {
		var resultado = new RespuestaWSVO<BeneficiariosDTO>();
		try {
			var filtro = to(info);
			filtro.setId(idPersonal);
			if (filtro.getStartRow() == 0 || resultado.getContador() == 0)
				resultado.setContador(servicioApp.contarListarBeneficiarios(filtro));
			if (resultado.isData() || filtro.getStartRow() > 0) {
				resultado.setListaResultado(servicioApp.listarBeneficiarios(filtro));
			}
		} catch (Exception e) {
			log.error("paginador", e);
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null, info);
	}

}