package pe.buildsoft.erp.core.api.controller.hora.reporte;

import java.util.List;

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
import pe.buildsoft.erp.core.application.interfaces.hora.reporte.ReporteRegistroHoraAppServiceLocal;
import pe.buildsoft.erp.core.application.servicios.mdb.ConsumidorMDBServiceUtil;
import pe.buildsoft.erp.core.domain.entidades.rrhh.hora.vo.RegistroHoraReporteVO;
import pe.buildsoft.erp.core.domain.entidades.rrhh.hora.vo.RegistroHoraVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.type.TipoReporteGenerarType;

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
@Path("/reporteRegistroHora")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ReporteRegistroHoraRestController extends ConsumidorMDBServiceUtil
		implements IReporteRegistroHoraRestController {

	private static final String DINAMIC_EJECUCION_CLASS = "ReporteRegistroHoraAppServiceImpl";

	private Logger log = LoggerFactory.getLogger(ReporteRegistroHoraRestController.class);

	@Inject
	private ReporteRegistroHoraAppServiceLocal servicioApp;

	@GET
	public Response paginador(@Context HttpHeaders headers, @Context UriInfo info) {
		RespuestaWSVO<RegistroHoraVO> resultado = new RespuestaWSVO<>();
		try {
			BaseSearch basePaginator = to(info);
			String idPeriodo = info.getQueryParameters().getFirst("idPeriodo");
			String idPersonal = info.getQueryParameters().getFirst("idPersonal");
			if (basePaginator.getStartRow() == 0) {
				resultado.setContador(servicioApp.contarReporteRegistroHora(basePaginator, idPersonal, idPeriodo));
			}
			if (resultado.isData() || basePaginator.getStartRow() > 0) {
				List<RegistroHoraVO> result = servicioApp.obtenerRegistroHora(basePaginator, idPersonal, idPeriodo);
				resultado.setListaResultado(result);
			}
		} catch (Exception e) {
			log.error("paginador",e);
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null);
	}

	@POST
	public Response ejecutar(@Context HttpHeaders headers, RegistroHoraReporteVO data) {
		data.setClassDinamic(DINAMIC_EJECUCION_CLASS);
		return ejecutar(headers, data, TipoReporteGenerarType.XLSX.getKey());
	}
}