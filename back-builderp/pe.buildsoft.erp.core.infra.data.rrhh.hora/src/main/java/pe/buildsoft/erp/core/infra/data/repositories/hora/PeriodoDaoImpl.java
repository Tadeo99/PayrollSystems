package pe.buildsoft.erp.core.infra.data.repositories.hora;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.hora.Periodo;
import pe.buildsoft.erp.core.domain.interfaces.repositories.hora.PeriodoDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericHoraDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class PeriodoDaoImpl.
 * <ul>
 * <li>Copyright 2021 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Thu Feb 24 15:07:25 COT 2022
 * @since BuildErp 1.0
 */
@Stateless
public class PeriodoDaoImpl extends GenericHoraDAOImpl<String, Periodo> implements PeriodoDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.mapfre.BuildErp.core.domain.interfaces.repositories.registrohoras.mantenedor
	 * .PeriodoDaoLocal#listarPeriodo(pe.mapfre.BuildErp.core.domain.entidades.
	 * registrohoras.mantenedor.Periodo)
	 */
	@Override
	public List<Periodo> listar(BaseSearch obj) {
		var query = generarQuery(obj, false);
		query.setFirstResult(obj.getStartRow());
		query.setMaxResults(obj.getOffSet());
		return query.getResultList();
	}

	@Override
	public List<Periodo> listarPeriodoMax(Periodo obj) {
		var query = generarQueryPeriodoMax(obj, false);
		return query.getResultList();
	}

	/**
	 * Generar query lista Periodo.
	 *
	 * @param PeriodoDTO el periodo
	 * @param esContador el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idPeriodo) from Periodo o where 1=1 ");
		} else {
			jpaql.append(" select o from Periodo o where 1=1 ");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.nombre) like :search ");
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		}
		if (!esContador) {
			jpaql.append(" ORDER BY o.fechaInicio desc ");
		}
		return createQuery(jpaql.toString(), parametros);
	}

	private Query generarQueryPeriodoMax(Periodo filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idPeriodo) from Periodo o where 1=1 ");
		} else {
			jpaql.append(" select o from Periodo o where 1=1 ");
		}

		if (!StringUtil.isNullOrEmpty(filtro.getIdPeriodo())) {
			jpaql.append(" and fechaInicio = (SELECT MAX (fechaInicio) FROM Periodo) ");
		}

		if (!esContador) {
			jpaql.append(" ORDER BY o.fechaInicio desc ");
		}
		return createQuery(jpaql.toString(), parametros);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.mapfre.BuildErp.core.domain.interfaces.repositories.registrohoras.mantenedor
	 * .PeriodoDaoLocal#contarListar{entity.getClassName()}(pe.mapfre.BuildErp.core.
	 * domain.entidades.registrohoras.mantenedor.PeriodoDTO)
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
	 * pe.mapfre.BuildErp.core.domain.interfaces.repositories.registrohoras.mantenedor
	 * .PeriodoDaoLocal#generarIdPeriodo()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

}