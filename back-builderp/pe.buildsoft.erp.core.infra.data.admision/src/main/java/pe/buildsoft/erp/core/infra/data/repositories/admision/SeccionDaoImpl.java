package pe.buildsoft.erp.core.infra.data.repositories.admision;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.admision.Seccion;
import pe.buildsoft.erp.core.domain.interfaces.repositories.admision.SeccionDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericAdmisionDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ObjectUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * La Class SeccionDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Wed Apr 21 12:29:28 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class SeccionDaoImpl extends GenericAdmisionDAOImpl<Long, Seccion> implements SeccionDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.admision.dao.local.SeccionDaoLocal#listarSeccion
	 * (pe.com.builderp.core.service.admision.model.jpa.Seccion)
	 */
	@Override
	public List<Seccion> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	/**
	 * Generar query lista Seccion.
	 *
	 * @param Seccion    el seccion
	 * @param esContador el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idSeccion) from Seccion o where 1=1 ");
		} else {
			jpaql.append(" select o from Seccion o left join fetch o.grado where 1=1 ");
		}
		if (!StringUtil.isNullOrEmptyNumeric(filtro.getIdNivel())) {
			jpaql.append(" and o.grado.idItemByNivel = :idItemByNivel ");
			parametros.put("idItemByNivel", ObjectUtil.objectToLong(filtro.getIdNivel()));
		}
		if (!StringUtil.isNullOrEmptyNumeric(filtro.getId())) {
			jpaql.append(" and o.grado.idGrado = :idGrado ");
			parametros.put("idGrado", ObjectUtil.objectToLong(filtro.getId()));
		}
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.nombre) like :search ");
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		} else {
			if (!StringUtil.isNullOrEmpty(filtro.getCodigoSearch())) {
				jpaql.append(" and o.codigo =:codigo ");
				parametros.put("codigo", filtro.getCodigoSearch());
			}
		}
		if (!esContador) {
			jpaql.append(" ORDER BY o." + filtro.getSortFields() + " " + filtro.getSortDirections());
		}
		return createQuery(jpaql.toString(), parametros);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.admision.dao.local.SeccionDaoLocal#contarListar{
	 * entity.getClassName()}(pe.com.builderp.core.service.admision.model.jpa.
	 * Seccion)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.admision.dao.local.SeccionDaoLocal#
	 * generarIdSeccion()
	 */
	@Override
	public Long generarId() {
		var resultado = 1L;
		var query = createQuery("select max(o.idSeccion) from Seccion o", null);
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