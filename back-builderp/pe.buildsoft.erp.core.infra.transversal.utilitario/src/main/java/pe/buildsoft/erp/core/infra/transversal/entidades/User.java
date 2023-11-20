package pe.buildsoft.erp.core.infra.transversal.entidades;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String email;
	private String password;

	public User() {
		super();
	}

}
