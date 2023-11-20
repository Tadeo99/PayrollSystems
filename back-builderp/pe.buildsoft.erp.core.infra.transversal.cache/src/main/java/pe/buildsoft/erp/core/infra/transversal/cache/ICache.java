package pe.buildsoft.erp.core.infra.transversal.cache;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Local;

@Local
public interface ICache {
	static final String ID_ENTIDAD_SELECT = "idEntidadSelect";
	static final String USER = "User";
	static final String USER_NOMBRES = "UserNombres";
	static final String KEY = "Key";
	static final String FECHA_ULTIMO_ACCESO = "FechaUltimoAcceso";

	void put(String key, Object value);

	void put(String key, String value);

	void remove(String key);

	Object get(String key);

	String getString(String key);

	String getUserName(String token);
	
	String getUserNombres(String token);
	
	String getEntidadSelect(String token);

	Object getKey();

	boolean containsKey(String key);
	List<?> completarDataMap(List<?> resultado,Map<String,Map<String,String>> completeMap);
	List<?> completarData(List<?> resultado,Map<String,String> completeMap,String keyCache);
	Object completarData(Object resultado,Map<String,String> completeMap,String keyCache);
}
