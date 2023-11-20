package pe.buildsoft.erp.core.application.entidades.admision;

import java.io.Serializable;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.common.ItemDTO;
import pe.buildsoft.erp.core.application.entidades.common.UbigeoDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class PostulanteDTO.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Wed Apr 21 12:29:29 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Getter
@Setter
public class PostulanteDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id postulante. */
	private String idPostulante;

	/** El codigo. */
	private String codigo;

	/** El estado. */
	private String estado;

	/** El item by doc identidad. */
	private Long idItemByDocIdentidad;
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
	private String idLugarNacimiento;
	private UbigeoDTO lugarNacimiento;

	/** El item by nacionalidad. */
	private Long idItemByNacionalidad;
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

	private Long tipo;

	/** El idApoderado. */
	private AsignaPostulanteDTO asignaPostulante;

	/**
	 * Instancia un nuevo postulanteDTO.
	 */
	public PostulanteDTO() {
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
		result = prime * result + ((idPostulante == null) ? 0 : idPostulante.hashCode());
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
		PostulanteDTO other = (PostulanteDTO) obj;
		if (idPostulante == null) {
			if (other.idPostulante != null) {
				return false;
			}
		} else if (!idPostulante.equals(other.idPostulante)) {
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
		return "PostulanteDTO [idPostulante=" + idPostulante + "]";
	}

}