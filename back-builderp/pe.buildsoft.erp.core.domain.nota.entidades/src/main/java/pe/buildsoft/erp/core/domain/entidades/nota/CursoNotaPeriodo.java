package pe.buildsoft.erp.core.domain.entidades.nota;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.entidades.matricula.CriterioEvaluacion;
import pe.buildsoft.erp.core.domain.entidades.matricula.Periodo;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class CursoNotaPeriodo.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Jun 10 00:13:45 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "CursoNotaPeriodo", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_NOTA)
@Getter
@Setter
public class CursoNotaPeriodo extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id curso nota comp prom. */
	@Id
	@Column(name = "idCursoNotaCompProm", length = 32)
	private String idCursoNotaCompProm;

	/** El det registro nota. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idDetRegistroNota", referencedColumnName = "idDetRegistroNota")
	private DetRegistroNota detRegistroNota;

	/** El criterio evaluacion. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idCriterioEvaluacion", referencedColumnName = "idCriterioEvaluacion")
	// @Column(name = "idCriterioEvaluacion" , length = 32)
	private CriterioEvaluacion criterioEvaluacion;

	/** El periodo. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPeriodo", referencedColumnName = "idPeriodo")
	// @Column(name = "idPeriodo" , precision = 18 , scale = 0)
	private Periodo periodo;

	/** El nota. */
	@Column(name = "nota", precision = 18, scale = 2)
	private BigDecimal nota;

	/**
	 * Instancia un nuevo curso nota periodo.
	 */
	public CursoNotaPeriodo() {
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
		CursoNotaPeriodo other = (CursoNotaPeriodo) obj;
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
		return "CursoNotaPeriodo [idCursoNotaCompProm=" + idCursoNotaCompProm + "]";
	}

}