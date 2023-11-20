package pe.buildsoft.erp.core.domain.entidades.planilla;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class InformaOtrosIngreso5ta.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "InformaOtrosIngreso5ta", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_RRHH_PLANILLA)
@Getter
@Setter
public class InformaOtrosIngreso5ta extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id informa otros ingreso5ta. */
	@Id
	@Column(name = "idInformaOtrosIngreso5ta", length = 32)
	private String idInformaOtrosIngreso5ta;

	/** El personal. */
	@Column(name = "idPersonal", length = 32)
	private String idPersonal;

	/**
	 * El empresa.
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * @JoinColumn(name = "idEmpresa", referencedColumnName = "idEmpresa") private
	 *                  Empresa empresa;
	 */
	@Column(name = "idEmpresa", precision = 18, scale = 0)
	private Long idEmpresa;

	/** El importe. */
	@Column(name = "importe", precision = 18, scale = 2)
	private BigDecimal importe;

	@Column(name = "idAnhio", precision = 18, scale = 0)
	private Long idAnhio;

	/**
	 * Instancia un nuevo informa otros ingreso5ta.
	 */
	public InformaOtrosIngreso5ta() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idInformaOtrosIngreso5ta == null) ? 0 : idInformaOtrosIngreso5ta.hashCode());
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
		InformaOtrosIngreso5ta other = (InformaOtrosIngreso5ta) obj;
		if (idInformaOtrosIngreso5ta == null) {
			if (other.idInformaOtrosIngreso5ta != null) {
				return false;
			}
		} else if (!idInformaOtrosIngreso5ta.equals(other.idInformaOtrosIngreso5ta)) {
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
		return "InformaOtrosIngreso5ta [idInformaOtrosIngreso5ta=" + idInformaOtrosIngreso5ta + "]";
	}

}