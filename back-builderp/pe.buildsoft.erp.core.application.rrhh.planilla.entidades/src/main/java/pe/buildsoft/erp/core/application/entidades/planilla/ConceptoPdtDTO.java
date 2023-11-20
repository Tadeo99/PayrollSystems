package pe.buildsoft.erp.core.application.entidades.planilla;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class ConceptoPdtDTO.
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
public class ConceptoPdtDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id concepto pdt. */
	private String idConceptoPdt;

	/** El descripcion. */
	private String descripcion;

	/** El conceptoPdtPadre. */
	private ConceptoPdtDTO conceptoPdtPadre;

	/** El tipo. */
	private String tipo;

	/** El estado. */
	private String estado;

	private String formula;

	private String codigo;

	private String abreviatura;

	private String visible;


	/**
	 * Instancia un nuevo concepto pdtDTO.
	 */
	public ConceptoPdtDTO() {
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
		result = prime * result + ((idConceptoPdt == null) ? 0 : idConceptoPdt.hashCode());
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
		ConceptoPdtDTO other = (ConceptoPdtDTO) obj;
		if (idConceptoPdt == null) {
			if (other.idConceptoPdt != null) {
				return false;
			}
		} else if (!idConceptoPdt.equals(other.idConceptoPdt)) {
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
		return "ConceptoPdtDTO [idConceptoPdt=" + idConceptoPdt + "]";
	}

}