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
import jakarta.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.CabeceraReporteVO;

/**
 * La Class ControlPago.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:56 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "ControlPago", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_PAGO)
@Getter
@Setter
public class ControlPago extends CabeceraReporteVO implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id control pago. */
	@Id
	@Column(name = "idControlPago", length = 32)
	private String idControlPago;

	/** El anhio. 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idAnhio", referencedColumnName = "idAnhio")
	// @Column(name = "idAnhio" , precision = 18 , scale = 0)
	private Anhio anhio;*/
	@Column(name = "idAnhio" , precision = 18 , scale = 0)
	private Long idAnhio;

	/** El periodo. 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPeriodo", referencedColumnName = "idPeriodo")
	// @Column(name = "idPeriodo" , precision = 18 , scale = 0)
	private Periodo periodo;*/
	@Column(name = "idPeriodo", precision = 18, scale = 0)
	private Long idPeriodo;

	/** El alumno.
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idAlumno", referencedColumnName = "idAlumno")
	// @Column(name = "idAlumno" , length = 10)
	private Alumno alumno; */
	@Column(name = "idAlumno", length = 10)
	private String idAlumno;

	/** El empresa. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idEmpresa", referencedColumnName = "idEmpresa")
	private Empresa empresa;

	/** El tipo doc sunat.
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idTipoDocSunat", referencedColumnName = "idItem")
	private Item tipoDocSunat; */
	@Column(name = "idTipoDocSunat" , precision = 18 , scale = 0)
    private Long idTipoDocSunat;

	/** El item by tipo moneda. 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idTipoMoneda", referencedColumnName = "idItem")
	private Item itemByTipoMoneda;*/
	@Column(name = "idTipoMoneda" , precision = 18 , scale = 0)
    private Long idItemByTipoMoneda;

	/** El tipo cambio. */
	@Column(name = "tipoCambio", precision = 18, scale = 2)
	private BigDecimal tipoCambio;

	/** El nro doc. */
	@Column(name = "nroDoc", length = 50)
	private String nroDoc;

	/** El igv. */
	@Column(name = "igv", precision = 18, scale = 2)
	private BigDecimal igv;

	/** El sub monto total. */
	@Column(name = "subMontoTotal", precision = 18, scale = 2)
	private BigDecimal subMontoTotal;

	/** El monto total. */
	@Column(name = "montoTotal", precision = 18, scale = 2)
	private BigDecimal montoTotal;

	/** El fecha pago. */
	// @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaPago")
	private OffsetDateTime fechaPago;

	/** El fecha creacion. */
	// @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaCreacion")
	private OffsetDateTime fechaCreacion;

	/** El usuario creacion. */
	@Column(name = "usuarioCreacion", length = 50)
	private String usuarioCreacion;

	/** El fecha modificacion. */
	// @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechamodificacion")
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

	/** El origen. */
	@Column(name = "origen", length = 150)
	private String origen;

	/** El tipo trajeta. */
	@Column(name = "tipoTrajeta", length = 150)
	private String tipoTrajeta;

	/** El cuenta bancaria. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idCuentaBancariaEntidad", referencedColumnName = "idCuentaBancariaEntidad")
	private CuentaBancariaEntidad cuentaBancariaEntidad;

	/** El nro operacion. */
	@Column(name = "nroOperacion", length = 20)
	private String nroOperacion;

	/** El fecha operacion. */
	// @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaOperacion")
	private OffsetDateTime fechaOperacion;

	/** El serie. */
	@Column(name = "serie", length = 4)
	private String serie;

	/** El envio sunat. */
	@Column(name = "envioSunat", length = 1)
	private String envioSunat;

	/** El anulado. */
	@Column(name = "anulado", length = 1)
	private String anulado;

	/** El tipo descuento. */
	@Column(name = "tipoDescuento", length = 1)
	private String tipoDescuento;

	/** El descuento total. */
	@Column(name = "descuentoTotal", precision = 18, scale = 2)
	private BigDecimal descuentoTotal;

	/** El descuento total. */
	@Column(name = "descuento", precision = 18, scale = 2)
	private BigDecimal descuento;

	/** El observacion. */
	@Column(name = "observacion", length = 200)
	private String observacion;

	/** El estado. */
	@Column(name = "deposito")
	private Boolean deposito;

	/** El entidad. */
	// @ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "idEntidad", referencedColumnName = "idEntidad")
	@Column(name = "idEntidad", length = 32)
	private String idEntidad;

	/** El control pago det control pago list. */
	@OneToMany(mappedBy = "controlPago", fetch = FetchType.LAZY)
	private List<DetControlPago> controlPagoDetControlPagoList = new ArrayList<>();

	@Transient
	private String tipo = "";
	@Transient
	private String det = "";
	@Transient
	private String tri;

	@Transient
	private String ley = "";
	@Transient
	private String montoletra = "";

	@Transient
	private Integer varCantidad;
	
	@Transient
	private String idEntidadSelect;

	/**
	 * Instancia un nuevo control pago.
	 */
	public ControlPago() {
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
		result = prime * result + ((idControlPago == null) ? 0 : idControlPago.hashCode());
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
		ControlPago other = (ControlPago) obj;
		if (idControlPago == null) {
			if (other.idControlPago != null) {
				return false;
			}
		} else if (!idControlPago.equals(other.idControlPago)) {
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
		return "ControlPago [idControlPago=" + idControlPago + "]";
	}

}