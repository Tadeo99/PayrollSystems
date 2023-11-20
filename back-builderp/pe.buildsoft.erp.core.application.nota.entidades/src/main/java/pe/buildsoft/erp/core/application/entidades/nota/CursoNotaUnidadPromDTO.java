package pe.buildsoft.erp.core.application.entidades.nota;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class CursoNotaUnidadPromDTO.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Jun 10 00:13:46 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Getter
@Setter
public class CursoNotaUnidadPromDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id curso nota unidad. */
	private String idCursoNotaUnidad;

	/** El det registro nota. */
	private DetRegistroNotaDTO detRegistroNota;

	/** El unidad. */
	private Long unidad;

	/** El nota. */
	private BigDecimal nota;

	/**
	 * Instancia un nuevo curso nota unidad promDTO.
	 */
	public CursoNotaUnidadPromDTO() {
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
		result = prime * result + ((idCursoNotaUnidad == null) ? 0 : idCursoNotaUnidad.hashCode());
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
		CursoNotaUnidadPromDTO other = (CursoNotaUnidadPromDTO) obj;
		if (idCursoNotaUnidad == null) {
			if (other.idCursoNotaUnidad != null) {
				return false;
			}
		} else if (!idCursoNotaUnidad.equals(other.idCursoNotaUnidad)) {
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
		return "CursoNotaUnidadPromDTO [idCursoNotaUnidad=" + idCursoNotaUnidad + "]";
	}

}