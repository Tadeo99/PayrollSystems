package pe.buildsoft.erp.core.infra.data.repositories.planilla;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.planilla.RentaQuintaCategoria;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.RentaQuintaCategoriaDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericPlanillaDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class RentaQuintaCategoriaDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class RentaQuintaCategoriaDaoImpl extends GenericPlanillaDAOImpl<String, RentaQuintaCategoria>
		implements RentaQuintaCategoriaDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.rrhh.planilla.dao.local.
	 * RentaQuintaCategoriaDaoLocal#listarRentaQuintaCategoria(pe.com.builderp.core.
	 * service.rrhh.planilla.model.jpa.RentaQuintaCategoria)
	 */
	@Override
	public List<RentaQuintaCategoria> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	/**
	 * Generar query lista RentaQuintaCategoria.
	 *
	 * @param RentaQuintaCategoria el rentaQuintaCategoria
	 * @param esContador           el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idRentaQuintaCategoria) from RentaQuintaCategoria o where 1=1 ");
		} else {
			jpaql.append(" select o from RentaQuintaCategoria o where 1=1 ");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.idRentaQuintaCategoria) like :search ");
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
	 * @see pe.com.builderp.core.service.rrhh.planilla.dao.local.
	 * RentaQuintaCategoriaDaoLocal#contarListar{entity.getClassName()}(pe.com.
	 * builderp.core.service.rrhh.planilla.model.jpa.RentaQuintaCategoria)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.rrhh.planilla.dao.local.
	 * RentaQuintaCategoriaDaoLocal#generarIdRentaQuintaCategoria()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

}