package pe.buildsoft.erp.core.domain.entidades.admision;

import java.io.Serializable;

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
 * La Class Seccion.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Wed Apr 21 12:29:28 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "Seccion", schema = ConfiguracionEntityManagerUtil.ESQUEMA_ADMISION)
@Getter
@Setter
public class Seccion extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id seccion. */
	@Id
	@Column(name = "idSeccion", precision = 18, scale = 0)
	private Long idSeccion;

	/** El grado. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idGrado", referencedColumnName = "idGrado")
	private Grado grado;

	/** El codigo. */
	@Column(name = "codigo", length = 20)
	private String codigo;

	/** El nombre. */
	@Column(name = "nombre", length = 200)
	private String nombre;

	/**
	 * Instancia un nuevo seccion.
	 */
	public Seccion() {
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
		result = prime * result + ((idSeccion == null) ? 0 : idSeccion.hashCode());
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
		Seccion other = (Seccion) obj;
		if (idSeccion == null) {
			if (other.idSeccion != null) {
				return false;
			}
		} else if (!idSeccion.equals(other.idSeccion)) {
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
		return "Seccion [idSeccion=" + idSeccion + "]";
	}

}