package pe.buildsoft.erp.core.domain.interfaces.webclient.rest.aas;

import java.io.IOException;

import jakarta.ejb.Local;

@Local
public interface AasServiceClient {

	String actualizarCache(String authToken) throws IOException;
	
	String validate(String jwt) throws IOException;

}
