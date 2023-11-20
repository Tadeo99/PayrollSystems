package pe.buildsoft.erp.core.infra.data.repositories.admision;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.admision.Sede;
import pe.buildsoft.erp.core.domain.interfaces.repositories.admision.SedeDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericAdmisionDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * La Class SedeDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Wed Oct 21 20:17:20 COT 2020
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class SedeDaoImpl extends GenericAdmisionDAOImpl<String, Sede> implements SedeDaoLocal {

	private static final String ESTADO_ACTIVO = "estadoActivo";
	private static final String ID_UBIGEO = "idUbigeo";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.admision.dao.local.SedeDaoLocal#listarSede(pe.
	 * com.builderp.core.service.admision.model.jpa.Sede)
	 */
	@Override
	public List<Sede> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		if (filtro.getOffSet() > 0) {
			query.setFirstResult(filtro.getStartRow());
			query.setMaxResults(filtro.getOffSet());
		}
		return query.getResultList();
	}

	/**
	 * Generar query lista Sede.
	 *
	 * @param Sede       el sede
	 * @param esContador el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idSede) from Sede o where 1=1 ");
		} else {
			jpaql.append(" select o from Sede o  where 1=1 ");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and (upper(o.codigo) like :search or upper(o.nombre) like :search ) ");
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		} else {
			if (!StringUtil.isNullOrEmpty(filtro.getCodigoSearch())) {
				jpaql.append(" and o.codigo=:codigo ");
				parametros.put("codigo", filtro.getCodigoSearch());
			}
			if (!StringUtil.isNullOrEmpty(filtro.getIdNivel())) {
				jpaql.append(" and o.idUbigeo =:idUbigeo ");
				parametros.put(ID_UBIGEO, filtro.getIdNivel());
			}
		}
		if (!esContador) {
			if (StringUtil.isNotNullOrBlank(filtro.getSortFields())) {
				jpaql.append(" ORDER BY " + filtro.getSortFields() + " " + filtro.getSortDirections());
			}
		}
		return createQuery(jpaql.toString(), parametros);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.admision.dao.local.SedeDaoLocal#contarListar{
	 * entity.getClassName()}(pe.com.builderp.core.service.admision.model.jpa.Sede)
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
	 * pe.com.builderp.core.service.admision.dao.local.SedeDaoLocal#generarIdSede()
	 */
	@Override
	public String generarId() {
		String resultado = "1";
		var query = createQuery("select max(o.idSede) from Sede o", null);
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