package pe.buildsoft.erp.core.domain.servicios.cola;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import pe.buildsoft.erp.core.domain.entidades.cola.SolicitudReporte;
import pe.buildsoft.erp.core.domain.interfaces.repositories.cola.ConfiguracionReporteFromCorreoDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.cola.IntentoGenerarReporteDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.cola.SolicitudReporteDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.servicios.cola.ReporteServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.cache.AtributosEntityCacheUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ConfiguracionReporteFromCorreoVO;
import pe.buildsoft.erp.core.infra.transversal.state.EstadoSolicitudEjecucionEstate;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.FechaUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ThreadColaUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Class ReporteServiceImpl.
 *
 * @author BuildSoft.
 * @version 1.0 , 22/05/2015
 * @since BuildErp 1.0
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ReporteServiceImpl implements ReporteServiceLocal {

	/** La log. */
	private Logger log = LoggerFactory.getLogger(ReporteServiceImpl.class);

	/** El servicio solicitud reporte dao impl. */
	@Inject
	private SolicitudReporteDaoLocal solicitudReporteDaoImpl;

	/** El intento generar reporte dao impl. */
	@Inject
	private IntentoGenerarReporteDaoLocal intentoGenerarReporteDaoImpl;

	@Inject
	private ConfiguracionReporteFromCorreoDaoLocal configuracionReporteFromCorreoDaoLocal;

	public ReporteServiceImpl() {
		AtributosEntityCacheUtil.getInstance().sincronizarAtributos("pe.buildsoft.erp.core.domain.entidades");
	}

	@Override
	public Map<String, ConfiguracionReporteFromCorreoVO> obtenerConfiguracionReporteFromCorreoMap() {
		return configuracionReporteFromCorreoDaoLocal.getFromCorreoMap();
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.gob.mapfre.pwr.rep.ejb.service.local.ReporteServiceLocal#
	 * controladorAccionSolicitudReporte(pe.gob.mapfre.pwr.rep.model..
	 * SolicitudReporte, pe.gob.mapfre.pwr.rep.model.type.AccionType)
	 */
	@Override
	public SolicitudReporte controladorAccionSolicitudReporte(SolicitudReporte solicitudReporte,
			AccionType accionType) {
		SolicitudReporte resultado = null;
		switch (accionType) {
		case CREAR:
			solicitudReporte.setIdSolicitudReporte(this.solicitudReporteDaoImpl.generarId());
			if (StringUtil.isNullOrEmpty(solicitudReporte.getEstado())) {
				solicitudReporte.setEstado(EstadoSolicitudEjecucionEstate.PENDIENTE.getKey());
			}
			log.error("controladorAccionSolicitudReporte.CREAR " + solicitudReporte.toString());
			this.solicitudReporteDaoImpl.save(solicitudReporte);
			resultado = solicitudReporte;
			break;
		case MODIFICAR:
			if (EstadoSolicitudEjecucionEstate.TERMINADO.getKey().equals(solicitudReporte.getEstado())) {
				solicitudReporte.setFechaTermino(FechaUtil.obtenerFechaActual());
			}
			log.error("controladorAccionSolicitudReporte.MODIFICAR " + solicitudReporte.toString());
			this.solicitudReporteDaoImpl.update(solicitudReporte);
			resultado = solicitudReporte;
			break;

		case ELIMINAR:
			intentoGenerarReporteDaoImpl.eliminarIntentoGenerarReporte(solicitudReporte.getIdSolicitudReporte());
			solicitudReporte = this.solicitudReporteDaoImpl.find(SolicitudReporte.class,
					solicitudReporte.getIdSolicitudReporte());
			String codigoSolicitud = solicitudReporte.getCodigoSolicitud();
			log.error("controladorAccionSolicitudReporte.ELIMINAR " + solicitudReporte.toString());
			this.solicitudReporteDaoImpl.delete(solicitudReporte);
			resultado = solicitudReporte;
			ThreadColaUtil.getInstance().stop(solicitudReporte.getUserName(), codigoSolicitud);
			break;

		case FIND_BY_ID:
			resultado = this.solicitudReporteDaoImpl.find(SolicitudReporte.class,
					solicitudReporte.getIdSolicitudReporte());
			break;

		default:
			break;
		}
		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.gob.mapfre.pwr.rep.ejb.service.local.ReporteServiceLocal#
	 * obtenerUltimoCodigoSolicitudByUsuario(pe.gob.mapfre.pwr.rep.model..Usuario)
	 */
	@Override
	public String obtenerUltimoCodigoSolicitudByUsuario(Long idUsuario) {
		return UUIDUtil.generarElementUUID();
	}
}