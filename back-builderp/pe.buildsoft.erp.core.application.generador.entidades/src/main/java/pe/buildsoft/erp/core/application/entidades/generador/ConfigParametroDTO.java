package pe.buildsoft.erp.core.application.entidades.generador;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class ConfigParametroDTO.
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
public class ConfigParametroDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	/** El id config arquetipo. */
	private String idConfigParametro;

	private String idGrupoNegocio;

	/** El descripcion. */
	private String descripcion;

	/** El codigo. */
	private String codigo;

	/** El valor. */
	private String valor;

	/**
	 * Instancia un nuevo config arquetipoDTO.
	 */
	public ConfigParametroDTO() {
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
		result = prime * result + ((idConfigParametro == null) ? 0 : idConfigParametro.hashCode());
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
		ConfigParametroDTO other = (ConfigParametroDTO) obj;
		if (idConfigParametro == null) {
			if (other.idConfigParametro != null) {
				return false;
			}
		} else if (!idConfigParametro.equals(other.idConfigParametro)) {
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
		return "ConfigParametroDTO [idConfigParametro=" + idConfigParametro + "]";
	}

}