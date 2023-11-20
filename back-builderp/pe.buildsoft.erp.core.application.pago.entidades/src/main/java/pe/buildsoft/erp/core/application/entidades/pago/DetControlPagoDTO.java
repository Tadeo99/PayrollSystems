package pe.buildsoft.erp.core.application.entidades.pago;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class DetControlPagoDTO.
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
public class DetControlPagoDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id det control pago. */
	private String idDetControlPago;

	/** El control pago. */
	private ControlPagoDTO controlPago;

	/** El cuota concepto. */
	private CuotaConceptoDTO cuotaConcepto;

	/** El nro cuota. */
	private String nroCuota;

	/** El descripcion concepto. */
	private String descripcionConcepto;

	/** El monto. */
	private BigDecimal monto;

	/** El mora. */
	private BigDecimal mora;

	/** El fecha reversion. */
	private OffsetDateTime fechaReversion;

	/** El fecha creacion. */
	private OffsetDateTime fechaCreacion;

	/** El usuario creacion. */
	private String usuarioCreacion;

	/** El fecha modificacion. */
	private OffsetDateTime fechaModificacion;

	/** El usuario modificacion. */
	private String usuarioModificacion;

	/** El estado. */
	private String estado;

	/** El ip. */
	private String ip;

	/** El ip. */
	private BigDecimal montoResta;

	private Boolean aplicaIgv;

	/**
	 * Instancia un nuevo det control pagoDTO.
	 */
	public DetControlPagoDTO() {
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
		DetControlPagoDTO other = (DetControlPagoDTO) obj;
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
		return "DetControlPagoDTO [idDetControlPago=" + idDetControlPago + "]";
	}

}