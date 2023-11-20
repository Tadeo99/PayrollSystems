package pe.buildsoft.erp.core.infra.data.repositories.escalafon;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.escalafon.Carrera;
import pe.buildsoft.erp.core.domain.interfaces.repositories.escalafon.CarreraDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericEscalafonDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * La Class CarreraDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Wed Jul 22 00:55:18 COT 2020
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class CarreraDaoImpl extends GenericEscalafonDAOImpl<Long, Carrera> implements CarreraDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.rrhh.dao.local.CarreraDaoLocal#listarCarrera(pe.
	 * com.builderp.core.service.rrhh.model.jpa.Carrera)
	 */
	@Override
	public List<Carrera> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	/**
	 * Generar query lista Carrera.
	 *
	 * @param Carrera    el carrera
	 * @param esContador el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idCarrera) from Carrera o where 1=1 ");
		} else {
			jpaql.append(" select o from Carrera o left join fetch o.institucion i where 1=1 ");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.nombre) like :search ");
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		} else {
			if (!StringUtil.isNullOrEmpty(filtro.getCodigo())) {
				jpaql.append(" and upper(o.codigo) like :codigo ");
				parametros.put("codigo", "%" + filtro.getCodigo().toUpperCase() + "%");
			}
		}
		if (!esContador && StringUtil.isNotNullOrBlank(filtro.getSortFields())) {
			jpaql.append(" ORDER BY " + filtro.getSortFields() + " " + filtro.getSortDirections());
		}
		return createQuery(jpaql.toString(), parametros);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.rrhh.dao.local.CarreraDaoLocal#contarListar{
	 * entity.getClassName()}(pe.com.builderp.core.service.rrhh.model.jpa.Carrera)
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
	 * pe.com.builderp.core.service.rrhh.dao.local.CarreraDaoLocal#generarIdCarrera(
	 * )
	 */
	@Override
	public Long generarId() {
		var resultado = 1L;
		var query = createQuery("select max(o.idCarrera) from Carrera o", null);
		var listLong = query.getResultList();
		if (listLong != null && listLong.size() > 0 && listLong.get(0) != null) {
			Long ultimoIdGenerado = Long.valueOf(listLong.get(0).toString());
			if (!StringUtil.isNullOrEmpty(ultimoIdGenerado)) {
				resultado = resultado + ultimoIdGenerado;
			}
		}
		return resultado;
	}

	@Override
	public Carrera findCarrera(Carrera filtro) {// TODO:REVISAR NATAN OPT
		var resultado = new Carrera();
		var parametros = new HashMap<String, Object>();
		var ejecutarQuery = false;
		var jpaql = new StringBuilder();
		jpaql.append(" from Carrera o  where 1=1 ");
		if (!StringUtil.isNullOrEmpty(filtro.getCodigo())) {
			ejecutarQuery = true;
			jpaql.append(" and upper(o.codigo)=upper(:codigo)");
			parametros.put("codigo", filtro.getCodigo());
		}
		if (!StringUtil.isNullOrEmpty(filtro.getNombre())) {
			ejecutarQuery = true;
			jpaql.append(" and upper(o.nombre)=upper(:nombre)");
			parametros.put("nombre", filtro.getNombre());
		}
		if (ejecutarQuery) {
			var query = createQuery(jpaql.toString(), parametros);
			List<Carrera> listaObj = query.getResultList();
			if (listaObj != null && listaObj.size() > 0) {
				resultado = listaObj.get(0);
			}
		}
		return resultado;
	}
}