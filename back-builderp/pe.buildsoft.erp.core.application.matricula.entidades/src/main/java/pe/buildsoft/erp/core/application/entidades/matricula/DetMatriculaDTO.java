package pe.buildsoft.erp.core.application.entidades.matricula;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class DetMatriculaDTO.
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
public class DetMatriculaDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id det matricula. */
	private String idDetMatricula;

	/** El matricula. */
	private MatriculaDTO matricula;

	/** El det malla curricular. */
	private DetMallaCurricularDTO detMallaCurricular;

	/** El descripcion curso. */
	private String descripcionCurso;

	/** El codigo asignatura. */
	private String codigoAsignatura;

	/** El estado. */
	private String estado;

	/**
	 * Instancia un nuevo det matriculaDTO.
	 */
	public DetMatriculaDTO() {
		super();
	}

	public DetMatriculaDTO(String idDetMatricula) {
		this.idDetMatricula = idDetMatricula;
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
		result = prime * result + ((idDetMatricula == null) ? 0 : idDetMatricula.hashCode());
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
		DetMatriculaDTO other = (DetMatriculaDTO) obj;
		if (idDetMatricula == null) {
			if (other.idDetMatricula != null) {
				return false;
			}
		} else if (!idDetMatricula.equals(other.idDetMatricula)) {
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
		return "DetMatriculaDTO [idDetMatricula=" + idDetMatricula + "]";
	}

}