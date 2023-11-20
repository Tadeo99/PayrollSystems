package pe.buildsoft.erp.core.domain.entidades.planilla;

import java.io.Serializable;
import java.math.BigDecimal;

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
 * La Class ConceptosTipoPlanilla.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 23:10:02 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "ConceptosTipoPlanilla", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_RRHH_PLANILLA)
@Getter
@Setter
public class ConceptosTipoPlanilla extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id conceptos tipo planilla. */
	@Id
	@Column(name = "idConceptosTipoPlanilla", length = 32)
	private String idConceptosTipoPlanilla;

	/** El tipo planilla. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idTipoPlanilla", referencedColumnName = "idTipoPlanilla")
	private TipoPlanilla tipoPlanilla;

	/** El concepto pdt. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idConceptoPdt", referencedColumnName = "idConceptoPdt")
	private ConceptoPdt conceptoPdt;

	/** El descripcion. */
	@Column(name = "descripcion", length = 200)
	private String descripcion;

	/** El codigo. */
	@Column(name = "codigo", length = 200)
	private String codigo;

	/** El orden. */
	@Column(name = "orden", precision = 18, scale = 0)
	private Long orden;

	/** El descripcion. */
	@Column(name = "formula")
	private String formula;

	/** El descripcion. */
	@Column(name = "persistente", length = 1)
	private String persistente;

	/*** El descripcion. */
	@Column(name = "showBoleta", length = 1)
	private String showBoleta;

	/** El estado. */
	@Column(name = "estado", length = 1)
	private String estado;

	@Transient
	private boolean esEliminado = false;

	/**
	 * Instancia un nuevo conceptos tipo planilla.
	 */
	public ConceptosTipoPlanilla() {
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
		result = prime * result + ((idConceptosTipoPlanilla == null) ? 0 : idConceptosTipoPlanilla.hashCode());
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
		ConceptosTipoPlanilla other = (ConceptosTipoPlanilla) obj;
		if (idConceptosTipoPlanilla == null) {
			if (other.idConceptosTipoPlanilla != null) {
				return false;
			}
		} else if (!idConceptosTipoPlanilla.equals(other.idConceptosTipoPlanilla)) {
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
		return "ConceptosTipoPlanilla [idConceptosTipoPlanilla=" + idConceptosTipoPlanilla + "]";
	}

}