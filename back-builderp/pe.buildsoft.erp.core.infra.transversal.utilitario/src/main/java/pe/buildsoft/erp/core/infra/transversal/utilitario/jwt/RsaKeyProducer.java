package pe.buildsoft.erp.core.infra.transversal.utilitario.jwt;

import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * singleton. not using CDI bean on purpose (RsaJsonWebKey is not compatible.
 * this can be worked around though)
 * 
 * @author abhi
 */
public class RsaKeyProducer {

	private static Logger log = LoggerFactory.getLogger(RsaKeyProducer.class);

	private RsaKeyProducer() {
	}

	private static RsaJsonWebKey theOne;

	/**
	 * 
	 * not an ideal implementation since does not implement double-lock
	 * synchronization check
	 */
	public static RsaJsonWebKey produce() {
		if (theOne == null) {
			try {
				theOne = RsaJwkGenerator.generateJwk(2048);
			} catch (JoseException ex) {
				log.error("produce", ex);
			}
		}

		System.out.println("RSA Key setup... " + theOne.hashCode());
		return theOne;
	}
}
