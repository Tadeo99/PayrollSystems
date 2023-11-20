package pe.buildsoft.erp.core.application.entidades.pago;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class DetPlanPagosDTO.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Apr 22 21:45:33 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Getter
@Setter
public class DetPlanPagosDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id det plan pagos. */
	private String idDetPlanPagos;

	/** El plan pagos. */
	private PlanPagosDTO planPagos;

	/** El cuota concepto. */
	private CuotaConceptoDTO cuotaConcepto;

	/** El cuota. */
	private BigDecimal cuota;

	private BigDecimal subTotal;

	private BigDecimal igv;

	private Boolean aplicaIgv;

	/** El monto resta. */
	private BigDecimal montoResta;

	/** El fecha vencimiento. */
	private Date fechaVencimiento;

	/** El flag fraccionado. */
	private String flagFraccionado;

	private String nroCuota;

	private String estado;

	private String esLista;

	/** El alumno matricula list. */
	private List<DetPlanPagosDTO> detPlanPagosList = new ArrayList<>();

	/**
	 * Instancia un nuevo det plan pagosDTO.
	 */
	public DetPlanPagosDTO() {
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
		DetPlanPagosDTO other = (DetPlanPagosDTO) obj;
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
		return "DetPlanPagosDTO [idDetPlanPagos=" + idDetPlanPagos + "]";
	}

}