package pe.buildsoft.erp.core.api.controller.matricula.reporte;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import pe.buildsoft.erp.core.application.servicios.mdb.ConsumidorMDBServiceUtil;
import pe.buildsoft.erp.core.domain.entidades.matricula.vo.FichaMatriculaVO;
import pe.buildsoft.erp.core.infra.transversal.type.TipoReporteGenerarType;

/**
 * La Class ReporteFichaMatriculaRestController.
 * <ul>
 * <li>Copyright 2021 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Thu Feb 24 15:16:44 COT 2022
 * @since BuildErp 1.0
 */
@Path("/reporte/matricula")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ReporteFichaMatriculaRestController extends ConsumidorMDBServiceUtil {

	private static final String DINAMIC_EJECUCION_CLASS = "MatriculaReporteAppServiceImpl";

	@GET
	@Path("/ficha/{idAnhio}/{idAlumno}/{idPeriodo}")
	Response ejecutar(@Context HttpHeaders headers, @PathParam("idAnhio") Long idAnhio,
			@PathParam("idAlumno") String idAlumno, @PathParam("idPeriodo") Long idPeriodo) {
		FichaMatriculaVO filtro = new FichaMatriculaVO();
		filtro.setClassDinamic(DINAMIC_EJECUCION_CLASS);
		filtro.setIdAnhio(idAnhio);
		filtro.setIdAlumno(idAlumno);
		filtro.setIdPeriodo(idPeriodo);
		return ejecutar(headers, filtro, TipoReporteGenerarType.PDF.getKey());
	}
}