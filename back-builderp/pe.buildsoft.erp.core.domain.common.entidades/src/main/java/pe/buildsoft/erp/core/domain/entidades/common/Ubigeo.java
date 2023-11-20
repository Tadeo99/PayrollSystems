package pe.buildsoft.erp.core.domain.entidades.common;

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

/**
 * La Class Ubigeo.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue Apr 18 09:56:54 COT 2017
 * @since SIAA-CORE 2.1
 */
@Entity
@Table(name = "Ubigeo", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_COMMON)
@Getter
@Setter
public class Ubigeo implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id ubigeo. */
	@Id
	@Column(name = "idUbigeo", length = 6)
	private String idUbigeo;

	/** El descripcion. */
	@Column(name = "descripcion", length = 150)
	private String descripcion;

	/** El ubigeo by dependencia. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dependencia", referencedColumnName = "idUbigeo")
	private Ubigeo ubigeoByDependencia;

	/** El tipo. */
	@Column(name = "tipo", length = 2)
	private String tipo;

	/** El ubigeo by dependencia ubigeo list. */
//	@OneToMany(mappedBy = "ubigeoByDependencia", fetch = FetchType.LAZY)
	@Transient
	private List<Ubigeo> ubigeoByDependenciaUbigeoList = new ArrayList<>();

	/**
	 * Instancia un nuevo ubigeo.
	 */
	public Ubigeo() {
		super();
	}

	/**
	 * Instancia un nuevo ubigeo.
	 *
	 * @param idUbigeo            el id ubigeo
	 * @param descripcion         el descripcion
	 * @param ubigeoByDependencia el ubigeo by dependencia
	 * @param tipo                el tipo
	 */
	public Ubigeo(String idUbigeo, String descripcion, Ubigeo ubigeoByDependencia, String tipo) {
		super();
		this.idUbigeo = idUbigeo;
		this.descripcion = descripcion;
		this.ubigeoByDependencia = ubigeoByDependencia;
		this.tipo = tipo;
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
		result = prime * result + ((idUbigeo == null) ? 0 : idUbigeo.hashCode());
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
		Ubigeo other = (Ubigeo) obj;
		if (idUbigeo == null) {
			if (other.idUbigeo != null) {
				return false;
			}
		} else if (!idUbigeo.equals(other.idUbigeo)) {
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
		return "Ubigeo [idUbigeo=" + idUbigeo + "]";
	}

}