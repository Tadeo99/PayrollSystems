package pe.buildsoft.erp.core.infra.data.entidades.webclient.rest.security.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class UsuarioVO.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Dec 14 00:27:45 COT 2017
 * @since SIAA-CORE 2.1
 */
@Getter
@Setter
public class UsuarioVO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id usuario. */
	private String idUsuario;

	/** El nombre. */
	private String nombre;

	/** El apellido paterno. */
	private String apellidoPaterno;

	/** El apellido materno. */
	private String apellidoMaterno;

	/** El email. */
	private String email;

	/** El telefono. */
	private String telefono;

	/** El celular. */
	private String celular;

	/** El user name. */
	private String userName;

	/** El user password. */
	private String userPassword;

	/** El tipo usuario. */
	private TipoUsuarioVO tipoUsuario;

	/** El codigo externo. */
	private String codigoExterno;

	/** El estado. */
	private String estado;

	/** El user password. */
	private String userPasswordEncriptado;

	private String usuarioSession;

	private String idEntidadSelect;

	private String authToken;

	private String accion = "";

	/**
	 * Instancia un nuevo usuarioDTO.
	 */
	public UsuarioVO() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idUsuario == null) ? 0 : idUsuario.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		UsuarioVO other = (UsuarioVO) obj;
		if (idUsuario == null) {
			if (other.idUsuario != null) {
				return false;
			}
		} else if (!idUsuario.equals(other.idUsuario)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UsuarioDTO [idUsuario=" + idUsuario + "]";
	}

}