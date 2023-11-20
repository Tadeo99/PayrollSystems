package pe.buildsoft.erp.core.application.interfaces;

import jakarta.ejb.Local;
@Local
public interface IServiceApp {

	String procesar(Object objFiltro) throws Exception ;
	
}
