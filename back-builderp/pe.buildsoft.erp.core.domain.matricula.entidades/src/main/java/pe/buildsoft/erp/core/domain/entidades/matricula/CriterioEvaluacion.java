package pe.buildsoft.erp.core.domain.entidades.matricula;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class CriterioEvaluacion.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Jun 10 00:13:45 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "CriterioEvaluacion", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_MATRICULA)
@Getter
@Setter
public class CriterioEvaluacion extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id criterio evaluacion. */
	@Id
	@Column(name = "idCriterioEvaluacion", length = 32)
	private String idCriterioEvaluacion;

	/** El det malla curricular. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idDetMallaCurricular", referencedColumnName = "idDetMallaCurricular")
	private DetMallaCurricular detMallaCurricular;

	/** El criterio evaluacion. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idCriterioEvaluacionPadre", referencedColumnName = "idCriterioEvaluacion")
	private CriterioEvaluacion criterioEvaluacionPadre;

	/** El codigo. */
	@Column(name = "codigo", length = 20)
	private String codigo;

	/** El nombre. */
	@Column(name = "nombre", length = 200)
	private String nombre;

	/** El abreviatura. */
	@Column(name = "abreviatura", length = 50)
	private String abreviatura;

	/** El peso. */
	@Column(name = "peso", precision = 18, scale = 2)
	private BigDecimal peso;

	/** El nro orden. */
	@Column(name = "nroOrden", precision = 18, scale = 0)
	private Long nroOrden;

	/** El estado. */
	@Column(name = "estado", length = 1)
	private String estado;

	/** El criterio evaluacion padre menu list. */
	@Transient
	private List<CriterioEvaluacion> criterioEvaluacionHijos = new ArrayList<>();

	/**
	 * Instancia un nuevo criterio evaluacion.
	 */
	public CriterioEvaluacion() {
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
		result = prime * result + ((idCriterioEvaluacion == null) ? 0 : idCriterioEvaluacion.hashCode());
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
		CriterioEvaluacion other = (CriterioEvaluacion) obj;
		if (idCriterioEvaluacion == null) {
			if (other.idCriterioEvaluacion != null) {
				return false;
			}
		} else if (!idCriterioEvaluacion.equals(other.idCriterioEvaluacion)) {
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
		return "CriterioEvaluacion [idCriterioEvaluacion=" + idCriterioEvaluacion + "]";
	}

}