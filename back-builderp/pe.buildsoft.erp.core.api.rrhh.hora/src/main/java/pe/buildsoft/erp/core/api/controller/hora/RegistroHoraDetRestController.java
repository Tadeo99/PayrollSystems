package pe.buildsoft.erp.core.api.controller.hora;

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
import pe.buildsoft.erp.core.application.entidades.hora.RegistroHoraDetVO;
import pe.buildsoft.erp.core.application.interfaces.hora.RegistroHoraAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ObjectUtil;

/**
 * La Class RegistroCabeceraRestController.
 * <ul>
 * <li>Copyright 2021 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Thu Feb 24 15:16:44 COT 2022
 * @since BuildErp 1.0
 */
@Path("/registroHoraDet")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RegistroHoraDetRestController extends GenericServiceRestImpl implements IRegistroHoraDetRestController {

	private Logger log = LoggerFactory.getLogger(RegistroHoraDetRestController.class);

	@Inject
	private RegistroHoraAppServiceLocal servicioApp;

	@GET
	public Response paginador(@Context HttpHeaders headers, @Context UriInfo info) {
		RespuestaWSVO<RegistroHoraDetVO> resultado = new RespuestaWSVO<>();
		try {
			String idPersonal = info.getQueryParameters().getFirst("idPersonal");
			String idPeriodo = info.getQueryParameters().getFirst("idPeriodo");
			Long numSemana = ObjectUtil.objectToLong(info.getQueryParameters().getFirst("numSemana"));
			String estadoPeriodo = info.getQueryParameters().getFirst("estadoPeriodo");
			resultado.setListaResultado(
					servicioApp.obtenerRegistroHora(idPersonal, idPeriodo, numSemana, estadoPeriodo));
		} catch (Exception e) {
			log.error("paginador", e);
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null);
	}

}