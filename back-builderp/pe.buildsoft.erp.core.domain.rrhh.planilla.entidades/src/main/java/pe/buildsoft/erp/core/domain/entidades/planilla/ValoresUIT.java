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
 * La Class ValoresUIT.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 23:10:02 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "ValoresUIT", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_RRHH_PLANILLA)
@Getter
@Setter
public class ValoresUIT extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id uit. */
	@Id
	@Column(name = "idUit", length = 32)
	private String idUit;

	/** El anhio. 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idAnhio", referencedColumnName = "idAnhio")
	private Anhio anhio;*/
	@Column(name = "idAnhio", precision = 18, scale = 0)
	private Long idAnhio;

	/** El valor. */
	@Column(name = "valor", precision = 18, scale = 2)
	private BigDecimal valor;

	/** El base legal. */
	@Column(name = "baseLegal", length = 50)
	private String baseLegal;

	/** El observaciones. */
	@Column(name = "observaciones", length = 200)
	private String observaciones;

	/**
	 * Instancia un nuevo valores u i t.
	 */
	public ValoresUIT() {
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
		result = prime * result + ((idUit == null) ? 0 : idUit.hashCode());
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
		ValoresUIT other = (ValoresUIT) obj;
		if (idUit == null) {
			if (other.idUit != null) {
				return false;
			}
		} else if (!idUit.equals(other.idUit)) {
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
		return "ValoresUIT [idUit=" + idUit + "]";
	}

}