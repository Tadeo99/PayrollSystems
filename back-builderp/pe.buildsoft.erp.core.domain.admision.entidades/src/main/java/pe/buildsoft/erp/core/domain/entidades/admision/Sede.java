package pe.buildsoft.erp.core.domain.entidades.admision;

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
 * La Class Sede.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Wed Apr 21 12:29:29 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "Sede", schema = ConfiguracionEntityManagerUtil.ESQUEMA_ADMISION)
@Getter
@Setter
public class Sede extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id sede. */
	@Id
	@Column(name = "idSede", length = 32)
	private String idSede;

	/** El codigo. */
	@Column(name = "codigo", length = 150)
	private String codigo;

	/** El nombre. */
	@Column(name = "nombre", length = 200)
	private String nombre;

	/** El direccion. */
	@Column(name = "direccion", length = 200)
	private String direccion;

	/** El coordenada direccion. */
	@Column(name = "coordenadaDireccion", length = 200)
	private String coordenadaDireccion;

	/** El ubigeo. 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idUbigeo", referencedColumnName = "idUbigeo")
	private Ubigeo ubigeo;*/
	@Column(name = "idUbigeo", length = 6)
	private String idUbigeo;

	/** El estado. */
	@Column(name = "estado", length = 1)
	private String estado;

	/** El usuario creacion. */
	@Column(name = "usuarioCreacion", length = 50)
	private String usuarioCreacion;

	/** El fecha creacion. */
	// @Temporal( TemporalType.TIMESTAMP)
	@Column(name = "fechaCreacion")
	private OffsetDateTime fechaCreacion;

	/** El usuario modificacion. */
	@Column(name = "usuarioModificacion", length = 50)
	private String usuarioModificacion;

	/** El fecha modificacion. */
	// @Temporal( TemporalType.TIMESTAMP)
	@Column(name = "fechaModificacion")
	private OffsetDateTime fechaModificacion;

	/** El sede asigna postulante list. */
//	@OneToMany(mappedBy = "sede", fetch = FetchType.LAZY)
	@Transient
	private List<AsignaPostulante> sedeAsignaPostulanteList = new ArrayList<>();

	/** El sede det sede list. */
	@Transient
	private List<DetSede> sedeDetSedeList = new ArrayList<>();

	/**
	 * Instancia un nuevo sede.
	 */
	public Sede() {
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
		result = prime * result + ((idSede == null) ? 0 : idSede.hashCode());
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
		Sede other = (Sede) obj;
		if (idSede == null) {
			if (other.idSede != null) {
				return false;
			}
		} else if (!idSede.equals(other.idSede)) {
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
		return "Sede [idSede=" + idSede + "]";
	}

}