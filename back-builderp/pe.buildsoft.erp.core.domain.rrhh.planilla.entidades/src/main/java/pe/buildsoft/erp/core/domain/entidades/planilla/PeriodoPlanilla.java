package pe.buildsoft.erp.core.domain.entidades.planilla;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class PeriodoPlanilla.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Sun May 02 02:32:47 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "PeriodoPlanilla", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_RRHH_PLANILLA)
@Getter
@Setter
public class PeriodoPlanilla extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id periodo planilla. */
	@Id
	@Column(name = "idPeriodoPlanilla", length = 32)
	private String idPeriodoPlanilla;

	/** El descripcion. */
	@Column(name = "descripcion", length = 150)
	private String descripcion;

	/** El fecha inico. */
	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaInico")
	private OffsetDateTime fechaInico;

	/** El fecha fina. */
	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaFina")
	private OffsetDateTime fechaFina;

	/** El item by periodo mes. 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPeriodoMes", referencedColumnName = "idItem")
	private Item itemByPeriodoMes;*/
	@Column(name = "idPeriodoMes", precision = 18, scale = 0)
	private Long idItemByPeriodoMes;

	/** El anhio. 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idAnhio", referencedColumnName = "idAnhio")
	private Anhio anhio;*/
	@Column(name = "idAnhio", precision = 18, scale = 0)
	private Long idAnhio;

	/** El dias netos. */
	@Column(name = "diasNetos", precision = 18, scale = 0)
	private Long diasNetos;

	/** El estado. */
	@Column(name = "estado", length = 1)
	private String estado;

	/** El periodo planilla planilla list. */
//	@OneToMany(mappedBy = "periodoPlanilla", fetch = FetchType.LAZY)
	@Transient
	private List<Planilla> periodoPlanillaPlanillaList = new ArrayList<>();

	/** El periodo planilla renta quinta categoria list. */
//	@OneToMany(mappedBy = "periodoPlanilla", fetch = FetchType.LAZY)
	@Transient
	private List<RentaQuintaCategoria> periodoPlanillaRentaQuintaCategoriaList = new ArrayList<>();

	/**
	 * Instancia un nuevo periodo planilla.
	 */
	public PeriodoPlanilla() {
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
		PeriodoPlanilla other = (PeriodoPlanilla) obj;
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
		return "PeriodoPlanilla [idPeriodoPlanilla=" + idPeriodoPlanilla + "]";
	}

}