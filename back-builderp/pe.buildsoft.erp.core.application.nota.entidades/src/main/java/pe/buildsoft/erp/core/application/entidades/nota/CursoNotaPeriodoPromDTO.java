package pe.buildsoft.erp.core.application.entidades.nota;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.matricula.PeriodoDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class CursoNotaPeriodoPromDTO.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Jun 10 00:13:45 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Getter
@Setter
public class CursoNotaPeriodoPromDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id curso nota periodo. */
	private String idCursoNotaPeriodo;

	/** El det registro nota. */
	private DetRegistroNotaDTO detRegistroNota;

	/** El periodo. */
	private PeriodoDTO periodo;

	/** El nota. */
	private BigDecimal nota;

	/**
	 * Instancia un nuevo curso nota periodo promDTO.
	 */
	public CursoNotaPeriodoPromDTO() {
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
		result = prime * result + ((idCursoNotaPeriodo == null) ? 0 : idCursoNotaPeriodo.hashCode());
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
		CursoNotaPeriodoPromDTO other = (CursoNotaPeriodoPromDTO) obj;
		if (idCursoNotaPeriodo == null) {
			if (other.idCursoNotaPeriodo != null) {
				return false;
			}
		} else if (!idCursoNotaPeriodo.equals(other.idCursoNotaPeriodo)) {
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
		return "CursoNotaPeriodoPromDTO [idCursoNotaPeriodo=" + idCursoNotaPeriodo + "]";
	}

}