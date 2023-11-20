package pe.buildsoft.erp.core.domain.drools;

import jakarta.ejb.Local;

import org.kie.api.runtime.KieContainer;

@Local
public interface IReglaCacheUtil {
	KieContainer get(String key);

	String getDls(String key);

	Integer getDlsCantLinea(String key);

	boolean containsKeyDlsCantLinea(String key);

	boolean containsKeyDls(String key);
	
	void parsearRegla(String reglaPersonalizado, String nombreRegla);
}
