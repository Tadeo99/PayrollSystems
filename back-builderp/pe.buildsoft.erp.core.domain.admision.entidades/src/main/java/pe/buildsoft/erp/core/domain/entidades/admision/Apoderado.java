package pe.buildsoft.erp.core.domain.entidades.admision;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class Apoderado.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Wed Apr 21 12:29:29 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "Apoderado", schema = ConfiguracionEntityManagerUtil.ESQUEMA_ADMISION)
@Getter
@Setter
public class Apoderado extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id apoderado. */
	@Id
	@Column(name = "idApoderado", length = 32)
	private String idApoderado;

	/** El codigo. */
	@Column(name = "codigo", length = 150)
	private String codigo;

	/** El estado. */
	@Column(name = "estado", length = 1)
	private String estado;

	/**
	 * El item by doc identidad.
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * @JoinColumn(name = "idDocIdentidad", referencedColumnName = "idItem") private
	 * Item itemByDocIdentidad;
	 */
	@Column(name = "idDocIdentidad", precision = 18, scale = 0)
	private Long idItemByDocIdentidad;

	/** El nro doc. */
	@Column(name = "nroDoc", length = 20)
	private String nroDoc;

	/** El apellido paterno. */
	@Column(name = "apellidoPaterno", length = 150)
	private String apellidoPaterno;

	/** El apellido materno. */
	@Column(name = "apellidoMaterno", length = 150)
	private String apellidoMaterno;

	/** El nombres. */
	@Column(name = "nombres", length = 150)
	private String nombres;

	/**
	 * El item by estado civil.
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * @JoinColumn(name = "idEstadoCivil", referencedColumnName = "idItem") private
	 *                  Item itemByEstadoCivil;
	 */
	@Column(name = "idEstadoCivil", precision = 18, scale = 0)
	private Long idItemByEstadoCivil;

	/** El fecha nacimiento. */
	// @Temporal( TemporalType.TIMESTAMP)
	@Column(name = "fechaNacimiento")
	private OffsetDateTime fechaNacimiento;

	/** El telefono1. */
	@Column(name = "telefono1", length = 20)
	private String telefono1;

	/** El telefono2. */
	@Column(name = "telefono2", length = 20)
	private String telefono2;

	/** El celular. */
	@Column(name = "celular", length = 20)
	private String celular;

	/** El email. */
	@Column(name = "email", length = 100)
	private String email;

	/** El sexo. */
	@Column(name = "sexo", length = 32)
	private String sexo;

	/**
	 * El lugar nacimiento.
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * @JoinColumn(name = "idLugarNacimiento", referencedColumnName = "idUbigeo")
	 *                  private Ubigeo lugarNacimiento;
	 */
	@Column(name = "idLugarNacimiento", length = 6)
	private String idLugarNacimiento;

	/**
	 * El item by nacionalidad.
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * @JoinColumn(name = "idNacionalidad", referencedColumnName = "idItem") private
	 *                  Item itemByNacionalidad;
	 */
	@Column(name = "idNacionalidad", precision = 18, scale = 0)
	private Long idItemByNacionalidad;

	/** El foto. */
	@Column(name = "foto", length = 150)
	private String foto;

	/** El firma. */
	@Column(name = "firma", length = 150)
	private String firma;

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

	@Column(name = "tipo", precision = 18, scale = 0)
	private Long tipo;

	/** El apoderado asigna postulante list. */
//	@OneToMany(mappedBy = "apoderado", fetch = FetchType.LAZY)
	@Transient
	private List<AsignaPostulante> apoderadoAsignaPostulanteList = new ArrayList<>();

	@Transient
	private String clave;

	@Transient
	private String usuarioSession;
	
	@Transient
	private String idEntidadSelect;
	
	@Transient
	private String authToken;

	/**
	 * Instancia un nuevo apoderado.
	 */
	public Apoderado() {
		super();
	}

	// get y set

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idApoderado == null) ? 0 : idApoderado.hashCode());
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
		Apoderado other = (Apoderado) obj;
		if (idApoderado == null) {
			if (other.idApoderado != null) {
				return false;
			}
		} else if (!idApoderado.equals(other.idApoderado)) {
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
		return "Apoderado [idApoderado=" + idApoderado + "]";
	}

}