package pe.buildsoft.erp.core.application.entidades.matricula;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class DetMallaCurricularDTO.
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
public class DetMallaCurricularDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id det malla curricular. */
	private String idDetMallaCurricular;

	/** El det malla curricular padre. */
	private DetMallaCurricularDTO detMallaCurricularPadre;

	/** El malla curricular. */
	private MallaCurricularDTO mallaCurricular;

	/** El descripcion curso. */
	private String descripcionCurso;

	/** El codigo asignatura. */
	private String codigoAsignatura;

	/** El hora teorica. */
	private Integer horaTeorica;

	/** El hora practica. */
	private Integer horaPractica;

	/** El tipo asignatura. */
	private String tipoAsignatura;

	/** El peso. */
	private Long peso;

	/** El tipo calculo. */
	private String tipoCalculo;

	/** El tipo promedio. */
	private String tipoPromedio;

	/**
	 * Instancia un nuevo det malla curricularDTO.
	 */
	public DetMallaCurricularDTO() {
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
		result = prime * result + ((idDetMallaCurricular == null) ? 0 : idDetMallaCurricular.hashCode());
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
		DetMallaCurricularDTO other = (DetMallaCurricularDTO) obj;
		if (idDetMallaCurricular == null) {
			if (other.idDetMallaCurricular != null) {
				return false;
			}
		} else if (!idDetMallaCurricular.equals(other.idDetMallaCurricular)) {
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
		return "DetMallaCurricularDTO [idDetMallaCurricular=" + idDetMallaCurricular + "]";
	}

}