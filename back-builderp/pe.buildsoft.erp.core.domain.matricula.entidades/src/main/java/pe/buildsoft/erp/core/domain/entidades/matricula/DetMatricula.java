package pe.buildsoft.erp.core.domain.entidades.matricula;

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
 * La Class DetMatricula.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:55 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "DetMatricula", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_MATRICULA)
@Getter
@Setter
public class DetMatricula extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id det matricula. */
	@Id
	@Column(name = "idDetMatricula", length = 32)
	private String idDetMatricula;

	/** El matricula. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idMatricula", referencedColumnName = "idMatricula")
	private Matricula matricula;

	/** El det malla curricular. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idDetMallaCurricular", referencedColumnName = "idDetMallaCurricular")
	private DetMallaCurricular detMallaCurricular;

	/** El descripcion curso. */
	@Column(name = "descripcionCurso", length = 200)
	private String descripcionCurso;

	/** El codigo asignatura. */
	@Column(name = "codigoAsignatura", length = 10)
	private String codigoAsignatura;

	/** El estado. */
	@Column(name = "estado", length = 1)
	private String estado;

	/**
	 * Instancia un nuevo det matricula.
	 */
	public DetMatricula() {
		super();
	}

	public DetMatricula(String idDetMatricula) {
		super();
		this.idDetMatricula = idDetMatricula;
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
		result = prime * result + ((idDetMatricula == null) ? 0 : idDetMatricula.hashCode());
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
		DetMatricula other = (DetMatricula) obj;
		if (idDetMatricula == null) {
			if (other.idDetMatricula != null) {
				return false;
			}
		} else if (!idDetMatricula.equals(other.idDetMatricula)) {
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
		return "DetMatricula [idDetMatricula=" + idDetMatricula + "]";
	}

}