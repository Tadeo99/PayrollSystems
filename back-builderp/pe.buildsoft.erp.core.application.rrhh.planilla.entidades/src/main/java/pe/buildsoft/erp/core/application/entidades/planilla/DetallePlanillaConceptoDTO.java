package pe.buildsoft.erp.core.application.entidades.planilla;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class DetallePlanillaConceptoDTO.
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
public class DetallePlanillaConceptoDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id detalle planilla concepto. */
	private String idDetallePlanillaConcepto;

	/** El detalle planilla. */
	private DetallePlanillaDTO detallePlanilla;

	/**
	 * El concepto trabajador. private ConceptoPdtDTO conceptoPdt;
	 */
	/** El concepto trabajador. */
	private ConceptosTipoPlanillaDTO concepto;

	/** El conceptos tipo planilla. */
	private BigDecimal monto;

	/** El id detalle planilla concepto detalle planilla concepto list. */
	private List<DetallePlanillaConceptoDTO> detallePlanillaDetallePlanillaConceptoList = new ArrayList<>();

	/**
	 * Instancia un nuevo detalle planilla conceptoDTO.
	 */
	public DetallePlanillaConceptoDTO() {
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
		result = prime * result + ((idDetallePlanillaConcepto == null) ? 0 : idDetallePlanillaConcepto.hashCode());
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
		DetallePlanillaConceptoDTO other = (DetallePlanillaConceptoDTO) obj;
		if (idDetallePlanillaConcepto == null) {
			if (other.idDetallePlanillaConcepto != null) {
				return false;
			}
		} else if (!idDetallePlanillaConcepto.equals(other.idDetallePlanillaConcepto)) {
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
		return "DetallePlanillaConceptoDTO [idDetallePlanillaConcepto=" + idDetallePlanillaConcepto + "]";
	}

}