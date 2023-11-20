package pe.buildsoft.erp.core.infra.data.repositories.escalafon;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.escalafon.Beneficiarios;
import pe.buildsoft.erp.core.domain.interfaces.repositories.escalafon.BeneficiariosDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericEscalafonDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class BeneficiariosDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Wed Jul 22 00:55:18 COT 2020
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class BeneficiariosDaoImpl extends GenericEscalafonDAOImpl<String, Beneficiarios>
		implements BeneficiariosDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.rrhh.dao.local.BeneficiariosDaoLocal#
	 * listarBeneficiarios(pe.com.builderp.core.service.rrhh.model.jpa.
	 * Beneficiarios)
	 */
	@Override
	public List<Beneficiarios> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	/**
	 * Generar query lista Beneficiarios.
	 *
	 * @param BeneficiariosDTO el beneficiarios
	 * @param esContador       el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idBeneficiario) from Beneficiarios o where 1=1 ");
		} else {
			jpaql.append(" select o from Beneficiarios o where 1=1 ");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getId())) {
			jpaql.append(" and o.personal.idPersonal =:idPersonal ");
			parametros.put("idPersonal", filtro.getId());
		}
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.nombre) like :search ");
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
	 * @see pe.com.builderp.core.service.rrhh.dao.local.BeneficiariosDaoLocal#
	 * contarListar{entity.getClassName()}(pe.com.builderp.core.service.rrhh.model.
	 * jpa.BeneficiariosDTO)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.rrhh.dao.local.BeneficiariosDaoLocal#
	 * generarIdBeneficiarios()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

}