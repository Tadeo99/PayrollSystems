package pe.buildsoft.erp.core.api.controller.pago;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.buildsoft.erp.core.domain.interfaces.webclient.rest.sunat.SunatServiceClient;
import pe.buildsoft.erp.core.infra.data.entidades.webclient.rest.sunat.vo.EmpresaSunatVo;
import pe.buildsoft.erp.core.infra.data.entidades.webclient.rest.sunat.vo.PersonaSunatVo;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
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
@Path("/sunat")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SunatRestController extends GenericServiceRestImpl implements ISunatRestController {

	private Logger log = LoggerFactory.getLogger(SunatRestController.class);

	@Inject
	private SunatServiceClient servicioInfra;

	@GET
	@Path("/dni/{nroDoc}")
	public Response getDni(@PathParam("nroDoc") String nroDoc) {
		RespuestaWSVO<PersonaSunatVo> resultado = new RespuestaWSVO<>();
		try {
			resultado.setListaResultado(servicioInfra.consultarReniecDni(nroDoc));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null);
	}

	@GET
	@Path("/ruc/{nroDoc}")
	public Response getRuc(@PathParam("nroDoc") String nroDoc) {
		RespuestaWSVO<EmpresaSunatVo> resultado = new RespuestaWSVO<>();
		try {
			resultado.setListaResultado(servicioInfra.consultarRuc(nroDoc));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null);
	}

}