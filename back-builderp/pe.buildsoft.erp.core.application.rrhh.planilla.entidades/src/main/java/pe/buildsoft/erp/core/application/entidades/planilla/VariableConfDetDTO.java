package pe.buildsoft.erp.core.application.entidades.planilla;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class VariableConfDetDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id Variable. */
	private String idVariableConfDet;

	/** El concepto pdt. */
	private VariableConfDTO variableConf;

	/** El variable. */
	private String variable;

	/** El formula. */
	private String formula;

	/** El descripcion. */
	private String descripcion;

	/** El orden. */
	private Long orden;

	/**
	 * Instancia un nuevo concepto regimen pensionario.
	 */
	public VariableConfDetDTO() {
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
		VariableConfDetDTO other = (VariableConfDetDTO) obj;
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