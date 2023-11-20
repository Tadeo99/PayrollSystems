package pe.buildsoft.erp.core.infra.data.repositories.nota;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.nota.RegistroNota;
import pe.buildsoft.erp.core.domain.interfaces.repositories.nota.RegistroNotaDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericNotaDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class RegistroNotaDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Jun 10 00:13:46 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class RegistroNotaDaoImpl extends GenericNotaDAOImpl<String, RegistroNota> implements RegistroNotaDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.nota.dao.local.RegistroNotaDaoLocal#
	 * listarRegistroNota(pe.com.builderp.core.service.nota.model.jpa.RegistroNota)
	 */
	@Override
	public List<RegistroNota> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	/**
	 * Generar query lista RegistroNota.
	 *
	 * @param RegistroNota el registroNota
	 * @param esContador   el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idRegistroNota) from RegistroNota o where 1=1 ");
		} else {
			jpaql.append(" select o from RegistroNota o where 1=1 ");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.idRegistroNota) like :search ");
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
	 * pe.com.builderp.core.service.nota.dao.local.RegistroNotaDaoLocal#contarListar
	 * {entity.getClassName()}(pe.com.builderp.core.service.nota.model.jpa.
	 * RegistroNota)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.nota.dao.local.RegistroNotaDaoLocal#
	 * generarIdRegistroNota()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

	@Override
	public RegistroNota find(String idMatricula) {
		var parametros = new HashMap<String, Object>();
		var ejecutarQuery = false;
		var jpaql = new StringBuilder();
		jpaql.append(" from RegistroNota o where 1 = 1 ");
		if (StringUtil.isNotNullOrBlank(idMatricula)) {
			jpaql.append(" and o.matricula = :idMatricula ");
			parametros.put("idMatricula", idMatricula);
			ejecutarQuery = true;
		}
		if (ejecutarQuery) {
			var query = createQuery(jpaql.toString(), parametros);
			List<RegistroNota> resulTemp = query.getResultList();
			if (resulTemp != null && !resulTemp.isEmpty()) {
				return resulTemp.get(0);
			}
		}
		return null;
	}

}