package pe.buildsoft.erp.core.domain.entidades.planilla;

import java.io.Serializable;
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
 * La Class PersonalConcepto.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 23:10:03 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "PersonalConcepto", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_RRHH_PLANILLA)
@Getter
@Setter
public class PersonalConcepto extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id tipo planilla. */
	@Id
	@Column(name = "idPersonalConcepto", length = 32)
	private String idPersonalConcepto;

	/** El item by tipo moneda.
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPersonal", referencedColumnName = "idPersonal")
	private Personal personal; */
	@Column(name = "idPersonal", length = 32)
	private String idPersonal;

	/** El item by categoria trabajador. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idTipoPlanilla", referencedColumnName = "idTipoPlanilla")
	private TipoPlanilla tipoPlanilla;

	/** El item by categoria trabajador. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPeriodoPlanilla", referencedColumnName = "idPeriodoPlanilla")
	private PeriodoPlanilla periodoPlanilla;

	@Transient
	private List<DetallePersonalConcepto> detallePersonalConceptoList = new ArrayList<>();
	/**
	 * Instancia un nuevo tipo planilla.
	 */
	public PersonalConcepto() {
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
		PersonalConcepto other = (PersonalConcepto) obj;
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
		return "PersonalConcepto [idPersonalConcepto=" + idPersonalConcepto + "]";
	}

}