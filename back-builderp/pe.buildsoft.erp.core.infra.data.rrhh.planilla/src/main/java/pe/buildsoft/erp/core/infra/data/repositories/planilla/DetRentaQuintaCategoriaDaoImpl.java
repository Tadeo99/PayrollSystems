package pe.buildsoft.erp.core.infra.data.repositories.planilla;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.planilla.DetRentaQuintaCategoria;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.DetRentaQuintaCategoriaDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericPlanillaDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class DetRentaQuintaCategoriaDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class DetRentaQuintaCategoriaDaoImpl extends GenericPlanillaDAOImpl<String, DetRentaQuintaCategoria>
		implements DetRentaQuintaCategoriaDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.rrhh.planilla.dao.local.
	 * DetRentaQuintaCategoriaDaoLocal#listarDetRentaQuintaCategoria(pe.com.builderp
	 * .core.service.rrhh.planilla.model.jpa.DetRentaQuintaCategoria)
	 */
	@Override
	public List<DetRentaQuintaCategoria> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	/**
	 * Generar query lista DetRentaQuintaCategoria.
	 *
	 * @param DetRentaQuintaCategoria el detRentaQuintaCategoria
	 * @param esContador              el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idDetRentaQuintaCategoria) from DetRentaQuintaCategoria o where 1=1 ");
		} else {
			jpaql.append(" select o from DetRentaQuintaCategoria o where 1=1 ");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.idDetRentaQuintaCategoria) like :search ");
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
	 * DetRentaQuintaCategoriaDaoLocal#contarListar{entity.getClassName()}(pe.com.
	 * builderp.core.service.rrhh.planilla.model.jpa.DetRentaQuintaCategoria)
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
	 * DetRentaQuintaCategoriaDaoLocal#generarIdDetRentaQuintaCategoria()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

}