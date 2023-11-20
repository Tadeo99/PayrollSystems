package pe.buildsoft.erp.core.api.controller.common;

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
import pe.buildsoft.erp.core.application.entidades.common.UbigeoDTO;
import pe.buildsoft.erp.core.application.interfaces.common.CommonAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.type.TipoUbigeoType;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * La Class UbigeoRestController.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue Apr 18 13:43:34 COT 2017
 * @since SIAA-CORE 2.1
 */
@Path("/ubigeo")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class UbigeoRestController extends GenericServiceRestImpl implements IUbigeoRestController {

	private Logger log = LoggerFactory.getLogger(UbigeoRestController.class);

	@Inject
	private CommonAppServiceLocal servicioApp;

	@POST
	public Response crear(UbigeoDTO obj) {
		return controladorAccion(obj, AccionType.CREAR);
	}

	@PUT
	@Path("/{id}")
	public Response modificar(UbigeoDTO obj) {
		return controladorAccion(obj, AccionType.MODIFICAR);
	}

	@DELETE
	@Path("/{id}")
	public Response eliminar(@PathParam("id") String id) {
		UbigeoDTO obj = new UbigeoDTO();
		obj.setIdUbigeo(id);
		return controladorAccion(obj, AccionType.ELIMINAR);
	}

	private Response controladorAccion(UbigeoDTO obj, AccionType accionType) {
		RespuestaWSVO<UbigeoDTO> resultado = new RespuestaWSVO<>();
		try {
			resultado.setObjetoResultado(servicioApp.controladorAccionUbigeo(obj, accionType));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, accionType);
	}

	@GET
	@Path("/{id}")
	public Response finById(@PathParam("id") String id) {
		UbigeoDTO obj = new UbigeoDTO();
		obj.setIdUbigeo(id);
		return controladorAccion(obj, AccionType.FIND_BY_ID);
	}

	@GET
	public Response paginador(@Context HttpHeaders headers, @Context UriInfo info) {
		RespuestaWSVO<UbigeoDTO> resultado = new RespuestaWSVO<>();
		try {
			BaseSearch filter = to(info);
			if (!StringUtil.isNotNullOrBlank(filter.getId())) {
				filter.setTipo(TipoUbigeoType.DEPARTAMENTO.getKey());
			}
			if (filter.getStartRow() == 0 || resultado.getContador() == 0)
				resultado.setContador(servicioApp.contarListarUbigeo(filter));

			if (resultado.isData() || filter.getStartRow() > 0) {
				resultado.setListaResultado(servicioApp.listarUbigeo(filter));
			}
		} catch (Exception e) {
			log.error("paginador", e);
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null, info);
	}

}