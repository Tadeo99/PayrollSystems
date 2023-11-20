package pe.buildsoft.erp.core.infra.data.repositories.cola;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.cola.ValorConfiguracionFiltroReporte;
import pe.buildsoft.erp.core.domain.interfaces.servicios.cola.ValorConfiguracionFiltroReporteDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericColaDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.CollectionUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * La Class ValorConfiguracionFiltroReporteDaoImpl.
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
public class ValorConfiguracionFiltroReporteDaoImpl extends GenericColaDAOImpl<String, ValorConfiguracionFiltroReporte>
		implements ValorConfiguracionFiltroReporteDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.gob.mapfre.pwr.rep.ejb.dao.configuracion.cola.local.
	 * ValorConfiguracionFiltroReporteDaoLocal#registrarMasivo(java.util.List)
	 */
	@Override
	public boolean registrarMasivo(List<ValorConfiguracionFiltroReporte> listaObj) {
		return insertMasivo(listaObj);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.gob.mapfre.pwr.rep.ejb.dao.configuracion.cola.local.
	 * ValorConfiguracionFiltroReporteDaoLocal#listarValorConfiguracionFiltroReporte
	 * (pe.gob.mapfre.pwr.rep.model.configuracion.cola.jpa.
	 * ValorConfiguracionFiltroReporte)
	 */
	@Override
	public List<ValorConfiguracionFiltroReporte> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	/**
	 * Generar query lista ValorConfiguracionFiltroReporte.
	 *
	 * @param ValorConfiguracionFiltroReporte el valorConfiguracionFiltroReporte
	 * @param esContador                      el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idValorFiltro) from ValorConfiguracionFiltroReporte o where 1=1 ");
		} else {
			jpaql.append(" select o from ValorConfiguracionFiltroReporte o where 1=1 ");
		}
		if (!StringUtil.isNullOrEmptyNumeric(filtro.getIdSolicitudReporte())) {
			jpaql.append(" and o.idSolicitudReporte = :idSolicitudReporte ");
			parametros.put("idSolicitudReporte", filtro.getIdSolicitudReporte());
		}
		/*if (!StringUtil.isNullOrEmpty(filtro.getValorFiltroText())) {
			jpaql.append(" and upper(o.valorFiltroText) like :valorFiltroText ");
			parametros.put("valorFiltroText", "%" + filtro.getValorFiltroText().toUpperCase() + "%");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getValorFiltro())) {
			jpaql.append(" and upper(o.valorFiltro) like :valorFiltro ");
			parametros.put("valorFiltro", "%" + filtro.getValorFiltro().toUpperCase() + "%");
		}*/
		if (!StringUtil.isNullOrEmpty(filtro.getEstado())) {
			jpaql.append(" and upper(o.estado) like :estado ");
			parametros.put("estado", "%" + filtro.getEstado().toUpperCase() + "%");
		}
		if (!esContador) {
			// jpaql.append(" ORDER BY 1 ");
		}
		return createQuery(jpaql.toString(), parametros);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.gob.mapfre.pwr.rep.ejb.dao.configuracion.cola.local.
	 * ValorConfiguracionFiltroReporteDaoLocal#contarListar{entity.getClassName()}(
	 * pe.gob.mapfre.pwr.rep.model.configuracion.cola.jpa.
	 * ValorConfiguracionFiltroReporte)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.gob.mapfre.pwr.rep.ejb.dao.configuracion.cola.local.
	 * ValorConfiguracionFiltroReporteDaoLocal#
	 * generarIdValorConfiguracionFiltroReporte()
	 */
	@Override
	public String generarId() {
		String resultado = "1";
		var query = createQuery("select max(o.idValorFiltro) from ValorConfiguracionFiltroReporte o", null);
		var listLong = query.getResultList();
		if (listLong != null && !listLong.isEmpty() && listLong.get(0) != null) {
			Long ultimoIdGenerado = Long.valueOf(listLong.get(0).toString());
			if (!StringUtil.isNullOrEmpty(ultimoIdGenerado)) {
				resultado = resultado + ultimoIdGenerado;
			}
		}
		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.gob.mapfre.pwr.rep.ejb.dao.configuracion.cola.local.
	 * ValorConfiguracionFiltroReporteDaoLocal#listarDatosFiltro(java.util.List)
	 */
	@Override
	public List<ValorConfiguracionFiltroReporte> listarDatosFiltro(List<Long> keyList, List<String> listaLugarAparece) {
		if (CollectionUtil.isEmpty(keyList) || CollectionUtil.isEmpty(listaLugarAparece)) {
			return new ArrayList<>();
		}
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		jpaql.append(
				" select o from ValorConfiguracionFiltroReporte o left join fetch o.configuracionFiltroReporte r where 1 = 1 ");
		jpaql.append(" and o.idSolicitudReporte IN :idSolicitudReporte and r.visibleFiltro IN :listaLugarAparece ");
		jpaql.append(" order by r.ordenFiltro ");
		parametros.put("idSolicitudReporte", keyList);
		parametros.put("listaLugarAparece", listaLugarAparece);
		var query = createQuery(jpaql.toString(), parametros);
		return query.getResultList();
	}

	@Override
	public boolean eliminarByIdSolicitudReporte(String idSolicitudReporte) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		jpaql.append(" DELETE FROM TRON2000.GCM_VALOR_FILTRO WHERE N_ID_SOL_REPORT = :idSolicitudReporte ");
		parametros.put("idSolicitudReporte", idSolicitudReporte);
		var query = createNativeQuery(jpaql.toString(), parametros);
		return query.executeUpdate() > 0;
	}
}