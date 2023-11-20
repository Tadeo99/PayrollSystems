package pe.buildsoft.erp.core.infra.data.repositories.escalafon;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.escalafon.PeriodoLaboraPersonal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.escalafon.PeriodoLaboraPersonalDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericEscalafonDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class PeriodoLaboraPersonalDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:12 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class PeriodoLaboraPersonalDaoImpl extends GenericEscalafonDAOImpl<String, PeriodoLaboraPersonal>
		implements PeriodoLaboraPersonalDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.rrhh.escalafon.dao.local.
	 * PeriodoLaboraPersonalDaoLocal#listarPeriodoLaboraPersonal(pe.com.builderp.
	 * core.service.rrhh.escalafon.model.jpa.PeriodoLaboraPersonal)
	 */
	@Override
	public List<PeriodoLaboraPersonal> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		if (filtro.getOffSet() > 0) {
			query.setFirstResult(filtro.getStartRow());
			query.setMaxResults(filtro.getOffSet());
		}
		return query.getResultList();
	}

	/**
	 * Generar query lista PeriodoLaboraPersonal.
	 *
	 * @param PeriodoLaboraPersonal el periodoLaboraPersonal
	 * @param esContador            el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idPeriodoLaboraPersonal) from PeriodoLaboraPersonal o where 1=1 ");
		} else {
			jpaql.append(" select o from PeriodoLaboraPersonal o   ");
			jpaql.append(" where 1=1");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getId())) {
			jpaql.append(" and o.personal.idPersonal=:idPersonal");
			parametros.put("idPersonal", filtro.getId());
		}
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(
					" and (TRANSLATE(UPPER(o.personal.nombres || ' ' || o.personal.apellidoPaterno || ' ' || o.personal.apellidoMaterno ), :discriminaTildeMAC, :discriminaTildeMAT) like UPPER(translate(:search, :discriminaTildeMIC, :discriminaTildeMIT)) or (upper(o.situacion) like :search) or (upper(o.personal.nroDoc) like :search) )");
			parametros.putAll(obtenerParametroDiscriminarTilde());
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		}
		if (!esContador && !StringUtil.isNotNullOrBlank(filtro.getSortFields())) {
			jpaql.append(" ORDER BY " + filtro.getSortFields() + " " + filtro.getSortDirections());
		}
		return createQuery(jpaql.toString(), parametros);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.rrhh.escalafon.dao.local.
	 * PeriodoLaboraPersonalDaoLocal#contarListar{entity.getClassName()}(pe.com.
	 * builderp.core.service.rrhh.escalafon.model.jpa.PeriodoLaboraPersonal)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.rrhh.escalafon.dao.local.
	 * PeriodoLaboraPersonalDaoLocal#generarIdPeriodoLaboraPersonal()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

}