package pe.buildsoft.erp.core.domain.entidades.planilla;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.CabeceraReporteVO;

/**
 * La Class DetallePlanilla.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 23:10:02 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "DetallePlanilla", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_RRHH_PLANILLA)
@Getter
@Setter
public class DetallePlanilla extends CabeceraReporteVO implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id detalle planilla. */
	@Id
	@Column(name = "idDetallePlanilla", length = 32)
	private String idDetallePlanilla;

	/** El planilla. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPlanilla", referencedColumnName = "idPlanilla")
	private Planilla planilla;

	/** El personal.
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPersonal", referencedColumnName = "idPersonal")
	private Personal personal; */
	@Column(name = "idPersonal", length = 32)
	private String idPersonal;

	/** El basico. */
	@Column(name = "totalIngreso", precision = 18, scale = 2)
	private BigDecimal totalIngreso;

	/** El neto pagar. */
	@Column(name = "totalDescuento", precision = 18, scale = 2)
	private BigDecimal totalDescuento;

	/** El neto pagar. */
	@Column(name = "totalAportaciones", precision = 18, scale = 2)
	private BigDecimal totalAportaciones;

	/** El neto pagar. */
	@Column(name = "netoPagar", precision = 18, scale = 2)
	private BigDecimal netoPagar;

	/** El detalle planilla detalle planilla concepto list. */
//	@OneToMany(mappedBy = "detallePlanilla", fetch = FetchType.LAZY)
	@Transient
	private List<DetallePlanillaConcepto> detallePlanillaDetallePlanillaConceptoList = new ArrayList<>();

	@Transient
	private List<DetallePlanilla> detallePlanillaMasiva = new ArrayList<>();
	/**
	 * Instancia un nuevo detalle planilla.
	 */
	public DetallePlanilla() {
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
		result = prime * result + ((idDetallePlanilla == null) ? 0 : idDetallePlanilla.hashCode());
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
		DetallePlanilla other = (DetallePlanilla) obj;
		if (idDetallePlanilla == null) {
			if (other.idDetallePlanilla != null) {
				return false;
			}
		} else if (!idDetallePlanilla.equals(other.idDetallePlanilla)) {
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
		return "DetallePlanilla [idDetallePlanilla=" + idDetallePlanilla + "]";
	}

}