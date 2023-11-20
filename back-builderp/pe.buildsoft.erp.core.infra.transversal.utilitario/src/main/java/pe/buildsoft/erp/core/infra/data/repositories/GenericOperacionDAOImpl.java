package pe.buildsoft.erp.core.infra.data.repositories;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.infra.transversal.cache.AtributosEntityCacheUtil;
import pe.buildsoft.erp.core.infra.transversal.cache.EntityMapperJPAQLUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.ModalVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.AtributoEntityVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.SelectItemVO;
import pe.buildsoft.erp.core.infra.transversal.utilitario.CollectionUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ConstanteQueryParseEntityUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.FechaUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ObjectUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * La Class GenericOperacionDAOImpl.
 * <ul>
 * <li>Copyright 2014 MAPFRE- mapfre. Todos los derechos reservados.</li>
 * </ul>
 * 
 * @param <K> el tipo de clave
 * @param <T> el tipo generico
 * @author BuildSoft.
 * @version 1.0, Fri Apr 25 17:57:10 COT 2014
 * @since Rep v1..0
 */
public class GenericOperacionDAOImpl<K, T> extends GenericJDBC<T> {

	private static final int BATCH_UPDATE = 2000;
	private static final String NOT_IN = " not in ";
	private static final String PROCESO_REGISTROS_ACTUALIZADOS = ".proceso.registros.actualizados ";
	private static final String PARAMETROS_MASIVO = "parametrosMasivo";
	private static final String SQL_INSERT = "sqlInsert";
	private Logger log = LoggerFactory.getLogger(GenericOperacionDAOImpl.class);

	protected String saveInsert(T entityClass) {
		return EntityMapperJPAQLUtil.generarInsertNative(entityClass.getClass());
	}

	public GenericOperacionDAOImpl() {
		// Constructor
	}

	public int getContador(Query query) {
		Object obj = query.getSingleResult();
		if (obj instanceof Long resultado)
			return resultado.intValue();
		else if (obj instanceof BigDecimal resultado)
			return resultado.intValue();
		else if (obj instanceof Integer resultado)
			return resultado.intValue();
		return ObjectUtil.objectToInteger(obj);
	}

	public Query getQueryPaginada(Query query, BaseSearch t) {
		if (t == null || t.getOffSet() <= 0) {
			query.setFirstResult(0);
			query.setMaxResults(10);
		} else {
			query.setFirstResult(t.getStartRow());
			query.setMaxResults(t.getOffSet());
		}
		return query;
	}

	@SuppressWarnings("unchecked")
	public List<ModalVO> getListaModalVO(Query query, BaseSearch t) {
		query = getQueryPaginada(query, t);
		List<Object[]> resultado = query.getResultList();
		List<ModalVO> lista = new ArrayList<>(resultado.size());
		for (var arr : resultado) {
			if (arr == null)
				continue;
			if (arr.length == 2)
				lista.add(new ModalVO(arr[0], ObjectUtil.objectToString(arr[1])));
			else if (arr.length == 3)
				lista.add(new ModalVO(arr[0], ObjectUtil.objectToString(arr[1]), ObjectUtil.objectToString(arr[2])));
		}
		return lista;
	}

	public String getK(K k) {
		return k.getClass().getName();
	}

	protected boolean insertMasivo(List<T> listaTemp) {
		var tableName = obtenerNombreEsquemaTabla(listaTemp.get(0).getClass());
		return insertMasivo(listaTemp, tableName);
	}

