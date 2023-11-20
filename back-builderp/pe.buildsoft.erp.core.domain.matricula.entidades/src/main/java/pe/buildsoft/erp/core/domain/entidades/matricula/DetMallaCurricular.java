package pe.buildsoft.erp.core.domain.entidades.matricula;

import java.io.Serializable;
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

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class DetMallaCurricular.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:55 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "DetMallaCurricular", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_MATRICULA)
@Getter
@Setter
public class DetMallaCurricular extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id det malla curricular. */
	@Id
	@Column(name = "idDetMallaCurricular", length = 32)
	private String idDetMallaCurricular;

	/** El det malla curricular padre. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idMallaCurricularPadre", referencedColumnName = "idDetMallaCurricular")
	private DetMallaCurricular detMallaCurricularPadre;

	/** El malla curricular. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idMallaCurricular", referencedColumnName = "idMallaCurricular")
	private MallaCurricular mallaCurricular;

	/** El descripcion curso. */
	@Column(name = "descripcionCurso", length = 200)
	private String descripcionCurso;

	/** El codigo asignatura. */
	@Column(name = "codigoAsignatura", length = 10)
	private String codigoAsignatura;

	/** El hora teorica. */
	@Column(name = "horaTeorica", precision = 32, scale = 0)
	private Integer horaTeorica;

	/** El hora practica. */
	@Column(name = "horaPractica", precision = 32, scale = 0)
	private Integer horaPractica;

	/** El tipo asignatura. */
	@Column(name = "tipoAsignatura", length = 1)
	private String tipoAsignatura;

	/** El peso. */
	@Column(name = "peso", precision = 18, scale = 0)
	private Long peso;

	/** El tipo calculo. */
	@Column(name = "tipoCalculo", length = 1)
	private String tipoCalculo;

	/** El tipo promedio. */
	@Column(name = "tipoPromedio", length = 1)
	private String tipoPromedio;

	/** El det malla curricular det matricula list. */
	@OneToMany(mappedBy = "detMallaCurricular", fetch = FetchType.LAZY)
	private List<DetMatricula> detMallaCurricularDetMatriculaList = new ArrayList<>();

	/** El det malla curricular detalle carga academica list. */
	@OneToMany(mappedBy = "detMallaCurricular", fetch = FetchType.LAZY)
	private List<DetalleCargaAcademica> detMallaCurricularDetalleCargaAcademicaList = new ArrayList<>();

	/** El det malla curricular padre det malla curricular list. */
	@OneToMany(mappedBy = "detMallaCurricularPadre", fetch = FetchType.LAZY)
	private List<DetMallaCurricular> detMallaCurricularPadreDetMallaCurricularList = new ArrayList<>();

	/** El det malla curricular CriterioEvaluacion list. */
	@OneToMany(mappedBy = "detMallaCurricular", fetch = FetchType.LAZY)
	private List<CriterioEvaluacion> detMallaCurricularCriterioEvaluacionList = new ArrayList<>();

	/** El det malla curricular pre requisito list. */
	@OneToMany(mappedBy = "detMallaCurricular", fetch = FetchType.LAZY)
	private List<PreRequisito> detMallaCurricularPreRequisitoList = new ArrayList<>();

	/** El det malla curricular requisito pre requisito list. */
	@OneToMany(mappedBy = "detMallaCurricularRequisito", fetch = FetchType.LAZY)
	private List<PreRequisito> detMallaCurricularRequisitoPreRequisitoList = new ArrayList<>();

	/**
	 * Instancia un nuevo det malla curricular.
	 */
	public DetMallaCurricular() {
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
		result = prime * result + ((idDetMallaCurricular == null) ? 0 : idDetMallaCurricular.hashCode());
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
		DetMallaCurricular other = (DetMallaCurricular) obj;
		if (idDetMallaCurricular == null) {
			if (other.idDetMallaCurricular != null) {
				return false;
			}
		} else if (!idDetMallaCurricular.equals(other.idDetMallaCurricular)) {
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
		return "DetMallaCurricular [idDetMallaCurricular=" + idDetMallaCurricular + "]";
	}

}