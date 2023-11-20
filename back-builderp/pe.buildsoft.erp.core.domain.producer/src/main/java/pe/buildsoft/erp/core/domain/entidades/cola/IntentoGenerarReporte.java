package pe.buildsoft.erp.core.domain.entidades.cola;

import java.io.Serializable;
import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class IntentoGenerarReporte.
 * <ul>
 * <li>Copyright 2014 MAPFRE -
 * MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Fri Aug 22 09:10:01 COT 2014
 * @since BuildErp 1.0
 */
@Entity
@Table(name = "IntentoGenerarReporte", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_COMMON)
@Getter
@Setter
public class IntentoGenerarReporte extends BaseEntidad implements Serializable {
 
	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** El id intento. */
	@Id
	@Column(name = "idIntento" , length = 39)
	private String idIntento;
	
	/** El solicitud reporte. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idSolicitudReporte", referencedColumnName = "idSolicitudReporte")
	private SolicitudReporte solicitudReporte;
	
	/** El fecha envio. */
	//@Temporal( TemporalType.TIMESTAMP)
	@Column(name = "fechaEnvio")
	private OffsetDateTime fechaEnvio;
	
	/** El numero intento. */
	@Column(name = "numeroIntento")
	private Long numeroIntento;
	
	/** El estado. */
	@Column(name = "estado" , length = 1)
	private String estado;
	
	/** El descripcion. */
	@Column(name = "descripcion")
	private String descripcion;
	
	/**
	 * Instancia un nuevo intento generar reporte.
	 */
	public IntentoGenerarReporte() {
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idIntento == null) ? 0 : idIntento.hashCode());
		return result;
	}

	/* (non-Javadoc)
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
		IntentoGenerarReporte other = (IntentoGenerarReporte) obj;
		if (idIntento == null) {
			if (other.idIntento != null) {
				return false;
			}
		} else if (!idIntento.equals(other.idIntento)) {
			return false;
		}
		return true;
	}
     
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "IntentoGenerarReporte [idIntento=" + idIntento + "]";
	}
	
}