
package pe.buildsoft.erp.core.infra.data.repositories.matricula;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.matricula.CargaAcademica;
import pe.buildsoft.erp.core.domain.interfaces.repositories.matricula.CargaAcademicaDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericMatriculaDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ObjectUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class CargaAcademicaDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:55 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class CargaAcademicaDaoImpl extends GenericMatriculaDAOImpl<String, CargaAcademica>
		implements CargaAcademicaDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.matricula.dao.local.CargaAcademicaDaoLocal#
	 * listarCargaAcademica(pe.com.builderp.core.service.matricula.model.jpa.
	 * CargaAcademica)
	 */
	@Override
	public List<CargaAcademica> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	/**
	 * Generar query lista CargaAcademica.
	 *
	 * @param CargaAcademicaDTO el cargaAcademica
	 * @param esContador        el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idCargaAcademica) from CargaAcademica o where 1=1 ");
		} else {
			jpaql.append(
					" select o from CargaAcademica o left join fetch o.aula left join fetch o.itemByTurno  left join fetch o.anhio  ");
			jpaql.append(
					" left join fetch o.seccion secc left join fetch o.personalByTutor left join fetch o.personalByCoTutor  ");
			jpaql.append(
					" left join fetch o.personalByCoordinador left join fetch secc.grado g left join fetch g.itemByNivel  where 1=1 ");
		}

		if (!StringUtil.isNullOrEmpty(filtro.getIdEntidadSelect())) {
			jpaql.append(" and o.idEntidad =:idEntidadSelect");
			parametros.put("idEntidadSelect", filtro.getIdEntidadSelect());
		}

		if (!StringUtil.isNullOrEmpty(filtro.getId())) {
			jpaql.append(" and o.anhio.idAnhio = :idAnhio ");
			parametros.put("idAnhio", ObjectUtil.objectToLong(filtro.getId()));
		}
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(
					" and upper(o.nombre) like :search or upper(o.seccion.nombre ) like :search or upper(o.aula.descripcion ) like :search");
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		}
		if (!esContador) {
			jpaql.append(" ORDER BY o.seccion.grado.itemByNivel.idItem,o.seccion.grado.codigo,o.seccion.codigo ");
		}
		return createQuery(jpaql.toString(), parametros);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.matricula.dao.local.CargaAcademicaDaoLocal#
	 * contarListar{entity.getClassName()}(pe.com.builderp.core.service.matricula.
	 * model.jpa.CargaAcademicaDTO)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.matricula.dao.local.CargaAcademicaDaoLocal#
	 * generarIdCargaAcademica()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

}