package pe.buildsoft.erp.core.infra.data.repositories.escalafon;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.escalafon.DireccionPersonal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.escalafon.DireccionPersonalDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericEscalafonDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class DireccionPersonalDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Wed Jul 22 00:55:19 COT 2020
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class DireccionPersonalDaoImpl extends GenericEscalafonDAOImpl<String, DireccionPersonal>
		implements DireccionPersonalDaoLocal {
	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.rrhh.dao.local.DireccionPersonalDaoLocal#
	 * listarDireccionPersonal(pe.com.builderp.core.service.rrhh.model.jpa.
	 * DireccionPersonal)
	 */
	@Override
	public List<DireccionPersonal> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		return query.getResultList();
	}

	/**
	 * Generar query lista DireccionPersonal.
	 *
	 * @param DireccionPersonal el direccionPersonal
	 * @param esContador        el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idDireccionPersonal) from DireccionPersonal o where 1=1 ");
		} else {
			jpaql.append(" select o from DireccionPersonal o where 1=1 ");
		}

		if (filtro.getListaIdPersonal() != null && filtro.getListaIdPersonal().size() > 0) {
			jpaql.append(" and o.personal.idPersonal in (:listadoIdPersonal) ");
			parametros.put("listadoIdPersonal", filtro.getListaIdPersonal());
		}

		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.domiciliado) like :search ");
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		}
		if (!esContador) {
			// jpaql.append(" ORDER BY 1 ");
		}
		return createQuery(jpaql.toString(), parametros);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.rrhh.dao.local.DireccionPersonalDaoLocal#
	 * contarListar{entity.getClassName()}(pe.com.builderp.core.service.rrhh.model.
	 * jpa.DireccionPersonal)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.rrhh.dao.local.DireccionPersonalDaoLocal#
	 * generarIdDireccionPersonal()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

}