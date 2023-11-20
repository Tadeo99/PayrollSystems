package pe.buildsoft.erp.core.application.servicios.cola;

import java.util.List;
import java.util.TreeMap;

import jakarta.inject.Inject;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import pe.buildsoft.erp.core.application.entidades.cola.ColaDTO;
import pe.buildsoft.erp.core.application.entidades.cola.ColaNocturaDTO;
import pe.buildsoft.erp.core.application.entidades.cola.ConfiguracionFiltroColaDTO;
import pe.buildsoft.erp.core.application.entidades.cola.ConfiguracionFiltroReporteDTO;
import pe.buildsoft.erp.core.application.entidades.cola.ValorConfiguracionFiltroReporteDTO;
import pe.buildsoft.erp.core.application.interfaces.cola.ConfiguracionColaAppServiceLocal;
import pe.buildsoft.erp.core.domain.entidades.cola.Cola;
import pe.buildsoft.erp.core.domain.entidades.cola.ColaNoctura;
import pe.buildsoft.erp.core.domain.entidades.cola.ConfiguracionFiltroCola;
import pe.buildsoft.erp.core.domain.entidades.cola.ConfiguracionFiltroReporte;
import pe.buildsoft.erp.core.domain.entidades.cola.ValorConfiguracionFiltroReporte;
import pe.buildsoft.erp.core.domain.interfaces.servicios.cola.ConfiguracionColaServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.cache.AtributosEntityCacheUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.state.EstadoSolicitudEjecucionEstate;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.BaseTransfer;

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
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ConfiguracionColaAppServiceImpl extends BaseTransfer implements ConfiguracionColaAppServiceLocal {

	/** El servicio configuracion cola servicio impl. */
	@Inject
	private ConfiguracionColaServiceLocal servicio;

	public ConfiguracionColaAppServiceImpl() {
		AtributosEntityCacheUtil.getInstance().sincronizarAtributos("pe.buildsoft.erp.core.domain.entidades.cola");
	}

	@Override
	public String actualizarEstadoColaNoctura(String idColaNocturna,
			EstadoSolicitudEjecucionEstate estadoSolicitudEjecucionEstate) {
		return servicio.actualizarEstadoColaNoctura(idColaNocturna, estadoSolicitudEjecucionEstate);
	}

	@Override
	public ConfiguracionFiltroReporteDTO controladorAccionConfiguracionFiltroReporte(ConfiguracionFiltroReporteDTO obj,
			AccionType accionType) {
		return toDTO(servicio.controladorAccionConfiguracionFiltroReporte(to(obj, ConfiguracionFiltroReporte.class),
				accionType), ConfiguracionFiltroReporteDTO.class);
	}

	@Override
	public List<ConfiguracionFiltroReporteDTO> listarConfiguracionFiltroReporte(BaseSearch filtro) {
		return toList(this.servicio.listarConfiguracionFiltroReporte(filtro), ConfiguracionFiltroReporteDTO.class);
	}

	@Override
	public int contarListarConfiguracionFiltroReporte(BaseSearch filtro) {
		return this.servicio.contarListarConfiguracionFiltroReporte(filtro);
	}

	@Override
	public ConfiguracionFiltroColaDTO controladorAccionConfiguracionFiltroCola(ConfiguracionFiltroColaDTO obj,
			AccionType accionType) {
		return toDTO(
				servicio.controladorAccionConfiguracionFiltroCola(to(obj, ConfiguracionFiltroCola.class, "cola@PK@",
						"configuracionFiltroReporte@PK@", "configuracionFiltroReporteRango@PK@"), accionType),
				ConfiguracionFiltroColaDTO.class);
	}

	@Override
	public TreeMap<Long, List<ConfiguracionFiltroColaDTO>> listarConfiguracionFiltroColaMap(BaseSearch filtro) {
		TreeMap<Long, List<ConfiguracionFiltroColaDTO>> respuesta = new TreeMap<>();
		TreeMap<Long, List<ConfiguracionFiltroCola>> respuestaTemp = this.servicio
				.listarConfiguracionFiltroColaMap(filtro);
		for (var objData : respuestaTemp.entrySet()) {
			Long key = objData.getKey();
			if (!respuesta.containsKey(key)) {
				respuesta.put(key, toList(objData.getValue(), ConfiguracionFiltroColaDTO.class, "cola",
						"configuracionFiltroReporte", "configuracionFiltroReporteRango"));
			}
		}
		return respuesta;
	}

	@Override
	public int contarListarConfiguracionFiltroCola(BaseSearch filtro) {
		return this.servicio.contarListarConfiguracionFiltroCola(filtro);
	}

	@Override
	public ValorConfiguracionFiltroReporteDTO controladorAccionValorConfiguracionFiltroReporte(
			ValorConfiguracionFiltroReporteDTO obj, AccionType accionType) {
		return toDTO(
				servicio.controladorAccionValorConfiguracionFiltroReporte(
						to(obj, ValorConfiguracionFiltroReporte.class, "configuracionFiltroReporte@PK@"), accionType),
				ValorConfiguracionFiltroReporteDTO.class);
	}

	@Override
	public List<ValorConfiguracionFiltroReporteDTO> listarValorConfiguracionFiltroReporte(BaseSearch filtro) {
		return toList(this.servicio.listarValorConfiguracionFiltroReporte(filtro),
				ValorConfiguracionFiltroReporteDTO.class);
	}

	@Override
	public int contarListarValorConfiguracionFiltroReporte(BaseSearch filtro) {
		return this.servicio.contarListarValorConfiguracionFiltroReporte(filtro);
	}

	@Override
	public ColaNocturaDTO controladorAccionColaNoctura(ColaNocturaDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionColaNoctura(to(obj, ColaNoctura.class), accionType),
				ColaNocturaDTO.class);
	}

	@Override
	public List<ColaNocturaDTO> listarColaNoctura(BaseSearch filtro) {
		return toList(this.servicio.listarColaNoctura(filtro), ColaNocturaDTO.class);
	}

	@Override
	public int contarListarColaNoctura(BaseSearch filtro) {
		return this.servicio.contarListarColaNoctura(filtro);
	}

	@Override
	public ColaDTO controladorAccionCola(ColaDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionCola(to(obj, Cola.class), accionType), ColaDTO.class);
	}

	@Override
	public List<ColaDTO> listarCola(BaseSearch filtro) {
		return toList(this.servicio.listarCola(filtro), ColaDTO.class);
	}

	@Override
	public int contarListarCola(BaseSearch filtro) {
		return this.servicio.contarListarCola(filtro);
	}
}