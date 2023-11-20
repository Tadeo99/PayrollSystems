package pe.buildsoft.erp.core.infra.transversal.cache;

import java.security.GeneralSecurityException;

import jakarta.ejb.Local;

import org.jose4j.jwt.consumer.InvalidJwtException;

@Local
public interface IJWT {	

	boolean isServiceKeyValid(String serviceKey);

	boolean isAuthTokenValid(String serviceKey, String authToken);

	boolean isSessionActiva(String serviceKey, String authToken);

	void logout(String serviceKey, String authToken) throws GeneralSecurityException;

	String validate(String jwt) throws InvalidJwtException;
}
