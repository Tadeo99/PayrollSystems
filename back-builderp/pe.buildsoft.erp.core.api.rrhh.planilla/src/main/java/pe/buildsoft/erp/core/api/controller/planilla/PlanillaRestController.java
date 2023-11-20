package pe.buildsoft.erp.core.api.controller.planilla;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import pe.buildsoft.erp.core.application.entidades.planilla.DetallePlanillaDTO;
import pe.buildsoft.erp.core.application.interfaces.planilla.PlanillaAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ObjectUtil;

/**
 * La Class PlanillaRestController.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Path("/planilla")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PlanillaRestController extends GenericServiceRestImpl implements IPlanillaRestController {

	private Logger log = LoggerFactory.getLogger(PlanillaRestController.class);

	@Inject
	private PlanillaAppServiceLocal servicioApp;

	@GET
	public Response paginador(@Context HttpHeaders headers, @Context UriInfo info) {
		var resultado = new RespuestaWSVO<DetallePlanillaDTO>();
		try {
			resultado.setListaResultado(new ArrayList<>());
			var filtro = to(info);
			filtro.setAuthToken(getToken(headers));
			/*
			 * if (filtro.getStartRow() == 0)
			 * resultado.setContador(servicioApp.contarListarDetallePlanilla(filtro)); if
			 * (resultado.isData() || filtro.getStartRow() > 0) {
			 * resultado.setListaResultado(servicioApp.listarDetallePlanilla(filtro)); }
			 */
			resultado.setListaResultado(servicioApp.listarDetallePlanilla(filtro));
		} catch (Exception e) {
			log.error("paginador", e);
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null, info);
	}

	protected BaseSearch to(@Context UriInfo info) {
		var resultado = toRest(info, BaseSearch.class);
		var idAnhio = ObjectUtil.objectToLong(info.getQueryParameters().getFirst("idAnhio"));
		var idTipoPlanilla = info.getQueryParameters().getFirst("idTipoPlanilla");
		var idItemByPeriodoMes = ObjectUtil.objectToLong(info.getQueryParameters().getFirst("idItemByPeriodoMes"));

		resultado.setIdAnhio(idAnhio);

		resultado.setIdTipoPlanilla(idTipoPlanilla);

		resultado.setIdItemByPeriodoMes(idItemByPeriodoMes);
		return resultado;
	}
}