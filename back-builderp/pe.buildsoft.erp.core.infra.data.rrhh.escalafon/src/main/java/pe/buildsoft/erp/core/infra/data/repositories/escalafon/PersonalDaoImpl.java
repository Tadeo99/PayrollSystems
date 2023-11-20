package pe.buildsoft.erp.core.infra.data.repositories.escalafon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.escalafon.Personal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.escalafon.PersonalDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericEscalafonDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.CollectionUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class PersonalDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Wed Jul 22 00:55:19 COT 2020
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class PersonalDaoImpl extends GenericEscalafonDAOImpl<String, Personal> implements PersonalDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.rrhh.dao.local.PersonalDaoLocal#listarPersonal(
	 * pe.com.builderp.core.service.rrhh.model.jpa.Personal)
	 */
	@Override
	public List<Personal> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false,false);
		if (filtro.getOffSet() > 0) {
			query.setFirstResult(filtro.getStartRow());
			query.setMaxResults(filtro.getOffSet());
		}
		return query.getResultList();
	}
	@Override
	public List<String> listarIds(BaseSearch filtro){
		var resultado = new ArrayList<String>();
		var query = generarQuery(filtro, false,true);
		if (filtro.getOffSet() > 0) {
			query.setFirstResult(filtro.getStartRow());
			query.setMaxResults(filtro.getOffSet());
		}
		List<Object> resulTemp = query.getResultList();
		for (Object obj : resulTemp) {
			resultado.add(obj.toString());
		}
		return resultado;
	}
	/**
	 * Generar query lista Personal.
	 *
	 * @param Personal   el personal
	 * @param esContador el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador, boolean isIds) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idPersonal) from Personal o ");
		} else {
			if (isIds) {
				jpaql.append(" select o.idPersonal from Personal o  ");
			} else {
				jpaql.append(" select o from Personal o  ");
			}
		}
		jpaql.append(" where 1=1 ");

		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(
					" and (TRANSLATE(UPPER(o.nombres || ' ' || o.apellidoPaterno || ' ' || o.apellidoMaterno ), :discriminaTildeMAC, :discriminaTildeMAT) like UPPER(translate(:search, :discriminaTildeMIC, :discriminaTildeMIT)) or (upper(o.codigoUnico) like :search) or (upper(o.nroDoc) like :search) )");
			parametros.putAll(obtenerParametroDiscriminarTilde());
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		}
		if (!StringUtil.isNullOrEmptyNumeric(filtro.getIdItemByCategoriaTrabajador())) {
			jpaql.append(" and o.idItemByCategoriaTrabajador= :idItemByCategoriaTrabajador");
			parametros.put("idItemByCategoriaTrabajador", filtro.getIdItemByCategoriaTrabajador());
		}
		if (!StringUtil.isNullOrEmptyNumeric(filtro.getIdItemByEps())) {
			jpaql.append(" and o.idItemByEps= :idItemByEps");
			parametros.put("idItemByEps", filtro.getIdItemByEps());
		}
		if (!CollectionUtil.isEmpty(filtro.getListaIdPersonal())) {
			jpaql.append(" and o.idPersonal in (:listaIdPersonal) ");
			parametros.put("listaIdPersonal", filtro.getListaIdPersonal());
		}
		if (!esContador && !isIds && !StringUtil.isNullOrEmpty(filtro.getSortFields())
				&& !StringUtil.isNullOrEmpty(filtro.getSortDirections())) {
			jpaql.append(" ORDER BY " + filtro.getSortFields() + " " + filtro.getSortDirections());
		}
		return createQuery(jpaql.toString(), parametros);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.rrhh.dao.local.PersonalDaoLocal#contarListar{
	 * entity.getClassName()}(pe.com.builderp.core.service.rrhh.model.jpa. Personal)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true,false);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.rrhh.dao.local.PersonalDaoLocal#
	 * generarIdPersonal()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

	@Override
	public List<Personal> listarPagina(Long idCategoriaTrabajador) {
		var parametros = new HashMap<String, Object>();
		var ejecutarQuery = false;
		var jpaql = new StringBuilder();
		jpaql.append(" select o from Personal o  ");
		jpaql.append(" left join fetch o.carrera");
		jpaql.append("  where 1 = 1 ");

		if (StringUtil.isNotNullOrBlank(idCategoriaTrabajador)) {
			ejecutarQuery = true;
			jpaql.append(" and o.idItemByCategoriaTrabajador = :idCategoriaTrabajador ");
			parametros.put("idCategoriaTrabajador", idCategoriaTrabajador);
		}
		if (ejecutarQuery) {
			var query = createQuery(jpaql.toString(), parametros);
			return query.getResultList();
		}
		return null;
	}

	@Override
	public Personal findPersonal(Personal filtro) {
		var resultado = new Personal();
		var parametros = new HashMap<String, Object>();
		var ejecutarQuery = false;
		var jpaql = new StringBuilder();
		jpaql.append(" from Personal o  where 1=1 ");
		if (!StringUtil.isNullOrEmpty(filtro.getNroDoc())) {
			ejecutarQuery = true;
			jpaql.append(" and o.nroDoc=:nroDoc");
			parametros.put("nroDoc", filtro.getNroDoc());
		}
		if (ejecutarQuery) {
			var query = createQuery(jpaql.toString(), parametros);
			List<Personal> listaObj = query.getResultList();
			if (listaObj != null && !listaObj.isEmpty()) {
				resultado = listaObj.get(0);
			}
		}
		return resultado;
	}

	@Override
	public Personal find(String idPersonal) {
		var resultado = new Personal();
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		jpaql.append(" select o from Personal o  ");
		jpaql.append(" left join fetch o.carrera");
		jpaql.append(" where o.idPersonal =:id ");
		parametros.put("id", idPersonal);
		var query = createQuery(jpaql.toString(), parametros);
		List<Personal> listaObj = query.getResultList();
		if (listaObj != null && !listaObj.isEmpty()) {
			resultado = listaObj.get(0);
		}
		return resultado;
	}

}