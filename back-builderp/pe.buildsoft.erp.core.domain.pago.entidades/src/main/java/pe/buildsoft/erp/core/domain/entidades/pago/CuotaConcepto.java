package pe.buildsoft.erp.core.domain.entidades.pago;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class CuotaConcepto.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:56 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "CuotaConcepto", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_PAGO)
@Getter
@Setter
public class CuotaConcepto extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id cuota concepto. */
	@Id
	@Column(name = "idCuotaConcepto", length = 32)
	private String idCuotaConcepto;

	/** El anhio.
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idAnhio", referencedColumnName = "idAnhio")
	// @Column(name = "idAnhio" , precision = 18 , scale = 0)
	private Long anhio; */
	@Column(name = "idAnhio" , precision = 18 , scale = 0)
	private Long idAnhio;

	/** El item by nivel.
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idNivel", referencedColumnName = "idItem")
	private Item itemByNivel; */
	@Column(name = "idNivel" , precision = 18 , scale = 0)
    private Long idItemByNivel;


	/** El catalogo cuenta. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idCatalogoCuenta", referencedColumnName = "idCatalogoCuenta")
	private CatalogoCuenta catalogoCuenta;

	/** El nro min fraccionamiento. */
	@Column(name = "nroMinFraccionamiento", precision = 32, scale = 0)
	private Integer nroMinFraccionamiento;

	/** El nro max fraccionamiento. */
	@Column(name = "nroMaxFraccionamiento", precision = 32, scale = 0)
	private Integer nroMaxFraccionamiento;

	/** El monto. */
	@Column(name = "monto", precision = 18, scale = 2)
	private BigDecimal monto;

	/** El monto. */
	@Column(name = "subTotal", precision = 18, scale = 2)
	private BigDecimal subTotal;

	/** El monto. */
	@Column(name = "igv", precision = 18, scale = 2)
	private BigDecimal igv;

	/** El permanente. */
	@Column(name = "permanente", length = 1)
	private String permanente;

	/** El fecha tentativa. */
	// @Temporal( TemporalType.TIMESTAMP)
	@Column(name = "fechaTentativa")
	private OffsetDateTime fechaTentativa;

	/** El fecha creacion. */
	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaCreacion")
	private OffsetDateTime fechaCreacion;

	/** El usuario creacion. */
	@Column(name = "usuarioCreacion", length = 50)
	private String usuarioCreacion;

	/** El fecha modificacion. */
	// @Temporal( TemporalType.TIMESTAMP)
	@Column(name = "fechaModificacion")
	private OffsetDateTime fechaModificacion;

	/** El usuario modificacion. */
	@Column(name = "usuarioModificacion", length = 50)
	private String usuarioModificacion;

	/** El estado. */
	@Column(name = "aplicaIgv")
	private Boolean aplicaIgv;

	/** El cuota concepto det plan pagos list. */
	@OneToMany(mappedBy = "cuotaConcepto", fetch = FetchType.LAZY)
	private List<DetPlanPagos> cuotaConceptoDetPlanPagosList = new ArrayList<>();

	/** El cuota concepto det control pago list. */
	@OneToMany(mappedBy = "cuotaConcepto", fetch = FetchType.LAZY)
	private List<DetControlPago> cuotaConceptoDetControlPagoList = new ArrayList<>();

	/**
	 * Instancia un nuevo cuota concepto.
	 */
	public CuotaConcepto() {
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
		result = prime * result + ((idCuotaConcepto == null) ? 0 : idCuotaConcepto.hashCode());
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
		CuotaConcepto other = (CuotaConcepto) obj;
		if (idCuotaConcepto == null) {
			if (other.idCuotaConcepto != null) {
				return false;
			}
		} else if (!idCuotaConcepto.equals(other.idCuotaConcepto)) {
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
		return "CuotaConcepto [idCuotaConcepto=" + idCuotaConcepto + "]";
	}

}