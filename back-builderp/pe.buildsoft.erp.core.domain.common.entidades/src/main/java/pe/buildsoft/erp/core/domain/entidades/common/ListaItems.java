package pe.buildsoft.erp.core.domain.entidades.common;

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

/**
 * La Class ListaItems.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue Apr 18 09:56:46 COT 2017
 * @since SIAA-CORE 2.1
 */
@Entity
@Table(name = "ListaItems", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_COMMON)
@Getter
@Setter
public class ListaItems implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id lista items. */
	@Id
	@Column(name = "idListaItems", precision = 18, scale = 0)
	private Long idListaItems;

	/** El descripcion. */
	@Column(name = "descripcion", length = 250)
	private String descripcion;

	/** El estado. */
	@Column(name = "estado", length = 1)
	private String estado;

	@Column(name = "observacion", length = 1000)
	private String observacion;

	/** El lista items item list. */
//	@OneToMany(mappedBy = "listaItems", fetch = FetchType.LAZY)
	@Transient
	private List<Item> listaItemsItemList = new ArrayList<>();

	/**
	 * Instancia un nuevo lista items.
	 */
	public ListaItems() {
		super();
	}

	/**
	 * Instancia un nuevo lista items.
	 *
	 * @param idListaItems el id lista items
	 * @param descripcion  el descripcion
	 * @param estado       el estado
	 */
	public ListaItems(Long idListaItems, String descripcion, String estado) {
		super();
		this.idListaItems = idListaItems;
		this.descripcion = descripcion;
		this.estado = estado;
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
		result = prime * result + ((idListaItems == null) ? 0 : idListaItems.hashCode());
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
		ListaItems other = (ListaItems) obj;
		if (idListaItems == null) {
			if (other.idListaItems != null) {
				return false;
			}
		} else if (!idListaItems.equals(other.idListaItems)) {
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
		return "ListaItems [idListaItems=" + idListaItems + "]";
	}

}