package pe.buildsoft.erp.core.domain.entidades.matricula;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class Periodo.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:55 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "Periodo", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_MATRICULA)
@Getter
@Setter
public class Periodo extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id periodo. */
	@Id
	@Column(name = "idPeriodo", precision = 18, scale = 0)
	private Long idPeriodo;

	/** El tipo. */
	@Column(name = "tipo", length = 1)
	private String tipo;

	/** El descripcion. */
	@Column(name = "descripcion", length = 100)
	private String descripcion;

	/** El abreviatura. */
	@Column(name = "abreviatura", length = 100)
	private String abreviatura;

	/** El estado. */
	@Column(name = "estado", length = 1)
	private String estado;

	/** El periodo unidad list. */
	@OneToMany(mappedBy = "periodo", fetch = FetchType.LAZY)
	private List<Unidad> periodoUnidadList = new ArrayList<>();

	/** El periodo calendario academico list. */
	@OneToMany(mappedBy = "periodo", fetch = FetchType.LAZY)
	private List<CalendarioAcademico> periodoCalendarioAcademicoList = new ArrayList<>();

	/** El periodo matricula list. */
	@OneToMany(mappedBy = "periodo", fetch = FetchType.LAZY)
	private List<Matricula> periodoMatriculaList = new ArrayList<>();

	/**
	 * Instancia un nuevo periodo.
	 */
	public Periodo() {
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
		result = prime * result + ((idPeriodo == null) ? 0 : idPeriodo.hashCode());
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
		Periodo other = (Periodo) obj;
		if (idPeriodo == null) {
			if (other.idPeriodo != null) {
				return false;
			}
		} else if (!idPeriodo.equals(other.idPeriodo)) {
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
		return "Periodo [idPeriodo=" + idPeriodo + "]";
	}

}