	@SuppressWarnings("unchecked")
	protected boolean insertMasivo(List<T> listaTemp, String tableName) {
		var respuesta = true;
		if (CollectionUtil.isEmpty(listaTemp)) {
			return respuesta;
		}
		try {
			var genericBatchJDBC = new GenericBatchJDBC();
			genericBatchJDBC.getConexionDS();
			var dataMap = toMap(listaTemp.get(0), true, true);
			var contador = 0;
			var resul = generarIntsertMasivo(dataMap, new HashMap<>(), contador, tableName);
			var sqlInsert = (StringBuilder) resul.get(SQL_INSERT);
			genericBatchJDBC.prepareStatement(sqlInsert, tableName);
			for (var entity : listaTemp) {
				dataMap = toMap(entity, true, true);
				contador = -1;
				resul = generarIntsertMasivo(dataMap, new HashMap<>(), contador, tableName);
				var parametrosMasivoTemp = (Map<String, Object>) resul.get(PARAMETROS_MASIVO);
				genericBatchJDBC.addBatch(parametrosMasivoTemp);
			}
			genericBatchJDBC.executeBatch();
		} catch (Exception e) {
			log.error("insertMasivo", e);
			respuesta = false;
		}
		return respuesta;
	}

	@SuppressWarnings("unchecked")
	protected boolean insertMasivoMap(List<Map<String, Object>> listaTemp, String tableName, String jndiConexion) {
		var respuesta = true;
		if (CollectionUtil.isEmpty(listaTemp)) {
			return respuesta;
		}
		var batch = BATCH_UPDATE;
		var cantidadRegitrosActualizados = 0;
		var contadorBatch = 1;
		try {
			var genericBatchJDBC = new GenericBatchJDBC();
			if (StringUtil.isNullOrEmpty(jndiConexion)) {
				genericBatchJDBC.getConexionDS();
			} else {
				genericBatchJDBC.getConexionDS(jndiConexion);
			}
			var dataMap = listaTemp.get(0);
			var contador = 0;
			var resul = generarIntsertMasivo(dataMap, new HashMap<>(), contador, tableName);
			var sqlInsert = (StringBuilder) resul.get(SQL_INSERT);
			genericBatchJDBC.prepareStatement(sqlInsert, tableName);
			for (var entity : listaTemp) {
				dataMap = entity;
				contador = -1;
				resul = generarIntsertMasivo(dataMap, new HashMap<>(), contador, tableName);
				var parametrosMasivoTemp = (Map<String, Object>) resul.get(PARAMETROS_MASIVO);
				genericBatchJDBC.addBatch(parametrosMasivoTemp);
				if (contadorBatch == BATCH_UPDATE) {
					// String e = "es"
				}
				if (contadorBatch == batch) {
					var actulizados = genericBatchJDBC.executeBatch();
					contadorBatch = 0;
					cantidadRegitrosActualizados = cantidadRegitrosActualizados + actulizados.length;
					log.error("insertMasivoMap." + tableName + PROCESO_REGISTROS_ACTUALIZADOS
							+ cantidadRegitrosActualizados + " " + FechaUtil.obtenerFechaActual());
					genericBatchJDBC = new GenericBatchJDBC();
					if (jndiConexion == null) {
						genericBatchJDBC.getConexionDS();
					} else {
						genericBatchJDBC.getConexionDS(jndiConexion);
					}
					genericBatchJDBC.prepareStatement(sqlInsert, tableName);
				}
				contadorBatch++;
			}
			var actulizados = genericBatchJDBC.executeBatch();
			cantidadRegitrosActualizados = cantidadRegitrosActualizados + actulizados.length;
			log.error("insertMasivoMap." + tableName + PROCESO_REGISTROS_ACTUALIZADOS + cantidadRegitrosActualizados
					+ " " + FechaUtil.obtenerFechaActual());
		} catch (Exception e) {
			log.error("insertMasivoMap.error." + tableName + PROCESO_REGISTROS_ACTUALIZADOS
					+ cantidadRegitrosActualizados + " " + FechaUtil.obtenerFechaActual());
			log.error("insertMasivoMap", e);
			respuesta = false;
		}
		return respuesta;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String obtenerNombreEsquemaTabla(Class calse) {
		AtributosEntityCacheUtil.getInstance();
		List<AtributoEntityVO> listaAtributos = AtributosEntityCacheUtil.obtenerListaAtributos(calse);
		var tableName = "";
		if (listaAtributos != null && !listaAtributos.isEmpty()) {
			tableName = listaAtributos.get(0).getTableName();
			if (listaAtributos.get(0).getSchema() != null) {
				tableName = listaAtributos.get(0).getSchema() + "." + tableName;
			}
		}
		return tableName;
	}

	public Map<String, Object> generarIntsertMasivo(Map<String, Object> dataMap,
			Map<String, String> atributoRequeridoMap, Integer contador, String tableName) {
		Map<String, Object> resultado = new HashMap<>();
		Map<String, Object> parametrosMasivo = new HashMap<>();
		var cantidadCampoValido = contadorCampoValido(dataMap);
		var contadorInsert = 0;
		var sqlInsert = new StringBuilder();
		var sqlHeader = new StringBuilder();
		var sqlValue = new StringBuilder();
		sqlInsert.append("Insert Into " + tableName + " (");
		sqlValue.append(" values(");
		var isSoloParametro = contador == -1;
		if (isSoloParametro) {
			contador = 0;
			log.error(atributoRequeridoMap == null ? "atributoRequeridoMap null" : "atributoRequeridoMap vacio");
		}

		var listaKeys = new ArrayList<SelectItemVO>();
		var listaKeysTemp = new ArrayList<>(dataMap.keySet());
		for (var string : listaKeysTemp) {
			var objHeader = new SelectItemVO();
			objHeader.setNombre(string);
			listaKeys.add(objHeader);
		}
		CollectionUtil.ordenador(false, listaKeys, "nombre");
		for (var entrySet : listaKeys) {
			var value = dataMap.get(entrySet.getNombre());
			if (!isSoloParametro) {
				sqlHeader.append(entrySet.getNombre());
				sqlValue.append("?");
			}
			contador++;
			contadorInsert++;
			parametrosMasivo.put(contador + "", value);
			if (!isSoloParametro && contadorInsert < cantidadCampoValido) {
				sqlHeader.append(",");
				sqlValue.append(",");
			}
		}
		sqlHeader.append(" )");
		sqlValue.append(" )");

		sqlInsert.append(sqlHeader.toString());
		sqlInsert.append(sqlValue.toString());
		resultado.put(SQL_INSERT, sqlInsert);
		resultado.put("contador", contador);
		resultado.put(PARAMETROS_MASIVO, parametrosMasivo);
		return resultado;
	}

	public Integer contadorCampoValido(Map<String, Object> dataMap) {
		return dataMap.size();
	}

	public Map<String, Object> obtenerParametroDiscriminarTilde() {
		Map<String, Object> parametraMap = new HashMap<>();
		parametraMap.put("discriminaTildeMAC", ConstanteQueryParseEntityUtil.DISCRIMINAR_TILDE_MAYUSCULA_CONVERT);
		parametraMap.put("discriminaTildeMAT", ConstanteQueryParseEntityUtil.DISCRIMINAR_TILDE_MAYUSCULA_TRASLATE);
		parametraMap.put("discriminaTildeMIC", ConstanteQueryParseEntityUtil.DISCRIMINAR_TILDE_MINUSCULA_CONVERT);
		parametraMap.put("discriminaTildeMIT", ConstanteQueryParseEntityUtil.DISCRIMINAR_TILDE_MINUSCULA_TRASLATE);
		return parametraMap;
	}

	public Map<String, Object> obtenerParametroListaIn(String nombreParametro, List<?> listaParametroTemp) {
		Map<String, Object> parametraMap = new HashMap<>();
		var indexDinamic = 0;
		if (listaParametroTemp != null) {
			var listaParametro = new ArrayList<>(listaParametroTemp);
			if (!listaParametro.isEmpty()) {
				if (listaParametro.size() > 1000) {
					while (listaParametro.size() > 1000) {
						var subList = new ArrayList<>(listaParametro.subList(0, 1000));
						parametraMap.put(nombreParametro + indexDinamic, subList);
						listaParametro.subList(0, 1000).clear();
						indexDinamic++;
					}
				}
				parametraMap.put(nombreParametro, listaParametro);
			}
		}
		return parametraMap;
	}

	/**
	 * Obtener parametro sql lista in.
	 *
	 * @param nombreParametro the nombre parametro
	 * @param campo           the campo
	 * @param listaKeysTemp   the lista parametro temp
	 * @param isIn            the is in
	 * @return the string builder
	 */
	public StringBuilder obtenerParametroSqlListaIn(String nombreParametro, String campo, List<?> listaKeysTemp,
			boolean isIn) {
		var jpaql = new StringBuilder();
		var indexDinamic = 0;
		var listaParametro = new ArrayList<>(listaKeysTemp);
		if (!CollectionUtil.isEmpty(listaParametro)) {
			jpaql.append(" and ( ");
			if (listaParametro.size() > 1000) {
				while (listaParametro.size() > 1000) {
					jpaql.append(" " + campo + " " + (isIn ? "in" : NOT_IN) + " (:" + nombreParametro + ""
							+ indexDinamic + ") OR ");
					listaParametro.subList(0, 1000).clear();
					indexDinamic++;
				}
			}
			jpaql.append("  " + campo + " " + (isIn ? "in" : NOT_IN) + "  (:" + nombreParametro + ") ) ");
		}
		return jpaql;
	}

	private List<Object> getSubList(List<Object> listaParametro) {
		if (listaParametro.size() > 1000)
			return new ArrayList<>(listaParametro.subList(0, 1000));
		else
			return new ArrayList<>(listaParametro.subList(0, listaParametro.size()));
	}

	private StringBuilder getCadena(List<Object> subList, StringBuilder cadena) {
		for (var object : subList) {
			if (!cadena.toString().isEmpty())
				cadena = cadena.append(", ").append(object.toString());
			else
				cadena = new StringBuilder(object.toString());
		}
		return cadena;
	}

	public StringBuilder obtenerParametroSqlListaInJDBC(String campo, List<Object> listaParametroTemp, boolean isIn) {
		var jpaql = new StringBuilder();
		var cadena = new StringBuilder();
		var listaParametro = new ArrayList<>(listaParametroTemp);
		if (!CollectionUtil.isEmpty(listaParametro)) {
			jpaql.append(" and ( ");
			while (!CollectionUtil.isEmpty(listaParametro)) {
				var subList = getSubList(listaParametro);
				cadena = getCadena(subList, cadena);
				jpaql.append(" " + campo + " " + (isIn ? "in" : NOT_IN) + " (" + cadena + ") ");
				cadena = new StringBuilder("");
				listaParametro.subList(0, subList.size()).clear();
				if (listaParametroTemp.size() > 1000 && !listaParametro.isEmpty()) {
					jpaql.append(" OR ");
				}
			}
			jpaql.append(" )");
		}
		return jpaql;
	}

	public Connection getConexionDS(String nombreJndi) throws NamingException, SQLException {
		var ctx = new InitialContext();
		var dataSource = (DataSource) ctx.lookup("java:jboss/datasources/" + nombreJndi);
		return dataSource.getConnection();

	}

	public Connection getConexionDS() throws SQLException, NamingException {
		var ctx = new InitialContext();
		var dataSource = (DataSource) ctx.lookup("java:jboss/datasources/commonDs");
		return dataSource.getConnection();

	}

	public Map<String, String> obtenerResultadoMap(List<Object[]> listaObjetos, int cantidadKey, int posicionValue) {
		Map<String, String> resultado = new HashMap<>();
		if (!CollectionUtil.isEmpty(listaObjetos)) {
			for (var objects : listaObjetos) {
				var key = StringUtil.generarKey(objects, cantidadKey);
				if (!resultado.containsKey(key)) {
					resultado.put(key, ObjectUtil.objectToString(objects[posicionValue]));
				}
			}
		}
		return resultado;
	}

	public Map<Long, String> obtenerResultadoLongMap(List<Object[]> listaObjetos, int cantidadKey, int posicionValue) {
		Map<Long, String> resultado = new HashMap<>();
		if (!CollectionUtil.isEmpty(listaObjetos)) {
			for (var objects : listaObjetos) {
				var key = ObjectUtil.objectToLong(StringUtil.generarKey(objects, cantidadKey));
				if (!resultado.containsKey(key))
					resultado.put(key, ObjectUtil.objectToString(objects[posicionValue]));
			}
		}
		return resultado;
	}

	public Connection getConexionDSPer(String jndi) throws NamingException, SQLException {
		var ctx = new InitialContext();
		var dataSource = (DataSource) ctx.lookup(jndi);
		return dataSource.getConnection();
	}

	/**
	 * saveNative.
	 *
	 * @param entity el entity
	 * @return the t
	 */
	public T saveNative(T entity,EntityManager entityManager) {
		try {
			var parametros = toEntityMap(entity);
			var sql = saveInsert(entity);
			log.info(" saveNative sql : " + sql);
			log.info(" saveNative parametros : " + parametros);
			var query = createNativeQuery(sql, parametros,entityManager);
			query.executeUpdate();
		} catch (Exception e) {
			log.error("saveNative", e);
		}
		return entity;
	}

	/**
	 * Save.
	 *
	 * @param entity el entity
	 * @return the t
	 */
	public T save(T entity,EntityManager entityManager) {
		entityManager.persist(entity);
		return entity;
	}

	/**
	 * Update.
	 *
	 * @param entity el entity
	 * @return the t
	 */
	public T update(T entity,EntityManager entityManager) {
		entity = entityManager.merge(entity);
		return entity;
	}

	/**
	 * Delete.
	 *
	 * @param entity el entity
	 * @return the t
	 */
	public T delete(T entity,EntityManager entityManager) {
		entityManager.remove(entity);
		return entity;
	}

	/**
	 * Find.
	 *
	 * @param classs el classs
	 * @param id     el id
	 * @return the t
	 */
	public T find(Class<T> classs, K id,EntityManager entityManager) {
		return entityManager.find(classs, id);
	}

	/**
	 * Creates the named query.
	 *
	 * @param arg0         el arg0
	 * @param parametraMap el parametra map
	 * @return the query
	 */
	public Query createNamedQuery(String arg0, Map<String, Object> parametraMap,EntityManager entityManager) {
		var query = entityManager.createNamedQuery(arg0);
		if (parametraMap != null) {
			for (var entry : parametraMap.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return query;
	}

	/**
	 * Creates the query.
	 *
	 * @param arg0         el arg0
	 * @param parametraMap el parametra map
	 * @return the query
	 */
	public Query createQuery(String arg0, Map<String, Object> parametraMap,EntityManager entityManager) {
		var query = entityManager.createQuery(arg0);
		if (parametraMap != null) {
			for (var entry : parametraMap.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return query;
	}

	/**
	 * Creates the native query.
	 *
	 * @param arg0         el arg0
	 * @param parametraMap el parametra map
	 * @return the query
	 */
	public Query createNativeQuery(String arg0, Map<String, Object> parametraMap,EntityManager entityManager) {
		var query = entityManager.createNativeQuery(arg0);
		if (parametraMap != null) {
			for (var entry : parametraMap.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return query;
	}

	/**
	 * Creates the native query.
	 *
	 * @param arg0         the arg0
	 * @param parametraMap the parametra map
	 * @param classs       the classs
	 * @return the query
	 */
	public Query createNativeQuery(String arg0, Map<String, Object> parametraMap, Class<?> classs,EntityManager entityManager) {
		var query = entityManager.createNativeQuery(arg0, classs);
		if (parametraMap != null) {
			for (var entry : parametraMap.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return query;
	}

	public Query createNativeQueryInsert(String arg0, Map<Integer, Object> parametraMap,EntityManager entityManager) {
		var query = entityManager.createNativeQuery(arg0);
		if (parametraMap != null) {
			for (var entry : parametraMap.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return query;
	}

	/**
	 * Creates the native query.
	 *
	 * @param arg0         el arg0
	 * @param arg1         el arg1
	 * @param parametraMap el parametra map
	 * @return the query
	 */
	public Query createNativeQuery(String arg0, Class<?> arg1, Map<String, Object> parametraMap,EntityManager entityManager) {
		var query = entityManager.createNativeQuery(arg0, arg1);
		if (parametraMap != null) {
			for (var entry : parametraMap.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return query;
	}
}
