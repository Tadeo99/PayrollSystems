package pe.buildsoft.erp.core.application.entidades.generador;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class ConfigTypeEquivalenciaDTO.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Mon Sep 26 12:14:56 PET 2022
 * @since BUILDERP-CORE 2.1
 */
@Getter
@Setter
public class EquivalenciaTypeDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id config type equivalencia. */
	private String idEquivalenciaType;

	/** El tecnologia by base datos. */
	private TecnologiaDTO tecnologia;

	/** El tecnologia equivalente. */
	private TecnologiaDTO tecnologiaEquivalente;

	/** El type. */
	private String type;

	/** El type atribute. */
	private String typeAtribute;

	/** El es decimal. */
	private String esDecimal;

	/**
	 * Instancia un nuevo config type equivalenciaDTO.
	 */
	public EquivalenciaTypeDTO() {
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
		result = prime * result + ((idEquivalenciaType == null) ? 0 : idEquivalenciaType.hashCode());
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
		EquivalenciaTypeDTO other = (EquivalenciaTypeDTO) obj;
		if (idEquivalenciaType == null) {
			if (other.idEquivalenciaType != null) {
				return false;
			}
		} else if (!idEquivalenciaType.equals(other.idEquivalenciaType)) {
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
		return "EquivalenciaTypeDTO [idEquivalenciaType=" + idEquivalenciaType + "]";
	}

}