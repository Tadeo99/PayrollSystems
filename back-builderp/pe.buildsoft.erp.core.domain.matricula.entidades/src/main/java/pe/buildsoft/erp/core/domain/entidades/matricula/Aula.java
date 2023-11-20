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
 * La Class Aula.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:55 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "Aula", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_MATRICULA)
@Getter
@Setter
public class Aula extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id aula. */
	@Id
	@Column(name = "idAula", precision = 18, scale = 0)
	private Long idAula;

	/** El pabellon. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPabellon", referencedColumnName = "idPabellon")
	private Pabellon pabellon;

	/** El descripcion. */
	@Column(name = "descripcion", length = 100)
	private String descripcion;

	/** El abreviatura. */
	@Column(name = "abreviatura", length = 50)
	private String abreviatura;

	/** El estado. */
	@Column(name = "estado", length = 1)
	private String estado;

	/** El aula carga academica list. */
	@OneToMany(mappedBy = "aula", fetch = FetchType.LAZY)
	private List<CargaAcademica> aulaCargaAcademicaList = new ArrayList<>();

	/**
	 * Instancia un nuevo aula.
	 */
	public Aula() {
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
		result = prime * result + ((idAula == null) ? 0 : idAula.hashCode());
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
		Aula other = (Aula) obj;
		if (idAula == null) {
			if (other.idAula != null) {
				return false;
			}
		} else if (!idAula.equals(other.idAula)) {
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
		return "Aula [idAula=" + idAula + "]";
	}

}