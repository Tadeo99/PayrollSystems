package pe.buildsoft.erp.core.application.entidades.matricula;

import java.io.Serializable;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.admision.GradoDTO;
import pe.buildsoft.erp.core.application.entidades.common.AnhioDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class MallaCurricularDTO.
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
public class MallaCurricularDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id malla curricular. */
	private String idMallaCurricular;

	/** El anhio. */
	private AnhioDTO anhio;

	/** El grado. */
	private GradoDTO grado;

	/** El estado. */
	private String estado;

	/** El fecha creacion. */
	private OffsetDateTime fechaCreacion;

	/**
	 * Instancia un nuevo malla curricularDTO.
	 */
	public MallaCurricularDTO() {
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
		result = prime * result + ((idMallaCurricular == null) ? 0 : idMallaCurricular.hashCode());
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
		MallaCurricularDTO other = (MallaCurricularDTO) obj;
		if (idMallaCurricular == null) {
			if (other.idMallaCurricular != null) {
				return false;
			}
		} else if (!idMallaCurricular.equals(other.idMallaCurricular)) {
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
		return "MallaCurricularDTO [idMallaCurricular=" + idMallaCurricular + "]";
	}

}