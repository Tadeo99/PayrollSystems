package pe.buildsoft.erp.core.application.entidades.matricula;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class UnidadDTO.
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
public class UnidadDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id unidad. */
	private Long idUnidad;

	/** El periodo. */
	private PeriodoDTO periodo;

	/** El descripcion. */
	private String descripcion;

	/** El abreviatura. */
	private String abreviatura;

	/** El estado. */
	private String estado;


	/**
	 * Instancia un nuevo unidadDTO.
	 */
	public UnidadDTO() {
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
		result = prime * result + ((idUnidad == null) ? 0 : idUnidad.hashCode());
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
		UnidadDTO other = (UnidadDTO) obj;
		if (idUnidad == null) {
			if (other.idUnidad != null) {
				return false;
			}
		} else if (!idUnidad.equals(other.idUnidad)) {
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
		return "UnidadDTO [idUnidad=" + idUnidad + "]";
	}

}