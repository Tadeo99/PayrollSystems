package pe.buildsoft.erp.core.application.entidades.nota;

import java.io.Serializable;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.common.ItemDTO;
import pe.buildsoft.erp.core.application.entidades.matricula.AlumnoDTO;
import pe.buildsoft.erp.core.application.entidades.matricula.DetalleCargaAcademicaDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class AsistenciaAlumnoDTO.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Jun 10 00:13:45 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Getter
@Setter
public class AsistenciaAlumnoDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id asistencia alumno. */
	private String idAsistenciaAlumno;

	/** El alumno. */
	private AlumnoDTO alumno;

	/** El id detalle carga academica. */
	private DetalleCargaAcademicaDTO detalleCargaAcademica;

	/** El item by dia. */
	private ItemDTO itemByDia;

	/** El estado. */
	private String estado;

	/** El justificacion. */
	private String justificacion;

	/** El fecha horario. */
	private OffsetDateTime fechaHorario;

	/** El fecha creacion. */
	private OffsetDateTime fechaCreacion;

	/** El usuario creacion. */
	private String usuarioCreacion;

	/** El fecha modificacion. */
	private OffsetDateTime fechaModificacion;

	/** El usuario modificacion. */
	private String usuarioModificacion;

	/**
	 * Instancia un nuevo asistencia alumnoDTO.
	 */
	public AsistenciaAlumnoDTO() {
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
		result = prime * result + ((idAsistenciaAlumno == null) ? 0 : idAsistenciaAlumno.hashCode());
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
		AsistenciaAlumnoDTO other = (AsistenciaAlumnoDTO) obj;
		if (idAsistenciaAlumno == null) {
			if (other.idAsistenciaAlumno != null) {
				return false;
			}
		} else if (!idAsistenciaAlumno.equals(other.idAsistenciaAlumno)) {
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
		return "AsistenciaAlumnoDTO [idAsistenciaAlumno=" + idAsistenciaAlumno + "]";
	}

}