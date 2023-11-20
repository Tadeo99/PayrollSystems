package pe.buildsoft.erp.core.infra.data.repositories.pago;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.pago.CuotaConcepto;
import pe.buildsoft.erp.core.domain.interfaces.repositories.pago.CuotaConceptoDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericPagoDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ObjectUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class CuotaConceptoDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 21:45:29 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class CuotaConceptoDaoImpl extends GenericPagoDAOImpl<String, CuotaConcepto> implements CuotaConceptoDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.pago.dao.local.CuotaConceptoDaoLocal#
	 * listarCuotaConcepto(pe.com.builderp.core.service.pago.model.jpa.
	 * CuotaConcepto)
	 */
	@Override
	public List<CuotaConcepto> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	/**
	 * Generar query lista CuotaConcepto.
	 *
	 * @param CuotaConcepto el cuotaConcepto
	 * @param esContador    el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idCuotaConcepto) from CuotaConcepto o where 1=1 ");
		} else {
			jpaql.append(" select o from CuotaConcepto o  ");
			jpaql.append(" left join fetch o.anhio left join fetch o.itemByNivel ");
			jpaql.append(" left join fetch o.catalogoCuenta where 1=1 ");
		}

		jpaql.append(" and o.catalogoCuenta.clasificacion.entidad =:idEntidad ");
		parametros.put("idEntidad", filtro.getIdEntidadSelect());

		if (!StringUtil.isNullOrEmpty(filtro.getId())) {
			jpaql.append(" and o.itemByNivel.idItem  =:item ");
			parametros.put("item", ObjectUtil.objectToLong(filtro.getId()));
		}

		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.catalogoCuenta.cuenta) like :search ");
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
	 * @see pe.com.builderp.core.service.pago.dao.local.CuotaConceptoDaoLocal#
	 * contarListar{entity.getClassName()}(pe.com.builderp.core.service.pago.model.
	 * jpa.CuotaConcepto)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.pago.dao.local.CuotaConceptoDaoLocal#
	 * generarIdCuotaConcepto()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

}