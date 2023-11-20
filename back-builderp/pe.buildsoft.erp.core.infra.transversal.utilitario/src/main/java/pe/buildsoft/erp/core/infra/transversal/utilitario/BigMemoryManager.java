package pe.buildsoft.erp.core.infra.transversal.utilitario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BigMemoryManager<K, V> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> listaKey = new ArrayList<>();
	private List<String> listaKeyHelp = new ArrayList<>();
	private Map<String,Object>  dataConcurrentMap = new ConcurrentHashMap<String, Object>();
	private boolean isAllocateDirect = false;
	private Long grupo = 500L;
	private V klazz;
     
	public BigMemoryManager(Long grupo,boolean isAllocateDirect, String rutaRelativa) {
		super();
		this.grupo = grupo;
		this.isAllocateDirect = isAllocateDirect;
	}
	
	public BigMemoryManager(String rutaRelativaTemp, String rutaRelativa) {
		super();
	}
	public BigMemoryManager(String rutaRelativaTemp, String rutaRelativa,Long grupo) {
		super();
		this.grupo = grupo;
	}
	
	
	public BigMemoryManager(String rutaRelativaTemp, String rutaRelativa, V klazz) {
		super();
		this.klazz = klazz;
	}
	
	public BigMemoryManager() {
		super();
		isAllocateDirect = false;
	}
	public String generarElementUUID() {
		return java.util.UUID.randomUUID().toString().replaceAll("-", "");
	}
	public  void add(V value) {
		put(generarElementUUID(), value);
	}
	public  void put(V value) {
		put(generarElementUUID(), value);
	}
	public  void put(String key, V value) {
		listaKey.add(key);
		dataConcurrentMap.put(key, value);
	}
	public void update(String key, V value) {
		dataConcurrentMap.put(key, value);
	}
	

	public V get(String key) {
		return (V)dataConcurrentMap.get(key);
	}
	
	public  void remove(String key) {
		try {
			if (!isAllocateDirect) {
				if (listaKey.contains(key)) {
					listaKey.remove(key);
				}
			} 
		} catch (Exception e) {
			System.err.println("Error.remove( " + key + ") " + e.getMessage());
		}
		
	}
	public  void removeKey(String key) {
		try {
			if (!isAllocateDirect) {
				if (listaKey.contains(key)) {
					listaKey.remove(key);
				}
			}
		} catch (Exception e) {
			System.err.println("Error.removeKey( " + key + ") " + e.getMessage());
		}
	}
	public synchronized void flush() {
		
	}
	public synchronized void reiniciar() {
	}
	public  void clean() {
		flush();
		listaKey = new ArrayList<>();
		cleanClon();
		dataConcurrentMap.clear();
		dataConcurrentMap = null;
		
	}
	public  void clear() {
		clean();
	}
	public  void cleanClon() {
		listaKey = new ArrayList<>();
	}
	public void addAll(List<V> dataBigMemory) {
		for (var objValue : dataBigMemory) {
			put(objValue);
		}	  
	}
    public void addAll(BigMemoryManager<K, V> dataBigMemory) {
    	if (dataBigMemory != null && dataBigMemory.getListaKey() != null) {
    		if (dataBigMemory != null && dataBigMemory.getListaKey().size() > 0) {
    			listaKey.addAll(dataBigMemory.getListaKey());
    			if (!isAllocateDirect) {
    				dataBigMemory.flush();
    				this.dataConcurrentMap.putAll(dataBigMemory.getDataConcurrentMap());
    			}
    			dataBigMemory.cleanClon();
    		}
    	}
    }
	public List<String> getListaKey() {
		return listaKey;
	}

	public void setListaKey(List<String> listaKey) {
		this.listaKey = listaKey;
	}

	public List<String> getListaKeyHelp() {
		return listaKeyHelp;
	}

	public void setListaKeyHelp(List<String> listaKeyHelp) {
		this.listaKeyHelp = listaKeyHelp;
	}

	

	public boolean isAllocateDirect() {
		return isAllocateDirect;
	}

	public void setAllocateDirect(boolean isAllocateDirect) {
		this.isAllocateDirect = isAllocateDirect;
	}

	public Long getGrupo() {
		return grupo;
	}

	public void setGrupo(Long grupo) {
		this.grupo = grupo;
	}

	public V getKlazz() {
		return klazz;
	}

	public void setKlazz(V klazz) {
		this.klazz = klazz;
	}

	public Map<String, Object> getDataConcurrentMap() {
		return dataConcurrentMap;
	}

	public void setDataConcurrentMap(Map<String, Object> dataConcurrentMap) {
		this.dataConcurrentMap = dataConcurrentMap;
	}
	
	

	
}
