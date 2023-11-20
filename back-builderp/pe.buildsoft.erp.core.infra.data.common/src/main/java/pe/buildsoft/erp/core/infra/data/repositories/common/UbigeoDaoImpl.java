package pe.buildsoft.erp.core.infra.data.repositories.common;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.common.Ubigeo;
import pe.buildsoft.erp.core.domain.interfaces.repositories.common.UbigeoDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericCommonDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * La Class UbigeoDaoImpl.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue Apr 18 19:53:42 COT 2017
 * @since SIAA-CORE 2.1
 */
@Stateless
public class UbigeoDaoImpl extends GenericCommonDAOImpl<String, Ubigeo> implements UbigeoDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.edu.siaa.core.ejb.dao.seguridad.local.UbigeoDaoLocal#listarUbigeo(pe.
	 * com.edu.siaa.core.model.jpa.seguridad.Ubigeo)
	 */
	@Override
	public List<Ubigeo> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		if (filtro.getOffSet() > 0) {
			query.setFirstResult(filtro.getStartRow());
			query.setMaxResults(filtro.getOffSet());
		}
		return query.getResultList();
	}

	/**
	 * Generar query lista Ubigeo.
	 *
	 * @param Ubigeo     el ubigeo
	 * @param esContador el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idUbigeo) from Ubigeo o where 1=1 ");
		} else {
			jpaql.append(" select o from Ubigeo o left join fetch o.ubigeoByDependencia where 1=1 ");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getId())) {// comodin
			jpaql.append(" and o.ubigeoByDependencia.idUbigeo =:idUbigeoDependencia ");
			parametros.put("idUbigeoDependencia", filtro.getId() + "");
		}

		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.descripcion) like :search ");
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getTipo())) {
			jpaql.append(" and o.tipo = :tipo ");
			parametros.put("tipo", filtro.getTipo());
		}

		if (!esContador) {
			jpaql.append(" ORDER BY o."+ filtro.getSortFields() +" " + filtro.getSortDirections());
		}
		return createQuery(jpaql.toString(), parametros);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.edu.siaa.core.ejb.dao.seguridad.local.UbigeoDaoLocal#contarListar{
	 * entity.getClassName()}(pe.com.edu.siaa.core.model.jpa.seguridad.Ubigeo)
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
	 * pe.com.edu.siaa.core.ejb.dao.seguridad.local.UbigeoDaoLocal#generarIdUbigeo()
	 */
	@Override
	public String generarId() {
		String resultado = "1";
		var query = createQuery("select max(o.idUbigeo) from Ubigeo o", null);
		var listLong = query.getResultList();
		if (listLong != null && !listLong.isEmpty() && listLong.get(0) != null) {
			Long ultimoIdGenerado = Long.valueOf(listLong.get(0).toString());
			if (!StringUtil.isNullOrEmpty(ultimoIdGenerado)) {
				resultado = resultado + ultimoIdGenerado;
			}
		}
		return resultado;
	}

}