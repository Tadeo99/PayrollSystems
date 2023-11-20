package pe.buildsoft.erp.core.application.entidades.planilla;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.common.AnhioDTO;
import pe.buildsoft.erp.core.application.entidades.common.ItemDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class ConceptoRegimenPensionarioDTO.
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
public class ConceptoRegimenPensionarioDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id concepto regimen pensionario. */
	private String idConceptoRegimenPensionario;

	/** El item by regimen pensionario. */
	private Long idItemByRegimenPensionario;
	private ItemDTO itemByRegimenPensionario;

	/** El periodo laboral personal.
	private PeriodoLaboraPersonalDTO periodoLaboraPersonal;
	private String idPeriodoLaboraPersonal; */

	/** El item mes by devengue. */
	private Long idItemByMesByDevengue;
	private ItemDTO itemByMesByDevengue;

	/** El comision fija. */
	private BigDecimal comisionFija;

	/** El comision sobre flujo. */
	private BigDecimal comisionSobreFlujo;

	/** El comision sobre flujo mixto. */
	private BigDecimal comisionSobreFlujoMixto;

	/** El comision anual sobre saldo mixto. */
	private BigDecimal comisionAnualSobreSaldoMixto;

	/** El prima seguros. */
	private BigDecimal primaSeguros;

	/** El aporte obligatorio. */
	private BigDecimal aporteObligatorio;

	/** El remuneracion maxima asegurable. */
	private BigDecimal remuneracionMaximaAsegurable;

	private Long idAnhio;
	private AnhioDTO anhio;

	private boolean esEliminado = false;

	/**
	 * Instancia un nuevo concepto regimen pensionarioDTO.
	 */
	public ConceptoRegimenPensionarioDTO() {
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
		result = prime * result
				+ ((idConceptoRegimenPensionario == null) ? 0 : idConceptoRegimenPensionario.hashCode());
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
		ConceptoRegimenPensionarioDTO other = (ConceptoRegimenPensionarioDTO) obj;
		if (idConceptoRegimenPensionario == null) {
			if (other.idConceptoRegimenPensionario != null) {
				return false;
			}
		} else if (!idConceptoRegimenPensionario.equals(other.idConceptoRegimenPensionario)) {
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
		return "ConceptoRegimenPensionarioDTO [idConceptoRegimenPensionario=" + idConceptoRegimenPensionario + "]";
	}

}