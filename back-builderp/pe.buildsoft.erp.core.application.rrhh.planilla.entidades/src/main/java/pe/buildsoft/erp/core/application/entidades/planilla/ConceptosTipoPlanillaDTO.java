package pe.buildsoft.erp.core.application.entidades.planilla;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class ConceptosTipoPlanillaDTO.
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
public class ConceptosTipoPlanillaDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id conceptos tipo planilla. */
	private String idConceptosTipoPlanilla;

	/** El tipo planilla. */
	private TipoPlanillaDTO tipoPlanilla;

	/** El concepto pdt. */
	private ConceptoPdtDTO conceptoPdt;

	/** El descripcion. */
	private String descripcion;

	private String codigo;

	private Long orden;

	private String formula;

	private String persistente;

	private String showBoleta;

	private boolean esEliminado = false;

	private String estado;

	/**
	 * Instancia un nuevo conceptos tipo planillaDTO.
	 */
	public ConceptosTipoPlanillaDTO() {
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
		result = prime * result + ((idConceptosTipoPlanilla == null) ? 0 : idConceptosTipoPlanilla.hashCode());
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
		ConceptosTipoPlanillaDTO other = (ConceptosTipoPlanillaDTO) obj;
		if (idConceptosTipoPlanilla == null) {
			if (other.idConceptosTipoPlanilla != null) {
				return false;
			}
		} else if (!idConceptosTipoPlanilla.equals(other.idConceptosTipoPlanilla)) {
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
		return "ConceptosTipoPlanillaDTO [idConceptosTipoPlanilla=" + idConceptosTipoPlanilla + "]";
	}

}