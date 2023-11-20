package pe.buildsoft.erp.core.application.entidades.nota;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.matricula.CriterioEvaluacionDTO;
import pe.buildsoft.erp.core.application.entidades.matricula.PeriodoDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class CursoNotaPeriodoDTO.
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
public class CursoNotaPeriodoDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id curso nota comp prom. */
	private String idCursoNotaCompProm;

	/** El det registro nota. */
	private DetRegistroNotaDTO detRegistroNota;

	/** El criterio evaluacion. */
	private CriterioEvaluacionDTO criterioEvaluacion;

	/** El periodo. */
	private PeriodoDTO periodo;

	/** El nota. */
	private BigDecimal nota;


	/**
	 * Instancia un nuevo curso nota periodoDTO.
	 */
	public CursoNotaPeriodoDTO() {
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
		result = prime * result + ((idCursoNotaCompProm == null) ? 0 : idCursoNotaCompProm.hashCode());
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
		CursoNotaPeriodoDTO other = (CursoNotaPeriodoDTO) obj;
		if (idCursoNotaCompProm == null) {
			if (other.idCursoNotaCompProm != null) {
				return false;
			}
		} else if (!idCursoNotaCompProm.equals(other.idCursoNotaCompProm)) {
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
		return "CursoNotaPeriodoDTO [idCursoNotaCompProm=" + idCursoNotaCompProm + "]";
	}

}