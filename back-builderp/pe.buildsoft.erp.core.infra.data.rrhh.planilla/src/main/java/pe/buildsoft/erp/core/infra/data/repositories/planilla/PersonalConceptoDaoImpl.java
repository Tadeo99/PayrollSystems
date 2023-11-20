package pe.buildsoft.erp.core.infra.data.repositories.planilla;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.planilla.PersonalConcepto;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.PersonalConceptoDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericPlanillaDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.CollectionUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class PersonalConceptoDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class PersonalConceptoDaoImpl extends GenericPlanillaDAOImpl<String, PersonalConcepto>
		implements PersonalConceptoDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.rrhh.planilla.dao.local.PersonalConceptoDaoLocal
	 * #listarPersonalConcepto(pe.com.builderp.core.service.rrhh.planilla.model.jpa.
	 * PersonalConcepto)
	 */
	@Override
	public List<PersonalConcepto> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		return query.getResultList();
	}

	/**
	 * Generar query lista PersonalConcepto.
	 *
	 * @param PersonalConcepto el personalConcepto
	 * @param esContador       el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idPersonalConcepto) from PersonalConcepto o where 1=1 ");
		} else {
			jpaql.append(" select o from PersonalConcepto o ");
			jpaql.append(" left join fetch o.tipoPlanilla ");
			jpaql.append(" left join fetch o.periodoPlanilla where 1=1 ");
		}

		if (!StringUtil.isNullOrEmpty(filtro.getIdTipoPlanilla())) {
			jpaql.append(" and o.tipoPlanilla.idTipoPlanilla=:idTipoPlanilla");
			parametros.put("idTipoPlanilla", filtro.getIdTipoPlanilla());
		}

		if (!StringUtil.isNullOrEmpty(filtro.getIdPeriodoPlanilla())) {
			jpaql.append(" and o.periodoPlanilla.idPeriodoPlanilla=:idPeriodoPlanilla");
			parametros.put("idPeriodoPlanilla", filtro.getIdPeriodoPlanilla());
		}
		if (!CollectionUtil.isEmpty(filtro.getListaIdPersonal())) {
			jpaql.append(" and o.idPersonal in (:listaIdPersonal) ");
			parametros.put("listaIdPersonal", filtro.getListaIdPersonal());
		}
		if (!esContador) {
			jpaql.append(" ORDER BY o.idPersonal asc ");
		}
		return createQuery(jpaql.toString(), parametros);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.rrhh.planilla.dao.local.PersonalConceptoDaoLocal
	 * #contarListar{entity.getClassName()}(pe.com.builderp.core.service.rrhh.
	 * planilla.model.jpa.PersonalConcepto)
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
	 * pe.com.builderp.core.service.rrhh.planilla.dao.local.PersonalConceptoDaoLocal
	 * #generarIdPersonalConcepto()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

}