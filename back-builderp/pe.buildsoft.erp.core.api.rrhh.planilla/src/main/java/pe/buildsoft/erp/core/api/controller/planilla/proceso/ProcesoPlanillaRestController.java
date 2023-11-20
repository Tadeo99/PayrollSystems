package pe.buildsoft.erp.core.api.controller.planilla.proceso;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import pe.buildsoft.erp.core.application.entidades.planilla.PlanillaDTO;
import pe.buildsoft.erp.core.application.servicios.mdb.ConsumidorMDBServiceUtil;
import pe.buildsoft.erp.core.infra.transversal.type.TipoOpcionType;

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
@Path("/proceso/planilla")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProcesoPlanillaRestController extends ConsumidorMDBServiceUtil implements IProcesoPlanillaRestController {

	private static final String DINAMIC_EJECUCION_CLASS = "PlanillaProcesoAppServiceImpl";

	@POST
	public Response generarPlanilla(@Context HttpHeaders headers,PlanillaDTO obj) {
		obj.setTipoOpcion(TipoOpcionType.PROCESO.getKey());
		obj.setClassDinamic(DINAMIC_EJECUCION_CLASS);
		return ejecutar(headers,obj, null);
	}
}