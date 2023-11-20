package pe.buildsoft.erp.core.domain.entidades.planilla;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class ConceptoTrabajabador.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Sun May 02 01:34:10 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "ConceptoTrabajador", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_RRHH_PLANILLA)
@Getter
@Setter
public class ConceptoFijosTrabajador extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id concepto trabajador. */
	@Id
	@Column(name = "idConceptoTrabajador", length = 32)
	private String idConceptoTrabajador;

	/** El concepto pdt. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idConceptoPdt", referencedColumnName = "idConceptoPdt")
	private ConceptoPdt conceptoPdt;

	/** El personal. 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPersonal", referencedColumnName = "idPersonal")
	private Personal personal;*/
	@Column(name = "idPersonal", length = 32)
	private String idPersonal;

	/** El item by periodo mes. 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPeriodoMes", referencedColumnName = "idItem")
	private Item itemByPeriodoMes;*/
	@Column(name = "idPeriodoMes", precision = 18, scale = 0)
	private Long idItemByPeriodoMes;

	/** El descripcion. */
	@Column(name = "descripcion", precision = 18, scale = 0)
	private String descripcion;

	/** El porcentaje. */
	@Column(name = "porcentaje", precision = 18, scale = 2)
	private BigDecimal porcentaje;

	/** El monto. */
	@Column(name = "monto", precision = 18, scale = 2)
	private BigDecimal monto;

	/**
	 * Instancia un nuevo concepto trabajabador.
	 */
	public ConceptoFijosTrabajador() {
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
		result = prime * result + ((idConceptoTrabajador == null) ? 0 : idConceptoTrabajador.hashCode());
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
		ConceptoFijosTrabajador other = (ConceptoFijosTrabajador) obj;
		if (idConceptoTrabajador == null) {
			if (other.idConceptoTrabajador != null) {
				return false;
			}
		} else if (!idConceptoTrabajador.equals(other.idConceptoTrabajador)) {
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
		return "ConceptoTrabajador [idConceptoTrabajador=" + idConceptoTrabajador + "]";
	}

}