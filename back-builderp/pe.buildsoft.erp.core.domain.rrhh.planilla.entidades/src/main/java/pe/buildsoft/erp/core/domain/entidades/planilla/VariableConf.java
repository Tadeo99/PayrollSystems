package pe.buildsoft.erp.core.domain.entidades.planilla;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
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
@Entity
@Table(name = "VariableConf", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_RRHH_PLANILLA)
@Getter
@Setter
public class VariableConf extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id Variable. */
	@Id
	@Column(name = "idVariableConf", length = 32)
	private String idVariableConf;

	/** El nombre. */
	@Column(name = "nombre", length = 100)
	private String nombre;

	/** El descripcion. */
	@Column(name = "descripcion", length = 300)
	private String descripcion;

	/** El estado. */
	@Column(name = "estado", length = 1)
	private String estado;

	@Column(name = "idTipoPlanilla", length = 32)
	private String idTipoPlanilla;

	/**
	 * Instancia un nuevo concepto regimen pensionario.
	 */
	public VariableConf() {
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
		VariableConf other = (VariableConf) obj;
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