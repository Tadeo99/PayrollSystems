package pe.buildsoft.erp.core.domain.entidades.planilla;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class Planilla.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 23:10:02 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "Planilla", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_RRHH_PLANILLA)
@Getter
@Setter
public class Planilla extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id planilla. */
	@Id
	@Column(name = "idPlanilla", length = 32)
	private String idPlanilla;

	/** El perido planilla. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPeriodoPlanilla", referencedColumnName = "idPeriodoPlanilla")
	private PeriodoPlanilla periodoPlanilla;

	/** El nombre. */
	@Column(name = "nombre", length = 150)
	private String nombre;

	/** El tipo calculo. */
	@Column(name = "tipoCalculo", length = 50)
	private String tipoCalculo;

	/** El item by tipo trabajador. 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idTipoTrabajador", referencedColumnName = "idItem")
	private Item itemByTipoTrabajador;*/
	@Column(name = "idTipoTrabajador", precision = 18, scale = 0)
	private Long idItemByTipoTrabajador;

	/** El item by periodo mes.
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPeriodoMes", referencedColumnName = "idItem")
	private Item itemByPeriodoMes; */
	@Column(name = "idPeriodoMes", precision = 18, scale = 0)
	private Long idItemByPeriodoMes;

	/** El anhio. 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idAnhio", referencedColumnName = "idAnhio")
	private Anhio anhio;*/
	@Column(name = "idAnhio", precision = 18, scale = 0)
	private Long idAnhio;

	/** El tipo planilla. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idTipoPlanilla", referencedColumnName = "idTipoPlanilla")
	private TipoPlanilla tipoPlanilla;

	/** El fecha pago. */
	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaPago")
	private OffsetDateTime fechaPago;

	/** El fecha inicio. */
	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaInicio")
	private OffsetDateTime fechaInicio;

	/** El fcha fin. */
	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fchaFin")
	private OffsetDateTime fchaFin;

	/** El estado. */
	@Column(name = "estado", length = 1)
	private String estado;

	/** El planilla detalle planilla list. */
//	@OneToMany(mappedBy = "planilla", fetch = FetchType.LAZY)
	@Transient
	private List<DetallePlanilla> planillaDetallePlanillaList = new ArrayList<>();

	/**
	 * Instancia un nuevo planilla.
	 */
	public Planilla() {
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
		Planilla other = (Planilla) obj;
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
		return "Planilla [idPlanilla=" + idPlanilla + "]";
	}

}