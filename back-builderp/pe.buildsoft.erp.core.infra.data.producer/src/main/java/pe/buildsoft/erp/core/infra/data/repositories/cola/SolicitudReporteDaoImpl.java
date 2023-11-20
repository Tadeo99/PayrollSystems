package pe.buildsoft.erp.core.infra.data.repositories.cola;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import pe.buildsoft.erp.core.domain.entidades.cola.SolicitudReporte;
import pe.buildsoft.erp.core.domain.interfaces.repositories.cola.SolicitudReporteDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericColaDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class SolicitudReporteDaoImpl.
 * <ul>
 * <li>Copyright 2014 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Wed Jul 30 17:22:43 COT 2014
 * @since BuildErp 1.0
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class SolicitudReporteDaoImpl extends GenericColaDAOImpl<String, SolicitudReporte>
		implements SolicitudReporteDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.gob.mapfre.pwr.rep.ejb.dao.local.SolicitudReporteDaoLocal#
	 * generarIdSolicitudReporte()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

}