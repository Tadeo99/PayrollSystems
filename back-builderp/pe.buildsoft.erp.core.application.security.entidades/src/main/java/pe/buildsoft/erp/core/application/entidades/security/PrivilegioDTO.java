package pe.buildsoft.erp.core.application.entidades.security;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class PrivilegioDTO.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Dec 14 00:27:43 COT 2017
 * @since SIAA-CORE 2.1
 */
@Getter
@Setter
public class PrivilegioDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id privilegio. */
	private Long idPrivilegio;

	/** El acronimo. */
	private String acronimo;

	/** El nombre. */
	private String nombre;

	/** El descripcion. */
	private String descripcion;

	/** El accion. */
	private String accion;

	/** El estado. */
	private String estado;

	/**
	 * Instancia un nuevo privilegioDTO.
	 */
	public PrivilegioDTO() {
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
		result = prime * result + ((idPrivilegio == null) ? 0 : idPrivilegio.hashCode());
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
		PrivilegioDTO other = (PrivilegioDTO) obj;
		if (idPrivilegio == null) {
			if (other.idPrivilegio != null) {
				return false;
			}
		} else if (!idPrivilegio.equals(other.idPrivilegio)) {
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
		return "PrivilegioDTO [idPrivilegio=" + idPrivilegio + "]";
	}

}