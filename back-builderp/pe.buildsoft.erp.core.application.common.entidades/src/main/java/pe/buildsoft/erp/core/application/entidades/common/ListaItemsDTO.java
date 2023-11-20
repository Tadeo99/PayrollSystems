package pe.buildsoft.erp.core.application.entidades.common;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * La Class ListaItemsDTO.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Tue Apr 18 09:56:46 COT 2017
 * @since SIAA-CORE 2.1
 */
@Getter
@Setter
public class ListaItemsDTO implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id lista items. */
	private Long idListaItems;

	/** El descripcion. */
	private String descripcion;

	/** El estado. */
	private String estado;

	private String observacion;

	private List<ItemDTO> listaItemsItemList;
	
	private String descripcionView;

	/**
	 * Instancia un nuevo lista itemsDTO.
	 */
	public ListaItemsDTO() {
		super();
	}

	/**
	 * Instancia un nuevo lista itemsDTO.
	 *
	 * @param idListaItems el id lista items
	 * @param descripcion  el descripcion
	 * @param estado       el estado
	 */
	public ListaItemsDTO(Long idListaItems, String descripcion, String estado) {
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
		ListaItemsDTO other = (ListaItemsDTO) obj;
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
		return "ListaItemsDTO [idListaItems=" + idListaItems + "]";
	}

}