package pe.buildsoft.erp.core.infra.data.repositories.hora;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.hora.RegistroHoraDet;
import pe.buildsoft.erp.core.domain.interfaces.repositories.hora.RegistroHoraDetDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericHoraDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
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
public class RegistroHoraDetDaoImpl extends GenericHoraDAOImpl<String, RegistroHoraDet>
		implements RegistroHoraDetDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.mapfre.BuildErp.core.domain.interfaces.repositories.registrohoras.mantenedor
	 * .RegistroCabeceraDaoLocal#listarRegistroCabecera(pe.mapfre.BuildErp.core.domain
	 * .entidades.registrohoras.mantenedor.RegistroCabecera)
	 */
	@Override
	public List<RegistroHoraDet> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
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
			jpaql.append("select count(o.idRegistroHoraDet) from RegistroHoraDet o where 1=1 ");
		} else {
			jpaql.append("select o from RegistroHoraDet o  left join fetch o.requerimiento ");
			jpaql.append(
					"left join fetch o.registroHora rh left join fetch rh.personal left join fetch rh.periodo where 1=1  ");
		}

		if (!StringUtil.isNullOrEmpty(filtro.getIdPersonal())) {
			jpaql.append(" and o.registroHora.personal.idPersonal = :idPersonal ");
			parametros.put("idPersonal", filtro.getIdPersonal());
		}
		if (!StringUtil.isNullOrEmpty(filtro.getIdPeriodo())) {
			jpaql.append(" and o.registroHora.periodo.idPeriodo = :idPeriodo ");
			parametros.put("idPeriodo", filtro.getIdPeriodo());
		}
		if (!StringUtil.isNullOrEmptyNumeric(filtro.getNumSemana())) {
			jpaql.append(" and o.numSemana =:numSemana ");
			parametros.put("numSemana", filtro.getNumSemana());
		}
		if (!esContador) {
			jpaql.append(" ORDER BY o.requerimiento.nombre ");
		}
		return createQuery(jpaql.toString(), parametros);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.mapfre.BuildErp.core.domain.interfaces.repositories.registrohoras.mantenedor
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
	 * @see
	 * pe.mapfre.BuildErp.core.domain.interfaces.repositories.registrohoras.mantenedor
	 * .RegistroCabeceraDaoLocal#generarIdRegistroCabecera()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

	@Override
	public boolean eliminar(String idRegistroHora, Long numSemana) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		jpaql.append(
				"delete from RegistroHoraDet o where o.registroHora.idRegistroHora =:idRegistroHora and o.numSemana=:numSemana ");
		parametros.put("idRegistroHora", idRegistroHora);
		parametros.put("numSemana", numSemana);
		createQuery(jpaql.toString(), parametros).executeUpdate();
		return true;
	}

}