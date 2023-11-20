package pe.buildsoft.erp.core.infra.transversal.cache;

import java.security.GeneralSecurityException;
import java.security.Key;
import java.time.OffsetDateTime;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

import org.jose4j.jwt.consumer.InvalidJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.inject.Inject;
import pe.buildsoft.erp.core.infra.transversal.utilitario.FechaUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.jwt.JWTokenUtility;

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
@Startup
@Singleton
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@TransactionManagement(TransactionManagementType.BEAN)
public class JWTService implements IJWT {

	private Logger log = LoggerFactory.getLogger(JWTService.class);

	@Inject
	private ICache cache;

	private boolean containsKey(String key) {
		return cache.containsKey(key);
	}

	@Override
	public boolean isServiceKeyValid(String serviceKey) {
		return containsKey(serviceKey);
	}

	@Override
	public boolean isAuthTokenValid(String serviceKey, String authToken) {
		if (isServiceKeyValid(serviceKey)) {
			Map<String, String> userMap = (Map<String, String>) cache.get(serviceKey);
			if (containsKey(authToken)) {
				String usernameMatch2 = cache.getUserName(authToken);
				if (userMap.containsKey(usernameMatch2)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public synchronized boolean isSessionActiva(String serviceKey, String authToken) {
		if (!containsKey(serviceKey)) {
			return false;
		}
		if (!containsKey(authToken)) {
			return false;
		}
		OffsetDateTime fechaUltimoAcceso = (OffsetDateTime) cache.get(authToken + ICache.FECHA_ULTIMO_ACCESO);
		if (fechaUltimoAcceso == null) {
			fechaUltimoAcceso = FechaUtil.obtenerFechaActual();
		}
		OffsetDateTime fechaActual = FechaUtil.obtenerFechaActual();
		if (FechaUtil.restaMinutos(fechaUltimoAcceso, fechaActual) > 30) {
			return false;
		}
		actualizarFechaUltimoAcceso(serviceKey, authToken);
		return true;

	}

	@Override
	public void logout(String serviceKey, String authToken) throws GeneralSecurityException {
		if (containsKey(serviceKey)) {
			Map<String, String> userMap = (Map<String, String>) cache.get(serviceKey);
			if (containsKey(authToken)) {
				String usernameMatch2 = cache.getUserName(authToken);
				if (userMap.containsKey(usernameMatch2)) {
					/**
					 * When a client logs out, the authentication token will be remove and will be
					 * made invalid.
					 */
					userMap.remove(usernameMatch2);
					cache.remove(usernameMatch2);
					cache.remove(authToken);
					return;
				}
			}
		}
		throw new GeneralSecurityException("Invalid service key and authorization token match.");
	}

	@Override
	public String validate(String jwt) throws InvalidJwtException {
		return JWTokenUtility.validate(jwt, (Key) cache.getKey());
	}

	private synchronized void actualizarFechaUltimoAcceso(String serviceKey, String authToken) {
		if (containsKey(serviceKey) && containsKey(authToken)) {
			cache.put(authToken + ICache.FECHA_ULTIMO_ACCESO, FechaUtil.obtenerFechaActual());
		}
	}

}
