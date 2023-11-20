package pe.buildsoft.erp.core.application.entidades.matricula;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.escalafon.PersonalDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class DetalleCargaAcademicaDTO.
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
public class DetalleCargaAcademicaDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id detalle carga academica. */
	private String idDetalleCargaAcademica;

	/** El carga academica. */
	private CargaAcademicaDTO cargaAcademica;

	/** El personal by docente principal. */
	private PersonalDTO personalByDocentePrincipal;

	/** El personal by docente auxiliar. */
	private PersonalDTO personalByDocenteAuxiliar;

	/** El det malla curricular. */
	private DetMallaCurricularDTO detMallaCurricular;

	/** El codigo acta. */
	private String codigoActa;

	/** El grupo. */
	private GrupoDTO grupo;

	private boolean checked;

	private boolean esPadre;

	/** El registro nota det registro nota list. */
	private List<DetalleCargaAcademicaDTO> detalleCargaAcademicaList = new ArrayList<>();

	/**
	 * Instancia un nuevo detalle carga academicaDTO.
	 */
	public DetalleCargaAcademicaDTO() {
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
		result = prime * result + ((idDetalleCargaAcademica == null) ? 0 : idDetalleCargaAcademica.hashCode());
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
		DetalleCargaAcademicaDTO other = (DetalleCargaAcademicaDTO) obj;
		if (idDetalleCargaAcademica == null) {
			if (other.idDetalleCargaAcademica != null) {
				return false;
			}
		} else if (!idDetalleCargaAcademica.equals(other.idDetalleCargaAcademica)) {
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
		return "DetalleCargaAcademicaDTO [idDetalleCargaAcademica=" + idDetalleCargaAcademica + "]";
	}

}