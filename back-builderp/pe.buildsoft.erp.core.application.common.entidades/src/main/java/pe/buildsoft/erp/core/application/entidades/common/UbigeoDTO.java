package pe.buildsoft.erp.core.application.entidades.common;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * La Class UbigeoDTO.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Tue Apr 18 09:56:54 COT 2017
 * @since SIAA-CORE 2.1
 */
@Getter
@Setter
public class UbigeoDTO implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id ubigeo. */
	private String idUbigeo;

	/** El descripcion. */
	private String descripcion;

	/** El ubigeo by dependencia. */
	private UbigeoDTO ubigeoByDependencia;

	/** El tipo. */
	private String tipo;
	
	private String descripcionView;

	/**
	 * Instancia un nuevo ubigeoDTO.
	 */
	public UbigeoDTO() {
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
		result = prime * result + ((idUbigeo == null) ? 0 : idUbigeo.hashCode());
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
		UbigeoDTO other = (UbigeoDTO) obj;
		if (idUbigeo == null) {
			if (other.idUbigeo != null) {
				return false;
			}
		} else if (!idUbigeo.equals(other.idUbigeo)) {
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
		return "UbigeoDTO [idUbigeo=" + idUbigeo + "]";
	}

}