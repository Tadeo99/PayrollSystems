package pe.buildsoft.erp.core.domain.entidades.matricula;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class Unidad.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:55 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "Unidad", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_MATRICULA)
@Getter
@Setter
public class Unidad extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id unidad. */
	@Id
	@Column(name = "idUnidad", precision = 18, scale = 0)
	private Long idUnidad;

	/** El periodo. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPeriodo", referencedColumnName = "idPeriodo")
	private Periodo periodo;

	/** El descripcion. */
	@Column(name = "descripcion", length = 100)
	private String descripcion;

	/** El abreviatura. */
	@Column(name = "abreviatura", length = 100)
	private String abreviatura;

	/** El estado. */
	@Column(name = "estado", length = 1)
	private String estado;

	/** El unidad calendario academico list. */
	@OneToMany(mappedBy = "unidad", fetch = FetchType.LAZY)
	private List<CalendarioAcademico> unidadCalendarioAcademicoList = new ArrayList<>();

	/**
	 * Instancia un nuevo unidad.
	 */
	public Unidad() {
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
		result = prime * result + ((idUnidad == null) ? 0 : idUnidad.hashCode());
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
		Unidad other = (Unidad) obj;
		if (idUnidad == null) {
			if (other.idUnidad != null) {
				return false;
			}
		} else if (!idUnidad.equals(other.idUnidad)) {
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
		return "Unidad [idUnidad=" + idUnidad + "]";
	}

}