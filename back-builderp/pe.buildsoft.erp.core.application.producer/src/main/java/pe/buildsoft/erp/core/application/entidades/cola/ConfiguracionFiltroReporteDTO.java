package pe.buildsoft.erp.core.application.entidades.cola;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class ConfiguracionFiltroReporteDTO.
 * <ul>
 * <li>Copyright 2015 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Thu Dec 21 12:16:30 COT 2017
 * @since BuildErp 1.0
 */
@Getter
@Setter
public class ConfiguracionFiltroReporteDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id codigo filtro. */
	private Long idCodigoFiltro;

	/** El id opcion menu. */
	private Long idOpcionMenu;

	/** El nombre filtro. */
	private String nombreFiltro;

	/** La id componente ui. */
	private String idComponenteUI;

	/** El tipo valor filtro. */
	private String tipoValorFiltro;

	/** El visible filtro. */
	private String visibleFiltro;

	/** El tipo dato filtro. */
	private String tipoDatoFiltro;

	/** El fecha actualizacion. */
	private OffsetDateTime fechaActualizacion;

	/** El codigo usuario. */
	private String codigoUsuario;

	/** El objecto atributo. */
	private String objectoAtributo;

	/** La objecto atributo descripcion. */
	private String objectoAtributoDescripcion;

	/** El formato tipo dato. */
	private String formatoTipoDato;

	/** El estado. */
	private String estado;

	/** La orden filtro. */
	private Long ordenFiltro;

	/** El configuracion filtro reporte valor configuracion filtro reporte list. */
	private List<ValorConfiguracionFiltroReporteDTO> configuracionFiltroReporteValorConfiguracionFiltroReporteList = new ArrayList<>();

	/** El configuracion filtro reporte configuracion filtro cola list. */
	private List<ConfiguracionFiltroColaDTO> configuracionFiltroReporteConfiguracionFiltroColaList = new ArrayList<>();

	/** El configuracion filtro reporte rango configuracion filtro cola list. */
	private List<ConfiguracionFiltroColaDTO> configuracionFiltroReporteRangoConfiguracionFiltroColaList = new ArrayList<>();

	/**
	 * Instancia un nuevo configuracion filtro reporteDTO.
	 */
	public ConfiguracionFiltroReporteDTO() {
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
		result = prime * result + ((idCodigoFiltro == null) ? 0 : idCodigoFiltro.hashCode());
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
		ConfiguracionFiltroReporteDTO other = (ConfiguracionFiltroReporteDTO) obj;
		if (idCodigoFiltro == null) {
			if (other.idCodigoFiltro != null) {
				return false;
			}
		} else if (!idCodigoFiltro.equals(other.idCodigoFiltro)) {
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
		return "ConfiguracionFiltroReporteDTO [idCodigoFiltro=" + idCodigoFiltro + "]";
	}
}