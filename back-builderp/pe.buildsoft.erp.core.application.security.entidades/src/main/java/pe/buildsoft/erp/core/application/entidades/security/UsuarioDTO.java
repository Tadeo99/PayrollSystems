package pe.buildsoft.erp.core.application.entidades.security;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.aas.vo.NavigationItemVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.SelectItemVO;

/**
 * La Class UsuarioDTO.
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
public class UsuarioDTO extends BaseEntidad implements Serializable {

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
	private TipoUsuarioDTO tipoUsuario;

	/** El codigo externo. */
	private String codigoExterno;

	/** El estado. */
	private String estado;

	// Datos adicionales
	private Map<String, Boolean> privilegiosMap = new HashMap<>();
	private List<NavigationItemVO> listaMenu = new ArrayList<>();

	/** El user password. */
	private String userPasswordEncriptado;

	private OffsetDateTime fechaUltimoAcceso;
	private String serviceKey;
	private String authToken;

	private String usuarioSession;

	private String idEntidadSelect;

	private List<Long> grupoUsuarios = new ArrayList<>();
	private List<String> entidades = new ArrayList<>();

	private String accion = "";

	private List<SelectItemVO> entidadSelect = new ArrayList<>();

	/**
	 * Instancia un nuevo usuarioDTO.
	 */
	public UsuarioDTO() {
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
		UsuarioDTO other = (UsuarioDTO) obj;
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