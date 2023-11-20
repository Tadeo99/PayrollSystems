package pe.buildsoft.erp.core.api.controller.pago;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import pe.buildsoft.erp.core.application.entidades.pago.ControlPagoDTO;
import pe.buildsoft.erp.core.application.interfaces.pago.PagoAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.cache.ICache;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ObjectUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * La Class ControlPagoRestController.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 21:45:29 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Path("/controlPago")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ControlPagoRestController extends GenericServiceRestImpl implements IControlPagoRestController {

	private Logger log = LoggerFactory.getLogger(ControlPagoRestController.class);

	@Inject
	private PagoAppServiceLocal servicioApp;

	@Inject
	private ICache cacheUtil;

	@POST
	public Response crear(@Context HttpHeaders httpHeaders, ControlPagoDTO obj) {
		RespuestaWSVO<String> resultado = new RespuestaWSVO<>();
		try {
			if (!StringUtil.isNotNullOrBlank(obj.getIdControlPago())) {
				obj.setUsuarioCreacion(cacheUtil.getUserName(getToken(httpHeaders)));
			} else {
				obj.setUsuarioModificacion(cacheUtil.getUserName(getToken(httpHeaders)));
			}
			resultado.setObjetoResultado(servicioApp.registrarPago(obj));

		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, AccionType.CREAR);
	}

	@GET
	public Response paginador(@Context HttpHeaders headers, @Context UriInfo info) {
		RespuestaWSVO<ControlPagoDTO> resultado = new RespuestaWSVO<>();
		try {
			var filtro = to(info);
			if (filtro.getStartRow() == 0 || resultado.getContador() == 0)
				resultado.setContador(servicioApp.contarListarControlPago(filtro));
			if (resultado.isData() || filtro.getStartRow() > 0) {
				resultado.setListaResultado(servicioApp.listarControlPago(filtro));
			}
		} catch (Exception e) {
			log.error("paginador", e);
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null, info);
	}

	protected BaseSearch to(@Context UriInfo info) {
		BaseSearch resultado = toRest(info, BaseSearch.class);
		String idAlumno = info.getQueryParameters().getFirst("idAlumno");
		Long idAnhio = ObjectUtil.objectToLong(info.getQueryParameters().getFirst("idAnhio"));
		String idPeriodo = info.getQueryParameters().getFirst("idPeriodo");
		resultado.setIdAlumno(idAlumno);
		resultado.setIdAnhio(idAnhio);
		resultado.setIdPeriodo(idPeriodo);
		return resultado;
	}
}