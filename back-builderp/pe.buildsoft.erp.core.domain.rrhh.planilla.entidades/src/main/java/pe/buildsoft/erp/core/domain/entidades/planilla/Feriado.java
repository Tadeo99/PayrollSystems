package pe.buildsoft.erp.core.domain.entidades.planilla;

import java.io.Serializable;
import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class Feriado.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 23:10:02 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "Feriado", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_RRHH_PLANILLA)
@Getter
@Setter
public class Feriado extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id eps. */
	@Id
	@Column(name = "idFeriado", length = 32)
	private String idFeriado;

	@Column(name = "fecha")
	private OffsetDateTime fecha;

	@Column(name = "idAnhio", precision = 18, scale = 0)
	private Long idAnhio;

	@Column(name = "idMes", precision = 18, scale = 0)
	private Long idMes;

	@Column(name = "fijo", length = 1)
	private String fijo;

	/**
	 * Instancia un nuevo concepto regimen pensionario.
	 */
	public Feriado() {
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
		result = prime * result + ((idFeriado == null) ? 0 : idFeriado.hashCode());
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
		Feriado other = (Feriado) obj;
		if (idFeriado == null) {
			if (other.idFeriado != null) {
				return false;
			}
		} else if (!idFeriado.equals(other.idFeriado)) {
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
		return "Feriado [idFeriado=" + idFeriado + "]";
	}

}