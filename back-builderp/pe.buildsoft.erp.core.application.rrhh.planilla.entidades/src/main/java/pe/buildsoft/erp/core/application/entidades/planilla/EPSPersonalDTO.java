package pe.buildsoft.erp.core.application.entidades.planilla;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class EPSPersonalDTO.
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
public class EPSPersonalDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id eps. */
	private String idEPSPersonal;

	/**
	 * El personal.
	 */
	private String idPersonal;
	private PersonalResponseVo personal;

	/** El item by eps. */
	private Long idItemByEps;

	/**
	 * El item by mes.
	 */
	private Long idItemByMes;

	/**
	 * El anhio.
	 * 
	 */
	private Long idAnhio;

	private BigDecimal planMonto;

	private BigDecimal creditoPct;

	private BigDecimal sueldoPct;

	private BigDecimal essaludPct;

	private Integer afiliados;

	private BigDecimal descontar;

	private BigDecimal descontarTrabajador;

	/**
	 * Instancia un nuevo concepto regimen pensionario.
	 */
	public EPSPersonalDTO() {
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
		EPSPersonalDTO other = (EPSPersonalDTO) obj;
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