package pe.buildsoft.erp.core.infra.data.repositories.nota;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.nota.CursoNotaPeriodoProm;
import pe.buildsoft.erp.core.domain.interfaces.repositories.nota.CursoNotaPeriodoPromDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericNotaDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * La Class CursoNotaPeriodoPromDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Jun 10 00:13:45 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class CursoNotaPeriodoPromDaoImpl extends GenericNotaDAOImpl<String, CursoNotaPeriodoProm>
		implements CursoNotaPeriodoPromDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.nota.dao.local.CursoNotaPeriodoPromDaoLocal#
	 * listarCursoNotaPeriodoProm(pe.com.builderp.core.service.nota.model.jpa.
	 * CursoNotaPeriodoProm)
	 */
	@Override
	public List<CursoNotaPeriodoProm> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		return query.getResultList();
	}

	/**
	 * Generar query lista CursoNotaPeriodoProm.
	 *
	 * @param CursoNotaPeriodoPromDTO el cursoNotaPeriodoProm
	 * @param esContador              el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idCursoNotaPeriodo) from CursoNotaPeriodoProm o where 1=1 ");
		} else {
			jpaql.append(" select o from CursoNotaPeriodoProm o where 1=1 ");
		}
		if (!StringUtil.isNullOrEmptyNumeric(filtro.getNota())) {
			jpaql.append(" and o.nota = :nota ");
			parametros.put("nota", filtro.getNota());
		}
		if (!esContador) {
			jpaql.append(" ORDER BY 1 ");
		}
		return createQuery(jpaql.toString(), parametros);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.nota.dao.local.CursoNotaPeriodoPromDaoLocal#
	 * contarListar{entity.getClassName()}(pe.com.builderp.core.service.nota.model.
	 * jpa.CursoNotaPeriodoPromDTO)
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
	 * pe.com.builderp.core.service.nota.dao.local.CursoNotaPeriodoPromDaoLocal#
	 * generarIdCursoNotaPeriodoProm()
	 */
	@Override
	public String generarId() {
		String resultado = "1";
		var query = createQuery("select max(o.idCursoNotaPeriodo) from CursoNotaPeriodoProm o", null);
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