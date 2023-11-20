package pe.buildsoft.erp.core.infra.data.repositories.matricula;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.matricula.Alumno;
import pe.buildsoft.erp.core.domain.interfaces.repositories.matricula.AlumnoDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericMatriculaDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ObjectUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class AlumnoDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:54 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class AlumnoDaoImpl extends GenericMatriculaDAOImpl<String, Alumno> implements AlumnoDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.matricula.dao.local.AlumnoDaoLocal#listarAlumno(
	 * pe.com.builderp.core.service.matricula.model.jpa.Alumno)
	 */
	@Override
	public List<Alumno> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	/**
	 * Generar query lista Alumno.
	 *
	 * @param Alumno     el alumno
	 * @param esContador el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idAlumno) from Alumno o where 1=1 ");
		} else {
			jpaql.append(" select o from Alumno o left join fetch o.grado gr left join fetch  gr.itemByNivel  ");
			jpaql.append(" left join fetch o.itemByDocIdentidad ");
			jpaql.append(" left join fetch o.lugarNacimiento ");
			jpaql.append(" lg left join fetch o.itemByNacionalidad  ");
			jpaql.append(" left join fetch o.sede  ");
			jpaql.append(" where 1=1 ");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getIdEntidadSelect())) {
			jpaql.append(" and o.entidad =:idEntidadSelect");
			parametros.put("idEntidadSelect", filtro.getIdEntidadSelect());
		}
		if (!StringUtil.isNullOrEmpty(filtro.getId())) {
			jpaql.append(" and o.estado =:estadoTemp");
			parametros.put("estadoTemp", filtro.getId());
		}
		if (!StringUtil.isNullOrEmpty(filtro.getIdPadreView())) {
			jpaql.append(" and o.grado.idGrado =:idgrado");
			parametros.put("idgrado", ObjectUtil.objectToLong(filtro.getIdPadreView()));
		}
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and (TRANSLATE(UPPER(o.nombres || ' ' || o.apellidoPaterno || ' ' || o.apellidoMaterno ) ");
			jpaql.append(" , :discriminaTildeMAC, :discriminaTildeMAT) ");
			jpaql.append(" like UPPER(translate(:search, :discriminaTildeMIC, :discriminaTildeMIT)) ");
			jpaql.append(" or (upper(o.codigo) like :search) or (upper(o.nroDoc) like :search) )");

			parametros.putAll(obtenerParametroDiscriminarTilde());
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		} else {
			if (!StringUtil.isNullOrEmpty(filtro.getCodigo())) {
				jpaql.append(" and upper(o.codigo) like :codigo ");
				parametros.put("codigo", "%" + filtro.getCodigo().toUpperCase() + "%");
			}
			if (!StringUtil.isNullOrEmpty(filtro.getEstado())) {
				jpaql.append(" and upper(o.estado) like :estado ");
				parametros.put("estado", "%" + filtro.getEstado().toUpperCase() + "%");
			}
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
	 * pe.com.builderp.core.service.matricula.dao.local.AlumnoDaoLocal#contarListar{
	 * entity.getClassName()}(pe.com.builderp.core.service.matricula.model.jpa.
	 * Alumno)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.matricula.dao.local.AlumnoDaoLocal#
	 * generarIdAlumno()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

}