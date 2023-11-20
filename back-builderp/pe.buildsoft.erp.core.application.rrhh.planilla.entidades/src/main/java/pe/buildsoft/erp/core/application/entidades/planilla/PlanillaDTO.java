package pe.buildsoft.erp.core.application.entidades.planilla;

import java.io.Serializable;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.common.AnhioDTO;
import pe.buildsoft.erp.core.application.entidades.common.ItemDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.CabeceraReporteVO;

/**
 * La Class PlanillaDTO.
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
public class PlanillaDTO extends CabeceraReporteVO implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id planilla. */
	private String idPlanilla;

	/** El perido planilla. */
	private PeriodoPlanillaDTO periodoPlanilla;

	/** El nombre. */
	private String nombre;

	/** El tipo calculo. */
	private String tipoCalculo;

	/** El item by tipo trabajador. */
	private Long idItemByTipoTrabajador;
	private ItemDTO itemByTipoTrabajador;

	/** El item by periodo mes. */
	private Long idItemByPeriodoMes;
	private ItemDTO itemByPeriodoMes;

	/** El anhio. */
	private Long idAnhio;
	private AnhioDTO anhio;

	/** El tipo planilla. */
	private TipoPlanillaDTO tipoPlanilla;

	/** El fecha pago. */
	private OffsetDateTime fechaPago;

	/** El fecha inicio. */
	private OffsetDateTime fechaInicio;

	/** El fcha fin. */
	private OffsetDateTime fchaFin;

	/** El estado. */
	private String estado;


	/**
	 * Instancia un nuevo planillaDTO.
	 */
	public PlanillaDTO() {
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
		result = prime * result + ((idPlanilla == null) ? 0 : idPlanilla.hashCode());
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
		PlanillaDTO other = (PlanillaDTO) obj;
		if (idPlanilla == null) {
			if (other.idPlanilla != null) {
				return false;
			}
		} else if (!idPlanilla.equals(other.idPlanilla)) {
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
		return "PlanillaDTO [idPlanilla=" + idPlanilla + "]";
	}

}