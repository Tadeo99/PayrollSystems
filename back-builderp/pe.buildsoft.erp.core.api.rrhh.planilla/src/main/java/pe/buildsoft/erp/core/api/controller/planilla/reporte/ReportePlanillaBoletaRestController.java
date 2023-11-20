package pe.buildsoft.erp.core.api.controller.planilla.reporte;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import pe.buildsoft.erp.core.application.entidades.planilla.DetallePlanillaDTO;
import pe.buildsoft.erp.core.application.servicios.mdb.ConsumidorMDBServiceUtil;
import pe.buildsoft.erp.core.infra.transversal.type.TipoOpcionType;
import pe.buildsoft.erp.core.infra.transversal.type.TipoReporteGenerarType;

/**
 * La Class ReportePlanillaRestController.
 * <ul>
 * <li>Copyright 2021 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Thu Feb 24 15:16:44 COT 2022
 * @since BuildErp 1.0
 */
@Path("/reporte/boleta")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ReportePlanillaBoletaRestController extends ConsumidorMDBServiceUtil
		implements IReportePlanillaRestController {

	private static final String DINAMIC_EJECUCION_CLASS = "PlanillaReporteAppServiceImpl";

	@POST
	@Path("/individual")
	public Response generarReportePlanillaBoletaIndividual(@Context HttpHeaders headers, DetallePlanillaDTO filtro) {
		filtro.setTipoOpcion(TipoOpcionType.REPORTE.getKey());
		filtro.setClassDinamic(DINAMIC_EJECUCION_CLASS);
		filtro.setTipo("I");
		return ejecutar(headers,filtro, TipoReporteGenerarType.PDF.getKey());
	}

	@POST
	@Path("/masivo")
	public Response generarReportePlanillaBoletaMasiva(@Context HttpHeaders headers, DetallePlanillaDTO filtro) {
		filtro.setTipoOpcion(TipoOpcionType.REPORTE.getKey());
		filtro.setClassDinamic(DINAMIC_EJECUCION_CLASS);
		filtro.setTipo("M");
		return ejecutar(headers,filtro, TipoReporteGenerarType.PDF.getKey());
	}

}