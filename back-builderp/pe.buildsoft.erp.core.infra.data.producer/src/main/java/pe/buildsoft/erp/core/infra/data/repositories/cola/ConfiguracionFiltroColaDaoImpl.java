package pe.buildsoft.erp.core.infra.data.repositories.cola;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.cola.ConfiguracionFiltroCola;
import pe.buildsoft.erp.core.domain.interfaces.repositories.cola.ConfiguracionFiltroColaDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericColaDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class ConfiguracionFiltroColaDaoImpl.
 * <ul>
 * <li>Copyright 2015 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 2.1, Thu Dec 21 12:25:45 COT 2017
 * @since BuildErp 1.0
 */
@Stateless
public class ConfiguracionFiltroColaDaoImpl extends GenericColaDAOImpl<String, ConfiguracionFiltroCola>
		implements ConfiguracionFiltroColaDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.gob.mapfre.pwr.rep.ejb.dao.configuracion.cola.local.
	 * ConfiguracionFiltroColaDaoLocal#listarConfiguracionFiltroCola(pe.gob.mapfre.
	 * pwr.rep.model.configuracion.cola.jpa.ConfiguracionFiltroCola)
	 */
	@Override
	public List<ConfiguracionFiltroCola> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	/**
	 * Generar query lista ConfiguracionFiltroCola.
	 *
	 * @param ConfiguracionFiltroCola el configuracionFiltroCola
	 * @param esContador              el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idJuegoFiltro) from ConfiguracionFiltroCola o where 1=1 ");
		} else {
			jpaql.append(
					" select o from ConfiguracionFiltroCola o left join fetch o.cola  left join fetch o.configuracionFiltroReporte left join fetch o.configuracionFiltroReporteRango where 1=1 ");
		}
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
	 * ConfiguracionFiltroColaDaoLocal#contarListar{entity.getClassName()}(pe.gob.
	 * mapfre.pwr.rep.model.configuracion.cola.jpa.ConfiguracionFiltroCola)
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
	 * ConfiguracionFiltroColaDaoLocal#generarIdConfiguracionFiltroCola()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

}