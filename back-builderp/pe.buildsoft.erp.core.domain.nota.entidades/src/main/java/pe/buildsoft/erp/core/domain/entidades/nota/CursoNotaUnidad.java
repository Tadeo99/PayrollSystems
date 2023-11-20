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
import pe.buildsoft.erp.core.domain.entidades.matricula.Unidad;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class CursoNotaUnidad.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Jun 10 00:13:45 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "CursoNotaUnidad", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_NOTA)
@Getter
@Setter
public class CursoNotaUnidad extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id curso nota. */
	@Id
	@Column(name = "idCursoNotaComp", length = 32)
	private String idCursoNota;

	/** El det registro nota. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idDetRegistroNota", referencedColumnName = "idDetRegistroNota")
	private DetRegistroNota detRegistroNota;

	/** El criterio evaluacion. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idCriterioEvaluacion", referencedColumnName = "idCriterioEvaluacion")
	// @Column(name = "idCriterioEvaluacion" , length = 32)
	private CriterioEvaluacion criterioEvaluacion;

	/** El unidad. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idUnidad", referencedColumnName = "idUnidad")
	// @Column(name = "idUnidad" , precision = 18 , scale = 0)
	private Unidad unidad;

	/** El nota. */
	@Column(name = "nota", precision = 18, scale = 2)
	private BigDecimal nota;

	/**
	 * Instancia un nuevo curso nota unidad.
	 */
	public CursoNotaUnidad() {
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
		result = prime * result + ((idCursoNota == null) ? 0 : idCursoNota.hashCode());
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
		CursoNotaUnidad other = (CursoNotaUnidad) obj;
		if (idCursoNota == null) {
			if (other.idCursoNota != null) {
				return false;
			}
		} else if (!idCursoNota.equals(other.idCursoNota)) {
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
		return "CursoNotaUnidad [idCursoNota=" + idCursoNota + "]";
	}

}