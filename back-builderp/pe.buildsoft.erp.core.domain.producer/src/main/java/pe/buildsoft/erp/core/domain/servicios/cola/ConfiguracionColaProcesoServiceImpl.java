package pe.buildsoft.erp.core.domain.servicios.cola;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.inject.Inject;
import pe.buildsoft.erp.core.domain.entidades.cola.ColaNoctura;
import pe.buildsoft.erp.core.domain.entidades.cola.SolicitudReporte;
import pe.buildsoft.erp.core.domain.interfaces.repositories.cola.ColaNocturaProcesoDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.servicios.cola.ConfiguracionColaProcesoServiceLocal;
import pe.buildsoft.erp.core.domain.interfaces.servicios.cola.IJMSSender;
import pe.buildsoft.erp.core.domain.interfaces.servicios.cola.ReporteServiceLocal;
import pe.buildsoft.erp.core.domain.util.ParametroUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.SolicitudReporteDTO;
import pe.buildsoft.erp.core.infra.transversal.paginator.IDataProvider;
import pe.buildsoft.erp.core.infra.transversal.paginator.LazyLoadingList;
import pe.buildsoft.erp.core.infra.transversal.state.EstadoGeneralState;
import pe.buildsoft.erp.core.infra.transversal.state.EstadoSolicitudEjecucionEstate;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.BaseTransfer;
import pe.buildsoft.erp.core.infra.transversal.utilitario.CollectionUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.FechaUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.SerializationUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * La Class ConfiguracionColaServiceImpl.
 * <ul>
 * <li>Copyright 2015 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 2.1, Thu Dec 21 12:25:45 COT 2017
 * @since BuildErp 1.0
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@TransactionManagement(TransactionManagementType.BEAN)
public class ConfiguracionColaProcesoServiceImpl extends BaseTransfer implements ConfiguracionColaProcesoServiceLocal {

	private static final String EJECUTAR_COLA_NOCTURA = "ejecutarColaNoctura.";

	private static final String ERROR_EN_LA_SOLICITUD = " ERROR EN LA SOLICITUD ==> ";

	private static Logger log = LoggerFactory.getLogger(ConfiguracionColaProcesoServiceImpl.class);

	/** El servicio cola noctura dao impl. */
	@Inject
	private ColaNocturaProcesoDaoLocal colaNocturaDaoImpl;

	@Inject
	protected ReporteServiceLocal reporteServiceLocal;

	@Inject
	protected IJMSSender jMSSender;

	public String ejecucionColaNoctura(String uuid) {
		log.error(EJECUTAR_COLA_NOCTURA + uuid + " PROCESANDO INICIO -----------------> "
				+ FechaUtil.obtenerFechaActual());
		String resultado = "";
		BaseSearch colaNocturaFiltro = new BaseSearch();
		colaNocturaFiltro.setEstadoProceso(EstadoSolicitudEjecucionEstate.PENDIENTE.getKey());
		colaNocturaFiltro.setEstado(EstadoGeneralState.ACTIVO.getKey());
		List<ColaNoctura> listaColaNoctura = new ArrayList<>();
		listaColaNoctura = buscarPaginadoColaNocturna(colaNocturaFiltro);
		if (!CollectionUtil.isEmpty(listaColaNoctura)) {
			for (var colaNoctura : listaColaNoctura) {
				if (!StringUtil.isNullOrEmpty(colaNoctura.getObjectoJson())) {
					try {
						SolicitudReporteDTO solicitudReporteDTO = (SolicitudReporteDTO) SerializationUtil
								.fromString(colaNoctura.getObjectoJson());
						if (solicitudReporteDTO != null) {
							solicitudReporteDTO.getPametrosMap().put(ParametroUtil.ES_COLA_NOCTURNA, true);
							solicitudReporteDTO.getPametrosMap().put(ParametroUtil.COLA_NOCTURNA_ID,
									colaNoctura.getIdColaNocturna());
							resultado = jMSSender.sendMessageNocturno(solicitudReporteDTO);
						} else {
							actualizarEstadoProceso(uuid, colaNoctura);
						}
					} catch (Exception e) {
						actualizarEstadoProceso(uuid, colaNoctura);
						log.error(EJECUTAR_COLA_NOCTURA + uuid + ERROR_EN_LA_SOLICITUD
								+ colaNoctura.getIdSolicitudReporte() + " ERROR ==> " + e.getMessage() + "");
					}

				}
			}

		}
		log.error(
				EJECUTAR_COLA_NOCTURA + uuid + " PROCESANDO FIN -----------------> " + FechaUtil.obtenerFechaActual());
		return resultado;
	}

	private void actualizarEstadoProceso(String uuid, ColaNoctura colaNoctura) {
		SolicitudReporte solicitudReporteFiltro = new SolicitudReporte();
		solicitudReporteFiltro.setIdSolicitudReporte(colaNoctura.getIdSolicitudReporte());
		SolicitudReporte solicitudReporteError = reporteServiceLocal
				.controladorAccionSolicitudReporte(solicitudReporteFiltro, AccionType.FIND_BY_ID);
		if (solicitudReporteError != null) {
			jMSSender.actualizarSolicitud(toDTO(solicitudReporteError, SolicitudReporteDTO.class),
					EstadoSolicitudEjecucionEstate.FALLO_EJECUCION, "NO SE PUDO CREAR SOLICITUD REPORTE");
			log.error(EJECUTAR_COLA_NOCTURA + uuid + ERROR_EN_LA_SOLICITUD + colaNoctura.getIdSolicitudReporte()
					+ " ERROR ==> NULL");
		} else {
			log.error(EJECUTAR_COLA_NOCTURA + uuid + ERROR_EN_LA_SOLICITUD + colaNoctura.getIdSolicitudReporte()
					+ " ERROR ==> NO EXISTE SOLICITUD REPORTE");
			jMSSender.actualizarColaNocturna(colaNoctura.getIdColaNocturna(),
					EstadoSolicitudEjecucionEstate.FALLO_EJECUCION);
		}
	}

	private List<ColaNoctura> buscarPaginadoColaNocturna(final BaseSearch colaNocturaFiltro) {
		IDataProvider<ColaNoctura> dataProvider;
		dataProvider = new IDataProvider<ColaNoctura>() {
			private int total = 0;
			private int cuenta = 0;

			@Override
			public List<ColaNoctura> getBufferedData(int startRow, int offset) {
				List<ColaNoctura> lista = new ArrayList<>();
				colaNocturaFiltro.setStartRow(startRow);
				colaNocturaFiltro.setOffSet(offset);
				try {
					lista = listarColaNoctura(colaNocturaFiltro);
				} catch (Exception e) {
					lista = new ArrayList<>();
				}
				return lista;
			}

			@Override
			public int getTotalResultsNumber() {
				if (total == 0 && cuenta == 0) {
					total = contarListarColaNoctura(colaNocturaFiltro);
					cuenta++;
				}
				return total;
			}

		};
		return new LazyLoadingList<>(dataProvider, 2000);
	}

	@Override
	public List<ColaNoctura> listarColaNoctura(BaseSearch colaNoctura) {
		return colaNocturaDaoImpl.listar(colaNoctura);
	}

	@Override
	public int contarListarColaNoctura(BaseSearch colaNoctura) {
		return colaNocturaDaoImpl.contar(colaNoctura);
	}
}