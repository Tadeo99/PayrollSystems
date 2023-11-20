package pe.buildsoft.erp.core.infra.data.repositories.common;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.common.Item;
import pe.buildsoft.erp.core.domain.interfaces.repositories.common.ItemDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericCommonDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.state.EstadoGeneralState;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ObjectUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * La Class ItemDaoImpl.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue Apr 18 19:53:32 COT 2017
 * @since SIAA-CORE 2.1
 */
@Stateless
public class ItemDaoImpl extends GenericCommonDAOImpl<Long, Item> implements ItemDaoLocal {

	@Override
	public List<Item> listar() {
		var parametros = new HashMap<String, Object>();
		parametros.put("estadoActivo", EstadoGeneralState.ACTIVO.getKey());
		var query = createQuery(
				"select o from Item o left join fetch o.listaItems where o.estado=:estadoActivo order by o.listaItems.idListaItems,o.codigo, o.nombre",
				parametros);

		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.edu.siaa.core.ejb.dao.seguridad.local.ItemDaoLocal#listarItem(pe.com.
	 * edu.siaa.core.model.jpa.seguridad.Item)
	 */
	@Override
	public List<Item> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		if (filtro.getOffSet() > 0) {
			query.setFirstResult(filtro.getStartRow());
			query.setMaxResults(filtro.getOffSet());
		}
		return query.getResultList();
	}

	/**
	 * Generar query lista Item.
	 *
	 * @param Item       el item
	 * @param esContador el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idItem) from Item o where 1=1 ");
		} else {
			jpaql.append(" select o from Item o where 1=1 ");
		}

		if (!StringUtil.isNullOrEmpty(filtro.getId())) {
			jpaql.append(" and o.listaItems.idListaItems = :idListaItem ");
			parametros.put("idListaItem", ObjectUtil.objectToLong(filtro.getId()));
		}

		if (!StringUtil.isNullOrEmptyNumeric(filtro.getIdItem())) {
			jpaql.append(" and o.idItem = :idItem ");
			parametros.put("idItem", filtro.getIdItem());
		}

		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.nombre) like :search ");
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		}

		/*
		 * if (!StringUtil.isNullOrEmpty(filtro.getDescripcion())) {
		 * jpaql.append(" and upper(o.descripcion) like :descripcion ");
		 * parametros.put("descripcion", "%" + filtro.getDescripcion().toUpperCase() +
		 * "%"); } if (!StringUtil.isNullOrEmpty(filtro.getNombre())) {
		 * jpaql.append(" and upper(o.nombre) like :nombre "); parametros.put("nombre",
		 * "%" + filtro.getNombre().toUpperCase() + "%"); } if
		 * (!StringUtil.isNullOrEmptyNumeric(filtro.getCodigo())) {
		 * jpaql.append(" and o.codigo = :codigo "); parametros.put("codigo",
		 * filtro.getCodigo()); } if
		 * (!StringUtil.isNullOrEmpty(filtro.getCodigoExterno())) {
		 * jpaql.append(" and upper(o.codigoExterno) like :codigoExterno ");
		 * parametros.put("codigoExterno", "%" + filtro.getCodigoExterno().toUpperCase()
		 * + "%"); }
		 */
		if (!StringUtil.isNullOrEmpty(filtro.getEstado())) {
			jpaql.append(" and upper(o.estado) like :estado ");
			parametros.put("estado", "%" + filtro.getEstado().toUpperCase() + "%");
		}

		if (!StringUtil.isNullOrEmpty(filtro.getTipo())) {
			if ("LIBRO".equalsIgnoreCase(filtro.getTipo())) {
				jpaql.append(
						" and o.idItem in (select it.item.idItem from ConfigDependenciaItem it where it.estado =:estadoIT ) ");
				parametros.put("estadoIT", EstadoGeneralState.ACTIVO.getKey());
			} else if ("SUBLIBRO".equalsIgnoreCase(filtro.getTipo())) {
				jpaql.append(
						" and o.idItem in (select it.itemHijo.idItem from ConfigDependenciaItem it where it.estado =:estadoIT and it.item.idItem =:idItemPadreView ) ");
				parametros.put("idItemPadreView", ObjectUtil.objectToLong(filtro.getIdPadreView()));
				parametros.put("estadoIT", EstadoGeneralState.ACTIVO.getKey());
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
	 * pe.com.edu.siaa.core.ejb.dao.seguridad.local.ItemDaoLocal#contarListar{entity
	 * .getClassName()}(pe.com.edu.siaa.core.model.jpa.seguridad.Item)
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
	 * pe.com.edu.siaa.core.ejb.dao.seguridad.local.ItemDaoLocal#generarIdItem()
	 */
	@Override
	public Long generarId() {
		var resultado = 1L;
		var query = createQuery("select max(o.idItem) from Item o", null);
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