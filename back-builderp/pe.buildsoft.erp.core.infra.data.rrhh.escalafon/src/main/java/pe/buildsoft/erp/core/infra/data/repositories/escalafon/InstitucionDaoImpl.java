package pe.buildsoft.erp.core.infra.data.repositories.escalafon;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.escalafon.Institucion;
import pe.buildsoft.erp.core.domain.interfaces.repositories.escalafon.InstitucionDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericEscalafonDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * La Class InstitucionDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Wed Jul 22 10:43:15 COT 2020
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class InstitucionDaoImpl extends GenericEscalafonDAOImpl<Long, Institucion> implements InstitucionDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.rrhh.dao.local.InstitucionDaoLocal#
	 * listarInstitucion(pe.com.builderp.core.service.rrhh.model.jpa.Institucion)
	 */
	@Override
	public List<Institucion> listar(BaseSearch filtro) {
		var query = generarQueryListaInstitucion(filtro, false);
		if (filtro.getOffSet() > 0) {
			query.setFirstResult(filtro.getStartRow());
			query.setMaxResults(filtro.getOffSet());
		}
		return query.getResultList();
	}

	/**
	 * Generar query lista Institucion.
	 *
	 * @param Institucion el institucion
	 * @param esContador  el es contador
	 * @return the query
	 */
	private Query generarQueryListaInstitucion(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idInstitucion) from Institucion o where 1=1 ");
		} else {
			jpaql.append(" select o from Institucion o ");
			jpaql.append(" where 1=1 ");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.idItemByTipoInstitucion) like :search ");
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		} else {
			if (!StringUtil.isNullOrEmpty(filtro.getEstado())) {
				jpaql.append(" and upper(o.estado) like :estado ");
				parametros.put("estado", "%" + filtro.getEstado().toUpperCase() + "%");
			}
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
	 * pe.com.builderp.core.service.rrhh.dao.local.InstitucionDaoLocal#contarListar{
	 * entity.getClassName()}(pe.com.builderp.core.service.rrhh.model.jpa.
	 * Institucion)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQueryListaInstitucion(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.rrhh.dao.local.InstitucionDaoLocal#
	 * generarIdInstitucion()
	 */
	@Override
	public Long generarId() {
		var resultado = 1L;
		var query = createQuery("select max(o.idInstitucion) from Institucion o", null);
		var listLong = query.getResultList();
		if (listLong != null && listLong.size() > 0 && listLong.get(0) != null) {
			var ultimoIdGenerado = Long.valueOf(listLong.get(0).toString());
			if (!StringUtil.isNullOrEmpty(ultimoIdGenerado)) {
				resultado = resultado + ultimoIdGenerado;
			}
		}
		return resultado;
	}

}