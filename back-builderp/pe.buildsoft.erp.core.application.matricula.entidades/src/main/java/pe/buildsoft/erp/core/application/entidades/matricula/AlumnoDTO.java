package pe.buildsoft.erp.core.application.entidades.matricula;

import java.io.Serializable;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.admision.GradoDTO;
import pe.buildsoft.erp.core.application.entidades.admision.PostulanteDTO;
import pe.buildsoft.erp.core.application.entidades.admision.SedeDTO;
import pe.buildsoft.erp.core.application.entidades.common.ItemDTO;
import pe.buildsoft.erp.core.application.entidades.common.UbigeoDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class AlumnoDTO.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Apr 22 00:26:54 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Getter
@Setter
public class AlumnoDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id alumno. */
	private String idAlumno;

	/** El postulante. */
	private PostulanteDTO postulante;

	/** El grado. */
	private GradoDTO grado;

	/** El codigo. */
	private String codigo;

	/** El estado. */
	private String estado;

	/** El item by doc identidad. */
	private ItemDTO itemByDocIdentidad;

	/** El nro doc. */
	private String nroDoc;

	/** El apellido paterno. */
	private String apellidoPaterno;

	/** El apellido materno. */
	private String apellidoMaterno;

	/** El nombres. */
	private String nombres;

	/** El fecha nacimiento. */
	private OffsetDateTime fechaNacimiento;

	/** El telefono1. */
	private String telefono1;

	/** El telefono2. */
	private String telefono2;

	/** El celular. */
	private String celular;

	/** El email. */
	private String email;

	/** El sexo. */
	private String sexo;

	/** El lugar nacimiento. */
	private UbigeoDTO lugarNacimiento;

	/** El item by nacionalidad. */
	private ItemDTO itemByNacionalidad;

	/** El foto. */
	private String foto;

	/** El usuario creacion. */
	private String usuarioCreacion;

	/** El fecha creacion. */
	private OffsetDateTime fechaCreacion;

	/** El usuario modificacion. */
	private String usuarioModificacion;

	/** El fecha modificacion. */
	private OffsetDateTime fechaModificacion;

	/** El entidad. */
	private String entidad;

	/** El sede. */
	private SedeDTO sede;


	/**
	 * Instancia un nuevo alumnoDTO.
	 */
	public AlumnoDTO() {
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
		result = prime * result + ((idAlumno == null) ? 0 : idAlumno.hashCode());
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
		AlumnoDTO other = (AlumnoDTO) obj;
		if (idAlumno == null) {
			if (other.idAlumno != null) {
				return false;
			}
		} else if (!idAlumno.equals(other.idAlumno)) {
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
		return "AlumnoDTO [idAlumno=" + idAlumno + "]";
	}

}