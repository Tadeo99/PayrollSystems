package pe.buildsoft.erp.core.application.entidades.planilla;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class DetallePersonalConceptoDTODTO.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Apr 22 23:10:03 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Getter
@Setter
public class DetallePersonalConceptoDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id tipo planilla. */
	private String idDetallePersonalConcepto;

	/** El item by categoria ocupacional. */
	private ConceptosTipoPlanillaDTO conceptosTipoPlanilla;

	private PersonalConceptoDTO personalConcepto;

	private BigDecimal monto;

	private List<DetallePersonalConceptoDTO> detallePersonalConceptoList = new ArrayList<>();

	/**
	 * Instancia un nuevo tipo planillaDTO.
	 */
	public DetallePersonalConceptoDTO() {
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
		result = prime * result + ((idDetallePersonalConcepto == null) ? 0 : idDetallePersonalConcepto.hashCode());
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
		DetallePersonalConceptoDTO other = (DetallePersonalConceptoDTO) obj;
		if (idDetallePersonalConcepto == null) {
			if (other.idDetallePersonalConcepto != null) {
				return false;
			}
		} else if (!idDetallePersonalConcepto.equals(other.idDetallePersonalConcepto)) {
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
		return "DetallePersonalConceptoDTO [idDetallePersonalConcepto=" + idDetallePersonalConcepto + "]";
	}

}