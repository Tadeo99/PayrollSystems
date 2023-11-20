package pe.buildsoft.erp.core.application.entidades.matricula;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.admision.SedeDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class PabellonDTO.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Apr 22 00:26:54 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Getter
@Setter
public class PabellonDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id pabellon. */
	private Long idPabellon;

	/** El descripcion. */
	private String descripcion;

	/** El abreviatura. */
	private String abreviatura;

	/** El estado. */
	private String estado;

	/** El entidad. */
	private String idEntidad;

	/** El sede. */
	private String idSede;

	/**
	 * Instancia un nuevo pabellonDTO.
	 */
	public PabellonDTO() {
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
		result = prime * result + ((idPabellon == null) ? 0 : idPabellon.hashCode());
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
		PabellonDTO other = (PabellonDTO) obj;
		if (idPabellon == null) {
			if (other.idPabellon != null) {
				return false;
			}
		} else if (!idPabellon.equals(other.idPabellon)) {
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
		return "PabellonDTO [idPabellon=" + idPabellon + "]";
	}

}