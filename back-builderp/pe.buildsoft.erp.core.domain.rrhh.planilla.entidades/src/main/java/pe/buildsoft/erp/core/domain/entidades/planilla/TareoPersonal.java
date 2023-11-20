package pe.buildsoft.erp.core.domain.entidades.planilla;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class TareoPersonal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 23:10:02 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "TareoPersonal", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_RRHH_PLANILLA)
@Getter
@Setter
public class TareoPersonal extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id tareo personal. */
	@Id
	@Column(name = "idTareoPersonal", length = 32)
	private String idTareoPersonal;

	/** El personal. 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPersonal", referencedColumnName = "idPersonal")
	private Personal personal;*/
	@Column(name = "idPersonal", length = 32)
	private String idPersonal;


	/** El item by mes. 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idMes", referencedColumnName = "idItem")
	private Item itemByMes;*/
	@Column(name = "idMes", precision = 18, scale = 0)
	private Long idItemByMes;

	/** El anhio. 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idAnhio", referencedColumnName = "idAnhio")
	// @Column(name = "idAnhio" , precision = 18 , scale = 0)
	private Anhio anhio;*/
	@Column(name = "idAnhio", precision = 18, scale = 0)
	private Long idAnhio;

	/** El item by categoria ocupacional. 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idCategoriaOcupacional", referencedColumnName = "idItem")
	private Item itemByCategoriaOcupacional;*/
	@Column(name = "idCategoriaOcupacional", precision = 18, scale = 0)
	private Long idItemByCategoriaOcupacional;

	/** El item by periocidad. 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPeriocidad", referencedColumnName = "idItem")
	private Item itemByPeriocidad;*/
	@Column(name = "idPeriocidad", precision = 18, scale = 0)
	private Long idItemByPeriocidad;

	/** El dias lab. */
	@Column(name = "diasLab", precision = 18, scale = 0)
	private Long diasLab;

	/** El dias tra. */
	@Column(name = "diasTra", precision = 18, scale = 0)
	private Long diasTra;
	
	/** El dias tra. */
	@Column(name = "dominical", precision = 18, scale = 0)
	private Long dominical;

	/** El horas extras25. */
	@Column(name = "horasNormal", precision = 18, scale = 0)
	private Long horasNormal;
	
	/** El horas extras25. */
	@Column(name = "horasExtras25", precision = 18, scale = 0)
	private Long horasExtras25;
	
	/** El horas extras35. */
	@Column(name = "horasExtras35", precision = 18, scale = 0)
	private Long horasExtras35;

	/** El horas extras. */
	@Column(name = "horasExtras100", precision = 18, scale = 0)
	private Long horasExtras100;
	
	/** El horas nocturna. */
	@Column(name = "horasNocturna", precision = 18, scale = 0)
	private Long horasNocturna;

	/** El vacaciones. */
	@Column(name = "vacaciones", precision = 18, scale = 0)
	private Long vacaciones;
	
	/** El permisio sin goce de haber. */
	@Column(name = "permisoSinGoceHaber", precision = 18, scale = 0)
	private Long permisoSinGoceHaber;
	
	/** El falta. */
	@Column(name = "falta", precision = 18, scale = 0)
	private Long falta;
	
	/** El subsidio. */
	@Column(name = "subsidio", precision = 18, scale = 0)
	private Long subsidio;
	
	/** El tardanza. */
	@Column(name = "tardanza", precision = 18, scale = 0)
	private Long tardanza;
	
	/** El rmv. */
	@Column(name = "rmv", precision = 18, scale = 2)
	private BigDecimal rmv;
	
	/** El bofinicacion nocturna. */
	@Column(name = "bonifiNocturna", precision = 18, scale = 2)
	private BigDecimal bonifiNocturna;
	
	/**
	 * Instancia un nuevo asistencia personal.
	 */
	public TareoPersonal() {
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
		result = prime * result + ((idTareoPersonal == null) ? 0 : idTareoPersonal.hashCode());
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
		TareoPersonal other = (TareoPersonal) obj;
		if (idTareoPersonal == null) {
			if (other.idTareoPersonal != null) {
				return false;
			}
		} else if (!idTareoPersonal.equals(other.idTareoPersonal)) {
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
		return "TareoPersonal [idTareoPersonal=" + idTareoPersonal + "]";
	}

}