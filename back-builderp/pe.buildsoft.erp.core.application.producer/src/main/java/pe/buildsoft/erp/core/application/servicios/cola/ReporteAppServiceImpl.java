package pe.buildsoft.erp.core.application.servicios.cola;

import jakarta.inject.Inject;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;

import pe.buildsoft.erp.core.application.interfaces.cola.ReporteAppServiceLocal;
import pe.buildsoft.erp.core.domain.entidades.cola.SolicitudReporte;
import pe.buildsoft.erp.core.domain.interfaces.servicios.cola.ReporteServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.cache.AtributosEntityCacheUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.SolicitudReporteDTO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.BaseTransfer;

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
public class ReporteAppServiceImpl extends BaseTransfer implements ReporteAppServiceLocal {

	/** El servicio reporte service impl. */
	@Inject
	private ReporteServiceLocal servicio;

	public ReporteAppServiceImpl() {
		AtributosEntityCacheUtil.getInstance().sincronizarAtributos("pe.buildsoft.erp.core.domain.entidades.cola");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.gob.mapfre.pwr.rep.ejb.service.local.ReporteServiceLocal#
	 * controladorAccionSolicitudReporte(pe.gob.mapfre.pwr.rep.model.dto.
	 * SolicitudReporteDTO, pe.gob.mapfre.pwr.rep.model.type.AccionType)
	 */
	@Override
	public SolicitudReporteDTO controladorAccionSolicitudReporte(SolicitudReporteDTO obj, AccionType accionType)
			throws Exception {
		return toDTO(servicio.controladorAccionSolicitudReporte(to(obj, SolicitudReporte.class), accionType),
				SolicitudReporteDTO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.gob.mapfre.pwr.rep.ejb.service.local.ReporteServiceLocal#
	 * obtenerUltimoCodigoSolicitudByUsuario(pe.gob.mapfre.pwr.rep.model.dto.
	 * UsuarioDTO)
	 */
	@Override
	public String obtenerUltimoCodigoSolicitudByUsuario(Long idUsuario) {
		return servicio.obtenerUltimoCodigoSolicitudByUsuario(idUsuario);
	}

}