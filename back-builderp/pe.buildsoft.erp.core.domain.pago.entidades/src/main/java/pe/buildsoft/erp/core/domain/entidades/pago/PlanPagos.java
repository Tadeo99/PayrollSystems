package pe.buildsoft.erp.core.domain.entidades.pago;

import java.io.Serializable;
import java.time.OffsetDateTime;
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
 * La Class PlanPagos.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:56 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "PlanPagos", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_PAGO)
@Getter
@Setter
public class PlanPagos extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id plan pagos. */
	@Id
	@Column(name = "idPlanPagos", length = 32)
	private String idPlanPagos;

	/** El anhio. 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idAnhio", referencedColumnName = "idAnhio")
	// @Column(name = "idAnhio" , precision = 18 , scale = 0)
	private Anhio anhio;*/
	@Column(name = "idAnhio" , precision = 18 , scale = 0)
    private Long idAnhio;
	

	/** El id periodo.
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPeriodo", referencedColumnName = "idPeriodo")
	// @Column(name = "idPeriodo" , precision = 18 , scale = 0)
	private Periodo periodo; */
	@Column(name = "idPeriodo", precision = 18, scale = 0)
	private Long idPeriodo;

	/** El alumno. 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idAlumno", referencedColumnName = "idAlumno")
	// @Column(name = "idAlumno" , length = 10)
	private Alumno alumno;*/
	@Column(name = "idAlumno", length = 10)
	private String idAlumno;

	/** El fecha creacion. */
	// @Temporal( TemporalType.TIMESTAMP)
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

	/** El plan pagos det plan pagos list. */
	@OneToMany(mappedBy = "planPagos", fetch = FetchType.LAZY)
	private List<DetPlanPagos> planPagosDetPlanPagosList = new ArrayList<>();

	/**
	 * Instancia un nuevo plan pagos.
	 */
	public PlanPagos() {
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
		PlanPagos other = (PlanPagos) obj;
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
		return "PlanPagos [idPlanPagos=" + idPlanPagos + "]";
	}

}