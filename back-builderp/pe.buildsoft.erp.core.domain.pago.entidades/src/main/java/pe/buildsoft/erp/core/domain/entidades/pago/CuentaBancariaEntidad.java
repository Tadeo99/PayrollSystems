package pe.buildsoft.erp.core.domain.entidades.pago;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class CuentaBancariaEntidad.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:56 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "CuentaBancariaEntidad", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_PAGO)
@Getter
@Setter
public class CuentaBancariaEntidad extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id cuenta bancaria. */
	@Id
	@Column(name = "idCuentaBancariaEntidad", length = 32)
	private String idCuentaBancariaEntidad;

	/** El item by banco. 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idBanco", referencedColumnName = "idItem")
	private Item itemByBanco;*/
	@Column(name = "idBanco" , precision = 18 , scale = 0)
    private Long idItemByBanco;

	/** El entidad. */
	// @ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "idEntidad", referencedColumnName = "idEntidad")
	@Column(name = "idEntidad", length = 32)
	private String entidad;

	/** El item by moneda. 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idMoneda", referencedColumnName = "idItem")
	private Item itemByMoneda;*/
	@Column(name = "idMoneda" , precision = 18 , scale = 0)
    private Long idItemByMoneda;

	/** El item by tipo cuenta. 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idTipoCuenta", referencedColumnName = "idItem")
	private Item itemByTipoCuenta;*/
	@Column(name = "idTipoCuenta" , precision = 18 , scale = 0)
    private Long idItemByTipoCuenta;

	/** El nro cuenta. */
	@Column(name = "nroCuenta", length = 150)
	private String nroCuenta;

	/** El nro c c i. */
	@Column(name = "nroCCI", length = 151)
	private String nroCCI;

	/** El titular. 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idTitular", referencedColumnName = "idPersonal")
	// @Column(name = "idTitular" , length = 32)
	private Personal titular;*/
	@Column(name = "idPersonal", length = 32)
	private String idTitular;

	/** El estado. */
	@Column(name = "estado", length = 1)
	private String estado;

	/** El cuenta bancaria control pago list. */
	@OneToMany(mappedBy = "cuentaBancariaEntidad", fetch = FetchType.LAZY)
	private List<ControlPago> cuentaBancariaControlPagoList = new ArrayList<>();

	/**
	 * Instancia un nuevo cuenta bancaria entidad.
	 */
	public CuentaBancariaEntidad() {
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
		result = prime * result + ((idCuentaBancariaEntidad == null) ? 0 : idCuentaBancariaEntidad.hashCode());
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
		CuentaBancariaEntidad other = (CuentaBancariaEntidad) obj;
		if (idCuentaBancariaEntidad == null) {
			if (other.idCuentaBancariaEntidad != null) {
				return false;
			}
		} else if (!idCuentaBancariaEntidad.equals(other.idCuentaBancariaEntidad)) {
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
		return "CuentaBancariaEntidad [idCuentaBancariaEntidad=" + idCuentaBancariaEntidad + "]";
	}

}