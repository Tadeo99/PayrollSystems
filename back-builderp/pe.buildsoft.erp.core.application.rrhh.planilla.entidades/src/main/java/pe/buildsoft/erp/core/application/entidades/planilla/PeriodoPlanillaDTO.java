package pe.buildsoft.erp.core.application.entidades.planilla;

import java.io.Serializable;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.common.AnhioDTO;
import pe.buildsoft.erp.core.application.entidades.common.ItemDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class PeriodoPlanillaDTO.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:47 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Getter
@Setter
public class PeriodoPlanillaDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id periodo planilla. */
	private String idPeriodoPlanilla;

	/** El descripcion. */
	private String descripcion;

	/** El fecha inico. */
	private OffsetDateTime fechaInico;

	/** El fecha fina. */
	private OffsetDateTime fechaFina;

	/** El item by periodo mes. */
	private Long idItemByPeriodoMes;
	private ItemDTO itemByPeriodoMes;

	/** El anhio. */
	private Long idAnhio;
	private AnhioDTO anhio;

	/** El dias netos. */
	private Long diasNetos;

	/** El estado. */
	private String estado;

	/**
	 * Instancia un nuevo periodo planillaDTO.
	 */
	public PeriodoPlanillaDTO() {
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
		result = prime * result + ((idPeriodoPlanilla == null) ? 0 : idPeriodoPlanilla.hashCode());
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
		PeriodoPlanillaDTO other = (PeriodoPlanillaDTO) obj;
		if (idPeriodoPlanilla == null) {
			if (other.idPeriodoPlanilla != null) {
				return false;
			}
		} else if (!idPeriodoPlanilla.equals(other.idPeriodoPlanilla)) {
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
		return "PeriodoPlanillaDTO [idPeriodoPlanilla=" + idPeriodoPlanilla + "]";
	}

}