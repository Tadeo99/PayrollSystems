package pe.buildsoft.erp.core.infra.data.repositories.cola;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.cola.Cola;
import pe.buildsoft.erp.core.domain.interfaces.repositories.cola.ColaDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericColaDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * La Class ColaDaoImpl.
 * <ul>
 * <li>Copyright 2015 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 2.1, Thu Dec 21 12:25:46 COT 2017
 * @since BuildErp 1.0
 */
@Stateless
public class ColaDaoImpl extends GenericColaDAOImpl<Long, Cola> implements ColaDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.gob.mapfre.pwr.rep.ejb.dao.configuracion.cola.local.ColaDaoLocal#
	 * listarCola(pe.gob.mapfre.pwr.rep.model.configuracion.cola.jpa.Cola)
	 */
	@Override
	public List<Cola> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	/**
	 * Generar query lista Cola.
	 *
	 * @param Cola       el cola
	 * @param esContador el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idCola) from Cola o where 1=1 ");
		} else {
			jpaql.append(" select o from Cola o where 1=1 ");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getEstado())) {
			jpaql.append(" and upper(o.estado) =:estado ");
			parametros.put("estado", filtro.getEstado().toUpperCase());
		}
		if (!esContador) {
			// jpaql.append(" ORDER BY 1 ");
		}
		return createQuery(jpaql.toString(), parametros);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.gob.mapfre.pwr.rep.ejb.dao.configuracion.cola.local.ColaDaoLocal#
	 * contarListar{entity.getClassName()}(pe.gob.mapfre.pwr.rep.model.configuracion
	 * .cola.jpa.Cola)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.gob.mapfre.pwr.rep.ejb.dao.configuracion.cola.local.ColaDaoLocal#
	 * generarIdCola()
	 */
	@Override
	public Long generarId() {
		var resultado = 1L;
		var query = createQuery("select max(o.idCola) from Cola o", null);
		var listLong = query.getResultList();
		if (listLong != null && !listLong.isEmpty() && listLong.get(0) != null) {
			var ultimoIdGenerado = Long.valueOf(listLong.get(0).toString());
			if (!StringUtil.isNullOrEmpty(ultimoIdGenerado)) {
				resultado = resultado + ultimoIdGenerado;
			}
		}
		return resultado;
	}

}