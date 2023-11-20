package pe.buildsoft.erp.core.domain.entidades.planilla;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class EPSConf.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 23:10:02 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "EPSConf", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_RRHH_PLANILLA)
@Getter
@Setter
public class EPSConf extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id eps. */
	@Id
	@Column(name = "idEpsConf", length = 32)
	private String idEPSConf;

	/** El item by eps. */
	@Column(name = "idItemEps", precision = 18, scale = 0)
	private Long idItemByEps;

	@Column(name = "planBase", precision = 18, scale = 2)
	private BigDecimal planBase;

	@Column(name = "planAdicional1", precision = 18, scale = 2)
	private BigDecimal planAdicional1;

	@Column(name = "planAdicional2", precision = 18, scale = 2)
	private BigDecimal planAdicional2;

	@Column(name = "planAdicional3", precision = 18, scale = 2)
	private BigDecimal planAdicional3;

	@Column(name = "planAdicional4", precision = 18, scale = 2)
	private BigDecimal planAdicional4;

	/**
	 * Instancia un nuevo concepto regimen pensionario.
	 */
	public EPSConf() {
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
		result = prime * result + ((idEPSConf == null) ? 0 : idEPSConf.hashCode());
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
		EPSConf other = (EPSConf) obj;
		if (idEPSConf == null) {
			if (other.idEPSConf != null) {
				return false;
			}
		} else if (!idEPSConf.equals(other.idEPSConf)) {
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
		return "EPS [idEPSConf=" + idEPSConf + "]";
	}

}