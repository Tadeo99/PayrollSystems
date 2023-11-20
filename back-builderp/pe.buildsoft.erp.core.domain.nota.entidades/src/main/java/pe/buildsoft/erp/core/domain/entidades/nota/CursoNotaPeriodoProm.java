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
import pe.buildsoft.erp.core.domain.entidades.matricula.Periodo;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class CursoNotaPeriodoProm.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Jun 10 00:13:45 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "CursoNotaPeriodoProm", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_NOTA)
@Getter
@Setter
public class CursoNotaPeriodoProm extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id curso nota periodo. */
	@Id
	@Column(name = "idCursoNotaPeriodo", length = 32)
	private String idCursoNotaPeriodo;

	/** El det registro nota. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idDetRegistroNota", referencedColumnName = "idDetRegistroNota")
	private DetRegistroNota detRegistroNota;

	/** El periodo. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPeriodo", referencedColumnName = "idPeriodo")
	// @Column(name = "idPeriodo" , precision = 18 , scale = 0)
	private Periodo periodo;

	/** El nota. */
	@Column(name = "nota", precision = 18, scale = 2)
	private BigDecimal nota;

	/**
	 * Instancia un nuevo curso nota periodo prom.
	 */
	public CursoNotaPeriodoProm() {
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
		CursoNotaPeriodoProm other = (CursoNotaPeriodoProm) obj;
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
		return "CursoNotaPeriodoProm [idCursoNotaPeriodo=" + idCursoNotaPeriodo + "]";
	}

}