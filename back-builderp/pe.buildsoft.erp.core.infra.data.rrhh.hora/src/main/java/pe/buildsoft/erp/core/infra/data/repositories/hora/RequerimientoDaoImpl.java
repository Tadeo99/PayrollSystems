package pe.buildsoft.erp.core.infra.data.repositories.hora;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.hora.Requerimiento;
import pe.buildsoft.erp.core.domain.interfaces.repositories.hora.RequerimientoDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericHoraDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class PeriodoDaoImpl.
 * <ul>
 * <li>Copyright 2021 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Thu Feb 24 15:07:25 COT 2022
 * @since BuildErp 1.0
 */
@Stateless
public class RequerimientoDaoImpl extends GenericHoraDAOImpl<String, Requerimiento> implements RequerimientoDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.ejb.dao.seguridad.local.requerimientoDaoLocal#
	 * listarrequerimiento(pe.com.builderp.core.model.jpa.seguridad.requerimiento)
	 */
	@Override
	public List<Requerimiento> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	/**
	 * Generar query lista requerimiento.
	 *
	 * @param filtro     el requerimiento
	 * @param esContador el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idRequerimiento) from Requerimiento o where 1=1 ");
		} else {
			jpaql.append(
					" select o from Requerimiento o left join fetch o.personal per left join fetch o.centroCosto c left join fetch o.itemByTipoGobierno i  where 1=1 ");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.nombre) like :search or upper(o.codigo) like :search ");
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		}

		if (!StringUtil.isNullOrEmpty(filtro.getCodigo())) {
			jpaql.append(" and o.codigo like :codigo ");
			parametros.put("codigo", "%" + filtro.getCodigo() + "%");
		}
		if (!esContador) {
			jpaql.append(" ORDER BY o.nombre ");
		}
		return createQuery(jpaql.toString(), parametros);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.ejb.dao.seguridad.local.requerimientoDaoLocal#
	 * contarListar{entity.getClassName()}(pe.com.builderp.core.model.jpa.seguridad.
	 * requerimiento)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.ejb.dao.seguridad.local.requerimientoDaoLocal#
	 * generarIdrequerimiento()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

}