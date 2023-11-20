package pe.buildsoft.erp.core.application.servicios.cola;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.inject.Inject;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import pe.buildsoft.erp.core.application.entidades.cola.ConfiguracionFiltroReporteDTO;
import pe.buildsoft.erp.core.application.entidades.cola.ValorConfiguracionFiltroReporteDTO;
import pe.buildsoft.erp.core.application.interfaces.cola.ConfiguracionColaAppServiceLocal;
import pe.buildsoft.erp.core.application.interfaces.cola.ProcesarColaAppServiceLocal;
import pe.buildsoft.erp.core.application.interfaces.cola.ReporteAppServiceLocal;
import pe.buildsoft.erp.core.domain.entidades.cola.ValorConfiguracionFiltroReporte;
import pe.buildsoft.erp.core.domain.interfaces.servicios.cola.ProcesarColaServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.cache.AtributosEntityCacheUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.SolicitudReporteDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RegistroMensajeriaOutputVO;
import pe.buildsoft.erp.core.infra.transversal.utilitario.BaseTransfer;

/**
 * La Class ProcesarColaServiceLocal.
 * <ul>
 * <li>Copyright 2014 MAPFRE- OSCE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Tue Apr 29 19:20:13 COT 2014
 * @since Rep v1..0
 */
@Stateless // Servicios remotos
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ProcesarColaAppServiceImpl extends BaseTransfer implements ProcesarColaAppServiceLocal {

	@Inject
	private ReporteAppServiceLocal servicioReporte;

	@Inject
	private ConfiguracionColaAppServiceLocal servicioConfCola;

	@Inject
	private ProcesarColaServiceLocal servicioProcesarCola;

	public ProcesarColaAppServiceImpl() {
		AtributosEntityCacheUtil.getInstance().sincronizarAtributos("pe.buildsoft.erp.core.domain.entidades.cola");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.gob.osce.bsp.nms.ejb.service.local.MensajeriaServiceLocal#enviarCorreo(
	 * java.util.List)
	 */
	@Override
	public RegistroMensajeriaOutputVO enviarCola(List<Object> listaObjectos) {
		return servicioProcesarCola.enviarCola(listaObjectos);
	}

	@Override
	public RegistroMensajeriaOutputVO enviarColaGenerarSolicitudReporte(SolicitudReporteDTO solicitudReporteDTO) {
		return servicioProcesarCola.enviarColaGenerarSolicitudReporte(solicitudReporteDTO);
	}

	@Override
	public void guardarIntentosGenerarReporteFallidos(SolicitudReporteDTO solicitudReporteDTO, String descripcionError,
			boolean isReintentar) {
		servicioProcesarCola.guardarIntentosGenerarReporteFallidos(solicitudReporteDTO, descripcionError, isReintentar);
	}

	@Override
	public Map<Long, List<ValorConfiguracionFiltroReporteDTO>> listarDatosFiltro(List<Long> keyList) throws Exception {
		Map<Long, List<ValorConfiguracionFiltroReporteDTO>> respuesta = new HashMap<>();
		List<String> lugarApareceList = new ArrayList<>();
		lugarApareceList.add("A");
		lugarApareceList.add("P");
		List<ValorConfiguracionFiltroReporte> resultado = servicioProcesarCola.listarDatosFiltro(keyList,
				lugarApareceList);
		for (var objVConfFiltro : resultado) {
			if (!respuesta.containsKey(objVConfFiltro.getIdSolicitudReporte())) {
				ValorConfiguracionFiltroReporteDTO objVConf = toDTO(objVConfFiltro,
						ValorConfiguracionFiltroReporteDTO.class);
				ConfiguracionFiltroReporteDTO configuracionFiltroReporte = toDTO(
						objVConfFiltro.getConfiguracionFiltroReporte(), ConfiguracionFiltroReporteDTO.class);
				objVConf.setConfiguracionFiltroReporte(configuracionFiltroReporte);
				List<ValorConfiguracionFiltroReporteDTO> listaConfiguracionFiltro = new ArrayList<>();
				listaConfiguracionFiltro.add(objVConf);
				respuesta.put(objVConf.getIdSolicitudReporte(), listaConfiguracionFiltro);
			} else {
				List<ValorConfiguracionFiltroReporteDTO> listaConfiguracionFiltro = respuesta
						.get(objVConfFiltro.getIdSolicitudReporte());
				ValorConfiguracionFiltroReporteDTO valorConfiguracionFiltroReporteTemp = toDTO(objVConfFiltro,
						ValorConfiguracionFiltroReporteDTO.class);
				ConfiguracionFiltroReporteDTO configuracionFiltroReporte = toDTO(
						objVConfFiltro.getConfiguracionFiltroReporte(), ConfiguracionFiltroReporteDTO.class);
				valorConfiguracionFiltroReporteTemp.setConfiguracionFiltroReporte(configuracionFiltroReporte);
				listaConfiguracionFiltro.add(valorConfiguracionFiltroReporteTemp);
			}
		}
		return respuesta;
	}

	@Override
	public Map<String, List<String>> listarIdComponentes(Long idOpcion) {
		return servicioProcesarCola.listarIdComponentes(idOpcion);
	}
}