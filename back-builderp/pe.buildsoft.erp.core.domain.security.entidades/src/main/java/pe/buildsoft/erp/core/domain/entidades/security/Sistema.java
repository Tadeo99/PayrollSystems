package pe.buildsoft.erp.core.domain.entidades.security;

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
 * La Class Sistema.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Wed Dec 13 22:04:27 COT 2017
 * @since SIAA-CORE 2.1
 */
@Entity
@Table(name = "Sistema", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_SECURIDAD)
@Getter
@Setter
public class Sistema extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id sistema. */
	@Id
	@Column(name = "idSistema", precision = 18, scale = 0)
	private Long idSistema;

	/** El nombre. */
	@Column(name = "nombre", length = 50)
	private String nombre;

	/** El descripcion. */
	@Column(name = "descripcion", length = 100)
	private String descripcion;

	/** El abreviatura. */
	@Column(name = "abreviatura", length = 50)
	private String abreviatura;

	/** El fecha. */
	// @Temporal( TemporalType.TIMESTAMP)
	@Column(name = "fecha")
	private OffsetDateTime fecha;

	/** El version. */
	@Column(name = "version", length = 50)
	private String version;

	/** El estado. */
	@Column(name = "estado", length = 1)
	private String estado;

	/** El icono. */
	@Column(name = "icono", length = 50)
	private String icono;

	/** El sistema menu list. */
//	@OneToMany(mappedBy = "sistema", fetch = FetchType.LAZY)
	@Transient
	private List<Menu> sistemaMenuList = new ArrayList<>();

	/**
	 * Instancia un nuevo sistema.
	 */
	public Sistema() {
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
		result = prime * result + ((idSistema == null) ? 0 : idSistema.hashCode());
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
		Sistema other = (Sistema) obj;
		if (idSistema == null) {
			if (other.idSistema != null) {
				return false;
			}
		} else if (!idSistema.equals(other.idSistema)) {
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
		return "Sistema [idSistema=" + idSistema + "]";
	}

}