package pe.buildsoft.erp.core.application.entidades.matricula;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.common.ItemDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class HorarioDTO.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Apr 22 00:26:55 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Getter
@Setter
public class HorarioDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id horario. */
	private String idHorario;

	/** El detalle carga academica. */
	private DetalleCargaAcademicaDTO detalleCargaAcademica;

	/** El personal by docente. */
	private String personalByDocente;

	/** El item by dia. */
	private ItemDTO itemByDia;

	/** El hora inicio. */
	private String horaInicio;

	/** El hora fin. */
	private String horaFin;

	/**
	 * Instancia un nuevo horarioDTO.
	 */
	public HorarioDTO() {
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
		result = prime * result + ((idHorario == null) ? 0 : idHorario.hashCode());
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
		HorarioDTO other = (HorarioDTO) obj;
		if (idHorario == null) {
			if (other.idHorario != null) {
				return false;
			}
		} else if (!idHorario.equals(other.idHorario)) {
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
		return "HorarioDTO [idHorario=" + idHorario + "]";
	}

}