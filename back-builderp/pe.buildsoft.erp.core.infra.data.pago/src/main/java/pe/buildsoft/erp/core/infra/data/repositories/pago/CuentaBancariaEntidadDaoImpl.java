package pe.buildsoft.erp.core.infra.data.repositories.pago;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.pago.CuentaBancariaEntidad;
import pe.buildsoft.erp.core.domain.interfaces.repositories.pago.CuentaBancariaEntidadDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericPagoDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class CuentaBancariaEntidadDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 21:45:29 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class CuentaBancariaEntidadDaoImpl extends GenericPagoDAOImpl<String, CuentaBancariaEntidad>
		implements CuentaBancariaEntidadDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.pago.dao.local.CuentaBancariaEntidadDaoLocal#
	 * listarCuentaBancariaEntidad(pe.com.builderp.core.service.pago.model.jpa.
	 * CuentaBancariaEntidad)
	 */
	@Override
	public List<CuentaBancariaEntidad> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	/**
	 * Generar query lista CuentaBancariaEntidad.
	 *
	 * @param CuentaBancariaEntidad el cuentaBancariaEntidad
	 * @param esContador            el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idCuentaBancariaEntidad) from CuentaBancariaEntidad o where 1=1 ");
		} else {
			jpaql.append(" select o from CuentaBancariaEntidad o left join fetch o.itemByBanco  ");
			jpaql.append(" left join fetch o.itemByMoneda  left join fetch o.itemByTipoCuenta ");
			jpaql.append("  left join fetch o.titular where 1=1");
		}

		jpaql.append(" and o.entidad =:idEntidad ");
		parametros.put("idEntidad", filtro.getIdEntidadSelect());

		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and (upper(o.nroCuenta) like :search   or upper(o.itemByBanco.nombre) like :search  ) ");
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
	 * @see
	 * pe.com.builderp.core.service.pago.dao.local.CuentaBancariaEntidadDaoLocal#
	 * contarListar{entity.getClassName()}(pe.com.builderp.core.service.pago.model.
	 * jpa.CuentaBancariaEntidad)
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
	 * pe.com.builderp.core.service.pago.dao.local.CuentaBancariaEntidadDaoLocal#
	 * generarIdCuentaBancariaEntidad()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

}