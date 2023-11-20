package pe.buildsoft.erp.core.application.entidades.planilla;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.common.ItemDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class ConceptoTrabajabadorDTO.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 01:34:10 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Getter
@Setter
public class ConceptoFijosTrabajadorDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id concepto trabajador. */
	private String idConceptoTrabajador;

	/** El concepto pdt. */
	private ConceptoPdtDTO conceptoPdt;

	private String idPersonal;

	/** El item by periodo mes. */
	private Long idItemByPeriodoMes;
	private ItemDTO itemByPeriodoMes;

	/** El descripcion. */
	private String descripcion;

	/** El porcentaje. */
	private BigDecimal porcentaje;

	/** El monto. */
	private BigDecimal monto;

	/**
	 * Instancia un nuevo concepto trabajabadorDTO.
	 */
	public ConceptoFijosTrabajadorDTO() {
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
		result = prime * result + ((idConceptoTrabajador == null) ? 0 : idConceptoTrabajador.hashCode());
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
		ConceptoFijosTrabajadorDTO other = (ConceptoFijosTrabajadorDTO) obj;
		if (idConceptoTrabajador == null) {
			if (other.idConceptoTrabajador != null) {
				return false;
			}
		} else if (!idConceptoTrabajador.equals(other.idConceptoTrabajador)) {
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
		return "ConceptoTrabajadorDTO [idConceptoTrabajador=" + idConceptoTrabajador + "]";
	}

}