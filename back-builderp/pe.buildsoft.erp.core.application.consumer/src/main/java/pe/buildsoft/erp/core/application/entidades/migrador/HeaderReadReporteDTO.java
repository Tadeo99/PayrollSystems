package pe.buildsoft.erp.core.application.entidades.migrador;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class HeaderReadReporteDTO.
 * <ul>
 * <li>Copyright 2018 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Wed Aug 07 14:48:19 COT 2019
 * @since BuildErp 1.0
 */
@Getter
@Setter
public class HeaderReadReporteDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id header read reporte. */
	private String idHeaderReadReporte;

	/** El codigo reporte. */
	private String codigoReporte;

	/** El nombre tabla. */
	private String nombreTabla;

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

	/** El tipo campo. */
	private String tipoCampo;

	/** El obligatorio. */
	private String obligatorio;

	/** El longitud. */
	private Long longitud;

	/** El fila data. */
	private Long filaData;

	/** El posicion campo inicial. */
	private Long posicionCampoInicial;

	/** El posicion campo final. */
	private Long posicionCampoFinal;

	/** El valor defecto campo. */
	private String valorDefectoCampo;

	/** El orden. */
	private Long orden;

	/** El es persistente. */
	private String esPersistente;

	/** El es campoLeidoTrama. */
	private String campoLeidoTrama;

	/** El regla. */
	private String regla;

	/** El estado. */
	private String estado;

	/**
	 * Instancia un nuevo header read reporteDTO.
	 */
	public HeaderReadReporteDTO() {
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
		result = prime * result + ((idHeaderReadReporte == null) ? 0 : idHeaderReadReporte.hashCode());
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
		HeaderReadReporteDTO other = (HeaderReadReporteDTO) obj;
		if (idHeaderReadReporte == null) {
			if (other.idHeaderReadReporte != null) {
				return false;
			}
		} else if (!idHeaderReadReporte.equals(other.idHeaderReadReporte)) {
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
		return "HeaderReadReporteDTO [idHeaderReadReporte=" + idHeaderReadReporte + "]";
	}

}