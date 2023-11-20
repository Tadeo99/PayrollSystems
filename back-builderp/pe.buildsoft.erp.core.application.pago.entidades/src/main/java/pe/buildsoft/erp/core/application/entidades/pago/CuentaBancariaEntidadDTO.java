package pe.buildsoft.erp.core.application.entidades.pago;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.common.ItemDTO;
import pe.buildsoft.erp.core.application.entidades.escalafon.PersonalDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class CuentaBancariaEntidadDTO.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Apr 22 21:45:29 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Getter
@Setter
public class CuentaBancariaEntidadDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id cuenta bancaria. */
	private String idCuentaBancariaEntidad;

	/** El item by banco. */
	private Long idItemByBanco;
	private ItemDTO itemByBanco;

	/** El entidad. */
	private String idEntidad;

	/** El item by moneda. */
	private Long idItemByMoneda;
	private ItemDTO itemByMoneda;

	/** El item by tipo cuenta. */
	private Long idItemByTipoCuenta;
	private ItemDTO itemByTipoCuenta;

	/** El nro cuenta. */
	private String nroCuenta;

	/** El nro c c i. */
	private String nroCCI;

	/** El titular. */
	private PersonalDTO idTitular;
	private PersonalDTO titular;

	/** El estado. */
	private String estado;


	/**
	 * Instancia un nuevo cuenta bancaria entidadDTO.
	 */
	public CuentaBancariaEntidadDTO() {
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
		CuentaBancariaEntidadDTO other = (CuentaBancariaEntidadDTO) obj;
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
		return "CuentaBancariaEntidadDTO [idCuentaBancariaEntidad=" + idCuentaBancariaEntidad + "]";
	}

}