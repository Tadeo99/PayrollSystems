package pe.buildsoft.erp.core.application.entidades.pago;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.common.AnhioDTO;
import pe.buildsoft.erp.core.application.entidades.common.ItemDTO;
import pe.buildsoft.erp.core.application.entidades.escalafon.EmpresaDTO;
import pe.buildsoft.erp.core.application.entidades.matricula.AlumnoDTO;
import pe.buildsoft.erp.core.application.entidades.matricula.PeriodoDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class ControlPagoDTO.
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
public class ControlPagoDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id control pago. */
	private String idControlPago;

	/** El anhio. */
	private Long idAnhio;
	private AnhioDTO anhio;

	/** El periodo. */
	private PeriodoDTO periodo;

	/** El alumno. */
	private String idAlumno;
	private AlumnoDTO alumno;

	/** El empresa. */
	private EmpresaDTO empresa;

	/** El tipo doc sunat. */
	private Long idTipoDocSunat;
	private ItemDTO tipoDocSunat;

	/** El item by tipo moneda. */
	private Long idItemByTipoMoneda;
	private ItemDTO itemByTipoMoneda;

	/** El tipo cambio. */
	private BigDecimal tipoCambio;

	/** El nro doc. */
	private String nroDoc;

	/** El igv. */
	private BigDecimal igv;

	/** El sub monto total. */
	private BigDecimal subMontoTotal;

	/** El monto total. */
	private BigDecimal montoTotal;

	/** El fecha pago. */
	private OffsetDateTime fechaPago;

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

	/** El origen. */
	private String origen;

	/** El tipo trajeta. */
	private String tipoTrajeta;

	/** El cuenta bancaria. */
	private CuentaBancariaEntidadDTO cuentaBancariaEntidad;

	/** El nro operacion. */
	private String nroOperacion;

	/** El fecha operacion. */
	private OffsetDateTime fechaOperacion;

	/** El serie. */
	private String serie;

	/** El envio sunat. */
	private String envioSunat;

	/** El anulado. */
	private String anulado;

	/** El tipo descuento. */
	private String tipoDescuento;

	/** El descuento total. */
	private BigDecimal descuentoTotal;

	/** El descuento */
	private BigDecimal descuento;

	/** El observacion. */
	private String observacion;

	private Boolean deposito;

	/** El entidad. */
	private String idEntidad;

	/** El control pago det control pago list. */
	private List<DetControlPagoDTO> controlPagoDetControlPagoList = new ArrayList<>();

	private boolean esAnulado;

	private String tri = "";

	private String det = "";

	private String ley = "";

	private String montoletra = "";

	private String tipo = "";
	private Integer varCantidad;

	private String idEntidadSelect;

	/**
	 * Instancia un nuevo control pagoDTO.
	 */
	public ControlPagoDTO() {
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
		ControlPagoDTO other = (ControlPagoDTO) obj;
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
		return "ControlPagoDTO [idControlPago=" + idControlPago + "]";
	}

}