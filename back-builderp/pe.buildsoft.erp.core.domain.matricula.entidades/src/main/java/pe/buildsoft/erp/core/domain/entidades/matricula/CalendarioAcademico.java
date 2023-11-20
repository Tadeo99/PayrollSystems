package pe.buildsoft.erp.core.domain.entidades.matricula;

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
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class CalendarioAcademico.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:54 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "CalendarioAcademico", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_MATRICULA)
@Getter
@Setter
public class CalendarioAcademico extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id calendario academico. */
	@Id
	@Column(name = "idCalendarioAcademico", length = 10)
	private String idCalendarioAcademico;

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

	/** El unidad. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idUnidad", referencedColumnName = "idUnidad")
	private Unidad unidad;

	/** El item by nivel.
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idNivel", referencedColumnName = "idItem")
	private Item itemByNivel; */
	@Column(name = "idNivel" , precision = 18 , scale = 0)
    private Long idItemByNivel;

	/** El descripcion. */
	@Column(name = "descripcion", length = 100)
	private String descripcion;

	/** El codigo cronograma. */
	@Column(name = "codigoCronograma", precision = 18, scale = 0)
	private Long codigoCronograma;

	/** El fecha inicio. */
	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaInicio")
	private OffsetDateTime fechaInicio;

	/** El fecha fin. */
	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaFin")
	private OffsetDateTime fechaFin;

	/** El estado. */
	@Column(name = "estado", length = 1)
	private String estado;

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

	/**
	 * Instancia un nuevo calendario academico.
	 */
	public CalendarioAcademico() {
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
		result = prime * result + ((idCalendarioAcademico == null) ? 0 : idCalendarioAcademico.hashCode());
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
		CalendarioAcademico other = (CalendarioAcademico) obj;
		if (idCalendarioAcademico == null) {
			if (other.idCalendarioAcademico != null) {
				return false;
			}
		} else if (!idCalendarioAcademico.equals(other.idCalendarioAcademico)) {
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
		return "CalendarioAcademico [idCalendarioAcademico=" + idCalendarioAcademico + "]";
	}

}