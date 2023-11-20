package pe.buildsoft.erp.core.application.entidades.planilla;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class PersonalDTO.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Apr 22 23:29:56 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Getter
@Setter
public class PersonalResponseVo extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id personal. */
	private String idPersonal;

	/** El codigo unico. */
	private String codigoUnico;

	/** El estado. */
	private String estado;

	/** El item by doc identidad. */
	private Long idItemByDocIdentidad;

	/** El nro doc. */
	private String nroDoc;

	/** El apellido paterno. */
	private String apellidoPaterno;

	/** El apellido materno. */
	private String apellidoMaterno;

	/** El nombres. */
	private String nombres;

	/**
	 * Instancia un nuevo personalDTO.
	 */
	public PersonalResponseVo() {
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
		result = prime * result + ((idPersonal == null) ? 0 : idPersonal.hashCode());
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
		PersonalResponseVo other = (PersonalResponseVo) obj;
		if (idPersonal == null) {
			if (other.idPersonal != null) {
				return false;
			}
		} else if (!idPersonal.equals(other.idPersonal)) {
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
		return "PersonalDTO [idPersonal=" + idPersonal + "]";
	}

}