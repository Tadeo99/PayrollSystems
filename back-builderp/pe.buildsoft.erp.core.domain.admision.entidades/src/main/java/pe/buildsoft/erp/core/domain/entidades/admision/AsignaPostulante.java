package pe.buildsoft.erp.core.domain.entidades.admision;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class AsignaPostulante.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Wed Apr 21 12:29:29 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "AsignaPostulante", schema = ConfiguracionEntityManagerUtil.ESQUEMA_ADMISION)
@Getter
@Setter
public class AsignaPostulante extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id asigna postulante. */
	@Id
	@Column(name = "idAsignaPostulante", length = 32)
	private String idAsignaPostulante;

	/** El apoderado. */
	// @ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "idApoderado", referencedColumnName = "idApoderado")
	@Column(name = "idApoderado", length = 32)
	private String apoderado;

	/** El postulante. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPostulante", referencedColumnName = "idPostulante")
	private Postulante postulante;

	/** El sede. */
	@Column(name = "idSede", length = 32)
	private String sede;

	/** El grado. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idGrado", referencedColumnName = "idGrado")
	// @Column(name = "idGrado" , precision = 18 , scale = 0)
	private Grado grado;

	/** El anho. */
	@Column(name = "idAnhio", precision = 18, scale = 0)
	private Long anho;

	/** El periodo. */
	@Column(name = "idPeriodo", precision = 18, scale = 0)
	private Long periodo;

	/** El estado. */
	@Column(name = "estado", length = 5)
	private String estado;

	@Transient
	private String nroDoc;

	/**
	 * Instancia un nuevo asigna postulante.
	 */
	public AsignaPostulante() {
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
		result = prime * result + ((idAsignaPostulante == null) ? 0 : idAsignaPostulante.hashCode());
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
		AsignaPostulante other = (AsignaPostulante) obj;
		if (idAsignaPostulante == null) {
			if (other.idAsignaPostulante != null) {
				return false;
			}
		} else if (!idAsignaPostulante.equals(other.idAsignaPostulante)) {
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
		return "AsignaPostulante [idAsignaPostulante=" + idAsignaPostulante + "]";
	}

}