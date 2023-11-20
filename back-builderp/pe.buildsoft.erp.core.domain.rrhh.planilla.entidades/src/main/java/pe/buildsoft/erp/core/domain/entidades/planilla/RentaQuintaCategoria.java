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
 * La Class RentaQuintaCategoria.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 23:29:55 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "RentaQuintaCategoria", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_RRHH_PLANILLA)
@Getter
@Setter
public class RentaQuintaCategoria extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id renta quinta categoria. */
	@Id
	@Column(name = "idRentaQuintaCategoria", length = 32)
	private String idRentaQuintaCategoria;

	/** El tipo planilla. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idTipoPlanilla", referencedColumnName = "idTipoPlanilla")
	private TipoPlanilla tipoPlanilla;

	/** El periodo planilla. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPeriodoPlanilla", referencedColumnName = "idPeriodoPlanilla")
	private PeriodoPlanilla periodoPlanilla;

	/** El id renta quinta categoria det renta quinta categoria list. */
//	@OneToMany(mappedBy = "idRentaQuintaCategoria", fetch = FetchType.LAZY)
	@Transient
	private List<DetRentaQuintaCategoria> idRentaQuintaCategoriaDetRentaQuintaCategoriaList = new ArrayList<>();

	/**
	 * Instancia un nuevo renta quinta categoria.
	 */
	public RentaQuintaCategoria() {
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
		result = prime * result + ((idRentaQuintaCategoria == null) ? 0 : idRentaQuintaCategoria.hashCode());
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
		RentaQuintaCategoria other = (RentaQuintaCategoria) obj;
		if (idRentaQuintaCategoria == null) {
			if (other.idRentaQuintaCategoria != null) {
				return false;
			}
		} else if (!idRentaQuintaCategoria.equals(other.idRentaQuintaCategoria)) {
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
		return "RentaQuintaCategoria [idRentaQuintaCategoria=" + idRentaQuintaCategoria + "]";
	}

}