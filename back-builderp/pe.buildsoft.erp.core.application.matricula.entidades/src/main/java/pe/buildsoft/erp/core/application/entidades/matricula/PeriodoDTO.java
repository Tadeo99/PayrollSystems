package pe.buildsoft.erp.core.application.entidades.matricula;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class PeriodoDTO.
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
public class PeriodoDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id periodo. */
	private Long idPeriodo;

	/** El tipo. */
	private String tipo;

	/** El descripcion. */
	private String descripcion;

	/** El abreviatura. */
	private String abreviatura;

	/** El estado. */
	private String estado;

	/**
	 * Instancia un nuevo periodoDTO.
	 */
	public PeriodoDTO() {
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
		result = prime * result + ((idPeriodo == null) ? 0 : idPeriodo.hashCode());
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
		PeriodoDTO other = (PeriodoDTO) obj;
		if (idPeriodo == null) {
			if (other.idPeriodo != null) {
				return false;
			}
		} else if (!idPeriodo.equals(other.idPeriodo)) {
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
		return "PeriodoDTO [idPeriodo=" + idPeriodo + "]";
	}

}