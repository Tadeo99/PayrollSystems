package pe.buildsoft.erp.core.infra.data.entidades.webclient.rest.common.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * La Class ParametroVO.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Dec 14 00:27:42 COT 2017
 * @since SIAA-CORE 2.1
 */
@Getter
@Setter
public class ParametroVO implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id parametro. */
	private String idParametro;

	/** El id entidad. */
	private String entidad;

	/** El descripcion. */
	private String descripcion;

	/** El codigo. */
	private String codigo;

	/** El valor. */
	private String valor;

	/** El estado. */
	private String estado;

	/**
	 * Instancia un nuevo parametroDTO.
	 */
	public ParametroVO() {
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
		result = prime * result + ((idParametro == null) ? 0 : idParametro.hashCode());
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
		ParametroVO other = (ParametroVO) obj;
		if (idParametro == null) {
			if (other.idParametro != null) {
				return false;
			}
		} else if (!idParametro.equals(other.idParametro)) {
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
		return "ParametroDTO [idParametro=" + idParametro + "]";
	}

}