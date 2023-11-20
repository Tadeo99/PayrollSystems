package pe.buildsoft.erp.core.application.entidades.planilla;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.CabeceraReporteVO;

/**
 * La Class DetallePlanillaDTO.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Apr 22 23:10:02 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Getter
@Setter
public class DetallePlanillaDTO extends CabeceraReporteVO implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id detalle planilla. */
	private String idDetallePlanilla;

	/** El planilla. */
	private PlanillaDTO planilla;

	/** El personal. */
	private PersonalResponseVo personal;
	private String idPersonal;

	/** El basico. */
	private BigDecimal totalIngreso;

	private BigDecimal totalDescuento;

	/** El tota lAportaciones. */
	private BigDecimal totalAportaciones;

	/** El neto netoPagar. */
	private BigDecimal netoPagar;

	/** El detalle planilla detalle planilla concepto list. */
	private List<DetallePlanillaConceptoDTO> detallePlanillaDetallePlanillaConceptoList = new ArrayList<>();

	private List<DetallePlanillaDTO> detallePlanillaMasiva = new ArrayList<>();

	private String tipo;

	/**
	 * Instancia un nuevo detalle planillaDTO.
	 */
	public DetallePlanillaDTO() {
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
		DetallePlanillaDTO other = (DetallePlanillaDTO) obj;
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
		return "DetallePlanillaDTO [idDetallePlanilla=" + idDetallePlanilla + "]";
	}

}