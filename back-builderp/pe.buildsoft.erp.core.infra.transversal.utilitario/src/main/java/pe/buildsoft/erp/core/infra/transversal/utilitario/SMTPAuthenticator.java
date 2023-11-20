package pe.buildsoft.erp.core.infra.transversal.utilitario;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;

/**
 * La Class SMTPAuthenticator.
 * <ul>
 * <li>Copyright 2014 MAPFRE-
 * mapfre. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Fri Apr 25 18:56:23 COT 2014
 * @since Rep v1..0
 */
public class SMTPAuthenticator extends Authenticator {
	
	/** La usuario. */
	private String usuario;
	
	/** La password. */
	private String password;
	
	/**
	 * Instancia un nuevo sMTP authenticator.
	 *
	 * @param usuario el usuario
	 * @param password el password
	 */
	public SMTPAuthenticator(String usuario, String password) {
		this.usuario = usuario;
		this.password = password;
	}
	
	/* (non-Javadoc)
	 * @see jakarta.mail.Authenticator#getPasswordAuthentication()
	 */
	@Override
	public PasswordAuthentication getPasswordAuthentication() {
	    return new PasswordAuthentication(usuario, password);

	}
}
