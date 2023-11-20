package pe.buildsoft.erp.core.domain.entidades.planilla;

import java.io.Serializable;
import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class Vacaciones.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 23:10:02 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "Vacaciones", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_RRHH_PLANILLA)
@Getter
@Setter
public class Vacaciones extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id eps. */
	@Id
	@Column(name = "idVacaciones", length = 32)
	private String idVacaciones;

	/**
	 * El personal.
	 */
	@Column(name = "idPersonal", length = 32)
	private String idPersonal;

	@Column(name = "fechaInicio")
	private OffsetDateTime fechaInicio;

	@Column(name = "fechaFin")
	private OffsetDateTime fechaFin;

	/**
	 * El anhio.
	 * 
	 */
	@Column(name = "idAnhio", precision = 18, scale = 0)
	private Long idAnhio;

	@Column(name = "fechaReg")
	private OffsetDateTime fechaReg;

	@Column(name = "fechaAprobacion")
	private OffsetDateTime fechaAprobacion;

	@Column(name = "idpersonalAprob", length = 32)
	private String idpersonalAprob;

	@Column(name = "estado", length = 1)
	private String estado;
	
	@Column(name = "observacion", length = 500)
	private String observacion;


	/**
	 * Instancia un nuevo concepto regimen pensionario.
	 */
	public Vacaciones() {
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
		result = prime * result + ((idVacaciones == null) ? 0 : idVacaciones.hashCode());
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
		Vacaciones other = (Vacaciones) obj;
		if (idVacaciones == null) {
			if (other.idVacaciones != null) {
				return false;
			}
		} else if (!idVacaciones.equals(other.idVacaciones)) {
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
		return "Vacaciones [idVacaciones=" + idVacaciones + "]";
	}

}