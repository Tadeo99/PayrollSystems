package pe.buildsoft.erp.core.domain.entidades.planilla;

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
 * La Class DetallePlanillaConcepto.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 23:10:02 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "DetallePlanillaConcepto", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_RRHH_PLANILLA)
@Getter
@Setter
public class DetallePlanillaConcepto extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id detalle planilla concepto. */
	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	// @Basic(optional = false)
	@Column(name = "idDetallePlanillaConcepto", length = 32)
	private String idDetallePlanillaConcepto;

	/** El detalle planilla. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idDetallePlanilla", referencedColumnName = "idDetallePlanilla")
	private DetallePlanilla detallePlanilla;

	/** El concepto trabajador. 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idConceptoPdt", referencedColumnName = "idConceptoPdt")
	private ConceptoPdt conceptoPdt;*/
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idConceptosTipoPlanilla", referencedColumnName = "idConceptosTipoPlanilla")
	private ConceptosTipoPlanilla concepto;

	/** El neto pagar. */
	@Column(name = "monto", precision = 18, scale = 2)
	private BigDecimal monto;

	/** El id detalle planilla concepto detalle planilla concepto list. */
//	@OneToMany(mappedBy = "idDetallePlanillaConcepto", fetch = FetchType.LAZY)
	@Transient
	private List<DetallePlanillaConcepto> detallePlanillaDetallePlanillaConceptoList = new ArrayList<>();

	/**
	 * Instancia un nuevo detalle planilla concepto.
	 */
	public DetallePlanillaConcepto() {
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
		result = prime * result + ((idDetallePlanillaConcepto == null) ? 0 : idDetallePlanillaConcepto.hashCode());
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
		DetallePlanillaConcepto other = (DetallePlanillaConcepto) obj;
		if (idDetallePlanillaConcepto == null) {
			if (other.idDetallePlanillaConcepto != null) {
				return false;
			}
		} else if (!idDetallePlanillaConcepto.equals(other.idDetallePlanillaConcepto)) {
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
		return "DetallePlanillaConcepto [idDetallePlanillaConcepto=" + idDetallePlanillaConcepto + "]";
	}

}