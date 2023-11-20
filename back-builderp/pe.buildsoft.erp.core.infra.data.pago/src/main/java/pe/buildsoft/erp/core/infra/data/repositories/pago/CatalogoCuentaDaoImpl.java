package pe.buildsoft.erp.core.infra.data.repositories.pago;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.pago.CatalogoCuenta;
import pe.buildsoft.erp.core.domain.interfaces.repositories.pago.CatalogoCuentaDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericPagoDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * La Class CatalogoCuentaDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 21:45:30 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class CatalogoCuentaDaoImpl extends GenericPagoDAOImpl<Long, CatalogoCuenta> implements CatalogoCuentaDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.pago.dao.local.CatalogoCuentaDaoLocal#
	 * listarCatalogoCuenta(pe.com.builderp.core.service.pago.model.jpa.
	 * CatalogoCuenta)
	 */
	@Override
	public List<CatalogoCuenta> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	/**
	 * Generar query lista CatalogoCuenta.
	 *
	 * @param CatalogoCuenta el catalogoCuenta
	 * @param esContador     el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idCatalogoCuenta) from CatalogoCuenta o where 1=1 ");
		} else {
			jpaql.append(" select o from CatalogoCuenta o left join fetch o.clasificacion ");
			jpaql.append(" where 1=1 ");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getIdEntidadSelect())) {
			jpaql.append(" and o.clasificacion.entidad =:idEntidadSelect");
			parametros.put("idEntidadSelect", filtro.getIdEntidadSelect());
		}

		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.cuenta) like :search ");
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		}
		if (!esContador) {
			jpaql.append(" ORDER BY " + filtro.getSortFields() + " " + filtro.getSortDirections());
		}
		return createQuery(jpaql.toString(), parametros);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.pago.dao.local.CatalogoCuentaDaoLocal#
	 * contarListar{entity.getClassName()}(pe.com.builderp.core.service.pago.model.
	 * jpa.CatalogoCuenta)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.pago.dao.local.CatalogoCuentaDaoLocal#
	 * generarIdCatalogoCuenta()
	 */
	@Override
	public Long generarId() {
		var resultado = 1L;
		var query = createQuery("select max(o.idCatalogoCuenta) from CatalogoCuenta o", null);
		var listLong = query.getResultList();
		if (listLong != null && !listLong.isEmpty() && listLong.get(0) != null) {
			Long ultimoIdGenerado = Long.valueOf(listLong.get(0).toString());
			if (!StringUtil.isNullOrEmpty(ultimoIdGenerado)) {
				resultado = resultado + ultimoIdGenerado;
			}
		}
		return resultado;
	}

	@Override
	public CatalogoCuenta find(CatalogoCuenta filtro) {
		CatalogoCuenta resultado = new CatalogoCuenta();
		var parametros = new HashMap<String, Object>();
		var ejecutarQuery = false;
		var jpaql = new StringBuilder();
		jpaql.append(" from CatalogoCuenta o  where 1=1 ");
		if (!StringUtil.isNullOrEmpty(filtro.getCuenta())) {
			ejecutarQuery = true;
			jpaql.append(" and upper(o.cuenta)=upper(:cuenta)");
			parametros.put("cuenta", filtro.getCuenta());
		}
		if (ejecutarQuery) {
			var query = createQuery(jpaql.toString(), parametros);
			List<CatalogoCuenta> listaObj = query.getResultList();
			if (listaObj != null && !listaObj.isEmpty()) {
				resultado = listaObj.get(0);
			}
		}
		return resultado;
	}

}