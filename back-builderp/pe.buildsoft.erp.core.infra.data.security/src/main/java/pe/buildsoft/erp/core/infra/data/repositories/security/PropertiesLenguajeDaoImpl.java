package pe.buildsoft.erp.core.infra.data.repositories.security;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.security.Properties;
import pe.buildsoft.erp.core.domain.entidades.security.PropertiesLenguaje;
import pe.buildsoft.erp.core.domain.interfaces.repositories.security.PropertiesLenguajeDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericSecurityDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.CollectionUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class PropertiesLenguajeDaoImpl.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue Apr 18 19:53:36 COT 2017
 * @since SIAA-CORE 2.1
 */
@Stateless
public class PropertiesLenguajeDaoImpl extends GenericSecurityDAOImpl<String, PropertiesLenguaje>
		implements PropertiesLenguajeDaoLocal {

	private static final long LENGUAJE_SPANISH = 526L;

	@Override
	public int actualizarPropertiesLenguaje(Properties obj) {
		Integer resultado = 0;
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		jpaql.append("  select o from PropertiesLenguaje o left join fetch o.properties pro ");
		jpaql.append(" where pro.idProperties=:idProperties and  o.idItemByLenguaje=:idItemLenguaje ");
		parametros.put("idProperties", obj.getIdProperties());
		parametros.put("idItemLenguaje", LENGUAJE_SPANISH);
		var query = createQuery(jpaql.toString(), parametros);
		List<PropertiesLenguaje> resultadoTemp = query.getResultList();
		if (!CollectionUtil.isEmpty(resultadoTemp)) {
			PropertiesLenguaje propertiesLenguaje = resultadoTemp.get(0);
			propertiesLenguaje.setValue(obj.getValue());
			update(propertiesLenguaje);
			resultado = 1;
		} else {
			PropertiesLenguaje propertiesLenguaje = new PropertiesLenguaje();
			propertiesLenguaje.setIdPropertiesLenguaje(generarId());
			propertiesLenguaje.setValue(obj.getValue());
			propertiesLenguaje.setProperties(new Properties());
			propertiesLenguaje.getProperties().setIdProperties(obj.getIdProperties());
			propertiesLenguaje.setIdItemByLenguaje(LENGUAJE_SPANISH);
			save(propertiesLenguaje);
			resultado = 1;
		}
		return resultado;
	}

	@Override
	public List<PropertiesLenguaje> obtenerPropertiesLenguajeMap(List<Long> listaIdProperties) {
		/*Map<String, Map<String, String>> resultado = new HashMap<>();
		if (listaIdProperties == null || listaIdProperties.isEmpty()) {
			return resultado;
		}*/
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		// pro.name,it.codigoExterno,pro.value, o.value
		jpaql.append("select o from PropertiesLenguaje o  ");
		jpaql.append(" left join fetch o.properties pro  left join fetch o.itemByLenguaje it where 1 = 1 ");
		jpaql.append(obtenerParametroSqlListaIn("listaIdProperties", "pro.idProperties", listaIdProperties, true));
		parametros.putAll(obtenerParametroListaIn("listaIdProperties", listaIdProperties));

		var query = createQuery(jpaql.toString(), parametros);
		return query.getResultList();
		/*for (PropertiesLenguaje objData : resul) {
			String key = objData.getProperties().getName();
			String keyIdeoma = objData.getItemByLenguaje().getCodigoExterno();
			if (!resultado.containsKey(keyIdeoma)) {
				Map<String, String> ideomaMap = new HashMap<>();
				ideomaMap.put(key, objData.getProperties().getValue());
				ideomaMap.put(key, objData.getValue());
				resultado.put(keyIdeoma, ideomaMap);
			} else {
				Map<String, String> ideomaMap = resultado.get(key);
				ideomaMap.put(key, objData.getValue());
				resultado.put(keyIdeoma, ideomaMap);
			}
		}

		return resultado;*/
	}

	@Override
	public List<PropertiesLenguaje> obtenerPropertiesLenguajeAllMap() {
		var parametros = new HashMap<String, Object>();

		var jpaql = new StringBuilder();
		// o.properties.name,o.itemByLenguaje.codigoExterno,o.properties.value, o.value
		jpaql.append("select o from PropertiesLenguaje o  ");
		jpaql.append(" left join fetch o.properties  pro  where 1 = 1 ");
		var query = createQuery(jpaql.toString(), parametros);
		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.edu.siaa.core.ejb.dao.seguridad.local.PropertiesLenguajeDaoLocal#
	 * listarPropertiesLenguaje(pe.com.edu.siaa.core.model.jpa.seguridad.
	 * PropertiesLenguaje)
	 */
	@Override
	public List<PropertiesLenguaje> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	/**
	 * Generar query lista PropertiesLenguaje.
	 *
	 * @param PropertiesLenguaje el propertiesLenguaje
	 * @param esContador         el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idPropertiesLenguaje) from PropertiesLenguaje o where 1=1 ");
		} else {
			jpaql.append(" select o from PropertiesLenguaje o where 1=1 ");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.idPropertiesLenguaje) like :search ");
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		}
		if (!esContador) {
			jpaql.append(" ORDER BY 1 ");
		}
		return createQuery(jpaql.toString(), parametros);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.edu.siaa.core.ejb.dao.seguridad.local.PropertiesLenguajeDaoLocal#
	 * contarListar{entity.getClassName()}(pe.com.edu.siaa.core.model.jpa.seguridad.
	 * PropertiesLenguaje)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.edu.siaa.core.ejb.dao.seguridad.local.PropertiesLenguajeDaoLocal#
	 * generarIdPropertiesLenguaje()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

}