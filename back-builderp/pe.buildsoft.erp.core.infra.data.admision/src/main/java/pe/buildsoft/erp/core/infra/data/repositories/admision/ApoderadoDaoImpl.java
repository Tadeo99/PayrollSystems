package pe.buildsoft.erp.core.infra.data.repositories.admision;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.admision.Apoderado;
import pe.buildsoft.erp.core.domain.entidades.admision.vo.BaseSearchVO;
import pe.buildsoft.erp.core.domain.interfaces.repositories.admision.ApoderadoDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericAdmisionDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class ApoderadoDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Wed Oct 21 20:17:19 COT 2020
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class ApoderadoDaoImpl extends GenericAdmisionDAOImpl<String, Apoderado> implements ApoderadoDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.admision.dao.local.ApoderadoDaoLocal#
	 * listarApoderado(pe.com.builderp.core.service.admision.model.jpa.Apoderado)
	 */
	@Override
	public List<Apoderado> listar(BaseSearchVO filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	/**
	 * Generar query lista Apoderado.
	 *
	 * @param Apoderado  el apoderado
	 * @param esContador el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearchVO filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idApoderado) from Apoderado o where 1=1 ");
		} else {
			jpaql.append(" select o from Apoderado o ");
			jpaql.append(" where 1=1 ");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(
					" and (upper(o.codigo) like :search or  upper(o.nombres || ' ' || o.apellidoPaterno || ' ' || o.apellidoMaterno  ) like :search ) ");
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		} else {
			if (!StringUtil.isNullOrEmpty(filtro.getId())) {
				jpaql.append(" and o.idApoderado =:idApoderado ");
				parametros.put("idApoderado", filtro.getId());
			}
			if (!StringUtil.isNullOrEmpty(filtro.getNroDoc())) {
				jpaql.append(" and o.nroDoc =:nroDoc ");
				parametros.put("nroDoc", filtro.getNroDoc());
			}
		}
		if (!esContador) {
			jpaql.append(" ORDER BY o.apellidoPaterno,o.apellidoMaterno ,o.nombres ");
		}
		return createQuery(jpaql.toString(), parametros);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.admision.dao.local.ApoderadoDaoLocal#
	 * contarListar{entity.getClassName()}(pe.com.builderp.core.service.admision.
	 * model.jpa.Apoderado)
	 */
	@Override
	public int contar(BaseSearchVO filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.admision.dao.local.ApoderadoDaoLocal#
	 * generarIdApoderado()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

}