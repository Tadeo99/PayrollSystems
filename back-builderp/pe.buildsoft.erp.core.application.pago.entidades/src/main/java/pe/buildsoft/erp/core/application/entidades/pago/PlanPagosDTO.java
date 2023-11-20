package pe.buildsoft.erp.core.application.entidades.pago;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.common.AnhioDTO;
import pe.buildsoft.erp.core.application.entidades.matricula.AlumnoDTO;
import pe.buildsoft.erp.core.application.entidades.matricula.PeriodoDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class PlanPagosDTO.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Apr 22 21:45:31 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Getter
@Setter
public class PlanPagosDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id plan pagos. */
	private String idPlanPagos;

	/** El anhio. */
	private Long idAnhio;
	private AnhioDTO anhio;

	/** El id periodo. */
	private Long idPeriodo;
	private PeriodoDTO periodo;

	/** El alumno. */
	private String idAlumno;
	private AlumnoDTO alumno;

	/** El fecha creacion. */
	private OffsetDateTime fechaCreacion;

	/** El usuario creacion. */
	private String usuarioCreacion;

	/** El fecha modificacion. */
	private OffsetDateTime fechaModificacion;

	/** El usuario modificacion. */
	private String usuarioModificacion;

	/** El plan pagos det plan pagos list. */
	private List<DetPlanPagosDTO> planPagosDetPlanPagosList = new ArrayList<>();

	/**
	 * Instancia un nuevo plan pagosDTO.
	 */
	public PlanPagosDTO() {
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
		result = prime * result + ((idPlanPagos == null) ? 0 : idPlanPagos.hashCode());
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
		PlanPagosDTO other = (PlanPagosDTO) obj;
		if (idPlanPagos == null) {
			if (other.idPlanPagos != null) {
				return false;
			}
		} else if (!idPlanPagos.equals(other.idPlanPagos)) {
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
		return "PlanPagosDTO [idPlanPagos=" + idPlanPagos + "]";
	}

}