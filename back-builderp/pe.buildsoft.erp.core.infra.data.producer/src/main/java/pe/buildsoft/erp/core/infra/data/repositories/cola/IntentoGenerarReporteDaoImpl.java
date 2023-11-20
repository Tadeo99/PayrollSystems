package pe.buildsoft.erp.core.infra.data.repositories.cola;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import pe.buildsoft.erp.core.domain.entidades.cola.IntentoGenerarReporte;
import pe.buildsoft.erp.core.domain.interfaces.repositories.cola.IntentoGenerarReporteDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericColaDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class IntentoGenerarReporteDaoImpl.
 * <ul>
 * <li>Copyright 2014 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Thu Aug 21 17:34:21 COT 2014
 * @since BuildErp 1.0
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class IntentoGenerarReporteDaoImpl extends GenericColaDAOImpl<String, IntentoGenerarReporte>
		implements IntentoGenerarReporteDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.gob.mapfre.pwr.rep.ejb.dao.local.IntentoGenerarReporteDaoLocal#
	 * listarIntentoGenerarReporte(pe.gob.mapfre.pwr.rep.model.jpa.
	 * IntentoGenerarReporte)
	 */
	@Override
	public List<IntentoGenerarReporte> listar(IntentoGenerarReporte filtro) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		jpaql.append("from IntentoGenerarReporte o  where o.solicitudReporte.idSolicitudReporte=:idSolicitudReporte");
		parametros.put("idSolicitudReporte", filtro.getSolicitudReporte().getIdSolicitudReporte());
		if (!StringUtil.isNullOrEmpty(filtro.getFechaEnvio())) {
			jpaql.append(" and o.fechaEnvio = :fechaEnvio ");
			parametros.put("fechaEnvio", filtro.getFechaEnvio());
		}
		if (!StringUtil.isNullOrEmpty(filtro.getEstado())) {
			jpaql.append(" and upper(o.estado) like :estado ");
			parametros.put("estado", "%" + filtro.getEstado().toUpperCase() + "%");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getDescripcion())) {
			jpaql.append(" and upper(o.descripcion) like :descripcion ");
			parametros.put("descripcion", "%" + filtro.getDescripcion().toUpperCase() + "%");
		}
		jpaql.append(" order by o.numeroIntento desc");
		var query = createQuery(jpaql.toString(), parametros);
		return query.getResultList();
	}

	@Override
	public void eliminarIntentoGenerarReporte(String idSolicitudReporte) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		jpaql.append(
				" delete from IntentoGenerarReporte o where  o.solicitudReporte.idSolicitudReporte =:idSolicitudReporte");
		parametros.put("idSolicitudReporte", idSolicitudReporte);
		var query = createQuery(jpaql.toString(), parametros);
		query.executeUpdate();
	}

	@Override
	public String generarId(String codigoUUID, int incremento) {
		return UUIDUtil.generarElementUUID() + StringUtil.completeLeft(incremento, '0', 3);
	}
}