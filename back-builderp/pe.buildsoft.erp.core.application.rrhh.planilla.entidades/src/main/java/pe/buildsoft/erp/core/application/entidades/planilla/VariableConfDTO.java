package pe.buildsoft.erp.core.application.entidades.planilla;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class VariableConf.
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
public class VariableConfDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id Variable. */
	private String idVariableConf;

	/** El nombre. */
	private String nombre;

	/** El descripcion. */
	private String descripcion;

	/** El estado. */
	private String estado;
	
	private String idTipoPlanilla;
	

	/**
	 * Instancia un nuevo concepto regimen pensionario.
	 */
	public VariableConfDTO() {
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
		result = prime * result + ((idVariableConf == null) ? 0 : idVariableConf.hashCode());
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
		VariableConfDTO other = (VariableConfDTO) obj;
		if (idVariableConf == null) {
			if (other.idVariableConf != null) {
				return false;
			}
		} else if (!idVariableConf.equals(other.idVariableConf)) {
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
		return "Variable [idVariableConf=" + idVariableConf + "]";
	}

}