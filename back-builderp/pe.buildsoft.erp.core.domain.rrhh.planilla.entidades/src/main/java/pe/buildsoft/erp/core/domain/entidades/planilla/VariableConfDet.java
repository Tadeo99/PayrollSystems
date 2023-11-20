package pe.buildsoft.erp.core.domain.entidades.planilla;

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
 * La Class VariableConf.java.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 23:10:02 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "VariableConfDet", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_RRHH_PLANILLA)
@Getter
@Setter
public class VariableConfDet extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id Variable. */
	@Id
	@Column(name = "idVariableConfDet", length = 32)
	private String idVariableConfDet;

	/** El concepto pdt. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idvariableconf", referencedColumnName = "idvariableconf")
	private VariableConf variableConf;

	/** El variable. */
	@Column(name = "variable", length = 100)
	private String variable;

	/** El formula. */
	@Column(name = "formula")
	private String formula;

	/** El descripcion. */
	@Column(name = "descripcion", length = 300)
	private String descripcion;

	/** El orden. */
	@Column(name = "orden", precision = 18, scale = 0)
	private Long orden;

	/**
	 * Instancia un nuevo concepto regimen pensionario.
	 */
	public VariableConfDet() {
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
		result = prime * result + ((idVariableConfDet == null) ? 0 : idVariableConfDet.hashCode());
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
		VariableConfDet other = (VariableConfDet) obj;
		if (idVariableConfDet == null) {
			if (other.idVariableConfDet != null) {
				return false;
			}
		} else if (!idVariableConfDet.equals(other.idVariableConfDet)) {
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
		return "VariableDet [idVariableConfDet=" + idVariableConfDet + "]";
	}

}