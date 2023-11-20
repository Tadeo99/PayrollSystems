package pe.buildsoft.erp.core.application.entidades.cola;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class ColaDTO.
 * <ul>
 * <li>Copyright 2015 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @version 1.0, Thu Dec 21 12:16:30 COT 2017
 * @since BuildErp 1.0
 */
@Getter
@Setter
public class ColaDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id cola. */
	private Long idCola;

	/** El nombre cola. */
	private String nombreCola;

	/** El nivel cola. */
	private Long nivelCola;

	/** El fecha actualizacion. */
	private OffsetDateTime fechaActualizacion;

	/** El codigo usuario. */
	private String codigoUsuario;

	/** El estado. */
	private String estado;

	/** El cola configuracion filtro cola list. */
	private List<ConfiguracionFiltroColaDTO> colaConfiguracionFiltroColaList = new ArrayList<>();

	/**
	 * Instancia un nuevo colaDTO.
	 */
	public ColaDTO() {
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
		result = prime * result + ((idCola == null) ? 0 : idCola.hashCode());
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
		ColaDTO other = (ColaDTO) obj;
		if (idCola == null) {
			if (other.idCola != null) {
				return false;
			}
		} else if (!idCola.equals(other.idCola)) {
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
		return "ColaDTO [idCola=" + idCola + "]";
	}

}