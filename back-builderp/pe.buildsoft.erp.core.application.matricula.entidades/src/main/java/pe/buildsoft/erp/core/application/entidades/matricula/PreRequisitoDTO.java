package pe.buildsoft.erp.core.application.entidades.matricula;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class PreRequisitoDTO.
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
public class PreRequisitoDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id pre requisito. */
	private String idPreRequisito;

	/** El det malla curricular. */
	private DetMallaCurricularDTO detMallaCurricular;

	/** El det malla curricular requisito. */
	private DetMallaCurricularDTO detMallaCurricularRequisito;

	/**
	 * Instancia un nuevo pre requisitoDTO.
	 */
	public PreRequisitoDTO() {
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
		result = prime * result + ((idPreRequisito == null) ? 0 : idPreRequisito.hashCode());
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
		PreRequisitoDTO other = (PreRequisitoDTO) obj;
		if (idPreRequisito == null) {
			if (other.idPreRequisito != null) {
				return false;
			}
		} else if (!idPreRequisito.equals(other.idPreRequisito)) {
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
		return "PreRequisitoDTO [idPreRequisito=" + idPreRequisito + "]";
	}

}