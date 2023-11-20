package pe.buildsoft.erp.core.application.entidades.cola;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class ConfiguracionFiltroColaDTO.
 * <ul>
 * <li>Copyright 2015 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Thu Dec 21 12:16:30 COT 2017
 * @since BuildErp 1.0
 */
@Setter
@Getter
public class ConfiguracionFiltroColaDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id juego filtro. */
	private String idJuegoFiltro;

	/** El cola. */
	private ColaDTO cola;

	/** El configuracion filtro reporte. */
	private ConfiguracionFiltroReporteDTO configuracionFiltroReporte;

	/** El configuracion filtro reporte rango. */
	private ConfiguracionFiltroReporteDTO configuracionFiltroReporteRango;

	/** El marca rango. */
	private String marcaRango;

	/** El valor juego filtro. */
	private String valorJuegoFiltro;

	/** El valor rango desde. */
	private String valorRangoDesde;

	/** El valor rango hasta. */
	private String valorRangoHasta;

	/** El fecha actualizacion. */
	private OffsetDateTime fechaActualizacion;

	/** El codigo usuario. */
	private String codigoUsuario;

	/** El estado. */
	private String estado;

	private Long codigoJuego;

	// Trasient
	List<Long> listaIdConfiguracionFiltroReporte = new ArrayList<>();

	/**
	 * Instancia un nuevo configuracion filtro colaDTO.
	 */
	public ConfiguracionFiltroColaDTO() {
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
		result = prime * result + ((idJuegoFiltro == null) ? 0 : idJuegoFiltro.hashCode());
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
		ConfiguracionFiltroColaDTO other = (ConfiguracionFiltroColaDTO) obj;
		if (idJuegoFiltro == null) {
			if (other.idJuegoFiltro != null) {
				return false;
			}
		} else if (!idJuegoFiltro.equals(other.idJuegoFiltro)) {
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
		return "ConfiguracionFiltroColaDTO [idJuegoFiltro=" + idJuegoFiltro + "]";
	}

}