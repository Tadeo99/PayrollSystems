package pe.buildsoft.erp.core.application.entidades.planilla;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class PersonalConceptoDTODTO.
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
public class PersonalConceptoDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id tipo planilla. */
	private String idPersonalConcepto;

	/** El item by categoria ocupacional. */
	private PersonalResponseVo personal;
	private String idPersonal;

	private TipoPlanillaDTO tipoPlanilla;

	private PeriodoPlanillaDTO periodoPlanilla;

	/** El tipo planilla conceptos tipo planilla list. */
	private List<DetallePersonalConceptoDTO> detallePersonalConceptoList = new ArrayList<>();

	/**
	 * Instancia un nuevo tipo planillaDTO.
	 */
	public PersonalConceptoDTO() {
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
		result = prime * result + ((idPersonalConcepto == null) ? 0 : idPersonalConcepto.hashCode());
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
		PersonalConceptoDTO other = (PersonalConceptoDTO) obj;
		if (idPersonalConcepto == null) {
			if (other.idPersonalConcepto != null) {
				return false;
			}
		} else if (!idPersonalConcepto.equals(other.idPersonalConcepto)) {
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
		return "PersonalConceptoDTODTO [idPersonalConcepto=" + idPersonalConcepto + "]";
	}

}