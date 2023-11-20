package pe.buildsoft.erp.core.domain.entidades.pago;

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
 * La Class Clasificacion.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:56 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "Clasificacion", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_PAGO)
@Getter
@Setter
public class Clasificacion extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id clasificacion. */
	@Id
	@Column(name = "idClasificacion", precision = 18, scale = 0)
	private Long idClasificacion;

	/** El item by tipo clasificacion. */
	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idTipoClasificacion", referencedColumnName = "idItem")
	private Item itemByTipoClasificacion;*/
	@Column(name = "idTipoClasificacion" , precision = 18 , scale = 0)
	private Long idItemByTipoClasificacion;

	/** El descripcion. */
	@Column(name = "descripcion", length = 100)
	private String descripcion;

	/** El abreviatura. */
	@Column(name = "abreviatura", length = 50)
	private String abreviatura;

	/** El entidad. */
	// @ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "idEntidad", referencedColumnName = "idEntidad")
	@Column(name = "idEntidad", length = 32)
	private String entidad;

	/** El sede. */
	// @ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "idSede", referencedColumnName = "idSede")
	@Column(name = "idSede", length = 32)
	private String idSede;

	/** El clasificacion catalogo cuenta list. */
	@OneToMany(mappedBy = "clasificacion", fetch = FetchType.LAZY)
	private List<CatalogoCuenta> clasificacionCatalogoCuentaList = new ArrayList<>();

	/**
	 * Instancia un nuevo clasificacion.
	 */
	public Clasificacion() {
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
		result = prime * result + ((idClasificacion == null) ? 0 : idClasificacion.hashCode());
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
		Clasificacion other = (Clasificacion) obj;
		if (idClasificacion == null) {
			if (other.idClasificacion != null) {
				return false;
			}
		} else if (!idClasificacion.equals(other.idClasificacion)) {
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
		return "Clasificacion [idClasificacion=" + idClasificacion + "]";
	}

}