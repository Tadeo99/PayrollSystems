package pe.buildsoft.erp.core.domain.entidades.admision;

import java.io.Serializable;

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
 * La Class DetSede.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Wed Apr 21 12:29:29 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "DetSede", schema = ConfiguracionEntityManagerUtil.ESQUEMA_ADMISION)
@Getter
@Setter
public class DetSede extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id det sede. */
	@Id
	@Column(name = "idDetSede", length = 32)
	private String idDetSede;

	/** El sede. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idSede", referencedColumnName = "idSede")
	private Sede sede;

	/** El grado. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idGrado", referencedColumnName = "idGrado")
	private Grado grado;

	/** El nro vacante. */
	@Column(name = "nroVacante", precision = 18, scale = 0)
	private Long nroVacante;

	/**
	 * Instancia un nuevo det sede.
	 */
	public DetSede() {
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
		result = prime * result + ((idDetSede == null) ? 0 : idDetSede.hashCode());
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
		DetSede other = (DetSede) obj;
		if (idDetSede == null) {
			if (other.idDetSede != null) {
				return false;
			}
		} else if (!idDetSede.equals(other.idDetSede)) {
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
		return "DetSede [idDetSede=" + idDetSede + "]";
	}

}