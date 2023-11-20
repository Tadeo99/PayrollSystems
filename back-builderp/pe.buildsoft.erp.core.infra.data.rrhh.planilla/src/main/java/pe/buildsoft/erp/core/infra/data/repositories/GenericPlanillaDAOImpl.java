package pe.buildsoft.erp.core.infra.data.repositories;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;

/**
 * La Class GenericIntegrationDAOImpl.
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
public class GenericPlanillaDAOImpl<K, T> extends GenericOperacionDAOImpl<K, T> {

	private Logger log = LoggerFactory.getLogger(GenericPlanillaDAOImpl.class);
	/** El objeto entity manager. */
	@PersistenceContext(unitName = ConfiguracionEntityManagerUtil.PU_PLANILLA)
	private EntityManager entityManager;

	public GenericPlanillaDAOImpl() {
		// Constructor
	}

	@Override
	public Connection getConexionDS() throws NamingException, SQLException {
		return getConexionDSPer("java:jboss/datasources/planillaDS");
	}

	/**
	 * saveNative.
	 *
	 * @param entity el entity
	 * @return the t
	 */
	public T saveNative(T entity) {
		return saveNative(entity, entityManager);
	}

	/**
	 * Save.
	 *
	 * @param entity el entity
	 * @return the t
	 */
	public T save(T entity) {
		return save(entity, entityManager);
	}

	/**
	 * Update.
	 *
	 * @param entity el entity
	 * @return the t
	 */
	public T update(T entity) {
		return update(entity, entityManager);
	}

	/**
	 * Delete.
	 *
	 * @param entity el entity
	 * @return the t
	 */
	public T delete(T entity) {
		return delete(entity, entityManager);
	}

	/**
	 * Find.
	 *
	 * @param classs el classs
	 * @param id     el id
	 * @return the t
	 */
	public T find(Class<T> classs, K id) {
		return find(classs, id, entityManager);
	}

	/**
	 * Creates the named query.
	 *
	 * @param arg0         el arg0
	 * @param parametraMap el parametra map
	 * @return the query
	 */
	public Query createNamedQuery(String arg0, Map<String, Object> parametraMap) {
		return createNamedQuery(arg0, parametraMap, entityManager);
	}

	/**
	 * Creates the query.
	 *
	 * @param arg0         el arg0
	 * @param parametraMap el parametra map
	 * @return the query
	 */
	public Query createQuery(String arg0, Map<String, Object> parametraMap) {
		return createQuery(arg0, parametraMap, entityManager);
	}

	/**
	 * Creates the native query.
	 *
	 * @param arg0         el arg0
	 * @param parametraMap el parametra map
	 * @return the query
	 */
	public Query createNativeQuery(String arg0, Map<String, Object> parametraMap) {
		return createNativeQuery(arg0, parametraMap, entityManager);
	}

	/**
	 * Creates the native query.
	 *
	 * @param arg0         the arg0
	 * @param parametraMap the parametra map
	 * @param classs       the classs
	 * @return the query
	 */
	public Query createNativeQuery(String arg0, Map<String, Object> parametraMap, Class<?> classs) {
		return createNativeQuery(arg0, parametraMap, classs, entityManager);
	}

	public Query createNativeQueryInsert(String arg0, Map<Integer, Object> parametraMap) {
		return createNativeQueryInsert(arg0, parametraMap, entityManager);
	}

	/**
	 * Creates the native query.
	 *
	 * @param arg0         el arg0
	 * @param arg1         el arg1
	 * @param parametraMap el parametra map
	 * @return the query
	 */
	public Query createNativeQuery(String arg0, Class<?> arg1, Map<String, Object> parametraMap) {
		return createNativeQuery(arg0, arg1, parametraMap, entityManager);
	}
}
