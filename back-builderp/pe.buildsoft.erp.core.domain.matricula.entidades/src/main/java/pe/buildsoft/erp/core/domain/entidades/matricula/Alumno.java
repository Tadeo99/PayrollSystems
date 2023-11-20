package pe.buildsoft.erp.core.domain.entidades.matricula;

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

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class Alumno.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:54 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "Alumno", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_MATRICULA)
@Getter
@Setter
public class Alumno extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id alumno. */
	@Id
	@Column(name = "idAlumno", length = 10)
	private String idAlumno;

	/** El postulante. 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPostulante", referencedColumnName = "idPostulante")
	// @Column(name = "idPostulante" , length = 32)
	private Postulante postulante;*/
	@Column(name = "idPostulante", length = 32)
	private String idPostulante;

	/** El grado. 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idGrado", referencedColumnName = "idGrado")
	// @Column(name = "idGrado" , precision = 18 , scale = 0)
	private Grado grado;*/
	@Column(name = "idGrado", precision = 18, scale = 0)
	private Long idGrado;

	/** El codigo. */
	@Column(name = "codigo", length = 150)
	private String codigo;

	/** El estado. */
	@Column(name = "estado", length = 1)
	private String estado;

	/** El item by doc identidad.
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idDocIdentidad", referencedColumnName = "idItem")
	private Item itemByDocIdentidad; */
	@Column(name = "idDocIdentidad" , precision = 18 , scale = 0)
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

	/** El fecha nacimiento. */
	//@Temporal(TemporalType.TIMESTAMP)
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

	/** El lugar nacimiento.
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idLugarNacimiento", referencedColumnName = "idUbigeo")
	private Ubigeo lugarNacimiento; */
	@Column(name = "idLugarNacimiento", length = 6)
	private String idLugarNacimiento;

	/** El item by nacionalidad. 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idNacionalidad", referencedColumnName = "idItem")
	private Item itemByNacionalidad;*/
	@Column(name = "idNacionalidad" , precision = 18 , scale = 0)
    private Long idItemByNacionalidad;

	/** El foto. */
	@Column(name = "foto", length = 150)
	private String foto;

	/** El usuario creacion. */
	@Column(name = "usuarioCreacion", length = 50)
	private String usuarioCreacion;

	/** El fecha creacion. */
	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaCreacion")
	private OffsetDateTime fechaCreacion;

	/** El usuario modificacion. */
	@Column(name = "usuarioModificacion", length = 50)
	private String usuarioModificacion;

	/** El fecha modificacion. */
	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaModificacion")
	private OffsetDateTime fechaModificacion;

	/** El entidad. */
	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idEntidad", referencedColumnName = "idEntidad")
	*/
	 @Column(name = "idEntidad" , length = 32)
	private String entidad;

	/** El sede. 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idSede", referencedColumnName = "idSede")
	// @Column(name = "idSede" , length = 32)
	private Sede sede;*/
	@Column(name = "idSede", length = 32)
	private String idSede;

	/** El alumno matricula list. */
	@OneToMany(mappedBy = "alumno", fetch = FetchType.LAZY)
	private List<Matricula> alumnoMatriculaList = new ArrayList<>();

	/**
	 * Instancia un nuevo alumno.
	 */
	public Alumno() {
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
		result = prime * result + ((idAlumno == null) ? 0 : idAlumno.hashCode());
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
		Alumno other = (Alumno) obj;
		if (idAlumno == null) {
			if (other.idAlumno != null) {
				return false;
			}
		} else if (!idAlumno.equals(other.idAlumno)) {
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
		return "Alumno [idAlumno=" + idAlumno + "]";
	}

}