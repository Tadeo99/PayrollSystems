package pe.buildsoft.erp.core.domain.entidades.pago;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
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
 * La Class DetPlanPagos.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:57 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "DetPlanPagos", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_PAGO)
@Getter
@Setter
public class DetPlanPagos extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id det plan pagos. */
	@Id
	@Column(name = "idDetPlanPagos", length = 32)
	private String idDetPlanPagos;

	/** El plan pagos. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPlanPagos", referencedColumnName = "idPlanPagos")
	private PlanPagos planPagos;

	/** El cuota concepto. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idCuotaConcepto", referencedColumnName = "idCuotaConcepto")
	private CuotaConcepto cuotaConcepto;

	/** El cuota. */
	@Column(name = "cuota", precision = 18, scale = 2)
	private BigDecimal cuota;

	/** El monto. */
	@Column(name = "subTotal", precision = 18, scale = 2)
	private BigDecimal subTotal;

	/** El monto. */
	@Column(name = "igv", precision = 18, scale = 2)
	private BigDecimal igv;

	/** El estado. */
	@Column(name = "aplicaIgv")
	private Boolean aplicaIgv;

	/** El monto resta. */
	@Column(name = "montoResta", precision = 18, scale = 2)
	private BigDecimal montoResta;

	/** El fecha vencimiento. */
	// @Temporal( TemporalType.TIMESTAMP)
	@Column(name = "fechaVencimiento")
	private OffsetDateTime fechaVencimiento;

	/** El flag fraccionado. */
	@Column(name = "flagFraccionado", length = 1)
	private String flagFraccionado;

	/** El flag fraccionado. */
	@Column(name = "nroCuota", length = 5)
	private String nroCuota;

	/** El estado. */
	@Column(name = "estado", length = 50)
	private String estado;

	@Transient
	private List<DetPlanPagos> detPlanPagosList;
	@Transient
	private String esLista;

	/**
	 * Instancia un nuevo det plan pagos.
	 */
	public DetPlanPagos() {
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
		result = prime * result + ((idDetPlanPagos == null) ? 0 : idDetPlanPagos.hashCode());
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
		DetPlanPagos other = (DetPlanPagos) obj;
		if (idDetPlanPagos == null) {
			if (other.idDetPlanPagos != null) {
				return false;
			}
		} else if (!idDetPlanPagos.equals(other.idDetPlanPagos)) {
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
		return "DetPlanPagos [idDetPlanPagos=" + idDetPlanPagos + "]";
	}

}