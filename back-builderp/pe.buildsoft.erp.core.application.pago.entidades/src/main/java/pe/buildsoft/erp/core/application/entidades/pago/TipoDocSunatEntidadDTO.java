package pe.buildsoft.erp.core.application.entidades.pago;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.common.ItemDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class TipoDocSunatEntidadDTO.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Apr 22 21:45:31 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Getter
@Setter
public class TipoDocSunatEntidadDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id tipo doc sunat entidad. */
	private String idTipoDocSunatEntidad;

	/** El item by tipo doc sunat. */
	private Long idItemByTipoDocSunat;
	private ItemDTO itemByTipoDocSunat;

	/** El entidad. */
	private String idEntidad;

	/** El correla. */
	private String correla;

	private String serie;

	/**
	 * Instancia un nuevo tipo doc sunat entidadDTO.
	 */
	public TipoDocSunatEntidadDTO() {
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
		result = prime * result + ((idTipoDocSunatEntidad == null) ? 0 : idTipoDocSunatEntidad.hashCode());
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
		TipoDocSunatEntidadDTO other = (TipoDocSunatEntidadDTO) obj;
		if (idTipoDocSunatEntidad == null) {
			if (other.idTipoDocSunatEntidad != null) {
				return false;
			}
		} else if (!idTipoDocSunatEntidad.equals(other.idTipoDocSunatEntidad)) {
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
		return "TipoDocSunatEntidadDTO [idTipoDocSunatEntidad=" + idTipoDocSunatEntidad + "]";
	}

}