package pe.buildsoft.erp.core.application.entidades.planilla;

import java.io.Serializable;
import java.time.OffsetDateTime;

import jakarta.persistence.Entity;
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
public class VacacionesDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id eps. */
	private String idVacaciones;

	/**
	 * El personal.
	 */
	private String idPersonal;
	private PersonalResponseVo personal;

	private OffsetDateTime fechaInicio;

	private OffsetDateTime fechaFin;

	private Integer dias;

	/**
	 * El anhio.
	 * 
	 */
	private Long idAnhio;

	private OffsetDateTime fechaReg;

	private OffsetDateTime fechaAprobacion;

	private String idpersonalAprob;

	private String estado;
	
	private String observacion;

	/**
	 * Instancia un nuevo vacaciones.
	 */
	public VacacionesDTO() {
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
		VacacionesDTO other = (VacacionesDTO) obj;
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
		return "VacacionesDTO [idVacaciones=" + idVacaciones + "]";
	}

}