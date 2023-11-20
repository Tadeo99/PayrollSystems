package pe.buildsoft.erp.core.domain.entidades.nota;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
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
import pe.buildsoft.erp.core.domain.entidades.matricula.DetMatricula;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class DetRegistroNota.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Jun 10 00:13:46 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "DetRegistroNota", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_NOTA)
@Getter
@Setter
public class DetRegistroNota extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id det registro nota. */
	@Id
	@Column(name = "idDetRegistroNota", length = 32)
	private String idDetRegistroNota;

	/** El registro nota. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idRegistroNota", referencedColumnName = "idRegistroNota")
	private RegistroNota registroNota;

	/** El det matricula. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idDetMatricula", referencedColumnName = "idDetMatricula")
	// @Column(name = "idDetMatricula" , length = 32)
	private DetMatricula detMatricula;

	/** El nota letra. */
	@Column(name = "ponderadoCurso", precision = 18, scale = 2)
	private BigDecimal notaLetra;

	/** El nota letra by nota aplazado. */
	@Column(name = "notaAplazado", precision = 18, scale = 2)
	private BigDecimal notaLetraByNotaAplazado;

	/** El fecha creacion. */
	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaCreacion")
	private OffsetDateTime fechaCreacion;

	/** El fecha modificacion. */
	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaModificacion")
	private OffsetDateTime fechaModificacion;

	/** El fecha nota aplazado. */
	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaNotaAplazado")
	private OffsetDateTime fechaNotaAplazado;

	/** El usuario creacion. */
	@Column(name = "usuarioCreacion", length = 50)
	private String usuarioCreacion;

	/** El usuario modificacion. */
	@Column(name = "usuarioModificacion", length = 50)
	private String usuarioModificacion;

	/** El det registro nota curso nota unidad list. */
	/*
	 * @OneToMany(mappedBy = "detRegistroNota", fetch = FetchType.LAZY) private
	 * List<CursoNotaUnidad> detRegistroNotaCursoNotaUnidadList = new ArrayList<>();
	 */

	/** El det registro nota curso nota periodo list. */
	/*
	 * @OneToMany(mappedBy = "detRegistroNota", fetch = FetchType.LAZY) private
	 * List<CursoNotaPeriodo> detRegistroNotaCursoNotaPeriodoList = new
	 * ArrayList<>();
	 */
	/** El det registro nota curso nota unidad prom list. */
	/*
	 * @OneToMany(mappedBy = "detRegistroNota", fetch = FetchType.LAZY) private
	 * List<CursoNotaUnidadProm> detRegistroNotaCursoNotaUnidadPromList = new
	 * ArrayList<>();
	 */
	/** El det registro nota curso nota periodo prom list. */

	/*
	 * @OneToMany(mappedBy = "detRegistroNota", fetch = FetchType.LAZY) private
	 * List<CursoNotaPeriodoProm> detRegistroNotaCursoNotaPeriodoPromList = new
	 * ArrayList<>();
	 */
	
	
	/** El det registro nota curso nota unidad list. */
	@Transient
	private List<CursoNotaUnidad> detRegistroNotaCursoNotaUnidadList = new ArrayList<>();

	/** El det registro nota curso nota periodo list. */
	@Transient
	private List<CursoNotaPeriodo> detRegistroNotaCursoNotaPeriodoList = new ArrayList<>();

	/** El det registro nota curso nota unidad prom list. */
	@Transient
	private List<CursoNotaUnidadProm> detRegistroNotaCursoNotaUnidadPromList = new ArrayList<>();

	/** El det registro nota curso nota periodo prom list. */
	@Transient
	private List<CursoNotaPeriodoProm> detRegistroNotaCursoNotaPeriodoPromList = new ArrayList<>();
	
	/** La curso nota evaluar. */
	@Transient
	private List<CursoNotaPeriodo> cursoNotaEvaluarPeriodo = new ArrayList<>();

	@Transient
	private List<CursoNotaUnidad> cursoNotaEvaluarUnidad = new ArrayList<>();

	@Transient
	private List<CursoNotaUnidadProm> cursoNotaEvaluarUnidadProm = new ArrayList<>();
	
	@Transient
	private Integer position;
	/**
	 * Instancia un nuevo det registro nota.
	 */
	public DetRegistroNota() {
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
		DetRegistroNota other = (DetRegistroNota) obj;
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
		return "DetRegistroNota [idDetRegistroNota=" + idDetRegistroNota + "]";
	}

}