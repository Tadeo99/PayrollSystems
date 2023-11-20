package pe.buildsoft.erp.core.domain.entidades.matricula;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class Pabellon.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:54 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "Pabellon", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_MATRICULA)
@Getter
@Setter
public class Pabellon extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id pabellon. */
	@Id
	@Column(name = "idPabellon", precision = 18, scale = 0)
	private Long idPabellon;

	/** El descripcion. */
	@Column(name = "descripcion", length = 150)
	private String descripcion;

	/** El abreviatura. */
	@Column(name = "abreviatura", length = 50)
	private String abreviatura;

	/** El estado. */
	@Column(name = "estado", length = 1)
	private String estado;

	/** El entidad. */
	// @ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "idEntidad", referencedColumnName = "idEntidad")
	@Column(name = "idEntidad", length = 32)
	private String idEntidad;

	/** El sede. 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idSede", referencedColumnName = "idSede")
	private Sede sede;*/
	@Column(name = "idSede", length = 32)
	private String idSede;

	/** El pabellon aula list. */
	@OneToMany(mappedBy = "pabellon", fetch = FetchType.LAZY)
	private List<Aula> pabellonAulaList = new ArrayList<>();

	/**
	 * Instancia un nuevo pabellon.
	 */
	public Pabellon() {
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
		result = prime * result + ((idPabellon == null) ? 0 : idPabellon.hashCode());
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
		Pabellon other = (Pabellon) obj;
		if (idPabellon == null) {
			if (other.idPabellon != null) {
				return false;
			}
		} else if (!idPabellon.equals(other.idPabellon)) {
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
		return "Pabellon [idPabellon=" + idPabellon + "]";
	}

}