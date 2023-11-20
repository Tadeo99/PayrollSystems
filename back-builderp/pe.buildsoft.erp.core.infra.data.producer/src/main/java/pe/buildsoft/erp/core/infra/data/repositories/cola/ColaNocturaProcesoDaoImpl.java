package pe.buildsoft.erp.core.infra.data.repositories.cola;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.cola.ColaNoctura;
import pe.buildsoft.erp.core.domain.interfaces.repositories.cola.ColaNocturaProcesoDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericColaDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

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
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@TransactionManagement(TransactionManagementType.BEAN)
public class ColaNocturaProcesoDaoImpl extends GenericColaDAOImpl<String, ColaNoctura>
		implements ColaNocturaProcesoDaoLocal {

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
		jpaql.append(" and o.estadoProceso =:estadoProceso ");
		parametros.put("estadoProceso", filtro.getEstadoProceso());

		jpaql.append(" and o.estado =:estado ");
		parametros.put("estado", filtro.getEstado());

		return createQuery(jpaql.toString(), parametros);
	}

	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

}