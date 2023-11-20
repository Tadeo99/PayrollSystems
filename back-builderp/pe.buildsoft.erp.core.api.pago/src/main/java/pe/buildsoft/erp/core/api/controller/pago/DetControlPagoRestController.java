package pe.buildsoft.erp.core.api.controller.pago;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.buildsoft.erp.core.application.entidades.pago.DetControlPagoDTO;
import pe.buildsoft.erp.core.application.interfaces.pago.PagoAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;

/**
 * La Class DetControlPagoRestController.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 21:45:29 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Path("/detControlPago")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DetControlPagoRestController extends GenericServiceRestImpl implements IDetControlPagoRestController {

	@Inject
	private PagoAppServiceLocal servicioApp;

	@GET
	@Path("/{idAnho}/{idPeriodo}/{idAlumno}/{flagFaltaMontoResta}")
	public Response listar(@PathParam("idAnho") Long idAnho, @PathParam("idPeriodo") Long idPeriodo,
			@PathParam("idAlumno") String idAlumno, @PathParam("flagFaltaMontoResta") boolean flagFaltaMontoResta) {
		RespuestaWSVO<DetControlPagoDTO> resultado = new RespuestaWSVO<>();
		try {
			resultado.setListaResultado(
					servicioApp.obtenerConceptoPagoAlumnoSemestre(idAnho, idPeriodo, idAlumno, flagFaltaMontoResta));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null);
	}
}