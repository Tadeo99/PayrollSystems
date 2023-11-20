package pe.buildsoft.erp.core.application.entidades.admision;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.common.UbigeoDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class SedeDTO.
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
public class SedeDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id sede. */
	private String idSede;

	/** El codigo. */
	private String codigo;

	/** El nombre. */
	private String nombre;

	/** El direccion. */
	private String direccion;

	/** El coordenada direccion. */
	private String coordenadaDireccion;

	/** El ubigeo. */
	private String idUbigeo;
	private UbigeoDTO ubigeo;

	/** El estado. */
	private String estado;

	/** El usuario creacion. */
	private String usuarioCreacion;

	/** El fecha creacion. */
	private OffsetDateTime fechaCreacion;

	/** El usuario modificacion. */
	private String usuarioModificacion;

	/** El fecha modificacion. */
	private OffsetDateTime fechaModificacion;

	/** El sede det sede list. */
	private List<DetSedeDTO> sedeDetSedeList = new ArrayList<>();

	/**
	 * Instancia un nuevo sedeDTO.
	 */
	public SedeDTO() {
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
		result = prime * result + ((idSede == null) ? 0 : idSede.hashCode());
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
		SedeDTO other = (SedeDTO) obj;
		if (idSede == null) {
			if (other.idSede != null) {
				return false;
			}
		} else if (!idSede.equals(other.idSede)) {
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
		return "SedeDTO [idSede=" + idSede + "]";
	}

}