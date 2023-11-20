package pe.buildsoft.erp.core.domain.entidades.planilla;

import java.io.Serializable;
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
 * La Class ConceptoPdt.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Sun May 02 01:34:10 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "ConceptoPdt", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_RRHH_PLANILLA)
@Getter
@Setter
public class ConceptoPdt extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id concepto pdt. */
	@Id
	@Column(name = "idConceptoPdt", length = 32)
	private String idConceptoPdt;

	/** El descripcion. */
	@Column(name = "descripcion", length = 200)
	private String descripcion;

	/** El det malla curricular padre. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idConceptoPdtPadre", referencedColumnName = "idConceptoPdt")
	private ConceptoPdt conceptoPdtPadre;

	/** El tipo. */
	@Column(name = "tipo", length = 1)
	private String tipo;

	/** El estado. */
	@Column(name = "estado", length = 1)
	private String estado;

	/** El estado. */
	@Column(name = "formula", length = 200)
	private String formula;

	/** El estado. */
	@Column(name = "codigo", length = 200)
	private String codigo;

	/** El estado. */
	@Column(name = "abreviatura", length = 200)
	private String abreviatura;

	/** El tipo. */
	@Column(name = "visible", length = 1)
	private String visible;

	/** El concepto pdt concepto trabajabador list. */
//	@OneToMany(mappedBy = "conceptoPdt", fetch = FetchType.LAZY)
	@Transient
	private List<ConceptoFijosTrabajador> conceptoPdtConceptoTrabajabadorList = new ArrayList<>();

	/** El concepto p d t adelanto list. */
//	@OneToMany(mappedBy = "conceptoPdt", fetch = FetchType.LAZY)
	@Transient
	private List<Adelanto> conceptoPDTAdelantoList = new ArrayList<>();

	/** El concepto pdt conceptos tipo planilla list. */
//	@OneToMany(mappedBy = "conceptoPdt", fetch = FetchType.LAZY)
	@Transient
	private List<ConceptosTipoPlanilla> conceptoPdtConceptosTipoPlanillaList = new ArrayList<>();

	/**
	 * Instancia un nuevo concepto pdt.
	 */
	public ConceptoPdt() {
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
		result = prime * result + ((idConceptoPdt == null) ? 0 : idConceptoPdt.hashCode());
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
		ConceptoPdt other = (ConceptoPdt) obj;
		if (idConceptoPdt == null) {
			if (other.idConceptoPdt != null) {
				return false;
			}
		} else if (!idConceptoPdt.equals(other.idConceptoPdt)) {
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
		return "ConceptoPdt [idConceptoPdt=" + idConceptoPdt + "]";
	}

}