package pe.buildsoft.erp.core.infra.data.repositories.planilla;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.planilla.ConceptoFijosTrabajador;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.ConceptoTrabajadorDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericPlanillaDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class ConceptoTrabajadorDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class ConceptoTrabajadorDaoImpl extends GenericPlanillaDAOImpl<String, ConceptoFijosTrabajador>
		implements ConceptoTrabajadorDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.rrhh.planilla.dao.local.
	 * ConceptoTrabajabadorDaoLocal#listarConceptoTrabajabador(pe.com.builderp.core.
	 * service.rrhh.planilla.model.jpa.ConceptoTrabajabador)
	 */
	@Override
	public List<ConceptoFijosTrabajador> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	/**
	 * Generar query lista ConceptoTrabajabador.
	 *
	 * @param ConceptoFijosTrabajador el conceptoTrabajabador
	 * @param esContador              el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idConceptoTrabajador) from ConceptoFijosTrabajador o where 1=1 ");
		} else {
			jpaql.append(" select o from ConceptoFijosTrabajador o ");
			jpaql.append(" left join fetch o.conceptoPdt ");
			jpaql.append(" where 1=1 ");
		}

		if (!StringUtil.isNullOrEmpty(filtro.getId())) {
			jpaql.append(" and o.idPersonal = :id ");
			parametros.put("id", filtro.getId());
		}
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(
					" and TRANSLATE(UPPER(o.conceptoPdt.descripcion || ' ' || o.descripcion ), :discriminaTildeMAC, :discriminaTildeMAT) like UPPER(translate(:search, :discriminaTildeMIC, :discriminaTildeMIT)) ");
			parametros.putAll(obtenerParametroDiscriminarTilde());
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getTipo())) {
			jpaql.append(" and o.conceptoPdt.tipo = :tipo ");
			parametros.put("tipo", filtro.getTipo());
		}
		if (!esContador) {
			jpaql.append(" ORDER BY " + filtro.getSortFields() + " " + filtro.getSortDirections());
		}
		return createQuery(jpaql.toString(), parametros);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.rrhh.planilla.dao.local.
	 * ConceptoTrabajabadorDaoLocal#contarListar{entity.getClassName()}(pe.com.
	 * builderp.core.service.rrhh.planilla.model.jpa.ConceptoTrabajabador)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.rrhh.planilla.dao.local.
	 * ConceptoTrabajabadorDaoLocal#generarIdConceptoTrabajabador()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

	@Override
	public ConceptoFijosTrabajador find(ConceptoFijosTrabajador filtro) {
		ConceptoFijosTrabajador resultado = new ConceptoFijosTrabajador();
		var parametros = new HashMap<String, Object>();
		var ejecutarQuery = false;
		var jpaql = new StringBuilder();
		jpaql.append(" from ConceptoTrabajador o  where 1=1 ");
		if (!StringUtil.isNullOrEmpty(filtro.getIdPersonal())) {
			ejecutarQuery = true;
			jpaql.append(" and o.personal.idPersonal=:idPersonal");
			parametros.put("idPersonal", filtro.getIdPersonal());
		}
		if (!StringUtil.isNullOrEmpty(filtro.getConceptoPdt().getIdConceptoPdt())) {
			ejecutarQuery = true;
			jpaql.append(" and o.conceptoPdt.idConceptoPdt=:idConceptoPdt");
			parametros.put("idConceptoPdt", filtro.getConceptoPdt().getIdConceptoPdt());
		}
		if (!StringUtil.isNullOrEmpty(filtro.getIdItemByPeriodoMes())) {
			ejecutarQuery = true;
			jpaql.append(" and o.idItemByPeriodoMes=:idItem");
			parametros.put("idItem", filtro.getIdItemByPeriodoMes());
		}
		if (ejecutarQuery) {
			var query = createQuery(jpaql.toString(), parametros);
			List<ConceptoFijosTrabajador> listaObj = query.getResultList();
			if (listaObj != null && !listaObj.isEmpty()) {
				resultado = listaObj.get(0);
			}
		}
		return resultado;
	}

	@Override
	public List<ConceptoFijosTrabajador> listarByTrabajador(String idTrabajador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		jpaql.append(
				" select o from ConceptoTrabajador o  left join fetch  o.conceptoPdt where 1=1 and o.idPersonal=:idTrabajador ");
		parametros.put("idTrabajador", idTrabajador);
		var query = createQuery(jpaql.toString(), parametros);
		return query.getResultList();
	}

}