package pe.buildsoft.erp.core.infra.data.repositories.cola;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.cola.ColaNoctura;
import pe.buildsoft.erp.core.domain.interfaces.repositories.cola.ColaNocturaDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericColaDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.state.EstadoSolicitudEjecucionEstate;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class ColaNocturaDaoImpl.
 * <ul>
 * <li>Copyright 2015 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 2.1, Thu Dec 21 12:25:46 COT 2017
 * @since BuildErp 1.0
 */
@Stateless
public class ColaNocturaDaoImpl extends GenericColaDAOImpl<String, ColaNoctura> implements ColaNocturaDaoLocal {

	@Override
	public String actualizarEstadoColaNoctura(String idColaNocturna,
			EstadoSolicitudEjecucionEstate estadoSolicitudEjecucionEstate) {
		String resultado = "";
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		jpaql.append("update ColaNoctura set estadoProceso =:estado  where idColaNocturna =:idColaNocturna ");
		parametros.put("idColaNocturna", idColaNocturna);
		parametros.put("estado", estadoSolicitudEjecucionEstate.getKey());
		var query = createQuery(jpaql.toString(), parametros);
		query.executeUpdate();
		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.gob.mapfre.pwr.rep.ejb.dao.configuracion.cola.local.ColaNocturaDaoLocal#
	 * listarColaNoctura(pe.gob.mapfre.pwr.rep.model.configuracion.cola.jpa.
	 * ColaNoctura)
	 */
	@Override
	public List<ColaNoctura> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	/**
	 * Generar query lista ColaNoctura.
	 *
	 * @param ColaNoctura el colaNoctura
	 * @param esContador  el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idColaNocturna) from ColaNoctura o where 1=1 ");
		} else {
			jpaql.append(" select o from ColaNoctura o where 1=1 ");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getEstadoProceso())) {
			jpaql.append(" and o.estadoProceso =:estadoProceso ");
			parametros.put("estadoProceso", filtro.getEstadoProceso());
		}
		if (!StringUtil.isNullOrEmpty(filtro.getEstado())) {
			jpaql.append(" and o.estado =:estado ");
			parametros.put("estado", filtro.getEstado());
		}
		if (!esContador) {
			// jpaql.append(" ORDER BY 1 ");
		}
		return createQuery(jpaql.toString(), parametros);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.gob.mapfre.pwr.rep.ejb.dao.configuracion.cola.local.ColaNocturaDaoLocal#
	 * contarListar{entity.getClassName()}(pe.gob.mapfre.pwr.rep.model.configuracion
	 * .cola.jpa.ColaNoctura)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.gob.mapfre.pwr.rep.ejb.dao.configuracion.cola.local.ColaNocturaDaoLocal#
	 * generarIdColaNoctura()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

}