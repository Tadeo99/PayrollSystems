package pe.buildsoft.erp.core.infra.transversal.utilitario.jwt;

import java.security.Key;

import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JWTokenUtility {

	private static Logger log = LoggerFactory.getLogger(JWTokenUtility.class);

	private JWTokenUtility() {
		throw new IllegalStateException("Utility JWTokenUtility class");
	}

	/*
	 * public static String buildJWT(String subject) { RsaJsonWebKey rsaJsonWebKey =
	 * RsaKeyProducer.produce(); Date now = new Date(); Date expiryDate = new
	 * Date(now.getTime() + 604800000);
	 * 
	 * return Jwts.builder() .setSubject(subject) .setIssuedAt(new Date())
	 * //.setExpiration(expiryDate) .signWith(SignatureAlgorithm.RS256,
	 * rsaJsonWebKey.getPrivateKey()) .compact(); }
	 */
	public static String buildJWT(String subject) {
		RsaJsonWebKey rsaJsonWebKey = RsaKeyProducer.produce();
		JwtClaims claims = new JwtClaims();
		claims.setSubject(subject); // the subject/principal is whom the token is about
		JsonWebSignature jws = new JsonWebSignature();
		jws.setPayload(claims.toJson());
		jws.setKey(rsaJsonWebKey.getPrivateKey());
		jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
		try {
			return jws.getCompactSerialization();
		} catch (JoseException ex) {
			log.error(ex.getMessage(), ex);
		}
		return null;
	}

	public static Key getKey() {
		RsaJsonWebKey rsaJsonWebKey = RsaKeyProducer.produce();
		return rsaJsonWebKey.getKey();
	}

	public static String validate(String jwt, Key key) throws InvalidJwtException {
		String subject = null;
		JwtConsumer jwtConsumer = new JwtConsumerBuilder().setRequireSubject() // the JWT must have a subject claim
				.setVerificationKey(key) // verify the signature with the public key
				.build(); // create the JwtConsumer instance

		try {
			// Validate the JWT and process it to the Claims
			JwtClaims jwtClaims = jwtConsumer.processToClaims(jwt);
			subject = (String) jwtClaims.getClaimValue("sub");
			log.info("JWT validation succeeded! " + jwtClaims);
		} catch (InvalidJwtException e) {
			log.error("log.info", e);
			throw e;
		}
		return subject;
	}
}
