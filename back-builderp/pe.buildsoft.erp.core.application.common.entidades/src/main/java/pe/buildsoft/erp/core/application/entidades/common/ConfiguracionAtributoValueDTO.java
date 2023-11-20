package pe.buildsoft.erp.core.application.entidades.common;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * La Class ConfiguracionAtributoValueDTO.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Tue Apr 09 11:11:55 COT 2019
 * @since SIAA-CORE 2.1
 */
@Getter
@Setter
public class ConfiguracionAtributoValueDTO implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id configuracion atributo value. */
	private String idConfiguracionAtributoValue;

	/** El configuracion atributo. */
	private ConfiguracionAtributoDTO configuracionAtributo;

	/** El id tupla entidad. */
	private String idTuplaEntidad;

	/** El value. */
	private String value;

	/**
	 * Instancia un nuevo configuracion atributo valueDTO.
	 */
	public ConfiguracionAtributoValueDTO() {
		super();
	}

	/**
	 * Instancia un nuevo configuracion atributo valueDTO.
	 *
	 * @param idConfiguracionAtributoValue el id configuracion atributo value
	 * @param configuracionAtributo        el configuracion atributo
	 * @param idTuplaEntidad               el id tupla entidad
	 * @param value                        el value
	 */
	public ConfiguracionAtributoValueDTO(String idConfiguracionAtributoValue,
			ConfiguracionAtributoDTO configuracionAtributo, String idTuplaEntidad, String value) {
		super();
		this.idConfiguracionAtributoValue = idConfiguracionAtributoValue;
		this.configuracionAtributo = configuracionAtributo;
		this.idTuplaEntidad = idTuplaEntidad;
		this.value = value;
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
		result = prime * result
				+ ((idConfiguracionAtributoValue == null) ? 0 : idConfiguracionAtributoValue.hashCode());
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
		ConfiguracionAtributoValueDTO other = (ConfiguracionAtributoValueDTO) obj;
		if (idConfiguracionAtributoValue == null) {
			if (other.idConfiguracionAtributoValue != null) {
				return false;
			}
		} else if (!idConfiguracionAtributoValue.equals(other.idConfiguracionAtributoValue)) {
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
		return "ConfiguracionAtributoValueDTO [idConfiguracionAtributoValue=" + idConfiguracionAtributoValue + "]";
	}

}