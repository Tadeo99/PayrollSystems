package pe.buildsoft.erp.core.infra.transversal.cache;

import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.infinispan.Cache;
import org.infinispan.commons.dataconversion.MediaType;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.manager.EmbeddedCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.BaseTransfer;
import pe.buildsoft.erp.core.infra.transversal.utilitario.SerializationUtil;

/**
 * <ul>
 * <li>Copyright 2017 ndavilal. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Class SessionUtil.
 *
 * @author ndavilal
 * @version 1.0 , 08/04/2015
 * @since SIAA-CORE 2.1
 */
//https://docs.jboss.org/author/display/ISPN53/Configuring%20cache%20programmatically.html
//https://infinispan.org/docs/stable/titles/encoding/encoding.html
@Startup
@Singleton
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@TransactionManagement(TransactionManagementType.BEAN)
public class CacheService extends BaseTransfer implements ICache {

	private Logger log = LoggerFactory.getLogger(CacheService.class);

	private Cache<String, String> appAuthenticatorCache;

	private static final String DEFAULT_NAME_CACHE = "default";
	private static final String JAVA_JBOSS_INFINISPAN_CONTAINER_SERVER = "java:jboss/infinispan/container/server/";

	public static EmbeddedCacheManager getDefaultCacheContainer() throws NamingException {
		var ctx = new InitialContext();
		return (EmbeddedCacheManager) ctx.lookup(JAVA_JBOSS_INFINISPAN_CONTAINER_SERVER);
	}

	private EmbeddedCacheManager configurarDefault(EmbeddedCacheManager embededCacheManager) {
		var dcc = embededCacheManager.getCacheConfiguration(DEFAULT_NAME_CACHE);
		if (dcc == null) {
			var cfg = new ConfigurationBuilder();
			// cfg.clustering().cacheMode(CacheMode.REPL_SYNC).l1().lifespan(60000L);
			// cfg.read(dcc).clustering().stateTransfer().fetchInMemoryState(true);
			cfg.encoding().key().mediaType(MediaType.APPLICATION_PROTOSTREAM_TYPE);
			cfg.encoding().value().mediaType(MediaType.APPLICATION_PROTOSTREAM_TYPE);
			if (embededCacheManager.getCacheConfiguration(DEFAULT_NAME_CACHE) == null) {
				embededCacheManager.defineConfiguration(DEFAULT_NAME_CACHE, cfg.build());
			}

		}
		return embededCacheManager;
	}

	public Cache<String, String> appAuthenticatorCache() throws NamingException {
		var embededCacheManager = configurarDefault(getDefaultCacheContainer());
		return embededCacheManager.getCache();

	}

	private void init() {
		if (appAuthenticatorCache == null) {
			/*
			 * try { DefaultCacheManager cacheManager = new DefaultCacheManager();
			 * 
			 * // Encode keys and values as Protobuf ConfigurationBuilder cfg = new
			 * ConfigurationBuilder();
			 * cfg.encoding().key().mediaType("application/x-protostream");
			 * cfg.encoding().value().mediaType("application/x-protostream");
			 * 
			 * cacheManager.defineConfiguration("mycache", cfg.build());
			 * 
			 * Cache<Integer, String> cache = cacheManager.getCache("mycache");
			 * 
			 * cache.put(1, "John");
			 * 
			 * // Use Protobuf for keys and JSON for values Cache<Integer, byte[]>
			 * jsonValuesCache = (Cache<Integer, byte[]>) cache.getAdvancedCache()
			 * .withMediaType("application/x-protostream", "application/json");
			 * 
			 * byte[] json = jsonValuesCache.get(1);
			 * 
			 * } catch (Exception e) { e.printStackTrace(); }
			 */
			try {
				appAuthenticatorCache = appAuthenticatorCache();
			} catch (Exception e) {
//				e.printStackTrace();
				log.error("init", e);
			}
		}
	}

	/**
	 * Pasar parametro.
	 *
	 * @param key   el resultado
	 * @param value el objeto
	 */
	@Override
	public void put(String key, Object value) {
		put(key, SerializationUtil.toString(value));

	}

	@Override
	public void put(String key, String value) {
		init();
		if (appAuthenticatorCache != null) {
			appAuthenticatorCache.put(key, value);
			// appAuthenticatorCache.getAdvancedCache().withMediaType("application/x-java-object",
			// "application/json");
		}
	}

	/**
	 * Remover parametro.
	 *
	 * @param key el resultado
	 */
	@Override
	public void remove(String key) {
		init();
		if (appAuthenticatorCache != null) {
			appAuthenticatorCache.remove(key);
		}
	}

	@Override
	public Object get(String key) {
		init();
		return SerializationUtil.fromString((String) appAuthenticatorCache.get(key));
	}

	@Override
	public String getString(String key) {
		init();
		if (appAuthenticatorCache != null) {
			return (String) appAuthenticatorCache.get(key);
		}
		return null;
	}

	@Override
	public String getUserName(String token) {
		return getString(token + USER);
	}

	@Override
	public String getUserNombres(String token) {
		return getString(token + USER_NOMBRES);
	}

	@Override
	public String getEntidadSelect(String token) {
		return getString(token + ID_ENTIDAD_SELECT);
	}

	@Override
	public Object getKey() {
		return get(KEY);
	}

	@Override
	public boolean containsKey(String key) {
		init();
		return appAuthenticatorCache.containsKey(key);
	}

	@Override
	public List<?> completarDataMap(List<?> resultado, Map<String, Map<String, String>> completeMap) {
		for (var objMap : completeMap.entrySet()) {
			completarData(resultado, objMap.getValue(), objMap.getKey());
		}
		return resultado;
	}

	@Override
	public List<?> completarData(List<?> resultado, Map<String, String> completeMap, String keyCache) {
		try {
			for (var obj : resultado) {
				completarDataPer(obj, completeMap, keyCache);
			}
		} catch (Exception e) {
			log.error("completarData", e);
		}

		return resultado;
	}

	private Object completarDataPer(Object resultado, Map<String, String> completeMap, String keyCache)
			throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		for (var entry : completeMap.entrySet()) {
			var id = getFieldValue(resultado, entry.getKey());
			if (id != null) {
				var item = get(keyCache + id);
				setField(resultado, item, entry.getValue());
			}
		}
		return resultado;
	}

	@Override
	public Object completarData(Object resultado, Map<String, String> completeMap, String keyCache) {
		try {
			return completarDataPer(resultado, completeMap, keyCache);
		} catch (Exception e) {
			log.error("completarData", e);
		}
		return resultado;
	}

}
