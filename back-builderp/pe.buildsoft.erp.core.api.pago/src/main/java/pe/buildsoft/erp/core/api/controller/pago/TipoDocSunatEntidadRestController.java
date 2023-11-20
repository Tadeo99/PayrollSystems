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
import pe.buildsoft.erp.core.application.entidades.pago.TipoDocSunatEntidadDTO;
import pe.buildsoft.erp.core.application.interfaces.pago.PagoAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;

/**
 * La Class TipoDocSunatEntidadRestController.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 21:45:31 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Path("/tipoDocSunatEntidad")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TipoDocSunatEntidadRestController extends GenericServiceRestImpl
		implements ITipoDocSunatEntidadRestController {

	private Logger log = LoggerFactory.getLogger(TipoDocSunatEntidadRestController.class);

	@Inject
	private PagoAppServiceLocal servicioApp;

	@POST
	public Response crear(TipoDocSunatEntidadDTO obj) {
		return controladorAccion(obj, AccionType.CREAR);
	}

	@PUT
	@Path("/{id}")
	public Response modificar(TipoDocSunatEntidadDTO obj) {
		return controladorAccion(obj, AccionType.MODIFICAR);
	}

	@DELETE
	@Path("/{id}")
	public Response eliminar(@PathParam("id") String id) {
		TipoDocSunatEntidadDTO obj = new TipoDocSunatEntidadDTO();
		obj.setIdTipoDocSunatEntidad(id);
		return controladorAccion(obj, AccionType.ELIMINAR);
	}

	private Response controladorAccion(TipoDocSunatEntidadDTO obj, AccionType accionType) {
		RespuestaWSVO<TipoDocSunatEntidadDTO> resultado = new RespuestaWSVO<>();
		try {
			boolean validar = true;
			if (accionType.equals(AccionType.CREAR) || accionType.equals(AccionType.MODIFICAR)) {
				TipoDocSunatEntidadDTO objValidar = servicioApp.findTipoDocSunatEntidad(obj);
				String id = obj.getIdTipoDocSunatEntidad();
				if (id == null) {
					id = "";
				}
				String idValidar = objValidar.getIdTipoDocSunatEntidad();
				if (idValidar == null) {
					idValidar = "";
				}
				if (!id.equals(idValidar)) {
					if (idValidar.length() > 0) {
						validar = false;
						resultado.setError(true);
						resultado.setCodigoError("999");
						resultado.setMensajeError("La " + objValidar.getItemByTipoDocSunat().getNombre() + " con Serie  "
								+ obj.getSerie() + " ya Existe");
					}
				}
			}
			if (validar) {
				resultado.setObjetoResultado(servicioApp.controladorAccionTipoDocSunatEntidad(obj, accionType));
			}

		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, accionType);
	}

	@GET
	@Path("/{id}")
	public Response finById(@PathParam("id") String id) {
		TipoDocSunatEntidadDTO filtro = new TipoDocSunatEntidadDTO();
		filtro.setIdTipoDocSunatEntidad(id);
		return controladorAccion(filtro, AccionType.FIND_BY_ID);
	}

	@GET
	public Response paginador(@Context HttpHeaders headers, @Context UriInfo info) {
		RespuestaWSVO<TipoDocSunatEntidadDTO> resultado = new RespuestaWSVO<>();
		try {
			var filtro = to(info);
			if (filtro.getStartRow() == 0 || resultado.getContador() == 0)
				resultado.setContador(servicioApp.contarListarTipoDocSunatEntidad(filtro));
			if (resultado.isData() || filtro.getStartRow() > 0) {
				resultado.setListaResultado(servicioApp.listarTipoDocSunatEntidad(filtro));
			}
		} catch (Exception e) {
			log.error("paginador", e);
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null,info);
	}

	@POST
	@Path("/listarTipoDocByItem")
	public Response listarTipoDocByItem(TipoDocSunatEntidadDTO filtro) {
		RespuestaWSVO<TipoDocSunatEntidadDTO> resultado = new RespuestaWSVO<>();
		try {
			resultado.setListaResultado(servicioApp.listarTipoDocByItem(filtro));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null);
	}

}