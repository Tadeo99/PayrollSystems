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

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class CargaAcademica.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:55 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "CargaAcademica", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_MATRICULA)
@Getter
@Setter
public class CargaAcademica extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id carga academica. */
	@Id
	@Column(name = "idCargaAcademica", length = 32)
	private String idCargaAcademica;

	/** El id entidad. */
	@Column(name = "idEntidad", length = 32)
	private String idEntidad;

	/** El codigo salon. */
	@Column(name = "codigoSalon", length = 10)
	private String codigoSalon;

	/** El nombre. */
	@Column(name = "nombre", length = 100)
	private String nombre;

	/** El anhio.
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idAnhio", referencedColumnName = "idAnhio")
	private Anhio anhio; */
	@Column(name = "idAnhio" , precision = 18 , scale = 0)
	private Long idAnhio;

	/** El seccion.
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idSeccion", referencedColumnName = "idSeccion")
	// @Column(name = "idSeccion" , precision = 18 , scale = 0)
	private Seccion seccion; */
	@Column(name = "idSeccion", precision = 18, scale = 0)
	private Long idSeccion;

	/** El item by turno. 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idTurno", referencedColumnName = "idItem")
	private Item itemByTurno;*/
	@Column(name = "idTurno" , precision = 18 , scale = 0)
    private Long idItemByTurno;

	/** El aula. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idAula", referencedColumnName = "idAula")
	private Aula aula;

	/** El personal by tutor. 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPersonalTutor", referencedColumnName = "idPersonal")
	// @Column(name = "idPersonalTutor" , length = 32)
	private Personal personalByTutor;*/
	@Column(name = "idPersonalTutor", length = 32)
	private String idPersonalByTutor;

	/** El personal by co tutor. 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPersonalCoTutor", referencedColumnName = "idPersonal")
	// @Column(name = "idPersonalCoTutor" , length = 32)
	private Personal personalByCoTutor;*/
	@Column(name = "idPersonalCoTutor", length = 32)
	private String idPersonalByCoTutor;

	/** El personal by coordinador. 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPersonalCoordinador", referencedColumnName = "idPersonal")
	// @Column(name = "idPersonalCoordinador" , length = 32)
	private Personal personalByCoordinador;*/
	@Column(name = "idPersonalCoordinador", length = 32)
	private String idPersonalByCoordinador;

	/** El tipo periodo. */
	@Column(name = "tipoPeriodo", length = 1)
	private String tipoPeriodo;

	/** El nro vacante. */
	@Column(name = "nroVacante", precision = 18, scale = 0)
	private Long nroVacante;

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

	/** El observacion. */
	@Column(name = "observacion", length = 200)
	private String observacion;

	/** El carga academica detalle carga academica list. */
	@OneToMany(mappedBy = "cargaAcademica", fetch = FetchType.LAZY)
	private List<DetalleCargaAcademica> cargaAcademicaDetalleCargaAcademicaList = new ArrayList<>();

	/**
	 * Instancia un nuevo carga academica.
	 */
	public CargaAcademica() {
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
		result = prime * result + ((idCargaAcademica == null) ? 0 : idCargaAcademica.hashCode());
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
		CargaAcademica other = (CargaAcademica) obj;
		if (idCargaAcademica == null) {
			if (other.idCargaAcademica != null) {
				return false;
			}
		} else if (!idCargaAcademica.equals(other.idCargaAcademica)) {
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
		return "CargaAcademica [idCargaAcademica=" + idCargaAcademica + "]";
	}

}