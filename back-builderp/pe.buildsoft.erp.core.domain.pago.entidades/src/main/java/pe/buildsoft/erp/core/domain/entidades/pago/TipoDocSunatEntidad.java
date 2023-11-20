package pe.buildsoft.erp.core.domain.entidades.pago;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class TipoDocSunatEntidad.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:56 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "TipoDocSunatEntidad", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_PAGO)
@Getter
@Setter
public class TipoDocSunatEntidad extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id tipo doc sunat entidad. */
	@Id
	@Column(name = "idTipoDocSunatEntidad", length = 32)
	private String idTipoDocSunatEntidad;

	/** El item by tipo doc sunat.
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idTipoDocSunat", referencedColumnName = "idItem")
	private Item itemByTipoDocSunat; */
	@Column(name = "idTipoDocSunat" , precision = 18 , scale = 0)
    private Long idItemByTipoDocSunat;

	/** El entidad. */
	// @ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "idEntidad", referencedColumnName = "idEntidad")
	@Column(name = "idEntidad", length = 32)
	private String idEntidad;

	/** El correla. */
	@Column(name = "correla", length = 50)
	private String correla;

	/** El correla. */
	@Column(name = "serie", length = 4)
	private String serie;

	/**
	 * Instancia un nuevo tipo doc sunat entidad.
	 */
	public TipoDocSunatEntidad() {
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
		result = prime * result + ((idTipoDocSunatEntidad == null) ? 0 : idTipoDocSunatEntidad.hashCode());
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
		TipoDocSunatEntidad other = (TipoDocSunatEntidad) obj;
		if (idTipoDocSunatEntidad == null) {
			if (other.idTipoDocSunatEntidad != null) {
				return false;
			}
		} else if (!idTipoDocSunatEntidad.equals(other.idTipoDocSunatEntidad)) {
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
		return "TipoDocSunatEntidad [idTipoDocSunatEntidad=" + idTipoDocSunatEntidad + "]";
	}

}