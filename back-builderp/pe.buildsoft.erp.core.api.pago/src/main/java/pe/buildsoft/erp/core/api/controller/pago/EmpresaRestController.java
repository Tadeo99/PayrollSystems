package pe.buildsoft.erp.core.api.controller.pago;

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
import pe.buildsoft.erp.core.application.entidades.escalafon.EmpresaDTO;
import pe.buildsoft.erp.core.application.interfaces.pago.PagoAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;

/**
 * La Class EmpresaRestController.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 21:45:31 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Path("/empresa")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EmpresaRestController extends GenericServiceRestImpl implements IEmpresaRestController {

	private Logger log = LoggerFactory.getLogger(EmpresaRestController.class);

	@Inject
	private PagoAppServiceLocal servicioApp;

	@POST
	public Response crear(EmpresaDTO obj) {
		return controladorAccion(obj, AccionType.CREAR);
	}

	@PUT
	@Path("/{id}")
	public Response modificar(EmpresaDTO obj) {
		return controladorAccion(obj, AccionType.MODIFICAR);
	}

	@DELETE
	@Path("/{id}")
	public Response eliminar(@PathParam("id") Long id) {
		var obj = new EmpresaDTO();
		obj.setIdEmpresa(id);
		return controladorAccion(obj, AccionType.ELIMINAR);
	}

	private Response controladorAccion(EmpresaDTO obj, AccionType accionType) {
		var resultado = new RespuestaWSVO<EmpresaDTO>();
		try {
			resultado.setObjetoResultado(servicioApp.controladorAccionEmpresa(obj, accionType));
		} catch (Exception e) {
			// parsearResultadoError(e, resultado);
			resultado.setError(true);
			resultado.setMensajeError("No se puede eliminar ==> Empresa esta siendo utilizada ! ");
		}
		return respuesta(resultado, accionType);
	}

	@GET
	@Path("/{id}")
	public Response finById(@PathParam("id") Long id) {
		var filtro = new EmpresaDTO();
		filtro.setIdEmpresa(id);
		return controladorAccion(filtro, AccionType.FIND_BY_ID);
	}

	@GET
	public Response paginador(@Context HttpHeaders headers, @Context UriInfo info) {
		var resultado = new RespuestaWSVO<EmpresaDTO>();
		try {
			var filtro = to(info);
			if (filtro.getStartRow() == 0 || resultado.getContador() == 0)
				resultado.setContador(servicioApp.contarListarEmpresa(filtro));
			if (resultado.isData() || filtro.getStartRow() > 0) {
				resultado.setListaResultado(servicioApp.listarEmpresa(filtro));
			}
		} catch (Exception e) {
			log.error("paginador", e);
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null, info);
	}

	@POST
	@Path("/find")
	public Response find(EmpresaDTO filtro) {
		var resultado = new RespuestaWSVO<EmpresaDTO>();
		try {
			resultado.setObjetoResultado(servicioApp.findEmpresa(filtro));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null);
	}

}