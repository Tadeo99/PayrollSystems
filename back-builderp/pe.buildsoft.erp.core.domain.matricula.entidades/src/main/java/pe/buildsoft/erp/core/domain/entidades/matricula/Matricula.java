package pe.buildsoft.erp.core.domain.entidades.matricula;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class Matricula.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:55 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "Matricula", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_MATRICULA)
@Getter
@Setter
public class Matricula extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id matricula. */
	@Id
	@Column(name = "idMatricula", length = 32)
	private String idMatricula;

	/** El anhio. 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idAnhio", referencedColumnName = "idAnhio")
	private Anhio anhio;*/
	@Column(name = "idAnhio" , precision = 18 , scale = 0)
    private Long idAnhio;

	/** El periodo. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPeriodo", referencedColumnName = "idPeriodo")
	private Periodo periodo;

	/** El alumno. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idAlumno", referencedColumnName = "idAlumno")
	private Alumno alumno;

	/** El carga academica. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idCargaAcademica", referencedColumnName = "idCargaAcademica")
	private CargaAcademica cargaAcademica;

	/** El creditos. */
	@Column(name = "creditos", precision = 32, scale = 0)
	private Integer creditos;

	/** El tipo matricula. */
	@Column(name = "tipoMatricula", length = 1)
	private String tipoMatricula;

	/** El fecha matricula. */
	// @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaMatricula")
	private OffsetDateTime fechaMatricula;

	/** El usuario creacion. */
	@Column(name = "usuarioCreacion", length = 50)
	private String usuarioCreacion;

	/** El fecha creacion. */
	// @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaCreacion")
	private OffsetDateTime fechaCreacion;

	/** El usuario modificacion. */
	@Column(name = "usuarioModificacion", length = 50)
	private String usuarioModificacion;

	/** El fecha modificacion. */
	// @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaModificacion")
	private OffsetDateTime fechaModificacion;

	/** El observacion. */
	@Column(name = "observacion", length = 200)
	private String observacion;

	/** El estado. */
	@Column(name = "estado", length = 1)
	private String estado;

	/** El matricula det matricula list. */
	@OneToMany(mappedBy = "matricula", fetch = FetchType.LAZY)
	private List<DetMatricula> matriculaDetMatriculaList = new ArrayList<>();

	/** La lista det car lectiva. */
	@Transient
	private List<DetalleCargaAcademica> listaDetalleCargaAcademica = new ArrayList<>();
	
	@Transient
	private String usuarioSession;

	/**
	 * Instancia un nuevo matricula.
	 */
	public Matricula() {
		super();
	}

	public Matricula(String idMatricula) {
		super();
		this.idMatricula = idMatricula;
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
		result = prime * result + ((idMatricula == null) ? 0 : idMatricula.hashCode());
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
		Matricula other = (Matricula) obj;
		if (idMatricula == null) {
			if (other.idMatricula != null) {
				return false;
			}
		} else if (!idMatricula.equals(other.idMatricula)) {
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
		return "Matricula [idMatricula=" + idMatricula + "]";
	}

}