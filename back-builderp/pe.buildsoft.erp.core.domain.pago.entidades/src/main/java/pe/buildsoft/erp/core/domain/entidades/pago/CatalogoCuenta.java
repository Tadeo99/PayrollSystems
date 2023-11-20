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
 * La Class CatalogoCuenta.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:56 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "CatalogoCuenta", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_PAGO)
@Getter
@Setter
public class CatalogoCuenta extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id catalogo cuenta. */
	@Id
	@Column(name = "idCatalogoCuenta", precision = 18, scale = 0)
	private Long idCatalogoCuenta;

	/** El cuenta. */
	@Column(name = "cuenta", length = 50)
	private String cuenta;

	/** El nro cuenta. */
	@Column(name = "nroCuenta", length = 50)
	private String nroCuenta;

	/** El clasificacion. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idClasificacion", referencedColumnName = "idClasificacion")
	private Clasificacion clasificacion;

	/** El estado. */
	@Column(name = "estado", length = 1)
	private String estado;

	/** El monto. */
	@Column(name = "monto", precision = 18, scale = 2)
	private BigDecimal monto;

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

	/** El catalogo cuenta cuota concepto list. */
	@OneToMany(mappedBy = "catalogoCuenta", fetch = FetchType.LAZY)
	private List<CuotaConcepto> catalogoCuentaCuotaConceptoList = new ArrayList<>();

	/**
	 * Instancia un nuevo catalogo cuenta.
	 */
	public CatalogoCuenta() {
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
		result = prime * result + ((idCatalogoCuenta == null) ? 0 : idCatalogoCuenta.hashCode());
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
		CatalogoCuenta other = (CatalogoCuenta) obj;
		if (idCatalogoCuenta == null) {
			if (other.idCatalogoCuenta != null) {
				return false;
			}
		} else if (!idCatalogoCuenta.equals(other.idCatalogoCuenta)) {
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
		return "CatalogoCuenta [idCatalogoCuenta=" + idCatalogoCuenta + "]";
	}

}