package pe.buildsoft.erp.core.application.entidades.matricula;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.common.AnhioDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class MatriculaDTO.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Apr 22 00:26:55 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Getter
@Setter
public class MatriculaDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id matricula. */
	private String idMatricula;

	/** El anhio. */
	private Long idAnhio;
	private AnhioDTO anhio;

	/** El periodo. */
	private PeriodoDTO periodo;

	/** El alumno. */
	private AlumnoDTO alumno;

	/** El carga academica. */
	private CargaAcademicaDTO cargaAcademica;

	/** El creditos. */
	private Integer creditos;

	/** El tipo matricula. */
	private String tipoMatricula;

	/** El fecha matricula. */
	private OffsetDateTime fechaMatricula;

	/** El usuario creacion. */
	private String usuarioCreacion;

	/** El fecha creacion. */
	private OffsetDateTime fechaCreacion;

	/** El usuario modificacion. */
	private String usuarioModificacion;

	/** El fecha modificacion. */
	private OffsetDateTime fechaModificacion;

	/** El observacion. */
	private String observacion;

	/** El estado. */
	private String estado;

	/** La lista det car lectiva. */
	private List<DetalleCargaAcademicaDTO> listaDetalleCargaAcademica = new ArrayList<>();

	private String usuarioSession;

	/**
	 * Instancia un nuevo matriculaDTO.
	 */
	public MatriculaDTO() {
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
		MatriculaDTO other = (MatriculaDTO) obj;
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
		return "MatriculaDTO [idMatricula=" + idMatricula + "]";
	}

}