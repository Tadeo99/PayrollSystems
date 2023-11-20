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
 * La Class PreRequisito.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:54 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "PreRequisito", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_MATRICULA)
@Getter
@Setter
public class PreRequisito extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id pre requisito. */
	@Id
	@Column(name = "idPreRequisito", length = 13)
	private String idPreRequisito;

	/** El det malla curricular. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idDetMallaCurricular", referencedColumnName = "idDetMallaCurricular")
	private DetMallaCurricular detMallaCurricular;

	/** El det malla curricular requisito. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idRequisito", referencedColumnName = "idDetMallaCurricular")
	private DetMallaCurricular detMallaCurricularRequisito;

	/**
	 * Instancia un nuevo pre requisito.
	 */
	public PreRequisito() {
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
		result = prime * result + ((idPreRequisito == null) ? 0 : idPreRequisito.hashCode());
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
		PreRequisito other = (PreRequisito) obj;
		if (idPreRequisito == null) {
			if (other.idPreRequisito != null) {
				return false;
			}
		} else if (!idPreRequisito.equals(other.idPreRequisito)) {
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
		return "PreRequisito [idPreRequisito=" + idPreRequisito + "]";
	}

}