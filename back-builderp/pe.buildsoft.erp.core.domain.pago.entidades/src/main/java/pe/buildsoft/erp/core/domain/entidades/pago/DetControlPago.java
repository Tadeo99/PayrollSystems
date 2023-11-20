package pe.buildsoft.erp.core.domain.entidades.pago;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

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
 * La Class DetControlPago.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:56 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "DetControlPago", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_PAGO)
@Getter
@Setter
public class DetControlPago extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id det control pago. */
	@Id
	@Column(name = "idDetControlPago", length = 32)
	private String idDetControlPago;

	/** El control pago. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idControlPago", referencedColumnName = "idControlPago")
	private ControlPago controlPago;

	/** El cuota concepto. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idCuotaConcepto", referencedColumnName = "idCuotaConcepto")
	private CuotaConcepto cuotaConcepto;

	/** El nro cuota. */
	@Column(name = "nroCuota", length = 5)
	private String nroCuota;

	/** El descripcion concepto. */
	@Column(name = "descripcionConcepto", length = 150)
	private String descripcionConcepto;

	/** El monto. */
	@Column(name = "monto", precision = 18, scale = 2)
	private BigDecimal monto;

	/** El mora. */
	@Column(name = "mora", precision = 18, scale = 2)
	private BigDecimal mora;

	/** El fecha reversion. */
	// @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaReversion")
	private OffsetDateTime fechaReversion;

	/** El fecha creacion. */
	// @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaCreacion")
	private OffsetDateTime fechaCreacion;

	/** El usuario creacion. */
	@Column(name = "usuarioCreacion", length = 50)
	private String usuarioCreacion;

	/** El fecha modificacion. */
	// @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaModificacion")
	private OffsetDateTime fechaModificacion;

	/** El usuario modificacion. */
	@Column(name = "usuarioModificacion", length = 50)
	private String usuarioModificacion;

	/** El estado. */
	@Column(name = "estado", length = 1)
	private String estado;

	/** El ip. */
	@Column(name = "ip", length = 150)
	private String ip;

	/** El estado. */
	@Column(name = "aplicaIgv")
	private Boolean aplicaIgv;

	@Transient
	private BigDecimal montoResta;

	/**
	 * Instancia un nuevo det control pago.
	 */
	public DetControlPago() {
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
		result = prime * result + ((idDetControlPago == null) ? 0 : idDetControlPago.hashCode());
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
		DetControlPago other = (DetControlPago) obj;
		if (idDetControlPago == null) {
			if (other.idDetControlPago != null) {
				return false;
			}
		} else if (!idDetControlPago.equals(other.idDetControlPago)) {
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
		return "DetControlPago [idDetControlPago=" + idDetControlPago + "]";
	}

}