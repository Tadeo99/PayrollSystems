package pe.buildsoft.erp.core.domain.entidades.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class Usuario.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Wed Dec 13 22:04:28 COT 2017
 * @since SIAA-CORE 2.1
 */
@Entity
@Table(name = "Usuario", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_SECURIDAD)
@Getter
@Setter
public class Usuario extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id usuario. */
	@Id
	@Column(name = "idUsuario", length = 32)
	private String idUsuario;

	/** El nombre. */
	@Column(name = "nombre", length = 150)
	private String nombre;

	/** El apellido paterno. */
	@Column(name = "apellidoPaterno", length = 150)
	private String apellidoPaterno;

	/** El apellido materno. */
	@Column(name = "apellidoMaterno", length = 150)
	private String apellidoMaterno;

	/** El email. */
	@Column(name = "email", length = 150)
	private String email;

	/** El telefono. */
	@Column(name = "telefono", length = 150)
	private String telefono;

	/** El celular. */
	@Column(name = "celular", length = 150)
	private String celular;

	/** El user name. */
	@Column(name = "userName", length = 50)
	private String userName;

	/** El user password. */
	@Column(name = "userPassword", length = 100)
	private String userPassword;

	/** El tipo usuario. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idTipoUsuario", referencedColumnName = "idTipoUsuario")
	private TipoUsuario tipoUsuario;

	/** El codigo externo. */
	@Column(name = "codigoExterno", length = 32)
	private String codigoExterno;

	/** El estado. */
	@Column(name = "estado", length = 1)
	private String estado;

	@Transient
	private String userPasswordEncriptado;

	@Transient
	private String usuarioSession;

	@Transient
	private String idEntidadSelect;

	@Transient
	private List<Long> grupoUsuarios = new ArrayList<>();
	@Transient
	private List<String> entidades = new ArrayList<>();

	/**
	 * Instancia un nuevo usuario.
	 */
	public Usuario() {
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
		Usuario other = (Usuario) obj;
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
		return "Usuario [idUsuario=" + idUsuario + "]";
	}

}