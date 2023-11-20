package pe.buildsoft.erp.core.api.controller.pago.proceso;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.buildsoft.erp.core.application.interfaces.pago.PagoAppServiceLocal;
import pe.buildsoft.erp.core.domain.entidades.pago.vo.SunatDatosVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;

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
public class ProcesoControlPagoRestController extends GenericServiceRestImpl implements IProcesoControlPagoRestController {

	private Logger log = LoggerFactory.getLogger(IProcesoControlPagoRestController.class);

	@Inject
	private PagoAppServiceLocal servicioApp;

	@POST
	@Path("/enviarComprobante")
	public Response enviarComprobante(@Context HttpHeaders httpHeaders, SunatDatosVO sfs) {
		RespuestaWSVO<SunatDatosVO> resultado = new RespuestaWSVO<>();
		try {
			// resultado.setListaResultado(servicioApp.enviarComprobante(sfs));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null);
	}

	@POST
	@Path("/eliminarBandeja")
	public Response eliminarBandeja(@Context HttpHeaders httpHeaders) {
		RespuestaWSVO<SunatDatosVO> resultado = new RespuestaWSVO<>();
		try {
			// resultado.setListaResultado(servicioApp.eliminarBandeja());
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null);
	}

	@POST
	@Path("/actualizarBandeja")
	public Response actualizarBandeja(@Context HttpHeaders httpHeaders) {
		RespuestaWSVO<SunatDatosVO> resultado = new RespuestaWSVO<>();
		try {
			// resultado.setListaResultado(servicioApp.actualizarBandeja());
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null);
	}
}