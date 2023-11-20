package pe.buildsoft.erp.core.api.controller.matricula;

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
import pe.buildsoft.erp.core.application.entidades.matricula.MallaCurricularDTO;
import pe.buildsoft.erp.core.application.interfaces.matricula.MatriculaAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;

/**
 * La Class MallaCurricularRestController.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 21:45:24 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Path("/mallaCurricular")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MallaCurricularRestController extends GenericServiceRestImpl implements IMallaCurricularRestController {

	private Logger log = LoggerFactory.getLogger(MallaCurricularRestController.class);

	@Inject
	private MatriculaAppServiceLocal servicioApp;

	@POST
	public Response crear(MallaCurricularDTO obj) {
		return controladorAccion(obj, AccionType.CREAR);
	}

	@PUT
	@Path("/{id}")
	public Response modificar(MallaCurricularDTO obj) {
		return controladorAccion(obj, AccionType.MODIFICAR);
	}

	@DELETE
	@Path("/{id}")
	public Response eliminar(@PathParam("id") String id) {
		MallaCurricularDTO obj = new MallaCurricularDTO();
		obj.setIdMallaCurricular(id);
		return controladorAccion(obj, AccionType.ELIMINAR);
	}

	private Response controladorAccion(MallaCurricularDTO obj, AccionType accionType) {
		RespuestaWSVO<MallaCurricularDTO> resultado = new RespuestaWSVO<>();
		try {
			resultado.setObjetoResultado(servicioApp.controladorAccionMallaCurricular(obj, accionType));
		} catch (Exception e) {
			// parsearResultadoError(e, resultado);
			resultado.setError(true);
			resultado.setMensajeError("No se puede eliminar ==> Cursos esta siendo utilizada ! ");
		}
		return respuesta(resultado, accionType);
	}

	@GET
	@Path("/{id}")
	public Response finById(@PathParam("id") String id) {
		MallaCurricularDTO filtro = new MallaCurricularDTO();
		filtro.setIdMallaCurricular(id);
		return controladorAccion(filtro, AccionType.FIND_BY_ID);
	}

	@GET
	public Response paginador(@Context HttpHeaders headers, @Context UriInfo info) {
		RespuestaWSVO<MallaCurricularDTO> resultado = new RespuestaWSVO<>();
		try {
			var filtro = to(info);
			if (filtro.getStartRow() == 0)
				resultado.setContador(servicioApp.contarListarMallaCurricular(filtro));
			if (resultado.isData() || filtro.getStartRow() > 0) {
				resultado.setListaResultado(servicioApp.listarMallaCurricular(filtro));
			}
		} catch (Exception e) {
			log.error("paginador", e);
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null);
	}

}