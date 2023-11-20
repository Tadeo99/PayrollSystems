package pe.buildsoft.erp.core.domain.entidades.cola;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class SolicitudReporte.
 * <ul>
 * <li>Copyright 2014 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Fri Aug 22 08:40:42 COT 2014
 * @since BuildErp 1.0
 */
@Entity
@Table(name = "SolicitudReporte", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_COMMON)
@Getter
@Setter
public class SolicitudReporte extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id solicitud reporte. */
	@Id
	@Column(name = "idSolicitudReporte", length = 32)
	private String idSolicitudReporte;

	/** El usuario. */
	/*
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * 
	 * @JoinColumn(name = "N_ID_USUARIO", referencedColumnName = "N_ID_USUARIO")
	 */
	@Column(name = "idUsuario", length = 32)
	private String idUsuario;

	/** El opcion. */
	/*
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * 
	 * @JoinColumn(name = "N_ID_OPCION", referencedColumnName = "N_ID_OPCION")
	 */
	@Column(name = "idMenu", precision = 18, scale = 0)
	private Long idOpcion;

	/** El codigo solicitud. */
	@Column(name = "codigoSolicitud", length = 36)
	private String codigoSolicitud;

	/** El formato reporte. */
	@Column(name = "formatoReporte", length = 5)
	private String formatoReporte;

	/** El estado. */
	@Column(name = "estado", length = 1)
	private String estado;

	/** El fecha generacion. */
	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaGeneracion")
	private OffsetDateTime fechaGeneracion;

	/** El fecha termino. */
	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaTermino")
	private OffsetDateTime fechaTermino;

	/** El intentos. */
	@Column(name = "intentos")
	private Long intentos;

	@Column(name = "idCola", precision = 18, scale = 0)
	private Long idCola;

	@Column(name = "codigoJuego", precision = 18, scale = 0)
	private Long codigoJuego;

	/** El solicitud reporte intento generar reporte list. */
	@OneToMany(mappedBy = "solicitudReporte", fetch = FetchType.LAZY)
	private List<IntentoGenerarReporte> solicitudReporteIntentoGenerarReporteList = new ArrayList<IntentoGenerarReporte>();

	@Transient
	private String userName;
	/**
	 * Instancia un nuevo solicitud reporte.
	 */
	public SolicitudReporte() {
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
		SolicitudReporte other = (SolicitudReporte) obj;
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
	@Override
	public String toString() {
		return "SolicitudReporte [idSolicitudReporte=" + idSolicitudReporte + "]";
	}

}