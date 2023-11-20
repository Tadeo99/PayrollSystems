package pe.buildsoft.erp.core.infra.data.repositories.matricula;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.matricula.Pabellon;
import pe.buildsoft.erp.core.domain.interfaces.repositories.matricula.PabellonDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericMatriculaDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * La Class PabellonDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:54 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class PabellonDaoImpl extends GenericMatriculaDAOImpl<Long, Pabellon> implements PabellonDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.matricula.dao.local.PabellonDaoLocal#
	 * listarPabellon(pe.com.builderp.core.service.matricula.model.jpa.Pabellon)
	 */
	@Override
	public List<Pabellon> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	/**
	 * Generar query lista Pabellon.
	 *
	 * @param PabellonDTO el pabellon
	 * @param esContador  el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idPabellon) from Pabellon o where 1=1 ");
		} else {
			jpaql.append(" select o from Pabellon o where 1=1 ");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getIdEntidadSelect())) {
			jpaql.append(" and o.entidad =:idEntidadSelect");
			parametros.put("idEntidadSelect", filtro.getIdEntidadSelect());
		}
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and ((upper(o.descripcion) like :search) or (upper(o.abreviatura) like :search)) ");
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
	 * @see pe.com.builderp.core.service.matricula.dao.local.PabellonDaoLocal#
	 * contarListar{entity.getClassName()}(pe.com.builderp.core.service.matricula.
	 * model.jpa.PabellonDTO)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.matricula.dao.local.PabellonDaoLocal#
	 * generarIdPabellon()
	 */
	@Override
	public Long generarId() {
		var resultado = 1L;
		var query = createQuery("select max(o.idPabellon) from Pabellon o", null);
		var listLong = query.getResultList();
		if (listLong != null && !listLong.isEmpty() && listLong.get(0) != null) {
			Long ultimoIdGenerado = Long.valueOf(listLong.get(0).toString());
			if (!StringUtil.isNullOrEmpty(ultimoIdGenerado)) {
				resultado = resultado + ultimoIdGenerado;
			}
		}
		return resultado;
	}

	@Override
	public Pabellon find(Pabellon filtro) {
		Pabellon resultado = new Pabellon();
		var parametros = new HashMap<String, Object>();
		var ejecutarQuery = false;
		var jpaql = new StringBuilder();
		jpaql.append(" from Pabellon o  where 1=1 ");
		if (!StringUtil.isNullOrEmpty(filtro.getDescripcion())) {
			ejecutarQuery = true;
			jpaql.append(" and upper(o.descripcion)=upper(:descripcion)");
			parametros.put("descripcion", filtro.getDescripcion());
		}
		if (!StringUtil.isNullOrEmpty(filtro.getIdEntidad())) {
			ejecutarQuery = true;
			jpaql.append(" and o.idEntidad =:idEntidad");
			parametros.put("idEntidad", filtro.getIdEntidad());
		}
		if (ejecutarQuery) {
			var query = createQuery(jpaql.toString(), parametros);
			List<Pabellon> listaObj = query.getResultList();
			if (listaObj != null && !listaObj.isEmpty()) {
				resultado = listaObj.get(0);
			}
		}
		return resultado;
	}
}