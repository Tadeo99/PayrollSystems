package pe.buildsoft.erp.core.domain.entidades.planilla;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * La Class DetallePersonalConcepto.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 23:10:03 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "DetallePersonalConcepto", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_RRHH_PLANILLA)
@Getter
@Setter
public class DetallePersonalConcepto extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id tipo planilla. */
	@Id
	@Column(name = "idDetallePersonalConcepto", length = 32)
	private String idDetallePersonalConcepto;

	/** El item by tipo moneda. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idConceptosTipoPlanilla", referencedColumnName = "idConceptosTipoPlanilla")
	private ConceptosTipoPlanilla conceptosTipoPlanilla;

	/** El item by tipo moneda. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPersonalConcepto", referencedColumnName = "idPersonalConcepto")
	private PersonalConcepto personalConcepto;

	@Column(name = "monto", precision = 18, scale = 2)
	private BigDecimal monto;

	@Transient
	private List<DetallePersonalConcepto> detallePersonalConceptoList = new ArrayList<>();
	
	/**
	 * Instancia un nuevo tipo planilla.
	 */
	public DetallePersonalConcepto() {
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
		DetallePersonalConcepto other = (DetallePersonalConcepto) obj;
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
		return "DetallePersonalConcepto [idDetallePersonalConcepto=" + idDetallePersonalConcepto + "]";
	}

}