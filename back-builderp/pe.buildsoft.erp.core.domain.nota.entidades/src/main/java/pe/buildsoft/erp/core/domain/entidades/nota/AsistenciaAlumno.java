package pe.buildsoft.erp.core.domain.entidades.nota;

import java.io.Serializable;
import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.entidades.matricula.Alumno;
import pe.buildsoft.erp.core.domain.entidades.matricula.DetalleCargaAcademica;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class AsistenciaAlumno.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Jun 10 00:13:45 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "AsistenciaAlumno", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_NOTA)
@Getter
@Setter
public class AsistenciaAlumno extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id asistencia alumno. */
	@Id
	@Column(name = "idAsistenciaAlumno", length = 32)
	private String idAsistenciaAlumno;

	/** El alumno. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idAlumno", referencedColumnName = "idAlumno")
	// @Column(name = "idAlumno" , length = 10)
	private Alumno alumno;

	/** El id detalle carga academica. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idDetalleCargaAcademica", referencedColumnName = "idDetalleCargaAcademica")
	// @Column(name = "idDetalleCargaAcademica" , length = 32)
	private DetalleCargaAcademica detalleCargaAcademica;

	/** El item by dia. 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idDia", referencedColumnName = "idItem")
	private Item itemByDia;*/
	@Column(name = "idDia" , precision = 18 , scale = 0)
    private Long idItemByDia;

	/** El estado. */
	@Column(name = "estado", length = 1)
	private String estado;

	/** El justificacion. */
	@Column(name = "justificacion", length = 200)
	private String justificacion;

	/** El fecha horario. */
	// @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaHorario")
	private OffsetDateTime fechaHorario;

	/** El fecha creacion. */
	// @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaCreacion")
	private OffsetDateTime fechaCreacion;

	/** El usuario creacion. */
	@Column(name = "usuarioCreacion", length = 50)
	private String usuarioCreacion;

	/** El fecha modificacion. */
	// @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaModificacion")
	private OffsetDateTime fechaModificacion;

	/** El usuario modificacion. */
	@Column(name = "usuarioModificacion", length = 50)
	private String usuarioModificacion;

	/**
	 * Instancia un nuevo asistencia alumno.
	 */
	public AsistenciaAlumno() {
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
		result = prime * result + ((idAsistenciaAlumno == null) ? 0 : idAsistenciaAlumno.hashCode());
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
		AsistenciaAlumno other = (AsistenciaAlumno) obj;
		if (idAsistenciaAlumno == null) {
			if (other.idAsistenciaAlumno != null) {
				return false;
			}
		} else if (!idAsistenciaAlumno.equals(other.idAsistenciaAlumno)) {
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
		return "AsistenciaAlumno [idAsistenciaAlumno=" + idAsistenciaAlumno + "]";
	}

}