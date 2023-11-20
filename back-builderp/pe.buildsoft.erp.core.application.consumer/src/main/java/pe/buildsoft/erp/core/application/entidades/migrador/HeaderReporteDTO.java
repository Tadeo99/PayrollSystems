package pe.buildsoft.erp.core.application.entidades.migrador;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class HeaderReporteDTO.
 * <ul>
 * <li>Copyright 2018 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Mon Oct 22 12:34:48 COT 2018
 * @since BuildErp 1.0
 */
@Getter
@Setter
public class HeaderReporteDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id header reporte. */
	private String idHeaderReporte;

	/** El codigo reporte. */
	private String codigoReporte;

	/** El tipo reporte. */
	private String tipoReporte;

	/** El key header. */
	private String keyHeader;

	/** El value header. */
	private String valueHeader;

	/** El tipo formato. */
	private String tipoFormato;

	/** El value formato. */
	private String valueFormato;

	/** El orden. */
	private Long orden;

	/** El estado. */
	private String estado;

	/**
	 * Instancia un nuevo header reporteDTO.
	 */
	public HeaderReporteDTO() {
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
		result = prime * result + ((idHeaderReporte == null) ? 0 : idHeaderReporte.hashCode());
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
		HeaderReporteDTO other = (HeaderReporteDTO) obj;
		if (idHeaderReporte == null) {
			if (other.idHeaderReporte != null) {
				return false;
			}
		} else if (!idHeaderReporte.equals(other.idHeaderReporte)) {
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
		return "HeaderReporteDTO [idHeaderReporte=" + idHeaderReporte + "]";
	}

}