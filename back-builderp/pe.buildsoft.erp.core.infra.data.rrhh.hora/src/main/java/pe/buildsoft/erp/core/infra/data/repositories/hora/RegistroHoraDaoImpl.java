package pe.buildsoft.erp.core.infra.data.repositories.hora;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.hora.RegistroHora;
import pe.buildsoft.erp.core.domain.interfaces.repositories.hora.RegistroHoraDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericHoraDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.CollectionUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class RegistroCabeceraDaoImpl.
 * <ul>
 * <li>Copyright 2021 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Thu Feb 24 15:16:44 COT 2022
 * @since BuildErp 1.0
 */
@Stateless
public class RegistroHoraDaoImpl extends GenericHoraDAOImpl<String, RegistroHora> implements RegistroHoraDaoLocal {
	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.mapfre.BuildErp.core.domain.interfaces.repositories.registrohoras.
	 * mantenedor
	 * .RegistroCabeceraDaoLocal#listarRegistroCabecera(pe.mapfre.BuildErp.core.
	 * domain .entidades.registrohoras.mantenedor.RegistroCabecera)
	 */
	@Override
	public List<RegistroHora> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();

	}

	/**
	 * Generar query lista RegistroCabecera.
	 *
	 * @param RegistroCabeceraDTO el registroCabecera
	 * @param esContador          el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append("select count(o.idRegistroHora) from RegistroHora o where 1=1 ");
		} else {
			jpaql.append(
					"select o from RegistroHora o left join fetch o.personal left join fetch o.periodo where 1=1 ");
		}

		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(
					" and upper(o.personal.nombres) like :search or upper(o.personal.apellidoPaterno) like :search or upper(o.personal.apellidoMaterno) like :search");
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getIdPersonal())) {
			jpaql.append(" and o.personal.idPersonal = :idPersonal ");
			parametros.put("idPersonal", filtro.getIdPersonal());
		}
		if (!StringUtil.isNullOrEmpty(filtro.getIdPeriodo())) {
			jpaql.append(" and o.periodo.idPeriodo = :idPeriodo ");
			parametros.put("idPeriodo", filtro.getIdPeriodo());
		}
		if (!CollectionUtil.isEmpty(filtro.getListaEstado())) {
			jpaql.append(" and o.estado IN (:listaEstado) ");
			parametros.put("listaEstado", filtro.getListaEstado());
		}

		if (!esContador) {
			jpaql.append(" ORDER BY " + filtro.getSortFields() + " " + filtro.getSortDirections());
		}
		return createQuery(jpaql.toString(), parametros);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.mapfre.BuildErp.core.domain.interfaces.repositories.registrohoras.
	 * mantenedor
	 * .RegistroCabeceraDaoLocal#contarListar{entity.getClassName()}(pe.mapfre.
	 * BuildErp.core.domain.entidades.registrohoras.mantenedor.RegistroCabeceraDTO)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.mapfre.BuildErp.core.domain.interfaces.repositories.registrohoras.
	 * mantenedor .RegistroCabeceraDaoLocal#generarIdRegistroCabecera()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

}