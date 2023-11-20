package pe.buildsoft.erp.core.infra.data.repositories.pago;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.pago.Empresa;
import pe.buildsoft.erp.core.domain.interfaces.repositories.pago.EmpresaDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericPagoDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * La Class EmpresaDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 21:45:31 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class EmpresaDaoImpl extends GenericPagoDAOImpl<Long, Empresa> implements EmpresaDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.pago.dao.local.EmpresaDaoLocal#listarEmpresa(pe.
	 * com.builderp.core.service.pago.model.jpa.Empresa)
	 */
	@Override
	public List<Empresa> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		if (filtro.getOffSet() > 0) {
			query.setFirstResult(filtro.getStartRow());
			query.setMaxResults(filtro.getOffSet());
		}
		return query.getResultList();
	}

	/**
	 * Generar query lista Empresa.
	 *
	 * @param Empresa    el empresa
	 * @param esContador el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idEmpresa) from Empresa o where 1=1 ");
		} else {
			jpaql.append(" select o from Empresa o where 1=1 ");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.razonSocial || o.ruc ) like :search ");
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
	 * pe.com.builderp.core.service.pago.dao.local.EmpresaDaoLocal#contarListar{
	 * entity.getClassName()}(pe.com.builderp.core.service.pago.model.jpa.Empresa)
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
	 * pe.com.builderp.core.service.pago.dao.local.EmpresaDaoLocal#generarIdEmpresa(
	 * )
	 */
	@Override
	public Long generarId() {
		var resultado = 1L;
		var query = createQuery("select max(o.idEmpresa) from Empresa o", null);
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
	public Empresa find(Empresa filtro) {
		Empresa resultado = new Empresa();
		var parametros = new HashMap<String, Object>();
		var ejecutarQuery = false;
		var jpaql = new StringBuilder();
		jpaql.append(" from Empresa o  where 1=1 ");
		if (!StringUtil.isNullOrEmpty(filtro.getRuc())) {
			ejecutarQuery = true;
			jpaql.append(" and o.ruc =:ruc");
			parametros.put("ruc", filtro.getRuc());
		}
		if (ejecutarQuery) {
			var query = createQuery(jpaql.toString(), parametros);
			List<Empresa> listaObj = query.getResultList();
			if (listaObj != null && !listaObj.isEmpty()) {
				resultado = listaObj.get(0);
			}
		}
		return resultado;
	}

}