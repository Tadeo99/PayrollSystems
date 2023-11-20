package pe.buildsoft.erp.core.domain.entidades.admision;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class Grado.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Wed Apr 21 12:29:29 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "Grado", schema = ConfiguracionEntityManagerUtil.ESQUEMA_ADMISION)
@Getter
@Setter
public class Grado extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id grado. */
	@Id
	@Column(name = "idGrado", precision = 18, scale = 0)
	private Long idGrado;

	/** El item by nivel.
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idNivel", referencedColumnName = "idItem")
	private Item itemByNivel; */
	@Column(name = "idNivel", precision = 18, scale = 0)
	private Long idItemByNivel;

	/** El codigo. */
	@Column(name = "codigo", length = 20)
	private String codigo;

	/** El nombre. */
	@Column(name = "nombre", length = 200)
	private String nombre;

	/** El grado asigna postulante list. */
//	@OneToMany(mappedBy = "grado", fetch = FetchType.LAZY)
	@Transient
	private List<AsignaPostulante> gradoAsignaPostulanteList = new ArrayList<>();

	/** El grado det sede list. */
//	@OneToMany(mappedBy = "grado", fetch = FetchType.LAZY)
	@Transient
	private List<DetSede> gradoDetSedeList = new ArrayList<>();

	/** El grado seccion list. */
//	@OneToMany(mappedBy = "grado", fetch = FetchType.LAZY)
	@Transient
	private List<Seccion> gradoSeccionList = new ArrayList<>();

	/**
	 * Instancia un nuevo grado.
	 */
	public Grado() {
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
		result = prime * result + ((idGrado == null) ? 0 : idGrado.hashCode());
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
		Grado other = (Grado) obj;
		if (idGrado == null) {
			if (other.idGrado != null) {
				return false;
			}
		} else if (!idGrado.equals(other.idGrado)) {
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
		return "Grado [idGrado=" + idGrado + "]";
	}

}