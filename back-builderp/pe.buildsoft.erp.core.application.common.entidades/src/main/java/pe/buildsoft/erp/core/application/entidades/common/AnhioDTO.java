package pe.buildsoft.erp.core.application.entidades.common;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * La Class AnhioDTO.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Apr 22 00:26:55 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Getter
@Setter
public class AnhioDTO implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id anhio. */
	private Long idAnhio;

	/** El nombre. */
	private String nombre;

	/** El estado. */
	private String estado;
	
	private String descripcionView;

	/**
	 * Instancia un nuevo anhioDTO.
	 */
	public AnhioDTO() {
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
		result = prime * result + ((idAnhio == null) ? 0 : idAnhio.hashCode());
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
		AnhioDTO other = (AnhioDTO) obj;
		if (idAnhio == null) {
			if (other.idAnhio != null) {
				return false;
			}
		} else if (!idAnhio.equals(other.idAnhio)) {
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
		return "AnhioDTO [idAnhio=" + idAnhio + "]";
	}

}