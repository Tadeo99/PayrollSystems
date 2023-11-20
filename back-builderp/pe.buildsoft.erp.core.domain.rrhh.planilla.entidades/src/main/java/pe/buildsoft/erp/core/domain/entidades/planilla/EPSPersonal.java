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
//YA NO VA
/**
 * La Class EPSPersonal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 23:10:02 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "EPSPersonal", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_RRHH_PLANILLA)
@Getter
@Setter
public class EPSPersonal extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id eps. */
	@Id
	@Column(name = "idEpsPersonal", length = 32)
	private String idEPSPersonal;

	/**
	 * El personal.
	 */
	@Column(name = "idPersonal", length = 32)
	private String idPersonal;

	/** El item by eps. */
	@Column(name = "idItemEps", precision = 18, scale = 0)
	private Long idItemByEps;

	/**
	 * El item by mes.
	 */
	@Column(name = "idMes", precision = 18, scale = 0)
	private Long idItemByMes;

	/**
	 * El anhio.
	 * 
	 */
	@Column(name = "idAnhio", precision = 18, scale = 0)
	private Long idAnhio;

	@Column(name = "planMonto", precision = 18, scale = 2)
	private BigDecimal planMonto;

	@Column(name = "creditoPct", precision = 18, scale = 2)
	private BigDecimal creditoPct;

	@Column(name = "sueldoPct", precision = 18, scale = 2)
	private BigDecimal sueldoPct;

	@Column(name = "essaludPct", precision = 18, scale = 2)
	private BigDecimal essaludPct;

	@Column(name = "afiliados", precision = 2, scale = 0)
	private Integer afiliados;

	@Column(name = "descontar", precision = 18, scale = 2)
	private BigDecimal descontar;

	@Column(name = "descontarTrabajador", precision = 18, scale = 2)
	private BigDecimal descontarTrabajador;

	/**
	 * Instancia un nuevo concepto regimen pensionario.
	 */
	public EPSPersonal() {
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
		result = prime * result + ((idEPSPersonal == null) ? 0 : idEPSPersonal.hashCode());
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
		EPSPersonal other = (EPSPersonal) obj;
		if (idEPSPersonal == null) {
			if (other.idEPSPersonal != null) {
				return false;
			}
		} else if (!idEPSPersonal.equals(other.idEPSPersonal)) {
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
		return "EPS [idEPS=" + idEPSPersonal + "]";
	}

}