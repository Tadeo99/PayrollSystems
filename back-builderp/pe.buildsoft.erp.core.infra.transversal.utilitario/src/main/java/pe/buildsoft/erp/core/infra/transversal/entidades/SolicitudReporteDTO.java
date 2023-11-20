package pe.buildsoft.erp.core.infra.transversal.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.type.TipoReporteGenerarType;

/**
 * La Class SolicitudReporteDTO.
 * <ul>
 * <li>Copyright 2014 MAPFRE -
 * MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Wed Jul 30 18:01:09 COT 2014
 * @since BuildErp 1.0
 */
/**
 * @author T13629
 *
 */
@Getter
@Setter
public class SolicitudReporteDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id solicitud reporte. */
	private String idSolicitudReporte;

	/** El usuario. */
	private String idUsuario;

	/** El opcion. */
	private Long idOpcion;

	/** El codigo solicitud. */
	private String codigoSolicitud;

	/** El formato reporte. */
	private String formatoReporte;

	/** El estado. */
	private String estado;

	/** El fecha generacion. */
	private OffsetDateTime fechaGeneracion;

	/** El fecha termino. */
	private OffsetDateTime fechaTermino;

	/** El intentos. */
	private Long intentos;

	// new campos
	private Long idCola;
	private Long codigoJuego;

	// Regla de negocio
	/** La accion ver reporte. */
	private boolean accionVerReporte;

	/** La accion eliminar. */
	private boolean accionEliminar;

	/** El solicitud reporte intento generar reporte list. */
	private List<IntentoGenerarReporteDTO> solicitudReporteIntentoGenerarReporteList = new ArrayList<>();

	/** La estado list. */
	private List<String> estadoList = new ArrayList<>();

	/** La criterio filtro. */
	private Object criterioFiltro;

	/** La pametros map. */
	private Map<String, Object> pametrosMap = new HashMap<>();

	/** La column widt max map. */
	private Map<Integer, Integer> columnWidtMaxMap = new HashMap<>();

	private Long codigoCola;

	private String userName;

	private String tipoOpcion;

	private String email;

	private String classDinamic;

	/** La id component map. */
	private Map<String, String> idComponentMap = new HashMap<>();

	private String KeyWebSockect;

	/**
	 * Instancia un nuevo solicitud reporte.
	 */
	public SolicitudReporteDTO() {
		formatoReporte = TipoReporteGenerarType.PDF.getKey();
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
		result = prime * result + ((idSolicitudReporte == null) ? 0 : idSolicitudReporte.hashCode());
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
		SolicitudReporteDTO other = (SolicitudReporteDTO) obj;
		if (idSolicitudReporte == null) {
			if (other.idSolicitudReporte != null) {
				return false;
			}
		} else if (!idSolicitudReporte.equals(other.idSolicitudReporte)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "SolicitudReporteDTO [idSolicitudReporte=" + idSolicitudReporte + ", usuario=" + idUsuario + ", opcion="
				+ idOpcion + ", codigoSolicitud=" + codigoSolicitud + ", formatoReporte=" + formatoReporte + ", estado="
				+ estado + ", fechaGeneracion=" + fechaGeneracion + ", fechaTermino=" + fechaTermino + ", intentos="
				+ intentos + ", idCola=" + idCola + ", codigoJuego=" + codigoJuego + ", accionVerReporte="
				+ accionVerReporte + ", accionEliminar=" + accionEliminar
				+ ", solicitudReporteIntentoGenerarReporteList=" + solicitudReporteIntentoGenerarReporteList
				+ ", estadoList=" + estadoList + ", criterioFiltro=" + criterioFiltro + ", pametrosMap=" + pametrosMap
				+ ", columnWidtMaxMap=" + columnWidtMaxMap + ", listaValorConfiguracionFiltroReporte=]";
	}
}