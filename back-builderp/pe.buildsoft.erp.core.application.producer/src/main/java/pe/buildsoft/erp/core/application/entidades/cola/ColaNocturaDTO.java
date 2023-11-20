package pe.buildsoft.erp.core.application.entidades.cola;

import java.io.Serializable;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class ColaNocturaDTO.
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
public class ColaNocturaDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id cola nocturna. */
	private String idColaNocturna;

	/** El objecto json. */
	private String objectoJson;

	/** El fecha actualizacion. */
	private OffsetDateTime fechaActualizacion;

	/** El codigo usuario. */
	private String codigoUsuario;

	/** El estado proceso. */
	private String estadoProceso;

	/** El estado. */
	private String estado;

	/** El id solicitud reporte. */
	private Long idSolicitudReporte;

	/**
	 * Instancia un nuevo cola nocturaDTO.
	 */
	public ColaNocturaDTO() {
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
		result = prime * result + ((idColaNocturna == null) ? 0 : idColaNocturna.hashCode());
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
		ColaNocturaDTO other = (ColaNocturaDTO) obj;
		if (idColaNocturna == null) {
			if (other.idColaNocturna != null) {
				return false;
			}
		} else if (!idColaNocturna.equals(other.idColaNocturna)) {
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
		return "ColaNocturaDTO [idColaNocturna=" + idColaNocturna + "]";
	}

}