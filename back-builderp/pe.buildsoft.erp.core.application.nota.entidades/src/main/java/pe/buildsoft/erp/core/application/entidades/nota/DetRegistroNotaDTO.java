package pe.buildsoft.erp.core.application.entidades.nota;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.matricula.DetMatriculaDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class DetRegistroNotaDTO.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Jun 10 00:13:46 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Getter
@Setter
public class DetRegistroNotaDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id det registro nota. */
	private String idDetRegistroNota;

	/** El registro nota. */
	private RegistroNotaDTO registroNota;

	/** El det matricula. */
	private DetMatriculaDTO detMatricula;

	/** El nota letra. */
	private BigDecimal notaLetra;

	/** El nota letra by nota aplazado. */
	private BigDecimal notaLetraByNotaAplazado;

	/** El fecha creacion. */
	private OffsetDateTime fechaCreacion;

	/** El fecha modificacion. */
	private OffsetDateTime fechaModificacion;

	/** El fecha nota aplazado. */
	private OffsetDateTime fechaNotaAplazado;

	/** El usuario creacion. */
	private String usuarioCreacion;

	/** El usuario modificacion. */
	private String usuarioModificacion;

	/** El det registro nota curso nota unidad list. */
	private List<CursoNotaUnidadDTO> detRegistroNotaCursoNotaUnidadList = new ArrayList<>();

	/** El det registro nota curso nota unidad prom list. */
	private List<CursoNotaUnidadPromDTO> detRegistroNotaCursoNotaUnidadPromList = new ArrayList<>();

	/** La observacion. */
	private String observacion;

	private boolean esAplazado;

	/** La curso nota evaluar. */
	private List<CursoNotaPeriodoDTO> cursoNotaEvaluarPeriodo = new ArrayList<>();

	private List<CursoNotaUnidadDTO> cursoNotaEvaluarUnidad = new ArrayList<>();

	private List<CursoNotaUnidadPromDTO> cursoNotaEvaluarUnidadProm = new ArrayList<>();
	
	private Integer position;

	/**
	 * Instancia un nuevo det registro notaDTO.
	 */
	public DetRegistroNotaDTO() {
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
		result = prime * result + ((idDetRegistroNota == null) ? 0 : idDetRegistroNota.hashCode());
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
		DetRegistroNotaDTO other = (DetRegistroNotaDTO) obj;
		if (idDetRegistroNota == null) {
			if (other.idDetRegistroNota != null) {
				return false;
			}
		} else if (!idDetRegistroNota.equals(other.idDetRegistroNota)) {
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
		return "DetRegistroNotaDTO [idDetRegistroNota=" + idDetRegistroNota + "]";
	}

}