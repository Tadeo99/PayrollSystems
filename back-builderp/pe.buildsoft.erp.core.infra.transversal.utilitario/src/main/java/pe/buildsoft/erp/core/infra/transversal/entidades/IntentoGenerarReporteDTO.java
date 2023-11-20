package pe.buildsoft.erp.core.infra.transversal.entidades;

import java.io.Serializable;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

/**
 * La Class IntentoGenerarReporteDTO.
 * <ul>
 * <li>Copyright 2014 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Fri Aug 22 09:10:01 COT 2014
 * @since BuildErp 1.0
 */
@Getter
@Setter
public class IntentoGenerarReporteDTO implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id intento. */
	private String idIntento;

	/** El solicitud reporte. */
	private SolicitudReporteDTO solicitudReporte;

	/** El fecha envio. */
	private OffsetDateTime fechaEnvio;

	/** El numero intento. */
	private Long numeroIntento;

	/** El estado. */
	private String estado;

	/** El descripcion. */
	private String descripcion;

	/**
	 * Instancia un nuevo intento generar reporte.
	 */
	public IntentoGenerarReporteDTO() {
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
		result = prime * result + ((idIntento == null) ? 0 : idIntento.hashCode());
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
		IntentoGenerarReporteDTO other = (IntentoGenerarReporteDTO) obj;
		if (idIntento == null) {
			if (other.idIntento != null) {
				return false;
			}
		} else if (!idIntento.equals(other.idIntento)) {
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
		return "IntentoGenerarReporteDTO [idIntento=" + idIntento + "]";
	}

}