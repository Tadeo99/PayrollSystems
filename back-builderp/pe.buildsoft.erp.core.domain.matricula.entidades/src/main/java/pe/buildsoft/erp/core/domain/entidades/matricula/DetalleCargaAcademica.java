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
import jakarta.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class DetalleCargaAcademica.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:54 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "DetalleCargaAcademica", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_MATRICULA)
@Getter
@Setter
public class DetalleCargaAcademica extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id detalle carga academica. */
	@Id
	@Column(name = "idDetalleCargaAcademica", length = 32)
	private String idDetalleCargaAcademica;

	/** El carga academica. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idCargaAcademica", referencedColumnName = "idCargaAcademica")
	private CargaAcademica cargaAcademica;

	/** El personal by docente principal. 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idDocentePrincipal", referencedColumnName = "idPersonal")
	// @Column(name = "idDocentePrincipal" , length = 32)
	private Personal personalByDocentePrincipal;*/
	@Column(name = "idDocentePrincipal", length = 32)
	private String idPersonalByDocentePrincipal;

	/** El personal by docente auxiliar. 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idDocenteAuxiliar", referencedColumnName = "idPersonal")
	// @Column(name = "idDocenteAuxiliar" , length = 32)
	private Personal personalByDocenteAuxiliar;*/
	@Column(name = "idDocenteAuxiliar", length = 32)
	private String idPersonalByDocenteAuxiliar;

	/** El det malla curricular. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idDetMallaCurricular", referencedColumnName = "idDetMallaCurricular")
	private DetMallaCurricular detMallaCurricular;

	/** El codigo acta. */
	@Column(name = "codigoActa", length = 11)
	private String codigoActa;

	/** El grupo. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idGrupo", referencedColumnName = "idGrupo")
	private Grupo grupo;

	/** El detalle carga academica horario list. */
	@OneToMany(mappedBy = "detalleCargaAcademica", fetch = FetchType.LAZY)
	private List<Horario> detalleCargaAcademicaHorarioList = new ArrayList<>();
	
	@Transient
	private List<DetalleCargaAcademica> detalleCargaAcademicaList = new ArrayList<>();

	/**
	 * Instancia un nuevo detalle carga academica.
	 */
	public DetalleCargaAcademica() {
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
		result = prime * result + ((idDetalleCargaAcademica == null) ? 0 : idDetalleCargaAcademica.hashCode());
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
		DetalleCargaAcademica other = (DetalleCargaAcademica) obj;
		if (idDetalleCargaAcademica == null) {
			if (other.idDetalleCargaAcademica != null) {
				return false;
			}
		} else if (!idDetalleCargaAcademica.equals(other.idDetalleCargaAcademica)) {
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
		return "DetalleCargaAcademica [idDetalleCargaAcademica=" + idDetalleCargaAcademica + "]";
	}

}