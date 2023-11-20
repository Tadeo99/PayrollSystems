package pe.buildsoft.erp.core.domain.entidades.planilla;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

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
 * La Class Adelanto.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 23:10:02 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "Adelanto", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_RRHH_PLANILLA)
@Getter
@Setter
public class Adelanto extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id adelanto. */
	@Id
	@Column(name = "idAdelanto", length = 32)
	private String idAdelanto;

	/** El personal.
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPersonal", referencedColumnName = "idPersonal")
	private Personal personal; */
	@Column(name = "idPersonal", length = 32)
	private String idPersonal;

	/** El concepto p d t. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idConceptoPdt", referencedColumnName = "idConceptoPdt")
	private ConceptoPdt conceptoPdt;

	/** El monto. */
	@Column(name = "monto", precision = 18, scale = 2)
	private BigDecimal monto;

	/** El fecha adelanto. */
	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaAdelanto")
	private OffsetDateTime fechaAdelanto;

	/** El fecha pago. */
	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaPago")
	private OffsetDateTime fechaPago;

	/** El estado. */
	@Column(name = "estado", length = 1)
	private String estado;

	/**
	 * Instancia un nuevo adelanto.
	 */
	public Adelanto() {
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
		result = prime * result + ((idAdelanto == null) ? 0 : idAdelanto.hashCode());
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
		Adelanto other = (Adelanto) obj;
		if (idAdelanto == null) {
			if (other.idAdelanto != null) {
				return false;
			}
		} else if (!idAdelanto.equals(other.idAdelanto)) {
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
		return "Adelanto [idAdelanto=" + idAdelanto + "]";
	}

}