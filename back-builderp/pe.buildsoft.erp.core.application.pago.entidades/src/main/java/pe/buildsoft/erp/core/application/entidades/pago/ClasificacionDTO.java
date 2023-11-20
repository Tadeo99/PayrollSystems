package pe.buildsoft.erp.core.application.entidades.pago;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.common.ItemDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class ClasificacionDTO.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Apr 22 21:45:29 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Getter
@Setter
public class ClasificacionDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id clasificacion. */
	private Long idClasificacion;

	/** El item by tipo clasificacion. */
	private Long idItemByTipoClasificacion;
	private ItemDTO itemByTipoClasificacion;

	/** El descripcion. */
	private String descripcion;

	/** El abreviatura. */
	private String abreviatura;

	/** El entidad. */
	private String entidad;

	/** El sede. */
	private String idSede;


	/**
	 * Instancia un nuevo clasificacionDTO.
	 */
	public ClasificacionDTO() {
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
		result = prime * result + ((idClasificacion == null) ? 0 : idClasificacion.hashCode());
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
		ClasificacionDTO other = (ClasificacionDTO) obj;
		if (idClasificacion == null) {
			if (other.idClasificacion != null) {
				return false;
			}
		} else if (!idClasificacion.equals(other.idClasificacion)) {
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
		return "ClasificacionDTO [idClasificacion=" + idClasificacion + "]";
	}

}