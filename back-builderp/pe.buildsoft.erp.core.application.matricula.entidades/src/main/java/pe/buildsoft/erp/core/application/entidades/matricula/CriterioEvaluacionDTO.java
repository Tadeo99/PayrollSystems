package pe.buildsoft.erp.core.application.entidades.matricula;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class CriterioEvaluacionDTO.
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
public class CriterioEvaluacionDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id criterio evaluacion. */
	private String idCriterioEvaluacion;

	/** El det malla curricular. */
	private DetMallaCurricularDTO detMallaCurricular;

	/** El criterio evaluacion. */
	private CriterioEvaluacionDTO criterioEvaluacionPadre;

	/** El codigo. */
	private String codigo;

	/** El nombre. */
	private String nombre;

	/** El abreviatura. */
	private String abreviatura;

	/** El peso. */
	private BigDecimal peso;

	/** El nro orden. */
	private Long nroOrden;

	/** El estado. */
	private String estado;

	/** El criterio evaluacion padre menu list. */
	private List<CriterioEvaluacionDTO> criterioEvaluacionHijos = new ArrayList<>();

	/**
	 * Instancia un nuevo criterio evaluacionDTO.
	 */
	public CriterioEvaluacionDTO() {
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
		result = prime * result + ((idCriterioEvaluacion == null) ? 0 : idCriterioEvaluacion.hashCode());
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
		CriterioEvaluacionDTO other = (CriterioEvaluacionDTO) obj;
		if (idCriterioEvaluacion == null) {
			if (other.idCriterioEvaluacion != null) {
				return false;
			}
		} else if (!idCriterioEvaluacion.equals(other.idCriterioEvaluacion)) {
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
		return "CriterioEvaluacionDTO [idCriterioEvaluacion=" + idCriterioEvaluacion + "]";
	}

}