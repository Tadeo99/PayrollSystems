package pe.buildsoft.erp.core.infra.data.repositories.migrador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.migrador.HeaderReadReporte;
import pe.buildsoft.erp.core.domain.interfaces.repositories.migrador.HeaderReadReporteDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericCommonBatchDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ScriptSqlResulJDBCVO;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class HeaderReadReporteDaoImpl.
 * <ul>
 * <li>Copyright 2018 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Wed Aug 07 14:48:19 COT 2019
 * @since BuildErp 1.0
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class HeaderReadReporteDaoImpl extends GenericCommonBatchDAOImpl<String, HeaderReadReporte>
		implements HeaderReadReporteDaoLocal {

	private Logger log = LoggerFactory.getLogger(HeaderReadReporteDaoImpl.class);

	@Override
	public List<Map<String, Object>> listar(String codigoReporte) {
		ScriptSqlResulJDBCVO data;
		try {
			data = executeQuery(new StringBuilder("select * from TBL_HEADER_READ_REP where C_CODIGO_REPORTE='"
					+ codigoReporte + "' order by N_ORDEN "), null);
			if (!data.isTieneError() && data.getListaData().size() > 0) {
				return data.getListaData();
			}
		} catch (Exception e) {
			log.error("listar",e);
		}

		return new ArrayList<>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.gob.mapfre.pwr.rep.ejb.dao.mantenimiento.cubos.oa.local.
	 * HeaderReadReporteDaoLocal#listarHeaderReadReporte(pe.gob.mapfre.pwr.rep.model
	 * .mantenimiento.cubos.oa.jpa.HeaderReadReporte)
	 */
	@Override
	public List<HeaderReadReporte> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	/**
	 * Generar query lista HeaderReadReporte.
	 *
	 * @param HeaderReadReporte el headerReadReporte
	 * @param esContador        el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		Map<String, Object> parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idHeaderReadReporte) from HeaderReadReporte o where 1=1 ");
		} else {
			jpaql.append(" select o from HeaderReadReporte o where 1=1 ");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getCodigoReporte())) {
			jpaql.append(" and upper(o.codigoReporte) like :codigoReporte ");
			parametros.put("codigoReporte", "%" + filtro.getCodigoReporte().toUpperCase() + "%");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getEstado())) {
			jpaql.append(" and upper(o.estado) like :estado ");
			parametros.put("estado", "%" + filtro.getEstado().toUpperCase() + "%");
		}
		if (!esContador) {
			jpaql.append(" ORDER BY o.codigoReporte,o.orden ");
		}
		return createQuery(jpaql.toString(), parametros);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.gob.mapfre.pwr.rep.ejb.dao.mantenimiento.cubos.oa.local.
	 * HeaderReadReporteDaoLocal#contarListar{entity.getClassName()}(pe.gob.mapfre.
	 * pwr.rep.model.mantenimiento.cubos.oa.jpa.HeaderReadReporte)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.gob.mapfre.pwr.rep.ejb.dao.mantenimiento.cubos.oa.local.
	 * HeaderReadReporteDaoLocal#generarIdHeaderReadReporte()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

}