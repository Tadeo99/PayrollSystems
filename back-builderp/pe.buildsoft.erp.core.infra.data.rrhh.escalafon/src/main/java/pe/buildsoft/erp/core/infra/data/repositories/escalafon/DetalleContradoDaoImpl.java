package pe.buildsoft.erp.core.infra.data.repositories.escalafon;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.escalafon.DetalleContrado;
import pe.buildsoft.erp.core.domain.interfaces.repositories.escalafon.DetalleContradoDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericEscalafonDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class DetalleContradoDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class DetalleContradoDaoImpl extends GenericEscalafonDAOImpl<String, DetalleContrado>
		implements DetalleContradoDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.rrhh.escalafon.dao.local.DetalleContradoDaoLocal
	 * #listarDetalleContrado(pe.com.builderp.core.service.rrhh.escalafon.model.jpa.
	 * DetalleContrado)
	 */
	@Override
	public List<DetalleContrado> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	/**
	 * Generar query lista DetalleContrado.
	 *
	 * @param DetalleContrado el detalleContrado
	 * @param esContador      el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idDetalleContrado) from DetalleContrado o where 1=1 ");
		} else {
			jpaql.append(" select o from DetalleContrado o where 1=1 ");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.idDetalleContrado) like :search ");
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
	 * pe.com.builderp.core.service.rrhh.escalafon.dao.local.DetalleContradoDaoLocal
	 * #contarListar{entity.getClassName()}(pe.com.builderp.core.service.rrhh.
	 * escalafon.model.jpa.DetalleContrado)
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
	 * pe.com.builderp.core.service.rrhh.escalafon.dao.local.DetalleContradoDaoLocal
	 * #generarIdDetalleContrado()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

}