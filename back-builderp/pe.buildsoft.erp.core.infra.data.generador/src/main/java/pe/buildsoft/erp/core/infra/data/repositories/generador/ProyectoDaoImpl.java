package pe.buildsoft.erp.core.infra.data.repositories.generador;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.generador.Proyecto;
import pe.buildsoft.erp.core.domain.interfaces.repositories.generador.ProyectoDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericGeneradorDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class ProyectoDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Mon Sep 26 12:14:56 PET 2022
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class ProyectoDaoImpl extends GenericGeneradorDAOImpl<String, Proyecto> implements ProyectoDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.generador.dao.local.ProyectoDaoLocal#listar(pe.
	 * com.builderp.core.service.generador.model.jpa.Proyecto)
	 */
	@Override
	public List<Proyecto> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	/**
	 * Generar query lista Proyecto.
	 *
	 * @param Proyecto   el proyecto
	 * @param esContador el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idProyecto) from Proyecto o where 1=1 ");
		} else {
			jpaql.append(" select o from Proyecto o  ");
			jpaql.append(" where 1=1 ");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.nombre) like :search ");
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		}
		if (!esContador) {
			jpaql.append(" ORDER BY o." + filtro.getSortFields() + " " + filtro.getSortDirections());
		}
		return createQuery(jpaql.toString(), parametros);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.generador.dao.local.ProyectoDaoLocal#
	 * contarListar{entity.getClassName()}(pe.com.builderp.core.service.generador.
	 * model.jpa.ProyectoDTO)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.generador.dao.local.ProyectoDaoLocal#
	 * generarIdProyecto()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

}