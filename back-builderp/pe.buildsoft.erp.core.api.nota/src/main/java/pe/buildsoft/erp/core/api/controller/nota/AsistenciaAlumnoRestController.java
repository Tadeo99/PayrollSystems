package pe.buildsoft.erp.core.api.controller.nota;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import pe.buildsoft.erp.core.application.entidades.nota.AsistenciaAlumnoDTO;
import pe.buildsoft.erp.core.application.interfaces.nota.NotaAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;

/**
 * La Class AsistenciaAlumnoRestController.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Jun 10 00:13:45 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Path("/asistenciaAlumno")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AsistenciaAlumnoRestController extends GenericServiceRestImpl implements IAsistenciaAlumnoRestController {

	@Inject
	private NotaAppServiceLocal servicioApp;

	@POST
	public Response registrar(List<AsistenciaAlumnoDTO> listaObj) {
		RespuestaWSVO<String> resultado = new RespuestaWSVO<>();
		servicioApp.registrarAsistencia(listaObj);
		resultado.setObjetoResultado("OK");
		return respuesta(resultado, AccionType.CREAR);
	}

	@PUT
	@Path("/{id}")
	public Response modificar(AsistenciaAlumnoDTO obj) {
		return null;
	}

	@POST
	@Path("/obtenerAsistenciaAlumno")
	public Response obtenerAsistencia(AsistenciaAlumnoDTO filtro) {
		RespuestaWSVO<AsistenciaAlumnoDTO> resultado = new RespuestaWSVO<>();
		try {
			resultado.setListaResultado(servicioApp.obtenerAsistenciaAlumno(filtro));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null);
	}
}